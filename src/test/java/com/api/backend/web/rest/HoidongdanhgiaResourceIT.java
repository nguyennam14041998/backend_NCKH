package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Hoidongdanhgia;
import com.api.backend.domain.Detai;
import com.api.backend.domain.ThanhvienHD;
import com.api.backend.repository.HoidongdanhgiaRepository;
import com.api.backend.service.HoidongdanhgiaService;
import com.api.backend.service.dto.HoidongdanhgiaDTO;
import com.api.backend.service.mapper.HoidongdanhgiaMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.HoidongdanhgiaCriteria;
import com.api.backend.service.HoidongdanhgiaQueryService;

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
 * Integration tests for the {@link HoidongdanhgiaResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class HoidongdanhgiaResourceIT {

    private static final String DEFAULT_MAHOIDONG = "AAAAAAAAAA";
    private static final String UPDATED_MAHOIDONG = "BBBBBBBBBB";

    private static final String DEFAULT_TENHOIDONG = "AAAAAAAAAA";
    private static final String UPDATED_TENHOIDONG = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private HoidongdanhgiaRepository hoidongdanhgiaRepository;

    @Autowired
    private HoidongdanhgiaMapper hoidongdanhgiaMapper;

    @Autowired
    private HoidongdanhgiaService hoidongdanhgiaService;

    @Autowired
    private HoidongdanhgiaQueryService hoidongdanhgiaQueryService;

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

    private MockMvc restHoidongdanhgiaMockMvc;

    private Hoidongdanhgia hoidongdanhgia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HoidongdanhgiaResource hoidongdanhgiaResource = new HoidongdanhgiaResource(hoidongdanhgiaService, hoidongdanhgiaQueryService);
        this.restHoidongdanhgiaMockMvc = MockMvcBuilders.standaloneSetup(hoidongdanhgiaResource)
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
    public static Hoidongdanhgia createEntity(EntityManager em) {
        Hoidongdanhgia hoidongdanhgia = new Hoidongdanhgia()
            .mahoidong(DEFAULT_MAHOIDONG)
            .tenhoidong(DEFAULT_TENHOIDONG)
            .sudung(DEFAULT_SUDUNG);
        return hoidongdanhgia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hoidongdanhgia createUpdatedEntity(EntityManager em) {
        Hoidongdanhgia hoidongdanhgia = new Hoidongdanhgia()
            .mahoidong(UPDATED_MAHOIDONG)
            .tenhoidong(UPDATED_TENHOIDONG)
            .sudung(UPDATED_SUDUNG);
        return hoidongdanhgia;
    }

    @BeforeEach
    public void initTest() {
        hoidongdanhgia = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoidongdanhgia() throws Exception {
        int databaseSizeBeforeCreate = hoidongdanhgiaRepository.findAll().size();

        // Create the Hoidongdanhgia
        HoidongdanhgiaDTO hoidongdanhgiaDTO = hoidongdanhgiaMapper.toDto(hoidongdanhgia);
        restHoidongdanhgiaMockMvc.perform(post("/api/hoidongdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoidongdanhgiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Hoidongdanhgia in the database
        List<Hoidongdanhgia> hoidongdanhgiaList = hoidongdanhgiaRepository.findAll();
        assertThat(hoidongdanhgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Hoidongdanhgia testHoidongdanhgia = hoidongdanhgiaList.get(hoidongdanhgiaList.size() - 1);
        assertThat(testHoidongdanhgia.getMahoidong()).isEqualTo(DEFAULT_MAHOIDONG);
        assertThat(testHoidongdanhgia.getTenhoidong()).isEqualTo(DEFAULT_TENHOIDONG);
        assertThat(testHoidongdanhgia.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createHoidongdanhgiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hoidongdanhgiaRepository.findAll().size();

        // Create the Hoidongdanhgia with an existing ID
        hoidongdanhgia.setId(1L);
        HoidongdanhgiaDTO hoidongdanhgiaDTO = hoidongdanhgiaMapper.toDto(hoidongdanhgia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoidongdanhgiaMockMvc.perform(post("/api/hoidongdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoidongdanhgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hoidongdanhgia in the database
        List<Hoidongdanhgia> hoidongdanhgiaList = hoidongdanhgiaRepository.findAll();
        assertThat(hoidongdanhgiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHoidongdanhgias() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoidongdanhgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].mahoidong").value(hasItem(DEFAULT_MAHOIDONG)))
            .andExpect(jsonPath("$.[*].tenhoidong").value(hasItem(DEFAULT_TENHOIDONG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getHoidongdanhgia() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get the hoidongdanhgia
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias/{id}", hoidongdanhgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hoidongdanhgia.getId().intValue()))
            .andExpect(jsonPath("$.mahoidong").value(DEFAULT_MAHOIDONG))
            .andExpect(jsonPath("$.tenhoidong").value(DEFAULT_TENHOIDONG))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getHoidongdanhgiasByIdFiltering() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        Long id = hoidongdanhgia.getId();

        defaultHoidongdanhgiaShouldBeFound("id.equals=" + id);
        defaultHoidongdanhgiaShouldNotBeFound("id.notEquals=" + id);

        defaultHoidongdanhgiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHoidongdanhgiaShouldNotBeFound("id.greaterThan=" + id);

        defaultHoidongdanhgiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHoidongdanhgiaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHoidongdanhgiasByMahoidongIsEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where mahoidong equals to DEFAULT_MAHOIDONG
        defaultHoidongdanhgiaShouldBeFound("mahoidong.equals=" + DEFAULT_MAHOIDONG);

        // Get all the hoidongdanhgiaList where mahoidong equals to UPDATED_MAHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("mahoidong.equals=" + UPDATED_MAHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByMahoidongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where mahoidong not equals to DEFAULT_MAHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("mahoidong.notEquals=" + DEFAULT_MAHOIDONG);

        // Get all the hoidongdanhgiaList where mahoidong not equals to UPDATED_MAHOIDONG
        defaultHoidongdanhgiaShouldBeFound("mahoidong.notEquals=" + UPDATED_MAHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByMahoidongIsInShouldWork() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where mahoidong in DEFAULT_MAHOIDONG or UPDATED_MAHOIDONG
        defaultHoidongdanhgiaShouldBeFound("mahoidong.in=" + DEFAULT_MAHOIDONG + "," + UPDATED_MAHOIDONG);

        // Get all the hoidongdanhgiaList where mahoidong equals to UPDATED_MAHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("mahoidong.in=" + UPDATED_MAHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByMahoidongIsNullOrNotNull() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where mahoidong is not null
        defaultHoidongdanhgiaShouldBeFound("mahoidong.specified=true");

        // Get all the hoidongdanhgiaList where mahoidong is null
        defaultHoidongdanhgiaShouldNotBeFound("mahoidong.specified=false");
    }
                @Test
    @Transactional
    public void getAllHoidongdanhgiasByMahoidongContainsSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where mahoidong contains DEFAULT_MAHOIDONG
        defaultHoidongdanhgiaShouldBeFound("mahoidong.contains=" + DEFAULT_MAHOIDONG);

        // Get all the hoidongdanhgiaList where mahoidong contains UPDATED_MAHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("mahoidong.contains=" + UPDATED_MAHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByMahoidongNotContainsSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where mahoidong does not contain DEFAULT_MAHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("mahoidong.doesNotContain=" + DEFAULT_MAHOIDONG);

        // Get all the hoidongdanhgiaList where mahoidong does not contain UPDATED_MAHOIDONG
        defaultHoidongdanhgiaShouldBeFound("mahoidong.doesNotContain=" + UPDATED_MAHOIDONG);
    }


    @Test
    @Transactional
    public void getAllHoidongdanhgiasByTenhoidongIsEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where tenhoidong equals to DEFAULT_TENHOIDONG
        defaultHoidongdanhgiaShouldBeFound("tenhoidong.equals=" + DEFAULT_TENHOIDONG);

        // Get all the hoidongdanhgiaList where tenhoidong equals to UPDATED_TENHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("tenhoidong.equals=" + UPDATED_TENHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByTenhoidongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where tenhoidong not equals to DEFAULT_TENHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("tenhoidong.notEquals=" + DEFAULT_TENHOIDONG);

        // Get all the hoidongdanhgiaList where tenhoidong not equals to UPDATED_TENHOIDONG
        defaultHoidongdanhgiaShouldBeFound("tenhoidong.notEquals=" + UPDATED_TENHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByTenhoidongIsInShouldWork() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where tenhoidong in DEFAULT_TENHOIDONG or UPDATED_TENHOIDONG
        defaultHoidongdanhgiaShouldBeFound("tenhoidong.in=" + DEFAULT_TENHOIDONG + "," + UPDATED_TENHOIDONG);

        // Get all the hoidongdanhgiaList where tenhoidong equals to UPDATED_TENHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("tenhoidong.in=" + UPDATED_TENHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByTenhoidongIsNullOrNotNull() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where tenhoidong is not null
        defaultHoidongdanhgiaShouldBeFound("tenhoidong.specified=true");

        // Get all the hoidongdanhgiaList where tenhoidong is null
        defaultHoidongdanhgiaShouldNotBeFound("tenhoidong.specified=false");
    }
                @Test
    @Transactional
    public void getAllHoidongdanhgiasByTenhoidongContainsSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where tenhoidong contains DEFAULT_TENHOIDONG
        defaultHoidongdanhgiaShouldBeFound("tenhoidong.contains=" + DEFAULT_TENHOIDONG);

        // Get all the hoidongdanhgiaList where tenhoidong contains UPDATED_TENHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("tenhoidong.contains=" + UPDATED_TENHOIDONG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasByTenhoidongNotContainsSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where tenhoidong does not contain DEFAULT_TENHOIDONG
        defaultHoidongdanhgiaShouldNotBeFound("tenhoidong.doesNotContain=" + DEFAULT_TENHOIDONG);

        // Get all the hoidongdanhgiaList where tenhoidong does not contain UPDATED_TENHOIDONG
        defaultHoidongdanhgiaShouldBeFound("tenhoidong.doesNotContain=" + UPDATED_TENHOIDONG);
    }


    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung equals to DEFAULT_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung equals to UPDATED_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung not equals to DEFAULT_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung not equals to UPDATED_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung equals to UPDATED_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung is not null
        defaultHoidongdanhgiaShouldBeFound("sudung.specified=true");

        // Get all the hoidongdanhgiaList where sudung is null
        defaultHoidongdanhgiaShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung is less than or equal to SMALLER_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung is less than DEFAULT_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung is less than UPDATED_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllHoidongdanhgiasBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        // Get all the hoidongdanhgiaList where sudung is greater than DEFAULT_SUDUNG
        defaultHoidongdanhgiaShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the hoidongdanhgiaList where sudung is greater than SMALLER_SUDUNG
        defaultHoidongdanhgiaShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllHoidongdanhgiasByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        hoidongdanhgia.addDetai(detai);
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);
        Long detaiId = detai.getId();

        // Get all the hoidongdanhgiaList where detai equals to detaiId
        defaultHoidongdanhgiaShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the hoidongdanhgiaList where detai equals to detaiId + 1
        defaultHoidongdanhgiaShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }


    @Test
    @Transactional
    public void getAllHoidongdanhgiasByThanhvienHDIsEqualToSomething() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);
        ThanhvienHD thanhvienHD = ThanhvienHDResourceIT.createEntity(em);
        em.persist(thanhvienHD);
        em.flush();
        hoidongdanhgia.addThanhvienHD(thanhvienHD);
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);
        Long thanhvienHDId = thanhvienHD.getId();

        // Get all the hoidongdanhgiaList where thanhvienHD equals to thanhvienHDId
        defaultHoidongdanhgiaShouldBeFound("thanhvienHDId.equals=" + thanhvienHDId);

        // Get all the hoidongdanhgiaList where thanhvienHD equals to thanhvienHDId + 1
        defaultHoidongdanhgiaShouldNotBeFound("thanhvienHDId.equals=" + (thanhvienHDId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHoidongdanhgiaShouldBeFound(String filter) throws Exception {
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoidongdanhgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].mahoidong").value(hasItem(DEFAULT_MAHOIDONG)))
            .andExpect(jsonPath("$.[*].tenhoidong").value(hasItem(DEFAULT_TENHOIDONG)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHoidongdanhgiaShouldNotBeFound(String filter) throws Exception {
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHoidongdanhgia() throws Exception {
        // Get the hoidongdanhgia
        restHoidongdanhgiaMockMvc.perform(get("/api/hoidongdanhgias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoidongdanhgia() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        int databaseSizeBeforeUpdate = hoidongdanhgiaRepository.findAll().size();

        // Update the hoidongdanhgia
        Hoidongdanhgia updatedHoidongdanhgia = hoidongdanhgiaRepository.findById(hoidongdanhgia.getId()).get();
        // Disconnect from session so that the updates on updatedHoidongdanhgia are not directly saved in db
        em.detach(updatedHoidongdanhgia);
        updatedHoidongdanhgia
            .mahoidong(UPDATED_MAHOIDONG)
            .tenhoidong(UPDATED_TENHOIDONG)
            .sudung(UPDATED_SUDUNG);
        HoidongdanhgiaDTO hoidongdanhgiaDTO = hoidongdanhgiaMapper.toDto(updatedHoidongdanhgia);

        restHoidongdanhgiaMockMvc.perform(put("/api/hoidongdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoidongdanhgiaDTO)))
            .andExpect(status().isOk());

        // Validate the Hoidongdanhgia in the database
        List<Hoidongdanhgia> hoidongdanhgiaList = hoidongdanhgiaRepository.findAll();
        assertThat(hoidongdanhgiaList).hasSize(databaseSizeBeforeUpdate);
        Hoidongdanhgia testHoidongdanhgia = hoidongdanhgiaList.get(hoidongdanhgiaList.size() - 1);
        assertThat(testHoidongdanhgia.getMahoidong()).isEqualTo(UPDATED_MAHOIDONG);
        assertThat(testHoidongdanhgia.getTenhoidong()).isEqualTo(UPDATED_TENHOIDONG);
        assertThat(testHoidongdanhgia.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingHoidongdanhgia() throws Exception {
        int databaseSizeBeforeUpdate = hoidongdanhgiaRepository.findAll().size();

        // Create the Hoidongdanhgia
        HoidongdanhgiaDTO hoidongdanhgiaDTO = hoidongdanhgiaMapper.toDto(hoidongdanhgia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoidongdanhgiaMockMvc.perform(put("/api/hoidongdanhgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoidongdanhgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hoidongdanhgia in the database
        List<Hoidongdanhgia> hoidongdanhgiaList = hoidongdanhgiaRepository.findAll();
        assertThat(hoidongdanhgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHoidongdanhgia() throws Exception {
        // Initialize the database
        hoidongdanhgiaRepository.saveAndFlush(hoidongdanhgia);

        int databaseSizeBeforeDelete = hoidongdanhgiaRepository.findAll().size();

        // Delete the hoidongdanhgia
        restHoidongdanhgiaMockMvc.perform(delete("/api/hoidongdanhgias/{id}", hoidongdanhgia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hoidongdanhgia> hoidongdanhgiaList = hoidongdanhgiaRepository.findAll();
        assertThat(hoidongdanhgiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
