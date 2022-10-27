package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Gallery;
import mil.af.welcometoarmy.domain.Image;
import mil.af.welcometoarmy.domain.Survey;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.GalleryRepository;
import mil.af.welcometoarmy.repository.ImageRepository;
import mil.af.welcometoarmy.util.AuthChecker;
import mil.af.welcometoarmy.util.FileHandler;
import mil.af.welcometoarmy.web.dto.FileInfo;
import mil.af.welcometoarmy.web.dto.gallery.GalleryCreateDto;
import mil.af.welcometoarmy.web.dto.gallery.GalleryResponseDto;
import mil.af.welcometoarmy.web.dto.gallery.GalleryUpdateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    private final ImageRepository imageRepository;

    private final FileHandler fileHandler;

    private final AuthChecker authChecker;

    @Transactional
    public void save(GalleryCreateDto galleryCreateDto, List<MultipartFile> files) {
        Gallery gallery = galleryRepository.save(galleryCreateDto.toEntity());


        saveFiles(files, gallery);
    }

    @Transactional
    public void update(Long id, GalleryUpdateDto galleryUpdateDto, List<Long> deleteFilesId, List<MultipartFile> files) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_GALLERY_MESSAGE));
        gallery.update(galleryUpdateDto.toEntity());

        List<Image> deleteFileList = new ArrayList<>();

        if (deleteFilesId != null)
            deleteFilesId.forEach(i -> deleteFileList.add(imageRepository.findById(i).orElseThrow(() -> new
                    EntityNotFoundException("해당 파일이 없습니다."))));

        deleteFiles(deleteFileList);

        saveFiles(files, gallery);
    }

    public GalleryResponseDto getOne(Long id) {
        return galleryRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_GALLERY_MESSAGE)).toDto();
    }

    public String getPath(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_IMAGE_MESSAGE)).getFilePath();
    }

    public List<GalleryResponseDto> getAll(UserDetails userDetails) {
        String belong = authChecker.getBelong(userDetails);
        int generation = authChecker.getGeneration(userDetails);

        String battalion = belong.substring(0, 1);
        String company = belong.substring(1, 2);
        String platoon = belong.substring(2, 3);

        List<Gallery> galleries;
        if (generation == 0) galleries = galleryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        else galleries = galleryRepository.findAllByGeneration(generation, Sort.by(Sort.Direction.DESC, "id"));
        if (!battalion.equals("0")) {
            galleries = galleries.stream().filter(s -> s.getBelong().substring(0, 1).equals(battalion)).collect(Collectors.toList());

            if (!company.equals("0")) {
                galleries = galleries.stream().filter(s -> s.getBelong().substring(1, 2).equals(company) ||
                        s.getBelong().charAt(1) == '0').collect(Collectors.toList());

                if (!platoon.equals("0")) {
                    galleries = galleries.stream().filter(s -> s.getBelong().substring(2, 3).equals(platoon) ||
                            s.getBelong().charAt(2) == '0').collect(Collectors.toList());
                }
            }
        }
        return getDtoList(galleries);
    }

    @Transactional
    public void delete(Long id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_GALLERY_MESSAGE));
        galleryRepository.delete(gallery);
    }

    private List<GalleryResponseDto> getDtoList(List<Gallery> galleries) {
        List<GalleryResponseDto> list = new ArrayList<>();

        for (Gallery gallery : galleries) {
            list.add(gallery.toDto());
        }

        return list;
    }

    private void saveFiles(List<MultipartFile> files, Gallery gallery) {
        List<FileInfo> fileInfoList = fileHandler.saveFile(files, "gallery", gallery.getGeneration(), gallery.getBelong());

        if (!fileInfoList.isEmpty()) {
            for (FileInfo fileInfo : fileInfoList) {
                Image image = fileInfo.toImage();
                image.setGallery(gallery);

                imageRepository.save(image);
            }
        }
    }

    private void deleteFiles(List<Image> deleteFileList) {
        for (Image image : deleteFileList) {
            image.clear();
            fileHandler.delete(image.getFilePath());
            imageRepository.delete(image);
        }
    }
}
