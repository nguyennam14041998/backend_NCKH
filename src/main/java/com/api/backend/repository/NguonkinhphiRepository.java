package com.api.backend.repository;
import com.api.backend.domain.Nguonkinhphi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nguonkinhphi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NguonkinhphiRepository extends JpaRepository<Nguonkinhphi, Long>, JpaSpecificationExecutor<Nguonkinhphi> {

}
