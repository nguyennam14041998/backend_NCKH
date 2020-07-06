package com.api.backend.repository;
import com.api.backend.domain.Linhvuc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Linhvuc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinhvucRepository extends JpaRepository<Linhvuc, Long>, JpaSpecificationExecutor<Linhvuc> {

}
