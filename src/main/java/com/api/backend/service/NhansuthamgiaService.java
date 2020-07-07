package com.api.backend.service;

import com.api.backend.domain.Nhansuthamgia;
import com.api.backend.service.dto.NhansuthamgiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Nhansuthamgia}.
 */
public interface NhansuthamgiaService {

    /**
     * Save a nhansuthamgia.
     *
     * @param nhansuthamgiaDTO the entity to save.
     * @return the persisted entity.
     */
    NhansuthamgiaDTO save(NhansuthamgiaDTO nhansuthamgiaDTO);

    /**
     * Get all the nhansuthamgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NhansuthamgiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nhansuthamgia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nhansuthamgia> findOne(Long id);

    /**
     * Delete the "id" nhansuthamgia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
