package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Nhansuthamgia;
import com.api.backend.domain.Nhansu;
import com.api.backend.domain.Detai;
import com.api.backend.repository.NhansuthamgiaRepository;
import com.api.backend.service.NhansuthamgiaService;
import com.api.backend.service.dto.NhansuthamgiaDTO;
import com.api.backend.service.mapper.NhansuthamgiaMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.NhansuthamgiaCriteria;
import com.api.backend.service.NhansuthamgiaQueryService;

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
 * Integration tests for the {@link NhansuthamgiaResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class NhansuthamgiaResourceIT {

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private NhansuthamgiaRepository nhansuthamgiaRepository;

    @Autowired
    private NhansuthamgiaMapper nhansuthamgiaMapper;

    @Autowired
    private NhansuthamgiaService nhansuthamgiaService;

    @Autowired
    private NhansuthamgiaQueryService nhansuthamgiaQueryService;

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

    private MockMvc restNhansuthamgiaMockMvc;

    private Nhansuthamgia nhansuthamgia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhansuthamgiaResource nhansuthamgiaResource = new NhansuthamgiaResource(nhansuthamgiaService, nhansuthamgiaQueryService);
        this.restNhansuthamgiaMockMvc = MockMvcBuilders.standaloneSetup(nhansuthamgiaResource)
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
    public static Nhansuthamgia createEntity(EntityManager em) {
        Nhansuthamgia nhansuthamgia = new Nhansuthamgia()
            .sudung(DEFAULT_SUDUNG);
        return nhansuthamgia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nhansuthamgia createUpdatedEntity(EntityManager em) {
        Nhansuthamgia nhansuthamgia = new Nhansuthamgia()
            .sudung(UPDATED_SUDUNG);
        return nhansuthamgia;
    }

    @BeforeEach
    public void initTest() {
        nhansuthamgia = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhansuthamgia() throws Exception {
        int databaseSizeBeforeCreate = nhansuthamgiaRepository.findAll().size();

        // Create the Nhansuthamgia
        NhansuthamgiaDTO nhansuthamgiaDTO = nhansuthamgiaMapper.toDto(nhansuthamgia);
        restNhansuthamgiaMockMvc.perform(post("/api/nhansuthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuthamgiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Nhansuthamgia in the database
        List<Nhansuthamgia> nhansuthamgiaList = nhansuthamgiaRepository.findAll();
        assertThat(nhansuthamgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Nhansuthamgia testNhansuthamgia = nhansuthamgiaList.get(nhansuthamgiaList.size() - 1);
        assertThat(testNhansuthamgia.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createNhansuthamgiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhansuthamgiaRepository.findAll().size();

        // Create the Nhansuthamgia with an existing ID
        nhansuthamgia.setId(1L);
        NhansuthamgiaDTO nhansuthamgiaDTO = nhansuthamgiaMapper.toDto(nhansuthamgia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhansuthamgiaMockMvc.perform(post("/api/nhansuthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuthamgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nhansuthamgia in the database
        List<Nhansuthamgia> nhansuthamgiaList = nhansuthamgiaRepository.findAll();
        assertThat(nhansuthamgiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNhansuthamgias() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhansuthamgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getNhansuthamgia() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get the nhansuthamgia
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias/{id}", nhansuthamgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhansuthamgia.getId().intValue()))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getNhansuthamgiasByIdFiltering() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        Long id = nhansuthamgia.getId();

        defaultNhansuthamgiaShouldBeFound("id.equals=" + id);
        defaultNhansuthamgiaShouldNotBeFound("id.notEquals=" + id);

        defaultNhansuthamgiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNhansuthamgiaShouldNotBeFound("id.greaterThan=" + id);

        defaultNhansuthamgiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNhansuthamgiaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung equals to DEFAULT_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the nhansuthamgiaList where sudung equals to UPDATED_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung not equals to DEFAULT_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the nhansuthamgiaList where sudung not equals to UPDATED_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the nhansuthamgiaList where sudung equals to UPDATED_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung is not null
        defaultNhansuthamgiaShouldBeFound("sudung.specified=true");

        // Get all the nhansuthamgiaList where sudung is null
        defaultNhansuthamgiaShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the nhansuthamgiaList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the nhansuthamgiaList where sudung is less than or equal to SMALLER_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung is less than DEFAULT_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the nhansuthamgiaList where sudung is less than UPDATED_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNhansuthamgiasBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        // Get all the nhansuthamgiaList where sudung is greater than DEFAULT_SUDUNG
        defaultNhansuthamgiaShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the nhansuthamgiaList where sudung is greater than SMALLER_SUDUNG
        defaultNhansuthamgiaShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllNhansuthamgiasByNhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);
        Nhansu nhansu = NhansuResourceIT.createEntity(em);
        em.persist(nhansu);
        em.flush();
        nhansuthamgia.setNhansu(nhansu);
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);
        Long nhansuId = nhansu.getId();

        // Get all the nhansuthamgiaList where nhansu equals to nhansuId
        defaultNhansuthamgiaShouldBeFound("nhansuId.equals=" + nhansuId);

        // Get all the nhansuthamgiaList where nhansu equals to nhansuId + 1
        defaultNhansuthamgiaShouldNotBeFound("nhansuId.equals=" + (nhansuId + 1));
    }


    @Test
    @Transactional
    public void getAllNhansuthamgiasByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        nhansuthamgia.setDetai(detai);
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);
        Long detaiId = detai.getId();

        // Get all the nhansuthamgiaList where detai equals to detaiId
        defaultNhansuthamgiaShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the nhansuthamgiaList where detai equals to detaiId + 1
        defaultNhansuthamgiaShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNhansuthamgiaShouldBeFound(String filter) throws Exception {
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhansuthamgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNhansuthamgiaShouldNotBeFound(String filter) throws Exception {
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhansuthamgia() throws Exception {
        // Get the nhansuthamgia
        restNhansuthamgiaMockMvc.perform(get("/api/nhansuthamgias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhansuthamgia() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        int databaseSizeBeforeUpdate = nhansuthamgiaRepository.findAll().size();

        // Update the nhansuthamgia
        Nhansuthamgia updatedNhansuthamgia = nhansuthamgiaRepository.findById(nhansuthamgia.getId()).get();
        // Disconnect from session so that the updates on updatedNhansuthamgia are not directly saved in db
        em.detach(updatedNhansuthamgia);
        updatedNhansuthamgia
            .sudung(UPDATED_SUDUNG);
        NhansuthamgiaDTO nhansuthamgiaDTO = nhansuthamgiaMapper.toDto(updatedNhansuthamgia);

        restNhansuthamgiaMockMvc.perform(put("/api/nhansuthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuthamgiaDTO)))
            .andExpect(status().isOk());

        // Validate the Nhansuthamgia in the database
        List<Nhansuthamgia> nhansuthamgiaList = nhansuthamgiaRepository.findAll();
        assertThat(nhansuthamgiaList).hasSize(databaseSizeBeforeUpdate);
        Nhansuthamgia testNhansuthamgia = nhansuthamgiaList.get(nhansuthamgiaList.size() - 1);
        assertThat(testNhansuthamgia.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingNhansuthamgia() throws Exception {
        int databaseSizeBeforeUpdate = nhansuthamgiaRepository.findAll().size();

        // Create the Nhansuthamgia
        NhansuthamgiaDTO nhansuthamgiaDTO = nhansuthamgiaMapper.toDto(nhansuthamgia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhansuthamgiaMockMvc.perform(put("/api/nhansuthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhansuthamgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nhansuthamgia in the database
        List<Nhansuthamgia> nhansuthamgiaList = nhansuthamgiaRepository.findAll();
        assertThat(nhansuthamgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhansuthamgia() throws Exception {
        // Initialize the database
        nhansuthamgiaRepository.saveAndFlush(nhansuthamgia);

        int databaseSizeBeforeDelete = nhansuthamgiaRepository.findAll().size();

        // Delete the nhansuthamgia
        restNhansuthamgiaMockMvc.perform(delete("/api/nhansuthamgias/{id}", nhansuthamgia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nhansuthamgia> nhansuthamgiaList = nhansuthamgiaRepository.findAll();
        assertThat(nhansuthamgiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
