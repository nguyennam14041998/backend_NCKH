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

import com.api.backend.domain.Nhansuthamgia;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.NhansuthamgiaRepository;
import com.api.backend.service.dto.NhansuthamgiaCriteria;
import com.api.backend.service.dto.NhansuthamgiaDTO;
import com.api.backend.service.mapper.NhansuthamgiaMapper;

/**
 * Service for executing complex queries for {@link Nhansuthamgia} entities in the database.
 * The main input is a {@link NhansuthamgiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhansuthamgiaDTO} or a {@link Page} of {@link NhansuthamgiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhansuthamgiaQueryService extends QueryService<Nhansuthamgia> {

    private final Logger log = LoggerFactory.getLogger(NhansuthamgiaQueryService.class);

    private final NhansuthamgiaRepository nhansuthamgiaRepository;

    private final NhansuthamgiaMapper nhansuthamgiaMapper;

    public NhansuthamgiaQueryService(NhansuthamgiaRepository nhansuthamgiaRepository, NhansuthamgiaMapper nhansuthamgiaMapper) {
        this.nhansuthamgiaRepository = nhansuthamgiaRepository;
        this.nhansuthamgiaMapper = nhansuthamgiaMapper;
    }

    /**
     * Return a {@link List} of {@link NhansuthamgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhansuthamgiaDTO> findByCriteria(NhansuthamgiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nhansuthamgia> specification = createSpecification(criteria);
        return nhansuthamgiaMapper.toDto(nhansuthamgiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NhansuthamgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhansuthamgiaDTO> findByCriteria(NhansuthamgiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nhansuthamgia> specification = createSpecification(criteria);
        return nhansuthamgiaRepository.findAll(specification, page)
            .map(nhansuthamgiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhansuthamgiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nhansuthamgia> specification = createSpecification(criteria);
        return nhansuthamgiaRepository.count(specification);
    }

    /**
     * Function to convert {@link NhansuthamgiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nhansuthamgia> createSpecification(NhansuthamgiaCriteria criteria) {
        Specification<Nhansuthamgia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Nhansuthamgia_.id));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Nhansuthamgia_.sudung));
            }
            if (criteria.getNhansuId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuId(),
                    root -> root.join(Nhansuthamgia_.nhansu, JoinType.LEFT).get(Nhansu_.id)));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Nhansuthamgia_.detai, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
