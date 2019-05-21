package at.wrk.fmd.ikarusbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString(exclude = "event")
@Table(name = "Staff")
public class Staff {

    public enum StaffStatus {
        NOT_REGISTERED,
        REGISTERED,
        REGISTERED_WITH_MATERIAL,
        DEREGISTERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Staff_ID", nullable = false, unique = true, updatable = false)
    private long id;
    @Column(name = "External_ID")
    private String externalId;
    @NotBlank
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "E_Mail")
    private String email;
    @Column(name = "Start_Location", columnDefinition = "TEXT")
    private String startLocation;
    @Column(name = "Start_Time")
    private Timestamp startTime;
    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;
    @Column(name = "Section")
    private String section;
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private StaffStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Event_ID")
    private Event event;

}