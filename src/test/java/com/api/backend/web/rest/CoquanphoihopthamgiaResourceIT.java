package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Coquanphoihopthamgia;
import com.api.backend.domain.Detai;
import com.api.backend.domain.Coquanphoihop;
import com.api.backend.repository.CoquanphoihopthamgiaRepository;
import com.api.backend.service.CoquanphoihopthamgiaService;
import com.api.backend.service.dto.CoquanphoihopthamgiaDTO;
import com.api.backend.service.mapper.CoquanphoihopthamgiaMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.CoquanphoihopthamgiaCriteria;
import com.api.backend.service.CoquanphoihopthamgiaQueryService;

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
 * Integration tests for the {@link CoquanphoihopthamgiaResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class CoquanphoihopthamgiaResourceIT {

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private CoquanphoihopthamgiaRepository coquanphoihopthamgiaRepository;

    @Autowired
    private CoquanphoihopthamgiaMapper coquanphoihopthamgiaMapper;

    @Autowired
    private CoquanphoihopthamgiaService coquanphoihopthamgiaService;

    @Autowired
    private CoquanphoihopthamgiaQueryService coquanphoihopthamgiaQueryService;

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

    private MockMvc restCoquanphoihopthamgiaMockMvc;

    private Coquanphoihopthamgia coquanphoihopthamgia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoquanphoihopthamgiaResource coquanphoihopthamgiaResource = new CoquanphoihopthamgiaResource(coquanphoihopthamgiaService, coquanphoihopthamgiaQueryService);
        this.restCoquanphoihopthamgiaMockMvc = MockMvcBuilders.standaloneSetup(coquanphoihopthamgiaResource)
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
    public static Coquanphoihopthamgia createEntity(EntityManager em) {
        Coquanphoihopthamgia coquanphoihopthamgia = new Coquanphoihopthamgia()
            .sudung(DEFAULT_SUDUNG);
        return coquanphoihopthamgia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coquanphoihopthamgia createUpdatedEntity(EntityManager em) {
        Coquanphoihopthamgia coquanphoihopthamgia = new Coquanphoihopthamgia()
            .sudung(UPDATED_SUDUNG);
        return coquanphoihopthamgia;
    }

    @BeforeEach
    public void initTest() {
        coquanphoihopthamgia = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoquanphoihopthamgia() throws Exception {
        int databaseSizeBeforeCreate = coquanphoihopthamgiaRepository.findAll().size();

        // Create the Coquanphoihopthamgia
        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO = coquanphoihopthamgiaMapper.toDto(coquanphoihopthamgia);
        restCoquanphoihopthamgiaMockMvc.perform(post("/api/coquanphoihopthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopthamgiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Coquanphoihopthamgia in the database
        List<Coquanphoihopthamgia> coquanphoihopthamgiaList = coquanphoihopthamgiaRepository.findAll();
        assertThat(coquanphoihopthamgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Coquanphoihopthamgia testCoquanphoihopthamgia = coquanphoihopthamgiaList.get(coquanphoihopthamgiaList.size() - 1);
        assertThat(testCoquanphoihopthamgia.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createCoquanphoihopthamgiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coquanphoihopthamgiaRepository.findAll().size();

        // Create the Coquanphoihopthamgia with an existing ID
        coquanphoihopthamgia.setId(1L);
        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO = coquanphoihopthamgiaMapper.toDto(coquanphoihopthamgia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoquanphoihopthamgiaMockMvc.perform(post("/api/coquanphoihopthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopthamgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coquanphoihopthamgia in the database
        List<Coquanphoihopthamgia> coquanphoihopthamgiaList = coquanphoihopthamgiaRepository.findAll();
        assertThat(coquanphoihopthamgiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopthamgias() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coquanphoihopthamgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getCoquanphoihopthamgia() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get the coquanphoihopthamgia
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias/{id}", coquanphoihopthamgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coquanphoihopthamgia.getId().intValue()))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getCoquanphoihopthamgiasByIdFiltering() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        Long id = coquanphoihopthamgia.getId();

        defaultCoquanphoihopthamgiaShouldBeFound("id.equals=" + id);
        defaultCoquanphoihopthamgiaShouldNotBeFound("id.notEquals=" + id);

        defaultCoquanphoihopthamgiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoquanphoihopthamgiaShouldNotBeFound("id.greaterThan=" + id);

        defaultCoquanphoihopthamgiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoquanphoihopthamgiaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung equals to DEFAULT_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung equals to UPDATED_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung not equals to DEFAULT_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung not equals to UPDATED_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung equals to UPDATED_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung is not null
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.specified=true");

        // Get all the coquanphoihopthamgiaList where sudung is null
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung is less than or equal to SMALLER_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung is less than DEFAULT_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung is less than UPDATED_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        // Get all the coquanphoihopthamgiaList where sudung is greater than DEFAULT_SUDUNG
        defaultCoquanphoihopthamgiaShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the coquanphoihopthamgiaList where sudung is greater than SMALLER_SUDUNG
        defaultCoquanphoihopthamgiaShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        coquanphoihopthamgia.setDetai(detai);
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);
        Long detaiId = detai.getId();

        // Get all the coquanphoihopthamgiaList where detai equals to detaiId
        defaultCoquanphoihopthamgiaShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the coquanphoihopthamgiaList where detai equals to detaiId + 1
        defaultCoquanphoihopthamgiaShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }


    @Test
    @Transactional
    public void getAllCoquanphoihopthamgiasByCoquanphoihopIsEqualToSomething() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);
        Coquanphoihop coquanphoihop = CoquanphoihopResourceIT.createEntity(em);
        em.persist(coquanphoihop);
        em.flush();
        coquanphoihopthamgia.setCoquanphoihop(coquanphoihop);
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);
        Long coquanphoihopId = coquanphoihop.getId();

        // Get all the coquanphoihopthamgiaList where coquanphoihop equals to coquanphoihopId
        defaultCoquanphoihopthamgiaShouldBeFound("coquanphoihopId.equals=" + coquanphoihopId);

        // Get all the coquanphoihopthamgiaList where coquanphoihop equals to coquanphoihopId + 1
        defaultCoquanphoihopthamgiaShouldNotBeFound("coquanphoihopId.equals=" + (coquanphoihopId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoquanphoihopthamgiaShouldBeFound(String filter) throws Exception {
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coquanphoihopthamgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoquanphoihopthamgiaShouldNotBeFound(String filter) throws Exception {
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCoquanphoihopthamgia() throws Exception {
        // Get the coquanphoihopthamgia
        restCoquanphoihopthamgiaMockMvc.perform(get("/api/coquanphoihopthamgias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoquanphoihopthamgia() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        int databaseSizeBeforeUpdate = coquanphoihopthamgiaRepository.findAll().size();

        // Update the coquanphoihopthamgia
        Coquanphoihopthamgia updatedCoquanphoihopthamgia = coquanphoihopthamgiaRepository.findById(coquanphoihopthamgia.getId()).get();
        // Disconnect from session so that the updates on updatedCoquanphoihopthamgia are not directly saved in db
        em.detach(updatedCoquanphoihopthamgia);
        updatedCoquanphoihopthamgia
            .sudung(UPDATED_SUDUNG);
        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO = coquanphoihopthamgiaMapper.toDto(updatedCoquanphoihopthamgia);

        restCoquanphoihopthamgiaMockMvc.perform(put("/api/coquanphoihopthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopthamgiaDTO)))
            .andExpect(status().isOk());

        // Validate the Coquanphoihopthamgia in the database
        List<Coquanphoihopthamgia> coquanphoihopthamgiaList = coquanphoihopthamgiaRepository.findAll();
        assertThat(coquanphoihopthamgiaList).hasSize(databaseSizeBeforeUpdate);
        Coquanphoihopthamgia testCoquanphoihopthamgia = coquanphoihopthamgiaList.get(coquanphoihopthamgiaList.size() - 1);
        assertThat(testCoquanphoihopthamgia.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingCoquanphoihopthamgia() throws Exception {
        int databaseSizeBeforeUpdate = coquanphoihopthamgiaRepository.findAll().size();

        // Create the Coquanphoihopthamgia
        CoquanphoihopthamgiaDTO coquanphoihopthamgiaDTO = coquanphoihopthamgiaMapper.toDto(coquanphoihopthamgia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoquanphoihopthamgiaMockMvc.perform(put("/api/coquanphoihopthamgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coquanphoihopthamgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coquanphoihopthamgia in the database
        List<Coquanphoihopthamgia> coquanphoihopthamgiaList = coquanphoihopthamgiaRepository.findAll();
        assertThat(coquanphoihopthamgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoquanphoihopthamgia() throws Exception {
        // Initialize the database
        coquanphoihopthamgiaRepository.saveAndFlush(coquanphoihopthamgia);

        int databaseSizeBeforeDelete = coquanphoihopthamgiaRepository.findAll().size();

        // Delete the coquanphoihopthamgia
        restCoquanphoihopthamgiaMockMvc.perform(delete("/api/coquanphoihopthamgias/{id}", coquanphoihopthamgia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coquanphoihopthamgia> coquanphoihopthamgiaList = coquanphoihopthamgiaRepository.findAll();
        assertThat(coquanphoihopthamgiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
