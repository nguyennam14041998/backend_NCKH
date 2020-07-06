package com.api.backend.repository;
import com.api.backend.domain.DutoanKPCT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DutoanKPCT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DutoanKPCTRepository extends JpaRepository<DutoanKPCT, Long>, JpaSpecificationExecutor<DutoanKPCT> {

}
