package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.PetShipper;
import com.ispp.petcare.repository.PetShipperRepository;
import com.ispp.petcare.repository.search.PetShipperSearchRepository;

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
 * Test class for the PetShipperResource REST controller.
 *
 * @see PetShipperResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetShipperResourceIntTest {


    @Inject
    private PetShipperRepository petShipperRepository;

    @Inject
    private PetShipperSearchRepository petShipperSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetShipperMockMvc;

    private PetShipper petShipper;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetShipperResource petShipperResource = new PetShipperResource();
        ReflectionTestUtils.setField(petShipperResource, "petShipperSearchRepository", petShipperSearchRepository);
        ReflectionTestUtils.setField(petShipperResource, "petShipperRepository", petShipperRepository);
        this.restPetShipperMockMvc = MockMvcBuilders.standaloneSetup(petShipperResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        petShipper = new PetShipper();
    }

    @Test
    @Transactional
    public void createPetShipper() throws Exception {
        int databaseSizeBeforeCreate = petShipperRepository.findAll().size();

        // Create the PetShipper

        restPetShipperMockMvc.perform(post("/api/petShippers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petShipper)))
                .andExpect(status().isCreated());

        // Validate the PetShipper in the database
        List<PetShipper> petShippers = petShipperRepository.findAll();
        assertThat(petShippers).hasSize(databaseSizeBeforeCreate + 1);
        PetShipper testPetShipper = petShippers.get(petShippers.size() - 1);
    }

    @Test
    @Transactional
    public void getAllPetShippers() throws Exception {
        // Initialize the database
        petShipperRepository.saveAndFlush(petShipper);

        // Get all the petShippers
        restPetShipperMockMvc.perform(get("/api/petShippers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(petShipper.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPetShipper() throws Exception {
        // Initialize the database
        petShipperRepository.saveAndFlush(petShipper);

        // Get the petShipper
        restPetShipperMockMvc.perform(get("/api/petShippers/{id}", petShipper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(petShipper.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPetShipper() throws Exception {
        // Get the petShipper
        restPetShipperMockMvc.perform(get("/api/petShippers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetShipper() throws Exception {
        // Initialize the database
        petShipperRepository.saveAndFlush(petShipper);

		int databaseSizeBeforeUpdate = petShipperRepository.findAll().size();

        // Update the petShipper

        restPetShipperMockMvc.perform(put("/api/petShippers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petShipper)))
                .andExpect(status().isOk());

        // Validate the PetShipper in the database
        List<PetShipper> petShippers = petShipperRepository.findAll();
        assertThat(petShippers).hasSize(databaseSizeBeforeUpdate);
        PetShipper testPetShipper = petShippers.get(petShippers.size() - 1);
    }

    @Test
    @Transactional
    public void deletePetShipper() throws Exception {
        // Initialize the database
        petShipperRepository.saveAndFlush(petShipper);

		int databaseSizeBeforeDelete = petShipperRepository.findAll().size();

        // Get the petShipper
        restPetShipperMockMvc.perform(delete("/api/petShippers/{id}", petShipper.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PetShipper> petShippers = petShipperRepository.findAll();
        assertThat(petShippers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
