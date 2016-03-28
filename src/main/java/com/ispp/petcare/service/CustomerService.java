package com.ispp.petcare.service;

import com.ispp.petcare.domain.Customer;
import com.ispp.petcare.domain.User;
import com.ispp.petcare.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Inject
    private CustomerRepository customerRepository;


    @Inject
    private UserService userService;

    public CustomerService(){
        super();
    }

    public Customer getLoggedCustomer(){
        User user = userService.getUserWithAuthorities();

        Customer result;
        result = customerRepository.findCustomerByUserId(user.getId());

        return result;
    }





}
