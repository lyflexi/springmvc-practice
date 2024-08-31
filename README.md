# springmvc-practice
springmvc核心原理：
1. web容器在启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
2. 加载这个文件指定的类SpringServletContainerInitializer
3. spring的应用一启动会加载感兴趣的WebApplicationInitializer接口的下的所有组件(@HandlesTypes指定了WebApplicationInitializer)，并且为WebApplicationInitializer组件创建对象（组件不是接口，不是抽象类）

WebApplicationInitializer接口旗下三层抽象类：

1. AbstractContextLoaderInitializer：创建根容器；createRootApplicationContext()；
2. AbstractDispatcherServletInitializer：
   - 创建一个web的ioc容器；createServletApplicationContext();
   - 创建了DispatcherServlet；createDispatcherServlet()；
   - 将创建的DispatcherServlet添加到ServletContext中；
   - getServletMappings();
3. AbstractAnnotationConfigDispatcherServletInitializer：注解方式配置的DispatcherServlet初始化器
   - 创建根容器：重写了createRootApplicationContext()
   - getRootConfigClasses();传入一个配置类
   - 创建web的ioc容器： 重写了createServletApplicationContext();
   - 获取配置类；getServletConfigClasses();

springmvc最佳实践方式：
1. 以注解方式来启动SpringMVC；
2. 继承AbstractAnnotationConfigDispatcherServletInitializer；
3. 实现抽象方法指定DispatcherServlet的配置信息；


高阶，如何定制SpringMVC；
1. @EnableWebMvc:开启SpringMVC定制配置功能；等效于xml版本的<mvc:annotation-driven/>；
2. 配置组件（视图解析器、视图映射、静态资源映射、拦截器。。。） 
   - 需要 impl WebMvcConfigurer  实现所有方法 不可取
   - 需要继承 extends WebMvcConfigurerAdapter



			
	