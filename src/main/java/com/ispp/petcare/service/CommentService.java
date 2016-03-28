package com.ispp.petcare.service;

import com.ispp.petcare.domain.*;
import com.ispp.petcare.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Collection;

@Service
@Transactional
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    public CommentService(){
        super();
    }

    @Inject
        private UserService userService;
    @Inject
        private CommentRepository commentRepository;
    @Inject
        private ComplaintService complaintService;
    @Inject
        private AdministratorService administratorService;
    @Inject
        private CustomerService customerService;

    ZonedDateTime zdt = ZonedDateTime.now();
    public Comment create(){
        Comment result;

        result = new Comment();

        result.setCreationMoment(zdt);
        User user = userService.getUserWithAuthorities();
        result.setUser(user);
        return result;
    }

    public Collection<Comment> findCommentByComplaintId(Long id){

        Collection<Comment> result;
        result = commentRepository.findCommentByComplaintId(id);
        Complaint complaint = complaintService.findOne(id);


        User user = userService.getUserWithAuthorities();
        //tiene que ser un usuario logueado
        Assert.isTrue(user!=null, "no hay ningun usuario logueado");
        //la lista resultante de comment debe de ser del que está logueado
        Assert.isTrue(user.getComments().contains(result));

        //el usuario que esté logueado debe de tener esa complaint
        //si es un admin el que está coneectado
        Administrator administrator = administratorService.getLoggedAdministrator();
        Customer customer = customerService.getLoggedCustomer();
        if(administrator!=null){
            Assert.isTrue(administrator.getComplaints().contains(complaint), "El admin logueado no contiene esta complaint");
            Assert.isTrue(complaint.getAdministrator().getId()==administrator.getId(),"El admin logueado no coincide con el customer de la complaint");
        }else{
            Assert.isTrue(customer.getComplaints().contains(complaint), "El customer logueado no contiene esta complaint");
            Assert.isTrue(complaint.getCustomer().getId()==customer.getId(), "El customer logueado no coincide con el customer de la complaint");
        }


        return result;

    }

}
