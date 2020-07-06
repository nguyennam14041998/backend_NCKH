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

import com.api.backend.domain.Danhgia;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DanhgiaRepository;
import com.api.backend.service.dto.DanhgiaCriteria;
import com.api.backend.service.dto.DanhgiaDTO;
import com.api.backend.service.mapper.DanhgiaMapper;

/**
 * Service for executing complex queries for {@link Danhgia} entities in the database.
 * The main input is a {@link DanhgiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DanhgiaDTO} or a {@link Page} of {@link DanhgiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DanhgiaQueryService extends QueryService<Danhgia> {

    private final Logger log = LoggerFactory.getLogger(DanhgiaQueryService.class);

    private final DanhgiaRepository danhgiaRepository;

    private final DanhgiaMapper danhgiaMapper;

    public DanhgiaQueryService(DanhgiaRepository danhgiaRepository, DanhgiaMapper danhgiaMapper) {
        this.danhgiaRepository = danhgiaRepository;
        this.danhgiaMapper = danhgiaMapper;
    }

    /**
     * Return a {@link List} of {@link DanhgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DanhgiaDTO> findByCriteria(DanhgiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Danhgia> specification = createSpecification(criteria);
        return danhgiaMapper.toDto(danhgiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DanhgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DanhgiaDTO> findByCriteria(DanhgiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Danhgia> specification = createSpecification(criteria);
        return danhgiaRepository.findAll(specification, page)
            .map(danhgiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DanhgiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Danhgia> specification = createSpecification(criteria);
        return danhgiaRepository.count(specification);
    }

    /**
     * Function to convert {@link DanhgiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Danhgia> createSpecification(DanhgiaCriteria criteria) {
        Specification<Danhgia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Danhgia_.id));
            }
            if (criteria.getMa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMa(), Danhgia_.ma));
            }
            if (criteria.getTen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTen(), Danhgia_.ten));
            }
            if (criteria.getDiem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiem(), Danhgia_.diem));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), Danhgia_.noidung));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Danhgia_.sudung));
            }
            if (criteria.getDanhgiaCTId() != null) {
                specification = specification.and(buildSpecification(criteria.getDanhgiaCTId(),
                    root -> root.join(Danhgia_.danhgiaCTS, JoinType.LEFT).get(DanhgiaCT_.id)));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Danhgia_.detai, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
