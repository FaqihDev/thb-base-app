package com.thbdesabase.orderservices.endpoint;

import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/admin")
public class AdminController {

    @GetMapping
    public String get(){
        return "GET: : management endpoint";
    }

    @PostMapping
    public String post(){
        return "GET: : management endpoint";
    }

    @PutMapping
    public String put(){
        return "GET: : management endpoint";
    }

    @DeleteMapping
    public String delete(){
        return "GET: : management endpoint";
    }

}
