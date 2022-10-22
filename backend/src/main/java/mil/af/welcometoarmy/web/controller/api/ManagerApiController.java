package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.config.security.jwt.JwtTokenProvider;
import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.service.ManagerService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.manager.ManagerCreateDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerLoginDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerResponseDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerUpdateDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierLogInDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "관리자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerApiController {

    private final ManagerService managerService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping()
    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "관리자 생성")
    public ResponseEntity<BasicResponse> createManager(@RequestBody @Valid ManagerCreateDto managerCreateDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        managerService.save(managerCreateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("관리자 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "관리자 정보 조회")
    public ResponseEntity<BasicResponse> readManager(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        ManagerResponseDto dto = managerService.getOne(id, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("관리자 정보 조회 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "관리자 수정")
    public ResponseEntity<BasicResponse> updateManager(@PathVariable Long id, @RequestBody @Valid ManagerUpdateDto managerUpdateDto,
                                                       @ApiIgnore @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        managerService.update(id, managerUpdateDto, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("관리자 수정 완료")
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "관리자 삭제")
    public ResponseEntity<BasicResponse> deleteManager(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        managerService.delete(id, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("관리자 삭제 완료")
                        .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "관리자 로그인")
    public ResponseEntity<BasicResponse> logIn(@RequestBody @Valid ManagerLoginDto managerLoginDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Manager manager = managerService.getOneByManagerId(managerLoginDto.getManagerId());

        if (!passwordEncoder.matches(managerLoginDto.getPassword(), manager.getPassword())) {
            managerService.logInFail(manager);
            managerService.failCountCheck(manager);
            throw new IllegalArgumentException(ExceptionMessage.SIGN_IN_FAIL_MESSAGE);
        }

        managerService.failCountCheck(manager);
        managerService.failCntClear(manager);

        String token = jwtTokenProvider.createToken(manager.getManagerId(), manager.getAuthority());

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("관리자 로그인 완료")
                        .data(token)
                        .build(), HttpStatus.OK);
    }
}
