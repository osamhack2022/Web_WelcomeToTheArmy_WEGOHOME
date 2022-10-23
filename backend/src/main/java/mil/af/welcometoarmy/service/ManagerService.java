package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.ManagerRepository;
import mil.af.welcometoarmy.util.AuthChecker;
import mil.af.welcometoarmy.web.dto.manager.ManagerCreateDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerResponseDto;
import mil.af.welcometoarmy.web.dto.manager.ManagerUpdateDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthChecker authChecker;

    @Transactional
    public void save(ManagerCreateDto managerCreateDto) {
        managerCreateDto.passwordTypoCheck();
        managerCreateDto.setPassword(passwordEncoder.encode(managerCreateDto.getPassword()));

        managerRepository.save(managerCreateDto.toEntity());
    }

    @Transactional
    public void update(Long id, ManagerUpdateDto managerUpdateDto, UserDetails userDetails) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_MANAGER_MESSAGE));

        Authority authority = authChecker.authCheck(id, userDetails, 2, "수정");

        if (managerUpdateDto.getCurrentPw() == null) {
            if (managerUpdateDto.getPassword() == null) managerUpdateDto.setPassword(manager.getPassword());
            else if (authority == Authority.ROLE_MANAGER) throw new IllegalArgumentException("현재 비밀번호를 입력해주세요.");
            else {
                managerUpdateDto.validatePassword();
                managerUpdateDto.setPassword(passwordEncoder.encode(managerUpdateDto.getPassword()));
            }
        } else {
            if (managerUpdateDto.getCurrentPw().equals(managerUpdateDto.getPassword()))
                throw new IllegalArgumentException("현재 비밀번호와 변경할 비밀번호가 동일합니다.");
            if (!passwordEncoder.matches(managerUpdateDto.getCurrentPw(), manager.getPassword()))
                throw new IllegalArgumentException("잘못된 현재비밀번호 입니다.");
            managerUpdateDto.validatePassword();
            managerUpdateDto.setPassword(passwordEncoder.encode(managerUpdateDto.getPassword()));
        }

        if (checkDuplication(manager.getManagerId(), manager))
             throw new IllegalArgumentException("이미 등록된 아이디입니다.");
        manager.update(manager);
    }

    public ManagerResponseDto getOne(Long id, UserDetails userDetails) {
        Manager manager = managerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessage.NONE_MANAGER_MESSAGE));

        authChecker.authCheck(id, userDetails, 2, "조회");

        return manager.toDto();
    }

    public List<ManagerResponseDto> getAll() {
        return getDtoList(managerRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    public Manager getOneByManagerId(String managerId) {
        return managerRepository.findByManagerId(managerId).orElseThrow(() ->
                new IllegalArgumentException(ExceptionMessage.SIGN_IN_FAIL_MESSAGE));
    }

    @Transactional
    public void delete(Long id, UserDetails userDetails) {
        Manager manager = managerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessage.NONE_MANAGER_MESSAGE));

        authChecker.authCheck(id, userDetails, 2, "삭제");

        managerRepository.delete(manager);
    }

    private boolean checkDuplication(String managerId, Manager manager) {
        Manager find = managerRepository.findByManagerId(managerId).orElse(null);
        return find != null && !find.getId().equals(manager.getId());
    }

    //로그인 실패 카운트 추가
    @Transactional
    public void logInFail(Manager manager) {
        if (manager.getLogInFailCnt() < 5) {
            manager.setLogInFailCnt(manager.getLogInFailCnt() + 1);
        }
    }

    public void failCountCheck(Manager manager) {
        if (manager.getLogInFailCnt() >= 5)
            throw new IllegalArgumentException("5회 인증 실패로 계정 사용이 제한 됩니다.<br>" +
                    "패스워드 분실 시 담당자에게 문의해주세요.");
    }

    //로그인 실패 카운트 초기화
    @Transactional
    public void failCntClear(Manager manager) {
        manager.setLogInFailCnt(0);
    }

    private List<ManagerResponseDto> getDtoList(List<Manager> managers) {
        List<ManagerResponseDto> list = new ArrayList<>();

        for (Manager manager : managers) {
            list.add(manager.toDto());
        }

        return list;
    }
}
