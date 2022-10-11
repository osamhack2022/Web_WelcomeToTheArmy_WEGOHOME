package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.repository.SoldierRepository;
import mil.af.welcometoarmy.web.dto.SoldierRegisterDto;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Iterator;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SoldierService {

    private final PasswordEncoder passwordEncoder;

    private final SoldierRepository soldierRepository;

    @Transactional
    public void save(MultipartFile file) throws IOException {

        if (file != null) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            Workbook workbook = null;
            if (extension.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
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
                            .battalion((int)cellIterator.next().getNumericCellValue()+"")
                            .company((int)cellIterator.next().getNumericCellValue()+"")
                            .platoon((int)cellIterator.next().getNumericCellValue()+"")
                            .platoonNum(cellIterator.next().getStringCellValue())
                            .name(cellIterator.next().getStringCellValue())
                            .birthday(cellIterator.next().getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                            .build();

                    Soldier soldier = soldierInfo.toEntity();
                    soldier.setPassword(passwordEncoder.encode(soldier.getPlatoonNum()));

                    soldierRepository.save(soldier);
                }
            }
        }
    }


}
