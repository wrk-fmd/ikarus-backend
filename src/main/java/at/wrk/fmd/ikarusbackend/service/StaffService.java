package at.wrk.fmd.ikarusbackend.service;

import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.model.Staff;

import java.util.List;

public interface StaffService {

    void delete(Staff staff);
    Staff findByEventAndExternalId(Event event, String externalId);
    Staff findByStaffId(long staffId);
    Staff save(Staff staff);
    List<Staff> getStaff(long eventId);

}