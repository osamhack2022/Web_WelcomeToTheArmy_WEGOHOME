package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.SoldierService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/register")
    @ApiOperation(value = "훈련병 등록")
    public ResponseEntity<BasicResponse> soldierRegister(
            @RequestPart(value = "file") MultipartFile file
    ) throws IOException {

        soldierService.save(file);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("SOLDIER_REGISTRATION_COMPLETED")
                        .build(), HttpStatus.CREATED);
    }
}
