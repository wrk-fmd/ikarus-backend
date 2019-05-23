package at.wrk.fmd.ikarusbackend.controller;

import at.wrk.fmd.ikarusbackend.dto.JSONEventList;
import at.wrk.fmd.ikarusbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicAPIController {

    private final EventService eventService;

    @Autowired
    public PublicAPIController(final EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONEventList> getEvents() {
        return ResponseEntity.ok(new JSONEventList(eventService.getEvents()));
    }

}