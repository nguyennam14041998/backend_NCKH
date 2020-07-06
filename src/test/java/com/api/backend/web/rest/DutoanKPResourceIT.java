package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.DutoanKP;
import com.api.backend.domain.DutoanKPCT;
import com.api.backend.domain.Detai;
import com.api.backend.repository.DutoanKPRepository;
import com.api.backend.service.DutoanKPService;
import com.api.backend.service.dto.DutoanKPDTO;
import com.api.backend.service.mapper.DutoanKPMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DutoanKPCriteria;
import com.api.backend.service.DutoanKPQueryService;

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
 * Integration tests for the {@link DutoanKPResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DutoanKPResourceIT {

    private static final String DEFAULT_MADUTOAN = "AAAAAAAAAA";
    private static final String UPDATED_MADUTOAN = "BBBBBBBBBB";

    private static final String DEFAULT_TENDUTOAN = "AAAAAAAAAA";
    private static final String UPDATED_TENDUTOAN = "BBBBBBBBBB";

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private DutoanKPRepository dutoanKPRepository;

    @Autowired
    private DutoanKPMapper dutoanKPMapper;

    @Autowired
    private DutoanKPService dutoanKPService;

    @Autowired
    private DutoanKPQueryService dutoanKPQueryService;

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

    private MockMvc restDutoanKPMockMvc;

    private DutoanKP dutoanKP;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DutoanKPResource dutoanKPResource = new DutoanKPResource(dutoanKPService, dutoanKPQueryService);
        this.restDutoanKPMockMvc = MockMvcBuilders.standaloneSetup(dutoanKPResource)
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
    public static DutoanKP createEntity(EntityManager em) {
        DutoanKP dutoanKP = new DutoanKP()
            .madutoan(DEFAULT_MADUTOAN)
            .tendutoan(DEFAULT_TENDUTOAN)
            .noidung(DEFAULT_NOIDUNG)
            .sudung(DEFAULT_SUDUNG);
        return dutoanKP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DutoanKP createUpdatedEntity(EntityManager em) {
        DutoanKP dutoanKP = new DutoanKP()
            .madutoan(UPDATED_MADUTOAN)
            .tendutoan(UPDATED_TENDUTOAN)
            .noidung(UPDATED_NOIDUNG)
            .sudung(UPDATED_SUDUNG);
        return dutoanKP;
    }

    @BeforeEach
    public void initTest() {
        dutoanKP = createEntity(em);
    }

    @Test
    @Transactional
    public void createDutoanKP() throws Exception {
        int databaseSizeBeforeCreate = dutoanKPRepository.findAll().size();

        // Create the DutoanKP
        DutoanKPDTO dutoanKPDTO = dutoanKPMapper.toDto(dutoanKP);
        restDutoanKPMockMvc.perform(post("/api/dutoan-kps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPDTO)))
            .andExpect(status().isCreated());

        // Validate the DutoanKP in the database
        List<DutoanKP> dutoanKPList = dutoanKPRepository.findAll();
        assertThat(dutoanKPList).hasSize(databaseSizeBeforeCreate + 1);
        DutoanKP testDutoanKP = dutoanKPList.get(dutoanKPList.size() - 1);
        assertThat(testDutoanKP.getMadutoan()).isEqualTo(DEFAULT_MADUTOAN);
        assertThat(testDutoanKP.getTendutoan()).isEqualTo(DEFAULT_TENDUTOAN);
        assertThat(testDutoanKP.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testDutoanKP.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createDutoanKPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dutoanKPRepository.findAll().size();

        // Create the DutoanKP with an existing ID
        dutoanKP.setId(1L);
        DutoanKPDTO dutoanKPDTO = dutoanKPMapper.toDto(dutoanKP);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDutoanKPMockMvc.perform(post("/api/dutoan-kps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DutoanKP in the database
        List<DutoanKP> dutoanKPList = dutoanKPRepository.findAll();
        assertThat(dutoanKPList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDutoanKPS() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dutoanKP.getId().intValue())))
            .andExpect(jsonPath("$.[*].madutoan").value(hasItem(DEFAULT_MADUTOAN)))
            .andExpect(jsonPath("$.[*].tendutoan").value(hasItem(DEFAULT_TENDUTOAN)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getDutoanKP() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get the dutoanKP
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps/{id}", dutoanKP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dutoanKP.getId().intValue()))
            .andExpect(jsonPath("$.madutoan").value(DEFAULT_MADUTOAN))
            .andExpect(jsonPath("$.tendutoan").value(DEFAULT_TENDUTOAN))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getDutoanKPSByIdFiltering() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        Long id = dutoanKP.getId();

        defaultDutoanKPShouldBeFound("id.equals=" + id);
        defaultDutoanKPShouldNotBeFound("id.notEquals=" + id);

        defaultDutoanKPShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDutoanKPShouldNotBeFound("id.greaterThan=" + id);

        defaultDutoanKPShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDutoanKPShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDutoanKPSByMadutoanIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where madutoan equals to DEFAULT_MADUTOAN
        defaultDutoanKPShouldBeFound("madutoan.equals=" + DEFAULT_MADUTOAN);

        // Get all the dutoanKPList where madutoan equals to UPDATED_MADUTOAN
        defaultDutoanKPShouldNotBeFound("madutoan.equals=" + UPDATED_MADUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByMadutoanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where madutoan not equals to DEFAULT_MADUTOAN
        defaultDutoanKPShouldNotBeFound("madutoan.notEquals=" + DEFAULT_MADUTOAN);

        // Get all the dutoanKPList where madutoan not equals to UPDATED_MADUTOAN
        defaultDutoanKPShouldBeFound("madutoan.notEquals=" + UPDATED_MADUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByMadutoanIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where madutoan in DEFAULT_MADUTOAN or UPDATED_MADUTOAN
        defaultDutoanKPShouldBeFound("madutoan.in=" + DEFAULT_MADUTOAN + "," + UPDATED_MADUTOAN);

        // Get all the dutoanKPList where madutoan equals to UPDATED_MADUTOAN
        defaultDutoanKPShouldNotBeFound("madutoan.in=" + UPDATED_MADUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByMadutoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where madutoan is not null
        defaultDutoanKPShouldBeFound("madutoan.specified=true");

        // Get all the dutoanKPList where madutoan is null
        defaultDutoanKPShouldNotBeFound("madutoan.specified=false");
    }
                @Test
    @Transactional
    public void getAllDutoanKPSByMadutoanContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where madutoan contains DEFAULT_MADUTOAN
        defaultDutoanKPShouldBeFound("madutoan.contains=" + DEFAULT_MADUTOAN);

        // Get all the dutoanKPList where madutoan contains UPDATED_MADUTOAN
        defaultDutoanKPShouldNotBeFound("madutoan.contains=" + UPDATED_MADUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByMadutoanNotContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where madutoan does not contain DEFAULT_MADUTOAN
        defaultDutoanKPShouldNotBeFound("madutoan.doesNotContain=" + DEFAULT_MADUTOAN);

        // Get all the dutoanKPList where madutoan does not contain UPDATED_MADUTOAN
        defaultDutoanKPShouldBeFound("madutoan.doesNotContain=" + UPDATED_MADUTOAN);
    }


    @Test
    @Transactional
    public void getAllDutoanKPSByTendutoanIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where tendutoan equals to DEFAULT_TENDUTOAN
        defaultDutoanKPShouldBeFound("tendutoan.equals=" + DEFAULT_TENDUTOAN);

        // Get all the dutoanKPList where tendutoan equals to UPDATED_TENDUTOAN
        defaultDutoanKPShouldNotBeFound("tendutoan.equals=" + UPDATED_TENDUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByTendutoanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where tendutoan not equals to DEFAULT_TENDUTOAN
        defaultDutoanKPShouldNotBeFound("tendutoan.notEquals=" + DEFAULT_TENDUTOAN);

        // Get all the dutoanKPList where tendutoan not equals to UPDATED_TENDUTOAN
        defaultDutoanKPShouldBeFound("tendutoan.notEquals=" + UPDATED_TENDUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByTendutoanIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where tendutoan in DEFAULT_TENDUTOAN or UPDATED_TENDUTOAN
        defaultDutoanKPShouldBeFound("tendutoan.in=" + DEFAULT_TENDUTOAN + "," + UPDATED_TENDUTOAN);

        // Get all the dutoanKPList where tendutoan equals to UPDATED_TENDUTOAN
        defaultDutoanKPShouldNotBeFound("tendutoan.in=" + UPDATED_TENDUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByTendutoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where tendutoan is not null
        defaultDutoanKPShouldBeFound("tendutoan.specified=true");

        // Get all the dutoanKPList where tendutoan is null
        defaultDutoanKPShouldNotBeFound("tendutoan.specified=false");
    }
                @Test
    @Transactional
    public void getAllDutoanKPSByTendutoanContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where tendutoan contains DEFAULT_TENDUTOAN
        defaultDutoanKPShouldBeFound("tendutoan.contains=" + DEFAULT_TENDUTOAN);

        // Get all the dutoanKPList where tendutoan contains UPDATED_TENDUTOAN
        defaultDutoanKPShouldNotBeFound("tendutoan.contains=" + UPDATED_TENDUTOAN);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByTendutoanNotContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where tendutoan does not contain DEFAULT_TENDUTOAN
        defaultDutoanKPShouldNotBeFound("tendutoan.doesNotContain=" + DEFAULT_TENDUTOAN);

        // Get all the dutoanKPList where tendutoan does not contain UPDATED_TENDUTOAN
        defaultDutoanKPShouldBeFound("tendutoan.doesNotContain=" + UPDATED_TENDUTOAN);
    }


    @Test
    @Transactional
    public void getAllDutoanKPSByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where noidung equals to DEFAULT_NOIDUNG
        defaultDutoanKPShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPList where noidung equals to UPDATED_NOIDUNG
        defaultDutoanKPShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where noidung not equals to DEFAULT_NOIDUNG
        defaultDutoanKPShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPList where noidung not equals to UPDATED_NOIDUNG
        defaultDutoanKPShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultDutoanKPShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the dutoanKPList where noidung equals to UPDATED_NOIDUNG
        defaultDutoanKPShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where noidung is not null
        defaultDutoanKPShouldBeFound("noidung.specified=true");

        // Get all the dutoanKPList where noidung is null
        defaultDutoanKPShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllDutoanKPSByNoidungContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where noidung contains DEFAULT_NOIDUNG
        defaultDutoanKPShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPList where noidung contains UPDATED_NOIDUNG
        defaultDutoanKPShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where noidung does not contain DEFAULT_NOIDUNG
        defaultDutoanKPShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the dutoanKPList where noidung does not contain UPDATED_NOIDUNG
        defaultDutoanKPShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung equals to DEFAULT_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPList where sudung equals to UPDATED_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung not equals to DEFAULT_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPList where sudung not equals to UPDATED_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the dutoanKPList where sudung equals to UPDATED_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung is not null
        defaultDutoanKPShouldBeFound("sudung.specified=true");

        // Get all the dutoanKPList where sudung is null
        defaultDutoanKPShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPList where sudung is less than or equal to SMALLER_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung is less than DEFAULT_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPList where sudung is less than UPDATED_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDutoanKPSBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        // Get all the dutoanKPList where sudung is greater than DEFAULT_SUDUNG
        defaultDutoanKPShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the dutoanKPList where sudung is greater than SMALLER_SUDUNG
        defaultDutoanKPShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllDutoanKPSByDutoanKPCTIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);
        DutoanKPCT dutoanKPCT = DutoanKPCTResourceIT.createEntity(em);
        em.persist(dutoanKPCT);
        em.flush();
        dutoanKP.addDutoanKPCT(dutoanKPCT);
        dutoanKPRepository.saveAndFlush(dutoanKP);
        Long dutoanKPCTId = dutoanKPCT.getId();

        // Get all the dutoanKPList where dutoanKPCT equals to dutoanKPCTId
        defaultDutoanKPShouldBeFound("dutoanKPCTId.equals=" + dutoanKPCTId);

        // Get all the dutoanKPList where dutoanKPCT equals to dutoanKPCTId + 1
        defaultDutoanKPShouldNotBeFound("dutoanKPCTId.equals=" + (dutoanKPCTId + 1));
    }


    @Test
    @Transactional
    public void getAllDutoanKPSByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        dutoanKP.setDetai(detai);
        dutoanKPRepository.saveAndFlush(dutoanKP);
        Long detaiId = detai.getId();

        // Get all the dutoanKPList where detai equals to detaiId
        defaultDutoanKPShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the dutoanKPList where detai equals to detaiId + 1
        defaultDutoanKPShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDutoanKPShouldBeFound(String filter) throws Exception {
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dutoanKP.getId().intValue())))
            .andExpect(jsonPath("$.[*].madutoan").value(hasItem(DEFAULT_MADUTOAN)))
            .andExpect(jsonPath("$.[*].tendutoan").value(hasItem(DEFAULT_TENDUTOAN)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDutoanKPShouldNotBeFound(String filter) throws Exception {
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDutoanKP() throws Exception {
        // Get the dutoanKP
        restDutoanKPMockMvc.perform(get("/api/dutoan-kps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDutoanKP() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        int databaseSizeBeforeUpdate = dutoanKPRepository.findAll().size();

        // Update the dutoanKP
        DutoanKP updatedDutoanKP = dutoanKPRepository.findById(dutoanKP.getId()).get();
        // Disconnect from session so that the updates on updatedDutoanKP are not directly saved in db
        em.detach(updatedDutoanKP);
        updatedDutoanKP
            .madutoan(UPDATED_MADUTOAN)
            .tendutoan(UPDATED_TENDUTOAN)
            .noidung(UPDATED_NOIDUNG)
            .sudung(UPDATED_SUDUNG);
        DutoanKPDTO dutoanKPDTO = dutoanKPMapper.toDto(updatedDutoanKP);

        restDutoanKPMockMvc.perform(put("/api/dutoan-kps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPDTO)))
            .andExpect(status().isOk());

        // Validate the DutoanKP in the database
        List<DutoanKP> dutoanKPList = dutoanKPRepository.findAll();
        assertThat(dutoanKPList).hasSize(databaseSizeBeforeUpdate);
        DutoanKP testDutoanKP = dutoanKPList.get(dutoanKPList.size() - 1);
        assertThat(testDutoanKP.getMadutoan()).isEqualTo(UPDATED_MADUTOAN);
        assertThat(testDutoanKP.getTendutoan()).isEqualTo(UPDATED_TENDUTOAN);
        assertThat(testDutoanKP.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testDutoanKP.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDutoanKP() throws Exception {
        int databaseSizeBeforeUpdate = dutoanKPRepository.findAll().size();

        // Create the DutoanKP
        DutoanKPDTO dutoanKPDTO = dutoanKPMapper.toDto(dutoanKP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDutoanKPMockMvc.perform(put("/api/dutoan-kps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dutoanKPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DutoanKP in the database
        List<DutoanKP> dutoanKPList = dutoanKPRepository.findAll();
        assertThat(dutoanKPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDutoanKP() throws Exception {
        // Initialize the database
        dutoanKPRepository.saveAndFlush(dutoanKP);

        int databaseSizeBeforeDelete = dutoanKPRepository.findAll().size();

        // Delete the dutoanKP
        restDutoanKPMockMvc.perform(delete("/api/dutoan-kps/{id}", dutoanKP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DutoanKP> dutoanKPList = dutoanKPRepository.findAll();
        assertThat(dutoanKPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
