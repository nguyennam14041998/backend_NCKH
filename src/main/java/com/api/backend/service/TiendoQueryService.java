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

import com.api.backend.domain.Tiendo;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.TiendoRepository;
import com.api.backend.service.dto.TiendoCriteria;
import com.api.backend.service.dto.TiendoDTO;
import com.api.backend.service.mapper.TiendoMapper;

/**
 * Service for executing complex queries for {@link Tiendo} entities in the database.
 * The main input is a {@link TiendoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TiendoDTO} or a {@link Page} of {@link TiendoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TiendoQueryService extends QueryService<Tiendo> {

    private final Logger log = LoggerFactory.getLogger(TiendoQueryService.class);

    private final TiendoRepository tiendoRepository;

    private final TiendoMapper tiendoMapper;

    public TiendoQueryService(TiendoRepository tiendoRepository, TiendoMapper tiendoMapper) {
        this.tiendoRepository = tiendoRepository;
        this.tiendoMapper = tiendoMapper;
    }

    /**
     * Return a {@link List} of {@link TiendoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TiendoDTO> findByCriteria(TiendoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tiendo> specification = createSpecification(criteria);
        return tiendoMapper.toDto(tiendoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TiendoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TiendoDTO> findByCriteria(TiendoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tiendo> specification = createSpecification(criteria);
        return tiendoRepository.findAll(specification, page)
            .map(tiendoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TiendoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tiendo> specification = createSpecification(criteria);
        return tiendoRepository.count(specification);
    }

    /**
     * Function to convert {@link TiendoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tiendo> createSpecification(TiendoCriteria criteria) {
        Specification<Tiendo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tiendo_.id));
            }
            if (criteria.getMatiendo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatiendo(), Tiendo_.matiendo));
            }
            if (criteria.getKybaocao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKybaocao(), Tiendo_.kybaocao));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), Tiendo_.noidung));
            }
            if (criteria.getThoigianbatdau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThoigianbatdau(), Tiendo_.thoigianbatdau));
            }
            if (criteria.getThoigianketthuc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThoigianketthuc(), Tiendo_.thoigianketthuc));
            }
            if (criteria.getKhoiluonghoanthanh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKhoiluonghoanthanh(), Tiendo_.khoiluonghoanthanh));
            }
            if (criteria.getGhichu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGhichu(), Tiendo_.ghichu));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Tiendo_.sudung));
            }
            if (criteria.getUpfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUpfileId(),
                    root -> root.join(Tiendo_.upfiles, JoinType.LEFT).get(Upfile_.id)));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Tiendo_.detai, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
