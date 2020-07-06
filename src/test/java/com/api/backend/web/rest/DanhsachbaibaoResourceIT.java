package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Danhsachbaibao;
import com.api.backend.domain.Detai;
import com.api.backend.repository.DanhsachbaibaoRepository;
import com.api.backend.service.DanhsachbaibaoService;
import com.api.backend.service.dto.DanhsachbaibaoDTO;
import com.api.backend.service.mapper.DanhsachbaibaoMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DanhsachbaibaoCriteria;
import com.api.backend.service.DanhsachbaibaoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.api.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DanhsachbaibaoResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DanhsachbaibaoResourceIT {

    private static final String DEFAULT_TENBAIBAO = "AAAAAAAAAA";
    private static final String UPDATED_TENBAIBAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHANLOAI = 1;
    private static final Integer UPDATED_PHANLOAI = 2;
    private static final Integer SMALLER_PHANLOAI = 1 - 1;

    private static final String DEFAULT_TENHOITHAO = "AAAAAAAAAA";
    private static final String UPDATED_TENHOITHAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NAMXUATBAN = 1;
    private static final Integer UPDATED_NAMXUATBAN = 2;
    private static final Integer SMALLER_NAMXUATBAN = 1 - 1;

    private static final Integer DEFAULT_THANGXUATBAN = 1;
    private static final Integer UPDATED_THANGXUATBAN = 2;
    private static final Integer SMALLER_THANGXUATBAN = 1 - 1;

    private static final Integer DEFAULT_DANHMUC = 1;
    private static final Integer UPDATED_DANHMUC = 2;
    private static final Integer SMALLER_DANHMUC = 1 - 1;

    private static final String DEFAULT_IFFFF = "AAAAAAAAAA";
    private static final String UPDATED_IFFFF = "BBBBBBBBBB";

    private static final String DEFAULT_HINDEX = "AAAAAAAAAA";
    private static final String UPDATED_HINDEX = "BBBBBBBBBB";

    private static final Integer DEFAULT_XEPLOAI = 1;
    private static final Integer UPDATED_XEPLOAI = 2;
    private static final Integer SMALLER_XEPLOAI = 1 - 1;

    private static final Integer DEFAULT_RANKBAIBAO = 1;
    private static final Integer UPDATED_RANKBAIBAO = 2;
    private static final Integer SMALLER_RANKBAIBAO = 1 - 1;

    private static final String DEFAULT_VOLBAIBAO = "AAAAAAAAAA";
    private static final String UPDATED_VOLBAIBAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOBAIBAO = 1;
    private static final Integer UPDATED_SOBAIBAO = 2;
    private static final Integer SMALLER_SOBAIBAO = 1 - 1;

    private static final String DEFAULT_PPBAIBAO = "AAAAAAAAAA";
    private static final String UPDATED_PPBAIBAO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_GHICHU = "AAAAAAAAAA";
    private static final String UPDATED_GHICHU = "BBBBBBBBBB";

    private static final String DEFAULT_TACGIACHINH = "AAAAAAAAAA";
    private static final String UPDATED_TACGIACHINH = "BBBBBBBBBB";

    private static final String DEFAULT_TACGIAKHAC = "AAAAAAAAAA";
    private static final String UPDATED_TACGIAKHAC = "BBBBBBBBBB";

    private static final String DEFAULT_TENDETAI = "AAAAAAAAAA";
    private static final String UPDATED_TENDETAI = "BBBBBBBBBB";

    @Autowired
    private DanhsachbaibaoRepository danhsachbaibaoRepository;

    @Autowired
    private DanhsachbaibaoMapper danhsachbaibaoMapper;

    @Autowired
    private DanhsachbaibaoService danhsachbaibaoService;

    @Autowired
    private DanhsachbaibaoQueryService danhsachbaibaoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDanhsachbaibaoMockMvc;

    private Danhsachbaibao danhsachbaibao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DanhsachbaibaoResource danhsachbaibaoResource = new DanhsachbaibaoResource(danhsachbaibaoService, danhsachbaibaoQueryService);
        this.restDanhsachbaibaoMockMvc = MockMvcBuilders.standaloneSetup(danhsachbaibaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Danhsachbaibao createEntity(EntityManager em) {
        Danhsachbaibao danhsachbaibao = new Danhsachbaibao()
            .tenbaibao(DEFAULT_TENBAIBAO)
            .phanloai(DEFAULT_PHANLOAI)
            .tenhoithao(DEFAULT_TENHOITHAO)
            .namxuatban(DEFAULT_NAMXUATBAN)
            .thangxuatban(DEFAULT_THANGXUATBAN)
            .danhmuc(DEFAULT_DANHMUC)
            .iffff(DEFAULT_IFFFF)
            .hindex(DEFAULT_HINDEX)
            .xeploai(DEFAULT_XEPLOAI)
            .rankbaibao(DEFAULT_RANKBAIBAO)
            .volbaibao(DEFAULT_VOLBAIBAO)
            .sobaibao(DEFAULT_SOBAIBAO)
            .ppbaibao(DEFAULT_PPBAIBAO)
            .link(DEFAULT_LINK)
            .ghichu(DEFAULT_GHICHU)
            .tacgiachinh(DEFAULT_TACGIACHINH)
            .tacgiakhac(DEFAULT_TACGIAKHAC)
            .tendetai(DEFAULT_TENDETAI);
        return danhsachbaibao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Danhsachbaibao createUpdatedEntity(EntityManager em) {
        Danhsachbaibao danhsachbaibao = new Danhsachbaibao()
            .tenbaibao(UPDATED_TENBAIBAO)
            .phanloai(UPDATED_PHANLOAI)
            .tenhoithao(UPDATED_TENHOITHAO)
            .namxuatban(UPDATED_NAMXUATBAN)
            .thangxuatban(UPDATED_THANGXUATBAN)
            .danhmuc(UPDATED_DANHMUC)
            .iffff(UPDATED_IFFFF)
            .hindex(UPDATED_HINDEX)
            .xeploai(UPDATED_XEPLOAI)
            .rankbaibao(UPDATED_RANKBAIBAO)
            .volbaibao(UPDATED_VOLBAIBAO)
            .sobaibao(UPDATED_SOBAIBAO)
            .ppbaibao(UPDATED_PPBAIBAO)
            .link(UPDATED_LINK)
            .ghichu(UPDATED_GHICHU)
            .tacgiachinh(UPDATED_TACGIACHINH)
            .tacgiakhac(UPDATED_TACGIAKHAC)
            .tendetai(UPDATED_TENDETAI);
        return danhsachbaibao;
    }

    @BeforeEach
    public void initTest() {
        danhsachbaibao = createEntity(em);
    }

    @Test
    @Transactional
    public void createDanhsachbaibao() throws Exception {
        int databaseSizeBeforeCreate = danhsachbaibaoRepository.findAll().size();

        // Create the Danhsachbaibao
        DanhsachbaibaoDTO danhsachbaibaoDTO = danhsachbaibaoMapper.toDto(danhsachbaibao);
        restDanhsachbaibaoMockMvc.perform(post("/api/danhsachbaibaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhsachbaibaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Danhsachbaibao in the database
        List<Danhsachbaibao> danhsachbaibaoList = danhsachbaibaoRepository.findAll();
        assertThat(danhsachbaibaoList).hasSize(databaseSizeBeforeCreate + 1);
        Danhsachbaibao testDanhsachbaibao = danhsachbaibaoList.get(danhsachbaibaoList.size() - 1);
        assertThat(testDanhsachbaibao.getTenbaibao()).isEqualTo(DEFAULT_TENBAIBAO);
        assertThat(testDanhsachbaibao.getPhanloai()).isEqualTo(DEFAULT_PHANLOAI);
        assertThat(testDanhsachbaibao.getTenhoithao()).isEqualTo(DEFAULT_TENHOITHAO);
        assertThat(testDanhsachbaibao.getNamxuatban()).isEqualTo(DEFAULT_NAMXUATBAN);
        assertThat(testDanhsachbaibao.getThangxuatban()).isEqualTo(DEFAULT_THANGXUATBAN);
        assertThat(testDanhsachbaibao.getDanhmuc()).isEqualTo(DEFAULT_DANHMUC);
        assertThat(testDanhsachbaibao.getIffff()).isEqualTo(DEFAULT_IFFFF);
        assertThat(testDanhsachbaibao.getHindex()).isEqualTo(DEFAULT_HINDEX);
        assertThat(testDanhsachbaibao.getXeploai()).isEqualTo(DEFAULT_XEPLOAI);
        assertThat(testDanhsachbaibao.getRankbaibao()).isEqualTo(DEFAULT_RANKBAIBAO);
        assertThat(testDanhsachbaibao.getVolbaibao()).isEqualTo(DEFAULT_VOLBAIBAO);
        assertThat(testDanhsachbaibao.getSobaibao()).isEqualTo(DEFAULT_SOBAIBAO);
        assertThat(testDanhsachbaibao.getPpbaibao()).isEqualTo(DEFAULT_PPBAIBAO);
        assertThat(testDanhsachbaibao.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testDanhsachbaibao.getGhichu()).isEqualTo(DEFAULT_GHICHU);
        assertThat(testDanhsachbaibao.getTacgiachinh()).isEqualTo(DEFAULT_TACGIACHINH);
        assertThat(testDanhsachbaibao.getTacgiakhac()).isEqualTo(DEFAULT_TACGIAKHAC);
        assertThat(testDanhsachbaibao.getTendetai()).isEqualTo(DEFAULT_TENDETAI);
    }

    @Test
    @Transactional
    public void createDanhsachbaibaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = danhsachbaibaoRepository.findAll().size();

        // Create the Danhsachbaibao with an existing ID
        danhsachbaibao.setId(1L);
        DanhsachbaibaoDTO danhsachbaibaoDTO = danhsachbaibaoMapper.toDto(danhsachbaibao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDanhsachbaibaoMockMvc.perform(post("/api/danhsachbaibaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhsachbaibaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Danhsachbaibao in the database
        List<Danhsachbaibao> danhsachbaibaoList = danhsachbaibaoRepository.findAll();
        assertThat(danhsachbaibaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaos() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhsachbaibao.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenbaibao").value(hasItem(DEFAULT_TENBAIBAO)))
            .andExpect(jsonPath("$.[*].phanloai").value(hasItem(DEFAULT_PHANLOAI)))
            .andExpect(jsonPath("$.[*].tenhoithao").value(hasItem(DEFAULT_TENHOITHAO)))
            .andExpect(jsonPath("$.[*].namxuatban").value(hasItem(DEFAULT_NAMXUATBAN)))
            .andExpect(jsonPath("$.[*].thangxuatban").value(hasItem(DEFAULT_THANGXUATBAN)))
            .andExpect(jsonPath("$.[*].danhmuc").value(hasItem(DEFAULT_DANHMUC)))
            .andExpect(jsonPath("$.[*].iffff").value(hasItem(DEFAULT_IFFFF)))
            .andExpect(jsonPath("$.[*].hindex").value(hasItem(DEFAULT_HINDEX)))
            .andExpect(jsonPath("$.[*].xeploai").value(hasItem(DEFAULT_XEPLOAI)))
            .andExpect(jsonPath("$.[*].rankbaibao").value(hasItem(DEFAULT_RANKBAIBAO)))
            .andExpect(jsonPath("$.[*].volbaibao").value(hasItem(DEFAULT_VOLBAIBAO)))
            .andExpect(jsonPath("$.[*].sobaibao").value(hasItem(DEFAULT_SOBAIBAO)))
            .andExpect(jsonPath("$.[*].ppbaibao").value(hasItem(DEFAULT_PPBAIBAO)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].ghichu").value(hasItem(DEFAULT_GHICHU)))
            .andExpect(jsonPath("$.[*].tacgiachinh").value(hasItem(DEFAULT_TACGIACHINH)))
            .andExpect(jsonPath("$.[*].tacgiakhac").value(hasItem(DEFAULT_TACGIAKHAC)))
            .andExpect(jsonPath("$.[*].tendetai").value(hasItem(DEFAULT_TENDETAI)));
    }
    
    @Test
    @Transactional
    public void getDanhsachbaibao() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get the danhsachbaibao
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos/{id}", danhsachbaibao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(danhsachbaibao.getId().intValue()))
            .andExpect(jsonPath("$.tenbaibao").value(DEFAULT_TENBAIBAO))
            .andExpect(jsonPath("$.phanloai").value(DEFAULT_PHANLOAI))
            .andExpect(jsonPath("$.tenhoithao").value(DEFAULT_TENHOITHAO))
            .andExpect(jsonPath("$.namxuatban").value(DEFAULT_NAMXUATBAN))
            .andExpect(jsonPath("$.thangxuatban").value(DEFAULT_THANGXUATBAN))
            .andExpect(jsonPath("$.danhmuc").value(DEFAULT_DANHMUC))
            .andExpect(jsonPath("$.iffff").value(DEFAULT_IFFFF))
            .andExpect(jsonPath("$.hindex").value(DEFAULT_HINDEX))
            .andExpect(jsonPath("$.xeploai").value(DEFAULT_XEPLOAI))
            .andExpect(jsonPath("$.rankbaibao").value(DEFAULT_RANKBAIBAO))
            .andExpect(jsonPath("$.volbaibao").value(DEFAULT_VOLBAIBAO))
            .andExpect(jsonPath("$.sobaibao").value(DEFAULT_SOBAIBAO))
            .andExpect(jsonPath("$.ppbaibao").value(DEFAULT_PPBAIBAO))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.ghichu").value(DEFAULT_GHICHU))
            .andExpect(jsonPath("$.tacgiachinh").value(DEFAULT_TACGIACHINH))
            .andExpect(jsonPath("$.tacgiakhac").value(DEFAULT_TACGIAKHAC))
            .andExpect(jsonPath("$.tendetai").value(DEFAULT_TENDETAI));
    }


    @Test
    @Transactional
    public void getDanhsachbaibaosByIdFiltering() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        Long id = danhsachbaibao.getId();

        defaultDanhsachbaibaoShouldBeFound("id.equals=" + id);
        defaultDanhsachbaibaoShouldNotBeFound("id.notEquals=" + id);

        defaultDanhsachbaibaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDanhsachbaibaoShouldNotBeFound("id.greaterThan=" + id);

        defaultDanhsachbaibaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDanhsachbaibaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenbaibaoIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenbaibao equals to DEFAULT_TENBAIBAO
        defaultDanhsachbaibaoShouldBeFound("tenbaibao.equals=" + DEFAULT_TENBAIBAO);

        // Get all the danhsachbaibaoList where tenbaibao equals to UPDATED_TENBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("tenbaibao.equals=" + UPDATED_TENBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenbaibaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenbaibao not equals to DEFAULT_TENBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("tenbaibao.notEquals=" + DEFAULT_TENBAIBAO);

        // Get all the danhsachbaibaoList where tenbaibao not equals to UPDATED_TENBAIBAO
        defaultDanhsachbaibaoShouldBeFound("tenbaibao.notEquals=" + UPDATED_TENBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenbaibaoIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenbaibao in DEFAULT_TENBAIBAO or UPDATED_TENBAIBAO
        defaultDanhsachbaibaoShouldBeFound("tenbaibao.in=" + DEFAULT_TENBAIBAO + "," + UPDATED_TENBAIBAO);

        // Get all the danhsachbaibaoList where tenbaibao equals to UPDATED_TENBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("tenbaibao.in=" + UPDATED_TENBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenbaibaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenbaibao is not null
        defaultDanhsachbaibaoShouldBeFound("tenbaibao.specified=true");

        // Get all the danhsachbaibaoList where tenbaibao is null
        defaultDanhsachbaibaoShouldNotBeFound("tenbaibao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenbaibaoContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenbaibao contains DEFAULT_TENBAIBAO
        defaultDanhsachbaibaoShouldBeFound("tenbaibao.contains=" + DEFAULT_TENBAIBAO);

        // Get all the danhsachbaibaoList where tenbaibao contains UPDATED_TENBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("tenbaibao.contains=" + UPDATED_TENBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenbaibaoNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenbaibao does not contain DEFAULT_TENBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("tenbaibao.doesNotContain=" + DEFAULT_TENBAIBAO);

        // Get all the danhsachbaibaoList where tenbaibao does not contain UPDATED_TENBAIBAO
        defaultDanhsachbaibaoShouldBeFound("tenbaibao.doesNotContain=" + UPDATED_TENBAIBAO);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai equals to DEFAULT_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.equals=" + DEFAULT_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai equals to UPDATED_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.equals=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai not equals to DEFAULT_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.notEquals=" + DEFAULT_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai not equals to UPDATED_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.notEquals=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai in DEFAULT_PHANLOAI or UPDATED_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.in=" + DEFAULT_PHANLOAI + "," + UPDATED_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai equals to UPDATED_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.in=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai is not null
        defaultDanhsachbaibaoShouldBeFound("phanloai.specified=true");

        // Get all the danhsachbaibaoList where phanloai is null
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai is greater than or equal to DEFAULT_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.greaterThanOrEqual=" + DEFAULT_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai is greater than or equal to UPDATED_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.greaterThanOrEqual=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai is less than or equal to DEFAULT_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.lessThanOrEqual=" + DEFAULT_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai is less than or equal to SMALLER_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.lessThanOrEqual=" + SMALLER_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai is less than DEFAULT_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.lessThan=" + DEFAULT_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai is less than UPDATED_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.lessThan=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPhanloaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where phanloai is greater than DEFAULT_PHANLOAI
        defaultDanhsachbaibaoShouldNotBeFound("phanloai.greaterThan=" + DEFAULT_PHANLOAI);

        // Get all the danhsachbaibaoList where phanloai is greater than SMALLER_PHANLOAI
        defaultDanhsachbaibaoShouldBeFound("phanloai.greaterThan=" + SMALLER_PHANLOAI);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenhoithaoIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenhoithao equals to DEFAULT_TENHOITHAO
        defaultDanhsachbaibaoShouldBeFound("tenhoithao.equals=" + DEFAULT_TENHOITHAO);

        // Get all the danhsachbaibaoList where tenhoithao equals to UPDATED_TENHOITHAO
        defaultDanhsachbaibaoShouldNotBeFound("tenhoithao.equals=" + UPDATED_TENHOITHAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenhoithaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenhoithao not equals to DEFAULT_TENHOITHAO
        defaultDanhsachbaibaoShouldNotBeFound("tenhoithao.notEquals=" + DEFAULT_TENHOITHAO);

        // Get all the danhsachbaibaoList where tenhoithao not equals to UPDATED_TENHOITHAO
        defaultDanhsachbaibaoShouldBeFound("tenhoithao.notEquals=" + UPDATED_TENHOITHAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenhoithaoIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenhoithao in DEFAULT_TENHOITHAO or UPDATED_TENHOITHAO
        defaultDanhsachbaibaoShouldBeFound("tenhoithao.in=" + DEFAULT_TENHOITHAO + "," + UPDATED_TENHOITHAO);

        // Get all the danhsachbaibaoList where tenhoithao equals to UPDATED_TENHOITHAO
        defaultDanhsachbaibaoShouldNotBeFound("tenhoithao.in=" + UPDATED_TENHOITHAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenhoithaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenhoithao is not null
        defaultDanhsachbaibaoShouldBeFound("tenhoithao.specified=true");

        // Get all the danhsachbaibaoList where tenhoithao is null
        defaultDanhsachbaibaoShouldNotBeFound("tenhoithao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenhoithaoContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenhoithao contains DEFAULT_TENHOITHAO
        defaultDanhsachbaibaoShouldBeFound("tenhoithao.contains=" + DEFAULT_TENHOITHAO);

        // Get all the danhsachbaibaoList where tenhoithao contains UPDATED_TENHOITHAO
        defaultDanhsachbaibaoShouldNotBeFound("tenhoithao.contains=" + UPDATED_TENHOITHAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTenhoithaoNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tenhoithao does not contain DEFAULT_TENHOITHAO
        defaultDanhsachbaibaoShouldNotBeFound("tenhoithao.doesNotContain=" + DEFAULT_TENHOITHAO);

        // Get all the danhsachbaibaoList where tenhoithao does not contain UPDATED_TENHOITHAO
        defaultDanhsachbaibaoShouldBeFound("tenhoithao.doesNotContain=" + UPDATED_TENHOITHAO);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban equals to DEFAULT_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.equals=" + DEFAULT_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban equals to UPDATED_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.equals=" + UPDATED_NAMXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban not equals to DEFAULT_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.notEquals=" + DEFAULT_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban not equals to UPDATED_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.notEquals=" + UPDATED_NAMXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban in DEFAULT_NAMXUATBAN or UPDATED_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.in=" + DEFAULT_NAMXUATBAN + "," + UPDATED_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban equals to UPDATED_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.in=" + UPDATED_NAMXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban is not null
        defaultDanhsachbaibaoShouldBeFound("namxuatban.specified=true");

        // Get all the danhsachbaibaoList where namxuatban is null
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban is greater than or equal to DEFAULT_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.greaterThanOrEqual=" + DEFAULT_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban is greater than or equal to UPDATED_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.greaterThanOrEqual=" + UPDATED_NAMXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban is less than or equal to DEFAULT_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.lessThanOrEqual=" + DEFAULT_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban is less than or equal to SMALLER_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.lessThanOrEqual=" + SMALLER_NAMXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban is less than DEFAULT_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.lessThan=" + DEFAULT_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban is less than UPDATED_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.lessThan=" + UPDATED_NAMXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByNamxuatbanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where namxuatban is greater than DEFAULT_NAMXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("namxuatban.greaterThan=" + DEFAULT_NAMXUATBAN);

        // Get all the danhsachbaibaoList where namxuatban is greater than SMALLER_NAMXUATBAN
        defaultDanhsachbaibaoShouldBeFound("namxuatban.greaterThan=" + SMALLER_NAMXUATBAN);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban equals to DEFAULT_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.equals=" + DEFAULT_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban equals to UPDATED_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.equals=" + UPDATED_THANGXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban not equals to DEFAULT_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.notEquals=" + DEFAULT_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban not equals to UPDATED_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.notEquals=" + UPDATED_THANGXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban in DEFAULT_THANGXUATBAN or UPDATED_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.in=" + DEFAULT_THANGXUATBAN + "," + UPDATED_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban equals to UPDATED_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.in=" + UPDATED_THANGXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban is not null
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.specified=true");

        // Get all the danhsachbaibaoList where thangxuatban is null
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban is greater than or equal to DEFAULT_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.greaterThanOrEqual=" + DEFAULT_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban is greater than or equal to UPDATED_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.greaterThanOrEqual=" + UPDATED_THANGXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban is less than or equal to DEFAULT_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.lessThanOrEqual=" + DEFAULT_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban is less than or equal to SMALLER_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.lessThanOrEqual=" + SMALLER_THANGXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban is less than DEFAULT_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.lessThan=" + DEFAULT_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban is less than UPDATED_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.lessThan=" + UPDATED_THANGXUATBAN);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByThangxuatbanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where thangxuatban is greater than DEFAULT_THANGXUATBAN
        defaultDanhsachbaibaoShouldNotBeFound("thangxuatban.greaterThan=" + DEFAULT_THANGXUATBAN);

        // Get all the danhsachbaibaoList where thangxuatban is greater than SMALLER_THANGXUATBAN
        defaultDanhsachbaibaoShouldBeFound("thangxuatban.greaterThan=" + SMALLER_THANGXUATBAN);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc equals to DEFAULT_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.equals=" + DEFAULT_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc equals to UPDATED_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.equals=" + UPDATED_DANHMUC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc not equals to DEFAULT_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.notEquals=" + DEFAULT_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc not equals to UPDATED_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.notEquals=" + UPDATED_DANHMUC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc in DEFAULT_DANHMUC or UPDATED_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.in=" + DEFAULT_DANHMUC + "," + UPDATED_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc equals to UPDATED_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.in=" + UPDATED_DANHMUC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc is not null
        defaultDanhsachbaibaoShouldBeFound("danhmuc.specified=true");

        // Get all the danhsachbaibaoList where danhmuc is null
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc is greater than or equal to DEFAULT_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.greaterThanOrEqual=" + DEFAULT_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc is greater than or equal to UPDATED_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.greaterThanOrEqual=" + UPDATED_DANHMUC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc is less than or equal to DEFAULT_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.lessThanOrEqual=" + DEFAULT_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc is less than or equal to SMALLER_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.lessThanOrEqual=" + SMALLER_DANHMUC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc is less than DEFAULT_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.lessThan=" + DEFAULT_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc is less than UPDATED_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.lessThan=" + UPDATED_DANHMUC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDanhmucIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where danhmuc is greater than DEFAULT_DANHMUC
        defaultDanhsachbaibaoShouldNotBeFound("danhmuc.greaterThan=" + DEFAULT_DANHMUC);

        // Get all the danhsachbaibaoList where danhmuc is greater than SMALLER_DANHMUC
        defaultDanhsachbaibaoShouldBeFound("danhmuc.greaterThan=" + SMALLER_DANHMUC);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByIffffIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where iffff equals to DEFAULT_IFFFF
        defaultDanhsachbaibaoShouldBeFound("iffff.equals=" + DEFAULT_IFFFF);

        // Get all the danhsachbaibaoList where iffff equals to UPDATED_IFFFF
        defaultDanhsachbaibaoShouldNotBeFound("iffff.equals=" + UPDATED_IFFFF);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByIffffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where iffff not equals to DEFAULT_IFFFF
        defaultDanhsachbaibaoShouldNotBeFound("iffff.notEquals=" + DEFAULT_IFFFF);

        // Get all the danhsachbaibaoList where iffff not equals to UPDATED_IFFFF
        defaultDanhsachbaibaoShouldBeFound("iffff.notEquals=" + UPDATED_IFFFF);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByIffffIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where iffff in DEFAULT_IFFFF or UPDATED_IFFFF
        defaultDanhsachbaibaoShouldBeFound("iffff.in=" + DEFAULT_IFFFF + "," + UPDATED_IFFFF);

        // Get all the danhsachbaibaoList where iffff equals to UPDATED_IFFFF
        defaultDanhsachbaibaoShouldNotBeFound("iffff.in=" + UPDATED_IFFFF);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByIffffIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where iffff is not null
        defaultDanhsachbaibaoShouldBeFound("iffff.specified=true");

        // Get all the danhsachbaibaoList where iffff is null
        defaultDanhsachbaibaoShouldNotBeFound("iffff.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByIffffContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where iffff contains DEFAULT_IFFFF
        defaultDanhsachbaibaoShouldBeFound("iffff.contains=" + DEFAULT_IFFFF);

        // Get all the danhsachbaibaoList where iffff contains UPDATED_IFFFF
        defaultDanhsachbaibaoShouldNotBeFound("iffff.contains=" + UPDATED_IFFFF);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByIffffNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where iffff does not contain DEFAULT_IFFFF
        defaultDanhsachbaibaoShouldNotBeFound("iffff.doesNotContain=" + DEFAULT_IFFFF);

        // Get all the danhsachbaibaoList where iffff does not contain UPDATED_IFFFF
        defaultDanhsachbaibaoShouldBeFound("iffff.doesNotContain=" + UPDATED_IFFFF);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByHindexIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where hindex equals to DEFAULT_HINDEX
        defaultDanhsachbaibaoShouldBeFound("hindex.equals=" + DEFAULT_HINDEX);

        // Get all the danhsachbaibaoList where hindex equals to UPDATED_HINDEX
        defaultDanhsachbaibaoShouldNotBeFound("hindex.equals=" + UPDATED_HINDEX);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByHindexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where hindex not equals to DEFAULT_HINDEX
        defaultDanhsachbaibaoShouldNotBeFound("hindex.notEquals=" + DEFAULT_HINDEX);

        // Get all the danhsachbaibaoList where hindex not equals to UPDATED_HINDEX
        defaultDanhsachbaibaoShouldBeFound("hindex.notEquals=" + UPDATED_HINDEX);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByHindexIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where hindex in DEFAULT_HINDEX or UPDATED_HINDEX
        defaultDanhsachbaibaoShouldBeFound("hindex.in=" + DEFAULT_HINDEX + "," + UPDATED_HINDEX);

        // Get all the danhsachbaibaoList where hindex equals to UPDATED_HINDEX
        defaultDanhsachbaibaoShouldNotBeFound("hindex.in=" + UPDATED_HINDEX);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByHindexIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where hindex is not null
        defaultDanhsachbaibaoShouldBeFound("hindex.specified=true");

        // Get all the danhsachbaibaoList where hindex is null
        defaultDanhsachbaibaoShouldNotBeFound("hindex.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByHindexContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where hindex contains DEFAULT_HINDEX
        defaultDanhsachbaibaoShouldBeFound("hindex.contains=" + DEFAULT_HINDEX);

        // Get all the danhsachbaibaoList where hindex contains UPDATED_HINDEX
        defaultDanhsachbaibaoShouldNotBeFound("hindex.contains=" + UPDATED_HINDEX);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByHindexNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where hindex does not contain DEFAULT_HINDEX
        defaultDanhsachbaibaoShouldNotBeFound("hindex.doesNotContain=" + DEFAULT_HINDEX);

        // Get all the danhsachbaibaoList where hindex does not contain UPDATED_HINDEX
        defaultDanhsachbaibaoShouldBeFound("hindex.doesNotContain=" + UPDATED_HINDEX);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai equals to DEFAULT_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.equals=" + DEFAULT_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai equals to UPDATED_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.equals=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai not equals to DEFAULT_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.notEquals=" + DEFAULT_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai not equals to UPDATED_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.notEquals=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai in DEFAULT_XEPLOAI or UPDATED_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.in=" + DEFAULT_XEPLOAI + "," + UPDATED_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai equals to UPDATED_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.in=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai is not null
        defaultDanhsachbaibaoShouldBeFound("xeploai.specified=true");

        // Get all the danhsachbaibaoList where xeploai is null
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai is greater than or equal to DEFAULT_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.greaterThanOrEqual=" + DEFAULT_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai is greater than or equal to UPDATED_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.greaterThanOrEqual=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai is less than or equal to DEFAULT_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.lessThanOrEqual=" + DEFAULT_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai is less than or equal to SMALLER_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.lessThanOrEqual=" + SMALLER_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai is less than DEFAULT_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.lessThan=" + DEFAULT_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai is less than UPDATED_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.lessThan=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByXeploaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where xeploai is greater than DEFAULT_XEPLOAI
        defaultDanhsachbaibaoShouldNotBeFound("xeploai.greaterThan=" + DEFAULT_XEPLOAI);

        // Get all the danhsachbaibaoList where xeploai is greater than SMALLER_XEPLOAI
        defaultDanhsachbaibaoShouldBeFound("xeploai.greaterThan=" + SMALLER_XEPLOAI);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao equals to DEFAULT_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.equals=" + DEFAULT_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao equals to UPDATED_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.equals=" + UPDATED_RANKBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao not equals to DEFAULT_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.notEquals=" + DEFAULT_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao not equals to UPDATED_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.notEquals=" + UPDATED_RANKBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao in DEFAULT_RANKBAIBAO or UPDATED_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.in=" + DEFAULT_RANKBAIBAO + "," + UPDATED_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao equals to UPDATED_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.in=" + UPDATED_RANKBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao is not null
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.specified=true");

        // Get all the danhsachbaibaoList where rankbaibao is null
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao is greater than or equal to DEFAULT_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.greaterThanOrEqual=" + DEFAULT_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao is greater than or equal to UPDATED_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.greaterThanOrEqual=" + UPDATED_RANKBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao is less than or equal to DEFAULT_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.lessThanOrEqual=" + DEFAULT_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao is less than or equal to SMALLER_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.lessThanOrEqual=" + SMALLER_RANKBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao is less than DEFAULT_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.lessThan=" + DEFAULT_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao is less than UPDATED_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.lessThan=" + UPDATED_RANKBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByRankbaibaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where rankbaibao is greater than DEFAULT_RANKBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("rankbaibao.greaterThan=" + DEFAULT_RANKBAIBAO);

        // Get all the danhsachbaibaoList where rankbaibao is greater than SMALLER_RANKBAIBAO
        defaultDanhsachbaibaoShouldBeFound("rankbaibao.greaterThan=" + SMALLER_RANKBAIBAO);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByVolbaibaoIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where volbaibao equals to DEFAULT_VOLBAIBAO
        defaultDanhsachbaibaoShouldBeFound("volbaibao.equals=" + DEFAULT_VOLBAIBAO);

        // Get all the danhsachbaibaoList where volbaibao equals to UPDATED_VOLBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("volbaibao.equals=" + UPDATED_VOLBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByVolbaibaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where volbaibao not equals to DEFAULT_VOLBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("volbaibao.notEquals=" + DEFAULT_VOLBAIBAO);

        // Get all the danhsachbaibaoList where volbaibao not equals to UPDATED_VOLBAIBAO
        defaultDanhsachbaibaoShouldBeFound("volbaibao.notEquals=" + UPDATED_VOLBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByVolbaibaoIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where volbaibao in DEFAULT_VOLBAIBAO or UPDATED_VOLBAIBAO
        defaultDanhsachbaibaoShouldBeFound("volbaibao.in=" + DEFAULT_VOLBAIBAO + "," + UPDATED_VOLBAIBAO);

        // Get all the danhsachbaibaoList where volbaibao equals to UPDATED_VOLBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("volbaibao.in=" + UPDATED_VOLBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByVolbaibaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where volbaibao is not null
        defaultDanhsachbaibaoShouldBeFound("volbaibao.specified=true");

        // Get all the danhsachbaibaoList where volbaibao is null
        defaultDanhsachbaibaoShouldNotBeFound("volbaibao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByVolbaibaoContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where volbaibao contains DEFAULT_VOLBAIBAO
        defaultDanhsachbaibaoShouldBeFound("volbaibao.contains=" + DEFAULT_VOLBAIBAO);

        // Get all the danhsachbaibaoList where volbaibao contains UPDATED_VOLBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("volbaibao.contains=" + UPDATED_VOLBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByVolbaibaoNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where volbaibao does not contain DEFAULT_VOLBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("volbaibao.doesNotContain=" + DEFAULT_VOLBAIBAO);

        // Get all the danhsachbaibaoList where volbaibao does not contain UPDATED_VOLBAIBAO
        defaultDanhsachbaibaoShouldBeFound("volbaibao.doesNotContain=" + UPDATED_VOLBAIBAO);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao equals to DEFAULT_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.equals=" + DEFAULT_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao equals to UPDATED_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.equals=" + UPDATED_SOBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao not equals to DEFAULT_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.notEquals=" + DEFAULT_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao not equals to UPDATED_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.notEquals=" + UPDATED_SOBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao in DEFAULT_SOBAIBAO or UPDATED_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.in=" + DEFAULT_SOBAIBAO + "," + UPDATED_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao equals to UPDATED_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.in=" + UPDATED_SOBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao is not null
        defaultDanhsachbaibaoShouldBeFound("sobaibao.specified=true");

        // Get all the danhsachbaibaoList where sobaibao is null
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao is greater than or equal to DEFAULT_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.greaterThanOrEqual=" + DEFAULT_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao is greater than or equal to UPDATED_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.greaterThanOrEqual=" + UPDATED_SOBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao is less than or equal to DEFAULT_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.lessThanOrEqual=" + DEFAULT_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao is less than or equal to SMALLER_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.lessThanOrEqual=" + SMALLER_SOBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsLessThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao is less than DEFAULT_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.lessThan=" + DEFAULT_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao is less than UPDATED_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.lessThan=" + UPDATED_SOBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosBySobaibaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where sobaibao is greater than DEFAULT_SOBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("sobaibao.greaterThan=" + DEFAULT_SOBAIBAO);

        // Get all the danhsachbaibaoList where sobaibao is greater than SMALLER_SOBAIBAO
        defaultDanhsachbaibaoShouldBeFound("sobaibao.greaterThan=" + SMALLER_SOBAIBAO);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPpbaibaoIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ppbaibao equals to DEFAULT_PPBAIBAO
        defaultDanhsachbaibaoShouldBeFound("ppbaibao.equals=" + DEFAULT_PPBAIBAO);

        // Get all the danhsachbaibaoList where ppbaibao equals to UPDATED_PPBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("ppbaibao.equals=" + UPDATED_PPBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPpbaibaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ppbaibao not equals to DEFAULT_PPBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("ppbaibao.notEquals=" + DEFAULT_PPBAIBAO);

        // Get all the danhsachbaibaoList where ppbaibao not equals to UPDATED_PPBAIBAO
        defaultDanhsachbaibaoShouldBeFound("ppbaibao.notEquals=" + UPDATED_PPBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPpbaibaoIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ppbaibao in DEFAULT_PPBAIBAO or UPDATED_PPBAIBAO
        defaultDanhsachbaibaoShouldBeFound("ppbaibao.in=" + DEFAULT_PPBAIBAO + "," + UPDATED_PPBAIBAO);

        // Get all the danhsachbaibaoList where ppbaibao equals to UPDATED_PPBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("ppbaibao.in=" + UPDATED_PPBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPpbaibaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ppbaibao is not null
        defaultDanhsachbaibaoShouldBeFound("ppbaibao.specified=true");

        // Get all the danhsachbaibaoList where ppbaibao is null
        defaultDanhsachbaibaoShouldNotBeFound("ppbaibao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByPpbaibaoContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ppbaibao contains DEFAULT_PPBAIBAO
        defaultDanhsachbaibaoShouldBeFound("ppbaibao.contains=" + DEFAULT_PPBAIBAO);

        // Get all the danhsachbaibaoList where ppbaibao contains UPDATED_PPBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("ppbaibao.contains=" + UPDATED_PPBAIBAO);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByPpbaibaoNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ppbaibao does not contain DEFAULT_PPBAIBAO
        defaultDanhsachbaibaoShouldNotBeFound("ppbaibao.doesNotContain=" + DEFAULT_PPBAIBAO);

        // Get all the danhsachbaibaoList where ppbaibao does not contain UPDATED_PPBAIBAO
        defaultDanhsachbaibaoShouldBeFound("ppbaibao.doesNotContain=" + UPDATED_PPBAIBAO);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where link equals to DEFAULT_LINK
        defaultDanhsachbaibaoShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the danhsachbaibaoList where link equals to UPDATED_LINK
        defaultDanhsachbaibaoShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where link not equals to DEFAULT_LINK
        defaultDanhsachbaibaoShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the danhsachbaibaoList where link not equals to UPDATED_LINK
        defaultDanhsachbaibaoShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where link in DEFAULT_LINK or UPDATED_LINK
        defaultDanhsachbaibaoShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the danhsachbaibaoList where link equals to UPDATED_LINK
        defaultDanhsachbaibaoShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where link is not null
        defaultDanhsachbaibaoShouldBeFound("link.specified=true");

        // Get all the danhsachbaibaoList where link is null
        defaultDanhsachbaibaoShouldNotBeFound("link.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByLinkContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where link contains DEFAULT_LINK
        defaultDanhsachbaibaoShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the danhsachbaibaoList where link contains UPDATED_LINK
        defaultDanhsachbaibaoShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where link does not contain DEFAULT_LINK
        defaultDanhsachbaibaoShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the danhsachbaibaoList where link does not contain UPDATED_LINK
        defaultDanhsachbaibaoShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByGhichuIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ghichu equals to DEFAULT_GHICHU
        defaultDanhsachbaibaoShouldBeFound("ghichu.equals=" + DEFAULT_GHICHU);

        // Get all the danhsachbaibaoList where ghichu equals to UPDATED_GHICHU
        defaultDanhsachbaibaoShouldNotBeFound("ghichu.equals=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByGhichuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ghichu not equals to DEFAULT_GHICHU
        defaultDanhsachbaibaoShouldNotBeFound("ghichu.notEquals=" + DEFAULT_GHICHU);

        // Get all the danhsachbaibaoList where ghichu not equals to UPDATED_GHICHU
        defaultDanhsachbaibaoShouldBeFound("ghichu.notEquals=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByGhichuIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ghichu in DEFAULT_GHICHU or UPDATED_GHICHU
        defaultDanhsachbaibaoShouldBeFound("ghichu.in=" + DEFAULT_GHICHU + "," + UPDATED_GHICHU);

        // Get all the danhsachbaibaoList where ghichu equals to UPDATED_GHICHU
        defaultDanhsachbaibaoShouldNotBeFound("ghichu.in=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByGhichuIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ghichu is not null
        defaultDanhsachbaibaoShouldBeFound("ghichu.specified=true");

        // Get all the danhsachbaibaoList where ghichu is null
        defaultDanhsachbaibaoShouldNotBeFound("ghichu.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByGhichuContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ghichu contains DEFAULT_GHICHU
        defaultDanhsachbaibaoShouldBeFound("ghichu.contains=" + DEFAULT_GHICHU);

        // Get all the danhsachbaibaoList where ghichu contains UPDATED_GHICHU
        defaultDanhsachbaibaoShouldNotBeFound("ghichu.contains=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByGhichuNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where ghichu does not contain DEFAULT_GHICHU
        defaultDanhsachbaibaoShouldNotBeFound("ghichu.doesNotContain=" + DEFAULT_GHICHU);

        // Get all the danhsachbaibaoList where ghichu does not contain UPDATED_GHICHU
        defaultDanhsachbaibaoShouldBeFound("ghichu.doesNotContain=" + UPDATED_GHICHU);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiachinhIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiachinh equals to DEFAULT_TACGIACHINH
        defaultDanhsachbaibaoShouldBeFound("tacgiachinh.equals=" + DEFAULT_TACGIACHINH);

        // Get all the danhsachbaibaoList where tacgiachinh equals to UPDATED_TACGIACHINH
        defaultDanhsachbaibaoShouldNotBeFound("tacgiachinh.equals=" + UPDATED_TACGIACHINH);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiachinhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiachinh not equals to DEFAULT_TACGIACHINH
        defaultDanhsachbaibaoShouldNotBeFound("tacgiachinh.notEquals=" + DEFAULT_TACGIACHINH);

        // Get all the danhsachbaibaoList where tacgiachinh not equals to UPDATED_TACGIACHINH
        defaultDanhsachbaibaoShouldBeFound("tacgiachinh.notEquals=" + UPDATED_TACGIACHINH);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiachinhIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiachinh in DEFAULT_TACGIACHINH or UPDATED_TACGIACHINH
        defaultDanhsachbaibaoShouldBeFound("tacgiachinh.in=" + DEFAULT_TACGIACHINH + "," + UPDATED_TACGIACHINH);

        // Get all the danhsachbaibaoList where tacgiachinh equals to UPDATED_TACGIACHINH
        defaultDanhsachbaibaoShouldNotBeFound("tacgiachinh.in=" + UPDATED_TACGIACHINH);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiachinhIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiachinh is not null
        defaultDanhsachbaibaoShouldBeFound("tacgiachinh.specified=true");

        // Get all the danhsachbaibaoList where tacgiachinh is null
        defaultDanhsachbaibaoShouldNotBeFound("tacgiachinh.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiachinhContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiachinh contains DEFAULT_TACGIACHINH
        defaultDanhsachbaibaoShouldBeFound("tacgiachinh.contains=" + DEFAULT_TACGIACHINH);

        // Get all the danhsachbaibaoList where tacgiachinh contains UPDATED_TACGIACHINH
        defaultDanhsachbaibaoShouldNotBeFound("tacgiachinh.contains=" + UPDATED_TACGIACHINH);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiachinhNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiachinh does not contain DEFAULT_TACGIACHINH
        defaultDanhsachbaibaoShouldNotBeFound("tacgiachinh.doesNotContain=" + DEFAULT_TACGIACHINH);

        // Get all the danhsachbaibaoList where tacgiachinh does not contain UPDATED_TACGIACHINH
        defaultDanhsachbaibaoShouldBeFound("tacgiachinh.doesNotContain=" + UPDATED_TACGIACHINH);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiakhacIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiakhac equals to DEFAULT_TACGIAKHAC
        defaultDanhsachbaibaoShouldBeFound("tacgiakhac.equals=" + DEFAULT_TACGIAKHAC);

        // Get all the danhsachbaibaoList where tacgiakhac equals to UPDATED_TACGIAKHAC
        defaultDanhsachbaibaoShouldNotBeFound("tacgiakhac.equals=" + UPDATED_TACGIAKHAC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiakhacIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiakhac not equals to DEFAULT_TACGIAKHAC
        defaultDanhsachbaibaoShouldNotBeFound("tacgiakhac.notEquals=" + DEFAULT_TACGIAKHAC);

        // Get all the danhsachbaibaoList where tacgiakhac not equals to UPDATED_TACGIAKHAC
        defaultDanhsachbaibaoShouldBeFound("tacgiakhac.notEquals=" + UPDATED_TACGIAKHAC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiakhacIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiakhac in DEFAULT_TACGIAKHAC or UPDATED_TACGIAKHAC
        defaultDanhsachbaibaoShouldBeFound("tacgiakhac.in=" + DEFAULT_TACGIAKHAC + "," + UPDATED_TACGIAKHAC);

        // Get all the danhsachbaibaoList where tacgiakhac equals to UPDATED_TACGIAKHAC
        defaultDanhsachbaibaoShouldNotBeFound("tacgiakhac.in=" + UPDATED_TACGIAKHAC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiakhacIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiakhac is not null
        defaultDanhsachbaibaoShouldBeFound("tacgiakhac.specified=true");

        // Get all the danhsachbaibaoList where tacgiakhac is null
        defaultDanhsachbaibaoShouldNotBeFound("tacgiakhac.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiakhacContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiakhac contains DEFAULT_TACGIAKHAC
        defaultDanhsachbaibaoShouldBeFound("tacgiakhac.contains=" + DEFAULT_TACGIAKHAC);

        // Get all the danhsachbaibaoList where tacgiakhac contains UPDATED_TACGIAKHAC
        defaultDanhsachbaibaoShouldNotBeFound("tacgiakhac.contains=" + UPDATED_TACGIAKHAC);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTacgiakhacNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tacgiakhac does not contain DEFAULT_TACGIAKHAC
        defaultDanhsachbaibaoShouldNotBeFound("tacgiakhac.doesNotContain=" + DEFAULT_TACGIAKHAC);

        // Get all the danhsachbaibaoList where tacgiakhac does not contain UPDATED_TACGIAKHAC
        defaultDanhsachbaibaoShouldBeFound("tacgiakhac.doesNotContain=" + UPDATED_TACGIAKHAC);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTendetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tendetai equals to DEFAULT_TENDETAI
        defaultDanhsachbaibaoShouldBeFound("tendetai.equals=" + DEFAULT_TENDETAI);

        // Get all the danhsachbaibaoList where tendetai equals to UPDATED_TENDETAI
        defaultDanhsachbaibaoShouldNotBeFound("tendetai.equals=" + UPDATED_TENDETAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTendetaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tendetai not equals to DEFAULT_TENDETAI
        defaultDanhsachbaibaoShouldNotBeFound("tendetai.notEquals=" + DEFAULT_TENDETAI);

        // Get all the danhsachbaibaoList where tendetai not equals to UPDATED_TENDETAI
        defaultDanhsachbaibaoShouldBeFound("tendetai.notEquals=" + UPDATED_TENDETAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTendetaiIsInShouldWork() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tendetai in DEFAULT_TENDETAI or UPDATED_TENDETAI
        defaultDanhsachbaibaoShouldBeFound("tendetai.in=" + DEFAULT_TENDETAI + "," + UPDATED_TENDETAI);

        // Get all the danhsachbaibaoList where tendetai equals to UPDATED_TENDETAI
        defaultDanhsachbaibaoShouldNotBeFound("tendetai.in=" + UPDATED_TENDETAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTendetaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tendetai is not null
        defaultDanhsachbaibaoShouldBeFound("tendetai.specified=true");

        // Get all the danhsachbaibaoList where tendetai is null
        defaultDanhsachbaibaoShouldNotBeFound("tendetai.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhsachbaibaosByTendetaiContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tendetai contains DEFAULT_TENDETAI
        defaultDanhsachbaibaoShouldBeFound("tendetai.contains=" + DEFAULT_TENDETAI);

        // Get all the danhsachbaibaoList where tendetai contains UPDATED_TENDETAI
        defaultDanhsachbaibaoShouldNotBeFound("tendetai.contains=" + UPDATED_TENDETAI);
    }

    @Test
    @Transactional
    public void getAllDanhsachbaibaosByTendetaiNotContainsSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        // Get all the danhsachbaibaoList where tendetai does not contain DEFAULT_TENDETAI
        defaultDanhsachbaibaoShouldNotBeFound("tendetai.doesNotContain=" + DEFAULT_TENDETAI);

        // Get all the danhsachbaibaoList where tendetai does not contain UPDATED_TENDETAI
        defaultDanhsachbaibaoShouldBeFound("tendetai.doesNotContain=" + UPDATED_TENDETAI);
    }


    @Test
    @Transactional
    public void getAllDanhsachbaibaosByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        danhsachbaibao.setDetai(detai);
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);
        Long detaiId = detai.getId();

        // Get all the danhsachbaibaoList where detai equals to detaiId
        defaultDanhsachbaibaoShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the danhsachbaibaoList where detai equals to detaiId + 1
        defaultDanhsachbaibaoShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDanhsachbaibaoShouldBeFound(String filter) throws Exception {
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhsachbaibao.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenbaibao").value(hasItem(DEFAULT_TENBAIBAO)))
            .andExpect(jsonPath("$.[*].phanloai").value(hasItem(DEFAULT_PHANLOAI)))
            .andExpect(jsonPath("$.[*].tenhoithao").value(hasItem(DEFAULT_TENHOITHAO)))
            .andExpect(jsonPath("$.[*].namxuatban").value(hasItem(DEFAULT_NAMXUATBAN)))
            .andExpect(jsonPath("$.[*].thangxuatban").value(hasItem(DEFAULT_THANGXUATBAN)))
            .andExpect(jsonPath("$.[*].danhmuc").value(hasItem(DEFAULT_DANHMUC)))
            .andExpect(jsonPath("$.[*].iffff").value(hasItem(DEFAULT_IFFFF)))
            .andExpect(jsonPath("$.[*].hindex").value(hasItem(DEFAULT_HINDEX)))
            .andExpect(jsonPath("$.[*].xeploai").value(hasItem(DEFAULT_XEPLOAI)))
            .andExpect(jsonPath("$.[*].rankbaibao").value(hasItem(DEFAULT_RANKBAIBAO)))
            .andExpect(jsonPath("$.[*].volbaibao").value(hasItem(DEFAULT_VOLBAIBAO)))
            .andExpect(jsonPath("$.[*].sobaibao").value(hasItem(DEFAULT_SOBAIBAO)))
            .andExpect(jsonPath("$.[*].ppbaibao").value(hasItem(DEFAULT_PPBAIBAO)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].ghichu").value(hasItem(DEFAULT_GHICHU)))
            .andExpect(jsonPath("$.[*].tacgiachinh").value(hasItem(DEFAULT_TACGIACHINH)))
            .andExpect(jsonPath("$.[*].tacgiakhac").value(hasItem(DEFAULT_TACGIAKHAC)))
            .andExpect(jsonPath("$.[*].tendetai").value(hasItem(DEFAULT_TENDETAI)));

        // Check, that the count call also returns 1
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDanhsachbaibaoShouldNotBeFound(String filter) throws Exception {
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDanhsachbaibao() throws Exception {
        // Get the danhsachbaibao
        restDanhsachbaibaoMockMvc.perform(get("/api/danhsachbaibaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDanhsachbaibao() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        int databaseSizeBeforeUpdate = danhsachbaibaoRepository.findAll().size();

        // Update the danhsachbaibao
        Danhsachbaibao updatedDanhsachbaibao = danhsachbaibaoRepository.findById(danhsachbaibao.getId()).get();
        // Disconnect from session so that the updates on updatedDanhsachbaibao are not directly saved in db
        em.detach(updatedDanhsachbaibao);
        updatedDanhsachbaibao
            .tenbaibao(UPDATED_TENBAIBAO)
            .phanloai(UPDATED_PHANLOAI)
            .tenhoithao(UPDATED_TENHOITHAO)
            .namxuatban(UPDATED_NAMXUATBAN)
            .thangxuatban(UPDATED_THANGXUATBAN)
            .danhmuc(UPDATED_DANHMUC)
            .iffff(UPDATED_IFFFF)
            .hindex(UPDATED_HINDEX)
            .xeploai(UPDATED_XEPLOAI)
            .rankbaibao(UPDATED_RANKBAIBAO)
            .volbaibao(UPDATED_VOLBAIBAO)
            .sobaibao(UPDATED_SOBAIBAO)
            .ppbaibao(UPDATED_PPBAIBAO)
            .link(UPDATED_LINK)
            .ghichu(UPDATED_GHICHU)
            .tacgiachinh(UPDATED_TACGIACHINH)
            .tacgiakhac(UPDATED_TACGIAKHAC)
            .tendetai(UPDATED_TENDETAI);
        DanhsachbaibaoDTO danhsachbaibaoDTO = danhsachbaibaoMapper.toDto(updatedDanhsachbaibao);

        restDanhsachbaibaoMockMvc.perform(put("/api/danhsachbaibaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhsachbaibaoDTO)))
            .andExpect(status().isOk());

        // Validate the Danhsachbaibao in the database
        List<Danhsachbaibao> danhsachbaibaoList = danhsachbaibaoRepository.findAll();
        assertThat(danhsachbaibaoList).hasSize(databaseSizeBeforeUpdate);
        Danhsachbaibao testDanhsachbaibao = danhsachbaibaoList.get(danhsachbaibaoList.size() - 1);
        assertThat(testDanhsachbaibao.getTenbaibao()).isEqualTo(UPDATED_TENBAIBAO);
        assertThat(testDanhsachbaibao.getPhanloai()).isEqualTo(UPDATED_PHANLOAI);
        assertThat(testDanhsachbaibao.getTenhoithao()).isEqualTo(UPDATED_TENHOITHAO);
        assertThat(testDanhsachbaibao.getNamxuatban()).isEqualTo(UPDATED_NAMXUATBAN);
        assertThat(testDanhsachbaibao.getThangxuatban()).isEqualTo(UPDATED_THANGXUATBAN);
        assertThat(testDanhsachbaibao.getDanhmuc()).isEqualTo(UPDATED_DANHMUC);
        assertThat(testDanhsachbaibao.getIffff()).isEqualTo(UPDATED_IFFFF);
        assertThat(testDanhsachbaibao.getHindex()).isEqualTo(UPDATED_HINDEX);
        assertThat(testDanhsachbaibao.getXeploai()).isEqualTo(UPDATED_XEPLOAI);
        assertThat(testDanhsachbaibao.getRankbaibao()).isEqualTo(UPDATED_RANKBAIBAO);
        assertThat(testDanhsachbaibao.getVolbaibao()).isEqualTo(UPDATED_VOLBAIBAO);
        assertThat(testDanhsachbaibao.getSobaibao()).isEqualTo(UPDATED_SOBAIBAO);
        assertThat(testDanhsachbaibao.getPpbaibao()).isEqualTo(UPDATED_PPBAIBAO);
        assertThat(testDanhsachbaibao.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testDanhsachbaibao.getGhichu()).isEqualTo(UPDATED_GHICHU);
        assertThat(testDanhsachbaibao.getTacgiachinh()).isEqualTo(UPDATED_TACGIACHINH);
        assertThat(testDanhsachbaibao.getTacgiakhac()).isEqualTo(UPDATED_TACGIAKHAC);
        assertThat(testDanhsachbaibao.getTendetai()).isEqualTo(UPDATED_TENDETAI);
    }

    @Test
    @Transactional
    public void updateNonExistingDanhsachbaibao() throws Exception {
        int databaseSizeBeforeUpdate = danhsachbaibaoRepository.findAll().size();

        // Create the Danhsachbaibao
        DanhsachbaibaoDTO danhsachbaibaoDTO = danhsachbaibaoMapper.toDto(danhsachbaibao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhsachbaibaoMockMvc.perform(put("/api/danhsachbaibaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhsachbaibaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Danhsachbaibao in the database
        List<Danhsachbaibao> danhsachbaibaoList = danhsachbaibaoRepository.findAll();
        assertThat(danhsachbaibaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDanhsachbaibao() throws Exception {
        // Initialize the database
        danhsachbaibaoRepository.saveAndFlush(danhsachbaibao);

        int databaseSizeBeforeDelete = danhsachbaibaoRepository.findAll().size();

        // Delete the danhsachbaibao
        restDanhsachbaibaoMockMvc.perform(delete("/api/danhsachbaibaos/{id}", danhsachbaibao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Danhsachbaibao> danhsachbaibaoList = danhsachbaibaoRepository.findAll();
        assertThat(danhsachbaibaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
