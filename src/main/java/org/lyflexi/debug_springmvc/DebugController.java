package org.lyflexi.debug_springmvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {
    @GetMapping("/testRequestParam")
    @ResponseBody
    public String testRequestParam(@RequestParam String userName){
        String str ="testNoRequestParam userName:"+userName;
        System.out.println(str);
        return str;
    }
}
