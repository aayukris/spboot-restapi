package com.example.restful_web_services.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String helloworld(){
        return "hello world";
    }

    @GetMapping("/helloworld-bean/{name}")
    public HelloWorldBean helloworldbean(@PathVariable String name)
    {
        return new HelloWorldBean("Hello world "+name);

    }
}
