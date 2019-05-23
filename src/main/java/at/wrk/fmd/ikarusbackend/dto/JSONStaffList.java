package at.wrk.fmd.ikarusbackend.dto;

import at.wrk.fmd.ikarusbackend.model.Staff;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JSONStaffList {

    private final List<Staff> staff;

    public JSONStaffList(List<Staff> staff) {
        if (staff == null) {
            this.staff = new ArrayList<>();
        } else {
            this.staff = staff;
        }
    }

}