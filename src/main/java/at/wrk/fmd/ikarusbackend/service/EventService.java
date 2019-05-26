package at.wrk.fmd.ikarusbackend.service;

import at.wrk.fmd.ikarusbackend.model.Event;

import java.util.List;

public interface EventService {

    void delete(Event event);
    Event findByEventId(long eventId);
    Event findByExternalId(String externalId);
    Event save(Event event);
    List<Event> getEvents();

}