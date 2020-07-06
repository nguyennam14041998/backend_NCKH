package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Chunhiem;
import com.api.backend.domain.Detai;
import com.api.backend.domain.Nhansu;
import com.api.backend.repository.ChunhiemRepository;
import com.api.backend.service.ChunhiemService;
import com.api.backend.service.dto.ChunhiemDTO;
import com.api.backend.service.mapper.ChunhiemMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.ChunhiemCriteria;
import com.api.backend.service.ChunhiemQueryService;

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
 * Integration tests for the {@link ChunhiemResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class ChunhiemResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private ChunhiemRepository chunhiemRepository;

    @Autowired
    private ChunhiemMapper chunhiemMapper;

    @Autowired
    private ChunhiemService chunhiemService;

    @Autowired
    private ChunhiemQueryService chunhiemQueryService;

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

    private MockMvc restChunhiemMockMvc;

    private Chunhiem chunhiem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChunhiemResource chunhiemResource = new ChunhiemResource(chunhiemService, chunhiemQueryService);
        this.restChunhiemMockMvc = MockMvcBuilders.standaloneSetup(chunhiemResource)
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
    public static Chunhiem createEntity(EntityManager em) {
        Chunhiem chunhiem = new Chunhiem()
            .ten(DEFAULT_TEN)
            .sudung(DEFAULT_SUDUNG);
        return chunhiem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chunhiem createUpdatedEntity(EntityManager em) {
        Chunhiem chunhiem = new Chunhiem()
            .ten(UPDATED_TEN)
            .sudung(UPDATED_SUDUNG);
        return chunhiem;
    }

    @BeforeEach
    public void initTest() {
        chunhiem = createEntity(em);
    }

    @Test
    @Transactional
    public void createChunhiem() throws Exception {
        int databaseSizeBeforeCreate = chunhiemRepository.findAll().size();

        // Create the Chunhiem
        ChunhiemDTO chunhiemDTO = chunhiemMapper.toDto(chunhiem);
        restChunhiemMockMvc.perform(post("/api/chunhiems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chunhiemDTO)))
            .andExpect(status().isCreated());

        // Validate the Chunhiem in the database
        List<Chunhiem> chunhiemList = chunhiemRepository.findAll();
        assertThat(chunhiemList).hasSize(databaseSizeBeforeCreate + 1);
        Chunhiem testChunhiem = chunhiemList.get(chunhiemList.size() - 1);
        assertThat(testChunhiem.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testChunhiem.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createChunhiemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chunhiemRepository.findAll().size();

        // Create the Chunhiem with an existing ID
        chunhiem.setId(1L);
        ChunhiemDTO chunhiemDTO = chunhiemMapper.toDto(chunhiem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChunhiemMockMvc.perform(post("/api/chunhiems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chunhiemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chunhiem in the database
        List<Chunhiem> chunhiemList = chunhiemRepository.findAll();
        assertThat(chunhiemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChunhiems() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList
        restChunhiemMockMvc.perform(get("/api/chunhiems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chunhiem.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getChunhiem() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get the chunhiem
        restChunhiemMockMvc.perform(get("/api/chunhiems/{id}", chunhiem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chunhiem.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getChunhiemsByIdFiltering() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        Long id = chunhiem.getId();

        defaultChunhiemShouldBeFound("id.equals=" + id);
        defaultChunhiemShouldNotBeFound("id.notEquals=" + id);

        defaultChunhiemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChunhiemShouldNotBeFound("id.greaterThan=" + id);

        defaultChunhiemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChunhiemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChunhiemsByTenIsEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where ten equals to DEFAULT_TEN
        defaultChunhiemShouldBeFound("ten.equals=" + DEFAULT_TEN);

        // Get all the chunhiemList where ten equals to UPDATED_TEN
        defaultChunhiemShouldNotBeFound("ten.equals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllChunhiemsByTenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where ten not equals to DEFAULT_TEN
        defaultChunhiemShouldNotBeFound("ten.notEquals=" + DEFAULT_TEN);

        // Get all the chunhiemList where ten not equals to UPDATED_TEN
        defaultChunhiemShouldBeFound("ten.notEquals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllChunhiemsByTenIsInShouldWork() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where ten in DEFAULT_TEN or UPDATED_TEN
        defaultChunhiemShouldBeFound("ten.in=" + DEFAULT_TEN + "," + UPDATED_TEN);

        // Get all the chunhiemList where ten equals to UPDATED_TEN
        defaultChunhiemShouldNotBeFound("ten.in=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllChunhiemsByTenIsNullOrNotNull() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where ten is not null
        defaultChunhiemShouldBeFound("ten.specified=true");

        // Get all the chunhiemList where ten is null
        defaultChunhiemShouldNotBeFound("ten.specified=false");
    }
                @Test
    @Transactional
    public void getAllChunhiemsByTenContainsSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where ten contains DEFAULT_TEN
        defaultChunhiemShouldBeFound("ten.contains=" + DEFAULT_TEN);

        // Get all the chunhiemList where ten contains UPDATED_TEN
        defaultChunhiemShouldNotBeFound("ten.contains=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllChunhiemsByTenNotContainsSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where ten does not contain DEFAULT_TEN
        defaultChunhiemShouldNotBeFound("ten.doesNotContain=" + DEFAULT_TEN);

        // Get all the chunhiemList where ten does not contain UPDATED_TEN
        defaultChunhiemShouldBeFound("ten.doesNotContain=" + UPDATED_TEN);
    }


    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung equals to DEFAULT_SUDUNG
        defaultChunhiemShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the chunhiemList where sudung equals to UPDATED_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung not equals to DEFAULT_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the chunhiemList where sudung not equals to UPDATED_SUDUNG
        defaultChunhiemShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultChunhiemShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the chunhiemList where sudung equals to UPDATED_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung is not null
        defaultChunhiemShouldBeFound("sudung.specified=true");

        // Get all the chunhiemList where sudung is null
        defaultChunhiemShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultChunhiemShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the chunhiemList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultChunhiemShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the chunhiemList where sudung is less than or equal to SMALLER_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung is less than DEFAULT_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the chunhiemList where sudung is less than UPDATED_SUDUNG
        defaultChunhiemShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllChunhiemsBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        // Get all the chunhiemList where sudung is greater than DEFAULT_SUDUNG
        defaultChunhiemShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the chunhiemList where sudung is greater than SMALLER_SUDUNG
        defaultChunhiemShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllChunhiemsByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        chunhiem.addDetai(detai);
        chunhiemRepository.saveAndFlush(chunhiem);
        Long detaiId = detai.getId();

        // Get all the chunhiemList where detai equals to detaiId
        defaultChunhiemShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the chunhiemList where detai equals to detaiId + 1
        defaultChunhiemShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }


    @Test
    @Transactional
    public void getAllChunhiemsByNhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);
        Nhansu nhansu = NhansuResourceIT.createEntity(em);
        em.persist(nhansu);
        em.flush();
        chunhiem.setNhansu(nhansu);
        chunhiemRepository.saveAndFlush(chunhiem);
        Long nhansuId = nhansu.getId();

        // Get all the chunhiemList where nhansu equals to nhansuId
        defaultChunhiemShouldBeFound("nhansuId.equals=" + nhansuId);

        // Get all the chunhiemList where nhansu equals to nhansuId + 1
        defaultChunhiemShouldNotBeFound("nhansuId.equals=" + (nhansuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChunhiemShouldBeFound(String filter) throws Exception {
        restChunhiemMockMvc.perform(get("/api/chunhiems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chunhiem.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restChunhiemMockMvc.perform(get("/api/chunhiems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChunhiemShouldNotBeFound(String filter) throws Exception {
        restChunhiemMockMvc.perform(get("/api/chunhiems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChunhiemMockMvc.perform(get("/api/chunhiems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChunhiem() throws Exception {
        // Get the chunhiem
        restChunhiemMockMvc.perform(get("/api/chunhiems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChunhiem() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        int databaseSizeBeforeUpdate = chunhiemRepository.findAll().size();

        // Update the chunhiem
        Chunhiem updatedChunhiem = chunhiemRepository.findById(chunhiem.getId()).get();
        // Disconnect from session so that the updates on updatedChunhiem are not directly saved in db
        em.detach(updatedChunhiem);
        updatedChunhiem
            .ten(UPDATED_TEN)
            .sudung(UPDATED_SUDUNG);
        ChunhiemDTO chunhiemDTO = chunhiemMapper.toDto(updatedChunhiem);

        restChunhiemMockMvc.perform(put("/api/chunhiems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chunhiemDTO)))
            .andExpect(status().isOk());

        // Validate the Chunhiem in the database
        List<Chunhiem> chunhiemList = chunhiemRepository.findAll();
        assertThat(chunhiemList).hasSize(databaseSizeBeforeUpdate);
        Chunhiem testChunhiem = chunhiemList.get(chunhiemList.size() - 1);
        assertThat(testChunhiem.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testChunhiem.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingChunhiem() throws Exception {
        int databaseSizeBeforeUpdate = chunhiemRepository.findAll().size();

        // Create the Chunhiem
        ChunhiemDTO chunhiemDTO = chunhiemMapper.toDto(chunhiem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChunhiemMockMvc.perform(put("/api/chunhiems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chunhiemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chunhiem in the database
        List<Chunhiem> chunhiemList = chunhiemRepository.findAll();
        assertThat(chunhiemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChunhiem() throws Exception {
        // Initialize the database
        chunhiemRepository.saveAndFlush(chunhiem);

        int databaseSizeBeforeDelete = chunhiemRepository.findAll().size();

        // Delete the chunhiem
        restChunhiemMockMvc.perform(delete("/api/chunhiems/{id}", chunhiem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chunhiem> chunhiemList = chunhiemRepository.findAll();
        assertThat(chunhiemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
