package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.NoidungDT;
import com.api.backend.domain.DutoanKPCT;
import com.api.backend.repository.NoidungDTRepository;
import com.api.backend.service.NoidungDTService;
import com.api.backend.service.dto.NoidungDTDTO;
import com.api.backend.service.mapper.NoidungDTMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.NoidungDTCriteria;
import com.api.backend.service.NoidungDTQueryService;

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
 * Integration tests for the {@link NoidungDTResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class NoidungDTResourceIT {

    private static final String DEFAULT_TENNOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_TENNOIDUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private NoidungDTRepository noidungDTRepository;

    @Autowired
    private NoidungDTMapper noidungDTMapper;

    @Autowired
    private NoidungDTService noidungDTService;

    @Autowired
    private NoidungDTQueryService noidungDTQueryService;

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

    private MockMvc restNoidungDTMockMvc;

    private NoidungDT noidungDT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NoidungDTResource noidungDTResource = new NoidungDTResource(noidungDTService, noidungDTQueryService);
        this.restNoidungDTMockMvc = MockMvcBuilders.standaloneSetup(noidungDTResource)
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
    public static NoidungDT createEntity(EntityManager em) {
        NoidungDT noidungDT = new NoidungDT()
            .tennoidung(DEFAULT_TENNOIDUNG)
            .sudung(DEFAULT_SUDUNG);
        return noidungDT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoidungDT createUpdatedEntity(EntityManager em) {
        NoidungDT noidungDT = new NoidungDT()
            .tennoidung(UPDATED_TENNOIDUNG)
            .sudung(UPDATED_SUDUNG);
        return noidungDT;
    }

    @BeforeEach
    public void initTest() {
        noidungDT = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoidungDT() throws Exception {
        int databaseSizeBeforeCreate = noidungDTRepository.findAll().size();

        // Create the NoidungDT
        NoidungDTDTO noidungDTDTO = noidungDTMapper.toDto(noidungDT);
        restNoidungDTMockMvc.perform(post("/api/noidung-dts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungDTDTO)))
            .andExpect(status().isCreated());

        // Validate the NoidungDT in the database
        List<NoidungDT> noidungDTList = noidungDTRepository.findAll();
        assertThat(noidungDTList).hasSize(databaseSizeBeforeCreate + 1);
        NoidungDT testNoidungDT = noidungDTList.get(noidungDTList.size() - 1);
        assertThat(testNoidungDT.getTennoidung()).isEqualTo(DEFAULT_TENNOIDUNG);
        assertThat(testNoidungDT.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createNoidungDTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noidungDTRepository.findAll().size();

        // Create the NoidungDT with an existing ID
        noidungDT.setId(1L);
        NoidungDTDTO noidungDTDTO = noidungDTMapper.toDto(noidungDT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoidungDTMockMvc.perform(post("/api/noidung-dts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungDTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoidungDT in the database
        List<NoidungDT> noidungDTList = noidungDTRepository.findAll();
        assertThat(noidungDTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNoidungDTS() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList
        restNoidungDTMockMvc.perform(get("/api/noidung-dts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noidungDT.getId().intValue())))
            .andExpect(jsonPath("$.[*].tennoidung").value(hasItem(DEFAULT_TENNOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getNoidungDT() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get the noidungDT
        restNoidungDTMockMvc.perform(get("/api/noidung-dts/{id}", noidungDT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noidungDT.getId().intValue()))
            .andExpect(jsonPath("$.tennoidung").value(DEFAULT_TENNOIDUNG))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getNoidungDTSByIdFiltering() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        Long id = noidungDT.getId();

        defaultNoidungDTShouldBeFound("id.equals=" + id);
        defaultNoidungDTShouldNotBeFound("id.notEquals=" + id);

        defaultNoidungDTShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoidungDTShouldNotBeFound("id.greaterThan=" + id);

        defaultNoidungDTShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoidungDTShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNoidungDTSByTennoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where tennoidung equals to DEFAULT_TENNOIDUNG
        defaultNoidungDTShouldBeFound("tennoidung.equals=" + DEFAULT_TENNOIDUNG);

        // Get all the noidungDTList where tennoidung equals to UPDATED_TENNOIDUNG
        defaultNoidungDTShouldNotBeFound("tennoidung.equals=" + UPDATED_TENNOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSByTennoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where tennoidung not equals to DEFAULT_TENNOIDUNG
        defaultNoidungDTShouldNotBeFound("tennoidung.notEquals=" + DEFAULT_TENNOIDUNG);

        // Get all the noidungDTList where tennoidung not equals to UPDATED_TENNOIDUNG
        defaultNoidungDTShouldBeFound("tennoidung.notEquals=" + UPDATED_TENNOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSByTennoidungIsInShouldWork() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where tennoidung in DEFAULT_TENNOIDUNG or UPDATED_TENNOIDUNG
        defaultNoidungDTShouldBeFound("tennoidung.in=" + DEFAULT_TENNOIDUNG + "," + UPDATED_TENNOIDUNG);

        // Get all the noidungDTList where tennoidung equals to UPDATED_TENNOIDUNG
        defaultNoidungDTShouldNotBeFound("tennoidung.in=" + UPDATED_TENNOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSByTennoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where tennoidung is not null
        defaultNoidungDTShouldBeFound("tennoidung.specified=true");

        // Get all the noidungDTList where tennoidung is null
        defaultNoidungDTShouldNotBeFound("tennoidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoidungDTSByTennoidungContainsSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where tennoidung contains DEFAULT_TENNOIDUNG
        defaultNoidungDTShouldBeFound("tennoidung.contains=" + DEFAULT_TENNOIDUNG);

        // Get all the noidungDTList where tennoidung contains UPDATED_TENNOIDUNG
        defaultNoidungDTShouldNotBeFound("tennoidung.contains=" + UPDATED_TENNOIDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSByTennoidungNotContainsSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where tennoidung does not contain DEFAULT_TENNOIDUNG
        defaultNoidungDTShouldNotBeFound("tennoidung.doesNotContain=" + DEFAULT_TENNOIDUNG);

        // Get all the noidungDTList where tennoidung does not contain UPDATED_TENNOIDUNG
        defaultNoidungDTShouldBeFound("tennoidung.doesNotContain=" + UPDATED_TENNOIDUNG);
    }


    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung equals to DEFAULT_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the noidungDTList where sudung equals to UPDATED_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung not equals to DEFAULT_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the noidungDTList where sudung not equals to UPDATED_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the noidungDTList where sudung equals to UPDATED_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung is not null
        defaultNoidungDTShouldBeFound("sudung.specified=true");

        // Get all the noidungDTList where sudung is null
        defaultNoidungDTShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the noidungDTList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the noidungDTList where sudung is less than or equal to SMALLER_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung is less than DEFAULT_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the noidungDTList where sudung is less than UPDATED_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllNoidungDTSBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        // Get all the noidungDTList where sudung is greater than DEFAULT_SUDUNG
        defaultNoidungDTShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the noidungDTList where sudung is greater than SMALLER_SUDUNG
        defaultNoidungDTShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllNoidungDTSByDutoanKPCTIsEqualToSomething() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);
        DutoanKPCT dutoanKPCT = DutoanKPCTResourceIT.createEntity(em);
        em.persist(dutoanKPCT);
        em.flush();
        noidungDT.addDutoanKPCT(dutoanKPCT);
        noidungDTRepository.saveAndFlush(noidungDT);
        Long dutoanKPCTId = dutoanKPCT.getId();

        // Get all the noidungDTList where dutoanKPCT equals to dutoanKPCTId
        defaultNoidungDTShouldBeFound("dutoanKPCTId.equals=" + dutoanKPCTId);

        // Get all the noidungDTList where dutoanKPCT equals to dutoanKPCTId + 1
        defaultNoidungDTShouldNotBeFound("dutoanKPCTId.equals=" + (dutoanKPCTId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoidungDTShouldBeFound(String filter) throws Exception {
        restNoidungDTMockMvc.perform(get("/api/noidung-dts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noidungDT.getId().intValue())))
            .andExpect(jsonPath("$.[*].tennoidung").value(hasItem(DEFAULT_TENNOIDUNG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restNoidungDTMockMvc.perform(get("/api/noidung-dts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoidungDTShouldNotBeFound(String filter) throws Exception {
        restNoidungDTMockMvc.perform(get("/api/noidung-dts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoidungDTMockMvc.perform(get("/api/noidung-dts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNoidungDT() throws Exception {
        // Get the noidungDT
        restNoidungDTMockMvc.perform(get("/api/noidung-dts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoidungDT() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        int databaseSizeBeforeUpdate = noidungDTRepository.findAll().size();

        // Update the noidungDT
        NoidungDT updatedNoidungDT = noidungDTRepository.findById(noidungDT.getId()).get();
        // Disconnect from session so that the updates on updatedNoidungDT are not directly saved in db
        em.detach(updatedNoidungDT);
        updatedNoidungDT
            .tennoidung(UPDATED_TENNOIDUNG)
            .sudung(UPDATED_SUDUNG);
        NoidungDTDTO noidungDTDTO = noidungDTMapper.toDto(updatedNoidungDT);

        restNoidungDTMockMvc.perform(put("/api/noidung-dts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungDTDTO)))
            .andExpect(status().isOk());

        // Validate the NoidungDT in the database
        List<NoidungDT> noidungDTList = noidungDTRepository.findAll();
        assertThat(noidungDTList).hasSize(databaseSizeBeforeUpdate);
        NoidungDT testNoidungDT = noidungDTList.get(noidungDTList.size() - 1);
        assertThat(testNoidungDT.getTennoidung()).isEqualTo(UPDATED_TENNOIDUNG);
        assertThat(testNoidungDT.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingNoidungDT() throws Exception {
        int databaseSizeBeforeUpdate = noidungDTRepository.findAll().size();

        // Create the NoidungDT
        NoidungDTDTO noidungDTDTO = noidungDTMapper.toDto(noidungDT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoidungDTMockMvc.perform(put("/api/noidung-dts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noidungDTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoidungDT in the database
        List<NoidungDT> noidungDTList = noidungDTRepository.findAll();
        assertThat(noidungDTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNoidungDT() throws Exception {
        // Initialize the database
        noidungDTRepository.saveAndFlush(noidungDT);

        int databaseSizeBeforeDelete = noidungDTRepository.findAll().size();

        // Delete the noidungDT
        restNoidungDTMockMvc.perform(delete("/api/noidung-dts/{id}", noidungDT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NoidungDT> noidungDTList = noidungDTRepository.findAll();
        assertThat(noidungDTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
