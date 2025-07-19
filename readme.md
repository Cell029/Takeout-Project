# 苍穹外卖项目

# 一、软件开发流程

**1) 第 1 阶段: 需求分析**

完成需求规格说明书、产品原型的编写。一般来说需求规格说明书就是使用 Word 文档来描述当前项目的各个组成部分，如：系统定义、应用环境、功能规格、性能需求等，都会在文档中描述。
产品原型，一般是通过网页（html）的形式展示当前的页面展示什么样的数据，页面的布局是什么样子的，点击某个菜单，打开什么页面，点击某个按钮，出现什么效果，都可以通过产品原型看到。

**2) 第 2 阶段: 设计**

设计的内容包含 UI 设计、数据库设计、接口设计。

- UI 设计：用户界面的设计，主要设计项目的页面效果，小到一个按钮，大到一个页面布局，还有人机交互逻辑的体现。
- 数据库设计：需要设计当前项目中涉及到哪些数据库，每一个数据库里面包含哪些表，这些表结构之间的关系是什么样的，表结构中包含哪些字段。
- 接口设计：通过分析原型图，首先，粗粒度地分析每个页面有多少接口，然后，再细粒度地分析每个接口的传入参数，返回值参数，同时明确接口路径及请求方式。

**3) 第 3 阶段: 编码**

编写项目代码、并完成单元测试。

- 项目代码编写：作为软件开发工程师，需要对项目的模块功能分析后，进行编码实现。
- 单元测试：编码实现完毕后，进行单元测试，单元测试通过后再进入到下一阶段。

**4) 第 4 阶段: 测试**

在该阶段中主要由测试人员, 对部署在测试环境的项目进行功能测试, 并出具测试报告。

**5) 第 5 阶段: 上线运维**

在项目上线之前，会由运维人员准备服务器上的软件环境安装、配置，配置完毕后，再将开发好的项目部署在服务器上运行。

****
# 二、开发环境搭建

## 1. 后端环境搭建

基础项目中主要有四个模块：

| **序号** | **名称**          | **说明**                                                     |
| -------- |-----------------| ------------------------------------------------------------ |
| 1        | Takeout-Project | maven父工程，统一管理依赖版本，聚合其他子模块                |
| 2        | sky-common      | 子模块，存放公共类，例如：工具类、常量类、异常类等           |
| 3        | sky-pojo        | 子模块，存放实体类、VO、DTO等                                |
| 4        | sky-server      | 子模块，后端服务，存放配置文件、Controller、Service、Mapper等 |

- sky-common：模块中存放的是一些公共类，可以供其他模块使用

  - constant：存放相关常量类
  - context：存放上下文类
  - enumeration：项目的枚举类存储
  - exception：存放自定义异常类 
  - json：处理 json 转换的类
  - properties：存放 SpringBoot 相关的配置属性类
  - result：返回结果类的封装
  - utils：常用工具类    

- sky-pojo：模块中存放的是一些 entity、DTO、VO

  - Entity：通常和数据库中的表对应，属性对应数据库表的字段；作为 ORM（对象关系映射）框架（如 MyBatis）操作数据库的载体，在对数据库进行增删改查时使用               
  - DTO：数据传输对象，通常用于程序中各层之间传递数据，它是 Entity 的轻量级版本，可能只包含部分属性或对属性进行组合、拆分，甚至有额外逻辑的字段；用于不同层之间传输数据，减少层之间的耦合，通常接收客户端传来的请求参数
  - VO：视图对象，为前端展示数据提供的对象，是按照前端需要的格式组装的对象，可以添加额外的展示字段，比如格式化后的时间，但通常只关心前端展示，不关心业务逻辑           
  - POJO：普通 Java 对象，只有属性和对应的 getter 和 setter，是 Entity、DTO、VO 等各种对象的基础。任何纯 Java 对象都可以称为 POJO

- sky-server：模块中存放的是 配置文件、配置类、拦截器、controller、service、mapper、启动类等

  - config：存放配置类       
  - controller：存放 controller 类 
  - interceptor：存放拦截器类     
  - mapper：存放 mapper 接口   
  - service：存放 service 类    
  - SkyApplication：启动类           

因为使用的是 Java 21，所以导入文件后需要对 pom 文件进行依赖的更新，使用的 springboot 为 3.3.3 版本：

```xml
<parent>
    <artifactId>spring-boot-starter-parent</artifactId>
    <groupId>org.springframework.boot</groupId>
    <version>3.3.3</version>
</parent>
```

而与之对应的 mybatis 的版本也要进行更新，使用互相适配的版本：

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

如果使用的依赖不能互相适配，就可能发生 Spring 将 Mapper 接口当作了普通的 Bean 来注册或声明，导致 Spring 尝试用错误的方式加载它，从而发生注入错误的情况：

```text
org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'employeeMapper' defined in file [D:\JavaBasicNotes\Takeout-Project\sky-server\target\classes\com\sky\mapper\EmployeeMapper.class]: Invalid value type for attribute 'factoryBeanObjectType': java.lang.String
```

然后还有很多相关的依赖都需要更新，大部分通过 AI 就能解决。

****
## 2. 前后端联调

后端的初始工程中已经实现了登录功能，直接进行前后端联调测试即可，前端请求地址：http://localhost/api/employee/login ，而后端的接口地址为：http://localhost:8080/admin/employee/login ，
这就是用到了 nginx 的反向代理技术，将前端发送的动态请求由 nginx 转发到后端服务器：

监听 80 端口号，然后访问 http://localhost:80/api/../.. 这样的接口的时候，它会通过 location /api/ {} 这样的反向代理到 http://localhost:8080/admin/ 

```nginx
server{
    listen 80;
    server_name localhost;
    location /api/{
        # proxy_pass：该指令是用来设置代理服务器的地址，可以是主机名称，IP 地址加端口号等形式
        proxy_pass http://localhost:8080/admin/; #反向代理
    }
}
```

nginx 反向代理的好处：

- 提高访问速度
  因为nginx本身可以进行缓存，如果访问的同一接口，并且做了数据缓存，nginx就直接可把数据返回，不需要真正地访问服务端，从而提高访问速度。

- 进行负载均衡
  所谓负载均衡,就是把大量的请求按照我们指定的方式均衡的分配给集群中的每台服务器。

- 保证后端服务安全
  因为一般后台服务地址不会暴露，所以使用浏览器不能直接访问，可以把nginx作为请求访问的入口，请求到达nginx后转发到具体的服务中，从而保证后端服务的安全。

nginx 负载均衡策略：

- 轮询：默认方式

```nginx
upstream webservers{
    server 192.168.100.128:8080;
    server 192.168.100.129:8080;
}
```

- weight：权重方式，默认为 1，权重越高，被分配的客户端请求就越多，以下就是 9 : 1

```nginx
upstream webservers{
    server 192.168.100.128:8080 weight=90;
    server 192.168.100.129:8080 weight=10;
}
```

- ip_hash：依据 ip 的哈希值进行分配，这样每个访客可以固定访问一个后端服务

```nginx
upstream webservers{
    ip_hash;
    server 192.168.100.128:8080;
    server 192.168.100.129:8080;
}
```

- least_conn：依据最少连接方式，把请求优先分配给连接数少的后端服务

```nginx
upstream webservers{
    least_conn;
    server 192.168.100.128:8080;
    server 192.168.100.129:8080;
}
```

- url_hash：依据 url 的哈希值进行分配，这样相同的 url 会被分配到同一个后端服务

```nginx
upstream webservers{
    hash &request_uri;
    server 192.168.100.128:8080;
    server 192.168.100.129:8080;
}
```

- fair：依据响应时间方式，响应时间短的服务将会被优先分配

```nginx
upstream webservers{
    server 192.168.100.128:8080;
    server 192.168.100.129:8080;
    fair;
}
```

****
## 3. 完善登录功能

初始项目中员工表中的密码是明文存储，安全性太低，所以选择使用 md5 加密的方式将密码加密后存储，提高安全性。而 MD5 是一种不可逆的哈希算法，对相同的输入总是产生相同的输出，

```java
password = DigestUtils.md5DigestAsHex(password.getBytes());
```

所以只要 password 的字符串内容一样，无论执行多少次，加密结果都是一致的，根据这个特性就可以让加密后的密码存入数据库，然后每次登陆时比对两个加密的密码即可。

****
## 4. 导入接口文档

当前学习的项目是前后端分离的，此时不再是一个代码工程，而是两个独立项目，前端通过 HTTP 请求调用后端接口获取数据；后端通过定义接口文档 告诉前端：有哪些接口、怎么用、返回什么数据。
在这种开发模式下，前后端人员往往需要并行开发。为了使前端在后端尚未完全开发完接口的情况下也能继续开发页面和逻辑，必须提前定义好接口规范。

这里是直接给好了两个接口文档，直接把它们导入导 ApiFox 软件中，创建对应的两个项目，一个用户端，一个管理端，分别对应不同的角色使用不同的接口请求，提供给前端使用。

****
## 5. Swagger

初始项目使用的是 SpringBoot 2 + Swagger 的组合来生成 Api 文档，使用的 knife4j 框架也是 2.x 版本的，而 SpringBoot 3.x 不支持了，所以得更新依赖，
现在使用的是 knife4j 4.4.0 + openapi 2.3.0 的 OpenAPI，它其实可以看作是 Swagger 3.0 版本，它是 Swagger 的一种升级。

Knife4j 与各个版本 Spring Boot 的兼容性情况如下：

- Spring Boot 1.5.x~2.0.0：对应 Knife4j 2.0.0 以下版本；
- Spring Boot 2.0-2.2：对应 Knife4j 2.0.0-2.0.6 版本； 
- Spring Boot 2.2.x-2.4.0：对应 Knife4j 2.0.6-2.0.9 版本； 
- Spring Boot 2.4.0-2.7.x：对应 Knife4j 4.0.0 及以上版本； 
- Spring Boot 3.0 及以上：对应 Knife4j 4.0.0 及以上版本。 

这意味着在 springboot 3 项目需要使用 Knife4j 4.0.0 及以上版本

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.4.0</version>
</dependency>

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

配置成功后访问 http://localhost:8080/doc.html ，可以看到一个 OpenAPI definition 页面，然后可以通过配置 yaml 文件的方式设置相关配置：

```yaml
# springdoc-openapi 项目配置
springdoc:
  swagger-ui:
    path: /swagger-ui.html # 指定 Swagger UI 的访问路径，现在设置的是默认路径，它会自动映射导 Knife4j 的默认访问路径 /doc.html
    tags-sorter: alpha # 接口文档页面中，标签（tags）排序方式，alpha 表示按字母顺序排序
    operations-sorter: alpha # 操作（接口方法）排序方式，也用字母序
  api-docs:
    path: /v3/api-docs # 这是 OpenAPI 生成的接口文档 JSON 文件路径，默认 /v3/api-docs，前端页面会根据它请求数据
  group-configs: # 支持多个接口分组配置，方便大型项目拆分不同模块接口文档
    - group: 'default' # 分组名，设置默认分组 default，
      paths-to-match: '/**' # 该分组扫描的接口路径，这里匹配所有接口 /** 
      packages-to-scan: com.sky.controller # 扫描的包路径，这里只扫描 com.sky 这个包里的 Controller，扫描出来的文档就放到当前设置的分组；如果后面还设置了分组，那么就让每个分组扫描不同的包，达到清晰定位的效果
      
# knife4j 的增强配置，不需要增强可以不配
knife4j:
  enable: true # 是否启用 Knife4j 增强功能，true 表示启用
  setting:
    language: zh_cn # 界面语言，这里设置为简体中文（zh_cn）
```

当然上述基本信息也可以通过创建配置类来达到相同的效果，但较复杂，不过可以结合配置类，给这些接口文档设置基本的信息，例如：

```java
@Bean
public OpenAPI customOpenAPI() {
    log.info("准备生成接口文档...");
    return new OpenAPI()
            .info(new Info()
                    .title("苍穹外卖项目接口文档")
                    .version("1.0")
                    .description("苍穹外卖项目接口文档"));
}
```

给接口文档页面设置了 “苍穹外卖项目接口文档” 标题，如果这些在 yaml 文件中设置就需要引入其他的依赖，所以结合使用有时挺好的，但不建议全部通过配置类来配置。
但需要注意的是，YAML 配置只告诉 Springdoc 或 Knife4j 要扫描哪个包（packages-to-scan）、匹配哪些路径（paths-to-match）、开启哪些分组（group），
但文档内容的描述（如标题、参数说明、响应结构、接口用途等），依赖的是注解来提供的元信息。

@Tag：用于为控制器类（即一组接口）打上分组标签，代替 Swagger2 的 @Api

- name：分组名（必填）
- description：分组描述

```java
@Tag(name = "员工管理", description = "提供员工的增删改查接口")
public class EmployeeController {
  ...
}
```

@Operation：用于描述某个接口方法的作用，相当于 Swagger2 的 @ApiOperation

- summary：简要说明（显示在文档首页接口列表）
- description：详细说明（显示在接口详情页）
- tags：标签数组（可覆盖 @Tag 定义的类级标签）
- operationId：唯一标识符（默认是方法名，可用于文档引用）
- method：请求方法类型（可选：GET、POST 等，通常不写）

@Parameter：用于描述接口的某个单个参数（路径参数、查询参数、头部参数等）

- name：参数名（当没有 @RequestBody 注解时必须填写 name 属性，否则无法被识别为 OpenAPI 参数，且与参数名一致）
- description：参数说明
- required：是否为必填项（默认为 false）
- example：示例值，用于文档展示
- in：参数所在位置：query, header, path, cookie

```java
@PostMapping("/login")
@Operation(summary = "员工登录", description = "根据用户名和密码进行员工登录校验")
public Result<EmployeeLoginVO> login(@RequestBody @Parameter(description = "员工登录信息") EmployeeLoginDTO employeeLoginDTO) {
}
```

```java
@PostMapping("/logout")
@Operation(summary = "退出登录")
public Result<String> logout() {
    return Result.success();
}
```

@Schema：用于标注字段或类的模型结构信息

- title：实体名标题
- description：实体整体的说明

用于字段时新增了两个：

- example：示例值
- requiredMode：是否必须，取值为 REQUIRED, NOT_REQUIRED

```java
@Data
@Schema(description = "员工登录时传递的数据模型")
public class EmployeeLoginDTO implements Serializable {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "密码")
    private String password;
}
```

****
# 三、新增员工

## 1. 新增员工

查询 ApiFox 中新增员工的接口，查看规定的前端传递参数列表：

```text
id integer <int64> 员工id 可选
idNumber string 身份证 必需
name string 姓名 必需
phone string 手机号 必需
sex string 性别 必需
username string 用户名 必需
```

当前端提交的数据和实体类中对应的属性差别比较大时，建议使用 DTO 来封装数据，所以这里使用 EmployeeDTO 来封装数据，然后就是一些简单的新增逻辑：[EmployeeServiceImpl#save](./sky-server/src/main/java/com/sky/service/impl/EmployeeServiceImpl.java)

****
## 2. 新增员工的功能测试

功能测试实现方式分两种：通过接口文档测试和通前后端联调测试

通过接口文档：

在通常情况下，前端页面可能没有设计完，所以无法通过前端页面的形式进行测试，但可以通过生成的接口文档进行测试，访问 http://localhost:8080/doc.html ，
进入新增员工接口，选择调试，然后输入对应的调试数据，点击发送请求：

```json
{
  "id": 0,
  "username": "zhangsan",
  "name": "张三",
  "phone": "16676538878",
  "sex": "1",
  "idNumber": "647262548991901241"
}
```

此时只返回了一个 401 响应码，证明添加新用户失败，通过断点调试，发现新增请求被 jwt 拦截器拦截：

```java
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      // 判断当前拦截到的是 Controller 的方法还是其他资源
      if (!(handler instanceof HandlerMethod)) {
          // 放行静态资源等非控制器请求
          return true;
      }

      // 从请求头中获取令牌
      String token = request.getHeader(jwtProperties.getAdminTokenName());

      // 校验令牌
      try {
          log.info("jwt 校验:{}", token);
          Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
          Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
          log.info("当前员工 id：{}", empId);
          // 通过，放行
          return true;
      } catch (Exception ex) {
          // 不通过，响应 401 状态码
          response.setStatus(401);
          return false;
      }
  }
```

该拦截器就是专门拦截未登录用户的，如果没登陆则无法访问任何路径，当登录成功后会生成一段 jwt 密钥用于后续判断

```java
// 登录成功后，生成 jwt 令牌
Map<String, Object> claims = new HashMap<>();
claims.put(JwtClaimsConstant.EMP_ID, employee.getId()); // 向 Token 中添加一个自定义字段：当前登录员工的 ID
String token = JwtUtil.createJWT(
        jwtProperties.getAdminSecretKey(), // 设置 jwt 密钥，写在了 yaml 中
        jwtProperties.getAdminTtl(), // 设置过期时间
        claims);
```

所以测试时应该先发送一个登录请求，获取请求头中的 jwt 密钥，也就是 token（在 yaml 定义名字为 token 了），把它设置成全局参数，当再次断点调试时，
它就会被包含在发送的测试请求的请求头中:

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

此时返回响应码 200，代表测试成功。

****
## 3. 功能完善

目前，程序存在的问题主要有两个：

- 录入的用户名已存，抛出的异常后没有处理
- 新增员工时，创建人 id 和修改人 id 设置为固定值

因为 employee 表中的 username 字段设置为了唯一，所以再次添加相同 username 的用户时会抛出异常

```text
Duplicate entry 'zhangsan' for key 'employee.idx_username'
```

但此时前端页面没有相关的错误信息展示出来，所以需要在处理异常的地方新增逻辑，当插入相同 username 的用户时应该给出提示

```java
@ExceptionHandler
public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
    // Duplicate entry 'zhangsan' for key 'employee.idx_username'
    String message = ex.getMessage();
    if(message.contains("Duplicate entry")){
        String[] split = message.split(" ");
        String username = split[2];
        String msg = username + MessageConstant.ALREADY_EXISTS;
        return Result.error(msg);
    }else{
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
```

此时前端给出相应的错误信息:

```json
{
  "code": 0,
  "msg": "'zhangsan'账户已存在",
  "data": null
}
```

而对于动态获取当前登录用户的 id，则可以使用 ThreadLocal，ThreadLocal 为每个线程提供单独一份存储空间，具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问。
在进行 jwt 拦截时，可以从 token 中获取用户 id，然后存入 ThreadLocal

```java
Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
// 将当前登录用户的 id 保存到 ThreadLocal 中
BaseContext.setCurrentId(empId);
```

后续需要使用时直接从 ThreadLocal 中获取即可

```java
// 设置当前记录创建人 id 和修改人 id
employee.setCreateUser(BaseContext.getCurrentId());
employee.setUpdateUser(BaseContext.getCurrentId());
```

但如果想要向 ThreadLocal 中存放多个不同类型的数据，就要封装不同的 ThreadLocal，例如封装一个 currentUsername 专门动态获取用户名，当然为了节省麻烦可以直接把用户信息封装成对象存入 ThreadLocal

```java
private static final ThreadLocal<String> currentUsername = new ThreadLocal<>();

public static void setCurrentUsername(String username) {
  currentUsername.set(username);
}

public static String getCurrentUsername() {
  return currentUsername.get();
}
```

****
# 四、员工分页查询

## 1. 功能实现

系统中的员工很多的时候，如果在一个页面中全部展示出来会显得比较乱，不便于查看，所以一般的系统中都会以分页的方式来展示列表数据。而在分页查询页面中，除了分页条件以外，
还有一个查询条件 "员工姓名"。从接口文档中发现请求参数类型为 Query，不是 json 格式提交，所以直接在路径后拼接URL 查询参数，
例如：/admin/employee/page?name=zhangsan。返回的响应数据则是一个用数组封装的 json 对象，每个数组是一个 Employee 类型对象，封装员工的属性。

Controller 层：

EmployeePageQueryDTO 是用来封装一些简单的传递参数，包括起始页、每页条数和用来查询的员工姓名。

```java
// 分页查询员工信息
@GetMapping("/page")
@Operation(summary = "员工分页查询")
public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
    log.info("员工分页查询，参数为：{}", employeePageQueryDTO);
    PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
    return Result.success(pageResult);
}
```

Service 层：

```java
@Override
public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
    // select * from employee limit 0, 10
    // 开始分页查询
    PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
    Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
    // 获取查询到的记录条数
    long total = page.getTotal();
    List<Employee> records = page.getResult();
    return new PageResult(total, records);
}
```

关于 startPage 方法，传入了起始页和每页条数即可启动分页，实际上是调用了下面的重载方法，同时使用默认值 DEFAULT_COUNT = true

```java
// 默认会统计总记录数
protected static boolean DEFAULT_COUNT = true;

public static <E> Page<E> startPage(int pageNum, int pageSize) {
    return startPage(pageNum, pageSize, DEFAULT_COUNT);
}
```

```java
public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count) {
    return startPage(pageNum, pageSize, count, (Boolean)null, (Boolean)null);
}
```

```java
public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count, Boolean reasonable, Boolean pageSizeZero) {
    Page<E> page = new Page(pageNum, pageSize, count);
    page.setReasonable(reasonable);
    page.setPageSizeZero(pageSizeZero);
    Page<E> oldPage = getLocalPage();
    if (oldPage != null && oldPage.isOrderByOnly()) {
        page.setOrderBy(oldPage.getOrderBy());
    }

    setLocalPage(page);
    return page;
}
```

通过 Page<E> oldPage = getLocalPage(); 获取当前线程已有的分页对象

```java
protected static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal();

public static <T> Page<T> getLocalPage() {
    return (Page)LOCAL_PAGE.get();
}
```

最后将新数据再存入 ThreadLoad 中，所以通过 ThreadLoad 的方式动态保证每个分页请求的信息独立（每个请求只看到属于自己线程的分页参数）

```java
protected static void setLocalPage(Page page) {
    LOCAL_PAGE.set(page);
}
```

****
## 2. 测试与日期格式优化

访问 http://localhost:8080/doc.html ，进入员工分页查询，虽然能够正常展示每页的数据，但是最后操作时间格式不清晰，没有使用标准的形式展示：

```json
{
  "code": 1,
  "msg": null,
  "data": {
    "total": 0,
    "records": [
      {
        "id": 2,
        "username": "zhangsan",
        "name": "张三",
        "password": "e10adc3949ba59abbe56e057f20f883e",
        "phone": "16676538878",
        "sex": "1",
        "idNumber": "647262548991901241",
        "status": 1,
        "createTime": "2025-07-10T22:16:19",
        "updateTime": "2025-07-10T22:16:19",
        "createUser": 10,
        "updateUser": 10
      },
      {
        "id": 1,
        "username": "admin",
        "name": "管理员",
        "password": "e10adc3949ba59abbe56e057f20f883e",
        "phone": "13812312312",
        "sex": "1",
        "idNumber": "110101199001010047",
        "status": 1,
        "createTime": "2022-02-15T15:51:20",
        "updateTime": "2022-02-17T09:16:20",
        "createUser": 10,
        "updateUser": 1
      }
    ]
  }
}
```

所以可以给对应的字段上添加注解，对日期进行格式化：

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime createTime;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime updateTime;
```

或者添加自定义的消息转换器，统一对日期类型进行格式处理，但需要注意的是：Spring Boot 默认使用 Jackson 作为 JSON 解析器，自动注册一个 MappingJackson2HttpMessageConverter，
springdoc-openapi（和 Knife4j）依赖默认的 Jackson 配置，来序列化 OpenAPI 规范文档（JSON 格式），然后供前端 swagger-ui.html 或 doc.html 读取渲染接口文档，
如果直接写了一个 JacksonObjectMapper，扩展了日期时间格式等自定义序列化规则，并在配置中用如下代码新增一个消息转换器：

```java
// JacksonObjectMapper 为自定义的转换器
converters.add(0, new MappingJackson2HttpMessageConverter(new JacksonObjectMapper())); 
```

这种操作其实是覆盖了默认的转换器链，导致 springdoc-openapi 用的转换器变成了自定义的，而自定义的 Jackson 配置不完全兼容它生成的接口文档数据结构，
或者导致格式不对。于是访问接口文档的 JSON 时候，格式不对或缺少 openapi 版本字段，导致页面渲染失败，提示：

```text
Unable to render this definition
The provided definition does not specify a valid version field.
```

所以不能直接用 converters.add(0, ...) 新建转换器覆盖，因为 Spring MVC 的消息转换器是按顺序维护的，一个请求匹配第一个合适的转换器执行，如果把自定义的转换器放最前面，
那么所有请求都会优先用你的转换器，包括 springdoc-openapi 也会使用。

SpringBoot 中默认的转换器如下：

- ByteArrayHttpMessageConverter：处理 byte[] 数组
- StringHttpMessageConverter：处理 String
- ResourceHttpMessageConverter：处理 Resource，文件流、静态资源等
- SourceHttpMessageConverter：处理 javax.xml.transform.Source，主要用于 XML 文件
- AllEncompassingFormHttpMessageConverter：处理表单提交 application/x-www-form-urlencoded 和 multipart/form-data
- MappingJackson2HttpMessageConverter：处理 application/json，这是 Spring Boot 默认用来将 Java 和 JSON 转换的
- MappingJackson2XmlHttpMessageConverter：处理 application/xml
- ......

而在这里要进行的替换操作，就是替换掉 MappingJackson2HttpMessageConverter 转换器，它使用的 ObjectMapper 是 Spring Boot 默认配置的，包括日期格式、字段命名策略等。
是 springdoc-openapi 和 knife4j 生成接口文档时用于响应示例结构的关键组件。而用自定义的 JacksonObjectMapper 替换了 Spring 默认的 ObjectMapper，
保留了这个转换器的类型、顺序、支持的 MIME 类型，只替换了它内部用来处理 JSON 的工具类 ObjectMapper，用于定制日期格式。不影响 springdoc-openapi 和 knife4j 对默认转换器的依赖。

```java
@Override
public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 正确替换 ObjectMapper，而不是插入新 converter
    for (HttpMessageConverter<?> converter : converters) {
        if (converter instanceof MappingJackson2HttpMessageConverter) {
            ((MappingJackson2HttpMessageConverter) converter)
                    .setObjectMapper(new JacksonObjectMapper());
            break;
        }
    }
}
```

```java
public class JacksonObjectMapper extends ObjectMapper {
    // 定义了三个标准时间格式，用于格式化 LocalDate、LocalDateTime、LocalTime
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();

        // 允许对象中含有未知属性，不报错
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 避免以时间戳的形式输出

        // 注册 Java 8 时间模块，并设置自定义格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 自定义序列化器，将 LocalDateTime 类型转换为 "yyyy-MM-dd HH:mm:ss" 格式字符串
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        // 自定义反序列化器，将 "yyyy-MM-dd HH:mm:ss" 格式字符串转换为 Java 中的 LocalDateTime 对象
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        // LocalDate 和 LocalTime 同理
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));

        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        // 注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(javaTimeModule);
    }
}
```

如果新建一个 MappingJackson2HttpMessageConverter 就会覆盖掉默认 converter 的优先级，而自定义的新的 converter 没有 Spring Boot 自动配置的参数，比如默认媒体类型、字符集等，
但 knife4j 和 springdoc-openapi 依赖的是原来的 converter，所以它们不一定能兼容新的 converter。

```java
MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
converter.setObjectMapper(new JacksonObjectMapper());
converters.add(0, converter); // 放到最前面
```

设置成功后的展示：

```json
{
  "code": 1,
  "msg": null,
  "data": {
    "total": 0,
    "records": [
      {
        ...
        "createTime": "2025-07-10 22:16:19",
        "updateTime": "2025-07-10 22:16:19",
      },
      {
        ...
        "createTime": "2022-02-15 15:51:20",
        "updateTime": "2022-02-17 09:16:20",
      }
    ]
  }
}
```

****
# 五、启用、禁用员工账号功能

## 1. 功能实现

在员工管理列表页面，可以对某个员工账号进行启用或者禁用操作。账号禁用的员工不能登录系统，启用后的员工可以正常登录。如果某个员工账号状态为正常，则按钮显示为"禁用"，如果员工账号状态为已禁用，则按钮显示为"启用"。
在接口文档中，该功能的请求路径为：/admin/employee/status/{status}，路径参数携带状态值，但同时请求参数为员工 id。

Controller 层：

```java
@PostMapping("/status/{status}")
@ApiOperation("启用、禁用员工账号")
public Result startOrStop(@PathVariable Integer status,Long id){
    log.info("启用、禁用员工账号状态值与员工 id：{},{}",status,id);
    employeeService.startOrStop(status,id);//后绪步骤定义
    return Result.success();
}
```

Service 层：

通过员工 id 作为条件对员工的 status 字段进行修改，1 为启用，0 为禁用

```java
@Override
public void startOrStop(Integer status, Long id) {
    Employee employee = Employee.builder()
            .status(status)
            .id(id)
            .build();
    employeeMapper.update(employee);
}
```

****
## 2. 测试

在 API 管理界面输入对应的 status 和 员工 id，发送请求，返回：

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

但目前还没有设置员工登录时的状态判断，后续添加。

****
# 六、编辑员工信息

在员工管理列表页面点击 "编辑" 按钮，跳转到编辑页面，在编辑页面回显员工信息并进行修改，最后点击 "保存" 按钮完成编辑操作。所以该编辑操作包含一个根据 id 查询的功能，
点击 "编辑" 按钮时应该触发查询操作，点击 "保存" 按钮时完成修改操作，因为是修改操作，所以一般是通过表单提交，则传递的表单数据是 json 对象，即修改后的员工信息。

controller 层：

```java
@GetMapping("/{id}")
@Operation("根据id查询员工信息")
public Result<Employee> getById(@PathVariable Long id){
    Employee employee = employeeService.getById(id);
    return Result.success(employee);
}
```

```java
@PutMapping
@Operation(summary = "编辑员工信息")
public Result update(@RequestBody EmployeeDTO employeeDTO){
    log.info("编辑员工信息：{}", employeeDTO);
    employeeService.update(employeeDTO);
    return Result.success();
}
```

Service 层：

```java
@Override
public Employee getById(Long id) {
    Employee employee = employeeMapper.getById(id);
    employee.setPassword("****");
    return employee;
}
```

```java
@Override
public void update(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO, employee);
    employee.setUpdateTime(LocalDateTime.now());
    employee.setUpdateUser(BaseContext.getCurrentId());
    employeeMapper.update(employee);
}
```

****
## 2. 测试

先查询：

```json
{
  "code": 1,
  "msg": null,
  "data": {
    "id": 2,
    "username": "zhangsan",
    "name": "张三",
    "password": "****",
    "phone": "16676538878",
    "sex": "1",
    "idNumber": "647262548991901241",
    "status": 1,
    "createTime": "2025-07-10 22:16:19",
    "updateTime": "2025-07-10 22:16:19",
    "createUser": 10,
    "updateUser": 10
  }
}
```

再修改：

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

****
# 七、分类模块功能

## 1. 功能实现

后台系统中可以管理分类信息，分类包括两种类型，分别是菜品分类和套餐分类：

- 菜品分类：新增菜品分类、菜品分类分页查询、根据 id 删除菜品分类、修改菜品分类、启用禁用菜品分类、分类类型查询
- 套餐分类同理

[CategoryController](./sky-server/src/main/java/com/sky/controller/admin/CategoryController.java)

****
# 八、公共字段自动填充

## 1. 分析

在新增员工或者新增菜品分类时需要设置创建时间、创建人、修改时间、修改人等字段，在编辑员工或者编辑菜品分类时需要设置修改时间、修改人等字段。
这些字段属于公共字段，也就是也就是在系统中很多表中都会有这些字段，如下：

| **序号** | **字段名**  | **含义** | **数据类型** |
| -------- | ----------- |--------| ------------ |
| 1        | create_time | 创建时间   | datetime     |
| 2        | create_user | 创建人 id | bigint       |
| 3        | update_time | 修改时间   | datetime     |
| 4        | update_user | 修改人 id | bigint       |

针对于这些字段，当前的赋值方式为将 createTime、updateTime 设置为当前时间，createUser、updateUser 设置为当前登录用户ID；将 updateTime 设置为当前时间，updateUser 设置为当前登录用户 ID。
这种方式造成了很多冗余的代码，如果后期发生变更，就较难维护。所以可以利用 AOP 切面编程，实现功能增强，来完成公共字段自动填充功能。

****
## 2. 功能实现

因为当前需要自动填充的公共字段只有 insert 和 update 方法会用到，所以可以自定义一个注解，用来标记需要添加切入点的方法：

```java
// 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 数据库操作类型：UPDATE INSERT
    OperationType value();
}
```

该注解的 value 为数据库的操作类型，后续可以通过反射机制拿到这个 value，用来判断需要添加哪些数据。然后就是创建 [AutoFillAspect](./sky-server/src/main/java/com/sky/aspect/AutoFillAspect.java) 类，

```java
// 获取到当前被拦截的方法上的 @AutoFill 注解中的 value 的值（数据库操作类型）
MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 方法签名对象
AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 获得方法上的注解对象
OperationType operationType = autoFill.value(); // 获得数据库操作类型
```

JoinPoint 是 Spring AOP 提供的接口，表示当前连接点，通过它可以获取到拦截的方法的相关信息，signature.getMethod() 就相当于反射机制中的 XxxClass.class.getMethod(...)，
但因为在 AOP 中 Spring 已经通过代理对象获取到了当前执行的方法上下文，所以不需要用到常规的反射机制。

```java
// 通过反射获取实体的 setter 方法并设置传入的参数类型
Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
// 通过反射为对象属性赋值
setCreateTime.invoke(entity, now);
setCreateUser.invoke(entity, currentId);
setUpdateTime.invoke(entity, now);
setUpdateUser.invoke(entity, currentId);
```

不过想要获取到实体类中的 setter 方法，还是要用到反射机制，通过该实体的方法名获取到对应的方法，然后再提前设置数据，达到自动填充的效果。

****
# 九、新增菜品

## 1. 分析

后台系统中可以管理菜品信息，通过新增功能来添加一个新的菜品，在添加菜品时需要选择当前菜品所属的菜品分类，并且需要上传菜品图片。新增菜品的方式是通过表单提交，
填写完表单信息，点击 "保存" 按钮后，会提交该表单的数据到服务端，在服务端中需要接受数据，然后将数据保存至数据库中。这里规定了菜品名称必须是唯一的、
菜品必须属于某个分类下，不能单独存在、新增菜品时可以根据情况选择菜品的口味、并且每个菜品必须对应一张图片。

****
## 2. 文件上传

### 2.1 本地存储

上传文件的原始 form 表单，要求表单必须具备以下三点（上传文件页面三要素）：

- 表单必须有 file 域，用于选择要上传的文件
- 表单提交方式必须为 POST：通常上传的文件会比较大，所以需要使用 POST 提交方式
- 表单的编码类型 enctype 必须要设置为：multipart/form-data（普通默认的编码格式是不适合传输大型的二进制数据的）

基于以上要求，后端就必须使用 MultipartResolver 来解析该类型的请求体，而请求体中的文件部分会被封装为 MultipartFile 对象，由框架自动注入到你的控制器参数中。

```java
@PostMapping("/upload")
public Result upload(MultipartFile file) throws Exception {
    log.info("上传文件：{}", file);
    if(!file.isEmpty()){
      // 生成唯一文件名
      String originalFilename = file.getOriginalFilename();
      String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
      String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;
      // 拼接完整的文件路径
      File targetFile = new File(UPLOAD_DIR + uniqueFileName);

      // 如果目标目录不存在，则创建它
      if (!targetFile.getParentFile().exists()) {
        targetFile.getParentFile().mkdirs();
      }
      // 保存文件
      file.transferTo(targetFile);
    }
    return Result.success();
}
```

MultipartFile 常见方法：

- getOriginalFilename()：获取原始文件名
- transferTo(File dest)：将接收的文件转存到磁盘文件中
- getSize()：获取文件的大小，单位：字节
- getBytes()：获取文件内容的字节数组
- getInputStream()：获取接收到的文件内容的输入流

在默认情况下，文件上传时默认单个文件最大大小为 1M，不过可以在配置文件中进行修改:

```yaml
spring：
  servlet:
    multipart:
      max-file-size: 10MB # 最大单个文件上传大小
      max-request-size: 100MB # 单次最大请求文件的大小（包括文件和表单数据）
```

不过这种本地存储文件的方式存在一些问题：

- 不安全，磁盘如果损坏，所有的文件就会丢失
- 容量有限，如果存储大量的图片，磁盘空间有限(磁盘不可能无限制扩容)
- 无法直接访问
  
为了解决上述问题，通常有两种解决方案：

- 自己搭建存储服务器，如：fastDFS 、MinIO
- 使用现成的云服务，如：阿里云，腾讯云，华为云

****
### 2.2 阿里云 OSS

#### 1. 配置

1. 注册阿里云账户（注册完成后需要实名认证）
2. 注册完账号之后，登录阿里云
3. 通过控制台找到对象存储 OSS 服务并开通
4. 进入到阿里云对象存储的控制台
5. 点击左侧的 "Bucket列表"，创建一个Bucket
6. 输入 Bucket 的相关信息（一般只输入存储空间名字，其他默认）
7. 点击 "AccessKey管理"，进入到管理页面创建 AccessKey
8. 以管理员身份打开 CMD 命令行，执行如下命令，配置系统的环境变量

```text
set OSS_ACCESS_KEY_ID=对应生成的ID
set OSS_ACCESS_KEY_SECRET=对应生成的密钥
```

9. 执行如下命令，让更改生效

```text
setx OSS_ACCESS_KEY_ID "%OSS_ACCESS_KEY_ID%"
setx OSS_ACCESS_KEY_SECRET "%OSS_ACCESS_KEY_SECRET%"
```

10. 执行如下命令，验证环境变量是否生效

```text
echo %OSS_ACCESS_KEY_ID%
echo %OSS_ACCESS_KEY_SECRET%
```

****
#### 2. 程序的使用

引入依赖：

```xml
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.17.4</version>
</dependency>
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>3.0.1</version>
</dependency>
<dependency>
    <groupId>com.sun.activation</groupId>
    <artifactId>jakarta.activation</artifactId>
    <version>2.0.1</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>4.0.3</version>
</dependency>
```

上传文件[样例代码](./sky-server/src/test/java/test.java)

在以上代码中，需要替换的内容为：
- endpoint：阿里云 OSS 中的 bucket 对应的域名
- bucketName：Bucket 名称
- objectName：对象名称，在 Bucket 中存储的对象的名称
- region：bucket 所属区域

****
#### 3. 集成 OSS

引入阿里云 OSS 上传文件工具类（由官方的示例代码改造而来）：

```java
@Data
@Slf4j
@Component
@NoArgsConstructor
@AllArgsConstructor
public class AliOssUtil {

  @Autowired
  private AliOssProperties aliOssProperties;
  
  public String upload(byte[] content, String originalFilename) throws Exception {

    String endpoint = aliOssProperties.getEndpoint();
    String bucketName = aliOssProperties.getBucketName();
    String region = aliOssProperties.getRegion();
      
    // 从环境变量中获取访问凭证，请确保已设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET
    EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    // 填写 Object 完整路径，例如 202507/1.png。Object 完整路径中不能包含 Bucket 名称。
    // 获取当前系统日期的字符串,格式为 yyyy/MM
    String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
    // 生成一个新的不重复的文件名
    String newFileName = UUID.randomUUID().toString().replace("-", "") + originalFilename.substring(originalFilename.lastIndexOf("."));
    String objectName = dir + "/" + newFileName;

    // 创建 OSSClient 实例。
    ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
    clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
    OSS ossClient = OSSClientBuilder.create()
            .endpoint(endpoint)
            .credentialsProvider(credentialsProvider)
            .clientConfiguration(clientBuilderConfiguration)
            .region(region)
            .build();

    try {
      ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
    } finally {
      ossClient.shutdown();
    }
    // 因为前端页面最终通过返回的 url 访问图片，所以要把具体的 url 拼接起来
    // endpoint：https://oss-cn-beijing.aliyuncs.com
    // 例如：https://cell-sky-take-out.oss-cn-beijing.aliyuncs.com/2025/07/cd6a7db2161d472783fca141f7395f8b.jpg
    return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
  }
}
```

在新增菜品的时候，上传菜品的图像，而之所以需要上传菜品的图像，是因为需要在系统页面当中访问并展示菜品的图像。而要想完成这个操作，需要做两件事：

1. 需要上传菜品的图像，并把图像保存起来（存储到阿里云 OSS）
2. 访问菜品图像（通过图像在阿里云 OSS 的存储地址访问图像）
   - OSS中的每一个文件都会分配一个访问的url，通过这个url就可以访问到存储在阿里云上的图片。所以需要把url返回给前端，这样前端就可以通过 url 获取到图像。

****
#### 4. 测试

在 API 管理页面，选择上传的文件，发送后，返回：

```json
{
  "code": 1,
  "msg": null,
  "data": "https://cell-sky-take-out.oss-cn-beijing.aliyuncs.com/2025/07/cd6a7db2161d472783fca141f7395f8b.jpg"
}
```

****
## 3. 新增菜品

Controller 层：

```java
@PostMapping
@Operation(summary = "新增菜品")
public Result save(@RequestBody DishDTO dishDTO) {
    log.info("新增菜品：{}", dishDTO);
    dishService.saveWithFlavor(dishDTO);
    return Result.success();
}
```

Service 层：

因为新增菜品时要连带口味一起选择，而口味是一张新的表，它们靠菜品的 ID 作为逻辑外键达到相互关联的效果，所以插入新的菜品时需要获取到该菜品的主键 ID，
而前端选择口味时，将这些口味信息封装进 DishDTO 中了，因为可能选择多个口味，所以通过一个 List 集合接收，所以再把这些口味信息同时插入到 dish_flavor 表中即可。

```java
@Override
public void saveWithFlavor(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    // 向菜品表插入 1 条数据
    dishMapper.insert(dish);
    // 获取 insert 语句生成的主键值
    Long dishId = dish.getId();
    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && !flavors.isEmpty()) {
        flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
        // 向口味表插入 n 条数据
        dishFlavorMapper.insertBatch(flavors);
    }
}
```

Mapper 层：

通过使用 useGeneratedKeys="true" 表示要获取到插入数据时生成的主键，keyProperty="id" 则是将主键赋值给 dish 表的 id。

```sql
<insert id="insert" useGeneratedKeys="true" keyProperty="id">
    insert into dish (name, category_id, price, image, description, create_time, update_time, create_user,update_user, status)
        values 
    (#{name}, #{categoryId}, #{price}, #{image}, #{description}, #{createTime}, #{updateTime}, #{createUser},#{updateUser}, #{status})
</insert>
```

因为 Service 层传递过来的参数是一个 List 类型的 flavors，所以使用批量插入语句将多个 DishFlavor 对象插入 dish_flavor 表中。

```sql
<insert id="insertBatch">
    insert into dish_flavor (dish_id, name, value) VALUES
    <foreach collection="flavors" item="df" separator=",">
        (#{df.dishId},#{df.name},#{df.value})
    </foreach>
</insert>
```

****
## 4. 菜品分页查询

在菜品列表展示时，除了菜品的基本信息（名称、售价、售卖状态、最后操作时间）外，还有两个字段较为特殊，第一个是图片字段，从数据库查询出来的仅仅是图片的名字，
图片要想在表格中回显展示出来，就需要下载这个图片，所以之前上传图片后返回给前端的是图片的 url。第二个是菜品分类，这里展示的是分类名称，而不是分类 ID，此时就需要根据菜品的分类 ID，去分类表中查询分类信息，
然后在页面展示。所以封装一个 DishVO，将需要的数据封装进该 VO 对象，然后再返回给前端。

```sql
<select id="pageQuery" resultType="com.sky.vo.DishVO">
  select d.* , c.name as categoryName from dish d left outer join category c on d.category_id = c.id
  <where>
      <if test="name != null">
          and d.name like concat('%',#{name},'%')
      </if>
      <if test="categoryId != null">
          and d.category_id = #{categoryId}
      </if>
      <if test="status != null">
          and d.status = #{status}
      </if>
  </where>
  order by d.create_time desc
</select>
```

****
## 5. 删除菜品

可以一次删除一个菜品，也可以批量删除菜品，但起售中的菜品不能删除，被套餐关联的菜品不能删除，删除菜品后，关联的口味数据也需要删除掉。所以删除操作涉及到了
dish、dish_flavor、setmeal_dish（套餐和菜品的关系表）三个表。

Controller 层：

不管是单个删除还是批量删除，都可以直接通过批量删除的方式，所以只需要写一个删除逻辑即可。

```java
@DeleteMapping
@Operation(summary = "菜品批量删除")
public Result delete(@RequestParam List<Long> ids) {
  log.info("菜品批量删除：{}", ids);
  dishService.deleteBatch(ids);
  return Result.success();
}
```

Service 层：

因为起售中和与套餐关联的菜品不能删除，所以在删除前需要进行一次查询判断，通过查询该菜品的 status 和是否能通过该菜品的 id 查询到套餐的数据来进行判断。
最后再根据勾选中的菜品 id 进行菜品和对应口味的删除即可。

```java
@Override
@Transactional
public void deleteBatch(List<Long> ids) {
    // 判断当前菜品是否能够删除（是否存在起售中的菜品）
    for (Long id : ids) {
        Dish dish = dishMapper.getById(id);
        if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)) {
            // 当前菜品处于起售中，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
    }
    // 判断当前菜品是否能够删除（是否被套餐关联）
    List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
    if (setmealIds != null && !setmealIds.isEmpty()) {
        // 当前菜品被套餐关联了，不能删除
        throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
    }
    // 根据菜品 id 集合删除菜品表中的菜品数据
    dishMapper.deleteByIds(ids);
    // 根据菜品 id 集合删除菜品关联的口味数据
    dishFlavorMapper.deleteByDishIds(ids);
}
```

Mapper 层：

使用动态拼接的批量删除方式

```sql
<insert id="insertBatch">
    insert into dish_flavor (dish_id, name, value) VALUES
    <foreach collection="flavors" item="df" separator=",">
        (#{df.dishId},#{df.name},#{df.value})
    </foreach>
</insert>
```

****
## 6. 修改菜品

在菜品管理列表页面点击修改按钮，应该显示菜品的相关信息，所以涉及根据 id 查询的操作。

Controller 层：

```java
@GetMapping("/{id}")
@Operation(summary = "根据id查询菜品")
public Result<DishVO> getById(@PathVariable Long id) {
    log.info("根据id查询菜品：{}", id);
    DishVO dishVO = dishService.getByIdWithFlavor(id);
    return Result.success(dishVO);
}
```

```java
@PutMapping
@Operation(summary = "修改菜品")
public Result update(@RequestBody DishDTO dishDTO) {
    log.info("修改菜品：{}", dishDTO);
    dishService.updateWithFlavor(dishDTO);
    return Result.success();
}
```

Service 层：

因为前端的展示内容是一个整合项，所以还是返回一个 DishVO 对象

```java
@Override
public DishVO getByIdWithFlavor(Long id) {
    // 根据 id 查询菜品数据
    Dish dish = dishMapper.getById(id);
    // 根据菜品 id 查询口味数据
    List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
    // 将查询到的数据封装到 VO
    DishVO dishVO = new DishVO();
    BeanUtils.copyProperties(dish, dishVO);
    dishVO.setFlavors(dishFlavors);
    return dishVO;
}
```

修改菜品较为简单，直接通过 update 修改相关信息即可，但是口味的修改较为复杂，因为可能涉及多个删除或添加口味，所以可以在执行修改操作时，直接把所有的口味全删除，
然后再把表单提交的口味添加即可。

```java
@Override
public void updateWithFlavor(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    // 修改菜品表基本信息
    dishMapper.update(dish);
    // 删除原有的口味数据
    dishFlavorMapper.deleteByDishId(dishDTO.getId());
    // 重新插入口味数据
    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && !flavors.isEmpty()) {
        flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
        // 向口味表插入 n 条数据
        dishFlavorMapper.insertBatch(flavors);
    }
}
```
****
## 7. 菜品启售、停售功能

该功能与员工启用、禁用类似。

Controller 层：

```java
@PostMapping("/status/{status}")
@Operation(summary = "起售、停售菜品")
public Result startOrStop(@PathVariable Integer status, Long id) {
    log.info("起售、停售菜品：{},{}", status, id);
    dishService.startOrStop(status, id);
    return Result.success();
}
```

Service 层：

```java
@Override
public void startOrStop(Integer status, Long id) {
    Dish dish = Dish.builder()
            .status(status)
            .id(id)
            .build();
    dishMapper.update(dish);
}
```

****
# 十、套餐管理

## 1. 新增套餐

套餐名称唯一，必须属于某个分类，必须包含菜品，名称、分类、价格、图片为必填项，添加菜品窗口需要根据分类类型来展示菜品，新增的套餐默认为停售状态。
所以在添加套餐时需要用到通过菜品分类 id 或菜品名查询对应的菜品信息。

```java
@GetMapping("/list")
@Operation(summary = "根据分类id查询菜品")
public Result<List<Dish>> list(Long categoryId){
    List<Dish> list = dishService.list(categoryId);
    return Result.success(list);
}

@Override
public List<Dish> list(Long categoryId) {
  Dish dish = Dish.builder()
          .categoryId(categoryId)
          .status(StatusConstant.ENABLE)
          .build();
  return dishMapper.list(dish);
}
```

新增套餐：

Controller 层：

因为新增套餐的同时需要选择归属该套餐的菜品，所以新增操作中需要让这些菜品关联到 setmeal 表，所以放在同一个 service 方法中。而前端页面提交的表单数据不止包含 setmeal 表的内容，
所以封装了个 SetmealDTO 来接受所有的数据。

```java
@PostMapping
@Operation(summary = "新增套餐")
public Result save(@RequestBody SetmealDTO setmealDTO) {
    setmealService.saveWithDish(setmealDTO);
    return Result.success();
}
```

Service 层：

从 SetmealDTO 对象中获取 setmeal 表中的内容，然后插入 setmeal 表，然后通过插入 setmeal 表时生成的套餐 Id 赋值给前端选择的那些菜品，所以要使用一个 setmeal_dish 关联表，
通过套餐 Id 关联菜品 Id，SetmealDTO 中有 SetmealDish 类型的集合，就是用来封装菜品信息的，然后把这些 SetmealDish 添加进 setmeal_dish 表中。

```java
@Override
public void saveWithDish(SetmealDTO setmealDTO) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDTO, setmeal);
    // 向套餐表中提交数据
    setmealMapper.insert(setmeal);
    // 获取生成的套餐 id
    Long setmealId = setmeal.getId();
    // 获取要添加到套餐的菜品信息
    List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
    // 给这些要添加到套餐中的菜品绑定套餐 id
    setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
    // 保存套餐和菜品的关联关系
    setmealDishMapper.insertBatch(setmealDishes);
}
```

****
## 2. 套餐分页查询

可以根据需要，按照套餐名称、分类、售卖状态进行查询。

```java
@GetMapping("/page")
@Operation(summary = "套餐分页查询")
public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
    PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
    return Result.success(pageResult);
}

@Override
public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
  PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
  Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
  return new PageResult(page.getTotal(), page.getResult());
}
```

```sql
<select id="pageQuery" resultType="com.sky.vo.SetmealVO">
    select s.*,c.name categoryName from setmeal s
    left join category c on s.category_id = c.id
    <where>
        <if test="name != null">
            and s.name like concat('%',#{name},'%')
        </if>
        <if test="status != null">
            and s.status = #{status}
        </if>
        <if test="categoryId != null">
            and s.category_id = #{categoryId}
        </if>
    </where>
    order by s.create_time desc
</select>
```

****
## 3. 删除套餐

可以一次删除一个套餐，也可以批量删除套餐，但起售中的套餐不能删除。和删除菜品类似，都是利用批量删除达到两种效果。

```java
@DeleteMapping
@Operation(summary = "批量删除套餐")
public Result delete(@RequestParam List<Long> ids){
    setmealService.deleteBatch(ids);
    return Result.success();
}

@Override
@Transactional
public void deleteBatch(List<Long> ids) {
  ids.forEach(id -> {
    Setmeal setmeal = setmealMapper.getById(id);
    if (setmeal.getStatus() == StatusConstant.ENABLE) {
      // 起售中的套餐不能删除
      throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
    }
  });
  // 删除套餐表中的数据
  setmealMapper.deleteByIds(ids);
  // 删除 setmeal_dish 表中的数据
  setmealDishMapper.deleteByIds(ids);
}
```

****
## 4. 修改套餐

点击 "修改" 按钮后需要显示该套餐的信息，所以会用到根据 id 查询套餐信息和关联的菜品信息。

Controller 层：

```java
@GetMapping("/{id}")
@Operation(summary = "根据套餐 id 查询套餐")
public Result<SetmealVO> getById(@PathVariable Long id) {
    SetmealVO setmealVO = setmealService.getById(id);
    return Result.success(setmealVO);
}

@PutMapping
@Operation(summary = "修改套餐")
public Result update(@RequestBody SetmealDTO setmealDTO) {
    setmealService.update(setmealDTO);
    return Result.success();
}
```

Service 层：

这里页和修改菜品类似，都是先删除较为难修改的数据，然后再统一新增

```java
@Override
public SetmealVO getById(Long id) {
    Setmeal setmeal = setmealMapper.getById(id);
    List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
    SetmealVO setmealVO = new SetmealVO();
    BeanUtils.copyProperties(setmeal, setmealVO);
    setmealVO.setSetmealDishes(setmealDishes);
    return setmealVO;
}

@Override
public void update(SetmealDTO setmealDTO) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDTO, setmeal);
    // 修改 setmeal 表
    setmealMapper.update(setmeal);
    // 获取套餐 id
    Long setmealId = setmealDTO.getId();
    // 删除该套餐中的 setmeal_dish 表中的内容
    setmealDishMapper.deleteByIds(Collections.singletonList(setmealId));
    // 获取要添加到套餐的菜品信息
    List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
    // 给这些要添加到套餐中的菜品绑定套餐 id
    setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
    // 新增对应的菜品到 setmeal_dish
    setmealDishMapper.insertBatch(setmealDishes);
}
```

****
## 5. 起售、停售套餐

如果套餐内包含停售的菜品，则不能起售。

Controller 层：

```java
@PostMapping("/status/{status}")
@Operation(summary = "套餐起售、停售")
public Result startOrStop(@PathVariable Integer status, Long id){
    log.info("起售、停售套餐：{},{}", status, id);
    setmealService.startOrStop(status, id);
    return Result.success();
}
```

Service 层：

通过套餐 id 查询 setmeal_dish 表中的数据，获取关联的所有菜品的 id，然后依次遍历获取它们对应的 status，经过判断后决定是否可以起售（新增套餐默认停售状态）

```java
@Override
public void startOrStop(Integer status, Long id) {
    // 根据套餐 id 查询当前套餐包含的菜品中的 status 是否有为 0 的
    List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
    setmealDishes.forEach(setmealDish -> {
        if (Objects.equals(dishMapper.getById(setmealDish.getDishId()).getStatus(), StatusConstant.DISABLE)) {
            throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        }
    });
    // 菜品的 status 全为 1，则正常起售
    Setmeal setmeal = Setmeal.builder()
            .status(status)
            .id(id)
            .build();
    setmealMapper.update(setmeal);
}
```

****
# 十一、店铺营业状态设置

## 1. 引入 redis

配置 yaml 文件：

```yaml
spring:
  data:
    redis:
      host: ${sky.redis.host}
      port: ${sky.redis.port}
      password: ${sky.redis.password}
      database: ${sky.redis.database}
```

```yaml
sky:
  redis:
    host: localhost
    port: 6379
    password: 123
    database: 10
```

编写配置类，创建 RedisTemplate 对象:

```java
@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置 redis 的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置 redis key 的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
```

当前配置类不是必须的，因为 SpringBoot 框架会自动装配 RedisTemplate 对象，但是默认的 key 序列化器为 JdkSerializationRedisSerializer，
会导致存到 Redis 中后的数据和原始数据有差别，所以设置为 StringRedisSerializer 序列化器。如果只是操纵字符串类型的，也可以直接使用 StringRedisTemplate。

****
## 2. 设置店铺状态

Controller 层：

直接将店铺状态值 status 存入 redis 中

```java
@PutMapping("/{status}")
@Operation(summary = "设置店铺的营业状态")
public Result setStatus(@PathVariable Integer status){
    log.info("设置店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
    stringRedisTemplate.opsForValue().set(KEY, String.valueOf(status));
    return Result.success();
}

@GetMapping("/status")
@Operation(summary = "获取店铺的营业状态")
public Result<Integer> getStatus(){
    int status = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(KEY)));
    log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
    return Result.success(status);
}
```

通过接口文档测试后，在 redis 中查询 key

```redis
127.0.0.1:6379[10]> get SHOP_STATUS
"1"
```

****
# 十二、微信登录、商品浏览

## 1. HttpClient

Apache HttpClient 是 Apache 提供的一个强大、灵活、功能齐全的 HTTP 客户端工具库，常用于 Java 后端系统中进行 HTTP 通信，支持连接池、代理、认证、会话保持等高级功能。
用来发送 HTTP 请求并接收响应数据，以及管理连接等。当我们在使用扫描支付、查看地图、获取验证码、查看天气等功能时，
应用程序本身并未实现这些功能，都是在应用程序里访问提供这些功能的服务，而访问这些服务需要发送 HTTP 请求，并且接收服务端返回的响应数据，
这些可通过 HttpClient 来实现。

HttpClient 的核心组件：

- HttpClient：Http 客户端对象类型，使用该类型对象可发起 Http 请求。
- HttpClients：可认为是构建器，可创建 HttpClient 对象。
- CloseableHttpClient：实现类，实现了 HttpClient 接口。
- HttpGet：Get 方式请求类型。
- HttpPost：Post 方式请求类型。

HttpClient 发送请求步骤：

1. 创建 HttpClient 对象
2. 创建 Http 请求对象
3. 调用 HttpClient 的 execute 方法发送请求

GET 方式请求：

实现步骤：

1. 创建 HttpClient 对象
2. 创建请求对象
3. 发送请求，接受响应结果
4. 解析结果
5. 关闭资源

```java
@Test
public void testGET() throws Exception{
    // 创建 httpclient 对象
    CloseableHttpClient httpClient = HttpClients.createDefault();
    // 创建请求对象
    HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
    // 发送请求，接受响应结果
    CloseableHttpResponse response = httpClient.execute(httpGet);
    // 获取服务端返回的状态码
    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println("服务端返回的状态码为：" + statusCode);

    HttpEntity entity = response.getEntity();
    String body = EntityUtils.toString(entity);
    System.out.println("服务端返回的数据为：" + body);
    // 关闭资源
    response.close();
    httpClient.close();
}
```

输出结果：

```text
服务端返回的状态码为：200
服务端返回的数据为：{"code":1,"msg":null,"data":1}
```

POST 方式请求：

实现步骤：

1. 创建 HttpClient 对象
2. 创建请求对象
3. 发送请求，接收响应结果
4. 解析响应结果
5. 关闭资源

POST 请求整体与 GET 请求类似，只是多了个请求参数

```java
@Test
public void testPOST() throws Exception{
    // 创建 httpclient 对象
    CloseableHttpClient httpClient = HttpClients.createDefault();
    // 创建请求对象
    HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("username","admin");
    jsonObject.put("password","123456");
    StringEntity entity = new StringEntity(jsonObject.toString());
    // 指定请求编码方式
    entity.setContentEncoding("utf-8");
    // 数据格式
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    // 发送请求
    CloseableHttpResponse response = httpClient.execute(httpPost);
    // 解析返回结果
    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println("响应码为：" + statusCode);
    HttpEntity entity1 = response.getEntity();
    String body = EntityUtils.toString(entity1);
    System.out.println("响应数据为：" + body);
    // 关闭资源
    response.close();
    httpClient.close();
}
```

输出结果：

```text
响应码为：200
响应数据为：{"code":1,"msg":null,"data":{"id":1,"userName":"admin","name":"管理员","token":"eyJhbGciOiJIUzI1NiJ9.eyJlbXBJZCI6MSwiZXhwIjoxNzUyNDgzNTM4fQ.0he-qhWEXzDdbmcfJSMGBF4ySgg8OoRy3KFnJyn6i_w"}}
```

****
## 2. 微信小程序开发

### 2.1 小程序目录结构

小程序包含一个描述整体程序的 app 和多个描述各自页面的 page。一个小程序主体部分由三个文件组成，必须放在项目的根目录：

- app.js：必须存在，主要存放小程序的逻辑代码
- app.json：必须存在，小程序配置文件，主要存放小程序的公共配置
- app.wxss：非必须存在，主要存放小程序公共样式表，类似于前端的 CSS 样式

每个小程序页面主要由四个文件组成：

- index.js：必须存在，存放页面业务逻辑代码，编写的 js 代码。
- index.json：非必须，存放页面相关的配置。
- index.wxml：必须存在，存放页面结构，主要是做页面布局，页面效果展示的，类似于 HTML 页面。
- index.wxss：非必须，存放页面样式表，相当于 CSS 文件。

****
### 2.2 编写和编译小程序

进入到 index.wxml，编写页面布局：

```js
<!--index.wxml-->
<navigation-bar title="Weixin" back="{{false}}" color="black" background="#FFF"></navigation-bar>
<scroll-view class="scrollarea" scroll-y type="list">
  <view>
    <view class="container">{{msg}}</view>
    <view>
      <button bindtap="getUserInfo" type="primary">获取用户信息</button>
      <image style="width:100px;height:100px;" src="{{url}}"></image>
      {{nickName}}
    </view>
    <view>
      <button bindtap="wxLogin" type="warn">微信登陆</button>
      授权码：{{code}}
    </view>
    <view>
      <button bindtap="sendRequest" type="warn">发送请求</button>
      响应结果:{{result}}
     </view>
  </view>
</scroll-view>
```

进入到 index.js，编写业务逻辑代码:

```js
// index.js
Page({
  data:{
    msg:'hello world!',
    nickName:'',
    url:'',
    code:'',
    result:''
  },
  // 获取微信用户的头像和昵称
  getUserInfo() {
    // 小程序内置方法，获取当前用户信息
    wx.getUserProfile({
      desc: '获取用户信息',
      success:(res) => {
        // res 就是返回的获取到的当前用户的信息
        console.log(res.userInfo)
        // 为数据赋值
        this.setData({
          nickName: res.userInfo.nickName,
          url: res.userInfo.avatarUrl
        })
      }
    })
  },

  // 微信登录，获取微信的授权码
  wxLogin(){
    wx.login({
        success:(res)=>{
            console.log("授权码："+res.code)
            this.setData({
              code:res.code
            })
        }
    })
  },

  // 发送请求
  sendRequest(){
    wx.request({
      url:'http://localhost:8080/user/shop/status',
      method:'GET',
      success:(res)=>{
          console.log("响应结果："+res.data.data)
        this.setData({
          result:res.data.data
        })
      }
    })
  }
})
```

测试时需要设置不校验合法域名，若不勾选，请求发送会失败。

****
## 3. 微信登录

微信登录流程：

1. 小程序端，调用 wx.login() 获取 code，就是授权码。
2. 小程序端，调用 wx.request() 发送请求并携带 code，请求开发者服务器（自己编写的后端服务）。
3. 开发者服务端，通过 HttpClient 向微信接口服务发送请求，并携带 appId+appsecret+code 三个参数。
4. 开发者服务端，接收微信接口服务返回的数据，session_key+openid 等，openid 是微信用户的唯一标识。
5. 开发者服务端，自定义登录态，生成令牌（token）和 openid 等数据返回给小程序端，方便后绪请求身份校验。
6. 小程序端，收到自定义登录态，存储 storage。
7. 小程序端，后绪通过 wx.request() 发起业务请求时，携带 token。
8. 开发者服务端，收到请求后，通过携带的 token，解析当前登录用户的 id。
9. 开发者服务端，身份校验通过后，继续相关的业务逻辑处理，最终返回业务数据。

- wx.login(Object object)

调用接口获取登录凭证（code）。通过凭证进而换取用户登录态信息，包括用户在当前小程序的唯一标识（openid）、微信开放平台账号下的唯一标识（unionid，
若当前小程序已绑定到微信开放平台账号）及本次登录的会话密钥（session_key）等。用户数据的加解密通讯需要依赖会话密钥完成。

```js
wx.login({
  success (res) {
    if (res.code) {
      // 发起网络请求
      wx.request({
        url: 'https://example.com/onLogin',
        data: {
          code: res.code
        }
      })
    } else {
      console.log('登录失败！' + res.errMsg)
    }
  }
})
```

- 调用 auth.code2Session 接口，换取 用户唯一标识 OpenID 、 用户在微信开放平台账号下的唯一标识UnionID（若当前小程序已绑定到微信开放平台账号） 和 会话密钥 session_key。

登录凭证校验，通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。调用方式：

```http request
GET https://api.weixin.qq.com/sns/jscode2session 
```

请求参数：

| 属性       | 类型   | 必填 | 说明                             |
|------------|--------|------|--------------------------------|
| appid      | string | 是   | 小程序 appId                      |
| secret     | string | 是   | 小程序 appSecret                  |
| js_code    | string | 是   | 登录时获取的 code，可通过 wx.login 获取    |
| grant_type | string | 是   | 授权类型，此处只需填写 authorization_code |

返回参数：

| 属性        | 类型   | 说明                                                         |
|-------------|--------|--------------------------------------------------------------|
| session_key | string | 会话密钥                                                     |
| unionid     | string | 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回 |
| errmsg      | string | 错误信息，请求失败时返回                                     |
| openid      | string | 用户唯一标识                                                 |
| errcode     | int32  | 错误码，请求失败时返回                                       |

通过模拟发送请求：

```http request
https://api.weixin.qq.com/sns/jscode2session?appid=...&js_code=0c3450100mqXBU1RaH000kD0HR34501o&grant_type=authorization_code
```

等到返回结果：

```text
{
    "session_key": "eDQp/3XSaPb+XT1CJuHFHQ==",
    "openid": "oEi1JvnOyN6rLbR3Q1ligCJd5nPc"
}
```

因为一个授权码（code）只能使用一次，所以再次发送相同的请求就会报错

```text
{
    "errcode": 40163,
    "errmsg": "code been used, rid: 6874c752-0dcac9e8-687e7832"
}
```

****
## 4. 需求分析

用户进入到小程序的时候，微信授权登录之后才能点餐，并且需要获取当前微信用户的相关信息，比如昵称、头像等，这样才能够进入到小程序进行下单操作。
由于是基于微信登录来实现小程序的登录功能，没有采用传统账户密码登录的方式，所以如果是第一次使用小程序来点餐，那就是一个新用户，
需要把这个新的用户保存到数据库当中完成自动注册，而该用户不能直接使用 employee 表，因为从小程序登录的话会携带 openid，并且需要存放头像，
而 employee 表中没有这些字段，所以使用新的 user 表来存储微信小程序登录的新用户。

通过微信登录的流程，如果要完成微信登录的话，最终就要获得微信用户的 openid。在小程序端获取授权码后，向后端服务发送请求，并携带授权码，
这样后端服务在收到授权码后，就可以去请求微信接口服务。最后后端向小程序返回 openid 和 token 等数据。

| **字段名**  | **数据类型** | **说明**           | **备注** |
| ----------- | ------------ | ------------------ | -------- |
| id          | bigint       | 主键               | 自增     |
| openid      | varchar(45)  | 微信用户的唯一标识 |          |
| name        | varchar(32)  | 用户姓名           |          |
| phone       | varchar(11)  | 手机号             |          |
| sex         | varchar(2)   | 性别               |          |
| id_number   | varchar(18)  | 身份证号           |          |
| avatar      | varchar(500) | 微信用户头像路径   |          |
| create_time | datetime     | 注册时间           |          |

手机号字段比较特殊，个人身份注册的小程序没有权限获取到微信用户的手机号，但如果是以企业的资质注册的小程序就能够拿到微信用户的手机号。

相关配置：

application-dev.yml：

```yaml
sky:
  wechat:
    appid: wxb401fa14d7567036
    secret: 10fd5727e6f4c260e9765c6e346d0aeb
```

application.yml：

```yaml
sky:
  wechat:
    appid: ${sky.wechat.appid}
    secret: ${sky.wechat.secret}
```

application.yml：

因为该系统现在会有两种用户同时登录，一个管理员，一个微信小程序使用者，所以要设置一个新的 token，通过 token 来区分用户类型

```yaml
sky:
  jwt:
    # 设置jwt签名加密时使用的秘钥
    admin-secret-key: "RUwYpDnq3GZ8qQsQyDa4bNS0HHV1o7bRk+rCIMeV60U="
    # 设置jwt过期时间
    admin-ttl: 7200000
    # 设置前端传递过来的令牌名称
    admin-token-name: token
    user-secret-key: "5fJYB8XnCqD3rQ7Zy+z9vlphxB9WpzOhSBt1UdVTFos="
    user-ttl: 7200000
    user-token-name: authentication
```

****
## 5. 微信登录实现

### 5.1 分析

在接口文档中 C 端发送的登录请求为 POST 请求，请求参数为 json 格式的 code（微信授权码），而返回的内容则包括用户 id、用户的 openid 以及登陆后生成的 jwt 令牌。
所以需要封装一个 [UserLoginDTO](./sky-pojo/src/main/java/com/sky/dto/UserLoginDTO.java) 接收 code，封装一个 [UserLoginVO](./sky-pojo/src/main/java/com/sky/vo/UserLoginVO.java) 返回 id、openid、token。

因为现在的系统有两种使用者，所以需要区分这两个用户，那么就需要对它们的 jwt 令牌进行区分，新增一个属于 user 的 [jwt](./sky-server/src/main/java/com/sky/interceptor/JwtTokenUserInterceptor.java) 拦截器，
并在 WebMvcConfiguration 配置类中注册该拦截器：

```java
registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/login")
                .excludePathPatterns("/user/shop/status"); // 因为微信用户登陆时就应该可以看到店铺的状态，所以该请求路劲不进行拦截
```

****
### 5.2 功能实现

Controller 层：

因为微信小程序登录时生成的 code 是一次性的，发送一次请求就失效，所以后续发送的请求不能依靠 code 来进行判断，所以得靠服务端给小程序用户生成的 token 来对登录者进行判断，
后续该用户发送的每个请求都会携带该 token 并被拦截验证是否为服务端生成的即可。

```java
@PostMapping("/login")
@Operation(summary = "微信登录")
public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
    log.info("微信用户登录：{}", userLoginDTO.getCode());
    // 微信登录
    User user = userService.wxLogin(userLoginDTO);
    // 为微信用户生成 jwt 令牌
    Map<String, Object> claims = new HashMap<>();
    claims.put(JwtClaimsConstant.USER_ID, user.getId());
    String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
    // 封装 UserLoginVO 对象返回给小程序
    UserLoginVO userLoginVO = UserLoginVO.builder()
            .id(user.getId())
            .openid(user.getOpenid())
            .token(token)
            .build();
    return Result.success(userLoginVO);
}
```

Service 层：

小程序的用户登录后，需要对专门的微信服务接口地址发送请求，该请求包含四个参数（上面有记录），如果 code 有效，则会返回 session_key 和 openid，
目前只需要用到 openid。因为 openid 为小程序用户的唯一标识，所以可以通过它去查找 user 表判断是否为已注册的用户，如果 user 表中没有，代表是第一次登陆，
则进行注册操作，把 openid 存进去，并返回 User 对象，在小程序界面显示该用户的相关信息。

```java
// 微信服务接口地址
public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

@Override
public User wxLogin(UserLoginDTO userLoginDTO) {
    String openid = getOpenid(userLoginDTO.getCode());
    if(openid == null){
        throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
    }
    // 判断当前用户是否为新用户
    User user = userMapper.getByOpenid(openid);
    // 如果是新用户，则完成注册
    if(user == null){
        user = User.builder()
                .openid(openid)
                .createTime(LocalDateTime.now())
                .build();
        userMapper.insert(user);
    }
    return user;
}

// 调用微信接口服务，获取微信用户的 openid
private String getOpenid(String code) {
    Map<String, String> map = new HashMap<>();
    map.put("appid", weChatProperties.getAppid());
    map.put("secret", weChatProperties.getSecret());
    map.put("js_code", code);
    map.put("grant_type", "authorization_code");
    // 使用封装的 HttpClient 工具类发送 Http 请求
    String json = HttpClientUtil.doGet(WX_LOGIN, map);
    JSONObject jsonObject = JSON.parseObject(json);
    String openid = jsonObject.getString("openid");
    return openid;
}
```

测试：

在小程序端点击登录，查看控制台与数据库中是否有添加记录：

```text
// 获取到了 openid
com.sky.mapper.UserMapper.getByOpenid    : ==> Parameters: oEi1JvnOyN6rLbR3Q1ligCJd5nPc(String)
```

****
## 6. 商品浏览

### 6.1 查询分类

Controller 层：

该功能是在小程序的左侧显示菜品分类和套餐分类，这里直接调用的是之前写好的 categoryService.list(type) 方法，

```java
@GetMapping("/list")
@Operation(summary = "查询分类")
public Result<List<Category>> list(Integer type) {
    List<Category> list = categoryService.list(type);
    return Result.success(list);
}
```

****
### 6.2 查询菜品分类中的菜品

Controller 层：

```java
@GetMapping("/list")
@Operation(summary = "根据分类id查询菜品")
public Result<List<DishVO>> list(Long categoryId) {
    List<DishVO> list = dishService.listWithFlavor(categoryId);
    return Result.success(list);
}
```

Service 层：

因为查询菜品前，需要先点击某个菜品分类，此时会携带 categoryId，通过该 id 查询对应分类的菜品，但是只能查询到起售状态的菜品，所以查询条件有两个，
因为之前有写查询分类菜品的 mapper，所以直接封装一个带有这两个查询条件的 Dish 对象即可。然后从查询出的 Dish 集合中依次遍历它们，获取对应的口味，
然后封装进 DishVO，返回 DishVO 类型的集合。

```java
@Override
public List<DishVO> listWithFlavor(Long categoryId) {
    Dish dish = Dish.builder()
            .status(StatusConstant.ENABLE)
            .categoryId(categoryId)
            .build();
    List<Dish> dishList = dishMapper.getListByCategoryId(dish);
    List<DishVO> dishVOList = new ArrayList<>();
    for (Dish d : dishList) {
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(d, dishVO);
        // 根据菜品 id 查询对应的口味
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());
        dishVO.setFlavors(flavors);
        dishVOList.add(dishVO);
    }
    return dishVOList;
}
```

****
### 6.3 查询套餐分类中的套餐

因为套餐有多个分类，每个分类中又有多个套餐，所以要先根据分类 id 查询对应的套餐有哪些。

Controller 层：

```java
@GetMapping("/list")
@Operation(summary = "根据分类id查询套餐")
public Result<List<Setmeal>> list(Long categoryId) {
    List<Setmeal> list = setmealService.list(categoryId);
    return Result.success(list);
}
```

Service 层：

该方法与上面的类似，因为查询条件都是两个，一个 categoryId，一个为起售状态，所以直接封装成 Setmeal 对象作为参数进行查询

```java
@Override
public List<Setmeal> list(Long categoryId) {
    Setmeal setmeal = Setmeal.builder()
            .status(StatusConstant.ENABLE)
            .categoryId(categoryId)
            .build();
    List<Setmeal> setmealList = setmealMapper.list(setmeal);
    return setmealList;
}
```

****
### 6.4 查询套餐中的菜品信息

该功能只是简单的展示一些菜品的相关信息到页面，它只展示了 name、copies、image、description 四个属性，所以封装成一个简单的 DishItemVO 对象返回给小程序。

Controller 层：

```java
@GetMapping("/dish/{id}")
@Operation(summary = "根据套餐 id 查询包含的菜品列表")
public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
    List<DishItemVO> list = setmealService.getDishItemById(id);
    return Result.success(list);
}
```

Service 层：

```java
@Override
public List<DishItemVO> getDishItemById(Long id) {
    return setmealMapper.getDishItemBySetmealId(id);
}
```

Mapper 层：

```sql
<select id="getDishItemBySetmealId" resultType="com.sky.vo.DishItemVO" parameterType="com.sky.vo.DishItemVO">
    select sd.name, sd.copies, d.image, d.description from setmeal_dish sd
    left join dish d on sd.dish_id = d.id
    where sd.setmeal_id = #{setmealId}
</select>
```

****
# 十三、缓存商品、购物车

## 1. 缓存菜品

### 1.1 查询菜品时添加缓存
```java
// 构造 redis 中的 key，dish:category:categoryId
String key = "dish:category:" + categoryId;
// 查询 redis 是否有缓存数据，如果存在则返回 redis 中的数据
String dishVOListJson  = stringRedisTemplate.opsForValue().get(key);
if (dishVOListJson != null && !dishVOListJson.isEmpty()) {
    // 反序列化为 List<DishVO>
    List<DishVO> dishVOList = JSON.parseArray(dishVOListJson, DishVO.class);
    return dishVOList;
}
......
// 不存在则查询数据库并保存在 redis 中
stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(dishVOList));
return dishVOList;
```

****
### 1.2 清除缓存

因为新增、修改、删除操作会导致数据发生改变，所以需要同步删除 redis 的缓存，确保下次查询 redis 时数据的一致性。

```java
private void cleanCache(String pattern) {
    Set<String> keys = stringRedisTemplate.keys(pattern);
    stringRedisTemplate.delete(keys);
}
```

因为这些操作是管理端才能进行的，所以操作的频率不是很高，就可以直接采取删除全部缓存，而不是根据 id 寻找对应的 key 再删除，当然也可以这么做

```java
// 删除对应分类的 redis 缓存
Long categoryId = dishMapper.getCategoryId(id);
String key = "dish:category:" + categoryId;
cleanCache(key);
```

****
## 2. 缓存套餐

### 2.1 Spring Cache

Spring Cache 是一个框架，实现了基于注解的缓存功能，只需要简单地加一个注解，就能实现缓存功能。底层可以切换不同的缓存实现 EHCache、Caffeine、Redis 等。
常用注解如下：

- @EnableCaching：开启缓存功能，通常加在配置类上

```java
@Configuration
@EnableCaching
public class CacheConfig {
}
```

- @Cacheable：在方法执行前先查询缓存中是否有数据，如果有数据，则直接返回缓存数据；如果没有缓存数据，调用方法并将方法返回值放到缓存中

```java
@Cacheable(value = "userCache", key = "#id") // key = userCache::id
public User getUserById(Long id) {
    return userRepository.findById(id);
}
```

- value：缓存的名字（对应一个缓存区域） 
- key：缓存的键（SpEL 表达式） 
- condition：是否缓存的判断条件 
- unless：返回结果后判断是否不缓存

```java
// 结果不为空时缓存
@Cacheable(value = "userCache", key = "#id", condition = "#id > 100", unless = "#result == null")
```

- @CachePut：每次方法调用都会执行，并将返回值写入缓存。常用于更新数据库和缓存保持一致的场景。

```java
@CachePut(value = "userCache", key = "#user.id")
public User updateUser(User user) {
    return userRepository.save(user);
}
```

- @CacheEvict：清除缓存

```java
@CacheEvict(value = "userCache", key = "#id")
public void deleteUser(Long id) {
    userRepository.deleteById(id);
}
```

- allEntries = true：清空整个缓存区域 
- beforeInvocation = true：方法调用前执行清除（默认是调用后）

```java
@CacheEvict(value = "userCache", allEntries = true)
public void clearAll() {
}
```

常用 Spring Cache key 表达式:

| 表达式                            | 含义说明                                                      |
| ------------------------------ | --------------------------------------------------------- |
| `#user.id`                     | 方法中名为 `user` 的参数的 `id` 属性                                 |
| `#result.id`                   | 方法返回值的 `id` 属性（仅 `@CachePut` 和 `@Cacheable(unless=)` 中可用） |
| `#p0.id`                       | 方法第 0 个参数（即第一个参数）的 `id` 属性                                |
| `#a0.id`                       | 与 `#p0` 等价，方法第 0 个参数的 `id` 属性                             |
| `#root.args[0].id`             | 通过 `#root` 对象访问第一个参数的 `id` 属性                             |
| `#root.methodName`             | 当前方法名称（如 `updateUser`）                                    |
| `#root.targetClass.simpleName` | 当前类名简写，用于生成命名空间式 key                                      |


Spring 在启动时会扫描 @EnableCaching 并注册 CacheInterceptor 拦截器，被 @Cacheable、@CachePut、@CacheEvict 标注的方法就会被 AOP 代理，
当调用这些方法时，会被 CacheInterceptor 拦截，进行如下逻辑：

```text
@Cacheable：
    先判断 key 是否在缓存中 -> 如果有命中，返回缓存结果
    如果没有命中，执行方法体 -> 将返回值放入缓存

@CachePut：
    执行方法体 -> 将返回值写入缓存（覆盖原值）

@CacheEvict：
    判断是否执行清除缓存逻辑 -> 删除对应 key 的缓存
```

使用步骤：

1、配置 yaml 文件

```yaml
spring:
  cache:
    type: redis
  data:
    redis:
      host: ${sky.redis.host}
      port: ${sky.redis.port}
      password: ${sky.redis.password}
      database: ${sky.redis.database}
```

2、自定义 Redis 缓存配置

在 Spring Boot 整合 Redis 缓存时，无需手动处理反序列化，通过配置 RedisCacheConfiguration 中的序列化器，
Spring 会在缓存读写过程中自动完成数据的序列化（对象 -> Redis 存储格式）和反序列化（Redis 存储格式 -> 对象）

```java
@Configuration
@EnableCaching
public class RedisCacheConfig {
    // 因为 Jackson 在序列化或反序列化 LocalDateTime 时无法识别 java.time 类型，所以需要使用之前自定义的序列化器
    JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(jacksonObjectMapper);

    // 对 Redis 缓存的各项属性进行配置
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
              .entryTtl(Duration.ofMinutes(10)) // 设置默认过期时间
              .disableCachingNullValues() // 不缓存 null 值
              // 把缓存键序列化为字符串格式
              .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
              // 采用 Jackson JSON 格式对缓存值进行序列化
              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
  }
}
```

3、注意：

无论是否配置了自定义缓存配置文件，都需要在启动类上添加 @EnableCaching 注解。

缓存嵌套调用问题：

```java
public class UserService {
    @Cacheable("user")
    public User getUser(Long id) {
        return this.loadFromDb(id);
    }
    public User test(Long id) {
        return this.getUser(id); // 缓存不生效
    }
}
```

因为 this.getUser() 是对象内部调用，没有通过代理，而 Spring AOP 的缓存逻辑（如 @Cacheable）只在通过代理对象调用时生效，
内部调用 this.方法() 会直接执行目标方法，绕过代理逻辑，因此缓存注解失效。应该通过 Spring 的 @Autowired 注入代理对象，此时缓存才会生效。

```java
@Autowired
private UserService self;

public User test(Long id) {
    return self.getUser(id); // 使用代理对象调用，缓存才会生效
}
```

缓存更新问题：

因为 @Cacheable 遵循缓存优先，避免重复执行方法，所以可能不一定执行方法体，而更新操作是为了修改数据库中的数据，所以必须执行方法体，否则数据不会更新到数据库。

```java
// 错误用法
@Cacheable(value = "userCache", key = "#user.id")
public User update(User user) {
    return userRepository.save(user); // 缓存未更新
}

// 正确用法
@CachePut(value = "userCache", key = "#user.id")
public User update(User user) {
  return userRepository.save(user);
}
```

****
### 2.2 代码实现

在 SetmealServiceImpl 中添加 @Cacheable、@CachePut、@CacheEvict。

```java
@CacheEvict(cacheNames = "setmealCache", allEntries = true)
public void deleteBatch(List<Long> ids) {
}

@CacheEvict(cacheNames = {"dishItemCache", "setmealCache"}, allEntries = true)
public void update(SetmealDTO setmealDTO) {
}

@Cacheable(value = "setmealCache", key = "#categoryId")
public List<Setmeal> list(Long categoryId) {
}

@Cacheable(value = "dishItemCache", key = "#id")
public List<DishItemVO> getDishItemById(Long id) {
}
```

需要注意的是，在 update 方法上不能使用 @CachePut，因为 @CachePut 是缓存方法的返回值，如果是 void，没法缓存，导致发生异常。

****
## 3. 添加购物车

用户可以将菜品或者套餐添加到购物车。对于菜品来说，如果设置了口味信息，则需要选择规格后才能加入购物车；对于套餐来说，可以直接点击将当前套餐加入购物车。
在购物车中可以修改菜品和套餐的数量，也可以清空购物车。添加购物车时，有可能添加菜品，也有可能添加套餐，故传入参数要么是菜品 id，要么是套餐 id。
前端传入的数据包含 dishId、setmealId、dishFlavor，所以需要封装一个 [ShoppingCartDTO](./sky-pojo/src/main/java/com/sky/dto/ShoppingCartDTO.java) 来接首。

Controller 层：

```java
@PostMapping("/add")
@Operation(summary = "添加购物车")
public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
    log.info("添加购物车：{}", shoppingCartDTO);
    shoppingCartService.addShoppingCart(shoppingCartDTO);
    return Result.success();
}
```

Service 层：

因为可能对同一个东西进行多次添加，所以每次添加操作前都需要对添加的这个东西进行判断，如果是一样的，那么直接让它数量加一，而前端传来的数据只有 setmealId、dishId、dishFlavor，
而该操作应该是针对当前登录用户，所以要从 ThreadLocal 中获取当用户的 id 作为条件一起查询，因为菜品和套餐 id 的唯一性，所以很容易区分，但相同的菜品可能有不同的口味，
它们不能作为相同的东西，所以口味也作为了判断条件，使用这四个条件查询数据库，如果数据存在，那么一定就只能查到一条数据，然后对该数据的 number 属性加一。
如果添加的是新菜品或套餐，那么就需要判断添加的是哪个，因为要根据它们的类型去查找对应的数据表，这时候就可以根据前端传来的数据判断，因为一次添加操作不可能又传菜品 id 又传套餐 id 的，
所以可以根据它们谁不为空判断添加的是谁。

```java
@Override
public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart = new ShoppingCart();
    BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
    // 只能查询当前登录用户自己的购物车数据
    shoppingCart.setUserId(BaseContext.getCurrentId());
    // 判断当前商品是否在购物车中
    List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
    if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
        // 因为查询条件为 userId、setmealId、dishId、dishFlavor，所以最多只能查询出一条数据
        // 如果已经存在，就更新数量，数量加 1
        shoppingCart = shoppingCartList.get(0);
        shoppingCart.setNumber(shoppingCart.getNumber() + 1);
        shoppingCartMapper.updateNumberById(shoppingCart);
    } else {
        // 如果不存在，插入数据，数量就是 1
        // 判断当前添加到购物车的是菜品还是套餐
        Long dishId = shoppingCartDTO.getDishId();
        if (dishId != null) {
            // 添加到购物车的是菜品
            Dish dish = dishMapper.getById(dishId);
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        } else {
            // 添加到购物车的是套餐
            Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setAmount(setmeal.getPrice());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }
}
```

****
## 4. 查看购物车

用户每次登录时都会发送一个查看购物车的请求，该功能较为简单，直接根据 userId 查询数据库即可。

Controller 层：

```java
@GetMapping("/list")
@Operation(summary = "查看购物车")
public Result<List<ShoppingCart>> list() {
    return Result.success(shoppingCartService.showShoppingCart());
}
```

Service 层：

因为写添加操作时，已经写了一个查询数据库的 sql，而传入的四个条件中整合包含 userId，所以只需要创建一个只包含 userId 的 ShoppingCart 对象即可。

```java
@Override
public List<ShoppingCart> showShoppingCart() {
  ShoppingCart shoppingCart = ShoppingCart.builder()
          .userId(BaseContext.getCurrentId())
          .build();
  return shoppingCartMapper.list(shoppingCart);
}
```

****
## 5. 清空购物车

清空操作只需要删除表中 userId 的所有数据即可。

```java
@DeleteMapping("/clean")
@Operation(summary = "清空购物车商品")
public Result<String> clean(){
    shoppingCartService.cleanShoppingCart();
    return Result.success();
}

@Override
public void cleanShoppingCart() {
  shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
}
```

****
## 6. 删除购物车中一个商品

该功能与添加功能相反，删除前先判断它的数量是否为 1，如果为 1 则直接删除该条记录，如果不为 1，则直接修改它的 number 值即可。

Controller 层：

```java
@PostMapping("/sub")
@Operation(summary = "删除购物车中一个商品")
public Result<String> sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
    shoppingCartService.deleteOne(shoppingCartDTO);
    return Result.success();
}
```

Service 层：

与添加购物车不同的是，这里不需要对是否存在进行判断，因为能进行删减操作证明一定存在，所以只需要查询出进行操作的是哪个即可，然后对它的数量进行判断，大于 1 则数量减一然后结束方法即可。

```java
@Override
@Transactional
public void deleteOne(ShoppingCartDTO shoppingCartDTO) {
  ShoppingCart shoppingCart = new ShoppingCart();
  BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
  List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
  shoppingCart = shoppingCartList.getFirst();
  if (shoppingCart.getNumber() > 1) {
    shoppingCart.setNumber(shoppingCart.getNumber() - 1);
    shoppingCartMapper.updateNumberById(shoppingCart);
    return;
  }
  shoppingCartMapper.delete(shoppingCart);
}
```

Mapper 层：

因为同一个菜品不同口味被视为不同的菜品，所以口味也要作为删除条件而存在。

```sql
<delete id="delete">
delete from shopping_cart
  <where>
     <if test="dishId != null">and dish_id = #{dishId}</if>
     <if test="setmealId != null">and setmeal_id = #{setmealId}</if>
     <if test="dishFlavor != null">and dish_flavor = #{dishFlavor}</if>
  </where>
</delete>
```

****
# 十四、用户下单、订单支付

## 1. 收货地址

直接通过当前登录用户的 id 查询数据库中的表即可。

```java
@GetMapping("/list")
@Operation(summary = "查询当前登录用户的所有地址信息")
public Result<List<AddressBook>> list() {
    AddressBook addressBook = new AddressBook();
    addressBook.setUserId(BaseContext.getCurrentId());
    List<AddressBook> list = addressBookService.list(addressBook);
    return Result.success(list);
}

@Override
public List<AddressBook> list(AddressBook addressBook) {
  return addressBookMapper.list(addressBook);
}
```

```sql
<select id="list" parameterType="com.sky.entity.AddressBook" resultType="com.sky.entity.AddressBook">
  select * from address_book
  <where>
      <if test="userId != null">
          and user_id = #{userId}
      </if>
      <if test="phone != null">
          and phone = #{phone}
      </if>
      <if test="isDefault != null">
          and is_default = #{isDefault}
      </if>
  </where>
</select>
```

还有修改地址时会发送一个根据当前地址 id 查询地址信息的请求，所以还得写一个根据 id 查询的方法，其次就是删除。而设置默认地址本质上就是一种更新操作，
所以在设置某个地址为默认地址时，可以先把所有的地址设置为非默认地址，然后再把当前选定的地址的该值进行修改即可。

```java
@Override
public void setDefault(AddressBook addressBook) {
    // 将但当前用户的所有地址修改为非默认地址
    addressBook.setIsDefault(0);
    addressBook.setUserId(BaseContext.getCurrentId());
    addressBookMapper.updateIsDefaultByUserId(addressBook);
    // 将选定的地址修改为默认地址
    addressBook.setIsDefault(1);
    addressBookMapper.update(addressBook);
}
```

****
## 2. 用户下单

在电商系统中，用户是通过下单的方式通知商家某个用户已经购买了商品，需要商家进行备货和发货。用户下单后会产生订单相关数据，订单数据需要能够体现如下信息：

- 商品名称
- 对应商品的数量
- 订单总金额
- 收货人
- 收货地址
- 用户手机号

所以需要把这些信息放到一个 order 表中，当然通常还包括其他的内容。所以除了这个还需要一个 order_detail 表，用来记录当前用户点的东西的详细信息，
因为这些信息通常是一个菜品作为一条数据，所以不会放进 order 表中，当成功创建订单并向 order 中插入数据时，
也要同时向 order_detail 插入关联的菜品信息（多个菜品就多条数据），确保后续用户可以查看到自己点的菜品。

在前端提交订单时，会携带下单地址、总金额、配送时间等信息，具体参考接口文档，所以需要封装一个 [OrdersSubmitDTO](./sky-pojo/src/main/java/com/sky/dto/OrdersSubmitDTO.java)，
而返回值通常只需要在前端显示生成的订单 id、金额、订单号以及下单时间，所以封装一个返回对象 [OrderSubmitVO](./sky-pojo/src/main/java/com/sky/vo/OrderSubmitVO.java)。

Controller 层：

```java
@PostMapping("/submit")
@Operation(summary = "用户下单")
public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
    log.info("用户下单：{}", ordersSubmitDTO);
    OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
    return Result.success(orderSubmitVO);
}
```

Service 层：

在提交订单前需要先进行一些判断，例如收货地址是否为空，购物车是否为空，如果这些数据都不存在的话，就不能提交订单。所以先通过前端传递的地址 id，查询数据库中是否有这条数据，
然后根据当前用户 id 查询购物车表，看里面是否有数据。如果都存在，即可正常提交订单，然后封装订单信息并插入数据库中。而对于订单详情信息来说，需要详细的购物车信息才能进行插入操作，
而再插入前已经执行过一次查询操作，所以可以直接把查到的购物车中的数据封装成集合（一个菜品即一条数据），动态插入 order_detail，然后清空购物车，返回数据。

```java
@Override
public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
    // 异常情况的处理（收货地址为空、购物车为空）
    AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
    if (addressBook == null) {
        throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
    }
    Long userId = BaseContext.getCurrentId();
    ShoppingCart shoppingCart = new ShoppingCart();
    shoppingCart.setUserId(userId);
    // 查询当前用户的购物车数据
    List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
    if (shoppingCartList == null || shoppingCartList.isEmpty()) {
        throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
    }
    // 构造订单数据
    Orders order = new Orders();
    BeanUtils.copyProperties(ordersSubmitDTO, order);
    order.setPhone(addressBook.getPhone());
    order.setAddress(addressBook.getDetail());
    order.setConsignee(addressBook.getConsignee());
    order.setNumber(String.valueOf(System.currentTimeMillis()));
    order.setUserId(userId);
    order.setStatus(Orders.PENDING_PAYMENT);
    order.setPayStatus(Orders.UN_PAID);
    order.setOrderTime(LocalDateTime.now());
    // 向订单表插入一条数据
    orderMapper.insert(order);
    // 订单明细数据
    List<OrderDetail> orderDetailList = new ArrayList<>();
    for (ShoppingCart cart : shoppingCartList) {
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(cart, orderDetail);
        orderDetail.setOrderId(order.getId());
        orderDetailList.add(orderDetail);
    }
    // 向订单明细表插入N条数据
    orderDetailMapper.insertBatch(orderDetailList);
    // 清空购物车数据
    shoppingCartMapper.deleteByUserId(userId);
    OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
            .id(order.getId())
            .orderNumber(order.getNumber())
            .orderAmount(order.getAmount())
            .orderTime(order.getOrderTime())
            .build();
    return orderSubmitVO;
}
```

****
## 3. 订单支付

### 3.1 微信支付

1、用户下单初始化

1. 微信用户进入微信小程序下单，开启支付流程，这是交互的起点。 
2. 小程序将下单请求传给 “商户系统”，触发订单创建逻辑。

2、订单与支付参数准备

3. 商户系统生成订单，返回订单号等数据给小程序，用于后续关联支付信息。
4. 小程序向商户系统申请微信支付 -> 商户调用微信后台（支付接口）-> 微信返回支付交易标识 -> 商户对支付参数（含交易标识等）加密 “再次签名”，确保参数安全、防篡改。

3、支付确认与执行

5. 商户系统把 “签名后的支付参数” 返回小程序 -> 小程序调起支付页，用户确认支付，完成支付授权。
6. 小程序向微信后台发起 “确认支付” -> 微信处理后返回支付结果，告知是否成功。

4、结果同步与收尾

7. 小程序接收支付结果，显示给用户（成功 / 失败提示）。
8. 微信后台将支付结果推送给商户系统 -> 商户系统更新订单状态（如 “已支付”），完成订单闭环，方便后续业务（发货、对账等）。


微信支付相关接口：

1、JSAPI 下单接口

JSAPI 下单接口用于在微信支付系统中创建一笔交易，生成唯一的 prepay_id，它是发起支付的凭证，请求地址为：

```http request
POST https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi
```

请求参数：

| 字段             | 是否必须 | 说明                    |
| -------------- | ---- | --------------------- |
| `appid`        | 是    | 小程序的 appid            |
| `mchid`        | 是    | 商户号                   |
| `description`  | 是    | 商品描述                  |
| `out_trade_no` | 是    | 商户订单号，必须唯一            |
| `notify_url`   | 是    | 支付成功后的回调通知地址          |
| `amount`       | 是    | 金额信息（对象）              |
| `payer`        | 是    | 支付者信息（对象，必须包含 openid） |

通常会返回一些相关信息：

```json
{
"appid" : "wxd678efh567hg6787",
"mchid" : "1230000109",
"description" : "Image形象店-深圳腾大-QQ公仔",
"out_trade_no" : "1217752501201407033233368018",
"time_expire" : "2018-06-08T10:34:56+08:00",
"attach" : "自定义数据说明",
"notify_url" : " https://www.weixin.qq.com/wxpay/pay.php",
"goods_tag" : "WXG",
"support_fapiao" : false, 
  // 金额结构体
"amount" : { 
  "total" : 100,
  "currency" : "CNY"
},
  // 支付者结构体
"payer" : { 
  "openid" : "ovqdowRIfstpQK_kYShFS2MSS9XS"
},
"detail" : {
  "cost_price" : 608800,
  "invoice_id" : "微信123",
  "goods_detail" : [
    {
      "merchant_goods_id" : "1246464644",
      "wechatpay_goods_id" : "1001",
      "goods_name" : "iPhoneX 256G",
      "quantity" : 1,
      "unit_price" : 528800
    }
  ]
},
"scene_info" : {
  "payer_client_ip" : "14.23.150.211",
  "device_id" : "013467007045764",
  "store_info" : {
    "id" : "0001",
    "name" : "腾讯大厦分店",
    "area_code" : "440305",
    "address" : "广东省深圳市南山区科技中一道10000号"
  }
},
"settle_info" : {
  "profit_sharing" : false
}
}
```

```json
// 请求成功后的返回结果，这是后续调起支付的关键参数
{
  "prepay_id" : "wx201410272009395522657a690389285100"
}
```

2、小程序端调起支付

通过 JSAPI/小程序下单接口获取到发起支付的必要参数 prepay_id，然后使用微信支付提供的小程序方法调起微信支付。微信小程序通过调用 wx.requestPayment 方法，
拉起支付界面让用户确认并支付。

请求参数：

| 字段          | 说明                                           |
| ----------- |----------------------------------------------|
| `timeStamp` | 支付时间戳，字符串形式（秒）                               |
| `nonceStr`  | 随机字符串，不长于32位                            |
| `package`   | 固定格式为 `prepay_id=xxx`                        |
| `signType`  | 默认为 `RSA`，仅支持 `RSA`                          |
| `paySign`   | 使用字段appId、timeStamp、nonceStr、package计算得出的签名值 |


```json
wx.requestPayment
(
  {
    "timeStamp": "1414561699",
    "nonceStr": "5K8264ILTKCH16CQ2502SI8ZNMTM67VS",
    "package": "prepay_id=wx201410272009395522657a690389285100",
    "signType": "RSA",
    "paySign": "oR9d8PuhnIc+YZ8cBHFCwfgpaK9gd7vaRvkYD7rthRAZ\/X+QBhcCYL21N7cHCTUxbQ+EAt6Uy+lwSN22f5YZvI45MLko8Pfso0jm46v5hqcVwrk6uddkGuT+Cdvu4WBqDzaDjnNa5UK3GfE1Wfl2gHxIIY5lLdUgWFts17D4WuolLLkiFZV+JSHMvH7eaLdT9N5GBovBwu5yYKUR7skR8Fu+LozcSqQixnlEZUfyE55feLOQTUYzLmR9pNtPbPsu6WVhbNHMS3Ss2+AehHvz+n64GDmXxbX++IOBvm2olHu3PsOUGRwhudhVf7UcGcunXt8cqNjKNqZLhLw4jq\/xDg==",
    "success":function(res){},
    "fail":function(res){},
    "complete":function(res){}
  }
)
```

****
### 3.2 内网穿透工具

微信支付的异步支付结果通知（notify_url）是由微信服务器主动回调商户服务的公网 HTTP 地址，而如果商户系统部署在本地开发环境（比如 localhost 或局域网IP），
微信服务器是无法访问该机器的，因为它不在公网。它只能访问类似：

```http request
https://pay.example.com/wechat/notify
```

而不是：

```http request
http://localhost:8080/wechat/notify
http://127.0.0.1:8080/wechat/notify
```

而要让本地服务器的端口映射到公网，从而让微信服务器可以访问本地系统，就需要使用到内网穿透工具。通过 cpolar 软件可以获得一个临时域名，
而这个临时域名是一个公网 ip，这样微信后台就可以请求到商户系统了。

下载地址：[https://dashboard.cpolar.com/get-started](https://dashboard.cpolar.com/get-started)，注册成功后，可以获取到一个隧道 Authtoken，
复制后进入软件所在目录的控制台，执行以下命令：

```shell
cpolar.exe authtoken ....
```

然后将临时域名隐射到本地后端服务：

```shell
cpolar.exe http 8080
```

返回结果：

```shell
cpolar by @bestexpresser                                                                                

Tunnel Status       online
Account             cell (Plan: Free)
Version             2.86.16/3.18
Web Interface       127.0.0.1:4042
Forwarding          http://1627e927.r36.cpolar.top -> http://localhost:8080
Forwarding          https://1627e927.r36.cpolar.top -> http://localhost:8080 # 这个就是映射的公共域名
# Conn              0
Avg Conn Time       0.00ms
```

前端发送请求时也不再是通过 localhost，而是使用这个：

```http request
https://1627e927.r36.cpolar.top/admin/employee/login
```

****
### 3. 代码实现

因为实现真正的微信支付需要用到商户注册，所以这里直接跳过支付功能，直接通过调用支付成功的方法，然后生成对应的订单信息，不涉及那些支付加密等操作。

Controller 层：

因为前端发送该请求只携带了订单号和支付方式两种信息，所以后续直接通过订单号更新订单状态即可。

```java
@PutMapping("/payment")
@Operation(summary = "订单支付")
public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
    log.info("订单支付：{}", ordersPaymentDTO);
    OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
    log.info("生成预支付交易单：{}", orderPaymentVO);
    return Result.success(orderPaymentVO);
}
```

Service 层：

这里直接模拟小程序下单需要传递的必要参数，随意设置一些信息返回给前端，确保它获取到的数据不为空，然后直接调用支付成功的方法 paySuccess()，

```java
@Override
public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
    OrderPaymentVO orderPaymentVO = OrderPaymentVO.builder()
            .nonceStr(UUID.randomUUID().toString().replaceAll("-", ""))
            .paySign("test")
            .timeStamp(String.valueOf(System.currentTimeMillis() / 1000))
            .signType("noType")
            .packageStr("package")
            .build();
    paySuccess(ordersPaymentDTO.getOrderNumber());
    return orderPaymentVO;
}
```

```text
生成预支付交易单：OrderPaymentVO(nonceStr=71a96ebf2a0f4d54a4421d6d3fdceaa1, paySign=test, timeStamp=1752655580, signType=noType, packageStr=package)
```

然后直接根据前端发送来的订单号对提交订单后生成的订单信息进行相关的修改，这里是对订单状态、订单支付状态和下单时间进行修改（提交订单时这些数据为默认值）

```java
public void paySuccess(String outTradeNo) {
    // 根据订单号查询订单
    Orders ordersDB = orderMapper.getByNumber(outTradeNo);
    // 根据订单 id 更新订单的状态、支付方式、支付状态、结账时间
    Orders orders = Orders.builder()
            .id(ordersDB.getId())
            .status(Orders.TO_BE_CONFIRMED)
            .payStatus(Orders.PAID)
            .checkoutTime(LocalDateTime.now())
            .build();
    orderMapper.update(orders);
}
```

****
## 4. 历史订单

因为查询历史订单需要展示相关订单信息和该订单中包含的菜品信息，所以需要封装一个返回对象，里面包含 Orders 中的内容，还要包含 OrderDetail 的内容，
所以封装了一个 [OrderVO](./sky-pojo/src/main/java/com/sky/vo/OrderVO.java)，它继承 Orders，所以拥有 Orders 的所有属性，并且包含一个 OrderDetail 类型的集合，
最后查到的数据封装进这个对象并返回即可。

Controller 层：

```java
@GetMapping("/historyOrders")
@Operation(summary = "历史订单查询")
public Result<PageResult> page(HistoryOrdersPageQueryDTO historyOrdersPageQueryDTO) {
    PageResult pageResult = orderService.pageQuery4User(historyOrdersPageQueryDTO);
    return Result.success(pageResult);
}
```

Service 层：

先根据当前 userId 查到对应的订单有哪些，然后获取订单的 id，再通过每个订单的 id 查询 OrderDetail 详情，因为通过分页查询，查到的是 Orders 类型的 List，
所以需要依次遍历把订单信息封装进 OrderVO，接着把查询到的 OrderDetail 信息一起封装进去，最后包装进集合并返回。

```java
@Override
public PageResult pageQuery4User(HistoryOrdersPageQueryDTO historyOrdersPageQueryDTO) {
    PageHelper.startPage(historyOrdersPageQueryDTO.getPage(), historyOrdersPageQueryDTO.getPageSize());
    Orders orders = Orders.builder()
            .userId(BaseContext.getCurrentId())
            .status(historyOrdersPageQueryDTO.getStatus())
            .build();
    // 根据用户 id 以及订单状态查询出有哪些订单，然后获取它们的订单 id，通过订单号获取每个订单的详细情况
    Page<Orders> page = orderMapper.pageQuery(orders);
    List<OrderVO> list = new ArrayList<>();
    if (page != null && page.getTotal() > 0) {
        for (Orders order : page) {
            // 订单 id
            Long orderId = order.getId();
            List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            orderVO.setOrderDetailList(orderDetails);
            list.add(orderVO);
        }
    }
    return new PageResult(page.getTotal(), list);
}
```

查询某个订单详情类似，都是先查到对应的订单，然后根据订单查到 OrderDetail，最后一起封装进 OrderVO，只不过上面返回的是集合，这里返回一个单独的对象即可。

Controller 层：

```java
@GetMapping("/orderDetail/{id}")
@Operation(summary = "查询订单详情")
public Result<OrderVO> details(@PathVariable("id") Long id) {
    OrderVO orderVO = orderService.details(id);
    return Result.success(orderVO);
}
```

Service 层：

```java
@Override
public OrderVO details(Long id) {
    Orders orders = orderMapper.getById(id);
    OrderVO orderVO = new OrderVO();
    BeanUtils.copyProperties(orders, orderVO);
    List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);
    orderVO.setOrderDetailList(orderDetailList);
    return orderVO;
}
```

****
## 5. 取消订单

待支付和待接单状态下，用户可直接取消订单，但商家已接单状态下、派送中状态下，用户不能自己取消订单；取消订单后需要将订单状态修改为“已取消”。

Controller 层：

```java
@PutMapping("/cancel/{id}")
@Operation(summary = "取消订单")
public Result cancel(@PathVariable("id") Long id) throws Exception {
    orderService.userCancelById(id);
    return Result.success();
}
```

Service 层：

因为前端会发送当前订单的id，所以可以根据id查询对应的订单，然后判断该订单的状态，根据不同的状态决定是否可以进行取消订单，成功取消订单，则需要把订单状态修改为已取消。

```java
@Override
public void userCancelById(Long id) {
    // 根据id查询订单
    Orders ordersDB = orderMapper.getById(id);
    // 校验订单是否存在
    if (ordersDB == null) {
        throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }
    // 订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
    if (ordersDB.getStatus() > 2) {
        throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }
    // 当订单状态为待付款和待接单时
    Orders orders = new Orders();
    orders.setId(ordersDB.getId());
    // 更新订单状态、取消原因、取消时间
    orders.setStatus(Orders.CANCELLED);
    orders.setCancelReason("用户取消");
    orders.setCancelTime(LocalDateTime.now());
    orderMapper.update(orders);
}
```

## 6. 再来一单

再来一单就是将原订单中的商品重新加入到购物车中，但再执行再次添加商品时，会先执行依次清空购物车的操作，防止多次点击再来一单导致购物车一直新增

Controller 层：

```java
@PostMapping("/repetition/{id}")
@Operation(summary = "再来一单")
public Result repetition(@PathVariable Long id) {
    orderService.repetition(id);
    return Result.success();
}
```

Service 层：

根据订单id可以查询到关联的所有订单详情信息，因为一个订单可以包含多条订单详情（一个商品对应一条），所以查出的数据以集合的形式接收，
然后依次把每条数据封装进购物车对象中 ShoppingCart，然后把这些购物车对象插入表中即可。

```java
@Override
public void repetition(Long id) {
    // 根据订单id查询当前订单详情
    List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);
    for (OrderDetail orderDetail : orderDetailList) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(orderDetail, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }
}
```

****
# 十五、商家端订单管理

## 1. 订单搜索

商家端支持输入订单号/手机号进行搜索（支持模糊搜索），也可以根据订单状态、下单时间、进行筛选。

Controller 层：

```java
@GetMapping("/conditionSearch")
@Operation(summary = "订单搜索")
public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
    PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
    return Result.success(pageResult);
}
```

Service 层：

根据前端发送来的数据进行分页查询，获取到某页的所有订单信息，然后把订单信息封装进 OrderVO 返回给前端展示。而 OrderVO 中有个 private String orderDishes 字段，
它就是用来接收所有菜品名称的，所以要把查询出的菜品信息中提取菜品名和数量，用字符串拼接起来，然后注入到这个字段，这样前端页面才会展示出来。

```java
@Override
public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
  PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
  Page<Orders> page = orderMapper.pageQuery2(ordersPageQueryDTO);
  List<OrderVO> list = new ArrayList<>();
  if (page != null && page.getTotal() > 0) {
    for (Orders order : page) {
      OrderVO orderVO = new OrderVO();
      BeanUtils.copyProperties(order, orderVO);
      List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(order.getId());
      StringBuilder orderDishesStr = new StringBuilder();
      for (OrderDetail orderDetail : orderDetailList) {
        orderDishesStr.append(orderDetail.getName()).append("*").append(orderDetail.getNumber()).append("；");
      }
      orderDishesStr = new StringBuilder(orderDishesStr.substring(0, orderDishesStr.length() - 1) + "。");
      orderVO.setOrderDishes(orderDishesStr.toString());
      orderVO.setOrderDetailList(orderDetailList);
      list.add(orderVO);
    }
  }
  return new PageResult(page.getTotal(), list);
}
```

Mapper 层：

因为可以根据时间范围来查询订单，所以需要传入时间作为查询条件，而 order 表中没有开始和结束时间，只有订单下单时间，所以只能通过判断下单时间是否在指定的时间范围内，
order_time &gt;= #{beginTime} 中的 &gt;= 是 XML 中表示 >= 的转义字符，&lt;= 是 <= 的转义字符。

```sql
<select id="pageQuery2" resultType="com.sky.entity.Orders">
    select * from orders
    <where>
        <if test="number != null and number!=''">
            and number like concat('%',#{number},'%')
        </if>
        <if test="phone != null and phone!=''">
            and phone like concat('%',#{phone},'%')
        </if>
        <if test="userId != null">
            and user_id = #{userId}
        </if>
        <if test="status != null">
            and status = #{status}
        </if>
        <if test="beginTime != null">
            and order_time &gt;= #{beginTime}
        </if>
        <if test="endTime != null">
            and order_time &lt;= #{endTime}
        </if>
    </where>
    order by order_time desc
</select>
```

****
## 2. 各个状态的订单数量统计

该请求没有携带任何参数，但需要返回带派送、派送中、待接单的订单数量。

Controller 层：

```java
@GetMapping("/statistics")
@Operation(summary = "各个状态的订单数量统计")
public Result<OrderStatisticsVO> statistics() {
    OrderStatisticsVO orderStatisticsVO = orderService.statistics();
    return Result.success(orderStatisticsVO);
}
```

Service 层：

直接根据对应的状态值查询数据库中有几条订单即可。

```java
@Override
public OrderStatisticsVO statistics() {
    // 根据状态，分别查询出待接单、待派送、派送中的订单数量
    // select count(id) from orders where status = #{status}
    Integer toBeConfirmed = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
    Integer confirmed = orderMapper.countStatus(Orders.CONFIRMED);
    Integer deliveryInProgress = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);
    // 将查询出的数据封装到 orderStatisticsVO 中响应
    OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
    orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
    orderStatisticsVO.setConfirmed(confirmed);
    orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
    return orderStatisticsVO;
}
```

****
## 3. 查询订单详情

订单详情页面需要展示订单基本信息（状态、订单号、下单时间、收货人、电话、收货地址、金额等），还需要展示订单明细数据（商品名称、数量、单价）。也就是将订单表和订单详情表的内容一起封装进一个对象。
而在用户端也有一个查询订单详情的操作，所以它们的操作是一样的。

Controller 层：

```java
@GetMapping("/details/{id}")
@Operation(summary = "查询订单详情")
public Result<OrderVO> details(@PathVariable("id") Long id) {
    OrderVO orderVO = orderService.details(id);
    return Result.success(orderVO);
}
```

Service 层：

```java
@Override
public OrderVO details(Long id) {
    Orders orders = orderMapper.getById(id);
    OrderVO orderVO = new OrderVO();
    BeanUtils.copyProperties(orders, orderVO);
    List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);
    orderVO.setOrderDetailList(orderDetailList);
    return orderVO;
}
```

****
## 4. 商家接单

商家接单其实就是将订单的状态修改为“已接单”，所以就是把订单状态修改一下就行。

Controller 层：

```java
@PutMapping("/confirm")
@Operation(summary = "接单")
public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
    orderService.confirm(ordersConfirmDTO);
    return Result.success();
}
```

Service 层：

```java
@Override
public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
    Orders orders = Orders.builder()
            .id(ordersConfirmDTO.getId())
            .status(Orders.CONFIRMED)
            .build();
    orderMapper.update(orders);
}
```

****
## 5. 商家拒单

商家拒单其实就是将订单状态修改为“已取消”，但只有订单在“待接单”状态时才可以执行拒单操作，并且商家拒单时需要指明拒单原因。因为没有实现真实的付款操作，
所以这里不涉及退款操作，只把订单的支付状态设置为已退款（买家支付状态需要为已支付才执行）。

Controller 层：

```java
@PutMapping("/rejection")
@Operation(summary = "拒单")
public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
    orderService.rejection(ordersRejectionDTO);
    return Result.success();
}
```

Service 层：

```java
@Override
public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
    Orders ordersDB = orderMapper.getById(ordersRejectionDTO.getId());
    // 只有订单为待接单状态才能拒单，否则抛出异常
    if (ordersDB == null || !Objects.equals(ordersDB.getStatus(), Orders.TO_BE_CONFIRMED)) {
        throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }
    Orders orders = new Orders();
    // 查询订单的支付状态
    Integer payStatus = ordersDB.getPayStatus();
    if (Objects.equals(payStatus, Orders.PAID)) {
        // 用户已支付，需要退款
        orders.setPayStatus(Orders.REFUND);
    } else {
        orders.setPayStatus(ordersDB.getPayStatus());
    }
    orders.setId(ordersDB.getId());
    orders.setStatus(Orders.CANCELLED);
    orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
    orders.setCancelTime(LocalDateTime.now());
    orderMapper.update(orders);
}
```

****
## 6. 派送订单

派送订单其实就是将订单状态从“带派送”修改为“派送中”，同样的只有状态为“待派送”的订单可以执行派送订单操作。完成订单同理。

Controller 层：

```java
@PutMapping("/delivery/{id}")
@Operation(summary = "派送订单")
public Result delivery(@PathVariable("id") Long id) {
    orderService.delivery(id);
    return Result.success();
}
```

Service 层：

```java
@Override
public void delivery(Long id) {
    Orders ordersDB = orderMapper.getById(id);
    if (ordersDB == null || !ordersDB.getStatus().equals(Orders.CONFIRMED)) {
        throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }
    Orders orders = new Orders();
    orders.setId(id);
    // 更新状态为派送中
    orders.setStatus(Orders.DELIVERY_IN_PROGRESS);
    orders.setDeliveryTime(LocalDateTime.now());
    orderMapper.update(orders);
}
```

****
# 十六、订单状态定时处理、来单提醒和客户催单

## 1. Spring Task

Spring Task 是 Spring 提供的一种轻量级任务调度工具，主要用于定时任务的开发，适用于轻量级项目中的任务调度功能。Spring Task 基于 @Scheduled 注解和 Spring 容器实现定时执行任务、循环任务等需求。
例如信用卡每月还款提醒、银行贷款每月还款提醒以及火车票售票系统处理未支付订单，只要是需要定时处理的场景都可以使用 Spring Task。

### 1.1 Cron 表达式

Cron 表达式其实就是一个字符串，通过 Cron 表达式可以定义任务触发的时间，Cron 由 6 个字段组成：

```text
// Spring 不支持第 7 位“年”字段（Quartz 支持）
秒 分 时 日 月 星期
```

| 字段顺序 | 字段名称 | 取值范围           | 示例                          |
| ---- | ---- |----------------|-----------------------------|
| 1    | 秒    | 0–59           | 0, 15, 30, \*               |
| 2    | 分    | 0–59           | 0, 10, 45, \*               |
| 3    | 时    | 0–23           | 8, 12, 23, \*               |
| 4    | 日    | 1–31           | 1, 15, 31, \*               |
| 5    | 月    | 1–12 或 JAN–DEC | 1, 6, 12, \*                |
| 6    | 星期   | 1–7 或 SUN–SAT  | 7(SUN), 1(MON), ..., 6(SAT) |

特殊符号说明：

| 符号  | 含义                               |
| --- |----------------------------------|
| `*` | 任意值（通配符）                         |
| `?` | 仅用于“日”和“星期”字段之一，表示“不指定”该字段的值     |
| `-` | 表示范围，如 `10-12` 表示 10 到 12        |
| `,` | 列出多个值，如 `1,3,5` 表示 1、3、5         |
| `/` | 表示增量，如 `0/5` 表示从 0 开始每隔 5 个单位    |
| `L` | 表示最后一个，如 `L` 代表“当月最后一天”或“周六”     |
| `W` | 表示“最近的工作日”（非周末）                  |
| `#` | 表示“星期几#第几个”，如 `2#1` 表示“每月第一个星期二” |

常见表达式：

  秒分时日月星期

- 0 0 0 * * ?：每天凌晨 0 点执行
- 0 0/5 * * * ?：每 5 分钟执行一次
- 0 0 8,12,18 * * ?：每天的 8、12、18 点执行
- 0 0 0 1 * ?：每月 1 号凌晨执行
- 0 0 0 L * ?：每月最后一天执行
- 0 0 0 ? * 1：每周一凌晨 0 点执行
- 0 0 0 ? * 1：每周一凌晨 0 点执行
- 0 15 10 ? * MON-FRI：每周一到周五 10:15 执行
- 0 0 10 ? * 2#1：每月第一个星期二 10:00 执行
- */10 * * * * ?：每 10 秒执行一次
- 0 0/30 8-18 * * ?：每天 8 点到 18 点之间每半小时执行一次
- 0 0 0 15W * ?：每月 15 日最近的工作日（不是周末）

需要注意的是：日和星期两个字段不能同时指定值，否则会冲突，所以必须一个是值，另一个用 ?，例如 * ? 表示每天，不限制星期几；? 1 表示不指定日期，只在每周一执行。

使用：

在启动类上添加 @EnableScheduling 注解，然后创建定时任务类，使用 @Scheduled 注解定义任务方法：

```java
@Slf4j
@Component
public class MyTask {
    @Scheduled(cron = "0/5 * * * * ?")
    public void executeTask(){
        log.info("定时任务开始执行：{}", LocalDateTime.now());
    }
}
```

当程序运行时，每五秒就会打印一次日志。

****
## 2. 订单状态定时处理

用户下单后可能存在的情况：

- 下单后未支付，订单一直处于“待支付”状态
- 用户收货后管理端未点击完成按钮，订单一直处于“派送中”状态

对于上面两种情况需要通过定时任务来修改订单状态，具体逻辑为：

- 通过定时任务每分钟检查一次是否存在支付超时订单（下单后超过15分钟仍未支付则判定为支付超时订单），如果存在则修改订单状态为“已取消”
- 通过定时任务每天凌晨 1 点检查一次是否存在“派送中”的订单，如果存在则修改订单状态为“已完成”

自定义定时任务类 OrderTask：

每分钟检查一次，对 Order 表进行一次查询，根据当前时间 - 15 分钟和下单时间进行对比，判断是否超时支付。

```java
// 处理支付超时订单
@Scheduled(cron = "0 * * * * ?")
public void processTimeoutOrder(){
  log.info("处理支付超时订单：{}", LocalDateTime.now());
  // 计算当前时间的前 15 分钟，作为超时支付订单
  LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
  // select * from orders where status = 1 and order_time < 当前时间-15分钟
  List<Orders> ordersList = orderMapper.getByStatusAndOrdertimeLT(Orders.PENDING_PAYMENT, time);
  if(ordersList != null && !ordersList.isEmpty()){
    ordersList.forEach(order -> {
      // 设置新状态
      order.setStatus(Orders.CANCELLED);
      order.setCancelReason("支付超时，自动取消");
      order.setCancelTime(LocalDateTime.now());
      orderMapper.update(order);
    });
  }
}
```

```java
// 每天凌晨 1 点处理“派送中”状态的订单
@Scheduled(cron = "0 0 1 * * ?")
public void processDeliveryOrder(){
    log.info("处理派送中订单：{}", LocalDateTime.now());
    // select * from orders where status = 4 and order_time < 当前时间-1小时
    // 超过一小时没送达判定为超时
    LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
    List<Orders> ordersList = orderMapper.getByStatusAndOrdertimeLT(Orders.DELIVERY_IN_PROGRESS, time);
    if(ordersList != null && !ordersList.isEmpty()){
        ordersList.forEach(order -> {
            order.setStatus(Orders.COMPLETED);
            orderMapper.update(order);
        });
    }
}
```

需要注意的是：Spring 的 @Scheduled 方法默认是由一个单线程调度器执行的，这意味着如果两个定时任务恰好时间重合，它们会一个一个顺序执行，不会并发运行。
所以在凌晨 1 点，两个任务都会被触发，不会阻塞，但谁先谁后取决于调度器中哪个任务先被调度到了。

****
## 3. WebSocket

WebSocket 是基于 TCP 的一种新的网络协议。它实现了浏览器与服务器全双工通信——浏览器和服务器只需要完成一次握手，两者之间就可以创建持久性的连接，
并进行双向数据传输。在 WebSocket 出现前，浏览器和服务器通信主要通过 HTTP 协议，但 HTTP 是单向通信，只有客户端发送请求，服务器才能响应；所以它无法实现实时推送数据，
需要客户端定时轮询。而 WebSocket 的全双工通信可以保证客户端与服务器之间可以互相主动发送消息，而不必等待对方请求，常用于即时聊天、在线游戏、弹幕等实时系统。

实现全双工通信 —— 浏览器可以主动发送消息给服务器，服务器也可以主动推送消息给浏览器：

1). 直接使用 websocket.html 页面作为 WebSocket 客户端

2). 导入 WebSocket 的 maven 坐标

3). 导入 WebSocket 服务端组件 WebSocketServer，用于和客户端通信

4). 导入配置类 WebSocketConfiguration，注册 WebSocket 的服务端组件

5). 导入定时任务类 WebSocketTask，定时向客户端推送数据

websocket.html 页面：

```html
<!DOCTYPE HTML>
<html>
...
<script type="text/javascript">
    var websocket = null;
    var clientId = Math.random().toString(36).substr(2);

    // 判断当前浏览器是否支持 WebSocket
    if('WebSocket' in window){
        // 连接 WebSocket 节点
        websocket = new WebSocket("ws://localhost:8080/ws/"+clientId);
    }
    else{
        alert('Not support websocket')
    }

    // 连接发生错误的回调方法
    websocket.onerror = function(){
        setMessageInnerHTML("error");
    };

    // 连接成功建立的回调方法
    websocket.onopen = function(){
        setMessageInnerHTML("连接成功");
    }

    // 接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    }

    // 连接关闭的回调方法
    websocket.onclose = function(){
        setMessageInnerHTML("close");
    }

    // 监听窗口关闭事件，当窗口关闭时，主动去关闭 websocket 连接，防止连接还没断开就关闭窗口，server 端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }

    // 将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    // 发送消息
    function send(){
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
	
	// 关闭连接
    function closeWebSocket() {
        websocket.close();
    }
</script>
</html>
```

定义 [WebSocket](./sky-server/src/main/java/com/sky/websocket/WebSocketServer.java) 服务端组件:

```java
// 存放会话对象
private static Map<String, Session> sessionMap = new HashMap();

// 连接建立成功调用的方法
@OnOpen
public void onOpen(Session session, @PathParam("sid") String sid) {
    System.out.println("客户端：" + sid + "建立连接");
    sessionMap.put(sid, session);
}

// 收到客户端消息后调用的方法
@OnMessage
public void onMessage(String message, @PathParam("sid") String sid) {
    System.out.println("收到来自客户端：" + sid + "的信息:" + message);
}

// 连接关闭调用的方法
@OnClose
public void onClose(@PathParam("sid") String sid) {
    System.out.println("连接断开:" + sid);
    sessionMap.remove(sid);
}

// 群发
public void sendToAllClient(String message) {
    Collection<Session> sessions = sessionMap.values();
    for (Session session : sessions) {
        try {
            // 服务器向客户端发送消息
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

因为 WebSocket 不走 Spring MVC，所以需要注册 WebSocket 组件：

```java
@Configuration
public class WebSocketConfiguration {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

设置定时任务推送消息，利用 SpringTask 定时向客户端推送实时信息：

```java
// 通过 WebSocket 每隔 5 秒向客户端发送消息
@Scheduled(cron = "0/5 * * * * ?")
public void sendMessageToClient() {
    webSocketServer.sendToAllClient("这是来自服务端的消息：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
}
```

****
## 4. 来单提醒

通过 WebSocket 实现管理端页面和服务端保持长连接状态，当客户支付后调用 WebSocket 的相关 API 实现服务端向客户端推送消息，客户端浏览器解析服务端推送的消息，
判断是来单提醒还是客户催单，进行相应的消息提示和语音播报。这里约定服务端发送给客户端浏览器的数据格式为 JSON，字段包括 type（消息类型，1为来单提醒 2为客户催单），
orderId（订单id），content（消息内容）

因为下单并支付成功后就需要向商家发送消息，所以需要在支付成功的那个方法里面注入 WebSocketServer 对象，然后把相关信息推送给浏览器。

```java
// 在 paySuccess() 方法中添加 WebSocket
Map map = new HashMap();
map.put("type", 1);// 消息类型，1 表示来单提醒
map.put("orderId", orders.getId());
map.put("content", "订单号：" + outTradeNo);
// 通过 WebSocket 实现来单提醒，向客户端浏览器推送消息
webSocketServer.sendToAllClient(JSON.toJSONString(map));
```

****
## 5. 客户催单

该功能与上面的类似，都是通过 WebSocket 实现管理端页面和服务端保持长连接状态，当用户点击催单按钮后，调用 WebSocket 的相关 API 实现服务端向客户端推送消息，
但因为催单是一个新功能，所以它是发送一次请求。

Controller 层：

```java
@GetMapping("/reminder/{id}")
@Operation(summary = "用户催单")
public Result reminder(@PathVariable("id") Long id) {
    orderService.reminder(id);
    return Result.success();
}
```

Service 层：

具体逻辑和来单提醒一样，都是讲相关信息发送给浏览器。

```java
@Override
public void reminder(Long id) {
    // 查询订单是否存在
    Orders orders = orderMapper.getById(id);
    if (orders == null) {
        throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }
    // 基于 WebSocket 实现催单
    Map map = new HashMap();
    map.put("orderId", id);
    map.put("type", 2); // 2 代表用户催单
    map.put("content", "订单号：" + orders.getNumber());
    webSocketServer.sendToAllClient(JSON.toJSONString(map));
}
```

****
# 十七、统计

## 1. Apache ECharts

Apache ECharts 是一款基于 Javascript 的数据可视化图表库，提供直观，生动，可交互，可个性化定制的数据可视化图表。官网地址：[https://echarts.apache.org/zh/index.html](https://echarts.apache.org/zh/index.html)

入门案例：

1). 引入 echarts.js 文件

2). 为 ECharts 准备一个设置宽高的 DOM

3). 初始化 echarts 实例

4). 指定图表的配置项和数据

5). 使用指定的配置项和数据显示图表

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>ECharts</title>
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.5.0/dist/echarts.min.js"></script>
  </head>
  <body>
    <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'));

      // 指定图表的配置项和数据
      var option = {
        title: {
          text: 'ECharts 入门示例'
        },
        tooltip: {}, // 鼠标悬停时的提示框。空对象表示启用默认样式
        legend: {
          data: ['销量'] // 图例区域，这里用于显示系列“销量”的标识
        },
        // x轴的数据，表示横轴标签（商品种类）
        xAxis: { 
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        // y轴（纵轴）采用默认配置
        yAxis: {},
        // 系列数据，决定图表展示内容
        series: [
          {
            name: '销量', // 图例名称
            type: 'bar', // 图表类型：bar 表示柱状图
            data: [5, 20, 36, 10, 10, 20] // 每种商品对应的销量
          }
        ]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    </script>
  </body>
</html>
```

****
## 2. 营业额统计

营业额统计是基于折现图来展现，并且按照天来展示的，也就是某一个时间范围之内的每一天的营业额。同时，不管光标放在哪个点上，它都会把具体的数值展示出来。
并且还需要注意日期并不是固定写死的，是由上边时间选择器来决定。比如选择是近7天、或者是近30日，或者是本周，就会把相应这个时间段之内的每一天的日期通过横坐标展示。

具体返回数据一般由前端来决定，前端展示图表，具体折现图对应数据是什么格式是有固定的要求的。所以说，后端需要去适应前端，它需要什么格式的数据，就给它返回什么格式的数据。
而这个营业额统计功能，前端要求返回的是日期列表（日期之间以逗号分隔）和营业额列表（营业额之间以逗号分隔），它们两个用 String 接收。

Controller 层：

```java
@GetMapping("/turnoverStatistics")
@Operation(summary = "营业额数据统计")
public Result<TurnoverReportVO> turnoverStatistics(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate begin,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate end) {
    return Result.success(reportService.getTurnover(begin, end));
}
```

Service 层：

统计日期就是根据前端发送的起始日期和结束日期，统计有哪些具体的日期（当天的前一天为最新的数据），然后把这些日期放入集合中；后面可以从这个集合中拿出具体的一天，然后根据这一天作为查询 order 表的条件，
统计每一条查出来的订单信息的金额，把金额也放进一个集合，最后统一对这两个集合转换成字符串，用逗号拼接每一天的详细，例如 2025-10-01，2-25-10-02...对应 406，1024...

```java
@Override
public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
    // 当前集合存放从 begin 到 end 范围内的每天的日期
    List<LocalDate> dates = new ArrayList<>();
    dates.add(begin);
    while (!begin.equals(end)){
        begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
        dates.add(begin);
    }
    // 查询对应每天的营业额
    List<Double> turnoverList = new ArrayList<>();
    for (LocalDate date : dates) {
        // 因为 order 表中的下单时间格式为 年月日 时分秒，所以也需要按照这样的格式去查询
        LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN); // 获取当天的最早时间 00:00:00
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX); // 获取当天最晚时间 23:59:59
        Map map = new HashMap();
        // 统计营业额需要查询订单状态为已完成的
        map.put("status", Orders.COMPLETED);
        map.put("begin",beginTime);
        map.put("end", endTime);
        // select sum(amount) from orders where order_time > ? and order_time < ? and status = 5
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;
        turnoverList.add(turnover);
    }
    return TurnoverReportVO.builder()
            .dateList(StringUtils.join(dates,","))
            .turnoverList(StringUtils.join(turnoverList,","))
            .build();
}
```

****
## 3. 用户统计

用户统计统计的是用户的数量，通过折线图来展示，一根线代表的是用户总量，一根线代表的是新增用户数量，所以说用户统计主要统计两个数据，一个是总的用户数量，
另外一个是新增用户数量，具体到每一天。所以前端是根据时间选择区间，然后对应展示这两个数据。后端则需要返回新增用户和总用户列表，用 String 接收后返回。

Controller 层：

```java
@GetMapping("/userStatistics")
@Operation(summary = "用户数据统计")
public Result<UserReportVO> userStatistics(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate begin,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate end){
    return Result.success(reportService.getUserStatistics(begin,end));
}
```

Service 层：

这里也是讲范围内的每一天都添加到集合中，再从集合中取出每一天对应的新增人数和用户总量，然后讲集合中的元素拼接成字符串。
```java
@Override
public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
    // 讲开始日期到结束日期的所有日期添加到集合中
    List<LocalDate> dateList = new ArrayList<>();
    dateList.add(begin);
    while (!begin.equals(end)){
        begin = begin.plusDays(1);
        dateList.add(begin);
    }

    List<Integer> newUserList = new ArrayList<>(); // 新增用户数
    List<Integer> totalUserList = new ArrayList<>(); // 总用户数
    for (LocalDate date : dateList) {
        LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
        // 新增用户数量 select count(id) from user where create_time > ? and create_time < ?
        Integer newUser = getUserCount(beginTime, endTime);
        // 总用户数量截止目前日期的所有用户量，所以可以不用考虑起始日期
        // select count(id) from user where  create_time < ?
        Integer totalUser = getUserCount(null, endTime);
        newUserList.add(newUser);
        totalUserList.add(totalUser);
    }
    return UserReportVO.builder()
            .dateList(StringUtils.join(dateList,","))
            .newUserList(StringUtils.join(newUserList,","))
            .totalUserList(StringUtils.join(totalUserList,","))
            .build();
}

private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
    Map map = new HashMap();
    map.put("begin",beginTime);
    map.put("end", endTime);
    return userMapper.countByMap(map);
}
```

****
## 4. 订单统计

订单统计通过一个折现图来展现，一线代表的是订单总数，一根线代表的是有效订单数（状态为已完成的订单就属于有效订单），分别反映的是每一天的数据。
在折线图的上面还有 3 个数字，分别统计订单总数、有效订单、订单完成率，它统计的是整个时间区间之内总的数据。所以在返回属性的封装上，除了正常统计的日期、每日订单、有效订单集合，
还需要统计总的订单数和总的有效订单，然后计算它们的完成率（有效订单数 / 总订单数 * 100%）。

Controller 层：

```java
@GetMapping("/ordersStatistics")
@Operation(summary = "用户数据统计")
public Result<OrderReportVO> orderStatistics(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate begin,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate end){

    return Result.success(reportService.getOrderStatistics(begin,end));
}
```

Service 层：

这里的统计与上面的类似，统计每天的数据然后累加返回。

```java
@Override
public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
    List<LocalDate> dates = new ArrayList<>();
    dates.add(begin);
    while (!begin.equals(end)){
        begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
        dates.add(begin);
    }
    // 每日订单总数集合
    List<Integer> orderCountList = new ArrayList<>();
    // 每日有效订单数集合
    List<Integer> validOrderCountList = new ArrayList<>();
    // 时间区间内的总订单数
    Integer totalOrderCount = 0;
    // 时间区间内的总有效订单数
    Integer validTotalOrderCount = 0;
    // 订单完成率
    Double orderCompletionRate = 0.0;
    for (LocalDate date : dates) {
        LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
        // 查询每天的总订单数 select count(id) from orders where order_time > ? and order_time < ?
        Integer orderCount = getOrderCount(beginTime, endTime, null);
        // 查询每天的有效订单数 select count(id) from orders where order_time > ? and order_time < ? and status = ?
        Integer validOrderCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);
        orderCountList.add(orderCount);
        validOrderCountList.add(validOrderCount);
        totalOrderCount += orderCount;
        validTotalOrderCount += validOrderCount;
    }
    if(totalOrderCount != 0){
        orderCompletionRate = validTotalOrderCount.doubleValue() / totalOrderCount;
    }
    return OrderReportVO.builder()
            .dateList(StringUtils.join(dates, ","))
            .orderCountList(StringUtils.join(orderCountList, ","))
            .validOrderCountList(StringUtils.join(validOrderCountList, ","))
            .totalOrderCount(totalOrderCount)
            .validOrderCount(validTotalOrderCount)
            .orderCompletionRate(orderCompletionRate)
            .build();
}

private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
    Map map = new HashMap();
    map.put("begin", beginTime);
    map.put("end", endTime);
    map.put("status", status);
    return orderMapper.countByMap(map);
}
```

****
## 5. 销量排名 Top10

项目当中的商品主要包含两类：一个是套餐，一个是菜品，所以销量排名指的是菜品和套餐销售的数量排名。通过柱形图来展示销量排名，这些销量是按照降序来排列，
并且只需要统计销量排名前十的商品。所以需要根据时间选择区间，展示销量前 10 的商品（包括菜品和套餐）。

Controller 层：

```java
@GetMapping("/top10")
@Operation(summary = "销量排名统计")
public Result<SalesTop10ReportVO> top10(
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

    return Result.success(reportService.getSalesTop10(begin,end));
}
```

Service 层：

因为不需要使用日期作为坐标，所以不需要讲每天的日期添加到集合中并返回，而统计每个菜品的售出数量，只需要根据起止日期查询对应的 order_detail 表即可，
但是 order_detail 表中没有日期字段，所以需要用到 order 表。查出数据后讲名字和数量封装进集合中，但是数量集合使用的是 Integer 类型，所以要拼接逗号的话，
就需要先转换成 String 类型。

```java
@Override
public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
    LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
    LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
    List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);
    List<String> nameList = new ArrayList<>();
    List<Integer> numberList = new ArrayList<>();
    for (GoodsSalesDTO goodsSalesDTO : goodsSalesDTOList) {
        nameList.add(goodsSalesDTO.getName());
        numberList.add(goodsSalesDTO.getNumber());
    }
    return SalesTop10ReportVO.builder()
            .nameList(StringUtils.join(nameList, ","))
            .numberList(numberList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            .build();
}
```

Mapper 层：

统计每个菜品的数量，就需要使用到 group by，使用名字作为分组条件，然后根据起止日期以及已完成作为条件，查询对应的 number 字段，然后降序排序，
此时从集合中查出的数据就是按照数量由多到少排序的，然后再限制 10 条数据，达到预期效果。

```sql
<select id="getSalesTop10" resultType="com.sky.dto.GoodsSalesDTO">
    select od.name name,sum(od.number) number from order_detail od ,orders o
    where od.order_id = o.id
    and o.status = 5
    <if test="beginTime != null">
        and order_time &gt;= #{beginTime}
    </if>
    <if test="endTime != null">
        and order_time &lt;= #{endTime}
    </if>
    group by name
    order by number desc
    limit 0, 10
</select>
```

****
# 十八、Excel 报表

## 1. 工作台页面功能

工作台是系统运营的数据看板并提供快捷操作入口，工作台展示的数据：

- 今日数据
- 订单管理
- 菜品总览
- 套餐总览
- 订单信息

虽然这些数据都是在一个页面中展示的，可以把这些全部封装进一个接口中，但是这样就会导致后端的代码逻辑过于复杂混乱，所以通常是相关信息的内容封装进一个接口，
然后在该页面发送多个请求。

### 1.1 今日数据接口

该接口用于展示今日的营业数据，包括营业额、有效订单、订单完成率、平均客单价、新增用户，所以需要把这些数据封装进视图对象中并返回，因为这些数据只涉及一天，
所以只需要封装成基本数据类型即可。

Controller 层：

```java
@GetMapping("/businessData")
@Operation(summary = "工作台今日数据查询")
public Result<BusinessDataVO> businessData(){
    // 获得当天的开始时间
    LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
    // 获得当天的结束时间
    LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
    BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
    return Result.success(businessDataVO);
}
```

Service 层：

因为之前的查询数据库操作都涉及到了这些数据，所以只需要把开始和结束日期设置为当天即可，

```java
@Override
public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
    Map map = new HashMap();
    map.put("begin",begin);
    map.put("end",end);
    // 查询总订单数
    Integer totalOrderCount = orderMapper.countByMap(map);
    map.put("status", Orders.COMPLETED);
    // 营业额
    Double turnover = orderMapper.sumByMap(map);
    turnover = turnover == null? 0.0 : turnover;
    // 有效订单数（已完成的订单）
    Integer validOrderCount = orderMapper.countByMap(map);
    Double unitPrice = 0.0;
    Double orderCompletionRate = 0.0;
    if(totalOrderCount != 0 && validOrderCount != 0){
        // 订单完成率
        orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        // 平均客单价
        unitPrice = turnover / validOrderCount;
    }
    // 新增用户数
    Integer newUsers = userMapper.countByMap(map);
    return BusinessDataVO.builder()
            .turnover(turnover)
            .validOrderCount(validOrderCount)
            .orderCompletionRate(orderCompletionRate)
            .unitPrice(unitPrice)
            .newUsers(newUsers)
            .build();
}
```

****
### 1.2 查询订单

Controller 层：

```java
@GetMapping("/overviewOrders")
@Operation(summary = "查询订单管理数据")
public Result<OrderOverViewVO> orderOverView(){
    return Result.success(workspaceService.getOrderOverView());
}
```

Service 层：

这里也是通过之前写好的查询语句，只设置当天作为查询条件，获取当天的订单数据，因为页面仅需展示简单的数字总量，所以用基本类型封装。

```java
@Override
public OrderOverViewVO getOrderOverView() {
    Map map = new HashMap();
    map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
    map.put("status", Orders.TO_BE_CONFIRMED);
    // 待接单
    Integer waitingOrders = orderMapper.countByMap(map);
    // 待派送
    map.put("status", Orders.CONFIRMED);
    Integer deliveredOrders = orderMapper.countByMap(map);
    // 已完成
    map.put("status", Orders.COMPLETED);
    Integer completedOrders = orderMapper.countByMap(map);
    // 已取消
    map.put("status", Orders.CANCELLED);
    Integer cancelledOrders = orderMapper.countByMap(map);
    // 全部订单
    map.put("status", null);
    Integer allOrders = orderMapper.countByMap(map);
    return OrderOverViewVO.builder()
            .waitingOrders(waitingOrders)
            .deliveredOrders(deliveredOrders)
            .completedOrders(completedOrders)
            .cancelledOrders(cancelledOrders)
            .allOrders(allOrders)
            .build();
}
```

****
### 1.3 菜品与套餐数量总览

菜品与套餐仅需查询起售与停售的数量，所以只需要指定不同的在售状态作为查询条件即可，两部分代码类似。

Controller 层：

```java
@GetMapping("/overviewDishes")
@Operation(summary = "查询菜品总览")
public Result<DishOverViewVO> dishOverView(){
    return Result.success(workspaceService.getDishOverView());
}
```

Service 层：

```java
@Override
public DishOverViewVO getDishOverView() {
    Map map = new HashMap();
    // 查询在售
    map.put("status", StatusConstant.ENABLE);
    Integer sold = dishMapper.countByMap(map);
    // 查询已停售
    map.put("status", StatusConstant.DISABLE);
    Integer discontinued = dishMapper.countByMap(map);
    return DishOverViewVO.builder()
            .sold(sold)
            .discontinued(discontinued)
            .build();
}
```

****
## 2. Apache POI

Apache POI 是一个处理 Miscrosoft Office 各种文件格式的开源项目，简单来说就是可以使用 POI 在 Java 程序中对 Miscrosoft Office 各种文件进行读写操作。
一般情况下，POI 都是用于操作 Excel 文件。例如银行网银系统导出交易明细、各种业务系统导出 Excel 报表、批量导入业务数据。

导入依赖：

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.5</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
```

将数据写入 Excel 文件:

```java
public class POITest {
    public static void write() throws Exception{
        // 在内存中创建一个Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook();
        // 创建Sheet页
        XSSFSheet sheet = excel.createSheet("test");
        // 在Sheet页中创建行，0表示第1行
        XSSFRow row1 = sheet.createRow(0);
        // 创建单元格并在单元格中设置值，单元格编号也是从0开始，1表示第2个单元格
        row1.createCell(1).setCellValue("姓名");
        row1.createCell(2).setCellValue("城市");

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(1).setCellValue("张三");
        row2.createCell(2).setCellValue("北京");

        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(1).setCellValue("李四");
        row3.createCell(2).setCellValue("上海");

        FileOutputStream out = new FileOutputStream("D:\\POITest.xlsx");
        // 通过输出流将内存中的 Excel 文件写入到磁盘上
        excel.write(out);

        // 关闭资源
        out.flush();
        out.close();
        excel.close();
    }
    public static void main(String[] args) throws Exception {
        write(); // 在 D 盘中生成 POITest.xlsx 文件，创建名称为 test 的 Sheet 页，同时将内容成功写入。
    }
}
```

读取 Excel 文件中的数据:

```java
public static void read() throws Exception{
    FileInputStream in = new FileInputStream(new File("D:\\POITest.xlsx"));
    // 通过输入流读取指定的Excel文件
    XSSFWorkbook excel = new XSSFWorkbook(in);
    // 获取Excel文件的第1个Sheet页
    XSSFSheet sheet = excel.getSheetAt(0);
    // 获取Sheet页中的最后一行的行号
    int lastRowNum = sheet.getLastRowNum();
    for (int i = 0; i <= lastRowNum; i++) {
        // 获取Sheet页中的行
        XSSFRow titleRow = sheet.getRow(i);
        // 获取行的第2个单元格
        XSSFCell cell1 = titleRow.getCell(1);
        // 获取单元格中的文本内容
        String cellValue1 = cell1.getStringCellValue();
        // 获取行的第3个单元格
        XSSFCell cell2 = titleRow.getCell(2);
        // 获取单元格中的文本内容
        String cellValue2 = cell2.getStringCellValue();
        System.out.println(cellValue1 + " " +cellValue2);
    }
    // 关闭资源
    in.close();
    excel.close();
}
```

****
## 3. 导出运营数据 Excel 报表

在数据统计页面有一个数据导出的按钮，点击该按钮时，就会下载一个文件，这个文件实际上是一个 Excel 形式的文件，文件中主要包含最近 30 日运营相关的数据。
表格的形式已经固定，主要由概览数据和明细数据两部分组成。真正导出这个报表之后，相对应的数字就会填充在表格中，然后就可以进行存档。

表结构如下：

| 运营数据报表 |          |          |          |          |          |
|-----------|----------|----------|----------|----------|----------|
| 概览数据       |
|--------------|----------|----------|----------|----------|----------|
| 营业额    | 订单完成率 | 新增用户数 | 有效订单 | 平均客单价 |          |
| 明细数据 |
|--------------|----------|----------|----------|----------|----------|
| 日期      | 营业额   | 有效订单 | 订单完成率 | 平均客单价 | 新增用户数 |

因为导出的是最近 30 天的运营数据，后端计算即可，所以前端不需要传递任何请求参数；因为报表导出功能本质上是文件下载，服务端会通过输出流将 Excel 文件下载到客户端浏览器，
所以后端也不需要返回数据，当调用到此接口时直接通过 POI 完成写入与下载即可。

Controller 层：

虽然前端没有携带请求参数，但是这个接口的主要作用是导出 Excel 文件并直接将文件返回给前端下载，所以需要通过 HttpServletResponse 来向浏览器发送 Excel 文件流，而不是处理前端传递的参数。

```java
@GetMapping("/export")
@Operation(summary = "导出运营数据报表")
public void export(HttpServletResponse response){
    reportService.exportBusinessData(response);
}
```

Service 层：

```java
@Override
public void exportBusinessData(HttpServletResponse response) {
    // 获取当前系统日期向前推 30 天
    LocalDate begin = LocalDate.now().minusDays(30);
    // 获取当前系统日期向前推 1 天
    LocalDate end = LocalDate.now().minusDays(1);
    // 查询概览运营数据（工作台获取的概览数据），提供给 Excel 模板文件
    BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    // 通过输入流读取模板文件
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
    try {
        // 基于提供好的模板文件创建一个新的 Excel 表格对象
        XSSFWorkbook excel = new XSSFWorkbook(inputStream);
        // 获得 Excel 文件中的一个 Sheet 页
        XSSFSheet sheet = excel.getSheet("Sheet1");
        // 将日期填充到 Excel 文件的第二行的第二个单元格
        sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
        // 获得第 4 行
        XSSFRow row = sheet.getRow(3);
        // 获取单元格
        // 第三个单元格填充营业额
        row.getCell(2).setCellValue(businessData.getTurnover());
        // 第五个单元格填充订单完成率
        row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
        // 第七个单元格填充新增用户
        row.getCell(6).setCellValue(businessData.getNewUsers());
        // 获取第五行
        row = sheet.getRow(4);
        // 第三个单元格填充有效订单
        row.getCell(2).setCellValue(businessData.getValidOrderCount());
        // 第五个单元格填充平均单价
        row.getCell(4).setCellValue(businessData.getUnitPrice());
        // 获取一个月中每天的详细营业数据
        for (int i = 0; i < 30; i++) {
            // 循环遍历每个日期，每次加一天
            LocalDate date = begin.plusDays(i);
            // 获取当日的详细数据
            businessData = workspaceService.getBusinessData(LocalDateTime.of(date,LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
            // 初始数据从第八行开始填充，下一天的数据填充到下一行
            row = sheet.getRow(7 + i);
            // 第二个单元格填充日期
            row.getCell(1).setCellValue(date.toString());
            // 第三个单元格填充当日营业额
            row.getCell(2).setCellValue(businessData.getTurnover());
            // 第四个单元格填充当日有效订单
            row.getCell(3).setCellValue(businessData.getValidOrderCount());
            // 第五个单元格填充当日订单完成率
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            // 第六个单元格填充当日平均单价
            row.getCell(5).setCellValue(businessData.getUnitPrice());
            // 第七个单元格填充新增用户
            row.getCell(6).setCellValue(businessData.getNewUsers());
        }
        // 获取当前 HTTP 响应的输出流
        ServletOutputStream out = response.getOutputStream();
        // 将内存中的 Excel 文件内容写入 ServletOutputStream 输出流，也就是写入 HTTP 响应体中
        excel.write(out);
        // 关闭资源
        out.flush();
        out.close();
        excel.close();
    }catch (IOException e){
        e.printStackTrace();
    }
}
```

****
# 十九、Vue 基础用法

## 1. 基于脚手架创建前端工程

要想基于脚手架创建前端工程，需要具备如下环境要求：

- node.js：前端项目的运行环境
- npm：JavaScript 的包管理工具
- Vue CLI：基于 Vue 进行快速开发的完整系统，实现交互式的项目脚手架

```shell
-- 查看 node 版本号
node -v
v18.20.7
-- 查看 npm 版本
npm -v
10.8.2
-- 查看 Vue CLI 版本
vue --version
@vue/cli 5.0.8
```

使用 Vue CLI 创建前端工程的方式：

- 方式一：vue create 项目名称
- 方式二：vue ui

1、在命令行输入命令 vue ui，启动命令后，Vue CLI 会在本地启动一个 Web 服务器，打开浏览器并访问 http://localhost:8000

```shell
vue ui
```

2、点击“在此创建新项目”按钮，跳转到创建新项目设置页面

3、填写项目名称、选择包管理器为 npm，点击“下一步”按钮

4、选择 Default(Vue 2)，点击"创建项目"按钮，完成项目的创建

工程目录：

```text
# 文件夹
node_modules
public
src
.git
.babelrc
.editorconfig
.eslintrc.js
.gitignore
babel.config.js
package-lock.json
package.json
postcss.config.js
README.md
tailwind.config.js
tsconfig.json
vue.config.js
```

- node_modules：当前项目依赖的js包
- assets：静态资源存放目录
- components：公共组件存放目录
- App.vue：项目的主组件，页面的入口文件
- main.js：整个项目的入口文件
- package.json：项目的配置信息、依赖包管理
- vue.config.js：vue-cli 配置文件

创建好后，进入该项目目录，执行 npm run serve 启动前端工程：

```shell
npm run serve

> demo-1@0.1.0 serve
> vue-cli-service serve

 INFO  Starting development server...
 DONE  Compiled successfully in 6288ms                                                                          

  App running at:
  - Local:   http://localhost:8080/
  - Network: http://192.168.0.111:8080/

  Note that the development build is not optimized.
  To create a production build, run npm run build.
```

然后访问 http://localhost:8080/ 即可看到 Vue 的默认页面，即访问成功。但通常 8080 端口会被后端占用，所以可以在 vue.config.js 中配置前端服务端口号：

```js
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
    port: 7070 // 指定前端服务端口号
  }
})
```

****
## 2. Vue 基本使用

### 2.1 vue 组件

Vue 的组件文件以 .vue 结尾，每个组件由三部分组成：

- `<template>`：组件的结构部分（视图层），用于定义组件的 HTML 结构，也就是页面中将要渲染的内容。
- `<style>`：组件的逻辑部分，用于定义组件的行为和状态，包括数据属性、方法、计算属性、生命周期钩子等。
- `<script>`：组件的样式部分（CSS 样式），为组件添加样式，默认是全局样式；可使用 scoped 属性限制样式只作用于当前组件。

一个完整的 Vue 单文件组件示例：

```vue
<template>
  <div class="hello">
    <h1>{{ message }}</h1>
    <button @click="toggle">切换</button>
    <p v-if="show">显示内容</p>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      message: '欢迎使用 Vue!',
      show: true
    }
  },
  methods: {
    toggle() {
      this.show = !this.show
    }
  }
}
</script>

<style scoped>
.hello {
  padding: 20px;
  background: #f0f0f0;
}
</style>
```

****
### 2.2 文本插值

使用 `{{插值表达式}}` 来绑定 data 方法返回的对象属性，例如：

```vue
<template>
  <div>
    <h1>{{ name }}</h1>
    <h1>{{ age > 60 ? '老年' : '青年' }}</h1>
  </div>
</template>

<script>
  export default {
    data() {
      return { name: '张三', age: 30 };
    }
  }
</script>
```

data() 是一个函数，Vue 组件通过它来定义组件的响应式数据，它返回一个对象，对象中的每个属性会被 Vue 处理成响应式数据，并自动挂载到组件实例上。Vue 框架在内部做了一层代理和响应式绑定，
可以在模板中无需使用 this.name 或 this.age 就能直接访问 data 中的变量。

****
### 2.3 属性绑定

使用 v-bind:xxx（简写为 :xxx）为标签的属性绑定 data 方法中返回的属性，例如：

```vue
<div>
  <div><input type="text" v-bind:value="name"></div>
  <div><input type="text" :value="age"></div>
  <div><img :src="src"/></div>
</div>

<script>
export default {
  export default {
      data () {
          return {
              name: '王五',
              age: 20,
              src: 'https://www.itcast.cn/2018czgw/images/logo2.png'
          };
      }
  }
}
</script>
```

****
### 2.4 事件绑定

使用 v-on:xxx（简写为 @xxx）为元素绑定对应的事件，例如：

```vue
<div>
  <div>
    <input type="button" value="保存" v-on:click="handleSave"/>
    <input type="button" value="保存" @click="handleSave"/><br>
  </div>
</div>

<script>
export default {
  methods: {
    handleSave() {
      alert(this.name)
    }
  }
}
</script>
```

****
### 2.5 双向绑定

使用 v-model 后会将表单输入项和 data 方法中的属性进行绑定，任意一方改变都会同步给另一方，例如：

```vue
<div>
  双向绑定: {{ name }}
  <input type="text" v-model="name" />
  <input type="button" value="改变" @click="handleChange"/>
</div>

<script>
export default {
  methods: {
    handleChange() {
      alert('你输入的是：' + this.name);
    }
  }
}
</script>
```

当在输入框中修改内容时，所有的 name 字段都会同步更新。

****
### 2.6 条件渲染

使用 v-if、v-else、v-else-if 可以根据表达式的值来动态渲染页面元素，例如：

```vue
<div>
  <div v-if="sex === 1">
    男
  </div>
  <div v-else-if="sex === 2">
    女
  </div>
  <div v-else>
    未知
  </div>
</div>

<script>
  export default {
    data() {
      return { sex: 1 }
    }
  }
</script>
```

****
### 2.7 axios

Axios 是一个基于 Promise 的 HTTP 客户端，用于浏览器和 Node.js 中发送 HTTP 请求，在 Vue 项目中经常用于和后端交互。

安装和引入：

1、安装

```shell
npm install axios
```

2、引入

```shell
import axios from 'axios';
```

axios 的 API 列表：

- axios.get(url[, config])
- axios.delete(url[, config])
- axios.head(url[, config])
- axios.options(url[, config])
- axios.post(url[, data[, config]])
- axios.put(url[, data[, config]])
- axios.patch(url[, data[, config]])

参数说明：

- url：请求路径
- data：请求体数据，最常见的是 JSON 格式数据
- config：配置对象，可以设置查询参数、请求头信息
  - params：请求 URL 的查询参数（会自动序列化为 ?key=value） 
  - headers：自定义请求头 
  - timeout：请求超时时间（毫秒） 
  - responseType：响应数据类型，如 'json'、'blob' 等 
  - withCredentials：是否允许跨域请求时携带凭证（cookies）

在使用 axios 时经常会遇到跨域问题，为了解决跨域问题，可以在 vue.config.js 文件中配置代理：

```js
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 7070,
        // 匹配以 /api 开头的请求路径，把该请求转发到目标后端服务器地址
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                // 重写请求路径，将 /api 前缀移除。例如：前端请求 /api/users 会被转发到 http://localhost:8080/users
                pathRewrite: {
                  '^/api': ''
              }
          }
      }
  }
})
```

axios 提供的统一使用方式示例：

```vue
<script>
  export default {
    methods: {
      sendAxios() {
        axios({
          method: 'post', // 发送 post 请求
          url: '/user/12345',
          data: { // 请求体中包含的 JSON 数据
            firstName: 'Fred',
            lastName: 'Flintstone'
          }
        });
      }
    }
  }
</script>
```

```vue
<script>
  export default {
    methods: {
      sendAxios() {
        axios({
          url: '/api/admin/employee/login',
          method:'post',
          data: {
              username:'admin',
            password: '123456'
          }
          // 请求成功后的回调，res 是响应对象
        }).then((res) => {
            // 
            console.log(res.data.data.token)
          axios({
            url: '/api/admin/shop/status',
            method: 'get',
            params: {id: 100},
            headers: {
                token: res.data.data.token
            }
          })
        }).catch((error) => {
            console.log(error)
        })
      }
    }
  }
</script>
```

当使用 Axios 发请求后，返回的响应 res 是一个复杂的对象，大致结构如下：

```vue
{
  data: { // 第一个 .data：Axios 自动封装的响应体内容
    code: 1,
    msg: "登录成功",
    data: { // 第二个 .data：后端业务真正返回的数据字段
      id: 1,
      username: "admin",
      token: "abcdefg123456"
    }
  },
  status: 200, // HTTP 状态码
  statusText: "OK",
  headers: { ... },
  config: { ... },
  request: { ... }
}
```

- `res`：Axios 的完整响应对象
- `res.data`：Axios 自动把 HTTP 响应体提取到 `.data` 属性中，表示整个 JSON 响应
- `res.data.data`：后端返回的 `data` 字段，里面是用户信息、token 等业务数据
- `res.data.data.token`：最终取出的就是后端返回的数据中的 `token` 字段


1、axios 的 post 请求示例：

```vue
<script>
export default {
  methods: {
    sendAxios() {
      axios.post('/api/admin/employee/login',{
        username:'admin',
        password: '123456'
      }).then(res => {
        console.log(res.data)
      }).catch(error => {
        console.log(error.response)
      })
    }
  }
}
</script>
```

2、axios 的 get 请求示例：

```vue
<script>
export default {
  methods: {
    sendAxios() {
      axios.get('/api/admin/shop/status',{
          headers: {
              token: 'xxx.yyy.zzz'
          }
      })
    }
  }
}
</script>
```

3、统一方式：

```vue
<script>
export default {
  methods: {
    sendAxios() {
      axios({
        url: '/api/admin/employee/login',
        method:'post',
        data: {
            username:'admin',
            password: '123456'
        }
      }).then((res) => {
          console.log(res.data.data.token)
          axios({
            url: '/api/admin/shop/status',
            method: 'get',
            params: {id: 100},
            headers: {
                token: res.data.data.token
            }
          })
      }).catch((error) => {
          console.log(error)
      })
    }
  }
}
</script>
```
发送的请求为：http://localhost:7070/api/admin/employee/login ，它会被代理成：http://localhost:8080/admin/employee/login ，
当成功发送登录请求后，就会再次发送一个查询店铺状态的请求，因为所有的请求都要经过 jwt 令牌校验，所以需要获取到登录请求时后端返回的 token

```json
{
  code: 1,
  data: {
    id: 1,
    name: "管理员",
    token: "eyJhbGciOiJIUzI1NiJ9.eyJlbXBJZCI6MSwiZXhwIjoxNzUyOTIzOTc3fQ.EZi7siSt__gINnE6B3i4wF3KU-Yzue-UkT_-DAAuTXI",
    userName: "admin"
  },
  msg: null
}

{
  code: 1,
  data: 1,
  msg: null
}
```

****
## 3. 路由 Vue-Router

### 3.1 路由配置

vue 属于单页面应用，就是根据浏览器路径不同，用不同的视图组件替换这个页面内容。例如：

- /home 显示 Home 组件 
- /about 显示 About 组件

Vue-Router 就是用于管理这种“路径 -> 组件”映射关系的工具

为了能够使用路由功能，在前端项目的入口文件 main.js 中，创建Vue实例时需要指定路由对象：

```js
import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
```

路由的组成：

- VueRouter：路由器，根据路由请求在路由视图中动态渲染对应的视图组件
- `<router-link>`：路由链接组件，浏览器会解析成 `<a>` 标签
- `<router-view>`：路由视图组件，是一个占位符，用来展示与路由路径匹配的视图组件，比如当前路径是 /home，就会把 Home 组件渲染到 <router-view> 中。

```vue
<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
    <!--视图组件展示的位置-->
    <router-view/>
  </div>
</template>
```

```js
import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)
// 维护路由表，某个路由路径对应哪个视图组件
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // 懒加载，只有使用到该组件时才会加载
    component: () => import('../views/AboutView.vue')
  },
  {
    path: '/404',
    component: () => import('../views/404View.vue')
  },
  { // 当上面的路径都没匹配到时，就走这里，进入 404 页面组件
    path: '*',
    redirect: '/404'
  }
]

const router = new VueRouter({
  routes
})

export default router
```

要实现路由跳转，可以通过标签式和编程式两种：

- 标签式：`<router-link to="/about">About</router-link>`
- 编程式：this.$router.push('/about')

而路由的本质就是当请求某些 路径时不要去服务器加载新页面，而是在当前页面中把 `<router-view>` 替换成对应组件的内容，这样某些没存入 session 中的数据就不会因为页面的跳转而导致丢失数据，
例如用户填写完表单并跳转到 About 页后，再次返回填写页面，就会发现输入框中的 name 还在，因为 Vue 没有刷新页面，组件状态仍然保留在内存中。

****
### 3.2 路由嵌套

嵌套路由是指在一个路由组件中，还嵌套显示另一个组件的情况。它允许构建“父子视图”结构，比如：

- 用户中心（父） 
  - 个人信息（子） 
  - 修改密码（子） 
  - 订单记录（子）

每个子页面都有自己的路径、组件，但都在父组件的页面框架下渲染。在 App.vue 视图组件中有 `<router-view>` 标签，其他视图组件可以展示在此（即标签所在地）。
使用 ContainerView.vue 组件可以进行区域划分（分为上、左、右），在右边编写了 `<router-view>` 标签，点击左侧菜单时，可以将对应的子视图组件展示在此。

实现步骤：

1、安装并导入 elementui，实现页面布局（Container 布局容器）

```shell
npm i element-ui -s
```

在 main.js 中导入 elementui：

```js
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
// 全局使用 elementui
Vue.use(ElementUI);

new Vue({
  el: '#app',
  render: h => h(App)
});
```

2、创建布局容器与子视图组件

3、在 src/router/index.js 中配置路由映射规则（嵌套路由配置）

```js
{
  path: '/c',
  component: () => import('../views/container/ContainerView.vue'),
  // 嵌套路由（子路由），对应的组件会展示在当前组件内部
  children: [// 通过 children 属性指定子路由相关信息（path、component）
    {
      path: '/c/p1',
      component: () => import('../views/container/P1View.vue')
    },
    {
      path: '/c/p2',
      component: () => import('../views/container/P2View.vue')
    },
    {
      path: '/c/p3',
      component: () => import('../views/container/P3View.vue')
    }
  ]
}
```

4、在 ContainerView.vue 布局容器视图中添加 `<router-view>`，实现子视图组件展示

```vue
<el-main>
    <router-view/>
</el-main>
```

5、在 ContainerView.vue 布局容器视图中添加 `<router-link>`，实现路由请求

```vue
<el-aside width="200px">
    <router-link to="/c/p1">P1</router-link><br>
    <router-link to="/c/p2">P2</router-link><br>
    <router-link to="/c/p3">P3</router-link><br>
</el-aside>
```

需要注意的是，直接访问 /c 路径的话默认是不展示这些子视图的，只有点击后才会展示，所以可以配置一个重定向，访问 /c 时直接重定向到 /c/p1 即可：

```js
path: '/c',
component: () => import('../views/container/ContainerView.vue'),
redirect: '/c/p1',
```

****
## 4. 状态管理 vuex

### 4.1 定义

- vuex 是一个专为 Vue.js 应用程序开发的状态管理库
- vuex 可以在多个组件之间共享数据，并且共享的数据是响应式的，即数据的变更能及时渲染到模板
- vuex 采用集中式存储管理所有组件的状态

每一个 Vuex 应用的核心就是 store（仓库），store 基本上就是一个容器，它包含着应用中大部分的状态 (state)。Vuex 和单纯的全局对象有以下两点不同：

1. Vuex 的状态存储是响应式的，当 Vue 组件从 store 中读取状态的时候，若 store 中的状态发生变化，那么相应的组件也会相应地得到高效更新。
2. 不能直接改变 store 中的状态，改变 store 中的状态的唯一途径就是显式地提交 (commit) mutation。

- state：状态对象，集中定义各个组件共享的数据
- mutations：类似于一个事件，用于修改共享数据，要求必须是同步函数
- actions：类似于 mutation，可以包含异步操作，通过调用 mutation 来改变共享数据
- getters：类似计算属性，用于从 state 中派生出状态
- modules：将 store 拆分为多个模块，适合大型项目

安装：

```shell
npm install vuex@3 --save
```

****
### 4.2 使用

1、初始化 Vue 实例时需要引入 store 对象：

```js
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
```

2、在 src/store/index.js 文件中集中定义和管理共享数据

```js
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
// 集中管理多个组件共享的数据
export default new Vuex.Store({
  // 集中定义共享数据
  state: {
    name: '未登录游客'
  },
  getters: {
  },
  // 通过当前属性中定义的函数修改共享数据，必须都是同步操作
  mutations: {
  },
  // 通过 actions 调用 mutation，在 actions 中可以进行异步操作
  actions: {
  },
  modules: {
  }
})
```

3、在视图组件中展示共享数据

```vue
<template>
  <div class="hello">
    <!-- $store.state 为固定写法，用于访问共享数据-->
    <h1>欢迎你，{{$store.state.name}}</h1>
  </div>
</template>
```

4、在 mutations 中定义函数，用于修改共享数据

```js
mutations: {
  setName(state,newName) {
    state.name = newName
  }
},
```

5、在视图组件中调用 mutations 中定义的函数

```vue
<input type="button" value="通过 mutations 修改共享数据" @click="handleUpdate">

methods: {
    handleUpdate() {
      // mutations 中定义的函数不能直接调用，必须通过这种方式才能使用
      // setName 为 mutations 中定义的函数名称，zhangsan 为传递的参数
      this.$store.commit('setName', 'zhangsan')
  },
}
```

6、如果在修改共享数据的过程中有异步操作，则需要将异步操作的代码编写在 actions 的函数中

```js
actions: {
  setNameByAxios(context){
    axios({ // 异步请求
      url: '/api/admin/employee/login',
      method: 'post',
      data: {
        username: 'admin',
        password: '123456'
      }
    }).then(res => {
      if(res.data.code == 1){
        // 异步请求后，需要修改共享数据
        // 在 actions 中调用 mutation 中定义的 setName 函数
        context.commit('setName',res.data.data.name)
      }
    })
  }
},
```

7、在视图组件中调用 actions 中定义的函数

```vue
methods: {
  handleCallAction() {
    // 在 actions 中定义的函数不能直接调用，必须通过 this.$store.dispatch('函数名称') 这种方式调用
    this.$store.dispatch('setNameByAxios')
  },
}
```

****
## 5. TypeScript

### 5.1 定义

TypeScript（简称 TS）是微软推出的开源语言，它是 JavaScript 的超集（JS 有的 TS 都有），该类文件扩展名为 ts，可以编译成标准的 JavaScript，并且在编译时进行类型检查。

安装：

```shell
npm install -g typescript
```

1、创建 hello.ts 文件，内容如下：

```ts
// 定义一个函数 hello，并且指定参数类型为 string
function hello(msg:string): void {
      console.log(msg)
}
// 调用上面的函数，传递非 string 类型的参数
hello(123)
```

2、使用 tsc 命令编译 hello.ts 文件

```ts
tsc .\hello.ts

hello.ts:6:7 - error TS2345: Argument of type 'number' is not assignable to parameter of type 'string'.
6 hello(123)
```

可以看到编译报错，提示参数类型不匹配，这说明在编译时 TS 会进行类型检查并阻止编译代码成 JavaScript，
需要注意的是在编译为 JS 文件后类型会被擦除（浏览器无法识别 TypeScript 类型，且类型只是开发阶段的工具，运行时是 JavaScript 在工作），例如：

```ts
function hello(name) {
  console.log("Hello, " + name);
}
```

- TS 属于静态类型编程语言，JS 属于动态类型编程语言
- 静态类型在编译期做类型检查，动态类型在执行期做类型检查
- 对于 JS 来说，需要等到代码执行的时候才能发现错误（晚）
- 对于 TS 来说，在代码编译的时候就可以发现错误（早）
- 支持类型可以提升代码可读性与可维护性，并利用好编译工具的自动提示功能

****
### 5.2 常用类型

基于 TS 进行前端开发时，类型标注的位置有如下 3 个：

- 标注变量
- 标注参数
- 标注返回值

```ts
// 标注变量，指定变量 msg 的类型为 string
let msg: string = 'hello ts !';

// 标注参数和返回值，指定 m2 函数的参数类型为 string，并且返回值也为 string
const m2 = (name: string): string => {
  return name.toLowerCase() + msg;
};
```

#### 1. 基础类型

1、number

```ts
// 用于表示整数或浮点数
let age: number = 25;
let price: number = 9.99;
```

2、string

```ts
// 用于表示文本数据
let name: string = "Alice";
```

3、boolean

```ts
let isLoggedIn: boolean = false;
```

4、null 和 undefined

```ts
let u: undefined = undefined;
let n: null = null;
```

5、any

```ts
// 表示任意类型，关闭类型检查，一般不使用
let data: any = 123;
data = "hello";
data = true;
```

6、unknown

```ts
// 类似 any，但更安全。不能直接进行操作，需先进行类型断言。
let value: unknown = "Hello";
if (typeof value === "string") {
  console.log(value.toUpperCase());
}
```

7、void

```ts
// 通常用于函数无返回值的场景
function log(message: string): void {
  console.log(message);
}
```

8、never

```ts
// 表示永远不会有返回值的函数，如抛异常或无限循环
function error(message: string): never {
  throw new Error(message);
}
```

****
#### 2. 对象类型

1、object

```ts
// 表示非原始类型
let person: object = { name: "Bob", age: 30 };
```

2、接口

```ts
interface User {
  id: number;
  name: string;
  isAdmin?: boolean; // 可选属性
}
// 属性要一一对应，不能多或少
let user: User = { id: 1, name: "Tom" };
```

3、类型别名

```ts
type Point = {
  x: number;
  y: number;
};

let p: Point = { x: 10, y: 20 };
```

定义类与接口并继承与实现它们：

```ts
class User {
  // 类的属性及类型标注
  name: string; 
  // 构造方法（Constructor）
  constructor(name: string) { 
    this.name = name;
  }
  // 类的方法（Method）
  study() { 
    console.log(`${this.name}正在学习`);
  }
}

const u = new User('张三');
console.log(u.name);
u.study();
```

```ts
interface Animal {
  name: string;
  eat(): void;
}

class Bird implements Animal {
  name: string;
  constructor(name: string) {
    this.name = name;
  }
  eat(): void {
    console.log(this.name + ' eat');
  }
}

const b1 = new Bird('杜鹃');
console.log(b1.name);
b1.eat();
```

```ts
// 定义 Parrot 类，并且继承 Bird 类
class Parrot extends Bird {
  say(): void {
    console.log(this.name + ' say hello');
  }
}

const myParrot = new Parrot('Polly');
myParrot.say();
myParrot.eat();
```

****
#### 3. 数组类型

1、普通数组

```ts
let nums: number[] = [1, 2, 3];
let strs: Array<string> = ["a", "b"];
```

2、元组（Tuple）

```ts
// 固定长度和类型顺序的数组
let tuple: [string, number] = ["age", 25];
```

****
#### 4. 其他

1、联合类型（|）

```ts
// 可以为设置的任意一个类型
let input: string | number;
input = "hello";
input = 123;
```

2、类型断言（Type Assertion）

```ts
// someValue 是 unknown，不能直接访问其属性，使用 as string 告诉 TS：“这个值是 string，可以访问它的 length 属性”，有点类似于转型
let someValue: unknown = "abc";
let strLength: number = (someValue as string).length;
```

3、枚举（enum）

```ts
// 自动为元素赋值，从 0 开始
enum Role {
  User, // 0
  Admin, // 1
  SuperAdmin // 2
}

let r: Role = Role.Admin; // 值为 1
```

****






























