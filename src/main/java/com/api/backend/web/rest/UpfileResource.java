package com.api.backend.web.rest;

import com.api.backend.service.UpfileService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.UpfileDTO;
import com.api.backend.service.dto.UpfileCriteria;
import com.api.backend.service.UpfileQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Upfile}.
 */
@RestController
@RequestMapping("/api")
public class UpfileResource {

    private final Logger log = LoggerFactory.getLogger(UpfileResource.class);

    private static final String ENTITY_NAME = "upfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UpfileService upfileService;

    private final UpfileQueryService upfileQueryService;

    public UpfileResource(UpfileService upfileService, UpfileQueryService upfileQueryService) {
        this.upfileService = upfileService;
        this.upfileQueryService = upfileQueryService;
    }

    /**
     * {@code POST  /upfiles} : Create a new upfile.
     *
     * @param upfileDTO the upfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new upfileDTO, or with status {@code 400 (Bad Request)} if the upfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upfiles")
    public ResponseEntity<UpfileDTO> createUpfile(@RequestBody UpfileDTO upfileDTO) throws URISyntaxException {
        log.debug("REST request to save Upfile : {}", upfileDTO);
        if (upfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new upfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UpfileDTO result = upfileService.save(upfileDTO);
        return ResponseEntity.created(new URI("/api/upfiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upfiles} : Updates an existing upfile.
     *
     * @param upfileDTO the upfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated upfileDTO,
     * or with status {@code 400 (Bad Request)} if the upfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the upfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upfiles")
    public ResponseEntity<UpfileDTO> updateUpfile(@RequestBody UpfileDTO upfileDTO) throws URISyntaxException {
        log.debug("REST request to update Upfile : {}", upfileDTO);
        if (upfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UpfileDTO result = upfileService.save(upfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, upfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /upfiles} : get all the upfiles.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of upfiles in body.
     */
    @GetMapping("/upfiles")
    public ResponseEntity<List<UpfileDTO>> getAllUpfiles(UpfileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Upfiles by criteria: {}", criteria);
        Page<UpfileDTO> page = upfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /upfiles/count} : count all the upfiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/upfiles/count")
    public ResponseEntity<Long> countUpfiles(UpfileCriteria criteria) {
        log.debug("REST request to count Upfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(upfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /upfiles/:id} : get the "id" upfile.
     *
     * @param id the id of the upfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the upfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/upfiles/{id}")
    public ResponseEntity<UpfileDTO> getUpfile(@PathVariable Long id) {
        log.debug("REST request to get Upfile : {}", id);
        Optional<UpfileDTO> upfileDTO = upfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(upfileDTO);
    }

    /**
     * {@code DELETE  /upfiles/:id} : delete the "id" upfile.
     *
     * @param id the id of the upfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/upfiles/{id}")
    public ResponseEntity<Void> deleteUpfile(@PathVariable Long id) {
        log.debug("REST request to delete Upfile : {}", id);
        upfileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
