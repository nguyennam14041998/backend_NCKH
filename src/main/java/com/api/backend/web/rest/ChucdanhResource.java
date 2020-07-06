package com.api.backend.web.rest;

import com.api.backend.service.ChucdanhService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.ChucdanhDTO;
import com.api.backend.service.dto.ChucdanhCriteria;
import com.api.backend.service.ChucdanhQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Chucdanh}.
 */
@RestController
@RequestMapping("/api")
public class ChucdanhResource {

    private final Logger log = LoggerFactory.getLogger(ChucdanhResource.class);

    private static final String ENTITY_NAME = "chucdanh";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChucdanhService chucdanhService;

    private final ChucdanhQueryService chucdanhQueryService;

    public ChucdanhResource(ChucdanhService chucdanhService, ChucdanhQueryService chucdanhQueryService) {
        this.chucdanhService = chucdanhService;
        this.chucdanhQueryService = chucdanhQueryService;
    }

    /**
     * {@code POST  /chucdanhs} : Create a new chucdanh.
     *
     * @param chucdanhDTO the chucdanhDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chucdanhDTO, or with status {@code 400 (Bad Request)} if the chucdanh has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chucdanhs")
    public ResponseEntity<ChucdanhDTO> createChucdanh(@RequestBody ChucdanhDTO chucdanhDTO) throws URISyntaxException {
        log.debug("REST request to save Chucdanh : {}", chucdanhDTO);
        if (chucdanhDTO.getId() != null) {
            throw new BadRequestAlertException("A new chucdanh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChucdanhDTO result = chucdanhService.save(chucdanhDTO);
        return ResponseEntity.created(new URI("/api/chucdanhs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chucdanhs} : Updates an existing chucdanh.
     *
     * @param chucdanhDTO the chucdanhDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chucdanhDTO,
     * or with status {@code 400 (Bad Request)} if the chucdanhDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chucdanhDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chucdanhs")
    public ResponseEntity<ChucdanhDTO> updateChucdanh(@RequestBody ChucdanhDTO chucdanhDTO) throws URISyntaxException {
        log.debug("REST request to update Chucdanh : {}", chucdanhDTO);
        if (chucdanhDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChucdanhDTO result = chucdanhService.save(chucdanhDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chucdanhDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chucdanhs} : get all the chucdanhs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chucdanhs in body.
     */
    @GetMapping("/chucdanhs")
    public ResponseEntity<List<ChucdanhDTO>> getAllChucdanhs(ChucdanhCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Chucdanhs by criteria: {}", criteria);
        Page<ChucdanhDTO> page = chucdanhQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /chucdanhs/count} : count all the chucdanhs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/chucdanhs/count")
    public ResponseEntity<Long> countChucdanhs(ChucdanhCriteria criteria) {
        log.debug("REST request to count Chucdanhs by criteria: {}", criteria);
        return ResponseEntity.ok().body(chucdanhQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chucdanhs/:id} : get the "id" chucdanh.
     *
     * @param id the id of the chucdanhDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chucdanhDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chucdanhs/{id}")
    public ResponseEntity<ChucdanhDTO> getChucdanh(@PathVariable Long id) {
        log.debug("REST request to get Chucdanh : {}", id);
        Optional<ChucdanhDTO> chucdanhDTO = chucdanhService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chucdanhDTO);
    }

    /**
     * {@code DELETE  /chucdanhs/:id} : delete the "id" chucdanh.
     *
     * @param id the id of the chucdanhDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chucdanhs/{id}")
    public ResponseEntity<Void> deleteChucdanh(@PathVariable Long id) {
        log.debug("REST request to delete Chucdanh : {}", id);
        chucdanhService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
