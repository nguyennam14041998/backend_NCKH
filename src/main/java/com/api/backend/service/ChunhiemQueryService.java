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

import com.api.backend.domain.Chunhiem;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.ChunhiemRepository;
import com.api.backend.service.dto.ChunhiemCriteria;
import com.api.backend.service.dto.ChunhiemDTO;
import com.api.backend.service.mapper.ChunhiemMapper;

/**
 * Service for executing complex queries for {@link Chunhiem} entities in the database.
 * The main input is a {@link ChunhiemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChunhiemDTO} or a {@link Page} of {@link ChunhiemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChunhiemQueryService extends QueryService<Chunhiem> {

    private final Logger log = LoggerFactory.getLogger(ChunhiemQueryService.class);

    private final ChunhiemRepository chunhiemRepository;

    private final ChunhiemMapper chunhiemMapper;

    public ChunhiemQueryService(ChunhiemRepository chunhiemRepository, ChunhiemMapper chunhiemMapper) {
        this.chunhiemRepository = chunhiemRepository;
        this.chunhiemMapper = chunhiemMapper;
    }

    /**
     * Return a {@link List} of {@link ChunhiemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChunhiemDTO> findByCriteria(ChunhiemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chunhiem> specification = createSpecification(criteria);
        return chunhiemMapper.toDto(chunhiemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChunhiemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChunhiemDTO> findByCriteria(ChunhiemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chunhiem> specification = createSpecification(criteria);
        return chunhiemRepository.findAll(specification, page)
            .map(chunhiemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChunhiemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chunhiem> specification = createSpecification(criteria);
        return chunhiemRepository.count(specification);
    }

    /**
     * Function to convert {@link ChunhiemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Chunhiem> createSpecification(ChunhiemCriteria criteria) {
        Specification<Chunhiem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Chunhiem_.id));
            }
            if (criteria.getTen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTen(), Chunhiem_.ten));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Chunhiem_.sudung));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Chunhiem_.detais, JoinType.LEFT).get(Detai_.id)));
            }
            if (criteria.getNhansuId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuId(),
                    root -> root.join(Chunhiem_.nhansu, JoinType.LEFT).get(Nhansu_.id)));
            }
        }
        return specification;
    }
}
