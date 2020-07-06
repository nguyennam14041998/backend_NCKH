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

import com.api.backend.domain.Linhvuc;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.LinhvucRepository;
import com.api.backend.service.dto.LinhvucCriteria;
import com.api.backend.service.dto.LinhvucDTO;
import com.api.backend.service.mapper.LinhvucMapper;

/**
 * Service for executing complex queries for {@link Linhvuc} entities in the database.
 * The main input is a {@link LinhvucCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LinhvucDTO} or a {@link Page} of {@link LinhvucDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LinhvucQueryService extends QueryService<Linhvuc> {

    private final Logger log = LoggerFactory.getLogger(LinhvucQueryService.class);

    private final LinhvucRepository linhvucRepository;

    private final LinhvucMapper linhvucMapper;

    public LinhvucQueryService(LinhvucRepository linhvucRepository, LinhvucMapper linhvucMapper) {
        this.linhvucRepository = linhvucRepository;
        this.linhvucMapper = linhvucMapper;
    }

    /**
     * Return a {@link List} of {@link LinhvucDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LinhvucDTO> findByCriteria(LinhvucCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Linhvuc> specification = createSpecification(criteria);
        return linhvucMapper.toDto(linhvucRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LinhvucDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LinhvucDTO> findByCriteria(LinhvucCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Linhvuc> specification = createSpecification(criteria);
        return linhvucRepository.findAll(specification, page)
            .map(linhvucMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LinhvucCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Linhvuc> specification = createSpecification(criteria);
        return linhvucRepository.count(specification);
    }

    /**
     * Function to convert {@link LinhvucCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Linhvuc> createSpecification(LinhvucCriteria criteria) {
        Specification<Linhvuc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Linhvuc_.id));
            }
            if (criteria.getMalv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMalv(), Linhvuc_.malv));
            }
            if (criteria.getTenlv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenlv(), Linhvuc_.tenlv));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Linhvuc_.sudung));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Linhvuc_.detais, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
