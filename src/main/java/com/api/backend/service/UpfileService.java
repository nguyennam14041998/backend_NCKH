package com.api.backend.service;

import com.api.backend.service.dto.UpfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Upfile}.
 */
public interface UpfileService {

    /**
     * Save a upfile.
     *
     * @param upfileDTO the entity to save.
     * @return the persisted entity.
     */
    UpfileDTO save(UpfileDTO upfileDTO);

    /**
     * Get all the upfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UpfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" upfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UpfileDTO> findOne(Long id);

    /**
     * Delete the "id" upfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
