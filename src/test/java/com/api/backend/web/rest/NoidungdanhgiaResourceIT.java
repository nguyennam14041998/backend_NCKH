package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Noidungdanhgia;
import com.api.backend.domain.DanhgiaCT;
import com.api.backend.repository.NoidungdanhgiaRepository;
import com.api.backend.service.NoidungdanhgiaService;
import com.api.backend.service.dto.NoidungdanhgiaDTO;
import com.api.backend.service.mapper.NoidungdanhgiaMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.NoidungdanhgiaCriteria;
import com.api.backend.service.NoidungdanhgiaQueryService;

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
 * Integration tests for the {@link NoidungdanhgiaResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class NoidungdanhgiaResourceIT {

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private NoidungdanhgiaRepository noidungdanhgiaRepository;

    @Autowired
    private NoidungdanhgiaMapper noidungdanhgiaMapper;

    @Autowired
    private NoidungdanhgiaService noidungdanhgiaService;

    @Autowired
    private NoidungdanhgiaQueryService noidungdanhgiaQueryService;

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

    private MockMvc restNoidungdanhgiaMockMvc;

    private Noidungdanhgia noidungdanhgia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NoidungdanhgiaResource noidungdanhgiaResource = new NoidungdanhgiaResource(noidungdanhgiaService, noidungdanhgiaQueryService);
        this.restNoidungdanhgiaMockMvc = MockMvcBuilders.standaloneSetup(noidungdanhgiaResource)
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
    public static Noidungdanhgia createEntity(EntityManager em) {
        Noidungdanhgia noidungdanhgia = new Noidungdanhgia()
            .noidung(DEFAULT_NOIDUNG)
            .sudung(DEFAULT_SUDUNG);
        return noidungdanhgia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noidungdanhgia createUpdatedEntity(EntityManager em) {
        Noidungdanhgia noidungdanhgia = new Noidungdanhgia()
            .noidung(UPDATED_NOIDUNG)
            .sudung(UPDATED_SUDUNG);
        return noidungdanhgia;
    }

    @BeforeEach
    public void initTest() {
        noidungdanhgia = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoidungdanhgia() throws Exception {
        int databaseSizeBeforeCreate = noidungdanhgiaRepository.findAll().size();

        // Create the Noidungdanhgia
        NoidungdanhgiaDTO noidungdanhgiaDTO = noidungdanhgiaMapper.toDto(noidungdanhgia);
        restNoidungdanhgiaMockMvc.perform(post("/api/noidungdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungdanhgiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Noidungdanhgia in the database
        List<Noidungdanhgia> noidungdanhgiaList = noidungdanhgiaRepository.findAll();
        assertThat(noidungdanhgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Noidungdanhgia testNoidungdanhgia = noidungdanhgiaList.get(noidungdanhgiaList.size() - 1);
        assertThat(testNoidungdanhgia.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testNoidungdanhgia.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createNoidungdanhgiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noidungdanhgiaRepository.findAll().size();

        // Create the Noidungdanhgia with an existing ID
        noidungdanhgia.setId(1L);
        NoidungdanhgiaDTO noidungdanhgiaDTO = noidungdanhgiaMapper.toDto(noidungdanhgia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoidungdanhgiaMockMvc.perform(post("/api/noidungdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungdanhgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noidungdanhgia in the database
        List<Noidungdanhgia> noidungdanhgiaList = noidungdanhgiaRepository.findAll();
        assertThat(noidungdanhgiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNoidungdanhgias() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noidungdanhgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getNoidungdanhgia() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get the noidungdanhgia
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias/{id}", noidungdanhgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noidungdanhgia.getId().intValue()))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getNoidungdanhgiasByIdFiltering() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        Long id = noidungdanhgia.getId();

        defaultNoidungdanhgiaShouldBeFound("id.equals=" + id);
        defaultNoidungdanhgiaShouldNotBeFound("id.notEquals=" + id);

        defaultNoidungdanhgiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoidungdanhgiaShouldNotBeFound("id.greaterThan=" + id);

        defaultNoidungdanhgiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoidungdanhgiaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNoidungdanhgiasByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where noidung equals to DEFAULT_NOIDUNG
        defaultNoidungdanhgiaShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the noidungdanhgiaList where noidung equals to UPDATED_NOIDUNG
        defaultNoidungdanhgiaShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where noidung not equals to DEFAULT_NOIDUNG
        defaultNoidungdanhgiaShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the noidungdanhgiaList where noidung not equals to UPDATED_NOIDUNG
        defaultNoidungdanhgiaShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultNoidungdanhgiaShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the noidungdanhgiaList where noidung equals to UPDATED_NOIDUNG
        defaultNoidungdanhgiaShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where noidung is not null
        defaultNoidungdanhgiaShouldBeFound("noidung.specified=true");

        // Get all the noidungdanhgiaList where noidung is null
        defaultNoidungdanhgiaShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoidungdanhgiasByNoidungContainsSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where noidung contains DEFAULT_NOIDUNG
        defaultNoidungdanhgiaShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the noidungdanhgiaList where noidung contains UPDATED_NOIDUNG
        defaultNoidungdanhgiaShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where noidung does not contain DEFAULT_NOIDUNG
        defaultNoidungdanhgiaShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the noidungdanhgiaList where noidung does not contain UPDATED_NOIDUNG
        defaultNoidungdanhgiaShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung equals to DEFAULT_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the noidungdanhgiaList where sudung equals to UPDATED_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung not equals to DEFAULT_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the noidungdanhgiaList where sudung not equals to UPDATED_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the noidungdanhgiaList where sudung equals to UPDATED_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung is not null
        defaultNoidungdanhgiaShouldBeFound("sudung.specified=true");

        // Get all the noidungdanhgiaList where sudung is null
        defaultNoidungdanhgiaShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the noidungdanhgiaList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the noidungdanhgiaList where sudung is less than or equal to SMALLER_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung is less than DEFAULT_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the noidungdanhgiaList where sudung is less than UPDATED_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungdanhgiasBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        // Get all the noidungdanhgiaList where sudung is greater than DEFAULT_SUDUNG
        defaultNoidungdanhgiaShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the noidungdanhgiaList where sudung is greater than SMALLER_SUDUNG
        defaultNoidungdanhgiaShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllNoidungdanhgiasByDanhgiaCTIsEqualToSomething() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);
        DanhgiaCT danhgiaCT = DanhgiaCTResourceIT.createEntity(em);
        em.persist(danhgiaCT);
        em.flush();
        noidungdanhgia.addDanhgiaCT(danhgiaCT);
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);
        Long danhgiaCTId = danhgiaCT.getId();

        // Get all the noidungdanhgiaList where danhgiaCT equals to danhgiaCTId
        defaultNoidungdanhgiaShouldBeFound("danhgiaCTId.equals=" + danhgiaCTId);

        // Get all the noidungdanhgiaList where danhgiaCT equals to danhgiaCTId + 1
        defaultNoidungdanhgiaShouldNotBeFound("danhgiaCTId.equals=" + (danhgiaCTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoidungdanhgiaShouldBeFound(String filter) throws Exception {
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noidungdanhgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoidungdanhgiaShouldNotBeFound(String filter) throws Exception {
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNoidungdanhgia() throws Exception {
        // Get the noidungdanhgia
        restNoidungdanhgiaMockMvc.perform(get("/api/noidungdanhgias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoidungdanhgia() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        int databaseSizeBeforeUpdate = noidungdanhgiaRepository.findAll().size();

        // Update the noidungdanhgia
        Noidungdanhgia updatedNoidungdanhgia = noidungdanhgiaRepository.findById(noidungdanhgia.getId()).get();
        // Disconnect from session so that the updates on updatedNoidungdanhgia are not directly saved in db
        em.detach(updatedNoidungdanhgia);
        updatedNoidungdanhgia
            .noidung(UPDATED_NOIDUNG)
            .sudung(UPDATED_SUDUNG);
        NoidungdanhgiaDTO noidungdanhgiaDTO = noidungdanhgiaMapper.toDto(updatedNoidungdanhgia);

        restNoidungdanhgiaMockMvc.perform(put("/api/noidungdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungdanhgiaDTO)))
            .andExpect(status().isOk());

        // Validate the Noidungdanhgia in the database
        List<Noidungdanhgia> noidungdanhgiaList = noidungdanhgiaRepository.findAll();
        assertThat(noidungdanhgiaList).hasSize(databaseSizeBeforeUpdate);
        Noidungdanhgia testNoidungdanhgia = noidungdanhgiaList.get(noidungdanhgiaList.size() - 1);
        assertThat(testNoidungdanhgia.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testNoidungdanhgia.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingNoidungdanhgia() throws Exception {
        int databaseSizeBeforeUpdate = noidungdanhgiaRepository.findAll().size();

        // Create the Noidungdanhgia
        NoidungdanhgiaDTO noidungdanhgiaDTO = noidungdanhgiaMapper.toDto(noidungdanhgia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoidungdanhgiaMockMvc.perform(put("/api/noidungdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungdanhgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noidungdanhgia in the database
        List<Noidungdanhgia> noidungdanhgiaList = noidungdanhgiaRepository.findAll();
        assertThat(noidungdanhgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNoidungdanhgia() throws Exception {
        // Initialize the database
        noidungdanhgiaRepository.saveAndFlush(noidungdanhgia);

        int databaseSizeBeforeDelete = noidungdanhgiaRepository.findAll().size();

        // Delete the noidungdanhgia
        restNoidungdanhgiaMockMvc.perform(delete("/api/noidungdanhgias/{id}", noidungdanhgia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Noidungdanhgia> noidungdanhgiaList = noidungdanhgiaRepository.findAll();
        assertThat(noidungdanhgiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
