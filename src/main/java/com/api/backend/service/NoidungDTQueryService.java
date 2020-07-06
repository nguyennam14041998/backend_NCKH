package com.api.backend.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.api.backend.domain.NoidungDT;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.NoidungDTRepository;
import com.api.backend.service.dto.NoidungDTCriteria;
import com.api.backend.service.dto.NoidungDTDTO;
import com.api.backend.service.mapper.NoidungDTMapper;

/**
 * Service for executing complex queries for {@link NoidungDT} entities in the database.
 * The main input is a {@link NoidungDTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoidungDTDTO} or a {@link Page} of {@link NoidungDTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoidungDTQueryService extends QueryService<NoidungDT> {

    private final Logger log = LoggerFactory.getLogger(NoidungDTQueryService.class);

    private final NoidungDTRepository noidungDTRepository;

    private final NoidungDTMapper noidungDTMapper;

    public NoidungDTQueryService(NoidungDTRepository noidungDTRepository, NoidungDTMapper noidungDTMapper) {
        this.noidungDTRepository = noidungDTRepository;
        this.noidungDTMapper = noidungDTMapper;
    }

    /**
     * Return a {@link List} of {@link NoidungDTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoidungDTDTO> findByCriteria(NoidungDTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NoidungDT> specification = createSpecification(criteria);
        return noidungDTMapper.toDto(noidungDTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoidungDTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoidungDTDTO> findByCriteria(NoidungDTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NoidungDT> specification = createSpecification(criteria);
        return noidungDTRepository.findAll(specification, page)
            .map(noidungDTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoidungDTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NoidungDT> specification = createSpecification(criteria);
        return noidungDTRepository.count(specification);
    }

    /**
     * Function to convert {@link NoidungDTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NoidungDT> createSpecification(NoidungDTCriteria criteria) {
        Specification<NoidungDT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NoidungDT_.id));
            }
            if (criteria.getTennoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTennoidung(), NoidungDT_.tennoidung));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), NoidungDT_.sudung));
            }
            if (criteria.getDutoanKPCTId() != null) {
                specification = specification.and(buildSpecification(criteria.getDutoanKPCTId(),
                    root -> root.join(NoidungDT_.dutoanKPCTS, JoinType.LEFT).get(DutoanKPCT_.id)));
            }
        }
        return specification;
    }
}
