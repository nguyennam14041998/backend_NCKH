package com.api.backend.web.rest;

import com.api.backend.service.DanhgiaService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DanhgiaDTO;
import com.api.backend.service.dto.DanhgiaCriteria;
import com.api.backend.service.DanhgiaQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Danhgia}.
 */
@RestController
@RequestMapping("/api")
public class DanhgiaResource {

    private final Logger log = LoggerFactory.getLogger(DanhgiaResource.class);

    private static final String ENTITY_NAME = "danhgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DanhgiaService danhgiaService;

    private final DanhgiaQueryService danhgiaQueryService;

    public DanhgiaResource(DanhgiaService danhgiaService, DanhgiaQueryService danhgiaQueryService) {
        this.danhgiaService = danhgiaService;
        this.danhgiaQueryService = danhgiaQueryService;
    }

    /**
     * {@code POST  /danhgias} : Create a new danhgia.
     *
     * @param danhgiaDTO the danhgiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new danhgiaDTO, or with status {@code 400 (Bad Request)} if the danhgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/danhgias")
    public ResponseEntity<DanhgiaDTO> createDanhgia(@RequestBody DanhgiaDTO danhgiaDTO) throws URISyntaxException {
        log.debug("REST request to save Danhgia : {}", danhgiaDTO);
        if (danhgiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new danhgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DanhgiaDTO result = danhgiaService.save(danhgiaDTO);
        return ResponseEntity.created(new URI("/api/danhgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /danhgias} : Updates an existing danhgia.
     *
     * @param danhgiaDTO the danhgiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhgiaDTO,
     * or with status {@code 400 (Bad Request)} if the danhgiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the danhgiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/danhgias")
    public ResponseEntity<DanhgiaDTO> updateDanhgia(@RequestBody DanhgiaDTO danhgiaDTO) throws URISyntaxException {
        log.debug("REST request to update Danhgia : {}", danhgiaDTO);
        if (danhgiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DanhgiaDTO result = danhgiaService.save(danhgiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, danhgiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /danhgias} : get all the danhgias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of danhgias in body.
     */
    @GetMapping("/danhgias")
    public ResponseEntity<List<DanhgiaDTO>> getAllDanhgias(DanhgiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Danhgias by criteria: {}", criteria);
        Page<DanhgiaDTO> page = danhgiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /danhgias/count} : count all the danhgias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/danhgias/count")
    public ResponseEntity<Long> countDanhgias(DanhgiaCriteria criteria) {
        log.debug("REST request to count Danhgias by criteria: {}", criteria);
        return ResponseEntity.ok().body(danhgiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /danhgias/:id} : get the "id" danhgia.
     *
     * @param id the id of the danhgiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the danhgiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/danhgias/{id}")
    public ResponseEntity<DanhgiaDTO> getDanhgia(@PathVariable Long id) {
        log.debug("REST request to get Danhgia : {}", id);
        Optional<DanhgiaDTO> danhgiaDTO = danhgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(danhgiaDTO);
    }

    /**
     * {@code DELETE  /danhgias/:id} : delete the "id" danhgia.
     *
     * @param id the id of the danhgiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/danhgias/{id}")
    public ResponseEntity<Void> deleteDanhgia(@PathVariable Long id) {
        log.debug("REST request to delete Danhgia : {}", id);
        danhgiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
