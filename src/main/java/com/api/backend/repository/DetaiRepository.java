package com.api.backend.repository;
import com.api.backend.domain.Detai;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Detai entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetaiRepository extends JpaRepository<Detai, Long>, JpaSpecificationExecutor<Detai> {

}
