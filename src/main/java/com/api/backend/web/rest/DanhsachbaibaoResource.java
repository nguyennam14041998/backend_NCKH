package com.api.backend.web.rest;

import com.api.backend.service.DanhsachbaibaoService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.DanhsachbaibaoDTO;
import com.api.backend.service.dto.DanhsachbaibaoCriteria;
import com.api.backend.service.DanhsachbaibaoQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Danhsachbaibao}.
 */
@RestController
@RequestMapping("/api")
public class DanhsachbaibaoResource {

    private final Logger log = LoggerFactory.getLogger(DanhsachbaibaoResource.class);

    private static final String ENTITY_NAME = "danhsachbaibao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DanhsachbaibaoService danhsachbaibaoService;

    private final DanhsachbaibaoQueryService danhsachbaibaoQueryService;

    public DanhsachbaibaoResource(DanhsachbaibaoService danhsachbaibaoService, DanhsachbaibaoQueryService danhsachbaibaoQueryService) {
        this.danhsachbaibaoService = danhsachbaibaoService;
        this.danhsachbaibaoQueryService = danhsachbaibaoQueryService;
    }

    /**
     * {@code POST  /danhsachbaibaos} : Create a new danhsachbaibao.
     *
     * @param danhsachbaibaoDTO the danhsachbaibaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new danhsachbaibaoDTO, or with status {@code 400 (Bad Request)} if the danhsachbaibao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/danhsachbaibaos")
    public ResponseEntity<DanhsachbaibaoDTO> createDanhsachbaibao(@RequestBody DanhsachbaibaoDTO danhsachbaibaoDTO) throws URISyntaxException {
        log.debug("REST request to save Danhsachbaibao : {}", danhsachbaibaoDTO);
        if (danhsachbaibaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new danhsachbaibao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DanhsachbaibaoDTO result = danhsachbaibaoService.save(danhsachbaibaoDTO);
        return ResponseEntity.created(new URI("/api/danhsachbaibaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /danhsachbaibaos} : Updates an existing danhsachbaibao.
     *
     * @param danhsachbaibaoDTO the danhsachbaibaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated danhsachbaibaoDTO,
     * or with status {@code 400 (Bad Request)} if the danhsachbaibaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the danhsachbaibaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/danhsachbaibaos")
    public ResponseEntity<DanhsachbaibaoDTO> updateDanhsachbaibao(@RequestBody DanhsachbaibaoDTO danhsachbaibaoDTO) throws URISyntaxException {
        log.debug("REST request to update Danhsachbaibao : {}", danhsachbaibaoDTO);
        if (danhsachbaibaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DanhsachbaibaoDTO result = danhsachbaibaoService.save(danhsachbaibaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, danhsachbaibaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /danhsachbaibaos} : get all the danhsachbaibaos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of danhsachbaibaos in body.
     */
    @GetMapping("/danhsachbaibaos")
    public ResponseEntity<List<DanhsachbaibaoDTO>> getAllDanhsachbaibaos(DanhsachbaibaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Danhsachbaibaos by criteria: {}", criteria);
        Page<DanhsachbaibaoDTO> page = danhsachbaibaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /danhsachbaibaos/count} : count all the danhsachbaibaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/danhsachbaibaos/count")
    public ResponseEntity<Long> countDanhsachbaibaos(DanhsachbaibaoCriteria criteria) {
        log.debug("REST request to count Danhsachbaibaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(danhsachbaibaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /danhsachbaibaos/:id} : get the "id" danhsachbaibao.
     *
     * @param id the id of the danhsachbaibaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the danhsachbaibaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/danhsachbaibaos/{id}")
    public ResponseEntity<DanhsachbaibaoDTO> getDanhsachbaibao(@PathVariable Long id) {
        log.debug("REST request to get Danhsachbaibao : {}", id);
        Optional<DanhsachbaibaoDTO> danhsachbaibaoDTO = danhsachbaibaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(danhsachbaibaoDTO);
    }

    /**
     * {@code DELETE  /danhsachbaibaos/:id} : delete the "id" danhsachbaibao.
     *
     * @param id the id of the danhsachbaibaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/danhsachbaibaos/{id}")
    public ResponseEntity<Void> deleteDanhsachbaibao(@PathVariable Long id) {
        log.debug("REST request to delete Danhsachbaibao : {}", id);
        danhsachbaibaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
