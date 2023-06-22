package com.thbdesabase.orderservices.endpoint;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/management")
public class ManagementController {

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
