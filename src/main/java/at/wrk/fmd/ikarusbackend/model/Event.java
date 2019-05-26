package at.wrk.fmd.ikarusbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Event_ID", nullable = false, unique = true, updatable = false)
    private long id;
    @Column(name = "External_ID")
    private String externalId;
    @NotNull
    @NotEmpty
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "Location", columnDefinition = "TEXT")
    private String location;
    @Column(name = "Start_Time")
    private Timestamp start;
    @Column(name = "End_Time")
    private Timestamp end;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Staff> staff;

}