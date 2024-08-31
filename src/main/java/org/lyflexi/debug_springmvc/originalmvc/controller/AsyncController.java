package org.lyflexi.debug_springmvc.originalmvc.controller;

import org.lyflexi.debug_springmvc.originalmvc.queue.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;


@Controller
public class AsyncController {


	@ResponseBody
	@RequestMapping("/createOrder")
	public DeferredResult<Object> createOrder(){

		DeferredResult<Object> deferredResult = new DeferredResult<>((long)3000, "create fail...");
		//由于tomcat线程无法创建业务场景中的订单，但是tomcat线程可以把创建订单的消息发送给消息中间件
		DeferredResultQueue.save(deferredResult);
		return deferredResult;
	}


	@ResponseBody
	@RequestMapping("/create")
	public String create(){
		//创建订单
		String order = UUID.randomUUID().toString();
		DeferredResult<Object> deferredResult = DeferredResultQueue.get();
		deferredResult.setResult(order);
		return "success===>"+order;
	}

	/**
	 * 1、控制器返回Callable
	 * 2、Spring异步处理，将Callable 提交到 TaskExecutor 使用一个隔离的线程进行执行
	 * 3、DispatcherServlet和所有的Filter退出web容器的线程，但是response 保持打开状态；
	 * 4、Callable返回结果，SpringMVC将请求重新派发给容器，恢复之前的处理，唤醒DispatcherServlet，因此每一次请求preHandle会触发两次
	 * 5、根据Callable返回的结果。SpringMVC继续进行视图渲染流程等（从收请求-视图渲染）。


	 一次请求：
	  preHandle.../springmvc_annotation/async01
      主线程开始...Thread[http-nio-8080-exec-4,5,main]==>1565836350257
      主线程结束...Thread[http-nio-8080-exec-4,5,main]==>1565836350258
		=========DispatcherServlet及所有的Filter退出线程============================

		================等待Callable执行==========
       副线程开始...Thread[MvcAsync1,5,main]==>1565836350269
       ······
       副线程开始...Thread[MvcAsync1,5,main]==>1565836352269
		================Callable执行完成==========

		================再次收到之前重发过来的请求========
		preHandle.../springmvc-annotation/async01
		postHandle...目标方法就不用再次执行了，（因为Callable的之前的返回值就是目标方法的返回值）
		afterCompletion...

	 可以看到，异步mvc场景下，普通的拦截器不能够拦截目标方法，因此就需要异步的拦截器（略了）
		异步的拦截器:
			1）、原生API的AsyncListener
			2）、SpringMVC：实现AsyncHandlerInterceptor；
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/async01")
	public Callable<String> async01(){
		System.out.println("主线程开始..."+Thread.currentThread()+"==>"+System.currentTimeMillis());

		Callable<String> callable = ()->{
			System.out.println("副线程开始..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
			Thread.sleep(2000);
            System.out.println("······");
			System.out.println("副线程结束..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
			return "Callable<String> async01()";
		};

		System.out.println("主线程结束..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
		return callable;
	}

}
