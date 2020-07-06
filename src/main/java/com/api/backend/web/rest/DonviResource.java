package com.api.backend.web.rest;

import com.api.backend.service.DonviService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DonviDTO;
import com.api.backend.service.dto.DonviCriteria;
import com.api.backend.service.DonviQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Donvi}.
 */
@RestController
@RequestMapping("/api")
public class DonviResource {

    private final Logger log = LoggerFactory.getLogger(DonviResource.class);

    private static final String ENTITY_NAME = "donvi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonviService donviService;

    private final DonviQueryService donviQueryService;

    public DonviResource(DonviService donviService, DonviQueryService donviQueryService) {
        this.donviService = donviService;
        this.donviQueryService = donviQueryService;
    }

    /**
     * {@code POST  /donvis} : Create a new donvi.
     *
     * @param donviDTO the donviDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donviDTO, or with status {@code 400 (Bad Request)} if the donvi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donvis")
    public ResponseEntity<DonviDTO> createDonvi(@RequestBody DonviDTO donviDTO) throws URISyntaxException {
        log.debug("REST request to save Donvi : {}", donviDTO);
        if (donviDTO.getId() != null) {
            throw new BadRequestAlertException("A new donvi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonviDTO result = donviService.save(donviDTO);
        return ResponseEntity.created(new URI("/api/donvis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donvis} : Updates an existing donvi.
     *
     * @param donviDTO the donviDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donviDTO,
     * or with status {@code 400 (Bad Request)} if the donviDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donviDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donvis")
    public ResponseEntity<DonviDTO> updateDonvi(@RequestBody DonviDTO donviDTO) throws URISyntaxException {
        log.debug("REST request to update Donvi : {}", donviDTO);
        if (donviDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DonviDTO result = donviService.save(donviDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donviDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /donvis} : get all the donvis.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donvis in body.
     */
    @GetMapping("/donvis")
    public ResponseEntity<List<DonviDTO>> getAllDonvis(DonviCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Donvis by criteria: {}", criteria);
        Page<DonviDTO> page = donviQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /donvis/count} : count all the donvis.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/donvis/count")
    public ResponseEntity<Long> countDonvis(DonviCriteria criteria) {
        log.debug("REST request to count Donvis by criteria: {}", criteria);
        return ResponseEntity.ok().body(donviQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /donvis/:id} : get the "id" donvi.
     *
     * @param id the id of the donviDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donviDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donvis/{id}")
    public ResponseEntity<DonviDTO> getDonvi(@PathVariable Long id) {
        log.debug("REST request to get Donvi : {}", id);
        Optional<DonviDTO> donviDTO = donviService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donviDTO);
    }

    /**
     * {@code DELETE  /donvis/:id} : delete the "id" donvi.
     *
     * @param id the id of the donviDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donvis/{id}")
    public ResponseEntity<Void> deleteDonvi(@PathVariable Long id) {
        log.debug("REST request to delete Donvi : {}", id);
        donviService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
