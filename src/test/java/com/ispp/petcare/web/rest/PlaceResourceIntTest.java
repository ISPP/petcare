package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.Place;
import com.ispp.petcare.repository.PlaceRepository;
import com.ispp.petcare.repository.search.PlaceSearchRepository;

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
 * Test class for the PlaceResource REST controller.
 *
 * @see PlaceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlaceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";

    private static final Boolean DEFAULT_HAS_GARDEN = false;
    private static final Boolean UPDATED_HAS_GARDEN = true;

    private static final Boolean DEFAULT_HAS_PATIO = false;
    private static final Boolean UPDATED_HAS_PATIO = true;
    private static final String DEFAULT_BUILDING = "AAAAA";
    private static final String UPDATED_BUILDING = "BBBBB";

    @Inject
    private PlaceRepository placeRepository;

    @Inject
    private PlaceSearchRepository placeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlaceMockMvc;

    private Place place;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlaceResource placeResource = new PlaceResource();
        ReflectionTestUtils.setField(placeResource, "placeSearchRepository", placeSearchRepository);
        ReflectionTestUtils.setField(placeResource, "placeRepository", placeRepository);
        this.restPlaceMockMvc = MockMvcBuilders.standaloneSetup(placeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        place = new Place();
        place.setName(DEFAULT_NAME);
        place.setDescription(DEFAULT_DESCRIPTION);
        place.setAddress(DEFAULT_ADDRESS);
        place.setCity(DEFAULT_CITY);
        place.setHasGarden(DEFAULT_HAS_GARDEN);
        place.setHasPatio(DEFAULT_HAS_PATIO);
        place.setBuilding(DEFAULT_BUILDING);
    }

    @Test
    @Transactional
    public void createPlace() throws Exception {
        int databaseSizeBeforeCreate = placeRepository.findAll().size();

        // Create the Place

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isCreated());

        // Validate the Place in the database
        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeCreate + 1);
        Place testPlace = places.get(places.size() - 1);
        assertThat(testPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlace.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlace.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPlace.getHasGarden()).isEqualTo(DEFAULT_HAS_GARDEN);
        assertThat(testPlace.getHasPatio()).isEqualTo(DEFAULT_HAS_PATIO);
        assertThat(testPlace.getBuilding()).isEqualTo(DEFAULT_BUILDING);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setName(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setDescription(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setAddress(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setCity(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasGardenIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setHasGarden(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasPatioIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setHasPatio(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuildingIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setBuilding(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isBadRequest());

        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlaces() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        // Get all the places
        restPlaceMockMvc.perform(get("/api/places?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(place.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].hasGarden").value(hasItem(DEFAULT_HAS_GARDEN.booleanValue())))
                .andExpect(jsonPath("$.[*].hasPatio").value(hasItem(DEFAULT_HAS_PATIO.booleanValue())))
                .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())));
    }

    @Test
    @Transactional
    public void getPlace() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        // Get the place
        restPlaceMockMvc.perform(get("/api/places/{id}", place.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(place.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.hasGarden").value(DEFAULT_HAS_GARDEN.booleanValue()))
            .andExpect(jsonPath("$.hasPatio").value(DEFAULT_HAS_PATIO.booleanValue()))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlace() throws Exception {
        // Get the place
        restPlaceMockMvc.perform(get("/api/places/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlace() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

		int databaseSizeBeforeUpdate = placeRepository.findAll().size();

        // Update the place
        place.setName(UPDATED_NAME);
        place.setDescription(UPDATED_DESCRIPTION);
        place.setAddress(UPDATED_ADDRESS);
        place.setCity(UPDATED_CITY);
        place.setHasGarden(UPDATED_HAS_GARDEN);
        place.setHasPatio(UPDATED_HAS_PATIO);
        place.setBuilding(UPDATED_BUILDING);

        restPlaceMockMvc.perform(put("/api/places")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(place)))
                .andExpect(status().isOk());

        // Validate the Place in the database
        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeUpdate);
        Place testPlace = places.get(places.size() - 1);
        assertThat(testPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlace.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlace.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPlace.getHasGarden()).isEqualTo(UPDATED_HAS_GARDEN);
        assertThat(testPlace.getHasPatio()).isEqualTo(UPDATED_HAS_PATIO);
        assertThat(testPlace.getBuilding()).isEqualTo(UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void deletePlace() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

		int databaseSizeBeforeDelete = placeRepository.findAll().size();

        // Get the place
        restPlaceMockMvc.perform(delete("/api/places/{id}", place.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Place> places = placeRepository.findAll();
        assertThat(places).hasSize(databaseSizeBeforeDelete - 1);
    }
}
