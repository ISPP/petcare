package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.Trip;
import com.ispp.petcare.repository.TripRepository;
import com.ispp.petcare.repository.search.TripSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TripResource REST controller.
 *
 * @see TripResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TripResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DESCRIPTION_TEXT = "AAAAA";
    private static final String UPDATED_DESCRIPTION_TEXT = "BBBBB";
    private static final String DEFAULT_DISTANCE = "AAAAA";
    private static final String UPDATED_DISTANCE = "BBBBB";

    private static final ZonedDateTime DEFAULT_MOMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MOMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MOMENT_STR = dateTimeFormatter.format(DEFAULT_MOMENT);

    private static final Double DEFAULT_COST = 0D;
    private static final Double UPDATED_COST = 1D;

    @Inject
    private TripRepository tripRepository;

    @Inject
    private TripSearchRepository tripSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTripMockMvc;

    private Trip trip;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripResource tripResource = new TripResource();
        ReflectionTestUtils.setField(tripResource, "tripSearchRepository", tripSearchRepository);
        ReflectionTestUtils.setField(tripResource, "tripRepository", tripRepository);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trip = new Trip();
        trip.setDescriptionText(DEFAULT_DESCRIPTION_TEXT);
        trip.setDistance(DEFAULT_DISTANCE);
        trip.setMoment(DEFAULT_MOMENT);
        trip.setCost(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = trips.get(trips.size() - 1);
        assertThat(testTrip.getDescriptionText()).isEqualTo(DEFAULT_DESCRIPTION_TEXT);
        assertThat(testTrip.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testTrip.getMoment()).isEqualTo(DEFAULT_MOMENT);
        assertThat(testTrip.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void checkDescriptionTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDescriptionText(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isBadRequest());

        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDistance(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isBadRequest());

        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMomentIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setMoment(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isBadRequest());

        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setCost(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isBadRequest());

        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the trips
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
                .andExpect(jsonPath("$.[*].descriptionText").value(hasItem(DEFAULT_DESCRIPTION_TEXT.toString())))
                .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.toString())))
                .andExpect(jsonPath("$.[*].moment").value(hasItem(DEFAULT_MOMENT_STR)))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())));
    }

    @Test
    @Transactional
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.descriptionText").value(DEFAULT_DESCRIPTION_TEXT.toString()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.toString()))
            .andExpect(jsonPath("$.moment").value(DEFAULT_MOMENT_STR))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

		int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        trip.setDescriptionText(UPDATED_DESCRIPTION_TEXT);
        trip.setDistance(UPDATED_DISTANCE);
        trip.setMoment(UPDATED_MOMENT);
        trip.setCost(UPDATED_COST);

        restTripMockMvc.perform(put("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = trips.get(trips.size() - 1);
        assertThat(testTrip.getDescriptionText()).isEqualTo(UPDATED_DESCRIPTION_TEXT);
        assertThat(testTrip.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testTrip.getMoment()).isEqualTo(UPDATED_MOMENT);
        assertThat(testTrip.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

		int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Get the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeDelete - 1);
    }
}
