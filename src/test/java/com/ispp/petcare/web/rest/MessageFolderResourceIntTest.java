package com.ispp.petcare.web.rest;

import com.ispp.petcare.Application;
import com.ispp.petcare.domain.MessageFolder;
import com.ispp.petcare.repository.MessageFolderRepository;
import com.ispp.petcare.repository.search.MessageFolderSearchRepository;

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
 * Test class for the MessageFolderResource REST controller.
 *
 * @see MessageFolderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MessageFolderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private MessageFolderRepository messageFolderRepository;

    @Inject
    private MessageFolderSearchRepository messageFolderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMessageFolderMockMvc;

    private MessageFolder messageFolder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageFolderResource messageFolderResource = new MessageFolderResource();
        ReflectionTestUtils.setField(messageFolderResource, "messageFolderSearchRepository", messageFolderSearchRepository);
        ReflectionTestUtils.setField(messageFolderResource, "messageFolderRepository", messageFolderRepository);
        this.restMessageFolderMockMvc = MockMvcBuilders.standaloneSetup(messageFolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        messageFolder = new MessageFolder();
        messageFolder.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMessageFolder() throws Exception {
        int databaseSizeBeforeCreate = messageFolderRepository.findAll().size();

        // Create the MessageFolder

        restMessageFolderMockMvc.perform(post("/api/messageFolders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageFolder)))
                .andExpect(status().isCreated());

        // Validate the MessageFolder in the database
        List<MessageFolder> messageFolders = messageFolderRepository.findAll();
        assertThat(messageFolders).hasSize(databaseSizeBeforeCreate + 1);
        MessageFolder testMessageFolder = messageFolders.get(messageFolders.size() - 1);
        assertThat(testMessageFolder.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessageFolders() throws Exception {
        // Initialize the database
        messageFolderRepository.saveAndFlush(messageFolder);

        // Get all the messageFolders
        restMessageFolderMockMvc.perform(get("/api/messageFolders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(messageFolder.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMessageFolder() throws Exception {
        // Initialize the database
        messageFolderRepository.saveAndFlush(messageFolder);

        // Get the messageFolder
        restMessageFolderMockMvc.perform(get("/api/messageFolders/{id}", messageFolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(messageFolder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageFolder() throws Exception {
        // Get the messageFolder
        restMessageFolderMockMvc.perform(get("/api/messageFolders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageFolder() throws Exception {
        // Initialize the database
        messageFolderRepository.saveAndFlush(messageFolder);

		int databaseSizeBeforeUpdate = messageFolderRepository.findAll().size();

        // Update the messageFolder
        messageFolder.setName(UPDATED_NAME);

        restMessageFolderMockMvc.perform(put("/api/messageFolders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageFolder)))
                .andExpect(status().isOk());

        // Validate the MessageFolder in the database
        List<MessageFolder> messageFolders = messageFolderRepository.findAll();
        assertThat(messageFolders).hasSize(databaseSizeBeforeUpdate);
        MessageFolder testMessageFolder = messageFolders.get(messageFolders.size() - 1);
        assertThat(testMessageFolder.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteMessageFolder() throws Exception {
        // Initialize the database
        messageFolderRepository.saveAndFlush(messageFolder);

		int databaseSizeBeforeDelete = messageFolderRepository.findAll().size();

        // Get the messageFolder
        restMessageFolderMockMvc.perform(delete("/api/messageFolders/{id}", messageFolder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MessageFolder> messageFolders = messageFolderRepository.findAll();
        assertThat(messageFolders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
