package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Linhvuc;
import com.api.backend.domain.Detai;
import com.api.backend.repository.LinhvucRepository;
import com.api.backend.service.LinhvucService;
import com.api.backend.service.dto.LinhvucDTO;
import com.api.backend.service.mapper.LinhvucMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.LinhvucCriteria;
import com.api.backend.service.LinhvucQueryService;

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
 * Integration tests for the {@link LinhvucResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class LinhvucResourceIT {

    private static final String DEFAULT_MALV = "AAAAAAAAAA";
    private static final String UPDATED_MALV = "BBBBBBBBBB";

    private static final String DEFAULT_TENLV = "AAAAAAAAAA";
    private static final String UPDATED_TENLV = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private LinhvucRepository linhvucRepository;

    @Autowired
    private LinhvucMapper linhvucMapper;

    @Autowired
    private LinhvucService linhvucService;

    @Autowired
    private LinhvucQueryService linhvucQueryService;

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

    private MockMvc restLinhvucMockMvc;

    private Linhvuc linhvuc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LinhvucResource linhvucResource = new LinhvucResource(linhvucService, linhvucQueryService);
        this.restLinhvucMockMvc = MockMvcBuilders.standaloneSetup(linhvucResource)
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
    public static Linhvuc createEntity(EntityManager em) {
        Linhvuc linhvuc = new Linhvuc()
            .malv(DEFAULT_MALV)
            .tenlv(DEFAULT_TENLV)
            .sudung(DEFAULT_SUDUNG);
        return linhvuc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Linhvuc createUpdatedEntity(EntityManager em) {
        Linhvuc linhvuc = new Linhvuc()
            .malv(UPDATED_MALV)
            .tenlv(UPDATED_TENLV)
            .sudung(UPDATED_SUDUNG);
        return linhvuc;
    }

    @BeforeEach
    public void initTest() {
        linhvuc = createEntity(em);
    }

    @Test
    @Transactional
    public void createLinhvuc() throws Exception {
        int databaseSizeBeforeCreate = linhvucRepository.findAll().size();

        // Create the Linhvuc
        LinhvucDTO linhvucDTO = linhvucMapper.toDto(linhvuc);
        restLinhvucMockMvc.perform(post("/api/linhvucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhvucDTO)))
            .andExpect(status().isCreated());

        // Validate the Linhvuc in the database
        List<Linhvuc> linhvucList = linhvucRepository.findAll();
        assertThat(linhvucList).hasSize(databaseSizeBeforeCreate + 1);
        Linhvuc testLinhvuc = linhvucList.get(linhvucList.size() - 1);
        assertThat(testLinhvuc.getMalv()).isEqualTo(DEFAULT_MALV);
        assertThat(testLinhvuc.getTenlv()).isEqualTo(DEFAULT_TENLV);
        assertThat(testLinhvuc.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createLinhvucWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linhvucRepository.findAll().size();

        // Create the Linhvuc with an existing ID
        linhvuc.setId(1L);
        LinhvucDTO linhvucDTO = linhvucMapper.toDto(linhvuc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinhvucMockMvc.perform(post("/api/linhvucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhvucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Linhvuc in the database
        List<Linhvuc> linhvucList = linhvucRepository.findAll();
        assertThat(linhvucList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLinhvucs() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList
        restLinhvucMockMvc.perform(get("/api/linhvucs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linhvuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].malv").value(hasItem(DEFAULT_MALV)))
            .andExpect(jsonPath("$.[*].tenlv").value(hasItem(DEFAULT_TENLV)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getLinhvuc() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get the linhvuc
        restLinhvucMockMvc.perform(get("/api/linhvucs/{id}", linhvuc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(linhvuc.getId().intValue()))
            .andExpect(jsonPath("$.malv").value(DEFAULT_MALV))
            .andExpect(jsonPath("$.tenlv").value(DEFAULT_TENLV))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getLinhvucsByIdFiltering() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        Long id = linhvuc.getId();

        defaultLinhvucShouldBeFound("id.equals=" + id);
        defaultLinhvucShouldNotBeFound("id.notEquals=" + id);

        defaultLinhvucShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLinhvucShouldNotBeFound("id.greaterThan=" + id);

        defaultLinhvucShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLinhvucShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLinhvucsByMalvIsEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where malv equals to DEFAULT_MALV
        defaultLinhvucShouldBeFound("malv.equals=" + DEFAULT_MALV);

        // Get all the linhvucList where malv equals to UPDATED_MALV
        defaultLinhvucShouldNotBeFound("malv.equals=" + UPDATED_MALV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByMalvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where malv not equals to DEFAULT_MALV
        defaultLinhvucShouldNotBeFound("malv.notEquals=" + DEFAULT_MALV);

        // Get all the linhvucList where malv not equals to UPDATED_MALV
        defaultLinhvucShouldBeFound("malv.notEquals=" + UPDATED_MALV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByMalvIsInShouldWork() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where malv in DEFAULT_MALV or UPDATED_MALV
        defaultLinhvucShouldBeFound("malv.in=" + DEFAULT_MALV + "," + UPDATED_MALV);

        // Get all the linhvucList where malv equals to UPDATED_MALV
        defaultLinhvucShouldNotBeFound("malv.in=" + UPDATED_MALV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByMalvIsNullOrNotNull() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where malv is not null
        defaultLinhvucShouldBeFound("malv.specified=true");

        // Get all the linhvucList where malv is null
        defaultLinhvucShouldNotBeFound("malv.specified=false");
    }
                @Test
    @Transactional
    public void getAllLinhvucsByMalvContainsSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where malv contains DEFAULT_MALV
        defaultLinhvucShouldBeFound("malv.contains=" + DEFAULT_MALV);

        // Get all the linhvucList where malv contains UPDATED_MALV
        defaultLinhvucShouldNotBeFound("malv.contains=" + UPDATED_MALV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByMalvNotContainsSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where malv does not contain DEFAULT_MALV
        defaultLinhvucShouldNotBeFound("malv.doesNotContain=" + DEFAULT_MALV);

        // Get all the linhvucList where malv does not contain UPDATED_MALV
        defaultLinhvucShouldBeFound("malv.doesNotContain=" + UPDATED_MALV);
    }


    @Test
    @Transactional
    public void getAllLinhvucsByTenlvIsEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where tenlv equals to DEFAULT_TENLV
        defaultLinhvucShouldBeFound("tenlv.equals=" + DEFAULT_TENLV);

        // Get all the linhvucList where tenlv equals to UPDATED_TENLV
        defaultLinhvucShouldNotBeFound("tenlv.equals=" + UPDATED_TENLV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByTenlvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where tenlv not equals to DEFAULT_TENLV
        defaultLinhvucShouldNotBeFound("tenlv.notEquals=" + DEFAULT_TENLV);

        // Get all the linhvucList where tenlv not equals to UPDATED_TENLV
        defaultLinhvucShouldBeFound("tenlv.notEquals=" + UPDATED_TENLV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByTenlvIsInShouldWork() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where tenlv in DEFAULT_TENLV or UPDATED_TENLV
        defaultLinhvucShouldBeFound("tenlv.in=" + DEFAULT_TENLV + "," + UPDATED_TENLV);

        // Get all the linhvucList where tenlv equals to UPDATED_TENLV
        defaultLinhvucShouldNotBeFound("tenlv.in=" + UPDATED_TENLV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByTenlvIsNullOrNotNull() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where tenlv is not null
        defaultLinhvucShouldBeFound("tenlv.specified=true");

        // Get all the linhvucList where tenlv is null
        defaultLinhvucShouldNotBeFound("tenlv.specified=false");
    }
                @Test
    @Transactional
    public void getAllLinhvucsByTenlvContainsSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where tenlv contains DEFAULT_TENLV
        defaultLinhvucShouldBeFound("tenlv.contains=" + DEFAULT_TENLV);

        // Get all the linhvucList where tenlv contains UPDATED_TENLV
        defaultLinhvucShouldNotBeFound("tenlv.contains=" + UPDATED_TENLV);
    }

    @Test
    @Transactional
    public void getAllLinhvucsByTenlvNotContainsSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where tenlv does not contain DEFAULT_TENLV
        defaultLinhvucShouldNotBeFound("tenlv.doesNotContain=" + DEFAULT_TENLV);

        // Get all the linhvucList where tenlv does not contain UPDATED_TENLV
        defaultLinhvucShouldBeFound("tenlv.doesNotContain=" + UPDATED_TENLV);
    }


    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung equals to DEFAULT_SUDUNG
        defaultLinhvucShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the linhvucList where sudung equals to UPDATED_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung not equals to DEFAULT_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the linhvucList where sudung not equals to UPDATED_SUDUNG
        defaultLinhvucShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultLinhvucShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the linhvucList where sudung equals to UPDATED_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung is not null
        defaultLinhvucShouldBeFound("sudung.specified=true");

        // Get all the linhvucList where sudung is null
        defaultLinhvucShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultLinhvucShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the linhvucList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultLinhvucShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the linhvucList where sudung is less than or equal to SMALLER_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung is less than DEFAULT_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the linhvucList where sudung is less than UPDATED_SUDUNG
        defaultLinhvucShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllLinhvucsBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        // Get all the linhvucList where sudung is greater than DEFAULT_SUDUNG
        defaultLinhvucShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the linhvucList where sudung is greater than SMALLER_SUDUNG
        defaultLinhvucShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllLinhvucsByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        linhvuc.addDetai(detai);
        linhvucRepository.saveAndFlush(linhvuc);
        Long detaiId = detai.getId();

        // Get all the linhvucList where detai equals to detaiId
        defaultLinhvucShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the linhvucList where detai equals to detaiId + 1
        defaultLinhvucShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLinhvucShouldBeFound(String filter) throws Exception {
        restLinhvucMockMvc.perform(get("/api/linhvucs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linhvuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].malv").value(hasItem(DEFAULT_MALV)))
            .andExpect(jsonPath("$.[*].tenlv").value(hasItem(DEFAULT_TENLV)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restLinhvucMockMvc.perform(get("/api/linhvucs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLinhvucShouldNotBeFound(String filter) throws Exception {
        restLinhvucMockMvc.perform(get("/api/linhvucs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLinhvucMockMvc.perform(get("/api/linhvucs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLinhvuc() throws Exception {
        // Get the linhvuc
        restLinhvucMockMvc.perform(get("/api/linhvucs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLinhvuc() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        int databaseSizeBeforeUpdate = linhvucRepository.findAll().size();

        // Update the linhvuc
        Linhvuc updatedLinhvuc = linhvucRepository.findById(linhvuc.getId()).get();
        // Disconnect from session so that the updates on updatedLinhvuc are not directly saved in db
        em.detach(updatedLinhvuc);
        updatedLinhvuc
            .malv(UPDATED_MALV)
            .tenlv(UPDATED_TENLV)
            .sudung(UPDATED_SUDUNG);
        LinhvucDTO linhvucDTO = linhvucMapper.toDto(updatedLinhvuc);

        restLinhvucMockMvc.perform(put("/api/linhvucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhvucDTO)))
            .andExpect(status().isOk());

        // Validate the Linhvuc in the database
        List<Linhvuc> linhvucList = linhvucRepository.findAll();
        assertThat(linhvucList).hasSize(databaseSizeBeforeUpdate);
        Linhvuc testLinhvuc = linhvucList.get(linhvucList.size() - 1);
        assertThat(testLinhvuc.getMalv()).isEqualTo(UPDATED_MALV);
        assertThat(testLinhvuc.getTenlv()).isEqualTo(UPDATED_TENLV);
        assertThat(testLinhvuc.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingLinhvuc() throws Exception {
        int databaseSizeBeforeUpdate = linhvucRepository.findAll().size();

        // Create the Linhvuc
        LinhvucDTO linhvucDTO = linhvucMapper.toDto(linhvuc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinhvucMockMvc.perform(put("/api/linhvucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhvucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Linhvuc in the database
        List<Linhvuc> linhvucList = linhvucRepository.findAll();
        assertThat(linhvucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLinhvuc() throws Exception {
        // Initialize the database
        linhvucRepository.saveAndFlush(linhvuc);

        int databaseSizeBeforeDelete = linhvucRepository.findAll().size();

        // Delete the linhvuc
        restLinhvucMockMvc.perform(delete("/api/linhvucs/{id}", linhvuc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Linhvuc> linhvucList = linhvucRepository.findAll();
        assertThat(linhvucList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
