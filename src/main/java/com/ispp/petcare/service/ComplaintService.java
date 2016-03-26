package com.ispp.petcare.service;

import com.ispp.petcare.domain.Administrator;
import com.ispp.petcare.domain.Complaint;
import com.ispp.petcare.domain.User;
import com.ispp.petcare.repository.ComplaintRepository;
import com.ispp.petcare.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.util.Collection;

@Service
@Transactional
public class ComplaintService {

    private final Logger log = LoggerFactory.getLogger(ComplaintService.class);

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    // Constructors -----------------------------------------------------------

    public ComplaintService() {

        super();

    }
        // Simple CRUD methods
    // -------------------------------------------------------------

   /* private ZonedDateTime now = new ZonedDateTime(System.currentTimeMillis()-1000);

    public Complaint create(){
        Complaint result;
        User user = userService.getUserWithAuthorities();
        result.setCreationMoment(now);
        result.setCustomer(user);


        return result;
    }
*/

    //List the complaints he/she has made that haven’t been solved yet.
    public Collection<Complaint> findComplaintByCustommerIdAndResolution(){
        Collection<Complaint> result;
        User user = userService.getUserWithAuthorities();
        result = complaintRepository.findComplaintByCustommerIdAndResolution(user.getId());

        return result;
    }
    //List his/her complaints that have been solved by an administrator
    public Collection<Complaint> findComplaintByCustommerIdAndNotResolution(){
        Collection<Complaint> result;
        User user = userService.getUserWithAuthorities();
        result = complaintRepository.findComplaintByCustommerIdAndNotResolution(user.getId());

        return result;
    }

    //List an unassigned complaint to himself/herself
    public Collection<Complaint> findComplaintNotAdministrator(){
        Collection<Complaint> result;
        result = complaintRepository.findComplaintNotAdministrator();
        return result;
    }
    //metodo que asigna una complaint sin administrador al administrador que está logueado
   /* public void assignedComplaintAndAdministrator(Complaint complaint){
        User user = userService.getUserWithAuthorities();
        complaint.setAdministrator(user);
    }*/




}
