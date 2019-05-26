package at.wrk.fmd.ikarusbackend.repository;

import at.wrk.fmd.ikarusbackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByExternalId(String externalId);

}