package com.foodway.api.apiclient;

import com.foodway.api.apiclient.entities.SimpleMail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "simpleMailClient", url = "http://54.90.124.165:80")
public interface SimpleMailClient {

    @PostMapping("{uri}")
    void aaa(@PathVariable String uri, @RequestBody SimpleMail body);

}
