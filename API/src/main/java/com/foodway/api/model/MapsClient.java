package com.foodway.api.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mapsClient", url = "https://maps.googleapis.com/maps/api/geocode/json")
public interface MapsClient {
    @GetMapping("?address={number}+{street},+{city}&key={apiKey}")
    MapsLongLag getLongLat(@RequestParam String number, @RequestParam String street, @RequestParam String city, String apiKey);
}
