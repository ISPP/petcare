package com.ispp.petcare.service;

import com.ispp.petcare.domain.Administrator;
import com.ispp.petcare.domain.Complaint;
import com.ispp.petcare.domain.Customer;
import com.ispp.petcare.domain.User;
import com.ispp.petcare.repository.ComplaintRepository;
import com.ispp.petcare.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.time.*;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class ComplaintService {

    private final Logger log = LoggerFactory.getLogger(ComplaintService.class);

    @Inject
    private ComplaintRepository complaintRepository;

    @Inject
    private CustomerService customerService;

    @Inject
    private UserService userService;

    @Inject
    private AdministratorService administratorService;

    // Constructors -----------------------------------------------------------

    public ComplaintService() {

        super();

    }
        // Simple CRUD methods
    // -------------------------------------------------------------


    ZonedDateTime zdt = ZonedDateTime.now();

    public Complaint create(){
        Complaint result;
        result = new Complaint();
        Customer customer;
        customer = customerService.getLoggedCustomer();
        result.setCreationMoment(zdt);
        result.setCustomer(customer);


        return result;
    }


    //List the complaints he/she has made that haven’t been solved yet.
    public Collection<Complaint> findComplaintByCustommerIdAndResolution(){
        Collection<Complaint> result;
        Customer customer;
       customer = customerService.getLoggedCustomer();
        result = complaintRepository.findComplaintByCustommerIdAndResolution(customer.getId());
        Assert.isTrue(customer.getComplaints().contains(result),"el customer no tiene asosiadas las complaint");

        return result;
    }
    //List his/her complaints that have been solved by an administrator
    public Collection<Complaint> findComplaintByCustommerIdAndNotResolution(){
        Collection<Complaint> result;
        Administrator administrator;
        administrator = administratorService.getLoggedAdministrator();
        result = complaintRepository.findComplaintByCustommerIdAndNotResolution(administrator.getId());

        Assert.isTrue(administrator.getComplaints().contains(result),"el administrador no tiene asosiadas las complaint");
        return result;
    }

    //List an unassigned complaint to himself/herself
    public Collection<Complaint> findComplaintNotAdministrator(){
        Collection<Complaint> result;
        Administrator administrator;
        administrator = administratorService.getLoggedAdministrator();
        Assert.notNull(administrator,"no hay un administrador logueado");
        result = complaintRepository.findComplaintNotAdministrator();
        return result;
    }


    //metodo que asigna una complaint sin administrador al administrador que está logueado
    public void assignedComplaintAndAdministrator(Complaint complaint){

        Administrator administrator;
        administrator = administratorService.getLoggedAdministrator();
        Assert.notNull(administrator,"no hay un administrador logueado");
        complaint.setAdministrator(administrator);

        complaintRepository.save(complaint);
    }


    public Complaint findOne(Long id){
        Complaint result;
        result = complaintRepository.findOne(id);
        return result;
    }




}
