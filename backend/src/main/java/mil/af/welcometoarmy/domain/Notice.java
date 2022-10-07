package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Range range;

    @NotNull
    private int generation;

    private String battalion;

    private String company;

    private String platoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID", nullable = false)
    private Manager manager;

    public void setManager(Manager manager) {
        if (this.manager != null)
            this.manager.getNoticeList().remove(this);
        if (manager == null) this.manager = null;
        else {
            this.manager = manager;
            if (manager.getNoticeList() == null) {
                List<Notice> list = new ArrayList<>();
                list.add(this);
                manager.setNoticeList(list);
            } else manager.getNoticeList().add(this);
        }
    }
}
