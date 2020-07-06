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

import com.api.backend.domain.Capdetai;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.CapdetaiRepository;
import com.api.backend.service.dto.CapdetaiCriteria;
import com.api.backend.service.dto.CapdetaiDTO;
import com.api.backend.service.mapper.CapdetaiMapper;

/**
 * Service for executing complex queries for {@link Capdetai} entities in the database.
 * The main input is a {@link CapdetaiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CapdetaiDTO} or a {@link Page} of {@link CapdetaiDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CapdetaiQueryService extends QueryService<Capdetai> {

    private final Logger log = LoggerFactory.getLogger(CapdetaiQueryService.class);

    private final CapdetaiRepository capdetaiRepository;

    private final CapdetaiMapper capdetaiMapper;

    public CapdetaiQueryService(CapdetaiRepository capdetaiRepository, CapdetaiMapper capdetaiMapper) {
        this.capdetaiRepository = capdetaiRepository;
        this.capdetaiMapper = capdetaiMapper;
    }

    /**
     * Return a {@link List} of {@link CapdetaiDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CapdetaiDTO> findByCriteria(CapdetaiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Capdetai> specification = createSpecification(criteria);
        return capdetaiMapper.toDto(capdetaiRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CapdetaiDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CapdetaiDTO> findByCriteria(CapdetaiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Capdetai> specification = createSpecification(criteria);
        return capdetaiRepository.findAll(specification, page)
            .map(capdetaiMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CapdetaiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Capdetai> specification = createSpecification(criteria);
        return capdetaiRepository.count(specification);
    }

    /**
     * Function to convert {@link CapdetaiCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Capdetai> createSpecification(CapdetaiCriteria criteria) {
        Specification<Capdetai> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Capdetai_.id));
            }
            if (criteria.getMacapdetai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMacapdetai(), Capdetai_.macapdetai));
            }
            if (criteria.getTencapdetai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTencapdetai(), Capdetai_.tencapdetai));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Capdetai_.sudung));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Capdetai_.detais, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
