package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.StatisticsResponse;
import com.tanzeel.galleryvault.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;


    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public StatisticsResponse getStatistics() {

        return statisticsService.getStatistics();
    }
}
