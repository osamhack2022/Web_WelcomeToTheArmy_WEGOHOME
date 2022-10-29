package mil.af.welcometoarmy.util;

import mil.af.welcometoarmy.web.dto.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class FileHandler {

    public static final String BASE_PATH = System.getProperty("user.dir") + File.separator + "uploads";

    //파일 저장
    public List<FileInfo> saveFile(List<MultipartFile> fileList, String category, int generation, String belong) {
        if (fileList == null || fileList.isEmpty())   {
            return Collections.emptyList();}
        String battalion = belong.substring(0, 1) + "대대";
        String company = belong.substring(1, 2) + "중대";
        String platoon = belong.substring(2, 3) + "소대";

        String savePath = BASE_PATH + File.separator + category + File.separator + generation + File.separator + battalion +
                File.separator + company + File.separator + platoon + File.separator;
        String fileName = "";
        List<FileInfo> dtoList = new ArrayList<>();

        if (!new File(savePath).exists()) {
            try {
                new File(savePath).mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        try {
            for (MultipartFile mf : fileList) {
                String origFileName = mf.getOriginalFilename();
                String fileExtension = getFileExtension(origFileName);
                isValidExtension(fileExtension);
                fileName = fileNameGenerator(new MD5Generator(origFileName).toString(), fileExtension);
                String filePath = savePath + fileName;

                FileInfo fileInfo = new FileInfo();
                fileInfo.setOrigFileName(origFileName);
                fileInfo.setFileName(fileName);
                fileInfo.setFilePath(filePath);
                fileInfo.setFileSize(byteCalculation(mf.getSize()));
                dtoList.add(fileInfo);

                mf.transferTo(new File(filePath));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return dtoList;
    }

    //서버에 저장된 파일 삭제
    public void delete(String filePath) {
        try {
            File targetFile = new File(filePath);
            if(!targetFile.exists()) throw new FileNotFoundException();
            if(targetFile.isDirectory()) throw new FileNotFoundException();
            else {
                Files.delete(targetFile.toPath());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private String fileNameGenerator(String fileName, String fileExtension) {
        UUID uuid = UUID.randomUUID();
        fileName += uuid + "." + fileExtension;
        return fileName;
    }

    private String getFileExtension(String origFileName) {
        int pos = origFileName.lastIndexOf(".");
        return origFileName.substring(pos+1);
    }

    private String byteCalculation(Long size) {
        String retFormat = "0";


        String[] s = {"bytes", "KB", "MB", "GB", "TB", "PB"};

        if (size != 0L) {
            int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
            DecimalFormat df = new DecimalFormat("#,###.##");
            double ret = (size / Math.pow(1024, idx));
            retFormat = df.format(ret) + s[idx];
        } else {
            retFormat += s[0];
        }

        return retFormat;
    }

    //파일 확장자 체크
    private void isValidExtension(String fileExtension) {
        String pattern = "^(?i)bmp|gif|jpg|png|jfif$";

        if (!Pattern.matches(pattern, fileExtension))
            throw new IllegalArgumentException("첨부 가능 확장자가 아닙니다.");
    }

}
