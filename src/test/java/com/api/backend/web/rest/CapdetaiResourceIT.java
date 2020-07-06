package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Capdetai;
import com.api.backend.domain.Detai;
import com.api.backend.repository.CapdetaiRepository;
import com.api.backend.service.CapdetaiService;
import com.api.backend.service.dto.CapdetaiDTO;
import com.api.backend.service.mapper.CapdetaiMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.CapdetaiCriteria;
import com.api.backend.service.CapdetaiQueryService;

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
 * Integration tests for the {@link CapdetaiResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class CapdetaiResourceIT {

    private static final String DEFAULT_MACAPDETAI = "AAAAAAAAAA";
    private static final String UPDATED_MACAPDETAI = "BBBBBBBBBB";

    private static final String DEFAULT_TENCAPDETAI = "AAAAAAAAAA";
    private static final String UPDATED_TENCAPDETAI = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private CapdetaiRepository capdetaiRepository;

    @Autowired
    private CapdetaiMapper capdetaiMapper;

    @Autowired
    private CapdetaiService capdetaiService;

    @Autowired
    private CapdetaiQueryService capdetaiQueryService;

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

    private MockMvc restCapdetaiMockMvc;

    private Capdetai capdetai;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapdetaiResource capdetaiResource = new CapdetaiResource(capdetaiService, capdetaiQueryService);
        this.restCapdetaiMockMvc = MockMvcBuilders.standaloneSetup(capdetaiResource)
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
    public static Capdetai createEntity(EntityManager em) {
        Capdetai capdetai = new Capdetai()
            .macapdetai(DEFAULT_MACAPDETAI)
            .tencapdetai(DEFAULT_TENCAPDETAI)
            .sudung(DEFAULT_SUDUNG);
        return capdetai;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capdetai createUpdatedEntity(EntityManager em) {
        Capdetai capdetai = new Capdetai()
            .macapdetai(UPDATED_MACAPDETAI)
            .tencapdetai(UPDATED_TENCAPDETAI)
            .sudung(UPDATED_SUDUNG);
        return capdetai;
    }

    @BeforeEach
    public void initTest() {
        capdetai = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapdetai() throws Exception {
        int databaseSizeBeforeCreate = capdetaiRepository.findAll().size();

        // Create the Capdetai
        CapdetaiDTO capdetaiDTO = capdetaiMapper.toDto(capdetai);
        restCapdetaiMockMvc.perform(post("/api/capdetais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capdetaiDTO)))
            .andExpect(status().isCreated());

        // Validate the Capdetai in the database
        List<Capdetai> capdetaiList = capdetaiRepository.findAll();
        assertThat(capdetaiList).hasSize(databaseSizeBeforeCreate + 1);
        Capdetai testCapdetai = capdetaiList.get(capdetaiList.size() - 1);
        assertThat(testCapdetai.getMacapdetai()).isEqualTo(DEFAULT_MACAPDETAI);
        assertThat(testCapdetai.getTencapdetai()).isEqualTo(DEFAULT_TENCAPDETAI);
        assertThat(testCapdetai.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createCapdetaiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capdetaiRepository.findAll().size();

        // Create the Capdetai with an existing ID
        capdetai.setId(1L);
        CapdetaiDTO capdetaiDTO = capdetaiMapper.toDto(capdetai);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapdetaiMockMvc.perform(post("/api/capdetais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capdetaiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Capdetai in the database
        List<Capdetai> capdetaiList = capdetaiRepository.findAll();
        assertThat(capdetaiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCapdetais() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList
        restCapdetaiMockMvc.perform(get("/api/capdetais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capdetai.getId().intValue())))
            .andExpect(jsonPath("$.[*].macapdetai").value(hasItem(DEFAULT_MACAPDETAI)))
            .andExpect(jsonPath("$.[*].tencapdetai").value(hasItem(DEFAULT_TENCAPDETAI)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getCapdetai() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get the capdetai
        restCapdetaiMockMvc.perform(get("/api/capdetais/{id}", capdetai.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capdetai.getId().intValue()))
            .andExpect(jsonPath("$.macapdetai").value(DEFAULT_MACAPDETAI))
            .andExpect(jsonPath("$.tencapdetai").value(DEFAULT_TENCAPDETAI))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getCapdetaisByIdFiltering() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        Long id = capdetai.getId();

        defaultCapdetaiShouldBeFound("id.equals=" + id);
        defaultCapdetaiShouldNotBeFound("id.notEquals=" + id);

        defaultCapdetaiShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCapdetaiShouldNotBeFound("id.greaterThan=" + id);

        defaultCapdetaiShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCapdetaiShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCapdetaisByMacapdetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where macapdetai equals to DEFAULT_MACAPDETAI
        defaultCapdetaiShouldBeFound("macapdetai.equals=" + DEFAULT_MACAPDETAI);

        // Get all the capdetaiList where macapdetai equals to UPDATED_MACAPDETAI
        defaultCapdetaiShouldNotBeFound("macapdetai.equals=" + UPDATED_MACAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByMacapdetaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where macapdetai not equals to DEFAULT_MACAPDETAI
        defaultCapdetaiShouldNotBeFound("macapdetai.notEquals=" + DEFAULT_MACAPDETAI);

        // Get all the capdetaiList where macapdetai not equals to UPDATED_MACAPDETAI
        defaultCapdetaiShouldBeFound("macapdetai.notEquals=" + UPDATED_MACAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByMacapdetaiIsInShouldWork() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where macapdetai in DEFAULT_MACAPDETAI or UPDATED_MACAPDETAI
        defaultCapdetaiShouldBeFound("macapdetai.in=" + DEFAULT_MACAPDETAI + "," + UPDATED_MACAPDETAI);

        // Get all the capdetaiList where macapdetai equals to UPDATED_MACAPDETAI
        defaultCapdetaiShouldNotBeFound("macapdetai.in=" + UPDATED_MACAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByMacapdetaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where macapdetai is not null
        defaultCapdetaiShouldBeFound("macapdetai.specified=true");

        // Get all the capdetaiList where macapdetai is null
        defaultCapdetaiShouldNotBeFound("macapdetai.specified=false");
    }
                @Test
    @Transactional
    public void getAllCapdetaisByMacapdetaiContainsSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where macapdetai contains DEFAULT_MACAPDETAI
        defaultCapdetaiShouldBeFound("macapdetai.contains=" + DEFAULT_MACAPDETAI);

        // Get all the capdetaiList where macapdetai contains UPDATED_MACAPDETAI
        defaultCapdetaiShouldNotBeFound("macapdetai.contains=" + UPDATED_MACAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByMacapdetaiNotContainsSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where macapdetai does not contain DEFAULT_MACAPDETAI
        defaultCapdetaiShouldNotBeFound("macapdetai.doesNotContain=" + DEFAULT_MACAPDETAI);

        // Get all the capdetaiList where macapdetai does not contain UPDATED_MACAPDETAI
        defaultCapdetaiShouldBeFound("macapdetai.doesNotContain=" + UPDATED_MACAPDETAI);
    }


    @Test
    @Transactional
    public void getAllCapdetaisByTencapdetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where tencapdetai equals to DEFAULT_TENCAPDETAI
        defaultCapdetaiShouldBeFound("tencapdetai.equals=" + DEFAULT_TENCAPDETAI);

        // Get all the capdetaiList where tencapdetai equals to UPDATED_TENCAPDETAI
        defaultCapdetaiShouldNotBeFound("tencapdetai.equals=" + UPDATED_TENCAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByTencapdetaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where tencapdetai not equals to DEFAULT_TENCAPDETAI
        defaultCapdetaiShouldNotBeFound("tencapdetai.notEquals=" + DEFAULT_TENCAPDETAI);

        // Get all the capdetaiList where tencapdetai not equals to UPDATED_TENCAPDETAI
        defaultCapdetaiShouldBeFound("tencapdetai.notEquals=" + UPDATED_TENCAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByTencapdetaiIsInShouldWork() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where tencapdetai in DEFAULT_TENCAPDETAI or UPDATED_TENCAPDETAI
        defaultCapdetaiShouldBeFound("tencapdetai.in=" + DEFAULT_TENCAPDETAI + "," + UPDATED_TENCAPDETAI);

        // Get all the capdetaiList where tencapdetai equals to UPDATED_TENCAPDETAI
        defaultCapdetaiShouldNotBeFound("tencapdetai.in=" + UPDATED_TENCAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByTencapdetaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where tencapdetai is not null
        defaultCapdetaiShouldBeFound("tencapdetai.specified=true");

        // Get all the capdetaiList where tencapdetai is null
        defaultCapdetaiShouldNotBeFound("tencapdetai.specified=false");
    }
                @Test
    @Transactional
    public void getAllCapdetaisByTencapdetaiContainsSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where tencapdetai contains DEFAULT_TENCAPDETAI
        defaultCapdetaiShouldBeFound("tencapdetai.contains=" + DEFAULT_TENCAPDETAI);

        // Get all the capdetaiList where tencapdetai contains UPDATED_TENCAPDETAI
        defaultCapdetaiShouldNotBeFound("tencapdetai.contains=" + UPDATED_TENCAPDETAI);
    }

    @Test
    @Transactional
    public void getAllCapdetaisByTencapdetaiNotContainsSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where tencapdetai does not contain DEFAULT_TENCAPDETAI
        defaultCapdetaiShouldNotBeFound("tencapdetai.doesNotContain=" + DEFAULT_TENCAPDETAI);

        // Get all the capdetaiList where tencapdetai does not contain UPDATED_TENCAPDETAI
        defaultCapdetaiShouldBeFound("tencapdetai.doesNotContain=" + UPDATED_TENCAPDETAI);
    }


    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung equals to DEFAULT_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the capdetaiList where sudung equals to UPDATED_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung not equals to DEFAULT_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the capdetaiList where sudung not equals to UPDATED_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the capdetaiList where sudung equals to UPDATED_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung is not null
        defaultCapdetaiShouldBeFound("sudung.specified=true");

        // Get all the capdetaiList where sudung is null
        defaultCapdetaiShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the capdetaiList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the capdetaiList where sudung is less than or equal to SMALLER_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung is less than DEFAULT_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the capdetaiList where sudung is less than UPDATED_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllCapdetaisBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        // Get all the capdetaiList where sudung is greater than DEFAULT_SUDUNG
        defaultCapdetaiShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the capdetaiList where sudung is greater than SMALLER_SUDUNG
        defaultCapdetaiShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllCapdetaisByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        capdetai.addDetai(detai);
        capdetaiRepository.saveAndFlush(capdetai);
        Long detaiId = detai.getId();

        // Get all the capdetaiList where detai equals to detaiId
        defaultCapdetaiShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the capdetaiList where detai equals to detaiId + 1
        defaultCapdetaiShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCapdetaiShouldBeFound(String filter) throws Exception {
        restCapdetaiMockMvc.perform(get("/api/capdetais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capdetai.getId().intValue())))
            .andExpect(jsonPath("$.[*].macapdetai").value(hasItem(DEFAULT_MACAPDETAI)))
            .andExpect(jsonPath("$.[*].tencapdetai").value(hasItem(DEFAULT_TENCAPDETAI)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restCapdetaiMockMvc.perform(get("/api/capdetais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCapdetaiShouldNotBeFound(String filter) throws Exception {
        restCapdetaiMockMvc.perform(get("/api/capdetais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCapdetaiMockMvc.perform(get("/api/capdetais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCapdetai() throws Exception {
        // Get the capdetai
        restCapdetaiMockMvc.perform(get("/api/capdetais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapdetai() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        int databaseSizeBeforeUpdate = capdetaiRepository.findAll().size();

        // Update the capdetai
        Capdetai updatedCapdetai = capdetaiRepository.findById(capdetai.getId()).get();
        // Disconnect from session so that the updates on updatedCapdetai are not directly saved in db
        em.detach(updatedCapdetai);
        updatedCapdetai
            .macapdetai(UPDATED_MACAPDETAI)
            .tencapdetai(UPDATED_TENCAPDETAI)
            .sudung(UPDATED_SUDUNG);
        CapdetaiDTO capdetaiDTO = capdetaiMapper.toDto(updatedCapdetai);

        restCapdetaiMockMvc.perform(put("/api/capdetais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capdetaiDTO)))
            .andExpect(status().isOk());

        // Validate the Capdetai in the database
        List<Capdetai> capdetaiList = capdetaiRepository.findAll();
        assertThat(capdetaiList).hasSize(databaseSizeBeforeUpdate);
        Capdetai testCapdetai = capdetaiList.get(capdetaiList.size() - 1);
        assertThat(testCapdetai.getMacapdetai()).isEqualTo(UPDATED_MACAPDETAI);
        assertThat(testCapdetai.getTencapdetai()).isEqualTo(UPDATED_TENCAPDETAI);
        assertThat(testCapdetai.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingCapdetai() throws Exception {
        int databaseSizeBeforeUpdate = capdetaiRepository.findAll().size();

        // Create the Capdetai
        CapdetaiDTO capdetaiDTO = capdetaiMapper.toDto(capdetai);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapdetaiMockMvc.perform(put("/api/capdetais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capdetaiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Capdetai in the database
        List<Capdetai> capdetaiList = capdetaiRepository.findAll();
        assertThat(capdetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCapdetai() throws Exception {
        // Initialize the database
        capdetaiRepository.saveAndFlush(capdetai);

        int databaseSizeBeforeDelete = capdetaiRepository.findAll().size();

        // Delete the capdetai
        restCapdetaiMockMvc.perform(delete("/api/capdetais/{id}", capdetai.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Capdetai> capdetaiList = capdetaiRepository.findAll();
        assertThat(capdetaiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
