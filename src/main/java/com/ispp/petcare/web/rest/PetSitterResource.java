package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.PetSitter;
import com.ispp.petcare.repository.PetSitterRepository;
import com.ispp.petcare.repository.search.PetSitterSearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PetSitter.
 */
@RestController
@RequestMapping("/api")
public class PetSitterResource {

    private final Logger log = LoggerFactory.getLogger(PetSitterResource.class);
        
    @Inject
    private PetSitterRepository petSitterRepository;
    
    @Inject
    private PetSitterSearchRepository petSitterSearchRepository;
    
    /**
     * POST  /petSitters -> Create a new petSitter.
     */
    @RequestMapping(value = "/petSitters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetSitter> createPetSitter(@Valid @RequestBody PetSitter petSitter) throws URISyntaxException {
        log.debug("REST request to save PetSitter : {}", petSitter);
        if (petSitter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("petSitter", "idexists", "A new petSitter cannot already have an ID")).body(null);
        }
        PetSitter result = petSitterRepository.save(petSitter);
        petSitterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/petSitters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("petSitter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petSitters -> Updates an existing petSitter.
     */
    @RequestMapping(value = "/petSitters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetSitter> updatePetSitter(@Valid @RequestBody PetSitter petSitter) throws URISyntaxException {
        log.debug("REST request to update PetSitter : {}", petSitter);
        if (petSitter.getId() == null) {
            return createPetSitter(petSitter);
        }
        PetSitter result = petSitterRepository.save(petSitter);
        petSitterSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("petSitter", petSitter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petSitters -> get all the petSitters.
     */
    @RequestMapping(value = "/petSitters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PetSitter>> getAllPetSitters(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PetSitters");
        Page<PetSitter> page = petSitterRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/petSitters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /petSitters/:id -> get the "id" petSitter.
     */
    @RequestMapping(value = "/petSitters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetSitter> getPetSitter(@PathVariable Long id) {
        log.debug("REST request to get PetSitter : {}", id);
        PetSitter petSitter = petSitterRepository.findOne(id);
        return Optional.ofNullable(petSitter)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /petSitters/:id -> delete the "id" petSitter.
     */
    @RequestMapping(value = "/petSitters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePetSitter(@PathVariable Long id) {
        log.debug("REST request to delete PetSitter : {}", id);
        petSitterRepository.delete(id);
        petSitterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("petSitter", id.toString())).build();
    }

    /**
     * SEARCH  /_search/petSitters/:query -> search for the petSitter corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/petSitters/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PetSitter> searchPetSitters(@PathVariable String query) {
        log.debug("REST request to search PetSitters for query {}", query);
        return StreamSupport
            .stream(petSitterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
