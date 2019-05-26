package at.wrk.fmd.ikarusbackend.controller;

import at.wrk.fmd.ikarusbackend.dto.JSONEventList;
import at.wrk.fmd.ikarusbackend.dto.JSONStaffList;
import at.wrk.fmd.ikarusbackend.dto.JSONStatus;
import at.wrk.fmd.ikarusbackend.model.Staff;
import at.wrk.fmd.ikarusbackend.service.EventService;
import at.wrk.fmd.ikarusbackend.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/public")
public class PublicAPIController {
    private static final Logger LOG = LoggerFactory.getLogger(PublicAPIController.class);

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
    public ResponseEntity<Staff> setStatus(
            @PathVariable(name = "staffId") final long staffId,
            @Valid @RequestBody final JSONStatus status) {
        Staff staff = staffService.findByStaffId(staffId);
        if (staff != null) {
            LOG.debug("Setting staff status of {} to {}", staff.getName(), status.getStatus());
            staff.setStatus(status.getStatus());
            return ResponseEntity.ok(staffService.save(staff));
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}