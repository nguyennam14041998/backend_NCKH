package com.api.backend.web.rest;

import com.api.backend.Backend2App;
import com.api.backend.domain.Upfile;
import com.api.backend.domain.Detai;
import com.api.backend.domain.Tiendo;
import com.api.backend.repository.UpfileRepository;
import com.api.backend.service.UpfileService;
import com.api.backend.service.dto.UpfileDTO;
import com.api.backend.service.mapper.UpfileMapper;
import com.api.backend.web.rest.errors.ExceptionTranslator;
import com.api.backend.service.dto.UpfileCriteria;
import com.api.backend.service.UpfileQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link UpfileResource} REST controller.
 */
@SpringBootTest(classes = Backend2App.class)
public class UpfileResourceIT {

    private static final String DEFAULT_MOTA = "AAAAAAAAAA";
    private static final String UPDATED_MOTA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_NOIDUNG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NOIDUNG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NOIDUNG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NOIDUNG_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_THOIGIAN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOIGIAN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THOIGIAN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private UpfileRepository upfileRepository;

    @Autowired
    private UpfileMapper upfileMapper;

    @Autowired
    private UpfileService upfileService;

    @Autowired
    private UpfileQueryService upfileQueryService;

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

    private MockMvc restUpfileMockMvc;

    private Upfile upfile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UpfileResource upfileResource = new UpfileResource(upfileService, upfileQueryService);
        this.restUpfileMockMvc = MockMvcBuilders.standaloneSetup(upfileResource)
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
    public static Upfile createEntity(EntityManager em) {
        Upfile upfile = new Upfile()
            .mota(DEFAULT_MOTA)
            .noidung(DEFAULT_NOIDUNG)
            .noidungContentType(DEFAULT_NOIDUNG_CONTENT_TYPE)
            .thoigian(DEFAULT_THOIGIAN);
        return upfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upfile createUpdatedEntity(EntityManager em) {
        Upfile upfile = new Upfile()
            .mota(UPDATED_MOTA)
            .noidung(UPDATED_NOIDUNG)
            .noidungContentType(UPDATED_NOIDUNG_CONTENT_TYPE)
            .thoigian(UPDATED_THOIGIAN);
        return upfile;
    }

    @BeforeEach
    public void initTest() {
        upfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpfile() throws Exception {
        int databaseSizeBeforeCreate = upfileRepository.findAll().size();

        // Create the Upfile
        UpfileDTO upfileDTO = upfileMapper.toDto(upfile);
        restUpfileMockMvc.perform(post("/api/upfiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upfileDTO)))
            .andExpect(status().isCreated());

        // Validate the Upfile in the database
        List<Upfile> upfileList = upfileRepository.findAll();
        assertThat(upfileList).hasSize(databaseSizeBeforeCreate + 1);
        Upfile testUpfile = upfileList.get(upfileList.size() - 1);
        assertThat(testUpfile.getMota()).isEqualTo(DEFAULT_MOTA);
        assertThat(testUpfile.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testUpfile.getNoidungContentType()).isEqualTo(DEFAULT_NOIDUNG_CONTENT_TYPE);
        assertThat(testUpfile.getThoigian()).isEqualTo(DEFAULT_THOIGIAN);
    }

    @Test
    @Transactional
    public void createUpfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upfileRepository.findAll().size();

        // Create the Upfile with an existing ID
        upfile.setId(1L);
        UpfileDTO upfileDTO = upfileMapper.toDto(upfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpfileMockMvc.perform(post("/api/upfiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upfile in the database
        List<Upfile> upfileList = upfileRepository.findAll();
        assertThat(upfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUpfiles() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList
        restUpfileMockMvc.perform(get("/api/upfiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].mota").value(hasItem(DEFAULT_MOTA)))
            .andExpect(jsonPath("$.[*].noidungContentType").value(hasItem(DEFAULT_NOIDUNG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOIDUNG))))
            .andExpect(jsonPath("$.[*].thoigian").value(hasItem(DEFAULT_THOIGIAN.toString())));
    }
    
    @Test
    @Transactional
    public void getUpfile() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get the upfile
        restUpfileMockMvc.perform(get("/api/upfiles/{id}", upfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upfile.getId().intValue()))
            .andExpect(jsonPath("$.mota").value(DEFAULT_MOTA))
            .andExpect(jsonPath("$.noidungContentType").value(DEFAULT_NOIDUNG_CONTENT_TYPE))
            .andExpect(jsonPath("$.noidung").value(Base64Utils.encodeToString(DEFAULT_NOIDUNG)))
            .andExpect(jsonPath("$.thoigian").value(DEFAULT_THOIGIAN.toString()));
    }


    @Test
    @Transactional
    public void getUpfilesByIdFiltering() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        Long id = upfile.getId();

        defaultUpfileShouldBeFound("id.equals=" + id);
        defaultUpfileShouldNotBeFound("id.notEquals=" + id);

        defaultUpfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUpfileShouldNotBeFound("id.greaterThan=" + id);

        defaultUpfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUpfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUpfilesByMotaIsEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where mota equals to DEFAULT_MOTA
        defaultUpfileShouldBeFound("mota.equals=" + DEFAULT_MOTA);

        // Get all the upfileList where mota equals to UPDATED_MOTA
        defaultUpfileShouldNotBeFound("mota.equals=" + UPDATED_MOTA);
    }

    @Test
    @Transactional
    public void getAllUpfilesByMotaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where mota not equals to DEFAULT_MOTA
        defaultUpfileShouldNotBeFound("mota.notEquals=" + DEFAULT_MOTA);

        // Get all the upfileList where mota not equals to UPDATED_MOTA
        defaultUpfileShouldBeFound("mota.notEquals=" + UPDATED_MOTA);
    }

    @Test
    @Transactional
    public void getAllUpfilesByMotaIsInShouldWork() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where mota in DEFAULT_MOTA or UPDATED_MOTA
        defaultUpfileShouldBeFound("mota.in=" + DEFAULT_MOTA + "," + UPDATED_MOTA);

        // Get all the upfileList where mota equals to UPDATED_MOTA
        defaultUpfileShouldNotBeFound("mota.in=" + UPDATED_MOTA);
    }

    @Test
    @Transactional
    public void getAllUpfilesByMotaIsNullOrNotNull() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where mota is not null
        defaultUpfileShouldBeFound("mota.specified=true");

        // Get all the upfileList where mota is null
        defaultUpfileShouldNotBeFound("mota.specified=false");
    }
                @Test
    @Transactional
    public void getAllUpfilesByMotaContainsSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where mota contains DEFAULT_MOTA
        defaultUpfileShouldBeFound("mota.contains=" + DEFAULT_MOTA);

        // Get all the upfileList where mota contains UPDATED_MOTA
        defaultUpfileShouldNotBeFound("mota.contains=" + UPDATED_MOTA);
    }

    @Test
    @Transactional
    public void getAllUpfilesByMotaNotContainsSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where mota does not contain DEFAULT_MOTA
        defaultUpfileShouldNotBeFound("mota.doesNotContain=" + DEFAULT_MOTA);

        // Get all the upfileList where mota does not contain UPDATED_MOTA
        defaultUpfileShouldBeFound("mota.doesNotContain=" + UPDATED_MOTA);
    }


    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian equals to DEFAULT_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.equals=" + DEFAULT_THOIGIAN);

        // Get all the upfileList where thoigian equals to UPDATED_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.equals=" + UPDATED_THOIGIAN);
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsNotEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian not equals to DEFAULT_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.notEquals=" + DEFAULT_THOIGIAN);

        // Get all the upfileList where thoigian not equals to UPDATED_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.notEquals=" + UPDATED_THOIGIAN);
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsInShouldWork() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian in DEFAULT_THOIGIAN or UPDATED_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.in=" + DEFAULT_THOIGIAN + "," + UPDATED_THOIGIAN);

        // Get all the upfileList where thoigian equals to UPDATED_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.in=" + UPDATED_THOIGIAN);
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsNullOrNotNull() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian is not null
        defaultUpfileShouldBeFound("thoigian.specified=true");

        // Get all the upfileList where thoigian is null
        defaultUpfileShouldNotBeFound("thoigian.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian is greater than or equal to DEFAULT_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.greaterThanOrEqual=" + DEFAULT_THOIGIAN);

        // Get all the upfileList where thoigian is greater than or equal to UPDATED_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.greaterThanOrEqual=" + UPDATED_THOIGIAN);
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian is less than or equal to DEFAULT_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.lessThanOrEqual=" + DEFAULT_THOIGIAN);

        // Get all the upfileList where thoigian is less than or equal to SMALLER_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.lessThanOrEqual=" + SMALLER_THOIGIAN);
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsLessThanSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian is less than DEFAULT_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.lessThan=" + DEFAULT_THOIGIAN);

        // Get all the upfileList where thoigian is less than UPDATED_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.lessThan=" + UPDATED_THOIGIAN);
    }

    @Test
    @Transactional
    public void getAllUpfilesByThoigianIsGreaterThanSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        // Get all the upfileList where thoigian is greater than DEFAULT_THOIGIAN
        defaultUpfileShouldNotBeFound("thoigian.greaterThan=" + DEFAULT_THOIGIAN);

        // Get all the upfileList where thoigian is greater than SMALLER_THOIGIAN
        defaultUpfileShouldBeFound("thoigian.greaterThan=" + SMALLER_THOIGIAN);
    }


    @Test
    @Transactional
    public void getAllUpfilesByDetaiIsEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);
        Detai detai = DetaiResourceIT.createEntity(em);
        em.persist(detai);
        em.flush();
        upfile.setDetai(detai);
        upfileRepository.saveAndFlush(upfile);
        Long detaiId = detai.getId();

        // Get all the upfileList where detai equals to detaiId
        defaultUpfileShouldBeFound("detaiId.equals=" + detaiId);

        // Get all the upfileList where detai equals to detaiId + 1
        defaultUpfileShouldNotBeFound("detaiId.equals=" + (detaiId + 1));
    }


    @Test
    @Transactional
    public void getAllUpfilesByTiendoIsEqualToSomething() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);
        Tiendo tiendo = TiendoResourceIT.createEntity(em);
        em.persist(tiendo);
        em.flush();
        upfile.setTiendo(tiendo);
        upfileRepository.saveAndFlush(upfile);
        Long tiendoId = tiendo.getId();

        // Get all the upfileList where tiendo equals to tiendoId
        defaultUpfileShouldBeFound("tiendoId.equals=" + tiendoId);

        // Get all the upfileList where tiendo equals to tiendoId + 1
        defaultUpfileShouldNotBeFound("tiendoId.equals=" + (tiendoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUpfileShouldBeFound(String filter) throws Exception {
        restUpfileMockMvc.perform(get("/api/upfiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].mota").value(hasItem(DEFAULT_MOTA)))
            .andExpect(jsonPath("$.[*].noidungContentType").value(hasItem(DEFAULT_NOIDUNG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOIDUNG))))
            .andExpect(jsonPath("$.[*].thoigian").value(hasItem(DEFAULT_THOIGIAN.toString())));

        // Check, that the count call also returns 1
        restUpfileMockMvc.perform(get("/api/upfiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUpfileShouldNotBeFound(String filter) throws Exception {
        restUpfileMockMvc.perform(get("/api/upfiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUpfileMockMvc.perform(get("/api/upfiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUpfile() throws Exception {
        // Get the upfile
        restUpfileMockMvc.perform(get("/api/upfiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpfile() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        int databaseSizeBeforeUpdate = upfileRepository.findAll().size();

        // Update the upfile
        Upfile updatedUpfile = upfileRepository.findById(upfile.getId()).get();
        // Disconnect from session so that the updates on updatedUpfile are not directly saved in db
        em.detach(updatedUpfile);
        updatedUpfile
            .mota(UPDATED_MOTA)
            .noidung(UPDATED_NOIDUNG)
            .noidungContentType(UPDATED_NOIDUNG_CONTENT_TYPE)
            .thoigian(UPDATED_THOIGIAN);
        UpfileDTO upfileDTO = upfileMapper.toDto(updatedUpfile);

        restUpfileMockMvc.perform(put("/api/upfiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upfileDTO)))
            .andExpect(status().isOk());

        // Validate the Upfile in the database
        List<Upfile> upfileList = upfileRepository.findAll();
        assertThat(upfileList).hasSize(databaseSizeBeforeUpdate);
        Upfile testUpfile = upfileList.get(upfileList.size() - 1);
        assertThat(testUpfile.getMota()).isEqualTo(UPDATED_MOTA);
        assertThat(testUpfile.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testUpfile.getNoidungContentType()).isEqualTo(UPDATED_NOIDUNG_CONTENT_TYPE);
        assertThat(testUpfile.getThoigian()).isEqualTo(UPDATED_THOIGIAN);
    }

    @Test
    @Transactional
    public void updateNonExistingUpfile() throws Exception {
        int databaseSizeBeforeUpdate = upfileRepository.findAll().size();

        // Create the Upfile
        UpfileDTO upfileDTO = upfileMapper.toDto(upfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpfileMockMvc.perform(put("/api/upfiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upfile in the database
        List<Upfile> upfileList = upfileRepository.findAll();
        assertThat(upfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUpfile() throws Exception {
        // Initialize the database
        upfileRepository.saveAndFlush(upfile);

        int databaseSizeBeforeDelete = upfileRepository.findAll().size();

        // Delete the upfile
        restUpfileMockMvc.perform(delete("/api/upfiles/{id}", upfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Upfile> upfileList = upfileRepository.findAll();
        assertThat(upfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
