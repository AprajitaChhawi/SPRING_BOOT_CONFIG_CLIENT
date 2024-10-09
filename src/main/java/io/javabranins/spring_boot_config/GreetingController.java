package io.javabranins.spring_boot_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class GreetingController {

    @Value("${my.greeting}") //1. one way of using @Value
    private String message;

    @Value("some static string") //2. Putting static value
    private String staticmessage;

    @Value("${my.greeting: default value }") //3. giving a default value so the container doesn't fail
    private String messageWithDefault;

    @Value("${my.list.values}") //4. Taking a list of values
    private List<String> listValues;

    @Autowired
    private DataBaseSettings dataBaseSettings; //Config management bean

    @GetMapping("/greeting")
    public String greeting(){
        return message;
    }

    @GetMapping("/v1/greeting")
    public String greetingwithDefault(){
        return messageWithDefault +staticmessage+ listValues +dataBaseSettings.getConnection()+dataBaseSettings.getHost()+dataBaseSettings.getPort();
    }

    @GetMapping("/v2/greeting")
    public String greetingConfig(){
        return "greeting message " + message + "db connection " + dataBaseSettings.getConnection();
    }

    @Autowired
    private Environment environment;

    @GetMapping("/env")
    public String envDetails(){
        return environment.toString();
    }
}
