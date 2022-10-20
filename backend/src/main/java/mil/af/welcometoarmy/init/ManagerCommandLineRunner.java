package mil.af.welcometoarmy.init;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.repository.ManagerRepository;
import mil.af.welcometoarmy.web.dto.manager.ManagerCreateDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManagerCommandLineRunner implements CommandLineRunner {

    private final ManagerRepository managerRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String MGR_ID = "admin";
    private static final String PW = "1q2w1q2w!";
    private static final String NAME = "admin";

    @Override
    public void run(String... args) throws Exception {
        Optional<Manager> byAuthority = managerRepository.findByAuthority(Authority.ROLE_ADMINISTRATOR);

        if (!byAuthority.isPresent()) {
            ManagerCreateDto managerCreateDto = ManagerCreateDto.builder()
                    .managerId(MGR_ID)
                    .password(passwordEncoder.encode(PW))
                    .name(NAME)
                    .rank("none")
                    .position("none")
                    .phoneNumber("none")
                    .build();

            Manager manager = managerCreateDto.toEntity();
            manager.setAuthority(Authority.ROLE_ADMINISTRATOR);

            managerRepository.save(manager);
        }
    }
}
