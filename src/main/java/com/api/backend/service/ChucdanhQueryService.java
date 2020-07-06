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

import com.api.backend.domain.Chucdanh;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.ChucdanhRepository;
import com.api.backend.service.dto.ChucdanhCriteria;
import com.api.backend.service.dto.ChucdanhDTO;
import com.api.backend.service.mapper.ChucdanhMapper;

/**
 * Service for executing complex queries for {@link Chucdanh} entities in the database.
 * The main input is a {@link ChucdanhCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChucdanhDTO} or a {@link Page} of {@link ChucdanhDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChucdanhQueryService extends QueryService<Chucdanh> {

    private final Logger log = LoggerFactory.getLogger(ChucdanhQueryService.class);

    private final ChucdanhRepository chucdanhRepository;

    private final ChucdanhMapper chucdanhMapper;

    public ChucdanhQueryService(ChucdanhRepository chucdanhRepository, ChucdanhMapper chucdanhMapper) {
        this.chucdanhRepository = chucdanhRepository;
        this.chucdanhMapper = chucdanhMapper;
    }

    /**
     * Return a {@link List} of {@link ChucdanhDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChucdanhDTO> findByCriteria(ChucdanhCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chucdanh> specification = createSpecification(criteria);
        return chucdanhMapper.toDto(chucdanhRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChucdanhDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChucdanhDTO> findByCriteria(ChucdanhCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chucdanh> specification = createSpecification(criteria);
        return chucdanhRepository.findAll(specification, page)
            .map(chucdanhMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChucdanhCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chucdanh> specification = createSpecification(criteria);
        return chucdanhRepository.count(specification);
    }

    /**
     * Function to convert {@link ChucdanhCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Chucdanh> createSpecification(ChucdanhCriteria criteria) {
        Specification<Chucdanh> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Chucdanh_.id));
            }
            if (criteria.getMachucdanh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMachucdanh(), Chucdanh_.machucdanh));
            }
            if (criteria.getTenchucdanh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenchucdanh(), Chucdanh_.tenchucdanh));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Chucdanh_.sudung));
            }
            if (criteria.getNhansuId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuId(),
                    root -> root.join(Chucdanh_.nhansus, JoinType.LEFT).get(Nhansu_.id)));
            }
        }
        return specification;
    }
}
