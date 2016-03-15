package com.ispp.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ispp.petcare.domain.Booking;
import com.ispp.petcare.repository.BookingRepository;
import com.ispp.petcare.repository.search.BookingSearchRepository;
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
 * REST controller for managing Booking.
 */
@RestController
@RequestMapping("/api")
public class BookingResource {

    private final Logger log = LoggerFactory.getLogger(BookingResource.class);
        
    @Inject
    private BookingRepository bookingRepository;
    
    @Inject
    private BookingSearchRepository bookingSearchRepository;
    
    /**
     * POST  /bookings -> Create a new booking.
     */
    @RequestMapping(value = "/bookings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) throws URISyntaxException {
        log.debug("REST request to save Booking : {}", booking);
        if (booking.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("booking", "idexists", "A new booking cannot already have an ID")).body(null);
        }
        Booking result = bookingRepository.save(booking);
        bookingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("booking", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bookings -> Updates an existing booking.
     */
    @RequestMapping(value = "/bookings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Booking> updateBooking(@Valid @RequestBody Booking booking) throws URISyntaxException {
        log.debug("REST request to update Booking : {}", booking);
        if (booking.getId() == null) {
            return createBooking(booking);
        }
        Booking result = bookingRepository.save(booking);
        bookingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("booking", booking.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bookings -> get all the bookings.
     */
    @RequestMapping(value = "/bookings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Booking>> getAllBookings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Bookings");
        Page<Booking> page = bookingRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bookings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bookings/:id -> get the "id" booking.
     */
    @RequestMapping(value = "/bookings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        log.debug("REST request to get Booking : {}", id);
        Booking booking = bookingRepository.findOne(id);
        return Optional.ofNullable(booking)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bookings/:id -> delete the "id" booking.
     */
    @RequestMapping(value = "/bookings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        log.debug("REST request to delete Booking : {}", id);
        bookingRepository.delete(id);
        bookingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("booking", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bookings/:query -> search for the booking corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bookings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Booking> searchBookings(@PathVariable String query) {
        log.debug("REST request to search Bookings for query {}", query);
        return StreamSupport
            .stream(bookingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
