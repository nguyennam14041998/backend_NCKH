package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Nhansu;
import com.api.backend.domain.Chunhiem;
import com.api.backend.domain.Nhansuthamgia;
import com.api.backend.domain.Donvi;
import com.api.backend.domain.Chucdanh;
import com.api.backend.domain.Hocham;
import com.api.backend.repository.NhansuRepository;
import com.api.backend.service.NhansuService;
import com.api.backend.service.dto.NhansuDTO;
import com.api.backend.service.mapper.NhansuMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.NhansuCriteria;
import com.api.backend.service.NhansuQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.api.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NhansuResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class NhansuResourceIT {

    private static final String DEFAULT_MANHANSU = "AAAAAAAAAA";
    private static final String UPDATED_MANHANSU = "BBBBBBBBBB";

    private static final String DEFAULT_TENNHANSU = "AAAAAAAAAA";
    private static final String UPDATED_TENNHANSU = "BBBBBBBBBB";

    private static final Integer DEFAULT_SDT = 1;
    private static final Integer UPDATED_SDT = 2;
    private static final Integer SMALLER_SDT = 1 - 1;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final String DEFAULT_NAMSINH = "AAAAAAAAAA";
    private static final String UPDATED_NAMSINH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NGAYSINH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NGAYSINH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NGAYSINH = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private NhansuRepository nhansuRepository;

    @Autowired
    private NhansuMapper nhansuMapper;

    @Autowired
    private NhansuService nhansuService;

    @Autowired
    private NhansuQueryService nhansuQueryService;

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

    private MockMvc restNhansuMockMvc;

    private Nhansu nhansu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhansuResource nhansuResource = new NhansuResource(nhansuService, nhansuQueryService);
        this.restNhansuMockMvc = MockMvcBuilders.standaloneSetup(nhansuResource)
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
    public static Nhansu createEntity(EntityManager em) {
        Nhansu nhansu = new Nhansu()
            .manhansu(DEFAULT_MANHANSU)
            .tennhansu(DEFAULT_TENNHANSU)
            .sdt(DEFAULT_SDT)
            .email(DEFAULT_EMAIL)
            .diachi(DEFAULT_DIACHI)
            .namsinh(DEFAULT_NAMSINH)
            .ngaysinh(DEFAULT_NGAYSINH)
            .sudung(DEFAULT_SUDUNG);
        return nhansu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nhansu createUpdatedEntity(EntityManager em) {
        Nhansu nhansu = new Nhansu()
            .manhansu(UPDATED_MANHANSU)
            .tennhansu(UPDATED_TENNHANSU)
            .sdt(UPDATED_SDT)
            .email(UPDATED_EMAIL)
            .diachi(UPDATED_DIACHI)
            .namsinh(UPDATED_NAMSINH)
            .ngaysinh(UPDATED_NGAYSINH)
            .sudung(UPDATED_SUDUNG);
        return nhansu;
    }

    @BeforeEach
    public void initTest() {
        nhansu = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhansu() throws Exception {
        int databaseSizeBeforeCreate = nhansuRepository.findAll().size();

        // Create the Nhansu
        NhansuDTO nhansuDTO = nhansuMapper.toDto(nhansu);
        restNhansuMockMvc.perform(post("/api/nhansus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuDTO)))
            .andExpect(status().isCreated());

        // Validate the Nhansu in the database
        List<Nhansu> nhansuList = nhansuRepository.findAll();
        assertThat(nhansuList).hasSize(databaseSizeBeforeCreate + 1);
        Nhansu testNhansu = nhansuList.get(nhansuList.size() - 1);
        assertThat(testNhansu.getManhansu()).isEqualTo(DEFAULT_MANHANSU);
        assertThat(testNhansu.getTennhansu()).isEqualTo(DEFAULT_TENNHANSU);
        assertThat(testNhansu.getSdt()).isEqualTo(DEFAULT_SDT);
        assertThat(testNhansu.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNhansu.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testNhansu.getNamsinh()).isEqualTo(DEFAULT_NAMSINH);
        assertThat(testNhansu.getNgaysinh()).isEqualTo(DEFAULT_NGAYSINH);
        assertThat(testNhansu.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createNhansuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhansuRepository.findAll().size();

        // Create the Nhansu with an existing ID
        nhansu.setId(1L);
        NhansuDTO nhansuDTO = nhansuMapper.toDto(nhansu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhansuMockMvc.perform(post("/api/nhansus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nhansu in the database
        List<Nhansu> nhansuList = nhansuRepository.findAll();
        assertThat(nhansuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNhansus() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList
        restNhansuMockMvc.perform(get("/api/nhansus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhansu.getId().intValue())))
            .andExpect(jsonPath("$.[*].manhansu").value(hasItem(DEFAULT_MANHANSU)))
            .andExpect(jsonPath("$.[*].tennhansu").value(hasItem(DEFAULT_TENNHANSU)))
            .andExpect(jsonPath("$.[*].sdt").value(hasItem(DEFAULT_SDT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI)))
            .andExpect(jsonPath("$.[*].namsinh").value(hasItem(DEFAULT_NAMSINH)))
            .andExpect(jsonPath("$.[*].ngaysinh").value(hasItem(DEFAULT_NGAYSINH.toString())))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getNhansu() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get the nhansu
        restNhansuMockMvc.perform(get("/api/nhansus/{id}", nhansu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhansu.getId().intValue()))
            .andExpect(jsonPath("$.manhansu").value(DEFAULT_MANHANSU))
            .andExpect(jsonPath("$.tennhansu").value(DEFAULT_TENNHANSU))
            .andExpect(jsonPath("$.sdt").value(DEFAULT_SDT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI))
            .andExpect(jsonPath("$.namsinh").value(DEFAULT_NAMSINH))
            .andExpect(jsonPath("$.ngaysinh").value(DEFAULT_NGAYSINH.toString()))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getNhansusByIdFiltering() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        Long id = nhansu.getId();

        defaultNhansuShouldBeFound("id.equals=" + id);
        defaultNhansuShouldNotBeFound("id.notEquals=" + id);

        defaultNhansuShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNhansuShouldNotBeFound("id.greaterThan=" + id);

        defaultNhansuShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNhansuShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNhansusByManhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where manhansu equals to DEFAULT_MANHANSU
        defaultNhansuShouldBeFound("manhansu.equals=" + DEFAULT_MANHANSU);

        // Get all the nhansuList where manhansu equals to UPDATED_MANHANSU
        defaultNhansuShouldNotBeFound("manhansu.equals=" + UPDATED_MANHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByManhansuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where manhansu not equals to DEFAULT_MANHANSU
        defaultNhansuShouldNotBeFound("manhansu.notEquals=" + DEFAULT_MANHANSU);

        // Get all the nhansuList where manhansu not equals to UPDATED_MANHANSU
        defaultNhansuShouldBeFound("manhansu.notEquals=" + UPDATED_MANHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByManhansuIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where manhansu in DEFAULT_MANHANSU or UPDATED_MANHANSU
        defaultNhansuShouldBeFound("manhansu.in=" + DEFAULT_MANHANSU + "," + UPDATED_MANHANSU);

        // Get all the nhansuList where manhansu equals to UPDATED_MANHANSU
        defaultNhansuShouldNotBeFound("manhansu.in=" + UPDATED_MANHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByManhansuIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where manhansu is not null
        defaultNhansuShouldBeFound("manhansu.specified=true");

        // Get all the nhansuList where manhansu is null
        defaultNhansuShouldNotBeFound("manhansu.specified=false");
    }
                @Test
    @Transactional
    public void getAllNhansusByManhansuContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where manhansu contains DEFAULT_MANHANSU
        defaultNhansuShouldBeFound("manhansu.contains=" + DEFAULT_MANHANSU);

        // Get all the nhansuList where manhansu contains UPDATED_MANHANSU
        defaultNhansuShouldNotBeFound("manhansu.contains=" + UPDATED_MANHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByManhansuNotContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where manhansu does not contain DEFAULT_MANHANSU
        defaultNhansuShouldNotBeFound("manhansu.doesNotContain=" + DEFAULT_MANHANSU);

        // Get all the nhansuList where manhansu does not contain UPDATED_MANHANSU
        defaultNhansuShouldBeFound("manhansu.doesNotContain=" + UPDATED_MANHANSU);
    }


    @Test
    @Transactional
    public void getAllNhansusByTennhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where tennhansu equals to DEFAULT_TENNHANSU
        defaultNhansuShouldBeFound("tennhansu.equals=" + DEFAULT_TENNHANSU);

        // Get all the nhansuList where tennhansu equals to UPDATED_TENNHANSU
        defaultNhansuShouldNotBeFound("tennhansu.equals=" + UPDATED_TENNHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByTennhansuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where tennhansu not equals to DEFAULT_TENNHANSU
        defaultNhansuShouldNotBeFound("tennhansu.notEquals=" + DEFAULT_TENNHANSU);

        // Get all the nhansuList where tennhansu not equals to UPDATED_TENNHANSU
        defaultNhansuShouldBeFound("tennhansu.notEquals=" + UPDATED_TENNHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByTennhansuIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where tennhansu in DEFAULT_TENNHANSU or UPDATED_TENNHANSU
        defaultNhansuShouldBeFound("tennhansu.in=" + DEFAULT_TENNHANSU + "," + UPDATED_TENNHANSU);

        // Get all the nhansuList where tennhansu equals to UPDATED_TENNHANSU
        defaultNhansuShouldNotBeFound("tennhansu.in=" + UPDATED_TENNHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByTennhansuIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where tennhansu is not null
        defaultNhansuShouldBeFound("tennhansu.specified=true");

        // Get all the nhansuList where tennhansu is null
        defaultNhansuShouldNotBeFound("tennhansu.specified=false");
    }
                @Test
    @Transactional
    public void getAllNhansusByTennhansuContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where tennhansu contains DEFAULT_TENNHANSU
        defaultNhansuShouldBeFound("tennhansu.contains=" + DEFAULT_TENNHANSU);

        // Get all the nhansuList where tennhansu contains UPDATED_TENNHANSU
        defaultNhansuShouldNotBeFound("tennhansu.contains=" + UPDATED_TENNHANSU);
    }

    @Test
    @Transactional
    public void getAllNhansusByTennhansuNotContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where tennhansu does not contain DEFAULT_TENNHANSU
        defaultNhansuShouldNotBeFound("tennhansu.doesNotContain=" + DEFAULT_TENNHANSU);

        // Get all the nhansuList where tennhansu does not contain UPDATED_TENNHANSU
        defaultNhansuShouldBeFound("tennhansu.doesNotContain=" + UPDATED_TENNHANSU);
    }


    @Test
    @Transactional
    public void getAllNhansusBySdtIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt equals to DEFAULT_SDT
        defaultNhansuShouldBeFound("sdt.equals=" + DEFAULT_SDT);

        // Get all the nhansuList where sdt equals to UPDATED_SDT
        defaultNhansuShouldNotBeFound("sdt.equals=" + UPDATED_SDT);
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt not equals to DEFAULT_SDT
        defaultNhansuShouldNotBeFound("sdt.notEquals=" + DEFAULT_SDT);

        // Get all the nhansuList where sdt not equals to UPDATED_SDT
        defaultNhansuShouldBeFound("sdt.notEquals=" + UPDATED_SDT);
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt in DEFAULT_SDT or UPDATED_SDT
        defaultNhansuShouldBeFound("sdt.in=" + DEFAULT_SDT + "," + UPDATED_SDT);

        // Get all the nhansuList where sdt equals to UPDATED_SDT
        defaultNhansuShouldNotBeFound("sdt.in=" + UPDATED_SDT);
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt is not null
        defaultNhansuShouldBeFound("sdt.specified=true");

        // Get all the nhansuList where sdt is null
        defaultNhansuShouldNotBeFound("sdt.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt is greater than or equal to DEFAULT_SDT
        defaultNhansuShouldBeFound("sdt.greaterThanOrEqual=" + DEFAULT_SDT);

        // Get all the nhansuList where sdt is greater than or equal to UPDATED_SDT
        defaultNhansuShouldNotBeFound("sdt.greaterThanOrEqual=" + UPDATED_SDT);
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt is less than or equal to DEFAULT_SDT
        defaultNhansuShouldBeFound("sdt.lessThanOrEqual=" + DEFAULT_SDT);

        // Get all the nhansuList where sdt is less than or equal to SMALLER_SDT
        defaultNhansuShouldNotBeFound("sdt.lessThanOrEqual=" + SMALLER_SDT);
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsLessThanSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt is less than DEFAULT_SDT
        defaultNhansuShouldNotBeFound("sdt.lessThan=" + DEFAULT_SDT);

        // Get all the nhansuList where sdt is less than UPDATED_SDT
        defaultNhansuShouldBeFound("sdt.lessThan=" + UPDATED_SDT);
    }

    @Test
    @Transactional
    public void getAllNhansusBySdtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sdt is greater than DEFAULT_SDT
        defaultNhansuShouldNotBeFound("sdt.greaterThan=" + DEFAULT_SDT);

        // Get all the nhansuList where sdt is greater than SMALLER_SDT
        defaultNhansuShouldBeFound("sdt.greaterThan=" + SMALLER_SDT);
    }


    @Test
    @Transactional
    public void getAllNhansusByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where email equals to DEFAULT_EMAIL
        defaultNhansuShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the nhansuList where email equals to UPDATED_EMAIL
        defaultNhansuShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllNhansusByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where email not equals to DEFAULT_EMAIL
        defaultNhansuShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the nhansuList where email not equals to UPDATED_EMAIL
        defaultNhansuShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllNhansusByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultNhansuShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the nhansuList where email equals to UPDATED_EMAIL
        defaultNhansuShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllNhansusByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where email is not null
        defaultNhansuShouldBeFound("email.specified=true");

        // Get all the nhansuList where email is null
        defaultNhansuShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllNhansusByEmailContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where email contains DEFAULT_EMAIL
        defaultNhansuShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the nhansuList where email contains UPDATED_EMAIL
        defaultNhansuShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllNhansusByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where email does not contain DEFAULT_EMAIL
        defaultNhansuShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the nhansuList where email does not contain UPDATED_EMAIL
        defaultNhansuShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllNhansusByDiachiIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where diachi equals to DEFAULT_DIACHI
        defaultNhansuShouldBeFound("diachi.equals=" + DEFAULT_DIACHI);

        // Get all the nhansuList where diachi equals to UPDATED_DIACHI
        defaultNhansuShouldNotBeFound("diachi.equals=" + UPDATED_DIACHI);
    }

    @Test
    @Transactional
    public void getAllNhansusByDiachiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where diachi not equals to DEFAULT_DIACHI
        defaultNhansuShouldNotBeFound("diachi.notEquals=" + DEFAULT_DIACHI);

        // Get all the nhansuList where diachi not equals to UPDATED_DIACHI
        defaultNhansuShouldBeFound("diachi.notEquals=" + UPDATED_DIACHI);
    }

    @Test
    @Transactional
    public void getAllNhansusByDiachiIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where diachi in DEFAULT_DIACHI or UPDATED_DIACHI
        defaultNhansuShouldBeFound("diachi.in=" + DEFAULT_DIACHI + "," + UPDATED_DIACHI);

        // Get all the nhansuList where diachi equals to UPDATED_DIACHI
        defaultNhansuShouldNotBeFound("diachi.in=" + UPDATED_DIACHI);
    }

    @Test
    @Transactional
    public void getAllNhansusByDiachiIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where diachi is not null
        defaultNhansuShouldBeFound("diachi.specified=true");

        // Get all the nhansuList where diachi is null
        defaultNhansuShouldNotBeFound("diachi.specified=false");
    }
                @Test
    @Transactional
    public void getAllNhansusByDiachiContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where diachi contains DEFAULT_DIACHI
        defaultNhansuShouldBeFound("diachi.contains=" + DEFAULT_DIACHI);

        // Get all the nhansuList where diachi contains UPDATED_DIACHI
        defaultNhansuShouldNotBeFound("diachi.contains=" + UPDATED_DIACHI);
    }

    @Test
    @Transactional
    public void getAllNhansusByDiachiNotContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where diachi does not contain DEFAULT_DIACHI
        defaultNhansuShouldNotBeFound("diachi.doesNotContain=" + DEFAULT_DIACHI);

        // Get all the nhansuList where diachi does not contain UPDATED_DIACHI
        defaultNhansuShouldBeFound("diachi.doesNotContain=" + UPDATED_DIACHI);
    }


    @Test
    @Transactional
    public void getAllNhansusByNamsinhIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where namsinh equals to DEFAULT_NAMSINH
        defaultNhansuShouldBeFound("namsinh.equals=" + DEFAULT_NAMSINH);

        // Get all the nhansuList where namsinh equals to UPDATED_NAMSINH
        defaultNhansuShouldNotBeFound("namsinh.equals=" + UPDATED_NAMSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNamsinhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where namsinh not equals to DEFAULT_NAMSINH
        defaultNhansuShouldNotBeFound("namsinh.notEquals=" + DEFAULT_NAMSINH);

        // Get all the nhansuList where namsinh not equals to UPDATED_NAMSINH
        defaultNhansuShouldBeFound("namsinh.notEquals=" + UPDATED_NAMSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNamsinhIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where namsinh in DEFAULT_NAMSINH or UPDATED_NAMSINH
        defaultNhansuShouldBeFound("namsinh.in=" + DEFAULT_NAMSINH + "," + UPDATED_NAMSINH);

        // Get all the nhansuList where namsinh equals to UPDATED_NAMSINH
        defaultNhansuShouldNotBeFound("namsinh.in=" + UPDATED_NAMSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNamsinhIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where namsinh is not null
        defaultNhansuShouldBeFound("namsinh.specified=true");

        // Get all the nhansuList where namsinh is null
        defaultNhansuShouldNotBeFound("namsinh.specified=false");
    }
                @Test
    @Transactional
    public void getAllNhansusByNamsinhContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where namsinh contains DEFAULT_NAMSINH
        defaultNhansuShouldBeFound("namsinh.contains=" + DEFAULT_NAMSINH);

        // Get all the nhansuList where namsinh contains UPDATED_NAMSINH
        defaultNhansuShouldNotBeFound("namsinh.contains=" + UPDATED_NAMSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNamsinhNotContainsSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where namsinh does not contain DEFAULT_NAMSINH
        defaultNhansuShouldNotBeFound("namsinh.doesNotContain=" + DEFAULT_NAMSINH);

        // Get all the nhansuList where namsinh does not contain UPDATED_NAMSINH
        defaultNhansuShouldBeFound("namsinh.doesNotContain=" + UPDATED_NAMSINH);
    }


    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh equals to DEFAULT_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.equals=" + DEFAULT_NGAYSINH);

        // Get all the nhansuList where ngaysinh equals to UPDATED_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.equals=" + UPDATED_NGAYSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh not equals to DEFAULT_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.notEquals=" + DEFAULT_NGAYSINH);

        // Get all the nhansuList where ngaysinh not equals to UPDATED_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.notEquals=" + UPDATED_NGAYSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh in DEFAULT_NGAYSINH or UPDATED_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.in=" + DEFAULT_NGAYSINH + "," + UPDATED_NGAYSINH);

        // Get all the nhansuList where ngaysinh equals to UPDATED_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.in=" + UPDATED_NGAYSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh is not null
        defaultNhansuShouldBeFound("ngaysinh.specified=true");

        // Get all the nhansuList where ngaysinh is null
        defaultNhansuShouldNotBeFound("ngaysinh.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh is greater than or equal to DEFAULT_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.greaterThanOrEqual=" + DEFAULT_NGAYSINH);

        // Get all the nhansuList where ngaysinh is greater than or equal to UPDATED_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.greaterThanOrEqual=" + UPDATED_NGAYSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh is less than or equal to DEFAULT_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.lessThanOrEqual=" + DEFAULT_NGAYSINH);

        // Get all the nhansuList where ngaysinh is less than or equal to SMALLER_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.lessThanOrEqual=" + SMALLER_NGAYSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsLessThanSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh is less than DEFAULT_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.lessThan=" + DEFAULT_NGAYSINH);

        // Get all the nhansuList where ngaysinh is less than UPDATED_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.lessThan=" + UPDATED_NGAYSINH);
    }

    @Test
    @Transactional
    public void getAllNhansusByNgaysinhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where ngaysinh is greater than DEFAULT_NGAYSINH
        defaultNhansuShouldNotBeFound("ngaysinh.greaterThan=" + DEFAULT_NGAYSINH);

        // Get all the nhansuList where ngaysinh is greater than SMALLER_NGAYSINH
        defaultNhansuShouldBeFound("ngaysinh.greaterThan=" + SMALLER_NGAYSINH);
    }


    @Test
    @Transactional
    public void getAllNhansusBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung equals to DEFAULT_SUDUNG
        defaultNhansuShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the nhansuList where sudung equals to UPDATED_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung not equals to DEFAULT_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the nhansuList where sudung not equals to UPDATED_SUDUNG
        defaultNhansuShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultNhansuShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the nhansuList where sudung equals to UPDATED_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung is not null
        defaultNhansuShouldBeFound("sudung.specified=true");

        // Get all the nhansuList where sudung is null
        defaultNhansuShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultNhansuShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the nhansuList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultNhansuShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the nhansuList where sudung is less than or equal to SMALLER_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung is less than DEFAULT_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the nhansuList where sudung is less than UPDATED_SUDUNG
        defaultNhansuShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansusBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        // Get all the nhansuList where sudung is greater than DEFAULT_SUDUNG
        defaultNhansuShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the nhansuList where sudung is greater than SMALLER_SUDUNG
        defaultNhansuShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllNhansusByChunhiemIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);
        Chunhiem chunhiem = ChunhiemResourceIT.createEntity(em);
        em.persist(chunhiem);
        em.flush();
        nhansu.addChunhiem(chunhiem);
        nhansuRepository.saveAndFlush(nhansu);
        Long chunhiemId = chunhiem.getId();

        // Get all the nhansuList where chunhiem equals to chunhiemId
        defaultNhansuShouldBeFound("chunhiemId.equals=" + chunhiemId);

        // Get all the nhansuList where chunhiem equals to chunhiemId + 1
        defaultNhansuShouldNotBeFound("chunhiemId.equals=" + (chunhiemId + 1));
    }


    @Test
    @Transactional
    public void getAllNhansusByNhansuthamgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);
        Nhansuthamgia nhansuthamgia = NhansuthamgiaResourceIT.createEntity(em);
        em.persist(nhansuthamgia);
        em.flush();
        nhansu.addNhansuthamgia(nhansuthamgia);
        nhansuRepository.saveAndFlush(nhansu);
        Long nhansuthamgiaId = nhansuthamgia.getId();

        // Get all the nhansuList where nhansuthamgia equals to nhansuthamgiaId
        defaultNhansuShouldBeFound("nhansuthamgiaId.equals=" + nhansuthamgiaId);

        // Get all the nhansuList where nhansuthamgia equals to nhansuthamgiaId + 1
        defaultNhansuShouldNotBeFound("nhansuthamgiaId.equals=" + (nhansuthamgiaId + 1));
    }


    @Test
    @Transactional
    public void getAllNhansusByDonviIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);
        Donvi donvi = DonviResourceIT.createEntity(em);
        em.persist(donvi);
        em.flush();
        nhansu.setDonvi(donvi);
        nhansuRepository.saveAndFlush(nhansu);
        Long donviId = donvi.getId();

        // Get all the nhansuList where donvi equals to donviId
        defaultNhansuShouldBeFound("donviId.equals=" + donviId);

        // Get all the nhansuList where donvi equals to donviId + 1
        defaultNhansuShouldNotBeFound("donviId.equals=" + (donviId + 1));
    }


    @Test
    @Transactional
    public void getAllNhansusByChucdanhIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);
        Chucdanh chucdanh = ChucdanhResourceIT.createEntity(em);
        em.persist(chucdanh);
        em.flush();
        nhansu.setChucdanh(chucdanh);
        nhansuRepository.saveAndFlush(nhansu);
        Long chucdanhId = chucdanh.getId();

        // Get all the nhansuList where chucdanh equals to chucdanhId
        defaultNhansuShouldBeFound("chucdanhId.equals=" + chucdanhId);

        // Get all the nhansuList where chucdanh equals to chucdanhId + 1
        defaultNhansuShouldNotBeFound("chucdanhId.equals=" + (chucdanhId + 1));
    }


    @Test
    @Transactional
    public void getAllNhansusByHochamIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);
        Hocham hocham = HochamResourceIT.createEntity(em);
        em.persist(hocham);
        em.flush();
        nhansu.setHocham(hocham);
        nhansuRepository.saveAndFlush(nhansu);
        Long hochamId = hocham.getId();

        // Get all the nhansuList where hocham equals to hochamId
        defaultNhansuShouldBeFound("hochamId.equals=" + hochamId);

        // Get all the nhansuList where hocham equals to hochamId + 1
        defaultNhansuShouldNotBeFound("hochamId.equals=" + (hochamId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNhansuShouldBeFound(String filter) throws Exception {
        restNhansuMockMvc.perform(get("/api/nhansus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhansu.getId().intValue())))
            .andExpect(jsonPath("$.[*].manhansu").value(hasItem(DEFAULT_MANHANSU)))
            .andExpect(jsonPath("$.[*].tennhansu").value(hasItem(DEFAULT_TENNHANSU)))
            .andExpect(jsonPath("$.[*].sdt").value(hasItem(DEFAULT_SDT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI)))
            .andExpect(jsonPath("$.[*].namsinh").value(hasItem(DEFAULT_NAMSINH)))
            .andExpect(jsonPath("$.[*].ngaysinh").value(hasItem(DEFAULT_NGAYSINH.toString())))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restNhansuMockMvc.perform(get("/api/nhansus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNhansuShouldNotBeFound(String filter) throws Exception {
        restNhansuMockMvc.perform(get("/api/nhansus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhansuMockMvc.perform(get("/api/nhansus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhansu() throws Exception {
        // Get the nhansu
        restNhansuMockMvc.perform(get("/api/nhansus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhansu() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        int databaseSizeBeforeUpdate = nhansuRepository.findAll().size();

        // Update the nhansu
        Nhansu updatedNhansu = nhansuRepository.findById(nhansu.getId()).get();
        // Disconnect from session so that the updates on updatedNhansu are not directly saved in db
        em.detach(updatedNhansu);
        updatedNhansu
            .manhansu(UPDATED_MANHANSU)
            .tennhansu(UPDATED_TENNHANSU)
            .sdt(UPDATED_SDT)
            .email(UPDATED_EMAIL)
            .diachi(UPDATED_DIACHI)
            .namsinh(UPDATED_NAMSINH)
            .ngaysinh(UPDATED_NGAYSINH)
            .sudung(UPDATED_SUDUNG);
        NhansuDTO nhansuDTO = nhansuMapper.toDto(updatedNhansu);

        restNhansuMockMvc.perform(put("/api/nhansus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuDTO)))
            .andExpect(status().isOk());

        // Validate the Nhansu in the database
        List<Nhansu> nhansuList = nhansuRepository.findAll();
        assertThat(nhansuList).hasSize(databaseSizeBeforeUpdate);
        Nhansu testNhansu = nhansuList.get(nhansuList.size() - 1);
        assertThat(testNhansu.getManhansu()).isEqualTo(UPDATED_MANHANSU);
        assertThat(testNhansu.getTennhansu()).isEqualTo(UPDATED_TENNHANSU);
        assertThat(testNhansu.getSdt()).isEqualTo(UPDATED_SDT);
        assertThat(testNhansu.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhansu.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testNhansu.getNamsinh()).isEqualTo(UPDATED_NAMSINH);
        assertThat(testNhansu.getNgaysinh()).isEqualTo(UPDATED_NGAYSINH);
        assertThat(testNhansu.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingNhansu() throws Exception {
        int databaseSizeBeforeUpdate = nhansuRepository.findAll().size();

        // Create the Nhansu
        NhansuDTO nhansuDTO = nhansuMapper.toDto(nhansu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhansuMockMvc.perform(put("/api/nhansus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nhansu in the database
        List<Nhansu> nhansuList = nhansuRepository.findAll();
        assertThat(nhansuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhansu() throws Exception {
        // Initialize the database
        nhansuRepository.saveAndFlush(nhansu);

        int databaseSizeBeforeDelete = nhansuRepository.findAll().size();

        // Delete the nhansu
        restNhansuMockMvc.perform(delete("/api/nhansus/{id}", nhansu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nhansu> nhansuList = nhansuRepository.findAll();
        assertThat(nhansuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
