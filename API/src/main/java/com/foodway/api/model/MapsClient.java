package com.foodway.api.model;

import com.foodway.api.record.RequestUserEstablishment;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mapsClient", url = "https://maps.googleapis.com/maps/api/geocode/json?address=")
public interface MapsClient {
    @GetMapping("{number}+{street}+{city}&key={apiKey}")
    MapsLongLag getLongLat(@RequestParam String number, @RequestParam String street, @RequestParam String city, String apiKey);
}
