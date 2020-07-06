package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Donvi;
import com.api.backend.domain.Nhansu;
import com.api.backend.repository.DonviRepository;
import com.api.backend.service.DonviService;
import com.api.backend.service.dto.DonviDTO;
import com.api.backend.service.mapper.DonviMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DonviCriteria;
import com.api.backend.service.DonviQueryService;

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
 * Integration tests for the {@link DonviResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DonviResourceIT {

    private static final String DEFAULT_MADV = "AAAAAAAAAA";
    private static final String UPDATED_MADV = "BBBBBBBBBB";

    private static final String DEFAULT_TENDV = "AAAAAAAAAA";
    private static final String UPDATED_TENDV = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIENTHOAI = 1;
    private static final Integer UPDATED_DIENTHOAI = 2;
    private static final Integer SMALLER_DIENTHOAI = 1 - 1;

    private static final Integer DEFAULT_FAX = 1;
    private static final Integer UPDATED_FAX = 2;
    private static final Integer SMALLER_FAX = 1 - 1;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private DonviRepository donviRepository;

    @Autowired
    private DonviMapper donviMapper;

    @Autowired
    private DonviService donviService;

    @Autowired
    private DonviQueryService donviQueryService;

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

    private MockMvc restDonviMockMvc;

    private Donvi donvi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonviResource donviResource = new DonviResource(donviService, donviQueryService);
        this.restDonviMockMvc = MockMvcBuilders.standaloneSetup(donviResource)
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
    public static Donvi createEntity(EntityManager em) {
        Donvi donvi = new Donvi()
            .madv(DEFAULT_MADV)
            .tendv(DEFAULT_TENDV)
            .dienthoai(DEFAULT_DIENTHOAI)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .sudung(DEFAULT_SUDUNG);
        return donvi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donvi createUpdatedEntity(EntityManager em) {
        Donvi donvi = new Donvi()
            .madv(UPDATED_MADV)
            .tendv(UPDATED_TENDV)
            .dienthoai(UPDATED_DIENTHOAI)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .sudung(UPDATED_SUDUNG);
        return donvi;
    }

    @BeforeEach
    public void initTest() {
        donvi = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonvi() throws Exception {
        int databaseSizeBeforeCreate = donviRepository.findAll().size();

        // Create the Donvi
        DonviDTO donviDTO = donviMapper.toDto(donvi);
        restDonviMockMvc.perform(post("/api/donvis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donviDTO)))
            .andExpect(status().isCreated());

        // Validate the Donvi in the database
        List<Donvi> donviList = donviRepository.findAll();
        assertThat(donviList).hasSize(databaseSizeBeforeCreate + 1);
        Donvi testDonvi = donviList.get(donviList.size() - 1);
        assertThat(testDonvi.getMadv()).isEqualTo(DEFAULT_MADV);
        assertThat(testDonvi.getTendv()).isEqualTo(DEFAULT_TENDV);
        assertThat(testDonvi.getDienthoai()).isEqualTo(DEFAULT_DIENTHOAI);
        assertThat(testDonvi.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testDonvi.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDonvi.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createDonviWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donviRepository.findAll().size();

        // Create the Donvi with an existing ID
        donvi.setId(1L);
        DonviDTO donviDTO = donviMapper.toDto(donvi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonviMockMvc.perform(post("/api/donvis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donviDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Donvi in the database
        List<Donvi> donviList = donviRepository.findAll();
        assertThat(donviList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDonvis() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList
        restDonviMockMvc.perform(get("/api/donvis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donvi.getId().intValue())))
            .andExpect(jsonPath("$.[*].madv").value(hasItem(DEFAULT_MADV)))
            .andExpect(jsonPath("$.[*].tendv").value(hasItem(DEFAULT_TENDV)))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getDonvi() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get the donvi
        restDonviMockMvc.perform(get("/api/donvis/{id}", donvi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donvi.getId().intValue()))
            .andExpect(jsonPath("$.madv").value(DEFAULT_MADV))
            .andExpect(jsonPath("$.tendv").value(DEFAULT_TENDV))
            .andExpect(jsonPath("$.dienthoai").value(DEFAULT_DIENTHOAI))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getDonvisByIdFiltering() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        Long id = donvi.getId();

        defaultDonviShouldBeFound("id.equals=" + id);
        defaultDonviShouldNotBeFound("id.notEquals=" + id);

        defaultDonviShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDonviShouldNotBeFound("id.greaterThan=" + id);

        defaultDonviShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDonviShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDonvisByMadvIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where madv equals to DEFAULT_MADV
        defaultDonviShouldBeFound("madv.equals=" + DEFAULT_MADV);

        // Get all the donviList where madv equals to UPDATED_MADV
        defaultDonviShouldNotBeFound("madv.equals=" + UPDATED_MADV);
    }

    @Test
    @Transactional
    public void getAllDonvisByMadvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where madv not equals to DEFAULT_MADV
        defaultDonviShouldNotBeFound("madv.notEquals=" + DEFAULT_MADV);

        // Get all the donviList where madv not equals to UPDATED_MADV
        defaultDonviShouldBeFound("madv.notEquals=" + UPDATED_MADV);
    }

    @Test
    @Transactional
    public void getAllDonvisByMadvIsInShouldWork() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where madv in DEFAULT_MADV or UPDATED_MADV
        defaultDonviShouldBeFound("madv.in=" + DEFAULT_MADV + "," + UPDATED_MADV);

        // Get all the donviList where madv equals to UPDATED_MADV
        defaultDonviShouldNotBeFound("madv.in=" + UPDATED_MADV);
    }

    @Test
    @Transactional
    public void getAllDonvisByMadvIsNullOrNotNull() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where madv is not null
        defaultDonviShouldBeFound("madv.specified=true");

        // Get all the donviList where madv is null
        defaultDonviShouldNotBeFound("madv.specified=false");
    }
                @Test
    @Transactional
    public void getAllDonvisByMadvContainsSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where madv contains DEFAULT_MADV
        defaultDonviShouldBeFound("madv.contains=" + DEFAULT_MADV);

        // Get all the donviList where madv contains UPDATED_MADV
        defaultDonviShouldNotBeFound("madv.contains=" + UPDATED_MADV);
    }

    @Test
    @Transactional
    public void getAllDonvisByMadvNotContainsSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where madv does not contain DEFAULT_MADV
        defaultDonviShouldNotBeFound("madv.doesNotContain=" + DEFAULT_MADV);

        // Get all the donviList where madv does not contain UPDATED_MADV
        defaultDonviShouldBeFound("madv.doesNotContain=" + UPDATED_MADV);
    }


    @Test
    @Transactional
    public void getAllDonvisByTendvIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where tendv equals to DEFAULT_TENDV
        defaultDonviShouldBeFound("tendv.equals=" + DEFAULT_TENDV);

        // Get all the donviList where tendv equals to UPDATED_TENDV
        defaultDonviShouldNotBeFound("tendv.equals=" + UPDATED_TENDV);
    }

    @Test
    @Transactional
    public void getAllDonvisByTendvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where tendv not equals to DEFAULT_TENDV
        defaultDonviShouldNotBeFound("tendv.notEquals=" + DEFAULT_TENDV);

        // Get all the donviList where tendv not equals to UPDATED_TENDV
        defaultDonviShouldBeFound("tendv.notEquals=" + UPDATED_TENDV);
    }

    @Test
    @Transactional
    public void getAllDonvisByTendvIsInShouldWork() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where tendv in DEFAULT_TENDV or UPDATED_TENDV
        defaultDonviShouldBeFound("tendv.in=" + DEFAULT_TENDV + "," + UPDATED_TENDV);

        // Get all the donviList where tendv equals to UPDATED_TENDV
        defaultDonviShouldNotBeFound("tendv.in=" + UPDATED_TENDV);
    }

    @Test
    @Transactional
    public void getAllDonvisByTendvIsNullOrNotNull() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where tendv is not null
        defaultDonviShouldBeFound("tendv.specified=true");

        // Get all the donviList where tendv is null
        defaultDonviShouldNotBeFound("tendv.specified=false");
    }
                @Test
    @Transactional
    public void getAllDonvisByTendvContainsSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where tendv contains DEFAULT_TENDV
        defaultDonviShouldBeFound("tendv.contains=" + DEFAULT_TENDV);

        // Get all the donviList where tendv contains UPDATED_TENDV
        defaultDonviShouldNotBeFound("tendv.contains=" + UPDATED_TENDV);
    }

    @Test
    @Transactional
    public void getAllDonvisByTendvNotContainsSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where tendv does not contain DEFAULT_TENDV
        defaultDonviShouldNotBeFound("tendv.doesNotContain=" + DEFAULT_TENDV);

        // Get all the donviList where tendv does not contain UPDATED_TENDV
        defaultDonviShouldBeFound("tendv.doesNotContain=" + UPDATED_TENDV);
    }


    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai equals to DEFAULT_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.equals=" + DEFAULT_DIENTHOAI);

        // Get all the donviList where dienthoai equals to UPDATED_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.equals=" + UPDATED_DIENTHOAI);
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai not equals to DEFAULT_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.notEquals=" + DEFAULT_DIENTHOAI);

        // Get all the donviList where dienthoai not equals to UPDATED_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.notEquals=" + UPDATED_DIENTHOAI);
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsInShouldWork() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai in DEFAULT_DIENTHOAI or UPDATED_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.in=" + DEFAULT_DIENTHOAI + "," + UPDATED_DIENTHOAI);

        // Get all the donviList where dienthoai equals to UPDATED_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.in=" + UPDATED_DIENTHOAI);
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai is not null
        defaultDonviShouldBeFound("dienthoai.specified=true");

        // Get all the donviList where dienthoai is null
        defaultDonviShouldNotBeFound("dienthoai.specified=false");
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai is greater than or equal to DEFAULT_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.greaterThanOrEqual=" + DEFAULT_DIENTHOAI);

        // Get all the donviList where dienthoai is greater than or equal to UPDATED_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.greaterThanOrEqual=" + UPDATED_DIENTHOAI);
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai is less than or equal to DEFAULT_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.lessThanOrEqual=" + DEFAULT_DIENTHOAI);

        // Get all the donviList where dienthoai is less than or equal to SMALLER_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.lessThanOrEqual=" + SMALLER_DIENTHOAI);
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsLessThanSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai is less than DEFAULT_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.lessThan=" + DEFAULT_DIENTHOAI);

        // Get all the donviList where dienthoai is less than UPDATED_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.lessThan=" + UPDATED_DIENTHOAI);
    }

    @Test
    @Transactional
    public void getAllDonvisByDienthoaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where dienthoai is greater than DEFAULT_DIENTHOAI
        defaultDonviShouldNotBeFound("dienthoai.greaterThan=" + DEFAULT_DIENTHOAI);

        // Get all the donviList where dienthoai is greater than SMALLER_DIENTHOAI
        defaultDonviShouldBeFound("dienthoai.greaterThan=" + SMALLER_DIENTHOAI);
    }


    @Test
    @Transactional
    public void getAllDonvisByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax equals to DEFAULT_FAX
        defaultDonviShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the donviList where fax equals to UPDATED_FAX
        defaultDonviShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax not equals to DEFAULT_FAX
        defaultDonviShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the donviList where fax not equals to UPDATED_FAX
        defaultDonviShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultDonviShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the donviList where fax equals to UPDATED_FAX
        defaultDonviShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax is not null
        defaultDonviShouldBeFound("fax.specified=true");

        // Get all the donviList where fax is null
        defaultDonviShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax is greater than or equal to DEFAULT_FAX
        defaultDonviShouldBeFound("fax.greaterThanOrEqual=" + DEFAULT_FAX);

        // Get all the donviList where fax is greater than or equal to UPDATED_FAX
        defaultDonviShouldNotBeFound("fax.greaterThanOrEqual=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax is less than or equal to DEFAULT_FAX
        defaultDonviShouldBeFound("fax.lessThanOrEqual=" + DEFAULT_FAX);

        // Get all the donviList where fax is less than or equal to SMALLER_FAX
        defaultDonviShouldNotBeFound("fax.lessThanOrEqual=" + SMALLER_FAX);
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsLessThanSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax is less than DEFAULT_FAX
        defaultDonviShouldNotBeFound("fax.lessThan=" + DEFAULT_FAX);

        // Get all the donviList where fax is less than UPDATED_FAX
        defaultDonviShouldBeFound("fax.lessThan=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllDonvisByFaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where fax is greater than DEFAULT_FAX
        defaultDonviShouldNotBeFound("fax.greaterThan=" + DEFAULT_FAX);

        // Get all the donviList where fax is greater than SMALLER_FAX
        defaultDonviShouldBeFound("fax.greaterThan=" + SMALLER_FAX);
    }


    @Test
    @Transactional
    public void getAllDonvisByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where email equals to DEFAULT_EMAIL
        defaultDonviShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the donviList where email equals to UPDATED_EMAIL
        defaultDonviShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDonvisByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where email not equals to DEFAULT_EMAIL
        defaultDonviShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the donviList where email not equals to UPDATED_EMAIL
        defaultDonviShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDonvisByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDonviShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the donviList where email equals to UPDATED_EMAIL
        defaultDonviShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDonvisByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where email is not null
        defaultDonviShouldBeFound("email.specified=true");

        // Get all the donviList where email is null
        defaultDonviShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllDonvisByEmailContainsSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where email contains DEFAULT_EMAIL
        defaultDonviShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the donviList where email contains UPDATED_EMAIL
        defaultDonviShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDonvisByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where email does not contain DEFAULT_EMAIL
        defaultDonviShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the donviList where email does not contain UPDATED_EMAIL
        defaultDonviShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllDonvisBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung equals to DEFAULT_SUDUNG
        defaultDonviShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the donviList where sudung equals to UPDATED_SUDUNG
        defaultDonviShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung not equals to DEFAULT_SUDUNG
        defaultDonviShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the donviList where sudung not equals to UPDATED_SUDUNG
        defaultDonviShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultDonviShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the donviList where sudung equals to UPDATED_SUDUNG
        defaultDonviShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung is not null
        defaultDonviShouldBeFound("sudung.specified=true");

        // Get all the donviList where sudung is null
        defaultDonviShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultDonviShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the donviList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultDonviShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultDonviShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the donviList where sudung is less than or equal to SMALLER_SUDUNG
        defaultDonviShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung is less than DEFAULT_SUDUNG
        defaultDonviShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the donviList where sudung is less than UPDATED_SUDUNG
        defaultDonviShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDonvisBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        // Get all the donviList where sudung is greater than DEFAULT_SUDUNG
        defaultDonviShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the donviList where sudung is greater than SMALLER_SUDUNG
        defaultDonviShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllDonvisByNhansuIsEqualToSomething() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);
        Nhansu nhansu = NhansuResourceIT.createEntity(em);
        em.persist(nhansu);
        em.flush();
        donvi.addNhansu(nhansu);
        donviRepository.saveAndFlush(donvi);
        Long nhansuId = nhansu.getId();

        // Get all the donviList where nhansu equals to nhansuId
        defaultDonviShouldBeFound("nhansuId.equals=" + nhansuId);

        // Get all the donviList where nhansu equals to nhansuId + 1
        defaultDonviShouldNotBeFound("nhansuId.equals=" + (nhansuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDonviShouldBeFound(String filter) throws Exception {
        restDonviMockMvc.perform(get("/api/donvis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donvi.getId().intValue())))
            .andExpect(jsonPath("$.[*].madv").value(hasItem(DEFAULT_MADV)))
            .andExpect(jsonPath("$.[*].tendv").value(hasItem(DEFAULT_TENDV)))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restDonviMockMvc.perform(get("/api/donvis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDonviShouldNotBeFound(String filter) throws Exception {
        restDonviMockMvc.perform(get("/api/donvis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDonviMockMvc.perform(get("/api/donvis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDonvi() throws Exception {
        // Get the donvi
        restDonviMockMvc.perform(get("/api/donvis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonvi() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        int databaseSizeBeforeUpdate = donviRepository.findAll().size();

        // Update the donvi
        Donvi updatedDonvi = donviRepository.findById(donvi.getId()).get();
        // Disconnect from session so that the updates on updatedDonvi are not directly saved in db
        em.detach(updatedDonvi);
        updatedDonvi
            .madv(UPDATED_MADV)
            .tendv(UPDATED_TENDV)
            .dienthoai(UPDATED_DIENTHOAI)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .sudung(UPDATED_SUDUNG);
        DonviDTO donviDTO = donviMapper.toDto(updatedDonvi);

        restDonviMockMvc.perform(put("/api/donvis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donviDTO)))
            .andExpect(status().isOk());

        // Validate the Donvi in the database
        List<Donvi> donviList = donviRepository.findAll();
        assertThat(donviList).hasSize(databaseSizeBeforeUpdate);
        Donvi testDonvi = donviList.get(donviList.size() - 1);
        assertThat(testDonvi.getMadv()).isEqualTo(UPDATED_MADV);
        assertThat(testDonvi.getTendv()).isEqualTo(UPDATED_TENDV);
        assertThat(testDonvi.getDienthoai()).isEqualTo(UPDATED_DIENTHOAI);
        assertThat(testDonvi.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testDonvi.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonvi.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDonvi() throws Exception {
        int databaseSizeBeforeUpdate = donviRepository.findAll().size();

        // Create the Donvi
        DonviDTO donviDTO = donviMapper.toDto(donvi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonviMockMvc.perform(put("/api/donvis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donviDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Donvi in the database
        List<Donvi> donviList = donviRepository.findAll();
        assertThat(donviList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonvi() throws Exception {
        // Initialize the database
        donviRepository.saveAndFlush(donvi);

        int databaseSizeBeforeDelete = donviRepository.findAll().size();

        // Delete the donvi
        restDonviMockMvc.perform(delete("/api/donvis/{id}", donvi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Donvi> donviList = donviRepository.findAll();
        assertThat(donviList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
