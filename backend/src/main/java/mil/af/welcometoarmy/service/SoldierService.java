package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.repository.SoldierRepository;
import mil.af.welcometoarmy.web.dto.SoldierRegisterDto;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SoldierService {

    private final SoldierRepository soldierRepository;

    @Transactional
    public void save(MultipartHttpServletRequest mtfRequest) throws IOException {
        List<MultipartFile> files = mtfRequest.getFiles("file");

        if (!files.isEmpty()) {
            for (MultipartFile mf : files) {
                String extension = FilenameUtils.getExtension(mf.getOriginalFilename());
                Workbook workbook = null;
                if (extension.equals("xlsx")) {
                    workbook = new XSSFWorkbook(mf.getInputStream());
                } else if (extension.equals("xls")) {
                    workbook = new HSSFWorkbook(mf.getInputStream());
                } else throw new IOException("ONLY_UPLOAD_EXCEL");

               int sheets = workbook.getNumberOfSheets();
               for (int i=0; i<sheets; i++) {
                   Sheet sheet = workbook.getSheetAt(i);

                   Iterator<Row> rowIterator = sheet.iterator();
                   rowIterator.next();
                   while(rowIterator.hasNext()) {
                       Row row = rowIterator.next();

                       Iterator<Cell> cellIterator = row.cellIterator();
                       SoldierRegisterDto soldierInfo = SoldierRegisterDto.builder()
                               .generation((int) cellIterator.next().getNumericCellValue())
                               .battalion(cellIterator.next().getNumericCellValue()+"")
                               .company(cellIterator.next().getNumericCellValue()+"")
                               .platoon(cellIterator.next().getNumericCellValue()+"")
                               .platoonNum(cellIterator.next().getStringCellValue())
                               .name(cellIterator.next().getStringCellValue())
                               .birthday(cellIterator.next().getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                               .build();

                       soldierRepository.save(soldierInfo.toEntity());
                   }
               }
            }
        }
    }
}
