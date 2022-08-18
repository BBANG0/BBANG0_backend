package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.service.FavoriteService;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("")
    public String createFavorite(
            @RequestParam(value="bakery") String bakeryId
    ) throws BaseException {
        return favoriteService.create(bakeryId);
    }

    @DeleteMapping ("/{favoriteId}")
    public String deleteFavorite(
            @PathVariable("favoriteId") String favoriteId,
            @RequestParam(value="bakery") String bakeryId
    ) throws BaseException {
        return favoriteService.delete(favoriteId,bakeryId);
    }
}
