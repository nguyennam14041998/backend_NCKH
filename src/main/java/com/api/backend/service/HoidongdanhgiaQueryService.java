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

import com.api.backend.domain.Hoidongdanhgia;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.HoidongdanhgiaRepository;
import com.api.backend.service.dto.HoidongdanhgiaCriteria;
import com.api.backend.service.dto.HoidongdanhgiaDTO;
import com.api.backend.service.mapper.HoidongdanhgiaMapper;

/**
 * Service for executing complex queries for {@link Hoidongdanhgia} entities in the database.
 * The main input is a {@link HoidongdanhgiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HoidongdanhgiaDTO} or a {@link Page} of {@link HoidongdanhgiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HoidongdanhgiaQueryService extends QueryService<Hoidongdanhgia> {

    private final Logger log = LoggerFactory.getLogger(HoidongdanhgiaQueryService.class);

    private final HoidongdanhgiaRepository hoidongdanhgiaRepository;

    private final HoidongdanhgiaMapper hoidongdanhgiaMapper;

    public HoidongdanhgiaQueryService(HoidongdanhgiaRepository hoidongdanhgiaRepository, HoidongdanhgiaMapper hoidongdanhgiaMapper) {
        this.hoidongdanhgiaRepository = hoidongdanhgiaRepository;
        this.hoidongdanhgiaMapper = hoidongdanhgiaMapper;
    }

    /**
     * Return a {@link List} of {@link HoidongdanhgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HoidongdanhgiaDTO> findByCriteria(HoidongdanhgiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hoidongdanhgia> specification = createSpecification(criteria);
        return hoidongdanhgiaMapper.toDto(hoidongdanhgiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HoidongdanhgiaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HoidongdanhgiaDTO> findByCriteria(HoidongdanhgiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hoidongdanhgia> specification = createSpecification(criteria);
        return hoidongdanhgiaRepository.findAll(specification, page)
            .map(hoidongdanhgiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HoidongdanhgiaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hoidongdanhgia> specification = createSpecification(criteria);
        return hoidongdanhgiaRepository.count(specification);
    }

    /**
     * Function to convert {@link HoidongdanhgiaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hoidongdanhgia> createSpecification(HoidongdanhgiaCriteria criteria) {
        Specification<Hoidongdanhgia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hoidongdanhgia_.id));
            }
            if (criteria.getMahoidong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMahoidong(), Hoidongdanhgia_.mahoidong));
            }
            if (criteria.getTenhoidong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenhoidong(), Hoidongdanhgia_.tenhoidong));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Hoidongdanhgia_.sudung));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Hoidongdanhgia_.detais, JoinType.LEFT).get(Detai_.id)));
            }
            if (criteria.getThanhvienHDId() != null) {
                specification = specification.and(buildSpecification(criteria.getThanhvienHDId(),
                    root -> root.join(Hoidongdanhgia_.thanhvienHDS, JoinType.LEFT).get(ThanhvienHD_.id)));
            }
        }
        return specification;
    }
}
