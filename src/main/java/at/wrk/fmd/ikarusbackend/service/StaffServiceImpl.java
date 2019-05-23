package at.wrk.fmd.ikarusbackend.service;

import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.model.Staff;
import at.wrk.fmd.ikarusbackend.repository.EventRepository;
import at.wrk.fmd.ikarusbackend.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public void delete(Staff staff) {
        staffRepository.delete(staff);
    }

    @Override
    public Staff findByEventAndExternalId(Event event, String externalId) {
        return staffRepository.findByEventAndExternalId(event, externalId).orElse(null);
    }

    @Override
    public Staff findByStaffId(long staffId) {
        return staffRepository.findById(staffId).orElse(null);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public List<Staff> getStaff(long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            return event.get().getStaff();
        }
        return new ArrayList<>();
    }

}