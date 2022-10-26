package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.repository.NoticeRepository;
import mil.af.welcometoarmy.web.dto.notice.NoticeCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void save(NoticeCreateDto noticeCreateDto) {
        noticeRepository.save(noticeCreateDto.toEntity());
    }
}
