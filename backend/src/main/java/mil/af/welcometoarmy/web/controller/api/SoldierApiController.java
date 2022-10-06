package mil.af.welcometoarmy.web.controller.api;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.SoldierService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

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

    @PostMapping("/register")
    public ResponseEntity<BasicResponse> soldierRegister(MultipartHttpServletRequest mtfRequest) throws IOException {
        soldierService.save(mtfRequest);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("SOLDIER_REGISTRATION_COMPLETED")
                        .build(), HttpStatus.CREATED);
    }
}
