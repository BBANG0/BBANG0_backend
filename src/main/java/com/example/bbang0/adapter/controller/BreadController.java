package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.GetBreadResDto;
import com.example.bbang0.application.dto.PatchBreadReqDto;
import com.example.bbang0.application.dto.PostBreadReqDto;
import com.example.bbang0.application.dto.PostBreadResDto;
import com.example.bbang0.application.service.BreadService;
import com.example.bbang0.application.service.JwtService;
import com.example.bbang0.domain.exception.BaseException;
import com.example.bbang0.domain.exception.BaseResponse;
import com.example.bbang0.domain.exception.BaseResponseStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bbang0.domain.exception.BaseResponseStatus.INVALID_BAKERY;

@RestController
@RequestMapping("/breads")
public class BreadController {

    private final BreadService breadService;
    private final JwtService jwtService;

    public BreadController(BreadService breadService, JwtService jwtService){
        this.breadService = breadService;
        this.jwtService = jwtService;
    }


    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostBreadResDto> createBread(@RequestBody PostBreadReqDto postBreadReqDto) {
        try{
            // 빵집 유저 맞는지 확인하는 API을 통해 bakeryid를 미리 알아논다.
            String jwt = jwtService.getUserIdx();
            int bakery_id = breadService.findBakeryIdByUserId(jwt);

            PostBreadResDto postBreadResDto = breadService.createBread(bakery_id, postBreadReqDto);

            return new BaseResponse<>(postBreadResDto);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 빵 리스트 조회
    @ResponseBody
    @GetMapping("/{bakery_id}")
    public BaseResponse<List<GetBreadResDto>> getBread(@PathVariable ("bakery_id") int bakery_id) {
        try{
            List<GetBreadResDto> getBreadRes = breadService.retrieveBreads(bakery_id);
            return new BaseResponse<>(getBreadRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 빵 전체 수정 기능
    @ResponseBody
    @PatchMapping("/{bread_id}")
    public BaseResponse<String> modifyBread(@PathVariable ("bread_id") int bread_id, @RequestBody PatchBreadReqDto patchBreadReq) {
        try{
            String jwt = jwtService.getUserIdx();
            int bakery_id = breadService.findBakeryIdByUserId(jwt);
            if (bakery_id!= patchBreadReq.getBakery_id()){
                throw new BaseException(INVALID_BAKERY);
            }
            breadService.modifyBread(bread_id, patchBreadReq);
            String result = "빵 정보 수정을 완료하였습니다.";

            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 빵 개수만 수정하는 기능
    @ResponseBody
    @PatchMapping("/{bread_id}/count")
    public BaseResponse<String> modifyBreadCount(@PathVariable ("bread_id") int bread_id, @RequestParam int bread_count,PatchBreadReqDto patchBreadReq) {
        try{
            String jwt = jwtService.getUserIdx();
            int bakery_id = breadService.findBakeryIdByUserId(jwt);
            if (bakery_id!= patchBreadReq.getBakery_id()){
                System.out.println("bakery_id "+Integer.toString(bakery_id)+"bakery_id "+Integer.toString(patchBreadReq.getBakery_id()) );
                throw new BaseException(INVALID_BAKERY);
            }
            breadService.modifyBreadCount(bread_id, bread_count);
            String result = "빵 개수 수정을 완료하였습니다.";

            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PatchMapping("/{bread_id}/delete")
    public BaseResponse<String> deleteBread(@PathVariable ("bread_id") int bread_id) {
        try{

            breadService.deleteBread(bread_id);
            String result = "bread_id : " + bread_id + "인 빵을 삭제하였습니다.";

            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
