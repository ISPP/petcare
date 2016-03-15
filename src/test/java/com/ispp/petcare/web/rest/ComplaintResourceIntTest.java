package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.Complaint;
import com.ispp.petcare.repository.ComplaintRepository;
import com.ispp.petcare.repository.search.ComplaintSearchRepository;

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
 * Test class for the ComplaintResource REST controller.
 *
 * @see ComplaintResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ComplaintResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_RESOLUTION = "AAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBB";
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_MOMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_MOMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_MOMENT_STR = dateTimeFormatter.format(DEFAULT_CREATION_MOMENT);

    @Inject
    private ComplaintRepository complaintRepository;

    @Inject
    private ComplaintSearchRepository complaintSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restComplaintMockMvc;

    private Complaint complaint;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComplaintResource complaintResource = new ComplaintResource();
        ReflectionTestUtils.setField(complaintResource, "complaintSearchRepository", complaintSearchRepository);
        ReflectionTestUtils.setField(complaintResource, "complaintRepository", complaintRepository);
        this.restComplaintMockMvc = MockMvcBuilders.standaloneSetup(complaintResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        complaint = new Complaint();
        complaint.setTitle(DEFAULT_TITLE);
        complaint.setDescription(DEFAULT_DESCRIPTION);
        complaint.setResolution(DEFAULT_RESOLUTION);
        complaint.setStatus(DEFAULT_STATUS);
        complaint.setCreationMoment(DEFAULT_CREATION_MOMENT);
    }

    @Test
    @Transactional
    public void createComplaint() throws Exception {
        int databaseSizeBeforeCreate = complaintRepository.findAll().size();

        // Create the Complaint

        restComplaintMockMvc.perform(post("/api/complaints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(complaint)))
                .andExpect(status().isCreated());

        // Validate the Complaint in the database
        List<Complaint> complaints = complaintRepository.findAll();
        assertThat(complaints).hasSize(databaseSizeBeforeCreate + 1);
        Complaint testComplaint = complaints.get(complaints.size() - 1);
        assertThat(testComplaint.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testComplaint.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testComplaint.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
        assertThat(testComplaint.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testComplaint.getCreationMoment()).isEqualTo(DEFAULT_CREATION_MOMENT);
    }

    @Test
    @Transactional
    public void getAllComplaints() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        // Get all the complaints
        restComplaintMockMvc.perform(get("/api/complaints?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(complaint.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].creationMoment").value(hasItem(DEFAULT_CREATION_MOMENT_STR)));
    }

    @Test
    @Transactional
    public void getComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        // Get the complaint
        restComplaintMockMvc.perform(get("/api/complaints/{id}", complaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(complaint.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.creationMoment").value(DEFAULT_CREATION_MOMENT_STR));
    }

    @Test
    @Transactional
    public void getNonExistingComplaint() throws Exception {
        // Get the complaint
        restComplaintMockMvc.perform(get("/api/complaints/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

		int databaseSizeBeforeUpdate = complaintRepository.findAll().size();

        // Update the complaint
        complaint.setTitle(UPDATED_TITLE);
        complaint.setDescription(UPDATED_DESCRIPTION);
        complaint.setResolution(UPDATED_RESOLUTION);
        complaint.setStatus(UPDATED_STATUS);
        complaint.setCreationMoment(UPDATED_CREATION_MOMENT);

        restComplaintMockMvc.perform(put("/api/complaints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(complaint)))
                .andExpect(status().isOk());

        // Validate the Complaint in the database
        List<Complaint> complaints = complaintRepository.findAll();
        assertThat(complaints).hasSize(databaseSizeBeforeUpdate);
        Complaint testComplaint = complaints.get(complaints.size() - 1);
        assertThat(testComplaint.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testComplaint.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testComplaint.getResolution()).isEqualTo(UPDATED_RESOLUTION);
        assertThat(testComplaint.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testComplaint.getCreationMoment()).isEqualTo(UPDATED_CREATION_MOMENT);
    }

    @Test
    @Transactional
    public void deleteComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

		int databaseSizeBeforeDelete = complaintRepository.findAll().size();

        // Get the complaint
        restComplaintMockMvc.perform(delete("/api/complaints/{id}", complaint.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Complaint> complaints = complaintRepository.findAll();
        assertThat(complaints).hasSize(databaseSizeBeforeDelete - 1);
    }
}
