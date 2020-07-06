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

import com.api.backend.domain.DanhgiaCT;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DanhgiaCTRepository;
import com.api.backend.service.dto.DanhgiaCTCriteria;
import com.api.backend.service.dto.DanhgiaCTDTO;
import com.api.backend.service.mapper.DanhgiaCTMapper;

/**
 * Service for executing complex queries for {@link DanhgiaCT} entities in the database.
 * The main input is a {@link DanhgiaCTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DanhgiaCTDTO} or a {@link Page} of {@link DanhgiaCTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DanhgiaCTQueryService extends QueryService<DanhgiaCT> {

    private final Logger log = LoggerFactory.getLogger(DanhgiaCTQueryService.class);

    private final DanhgiaCTRepository danhgiaCTRepository;

    private final DanhgiaCTMapper danhgiaCTMapper;

    public DanhgiaCTQueryService(DanhgiaCTRepository danhgiaCTRepository, DanhgiaCTMapper danhgiaCTMapper) {
        this.danhgiaCTRepository = danhgiaCTRepository;
        this.danhgiaCTMapper = danhgiaCTMapper;
    }

    /**
     * Return a {@link List} of {@link DanhgiaCTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DanhgiaCTDTO> findByCriteria(DanhgiaCTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DanhgiaCT> specification = createSpecification(criteria);
        return danhgiaCTMapper.toDto(danhgiaCTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DanhgiaCTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DanhgiaCTDTO> findByCriteria(DanhgiaCTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DanhgiaCT> specification = createSpecification(criteria);
        return danhgiaCTRepository.findAll(specification, page)
            .map(danhgiaCTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DanhgiaCTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DanhgiaCT> specification = createSpecification(criteria);
        return danhgiaCTRepository.count(specification);
    }

    /**
     * Function to convert {@link DanhgiaCTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DanhgiaCT> createSpecification(DanhgiaCTCriteria criteria) {
        Specification<DanhgiaCT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DanhgiaCT_.id));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), DanhgiaCT_.noidung));
            }
            if (criteria.getDiem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiem(), DanhgiaCT_.diem));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), DanhgiaCT_.sudung));
            }
            if (criteria.getDanhgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getDanhgiaId(),
                    root -> root.join(DanhgiaCT_.danhgia, JoinType.LEFT).get(Danhgia_.id)));
            }
            if (criteria.getNoidungdanhgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getNoidungdanhgiaId(),
                    root -> root.join(DanhgiaCT_.noidungdanhgia, JoinType.LEFT).get(Noidungdanhgia_.id)));
            }
        }
        return specification;
    }
}
