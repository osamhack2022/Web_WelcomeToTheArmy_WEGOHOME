package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.NoticeService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.notice.NoticeCreateDto;
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

@Api(tags = "공지사항 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeApiController {

    private final NoticeService noticeService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping()
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "공지사항 생성")
    public ResponseEntity<BasicResponse> createNotice(@RequestBody @Valid NoticeCreateDto noticeCreateDto,
                                                      @ApiIgnore @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        noticeService.save(noticeCreateDto, userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("공지사항 생성 완료")
                        .build(), HttpStatus.CREATED);
    }
}
