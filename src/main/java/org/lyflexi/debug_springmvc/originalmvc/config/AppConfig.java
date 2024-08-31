package org.lyflexi.debug_springmvc.originalmvc.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

//SpringMVC只扫描Controller；子容器
//要想满足只扫描，还需要禁用默认的过滤规则useDefaultFilters=false ；
@ComponentScan(value= "org.lyflexi.debug_springmvc.originalmvc",includeFilters={
		@Filter(type=FilterType.ANNOTATION,classes={Controller.class})
},useDefaultFilters=false)
@EnableWebMvc
//public class AppConfig  extends WebMvcConfigurerAdapter  {
//WebMvcConfigurerAdapter该抽象类从Spring5.0，也就是SpringBoot2.0后，已被弃用。替代方案幽有两种：
//1.实现WebMvcConfigurer接口
//2.继承WebMvcConfigurationSupport类
public class AppConfig  implements WebMvcConfigurer  {
	//实现WebMvcConfigurerAdapter，相当于手写mvc.xml，来定制mvc

	//定制
	
	//视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		//默认所有的jsp页面都从 /WEB-INF/ 下找
//		registry.jsp();
		//改成从/WEB-INF/views/下找jsp
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	//静态资源访问
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		//将SpringMVC处理不了的静态资源请求交给tomcat；比如png图片 等效于xml时代的<mvc:default-servlet-handler/>
		configurer.enable();
	}
	
	//拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
//		super.addInterceptors(registry);
		registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
	}
}
