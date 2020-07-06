package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.ThanhvienHD;
import com.api.backend.domain.Hoidongdanhgia;
import com.api.backend.repository.ThanhvienHDRepository;
import com.api.backend.service.ThanhvienHDService;
import com.api.backend.service.dto.ThanhvienHDDTO;
import com.api.backend.service.mapper.ThanhvienHDMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.ThanhvienHDCriteria;
import com.api.backend.service.ThanhvienHDQueryService;

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
 * Integration tests for the {@link ThanhvienHDResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class ThanhvienHDResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_DONVI = "AAAAAAAAAA";
    private static final String UPDATED_DONVI = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRACHNHIEM = 1;
    private static final Integer UPDATED_TRACHNHIEM = 2;
    private static final Integer SMALLER_TRACHNHIEM = 1 - 1;

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private ThanhvienHDRepository thanhvienHDRepository;

    @Autowired
    private ThanhvienHDMapper thanhvienHDMapper;

    @Autowired
    private ThanhvienHDService thanhvienHDService;

    @Autowired
    private ThanhvienHDQueryService thanhvienHDQueryService;

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

    private MockMvc restThanhvienHDMockMvc;

    private ThanhvienHD thanhvienHD;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThanhvienHDResource thanhvienHDResource = new ThanhvienHDResource(thanhvienHDService, thanhvienHDQueryService);
        this.restThanhvienHDMockMvc = MockMvcBuilders.standaloneSetup(thanhvienHDResource)
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
    public static ThanhvienHD createEntity(EntityManager em) {
        ThanhvienHD thanhvienHD = new ThanhvienHD()
            .ten(DEFAULT_TEN)
            .donvi(DEFAULT_DONVI)
            .trachnhiem(DEFAULT_TRACHNHIEM)
            .sudung(DEFAULT_SUDUNG);
        return thanhvienHD;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThanhvienHD createUpdatedEntity(EntityManager em) {
        ThanhvienHD thanhvienHD = new ThanhvienHD()
            .ten(UPDATED_TEN)
            .donvi(UPDATED_DONVI)
            .trachnhiem(UPDATED_TRACHNHIEM)
            .sudung(UPDATED_SUDUNG);
        return thanhvienHD;
    }

    @BeforeEach
    public void initTest() {
        thanhvienHD = createEntity(em);
    }

    @Test
    @Transactional
    public void createThanhvienHD() throws Exception {
        int databaseSizeBeforeCreate = thanhvienHDRepository.findAll().size();

        // Create the ThanhvienHD
        ThanhvienHDDTO thanhvienHDDTO = thanhvienHDMapper.toDto(thanhvienHD);
        restThanhvienHDMockMvc.perform(post("/api/thanhvien-hds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thanhvienHDDTO)))
            .andExpect(status().isCreated());

        // Validate the ThanhvienHD in the database
        List<ThanhvienHD> thanhvienHDList = thanhvienHDRepository.findAll();
        assertThat(thanhvienHDList).hasSize(databaseSizeBeforeCreate + 1);
        ThanhvienHD testThanhvienHD = thanhvienHDList.get(thanhvienHDList.size() - 1);
        assertThat(testThanhvienHD.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testThanhvienHD.getDonvi()).isEqualTo(DEFAULT_DONVI);
        assertThat(testThanhvienHD.getTrachnhiem()).isEqualTo(DEFAULT_TRACHNHIEM);
        assertThat(testThanhvienHD.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createThanhvienHDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thanhvienHDRepository.findAll().size();

        // Create the ThanhvienHD with an existing ID
        thanhvienHD.setId(1L);
        ThanhvienHDDTO thanhvienHDDTO = thanhvienHDMapper.toDto(thanhvienHD);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThanhvienHDMockMvc.perform(post("/api/thanhvien-hds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thanhvienHDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThanhvienHD in the database
        List<ThanhvienHD> thanhvienHDList = thanhvienHDRepository.findAll();
        assertThat(thanhvienHDList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllThanhvienHDS() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thanhvienHD.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].donvi").value(hasItem(DEFAULT_DONVI)))
            .andExpect(jsonPath("$.[*].trachnhiem").value(hasItem(DEFAULT_TRACHNHIEM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getThanhvienHD() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get the thanhvienHD
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds/{id}", thanhvienHD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thanhvienHD.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.donvi").value(DEFAULT_DONVI))
            .andExpect(jsonPath("$.trachnhiem").value(DEFAULT_TRACHNHIEM))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getThanhvienHDSByIdFiltering() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        Long id = thanhvienHD.getId();

        defaultThanhvienHDShouldBeFound("id.equals=" + id);
        defaultThanhvienHDShouldNotBeFound("id.notEquals=" + id);

        defaultThanhvienHDShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThanhvienHDShouldNotBeFound("id.greaterThan=" + id);

        defaultThanhvienHDShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThanhvienHDShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllThanhvienHDSByTenIsEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where ten equals to DEFAULT_TEN
        defaultThanhvienHDShouldBeFound("ten.equals=" + DEFAULT_TEN);

        // Get all the thanhvienHDList where ten equals to UPDATED_TEN
        defaultThanhvienHDShouldNotBeFound("ten.equals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where ten not equals to DEFAULT_TEN
        defaultThanhvienHDShouldNotBeFound("ten.notEquals=" + DEFAULT_TEN);

        // Get all the thanhvienHDList where ten not equals to UPDATED_TEN
        defaultThanhvienHDShouldBeFound("ten.notEquals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTenIsInShouldWork() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where ten in DEFAULT_TEN or UPDATED_TEN
        defaultThanhvienHDShouldBeFound("ten.in=" + DEFAULT_TEN + "," + UPDATED_TEN);

        // Get all the thanhvienHDList where ten equals to UPDATED_TEN
        defaultThanhvienHDShouldNotBeFound("ten.in=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTenIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where ten is not null
        defaultThanhvienHDShouldBeFound("ten.specified=true");

        // Get all the thanhvienHDList where ten is null
        defaultThanhvienHDShouldNotBeFound("ten.specified=false");
    }
                @Test
    @Transactional
    public void getAllThanhvienHDSByTenContainsSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where ten contains DEFAULT_TEN
        defaultThanhvienHDShouldBeFound("ten.contains=" + DEFAULT_TEN);

        // Get all the thanhvienHDList where ten contains UPDATED_TEN
        defaultThanhvienHDShouldNotBeFound("ten.contains=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTenNotContainsSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where ten does not contain DEFAULT_TEN
        defaultThanhvienHDShouldNotBeFound("ten.doesNotContain=" + DEFAULT_TEN);

        // Get all the thanhvienHDList where ten does not contain UPDATED_TEN
        defaultThanhvienHDShouldBeFound("ten.doesNotContain=" + UPDATED_TEN);
    }


    @Test
    @Transactional
    public void getAllThanhvienHDSByDonviIsEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where donvi equals to DEFAULT_DONVI
        defaultThanhvienHDShouldBeFound("donvi.equals=" + DEFAULT_DONVI);

        // Get all the thanhvienHDList where donvi equals to UPDATED_DONVI
        defaultThanhvienHDShouldNotBeFound("donvi.equals=" + UPDATED_DONVI);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByDonviIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where donvi not equals to DEFAULT_DONVI
        defaultThanhvienHDShouldNotBeFound("donvi.notEquals=" + DEFAULT_DONVI);

        // Get all the thanhvienHDList where donvi not equals to UPDATED_DONVI
        defaultThanhvienHDShouldBeFound("donvi.notEquals=" + UPDATED_DONVI);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByDonviIsInShouldWork() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where donvi in DEFAULT_DONVI or UPDATED_DONVI
        defaultThanhvienHDShouldBeFound("donvi.in=" + DEFAULT_DONVI + "," + UPDATED_DONVI);

        // Get all the thanhvienHDList where donvi equals to UPDATED_DONVI
        defaultThanhvienHDShouldNotBeFound("donvi.in=" + UPDATED_DONVI);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByDonviIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where donvi is not null
        defaultThanhvienHDShouldBeFound("donvi.specified=true");

        // Get all the thanhvienHDList where donvi is null
        defaultThanhvienHDShouldNotBeFound("donvi.specified=false");
    }
                @Test
    @Transactional
    public void getAllThanhvienHDSByDonviContainsSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where donvi contains DEFAULT_DONVI
        defaultThanhvienHDShouldBeFound("donvi.contains=" + DEFAULT_DONVI);

        // Get all the thanhvienHDList where donvi contains UPDATED_DONVI
        defaultThanhvienHDShouldNotBeFound("donvi.contains=" + UPDATED_DONVI);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByDonviNotContainsSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where donvi does not contain DEFAULT_DONVI
        defaultThanhvienHDShouldNotBeFound("donvi.doesNotContain=" + DEFAULT_DONVI);

        // Get all the thanhvienHDList where donvi does not contain UPDATED_DONVI
        defaultThanhvienHDShouldBeFound("donvi.doesNotContain=" + UPDATED_DONVI);
    }


    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem equals to DEFAULT_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.equals=" + DEFAULT_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem equals to UPDATED_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.equals=" + UPDATED_TRACHNHIEM);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem not equals to DEFAULT_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.notEquals=" + DEFAULT_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem not equals to UPDATED_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.notEquals=" + UPDATED_TRACHNHIEM);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsInShouldWork() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem in DEFAULT_TRACHNHIEM or UPDATED_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.in=" + DEFAULT_TRACHNHIEM + "," + UPDATED_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem equals to UPDATED_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.in=" + UPDATED_TRACHNHIEM);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem is not null
        defaultThanhvienHDShouldBeFound("trachnhiem.specified=true");

        // Get all the thanhvienHDList where trachnhiem is null
        defaultThanhvienHDShouldNotBeFound("trachnhiem.specified=false");
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem is greater than or equal to DEFAULT_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.greaterThanOrEqual=" + DEFAULT_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem is greater than or equal to UPDATED_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.greaterThanOrEqual=" + UPDATED_TRACHNHIEM);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem is less than or equal to DEFAULT_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.lessThanOrEqual=" + DEFAULT_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem is less than or equal to SMALLER_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.lessThanOrEqual=" + SMALLER_TRACHNHIEM);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsLessThanSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem is less than DEFAULT_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.lessThan=" + DEFAULT_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem is less than UPDATED_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.lessThan=" + UPDATED_TRACHNHIEM);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSByTrachnhiemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where trachnhiem is greater than DEFAULT_TRACHNHIEM
        defaultThanhvienHDShouldNotBeFound("trachnhiem.greaterThan=" + DEFAULT_TRACHNHIEM);

        // Get all the thanhvienHDList where trachnhiem is greater than SMALLER_TRACHNHIEM
        defaultThanhvienHDShouldBeFound("trachnhiem.greaterThan=" + SMALLER_TRACHNHIEM);
    }


    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung equals to DEFAULT_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the thanhvienHDList where sudung equals to UPDATED_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung not equals to DEFAULT_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the thanhvienHDList where sudung not equals to UPDATED_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the thanhvienHDList where sudung equals to UPDATED_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung is not null
        defaultThanhvienHDShouldBeFound("sudung.specified=true");

        // Get all the thanhvienHDList where sudung is null
        defaultThanhvienHDShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the thanhvienHDList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the thanhvienHDList where sudung is less than or equal to SMALLER_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung is less than DEFAULT_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the thanhvienHDList where sudung is less than UPDATED_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllThanhvienHDSBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        // Get all the thanhvienHDList where sudung is greater than DEFAULT_SUDUNG
        defaultThanhvienHDShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the thanhvienHDList where sudung is greater than SMALLER_SUDUNG
        defaultThanhvienHDShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllThanhvienHDSByHoidongdanhgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);
        Hoidongdanhgia hoidongdanhgia = HoidongdanhgiaResourceIT.createEntity(em);
        em.persist(hoidongdanhgia);
        em.flush();
        thanhvienHD.setHoidongdanhgia(hoidongdanhgia);
        thanhvienHDRepository.saveAndFlush(thanhvienHD);
        Long hoidongdanhgiaId = hoidongdanhgia.getId();

        // Get all the thanhvienHDList where hoidongdanhgia equals to hoidongdanhgiaId
        defaultThanhvienHDShouldBeFound("hoidongdanhgiaId.equals=" + hoidongdanhgiaId);

        // Get all the thanhvienHDList where hoidongdanhgia equals to hoidongdanhgiaId + 1
        defaultThanhvienHDShouldNotBeFound("hoidongdanhgiaId.equals=" + (hoidongdanhgiaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThanhvienHDShouldBeFound(String filter) throws Exception {
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thanhvienHD.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].donvi").value(hasItem(DEFAULT_DONVI)))
            .andExpect(jsonPath("$.[*].trachnhiem").value(hasItem(DEFAULT_TRACHNHIEM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThanhvienHDShouldNotBeFound(String filter) throws Exception {
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingThanhvienHD() throws Exception {
        // Get the thanhvienHD
        restThanhvienHDMockMvc.perform(get("/api/thanhvien-hds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThanhvienHD() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        int databaseSizeBeforeUpdate = thanhvienHDRepository.findAll().size();

        // Update the thanhvienHD
        ThanhvienHD updatedThanhvienHD = thanhvienHDRepository.findById(thanhvienHD.getId()).get();
        // Disconnect from session so that the updates on updatedThanhvienHD are not directly saved in db
        em.detach(updatedThanhvienHD);
        updatedThanhvienHD
            .ten(UPDATED_TEN)
            .donvi(UPDATED_DONVI)
            .trachnhiem(UPDATED_TRACHNHIEM)
            .sudung(UPDATED_SUDUNG);
        ThanhvienHDDTO thanhvienHDDTO = thanhvienHDMapper.toDto(updatedThanhvienHD);

        restThanhvienHDMockMvc.perform(put("/api/thanhvien-hds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thanhvienHDDTO)))
            .andExpect(status().isOk());

        // Validate the ThanhvienHD in the database
        List<ThanhvienHD> thanhvienHDList = thanhvienHDRepository.findAll();
        assertThat(thanhvienHDList).hasSize(databaseSizeBeforeUpdate);
        ThanhvienHD testThanhvienHD = thanhvienHDList.get(thanhvienHDList.size() - 1);
        assertThat(testThanhvienHD.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testThanhvienHD.getDonvi()).isEqualTo(UPDATED_DONVI);
        assertThat(testThanhvienHD.getTrachnhiem()).isEqualTo(UPDATED_TRACHNHIEM);
        assertThat(testThanhvienHD.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingThanhvienHD() throws Exception {
        int databaseSizeBeforeUpdate = thanhvienHDRepository.findAll().size();

        // Create the ThanhvienHD
        ThanhvienHDDTO thanhvienHDDTO = thanhvienHDMapper.toDto(thanhvienHD);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThanhvienHDMockMvc.perform(put("/api/thanhvien-hds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thanhvienHDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThanhvienHD in the database
        List<ThanhvienHD> thanhvienHDList = thanhvienHDRepository.findAll();
        assertThat(thanhvienHDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThanhvienHD() throws Exception {
        // Initialize the database
        thanhvienHDRepository.saveAndFlush(thanhvienHD);

        int databaseSizeBeforeDelete = thanhvienHDRepository.findAll().size();

        // Delete the thanhvienHD
        restThanhvienHDMockMvc.perform(delete("/api/thanhvien-hds/{id}", thanhvienHD.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThanhvienHD> thanhvienHDList = thanhvienHDRepository.findAll();
        assertThat(thanhvienHDList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
