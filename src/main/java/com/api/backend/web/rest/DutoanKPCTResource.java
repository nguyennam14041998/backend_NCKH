package com.api.backend.web.rest;

import com.api.backend.service.DutoanKPCTService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DutoanKPCTDTO;
import com.api.backend.service.dto.DutoanKPCTCriteria;
import com.api.backend.service.DutoanKPCTQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.DutoanKPCT}.
 */
@RestController
@RequestMapping("/api")
public class DutoanKPCTResource {

    private final Logger log = LoggerFactory.getLogger(DutoanKPCTResource.class);

    private static final String ENTITY_NAME = "dutoanKPCT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DutoanKPCTService dutoanKPCTService;

    private final DutoanKPCTQueryService dutoanKPCTQueryService;

    public DutoanKPCTResource(DutoanKPCTService dutoanKPCTService, DutoanKPCTQueryService dutoanKPCTQueryService) {
        this.dutoanKPCTService = dutoanKPCTService;
        this.dutoanKPCTQueryService = dutoanKPCTQueryService;
    }

    /**
     * {@code POST  /dutoan-kpcts} : Create a new dutoanKPCT.
     *
     * @param dutoanKPCTDTO the dutoanKPCTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dutoanKPCTDTO, or with status {@code 400 (Bad Request)} if the dutoanKPCT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dutoan-kpcts")
    public ResponseEntity<DutoanKPCTDTO> createDutoanKPCT(@RequestBody DutoanKPCTDTO dutoanKPCTDTO) throws URISyntaxException {
        log.debug("REST request to save DutoanKPCT : {}", dutoanKPCTDTO);
        if (dutoanKPCTDTO.getId() != null) {
            throw new BadRequestAlertException("A new dutoanKPCT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DutoanKPCTDTO result = dutoanKPCTService.save(dutoanKPCTDTO);
        return ResponseEntity.created(new URI("/api/dutoan-kpcts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dutoan-kpcts} : Updates an existing dutoanKPCT.
     *
     * @param dutoanKPCTDTO the dutoanKPCTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dutoanKPCTDTO,
     * or with status {@code 400 (Bad Request)} if the dutoanKPCTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dutoanKPCTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dutoan-kpcts")
    public ResponseEntity<DutoanKPCTDTO> updateDutoanKPCT(@RequestBody DutoanKPCTDTO dutoanKPCTDTO) throws URISyntaxException {
        log.debug("REST request to update DutoanKPCT : {}", dutoanKPCTDTO);
        if (dutoanKPCTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DutoanKPCTDTO result = dutoanKPCTService.save(dutoanKPCTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dutoanKPCTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dutoan-kpcts} : get all the dutoanKPCTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dutoanKPCTS in body.
     */
    @GetMapping("/dutoan-kpcts")
    public ResponseEntity<List<DutoanKPCTDTO>> getAllDutoanKPCTS(DutoanKPCTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DutoanKPCTS by criteria: {}", criteria);
        Page<DutoanKPCTDTO> page = dutoanKPCTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /dutoan-kpcts/count} : count all the dutoanKPCTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/dutoan-kpcts/count")
    public ResponseEntity<Long> countDutoanKPCTS(DutoanKPCTCriteria criteria) {
        log.debug("REST request to count DutoanKPCTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(dutoanKPCTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dutoan-kpcts/:id} : get the "id" dutoanKPCT.
     *
     * @param id the id of the dutoanKPCTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dutoanKPCTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dutoan-kpcts/{id}")
    public ResponseEntity<DutoanKPCTDTO> getDutoanKPCT(@PathVariable Long id) {
        log.debug("REST request to get DutoanKPCT : {}", id);
        Optional<DutoanKPCTDTO> dutoanKPCTDTO = dutoanKPCTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dutoanKPCTDTO);
    }

    /**
     * {@code DELETE  /dutoan-kpcts/:id} : delete the "id" dutoanKPCT.
     *
     * @param id the id of the dutoanKPCTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dutoan-kpcts/{id}")
    public ResponseEntity<Void> deleteDutoanKPCT(@PathVariable Long id) {
        log.debug("REST request to delete DutoanKPCT : {}", id);
        dutoanKPCTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
