package com.api.backend.web.rest;

import com.api.backend.domain.Chunhiem;
import com.api.backend.service.ChunhiemService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.ChunhiemDTO;
import com.api.backend.service.dto.ChunhiemCriteria;
import com.api.backend.service.ChunhiemQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.api.backend.domain.Chunhiem}.
 */
@RestController
@RequestMapping("/api")
public class ChunhiemResource {

    private final Logger log = LoggerFactory.getLogger(ChunhiemResource.class);

    private static final String ENTITY_NAME = "chunhiem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChunhiemService chunhiemService;

    private final ChunhiemQueryService chunhiemQueryService;

    public ChunhiemResource(ChunhiemService chunhiemService, ChunhiemQueryService chunhiemQueryService) {
        this.chunhiemService = chunhiemService;
        this.chunhiemQueryService = chunhiemQueryService;
    }

    /**
     * {@code POST  /chunhiems} : Create a new chunhiem.
     *
     * @param chunhiemDTO the chunhiemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chunhiemDTO, or with status {@code 400 (Bad Request)} if the chunhiem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chunhiems")
    public ResponseEntity<ChunhiemDTO> createChunhiem(@RequestBody ChunhiemDTO chunhiemDTO) throws URISyntaxException {
        log.debug("REST request to save Chunhiem : {}", chunhiemDTO);
        if (chunhiemDTO.getId() != null) {
            throw new BadRequestAlertException("A new chunhiem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChunhiemDTO result = chunhiemService.save(chunhiemDTO);
        return ResponseEntity.created(new URI("/api/chunhiems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chunhiems} : Updates an existing chunhiem.
     *
     * @param chunhiemDTO the chunhiemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chunhiemDTO,
     * or with status {@code 400 (Bad Request)} if the chunhiemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chunhiemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chunhiems")
    public ResponseEntity<ChunhiemDTO> updateChunhiem(@RequestBody ChunhiemDTO chunhiemDTO) throws URISyntaxException {
        log.debug("REST request to update Chunhiem : {}", chunhiemDTO);
        if (chunhiemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChunhiemDTO result = chunhiemService.save(chunhiemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chunhiemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chunhiems} : get all the chunhiems.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chunhiems in body.
     */
    @GetMapping("/chunhiems")
    public ResponseEntity<List<Chunhiem>> getAllChunhiems(ChunhiemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Chunhiems by criteria: {}", criteria);
        Page<Chunhiem> page = chunhiemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /chunhiems/count} : count all the chunhiems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/chunhiems/count")
    public ResponseEntity<Long> countChunhiems(ChunhiemCriteria criteria) {
        log.debug("REST request to count Chunhiems by criteria: {}", criteria);
        return ResponseEntity.ok().body(chunhiemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chunhiems/:id} : get the "id" chunhiem.
     *
     * @param id the id of the chunhiemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chunhiemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chunhiems/{id}")
    public ResponseEntity<Chunhiem> getChunhiem(@PathVariable Long id) {
        log.debug("REST request to get Chunhiem : {}", id);
        Optional<Chunhiem> chunhiemDTO = chunhiemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chunhiemDTO);
    }

    /**
     * {@code DELETE  /chunhiems/:id} : delete the "id" chunhiem.
     *
     * @param id the id of the chunhiemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chunhiems/{id}")
    public ResponseEntity<Void> deleteChunhiem(@PathVariable Long id) {
        log.debug("REST request to delete Chunhiem : {}", id);
        chunhiemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
