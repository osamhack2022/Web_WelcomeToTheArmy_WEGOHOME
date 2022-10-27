package mil.af.welcometoarmy.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.Survey;
import mil.af.welcometoarmy.domain.SurveyAnswer;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.SoldierRepository;
import mil.af.welcometoarmy.repository.SurveyAnswerRepository;
import mil.af.welcometoarmy.repository.SurveyRepository;
import mil.af.welcometoarmy.web.dto.survey.QuestionDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import mil.af.welcometoarmy.web.dto.survey_answer.AnswerDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerCreateDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerResponseDto;
import mil.af.welcometoarmy.web.dto.survey_answer.SurveyAnswerUpdateDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyAnswerService {

    private final SurveyRepository surveyRepository;

    private final SurveyAnswerRepository surveyAnswerRepository;

    private final SoldierRepository soldierRepository;

    @Transactional
    public void save(Long id, SurveyAnswerCreateDto surveyAnswerCreateDto, UserDetails userDetails) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE));
        validateSurvey(survey);
        Soldier soldier = soldierRepository.findByPlatoonNum(userDetails.getUsername()).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SOLDIER_MESSAGE));
        if (survey.getSurveyAnswers().stream().anyMatch(s -> Objects.equals(s.getSoldier().getId(), soldier.getId())))
            throw new IllegalArgumentException("이미 답변하신 조사전달입니다.");
        SurveyAnswer surveyAnswer = surveyAnswerCreateDto.toEntity();
        surveyAnswer.setSurvey(survey);
        surveyAnswer.setSoldier(soldier);

        List<QuestionDto> questions = new Gson().fromJson(survey.getQuestions(), new TypeToken<List<QuestionDto>>(){}.getType());
        for (QuestionDto questionDto : questions) {
            if (questionDto.getType().equals("객관식")) {
                String answer = surveyAnswerCreateDto.getAnswers().get(questionDto.getId().intValue()).getAnswer();
                int[] counts = questionDto.getCounts();
                counts[questionDto.getOptions().indexOf(answer)]++;
                questionDto.setCounts(counts);
            }
        }
        survey.setQuestions(new Gson().toJson(questions));
        surveyAnswerRepository.save(surveyAnswer);
    }

    @Transactional
    public void update(Long id, SurveyAnswerUpdateDto surveyAnswerUpdateDto) {
        SurveyAnswer surveyAnswer = surveyAnswerRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_ANSWER_MESSAGE));
        Survey survey = surveyAnswer.getSurvey();
        validateSurvey(survey);

        List<QuestionDto> questions = new Gson().fromJson(survey.getQuestions(), new TypeToken<List<QuestionDto>>(){}.getType());
        List<AnswerDto> answers = new Gson().fromJson(surveyAnswer.getAnswers(), new TypeToken<List<AnswerDto>>(){}.getType());
        for (QuestionDto questionDto : questions) {
            if (questionDto.getType().equals("객관식")) {
                String origAnswer = answers.get(questionDto.getId().intValue()).getAnswer();
                String newAnswer = surveyAnswerUpdateDto.getAnswers().get(questionDto.getId().intValue()).getAnswer();
                int[] counts = questionDto.getCounts();
                counts[questionDto.getOptions().indexOf(origAnswer)]--;
                counts[questionDto.getOptions().indexOf(newAnswer)]++;
                questionDto.setCounts(counts);
            }
        }
        survey.setQuestions(new Gson().toJson(questions));
        surveyAnswer.update(surveyAnswerUpdateDto.toEntity());
    }

    public SurveyAnswerResponseDto getOne(Long id) {
        return surveyAnswerRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_ANSWER_MESSAGE)).toDto();
    }

    public List<SurveyAnswerResponseDto> getAll(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE));
        return getDtoList(survey.getSurveyAnswers());
    }

    @Transactional
    public void delete(Long id) {
        SurveyAnswer surveyAnswer = surveyAnswerRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_ANSWER_MESSAGE));
        Survey survey = surveyAnswer.getSurvey();

        List<QuestionDto> questions = new Gson().fromJson(survey.getQuestions(), new TypeToken<List<QuestionDto>>(){}.getType());
        List<AnswerDto> answers = new Gson().fromJson(surveyAnswer.getAnswers(), new TypeToken<List<AnswerDto>>(){}.getType());
        for (QuestionDto questionDto : questions) {
            if (questionDto.getType().equals("객관식")) {
                String origAnswer = answers.get(questionDto.getId().intValue()).getAnswer();
                int[] counts = questionDto.getCounts();
                counts[questionDto.getOptions().indexOf(origAnswer)]--;
                questionDto.setCounts(counts);
            }
        }
        survey.setQuestions(new Gson().toJson(questions));
        survey.getSurveyAnswers().remove(surveyAnswer);
        surveyAnswerRepository.delete(surveyAnswer);
    }

    private List<SurveyAnswerResponseDto> getDtoList(List<SurveyAnswer> surveyAnswers) {
        List<SurveyAnswerResponseDto> list = new ArrayList<>();

        for (SurveyAnswer surveyAnswer : surveyAnswers) {
            list.add(surveyAnswer.toDto());
        }

        return list;
    }

    private void validateSurvey(Survey survey) {
        LocalDateTime now = LocalDateTime.now();
        if (survey.getStartDate().isAfter(now)) throw new IllegalArgumentException("조사전달이 아직 시작하지 않았습니다.");
        if (survey.getEndDate().isBefore(now)) throw new IllegalArgumentException("조사전달이 마감 되었습니다.");
    }

    public void makeExcel(Long id, HttpServletResponse response) throws IOException {
        try(Workbook wb = new XSSFWorkbook()) {
            Survey survey = surveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE));
            if (survey.getSurveyAnswers().isEmpty()) throw new EntityNotFoundException("답변이 없습니다.");
            //sheet 작성
            Sheet sheet = wb.createSheet(survey.getTitle());
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            //Header style
            Font headerFont = wb.createFont();
            headerFont.setFontHeightInPoints((short)11);
            headerFont.setFontName("맑은 고딕");
            headerFont.setBold(true);

            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            //body style
            Font bodyFont = wb.createFont();
            bodyFont.setFontHeightInPoints((short)11);
            bodyFont.setFontName("맑은 고딕");

            CellStyle bodyStyle = wb.createCellStyle();
            bodyStyle.setFont(bodyFont);
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);


            //header 작성
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(1);
            cell.setCellValue("소대번호");
            cell.setCellStyle(headerStyle);
            cell = row.createCell(2);
            cell.setCellValue("이름");
            cell.setCellStyle(headerStyle);
            List<QuestionDto> questions = new Gson().fromJson(survey.getQuestions(), new TypeToken<List<QuestionDto>>(){}.getType());
            for (int i = 3; i < questions.size()+3; i++) {
                cell = row.createCell(i);
                cell.setCellValue(questions.get(i-1).getTitle());
                cell.setCellStyle(headerStyle);
            }
            cell = row.createCell(questions.size()+3);
            cell.setCellValue("답변시간");
            cell.setCellStyle(headerStyle);

            //body 작성
            for (int i = 0; i < survey.getSurveyAnswers().size(); i++) {
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                Soldier soldier = survey.getSurveyAnswers().get(i).getSoldier();
                cell.setCellValue(i+1);
                cell.setCellStyle(bodyStyle);
                List<AnswerDto> answers = new Gson().fromJson(survey.getSurveyAnswers().get(i).getAnswers(),
                        new TypeToken<List<AnswerDto>>(){}.getType());
                cell = row.createCell(1);
                cell.setCellValue(soldier.getPlatoonNum());
                cell.setCellStyle(bodyStyle);
                cell = row.createCell(2);
                cell.setCellValue(soldier.getName());
                cell.setCellStyle(bodyStyle);
                for (int j = 3; j < questions.size()+3; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue(answers.get(j-3).getAnswer());
                    cell.setCellStyle(bodyStyle);
                }
                cell = row.createCell(questions.size()+3);
                cell.setCellValue(survey.getSurveyAnswers().get(i).getCreatedDate());
                cell.setCellStyle(bodyStyle);
            }

            //열 너비 지정
            for (int i=0; i< questions.size()+1; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+256);
            }

            //타입과 파일명 지정
            String title = survey.getTitle() + "답변.xlsx";
            String encodedFileName = new String(title.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

            response.setContentType("ms-vnd/excel; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            //Excel File Output
            wb.write(response.getOutputStream());
        }
    }
}
