package com.ispp.petcare.service;

import com.ispp.petcare.domain.Administrator;
import com.ispp.petcare.domain.User;
import com.ispp.petcare.repository.AdministratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class AdministratorService {

    private final Logger log = LoggerFactory.getLogger(AdministratorService.class);

    public AdministratorService(){
        super();
    }


    @Inject
    private AdministratorRepository administratorRepository;

    @Inject
    private UserService userService;

    public Administrator getLoggedAdministrator(){
        Administrator result;
        User user = userService.getUserWithAuthorities();
        result = administratorRepository.findAdministratorByUsername(user.getId());
        return result;
    }

}
