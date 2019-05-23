package at.wrk.fmd.ikarusbackend.dto;

import at.wrk.fmd.ikarusbackend.model.Staff;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONStatus {

    private final Staff.StaffStatus status;

    public JSONStatus(Staff.StaffStatus status) {
        this.status = status;
    }

}