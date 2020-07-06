package com.api.backend.service;

import com.api.backend.service.dto.ChucdanhDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Chucdanh}.
 */
public interface ChucdanhService {

    /**
     * Save a chucdanh.
     *
     * @param chucdanhDTO the entity to save.
     * @return the persisted entity.
     */
    ChucdanhDTO save(ChucdanhDTO chucdanhDTO);

    /**
     * Get all the chucdanhs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChucdanhDTO> findAll(Pageable pageable);


    /**
     * Get the "id" chucdanh.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChucdanhDTO> findOne(Long id);

    /**
     * Delete the "id" chucdanh.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
