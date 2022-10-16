package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.config.security.jwt.JwtTokenProvider;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.service.SoldierService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.soldier.SoldierCreateDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierLogInDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierUpdateDto;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "훈련병 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/soldier")
public class SoldierApiController {

    private final SoldierService soldierService;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

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

    @PostMapping(value = "/read/{id}")
    @ApiOperation(value = "훈련병 정보 조회")
    public ResponseEntity<BasicResponse> readSoldier(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        SoldierResponseDto dto = soldierService.getOne(id, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 정보 조회 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/update/{id}")
    @ApiOperation(value = "훈련병 정보 수정")
    public ResponseEntity<BasicResponse> updateSoldier(@PathVariable Long id, @RequestBody @Valid SoldierUpdateDto soldierUpdateDto,
                                                       @ApiIgnore @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        soldierService.update(id, soldierUpdateDto, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 정보 수정 완료")
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "훈련병 삭제")
    public ResponseEntity<BasicResponse> deleteSoldier(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        soldierService.delete(id, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 삭제 완료")
                        .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/logIn")
    @ApiOperation(value = "훈련병 로그인")
    public ResponseEntity<BasicResponse> logIn(@RequestBody @Valid SoldierLogInDto soldierLogInDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Soldier soldier = soldierService.getOneByPlatoonNum(soldierLogInDto.getPlatoonNum());

        if (!passwordEncoder.matches(soldierLogInDto.getPassword(), soldier.getPassword())) {
            soldierService.logInFail(soldier);
            soldierService.failCountCheck(soldier);
            throw new IllegalArgumentException(ExceptionMessage.SIGN_IN_FAIL_MESSAGE);
        }

        soldierService.failCountCheck(soldier);
        soldier.setLogInFailCnt(0);

        String token = jwtTokenProvider.createToken(soldier.getPlatoonNum(), soldier.getAuthority());

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 로그인 완료")
                        .data(token)
                        .build(), HttpStatus.OK);
    }
}
