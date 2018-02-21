package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    public String HELLO;

    public WelcomeController(@Value("${WELCOME_MESSAGE:hello}")String msg ) {
      this.HELLO = msg;
    }

    @GetMapping("/")
    public String sayHello() {
        return HELLO;
    }
}