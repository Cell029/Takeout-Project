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


