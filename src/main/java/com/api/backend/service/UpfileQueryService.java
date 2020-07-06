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

import com.api.backend.domain.Upfile;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.UpfileRepository;
import com.api.backend.service.dto.UpfileCriteria;
import com.api.backend.service.dto.UpfileDTO;
import com.api.backend.service.mapper.UpfileMapper;

/**
 * Service for executing complex queries for {@link Upfile} entities in the database.
 * The main input is a {@link UpfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UpfileDTO} or a {@link Page} of {@link UpfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UpfileQueryService extends QueryService<Upfile> {

    private final Logger log = LoggerFactory.getLogger(UpfileQueryService.class);

    private final UpfileRepository upfileRepository;

    private final UpfileMapper upfileMapper;

    public UpfileQueryService(UpfileRepository upfileRepository, UpfileMapper upfileMapper) {
        this.upfileRepository = upfileRepository;
        this.upfileMapper = upfileMapper;
    }

    /**
     * Return a {@link List} of {@link UpfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UpfileDTO> findByCriteria(UpfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Upfile> specification = createSpecification(criteria);
        return upfileMapper.toDto(upfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UpfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UpfileDTO> findByCriteria(UpfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Upfile> specification = createSpecification(criteria);
        return upfileRepository.findAll(specification, page)
            .map(upfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UpfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Upfile> specification = createSpecification(criteria);
        return upfileRepository.count(specification);
    }

    /**
     * Function to convert {@link UpfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Upfile> createSpecification(UpfileCriteria criteria) {
        Specification<Upfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Upfile_.id));
            }
            if (criteria.getMota() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMota(), Upfile_.mota));
            }
            if (criteria.getThoigian() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThoigian(), Upfile_.thoigian));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Upfile_.detai, JoinType.LEFT).get(Detai_.id)));
            }
            if (criteria.getTiendoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTiendoId(),
                    root -> root.join(Upfile_.tiendo, JoinType.LEFT).get(Tiendo_.id)));
            }
        }
        return specification;
    }
}
