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









