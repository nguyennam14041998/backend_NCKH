package com.api.backend.repository;
import com.api.backend.domain.Nhansu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nhansu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhansuRepository extends JpaRepository<Nhansu, Long>, JpaSpecificationExecutor<Nhansu> {

}
