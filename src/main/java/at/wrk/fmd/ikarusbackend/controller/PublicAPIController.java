package at.wrk.fmd.ikarusbackend.controller;

import at.wrk.fmd.ikarusbackend.dto.JSONEventList;
import at.wrk.fmd.ikarusbackend.dto.JSONStaffList;
import at.wrk.fmd.ikarusbackend.dto.JSONStatus;
import at.wrk.fmd.ikarusbackend.model.Staff;
import at.wrk.fmd.ikarusbackend.service.EventService;
import at.wrk.fmd.ikarusbackend.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/public")
public class PublicAPIController {

    private final EventService eventService;
    private final StaffService staffService;

    @Autowired
    public PublicAPIController(final EventService eventService, final StaffService staffService) {
        this.eventService = eventService;
        this.staffService = staffService;
    }

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONEventList> getEvents() {
        return ResponseEntity.ok(new JSONEventList(eventService.getEvents()));
    }

    @GetMapping(value = "/staff/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONStaffList> getStaff(@PathVariable(name = "eventId") long eventId) {
        return ResponseEntity.ok(new JSONStaffList(staffService.getStaff(eventId)));
    }

    @PostMapping(value = "/staff/status/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> setStatus(@PathVariable(name = "staffId") long staffId,
                                           @Valid @RequestBody JSONStatus status) {
        Staff staff = staffService.findByStaffId(staffId);
        if (staff != null) {
            staff.setStatus(status.getStatus());
            return ResponseEntity.ok(staffService.save(staff));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}