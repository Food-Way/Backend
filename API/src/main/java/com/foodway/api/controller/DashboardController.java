package com.foodway.api.controller;

import com.foodway.api.record.DTOs.DashboardDTO;
import com.foodway.api.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/{idEstablishment}")
    @Operation(summary = "Get datas to use in dashboard", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return dashboard data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DashboardDTO> getDashboardData(@PathVariable UUID idEstablishment) {
        return dashboardService.getDashboardData(idEstablishment);
    }
}
