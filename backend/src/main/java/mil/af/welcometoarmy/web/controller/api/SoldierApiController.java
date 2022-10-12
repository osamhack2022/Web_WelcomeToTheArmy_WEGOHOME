package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.SoldierService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.soldier.SoldierCreateDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierUpdateDto;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "훈련병 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/soldier")
public class SoldierApiController {

    private final SoldierService soldierService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/create")
    @ApiOperation(value = "훈련병 생성")
    public ResponseEntity<BasicResponse> createSoldier(@RequestBody @Valid SoldierCreateDto soldierCreateDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        soldierService.save(soldierCreateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("훈련병 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/createMultiple")
    @ApiOperation(value = "훈련병 엑셀로 다중 생성")
    public ResponseEntity<BasicResponse> createMultipleSoldier(@RequestPart(value = "file") MultipartFile file) throws IOException {

        soldierService.saveMultiple(file);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("훈련병 다중 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/update/{id}")
    @ApiOperation(value = "훈련병 정보 수정")
    public ResponseEntity<BasicResponse> updateSoldier(@PathVariable Long id, @RequestBody @Valid SoldierUpdateDto soldierUpdateDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        soldierService.update(id, soldierUpdateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 정보 수정 완료")
                        .build(), HttpStatus.OK);
    }
}
