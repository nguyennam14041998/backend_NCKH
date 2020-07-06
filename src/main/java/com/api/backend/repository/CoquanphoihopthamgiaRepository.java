package com.api.backend.repository;
import com.api.backend.domain.Coquanphoihopthamgia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coquanphoihopthamgia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoquanphoihopthamgiaRepository extends JpaRepository<Coquanphoihopthamgia, Long>, JpaSpecificationExecutor<Coquanphoihopthamgia> {

}
