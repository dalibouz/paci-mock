package com.paci.mock.web.rest;

import com.paci.mock.PaciMockApp;
import com.paci.mock.domain.Paci;
import com.paci.mock.repository.PaciRepository;
import com.paci.mock.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;


import java.util.List;

import static com.paci.mock.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaciResource} REST controller.
 */
@SpringBootTest(classes = PaciMockApp.class)
public class PaciResourceIT {

    private static final Boolean DEFAULT_HAS_PACI = false;
    private static final Boolean UPDATED_HAS_PACI = true;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private PaciRepository paciRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPaciMockMvc;

    private Paci paci;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaciResource paciResource = new PaciResource(paciRepository);
        this.restPaciMockMvc = MockMvcBuilders.standaloneSetup(paciResource)
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
    public static Paci createEntity() {
        Paci paci = new Paci()
            .hasPaci(DEFAULT_HAS_PACI)
            .url(DEFAULT_URL);
        return paci;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paci createUpdatedEntity() {
        Paci paci = new Paci()
            .hasPaci(UPDATED_HAS_PACI)
            .url(UPDATED_URL);
        return paci;
    }

    @BeforeEach
    public void initTest() {
        paciRepository.deleteAll();
        paci = createEntity();
    }

    @Test
    public void createPaci() throws Exception {
        int databaseSizeBeforeCreate = paciRepository.findAll().size();

        // Create the Paci
        restPaciMockMvc.perform(post("/api/pacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paci)))
            .andExpect(status().isCreated());

        // Validate the Paci in the database
        List<Paci> paciList = paciRepository.findAll();
        assertThat(paciList).hasSize(databaseSizeBeforeCreate + 1);
        Paci testPaci = paciList.get(paciList.size() - 1);
        assertThat(testPaci.isHasPaci()).isEqualTo(DEFAULT_HAS_PACI);
        assertThat(testPaci.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    public void createPaciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paciRepository.findAll().size();

        // Create the Paci with an existing ID
        paci.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaciMockMvc.perform(post("/api/pacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paci)))
            .andExpect(status().isBadRequest());

        // Validate the Paci in the database
        List<Paci> paciList = paciRepository.findAll();
        assertThat(paciList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPacis() throws Exception {
        // Initialize the database
        paciRepository.save(paci);

        // Get all the paciList
        restPaciMockMvc.perform(get("/api/pacis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paci.getId())))
            .andExpect(jsonPath("$.[*].hasPaci").value(hasItem(DEFAULT_HAS_PACI.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    public void getPaci() throws Exception {
        // Initialize the database
        paciRepository.save(paci);

        // Get the paci
        restPaciMockMvc.perform(get("/api/pacis/{id}", paci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paci.getId()))
            .andExpect(jsonPath("$.hasPaci").value(DEFAULT_HAS_PACI.booleanValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    public void getNonExistingPaci() throws Exception {
        // Get the paci
        restPaciMockMvc.perform(get("/api/pacis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePaci() throws Exception {
        // Initialize the database
        paciRepository.save(paci);

        int databaseSizeBeforeUpdate = paciRepository.findAll().size();

        // Update the paci
        Paci updatedPaci = paciRepository.findById(paci.getId()).get();
        updatedPaci
            .hasPaci(UPDATED_HAS_PACI)
            .url(UPDATED_URL);

        restPaciMockMvc.perform(put("/api/pacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaci)))
            .andExpect(status().isOk());

        // Validate the Paci in the database
        List<Paci> paciList = paciRepository.findAll();
        assertThat(paciList).hasSize(databaseSizeBeforeUpdate);
        Paci testPaci = paciList.get(paciList.size() - 1);
        assertThat(testPaci.isHasPaci()).isEqualTo(UPDATED_HAS_PACI);
        assertThat(testPaci.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    public void updateNonExistingPaci() throws Exception {
        int databaseSizeBeforeUpdate = paciRepository.findAll().size();

        // Create the Paci

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaciMockMvc.perform(put("/api/pacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paci)))
            .andExpect(status().isBadRequest());

        // Validate the Paci in the database
        List<Paci> paciList = paciRepository.findAll();
        assertThat(paciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePaci() throws Exception {
        // Initialize the database
        paciRepository.save(paci);

        int databaseSizeBeforeDelete = paciRepository.findAll().size();

        // Delete the paci
        restPaciMockMvc.perform(delete("/api/pacis/{id}", paci.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paci> paciList = paciRepository.findAll();
        assertThat(paciList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
