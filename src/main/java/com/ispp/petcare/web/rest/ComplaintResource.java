package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.Complaint;
import com.ispp.petcare.repository.ComplaintRepository;
import com.ispp.petcare.repository.search.ComplaintSearchRepository;
import com.ispp.petcare.service.ComplaintService;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.time.LocalDate;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import javax.inject.Inject;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Complaint.
 */
@RestController
@RequestMapping("/api")
public class ComplaintResource {

    private final Logger log = LoggerFactory.getLogger(ComplaintResource.class);

    @Inject
    private ComplaintRepository complaintRepository;

    @Inject
    private ComplaintSearchRepository complaintSearchRepository;

    @Inject
    private ComplaintService complaintService;

    /**
     * POST  /complaints -> Create a new complaint.
     */
    @RequestMapping(value = "/complaints",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) throws URISyntaxException {
        log.debug("REST request to save Complaint : {}", complaint);
        if (complaint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("complaint", "idexists", "A new complaint cannot already have an ID")).body(null);
        }
        Complaint result = complaintRepository.save(complaint);
        complaintSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/complaints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("complaint", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /complaints -> Updates an existing complaint.
     */
    @RequestMapping(value = "/complaints",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Complaint> updateComplaint(@RequestBody Complaint complaint) throws URISyntaxException {
        log.debug("REST request to update Complaint : {}", complaint);
        if (complaint.getId() == null) {
            return createComplaint(complaint);
        }
        Complaint result = complaintRepository.save(complaint);
        complaintSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("complaint", complaint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /complaints -> get all the complaints.
     */
    @RequestMapping(value = "/complaints",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Complaint>> getAllComplaints(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Complaints");
        Page<Complaint> page = complaintRepository.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/complaints");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
}

    /**
     * GET  /complaints/:id -> get the "id" complaint.
     */
    @RequestMapping(value = "/complaints/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Complaint> getComplaint(@PathVariable Long id) {
        log.debug("REST request to get Complaint : {}", id);
        Complaint complaint = complaintRepository.findOne(id);
        return Optional.ofNullable(complaint)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /complaints/:id -> delete the "id" complaint.
     */
    @RequestMapping(value = "/complaints/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        log.debug("REST request to delete Complaint : {}", id);
        complaintRepository.delete(id);
        complaintSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("complaint", id.toString())).build();
    }

    /**
     * SEARCH  /_search/complaints/:query -> search for the complaint corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/complaints/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Complaint> searchComplaints(@PathVariable String query) {
        log.debug("REST request to search Complaints for query {}", query);
        return StreamSupport
            .stream(complaintSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }



    /*
    @RequestMapping(value = "/complaintsNotResolution",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Complaint>> getAllComplaintsNotResolution(Pageable pageable) throws URISyntaxException){



        Collection<Complaint> page =  complaintService.findComplaintByCustommerIdAndNotResolution();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/complaintsNotResolution");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }*/

}
