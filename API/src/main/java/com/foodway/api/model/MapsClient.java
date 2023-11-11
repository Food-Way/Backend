package com.foodway.api.model;

import com.foodway.api.record.DTOs.GMaps.MapsLongLag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mapsClient", url = "https://maps.googleapis.com/maps/api/geocode/json")
public interface MapsClient {

    @GetMapping("?address={number}+{street},+{city}&key={apiKey}")
//    "50", "Rua+Doutor+Rodrigo+Pereira+Barreto", "São+Paulo", "AIzaSyAzEwtZ4fQ-3qu6McrI5MoleuC8PNJ3F4w"
//    @GetMapping("?address=50+Rua+Doutor+Rodrigo+Pereira+Barreto,+São+Paulo&key=AIzaSyAzEwtZ4fQ-3qu6McrI5MoleuC8PNJ3F4w")
    MapsLongLag getLongLat(@RequestParam String number, @RequestParam String street, @RequestParam String city, @RequestParam String apiKey);
}
