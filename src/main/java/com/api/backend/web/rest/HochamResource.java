package com.api.backend.web.rest;

import com.api.backend.service.HochamService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.HochamDTO;
import com.api.backend.service.dto.HochamCriteria;
import com.api.backend.service.HochamQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Hocham}.
 */
@RestController
@RequestMapping("/api")
public class HochamResource {

    private final Logger log = LoggerFactory.getLogger(HochamResource.class);

    private static final String ENTITY_NAME = "hocham";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HochamService hochamService;

    private final HochamQueryService hochamQueryService;

    public HochamResource(HochamService hochamService, HochamQueryService hochamQueryService) {
        this.hochamService = hochamService;
        this.hochamQueryService = hochamQueryService;
    }

    /**
     * {@code POST  /hochams} : Create a new hocham.
     *
     * @param hochamDTO the hochamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hochamDTO, or with status {@code 400 (Bad Request)} if the hocham has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hochams")
    public ResponseEntity<HochamDTO> createHocham(@RequestBody HochamDTO hochamDTO) throws URISyntaxException {
        log.debug("REST request to save Hocham : {}", hochamDTO);
        if (hochamDTO.getId() != null) {
            throw new BadRequestAlertException("A new hocham cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HochamDTO result = hochamService.save(hochamDTO);
        return ResponseEntity.created(new URI("/api/hochams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hochams} : Updates an existing hocham.
     *
     * @param hochamDTO the hochamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hochamDTO,
     * or with status {@code 400 (Bad Request)} if the hochamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hochamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hochams")
    public ResponseEntity<HochamDTO> updateHocham(@RequestBody HochamDTO hochamDTO) throws URISyntaxException {
        log.debug("REST request to update Hocham : {}", hochamDTO);
        if (hochamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HochamDTO result = hochamService.save(hochamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hochamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hochams} : get all the hochams.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hochams in body.
     */
    @GetMapping("/hochams")
    public ResponseEntity<List<HochamDTO>> getAllHochams(HochamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Hochams by criteria: {}", criteria);
        Page<HochamDTO> page = hochamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /hochams/count} : count all the hochams.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/hochams/count")
    public ResponseEntity<Long> countHochams(HochamCriteria criteria) {
        log.debug("REST request to count Hochams by criteria: {}", criteria);
        return ResponseEntity.ok().body(hochamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hochams/:id} : get the "id" hocham.
     *
     * @param id the id of the hochamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hochamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hochams/{id}")
    public ResponseEntity<HochamDTO> getHocham(@PathVariable Long id) {
        log.debug("REST request to get Hocham : {}", id);
        Optional<HochamDTO> hochamDTO = hochamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hochamDTO);
    }

    /**
     * {@code DELETE  /hochams/:id} : delete the "id" hocham.
     *
     * @param id the id of the hochamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hochams/{id}")
    public ResponseEntity<Void> deleteHocham(@PathVariable Long id) {
        log.debug("REST request to delete Hocham : {}", id);
        hochamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
