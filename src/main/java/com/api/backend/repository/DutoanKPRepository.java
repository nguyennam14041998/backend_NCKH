package com.api.backend.repository;
import com.api.backend.domain.DutoanKP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DutoanKP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DutoanKPRepository extends JpaRepository<DutoanKP, Long>, JpaSpecificationExecutor<DutoanKP> {

}
