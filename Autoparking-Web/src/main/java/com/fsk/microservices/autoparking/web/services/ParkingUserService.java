package com.fsk.microservices.autoparking.web.services;

import com.fsk.microservices.autoparking.web.domain.ParkingUserPrincipal;
import com.fsk.microservices.autoparking.web.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParkingUserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final UserRolesRepository userRolesRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow();
        return new ParkingUserPrincipal(user, userRolesRepo.findByUserId(user.getId()));
    }
}
