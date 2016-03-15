package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.MessageFolder;
import com.ispp.petcare.repository.MessageFolderRepository;
import com.ispp.petcare.repository.search.MessageFolderSearchRepository;
import com.ispp.petcare.web.rest.util.HeaderUtil;
import com.ispp.petcare.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MessageFolder.
 */
@RestController
@RequestMapping("/api")
public class MessageFolderResource {

    private final Logger log = LoggerFactory.getLogger(MessageFolderResource.class);
        
    @Inject
    private MessageFolderRepository messageFolderRepository;
    
    @Inject
    private MessageFolderSearchRepository messageFolderSearchRepository;
    
    /**
     * POST  /messageFolders -> Create a new messageFolder.
     */
    @RequestMapping(value = "/messageFolders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MessageFolder> createMessageFolder(@RequestBody MessageFolder messageFolder) throws URISyntaxException {
        log.debug("REST request to save MessageFolder : {}", messageFolder);
        if (messageFolder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("messageFolder", "idexists", "A new messageFolder cannot already have an ID")).body(null);
        }
        MessageFolder result = messageFolderRepository.save(messageFolder);
        messageFolderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/messageFolders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("messageFolder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /messageFolders -> Updates an existing messageFolder.
     */
    @RequestMapping(value = "/messageFolders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MessageFolder> updateMessageFolder(@RequestBody MessageFolder messageFolder) throws URISyntaxException {
        log.debug("REST request to update MessageFolder : {}", messageFolder);
        if (messageFolder.getId() == null) {
            return createMessageFolder(messageFolder);
        }
        MessageFolder result = messageFolderRepository.save(messageFolder);
        messageFolderSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("messageFolder", messageFolder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /messageFolders -> get all the messageFolders.
     */
    @RequestMapping(value = "/messageFolders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MessageFolder>> getAllMessageFolders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MessageFolders");
        Page<MessageFolder> page = messageFolderRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/messageFolders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /messageFolders/:id -> get the "id" messageFolder.
     */
    @RequestMapping(value = "/messageFolders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MessageFolder> getMessageFolder(@PathVariable Long id) {
        log.debug("REST request to get MessageFolder : {}", id);
        MessageFolder messageFolder = messageFolderRepository.findOne(id);
        return Optional.ofNullable(messageFolder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /messageFolders/:id -> delete the "id" messageFolder.
     */
    @RequestMapping(value = "/messageFolders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMessageFolder(@PathVariable Long id) {
        log.debug("REST request to delete MessageFolder : {}", id);
        messageFolderRepository.delete(id);
        messageFolderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("messageFolder", id.toString())).build();
    }

    /**
     * SEARCH  /_search/messageFolders/:query -> search for the messageFolder corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/messageFolders/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MessageFolder> searchMessageFolders(@PathVariable String query) {
        log.debug("REST request to search MessageFolders for query {}", query);
        return StreamSupport
            .stream(messageFolderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
