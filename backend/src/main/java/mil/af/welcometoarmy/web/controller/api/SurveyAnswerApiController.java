package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.SurveyAnswerService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyUpdateDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerCreateDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerResponseDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerUpdateDto;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = "조사전달 답변 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/survey-answer")
public class SurveyAnswerApiController {

    private final SurveyAnswerService surveyAnswerService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "조사전달 답변 생성")
    public ResponseEntity<BasicResponse> createSurveyAnswer(@ApiParam(value = "조사전달의 id", required = true, example = "0") @PathVariable Long id,
                                                            @RequestBody @Valid SurveyAnswerCreateDto surveyAnswerCreateDto,
                                                            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        surveyAnswerService.save(id, surveyAnswerCreateDto, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("조사전달 답변 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "조사전달 답변 정보 조회")
    public ResponseEntity<BasicResponse> readSurveyAnswer(@PathVariable Long id) {

        SurveyAnswerResponseDto dto = surveyAnswerService.getOne(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 답변 정보 조회 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "조사전달 답변 전체 정보 조회")
    public ResponseEntity<BasicResponse> readSurveyAnswers(@ApiParam(value = "답변 조회할 조사전달의 id", required = true, example = "0")
                                                         @PathVariable Long id) {

        List<SurveyAnswerResponseDto> all = surveyAnswerService.getAll(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 답변 전체 정보 조회 완료")
                        .data(all)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "조사전달 답변 수정")
    public ResponseEntity<BasicResponse> updateSurveyAnswer(@PathVariable Long id, @RequestBody @Valid SurveyAnswerCreateDto surveyAnswerCreateDto,
                                                            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        surveyAnswerService.update(id, surveyAnswerCreateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 답변 수정 완료")
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "조사전달 답변 삭제")
    public ResponseEntity<BasicResponse> deleteSurveyAnswer(@PathVariable Long id) {

        surveyAnswerService.delete(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("조사전달 답변 삭제 완료")
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/excel/{id}")
    @ApiOperation(value = "조사전달 답변 엑셀 추출")
    public void excelDownload(@PathVariable Long id, @ApiIgnore HttpServletResponse response) throws IOException {
        surveyAnswerService.makeExcel(id, response);
    }
}
