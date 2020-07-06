package com.api.backend.service;

import com.api.backend.service.dto.NoidungdanhgiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.api.backend.domain.Noidungdanhgia}.
 */
public interface NoidungdanhgiaService {

    /**
     * Save a noidungdanhgia.
     *
     * @param noidungdanhgiaDTO the entity to save.
     * @return the persisted entity.
     */
    NoidungdanhgiaDTO save(NoidungdanhgiaDTO noidungdanhgiaDTO);

    /**
     * Get all the noidungdanhgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoidungdanhgiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" noidungdanhgia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoidungdanhgiaDTO> findOne(Long id);

    /**
     * Delete the "id" noidungdanhgia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
