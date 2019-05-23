package at.wrk.fmd.ikarusbackend.repository;

import at.wrk.fmd.ikarusbackend.model.Event;
import at.wrk.fmd.ikarusbackend.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByEventAndExternalId(Event event, String externalId);

}