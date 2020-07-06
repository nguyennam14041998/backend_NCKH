package com.api.backend.web.rest;

import com.api.backend.service.NoidungdanhgiaService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.NoidungdanhgiaDTO;
import com.api.backend.service.dto.NoidungdanhgiaCriteria;
import com.api.backend.service.NoidungdanhgiaQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.Noidungdanhgia}.
 */
@RestController
@RequestMapping("/api")
public class NoidungdanhgiaResource {

    private final Logger log = LoggerFactory.getLogger(NoidungdanhgiaResource.class);

    private static final String ENTITY_NAME = "noidungdanhgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoidungdanhgiaService noidungdanhgiaService;

    private final NoidungdanhgiaQueryService noidungdanhgiaQueryService;

    public NoidungdanhgiaResource(NoidungdanhgiaService noidungdanhgiaService, NoidungdanhgiaQueryService noidungdanhgiaQueryService) {
        this.noidungdanhgiaService = noidungdanhgiaService;
        this.noidungdanhgiaQueryService = noidungdanhgiaQueryService;
    }

    /**
     * {@code POST  /noidungdanhgias} : Create a new noidungdanhgia.
     *
     * @param noidungdanhgiaDTO the noidungdanhgiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noidungdanhgiaDTO, or with status {@code 400 (Bad Request)} if the noidungdanhgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/noidungdanhgias")
    public ResponseEntity<NoidungdanhgiaDTO> createNoidungdanhgia(@RequestBody NoidungdanhgiaDTO noidungdanhgiaDTO) throws URISyntaxException {
        log.debug("REST request to save Noidungdanhgia : {}", noidungdanhgiaDTO);
        if (noidungdanhgiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new noidungdanhgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoidungdanhgiaDTO result = noidungdanhgiaService.save(noidungdanhgiaDTO);
        return ResponseEntity.created(new URI("/api/noidungdanhgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /noidungdanhgias} : Updates an existing noidungdanhgia.
     *
     * @param noidungdanhgiaDTO the noidungdanhgiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noidungdanhgiaDTO,
     * or with status {@code 400 (Bad Request)} if the noidungdanhgiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noidungdanhgiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/noidungdanhgias")
    public ResponseEntity<NoidungdanhgiaDTO> updateNoidungdanhgia(@RequestBody NoidungdanhgiaDTO noidungdanhgiaDTO) throws URISyntaxException {
        log.debug("REST request to update Noidungdanhgia : {}", noidungdanhgiaDTO);
        if (noidungdanhgiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoidungdanhgiaDTO result = noidungdanhgiaService.save(noidungdanhgiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noidungdanhgiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /noidungdanhgias} : get all the noidungdanhgias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noidungdanhgias in body.
     */
    @GetMapping("/noidungdanhgias")
    public ResponseEntity<List<NoidungdanhgiaDTO>> getAllNoidungdanhgias(NoidungdanhgiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Noidungdanhgias by criteria: {}", criteria);
        Page<NoidungdanhgiaDTO> page = noidungdanhgiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /noidungdanhgias/count} : count all the noidungdanhgias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/noidungdanhgias/count")
    public ResponseEntity<Long> countNoidungdanhgias(NoidungdanhgiaCriteria criteria) {
        log.debug("REST request to count Noidungdanhgias by criteria: {}", criteria);
        return ResponseEntity.ok().body(noidungdanhgiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /noidungdanhgias/:id} : get the "id" noidungdanhgia.
     *
     * @param id the id of the noidungdanhgiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noidungdanhgiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noidungdanhgias/{id}")
    public ResponseEntity<NoidungdanhgiaDTO> getNoidungdanhgia(@PathVariable Long id) {
        log.debug("REST request to get Noidungdanhgia : {}", id);
        Optional<NoidungdanhgiaDTO> noidungdanhgiaDTO = noidungdanhgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noidungdanhgiaDTO);
    }

    /**
     * {@code DELETE  /noidungdanhgias/:id} : delete the "id" noidungdanhgia.
     *
     * @param id the id of the noidungdanhgiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/noidungdanhgias/{id}")
    public ResponseEntity<Void> deleteNoidungdanhgia(@PathVariable Long id) {
        log.debug("REST request to delete Noidungdanhgia : {}", id);
        noidungdanhgiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
