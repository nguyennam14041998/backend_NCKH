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

import com.api.backend.domain.Donvi;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DonviRepository;
import com.api.backend.service.dto.DonviCriteria;
import com.api.backend.service.dto.DonviDTO;
import com.api.backend.service.mapper.DonviMapper;

/**
 * Service for executing complex queries for {@link Donvi} entities in the database.
 * The main input is a {@link DonviCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DonviDTO} or a {@link Page} of {@link DonviDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DonviQueryService extends QueryService<Donvi> {

    private final Logger log = LoggerFactory.getLogger(DonviQueryService.class);

    private final DonviRepository donviRepository;

    private final DonviMapper donviMapper;

    public DonviQueryService(DonviRepository donviRepository, DonviMapper donviMapper) {
        this.donviRepository = donviRepository;
        this.donviMapper = donviMapper;
    }

    /**
     * Return a {@link List} of {@link DonviDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DonviDTO> findByCriteria(DonviCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Donvi> specification = createSpecification(criteria);
        return donviMapper.toDto(donviRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DonviDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DonviDTO> findByCriteria(DonviCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Donvi> specification = createSpecification(criteria);
        return donviRepository.findAll(specification, page)
            .map(donviMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DonviCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Donvi> specification = createSpecification(criteria);
        return donviRepository.count(specification);
    }

    /**
     * Function to convert {@link DonviCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Donvi> createSpecification(DonviCriteria criteria) {
        Specification<Donvi> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Donvi_.id));
            }
            if (criteria.getMadv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMadv(), Donvi_.madv));
            }
            if (criteria.getTendv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTendv(), Donvi_.tendv));
            }
            if (criteria.getDienthoai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDienthoai(), Donvi_.dienthoai));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFax(), Donvi_.fax));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Donvi_.email));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Donvi_.sudung));
            }
            if (criteria.getNhansuId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuId(),
                    root -> root.join(Donvi_.nhansus, JoinType.LEFT).get(Nhansu_.id)));
            }
        }
        return specification;
    }
}
