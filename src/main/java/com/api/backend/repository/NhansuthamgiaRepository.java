package com.api.backend.repository;
import com.api.backend.domain.Nhansuthamgia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nhansuthamgia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhansuthamgiaRepository extends JpaRepository<Nhansuthamgia, Long>, JpaSpecificationExecutor<Nhansuthamgia> {

}
