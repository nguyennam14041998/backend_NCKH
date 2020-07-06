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

import com.api.backend.domain.Nhansu;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.NhansuRepository;
import com.api.backend.service.dto.NhansuCriteria;
import com.api.backend.service.dto.NhansuDTO;
import com.api.backend.service.mapper.NhansuMapper;

/**
 * Service for executing complex queries for {@link Nhansu} entities in the database.
 * The main input is a {@link NhansuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhansuDTO} or a {@link Page} of {@link NhansuDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhansuQueryService extends QueryService<Nhansu> {

    private final Logger log = LoggerFactory.getLogger(NhansuQueryService.class);

    private final NhansuRepository nhansuRepository;

    private final NhansuMapper nhansuMapper;

    public NhansuQueryService(NhansuRepository nhansuRepository, NhansuMapper nhansuMapper) {
        this.nhansuRepository = nhansuRepository;
        this.nhansuMapper = nhansuMapper;
    }

    /**
     * Return a {@link List} of {@link NhansuDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhansuDTO> findByCriteria(NhansuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nhansu> specification = createSpecification(criteria);
        return nhansuMapper.toDto(nhansuRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NhansuDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhansuDTO> findByCriteria(NhansuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nhansu> specification = createSpecification(criteria);
        return nhansuRepository.findAll(specification, page)
            .map(nhansuMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhansuCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nhansu> specification = createSpecification(criteria);
        return nhansuRepository.count(specification);
    }

    /**
     * Function to convert {@link NhansuCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nhansu> createSpecification(NhansuCriteria criteria) {
        Specification<Nhansu> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Nhansu_.id));
            }
            if (criteria.getManhansu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManhansu(), Nhansu_.manhansu));
            }
            if (criteria.getTennhansu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTennhansu(), Nhansu_.tennhansu));
            }
            if (criteria.getSdt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSdt(), Nhansu_.sdt));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Nhansu_.email));
            }
            if (criteria.getDiachi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiachi(), Nhansu_.diachi));
            }
            if (criteria.getNamsinh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNamsinh(), Nhansu_.namsinh));
            }
            if (criteria.getNgaysinh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNgaysinh(), Nhansu_.ngaysinh));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Nhansu_.sudung));
            }
            if (criteria.getChunhiemId() != null) {
                specification = specification.and(buildSpecification(criteria.getChunhiemId(),
                    root -> root.join(Nhansu_.chunhiems, JoinType.LEFT).get(Chunhiem_.id)));
            }
            if (criteria.getNhansuthamgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuthamgiaId(),
                    root -> root.join(Nhansu_.nhansuthamgias, JoinType.LEFT).get(Nhansuthamgia_.id)));
            }
            if (criteria.getDonviId() != null) {
                specification = specification.and(buildSpecification(criteria.getDonviId(),
                    root -> root.join(Nhansu_.donvi, JoinType.LEFT).get(Donvi_.id)));
            }
            if (criteria.getChucdanhId() != null) {
                specification = specification.and(buildSpecification(criteria.getChucdanhId(),
                    root -> root.join(Nhansu_.chucdanh, JoinType.LEFT).get(Chucdanh_.id)));
            }
            if (criteria.getHochamId() != null) {
                specification = specification.and(buildSpecification(criteria.getHochamId(),
                    root -> root.join(Nhansu_.hocham, JoinType.LEFT).get(Hocham_.id)));
            }
        }
        return specification;
    }
}
