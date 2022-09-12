package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.service.BakeryService;
import com.example.bbang0.application.service.BreadService;
import com.example.bbang0.application.service.FavoriteService;
import com.example.bbang0.application.service.ReviewService;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bakery")
public class BakeryController {

    private final BakeryService bakeryService;
    private final BreadService breadService;
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;

    public BakeryController(BakeryService bakeryService, BreadService breadService, ReviewService reviewService, FavoriteService favoriteService) {
        this.bakeryService = bakeryService;
        this.breadService = breadService;
        this.reviewService = reviewService;
        this.favoriteService = favoriteService;
    }

    // 하나의 배너를 통한 빵집 페이지
    @GetMapping("/{bakeryId}")
    public ResponseEntity<Map<String, Object>> findById(
            @PathVariable("bakeryId") String bakeryId) throws BaseException {

        Map<String, Object> bakery = new HashMap<>();
        bakery.put("bakeryContent",bakeryService.findById(bakeryId));
        bakery.put("bakeryReview", reviewService.findAll(bakeryId));
        bakery.put("favorite", favoriteService.favoriteOrNot(bakeryId));
        bakery.put("bread", breadService.retrieveBreads(Integer.parseInt(bakeryId)));

        return ResponseEntity.ok().body(bakery);
    }

    // 맵에서 보이는 모든 빵집 페이지

}