package at.wrk.fmd.ikarusbackend.controller;

import at.wrk.fmd.ikarusbackend.dto.JSONStaffList;
import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.model.Staff;
import at.wrk.fmd.ikarusbackend.service.EventService;
import at.wrk.fmd.ikarusbackend.service.StaffService;
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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/private")
public class PrivateAPIController {

    private final EventService eventService;
    private final StaffService staffService;

    @Autowired
    public PrivateAPIController(final EventService eventService, StaffService staffService) {
        this.eventService = eventService;
        this.staffService = staffService;
    }

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

    @PostMapping(value = "/event/{eventId}/staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> saveStaff(@PathVariable(name = "eventId") long eventId,
                                           @Valid @RequestBody Staff staff) {
        Event event = eventService.findByEventId(eventId);
        if (event == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        staff.setEvent(event);
        if (staff.getId() <= 0) {
            return new ResponseEntity<>(staffService.save(staff), HttpStatus.CREATED);
        }
        return ResponseEntity.ok(staffService.save(staff));
    }

    @GetMapping(value = "/staff/{staffId}")
    public ResponseEntity<Staff> getStaffInfo(@PathVariable(name = "staffId") long staffId) {
        Staff staff = staffService.findByStaffId(staffId);
        if (staff == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(staff);
    }

    @DeleteMapping(value = "/staff/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStaff(@PathVariable(name = "staffId") long staffId) {
        Staff staff = staffService.findByStaffId(staffId);
        if (staff == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        staffService.delete(staff);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/event/{eventId}/staff/import", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONStaffList> importEvent(@PathVariable(name = "eventId") long eventId,
                                                           @RequestParam("staffFiles") MultipartFile[] staffFiles) throws IOException {
        Event event = eventService.findByEventId(eventId);
        if (event == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String section;
        boolean firstLine;
        int lineIndex;
        int lastLineIndex;
        String line;
        String[] values;
        int[] dataIndices;
        Staff currentStaff;
        int created = 0;
        int updated = 0;
        List<Staff> staff = new ArrayList<>();
        for (MultipartFile staffFile : staffFiles) {
            section = staffFile.getOriginalFilename();
            if (section == null) {
                section = "";
            } else {
                section = section.substring(0, section.length() - 4);
            }
            firstLine = true;
            dataIndices = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(staffFile.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    lineIndex = line.indexOf("\"");
                    while(lineIndex >= 0) {
                        lastLineIndex = line.indexOf("\"", lineIndex + 1);
                        if (lastLineIndex > lineIndex) {
                            line = line.substring(0, lineIndex) + line.substring(lineIndex + 1, lastLineIndex).replaceAll(";", ",") + line.substring(lastLineIndex + 1);
                        }
                        lineIndex = line.indexOf("\"");
                    }
                    values = line.split(";");
                    if (firstLine) {
                        for (int i = 0; i < values.length; i++) {
                            switch (values[i]) {
                                case "dNr":
                                case "externalId":
                                    dataIndices[0] = i;
                                    break;
                                case "Nachname":
                                case "name":
                                    dataIndices[1] = i;
                                    break;
                                case "Vorname":
                                    dataIndices[2] = i;
                                    break;
                                case "tel":
                                case "phone":
                                    dataIndices[3] = i;
                                    break;
                                case "Email":
                                case "email":
                                    dataIndices[4] = i;
                                    break;
                                case "Ort":
                                case "startLocation":
                                    dataIndices[5] = i;
                                    break;
                                case "Zeitpunkt":
                                case "startTime":
                                    dataIndices[6] = i;
                                    break;
                                case "Anmerkung":
                                case "comment":
                                    dataIndices[7] = i;
                                    break;
                                case "section":
                                    dataIndices[8] = i;
                                    break;
                                case "status":
                                    dataIndices[9] = i;
                                    break;
                            }
                        }
                        firstLine = false;
                        continue;
                    }
                    currentStaff = new Staff();
                    currentStaff.setSection(section);
                    for (int i = 0; i < values.length; i++) {
                        currentStaff.setExternalId(dataIndices[0] >= 0 ? values[dataIndices[0]] : "");
                        currentStaff.setName(dataIndices[1] >= 0 ? values[dataIndices[1]] + (dataIndices[2] >= 0 ? " " + values[dataIndices[2]] : "") : "");
                        currentStaff.setPhone(dataIndices[3] >= 0 ? values[dataIndices[3]] : "");
                        currentStaff.setEmail(dataIndices[4] >= 0 ? values[dataIndices[4]] : "");
                        currentStaff.setStartLocation(dataIndices[5] >= 0 ? values[dataIndices[5]] : "");
                        try {
                            currentStaff.setStartTime(new Timestamp(Timestamp.valueOf(values[dataIndices[6]]).getTime() + (ZonedDateTime.now().getOffset().getTotalSeconds() * 1000)));
                        } catch (IllegalArgumentException e) {
                            lineIndex = values[dataIndices[6]].indexOf(".");
                            lastLineIndex = values[dataIndices[6]].lastIndexOf(".");
                            String day = values[dataIndices[6]].substring(lineIndex + 1, lastLineIndex);
                            day = day.length() < 2 ? "0" + day : day;
                            String month = values[dataIndices[6]].substring(0, lineIndex);
                            month = month.length() < 2 ? "0" + month : month;
                            lineIndex = values[dataIndices[6]].indexOf(" ");
                            String year = "20" + values[dataIndices[6]].substring(lastLineIndex + 1, lineIndex);
                            String time = values[dataIndices[6]].substring(lineIndex + 1) + ":00";
                            currentStaff.setStartTime(new Timestamp(Timestamp.valueOf(year + "-" + month + "-" + day + " " + time).getTime() + (ZonedDateTime.now().getOffset().getTotalSeconds() * 1000)));
                        }
                        currentStaff.setComment(dataIndices[7] >= 0 ? values[dataIndices[7]] : "");
                        currentStaff.setStatus(dataIndices[8] >= 0 ? Staff.StaffStatus.valueOf(values[dataIndices[8]]) : Staff.StaffStatus.NOT_REGISTERED);
                        currentStaff.setSection(dataIndices[9] >= 0 ? values[dataIndices[9]] : currentStaff.getSection());
                    }
                    if (currentStaff.getName().length() > 2) {
                        if (currentStaff.getExternalId().length() > 0) {
                            Staff savedStaff = staffService.findByEventAndExternalId(event, currentStaff.getExternalId());
                            if (savedStaff != null) {
                                currentStaff.setId(savedStaff.getId());
                                updated++;
                            } else {
                                created++;
                            }
                        } else {
                            created++;
                        }
                        currentStaff.setEvent(event);
                        staffService.save(currentStaff);
                        staff.add(currentStaff);
                    }
                }
            }
        }
        return new ResponseEntity<>(new JSONStaffList(staff), created >= updated ? HttpStatus.CREATED : HttpStatus.OK);
    }

}