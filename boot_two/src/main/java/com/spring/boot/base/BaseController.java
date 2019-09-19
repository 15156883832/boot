package com.spring.boot.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@Controller
public class BaseController {
    public final static Logger logger = LoggerFactory.getLogger(BaseController.class);
}
