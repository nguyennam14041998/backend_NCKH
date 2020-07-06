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

import com.api.backend.domain.Coquanphoihop;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.CoquanphoihopRepository;
import com.api.backend.service.dto.CoquanphoihopCriteria;
import com.api.backend.service.dto.CoquanphoihopDTO;
import com.api.backend.service.mapper.CoquanphoihopMapper;

/**
 * Service for executing complex queries for {@link Coquanphoihop} entities in the database.
 * The main input is a {@link CoquanphoihopCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoquanphoihopDTO} or a {@link Page} of {@link CoquanphoihopDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoquanphoihopQueryService extends QueryService<Coquanphoihop> {

    private final Logger log = LoggerFactory.getLogger(CoquanphoihopQueryService.class);

    private final CoquanphoihopRepository coquanphoihopRepository;

    private final CoquanphoihopMapper coquanphoihopMapper;

    public CoquanphoihopQueryService(CoquanphoihopRepository coquanphoihopRepository, CoquanphoihopMapper coquanphoihopMapper) {
        this.coquanphoihopRepository = coquanphoihopRepository;
        this.coquanphoihopMapper = coquanphoihopMapper;
    }

    /**
     * Return a {@link List} of {@link CoquanphoihopDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CoquanphoihopDTO> findByCriteria(CoquanphoihopCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Coquanphoihop> specification = createSpecification(criteria);
        return coquanphoihopMapper.toDto(coquanphoihopRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CoquanphoihopDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CoquanphoihopDTO> findByCriteria(CoquanphoihopCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Coquanphoihop> specification = createSpecification(criteria);
        return coquanphoihopRepository.findAll(specification, page)
            .map(coquanphoihopMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoquanphoihopCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Coquanphoihop> specification = createSpecification(criteria);
        return coquanphoihopRepository.count(specification);
    }

    /**
     * Function to convert {@link CoquanphoihopCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Coquanphoihop> createSpecification(CoquanphoihopCriteria criteria) {
        Specification<Coquanphoihop> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Coquanphoihop_.id));
            }
            if (criteria.getMacoquan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMacoquan(), Coquanphoihop_.macoquan));
            }
            if (criteria.getTencoquan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTencoquan(), Coquanphoihop_.tencoquan));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), Coquanphoihop_.noidung));
            }
            if (criteria.getTendaidien() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTendaidien(), Coquanphoihop_.tendaidien));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Coquanphoihop_.sudung));
            }
            if (criteria.getCoquanphoihopthamgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoquanphoihopthamgiaId(),
                    root -> root.join(Coquanphoihop_.coquanphoihopthamgias, JoinType.LEFT).get(Coquanphoihopthamgia_.id)));
            }
        }
        return specification;
    }
}
