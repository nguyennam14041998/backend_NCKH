package com.api.backend.repository;
import com.api.backend.domain.Donvi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Donvi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonviRepository extends JpaRepository<Donvi, Long>, JpaSpecificationExecutor<Donvi> {

}
