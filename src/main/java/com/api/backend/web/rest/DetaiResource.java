package com.api.backend.web.rest;

import com.api.backend.service.DetaiService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DetaiDTO;
import com.api.backend.service.dto.DetaiCriteria;
import com.api.backend.service.DetaiQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Detai}.
 */
@RestController
@RequestMapping("/api")
public class DetaiResource {

    private final Logger log = LoggerFactory.getLogger(DetaiResource.class);

    private static final String ENTITY_NAME = "detai";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetaiService detaiService;

    private final DetaiQueryService detaiQueryService;

    public DetaiResource(DetaiService detaiService, DetaiQueryService detaiQueryService) {
        this.detaiService = detaiService;
        this.detaiQueryService = detaiQueryService;
    }

    /**
     * {@code POST  /detais} : Create a new detai.
     *
     * @param detaiDTO the detaiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detaiDTO, or with status {@code 400 (Bad Request)} if the detai has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detais")
    public ResponseEntity<DetaiDTO> createDetai(@RequestBody DetaiDTO detaiDTO) throws URISyntaxException {
        log.debug("REST request to save Detai : {}", detaiDTO);
        if (detaiDTO.getId() != null) {
            throw new BadRequestAlertException("A new detai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetaiDTO result = detaiService.save(detaiDTO);
        return ResponseEntity.created(new URI("/api/detais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detais} : Updates an existing detai.
     *
     * @param detaiDTO the detaiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detaiDTO,
     * or with status {@code 400 (Bad Request)} if the detaiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detaiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detais")
    public ResponseEntity<DetaiDTO> updateDetai(@RequestBody DetaiDTO detaiDTO) throws URISyntaxException {
        log.debug("REST request to update Detai : {}", detaiDTO);
        if (detaiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetaiDTO result = detaiService.save(detaiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detaiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detais} : get all the detais.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detais in body.
     */
    @GetMapping("/detais")
    public ResponseEntity<List<DetaiDTO>> getAllDetais(DetaiCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Detais by criteria: {}", criteria);
        Page<DetaiDTO> page = detaiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /detais/count} : count all the detais.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/detais/count")
    public ResponseEntity<Long> countDetais(DetaiCriteria criteria) {
        log.debug("REST request to count Detais by criteria: {}", criteria);
        return ResponseEntity.ok().body(detaiQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detais/:id} : get the "id" detai.
     *
     * @param id the id of the detaiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detaiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detais/{id}")
    public ResponseEntity<DetaiDTO> getDetai(@PathVariable Long id) {
        log.debug("REST request to get Detai : {}", id);
        Optional<DetaiDTO> detaiDTO = detaiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detaiDTO);
    }

    /**
     * {@code DELETE  /detais/:id} : delete the "id" detai.
     *
     * @param id the id of the detaiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detais/{id}")
    public ResponseEntity<Void> deleteDetai(@PathVariable Long id) {
        log.debug("REST request to delete Detai : {}", id);
        detaiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
