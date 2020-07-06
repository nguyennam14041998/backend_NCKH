package com.api.backend.web.rest;

import com.api.backend.service.NhansuthamgiaService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.NhansuthamgiaDTO;
import com.api.backend.service.dto.NhansuthamgiaCriteria;
import com.api.backend.service.NhansuthamgiaQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Nhansuthamgia}.
 */
@RestController
@RequestMapping("/api")
public class NhansuthamgiaResource {

    private final Logger log = LoggerFactory.getLogger(NhansuthamgiaResource.class);

    private static final String ENTITY_NAME = "nhansuthamgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhansuthamgiaService nhansuthamgiaService;

    private final NhansuthamgiaQueryService nhansuthamgiaQueryService;

    public NhansuthamgiaResource(NhansuthamgiaService nhansuthamgiaService, NhansuthamgiaQueryService nhansuthamgiaQueryService) {
        this.nhansuthamgiaService = nhansuthamgiaService;
        this.nhansuthamgiaQueryService = nhansuthamgiaQueryService;
    }

    /**
     * {@code POST  /nhansuthamgias} : Create a new nhansuthamgia.
     *
     * @param nhansuthamgiaDTO the nhansuthamgiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhansuthamgiaDTO, or with status {@code 400 (Bad Request)} if the nhansuthamgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhansuthamgias")
    public ResponseEntity<NhansuthamgiaDTO> createNhansuthamgia(@RequestBody NhansuthamgiaDTO nhansuthamgiaDTO) throws URISyntaxException {
        log.debug("REST request to save Nhansuthamgia : {}", nhansuthamgiaDTO);
        if (nhansuthamgiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new nhansuthamgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhansuthamgiaDTO result = nhansuthamgiaService.save(nhansuthamgiaDTO);
        return ResponseEntity.created(new URI("/api/nhansuthamgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhansuthamgias} : Updates an existing nhansuthamgia.
     *
     * @param nhansuthamgiaDTO the nhansuthamgiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhansuthamgiaDTO,
     * or with status {@code 400 (Bad Request)} if the nhansuthamgiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhansuthamgiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhansuthamgias")
    public ResponseEntity<NhansuthamgiaDTO> updateNhansuthamgia(@RequestBody NhansuthamgiaDTO nhansuthamgiaDTO) throws URISyntaxException {
        log.debug("REST request to update Nhansuthamgia : {}", nhansuthamgiaDTO);
        if (nhansuthamgiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhansuthamgiaDTO result = nhansuthamgiaService.save(nhansuthamgiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhansuthamgiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nhansuthamgias} : get all the nhansuthamgias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhansuthamgias in body.
     */
    @GetMapping("/nhansuthamgias")
    public ResponseEntity<List<NhansuthamgiaDTO>> getAllNhansuthamgias(NhansuthamgiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Nhansuthamgias by criteria: {}", criteria);
        Page<NhansuthamgiaDTO> page = nhansuthamgiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /nhansuthamgias/count} : count all the nhansuthamgias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/nhansuthamgias/count")
    public ResponseEntity<Long> countNhansuthamgias(NhansuthamgiaCriteria criteria) {
        log.debug("REST request to count Nhansuthamgias by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhansuthamgiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nhansuthamgias/:id} : get the "id" nhansuthamgia.
     *
     * @param id the id of the nhansuthamgiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhansuthamgiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhansuthamgias/{id}")
    public ResponseEntity<NhansuthamgiaDTO> getNhansuthamgia(@PathVariable Long id) {
        log.debug("REST request to get Nhansuthamgia : {}", id);
        Optional<NhansuthamgiaDTO> nhansuthamgiaDTO = nhansuthamgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhansuthamgiaDTO);
    }

    /**
     * {@code DELETE  /nhansuthamgias/:id} : delete the "id" nhansuthamgia.
     *
     * @param id the id of the nhansuthamgiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhansuthamgias/{id}")
    public ResponseEntity<Void> deleteNhansuthamgia(@PathVariable Long id) {
        log.debug("REST request to delete Nhansuthamgia : {}", id);
        nhansuthamgiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
