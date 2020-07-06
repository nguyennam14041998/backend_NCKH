package com.api.backend.web.rest;

import com.api.backend.service.CapdetaiService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.CapdetaiDTO;
import com.api.backend.service.dto.CapdetaiCriteria;
import com.api.backend.service.CapdetaiQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Capdetai}.
 */
@RestController
@RequestMapping("/api")
public class CapdetaiResource {

    private final Logger log = LoggerFactory.getLogger(CapdetaiResource.class);

    private static final String ENTITY_NAME = "capdetai";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapdetaiService capdetaiService;

    private final CapdetaiQueryService capdetaiQueryService;

    public CapdetaiResource(CapdetaiService capdetaiService, CapdetaiQueryService capdetaiQueryService) {
        this.capdetaiService = capdetaiService;
        this.capdetaiQueryService = capdetaiQueryService;
    }

    /**
     * {@code POST  /capdetais} : Create a new capdetai.
     *
     * @param capdetaiDTO the capdetaiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capdetaiDTO, or with status {@code 400 (Bad Request)} if the capdetai has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capdetais")
    public ResponseEntity<CapdetaiDTO> createCapdetai(@RequestBody CapdetaiDTO capdetaiDTO) throws URISyntaxException {
        log.debug("REST request to save Capdetai : {}", capdetaiDTO);
        if (capdetaiDTO.getId() != null) {
            throw new BadRequestAlertException("A new capdetai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapdetaiDTO result = capdetaiService.save(capdetaiDTO);
        return ResponseEntity.created(new URI("/api/capdetais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capdetais} : Updates an existing capdetai.
     *
     * @param capdetaiDTO the capdetaiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capdetaiDTO,
     * or with status {@code 400 (Bad Request)} if the capdetaiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capdetaiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capdetais")
    public ResponseEntity<CapdetaiDTO> updateCapdetai(@RequestBody CapdetaiDTO capdetaiDTO) throws URISyntaxException {
        log.debug("REST request to update Capdetai : {}", capdetaiDTO);
        if (capdetaiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CapdetaiDTO result = capdetaiService.save(capdetaiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, capdetaiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /capdetais} : get all the capdetais.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capdetais in body.
     */
    @GetMapping("/capdetais")
    public ResponseEntity<List<CapdetaiDTO>> getAllCapdetais(CapdetaiCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Capdetais by criteria: {}", criteria);
        Page<CapdetaiDTO> page = capdetaiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /capdetais/count} : count all the capdetais.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/capdetais/count")
    public ResponseEntity<Long> countCapdetais(CapdetaiCriteria criteria) {
        log.debug("REST request to count Capdetais by criteria: {}", criteria);
        return ResponseEntity.ok().body(capdetaiQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /capdetais/:id} : get the "id" capdetai.
     *
     * @param id the id of the capdetaiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capdetaiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capdetais/{id}")
    public ResponseEntity<CapdetaiDTO> getCapdetai(@PathVariable Long id) {
        log.debug("REST request to get Capdetai : {}", id);
        Optional<CapdetaiDTO> capdetaiDTO = capdetaiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(capdetaiDTO);
    }

    /**
     * {@code DELETE  /capdetais/:id} : delete the "id" capdetai.
     *
     * @param id the id of the capdetaiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capdetais/{id}")
    public ResponseEntity<Void> deleteCapdetai(@PathVariable Long id) {
        log.debug("REST request to delete Capdetai : {}", id);
        capdetaiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
