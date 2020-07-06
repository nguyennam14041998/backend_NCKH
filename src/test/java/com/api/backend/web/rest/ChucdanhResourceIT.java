package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Chucdanh;
import com.api.backend.domain.Nhansu;
import com.api.backend.repository.ChucdanhRepository;
import com.api.backend.service.ChucdanhService;
import com.api.backend.service.dto.ChucdanhDTO;
import com.api.backend.service.mapper.ChucdanhMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.ChucdanhCriteria;
import com.api.backend.service.ChucdanhQueryService;

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
 * Integration tests for the {@link ChucdanhResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class ChucdanhResourceIT {

    private static final String DEFAULT_MACHUCDANH = "AAAAAAAAAA";
    private static final String UPDATED_MACHUCDANH = "BBBBBBBBBB";

    private static final String DEFAULT_TENCHUCDANH = "AAAAAAAAAA";
    private static final String UPDATED_TENCHUCDANH = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private ChucdanhRepository chucdanhRepository;

    @Autowired
    private ChucdanhMapper chucdanhMapper;

    @Autowired
    private ChucdanhService chucdanhService;

    @Autowired
    private ChucdanhQueryService chucdanhQueryService;

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

    private MockMvc restChucdanhMockMvc;

    private Chucdanh chucdanh;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChucdanhResource chucdanhResource = new ChucdanhResource(chucdanhService, chucdanhQueryService);
        this.restChucdanhMockMvc = MockMvcBuilders.standaloneSetup(chucdanhResource)
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
    public static Chucdanh createEntity(EntityManager em) {
        Chucdanh chucdanh = new Chucdanh()
            .machucdanh(DEFAULT_MACHUCDANH)
            .tenchucdanh(DEFAULT_TENCHUCDANH)
            .sudung(DEFAULT_SUDUNG);
        return chucdanh;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chucdanh createUpdatedEntity(EntityManager em) {
        Chucdanh chucdanh = new Chucdanh()
            .machucdanh(UPDATED_MACHUCDANH)
            .tenchucdanh(UPDATED_TENCHUCDANH)
            .sudung(UPDATED_SUDUNG);
        return chucdanh;
    }

    @BeforeEach
    public void initTest() {
        chucdanh = createEntity(em);
    }

    @Test
    @Transactional
    public void createChucdanh() throws Exception {
        int databaseSizeBeforeCreate = chucdanhRepository.findAll().size();

        // Create the Chucdanh
        ChucdanhDTO chucdanhDTO = chucdanhMapper.toDto(chucdanh);
        restChucdanhMockMvc.perform(post("/api/chucdanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chucdanhDTO)))
            .andExpect(status().isCreated());

        // Validate the Chucdanh in the database
        List<Chucdanh> chucdanhList = chucdanhRepository.findAll();
        assertThat(chucdanhList).hasSize(databaseSizeBeforeCreate + 1);
        Chucdanh testChucdanh = chucdanhList.get(chucdanhList.size() - 1);
        assertThat(testChucdanh.getMachucdanh()).isEqualTo(DEFAULT_MACHUCDANH);
        assertThat(testChucdanh.getTenchucdanh()).isEqualTo(DEFAULT_TENCHUCDANH);
        assertThat(testChucdanh.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createChucdanhWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chucdanhRepository.findAll().size();

        // Create the Chucdanh with an existing ID
        chucdanh.setId(1L);
        ChucdanhDTO chucdanhDTO = chucdanhMapper.toDto(chucdanh);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChucdanhMockMvc.perform(post("/api/chucdanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chucdanhDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chucdanh in the database
        List<Chucdanh> chucdanhList = chucdanhRepository.findAll();
        assertThat(chucdanhList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChucdanhs() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList
        restChucdanhMockMvc.perform(get("/api/chucdanhs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chucdanh.getId().intValue())))
            .andExpect(jsonPath("$.[*].machucdanh").value(hasItem(DEFAULT_MACHUCDANH)))
            .andExpect(jsonPath("$.[*].tenchucdanh").value(hasItem(DEFAULT_TENCHUCDANH)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getChucdanh() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get the chucdanh
        restChucdanhMockMvc.perform(get("/api/chucdanhs/{id}", chucdanh.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chucdanh.getId().intValue()))
            .andExpect(jsonPath("$.machucdanh").value(DEFAULT_MACHUCDANH))
            .andExpect(jsonPath("$.tenchucdanh").value(DEFAULT_TENCHUCDANH))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getChucdanhsByIdFiltering() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        Long id = chucdanh.getId();

        defaultChucdanhShouldBeFound("id.equals=" + id);
        defaultChucdanhShouldNotBeFound("id.notEquals=" + id);

        defaultChucdanhShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChucdanhShouldNotBeFound("id.greaterThan=" + id);

        defaultChucdanhShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChucdanhShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChucdanhsByMachucdanhIsEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where machucdanh equals to DEFAULT_MACHUCDANH
        defaultChucdanhShouldBeFound("machucdanh.equals=" + DEFAULT_MACHUCDANH);

        // Get all the chucdanhList where machucdanh equals to UPDATED_MACHUCDANH
        defaultChucdanhShouldNotBeFound("machucdanh.equals=" + UPDATED_MACHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByMachucdanhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where machucdanh not equals to DEFAULT_MACHUCDANH
        defaultChucdanhShouldNotBeFound("machucdanh.notEquals=" + DEFAULT_MACHUCDANH);

        // Get all the chucdanhList where machucdanh not equals to UPDATED_MACHUCDANH
        defaultChucdanhShouldBeFound("machucdanh.notEquals=" + UPDATED_MACHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByMachucdanhIsInShouldWork() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where machucdanh in DEFAULT_MACHUCDANH or UPDATED_MACHUCDANH
        defaultChucdanhShouldBeFound("machucdanh.in=" + DEFAULT_MACHUCDANH + "," + UPDATED_MACHUCDANH);

        // Get all the chucdanhList where machucdanh equals to UPDATED_MACHUCDANH
        defaultChucdanhShouldNotBeFound("machucdanh.in=" + UPDATED_MACHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByMachucdanhIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where machucdanh is not null
        defaultChucdanhShouldBeFound("machucdanh.specified=true");

        // Get all the chucdanhList where machucdanh is null
        defaultChucdanhShouldNotBeFound("machucdanh.specified=false");
    }
                @Test
    @Transactional
    public void getAllChucdanhsByMachucdanhContainsSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where machucdanh contains DEFAULT_MACHUCDANH
        defaultChucdanhShouldBeFound("machucdanh.contains=" + DEFAULT_MACHUCDANH);

        // Get all the chucdanhList where machucdanh contains UPDATED_MACHUCDANH
        defaultChucdanhShouldNotBeFound("machucdanh.contains=" + UPDATED_MACHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByMachucdanhNotContainsSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where machucdanh does not contain DEFAULT_MACHUCDANH
        defaultChucdanhShouldNotBeFound("machucdanh.doesNotContain=" + DEFAULT_MACHUCDANH);

        // Get all the chucdanhList where machucdanh does not contain UPDATED_MACHUCDANH
        defaultChucdanhShouldBeFound("machucdanh.doesNotContain=" + UPDATED_MACHUCDANH);
    }


    @Test
    @Transactional
    public void getAllChucdanhsByTenchucdanhIsEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where tenchucdanh equals to DEFAULT_TENCHUCDANH
        defaultChucdanhShouldBeFound("tenchucdanh.equals=" + DEFAULT_TENCHUCDANH);

        // Get all the chucdanhList where tenchucdanh equals to UPDATED_TENCHUCDANH
        defaultChucdanhShouldNotBeFound("tenchucdanh.equals=" + UPDATED_TENCHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByTenchucdanhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where tenchucdanh not equals to DEFAULT_TENCHUCDANH
        defaultChucdanhShouldNotBeFound("tenchucdanh.notEquals=" + DEFAULT_TENCHUCDANH);

        // Get all the chucdanhList where tenchucdanh not equals to UPDATED_TENCHUCDANH
        defaultChucdanhShouldBeFound("tenchucdanh.notEquals=" + UPDATED_TENCHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByTenchucdanhIsInShouldWork() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where tenchucdanh in DEFAULT_TENCHUCDANH or UPDATED_TENCHUCDANH
        defaultChucdanhShouldBeFound("tenchucdanh.in=" + DEFAULT_TENCHUCDANH + "," + UPDATED_TENCHUCDANH);

        // Get all the chucdanhList where tenchucdanh equals to UPDATED_TENCHUCDANH
        defaultChucdanhShouldNotBeFound("tenchucdanh.in=" + UPDATED_TENCHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByTenchucdanhIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where tenchucdanh is not null
        defaultChucdanhShouldBeFound("tenchucdanh.specified=true");

        // Get all the chucdanhList where tenchucdanh is null
        defaultChucdanhShouldNotBeFound("tenchucdanh.specified=false");
    }
                @Test
    @Transactional
    public void getAllChucdanhsByTenchucdanhContainsSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where tenchucdanh contains DEFAULT_TENCHUCDANH
        defaultChucdanhShouldBeFound("tenchucdanh.contains=" + DEFAULT_TENCHUCDANH);

        // Get all the chucdanhList where tenchucdanh contains UPDATED_TENCHUCDANH
        defaultChucdanhShouldNotBeFound("tenchucdanh.contains=" + UPDATED_TENCHUCDANH);
    }

    @Test
    @Transactional
    public void getAllChucdanhsByTenchucdanhNotContainsSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where tenchucdanh does not contain DEFAULT_TENCHUCDANH
        defaultChucdanhShouldNotBeFound("tenchucdanh.doesNotContain=" + DEFAULT_TENCHUCDANH);

        // Get all the chucdanhList where tenchucdanh does not contain UPDATED_TENCHUCDANH
        defaultChucdanhShouldBeFound("tenchucdanh.doesNotContain=" + UPDATED_TENCHUCDANH);
    }


    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung equals to DEFAULT_SUDUNG
        defaultChucdanhShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the chucdanhList where sudung equals to UPDATED_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung not equals to DEFAULT_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the chucdanhList where sudung not equals to UPDATED_SUDUNG
        defaultChucdanhShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultChucdanhShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the chucdanhList where sudung equals to UPDATED_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung is not null
        defaultChucdanhShouldBeFound("sudung.specified=true");

        // Get all the chucdanhList where sudung is null
        defaultChucdanhShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultChucdanhShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the chucdanhList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultChucdanhShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the chucdanhList where sudung is less than or equal to SMALLER_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung is less than DEFAULT_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the chucdanhList where sudung is less than UPDATED_SUDUNG
        defaultChucdanhShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChucdanhsBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        // Get all the chucdanhList where sudung is greater than DEFAULT_SUDUNG
        defaultChucdanhShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the chucdanhList where sudung is greater than SMALLER_SUDUNG
        defaultChucdanhShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllChucdanhsByNhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);
        Nhansu nhansu = NhansuResourceIT.createEntity(em);
        em.persist(nhansu);
        em.flush();
        chucdanh.addNhansu(nhansu);
        chucdanhRepository.saveAndFlush(chucdanh);
        Long nhansuId = nhansu.getId();

        // Get all the chucdanhList where nhansu equals to nhansuId
        defaultChucdanhShouldBeFound("nhansuId.equals=" + nhansuId);

        // Get all the chucdanhList where nhansu equals to nhansuId + 1
        defaultChucdanhShouldNotBeFound("nhansuId.equals=" + (nhansuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChucdanhShouldBeFound(String filter) throws Exception {
        restChucdanhMockMvc.perform(get("/api/chucdanhs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chucdanh.getId().intValue())))
            .andExpect(jsonPath("$.[*].machucdanh").value(hasItem(DEFAULT_MACHUCDANH)))
            .andExpect(jsonPath("$.[*].tenchucdanh").value(hasItem(DEFAULT_TENCHUCDANH)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restChucdanhMockMvc.perform(get("/api/chucdanhs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChucdanhShouldNotBeFound(String filter) throws Exception {
        restChucdanhMockMvc.perform(get("/api/chucdanhs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChucdanhMockMvc.perform(get("/api/chucdanhs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChucdanh() throws Exception {
        // Get the chucdanh
        restChucdanhMockMvc.perform(get("/api/chucdanhs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChucdanh() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        int databaseSizeBeforeUpdate = chucdanhRepository.findAll().size();

        // Update the chucdanh
        Chucdanh updatedChucdanh = chucdanhRepository.findById(chucdanh.getId()).get();
        // Disconnect from session so that the updates on updatedChucdanh are not directly saved in db
        em.detach(updatedChucdanh);
        updatedChucdanh
            .machucdanh(UPDATED_MACHUCDANH)
            .tenchucdanh(UPDATED_TENCHUCDANH)
            .sudung(UPDATED_SUDUNG);
        ChucdanhDTO chucdanhDTO = chucdanhMapper.toDto(updatedChucdanh);

        restChucdanhMockMvc.perform(put("/api/chucdanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chucdanhDTO)))
            .andExpect(status().isOk());

        // Validate the Chucdanh in the database
        List<Chucdanh> chucdanhList = chucdanhRepository.findAll();
        assertThat(chucdanhList).hasSize(databaseSizeBeforeUpdate);
        Chucdanh testChucdanh = chucdanhList.get(chucdanhList.size() - 1);
        assertThat(testChucdanh.getMachucdanh()).isEqualTo(UPDATED_MACHUCDANH);
        assertThat(testChucdanh.getTenchucdanh()).isEqualTo(UPDATED_TENCHUCDANH);
        assertThat(testChucdanh.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingChucdanh() throws Exception {
        int databaseSizeBeforeUpdate = chucdanhRepository.findAll().size();

        // Create the Chucdanh
        ChucdanhDTO chucdanhDTO = chucdanhMapper.toDto(chucdanh);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChucdanhMockMvc.perform(put("/api/chucdanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chucdanhDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chucdanh in the database
        List<Chucdanh> chucdanhList = chucdanhRepository.findAll();
        assertThat(chucdanhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChucdanh() throws Exception {
        // Initialize the database
        chucdanhRepository.saveAndFlush(chucdanh);

        int databaseSizeBeforeDelete = chucdanhRepository.findAll().size();

        // Delete the chucdanh
        restChucdanhMockMvc.perform(delete("/api/chucdanhs/{id}", chucdanh.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chucdanh> chucdanhList = chucdanhRepository.findAll();
        assertThat(chucdanhList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
