package com.wissen.tresata.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for test.
 *
 * @author Anushka Saxena, Shubham Patel
 */
@RestController
public class TestController {
    @GetMapping("/")
    public String test() {
        return "dune api is running";
    }
}
