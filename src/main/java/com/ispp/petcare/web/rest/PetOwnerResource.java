package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.PetOwner;
import com.ispp.petcare.repository.PetOwnerRepository;
import com.ispp.petcare.repository.search.PetOwnerSearchRepository;
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
 * REST controller for managing PetOwner.
 */
@RestController
@RequestMapping("/api")
public class PetOwnerResource {

    private final Logger log = LoggerFactory.getLogger(PetOwnerResource.class);
        
    @Inject
    private PetOwnerRepository petOwnerRepository;
    
    @Inject
    private PetOwnerSearchRepository petOwnerSearchRepository;
    
    /**
     * POST  /petOwners -> Create a new petOwner.
     */
    @RequestMapping(value = "/petOwners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetOwner> createPetOwner(@RequestBody PetOwner petOwner) throws URISyntaxException {
        log.debug("REST request to save PetOwner : {}", petOwner);
        if (petOwner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("petOwner", "idexists", "A new petOwner cannot already have an ID")).body(null);
        }
        PetOwner result = petOwnerRepository.save(petOwner);
        petOwnerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/petOwners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("petOwner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petOwners -> Updates an existing petOwner.
     */
    @RequestMapping(value = "/petOwners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetOwner> updatePetOwner(@RequestBody PetOwner petOwner) throws URISyntaxException {
        log.debug("REST request to update PetOwner : {}", petOwner);
        if (petOwner.getId() == null) {
            return createPetOwner(petOwner);
        }
        PetOwner result = petOwnerRepository.save(petOwner);
        petOwnerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("petOwner", petOwner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petOwners -> get all the petOwners.
     */
    @RequestMapping(value = "/petOwners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PetOwner>> getAllPetOwners(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PetOwners");
        Page<PetOwner> page = petOwnerRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/petOwners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /petOwners/:id -> get the "id" petOwner.
     */
    @RequestMapping(value = "/petOwners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetOwner> getPetOwner(@PathVariable Long id) {
        log.debug("REST request to get PetOwner : {}", id);
        PetOwner petOwner = petOwnerRepository.findOne(id);
        return Optional.ofNullable(petOwner)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /petOwners/:id -> delete the "id" petOwner.
     */
    @RequestMapping(value = "/petOwners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePetOwner(@PathVariable Long id) {
        log.debug("REST request to delete PetOwner : {}", id);
        petOwnerRepository.delete(id);
        petOwnerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("petOwner", id.toString())).build();
    }

    /**
     * SEARCH  /_search/petOwners/:query -> search for the petOwner corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/petOwners/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PetOwner> searchPetOwners(@PathVariable String query) {
        log.debug("REST request to search PetOwners for query {}", query);
        return StreamSupport
            .stream(petOwnerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
