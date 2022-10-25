package mil.af.welcometoarmy.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.service.GalleryService;
import mil.af.welcometoarmy.web.dto.BasicResponse;
import mil.af.welcometoarmy.web.dto.gallery.GalleryCreateDto;
import mil.af.welcometoarmy.web.dto.gallery.GalleryResponseDto;
import mil.af.welcometoarmy.web.dto.gallery.GalleryUpdateDto;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "훈련사진 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gallery")
public class GalleryApiController {

    private final GalleryService galleryService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Request로 들어오는 String을 trim
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping()
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "사진 게시글 생성")
    public ResponseEntity<BasicResponse> createGallery(@Valid GalleryCreateDto galleryCreateDto,
                                                       @RequestPart(value = "file") List<MultipartFile> files, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        galleryService.save(galleryCreateDto, files);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("사진 게시글 생성 완료")
                        .build(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "사진 게시글 정보 조회")
    public ResponseEntity<BasicResponse> readGallery(@PathVariable Long id) {

        GalleryResponseDto dto = galleryService.getOne(id);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("사진 게시글 정보 조회 완료")
                        .data(dto)
                        .build(), HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value = "사진 게시글 전체 정보 조회")
    public ResponseEntity<BasicResponse> readGalleries(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        List<GalleryResponseDto> all = galleryService.getAll(userDetails);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("사진 게시글 전체 정보 조회 완료")
                        .data(all)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
    @ApiOperation(value = "사진 게시글 수정")
    public ResponseEntity<BasicResponse> updateGallery(@PathVariable Long id, @Valid GalleryUpdateDto galleryUpdateDto,
                                                       @RequestPart(value = "file") List<MultipartFile> files,
                                                       @ApiParam(value = "삭제할 파일의 id 리스트") @RequestParam("deleteFileList")
                                                       @Nullable List<Long> filesId, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {throw new
                IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        galleryService.update(id, galleryUpdateDto, filesId, files);

        return new ResponseEntity<>(
                BasicResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("사진 게시글 수정 완료")
                        .build(), HttpStatus.OK);
    }
}
