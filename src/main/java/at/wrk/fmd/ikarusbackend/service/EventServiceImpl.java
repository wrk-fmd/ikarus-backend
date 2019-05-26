package at.wrk.fmd.ikarusbackend.service;

import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void delete(final Event event) {
        eventRepository.delete(event);
    }

    @Override
    public Event findByEventId(final long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Override
    public Event findByExternalId(final String externalId) {
        return eventRepository.findByExternalId(externalId).orElse(null);
    }

    @Override
    public Event save(final Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

}