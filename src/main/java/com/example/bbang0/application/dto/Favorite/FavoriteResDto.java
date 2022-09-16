package com.example.bbang0.application.dto.Favorite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FavoriteResDto {
    private int favorite_id;
    private String favorite_user_id;
    private String favorite_bakery_id;
}
