package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.SoldierRepository;
import mil.af.welcometoarmy.util.AuthChecker;
import mil.af.welcometoarmy.web.dto.soldier.SoldierCreateDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierCreateMultipleDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierUpdateDto;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SoldierService {

    private final PasswordEncoder passwordEncoder;

    private final SoldierRepository soldierRepository;

    private final AuthChecker authChecker;

    @Transactional
    public void save(SoldierCreateDto soldierCreateDto) {
        Soldier soldier = soldierCreateDto.toEntity();
        soldier.setPassword(passwordEncoder.encode(birthdayToString(soldier)));
        soldier.setPoint(0);
        soldier.setAuthority(Authority.ROLE_SOLDIER);
        soldier.setLogInFailCnt(0);

        if (checkDuplication(soldier.getPlatoonNum(), soldier))
            throw new IllegalArgumentException("이미 등록 된 소대번호입니다.");
        soldierRepository.save(soldier);
    }

    @Transactional
    public void saveMultiple(MultipartFile file) throws IOException {

        if (file != null) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            Workbook workbook = null;
            if (extension.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else throw new IOException("엑셀파일만 업로드 해주세요.");

            int sheets = workbook.getNumberOfSheets();
            for (int i=0; i<sheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                rowIterator.next();
                while(rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    SoldierCreateMultipleDto soldierInfo = SoldierCreateMultipleDto.builder()
                            .generation((int) cellIterator.next().getNumericCellValue())
                            .battalion((int)cellIterator.next().getNumericCellValue()+"")
                            .company((int)cellIterator.next().getNumericCellValue()+"")
                            .platoon((int)cellIterator.next().getNumericCellValue()+"")
                            .platoonNum(cellIterator.next().getStringCellValue())
                            .name(cellIterator.next().getStringCellValue())
                            .birthday(cellIterator.next().getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                            .phoneNumber(cellIterator.next().getStringCellValue())
                            .homeTel(cellIterator.next().getStringCellValue())
                            .build();

                    Soldier soldier = soldierInfo.toEntity();
                    soldier.setPassword(passwordEncoder.encode(birthdayToString(soldier)));
                    soldier.setPoint(0);
                    soldier.setAuthority(Authority.ROLE_SOLDIER);
                    soldier.setLogInFailCnt(0);

                    if (!checkDuplication(soldier.getPlatoonNum(), soldier)) soldierRepository.save(soldier);
                }
            }
        }
    }

    @Transactional
    public void update(Long id, SoldierUpdateDto soldierUpdateDto, UserDetails userDetails) {
        Soldier soldier = soldierRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessage.NONE_SOLDIER_MESSAGE));

        Authority authority = authChecker.authCheck(id, userDetails, 1, "수정");

        if (soldierUpdateDto.getCurrentPw() == null) {
            if (soldierUpdateDto.getPassword() == null)
                soldierUpdateDto.setPassword(soldier.getPassword());
            else if (authority == Authority.ROLE_SOLDIER) throw new IllegalArgumentException("현재 비밀번호를 입력해주세요.");
            else {
                soldierUpdateDto.validatePassword();
                soldierUpdateDto.setPassword(passwordEncoder.encode(soldierUpdateDto.getPassword()));}
        } else {
            if (soldierUpdateDto.getCurrentPw().equals(soldierUpdateDto.getPassword()))
                throw new IllegalArgumentException("현재 비밀번호와 변경할 비밀번호가 동일합니다.");
            if (!passwordEncoder.matches(soldierUpdateDto.getCurrentPw(), soldier.getPassword()))
                throw new IllegalArgumentException("잘못된 현재비밀번호 입니다.");
            soldierUpdateDto.validatePassword();
            soldierUpdateDto.setPassword(passwordEncoder.encode(soldierUpdateDto.getPassword()));
        }

        if (checkDuplication(soldier.getPlatoonNum(), soldier))
            throw new IllegalArgumentException("이미 등록 된 아이디입니다.");
        soldier.update(soldierUpdateDto.toEntity());
    }

    public SoldierResponseDto getOne(Long id, UserDetails userDetails) {
        Soldier soldier = soldierRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessage.NONE_SOLDIER_MESSAGE));

        authChecker.authCheck(id, userDetails, 1, "조회");

        return soldier.toDto();
    }

    public Soldier getOneByPlatoonNum(String platoonNum) {
        return soldierRepository.findByPlatoonNum(platoonNum).orElseThrow(() ->
                new IllegalArgumentException(ExceptionMessage.SIGN_IN_FAIL_MESSAGE));
    }

    @Transactional
    public void delete(Long id, UserDetails userDetails) {
        Soldier soldier = soldierRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessage.NONE_SOLDIER_MESSAGE));

        authChecker.authCheck(id, userDetails, 1, "삭제");

        soldierRepository.delete(soldier);
    }

    private boolean checkDuplication(String platoonNum, Soldier soldier) {
        Soldier find = soldierRepository.findByPlatoonNum(platoonNum).orElse(null);
        return find != null && !find.getId().equals(soldier.getId());
    }

    //로그인 실패 카운트 추가
    @Transactional
    public void logInFail(Soldier soldier) {
        if (soldier.getLogInFailCnt() < 5) {
            soldier.setLogInFailCnt(soldier.getLogInFailCnt() + 1);
        }
    }

    public void failCountCheck(Soldier soldier) {
        if (soldier.getLogInFailCnt() >= 5)
            throw new IllegalArgumentException("5회 인증 실패로 계정 사용이 제한 됩니다.<br>" +
                    "패스워드 분실 시 관리자에게 문의해주세요.");
    }

    //로그인 실패 카운트 초기화
    @Transactional
    public void failCntClear(Soldier soldier) {
        soldier.setLogInFailCnt(0);
    }

    private String birthdayToString(Soldier soldier) {
        return soldier.getBirthday().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }
}
