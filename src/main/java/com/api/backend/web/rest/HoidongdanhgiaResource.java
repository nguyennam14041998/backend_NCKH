package com.api.backend.web.rest;

import com.api.backend.service.HoidongdanhgiaService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.HoidongdanhgiaDTO;
import com.api.backend.service.dto.HoidongdanhgiaCriteria;
import com.api.backend.service.HoidongdanhgiaQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Hoidongdanhgia}.
 */
@RestController
@RequestMapping("/api")
public class HoidongdanhgiaResource {

    private final Logger log = LoggerFactory.getLogger(HoidongdanhgiaResource.class);

    private static final String ENTITY_NAME = "hoidongdanhgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoidongdanhgiaService hoidongdanhgiaService;

    private final HoidongdanhgiaQueryService hoidongdanhgiaQueryService;

    public HoidongdanhgiaResource(HoidongdanhgiaService hoidongdanhgiaService, HoidongdanhgiaQueryService hoidongdanhgiaQueryService) {
        this.hoidongdanhgiaService = hoidongdanhgiaService;
        this.hoidongdanhgiaQueryService = hoidongdanhgiaQueryService;
    }

    /**
     * {@code POST  /hoidongdanhgias} : Create a new hoidongdanhgia.
     *
     * @param hoidongdanhgiaDTO the hoidongdanhgiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoidongdanhgiaDTO, or with status {@code 400 (Bad Request)} if the hoidongdanhgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hoidongdanhgias")
    public ResponseEntity<HoidongdanhgiaDTO> createHoidongdanhgia(@RequestBody HoidongdanhgiaDTO hoidongdanhgiaDTO) throws URISyntaxException {
        log.debug("REST request to save Hoidongdanhgia : {}", hoidongdanhgiaDTO);
        if (hoidongdanhgiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new hoidongdanhgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoidongdanhgiaDTO result = hoidongdanhgiaService.save(hoidongdanhgiaDTO);
        return ResponseEntity.created(new URI("/api/hoidongdanhgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hoidongdanhgias} : Updates an existing hoidongdanhgia.
     *
     * @param hoidongdanhgiaDTO the hoidongdanhgiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoidongdanhgiaDTO,
     * or with status {@code 400 (Bad Request)} if the hoidongdanhgiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoidongdanhgiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hoidongdanhgias")
    public ResponseEntity<HoidongdanhgiaDTO> updateHoidongdanhgia(@RequestBody HoidongdanhgiaDTO hoidongdanhgiaDTO) throws URISyntaxException {
        log.debug("REST request to update Hoidongdanhgia : {}", hoidongdanhgiaDTO);
        if (hoidongdanhgiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HoidongdanhgiaDTO result = hoidongdanhgiaService.save(hoidongdanhgiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hoidongdanhgiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hoidongdanhgias} : get all the hoidongdanhgias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoidongdanhgias in body.
     */
    @GetMapping("/hoidongdanhgias")
    public ResponseEntity<List<HoidongdanhgiaDTO>> getAllHoidongdanhgias(HoidongdanhgiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Hoidongdanhgias by criteria: {}", criteria);
        Page<HoidongdanhgiaDTO> page = hoidongdanhgiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /hoidongdanhgias/count} : count all the hoidongdanhgias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/hoidongdanhgias/count")
    public ResponseEntity<Long> countHoidongdanhgias(HoidongdanhgiaCriteria criteria) {
        log.debug("REST request to count Hoidongdanhgias by criteria: {}", criteria);
        return ResponseEntity.ok().body(hoidongdanhgiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hoidongdanhgias/:id} : get the "id" hoidongdanhgia.
     *
     * @param id the id of the hoidongdanhgiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoidongdanhgiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hoidongdanhgias/{id}")
    public ResponseEntity<HoidongdanhgiaDTO> getHoidongdanhgia(@PathVariable Long id) {
        log.debug("REST request to get Hoidongdanhgia : {}", id);
        Optional<HoidongdanhgiaDTO> hoidongdanhgiaDTO = hoidongdanhgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hoidongdanhgiaDTO);
    }

    /**
     * {@code DELETE  /hoidongdanhgias/:id} : delete the "id" hoidongdanhgia.
     *
     * @param id the id of the hoidongdanhgiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hoidongdanhgias/{id}")
    public ResponseEntity<Void> deleteHoidongdanhgia(@PathVariable Long id) {
        log.debug("REST request to delete Hoidongdanhgia : {}", id);
        hoidongdanhgiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
