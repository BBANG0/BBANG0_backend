package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.PostBreadReqDto;
import com.example.bbang0.application.dto.PostBreadResDto;
import com.example.bbang0.application.service.BreadService;
import com.example.bbang0.domain.exception.BaseException;
import com.example.bbang0.domain.exception.BaseResponse;
import com.example.bbang0.domain.exception.BaseResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/breads")
public class BreadController {
    @Autowired
    private final BreadService breadService;

    public BreadController(BreadService breadService){
        this.breadService = breadService;
    }


    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostBreadResDto> createBread(@RequestBody PostBreadReqDto postBreadReqDto) {
        try{
            // 빵집 유저 맞는지 확인하는 API을 통해 bakeryid를 미리 알아논다.
            int bakery_id = breadService.findBakeryIdByUserId(postBreadReqDto.getUser_id());

            PostBreadResDto postBreadResDto = breadService.createBread(bakery_id, postBreadReqDto);

            return new BaseResponse<>(postBreadResDto);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
