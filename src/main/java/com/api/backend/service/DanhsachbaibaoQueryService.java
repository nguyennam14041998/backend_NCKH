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

import com.api.backend.domain.Danhsachbaibao;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DanhsachbaibaoRepository;
import com.api.backend.service.dto.DanhsachbaibaoCriteria;
import com.api.backend.service.dto.DanhsachbaibaoDTO;
import com.api.backend.service.mapper.DanhsachbaibaoMapper;

/**
 * Service for executing complex queries for {@link Danhsachbaibao} entities in the database.
 * The main input is a {@link DanhsachbaibaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DanhsachbaibaoDTO} or a {@link Page} of {@link DanhsachbaibaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DanhsachbaibaoQueryService extends QueryService<Danhsachbaibao> {

    private final Logger log = LoggerFactory.getLogger(DanhsachbaibaoQueryService.class);

    private final DanhsachbaibaoRepository danhsachbaibaoRepository;

    private final DanhsachbaibaoMapper danhsachbaibaoMapper;

    public DanhsachbaibaoQueryService(DanhsachbaibaoRepository danhsachbaibaoRepository, DanhsachbaibaoMapper danhsachbaibaoMapper) {
        this.danhsachbaibaoRepository = danhsachbaibaoRepository;
        this.danhsachbaibaoMapper = danhsachbaibaoMapper;
    }

    /**
     * Return a {@link List} of {@link DanhsachbaibaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DanhsachbaibaoDTO> findByCriteria(DanhsachbaibaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Danhsachbaibao> specification = createSpecification(criteria);
        return danhsachbaibaoMapper.toDto(danhsachbaibaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DanhsachbaibaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DanhsachbaibaoDTO> findByCriteria(DanhsachbaibaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Danhsachbaibao> specification = createSpecification(criteria);
        return danhsachbaibaoRepository.findAll(specification, page)
            .map(danhsachbaibaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DanhsachbaibaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Danhsachbaibao> specification = createSpecification(criteria);
        return danhsachbaibaoRepository.count(specification);
    }

    /**
     * Function to convert {@link DanhsachbaibaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Danhsachbaibao> createSpecification(DanhsachbaibaoCriteria criteria) {
        Specification<Danhsachbaibao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Danhsachbaibao_.id));
            }
            if (criteria.getTenbaibao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenbaibao(), Danhsachbaibao_.tenbaibao));
            }
            if (criteria.getPhanloai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhanloai(), Danhsachbaibao_.phanloai));
            }
            if (criteria.getTenhoithao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenhoithao(), Danhsachbaibao_.tenhoithao));
            }
            if (criteria.getNamxuatban() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNamxuatban(), Danhsachbaibao_.namxuatban));
            }
            if (criteria.getThangxuatban() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThangxuatban(), Danhsachbaibao_.thangxuatban));
            }
            if (criteria.getDanhmuc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDanhmuc(), Danhsachbaibao_.danhmuc));
            }
            if (criteria.getIffff() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIffff(), Danhsachbaibao_.iffff));
            }
            if (criteria.getHindex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHindex(), Danhsachbaibao_.hindex));
            }
            if (criteria.getXeploai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXeploai(), Danhsachbaibao_.xeploai));
            }
            if (criteria.getRankbaibao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRankbaibao(), Danhsachbaibao_.rankbaibao));
            }
            if (criteria.getVolbaibao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVolbaibao(), Danhsachbaibao_.volbaibao));
            }
            if (criteria.getSobaibao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSobaibao(), Danhsachbaibao_.sobaibao));
            }
            if (criteria.getPpbaibao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPpbaibao(), Danhsachbaibao_.ppbaibao));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), Danhsachbaibao_.link));
            }
            if (criteria.getGhichu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGhichu(), Danhsachbaibao_.ghichu));
            }
            if (criteria.getTacgiachinh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTacgiachinh(), Danhsachbaibao_.tacgiachinh));
            }
            if (criteria.getTacgiakhac() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTacgiakhac(), Danhsachbaibao_.tacgiakhac));
            }
            if (criteria.getTendetai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTendetai(), Danhsachbaibao_.tendetai));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Danhsachbaibao_.detai, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
