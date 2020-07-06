package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Coquanphoihop;
import com.api.backend.domain.Coquanphoihopthamgia;
import com.api.backend.repository.CoquanphoihopRepository;
import com.api.backend.service.CoquanphoihopService;
import com.api.backend.service.dto.CoquanphoihopDTO;
import com.api.backend.service.mapper.CoquanphoihopMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.CoquanphoihopCriteria;
import com.api.backend.service.CoquanphoihopQueryService;

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
 * Integration tests for the {@link CoquanphoihopResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class CoquanphoihopResourceIT {

    private static final String DEFAULT_MACOQUAN = "AAAAAAAAAA";
    private static final String UPDATED_MACOQUAN = "BBBBBBBBBB";

    private static final String DEFAULT_TENCOQUAN = "AAAAAAAAAA";
    private static final String UPDATED_TENCOQUAN = "BBBBBBBBBB";

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final String DEFAULT_TENDAIDIEN = "AAAAAAAAAA";
    private static final String UPDATED_TENDAIDIEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private CoquanphoihopRepository coquanphoihopRepository;

    @Autowired
    private CoquanphoihopMapper coquanphoihopMapper;

    @Autowired
    private CoquanphoihopService coquanphoihopService;

    @Autowired
    private CoquanphoihopQueryService coquanphoihopQueryService;

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

    private MockMvc restCoquanphoihopMockMvc;

    private Coquanphoihop coquanphoihop;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoquanphoihopResource coquanphoihopResource = new CoquanphoihopResource(coquanphoihopService, coquanphoihopQueryService);
        this.restCoquanphoihopMockMvc = MockMvcBuilders.standaloneSetup(coquanphoihopResource)
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
    public static Coquanphoihop createEntity(EntityManager em) {
        Coquanphoihop coquanphoihop = new Coquanphoihop()
            .macoquan(DEFAULT_MACOQUAN)
            .tencoquan(DEFAULT_TENCOQUAN)
            .noidung(DEFAULT_NOIDUNG)
            .tendaidien(DEFAULT_TENDAIDIEN)
            .sudung(DEFAULT_SUDUNG);
        return coquanphoihop;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coquanphoihop createUpdatedEntity(EntityManager em) {
        Coquanphoihop coquanphoihop = new Coquanphoihop()
            .macoquan(UPDATED_MACOQUAN)
            .tencoquan(UPDATED_TENCOQUAN)
            .noidung(UPDATED_NOIDUNG)
            .tendaidien(UPDATED_TENDAIDIEN)
            .sudung(UPDATED_SUDUNG);
        return coquanphoihop;
    }

    @BeforeEach
    public void initTest() {
        coquanphoihop = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoquanphoihop() throws Exception {
        int databaseSizeBeforeCreate = coquanphoihopRepository.findAll().size();

        // Create the Coquanphoihop
        CoquanphoihopDTO coquanphoihopDTO = coquanphoihopMapper.toDto(coquanphoihop);
        restCoquanphoihopMockMvc.perform(post("/api/coquanphoihops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopDTO)))
            .andExpect(status().isCreated());

        // Validate the Coquanphoihop in the database
        List<Coquanphoihop> coquanphoihopList = coquanphoihopRepository.findAll();
        assertThat(coquanphoihopList).hasSize(databaseSizeBeforeCreate + 1);
        Coquanphoihop testCoquanphoihop = coquanphoihopList.get(coquanphoihopList.size() - 1);
        assertThat(testCoquanphoihop.getMacoquan()).isEqualTo(DEFAULT_MACOQUAN);
        assertThat(testCoquanphoihop.getTencoquan()).isEqualTo(DEFAULT_TENCOQUAN);
        assertThat(testCoquanphoihop.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testCoquanphoihop.getTendaidien()).isEqualTo(DEFAULT_TENDAIDIEN);
        assertThat(testCoquanphoihop.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createCoquanphoihopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coquanphoihopRepository.findAll().size();

        // Create the Coquanphoihop with an existing ID
        coquanphoihop.setId(1L);
        CoquanphoihopDTO coquanphoihopDTO = coquanphoihopMapper.toDto(coquanphoihop);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoquanphoihopMockMvc.perform(post("/api/coquanphoihops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coquanphoihop in the database
        List<Coquanphoihop> coquanphoihopList = coquanphoihopRepository.findAll();
        assertThat(coquanphoihopList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihops() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coquanphoihop.getId().intValue())))
            .andExpect(jsonPath("$.[*].macoquan").value(hasItem(DEFAULT_MACOQUAN)))
            .andExpect(jsonPath("$.[*].tencoquan").value(hasItem(DEFAULT_TENCOQUAN)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].tendaidien").value(hasItem(DEFAULT_TENDAIDIEN)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getCoquanphoihop() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get the coquanphoihop
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops/{id}", coquanphoihop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coquanphoihop.getId().intValue()))
            .andExpect(jsonPath("$.macoquan").value(DEFAULT_MACOQUAN))
            .andExpect(jsonPath("$.tencoquan").value(DEFAULT_TENCOQUAN))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.tendaidien").value(DEFAULT_TENDAIDIEN))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getCoquanphoihopsByIdFiltering() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        Long id = coquanphoihop.getId();

        defaultCoquanphoihopShouldBeFound("id.equals=" + id);
        defaultCoquanphoihopShouldNotBeFound("id.notEquals=" + id);

        defaultCoquanphoihopShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoquanphoihopShouldNotBeFound("id.greaterThan=" + id);

        defaultCoquanphoihopShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoquanphoihopShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopsByMacoquanIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where macoquan equals to DEFAULT_MACOQUAN
        defaultCoquanphoihopShouldBeFound("macoquan.equals=" + DEFAULT_MACOQUAN);

        // Get all the coquanphoihopList where macoquan equals to UPDATED_MACOQUAN
        defaultCoquanphoihopShouldNotBeFound("macoquan.equals=" + UPDATED_MACOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByMacoquanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where macoquan not equals to DEFAULT_MACOQUAN
        defaultCoquanphoihopShouldNotBeFound("macoquan.notEquals=" + DEFAULT_MACOQUAN);

        // Get all the coquanphoihopList where macoquan not equals to UPDATED_MACOQUAN
        defaultCoquanphoihopShouldBeFound("macoquan.notEquals=" + UPDATED_MACOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByMacoquanIsInShouldWork() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where macoquan in DEFAULT_MACOQUAN or UPDATED_MACOQUAN
        defaultCoquanphoihopShouldBeFound("macoquan.in=" + DEFAULT_MACOQUAN + "," + UPDATED_MACOQUAN);

        // Get all the coquanphoihopList where macoquan equals to UPDATED_MACOQUAN
        defaultCoquanphoihopShouldNotBeFound("macoquan.in=" + UPDATED_MACOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByMacoquanIsNullOrNotNull() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where macoquan is not null
        defaultCoquanphoihopShouldBeFound("macoquan.specified=true");

        // Get all the coquanphoihopList where macoquan is null
        defaultCoquanphoihopShouldNotBeFound("macoquan.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoquanphoihopsByMacoquanContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where macoquan contains DEFAULT_MACOQUAN
        defaultCoquanphoihopShouldBeFound("macoquan.contains=" + DEFAULT_MACOQUAN);

        // Get all the coquanphoihopList where macoquan contains UPDATED_MACOQUAN
        defaultCoquanphoihopShouldNotBeFound("macoquan.contains=" + UPDATED_MACOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByMacoquanNotContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where macoquan does not contain DEFAULT_MACOQUAN
        defaultCoquanphoihopShouldNotBeFound("macoquan.doesNotContain=" + DEFAULT_MACOQUAN);

        // Get all the coquanphoihopList where macoquan does not contain UPDATED_MACOQUAN
        defaultCoquanphoihopShouldBeFound("macoquan.doesNotContain=" + UPDATED_MACOQUAN);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopsByTencoquanIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tencoquan equals to DEFAULT_TENCOQUAN
        defaultCoquanphoihopShouldBeFound("tencoquan.equals=" + DEFAULT_TENCOQUAN);

        // Get all the coquanphoihopList where tencoquan equals to UPDATED_TENCOQUAN
        defaultCoquanphoihopShouldNotBeFound("tencoquan.equals=" + UPDATED_TENCOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTencoquanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tencoquan not equals to DEFAULT_TENCOQUAN
        defaultCoquanphoihopShouldNotBeFound("tencoquan.notEquals=" + DEFAULT_TENCOQUAN);

        // Get all the coquanphoihopList where tencoquan not equals to UPDATED_TENCOQUAN
        defaultCoquanphoihopShouldBeFound("tencoquan.notEquals=" + UPDATED_TENCOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTencoquanIsInShouldWork() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tencoquan in DEFAULT_TENCOQUAN or UPDATED_TENCOQUAN
        defaultCoquanphoihopShouldBeFound("tencoquan.in=" + DEFAULT_TENCOQUAN + "," + UPDATED_TENCOQUAN);

        // Get all the coquanphoihopList where tencoquan equals to UPDATED_TENCOQUAN
        defaultCoquanphoihopShouldNotBeFound("tencoquan.in=" + UPDATED_TENCOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTencoquanIsNullOrNotNull() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tencoquan is not null
        defaultCoquanphoihopShouldBeFound("tencoquan.specified=true");

        // Get all the coquanphoihopList where tencoquan is null
        defaultCoquanphoihopShouldNotBeFound("tencoquan.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoquanphoihopsByTencoquanContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tencoquan contains DEFAULT_TENCOQUAN
        defaultCoquanphoihopShouldBeFound("tencoquan.contains=" + DEFAULT_TENCOQUAN);

        // Get all the coquanphoihopList where tencoquan contains UPDATED_TENCOQUAN
        defaultCoquanphoihopShouldNotBeFound("tencoquan.contains=" + UPDATED_TENCOQUAN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTencoquanNotContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tencoquan does not contain DEFAULT_TENCOQUAN
        defaultCoquanphoihopShouldNotBeFound("tencoquan.doesNotContain=" + DEFAULT_TENCOQUAN);

        // Get all the coquanphoihopList where tencoquan does not contain UPDATED_TENCOQUAN
        defaultCoquanphoihopShouldBeFound("tencoquan.doesNotContain=" + UPDATED_TENCOQUAN);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopsByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where noidung equals to DEFAULT_NOIDUNG
        defaultCoquanphoihopShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the coquanphoihopList where noidung equals to UPDATED_NOIDUNG
        defaultCoquanphoihopShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where noidung not equals to DEFAULT_NOIDUNG
        defaultCoquanphoihopShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the coquanphoihopList where noidung not equals to UPDATED_NOIDUNG
        defaultCoquanphoihopShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultCoquanphoihopShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the coquanphoihopList where noidung equals to UPDATED_NOIDUNG
        defaultCoquanphoihopShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where noidung is not null
        defaultCoquanphoihopShouldBeFound("noidung.specified=true");

        // Get all the coquanphoihopList where noidung is null
        defaultCoquanphoihopShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoquanphoihopsByNoidungContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where noidung contains DEFAULT_NOIDUNG
        defaultCoquanphoihopShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the coquanphoihopList where noidung contains UPDATED_NOIDUNG
        defaultCoquanphoihopShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where noidung does not contain DEFAULT_NOIDUNG
        defaultCoquanphoihopShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the coquanphoihopList where noidung does not contain UPDATED_NOIDUNG
        defaultCoquanphoihopShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopsByTendaidienIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tendaidien equals to DEFAULT_TENDAIDIEN
        defaultCoquanphoihopShouldBeFound("tendaidien.equals=" + DEFAULT_TENDAIDIEN);

        // Get all the coquanphoihopList where tendaidien equals to UPDATED_TENDAIDIEN
        defaultCoquanphoihopShouldNotBeFound("tendaidien.equals=" + UPDATED_TENDAIDIEN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTendaidienIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tendaidien not equals to DEFAULT_TENDAIDIEN
        defaultCoquanphoihopShouldNotBeFound("tendaidien.notEquals=" + DEFAULT_TENDAIDIEN);

        // Get all the coquanphoihopList where tendaidien not equals to UPDATED_TENDAIDIEN
        defaultCoquanphoihopShouldBeFound("tendaidien.notEquals=" + UPDATED_TENDAIDIEN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTendaidienIsInShouldWork() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tendaidien in DEFAULT_TENDAIDIEN or UPDATED_TENDAIDIEN
        defaultCoquanphoihopShouldBeFound("tendaidien.in=" + DEFAULT_TENDAIDIEN + "," + UPDATED_TENDAIDIEN);

        // Get all the coquanphoihopList where tendaidien equals to UPDATED_TENDAIDIEN
        defaultCoquanphoihopShouldNotBeFound("tendaidien.in=" + UPDATED_TENDAIDIEN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTendaidienIsNullOrNotNull() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tendaidien is not null
        defaultCoquanphoihopShouldBeFound("tendaidien.specified=true");

        // Get all the coquanphoihopList where tendaidien is null
        defaultCoquanphoihopShouldNotBeFound("tendaidien.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoquanphoihopsByTendaidienContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tendaidien contains DEFAULT_TENDAIDIEN
        defaultCoquanphoihopShouldBeFound("tendaidien.contains=" + DEFAULT_TENDAIDIEN);

        // Get all the coquanphoihopList where tendaidien contains UPDATED_TENDAIDIEN
        defaultCoquanphoihopShouldNotBeFound("tendaidien.contains=" + UPDATED_TENDAIDIEN);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsByTendaidienNotContainsSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where tendaidien does not contain DEFAULT_TENDAIDIEN
        defaultCoquanphoihopShouldNotBeFound("tendaidien.doesNotContain=" + DEFAULT_TENDAIDIEN);

        // Get all the coquanphoihopList where tendaidien does not contain UPDATED_TENDAIDIEN
        defaultCoquanphoihopShouldBeFound("tendaidien.doesNotContain=" + UPDATED_TENDAIDIEN);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung equals to DEFAULT_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopList where sudung equals to UPDATED_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung not equals to DEFAULT_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopList where sudung not equals to UPDATED_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the coquanphoihopList where sudung equals to UPDATED_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung is not null
        defaultCoquanphoihopShouldBeFound("sudung.specified=true");

        // Get all the coquanphoihopList where sudung is null
        defaultCoquanphoihopShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopList where sudung is less than or equal to SMALLER_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung is less than DEFAULT_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopList where sudung is less than UPDATED_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopsBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        // Get all the coquanphoihopList where sudung is greater than DEFAULT_SUDUNG
        defaultCoquanphoihopShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopList where sudung is greater than SMALLER_SUDUNG
        defaultCoquanphoihopShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopsByCoquanphoihopthamgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);
        Coquanphoihopthamgia coquanphoihopthamgia = CoquanphoihopthamgiaResourceIT.createEntity(em);
        em.persist(coquanphoihopthamgia);
        em.flush();
        coquanphoihop.addCoquanphoihopthamgia(coquanphoihopthamgia);
        coquanphoihopRepository.saveAndFlush(coquanphoihop);
        Long coquanphoihopthamgiaId = coquanphoihopthamgia.getId();

        // Get all the coquanphoihopList where coquanphoihopthamgia equals to coquanphoihopthamgiaId
        defaultCoquanphoihopShouldBeFound("coquanphoihopthamgiaId.equals=" + coquanphoihopthamgiaId);

        // Get all the coquanphoihopList where coquanphoihopthamgia equals to coquanphoihopthamgiaId + 1
        defaultCoquanphoihopShouldNotBeFound("coquanphoihopthamgiaId.equals=" + (coquanphoihopthamgiaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoquanphoihopShouldBeFound(String filter) throws Exception {
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coquanphoihop.getId().intValue())))
            .andExpect(jsonPath("$.[*].macoquan").value(hasItem(DEFAULT_MACOQUAN)))
            .andExpect(jsonPath("$.[*].tencoquan").value(hasItem(DEFAULT_TENCOQUAN)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].tendaidien").value(hasItem(DEFAULT_TENDAIDIEN)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoquanphoihopShouldNotBeFound(String filter) throws Exception {
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCoquanphoihop() throws Exception {
        // Get the coquanphoihop
        restCoquanphoihopMockMvc.perform(get("/api/coquanphoihops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoquanphoihop() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        int databaseSizeBeforeUpdate = coquanphoihopRepository.findAll().size();

        // Update the coquanphoihop
        Coquanphoihop updatedCoquanphoihop = coquanphoihopRepository.findById(coquanphoihop.getId()).get();
        // Disconnect from session so that the updates on updatedCoquanphoihop are not directly saved in db
        em.detach(updatedCoquanphoihop);
        updatedCoquanphoihop
            .macoquan(UPDATED_MACOQUAN)
            .tencoquan(UPDATED_TENCOQUAN)
            .noidung(UPDATED_NOIDUNG)
            .tendaidien(UPDATED_TENDAIDIEN)
            .sudung(UPDATED_SUDUNG);
        CoquanphoihopDTO coquanphoihopDTO = coquanphoihopMapper.toDto(updatedCoquanphoihop);

        restCoquanphoihopMockMvc.perform(put("/api/coquanphoihops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopDTO)))
            .andExpect(status().isOk());

        // Validate the Coquanphoihop in the database
        List<Coquanphoihop> coquanphoihopList = coquanphoihopRepository.findAll();
        assertThat(coquanphoihopList).hasSize(databaseSizeBeforeUpdate);
        Coquanphoihop testCoquanphoihop = coquanphoihopList.get(coquanphoihopList.size() - 1);
        assertThat(testCoquanphoihop.getMacoquan()).isEqualTo(UPDATED_MACOQUAN);
        assertThat(testCoquanphoihop.getTencoquan()).isEqualTo(UPDATED_TENCOQUAN);
        assertThat(testCoquanphoihop.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testCoquanphoihop.getTendaidien()).isEqualTo(UPDATED_TENDAIDIEN);
        assertThat(testCoquanphoihop.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingCoquanphoihop() throws Exception {
        int databaseSizeBeforeUpdate = coquanphoihopRepository.findAll().size();

        // Create the Coquanphoihop
        CoquanphoihopDTO coquanphoihopDTO = coquanphoihopMapper.toDto(coquanphoihop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoquanphoihopMockMvc.perform(put("/api/coquanphoihops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coquanphoihop in the database
        List<Coquanphoihop> coquanphoihopList = coquanphoihopRepository.findAll();
        assertThat(coquanphoihopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoquanphoihop() throws Exception {
        // Initialize the database
        coquanphoihopRepository.saveAndFlush(coquanphoihop);

        int databaseSizeBeforeDelete = coquanphoihopRepository.findAll().size();

        // Delete the coquanphoihop
        restCoquanphoihopMockMvc.perform(delete("/api/coquanphoihops/{id}", coquanphoihop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coquanphoihop> coquanphoihopList = coquanphoihopRepository.findAll();
        assertThat(coquanphoihopList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
