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

import com.api.backend.domain.Nguonkinhphi;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.NguonkinhphiRepository;
import com.api.backend.service.dto.NguonkinhphiCriteria;
import com.api.backend.service.dto.NguonkinhphiDTO;
import com.api.backend.service.mapper.NguonkinhphiMapper;

/**
 * Service for executing complex queries for {@link Nguonkinhphi} entities in the database.
 * The main input is a {@link NguonkinhphiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NguonkinhphiDTO} or a {@link Page} of {@link NguonkinhphiDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NguonkinhphiQueryService extends QueryService<Nguonkinhphi> {

    private final Logger log = LoggerFactory.getLogger(NguonkinhphiQueryService.class);

    private final NguonkinhphiRepository nguonkinhphiRepository;

    private final NguonkinhphiMapper nguonkinhphiMapper;

    public NguonkinhphiQueryService(NguonkinhphiRepository nguonkinhphiRepository, NguonkinhphiMapper nguonkinhphiMapper) {
        this.nguonkinhphiRepository = nguonkinhphiRepository;
        this.nguonkinhphiMapper = nguonkinhphiMapper;
    }

    /**
     * Return a {@link List} of {@link NguonkinhphiDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NguonkinhphiDTO> findByCriteria(NguonkinhphiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nguonkinhphi> specification = createSpecification(criteria);
        return nguonkinhphiMapper.toDto(nguonkinhphiRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NguonkinhphiDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NguonkinhphiDTO> findByCriteria(NguonkinhphiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nguonkinhphi> specification = createSpecification(criteria);
        return nguonkinhphiRepository.findAll(specification, page)
            .map(nguonkinhphiMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NguonkinhphiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nguonkinhphi> specification = createSpecification(criteria);
        return nguonkinhphiRepository.count(specification);
    }

    /**
     * Function to convert {@link NguonkinhphiCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nguonkinhphi> createSpecification(NguonkinhphiCriteria criteria) {
        Specification<Nguonkinhphi> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Nguonkinhphi_.id));
            }
            if (criteria.getManguonkinhphi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManguonkinhphi(), Nguonkinhphi_.manguonkinhphi));
            }
            if (criteria.getTennguonkinhphi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTennguonkinhphi(), Nguonkinhphi_.tennguonkinhphi));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), Nguonkinhphi_.noidung));
            }
            if (criteria.getSotiencap() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSotiencap(), Nguonkinhphi_.sotiencap));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Nguonkinhphi_.sudung));
            }
            if (criteria.getDetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetaiId(),
                    root -> root.join(Nguonkinhphi_.detai, JoinType.LEFT).get(Detai_.id)));
            }
        }
        return specification;
    }
}
