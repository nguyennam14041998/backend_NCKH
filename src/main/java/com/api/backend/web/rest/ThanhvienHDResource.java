package com.api.backend.web.rest;

import com.api.backend.service.ThanhvienHDService;
import com.api.backend.web.rest.errors.BadRequestAlertException;
import com.api.backend.service.dto.ThanhvienHDDTO;
import com.api.backend.service.dto.ThanhvienHDCriteria;
import com.api.backend.service.ThanhvienHDQueryService;

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
 * REST controller for managing {@link com.api.backend.domain.ThanhvienHD}.
 */
@RestController
@RequestMapping("/api")
public class ThanhvienHDResource {

    private final Logger log = LoggerFactory.getLogger(ThanhvienHDResource.class);

    private static final String ENTITY_NAME = "thanhvienHD";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThanhvienHDService thanhvienHDService;

    private final ThanhvienHDQueryService thanhvienHDQueryService;

    public ThanhvienHDResource(ThanhvienHDService thanhvienHDService, ThanhvienHDQueryService thanhvienHDQueryService) {
        this.thanhvienHDService = thanhvienHDService;
        this.thanhvienHDQueryService = thanhvienHDQueryService;
    }

    /**
     * {@code POST  /thanhvien-hds} : Create a new thanhvienHD.
     *
     * @param thanhvienHDDTO the thanhvienHDDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thanhvienHDDTO, or with status {@code 400 (Bad Request)} if the thanhvienHD has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thanhvien-hds")
    public ResponseEntity<ThanhvienHDDTO> createThanhvienHD(@RequestBody ThanhvienHDDTO thanhvienHDDTO) throws URISyntaxException {
        log.debug("REST request to save ThanhvienHD : {}", thanhvienHDDTO);
        if (thanhvienHDDTO.getId() != null) {
            throw new BadRequestAlertException("A new thanhvienHD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThanhvienHDDTO result = thanhvienHDService.save(thanhvienHDDTO);
        return ResponseEntity.created(new URI("/api/thanhvien-hds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thanhvien-hds} : Updates an existing thanhvienHD.
     *
     * @param thanhvienHDDTO the thanhvienHDDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thanhvienHDDTO,
     * or with status {@code 400 (Bad Request)} if the thanhvienHDDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thanhvienHDDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thanhvien-hds")
    public ResponseEntity<ThanhvienHDDTO> updateThanhvienHD(@RequestBody ThanhvienHDDTO thanhvienHDDTO) throws URISyntaxException {
        log.debug("REST request to update ThanhvienHD : {}", thanhvienHDDTO);
        if (thanhvienHDDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThanhvienHDDTO result = thanhvienHDService.save(thanhvienHDDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thanhvienHDDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /thanhvien-hds} : get all the thanhvienHDS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thanhvienHDS in body.
     */
    @GetMapping("/thanhvien-hds")
    public ResponseEntity<List<ThanhvienHDDTO>> getAllThanhvienHDS(ThanhvienHDCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ThanhvienHDS by criteria: {}", criteria);
        Page<ThanhvienHDDTO> page = thanhvienHDQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /thanhvien-hds/count} : count all the thanhvienHDS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/thanhvien-hds/count")
    public ResponseEntity<Long> countThanhvienHDS(ThanhvienHDCriteria criteria) {
        log.debug("REST request to count ThanhvienHDS by criteria: {}", criteria);
        return ResponseEntity.ok().body(thanhvienHDQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /thanhvien-hds/:id} : get the "id" thanhvienHD.
     *
     * @param id the id of the thanhvienHDDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thanhvienHDDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thanhvien-hds/{id}")
    public ResponseEntity<ThanhvienHDDTO> getThanhvienHD(@PathVariable Long id) {
        log.debug("REST request to get ThanhvienHD : {}", id);
        Optional<ThanhvienHDDTO> thanhvienHDDTO = thanhvienHDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thanhvienHDDTO);
    }

    /**
     * {@code DELETE  /thanhvien-hds/:id} : delete the "id" thanhvienHD.
     *
     * @param id the id of the thanhvienHDDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thanhvien-hds/{id}")
    public ResponseEntity<Void> deleteThanhvienHD(@PathVariable Long id) {
        log.debug("REST request to delete ThanhvienHD : {}", id);
        thanhvienHDService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
