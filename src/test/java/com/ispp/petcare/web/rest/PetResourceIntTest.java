package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.Pet;
import com.ispp.petcare.repository.PetRepository;
import com.ispp.petcare.repository.search.PetSearchRepository;

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
 * Test class for the PetResource REST controller.
 *
 * @see PetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_BREED = "AAAAA";
    private static final String UPDATED_BREED = "BBBBB";
    private static final String DEFAULT_KIND = "AAAAA";
    private static final String UPDATED_KIND = "BBBBB";

    @Inject
    private PetRepository petRepository;

    @Inject
    private PetSearchRepository petSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetMockMvc;

    private Pet pet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetResource petResource = new PetResource();
        ReflectionTestUtils.setField(petResource, "petSearchRepository", petSearchRepository);
        ReflectionTestUtils.setField(petResource, "petRepository", petRepository);
        this.restPetMockMvc = MockMvcBuilders.standaloneSetup(petResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pet = new Pet();
        pet.setName(DEFAULT_NAME);
        pet.setDescription(DEFAULT_DESCRIPTION);
        pet.setBreed(DEFAULT_BREED);
        pet.setKind(DEFAULT_KIND);
    }

    @Test
    @Transactional
    public void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = pets.get(pets.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPet.getBreed()).isEqualTo(DEFAULT_BREED);
        assertThat(testPet.getKind()).isEqualTo(DEFAULT_KIND);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setName(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setDescription(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setKind(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the pets
        restPetMockMvc.perform(get("/api/pets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].breed").value(hasItem(DEFAULT_BREED.toString())))
                .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())));
    }

    @Test
    @Transactional
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.breed").value(DEFAULT_BREED.toString()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

		int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        pet.setName(UPDATED_NAME);
        pet.setDescription(UPDATED_DESCRIPTION);
        pet.setBreed(UPDATED_BREED);
        pet.setKind(UPDATED_KIND);

        restPetMockMvc.perform(put("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = pets.get(pets.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPet.getBreed()).isEqualTo(UPDATED_BREED);
        assertThat(testPet.getKind()).isEqualTo(UPDATED_KIND);
    }

    @Test
    @Transactional
    public void deletePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

		int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Get the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
