package com.api.backend.web.rest;

import com.api.backend.service.DanhgiaCTService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DanhgiaCTDTO;
import com.api.backend.service.dto.DanhgiaCTCriteria;
import com.api.backend.service.DanhgiaCTQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.DanhgiaCT}.
 */
@RestController
@RequestMapping("/api")
public class DanhgiaCTResource {

    private final Logger log = LoggerFactory.getLogger(DanhgiaCTResource.class);

    private static final String ENTITY_NAME = "danhgiaCT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DanhgiaCTService danhgiaCTService;

    private final DanhgiaCTQueryService danhgiaCTQueryService;

    public DanhgiaCTResource(DanhgiaCTService danhgiaCTService, DanhgiaCTQueryService danhgiaCTQueryService) {
        this.danhgiaCTService = danhgiaCTService;
        this.danhgiaCTQueryService = danhgiaCTQueryService;
    }

    /**
     * {@code POST  /danhgia-cts} : Create a new danhgiaCT.
     *
     * @param danhgiaCTDTO the danhgiaCTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new danhgiaCTDTO, or with status {@code 400 (Bad Request)} if the danhgiaCT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/danhgia-cts")
    public ResponseEntity<DanhgiaCTDTO> createDanhgiaCT(@RequestBody DanhgiaCTDTO danhgiaCTDTO) throws URISyntaxException {
        log.debug("REST request to save DanhgiaCT : {}", danhgiaCTDTO);
        if (danhgiaCTDTO.getId() != null) {
            throw new BadRequestAlertException("A new danhgiaCT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DanhgiaCTDTO result = danhgiaCTService.save(danhgiaCTDTO);
        return ResponseEntity.created(new URI("/api/danhgia-cts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /danhgia-cts} : Updates an existing danhgiaCT.
     *
     * @param danhgiaCTDTO the danhgiaCTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhgiaCTDTO,
     * or with status {@code 400 (Bad Request)} if the danhgiaCTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the danhgiaCTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/danhgia-cts")
    public ResponseEntity<DanhgiaCTDTO> updateDanhgiaCT(@RequestBody DanhgiaCTDTO danhgiaCTDTO) throws URISyntaxException {
        log.debug("REST request to update DanhgiaCT : {}", danhgiaCTDTO);
        if (danhgiaCTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DanhgiaCTDTO result = danhgiaCTService.save(danhgiaCTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, danhgiaCTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /danhgia-cts} : get all the danhgiaCTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of danhgiaCTS in body.
     */
    @GetMapping("/danhgia-cts")
    public ResponseEntity<List<DanhgiaCTDTO>> getAllDanhgiaCTS(DanhgiaCTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DanhgiaCTS by criteria: {}", criteria);
        Page<DanhgiaCTDTO> page = danhgiaCTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /danhgia-cts/count} : count all the danhgiaCTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/danhgia-cts/count")
    public ResponseEntity<Long> countDanhgiaCTS(DanhgiaCTCriteria criteria) {
        log.debug("REST request to count DanhgiaCTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(danhgiaCTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /danhgia-cts/:id} : get the "id" danhgiaCT.
     *
     * @param id the id of the danhgiaCTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the danhgiaCTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/danhgia-cts/{id}")
    public ResponseEntity<DanhgiaCTDTO> getDanhgiaCT(@PathVariable Long id) {
        log.debug("REST request to get DanhgiaCT : {}", id);
        Optional<DanhgiaCTDTO> danhgiaCTDTO = danhgiaCTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(danhgiaCTDTO);
    }

    /**
     * {@code DELETE  /danhgia-cts/:id} : delete the "id" danhgiaCT.
     *
     * @param id the id of the danhgiaCTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/danhgia-cts/{id}")
    public ResponseEntity<Void> deleteDanhgiaCT(@PathVariable Long id) {
        log.debug("REST request to delete DanhgiaCT : {}", id);
        danhgiaCTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
