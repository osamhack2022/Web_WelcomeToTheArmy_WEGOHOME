package mil.af.welcometoarmy.util;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.ManagerRepository;
import mil.af.welcometoarmy.repository.SoldierRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthChecker {

    private final SoldierRepository soldierRepository;
    private final ManagerRepository managerRepository;

    //Entity를 조회, 수정, 삭제 할 시 권한을 체크
    public Authority authCheck(Long targetId, UserDetails userDetails, int targetLevel, String operation) {

        Authority authority = Authority.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());
        boolean authorized = true;

        if (targetLevel > authority.getLevel()) authorized = false;
        else if (targetLevel == authority.getLevel()) {
            Long userId = 0L;
            if (targetLevel == 1) {
                userId = soldierRepository.findByPlatoonNum(userDetails.getUsername()).orElseThrow(() ->
                        new EntityNotFoundException(ExceptionMessage.NONE_SOLDIER_MESSAGE)).getId();
            } else if (targetLevel == 2) {
                userId = managerRepository.findByManagerId(userDetails.getUsername()).orElseThrow(() ->
                        new EntityNotFoundException(ExceptionMessage.NONE_MANAGER_MESSAGE)).getId();
            }

            if (!userId.equals(targetId)) authorized = false;
        }

        if (!authorized) throw new IllegalArgumentException(operation + " 권한이 없습니다.");

        return authority;
    }
}
