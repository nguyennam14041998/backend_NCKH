package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Detai;
import com.api.backend.domain.Tiendo;
import com.api.backend.domain.Upfile;
import com.api.backend.domain.Nhansuthamgia;
import com.api.backend.domain.Nguonkinhphi;
import com.api.backend.domain.Coquanphoihopthamgia;
import com.api.backend.domain.Danhgia;
import com.api.backend.domain.DutoanKP;
import com.api.backend.domain.Danhsachbaibao;
import com.api.backend.domain.Linhvuc;
import com.api.backend.domain.Capdetai;
import com.api.backend.domain.Hoidongdanhgia;
import com.api.backend.domain.Chunhiem;
import com.api.backend.repository.DetaiRepository;
import com.api.backend.service.DetaiService;
import com.api.backend.service.dto.DetaiDTO;
import com.api.backend.service.mapper.DetaiMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.DetaiCriteria;
import com.api.backend.service.DetaiQueryService;

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
 * Integration tests for the {@link DetaiResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class DetaiResourceIT {

    private static final String DEFAULT_MA = "AAAAAAAAAA";
    private static final String UPDATED_MA = "BBBBBBBBBB";

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_THOIGIANTAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOIGIANTAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THOIGIANTAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_THOIGIANBATDAU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOIGIANBATDAU = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THOIGIANBATDAU = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_THOIGIANKETTHUC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOIGIANKETTHUC = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THOIGIANKETTHUC = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MUCTIEU = "AAAAAAAAAA";
    private static final String UPDATED_MUCTIEU = "BBBBBBBBBB";

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final String DEFAULT_TINHCAPTHIET = "AAAAAAAAAA";
    private static final String UPDATED_TINHCAPTHIET = "BBBBBBBBBB";

    private static final String DEFAULT_NGUOIHUONGDAN = "AAAAAAAAAA";
    private static final String UPDATED_NGUOIHUONGDAN = "BBBBBBBBBB";

    private static final String DEFAULT_KETQUA = "AAAAAAAAAA";
    private static final String UPDATED_KETQUA = "BBBBBBBBBB";

    private static final Integer DEFAULT_XEPLOAI = 1;
    private static final Integer UPDATED_XEPLOAI = 2;
    private static final Integer SMALLER_XEPLOAI = 1 - 1;

    private static final Integer DEFAULT_PHANLOAI = 1;
    private static final Integer UPDATED_PHANLOAI = 2;
    private static final Integer SMALLER_PHANLOAI = 1 - 1;

    private static final Integer DEFAULT_TRANGTHAI = 1;
    private static final Integer UPDATED_TRANGTHAI = 2;
    private static final Integer SMALLER_TRANGTHAI = 1 - 1;

    private static final String DEFAULT_TENCHUNHIEM = "AAAAAAAAAA";
    private static final String UPDATED_TENCHUNHIEM = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUDUNG = 1;
    private static final Integer UPDATED_SUDUNG = 2;
    private static final Integer SMALLER_SUDUNG = 1 - 1;

    @Autowired
    private DetaiRepository detaiRepository;

    @Autowired
    private DetaiMapper detaiMapper;

    @Autowired
    private DetaiService detaiService;

    @Autowired
    private DetaiQueryService detaiQueryService;

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

    private MockMvc restDetaiMockMvc;

    private Detai detai;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetaiResource detaiResource = new DetaiResource(detaiService, detaiQueryService);
        this.restDetaiMockMvc = MockMvcBuilders.standaloneSetup(detaiResource)
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
    public static Detai createEntity(EntityManager em) {
        Detai detai = new Detai()
            .ma(DEFAULT_MA)
            .ten(DEFAULT_TEN)
            .thoigiantao(DEFAULT_THOIGIANTAO)
            .thoigianbatdau(DEFAULT_THOIGIANBATDAU)
            .thoigianketthuc(DEFAULT_THOIGIANKETTHUC)
            .muctieu(DEFAULT_MUCTIEU)
            .noidung(DEFAULT_NOIDUNG)
            .tinhcapthiet(DEFAULT_TINHCAPTHIET)
            .nguoihuongdan(DEFAULT_NGUOIHUONGDAN)
            .ketqua(DEFAULT_KETQUA)
            .xeploai(DEFAULT_XEPLOAI)
            .phanloai(DEFAULT_PHANLOAI)
            .trangthai(DEFAULT_TRANGTHAI)
            .tenchunhiem(DEFAULT_TENCHUNHIEM)
            .sudung(DEFAULT_SUDUNG);
        return detai;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Detai createUpdatedEntity(EntityManager em) {
        Detai detai = new Detai()
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN)
            .thoigiantao(UPDATED_THOIGIANTAO)
            .thoigianbatdau(UPDATED_THOIGIANBATDAU)
            .thoigianketthuc(UPDATED_THOIGIANKETTHUC)
            .muctieu(UPDATED_MUCTIEU)
            .noidung(UPDATED_NOIDUNG)
            .tinhcapthiet(UPDATED_TINHCAPTHIET)
            .nguoihuongdan(UPDATED_NGUOIHUONGDAN)
            .ketqua(UPDATED_KETQUA)
            .xeploai(UPDATED_XEPLOAI)
            .phanloai(UPDATED_PHANLOAI)
            .trangthai(UPDATED_TRANGTHAI)
            .tenchunhiem(UPDATED_TENCHUNHIEM)
            .sudung(UPDATED_SUDUNG);
        return detai;
    }

    @BeforeEach
    public void initTest() {
        detai = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetai() throws Exception {
        int databaseSizeBeforeCreate = detaiRepository.findAll().size();

        // Create the Detai
        DetaiDTO detaiDTO = detaiMapper.toDto(detai);
        restDetaiMockMvc.perform(post("/api/detais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detaiDTO)))
            .andExpect(status().isCreated());

        // Validate the Detai in the database
        List<Detai> detaiList = detaiRepository.findAll();
        assertThat(detaiList).hasSize(databaseSizeBeforeCreate + 1);
        Detai testDetai = detaiList.get(detaiList.size() - 1);
        assertThat(testDetai.getMa()).isEqualTo(DEFAULT_MA);
        assertThat(testDetai.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testDetai.getThoigiantao()).isEqualTo(DEFAULT_THOIGIANTAO);
        assertThat(testDetai.getThoigianbatdau()).isEqualTo(DEFAULT_THOIGIANBATDAU);
        assertThat(testDetai.getThoigianketthuc()).isEqualTo(DEFAULT_THOIGIANKETTHUC);
        assertThat(testDetai.getMuctieu()).isEqualTo(DEFAULT_MUCTIEU);
        assertThat(testDetai.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testDetai.getTinhcapthiet()).isEqualTo(DEFAULT_TINHCAPTHIET);
        assertThat(testDetai.getNguoihuongdan()).isEqualTo(DEFAULT_NGUOIHUONGDAN);
        assertThat(testDetai.getKetqua()).isEqualTo(DEFAULT_KETQUA);
        assertThat(testDetai.getXeploai()).isEqualTo(DEFAULT_XEPLOAI);
        assertThat(testDetai.getPhanloai()).isEqualTo(DEFAULT_PHANLOAI);
        assertThat(testDetai.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
        assertThat(testDetai.getTenchunhiem()).isEqualTo(DEFAULT_TENCHUNHIEM);
        assertThat(testDetai.getSudung()).isEqualTo(DEFAULT_SUDUNG);
    }

    @Test
    @Transactional
    public void createDetaiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detaiRepository.findAll().size();

        // Create the Detai with an existing ID
        detai.setId(1L);
        DetaiDTO detaiDTO = detaiMapper.toDto(detai);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetaiMockMvc.perform(post("/api/detais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detaiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Detai in the database
        List<Detai> detaiList = detaiRepository.findAll();
        assertThat(detaiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDetais() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList
        restDetaiMockMvc.perform(get("/api/detais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detai.getId().intValue())))
            .andExpect(jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA)))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].thoigiantao").value(hasItem(DEFAULT_THOIGIANTAO.toString())))
            .andExpect(jsonPath("$.[*].thoigianbatdau").value(hasItem(DEFAULT_THOIGIANBATDAU.toString())))
            .andExpect(jsonPath("$.[*].thoigianketthuc").value(hasItem(DEFAULT_THOIGIANKETTHUC.toString())))
            .andExpect(jsonPath("$.[*].muctieu").value(hasItem(DEFAULT_MUCTIEU)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].tinhcapthiet").value(hasItem(DEFAULT_TINHCAPTHIET)))
            .andExpect(jsonPath("$.[*].nguoihuongdan").value(hasItem(DEFAULT_NGUOIHUONGDAN)))
            .andExpect(jsonPath("$.[*].ketqua").value(hasItem(DEFAULT_KETQUA)))
            .andExpect(jsonPath("$.[*].xeploai").value(hasItem(DEFAULT_XEPLOAI)))
            .andExpect(jsonPath("$.[*].phanloai").value(hasItem(DEFAULT_PHANLOAI)))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI)))
            .andExpect(jsonPath("$.[*].tenchunhiem").value(hasItem(DEFAULT_TENCHUNHIEM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));
    }
    
    @Test
    @Transactional
    public void getDetai() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get the detai
        restDetaiMockMvc.perform(get("/api/detais/{id}", detai.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detai.getId().intValue()))
            .andExpect(jsonPath("$.ma").value(DEFAULT_MA))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.thoigiantao").value(DEFAULT_THOIGIANTAO.toString()))
            .andExpect(jsonPath("$.thoigianbatdau").value(DEFAULT_THOIGIANBATDAU.toString()))
            .andExpect(jsonPath("$.thoigianketthuc").value(DEFAULT_THOIGIANKETTHUC.toString()))
            .andExpect(jsonPath("$.muctieu").value(DEFAULT_MUCTIEU))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG))
            .andExpect(jsonPath("$.tinhcapthiet").value(DEFAULT_TINHCAPTHIET))
            .andExpect(jsonPath("$.nguoihuongdan").value(DEFAULT_NGUOIHUONGDAN))
            .andExpect(jsonPath("$.ketqua").value(DEFAULT_KETQUA))
            .andExpect(jsonPath("$.xeploai").value(DEFAULT_XEPLOAI))
            .andExpect(jsonPath("$.phanloai").value(DEFAULT_PHANLOAI))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI))
            .andExpect(jsonPath("$.tenchunhiem").value(DEFAULT_TENCHUNHIEM))
            .andExpect(jsonPath("$.sudung").value(DEFAULT_SUDUNG));
    }


    @Test
    @Transactional
    public void getDetaisByIdFiltering() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        Long id = detai.getId();

        defaultDetaiShouldBeFound("id.equals=" + id);
        defaultDetaiShouldNotBeFound("id.notEquals=" + id);

        defaultDetaiShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetaiShouldNotBeFound("id.greaterThan=" + id);

        defaultDetaiShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetaiShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDetaisByMaIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ma equals to DEFAULT_MA
        defaultDetaiShouldBeFound("ma.equals=" + DEFAULT_MA);

        // Get all the detaiList where ma equals to UPDATED_MA
        defaultDetaiShouldNotBeFound("ma.equals=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDetaisByMaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ma not equals to DEFAULT_MA
        defaultDetaiShouldNotBeFound("ma.notEquals=" + DEFAULT_MA);

        // Get all the detaiList where ma not equals to UPDATED_MA
        defaultDetaiShouldBeFound("ma.notEquals=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDetaisByMaIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ma in DEFAULT_MA or UPDATED_MA
        defaultDetaiShouldBeFound("ma.in=" + DEFAULT_MA + "," + UPDATED_MA);

        // Get all the detaiList where ma equals to UPDATED_MA
        defaultDetaiShouldNotBeFound("ma.in=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDetaisByMaIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ma is not null
        defaultDetaiShouldBeFound("ma.specified=true");

        // Get all the detaiList where ma is null
        defaultDetaiShouldNotBeFound("ma.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByMaContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ma contains DEFAULT_MA
        defaultDetaiShouldBeFound("ma.contains=" + DEFAULT_MA);

        // Get all the detaiList where ma contains UPDATED_MA
        defaultDetaiShouldNotBeFound("ma.contains=" + UPDATED_MA);
    }

    @Test
    @Transactional
    public void getAllDetaisByMaNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ma does not contain DEFAULT_MA
        defaultDetaiShouldNotBeFound("ma.doesNotContain=" + DEFAULT_MA);

        // Get all the detaiList where ma does not contain UPDATED_MA
        defaultDetaiShouldBeFound("ma.doesNotContain=" + UPDATED_MA);
    }


    @Test
    @Transactional
    public void getAllDetaisByTenIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ten equals to DEFAULT_TEN
        defaultDetaiShouldBeFound("ten.equals=" + DEFAULT_TEN);

        // Get all the detaiList where ten equals to UPDATED_TEN
        defaultDetaiShouldNotBeFound("ten.equals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ten not equals to DEFAULT_TEN
        defaultDetaiShouldNotBeFound("ten.notEquals=" + DEFAULT_TEN);

        // Get all the detaiList where ten not equals to UPDATED_TEN
        defaultDetaiShouldBeFound("ten.notEquals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ten in DEFAULT_TEN or UPDATED_TEN
        defaultDetaiShouldBeFound("ten.in=" + DEFAULT_TEN + "," + UPDATED_TEN);

        // Get all the detaiList where ten equals to UPDATED_TEN
        defaultDetaiShouldNotBeFound("ten.in=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ten is not null
        defaultDetaiShouldBeFound("ten.specified=true");

        // Get all the detaiList where ten is null
        defaultDetaiShouldNotBeFound("ten.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByTenContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ten contains DEFAULT_TEN
        defaultDetaiShouldBeFound("ten.contains=" + DEFAULT_TEN);

        // Get all the detaiList where ten contains UPDATED_TEN
        defaultDetaiShouldNotBeFound("ten.contains=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ten does not contain DEFAULT_TEN
        defaultDetaiShouldNotBeFound("ten.doesNotContain=" + DEFAULT_TEN);

        // Get all the detaiList where ten does not contain UPDATED_TEN
        defaultDetaiShouldBeFound("ten.doesNotContain=" + UPDATED_TEN);
    }


    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao equals to DEFAULT_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.equals=" + DEFAULT_THOIGIANTAO);

        // Get all the detaiList where thoigiantao equals to UPDATED_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.equals=" + UPDATED_THOIGIANTAO);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao not equals to DEFAULT_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.notEquals=" + DEFAULT_THOIGIANTAO);

        // Get all the detaiList where thoigiantao not equals to UPDATED_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.notEquals=" + UPDATED_THOIGIANTAO);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao in DEFAULT_THOIGIANTAO or UPDATED_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.in=" + DEFAULT_THOIGIANTAO + "," + UPDATED_THOIGIANTAO);

        // Get all the detaiList where thoigiantao equals to UPDATED_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.in=" + UPDATED_THOIGIANTAO);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao is not null
        defaultDetaiShouldBeFound("thoigiantao.specified=true");

        // Get all the detaiList where thoigiantao is null
        defaultDetaiShouldNotBeFound("thoigiantao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao is greater than or equal to DEFAULT_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.greaterThanOrEqual=" + DEFAULT_THOIGIANTAO);

        // Get all the detaiList where thoigiantao is greater than or equal to UPDATED_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.greaterThanOrEqual=" + UPDATED_THOIGIANTAO);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao is less than or equal to DEFAULT_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.lessThanOrEqual=" + DEFAULT_THOIGIANTAO);

        // Get all the detaiList where thoigiantao is less than or equal to SMALLER_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.lessThanOrEqual=" + SMALLER_THOIGIANTAO);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao is less than DEFAULT_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.lessThan=" + DEFAULT_THOIGIANTAO);

        // Get all the detaiList where thoigiantao is less than UPDATED_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.lessThan=" + UPDATED_THOIGIANTAO);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigiantaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigiantao is greater than DEFAULT_THOIGIANTAO
        defaultDetaiShouldNotBeFound("thoigiantao.greaterThan=" + DEFAULT_THOIGIANTAO);

        // Get all the detaiList where thoigiantao is greater than SMALLER_THOIGIANTAO
        defaultDetaiShouldBeFound("thoigiantao.greaterThan=" + SMALLER_THOIGIANTAO);
    }


    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau equals to DEFAULT_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.equals=" + DEFAULT_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau equals to UPDATED_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.equals=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau not equals to DEFAULT_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.notEquals=" + DEFAULT_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau not equals to UPDATED_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.notEquals=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau in DEFAULT_THOIGIANBATDAU or UPDATED_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.in=" + DEFAULT_THOIGIANBATDAU + "," + UPDATED_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau equals to UPDATED_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.in=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau is not null
        defaultDetaiShouldBeFound("thoigianbatdau.specified=true");

        // Get all the detaiList where thoigianbatdau is null
        defaultDetaiShouldNotBeFound("thoigianbatdau.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau is greater than or equal to DEFAULT_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.greaterThanOrEqual=" + DEFAULT_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau is greater than or equal to UPDATED_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.greaterThanOrEqual=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau is less than or equal to DEFAULT_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.lessThanOrEqual=" + DEFAULT_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau is less than or equal to SMALLER_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.lessThanOrEqual=" + SMALLER_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau is less than DEFAULT_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.lessThan=" + DEFAULT_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau is less than UPDATED_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.lessThan=" + UPDATED_THOIGIANBATDAU);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianbatdauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianbatdau is greater than DEFAULT_THOIGIANBATDAU
        defaultDetaiShouldNotBeFound("thoigianbatdau.greaterThan=" + DEFAULT_THOIGIANBATDAU);

        // Get all the detaiList where thoigianbatdau is greater than SMALLER_THOIGIANBATDAU
        defaultDetaiShouldBeFound("thoigianbatdau.greaterThan=" + SMALLER_THOIGIANBATDAU);
    }


    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc equals to DEFAULT_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.equals=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc equals to UPDATED_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.equals=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc not equals to DEFAULT_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.notEquals=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc not equals to UPDATED_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.notEquals=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc in DEFAULT_THOIGIANKETTHUC or UPDATED_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.in=" + DEFAULT_THOIGIANKETTHUC + "," + UPDATED_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc equals to UPDATED_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.in=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc is not null
        defaultDetaiShouldBeFound("thoigianketthuc.specified=true");

        // Get all the detaiList where thoigianketthuc is null
        defaultDetaiShouldNotBeFound("thoigianketthuc.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc is greater than or equal to DEFAULT_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.greaterThanOrEqual=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc is greater than or equal to UPDATED_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.greaterThanOrEqual=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc is less than or equal to DEFAULT_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.lessThanOrEqual=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc is less than or equal to SMALLER_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.lessThanOrEqual=" + SMALLER_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc is less than DEFAULT_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.lessThan=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc is less than UPDATED_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.lessThan=" + UPDATED_THOIGIANKETTHUC);
    }

    @Test
    @Transactional
    public void getAllDetaisByThoigianketthucIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where thoigianketthuc is greater than DEFAULT_THOIGIANKETTHUC
        defaultDetaiShouldNotBeFound("thoigianketthuc.greaterThan=" + DEFAULT_THOIGIANKETTHUC);

        // Get all the detaiList where thoigianketthuc is greater than SMALLER_THOIGIANKETTHUC
        defaultDetaiShouldBeFound("thoigianketthuc.greaterThan=" + SMALLER_THOIGIANKETTHUC);
    }


    @Test
    @Transactional
    public void getAllDetaisByMuctieuIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where muctieu equals to DEFAULT_MUCTIEU
        defaultDetaiShouldBeFound("muctieu.equals=" + DEFAULT_MUCTIEU);

        // Get all the detaiList where muctieu equals to UPDATED_MUCTIEU
        defaultDetaiShouldNotBeFound("muctieu.equals=" + UPDATED_MUCTIEU);
    }

    @Test
    @Transactional
    public void getAllDetaisByMuctieuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where muctieu not equals to DEFAULT_MUCTIEU
        defaultDetaiShouldNotBeFound("muctieu.notEquals=" + DEFAULT_MUCTIEU);

        // Get all the detaiList where muctieu not equals to UPDATED_MUCTIEU
        defaultDetaiShouldBeFound("muctieu.notEquals=" + UPDATED_MUCTIEU);
    }

    @Test
    @Transactional
    public void getAllDetaisByMuctieuIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where muctieu in DEFAULT_MUCTIEU or UPDATED_MUCTIEU
        defaultDetaiShouldBeFound("muctieu.in=" + DEFAULT_MUCTIEU + "," + UPDATED_MUCTIEU);

        // Get all the detaiList where muctieu equals to UPDATED_MUCTIEU
        defaultDetaiShouldNotBeFound("muctieu.in=" + UPDATED_MUCTIEU);
    }

    @Test
    @Transactional
    public void getAllDetaisByMuctieuIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where muctieu is not null
        defaultDetaiShouldBeFound("muctieu.specified=true");

        // Get all the detaiList where muctieu is null
        defaultDetaiShouldNotBeFound("muctieu.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByMuctieuContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where muctieu contains DEFAULT_MUCTIEU
        defaultDetaiShouldBeFound("muctieu.contains=" + DEFAULT_MUCTIEU);

        // Get all the detaiList where muctieu contains UPDATED_MUCTIEU
        defaultDetaiShouldNotBeFound("muctieu.contains=" + UPDATED_MUCTIEU);
    }

    @Test
    @Transactional
    public void getAllDetaisByMuctieuNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where muctieu does not contain DEFAULT_MUCTIEU
        defaultDetaiShouldNotBeFound("muctieu.doesNotContain=" + DEFAULT_MUCTIEU);

        // Get all the detaiList where muctieu does not contain UPDATED_MUCTIEU
        defaultDetaiShouldBeFound("muctieu.doesNotContain=" + UPDATED_MUCTIEU);
    }


    @Test
    @Transactional
    public void getAllDetaisByNoidungIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where noidung equals to DEFAULT_NOIDUNG
        defaultDetaiShouldBeFound("noidung.equals=" + DEFAULT_NOIDUNG);

        // Get all the detaiList where noidung equals to UPDATED_NOIDUNG
        defaultDetaiShouldNotBeFound("noidung.equals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisByNoidungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where noidung not equals to DEFAULT_NOIDUNG
        defaultDetaiShouldNotBeFound("noidung.notEquals=" + DEFAULT_NOIDUNG);

        // Get all the detaiList where noidung not equals to UPDATED_NOIDUNG
        defaultDetaiShouldBeFound("noidung.notEquals=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisByNoidungIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where noidung in DEFAULT_NOIDUNG or UPDATED_NOIDUNG
        defaultDetaiShouldBeFound("noidung.in=" + DEFAULT_NOIDUNG + "," + UPDATED_NOIDUNG);

        // Get all the detaiList where noidung equals to UPDATED_NOIDUNG
        defaultDetaiShouldNotBeFound("noidung.in=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisByNoidungIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where noidung is not null
        defaultDetaiShouldBeFound("noidung.specified=true");

        // Get all the detaiList where noidung is null
        defaultDetaiShouldNotBeFound("noidung.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByNoidungContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where noidung contains DEFAULT_NOIDUNG
        defaultDetaiShouldBeFound("noidung.contains=" + DEFAULT_NOIDUNG);

        // Get all the detaiList where noidung contains UPDATED_NOIDUNG
        defaultDetaiShouldNotBeFound("noidung.contains=" + UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisByNoidungNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where noidung does not contain DEFAULT_NOIDUNG
        defaultDetaiShouldNotBeFound("noidung.doesNotContain=" + DEFAULT_NOIDUNG);

        // Get all the detaiList where noidung does not contain UPDATED_NOIDUNG
        defaultDetaiShouldBeFound("noidung.doesNotContain=" + UPDATED_NOIDUNG);
    }


    @Test
    @Transactional
    public void getAllDetaisByTinhcapthietIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tinhcapthiet equals to DEFAULT_TINHCAPTHIET
        defaultDetaiShouldBeFound("tinhcapthiet.equals=" + DEFAULT_TINHCAPTHIET);

        // Get all the detaiList where tinhcapthiet equals to UPDATED_TINHCAPTHIET
        defaultDetaiShouldNotBeFound("tinhcapthiet.equals=" + UPDATED_TINHCAPTHIET);
    }

    @Test
    @Transactional
    public void getAllDetaisByTinhcapthietIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tinhcapthiet not equals to DEFAULT_TINHCAPTHIET
        defaultDetaiShouldNotBeFound("tinhcapthiet.notEquals=" + DEFAULT_TINHCAPTHIET);

        // Get all the detaiList where tinhcapthiet not equals to UPDATED_TINHCAPTHIET
        defaultDetaiShouldBeFound("tinhcapthiet.notEquals=" + UPDATED_TINHCAPTHIET);
    }

    @Test
    @Transactional
    public void getAllDetaisByTinhcapthietIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tinhcapthiet in DEFAULT_TINHCAPTHIET or UPDATED_TINHCAPTHIET
        defaultDetaiShouldBeFound("tinhcapthiet.in=" + DEFAULT_TINHCAPTHIET + "," + UPDATED_TINHCAPTHIET);

        // Get all the detaiList where tinhcapthiet equals to UPDATED_TINHCAPTHIET
        defaultDetaiShouldNotBeFound("tinhcapthiet.in=" + UPDATED_TINHCAPTHIET);
    }

    @Test
    @Transactional
    public void getAllDetaisByTinhcapthietIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tinhcapthiet is not null
        defaultDetaiShouldBeFound("tinhcapthiet.specified=true");

        // Get all the detaiList where tinhcapthiet is null
        defaultDetaiShouldNotBeFound("tinhcapthiet.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByTinhcapthietContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tinhcapthiet contains DEFAULT_TINHCAPTHIET
        defaultDetaiShouldBeFound("tinhcapthiet.contains=" + DEFAULT_TINHCAPTHIET);

        // Get all the detaiList where tinhcapthiet contains UPDATED_TINHCAPTHIET
        defaultDetaiShouldNotBeFound("tinhcapthiet.contains=" + UPDATED_TINHCAPTHIET);
    }

    @Test
    @Transactional
    public void getAllDetaisByTinhcapthietNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tinhcapthiet does not contain DEFAULT_TINHCAPTHIET
        defaultDetaiShouldNotBeFound("tinhcapthiet.doesNotContain=" + DEFAULT_TINHCAPTHIET);

        // Get all the detaiList where tinhcapthiet does not contain UPDATED_TINHCAPTHIET
        defaultDetaiShouldBeFound("tinhcapthiet.doesNotContain=" + UPDATED_TINHCAPTHIET);
    }


    @Test
    @Transactional
    public void getAllDetaisByNguoihuongdanIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where nguoihuongdan equals to DEFAULT_NGUOIHUONGDAN
        defaultDetaiShouldBeFound("nguoihuongdan.equals=" + DEFAULT_NGUOIHUONGDAN);

        // Get all the detaiList where nguoihuongdan equals to UPDATED_NGUOIHUONGDAN
        defaultDetaiShouldNotBeFound("nguoihuongdan.equals=" + UPDATED_NGUOIHUONGDAN);
    }

    @Test
    @Transactional
    public void getAllDetaisByNguoihuongdanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where nguoihuongdan not equals to DEFAULT_NGUOIHUONGDAN
        defaultDetaiShouldNotBeFound("nguoihuongdan.notEquals=" + DEFAULT_NGUOIHUONGDAN);

        // Get all the detaiList where nguoihuongdan not equals to UPDATED_NGUOIHUONGDAN
        defaultDetaiShouldBeFound("nguoihuongdan.notEquals=" + UPDATED_NGUOIHUONGDAN);
    }

    @Test
    @Transactional
    public void getAllDetaisByNguoihuongdanIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where nguoihuongdan in DEFAULT_NGUOIHUONGDAN or UPDATED_NGUOIHUONGDAN
        defaultDetaiShouldBeFound("nguoihuongdan.in=" + DEFAULT_NGUOIHUONGDAN + "," + UPDATED_NGUOIHUONGDAN);

        // Get all the detaiList where nguoihuongdan equals to UPDATED_NGUOIHUONGDAN
        defaultDetaiShouldNotBeFound("nguoihuongdan.in=" + UPDATED_NGUOIHUONGDAN);
    }

    @Test
    @Transactional
    public void getAllDetaisByNguoihuongdanIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where nguoihuongdan is not null
        defaultDetaiShouldBeFound("nguoihuongdan.specified=true");

        // Get all the detaiList where nguoihuongdan is null
        defaultDetaiShouldNotBeFound("nguoihuongdan.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByNguoihuongdanContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where nguoihuongdan contains DEFAULT_NGUOIHUONGDAN
        defaultDetaiShouldBeFound("nguoihuongdan.contains=" + DEFAULT_NGUOIHUONGDAN);

        // Get all the detaiList where nguoihuongdan contains UPDATED_NGUOIHUONGDAN
        defaultDetaiShouldNotBeFound("nguoihuongdan.contains=" + UPDATED_NGUOIHUONGDAN);
    }

    @Test
    @Transactional
    public void getAllDetaisByNguoihuongdanNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where nguoihuongdan does not contain DEFAULT_NGUOIHUONGDAN
        defaultDetaiShouldNotBeFound("nguoihuongdan.doesNotContain=" + DEFAULT_NGUOIHUONGDAN);

        // Get all the detaiList where nguoihuongdan does not contain UPDATED_NGUOIHUONGDAN
        defaultDetaiShouldBeFound("nguoihuongdan.doesNotContain=" + UPDATED_NGUOIHUONGDAN);
    }


    @Test
    @Transactional
    public void getAllDetaisByKetquaIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ketqua equals to DEFAULT_KETQUA
        defaultDetaiShouldBeFound("ketqua.equals=" + DEFAULT_KETQUA);

        // Get all the detaiList where ketqua equals to UPDATED_KETQUA
        defaultDetaiShouldNotBeFound("ketqua.equals=" + UPDATED_KETQUA);
    }

    @Test
    @Transactional
    public void getAllDetaisByKetquaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ketqua not equals to DEFAULT_KETQUA
        defaultDetaiShouldNotBeFound("ketqua.notEquals=" + DEFAULT_KETQUA);

        // Get all the detaiList where ketqua not equals to UPDATED_KETQUA
        defaultDetaiShouldBeFound("ketqua.notEquals=" + UPDATED_KETQUA);
    }

    @Test
    @Transactional
    public void getAllDetaisByKetquaIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ketqua in DEFAULT_KETQUA or UPDATED_KETQUA
        defaultDetaiShouldBeFound("ketqua.in=" + DEFAULT_KETQUA + "," + UPDATED_KETQUA);

        // Get all the detaiList where ketqua equals to UPDATED_KETQUA
        defaultDetaiShouldNotBeFound("ketqua.in=" + UPDATED_KETQUA);
    }

    @Test
    @Transactional
    public void getAllDetaisByKetquaIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ketqua is not null
        defaultDetaiShouldBeFound("ketqua.specified=true");

        // Get all the detaiList where ketqua is null
        defaultDetaiShouldNotBeFound("ketqua.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByKetquaContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ketqua contains DEFAULT_KETQUA
        defaultDetaiShouldBeFound("ketqua.contains=" + DEFAULT_KETQUA);

        // Get all the detaiList where ketqua contains UPDATED_KETQUA
        defaultDetaiShouldNotBeFound("ketqua.contains=" + UPDATED_KETQUA);
    }

    @Test
    @Transactional
    public void getAllDetaisByKetquaNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where ketqua does not contain DEFAULT_KETQUA
        defaultDetaiShouldNotBeFound("ketqua.doesNotContain=" + DEFAULT_KETQUA);

        // Get all the detaiList where ketqua does not contain UPDATED_KETQUA
        defaultDetaiShouldBeFound("ketqua.doesNotContain=" + UPDATED_KETQUA);
    }


    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai equals to DEFAULT_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.equals=" + DEFAULT_XEPLOAI);

        // Get all the detaiList where xeploai equals to UPDATED_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.equals=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai not equals to DEFAULT_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.notEquals=" + DEFAULT_XEPLOAI);

        // Get all the detaiList where xeploai not equals to UPDATED_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.notEquals=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai in DEFAULT_XEPLOAI or UPDATED_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.in=" + DEFAULT_XEPLOAI + "," + UPDATED_XEPLOAI);

        // Get all the detaiList where xeploai equals to UPDATED_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.in=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai is not null
        defaultDetaiShouldBeFound("xeploai.specified=true");

        // Get all the detaiList where xeploai is null
        defaultDetaiShouldNotBeFound("xeploai.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai is greater than or equal to DEFAULT_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.greaterThanOrEqual=" + DEFAULT_XEPLOAI);

        // Get all the detaiList where xeploai is greater than or equal to UPDATED_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.greaterThanOrEqual=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai is less than or equal to DEFAULT_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.lessThanOrEqual=" + DEFAULT_XEPLOAI);

        // Get all the detaiList where xeploai is less than or equal to SMALLER_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.lessThanOrEqual=" + SMALLER_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai is less than DEFAULT_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.lessThan=" + DEFAULT_XEPLOAI);

        // Get all the detaiList where xeploai is less than UPDATED_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.lessThan=" + UPDATED_XEPLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByXeploaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where xeploai is greater than DEFAULT_XEPLOAI
        defaultDetaiShouldNotBeFound("xeploai.greaterThan=" + DEFAULT_XEPLOAI);

        // Get all the detaiList where xeploai is greater than SMALLER_XEPLOAI
        defaultDetaiShouldBeFound("xeploai.greaterThan=" + SMALLER_XEPLOAI);
    }


    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai equals to DEFAULT_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.equals=" + DEFAULT_PHANLOAI);

        // Get all the detaiList where phanloai equals to UPDATED_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.equals=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai not equals to DEFAULT_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.notEquals=" + DEFAULT_PHANLOAI);

        // Get all the detaiList where phanloai not equals to UPDATED_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.notEquals=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai in DEFAULT_PHANLOAI or UPDATED_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.in=" + DEFAULT_PHANLOAI + "," + UPDATED_PHANLOAI);

        // Get all the detaiList where phanloai equals to UPDATED_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.in=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai is not null
        defaultDetaiShouldBeFound("phanloai.specified=true");

        // Get all the detaiList where phanloai is null
        defaultDetaiShouldNotBeFound("phanloai.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai is greater than or equal to DEFAULT_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.greaterThanOrEqual=" + DEFAULT_PHANLOAI);

        // Get all the detaiList where phanloai is greater than or equal to UPDATED_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.greaterThanOrEqual=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai is less than or equal to DEFAULT_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.lessThanOrEqual=" + DEFAULT_PHANLOAI);

        // Get all the detaiList where phanloai is less than or equal to SMALLER_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.lessThanOrEqual=" + SMALLER_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai is less than DEFAULT_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.lessThan=" + DEFAULT_PHANLOAI);

        // Get all the detaiList where phanloai is less than UPDATED_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.lessThan=" + UPDATED_PHANLOAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByPhanloaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where phanloai is greater than DEFAULT_PHANLOAI
        defaultDetaiShouldNotBeFound("phanloai.greaterThan=" + DEFAULT_PHANLOAI);

        // Get all the detaiList where phanloai is greater than SMALLER_PHANLOAI
        defaultDetaiShouldBeFound("phanloai.greaterThan=" + SMALLER_PHANLOAI);
    }


    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai equals to DEFAULT_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.equals=" + DEFAULT_TRANGTHAI);

        // Get all the detaiList where trangthai equals to UPDATED_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.equals=" + UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai not equals to DEFAULT_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.notEquals=" + DEFAULT_TRANGTHAI);

        // Get all the detaiList where trangthai not equals to UPDATED_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.notEquals=" + UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai in DEFAULT_TRANGTHAI or UPDATED_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.in=" + DEFAULT_TRANGTHAI + "," + UPDATED_TRANGTHAI);

        // Get all the detaiList where trangthai equals to UPDATED_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.in=" + UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai is not null
        defaultDetaiShouldBeFound("trangthai.specified=true");

        // Get all the detaiList where trangthai is null
        defaultDetaiShouldNotBeFound("trangthai.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai is greater than or equal to DEFAULT_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.greaterThanOrEqual=" + DEFAULT_TRANGTHAI);

        // Get all the detaiList where trangthai is greater than or equal to UPDATED_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.greaterThanOrEqual=" + UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai is less than or equal to DEFAULT_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.lessThanOrEqual=" + DEFAULT_TRANGTHAI);

        // Get all the detaiList where trangthai is less than or equal to SMALLER_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.lessThanOrEqual=" + SMALLER_TRANGTHAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai is less than DEFAULT_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.lessThan=" + DEFAULT_TRANGTHAI);

        // Get all the detaiList where trangthai is less than UPDATED_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.lessThan=" + UPDATED_TRANGTHAI);
    }

    @Test
    @Transactional
    public void getAllDetaisByTrangthaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where trangthai is greater than DEFAULT_TRANGTHAI
        defaultDetaiShouldNotBeFound("trangthai.greaterThan=" + DEFAULT_TRANGTHAI);

        // Get all the detaiList where trangthai is greater than SMALLER_TRANGTHAI
        defaultDetaiShouldBeFound("trangthai.greaterThan=" + SMALLER_TRANGTHAI);
    }


    @Test
    @Transactional
    public void getAllDetaisByTenchunhiemIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tenchunhiem equals to DEFAULT_TENCHUNHIEM
        defaultDetaiShouldBeFound("tenchunhiem.equals=" + DEFAULT_TENCHUNHIEM);

        // Get all the detaiList where tenchunhiem equals to UPDATED_TENCHUNHIEM
        defaultDetaiShouldNotBeFound("tenchunhiem.equals=" + UPDATED_TENCHUNHIEM);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenchunhiemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tenchunhiem not equals to DEFAULT_TENCHUNHIEM
        defaultDetaiShouldNotBeFound("tenchunhiem.notEquals=" + DEFAULT_TENCHUNHIEM);

        // Get all the detaiList where tenchunhiem not equals to UPDATED_TENCHUNHIEM
        defaultDetaiShouldBeFound("tenchunhiem.notEquals=" + UPDATED_TENCHUNHIEM);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenchunhiemIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tenchunhiem in DEFAULT_TENCHUNHIEM or UPDATED_TENCHUNHIEM
        defaultDetaiShouldBeFound("tenchunhiem.in=" + DEFAULT_TENCHUNHIEM + "," + UPDATED_TENCHUNHIEM);

        // Get all the detaiList where tenchunhiem equals to UPDATED_TENCHUNHIEM
        defaultDetaiShouldNotBeFound("tenchunhiem.in=" + UPDATED_TENCHUNHIEM);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenchunhiemIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tenchunhiem is not null
        defaultDetaiShouldBeFound("tenchunhiem.specified=true");

        // Get all the detaiList where tenchunhiem is null
        defaultDetaiShouldNotBeFound("tenchunhiem.specified=false");
    }
                @Test
    @Transactional
    public void getAllDetaisByTenchunhiemContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tenchunhiem contains DEFAULT_TENCHUNHIEM
        defaultDetaiShouldBeFound("tenchunhiem.contains=" + DEFAULT_TENCHUNHIEM);

        // Get all the detaiList where tenchunhiem contains UPDATED_TENCHUNHIEM
        defaultDetaiShouldNotBeFound("tenchunhiem.contains=" + UPDATED_TENCHUNHIEM);
    }

    @Test
    @Transactional
    public void getAllDetaisByTenchunhiemNotContainsSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where tenchunhiem does not contain DEFAULT_TENCHUNHIEM
        defaultDetaiShouldNotBeFound("tenchunhiem.doesNotContain=" + DEFAULT_TENCHUNHIEM);

        // Get all the detaiList where tenchunhiem does not contain UPDATED_TENCHUNHIEM
        defaultDetaiShouldBeFound("tenchunhiem.doesNotContain=" + UPDATED_TENCHUNHIEM);
    }


    @Test
    @Transactional
    public void getAllDetaisBySudungIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung equals to DEFAULT_SUDUNG
        defaultDetaiShouldBeFound("sudung.equals=" + DEFAULT_SUDUNG);

        // Get all the detaiList where sudung equals to UPDATED_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.equals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung not equals to DEFAULT_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.notEquals=" + DEFAULT_SUDUNG);

        // Get all the detaiList where sudung not equals to UPDATED_SUDUNG
        defaultDetaiShouldBeFound("sudung.notEquals=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsInShouldWork() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung in DEFAULT_SUDUNG or UPDATED_SUDUNG
        defaultDetaiShouldBeFound("sudung.in=" + DEFAULT_SUDUNG + "," + UPDATED_SUDUNG);

        // Get all the detaiList where sudung equals to UPDATED_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.in=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsNullOrNotNull() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung is not null
        defaultDetaiShouldBeFound("sudung.specified=true");

        // Get all the detaiList where sudung is null
        defaultDetaiShouldNotBeFound("sudung.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung is greater than or equal to DEFAULT_SUDUNG
        defaultDetaiShouldBeFound("sudung.greaterThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the detaiList where sudung is greater than or equal to UPDATED_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.greaterThanOrEqual=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung is less than or equal to DEFAULT_SUDUNG
        defaultDetaiShouldBeFound("sudung.lessThanOrEqual=" + DEFAULT_SUDUNG);

        // Get all the detaiList where sudung is less than or equal to SMALLER_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.lessThanOrEqual=" + SMALLER_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsLessThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung is less than DEFAULT_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.lessThan=" + DEFAULT_SUDUNG);

        // Get all the detaiList where sudung is less than UPDATED_SUDUNG
        defaultDetaiShouldBeFound("sudung.lessThan=" + UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void getAllDetaisBySudungIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        // Get all the detaiList where sudung is greater than DEFAULT_SUDUNG
        defaultDetaiShouldNotBeFound("sudung.greaterThan=" + DEFAULT_SUDUNG);

        // Get all the detaiList where sudung is greater than SMALLER_SUDUNG
        defaultDetaiShouldBeFound("sudung.greaterThan=" + SMALLER_SUDUNG);
    }


    @Test
    @Transactional
    public void getAllDetaisByTiendoIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Tiendo tiendo = TiendoResourceIT.createEntity(em);
        em.persist(tiendo);
        em.flush();
        detai.addTiendo(tiendo);
        detaiRepository.saveAndFlush(detai);
        Long tiendoId = tiendo.getId();

        // Get all the detaiList where tiendo equals to tiendoId
        defaultDetaiShouldBeFound("tiendoId.equals=" + tiendoId);

        // Get all the detaiList where tiendo equals to tiendoId + 1
        defaultDetaiShouldNotBeFound("tiendoId.equals=" + (tiendoId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByUpfileIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Upfile upfile = UpfileResourceIT.createEntity(em);
        em.persist(upfile);
        em.flush();
        detai.addUpfile(upfile);
        detaiRepository.saveAndFlush(detai);
        Long upfileId = upfile.getId();

        // Get all the detaiList where upfile equals to upfileId
        defaultDetaiShouldBeFound("upfileId.equals=" + upfileId);

        // Get all the detaiList where upfile equals to upfileId + 1
        defaultDetaiShouldNotBeFound("upfileId.equals=" + (upfileId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByNhansuthamgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Nhansuthamgia nhansuthamgia = NhansuthamgiaResourceIT.createEntity(em);
        em.persist(nhansuthamgia);
        em.flush();
        detai.addNhansuthamgia(nhansuthamgia);
        detaiRepository.saveAndFlush(detai);
        Long nhansuthamgiaId = nhansuthamgia.getId();

        // Get all the detaiList where nhansuthamgia equals to nhansuthamgiaId
        defaultDetaiShouldBeFound("nhansuthamgiaId.equals=" + nhansuthamgiaId);

        // Get all the detaiList where nhansuthamgia equals to nhansuthamgiaId + 1
        defaultDetaiShouldNotBeFound("nhansuthamgiaId.equals=" + (nhansuthamgiaId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByNguonkinhphiIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Nguonkinhphi nguonkinhphi = NguonkinhphiResourceIT.createEntity(em);
        em.persist(nguonkinhphi);
        em.flush();
        detai.addNguonkinhphi(nguonkinhphi);
        detaiRepository.saveAndFlush(detai);
        Long nguonkinhphiId = nguonkinhphi.getId();

        // Get all the detaiList where nguonkinhphi equals to nguonkinhphiId
        defaultDetaiShouldBeFound("nguonkinhphiId.equals=" + nguonkinhphiId);

        // Get all the detaiList where nguonkinhphi equals to nguonkinhphiId + 1
        defaultDetaiShouldNotBeFound("nguonkinhphiId.equals=" + (nguonkinhphiId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByCoquanphoihopthamgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Coquanphoihopthamgia coquanphoihopthamgia = CoquanphoihopthamgiaResourceIT.createEntity(em);
        em.persist(coquanphoihopthamgia);
        em.flush();
        detai.addCoquanphoihopthamgia(coquanphoihopthamgia);
        detaiRepository.saveAndFlush(detai);
        Long coquanphoihopthamgiaId = coquanphoihopthamgia.getId();

        // Get all the detaiList where coquanphoihopthamgia equals to coquanphoihopthamgiaId
        defaultDetaiShouldBeFound("coquanphoihopthamgiaId.equals=" + coquanphoihopthamgiaId);

        // Get all the detaiList where coquanphoihopthamgia equals to coquanphoihopthamgiaId + 1
        defaultDetaiShouldNotBeFound("coquanphoihopthamgiaId.equals=" + (coquanphoihopthamgiaId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByDanhgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Danhgia danhgia = DanhgiaResourceIT.createEntity(em);
        em.persist(danhgia);
        em.flush();
        detai.addDanhgia(danhgia);
        detaiRepository.saveAndFlush(detai);
        Long danhgiaId = danhgia.getId();

        // Get all the detaiList where danhgia equals to danhgiaId
        defaultDetaiShouldBeFound("danhgiaId.equals=" + danhgiaId);

        // Get all the detaiList where danhgia equals to danhgiaId + 1
        defaultDetaiShouldNotBeFound("danhgiaId.equals=" + (danhgiaId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByDutoanKPIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        DutoanKP dutoanKP = DutoanKPResourceIT.createEntity(em);
        em.persist(dutoanKP);
        em.flush();
        detai.addDutoanKP(dutoanKP);
        detaiRepository.saveAndFlush(detai);
        Long dutoanKPId = dutoanKP.getId();

        // Get all the detaiList where dutoanKP equals to dutoanKPId
        defaultDetaiShouldBeFound("dutoanKPId.equals=" + dutoanKPId);

        // Get all the detaiList where dutoanKP equals to dutoanKPId + 1
        defaultDetaiShouldNotBeFound("dutoanKPId.equals=" + (dutoanKPId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByDanhsachbaibaoIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Danhsachbaibao danhsachbaibao = DanhsachbaibaoResourceIT.createEntity(em);
        em.persist(danhsachbaibao);
        em.flush();
        detai.addDanhsachbaibao(danhsachbaibao);
        detaiRepository.saveAndFlush(detai);
        Long danhsachbaibaoId = danhsachbaibao.getId();

        // Get all the detaiList where danhsachbaibao equals to danhsachbaibaoId
        defaultDetaiShouldBeFound("danhsachbaibaoId.equals=" + danhsachbaibaoId);

        // Get all the detaiList where danhsachbaibao equals to danhsachbaibaoId + 1
        defaultDetaiShouldNotBeFound("danhsachbaibaoId.equals=" + (danhsachbaibaoId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByLinhvucIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Linhvuc linhvuc = LinhvucResourceIT.createEntity(em);
        em.persist(linhvuc);
        em.flush();
        detai.setLinhvuc(linhvuc);
        detaiRepository.saveAndFlush(detai);
        Long linhvucId = linhvuc.getId();

        // Get all the detaiList where linhvuc equals to linhvucId
        defaultDetaiShouldBeFound("linhvucId.equals=" + linhvucId);

        // Get all the detaiList where linhvuc equals to linhvucId + 1
        defaultDetaiShouldNotBeFound("linhvucId.equals=" + (linhvucId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByCapdetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Capdetai capdetai = CapdetaiResourceIT.createEntity(em);
        em.persist(capdetai);
        em.flush();
        detai.setCapdetai(capdetai);
        detaiRepository.saveAndFlush(detai);
        Long capdetaiId = capdetai.getId();

        // Get all the detaiList where capdetai equals to capdetaiId
        defaultDetaiShouldBeFound("capdetaiId.equals=" + capdetaiId);

        // Get all the detaiList where capdetai equals to capdetaiId + 1
        defaultDetaiShouldNotBeFound("capdetaiId.equals=" + (capdetaiId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByHoidongdanhgiaIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Hoidongdanhgia hoidongdanhgia = HoidongdanhgiaResourceIT.createEntity(em);
        em.persist(hoidongdanhgia);
        em.flush();
        detai.setHoidongdanhgia(hoidongdanhgia);
        detaiRepository.saveAndFlush(detai);
        Long hoidongdanhgiaId = hoidongdanhgia.getId();

        // Get all the detaiList where hoidongdanhgia equals to hoidongdanhgiaId
        defaultDetaiShouldBeFound("hoidongdanhgiaId.equals=" + hoidongdanhgiaId);

        // Get all the detaiList where hoidongdanhgia equals to hoidongdanhgiaId + 1
        defaultDetaiShouldNotBeFound("hoidongdanhgiaId.equals=" + (hoidongdanhgiaId + 1));
    }


    @Test
    @Transactional
    public void getAllDetaisByChunhiemIsEqualToSomething() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);
        Chunhiem chunhiem = ChunhiemResourceIT.createEntity(em);
        em.persist(chunhiem);
        em.flush();
        detai.setChunhiem(chunhiem);
        detaiRepository.saveAndFlush(detai);
        Long chunhiemId = chunhiem.getId();

        // Get all the detaiList where chunhiem equals to chunhiemId
        defaultDetaiShouldBeFound("chunhiemId.equals=" + chunhiemId);

        // Get all the detaiList where chunhiem equals to chunhiemId + 1
        defaultDetaiShouldNotBeFound("chunhiemId.equals=" + (chunhiemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetaiShouldBeFound(String filter) throws Exception {
        restDetaiMockMvc.perform(get("/api/detais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detai.getId().intValue())))
            .andExpect(jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA)))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].thoigiantao").value(hasItem(DEFAULT_THOIGIANTAO.toString())))
            .andExpect(jsonPath("$.[*].thoigianbatdau").value(hasItem(DEFAULT_THOIGIANBATDAU.toString())))
            .andExpect(jsonPath("$.[*].thoigianketthuc").value(hasItem(DEFAULT_THOIGIANKETTHUC.toString())))
            .andExpect(jsonPath("$.[*].muctieu").value(hasItem(DEFAULT_MUCTIEU)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.[*].tinhcapthiet").value(hasItem(DEFAULT_TINHCAPTHIET)))
            .andExpect(jsonPath("$.[*].nguoihuongdan").value(hasItem(DEFAULT_NGUOIHUONGDAN)))
            .andExpect(jsonPath("$.[*].ketqua").value(hasItem(DEFAULT_KETQUA)))
            .andExpect(jsonPath("$.[*].xeploai").value(hasItem(DEFAULT_XEPLOAI)))
            .andExpect(jsonPath("$.[*].phanloai").value(hasItem(DEFAULT_PHANLOAI)))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI)))
            .andExpect(jsonPath("$.[*].tenchunhiem").value(hasItem(DEFAULT_TENCHUNHIEM)))
            .andExpect(jsonPath("$.[*].sudung").value(hasItem(DEFAULT_SUDUNG)));

        // Check, that the count call also returns 1
        restDetaiMockMvc.perform(get("/api/detais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetaiShouldNotBeFound(String filter) throws Exception {
        restDetaiMockMvc.perform(get("/api/detais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetaiMockMvc.perform(get("/api/detais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDetai() throws Exception {
        // Get the detai
        restDetaiMockMvc.perform(get("/api/detais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetai() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        int databaseSizeBeforeUpdate = detaiRepository.findAll().size();

        // Update the detai
        Detai updatedDetai = detaiRepository.findById(detai.getId()).get();
        // Disconnect from session so that the updates on updatedDetai are not directly saved in db
        em.detach(updatedDetai);
        updatedDetai
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN)
            .thoigiantao(UPDATED_THOIGIANTAO)
            .thoigianbatdau(UPDATED_THOIGIANBATDAU)
            .thoigianketthuc(UPDATED_THOIGIANKETTHUC)
            .muctieu(UPDATED_MUCTIEU)
            .noidung(UPDATED_NOIDUNG)
            .tinhcapthiet(UPDATED_TINHCAPTHIET)
            .nguoihuongdan(UPDATED_NGUOIHUONGDAN)
            .ketqua(UPDATED_KETQUA)
            .xeploai(UPDATED_XEPLOAI)
            .phanloai(UPDATED_PHANLOAI)
            .trangthai(UPDATED_TRANGTHAI)
            .tenchunhiem(UPDATED_TENCHUNHIEM)
            .sudung(UPDATED_SUDUNG);
        DetaiDTO detaiDTO = detaiMapper.toDto(updatedDetai);

        restDetaiMockMvc.perform(put("/api/detais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detaiDTO)))
            .andExpect(status().isOk());

        // Validate the Detai in the database
        List<Detai> detaiList = detaiRepository.findAll();
        assertThat(detaiList).hasSize(databaseSizeBeforeUpdate);
        Detai testDetai = detaiList.get(detaiList.size() - 1);
        assertThat(testDetai.getMa()).isEqualTo(UPDATED_MA);
        assertThat(testDetai.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testDetai.getThoigiantao()).isEqualTo(UPDATED_THOIGIANTAO);
        assertThat(testDetai.getThoigianbatdau()).isEqualTo(UPDATED_THOIGIANBATDAU);
        assertThat(testDetai.getThoigianketthuc()).isEqualTo(UPDATED_THOIGIANKETTHUC);
        assertThat(testDetai.getMuctieu()).isEqualTo(UPDATED_MUCTIEU);
        assertThat(testDetai.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testDetai.getTinhcapthiet()).isEqualTo(UPDATED_TINHCAPTHIET);
        assertThat(testDetai.getNguoihuongdan()).isEqualTo(UPDATED_NGUOIHUONGDAN);
        assertThat(testDetai.getKetqua()).isEqualTo(UPDATED_KETQUA);
        assertThat(testDetai.getXeploai()).isEqualTo(UPDATED_XEPLOAI);
        assertThat(testDetai.getPhanloai()).isEqualTo(UPDATED_PHANLOAI);
        assertThat(testDetai.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
        assertThat(testDetai.getTenchunhiem()).isEqualTo(UPDATED_TENCHUNHIEM);
        assertThat(testDetai.getSudung()).isEqualTo(UPDATED_SUDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDetai() throws Exception {
        int databaseSizeBeforeUpdate = detaiRepository.findAll().size();

        // Create the Detai
        DetaiDTO detaiDTO = detaiMapper.toDto(detai);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetaiMockMvc.perform(put("/api/detais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detaiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Detai in the database
        List<Detai> detaiList = detaiRepository.findAll();
        assertThat(detaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetai() throws Exception {
        // Initialize the database
        detaiRepository.saveAndFlush(detai);

        int databaseSizeBeforeDelete = detaiRepository.findAll().size();

        // Delete the detai
        restDetaiMockMvc.perform(delete("/api/detais/{id}", detai.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Detai> detaiList = detaiRepository.findAll();
        assertThat(detaiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
