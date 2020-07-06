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

import com.api.backend.domain.DutoanKP;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DutoanKPRepository;
import com.api.backend.service.dto.DutoanKPCriteria;
import com.api.backend.service.dto.DutoanKPDTO;
import com.api.backend.service.mapper.DutoanKPMapper;

/**
 * Service for executing complex queries for {@link DutoanKP} entities in the database.
 * The main input is a {@link DutoanKPCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DutoanKPDTO} or a {@link Page} of {@link DutoanKPDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DutoanKPQueryService extends QueryService<DutoanKP> {

    private final Logger log = LoggerFactory.getLogger(DutoanKPQueryService.class);

    private final DutoanKPRepository dutoanKPRepository;

    private final DutoanKPMapper dutoanKPMapper;

    public DutoanKPQueryService(DutoanKPRepository dutoanKPRepository, DutoanKPMapper dutoanKPMapper) {
        this.dutoanKPRepository = dutoanKPRepository;
        this.dutoanKPMapper = dutoanKPMapper;
    }

    /**
     * Return a {@link List} of {@link DutoanKPDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DutoanKPDTO> findByCriteria(DutoanKPCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DutoanKP> specification = createSpecification(criteria);
        return dutoanKPMapper.toDto(dutoanKPRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DutoanKPDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DutoanKPDTO> findByCriteria(DutoanKPCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DutoanKP> specification = createSpecification(criteria);
        return dutoanKPRepository.findAll(specification, page)
            .map(dutoanKPMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DutoanKPCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DutoanKP> specification = createSpecification(criteria);
        return dutoanKPRepository.count(specification);
    }

    /**
     * Function to convert {@link DutoanKPCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DutoanKP> createSpecification(DutoanKPCriteria criteria) {
        Specification<DutoanKP> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DutoanKP_.id));
            }
            if (criteria.getMadutoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMadutoan(), DutoanKP_.madutoan));
            }
            if (criteria.getTendutoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTendutoan(), DutoanKP_.tendutoan));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), DutoanKP_.noidung));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), DutoanKP_.sudung));
            }
            if (criteria.getDutoanKPCTId() != null) {
                specification = specification.and(buildSpecification(criteria.getDutoanKPCTId(),
                    root -> root.join(DutoanKP_.dutoanKPCTS, JoinType.LEFT).get(DutoanKPCT_.id)));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(DutoanKP_.detai, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
