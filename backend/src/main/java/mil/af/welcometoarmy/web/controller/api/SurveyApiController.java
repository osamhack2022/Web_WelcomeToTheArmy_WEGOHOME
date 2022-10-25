package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.SurveyService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.manager.ManagerCreateDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerResponseDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerUpdateDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierCreateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyCreateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyUpdateDto;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "조사전달 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/survey")
public class SurveyApiController {

    private final SurveyService surveyService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping()
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "조사전달 생성")
    public ResponseEntity<BasicResponse> createSurvey(@RequestBody @Valid SurveyCreateDto surveyCreateDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        surveyService.save(surveyCreateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("조사전달 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "조사전달 정보 조회")
    public ResponseEntity<BasicResponse> readSurvey(@PathVariable Long id) {

        SurveyResponseDto dto = surveyService.getOne(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 정보 조회 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value = "조사전달 전체 정보 조회")
    public ResponseEntity<BasicResponse> readSurveys(@RequestParam("loadCompleted") boolean loadCompleted,
                                                     @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        List<SurveyResponseDto> all = surveyService.getAll(loadCompleted, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 전체 정보 조회 완료")
                        .data(all)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "조사전달 수정")
    public ResponseEntity<BasicResponse> updateSurvey(@PathVariable Long id, @RequestBody @Valid SurveyUpdateDto surveyUpdateDto,
                                                      BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        surveyService.update(id, surveyUpdateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 수정 완료")
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "조사전달 삭제")
    public ResponseEntity<BasicResponse> deleteSurvey(@PathVariable Long id) {

        surveyService.delete(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 삭제 완료")
                        .build(), HttpStatus.OK);
    }
}
