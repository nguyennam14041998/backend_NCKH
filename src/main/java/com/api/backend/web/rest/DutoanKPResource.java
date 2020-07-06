package com.api.backend.web.rest;

import com.api.backend.service.DutoanKPService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DutoanKPDTO;
import com.api.backend.service.dto.DutoanKPCriteria;
import com.api.backend.service.DutoanKPQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.DutoanKP}.
 */
@RestController
@RequestMapping("/api")
public class DutoanKPResource {

    private final Logger log = LoggerFactory.getLogger(DutoanKPResource.class);

    private static final String ENTITY_NAME = "dutoanKP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DutoanKPService dutoanKPService;

    private final DutoanKPQueryService dutoanKPQueryService;

    public DutoanKPResource(DutoanKPService dutoanKPService, DutoanKPQueryService dutoanKPQueryService) {
        this.dutoanKPService = dutoanKPService;
        this.dutoanKPQueryService = dutoanKPQueryService;
    }

    /**
     * {@code POST  /dutoan-kps} : Create a new dutoanKP.
     *
     * @param dutoanKPDTO the dutoanKPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dutoanKPDTO, or with status {@code 400 (Bad Request)} if the dutoanKP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dutoan-kps")
    public ResponseEntity<DutoanKPDTO> createDutoanKP(@RequestBody DutoanKPDTO dutoanKPDTO) throws URISyntaxException {
        log.debug("REST request to save DutoanKP : {}", dutoanKPDTO);
        if (dutoanKPDTO.getId() != null) {
            throw new BadRequestAlertException("A new dutoanKP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DutoanKPDTO result = dutoanKPService.save(dutoanKPDTO);
        return ResponseEntity.created(new URI("/api/dutoan-kps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dutoan-kps} : Updates an existing dutoanKP.
     *
     * @param dutoanKPDTO the dutoanKPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dutoanKPDTO,
     * or with status {@code 400 (Bad Request)} if the dutoanKPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dutoanKPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dutoan-kps")
    public ResponseEntity<DutoanKPDTO> updateDutoanKP(@RequestBody DutoanKPDTO dutoanKPDTO) throws URISyntaxException {
        log.debug("REST request to update DutoanKP : {}", dutoanKPDTO);
        if (dutoanKPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DutoanKPDTO result = dutoanKPService.save(dutoanKPDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dutoanKPDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dutoan-kps} : get all the dutoanKPS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dutoanKPS in body.
     */
    @GetMapping("/dutoan-kps")
    public ResponseEntity<List<DutoanKPDTO>> getAllDutoanKPS(DutoanKPCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DutoanKPS by criteria: {}", criteria);
        Page<DutoanKPDTO> page = dutoanKPQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /dutoan-kps/count} : count all the dutoanKPS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/dutoan-kps/count")
    public ResponseEntity<Long> countDutoanKPS(DutoanKPCriteria criteria) {
        log.debug("REST request to count DutoanKPS by criteria: {}", criteria);
        return ResponseEntity.ok().body(dutoanKPQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dutoan-kps/:id} : get the "id" dutoanKP.
     *
     * @param id the id of the dutoanKPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dutoanKPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dutoan-kps/{id}")
    public ResponseEntity<DutoanKPDTO> getDutoanKP(@PathVariable Long id) {
        log.debug("REST request to get DutoanKP : {}", id);
        Optional<DutoanKPDTO> dutoanKPDTO = dutoanKPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dutoanKPDTO);
    }

    /**
     * {@code DELETE  /dutoan-kps/:id} : delete the "id" dutoanKP.
     *
     * @param id the id of the dutoanKPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dutoan-kps/{id}")
    public ResponseEntity<Void> deleteDutoanKP(@PathVariable Long id) {
        log.debug("REST request to delete DutoanKP : {}", id);
        dutoanKPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
