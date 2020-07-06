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

import com.api.backend.domain.ThanhvienHD;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.ThanhvienHDRepository;
import com.api.backend.service.dto.ThanhvienHDCriteria;
import com.api.backend.service.dto.ThanhvienHDDTO;
import com.api.backend.service.mapper.ThanhvienHDMapper;

/**
 * Service for executing complex queries for {@link ThanhvienHD} entities in the database.
 * The main input is a {@link ThanhvienHDCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ThanhvienHDDTO} or a {@link Page} of {@link ThanhvienHDDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ThanhvienHDQueryService extends QueryService<ThanhvienHD> {

    private final Logger log = LoggerFactory.getLogger(ThanhvienHDQueryService.class);

    private final ThanhvienHDRepository thanhvienHDRepository;

    private final ThanhvienHDMapper thanhvienHDMapper;

    public ThanhvienHDQueryService(ThanhvienHDRepository thanhvienHDRepository, ThanhvienHDMapper thanhvienHDMapper) {
        this.thanhvienHDRepository = thanhvienHDRepository;
        this.thanhvienHDMapper = thanhvienHDMapper;
    }

    /**
     * Return a {@link List} of {@link ThanhvienHDDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ThanhvienHDDTO> findByCriteria(ThanhvienHDCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ThanhvienHD> specification = createSpecification(criteria);
        return thanhvienHDMapper.toDto(thanhvienHDRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ThanhvienHDDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ThanhvienHDDTO> findByCriteria(ThanhvienHDCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ThanhvienHD> specification = createSpecification(criteria);
        return thanhvienHDRepository.findAll(specification, page)
            .map(thanhvienHDMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ThanhvienHDCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ThanhvienHD> specification = createSpecification(criteria);
        return thanhvienHDRepository.count(specification);
    }

    /**
     * Function to convert {@link ThanhvienHDCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ThanhvienHD> createSpecification(ThanhvienHDCriteria criteria) {
        Specification<ThanhvienHD> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ThanhvienHD_.id));
            }
            if (criteria.getTen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTen(), ThanhvienHD_.ten));
            }
            if (criteria.getDonvi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDonvi(), ThanhvienHD_.donvi));
            }
            if (criteria.getTrachnhiem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrachnhiem(), ThanhvienHD_.trachnhiem));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), ThanhvienHD_.sudung));
            }
            if (criteria.getHoidongdanhgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getHoidongdanhgiaId(),
                    root -> root.join(ThanhvienHD_.hoidongdanhgia, JoinType.LEFT).get(Hoidongdanhgia_.id)));
            }
        }
        return specification;
    }
}
