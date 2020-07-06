package com.api.backend.repository;
import com.api.backend.domain.Danhsachbaibao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Danhsachbaibao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DanhsachbaibaoRepository extends JpaRepository<Danhsachbaibao, Long>, JpaSpecificationExecutor<Danhsachbaibao> {

}
