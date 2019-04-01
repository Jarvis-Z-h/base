package com.voidstar.base.config;

import com.voidstar.base.component.MyLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//使用WebMvcConfigurerAdapter可以拓展springmvc的功能
//做一些页面的映射
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       // super.addViewControllers(registry);
        //配置一个视图映射
        //浏览器发送一个/atguigu请求，来到login页面
        registry.addViewController("/smelt").setViewName("login");
    }

//所有的WebMvcConfigurerAdapter组件会一起起作用
    @Bean    //讲组件注册到容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter=new WebMvcConfigurerAdapter(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
               // super.addViewControllers(registry);
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/smelt.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("base");
               /* registry.addViewController("/users.html").setViewName("user_manager1");*/
            }
        };
        return adapter;
    }

//区域解析器
 /*  @Bean
    public LocaleResolver localeResolver(){
        return new MyLocalResolver();
    }*/

}
