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

import com.api.backend.domain.Noidungdanhgia;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.NoidungdanhgiaRepository;
import com.api.backend.service.dto.NoidungdanhgiaCriteria;
import com.api.backend.service.dto.NoidungdanhgiaDTO;
import com.api.backend.service.mapper.NoidungdanhgiaMapper;

/**
 * Service for executing complex queries for {@link Noidungdanhgia} entities in the database.
 * The main input is a {@link NoidungdanhgiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoidungdanhgiaDTO} or a {@link Page} of {@link NoidungdanhgiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoidungdanhgiaQueryService extends QueryService<Noidungdanhgia> {

    private final Logger log = LoggerFactory.getLogger(NoidungdanhgiaQueryService.class);

    private final NoidungdanhgiaRepository noidungdanhgiaRepository;

    private final NoidungdanhgiaMapper noidungdanhgiaMapper;

    public NoidungdanhgiaQueryService(NoidungdanhgiaRepository noidungdanhgiaRepository, NoidungdanhgiaMapper noidungdanhgiaMapper) {
        this.noidungdanhgiaRepository = noidungdanhgiaRepository;
        this.noidungdanhgiaMapper = noidungdanhgiaMapper;
    }

    /**
     * Return a {@link List} of {@link NoidungdanhgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoidungdanhgiaDTO> findByCriteria(NoidungdanhgiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Noidungdanhgia> specification = createSpecification(criteria);
        return noidungdanhgiaMapper.toDto(noidungdanhgiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoidungdanhgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoidungdanhgiaDTO> findByCriteria(NoidungdanhgiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Noidungdanhgia> specification = createSpecification(criteria);
        return noidungdanhgiaRepository.findAll(specification, page)
            .map(noidungdanhgiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoidungdanhgiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Noidungdanhgia> specification = createSpecification(criteria);
        return noidungdanhgiaRepository.count(specification);
    }

    /**
     * Function to convert {@link NoidungdanhgiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Noidungdanhgia> createSpecification(NoidungdanhgiaCriteria criteria) {
        Specification<Noidungdanhgia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Noidungdanhgia_.id));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), Noidungdanhgia_.noidung));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Noidungdanhgia_.sudung));
            }
            if (criteria.getDanhgiaCTId() != null) {
                specification = specification.and(buildSpecification(criteria.getDanhgiaCTId(),
                    root -> root.join(Noidungdanhgia_.danhgiaCTS, JoinType.LEFT).get(DanhgiaCT_.id)));
            }
        }
        return specification;
    }
}
