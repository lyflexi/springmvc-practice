package org.lyflexi.debug_springmvc.originalmvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.lyflexi.debug_springmvc.originalmvc.service.HelloService;

@Controller
public class HelloController {
	
	@Autowired
	HelloService helloService;
	
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello(){
		String hello = helloService.sayHello("tomcat..");
		return hello;
	}
	

	@RequestMapping("/suc")
	public String success(){
		//相当于找/WEB-INF/views/success.jsp
		return "success";
	}
	

}
