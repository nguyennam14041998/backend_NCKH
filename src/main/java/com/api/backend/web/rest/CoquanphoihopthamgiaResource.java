package com.api.backend.web.rest;

import com.api.backend.service.CoquanphoihopthamgiaService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.CoquanphoihopthamgiaDTO;
import com.api.backend.service.dto.CoquanphoihopthamgiaCriteria;
import com.api.backend.service.CoquanphoihopthamgiaQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Coquanphoihopthamgia}.
 */
@RestController
@RequestMapping("/api")
public class CoquanphoihopthamgiaResource {

    private final Logger log = LoggerFactory.getLogger(CoquanphoihopthamgiaResource.class);

    private static final String ENTITY_NAME = "coquanphoihopthamgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoquanphoihopthamgiaService coquanphoihopthamgiaService;

    private final CoquanphoihopthamgiaQueryService coquanphoihopthamgiaQueryService;

    public CoquanphoihopthamgiaResource(CoquanphoihopthamgiaService coquanphoihopthamgiaService, CoquanphoihopthamgiaQueryService coquanphoihopthamgiaQueryService) {
        this.coquanphoihopthamgiaService = coquanphoihopthamgiaService;
        this.coquanphoihopthamgiaQueryService = coquanphoihopthamgiaQueryService;
    }

    /**
     * {@code POST  /coquanphoihopthamgias} : Create a new coquanphoihopthamgia.
     *
     * @param coquanphoihopthamgiaDTO the coquanphoihopthamgiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coquanphoihopthamgiaDTO, or with status {@code 400 (Bad Request)} if the coquanphoihopthamgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coquanphoihopthamgias")
    public ResponseEntity<CoquanphoihopthamgiaDTO> createCoquanphoihopthamgia(@RequestBody CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO) throws URISyntaxException {
        log.debug("REST request to save Coquanphoihopthamgia : {}", coquanphoihopthamgiaDTO);
        if (coquanphoihopthamgiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new coquanphoihopthamgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoquanphoihopthamgiaDTO result = coquanphoihopthamgiaService.save(coquanphoihopthamgiaDTO);
        return ResponseEntity.created(new URI("/api/coquanphoihopthamgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coquanphoihopthamgias} : Updates an existing coquanphoihopthamgia.
     *
     * @param coquanphoihopthamgiaDTO the coquanphoihopthamgiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coquanphoihopthamgiaDTO,
     * or with status {@code 400 (Bad Request)} if the coquanphoihopthamgiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coquanphoihopthamgiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coquanphoihopthamgias")
    public ResponseEntity<CoquanphoihopthamgiaDTO> updateCoquanphoihopthamgia(@RequestBody CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO) throws URISyntaxException {
        log.debug("REST request to update Coquanphoihopthamgia : {}", coquanphoihopthamgiaDTO);
        if (coquanphoihopthamgiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoquanphoihopthamgiaDTO result = coquanphoihopthamgiaService.save(coquanphoihopthamgiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coquanphoihopthamgiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coquanphoihopthamgias} : get all the coquanphoihopthamgias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coquanphoihopthamgias in body.
     */
    @GetMapping("/coquanphoihopthamgias")
    public ResponseEntity<List<CoquanphoihopthamgiaDTO>> getAllCoquanphoihopthamgias(CoquanphoihopthamgiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Coquanphoihopthamgias by criteria: {}", criteria);
        Page<CoquanphoihopthamgiaDTO> page = coquanphoihopthamgiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /coquanphoihopthamgias/count} : count all the coquanphoihopthamgias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/coquanphoihopthamgias/count")
    public ResponseEntity<Long> countCoquanphoihopthamgias(CoquanphoihopthamgiaCriteria criteria) {
        log.debug("REST request to count Coquanphoihopthamgias by criteria: {}", criteria);
        return ResponseEntity.ok().body(coquanphoihopthamgiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /coquanphoihopthamgias/:id} : get the "id" coquanphoihopthamgia.
     *
     * @param id the id of the coquanphoihopthamgiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coquanphoihopthamgiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coquanphoihopthamgias/{id}")
    public ResponseEntity<CoquanphoihopthamgiaDTO> getCoquanphoihopthamgia(@PathVariable Long id) {
        log.debug("REST request to get Coquanphoihopthamgia : {}", id);
        Optional<CoquanphoihopthamgiaDTO> coquanphoihopthamgiaDTO = coquanphoihopthamgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coquanphoihopthamgiaDTO);
    }

    /**
     * {@code DELETE  /coquanphoihopthamgias/:id} : delete the "id" coquanphoihopthamgia.
     *
     * @param id the id of the coquanphoihopthamgiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coquanphoihopthamgias/{id}")
    public ResponseEntity<Void> deleteCoquanphoihopthamgia(@PathVariable Long id) {
        log.debug("REST request to delete Coquanphoihopthamgia : {}", id);
        coquanphoihopthamgiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
