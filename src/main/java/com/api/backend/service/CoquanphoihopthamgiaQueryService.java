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

import com.api.backend.domain.Coquanphoihopthamgia;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.CoquanphoihopthamgiaRepository;
import com.api.backend.service.dto.CoquanphoihopthamgiaCriteria;
import com.api.backend.service.dto.CoquanphoihopthamgiaDTO;
import com.api.backend.service.mapper.CoquanphoihopthamgiaMapper;

/**
 * Service for executing complex queries for {@link Coquanphoihopthamgia} entities in the database.
 * The main input is a {@link CoquanphoihopthamgiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoquanphoihopthamgiaDTO} or a {@link Page} of {@link CoquanphoihopthamgiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoquanphoihopthamgiaQueryService extends QueryService<Coquanphoihopthamgia> {

    private final Logger log = LoggerFactory.getLogger(CoquanphoihopthamgiaQueryService.class);

    private final CoquanphoihopthamgiaRepository coquanphoihopthamgiaRepository;

    private final CoquanphoihopthamgiaMapper coquanphoihopthamgiaMapper;

    public CoquanphoihopthamgiaQueryService(CoquanphoihopthamgiaRepository coquanphoihopthamgiaRepository, CoquanphoihopthamgiaMapper coquanphoihopthamgiaMapper) {
        this.coquanphoihopthamgiaRepository = coquanphoihopthamgiaRepository;
        this.coquanphoihopthamgiaMapper = coquanphoihopthamgiaMapper;
    }

    /**
     * Return a {@link List} of {@link CoquanphoihopthamgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CoquanphoihopthamgiaDTO> findByCriteria(CoquanphoihopthamgiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Coquanphoihopthamgia> specification = createSpecification(criteria);
        return coquanphoihopthamgiaMapper.toDto(coquanphoihopthamgiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CoquanphoihopthamgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CoquanphoihopthamgiaDTO> findByCriteria(CoquanphoihopthamgiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Coquanphoihopthamgia> specification = createSpecification(criteria);
        return coquanphoihopthamgiaRepository.findAll(specification, page)
            .map(coquanphoihopthamgiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoquanphoihopthamgiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Coquanphoihopthamgia> specification = createSpecification(criteria);
        return coquanphoihopthamgiaRepository.count(specification);
    }

    /**
     * Function to convert {@link CoquanphoihopthamgiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Coquanphoihopthamgia> createSpecification(CoquanphoihopthamgiaCriteria criteria) {
        Specification<Coquanphoihopthamgia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Coquanphoihopthamgia_.id));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Coquanphoihopthamgia_.sudung));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Coquanphoihopthamgia_.detai, JoinType.LEFT).get(Detai_.id)));
            }
            if (criteria.getCoquanphoihopId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoquanphoihopId(),
                    root -> root.join(Coquanphoihopthamgia_.coquanphoihop, JoinType.LEFT).get(Coquanphoihop_.id)));
            }
        }
        return specification;
    }
}
