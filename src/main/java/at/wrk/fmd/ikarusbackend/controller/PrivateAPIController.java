package at.wrk.fmd.ikarusbackend.controller;

import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.service.EventService;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/private")
public class PrivateAPIController {

    @Autowired
    private EventService eventService;

    @PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> saveEvent(@Valid @RequestBody Event event) {
        if (event.getId() <= 0) {
            return new ResponseEntity<>(eventService.save(event), HttpStatus.CREATED);
        }
        return ResponseEntity.ok(eventService.save(event));
    }

    @GetMapping(value = "/event/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getEventInfo(@PathVariable(name = "eventId") long eventId) {
        Event event = eventService.findByEventId(eventId);
        if (event == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping(value = "/event/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "eventId") long eventId) {
        Event event = eventService.findByEventId(eventId);
        if (event == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        eventService.delete(event);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/event/import", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> importEvent(@RequestParam("eventFile") MultipartFile eventFile) throws IOException, ParserException, ParseException {
        CalendarBuilder calendarBuilder = new CalendarBuilder();
        Calendar calendar;
        Component component;
        Property currentProperty;
        Event event = new Event();
        try {
            calendar = calendarBuilder.build(eventFile.getInputStream());
            component = (Component)calendar.getComponents().get(0);
            currentProperty = component.getProperty("DESCRIPTION");
            if (currentProperty == null) {
                event.setDescription("");
            } else {
                String description = currentProperty.getValue();
                int idIndex = description.indexOf("ID: \"");
                if (idIndex >= 0) {
                    int endIndex = description.indexOf("\"", idIndex + 5);
                    event.setExternalId(description.substring(idIndex + 5, endIndex));
                    description = description.substring(0, idIndex) + description.substring(endIndex + 1);
                }
                event.setDescription(description);
            }
        } catch (ParserException e) {
            String eventFileContent = new String(eventFile.getBytes());
            int eventIdStart = eventFileContent.indexOf("&AmbulanceNr=");
            event.setExternalId(URLDecoder.decode(eventFileContent.substring(eventIdStart + 13, eventFileContent.indexOf("&", eventIdStart + 13)), StandardCharsets.UTF_8.toString()));
            int descriptionStart = eventFileContent.indexOf("DESCRIPTION:");
            if (descriptionStart > 0) {
                descriptionStart = eventFileContent.indexOf("\n", descriptionStart + 12) + 1;
                String description = eventFileContent.substring(descriptionStart);
                eventFileContent = eventFileContent.substring(0, descriptionStart - 1) + ": ";
                int tempIndex = description.lastIndexOf("Wo: ");
                String location = null;
                if (tempIndex > 0) {
                    if (description.indexOf("\n", tempIndex + 4) > 0) {
                        location = description.substring(tempIndex + 4, description.indexOf("\n", tempIndex + 4)).trim();
                    } else {
                        location = description.substring(tempIndex + 4).trim();
                    }
                }
                tempIndex = description.lastIndexOf("Wann:") > tempIndex ? tempIndex : description.lastIndexOf("Wann:");
                if (tempIndex > 0) {
                    description = description.substring(0, tempIndex).trim();
                }
                eventFileContent += description.replaceAll("\n", " ") + (location == null ? "" : "\nLOCATION:" + location);
            }
            eventFileContent += "\nEND:VEVENT\nEND:VCALENDAR";
            calendar = calendarBuilder.build(new ByteArrayInputStream(eventFileContent.getBytes()));
            component = (Component)calendar.getComponents().get(0);
            currentProperty = component.getProperty("DESCRIPTION");
            event.setDescription(currentProperty == null ? "" : currentProperty.getValue());
        }
        event.setName(component.getProperty("SUMMARY").getValue());
        currentProperty = component.getProperty("LOCATION");
        event.setLocation(currentProperty == null ? "" : currentProperty.getValue());
        currentProperty = component.getProperty("DTSTART");
        event.setStart(currentProperty == null ? null : new Timestamp(new DateTime(currentProperty.getValue()).getTime() + (ZonedDateTime.now().getOffset().getTotalSeconds() * 1000)));
        currentProperty = component.getProperty("DTEND");
        event.setEnd(currentProperty == null ? null : new Timestamp(new DateTime(currentProperty.getValue()).getTime() + (ZonedDateTime.now().getOffset().getTotalSeconds() * 1000)));
        if (event.getExternalId() != null && event.getExternalId().length() > 0) {
            Event currentEvent = eventService.findByExternalId(event.getExternalId());
            if (currentEvent != null) {
                event.setId(currentEvent.getId());
                return ResponseEntity.ok(eventService.save(event));
            }
        }
        return new ResponseEntity<>(eventService.save(event), HttpStatus.CREATED);
    }

}