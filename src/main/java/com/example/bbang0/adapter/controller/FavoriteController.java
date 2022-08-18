package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.Favorite.FavoriteResDto;
import com.example.bbang0.application.service.FavoriteService;
import com.example.bbang0.application.service.JwtService;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final JwtService jwtService;

    public FavoriteController(FavoriteService favoriteService, JwtService jwtService) {
        this.favoriteService = favoriteService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    public List<FavoriteResDto> findAllByUserId(
            @RequestParam(value="bakery") int bakeryId
    ) throws BaseException {
        String userIdxByJwt = jwtService.getUserIdx();
        return favoriteService.findAllByUserId(userIdxByJwt);
    }

    @PostMapping("")
    public String createFavorite(
            @RequestParam(value="bakery") String bakeryId
    ) throws BaseException {
        return favoriteService.create(bakeryId);
    }

    @DeleteMapping ("/{favoriteId}")
    public String deleteFavorite(
            @PathVariable("favoriteId") int favoriteId,
            @RequestParam(value="bakery") String bakeryId
    ) throws BaseException {
        return favoriteService.delete(favoriteId,bakeryId);
    }
}
