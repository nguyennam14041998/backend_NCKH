package com.api.backend.web.rest;

import com.api.backend.service.NguonkinhphiService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.NguonkinhphiDTO;
import com.api.backend.service.dto.NguonkinhphiCriteria;
import com.api.backend.service.NguonkinhphiQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Nguonkinhphi}.
 */
@RestController
@RequestMapping("/api")
public class NguonkinhphiResource {

    private final Logger log = LoggerFactory.getLogger(NguonkinhphiResource.class);

    private static final String ENTITY_NAME = "nguonkinhphi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NguonkinhphiService nguonkinhphiService;

    private final NguonkinhphiQueryService nguonkinhphiQueryService;

    public NguonkinhphiResource(NguonkinhphiService nguonkinhphiService, NguonkinhphiQueryService nguonkinhphiQueryService) {
        this.nguonkinhphiService = nguonkinhphiService;
        this.nguonkinhphiQueryService = nguonkinhphiQueryService;
    }

    /**
     * {@code POST  /nguonkinhphis} : Create a new nguonkinhphi.
     *
     * @param nguonkinhphiDTO the nguonkinhphiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nguonkinhphiDTO, or with status {@code 400 (Bad Request)} if the nguonkinhphi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nguonkinhphis")
    public ResponseEntity<NguonkinhphiDTO> createNguonkinhphi(@RequestBody NguonkinhphiDTO nguonkinhphiDTO) throws URISyntaxException {
        log.debug("REST request to save Nguonkinhphi : {}", nguonkinhphiDTO);
        if (nguonkinhphiDTO.getId() != null) {
            throw new BadRequestAlertException("A new nguonkinhphi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NguonkinhphiDTO result = nguonkinhphiService.save(nguonkinhphiDTO);
        return ResponseEntity.created(new URI("/api/nguonkinhphis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nguonkinhphis} : Updates an existing nguonkinhphi.
     *
     * @param nguonkinhphiDTO the nguonkinhphiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nguonkinhphiDTO,
     * or with status {@code 400 (Bad Request)} if the nguonkinhphiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nguonkinhphiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nguonkinhphis")
    public ResponseEntity<NguonkinhphiDTO> updateNguonkinhphi(@RequestBody NguonkinhphiDTO nguonkinhphiDTO) throws URISyntaxException {
        log.debug("REST request to update Nguonkinhphi : {}", nguonkinhphiDTO);
        if (nguonkinhphiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NguonkinhphiDTO result = nguonkinhphiService.save(nguonkinhphiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nguonkinhphiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nguonkinhphis} : get all the nguonkinhphis.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nguonkinhphis in body.
     */
    @GetMapping("/nguonkinhphis")
    public ResponseEntity<List<NguonkinhphiDTO>> getAllNguonkinhphis(NguonkinhphiCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Nguonkinhphis by criteria: {}", criteria);
        Page<NguonkinhphiDTO> page = nguonkinhphiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /nguonkinhphis/count} : count all the nguonkinhphis.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/nguonkinhphis/count")
    public ResponseEntity<Long> countNguonkinhphis(NguonkinhphiCriteria criteria) {
        log.debug("REST request to count Nguonkinhphis by criteria: {}", criteria);
        return ResponseEntity.ok().body(nguonkinhphiQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nguonkinhphis/:id} : get the "id" nguonkinhphi.
     *
     * @param id the id of the nguonkinhphiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nguonkinhphiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nguonkinhphis/{id}")
    public ResponseEntity<NguonkinhphiDTO> getNguonkinhphi(@PathVariable Long id) {
        log.debug("REST request to get Nguonkinhphi : {}", id);
        Optional<NguonkinhphiDTO> nguonkinhphiDTO = nguonkinhphiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nguonkinhphiDTO);
    }

    /**
     * {@code DELETE  /nguonkinhphis/:id} : delete the "id" nguonkinhphi.
     *
     * @param id the id of the nguonkinhphiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nguonkinhphis/{id}")
    public ResponseEntity<Void> deleteNguonkinhphi(@PathVariable Long id) {
        log.debug("REST request to delete Nguonkinhphi : {}", id);
        nguonkinhphiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
