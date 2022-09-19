package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.GetBreadResDto;
import com.example.bbang0.application.dto.PatchBreadReqDto;
import com.example.bbang0.application.dto.PostBreadReqDto;
import com.example.bbang0.application.dto.PostBreadResDto;
import com.example.bbang0.application.service.BreadService;
import com.example.bbang0.domain.exception.BaseException;
import com.example.bbang0.domain.exception.BaseResponse;
import com.example.bbang0.domain.exception.BaseResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"breads"})
@RestController
@RequestMapping("/breads")
public class BreadController {
    @Autowired
    private final BreadService breadService;

    public BreadController(BreadService breadService){
        this.breadService = breadService;
    }


    @ApiOperation(value = "빵 추가", notes= "새로운 빵 정보를 입력하면 해당 유저 아이디를 검사하여 빵집을 찾고, 빵집에 빵 등록")
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

    @ApiOperation(value="베이커리의 모든 빵 조회", notes= "해당 베이커리의 모든 빵을 조회")
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


    @ApiOperation(value= "빵 모든 정보 수정", notes="해당 빵의 모든 정보를 수정")
    @ResponseBody
    @PatchMapping("/{bread_id}")
    public BaseResponse<String> modifyBread(@PathVariable ("bread_id") int bread_id, @RequestBody PatchBreadReqDto patchBreadReq) {
        try{
            breadService.modifyBread(bread_id, patchBreadReq);
            String result = "빵 정보 수정을 완료하였습니다.";

            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ApiOperation(value="빵 개수만 수정", notes="파라미터를 통해 해당 빵의 수정할 빵 개수를 입력 받고 수정")
    @ResponseBody
    @PatchMapping("/{bread_id}/count")
    public BaseResponse<String> modifyBreadCount(@PathVariable ("bread_id") int bread_id, @RequestParam int bread_count) {
        try{
            breadService.modifyBreadCount(bread_id, bread_count);
            String result = "빵 개수 수정을 완료하였습니다.";

            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ApiOperation(value="빵 삭제", notes= "해당 빵을 빵집에서 삭제")
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
