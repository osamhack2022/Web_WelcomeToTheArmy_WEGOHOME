package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.ScheduleService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleCreateDto;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleResponseDto;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleUpdateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
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

@Api(tags = "일정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleApiController {

    private final ScheduleService scheduleService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping()
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "일정 생성")
    public ResponseEntity<BasicResponse> createSchedule(@RequestBody @Valid ScheduleCreateDto scheduleCreateDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        scheduleService.save(scheduleCreateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("일정 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "일정 정보 조회")
    public ResponseEntity<BasicResponse> readSchedule(@PathVariable Long id) {

        ScheduleResponseDto dto = scheduleService.getOne(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("일정 정보 조회 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value = "일정 전체 정보 조회")
    public ResponseEntity<BasicResponse> readSchedules(@RequestParam(value = "date", required = false) String date,
                                                       @ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        List<ScheduleResponseDto> all = scheduleService.getAll(date, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("일정 전체 정보 조회 완료")
                        .data(all)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "일정 수정")
    public ResponseEntity<BasicResponse> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleUpdateDto scheduleUpdateDto,
                                                      BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        scheduleService.update(id, scheduleUpdateDto);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("일정 수정 완료")
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "일정 삭제")
    public ResponseEntity<BasicResponse> deleteSchedule(@PathVariable Long id) {

        scheduleService.delete(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("일정 삭제 완료")
                        .build(), HttpStatus.OK);
    }
}
