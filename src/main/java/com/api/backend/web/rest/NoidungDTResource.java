package com.api.backend.web.rest;

import com.api.backend.service.NoidungDTService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.NoidungDTDTO;
import com.api.backend.service.dto.NoidungDTCriteria;
import com.api.backend.service.NoidungDTQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.NoidungDT}.
 */
@RestController
@RequestMapping("/api")
public class NoidungDTResource {

    private final Logger log = LoggerFactory.getLogger(NoidungDTResource.class);

    private static final String ENTITY_NAME = "noidungDT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoidungDTService noidungDTService;

    private final NoidungDTQueryService noidungDTQueryService;

    public NoidungDTResource(NoidungDTService noidungDTService, NoidungDTQueryService noidungDTQueryService) {
        this.noidungDTService = noidungDTService;
        this.noidungDTQueryService = noidungDTQueryService;
    }

    /**
     * {@code POST  /noidung-dts} : Create a new noidungDT.
     *
     * @param noidungDTDTO the noidungDTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noidungDTDTO, or with status {@code 400 (Bad Request)} if the noidungDT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/noidung-dts")
    public ResponseEntity<NoidungDTDTO> createNoidungDT(@RequestBody NoidungDTDTO noidungDTDTO) throws URISyntaxException {
        log.debug("REST request to save NoidungDT : {}", noidungDTDTO);
        if (noidungDTDTO.getId() != null) {
            throw new BadRequestAlertException("A new noidungDT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoidungDTDTO result = noidungDTService.save(noidungDTDTO);
        return ResponseEntity.created(new URI("/api/noidung-dts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /noidung-dts} : Updates an existing noidungDT.
     *
     * @param noidungDTDTO the noidungDTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noidungDTDTO,
     * or with status {@code 400 (Bad Request)} if the noidungDTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noidungDTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/noidung-dts")
    public ResponseEntity<NoidungDTDTO> updateNoidungDT(@RequestBody NoidungDTDTO noidungDTDTO) throws URISyntaxException {
        log.debug("REST request to update NoidungDT : {}", noidungDTDTO);
        if (noidungDTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoidungDTDTO result = noidungDTService.save(noidungDTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noidungDTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /noidung-dts} : get all the noidungDTS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noidungDTS in body.
     */
    @GetMapping("/noidung-dts")
    public ResponseEntity<List<NoidungDTDTO>> getAllNoidungDTS(NoidungDTCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NoidungDTS by criteria: {}", criteria);
        Page<NoidungDTDTO> page = noidungDTQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /noidung-dts/count} : count all the noidungDTS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/noidung-dts/count")
    public ResponseEntity<Long> countNoidungDTS(NoidungDTCriteria criteria) {
        log.debug("REST request to count NoidungDTS by criteria: {}", criteria);
        return ResponseEntity.ok().body(noidungDTQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /noidung-dts/:id} : get the "id" noidungDT.
     *
     * @param id the id of the noidungDTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noidungDTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/noidung-dts/{id}")
    public ResponseEntity<NoidungDTDTO> getNoidungDT(@PathVariable Long id) {
        log.debug("REST request to get NoidungDT : {}", id);
        Optional<NoidungDTDTO> noidungDTDTO = noidungDTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noidungDTDTO);
    }

    /**
     * {@code DELETE  /noidung-dts/:id} : delete the "id" noidungDT.
     *
     * @param id the id of the noidungDTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/noidung-dts/{id}")
    public ResponseEntity<Void> deleteNoidungDT(@PathVariable Long id) {
        log.debug("REST request to delete NoidungDT : {}", id);
        noidungDTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
