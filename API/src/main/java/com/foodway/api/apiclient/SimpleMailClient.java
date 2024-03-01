package com.foodway.api.apiclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "simpleMailClient", url = "http://localhost:80")
public interface SimpleMailClient {

    @PostMapping("{uri}")
    void aaa(@PathVariable String uri, @RequestBody SimpleMail body);

}
