package com.api.backend.web.rest;

import com.api.backend.service.TiendoService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.TiendoDTO;
import com.api.backend.service.dto.TiendoCriteria;
import com.api.backend.service.TiendoQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Tiendo}.
 */
@RestController
@RequestMapping("/api")
public class TiendoResource {

    private final Logger log = LoggerFactory.getLogger(TiendoResource.class);

    private static final String ENTITY_NAME = "tiendo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiendoService tiendoService;

    private final TiendoQueryService tiendoQueryService;

    public TiendoResource(TiendoService tiendoService, TiendoQueryService tiendoQueryService) {
        this.tiendoService = tiendoService;
        this.tiendoQueryService = tiendoQueryService;
    }

    /**
     * {@code POST  /tiendos} : Create a new tiendo.
     *
     * @param tiendoDTO the tiendoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tiendoDTO, or with status {@code 400 (Bad Request)} if the tiendo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tiendos")
    public ResponseEntity<TiendoDTO> createTiendo(@RequestBody TiendoDTO tiendoDTO) throws URISyntaxException {
        log.debug("REST request to save Tiendo : {}", tiendoDTO);
        if (tiendoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tiendo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiendoDTO result = tiendoService.save(tiendoDTO);
        return ResponseEntity.created(new URI("/api/tiendos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tiendos} : Updates an existing tiendo.
     *
     * @param tiendoDTO the tiendoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiendoDTO,
     * or with status {@code 400 (Bad Request)} if the tiendoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiendoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tiendos")
    public ResponseEntity<TiendoDTO> updateTiendo(@RequestBody TiendoDTO tiendoDTO) throws URISyntaxException {
        log.debug("REST request to update Tiendo : {}", tiendoDTO);
        if (tiendoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TiendoDTO result = tiendoService.save(tiendoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tiendoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tiendos} : get all the tiendos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiendos in body.
     */
    @GetMapping("/tiendos")
    public ResponseEntity<List<TiendoDTO>> getAllTiendos(TiendoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tiendos by criteria: {}", criteria);
        Page<TiendoDTO> page = tiendoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tiendos/count} : count all the tiendos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tiendos/count")
    public ResponseEntity<Long> countTiendos(TiendoCriteria criteria) {
        log.debug("REST request to count Tiendos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tiendoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tiendos/:id} : get the "id" tiendo.
     *
     * @param id the id of the tiendoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tiendoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tiendos/{id}")
    public ResponseEntity<TiendoDTO> getTiendo(@PathVariable Long id) {
        log.debug("REST request to get Tiendo : {}", id);
        Optional<TiendoDTO> tiendoDTO = tiendoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tiendoDTO);
    }

    /**
     * {@code DELETE  /tiendos/:id} : delete the "id" tiendo.
     *
     * @param id the id of the tiendoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tiendos/{id}")
    public ResponseEntity<Void> deleteTiendo(@PathVariable Long id) {
        log.debug("REST request to delete Tiendo : {}", id);
        tiendoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
