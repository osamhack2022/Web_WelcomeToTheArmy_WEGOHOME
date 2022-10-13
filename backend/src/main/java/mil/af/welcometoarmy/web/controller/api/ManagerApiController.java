package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.ManagerService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.manager.ManagerCreateDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerUpdateDto;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/create")
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

    @PostMapping("/update/{id}")
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
}
