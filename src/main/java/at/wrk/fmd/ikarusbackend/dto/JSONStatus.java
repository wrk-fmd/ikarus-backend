package at.wrk.fmd.ikarusbackend.dto;

import at.wrk.fmd.ikarusbackend.model.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JSONStatus {

    private Staff.StaffStatus status;

}