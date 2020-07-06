package com.api.backend.web.rest;

import com.api.backend.service.CoquanphoihopService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.CoquanphoihopDTO;
import com.api.backend.service.dto.CoquanphoihopCriteria;
import com.api.backend.service.CoquanphoihopQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Coquanphoihop}.
 */
@RestController
@RequestMapping("/api")
public class CoquanphoihopResource {

    private final Logger log = LoggerFactory.getLogger(CoquanphoihopResource.class);

    private static final String ENTITY_NAME = "coquanphoihop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoquanphoihopService coquanphoihopService;

    private final CoquanphoihopQueryService coquanphoihopQueryService;

    public CoquanphoihopResource(CoquanphoihopService coquanphoihopService, CoquanphoihopQueryService coquanphoihopQueryService) {
        this.coquanphoihopService = coquanphoihopService;
        this.coquanphoihopQueryService = coquanphoihopQueryService;
    }

    /**
     * {@code POST  /coquanphoihops} : Create a new coquanphoihop.
     *
     * @param coquanphoihopDTO the coquanphoihopDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coquanphoihopDTO, or with status {@code 400 (Bad Request)} if the coquanphoihop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coquanphoihops")
    public ResponseEntity<CoquanphoihopDTO> createCoquanphoihop(@RequestBody CoquanphoihopDTO coquanphoihopDTO) throws URISyntaxException {
        log.debug("REST request to save Coquanphoihop : {}", coquanphoihopDTO);
        if (coquanphoihopDTO.getId() != null) {
            throw new BadRequestAlertException("A new coquanphoihop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoquanphoihopDTO result = coquanphoihopService.save(coquanphoihopDTO);
        return ResponseEntity.created(new URI("/api/coquanphoihops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coquanphoihops} : Updates an existing coquanphoihop.
     *
     * @param coquanphoihopDTO the coquanphoihopDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coquanphoihopDTO,
     * or with status {@code 400 (Bad Request)} if the coquanphoihopDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coquanphoihopDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coquanphoihops")
    public ResponseEntity<CoquanphoihopDTO> updateCoquanphoihop(@RequestBody CoquanphoihopDTO coquanphoihopDTO) throws URISyntaxException {
        log.debug("REST request to update Coquanphoihop : {}", coquanphoihopDTO);
        if (coquanphoihopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoquanphoihopDTO result = coquanphoihopService.save(coquanphoihopDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coquanphoihopDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coquanphoihops} : get all the coquanphoihops.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coquanphoihops in body.
     */
    @GetMapping("/coquanphoihops")
    public ResponseEntity<List<CoquanphoihopDTO>> getAllCoquanphoihops(CoquanphoihopCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Coquanphoihops by criteria: {}", criteria);
        Page<CoquanphoihopDTO> page = coquanphoihopQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /coquanphoihops/count} : count all the coquanphoihops.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/coquanphoihops/count")
    public ResponseEntity<Long> countCoquanphoihops(CoquanphoihopCriteria criteria) {
        log.debug("REST request to count Coquanphoihops by criteria: {}", criteria);
        return ResponseEntity.ok().body(coquanphoihopQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /coquanphoihops/:id} : get the "id" coquanphoihop.
     *
     * @param id the id of the coquanphoihopDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coquanphoihopDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coquanphoihops/{id}")
    public ResponseEntity<CoquanphoihopDTO> getCoquanphoihop(@PathVariable Long id) {
        log.debug("REST request to get Coquanphoihop : {}", id);
        Optional<CoquanphoihopDTO> coquanphoihopDTO = coquanphoihopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coquanphoihopDTO);
    }

    /**
     * {@code DELETE  /coquanphoihops/:id} : delete the "id" coquanphoihop.
     *
     * @param id the id of the coquanphoihopDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coquanphoihops/{id}")
    public ResponseEntity<Void> deleteCoquanphoihop(@PathVariable Long id) {
        log.debug("REST request to delete Coquanphoihop : {}", id);
        coquanphoihopService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
