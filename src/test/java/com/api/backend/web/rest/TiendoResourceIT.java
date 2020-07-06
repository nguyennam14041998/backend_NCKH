package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Tiendo;
import com.api.backend.domain.Upfile;
import com.api.backend.domain.Detai;
import com.api.backend.repository.TiendoRepository;
import com.api.backend.service.TiendoService;
import com.api.backend.service.dto.TiendoDTO;
import com.api.backend.service.mapper.TiendoMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.TiendoCriteria;
import com.api.backend.service.TiendoQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.api.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TiendoResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class TiendoResourceIT {

    private static final String DEFAULT_MATIENDO = "AAAAAAAAAA";
    private static final String UPDATED_MATIENDO = "BBBBBBBBBB";

    private static final String DEFAULT_KYBAOCAO = "AAAAAAAAAA";
    private static final String UPDATED_KYBAOCAO = "BBBBBBBBBB";

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_THOIGIANBATDAU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOIGIANBATDAU = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THOIGIANBATDAU = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_THOIGIANKETTHUC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOIGIANKETTHUC = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THOIGIANKETTHUC = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_KHOILUONGHOANTHANH = 1;
    private static final Integer UPDATED_KHOILUONGHOANTHANH = 2;
    private static final Integer SMALLER_KHOILUONGHOANTHANH = 1 - 1;

    private static final String DEFAULT_GHICHU = "AAAAAAAAAA";
    private static final String UPDATED_GHICHU = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private TiendoRepository tiendoRepository;

    @Autowired
    private TiendoMapper tiendoMapper;

    @Autowired
    private TiendoService tiendoService;

    @Autowired
    private TiendoQueryService tiendoQueryService;

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

    private MockMvc restTiendoMockMvc;

    private Tiendo tiendo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TiendoResource tiendoResource = new TiendoResource(tiendoService, tiendoQueryService);
        this.restTiendoMockMvc = MockMvcBuilders.standaloneSetup(tiendoResource)
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
    public static Tiendo createEntity(EntityManager em) {
        Tiendo tiendo = new Tiendo()
            .matiendo(DEFAULT_MATIENDO)
            .kybaocao(DEFAULT_KYBAOCAO)
            .noidung(DEFAULT_NOIDUNG)
            .thoigianbatdau(DEFAULT_THOIGIANBATDAU)
            .thoigianketthuc(DEFAULT_THOIGIANKETTHUC)
            .khoiluonghoanthanh(DEFAULT_KHOILUONGHOANTHANH)
            .ghichu(DEFAULT_GHICHU)
            .sudung(DEFAULT_SUDUNG);
        return tiendo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tiendo createUpdatedEntity(EntityManager em) {
        Tiendo tiendo = new Tiendo()
            .matiendo(UPDATED_MATIENDO)
            .kybaocao(UPDATED_KYBAOCAO)
            .noidung(UPDATED_NOIDUNG)
            .thoigianbatdau(UPDATED_THOIGIANBATDAU)
            .thoigianketthuc(UPDATED_THOIGIANKETTHUC)
            .khoiluonghoanthanh(UPDATED_KHOILUONGHOANTHANH)
            .ghichu(UPDATED_GHICHU)
            .sudung(UPDATED_SUDUNG);
        return tiendo;
    }

    @BeforeEach
    public void initTest() {
        tiendo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiendo() throws Exception {
        int databaseSizeBeforeCreate = tiendoRepository.findAll().size();

        // Create the Tiendo
        TiendoDTO tiendoDTO = tiendoMapper.toDto(tiendo);
        restTiendoMockMvc.perform(post("/api/tiendos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendoDTO)))
            .andExpect(status().isCreated());

        // Validate the Tiendo in the database
        List<Tiendo> tiendoList = tiendoRepository.findAll();
        assertThat(tiendoList).hasSize(databaseSizeBeforeCreate + 1);
        Tiendo testTiendo = tiendoList.get(tiendoList.size() - 1);
        assertThat(testTiendo.getMatiendo()).isEqualTo(DEFAULT_MATIENDO);
        assertThat(testTiendo.getKybaocao()).isEqualTo(DEFAULT_KYBAOCAO);
        assertThat(testTiendo.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testTiendo.getThoigianbatdau()).isEqualTo(DEFAULT_THOIGIANBATDAU);
        assertThat(testTiendo.getThoigianketthuc()).isEqualTo(DEFAULT_THOIGIANKETTHUC);
        assertThat(testTiendo.getKhoiluonghoanthanh()).isEqualTo(DEFAULT_KHOILUONGHOANTHANH);
        assertThat(testTiendo.getGhichu()).isEqualTo(DEFAULT_GHICHU);
        assertThat(testTiendo.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createTiendoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiendoRepository.findAll().size();

        // Create the Tiendo with an existing ID
        tiendo.setId(1L);
        TiendoDTO tiendoDTO = tiendoMapper.toDto(tiendo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiendoMockMvc.perform(post("/api/tiendos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tiendo in the database
        List<Tiendo> tiendoList = tiendoRepository.findAll();
        assertThat(tiendoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTiendos() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList
        restTiendoMockMvc.perform(get("/api/tiendos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiendo.getId().intValue())))
            .andExpect(jsonPath("$.[*].matiendo").value(hasItem(DEFAULT_MATIENDO)))
            .andExpect(jsonPath("$.[*].kybaocao").value(hasItem(DEFAULT_KYBAOCAO)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].thoigianbatdau").value(hasItem(DEFAULT_THOIGIANBATDAU.toString())))
            .andExpect(jsonPath("$.[*].thoigianketthuc").value(hasItem(DEFAULT_THOIGIANKETTHUC.toString())))
            .andExpect(jsonPath("$.[*].khoiluonghoanthanh").value(hasItem(DEFAULT_KHOILUONGHOANTHANH)))
            .andExpect(jsonPath("$.[*].ghichu").value(hasItem(DEFAULT_GHICHU)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getTiendo() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get the tiendo
        restTiendoMockMvc.perform(get("/api/tiendos/{id}", tiendo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tiendo.getId().intValue()))
            .andExpect(jsonPath("$.matiendo").value(DEFAULT_MATIENDO))
            .andExpect(jsonPath("$.kybaocao").value(DEFAULT_KYBAOCAO))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.thoigianbatdau").value(DEFAULT_THOIGIANBATDAU.toString()))
            .andExpect(jsonPath("$.thoigianketthuc").value(DEFAULT_THOIGIANKETTHUC.toString()))
            .andExpect(jsonPath("$.khoiluonghoanthanh").value(DEFAULT_KHOILUONGHOANTHANH))
            .andExpect(jsonPath("$.ghichu").value(DEFAULT_GHICHU))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getTiendosByIdFiltering() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        Long id = tiendo.getId();

        defaultTiendoShouldBeFound("id.equals=" + id);
        defaultTiendoShouldNotBeFound("id.notEquals=" + id);

        defaultTiendoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTiendoShouldNotBeFound("id.greaterThan=" + id);

        defaultTiendoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTiendoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTiendosByMatiendoIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where matiendo equals to DEFAULT_MATIENDO
        defaultTiendoShouldBeFound("matiendo.equals=" + DEFAULT_MATIENDO);

        // Get all the tiendoList where matiendo equals to UPDATED_MATIENDO
        defaultTiendoShouldNotBeFound("matiendo.equals=" + UPDATED_MATIENDO);
    }

    @Test
    @Transactional
    public void getAllTiendosByMatiendoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where matiendo not equals to DEFAULT_MATIENDO
        defaultTiendoShouldNotBeFound("matiendo.notEquals=" + DEFAULT_MATIENDO);

        // Get all the tiendoList where matiendo not equals to UPDATED_MATIENDO
        defaultTiendoShouldBeFound("matiendo.notEquals=" + UPDATED_MATIENDO);
    }

    @Test
    @Transactional
    public void getAllTiendosByMatiendoIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where matiendo in DEFAULT_MATIENDO or UPDATED_MATIENDO
        defaultTiendoShouldBeFound("matiendo.in=" + DEFAULT_MATIENDO + "," + UPDATED_MATIENDO);

        // Get all the tiendoList where matiendo equals to UPDATED_MATIENDO
        defaultTiendoShouldNotBeFound("matiendo.in=" + UPDATED_MATIENDO);
    }

    @Test
    @Transactional
    public void getAllTiendosByMatiendoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where matiendo is not null
        defaultTiendoShouldBeFound("matiendo.specified=true");

        // Get all the tiendoList where matiendo is null
        defaultTiendoShouldNotBeFound("matiendo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTiendosByMatiendoContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where matiendo contains DEFAULT_MATIENDO
        defaultTiendoShouldBeFound("matiendo.contains=" + DEFAULT_MATIENDO);

        // Get all the tiendoList where matiendo contains UPDATED_MATIENDO
        defaultTiendoShouldNotBeFound("matiendo.contains=" + UPDATED_MATIENDO);
    }

    @Test
    @Transactional
    public void getAllTiendosByMatiendoNotContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where matiendo does not contain DEFAULT_MATIENDO
        defaultTiendoShouldNotBeFound("matiendo.doesNotContain=" + DEFAULT_MATIENDO);

        // Get all the tiendoList where matiendo does not contain UPDATED_MATIENDO
        defaultTiendoShouldBeFound("matiendo.doesNotContain=" + UPDATED_MATIENDO);
    }


    @Test
    @Transactional
    public void getAllTiendosByKybaocaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where kybaocao equals to DEFAULT_KYBAOCAO
        defaultTiendoShouldBeFound("kybaocao.equals=" + DEFAULT_KYBAOCAO);

        // Get all the tiendoList where kybaocao equals to UPDATED_KYBAOCAO
        defaultTiendoShouldNotBeFound("kybaocao.equals=" + UPDATED_KYBAOCAO);
    }

    @Test
    @Transactional
    public void getAllTiendosByKybaocaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where kybaocao not equals to DEFAULT_KYBAOCAO
        defaultTiendoShouldNotBeFound("kybaocao.notEquals=" + DEFAULT_KYBAOCAO);

        // Get all the tiendoList where kybaocao not equals to UPDATED_KYBAOCAO
        defaultTiendoShouldBeFound("kybaocao.notEquals=" + UPDATED_KYBAOCAO);
    }

    @Test
    @Transactional
    public void getAllTiendosByKybaocaoIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where kybaocao in DEFAULT_KYBAOCAO or UPDATED_KYBAOCAO
        defaultTiendoShouldBeFound("kybaocao.in=" + DEFAULT_KYBAOCAO + "," + UPDATED_KYBAOCAO);

        // Get all the tiendoList where kybaocao equals to UPDATED_KYBAOCAO
        defaultTiendoShouldNotBeFound("kybaocao.in=" + UPDATED_KYBAOCAO);
    }

    @Test
    @Transactional
    public void getAllTiendosByKybaocaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where kybaocao is not null
        defaultTiendoShouldBeFound("kybaocao.specified=true");

        // Get all the tiendoList where kybaocao is null
        defaultTiendoShouldNotBeFound("kybaocao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTiendosByKybaocaoContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where kybaocao contains DEFAULT_KYBAOCAO
        defaultTiendoShouldBeFound("kybaocao.contains=" + DEFAULT_KYBAOCAO);

        // Get all the tiendoList where kybaocao contains UPDATED_KYBAOCAO
        defaultTiendoShouldNotBeFound("kybaocao.contains=" + UPDATED_KYBAOCAO);
    }

    @Test
    @Transactional
    public void getAllTiendosByKybaocaoNotContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where kybaocao does not contain DEFAULT_KYBAOCAO
        defaultTiendoShouldNotBeFound("kybaocao.doesNotContain=" + DEFAULT_KYBAOCAO);

        // Get all the tiendoList where kybaocao does not contain UPDATED_KYBAOCAO
        defaultTiendoShouldBeFound("kybaocao.doesNotContain=" + UPDATED_KYBAOCAO);
    }


    @Test
    @Transactional
    public void getAllTiendosByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where noidung equals to DEFAULT_NOIDUNG
        defaultTiendoShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the tiendoList where noidung equals to UPDATED_NOIDUNG
        defaultTiendoShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where noidung not equals to DEFAULT_NOIDUNG
        defaultTiendoShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the tiendoList where noidung not equals to UPDATED_NOIDUNG
        defaultTiendoShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultTiendoShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the tiendoList where noidung equals to UPDATED_NOIDUNG
        defaultTiendoShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where noidung is not null
        defaultTiendoShouldBeFound("noidung.specified=true");

        // Get all the tiendoList where noidung is null
        defaultTiendoShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllTiendosByNoidungContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where noidung contains DEFAULT_NOIDUNG
        defaultTiendoShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the tiendoList where noidung contains UPDATED_NOIDUNG
        defaultTiendoShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where noidung does not contain DEFAULT_NOIDUNG
        defaultTiendoShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the tiendoList where noidung does not contain UPDATED_NOIDUNG
        defaultTiendoShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau equals to DEFAULT_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.equals=" + DEFAULT_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau equals to UPDATED_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.equals=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau not equals to DEFAULT_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.notEquals=" + DEFAULT_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau not equals to UPDATED_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.notEquals=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau in DEFAULT_THOIGIANBATDAU or UPDATED_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.in=" + DEFAULT_THOIGIANBATDAU + "," + UPDATED_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau equals to UPDATED_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.in=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau is not null
        defaultTiendoShouldBeFound("thoigianbatdau.specified=true");

        // Get all the tiendoList where thoigianbatdau is null
        defaultTiendoShouldNotBeFound("thoigianbatdau.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau is greater than or equal to DEFAULT_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.greaterThanOrEqual=" + DEFAULT_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau is greater than or equal to UPDATED_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.greaterThanOrEqual=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau is less than or equal to DEFAULT_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.lessThanOrEqual=" + DEFAULT_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau is less than or equal to SMALLER_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.lessThanOrEqual=" + SMALLER_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsLessThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau is less than DEFAULT_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.lessThan=" + DEFAULT_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau is less than UPDATED_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.lessThan=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianbatdauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianbatdau is greater than DEFAULT_THOIGIANBATDAU
        defaultTiendoShouldNotBeFound("thoigianbatdau.greaterThan=" + DEFAULT_THOIGIANBATDAU);

        // Get all the tiendoList where thoigianbatdau is greater than SMALLER_THOIGIANBATDAU
        defaultTiendoShouldBeFound("thoigianbatdau.greaterThan=" + SMALLER_THOIGIANBATDAU);
    }


    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc equals to DEFAULT_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.equals=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc equals to UPDATED_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.equals=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc not equals to DEFAULT_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.notEquals=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc not equals to UPDATED_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.notEquals=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc in DEFAULT_THOIGIANKETTHUC or UPDATED_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.in=" + DEFAULT_THOIGIANKETTHUC + "," + UPDATED_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc equals to UPDATED_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.in=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc is not null
        defaultTiendoShouldBeFound("thoigianketthuc.specified=true");

        // Get all the tiendoList where thoigianketthuc is null
        defaultTiendoShouldNotBeFound("thoigianketthuc.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc is greater than or equal to DEFAULT_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.greaterThanOrEqual=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc is greater than or equal to UPDATED_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.greaterThanOrEqual=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc is less than or equal to DEFAULT_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.lessThanOrEqual=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc is less than or equal to SMALLER_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.lessThanOrEqual=" + SMALLER_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsLessThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc is less than DEFAULT_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.lessThan=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc is less than UPDATED_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.lessThan=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllTiendosByThoigianketthucIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where thoigianketthuc is greater than DEFAULT_THOIGIANKETTHUC
        defaultTiendoShouldNotBeFound("thoigianketthuc.greaterThan=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the tiendoList where thoigianketthuc is greater than SMALLER_THOIGIANKETTHUC
        defaultTiendoShouldBeFound("thoigianketthuc.greaterThan=" + SMALLER_THOIGIANKETTHUC);
    }


    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh equals to DEFAULT_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.equals=" + DEFAULT_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh equals to UPDATED_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.equals=" + UPDATED_KHOILUONGHOANTHANH);
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh not equals to DEFAULT_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.notEquals=" + DEFAULT_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh not equals to UPDATED_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.notEquals=" + UPDATED_KHOILUONGHOANTHANH);
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh in DEFAULT_KHOILUONGHOANTHANH or UPDATED_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.in=" + DEFAULT_KHOILUONGHOANTHANH + "," + UPDATED_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh equals to UPDATED_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.in=" + UPDATED_KHOILUONGHOANTHANH);
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh is not null
        defaultTiendoShouldBeFound("khoiluonghoanthanh.specified=true");

        // Get all the tiendoList where khoiluonghoanthanh is null
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh is greater than or equal to DEFAULT_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.greaterThanOrEqual=" + DEFAULT_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh is greater than or equal to UPDATED_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.greaterThanOrEqual=" + UPDATED_KHOILUONGHOANTHANH);
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh is less than or equal to DEFAULT_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.lessThanOrEqual=" + DEFAULT_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh is less than or equal to SMALLER_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.lessThanOrEqual=" + SMALLER_KHOILUONGHOANTHANH);
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsLessThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh is less than DEFAULT_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.lessThan=" + DEFAULT_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh is less than UPDATED_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.lessThan=" + UPDATED_KHOILUONGHOANTHANH);
    }

    @Test
    @Transactional
    public void getAllTiendosByKhoiluonghoanthanhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where khoiluonghoanthanh is greater than DEFAULT_KHOILUONGHOANTHANH
        defaultTiendoShouldNotBeFound("khoiluonghoanthanh.greaterThan=" + DEFAULT_KHOILUONGHOANTHANH);

        // Get all the tiendoList where khoiluonghoanthanh is greater than SMALLER_KHOILUONGHOANTHANH
        defaultTiendoShouldBeFound("khoiluonghoanthanh.greaterThan=" + SMALLER_KHOILUONGHOANTHANH);
    }


    @Test
    @Transactional
    public void getAllTiendosByGhichuIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where ghichu equals to DEFAULT_GHICHU
        defaultTiendoShouldBeFound("ghichu.equals=" + DEFAULT_GHICHU);

        // Get all the tiendoList where ghichu equals to UPDATED_GHICHU
        defaultTiendoShouldNotBeFound("ghichu.equals=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllTiendosByGhichuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where ghichu not equals to DEFAULT_GHICHU
        defaultTiendoShouldNotBeFound("ghichu.notEquals=" + DEFAULT_GHICHU);

        // Get all the tiendoList where ghichu not equals to UPDATED_GHICHU
        defaultTiendoShouldBeFound("ghichu.notEquals=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllTiendosByGhichuIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where ghichu in DEFAULT_GHICHU or UPDATED_GHICHU
        defaultTiendoShouldBeFound("ghichu.in=" + DEFAULT_GHICHU + "," + UPDATED_GHICHU);

        // Get all the tiendoList where ghichu equals to UPDATED_GHICHU
        defaultTiendoShouldNotBeFound("ghichu.in=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllTiendosByGhichuIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where ghichu is not null
        defaultTiendoShouldBeFound("ghichu.specified=true");

        // Get all the tiendoList where ghichu is null
        defaultTiendoShouldNotBeFound("ghichu.specified=false");
    }
                @Test
    @Transactional
    public void getAllTiendosByGhichuContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where ghichu contains DEFAULT_GHICHU
        defaultTiendoShouldBeFound("ghichu.contains=" + DEFAULT_GHICHU);

        // Get all the tiendoList where ghichu contains UPDATED_GHICHU
        defaultTiendoShouldNotBeFound("ghichu.contains=" + UPDATED_GHICHU);
    }

    @Test
    @Transactional
    public void getAllTiendosByGhichuNotContainsSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where ghichu does not contain DEFAULT_GHICHU
        defaultTiendoShouldNotBeFound("ghichu.doesNotContain=" + DEFAULT_GHICHU);

        // Get all the tiendoList where ghichu does not contain UPDATED_GHICHU
        defaultTiendoShouldBeFound("ghichu.doesNotContain=" + UPDATED_GHICHU);
    }


    @Test
    @Transactional
    public void getAllTiendosBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung equals to DEFAULT_SUDUNG
        defaultTiendoShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the tiendoList where sudung equals to UPDATED_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung not equals to DEFAULT_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the tiendoList where sudung not equals to UPDATED_SUDUNG
        defaultTiendoShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultTiendoShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the tiendoList where sudung equals to UPDATED_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung is not null
        defaultTiendoShouldBeFound("sudung.specified=true");

        // Get all the tiendoList where sudung is null
        defaultTiendoShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultTiendoShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the tiendoList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultTiendoShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the tiendoList where sudung is less than or equal to SMALLER_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung is less than DEFAULT_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the tiendoList where sudung is less than UPDATED_SUDUNG
        defaultTiendoShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllTiendosBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        // Get all the tiendoList where sudung is greater than DEFAULT_SUDUNG
        defaultTiendoShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the tiendoList where sudung is greater than SMALLER_SUDUNG
        defaultTiendoShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllTiendosByUpfileIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);
        Upfile upfile = UpfileResourceIT.createEntity(em);
        em.persist(upfile);
        em.flush();
        tiendo.addUpfile(upfile);
        tiendoRepository.saveAndFlush(tiendo);
        Long upfileId = upfile.getId();

        // Get all the tiendoList where upfile equals to upfileId
        defaultTiendoShouldBeFound("upfileId.equals=" + upfileId);

        // Get all the tiendoList where upfile equals to upfileId + 1
        defaultTiendoShouldNotBeFound("upfileId.equals=" + (upfileId + 1));
    }


    @Test
    @Transactional
    public void getAllTiendosByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        tiendo.setDetai(detai);
        tiendoRepository.saveAndFlush(tiendo);
        Long detaiId = detai.getId();

        // Get all the tiendoList where detai equals to detaiId
        defaultTiendoShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the tiendoList where detai equals to detaiId + 1
        defaultTiendoShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTiendoShouldBeFound(String filter) throws Exception {
        restTiendoMockMvc.perform(get("/api/tiendos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiendo.getId().intValue())))
            .andExpect(jsonPath("$.[*].matiendo").value(hasItem(DEFAULT_MATIENDO)))
            .andExpect(jsonPath("$.[*].kybaocao").value(hasItem(DEFAULT_KYBAOCAO)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].thoigianbatdau").value(hasItem(DEFAULT_THOIGIANBATDAU.toString())))
            .andExpect(jsonPath("$.[*].thoigianketthuc").value(hasItem(DEFAULT_THOIGIANKETTHUC.toString())))
            .andExpect(jsonPath("$.[*].khoiluonghoanthanh").value(hasItem(DEFAULT_KHOILUONGHOANTHANH)))
            .andExpect(jsonPath("$.[*].ghichu").value(hasItem(DEFAULT_GHICHU)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restTiendoMockMvc.perform(get("/api/tiendos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTiendoShouldNotBeFound(String filter) throws Exception {
        restTiendoMockMvc.perform(get("/api/tiendos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTiendoMockMvc.perform(get("/api/tiendos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTiendo() throws Exception {
        // Get the tiendo
        restTiendoMockMvc.perform(get("/api/tiendos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiendo() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        int databaseSizeBeforeUpdate = tiendoRepository.findAll().size();

        // Update the tiendo
        Tiendo updatedTiendo = tiendoRepository.findById(tiendo.getId()).get();
        // Disconnect from session so that the updates on updatedTiendo are not directly saved in db
        em.detach(updatedTiendo);
        updatedTiendo
            .matiendo(UPDATED_MATIENDO)
            .kybaocao(UPDATED_KYBAOCAO)
            .noidung(UPDATED_NOIDUNG)
            .thoigianbatdau(UPDATED_THOIGIANBATDAU)
            .thoigianketthuc(UPDATED_THOIGIANKETTHUC)
            .khoiluonghoanthanh(UPDATED_KHOILUONGHOANTHANH)
            .ghichu(UPDATED_GHICHU)
            .sudung(UPDATED_SUDUNG);
        TiendoDTO tiendoDTO = tiendoMapper.toDto(updatedTiendo);

        restTiendoMockMvc.perform(put("/api/tiendos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendoDTO)))
            .andExpect(status().isOk());

        // Validate the Tiendo in the database
        List<Tiendo> tiendoList = tiendoRepository.findAll();
        assertThat(tiendoList).hasSize(databaseSizeBeforeUpdate);
        Tiendo testTiendo = tiendoList.get(tiendoList.size() - 1);
        assertThat(testTiendo.getMatiendo()).isEqualTo(UPDATED_MATIENDO);
        assertThat(testTiendo.getKybaocao()).isEqualTo(UPDATED_KYBAOCAO);
        assertThat(testTiendo.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testTiendo.getThoigianbatdau()).isEqualTo(UPDATED_THOIGIANBATDAU);
        assertThat(testTiendo.getThoigianketthuc()).isEqualTo(UPDATED_THOIGIANKETTHUC);
        assertThat(testTiendo.getKhoiluonghoanthanh()).isEqualTo(UPDATED_KHOILUONGHOANTHANH);
        assertThat(testTiendo.getGhichu()).isEqualTo(UPDATED_GHICHU);
        assertThat(testTiendo.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingTiendo() throws Exception {
        int databaseSizeBeforeUpdate = tiendoRepository.findAll().size();

        // Create the Tiendo
        TiendoDTO tiendoDTO = tiendoMapper.toDto(tiendo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiendoMockMvc.perform(put("/api/tiendos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiendoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tiendo in the database
        List<Tiendo> tiendoList = tiendoRepository.findAll();
        assertThat(tiendoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTiendo() throws Exception {
        // Initialize the database
        tiendoRepository.saveAndFlush(tiendo);

        int databaseSizeBeforeDelete = tiendoRepository.findAll().size();

        // Delete the tiendo
        restTiendoMockMvc.perform(delete("/api/tiendos/{id}", tiendo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tiendo> tiendoList = tiendoRepository.findAll();
        assertThat(tiendoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
