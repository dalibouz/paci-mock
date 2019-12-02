package com.paci.mock.web.rest;

import com.paci.mock.PaciMockApp;
import com.paci.mock.domain.PaciContext;
import com.paci.mock.repository.PaciContextRepository;
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
 * Integration tests for the {@link PaciContextResource} REST controller.
 */
@SpringBootTest(classes = PaciMockApp.class)
public class PaciContextResourceIT {

    private static final String DEFAULT_PACI_DISPATCHER = "AAAAAAAAAA";
    private static final String UPDATED_PACI_DISPATCHER = "BBBBBBBBBB";

    private static final String DEFAULT_PACI_ENGINE = "AAAAAAAAAA";
    private static final String UPDATED_PACI_ENGINE = "BBBBBBBBBB";

    private static final String DEFAULT_PACI_REDIRECT = "AAAAAAAAAA";
    private static final String UPDATED_PACI_REDIRECT = "BBBBBBBBBB";

    @Autowired
    private PaciContextRepository paciContextRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPaciContextMockMvc;

    private PaciContext paciContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaciContextResource paciContextResource = new PaciContextResource(paciContextRepository);
        this.restPaciContextMockMvc = MockMvcBuilders.standaloneSetup(paciContextResource)
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
    public static PaciContext createEntity() {
        PaciContext paciContext = new PaciContext()
            .paciDispatcher(DEFAULT_PACI_DISPATCHER)
            .paciEngine(DEFAULT_PACI_ENGINE)
            .paciRedirect(DEFAULT_PACI_REDIRECT);
        return paciContext;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaciContext createUpdatedEntity() {
        PaciContext paciContext = new PaciContext()
            .paciDispatcher(UPDATED_PACI_DISPATCHER)
            .paciEngine(UPDATED_PACI_ENGINE)
            .paciRedirect(UPDATED_PACI_REDIRECT);
        return paciContext;
    }

    @BeforeEach
    public void initTest() {
        paciContextRepository.deleteAll();
        paciContext = createEntity();
    }

    @Test
    public void createPaciContext() throws Exception {
        int databaseSizeBeforeCreate = paciContextRepository.findAll().size();

        // Create the PaciContext
        restPaciContextMockMvc.perform(post("/api/paci-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciContext)))
            .andExpect(status().isCreated());

        // Validate the PaciContext in the database
        List<PaciContext> paciContextList = paciContextRepository.findAll();
        assertThat(paciContextList).hasSize(databaseSizeBeforeCreate + 1);
        PaciContext testPaciContext = paciContextList.get(paciContextList.size() - 1);
        assertThat(testPaciContext.getPaciDispatcher()).isEqualTo(DEFAULT_PACI_DISPATCHER);
        assertThat(testPaciContext.getPaciEngine()).isEqualTo(DEFAULT_PACI_ENGINE);
        assertThat(testPaciContext.getPaciRedirect()).isEqualTo(DEFAULT_PACI_REDIRECT);
    }

    @Test
    public void createPaciContextWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paciContextRepository.findAll().size();

        // Create the PaciContext with an existing ID
        paciContext.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaciContextMockMvc.perform(post("/api/paci-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciContext)))
            .andExpect(status().isBadRequest());

        // Validate the PaciContext in the database
        List<PaciContext> paciContextList = paciContextRepository.findAll();
        assertThat(paciContextList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPaciContexts() throws Exception {
        // Initialize the database
        paciContextRepository.save(paciContext);

        // Get all the paciContextList
        restPaciContextMockMvc.perform(get("/api/paci-contexts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciContext.getId())))
            .andExpect(jsonPath("$.[*].paciDispatcher").value(hasItem(DEFAULT_PACI_DISPATCHER)))
            .andExpect(jsonPath("$.[*].paciEngine").value(hasItem(DEFAULT_PACI_ENGINE)))
            .andExpect(jsonPath("$.[*].paciRedirect").value(hasItem(DEFAULT_PACI_REDIRECT)));
    }
    
    @Test
    public void getPaciContext() throws Exception {
        // Initialize the database
        paciContextRepository.save(paciContext);

        // Get the paciContext
        restPaciContextMockMvc.perform(get("/api/paci-contexts/{id}", paciContext.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paciContext.getId()))
            .andExpect(jsonPath("$.paciDispatcher").value(DEFAULT_PACI_DISPATCHER))
            .andExpect(jsonPath("$.paciEngine").value(DEFAULT_PACI_ENGINE))
            .andExpect(jsonPath("$.paciRedirect").value(DEFAULT_PACI_REDIRECT));
    }

    @Test
    public void getNonExistingPaciContext() throws Exception {
        // Get the paciContext
        restPaciContextMockMvc.perform(get("/api/paci-contexts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePaciContext() throws Exception {
        // Initialize the database
        paciContextRepository.save(paciContext);

        int databaseSizeBeforeUpdate = paciContextRepository.findAll().size();

        // Update the paciContext
        PaciContext updatedPaciContext = paciContextRepository.findById(paciContext.getId()).get();
        updatedPaciContext
            .paciDispatcher(UPDATED_PACI_DISPATCHER)
            .paciEngine(UPDATED_PACI_ENGINE)
            .paciRedirect(UPDATED_PACI_REDIRECT);

        restPaciContextMockMvc.perform(put("/api/paci-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaciContext)))
            .andExpect(status().isOk());

        // Validate the PaciContext in the database
        List<PaciContext> paciContextList = paciContextRepository.findAll();
        assertThat(paciContextList).hasSize(databaseSizeBeforeUpdate);
        PaciContext testPaciContext = paciContextList.get(paciContextList.size() - 1);
        assertThat(testPaciContext.getPaciDispatcher()).isEqualTo(UPDATED_PACI_DISPATCHER);
        assertThat(testPaciContext.getPaciEngine()).isEqualTo(UPDATED_PACI_ENGINE);
        assertThat(testPaciContext.getPaciRedirect()).isEqualTo(UPDATED_PACI_REDIRECT);
    }

    @Test
    public void updateNonExistingPaciContext() throws Exception {
        int databaseSizeBeforeUpdate = paciContextRepository.findAll().size();

        // Create the PaciContext

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaciContextMockMvc.perform(put("/api/paci-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciContext)))
            .andExpect(status().isBadRequest());

        // Validate the PaciContext in the database
        List<PaciContext> paciContextList = paciContextRepository.findAll();
        assertThat(paciContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePaciContext() throws Exception {
        // Initialize the database
        paciContextRepository.save(paciContext);

        int databaseSizeBeforeDelete = paciContextRepository.findAll().size();

        // Delete the paciContext
        restPaciContextMockMvc.perform(delete("/api/paci-contexts/{id}", paciContext.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaciContext> paciContextList = paciContextRepository.findAll();
        assertThat(paciContextList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
