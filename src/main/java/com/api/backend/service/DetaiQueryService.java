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

import com.api.backend.domain.Detai;
import com.api.backend.domain.*; // for static metamodels
import com.api.backend.repository.DetaiRepository;
import com.api.backend.service.dto.DetaiCriteria;
import com.api.backend.service.dto.DetaiDTO;
import com.api.backend.service.mapper.DetaiMapper;

/**
 * Service for executing complex queries for {@link Detai} entities in the database.
 * The main input is a {@link DetaiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetaiDTO} or a {@link Page} of {@link DetaiDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetaiQueryService extends QueryService<Detai> {

    private final Logger log = LoggerFactory.getLogger(DetaiQueryService.class);

    private final DetaiRepository detaiRepository;

    private final DetaiMapper detaiMapper;

    public DetaiQueryService(DetaiRepository detaiRepository, DetaiMapper detaiMapper) {
        this.detaiRepository = detaiRepository;
        this.detaiMapper = detaiMapper;
    }

    /**
     * Return a {@link List} of {@link DetaiDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetaiDTO> findByCriteria(DetaiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Detai> specification = createSpecification(criteria);
        return detaiMapper.toDto(detaiRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetaiDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Detai> findByCriteria(DetaiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Detai> specification = createSpecification(criteria);
        return detaiRepository.findAll(specification, page);
//        return detaiRepository.findAll(specification, page)
//            .map(detaiMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetaiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Detai> specification = createSpecification(criteria);
        return detaiRepository.count(specification);
    }

    /**
     * Function to convert {@link DetaiCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Detai> createSpecification(DetaiCriteria criteria) {
        Specification<Detai> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Detai_.id));
            }
            if (criteria.getMa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMa(), Detai_.ma));
            }
            if (criteria.getTen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTen(), Detai_.ten));
            }
            if (criteria.getThoigiantao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThoigiantao(), Detai_.thoigiantao));
            }
            if (criteria.getThoigianbatdau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThoigianbatdau(), Detai_.thoigianbatdau));
            }
            if (criteria.getThoigianketthuc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThoigianketthuc(), Detai_.thoigianketthuc));
            }
            if (criteria.getMuctieu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMuctieu(), Detai_.muctieu));
            }
            if (criteria.getNoidung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoidung(), Detai_.noidung));
            }
            if (criteria.getTinhcapthiet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTinhcapthiet(), Detai_.tinhcapthiet));
            }
            if (criteria.getNguoihuongdan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNguoihuongdan(), Detai_.nguoihuongdan));
            }
            if (criteria.getKetqua() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKetqua(), Detai_.ketqua));
            }
            if (criteria.getXeploai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getXeploai(), Detai_.xeploai));
            }
            if (criteria.getPhanloai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhanloai(), Detai_.phanloai));
            }
            if (criteria.getTrangthai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrangthai(), Detai_.trangthai));
            }
            if (criteria.getTenchunhiem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenchunhiem(), Detai_.tenchunhiem));
            }
            if (criteria.getSudung() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSudung(), Detai_.sudung));
            }
            if (criteria.getTiendoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTiendoId(),
                    root -> root.join(Detai_.tiendos, JoinType.LEFT).get(Tiendo_.id)));
            }
            if (criteria.getUpfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUpfileId(),
                    root -> root.join(Detai_.upfiles, JoinType.LEFT).get(Upfile_.id)));
            }
            if (criteria.getNhansuthamgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhansuthamgiaId(),
                    root -> root.join(Detai_.nhansuthamgias, JoinType.LEFT).get(Nhansuthamgia_.id)));
            }
            if (criteria.getNguonkinhphiId() != null) {
                specification = specification.and(buildSpecification(criteria.getNguonkinhphiId(),
                    root -> root.join(Detai_.nguonkinhphis, JoinType.LEFT).get(Nguonkinhphi_.id)));
            }
            if (criteria.getCoquanphoihopthamgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoquanphoihopthamgiaId(),
                    root -> root.join(Detai_.coquanphoihopthamgias, JoinType.LEFT).get(Coquanphoihopthamgia_.id)));
            }
            if (criteria.getDanhgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getDanhgiaId(),
                    root -> root.join(Detai_.danhgias, JoinType.LEFT).get(Danhgia_.id)));
            }
            if (criteria.getDutoanKPId() != null) {
                specification = specification.and(buildSpecification(criteria.getDutoanKPId(),
                    root -> root.join(Detai_.dutoanKPS, JoinType.LEFT).get(DutoanKP_.id)));
            }
            if (criteria.getDanhsachbaibaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDanhsachbaibaoId(),
                    root -> root.join(Detai_.danhsachbaibaos, JoinType.LEFT).get(Danhsachbaibao_.id)));
            }
            if (criteria.getLinhvucId() != null) {
                specification = specification.and(buildSpecification(criteria.getLinhvucId(),
                    root -> root.join(Detai_.linhvuc, JoinType.LEFT).get(Linhvuc_.id)));
            }
            if (criteria.getCapdetaiId() != null) {
                specification = specification.and(buildSpecification(criteria.getCapdetaiId(),
                    root -> root.join(Detai_.capdetai, JoinType.LEFT).get(Capdetai_.id)));
            }
            if (criteria.getHoidongdanhgiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getHoidongdanhgiaId(),
                    root -> root.join(Detai_.hoidongdanhgia, JoinType.LEFT).get(Hoidongdanhgia_.id)));
            }
            if (criteria.getChunhiemId() != null) {
                specification = specification.and(buildSpecification(criteria.getChunhiemId(),
                    root -> root.join(Detai_.chunhiem, JoinType.LEFT).get(Chunhiem_.id)));
            }
        }
        return specification;
    }
}
