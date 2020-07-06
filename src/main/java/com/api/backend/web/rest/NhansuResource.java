package com.api.backend.web.rest;

import com.api.backend.service.NhansuService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.NhansuDTO;
import com.api.backend.service.dto.NhansuCriteria;
import com.api.backend.service.NhansuQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Nhansu}.
 */
@RestController
@RequestMapping("/api")
public class NhansuResource {

    private final Logger log = LoggerFactory.getLogger(NhansuResource.class);

    private static final String ENTITY_NAME = "nhansu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhansuService nhansuService;

    private final NhansuQueryService nhansuQueryService;

    public NhansuResource(NhansuService nhansuService, NhansuQueryService nhansuQueryService) {
        this.nhansuService = nhansuService;
        this.nhansuQueryService = nhansuQueryService;
    }

    /**
     * {@code POST  /nhansus} : Create a new nhansu.
     *
     * @param nhansuDTO the nhansuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhansuDTO, or with status {@code 400 (Bad Request)} if the nhansu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhansus")
    public ResponseEntity<NhansuDTO> createNhansu(@RequestBody NhansuDTO nhansuDTO) throws URISyntaxException {
        log.debug("REST request to save Nhansu : {}", nhansuDTO);
        if (nhansuDTO.getId() != null) {
            throw new BadRequestAlertException("A new nhansu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhansuDTO result = nhansuService.save(nhansuDTO);
        return ResponseEntity.created(new URI("/api/nhansus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhansus} : Updates an existing nhansu.
     *
     * @param nhansuDTO the nhansuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhansuDTO,
     * or with status {@code 400 (Bad Request)} if the nhansuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhansuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhansus")
    public ResponseEntity<NhansuDTO> updateNhansu(@RequestBody NhansuDTO nhansuDTO) throws URISyntaxException {
        log.debug("REST request to update Nhansu : {}", nhansuDTO);
        if (nhansuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhansuDTO result = nhansuService.save(nhansuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhansuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nhansus} : get all the nhansus.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhansus in body.
     */
    @GetMapping("/nhansus")
    public ResponseEntity<List<NhansuDTO>> getAllNhansus(NhansuCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Nhansus by criteria: {}", criteria);
        Page<NhansuDTO> page = nhansuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /nhansus/count} : count all the nhansus.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/nhansus/count")
    public ResponseEntity<Long> countNhansus(NhansuCriteria criteria) {
        log.debug("REST request to count Nhansus by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhansuQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nhansus/:id} : get the "id" nhansu.
     *
     * @param id the id of the nhansuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhansuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhansus/{id}")
    public ResponseEntity<NhansuDTO> getNhansu(@PathVariable Long id) {
        log.debug("REST request to get Nhansu : {}", id);
        Optional<NhansuDTO> nhansuDTO = nhansuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhansuDTO);
    }

    /**
     * {@code DELETE  /nhansus/:id} : delete the "id" nhansu.
     *
     * @param id the id of the nhansuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhansus/{id}")
    public ResponseEntity<Void> deleteNhansu(@PathVariable Long id) {
        log.debug("REST request to delete Nhansu : {}", id);
        nhansuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
