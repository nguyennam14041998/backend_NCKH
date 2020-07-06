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

import com.api.backend.domain.Hocham;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.HochamRepository;
import com.api.backend.service.dto.HochamCriteria;
import com.api.backend.service.dto.HochamDTO;
import com.api.backend.service.mapper.HochamMapper;

/**
 * Service for executing complex queries for {@link Hocham} entities in the database.
 * The main input is a {@link HochamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HochamDTO} or a {@link Page} of {@link HochamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HochamQueryService extends QueryService<Hocham> {

    private final Logger log = LoggerFactory.getLogger(HochamQueryService.class);

    private final HochamRepository hochamRepository;

    private final HochamMapper hochamMapper;

    public HochamQueryService(HochamRepository hochamRepository, HochamMapper hochamMapper) {
        this.hochamRepository = hochamRepository;
        this.hochamMapper = hochamMapper;
    }

    /**
     * Return a {@link List} of {@link HochamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HochamDTO> findByCriteria(HochamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hocham> specification = createSpecification(criteria);
        return hochamMapper.toDto(hochamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HochamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HochamDTO> findByCriteria(HochamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hocham> specification = createSpecification(criteria);
        return hochamRepository.findAll(specification, page)
            .map(hochamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HochamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hocham> specification = createSpecification(criteria);
        return hochamRepository.count(specification);
    }

    /**
     * Function to convert {@link HochamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hocham> createSpecification(HochamCriteria criteria) {
        Specification<Hocham> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hocham_.id));
            }
            if (criteria.getMahocham() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMahocham(), Hocham_.mahocham));
            }
            if (criteria.getTenhocham() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenhocham(), Hocham_.tenhocham));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Hocham_.sudung));
            }
            if (criteria.getNhansuId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuId(),
                    root -> root.join(Hocham_.nhansus, JoinType.LEFT).get(Nhansu_.id)));
            }
        }
        return specification;
    }
}
