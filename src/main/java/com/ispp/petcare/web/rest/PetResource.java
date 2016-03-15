package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.Pet;
import com.ispp.petcare.repository.PetRepository;
import com.ispp.petcare.repository.search.PetSearchRepository;
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
 * REST controller for managing Pet.
 */
@RestController
@RequestMapping("/api")
public class PetResource {

    private final Logger log = LoggerFactory.getLogger(PetResource.class);
        
    @Inject
    private PetRepository petRepository;
    
    @Inject
    private PetSearchRepository petSearchRepository;
    
    /**
     * POST  /pets -> Create a new pet.
     */
    @RequestMapping(value = "/pets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) throws URISyntaxException {
        log.debug("REST request to save Pet : {}", pet);
        if (pet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pet", "idexists", "A new pet cannot already have an ID")).body(null);
        }
        Pet result = petRepository.save(pet);
        petSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pets -> Updates an existing pet.
     */
    @RequestMapping(value = "/pets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pet> updatePet(@Valid @RequestBody Pet pet) throws URISyntaxException {
        log.debug("REST request to update Pet : {}", pet);
        if (pet.getId() == null) {
            return createPet(pet);
        }
        Pet result = petRepository.save(pet);
        petSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pet", pet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pets -> get all the pets.
     */
    @RequestMapping(value = "/pets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pet>> getAllPets(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pets");
        Page<Pet> page = petRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pets/:id -> get the "id" pet.
     */
    @RequestMapping(value = "/pets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        log.debug("REST request to get Pet : {}", id);
        Pet pet = petRepository.findOne(id);
        return Optional.ofNullable(pet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pets/:id -> delete the "id" pet.
     */
    @RequestMapping(value = "/pets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        log.debug("REST request to delete Pet : {}", id);
        petRepository.delete(id);
        petSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pets/:query -> search for the pet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pets/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pet> searchPets(@PathVariable String query) {
        log.debug("REST request to search Pets for query {}", query);
        return StreamSupport
            .stream(petSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
