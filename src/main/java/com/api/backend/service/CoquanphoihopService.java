package com.api.backend.service;

import com.api.backend.service.dto.CoquanphoihopDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Coquanphoihop}.
 */
public interface CoquanphoihopService {

    /**
     * Save a coquanphoihop.
     *
     * @param coquanphoihopDTO the entity to save.
     * @return the persisted entity.
     */
    CoquanphoihopDTO save(CoquanphoihopDTO coquanphoihopDTO);

    /**
     * Get all the coquanphoihops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoquanphoihopDTO> findAll(Pageable pageable);


    /**
     * Get the "id" coquanphoihop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoquanphoihopDTO> findOne(Long id);

    /**
     * Delete the "id" coquanphoihop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
