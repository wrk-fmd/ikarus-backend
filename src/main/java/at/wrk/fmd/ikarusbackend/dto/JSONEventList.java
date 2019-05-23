package at.wrk.fmd.ikarusbackend.dto;

import at.wrk.fmd.ikarusbackend.model.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JSONEventList {

    private final List<Event> events;

    public JSONEventList(List<Event> events) {
        if (events == null) {
            this.events = new ArrayList<>();
        } else {
            this.events = events;
        }
    }

}