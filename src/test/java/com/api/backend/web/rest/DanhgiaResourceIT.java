package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Danhgia;
import com.api.backend.domain.DanhgiaCT;
import com.api.backend.domain.Detai;
import com.api.backend.repository.DanhgiaRepository;
import com.api.backend.service.DanhgiaService;
import com.api.backend.service.dto.DanhgiaDTO;
import com.api.backend.service.mapper.DanhgiaMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DanhgiaCriteria;
import com.api.backend.service.DanhgiaQueryService;

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
 * Integration tests for the {@link DanhgiaResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DanhgiaResourceIT {

    private static final String DEFAULT_MA = "AAAAAAAAAA";
    private static final String UPDATED_MA = "BBBBBBBBBB";

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIEM = 1;
    private static final Integer UPDATED_DIEM = 2;
    private static final Integer SMALLER_DIEM = 1 - 1;

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private DanhgiaRepository danhgiaRepository;

    @Autowired
    private DanhgiaMapper danhgiaMapper;

    @Autowired
    private DanhgiaService danhgiaService;

    @Autowired
    private DanhgiaQueryService danhgiaQueryService;

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

    private MockMvc restDanhgiaMockMvc;

    private Danhgia danhgia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DanhgiaResource danhgiaResource = new DanhgiaResource(danhgiaService, danhgiaQueryService);
        this.restDanhgiaMockMvc = MockMvcBuilders.standaloneSetup(danhgiaResource)
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
    public static Danhgia createEntity(EntityManager em) {
        Danhgia danhgia = new Danhgia()
            .ma(DEFAULT_MA)
            .ten(DEFAULT_TEN)
            .diem(DEFAULT_DIEM)
            .noidung(DEFAULT_NOIDUNG)
            .sudung(DEFAULT_SUDUNG);
        return danhgia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Danhgia createUpdatedEntity(EntityManager em) {
        Danhgia danhgia = new Danhgia()
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN)
            .diem(UPDATED_DIEM)
            .noidung(UPDATED_NOIDUNG)
            .sudung(UPDATED_SUDUNG);
        return danhgia;
    }

    @BeforeEach
    public void initTest() {
        danhgia = createEntity(em);
    }

    @Test
    @Transactional
    public void createDanhgia() throws Exception {
        int databaseSizeBeforeCreate = danhgiaRepository.findAll().size();

        // Create the Danhgia
        DanhgiaDTO danhgiaDTO = danhgiaMapper.toDto(danhgia);
        restDanhgiaMockMvc.perform(post("/api/danhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Danhgia in the database
        List<Danhgia> danhgiaList = danhgiaRepository.findAll();
        assertThat(danhgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Danhgia testDanhgia = danhgiaList.get(danhgiaList.size() - 1);
        assertThat(testDanhgia.getMa()).isEqualTo(DEFAULT_MA);
        assertThat(testDanhgia.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testDanhgia.getDiem()).isEqualTo(DEFAULT_DIEM);
        assertThat(testDanhgia.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testDanhgia.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createDanhgiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = danhgiaRepository.findAll().size();

        // Create the Danhgia with an existing ID
        danhgia.setId(1L);
        DanhgiaDTO danhgiaDTO = danhgiaMapper.toDto(danhgia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDanhgiaMockMvc.perform(post("/api/danhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Danhgia in the database
        List<Danhgia> danhgiaList = danhgiaRepository.findAll();
        assertThat(danhgiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDanhgias() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList
        restDanhgiaMockMvc.perform(get("/api/danhgias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA)))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].diem").value(hasItem(DEFAULT_DIEM)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getDanhgia() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get the danhgia
        restDanhgiaMockMvc.perform(get("/api/danhgias/{id}", danhgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(danhgia.getId().intValue()))
            .andExpect(jsonPath("$.ma").value(DEFAULT_MA))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.diem").value(DEFAULT_DIEM))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getDanhgiasByIdFiltering() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        Long id = danhgia.getId();

        defaultDanhgiaShouldBeFound("id.equals=" + id);
        defaultDanhgiaShouldNotBeFound("id.notEquals=" + id);

        defaultDanhgiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDanhgiaShouldNotBeFound("id.greaterThan=" + id);

        defaultDanhgiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDanhgiaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDanhgiasByMaIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ma equals to DEFAULT_MA
        defaultDanhgiaShouldBeFound("ma.equals=" + DEFAULT_MA);

        // Get all the danhgiaList where ma equals to UPDATED_MA
        defaultDanhgiaShouldNotBeFound("ma.equals=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByMaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ma not equals to DEFAULT_MA
        defaultDanhgiaShouldNotBeFound("ma.notEquals=" + DEFAULT_MA);

        // Get all the danhgiaList where ma not equals to UPDATED_MA
        defaultDanhgiaShouldBeFound("ma.notEquals=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByMaIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ma in DEFAULT_MA or UPDATED_MA
        defaultDanhgiaShouldBeFound("ma.in=" + DEFAULT_MA + "," + UPDATED_MA);

        // Get all the danhgiaList where ma equals to UPDATED_MA
        defaultDanhgiaShouldNotBeFound("ma.in=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByMaIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ma is not null
        defaultDanhgiaShouldBeFound("ma.specified=true");

        // Get all the danhgiaList where ma is null
        defaultDanhgiaShouldNotBeFound("ma.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhgiasByMaContainsSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ma contains DEFAULT_MA
        defaultDanhgiaShouldBeFound("ma.contains=" + DEFAULT_MA);

        // Get all the danhgiaList where ma contains UPDATED_MA
        defaultDanhgiaShouldNotBeFound("ma.contains=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByMaNotContainsSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ma does not contain DEFAULT_MA
        defaultDanhgiaShouldNotBeFound("ma.doesNotContain=" + DEFAULT_MA);

        // Get all the danhgiaList where ma does not contain UPDATED_MA
        defaultDanhgiaShouldBeFound("ma.doesNotContain=" + UPDATED_MA);
    }


    @Test
    @Transactional
    public void getAllDanhgiasByTenIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ten equals to DEFAULT_TEN
        defaultDanhgiaShouldBeFound("ten.equals=" + DEFAULT_TEN);

        // Get all the danhgiaList where ten equals to UPDATED_TEN
        defaultDanhgiaShouldNotBeFound("ten.equals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByTenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ten not equals to DEFAULT_TEN
        defaultDanhgiaShouldNotBeFound("ten.notEquals=" + DEFAULT_TEN);

        // Get all the danhgiaList where ten not equals to UPDATED_TEN
        defaultDanhgiaShouldBeFound("ten.notEquals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByTenIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ten in DEFAULT_TEN or UPDATED_TEN
        defaultDanhgiaShouldBeFound("ten.in=" + DEFAULT_TEN + "," + UPDATED_TEN);

        // Get all the danhgiaList where ten equals to UPDATED_TEN
        defaultDanhgiaShouldNotBeFound("ten.in=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByTenIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ten is not null
        defaultDanhgiaShouldBeFound("ten.specified=true");

        // Get all the danhgiaList where ten is null
        defaultDanhgiaShouldNotBeFound("ten.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhgiasByTenContainsSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ten contains DEFAULT_TEN
        defaultDanhgiaShouldBeFound("ten.contains=" + DEFAULT_TEN);

        // Get all the danhgiaList where ten contains UPDATED_TEN
        defaultDanhgiaShouldNotBeFound("ten.contains=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByTenNotContainsSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where ten does not contain DEFAULT_TEN
        defaultDanhgiaShouldNotBeFound("ten.doesNotContain=" + DEFAULT_TEN);

        // Get all the danhgiaList where ten does not contain UPDATED_TEN
        defaultDanhgiaShouldBeFound("ten.doesNotContain=" + UPDATED_TEN);
    }


    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem equals to DEFAULT_DIEM
        defaultDanhgiaShouldBeFound("diem.equals=" + DEFAULT_DIEM);

        // Get all the danhgiaList where diem equals to UPDATED_DIEM
        defaultDanhgiaShouldNotBeFound("diem.equals=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem not equals to DEFAULT_DIEM
        defaultDanhgiaShouldNotBeFound("diem.notEquals=" + DEFAULT_DIEM);

        // Get all the danhgiaList where diem not equals to UPDATED_DIEM
        defaultDanhgiaShouldBeFound("diem.notEquals=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem in DEFAULT_DIEM or UPDATED_DIEM
        defaultDanhgiaShouldBeFound("diem.in=" + DEFAULT_DIEM + "," + UPDATED_DIEM);

        // Get all the danhgiaList where diem equals to UPDATED_DIEM
        defaultDanhgiaShouldNotBeFound("diem.in=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem is not null
        defaultDanhgiaShouldBeFound("diem.specified=true");

        // Get all the danhgiaList where diem is null
        defaultDanhgiaShouldNotBeFound("diem.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem is greater than or equal to DEFAULT_DIEM
        defaultDanhgiaShouldBeFound("diem.greaterThanOrEqual=" + DEFAULT_DIEM);

        // Get all the danhgiaList where diem is greater than or equal to UPDATED_DIEM
        defaultDanhgiaShouldNotBeFound("diem.greaterThanOrEqual=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem is less than or equal to DEFAULT_DIEM
        defaultDanhgiaShouldBeFound("diem.lessThanOrEqual=" + DEFAULT_DIEM);

        // Get all the danhgiaList where diem is less than or equal to SMALLER_DIEM
        defaultDanhgiaShouldNotBeFound("diem.lessThanOrEqual=" + SMALLER_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsLessThanSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem is less than DEFAULT_DIEM
        defaultDanhgiaShouldNotBeFound("diem.lessThan=" + DEFAULT_DIEM);

        // Get all the danhgiaList where diem is less than UPDATED_DIEM
        defaultDanhgiaShouldBeFound("diem.lessThan=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByDiemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where diem is greater than DEFAULT_DIEM
        defaultDanhgiaShouldNotBeFound("diem.greaterThan=" + DEFAULT_DIEM);

        // Get all the danhgiaList where diem is greater than SMALLER_DIEM
        defaultDanhgiaShouldBeFound("diem.greaterThan=" + SMALLER_DIEM);
    }


    @Test
    @Transactional
    public void getAllDanhgiasByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where noidung equals to DEFAULT_NOIDUNG
        defaultDanhgiaShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaList where noidung equals to UPDATED_NOIDUNG
        defaultDanhgiaShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where noidung not equals to DEFAULT_NOIDUNG
        defaultDanhgiaShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaList where noidung not equals to UPDATED_NOIDUNG
        defaultDanhgiaShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultDanhgiaShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the danhgiaList where noidung equals to UPDATED_NOIDUNG
        defaultDanhgiaShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where noidung is not null
        defaultDanhgiaShouldBeFound("noidung.specified=true");

        // Get all the danhgiaList where noidung is null
        defaultDanhgiaShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhgiasByNoidungContainsSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where noidung contains DEFAULT_NOIDUNG
        defaultDanhgiaShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaList where noidung contains UPDATED_NOIDUNG
        defaultDanhgiaShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where noidung does not contain DEFAULT_NOIDUNG
        defaultDanhgiaShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaList where noidung does not contain UPDATED_NOIDUNG
        defaultDanhgiaShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung equals to DEFAULT_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the danhgiaList where sudung equals to UPDATED_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung not equals to DEFAULT_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the danhgiaList where sudung not equals to UPDATED_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the danhgiaList where sudung equals to UPDATED_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung is not null
        defaultDanhgiaShouldBeFound("sudung.specified=true");

        // Get all the danhgiaList where sudung is null
        defaultDanhgiaShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the danhgiaList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the danhgiaList where sudung is less than or equal to SMALLER_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung is less than DEFAULT_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the danhgiaList where sudung is less than UPDATED_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiasBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        // Get all the danhgiaList where sudung is greater than DEFAULT_SUDUNG
        defaultDanhgiaShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the danhgiaList where sudung is greater than SMALLER_SUDUNG
        defaultDanhgiaShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllDanhgiasByDanhgiaCTIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);
        DanhgiaCT danhgiaCT = DanhgiaCTResourceIT.createEntity(em);
        em.persist(danhgiaCT);
        em.flush();
        danhgia.addDanhgiaCT(danhgiaCT);
        danhgiaRepository.saveAndFlush(danhgia);
        Long danhgiaCTId = danhgiaCT.getId();

        // Get all the danhgiaList where danhgiaCT equals to danhgiaCTId
        defaultDanhgiaShouldBeFound("danhgiaCTId.equals=" + danhgiaCTId);

        // Get all the danhgiaList where danhgiaCT equals to danhgiaCTId + 1
        defaultDanhgiaShouldNotBeFound("danhgiaCTId.equals=" + (danhgiaCTId + 1));
    }


    @Test
    @Transactional
    public void getAllDanhgiasByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        danhgia.setDetai(detai);
        danhgiaRepository.saveAndFlush(danhgia);
        Long detaiId = detai.getId();

        // Get all the danhgiaList where detai equals to detaiId
        defaultDanhgiaShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the danhgiaList where detai equals to detaiId + 1
        defaultDanhgiaShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDanhgiaShouldBeFound(String filter) throws Exception {
        restDanhgiaMockMvc.perform(get("/api/danhgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA)))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].diem").value(hasItem(DEFAULT_DIEM)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restDanhgiaMockMvc.perform(get("/api/danhgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDanhgiaShouldNotBeFound(String filter) throws Exception {
        restDanhgiaMockMvc.perform(get("/api/danhgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDanhgiaMockMvc.perform(get("/api/danhgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDanhgia() throws Exception {
        // Get the danhgia
        restDanhgiaMockMvc.perform(get("/api/danhgias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDanhgia() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        int databaseSizeBeforeUpdate = danhgiaRepository.findAll().size();

        // Update the danhgia
        Danhgia updatedDanhgia = danhgiaRepository.findById(danhgia.getId()).get();
        // Disconnect from session so that the updates on updatedDanhgia are not directly saved in db
        em.detach(updatedDanhgia);
        updatedDanhgia
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN)
            .diem(UPDATED_DIEM)
            .noidung(UPDATED_NOIDUNG)
            .sudung(UPDATED_SUDUNG);
        DanhgiaDTO danhgiaDTO = danhgiaMapper.toDto(updatedDanhgia);

        restDanhgiaMockMvc.perform(put("/api/danhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaDTO)))
            .andExpect(status().isOk());

        // Validate the Danhgia in the database
        List<Danhgia> danhgiaList = danhgiaRepository.findAll();
        assertThat(danhgiaList).hasSize(databaseSizeBeforeUpdate);
        Danhgia testDanhgia = danhgiaList.get(danhgiaList.size() - 1);
        assertThat(testDanhgia.getMa()).isEqualTo(UPDATED_MA);
        assertThat(testDanhgia.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testDanhgia.getDiem()).isEqualTo(UPDATED_DIEM);
        assertThat(testDanhgia.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testDanhgia.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDanhgia() throws Exception {
        int databaseSizeBeforeUpdate = danhgiaRepository.findAll().size();

        // Create the Danhgia
        DanhgiaDTO danhgiaDTO = danhgiaMapper.toDto(danhgia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhgiaMockMvc.perform(put("/api/danhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Danhgia in the database
        List<Danhgia> danhgiaList = danhgiaRepository.findAll();
        assertThat(danhgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDanhgia() throws Exception {
        // Initialize the database
        danhgiaRepository.saveAndFlush(danhgia);

        int databaseSizeBeforeDelete = danhgiaRepository.findAll().size();

        // Delete the danhgia
        restDanhgiaMockMvc.perform(delete("/api/danhgias/{id}", danhgia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Danhgia> danhgiaList = danhgiaRepository.findAll();
        assertThat(danhgiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
