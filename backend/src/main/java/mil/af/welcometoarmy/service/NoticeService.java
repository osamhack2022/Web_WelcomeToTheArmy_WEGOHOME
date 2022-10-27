package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.Notice;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.ManagerRepository;
import mil.af.welcometoarmy.repository.NoticeRepository;
import mil.af.welcometoarmy.web.dto.notice.NoticeCreateDto;
import mil.af.welcometoarmy.web.dto.notice.NoticeUpdateDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final ManagerRepository managerRepository;

    @Transactional
    public void save(NoticeCreateDto noticeCreateDto, UserDetails userDetails) {
        Notice notice = noticeCreateDto.toEntity();
        Manager manager = managerRepository.findByManagerId(userDetails.getUsername()).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_MANAGER_MESSAGE));
        notice.setManager(manager);
        noticeRepository.save(notice);
    }
}
