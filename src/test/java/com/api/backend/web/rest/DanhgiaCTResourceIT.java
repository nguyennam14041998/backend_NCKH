package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.DanhgiaCT;
import com.api.backend.domain.Danhgia;
import com.api.backend.domain.Noidungdanhgia;
import com.api.backend.repository.DanhgiaCTRepository;
import com.api.backend.service.DanhgiaCTService;
import com.api.backend.service.dto.DanhgiaCTDTO;
import com.api.backend.service.mapper.DanhgiaCTMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DanhgiaCTCriteria;
import com.api.backend.service.DanhgiaCTQueryService;

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
 * Integration tests for the {@link DanhgiaCTResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DanhgiaCTResourceIT {

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIEM = 1;
    private static final Integer UPDATED_DIEM = 2;
    private static final Integer SMALLER_DIEM = 1 - 1;

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private DanhgiaCTRepository danhgiaCTRepository;

    @Autowired
    private DanhgiaCTMapper danhgiaCTMapper;

    @Autowired
    private DanhgiaCTService danhgiaCTService;

    @Autowired
    private DanhgiaCTQueryService danhgiaCTQueryService;

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

    private MockMvc restDanhgiaCTMockMvc;

    private DanhgiaCT danhgiaCT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DanhgiaCTResource danhgiaCTResource = new DanhgiaCTResource(danhgiaCTService, danhgiaCTQueryService);
        this.restDanhgiaCTMockMvc = MockMvcBuilders.standaloneSetup(danhgiaCTResource)
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
    public static DanhgiaCT createEntity(EntityManager em) {
        DanhgiaCT danhgiaCT = new DanhgiaCT()
            .noidung(DEFAULT_NOIDUNG)
            .diem(DEFAULT_DIEM)
            .sudung(DEFAULT_SUDUNG);
        return danhgiaCT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DanhgiaCT createUpdatedEntity(EntityManager em) {
        DanhgiaCT danhgiaCT = new DanhgiaCT()
            .noidung(UPDATED_NOIDUNG)
            .diem(UPDATED_DIEM)
            .sudung(UPDATED_SUDUNG);
        return danhgiaCT;
    }

    @BeforeEach
    public void initTest() {
        danhgiaCT = createEntity(em);
    }

    @Test
    @Transactional
    public void createDanhgiaCT() throws Exception {
        int databaseSizeBeforeCreate = danhgiaCTRepository.findAll().size();

        // Create the DanhgiaCT
        DanhgiaCTDTO danhgiaCTDTO = danhgiaCTMapper.toDto(danhgiaCT);
        restDanhgiaCTMockMvc.perform(post("/api/danhgia-cts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaCTDTO)))
            .andExpect(status().isCreated());

        // Validate the DanhgiaCT in the database
        List<DanhgiaCT> danhgiaCTList = danhgiaCTRepository.findAll();
        assertThat(danhgiaCTList).hasSize(databaseSizeBeforeCreate + 1);
        DanhgiaCT testDanhgiaCT = danhgiaCTList.get(danhgiaCTList.size() - 1);
        assertThat(testDanhgiaCT.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testDanhgiaCT.getDiem()).isEqualTo(DEFAULT_DIEM);
        assertThat(testDanhgiaCT.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createDanhgiaCTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = danhgiaCTRepository.findAll().size();

        // Create the DanhgiaCT with an existing ID
        danhgiaCT.setId(1L);
        DanhgiaCTDTO danhgiaCTDTO = danhgiaCTMapper.toDto(danhgiaCT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDanhgiaCTMockMvc.perform(post("/api/danhgia-cts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaCTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DanhgiaCT in the database
        List<DanhgiaCT> danhgiaCTList = danhgiaCTRepository.findAll();
        assertThat(danhgiaCTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDanhgiaCTS() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhgiaCT.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].diem").value(hasItem(DEFAULT_DIEM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getDanhgiaCT() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get the danhgiaCT
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts/{id}", danhgiaCT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(danhgiaCT.getId().intValue()))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.diem").value(DEFAULT_DIEM))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getDanhgiaCTSByIdFiltering() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        Long id = danhgiaCT.getId();

        defaultDanhgiaCTShouldBeFound("id.equals=" + id);
        defaultDanhgiaCTShouldNotBeFound("id.notEquals=" + id);

        defaultDanhgiaCTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDanhgiaCTShouldNotBeFound("id.greaterThan=" + id);

        defaultDanhgiaCTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDanhgiaCTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where noidung equals to DEFAULT_NOIDUNG
        defaultDanhgiaCTShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaCTList where noidung equals to UPDATED_NOIDUNG
        defaultDanhgiaCTShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where noidung not equals to DEFAULT_NOIDUNG
        defaultDanhgiaCTShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaCTList where noidung not equals to UPDATED_NOIDUNG
        defaultDanhgiaCTShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultDanhgiaCTShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the danhgiaCTList where noidung equals to UPDATED_NOIDUNG
        defaultDanhgiaCTShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where noidung is not null
        defaultDanhgiaCTShouldBeFound("noidung.specified=true");

        // Get all the danhgiaCTList where noidung is null
        defaultDanhgiaCTShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungContainsSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where noidung contains DEFAULT_NOIDUNG
        defaultDanhgiaCTShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaCTList where noidung contains UPDATED_NOIDUNG
        defaultDanhgiaCTShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where noidung does not contain DEFAULT_NOIDUNG
        defaultDanhgiaCTShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the danhgiaCTList where noidung does not contain UPDATED_NOIDUNG
        defaultDanhgiaCTShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem equals to DEFAULT_DIEM
        defaultDanhgiaCTShouldBeFound("diem.equals=" + DEFAULT_DIEM);

        // Get all the danhgiaCTList where diem equals to UPDATED_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.equals=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem not equals to DEFAULT_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.notEquals=" + DEFAULT_DIEM);

        // Get all the danhgiaCTList where diem not equals to UPDATED_DIEM
        defaultDanhgiaCTShouldBeFound("diem.notEquals=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem in DEFAULT_DIEM or UPDATED_DIEM
        defaultDanhgiaCTShouldBeFound("diem.in=" + DEFAULT_DIEM + "," + UPDATED_DIEM);

        // Get all the danhgiaCTList where diem equals to UPDATED_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.in=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem is not null
        defaultDanhgiaCTShouldBeFound("diem.specified=true");

        // Get all the danhgiaCTList where diem is null
        defaultDanhgiaCTShouldNotBeFound("diem.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem is greater than or equal to DEFAULT_DIEM
        defaultDanhgiaCTShouldBeFound("diem.greaterThanOrEqual=" + DEFAULT_DIEM);

        // Get all the danhgiaCTList where diem is greater than or equal to UPDATED_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.greaterThanOrEqual=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem is less than or equal to DEFAULT_DIEM
        defaultDanhgiaCTShouldBeFound("diem.lessThanOrEqual=" + DEFAULT_DIEM);

        // Get all the danhgiaCTList where diem is less than or equal to SMALLER_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.lessThanOrEqual=" + SMALLER_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsLessThanSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem is less than DEFAULT_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.lessThan=" + DEFAULT_DIEM);

        // Get all the danhgiaCTList where diem is less than UPDATED_DIEM
        defaultDanhgiaCTShouldBeFound("diem.lessThan=" + UPDATED_DIEM);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSByDiemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where diem is greater than DEFAULT_DIEM
        defaultDanhgiaCTShouldNotBeFound("diem.greaterThan=" + DEFAULT_DIEM);

        // Get all the danhgiaCTList where diem is greater than SMALLER_DIEM
        defaultDanhgiaCTShouldBeFound("diem.greaterThan=" + SMALLER_DIEM);
    }


    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung equals to DEFAULT_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the danhgiaCTList where sudung equals to UPDATED_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung not equals to DEFAULT_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the danhgiaCTList where sudung not equals to UPDATED_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the danhgiaCTList where sudung equals to UPDATED_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung is not null
        defaultDanhgiaCTShouldBeFound("sudung.specified=true");

        // Get all the danhgiaCTList where sudung is null
        defaultDanhgiaCTShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the danhgiaCTList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the danhgiaCTList where sudung is less than or equal to SMALLER_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung is less than DEFAULT_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the danhgiaCTList where sudung is less than UPDATED_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDanhgiaCTSBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        // Get all the danhgiaCTList where sudung is greater than DEFAULT_SUDUNG
        defaultDanhgiaCTShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the danhgiaCTList where sudung is greater than SMALLER_SUDUNG
        defaultDanhgiaCTShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllDanhgiaCTSByDanhgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);
        Danhgia danhgia = DanhgiaResourceIT.createEntity(em);
        em.persist(danhgia);
        em.flush();
        danhgiaCT.setDanhgia(danhgia);
        danhgiaCTRepository.saveAndFlush(danhgiaCT);
        Long danhgiaId = danhgia.getId();

        // Get all the danhgiaCTList where danhgia equals to danhgiaId
        defaultDanhgiaCTShouldBeFound("danhgiaId.equals=" + danhgiaId);

        // Get all the danhgiaCTList where danhgia equals to danhgiaId + 1
        defaultDanhgiaCTShouldNotBeFound("danhgiaId.equals=" + (danhgiaId + 1));
    }


    @Test
    @Transactional
    public void getAllDanhgiaCTSByNoidungdanhgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);
        Noidungdanhgia noidungdanhgia = NoidungdanhgiaResourceIT.createEntity(em);
        em.persist(noidungdanhgia);
        em.flush();
        danhgiaCT.setNoidungdanhgia(noidungdanhgia);
        danhgiaCTRepository.saveAndFlush(danhgiaCT);
        Long noidungdanhgiaId = noidungdanhgia.getId();

        // Get all the danhgiaCTList where noidungdanhgia equals to noidungdanhgiaId
        defaultDanhgiaCTShouldBeFound("noidungdanhgiaId.equals=" + noidungdanhgiaId);

        // Get all the danhgiaCTList where noidungdanhgia equals to noidungdanhgiaId + 1
        defaultDanhgiaCTShouldNotBeFound("noidungdanhgiaId.equals=" + (noidungdanhgiaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDanhgiaCTShouldBeFound(String filter) throws Exception {
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhgiaCT.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].diem").value(hasItem(DEFAULT_DIEM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDanhgiaCTShouldNotBeFound(String filter) throws Exception {
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDanhgiaCT() throws Exception {
        // Get the danhgiaCT
        restDanhgiaCTMockMvc.perform(get("/api/danhgia-cts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDanhgiaCT() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        int databaseSizeBeforeUpdate = danhgiaCTRepository.findAll().size();

        // Update the danhgiaCT
        DanhgiaCT updatedDanhgiaCT = danhgiaCTRepository.findById(danhgiaCT.getId()).get();
        // Disconnect from session so that the updates on updatedDanhgiaCT are not directly saved in db
        em.detach(updatedDanhgiaCT);
        updatedDanhgiaCT
            .noidung(UPDATED_NOIDUNG)
            .diem(UPDATED_DIEM)
            .sudung(UPDATED_SUDUNG);
        DanhgiaCTDTO danhgiaCTDTO = danhgiaCTMapper.toDto(updatedDanhgiaCT);

        restDanhgiaCTMockMvc.perform(put("/api/danhgia-cts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaCTDTO)))
            .andExpect(status().isOk());

        // Validate the DanhgiaCT in the database
        List<DanhgiaCT> danhgiaCTList = danhgiaCTRepository.findAll();
        assertThat(danhgiaCTList).hasSize(databaseSizeBeforeUpdate);
        DanhgiaCT testDanhgiaCT = danhgiaCTList.get(danhgiaCTList.size() - 1);
        assertThat(testDanhgiaCT.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testDanhgiaCT.getDiem()).isEqualTo(UPDATED_DIEM);
        assertThat(testDanhgiaCT.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDanhgiaCT() throws Exception {
        int databaseSizeBeforeUpdate = danhgiaCTRepository.findAll().size();

        // Create the DanhgiaCT
        DanhgiaCTDTO danhgiaCTDTO = danhgiaCTMapper.toDto(danhgiaCT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhgiaCTMockMvc.perform(put("/api/danhgia-cts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhgiaCTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DanhgiaCT in the database
        List<DanhgiaCT> danhgiaCTList = danhgiaCTRepository.findAll();
        assertThat(danhgiaCTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDanhgiaCT() throws Exception {
        // Initialize the database
        danhgiaCTRepository.saveAndFlush(danhgiaCT);

        int databaseSizeBeforeDelete = danhgiaCTRepository.findAll().size();

        // Delete the danhgiaCT
        restDanhgiaCTMockMvc.perform(delete("/api/danhgia-cts/{id}", danhgiaCT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DanhgiaCT> danhgiaCTList = danhgiaCTRepository.findAll();
        assertThat(danhgiaCTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
