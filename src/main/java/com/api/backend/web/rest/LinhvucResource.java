package com.api.backend.web.rest;

import com.api.backend.service.LinhvucService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.LinhvucDTO;
import com.api.backend.service.dto.LinhvucCriteria;
import com.api.backend.service.LinhvucQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Linhvuc}.
 */
@RestController
@RequestMapping("/api")
public class LinhvucResource {

    private final Logger log = LoggerFactory.getLogger(LinhvucResource.class);

    private static final String ENTITY_NAME = "linhvuc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinhvucService linhvucService;

    private final LinhvucQueryService linhvucQueryService;

    public LinhvucResource(LinhvucService linhvucService, LinhvucQueryService linhvucQueryService) {
        this.linhvucService = linhvucService;
        this.linhvucQueryService = linhvucQueryService;
    }

    /**
     * {@code POST  /linhvucs} : Create a new linhvuc.
     *
     * @param linhvucDTO the linhvucDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linhvucDTO, or with status {@code 400 (Bad Request)} if the linhvuc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linhvucs")
    public ResponseEntity<LinhvucDTO> createLinhvuc(@RequestBody LinhvucDTO linhvucDTO) throws URISyntaxException {
        log.debug("REST request to save Linhvuc : {}", linhvucDTO);
        if (linhvucDTO.getId() != null) {
            throw new BadRequestAlertException("A new linhvuc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinhvucDTO result = linhvucService.save(linhvucDTO);
        return ResponseEntity.created(new URI("/api/linhvucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linhvucs} : Updates an existing linhvuc.
     *
     * @param linhvucDTO the linhvucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linhvucDTO,
     * or with status {@code 400 (Bad Request)} if the linhvucDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linhvucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linhvucs")
    public ResponseEntity<LinhvucDTO> updateLinhvuc(@RequestBody LinhvucDTO linhvucDTO) throws URISyntaxException {
        log.debug("REST request to update Linhvuc : {}", linhvucDTO);
        if (linhvucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LinhvucDTO result = linhvucService.save(linhvucDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linhvucDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linhvucs} : get all the linhvucs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linhvucs in body.
     */
    @GetMapping("/linhvucs")
    public ResponseEntity<List<LinhvucDTO>> getAllLinhvucs(LinhvucCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Linhvucs by criteria: {}", criteria);
        Page<LinhvucDTO> page = linhvucQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /linhvucs/count} : count all the linhvucs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/linhvucs/count")
    public ResponseEntity<Long> countLinhvucs(LinhvucCriteria criteria) {
        log.debug("REST request to count Linhvucs by criteria: {}", criteria);
        return ResponseEntity.ok().body(linhvucQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /linhvucs/:id} : get the "id" linhvuc.
     *
     * @param id the id of the linhvucDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linhvucDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linhvucs/{id}")
    public ResponseEntity<LinhvucDTO> getLinhvuc(@PathVariable Long id) {
        log.debug("REST request to get Linhvuc : {}", id);
        Optional<LinhvucDTO> linhvucDTO = linhvucService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linhvucDTO);
    }

    /**
     * {@code DELETE  /linhvucs/:id} : delete the "id" linhvuc.
     *
     * @param id the id of the linhvucDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linhvucs/{id}")
    public ResponseEntity<Void> deleteLinhvuc(@PathVariable Long id) {
        log.debug("REST request to delete Linhvuc : {}", id);
        linhvucService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
