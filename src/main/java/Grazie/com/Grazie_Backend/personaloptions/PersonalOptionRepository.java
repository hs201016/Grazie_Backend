package Grazie.com.Grazie_Backend.personaloptions;

import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalOptionRepository extends JpaRepository<PersonalOptions, Long> {
}
