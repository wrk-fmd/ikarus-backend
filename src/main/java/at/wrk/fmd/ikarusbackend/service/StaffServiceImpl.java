package at.wrk.fmd.ikarusbackend.service;

import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.model.Staff;
import at.wrk.fmd.ikarusbackend.repository.EventRepository;
import at.wrk.fmd.ikarusbackend.repository.StaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private static final Logger LOG = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final EventRepository eventRepository;
    private final StaffRepository staffRepository;

    @Autowired
    public StaffServiceImpl(final EventRepository eventRepository, final StaffRepository staffRepository) {
        this.eventRepository = eventRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public void delete(final Staff staff) {
        staffRepository.delete(staff);
    }

    @Override
    public Staff findByEventAndExternalId(final Event event, final String externalId) {
        return staffRepository.findByEventAndExternalId(event, externalId).orElse(null);
    }

    @Override
    public Staff findByStaffId(final long staffId) {
        return staffRepository.findById(staffId).orElse(null);
    }

    @Override
    public Staff save(final Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public List<Staff> getStaff(final long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            return event.get().getStaff();
        }

        LOG.debug("Event with ID '{}' was not found.", eventId);
        return new ArrayList<>();
    }

}