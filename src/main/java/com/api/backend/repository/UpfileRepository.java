package com.api.backend.repository;
import com.api.backend.domain.Upfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Upfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpfileRepository extends JpaRepository<Upfile, Long>, JpaSpecificationExecutor<Upfile> {

}
