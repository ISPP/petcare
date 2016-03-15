package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.PetShipper;
import com.ispp.petcare.repository.PetShipperRepository;
import com.ispp.petcare.repository.search.PetShipperSearchRepository;
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
 * REST controller for managing PetShipper.
 */
@RestController
@RequestMapping("/api")
public class PetShipperResource {

    private final Logger log = LoggerFactory.getLogger(PetShipperResource.class);
        
    @Inject
    private PetShipperRepository petShipperRepository;
    
    @Inject
    private PetShipperSearchRepository petShipperSearchRepository;
    
    /**
     * POST  /petShippers -> Create a new petShipper.
     */
    @RequestMapping(value = "/petShippers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetShipper> createPetShipper(@RequestBody PetShipper petShipper) throws URISyntaxException {
        log.debug("REST request to save PetShipper : {}", petShipper);
        if (petShipper.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("petShipper", "idexists", "A new petShipper cannot already have an ID")).body(null);
        }
        PetShipper result = petShipperRepository.save(petShipper);
        petShipperSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/petShippers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("petShipper", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petShippers -> Updates an existing petShipper.
     */
    @RequestMapping(value = "/petShippers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetShipper> updatePetShipper(@RequestBody PetShipper petShipper) throws URISyntaxException {
        log.debug("REST request to update PetShipper : {}", petShipper);
        if (petShipper.getId() == null) {
            return createPetShipper(petShipper);
        }
        PetShipper result = petShipperRepository.save(petShipper);
        petShipperSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("petShipper", petShipper.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petShippers -> get all the petShippers.
     */
    @RequestMapping(value = "/petShippers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PetShipper>> getAllPetShippers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PetShippers");
        Page<PetShipper> page = petShipperRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/petShippers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /petShippers/:id -> get the "id" petShipper.
     */
    @RequestMapping(value = "/petShippers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetShipper> getPetShipper(@PathVariable Long id) {
        log.debug("REST request to get PetShipper : {}", id);
        PetShipper petShipper = petShipperRepository.findOne(id);
        return Optional.ofNullable(petShipper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /petShippers/:id -> delete the "id" petShipper.
     */
    @RequestMapping(value = "/petShippers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePetShipper(@PathVariable Long id) {
        log.debug("REST request to delete PetShipper : {}", id);
        petShipperRepository.delete(id);
        petShipperSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("petShipper", id.toString())).build();
    }

    /**
     * SEARCH  /_search/petShippers/:query -> search for the petShipper corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/petShippers/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PetShipper> searchPetShippers(@PathVariable String query) {
        log.debug("REST request to search PetShippers for query {}", query);
        return StreamSupport
            .stream(petShipperSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
