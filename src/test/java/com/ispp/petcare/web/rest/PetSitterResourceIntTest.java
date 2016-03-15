package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.PetSitter;
import com.ispp.petcare.repository.PetSitterRepository;
import com.ispp.petcare.repository.search.PetSitterSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PetSitterResource REST controller.
 *
 * @see PetSitterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetSitterResourceIntTest {


    private static final Double DEFAULT_PRICE_NIGHT = 0D;
    private static final Double UPDATED_PRICE_NIGHT = 1D;

    private static final Double DEFAULT_PRICE_HOUR = 0D;
    private static final Double UPDATED_PRICE_HOUR = 1D;

    @Inject
    private PetSitterRepository petSitterRepository;

    @Inject
    private PetSitterSearchRepository petSitterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetSitterMockMvc;

    private PetSitter petSitter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetSitterResource petSitterResource = new PetSitterResource();
        ReflectionTestUtils.setField(petSitterResource, "petSitterSearchRepository", petSitterSearchRepository);
        ReflectionTestUtils.setField(petSitterResource, "petSitterRepository", petSitterRepository);
        this.restPetSitterMockMvc = MockMvcBuilders.standaloneSetup(petSitterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        petSitter = new PetSitter();
        petSitter.setPriceNight(DEFAULT_PRICE_NIGHT);
        petSitter.setPriceHour(DEFAULT_PRICE_HOUR);
    }

    @Test
    @Transactional
    public void createPetSitter() throws Exception {
        int databaseSizeBeforeCreate = petSitterRepository.findAll().size();

        // Create the PetSitter

        restPetSitterMockMvc.perform(post("/api/petSitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petSitter)))
                .andExpect(status().isCreated());

        // Validate the PetSitter in the database
        List<PetSitter> petSitters = petSitterRepository.findAll();
        assertThat(petSitters).hasSize(databaseSizeBeforeCreate + 1);
        PetSitter testPetSitter = petSitters.get(petSitters.size() - 1);
        assertThat(testPetSitter.getPriceNight()).isEqualTo(DEFAULT_PRICE_NIGHT);
        assertThat(testPetSitter.getPriceHour()).isEqualTo(DEFAULT_PRICE_HOUR);
    }

    @Test
    @Transactional
    public void checkPriceNightIsRequired() throws Exception {
        int databaseSizeBeforeTest = petSitterRepository.findAll().size();
        // set the field null
        petSitter.setPriceNight(null);

        // Create the PetSitter, which fails.

        restPetSitterMockMvc.perform(post("/api/petSitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petSitter)))
                .andExpect(status().isBadRequest());

        List<PetSitter> petSitters = petSitterRepository.findAll();
        assertThat(petSitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = petSitterRepository.findAll().size();
        // set the field null
        petSitter.setPriceHour(null);

        // Create the PetSitter, which fails.

        restPetSitterMockMvc.perform(post("/api/petSitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petSitter)))
                .andExpect(status().isBadRequest());

        List<PetSitter> petSitters = petSitterRepository.findAll();
        assertThat(petSitters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPetSitters() throws Exception {
        // Initialize the database
        petSitterRepository.saveAndFlush(petSitter);

        // Get all the petSitters
        restPetSitterMockMvc.perform(get("/api/petSitters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(petSitter.getId().intValue())))
                .andExpect(jsonPath("$.[*].priceNight").value(hasItem(DEFAULT_PRICE_NIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].priceHour").value(hasItem(DEFAULT_PRICE_HOUR.doubleValue())));
    }

    @Test
    @Transactional
    public void getPetSitter() throws Exception {
        // Initialize the database
        petSitterRepository.saveAndFlush(petSitter);

        // Get the petSitter
        restPetSitterMockMvc.perform(get("/api/petSitters/{id}", petSitter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(petSitter.getId().intValue()))
            .andExpect(jsonPath("$.priceNight").value(DEFAULT_PRICE_NIGHT.doubleValue()))
            .andExpect(jsonPath("$.priceHour").value(DEFAULT_PRICE_HOUR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPetSitter() throws Exception {
        // Get the petSitter
        restPetSitterMockMvc.perform(get("/api/petSitters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetSitter() throws Exception {
        // Initialize the database
        petSitterRepository.saveAndFlush(petSitter);

		int databaseSizeBeforeUpdate = petSitterRepository.findAll().size();

        // Update the petSitter
        petSitter.setPriceNight(UPDATED_PRICE_NIGHT);
        petSitter.setPriceHour(UPDATED_PRICE_HOUR);

        restPetSitterMockMvc.perform(put("/api/petSitters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petSitter)))
                .andExpect(status().isOk());

        // Validate the PetSitter in the database
        List<PetSitter> petSitters = petSitterRepository.findAll();
        assertThat(petSitters).hasSize(databaseSizeBeforeUpdate);
        PetSitter testPetSitter = petSitters.get(petSitters.size() - 1);
        assertThat(testPetSitter.getPriceNight()).isEqualTo(UPDATED_PRICE_NIGHT);
        assertThat(testPetSitter.getPriceHour()).isEqualTo(UPDATED_PRICE_HOUR);
    }

    @Test
    @Transactional
    public void deletePetSitter() throws Exception {
        // Initialize the database
        petSitterRepository.saveAndFlush(petSitter);

		int databaseSizeBeforeDelete = petSitterRepository.findAll().size();

        // Get the petSitter
        restPetSitterMockMvc.perform(delete("/api/petSitters/{id}", petSitter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PetSitter> petSitters = petSitterRepository.findAll();
        assertThat(petSitters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
