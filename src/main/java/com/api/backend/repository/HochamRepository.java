package com.api.backend.repository;
import com.api.backend.domain.Hocham;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hocham entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HochamRepository extends JpaRepository<Hocham, Long>, JpaSpecificationExecutor<Hocham> {

}
