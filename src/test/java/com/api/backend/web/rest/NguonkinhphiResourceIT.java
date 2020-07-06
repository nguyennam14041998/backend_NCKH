package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Nguonkinhphi;
import com.api.backend.domain.Detai;
import com.api.backend.repository.NguonkinhphiRepository;
import com.api.backend.service.NguonkinhphiService;
import com.api.backend.service.dto.NguonkinhphiDTO;
import com.api.backend.service.mapper.NguonkinhphiMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.NguonkinhphiCriteria;
import com.api.backend.service.NguonkinhphiQueryService;

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
 * Integration tests for the {@link NguonkinhphiResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class NguonkinhphiResourceIT {

    private static final String DEFAULT_MANGUONKINHPHI = "AAAAAAAAAA";
    private static final String UPDATED_MANGUONKINHPHI = "BBBBBBBBBB";

    private static final String DEFAULT_TENNGUONKINHPHI = "AAAAAAAAAA";
    private static final String UPDATED_TENNGUONKINHPHI = "BBBBBBBBBB";

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOTIENCAP = 1;
    private static final Integer UPDATED_SOTIENCAP = 2;
    private static final Integer SMALLER_SOTIENCAP = 1 - 1;

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private NguonkinhphiRepository nguonkinhphiRepository;

    @Autowired
    private NguonkinhphiMapper nguonkinhphiMapper;

    @Autowired
    private NguonkinhphiService nguonkinhphiService;

    @Autowired
    private NguonkinhphiQueryService nguonkinhphiQueryService;

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

    private MockMvc restNguonkinhphiMockMvc;

    private Nguonkinhphi nguonkinhphi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NguonkinhphiResource nguonkinhphiResource = new NguonkinhphiResource(nguonkinhphiService, nguonkinhphiQueryService);
        this.restNguonkinhphiMockMvc = MockMvcBuilders.standaloneSetup(nguonkinhphiResource)
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
    public static Nguonkinhphi createEntity(EntityManager em) {
        Nguonkinhphi nguonkinhphi = new Nguonkinhphi()
            .manguonkinhphi(DEFAULT_MANGUONKINHPHI)
            .tennguonkinhphi(DEFAULT_TENNGUONKINHPHI)
            .noidung(DEFAULT_NOIDUNG)
            .sotiencap(DEFAULT_SOTIENCAP)
            .sudung(DEFAULT_SUDUNG);
        return nguonkinhphi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nguonkinhphi createUpdatedEntity(EntityManager em) {
        Nguonkinhphi nguonkinhphi = new Nguonkinhphi()
            .manguonkinhphi(UPDATED_MANGUONKINHPHI)
            .tennguonkinhphi(UPDATED_TENNGUONKINHPHI)
            .noidung(UPDATED_NOIDUNG)
            .sotiencap(UPDATED_SOTIENCAP)
            .sudung(UPDATED_SUDUNG);
        return nguonkinhphi;
    }

    @BeforeEach
    public void initTest() {
        nguonkinhphi = createEntity(em);
    }

    @Test
    @Transactional
    public void createNguonkinhphi() throws Exception {
        int databaseSizeBeforeCreate = nguonkinhphiRepository.findAll().size();

        // Create the Nguonkinhphi
        NguonkinhphiDTO nguonkinhphiDTO = nguonkinhphiMapper.toDto(nguonkinhphi);
        restNguonkinhphiMockMvc.perform(post("/api/nguonkinhphis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nguonkinhphiDTO)))
            .andExpect(status().isCreated());

        // Validate the Nguonkinhphi in the database
        List<Nguonkinhphi> nguonkinhphiList = nguonkinhphiRepository.findAll();
        assertThat(nguonkinhphiList).hasSize(databaseSizeBeforeCreate + 1);
        Nguonkinhphi testNguonkinhphi = nguonkinhphiList.get(nguonkinhphiList.size() - 1);
        assertThat(testNguonkinhphi.getManguonkinhphi()).isEqualTo(DEFAULT_MANGUONKINHPHI);
        assertThat(testNguonkinhphi.getTennguonkinhphi()).isEqualTo(DEFAULT_TENNGUONKINHPHI);
        assertThat(testNguonkinhphi.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testNguonkinhphi.getSotiencap()).isEqualTo(DEFAULT_SOTIENCAP);
        assertThat(testNguonkinhphi.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createNguonkinhphiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nguonkinhphiRepository.findAll().size();

        // Create the Nguonkinhphi with an existing ID
        nguonkinhphi.setId(1L);
        NguonkinhphiDTO nguonkinhphiDTO = nguonkinhphiMapper.toDto(nguonkinhphi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNguonkinhphiMockMvc.perform(post("/api/nguonkinhphis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nguonkinhphiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nguonkinhphi in the database
        List<Nguonkinhphi> nguonkinhphiList = nguonkinhphiRepository.findAll();
        assertThat(nguonkinhphiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphis() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nguonkinhphi.getId().intValue())))
            .andExpect(jsonPath("$.[*].manguonkinhphi").value(hasItem(DEFAULT_MANGUONKINHPHI)))
            .andExpect(jsonPath("$.[*].tennguonkinhphi").value(hasItem(DEFAULT_TENNGUONKINHPHI)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sotiencap").value(hasItem(DEFAULT_SOTIENCAP)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getNguonkinhphi() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get the nguonkinhphi
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis/{id}", nguonkinhphi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nguonkinhphi.getId().intValue()))
            .andExpect(jsonPath("$.manguonkinhphi").value(DEFAULT_MANGUONKINHPHI))
            .andExpect(jsonPath("$.tennguonkinhphi").value(DEFAULT_TENNGUONKINHPHI))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.sotiencap").value(DEFAULT_SOTIENCAP))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getNguonkinhphisByIdFiltering() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        Long id = nguonkinhphi.getId();

        defaultNguonkinhphiShouldBeFound("id.equals=" + id);
        defaultNguonkinhphiShouldNotBeFound("id.notEquals=" + id);

        defaultNguonkinhphiShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNguonkinhphiShouldNotBeFound("id.greaterThan=" + id);

        defaultNguonkinhphiShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNguonkinhphiShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphisByManguonkinhphiIsEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where manguonkinhphi equals to DEFAULT_MANGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("manguonkinhphi.equals=" + DEFAULT_MANGUONKINHPHI);

        // Get all the nguonkinhphiList where manguonkinhphi equals to UPDATED_MANGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("manguonkinhphi.equals=" + UPDATED_MANGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByManguonkinhphiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where manguonkinhphi not equals to DEFAULT_MANGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("manguonkinhphi.notEquals=" + DEFAULT_MANGUONKINHPHI);

        // Get all the nguonkinhphiList where manguonkinhphi not equals to UPDATED_MANGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("manguonkinhphi.notEquals=" + UPDATED_MANGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByManguonkinhphiIsInShouldWork() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where manguonkinhphi in DEFAULT_MANGUONKINHPHI or UPDATED_MANGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("manguonkinhphi.in=" + DEFAULT_MANGUONKINHPHI + "," + UPDATED_MANGUONKINHPHI);

        // Get all the nguonkinhphiList where manguonkinhphi equals to UPDATED_MANGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("manguonkinhphi.in=" + UPDATED_MANGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByManguonkinhphiIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where manguonkinhphi is not null
        defaultNguonkinhphiShouldBeFound("manguonkinhphi.specified=true");

        // Get all the nguonkinhphiList where manguonkinhphi is null
        defaultNguonkinhphiShouldNotBeFound("manguonkinhphi.specified=false");
    }
                @Test
    @Transactional
    public void getAllNguonkinhphisByManguonkinhphiContainsSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where manguonkinhphi contains DEFAULT_MANGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("manguonkinhphi.contains=" + DEFAULT_MANGUONKINHPHI);

        // Get all the nguonkinhphiList where manguonkinhphi contains UPDATED_MANGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("manguonkinhphi.contains=" + UPDATED_MANGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByManguonkinhphiNotContainsSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where manguonkinhphi does not contain DEFAULT_MANGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("manguonkinhphi.doesNotContain=" + DEFAULT_MANGUONKINHPHI);

        // Get all the nguonkinhphiList where manguonkinhphi does not contain UPDATED_MANGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("manguonkinhphi.doesNotContain=" + UPDATED_MANGUONKINHPHI);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphisByTennguonkinhphiIsEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where tennguonkinhphi equals to DEFAULT_TENNGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("tennguonkinhphi.equals=" + DEFAULT_TENNGUONKINHPHI);

        // Get all the nguonkinhphiList where tennguonkinhphi equals to UPDATED_TENNGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("tennguonkinhphi.equals=" + UPDATED_TENNGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByTennguonkinhphiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where tennguonkinhphi not equals to DEFAULT_TENNGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("tennguonkinhphi.notEquals=" + DEFAULT_TENNGUONKINHPHI);

        // Get all the nguonkinhphiList where tennguonkinhphi not equals to UPDATED_TENNGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("tennguonkinhphi.notEquals=" + UPDATED_TENNGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByTennguonkinhphiIsInShouldWork() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where tennguonkinhphi in DEFAULT_TENNGUONKINHPHI or UPDATED_TENNGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("tennguonkinhphi.in=" + DEFAULT_TENNGUONKINHPHI + "," + UPDATED_TENNGUONKINHPHI);

        // Get all the nguonkinhphiList where tennguonkinhphi equals to UPDATED_TENNGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("tennguonkinhphi.in=" + UPDATED_TENNGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByTennguonkinhphiIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where tennguonkinhphi is not null
        defaultNguonkinhphiShouldBeFound("tennguonkinhphi.specified=true");

        // Get all the nguonkinhphiList where tennguonkinhphi is null
        defaultNguonkinhphiShouldNotBeFound("tennguonkinhphi.specified=false");
    }
                @Test
    @Transactional
    public void getAllNguonkinhphisByTennguonkinhphiContainsSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where tennguonkinhphi contains DEFAULT_TENNGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("tennguonkinhphi.contains=" + DEFAULT_TENNGUONKINHPHI);

        // Get all the nguonkinhphiList where tennguonkinhphi contains UPDATED_TENNGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("tennguonkinhphi.contains=" + UPDATED_TENNGUONKINHPHI);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByTennguonkinhphiNotContainsSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where tennguonkinhphi does not contain DEFAULT_TENNGUONKINHPHI
        defaultNguonkinhphiShouldNotBeFound("tennguonkinhphi.doesNotContain=" + DEFAULT_TENNGUONKINHPHI);

        // Get all the nguonkinhphiList where tennguonkinhphi does not contain UPDATED_TENNGUONKINHPHI
        defaultNguonkinhphiShouldBeFound("tennguonkinhphi.doesNotContain=" + UPDATED_TENNGUONKINHPHI);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphisByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where noidung equals to DEFAULT_NOIDUNG
        defaultNguonkinhphiShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the nguonkinhphiList where noidung equals to UPDATED_NOIDUNG
        defaultNguonkinhphiShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where noidung not equals to DEFAULT_NOIDUNG
        defaultNguonkinhphiShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the nguonkinhphiList where noidung not equals to UPDATED_NOIDUNG
        defaultNguonkinhphiShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultNguonkinhphiShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the nguonkinhphiList where noidung equals to UPDATED_NOIDUNG
        defaultNguonkinhphiShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where noidung is not null
        defaultNguonkinhphiShouldBeFound("noidung.specified=true");

        // Get all the nguonkinhphiList where noidung is null
        defaultNguonkinhphiShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllNguonkinhphisByNoidungContainsSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where noidung contains DEFAULT_NOIDUNG
        defaultNguonkinhphiShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the nguonkinhphiList where noidung contains UPDATED_NOIDUNG
        defaultNguonkinhphiShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where noidung does not contain DEFAULT_NOIDUNG
        defaultNguonkinhphiShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the nguonkinhphiList where noidung does not contain UPDATED_NOIDUNG
        defaultNguonkinhphiShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap equals to DEFAULT_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.equals=" + DEFAULT_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap equals to UPDATED_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.equals=" + UPDATED_SOTIENCAP);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap not equals to DEFAULT_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.notEquals=" + DEFAULT_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap not equals to UPDATED_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.notEquals=" + UPDATED_SOTIENCAP);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsInShouldWork() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap in DEFAULT_SOTIENCAP or UPDATED_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.in=" + DEFAULT_SOTIENCAP + "," + UPDATED_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap equals to UPDATED_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.in=" + UPDATED_SOTIENCAP);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap is not null
        defaultNguonkinhphiShouldBeFound("sotiencap.specified=true");

        // Get all the nguonkinhphiList where sotiencap is null
        defaultNguonkinhphiShouldNotBeFound("sotiencap.specified=false");
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap is greater than or equal to DEFAULT_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.greaterThanOrEqual=" + DEFAULT_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap is greater than or equal to UPDATED_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.greaterThanOrEqual=" + UPDATED_SOTIENCAP);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap is less than or equal to DEFAULT_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.lessThanOrEqual=" + DEFAULT_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap is less than or equal to SMALLER_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.lessThanOrEqual=" + SMALLER_SOTIENCAP);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsLessThanSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap is less than DEFAULT_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.lessThan=" + DEFAULT_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap is less than UPDATED_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.lessThan=" + UPDATED_SOTIENCAP);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySotiencapIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sotiencap is greater than DEFAULT_SOTIENCAP
        defaultNguonkinhphiShouldNotBeFound("sotiencap.greaterThan=" + DEFAULT_SOTIENCAP);

        // Get all the nguonkinhphiList where sotiencap is greater than SMALLER_SOTIENCAP
        defaultNguonkinhphiShouldBeFound("sotiencap.greaterThan=" + SMALLER_SOTIENCAP);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung equals to DEFAULT_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the nguonkinhphiList where sudung equals to UPDATED_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung not equals to DEFAULT_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the nguonkinhphiList where sudung not equals to UPDATED_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the nguonkinhphiList where sudung equals to UPDATED_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung is not null
        defaultNguonkinhphiShouldBeFound("sudung.specified=true");

        // Get all the nguonkinhphiList where sudung is null
        defaultNguonkinhphiShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the nguonkinhphiList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the nguonkinhphiList where sudung is less than or equal to SMALLER_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung is less than DEFAULT_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the nguonkinhphiList where sudung is less than UPDATED_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNguonkinhphisBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        // Get all the nguonkinhphiList where sudung is greater than DEFAULT_SUDUNG
        defaultNguonkinhphiShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the nguonkinhphiList where sudung is greater than SMALLER_SUDUNG
        defaultNguonkinhphiShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllNguonkinhphisByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        nguonkinhphi.setDetai(detai);
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);
        Long detaiId = detai.getId();

        // Get all the nguonkinhphiList where detai equals to detaiId
        defaultNguonkinhphiShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the nguonkinhphiList where detai equals to detaiId + 1
        defaultNguonkinhphiShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNguonkinhphiShouldBeFound(String filter) throws Exception {
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nguonkinhphi.getId().intValue())))
            .andExpect(jsonPath("$.[*].manguonkinhphi").value(hasItem(DEFAULT_MANGUONKINHPHI)))
            .andExpect(jsonPath("$.[*].tennguonkinhphi").value(hasItem(DEFAULT_TENNGUONKINHPHI)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sotiencap").value(hasItem(DEFAULT_SOTIENCAP)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNguonkinhphiShouldNotBeFound(String filter) throws Exception {
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNguonkinhphi() throws Exception {
        // Get the nguonkinhphi
        restNguonkinhphiMockMvc.perform(get("/api/nguonkinhphis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNguonkinhphi() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        int databaseSizeBeforeUpdate = nguonkinhphiRepository.findAll().size();

        // Update the nguonkinhphi
        Nguonkinhphi updatedNguonkinhphi = nguonkinhphiRepository.findById(nguonkinhphi.getId()).get();
        // Disconnect from session so that the updates on updatedNguonkinhphi are not directly saved in db
        em.detach(updatedNguonkinhphi);
        updatedNguonkinhphi
            .manguonkinhphi(UPDATED_MANGUONKINHPHI)
            .tennguonkinhphi(UPDATED_TENNGUONKINHPHI)
            .noidung(UPDATED_NOIDUNG)
            .sotiencap(UPDATED_SOTIENCAP)
            .sudung(UPDATED_SUDUNG);
        NguonkinhphiDTO nguonkinhphiDTO = nguonkinhphiMapper.toDto(updatedNguonkinhphi);

        restNguonkinhphiMockMvc.perform(put("/api/nguonkinhphis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nguonkinhphiDTO)))
            .andExpect(status().isOk());

        // Validate the Nguonkinhphi in the database
        List<Nguonkinhphi> nguonkinhphiList = nguonkinhphiRepository.findAll();
        assertThat(nguonkinhphiList).hasSize(databaseSizeBeforeUpdate);
        Nguonkinhphi testNguonkinhphi = nguonkinhphiList.get(nguonkinhphiList.size() - 1);
        assertThat(testNguonkinhphi.getManguonkinhphi()).isEqualTo(UPDATED_MANGUONKINHPHI);
        assertThat(testNguonkinhphi.getTennguonkinhphi()).isEqualTo(UPDATED_TENNGUONKINHPHI);
        assertThat(testNguonkinhphi.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testNguonkinhphi.getSotiencap()).isEqualTo(UPDATED_SOTIENCAP);
        assertThat(testNguonkinhphi.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingNguonkinhphi() throws Exception {
        int databaseSizeBeforeUpdate = nguonkinhphiRepository.findAll().size();

        // Create the Nguonkinhphi
        NguonkinhphiDTO nguonkinhphiDTO = nguonkinhphiMapper.toDto(nguonkinhphi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNguonkinhphiMockMvc.perform(put("/api/nguonkinhphis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nguonkinhphiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nguonkinhphi in the database
        List<Nguonkinhphi> nguonkinhphiList = nguonkinhphiRepository.findAll();
        assertThat(nguonkinhphiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNguonkinhphi() throws Exception {
        // Initialize the database
        nguonkinhphiRepository.saveAndFlush(nguonkinhphi);

        int databaseSizeBeforeDelete = nguonkinhphiRepository.findAll().size();

        // Delete the nguonkinhphi
        restNguonkinhphiMockMvc.perform(delete("/api/nguonkinhphis/{id}", nguonkinhphi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nguonkinhphi> nguonkinhphiList = nguonkinhphiRepository.findAll();
        assertThat(nguonkinhphiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
