package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.config.security.jwt.JwtTokenProvider;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.service.SoldierService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.LoginResponseDto;
import mil.af.welcometoarmy.web.dto.soldier.*;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import java.util.List;

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

    @PostMapping()
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
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

    @PostMapping(value = "/multiple")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "훈련병 엑셀로 다중 생성")
    public ResponseEntity<BasicResponse> createMultipleSoldier(@RequestPart(value = "file") MultipartFile file) throws IOException {

        soldierService.saveMultiple(file);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("훈련병 다중 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
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

    @GetMapping()
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "훈련병 전체 정보 조회")
    public ResponseEntity<BasicResponse> readSoldiers() {

        List<SoldierResponseDto> all = soldierService.getAll();

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 정보 조회 완료")
                        .data(all)
                        .build(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
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

    @DeleteMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "훈련병 삭제")
    public ResponseEntity<BasicResponse> deleteSoldier(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        soldierService.delete(id, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 삭제 완료")
                        .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
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
        soldierService.failCntClear(soldier);

        String token = jwtTokenProvider.createToken(soldier.getPlatoonNum(), soldier.getAuthority());
        LoginResponseDto dto = LoginResponseDto.builder()
                .id(soldier.getId())
                .token(token)
                .build();

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("훈련병 로그인 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }
}
