package com.api.backend.service;

import com.api.backend.service.dto.ThanhvienHDDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.ThanhvienHD}.
 */
public interface ThanhvienHDService {

    /**
     * Save a thanhvienHD.
     *
     * @param thanhvienHDDTO the entity to save.
     * @return the persisted entity.
     */
    ThanhvienHDDTO save(ThanhvienHDDTO thanhvienHDDTO);

    /**
     * Get all the thanhvienHDS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThanhvienHDDTO> findAll(Pageable pageable);


    /**
     * Get the "id" thanhvienHD.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThanhvienHDDTO> findOne(Long id);

    /**
     * Delete the "id" thanhvienHD.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
