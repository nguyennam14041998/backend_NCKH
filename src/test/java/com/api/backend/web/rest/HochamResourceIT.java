package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Hocham;
import com.api.backend.domain.Nhansu;
import com.api.backend.repository.HochamRepository;
import com.api.backend.service.HochamService;
import com.api.backend.service.dto.HochamDTO;
import com.api.backend.service.mapper.HochamMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.HochamCriteria;
import com.api.backend.service.HochamQueryService;

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
 * Integration tests for the {@link HochamResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class HochamResourceIT {

    private static final String DEFAULT_MAHOCHAM = "AAAAAAAAAA";
    private static final String UPDATED_MAHOCHAM = "BBBBBBBBBB";

    private static final String DEFAULT_TENHOCHAM = "AAAAAAAAAA";
    private static final String UPDATED_TENHOCHAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private HochamRepository hochamRepository;

    @Autowired
    private HochamMapper hochamMapper;

    @Autowired
    private HochamService hochamService;

    @Autowired
    private HochamQueryService hochamQueryService;

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

    private MockMvc restHochamMockMvc;

    private Hocham hocham;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HochamResource hochamResource = new HochamResource(hochamService, hochamQueryService);
        this.restHochamMockMvc = MockMvcBuilders.standaloneSetup(hochamResource)
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
    public static Hocham createEntity(EntityManager em) {
        Hocham hocham = new Hocham()
            .mahocham(DEFAULT_MAHOCHAM)
            .tenhocham(DEFAULT_TENHOCHAM)
            .sudung(DEFAULT_SUDUNG);
        return hocham;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hocham createUpdatedEntity(EntityManager em) {
        Hocham hocham = new Hocham()
            .mahocham(UPDATED_MAHOCHAM)
            .tenhocham(UPDATED_TENHOCHAM)
            .sudung(UPDATED_SUDUNG);
        return hocham;
    }

    @BeforeEach
    public void initTest() {
        hocham = createEntity(em);
    }

    @Test
    @Transactional
    public void createHocham() throws Exception {
        int databaseSizeBeforeCreate = hochamRepository.findAll().size();

        // Create the Hocham
        HochamDTO hochamDTO = hochamMapper.toDto(hocham);
        restHochamMockMvc.perform(post("/api/hochams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hochamDTO)))
            .andExpect(status().isCreated());

        // Validate the Hocham in the database
        List<Hocham> hochamList = hochamRepository.findAll();
        assertThat(hochamList).hasSize(databaseSizeBeforeCreate + 1);
        Hocham testHocham = hochamList.get(hochamList.size() - 1);
        assertThat(testHocham.getMahocham()).isEqualTo(DEFAULT_MAHOCHAM);
        assertThat(testHocham.getTenhocham()).isEqualTo(DEFAULT_TENHOCHAM);
        assertThat(testHocham.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createHochamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hochamRepository.findAll().size();

        // Create the Hocham with an existing ID
        hocham.setId(1L);
        HochamDTO hochamDTO = hochamMapper.toDto(hocham);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHochamMockMvc.perform(post("/api/hochams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hochamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hocham in the database
        List<Hocham> hochamList = hochamRepository.findAll();
        assertThat(hochamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHochams() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList
        restHochamMockMvc.perform(get("/api/hochams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hocham.getId().intValue())))
            .andExpect(jsonPath("$.[*].mahocham").value(hasItem(DEFAULT_MAHOCHAM)))
            .andExpect(jsonPath("$.[*].tenhocham").value(hasItem(DEFAULT_TENHOCHAM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getHocham() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get the hocham
        restHochamMockMvc.perform(get("/api/hochams/{id}", hocham.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hocham.getId().intValue()))
            .andExpect(jsonPath("$.mahocham").value(DEFAULT_MAHOCHAM))
            .andExpect(jsonPath("$.tenhocham").value(DEFAULT_TENHOCHAM))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getHochamsByIdFiltering() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        Long id = hocham.getId();

        defaultHochamShouldBeFound("id.equals=" + id);
        defaultHochamShouldNotBeFound("id.notEquals=" + id);

        defaultHochamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHochamShouldNotBeFound("id.greaterThan=" + id);

        defaultHochamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHochamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHochamsByMahochamIsEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where mahocham equals to DEFAULT_MAHOCHAM
        defaultHochamShouldBeFound("mahocham.equals=" + DEFAULT_MAHOCHAM);

        // Get all the hochamList where mahocham equals to UPDATED_MAHOCHAM
        defaultHochamShouldNotBeFound("mahocham.equals=" + UPDATED_MAHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByMahochamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where mahocham not equals to DEFAULT_MAHOCHAM
        defaultHochamShouldNotBeFound("mahocham.notEquals=" + DEFAULT_MAHOCHAM);

        // Get all the hochamList where mahocham not equals to UPDATED_MAHOCHAM
        defaultHochamShouldBeFound("mahocham.notEquals=" + UPDATED_MAHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByMahochamIsInShouldWork() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where mahocham in DEFAULT_MAHOCHAM or UPDATED_MAHOCHAM
        defaultHochamShouldBeFound("mahocham.in=" + DEFAULT_MAHOCHAM + "," + UPDATED_MAHOCHAM);

        // Get all the hochamList where mahocham equals to UPDATED_MAHOCHAM
        defaultHochamShouldNotBeFound("mahocham.in=" + UPDATED_MAHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByMahochamIsNullOrNotNull() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where mahocham is not null
        defaultHochamShouldBeFound("mahocham.specified=true");

        // Get all the hochamList where mahocham is null
        defaultHochamShouldNotBeFound("mahocham.specified=false");
    }
                @Test
    @Transactional
    public void getAllHochamsByMahochamContainsSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where mahocham contains DEFAULT_MAHOCHAM
        defaultHochamShouldBeFound("mahocham.contains=" + DEFAULT_MAHOCHAM);

        // Get all the hochamList where mahocham contains UPDATED_MAHOCHAM
        defaultHochamShouldNotBeFound("mahocham.contains=" + UPDATED_MAHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByMahochamNotContainsSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where mahocham does not contain DEFAULT_MAHOCHAM
        defaultHochamShouldNotBeFound("mahocham.doesNotContain=" + DEFAULT_MAHOCHAM);

        // Get all the hochamList where mahocham does not contain UPDATED_MAHOCHAM
        defaultHochamShouldBeFound("mahocham.doesNotContain=" + UPDATED_MAHOCHAM);
    }


    @Test
    @Transactional
    public void getAllHochamsByTenhochamIsEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where tenhocham equals to DEFAULT_TENHOCHAM
        defaultHochamShouldBeFound("tenhocham.equals=" + DEFAULT_TENHOCHAM);

        // Get all the hochamList where tenhocham equals to UPDATED_TENHOCHAM
        defaultHochamShouldNotBeFound("tenhocham.equals=" + UPDATED_TENHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByTenhochamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where tenhocham not equals to DEFAULT_TENHOCHAM
        defaultHochamShouldNotBeFound("tenhocham.notEquals=" + DEFAULT_TENHOCHAM);

        // Get all the hochamList where tenhocham not equals to UPDATED_TENHOCHAM
        defaultHochamShouldBeFound("tenhocham.notEquals=" + UPDATED_TENHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByTenhochamIsInShouldWork() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where tenhocham in DEFAULT_TENHOCHAM or UPDATED_TENHOCHAM
        defaultHochamShouldBeFound("tenhocham.in=" + DEFAULT_TENHOCHAM + "," + UPDATED_TENHOCHAM);

        // Get all the hochamList where tenhocham equals to UPDATED_TENHOCHAM
        defaultHochamShouldNotBeFound("tenhocham.in=" + UPDATED_TENHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByTenhochamIsNullOrNotNull() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where tenhocham is not null
        defaultHochamShouldBeFound("tenhocham.specified=true");

        // Get all the hochamList where tenhocham is null
        defaultHochamShouldNotBeFound("tenhocham.specified=false");
    }
                @Test
    @Transactional
    public void getAllHochamsByTenhochamContainsSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where tenhocham contains DEFAULT_TENHOCHAM
        defaultHochamShouldBeFound("tenhocham.contains=" + DEFAULT_TENHOCHAM);

        // Get all the hochamList where tenhocham contains UPDATED_TENHOCHAM
        defaultHochamShouldNotBeFound("tenhocham.contains=" + UPDATED_TENHOCHAM);
    }

    @Test
    @Transactional
    public void getAllHochamsByTenhochamNotContainsSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where tenhocham does not contain DEFAULT_TENHOCHAM
        defaultHochamShouldNotBeFound("tenhocham.doesNotContain=" + DEFAULT_TENHOCHAM);

        // Get all the hochamList where tenhocham does not contain UPDATED_TENHOCHAM
        defaultHochamShouldBeFound("tenhocham.doesNotContain=" + UPDATED_TENHOCHAM);
    }


    @Test
    @Transactional
    public void getAllHochamsBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung equals to DEFAULT_SUDUNG
        defaultHochamShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the hochamList where sudung equals to UPDATED_SUDUNG
        defaultHochamShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung not equals to DEFAULT_SUDUNG
        defaultHochamShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the hochamList where sudung not equals to UPDATED_SUDUNG
        defaultHochamShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultHochamShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the hochamList where sudung equals to UPDATED_SUDUNG
        defaultHochamShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung is not null
        defaultHochamShouldBeFound("sudung.specified=true");

        // Get all the hochamList where sudung is null
        defaultHochamShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultHochamShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the hochamList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultHochamShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultHochamShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the hochamList where sudung is less than or equal to SMALLER_SUDUNG
        defaultHochamShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung is less than DEFAULT_SUDUNG
        defaultHochamShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the hochamList where sudung is less than UPDATED_SUDUNG
        defaultHochamShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHochamsBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        // Get all the hochamList where sudung is greater than DEFAULT_SUDUNG
        defaultHochamShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the hochamList where sudung is greater than SMALLER_SUDUNG
        defaultHochamShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllHochamsByNhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);
        Nhansu nhansu = NhansuResourceIT.createEntity(em);
        em.persist(nhansu);
        em.flush();
        hocham.addNhansu(nhansu);
        hochamRepository.saveAndFlush(hocham);
        Long nhansuId = nhansu.getId();

        // Get all the hochamList where nhansu equals to nhansuId
        defaultHochamShouldBeFound("nhansuId.equals=" + nhansuId);

        // Get all the hochamList where nhansu equals to nhansuId + 1
        defaultHochamShouldNotBeFound("nhansuId.equals=" + (nhansuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHochamShouldBeFound(String filter) throws Exception {
        restHochamMockMvc.perform(get("/api/hochams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hocham.getId().intValue())))
            .andExpect(jsonPath("$.[*].mahocham").value(hasItem(DEFAULT_MAHOCHAM)))
            .andExpect(jsonPath("$.[*].tenhocham").value(hasItem(DEFAULT_TENHOCHAM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restHochamMockMvc.perform(get("/api/hochams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHochamShouldNotBeFound(String filter) throws Exception {
        restHochamMockMvc.perform(get("/api/hochams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHochamMockMvc.perform(get("/api/hochams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHocham() throws Exception {
        // Get the hocham
        restHochamMockMvc.perform(get("/api/hochams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHocham() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        int databaseSizeBeforeUpdate = hochamRepository.findAll().size();

        // Update the hocham
        Hocham updatedHocham = hochamRepository.findById(hocham.getId()).get();
        // Disconnect from session so that the updates on updatedHocham are not directly saved in db
        em.detach(updatedHocham);
        updatedHocham
            .mahocham(UPDATED_MAHOCHAM)
            .tenhocham(UPDATED_TENHOCHAM)
            .sudung(UPDATED_SUDUNG);
        HochamDTO hochamDTO = hochamMapper.toDto(updatedHocham);

        restHochamMockMvc.perform(put("/api/hochams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hochamDTO)))
            .andExpect(status().isOk());

        // Validate the Hocham in the database
        List<Hocham> hochamList = hochamRepository.findAll();
        assertThat(hochamList).hasSize(databaseSizeBeforeUpdate);
        Hocham testHocham = hochamList.get(hochamList.size() - 1);
        assertThat(testHocham.getMahocham()).isEqualTo(UPDATED_MAHOCHAM);
        assertThat(testHocham.getTenhocham()).isEqualTo(UPDATED_TENHOCHAM);
        assertThat(testHocham.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingHocham() throws Exception {
        int databaseSizeBeforeUpdate = hochamRepository.findAll().size();

        // Create the Hocham
        HochamDTO hochamDTO = hochamMapper.toDto(hocham);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHochamMockMvc.perform(put("/api/hochams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hochamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hocham in the database
        List<Hocham> hochamList = hochamRepository.findAll();
        assertThat(hochamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHocham() throws Exception {
        // Initialize the database
        hochamRepository.saveAndFlush(hocham);

        int databaseSizeBeforeDelete = hochamRepository.findAll().size();

        // Delete the hocham
        restHochamMockMvc.perform(delete("/api/hochams/{id}", hocham.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hocham> hochamList = hochamRepository.findAll();
        assertThat(hochamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
