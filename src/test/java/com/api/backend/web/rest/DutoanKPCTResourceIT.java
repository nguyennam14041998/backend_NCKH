package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.DutoanKPCT;
import com.api.backend.domain.DutoanKP;
import com.api.backend.domain.NoidungDT;
import com.api.backend.repository.DutoanKPCTRepository;
import com.api.backend.service.DutoanKPCTService;
import com.api.backend.service.dto.DutoanKPCTDTO;
import com.api.backend.service.mapper.DutoanKPCTMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DutoanKPCTCriteria;
import com.api.backend.service.DutoanKPCTQueryService;

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
 * Integration tests for the {@link DutoanKPCTResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DutoanKPCTResourceIT {

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOLUONG = 1;
    private static final Integer UPDATED_SOLUONG = 2;
    private static final Integer SMALLER_SOLUONG = 1 - 1;

    private static final Integer DEFAULT_MUCCHI = 1;
    private static final Integer UPDATED_MUCCHI = 2;
    private static final Integer SMALLER_MUCCHI = 1 - 1;

    private static final Integer DEFAULT_TONG = 1;
    private static final Integer UPDATED_TONG = 2;
    private static final Integer SMALLER_TONG = 1 - 1;

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private DutoanKPCTRepository dutoanKPCTRepository;

    @Autowired
    private DutoanKPCTMapper dutoanKPCTMapper;

    @Autowired
    private DutoanKPCTService dutoanKPCTService;

    @Autowired
    private DutoanKPCTQueryService dutoanKPCTQueryService;

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

    private MockMvc restDutoanKPCTMockMvc;

    private DutoanKPCT dutoanKPCT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DutoanKPCTResource dutoanKPCTResource = new DutoanKPCTResource(dutoanKPCTService, dutoanKPCTQueryService);
        this.restDutoanKPCTMockMvc = MockMvcBuilders.standaloneSetup(dutoanKPCTResource)
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
    public static DutoanKPCT createEntity(EntityManager em) {
        DutoanKPCT dutoanKPCT = new DutoanKPCT()
            .noidung(DEFAULT_NOIDUNG)
            .soluong(DEFAULT_SOLUONG)
            .mucchi(DEFAULT_MUCCHI)
            .tong(DEFAULT_TONG)
            .sudung(DEFAULT_SUDUNG);
        return dutoanKPCT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DutoanKPCT createUpdatedEntity(EntityManager em) {
        DutoanKPCT dutoanKPCT = new DutoanKPCT()
            .noidung(UPDATED_NOIDUNG)
            .soluong(UPDATED_SOLUONG)
            .mucchi(UPDATED_MUCCHI)
            .tong(UPDATED_TONG)
            .sudung(UPDATED_SUDUNG);
        return dutoanKPCT;
    }

    @BeforeEach
    public void initTest() {
        dutoanKPCT = createEntity(em);
    }

    @Test
    @Transactional
    public void createDutoanKPCT() throws Exception {
        int databaseSizeBeforeCreate = dutoanKPCTRepository.findAll().size();

        // Create the DutoanKPCT
        DutoanKPCTDTO dutoanKPCTDTO = dutoanKPCTMapper.toDto(dutoanKPCT);
        restDutoanKPCTMockMvc.perform(post("/api/dutoan-kpcts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPCTDTO)))
            .andExpect(status().isCreated());

        // Validate the DutoanKPCT in the database
        List<DutoanKPCT> dutoanKPCTList = dutoanKPCTRepository.findAll();
        assertThat(dutoanKPCTList).hasSize(databaseSizeBeforeCreate + 1);
        DutoanKPCT testDutoanKPCT = dutoanKPCTList.get(dutoanKPCTList.size() - 1);
        assertThat(testDutoanKPCT.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testDutoanKPCT.getSoluong()).isEqualTo(DEFAULT_SOLUONG);
        assertThat(testDutoanKPCT.getMucchi()).isEqualTo(DEFAULT_MUCCHI);
        assertThat(testDutoanKPCT.getTong()).isEqualTo(DEFAULT_TONG);
        assertThat(testDutoanKPCT.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createDutoanKPCTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dutoanKPCTRepository.findAll().size();

        // Create the DutoanKPCT with an existing ID
        dutoanKPCT.setId(1L);
        DutoanKPCTDTO dutoanKPCTDTO = dutoanKPCTMapper.toDto(dutoanKPCT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDutoanKPCTMockMvc.perform(post("/api/dutoan-kpcts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPCTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DutoanKPCT in the database
        List<DutoanKPCT> dutoanKPCTList = dutoanKPCTRepository.findAll();
        assertThat(dutoanKPCTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTS() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dutoanKPCT.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].soluong").value(hasItem(DEFAULT_SOLUONG)))
            .andExpect(jsonPath("$.[*].mucchi").value(hasItem(DEFAULT_MUCCHI)))
            .andExpect(jsonPath("$.[*].tong").value(hasItem(DEFAULT_TONG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getDutoanKPCT() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get the dutoanKPCT
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts/{id}", dutoanKPCT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dutoanKPCT.getId().intValue()))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.soluong").value(DEFAULT_SOLUONG))
            .andExpect(jsonPath("$.mucchi").value(DEFAULT_MUCCHI))
            .andExpect(jsonPath("$.tong").value(DEFAULT_TONG))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getDutoanKPCTSByIdFiltering() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        Long id = dutoanKPCT.getId();

        defaultDutoanKPCTShouldBeFound("id.equals=" + id);
        defaultDutoanKPCTShouldNotBeFound("id.notEquals=" + id);

        defaultDutoanKPCTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDutoanKPCTShouldNotBeFound("id.greaterThan=" + id);

        defaultDutoanKPCTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDutoanKPCTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where noidung equals to DEFAULT_NOIDUNG
        defaultDutoanKPCTShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPCTList where noidung equals to UPDATED_NOIDUNG
        defaultDutoanKPCTShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where noidung not equals to DEFAULT_NOIDUNG
        defaultDutoanKPCTShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPCTList where noidung not equals to UPDATED_NOIDUNG
        defaultDutoanKPCTShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultDutoanKPCTShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the dutoanKPCTList where noidung equals to UPDATED_NOIDUNG
        defaultDutoanKPCTShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where noidung is not null
        defaultDutoanKPCTShouldBeFound("noidung.specified=true");

        // Get all the dutoanKPCTList where noidung is null
        defaultDutoanKPCTShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where noidung contains DEFAULT_NOIDUNG
        defaultDutoanKPCTShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPCTList where noidung contains UPDATED_NOIDUNG
        defaultDutoanKPCTShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where noidung does not contain DEFAULT_NOIDUNG
        defaultDutoanKPCTShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPCTList where noidung does not contain UPDATED_NOIDUNG
        defaultDutoanKPCTShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong equals to DEFAULT_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.equals=" + DEFAULT_SOLUONG);

        // Get all the dutoanKPCTList where soluong equals to UPDATED_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.equals=" + UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong not equals to DEFAULT_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.notEquals=" + DEFAULT_SOLUONG);

        // Get all the dutoanKPCTList where soluong not equals to UPDATED_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.notEquals=" + UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong in DEFAULT_SOLUONG or UPDATED_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.in=" + DEFAULT_SOLUONG + "," + UPDATED_SOLUONG);

        // Get all the dutoanKPCTList where soluong equals to UPDATED_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.in=" + UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong is not null
        defaultDutoanKPCTShouldBeFound("soluong.specified=true");

        // Get all the dutoanKPCTList where soluong is null
        defaultDutoanKPCTShouldNotBeFound("soluong.specified=false");
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong is greater than or equal to DEFAULT_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.greaterThanOrEqual=" + DEFAULT_SOLUONG);

        // Get all the dutoanKPCTList where soluong is greater than or equal to UPDATED_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.greaterThanOrEqual=" + UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong is less than or equal to DEFAULT_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.lessThanOrEqual=" + DEFAULT_SOLUONG);

        // Get all the dutoanKPCTList where soluong is less than or equal to SMALLER_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.lessThanOrEqual=" + SMALLER_SOLUONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsLessThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong is less than DEFAULT_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.lessThan=" + DEFAULT_SOLUONG);

        // Get all the dutoanKPCTList where soluong is less than UPDATED_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.lessThan=" + UPDATED_SOLUONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySoluongIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where soluong is greater than DEFAULT_SOLUONG
        defaultDutoanKPCTShouldNotBeFound("soluong.greaterThan=" + DEFAULT_SOLUONG);

        // Get all the dutoanKPCTList where soluong is greater than SMALLER_SOLUONG
        defaultDutoanKPCTShouldBeFound("soluong.greaterThan=" + SMALLER_SOLUONG);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi equals to DEFAULT_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.equals=" + DEFAULT_MUCCHI);

        // Get all the dutoanKPCTList where mucchi equals to UPDATED_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.equals=" + UPDATED_MUCCHI);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi not equals to DEFAULT_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.notEquals=" + DEFAULT_MUCCHI);

        // Get all the dutoanKPCTList where mucchi not equals to UPDATED_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.notEquals=" + UPDATED_MUCCHI);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi in DEFAULT_MUCCHI or UPDATED_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.in=" + DEFAULT_MUCCHI + "," + UPDATED_MUCCHI);

        // Get all the dutoanKPCTList where mucchi equals to UPDATED_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.in=" + UPDATED_MUCCHI);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi is not null
        defaultDutoanKPCTShouldBeFound("mucchi.specified=true");

        // Get all the dutoanKPCTList where mucchi is null
        defaultDutoanKPCTShouldNotBeFound("mucchi.specified=false");
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi is greater than or equal to DEFAULT_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.greaterThanOrEqual=" + DEFAULT_MUCCHI);

        // Get all the dutoanKPCTList where mucchi is greater than or equal to UPDATED_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.greaterThanOrEqual=" + UPDATED_MUCCHI);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi is less than or equal to DEFAULT_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.lessThanOrEqual=" + DEFAULT_MUCCHI);

        // Get all the dutoanKPCTList where mucchi is less than or equal to SMALLER_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.lessThanOrEqual=" + SMALLER_MUCCHI);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsLessThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi is less than DEFAULT_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.lessThan=" + DEFAULT_MUCCHI);

        // Get all the dutoanKPCTList where mucchi is less than UPDATED_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.lessThan=" + UPDATED_MUCCHI);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByMucchiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where mucchi is greater than DEFAULT_MUCCHI
        defaultDutoanKPCTShouldNotBeFound("mucchi.greaterThan=" + DEFAULT_MUCCHI);

        // Get all the dutoanKPCTList where mucchi is greater than SMALLER_MUCCHI
        defaultDutoanKPCTShouldBeFound("mucchi.greaterThan=" + SMALLER_MUCCHI);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong equals to DEFAULT_TONG
        defaultDutoanKPCTShouldBeFound("tong.equals=" + DEFAULT_TONG);

        // Get all the dutoanKPCTList where tong equals to UPDATED_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.equals=" + UPDATED_TONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong not equals to DEFAULT_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.notEquals=" + DEFAULT_TONG);

        // Get all the dutoanKPCTList where tong not equals to UPDATED_TONG
        defaultDutoanKPCTShouldBeFound("tong.notEquals=" + UPDATED_TONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong in DEFAULT_TONG or UPDATED_TONG
        defaultDutoanKPCTShouldBeFound("tong.in=" + DEFAULT_TONG + "," + UPDATED_TONG);

        // Get all the dutoanKPCTList where tong equals to UPDATED_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.in=" + UPDATED_TONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong is not null
        defaultDutoanKPCTShouldBeFound("tong.specified=true");

        // Get all the dutoanKPCTList where tong is null
        defaultDutoanKPCTShouldNotBeFound("tong.specified=false");
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong is greater than or equal to DEFAULT_TONG
        defaultDutoanKPCTShouldBeFound("tong.greaterThanOrEqual=" + DEFAULT_TONG);

        // Get all the dutoanKPCTList where tong is greater than or equal to UPDATED_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.greaterThanOrEqual=" + UPDATED_TONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong is less than or equal to DEFAULT_TONG
        defaultDutoanKPCTShouldBeFound("tong.lessThanOrEqual=" + DEFAULT_TONG);

        // Get all the dutoanKPCTList where tong is less than or equal to SMALLER_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.lessThanOrEqual=" + SMALLER_TONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsLessThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong is less than DEFAULT_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.lessThan=" + DEFAULT_TONG);

        // Get all the dutoanKPCTList where tong is less than UPDATED_TONG
        defaultDutoanKPCTShouldBeFound("tong.lessThan=" + UPDATED_TONG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSByTongIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where tong is greater than DEFAULT_TONG
        defaultDutoanKPCTShouldNotBeFound("tong.greaterThan=" + DEFAULT_TONG);

        // Get all the dutoanKPCTList where tong is greater than SMALLER_TONG
        defaultDutoanKPCTShouldBeFound("tong.greaterThan=" + SMALLER_TONG);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung equals to DEFAULT_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPCTList where sudung equals to UPDATED_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung not equals to DEFAULT_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPCTList where sudung not equals to UPDATED_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the dutoanKPCTList where sudung equals to UPDATED_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung is not null
        defaultDutoanKPCTShouldBeFound("sudung.specified=true");

        // Get all the dutoanKPCTList where sudung is null
        defaultDutoanKPCTShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPCTList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPCTList where sudung is less than or equal to SMALLER_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung is less than DEFAULT_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPCTList where sudung is less than UPDATED_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPCTSBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        // Get all the dutoanKPCTList where sudung is greater than DEFAULT_SUDUNG
        defaultDutoanKPCTShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPCTList where sudung is greater than SMALLER_SUDUNG
        defaultDutoanKPCTShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSByDutoanKPIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);
        DutoanKP dutoanKP = DutoanKPResourceIT.createEntity(em);
        em.persist(dutoanKP);
        em.flush();
        dutoanKPCT.setDutoanKP(dutoanKP);
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);
        Long dutoanKPId = dutoanKP.getId();

        // Get all the dutoanKPCTList where dutoanKP equals to dutoanKPId
        defaultDutoanKPCTShouldBeFound("dutoanKPId.equals=" + dutoanKPId);

        // Get all the dutoanKPCTList where dutoanKP equals to dutoanKPId + 1
        defaultDutoanKPCTShouldNotBeFound("dutoanKPId.equals=" + (dutoanKPId + 1));
    }


    @Test
    @Transactional
    public void getAllDutoanKPCTSByNoidungDTIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);
        NoidungDT noidungDT = NoidungDTResourceIT.createEntity(em);
        em.persist(noidungDT);
        em.flush();
        dutoanKPCT.setNoidungDT(noidungDT);
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);
        Long noidungDTId = noidungDT.getId();

        // Get all the dutoanKPCTList where noidungDT equals to noidungDTId
        defaultDutoanKPCTShouldBeFound("noidungDTId.equals=" + noidungDTId);

        // Get all the dutoanKPCTList where noidungDT equals to noidungDTId + 1
        defaultDutoanKPCTShouldNotBeFound("noidungDTId.equals=" + (noidungDTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDutoanKPCTShouldBeFound(String filter) throws Exception {
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dutoanKPCT.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].soluong").value(hasItem(DEFAULT_SOLUONG)))
            .andExpect(jsonPath("$.[*].mucchi").value(hasItem(DEFAULT_MUCCHI)))
            .andExpect(jsonPath("$.[*].tong").value(hasItem(DEFAULT_TONG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDutoanKPCTShouldNotBeFound(String filter) throws Exception {
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDutoanKPCT() throws Exception {
        // Get the dutoanKPCT
        restDutoanKPCTMockMvc.perform(get("/api/dutoan-kpcts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDutoanKPCT() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        int databaseSizeBeforeUpdate = dutoanKPCTRepository.findAll().size();

        // Update the dutoanKPCT
        DutoanKPCT updatedDutoanKPCT = dutoanKPCTRepository.findById(dutoanKPCT.getId()).get();
        // Disconnect from session so that the updates on updatedDutoanKPCT are not directly saved in db
        em.detach(updatedDutoanKPCT);
        updatedDutoanKPCT
            .noidung(UPDATED_NOIDUNG)
            .soluong(UPDATED_SOLUONG)
            .mucchi(UPDATED_MUCCHI)
            .tong(UPDATED_TONG)
            .sudung(UPDATED_SUDUNG);
        DutoanKPCTDTO dutoanKPCTDTO = dutoanKPCTMapper.toDto(updatedDutoanKPCT);

        restDutoanKPCTMockMvc.perform(put("/api/dutoan-kpcts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPCTDTO)))
            .andExpect(status().isOk());

        // Validate the DutoanKPCT in the database
        List<DutoanKPCT> dutoanKPCTList = dutoanKPCTRepository.findAll();
        assertThat(dutoanKPCTList).hasSize(databaseSizeBeforeUpdate);
        DutoanKPCT testDutoanKPCT = dutoanKPCTList.get(dutoanKPCTList.size() - 1);
        assertThat(testDutoanKPCT.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testDutoanKPCT.getSoluong()).isEqualTo(UPDATED_SOLUONG);
        assertThat(testDutoanKPCT.getMucchi()).isEqualTo(UPDATED_MUCCHI);
        assertThat(testDutoanKPCT.getTong()).isEqualTo(UPDATED_TONG);
        assertThat(testDutoanKPCT.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDutoanKPCT() throws Exception {
        int databaseSizeBeforeUpdate = dutoanKPCTRepository.findAll().size();

        // Create the DutoanKPCT
        DutoanKPCTDTO dutoanKPCTDTO = dutoanKPCTMapper.toDto(dutoanKPCT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDutoanKPCTMockMvc.perform(put("/api/dutoan-kpcts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPCTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DutoanKPCT in the database
        List<DutoanKPCT> dutoanKPCTList = dutoanKPCTRepository.findAll();
        assertThat(dutoanKPCTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDutoanKPCT() throws Exception {
        // Initialize the database
        dutoanKPCTRepository.saveAndFlush(dutoanKPCT);

        int databaseSizeBeforeDelete = dutoanKPCTRepository.findAll().size();

        // Delete the dutoanKPCT
        restDutoanKPCTMockMvc.perform(delete("/api/dutoan-kpcts/{id}", dutoanKPCT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DutoanKPCT> dutoanKPCTList = dutoanKPCTRepository.findAll();
        assertThat(dutoanKPCTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
