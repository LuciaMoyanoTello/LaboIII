package video2.number.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import video2.number.entities.MatchEntity;
@Repository
public interface MatchRepository extends JpaRepository<MatchEntity,Long> {

}
