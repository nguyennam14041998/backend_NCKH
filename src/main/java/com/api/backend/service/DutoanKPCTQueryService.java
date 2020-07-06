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

import com.api.backend.domain.DutoanKPCT;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DutoanKPCTRepository;
import com.api.backend.service.dto.DutoanKPCTCriteria;
import com.api.backend.service.dto.DutoanKPCTDTO;
import com.api.backend.service.mapper.DutoanKPCTMapper;

/**
 * Service for executing complex queries for {@link DutoanKPCT} entities in the database.
 * The main input is a {@link DutoanKPCTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DutoanKPCTDTO} or a {@link Page} of {@link DutoanKPCTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DutoanKPCTQueryService extends QueryService<DutoanKPCT> {

    private final Logger log = LoggerFactory.getLogger(DutoanKPCTQueryService.class);

    private final DutoanKPCTRepository dutoanKPCTRepository;

    private final DutoanKPCTMapper dutoanKPCTMapper;

    public DutoanKPCTQueryService(DutoanKPCTRepository dutoanKPCTRepository, DutoanKPCTMapper dutoanKPCTMapper) {
        this.dutoanKPCTRepository = dutoanKPCTRepository;
        this.dutoanKPCTMapper = dutoanKPCTMapper;
    }

    /**
     * Return a {@link List} of {@link DutoanKPCTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DutoanKPCTDTO> findByCriteria(DutoanKPCTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DutoanKPCT> specification = createSpecification(criteria);
        return dutoanKPCTMapper.toDto(dutoanKPCTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DutoanKPCTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DutoanKPCTDTO> findByCriteria(DutoanKPCTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DutoanKPCT> specification = createSpecification(criteria);
        return dutoanKPCTRepository.findAll(specification, page)
            .map(dutoanKPCTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DutoanKPCTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DutoanKPCT> specification = createSpecification(criteria);
        return dutoanKPCTRepository.count(specification);
    }

    /**
     * Function to convert {@link DutoanKPCTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DutoanKPCT> createSpecification(DutoanKPCTCriteria criteria) {
        Specification<DutoanKPCT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DutoanKPCT_.id));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), DutoanKPCT_.noidung));
            }
            if (criteria.getSoluong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSoluong(), DutoanKPCT_.soluong));
            }
            if (criteria.getMucchi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMucchi(), DutoanKPCT_.mucchi));
            }
            if (criteria.getTong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTong(), DutoanKPCT_.tong));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), DutoanKPCT_.sudung));
            }
            if (criteria.getDutoanKPId() != null) {
                specification = specification.and(buildSpecification(criteria.getDutoanKPId(),
                    root -> root.join(DutoanKPCT_.dutoanKP, JoinType.LEFT).get(DutoanKP_.id)));
            }
            if (criteria.getNoidungDTId() != null) {
                specification = specification.and(buildSpecification(criteria.getNoidungDTId(),
                    root -> root.join(DutoanKPCT_.noidungDT, JoinType.LEFT).get(NoidungDT_.id)));
            }
        }
        return specification;
    }
}
