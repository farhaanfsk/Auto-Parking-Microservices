package com.fsk.microservice.autoparking.service;

import com.fsk.microservice.autoparking.entity.ParkingUserPrincipal;
import com.fsk.microservice.autoparking.entity.User;
import com.fsk.microservice.autoparking.entity.UserRoles;
import com.fsk.microservice.autoparking.repository.UserRepository;
import com.fsk.microservice.autoparking.repository.UserRolesRepository;
import com.fsk.microservice.autoparking.security.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@Component
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final UserRolesRepository userRolesRepo;
    private final JWTProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepo, UserRolesRepository userRolesRepo, JWTProvider jwtProvider) {
        this.userRepo = userRepo;
        this.userRolesRepo = userRolesRepo;
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Optional<String> logIn(User user) {
        Optional<String> token = Optional.empty();
        Optional<User> isUser = userRepo.findByUsername(user.getUsername());
        if (isUser.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));
                List<UserRoles> roles = userRolesRepo.findByUserId(isUser.get().getId());
                log.info("roles found are : {}", roles);
                token = Optional.of(jwtProvider.createToken(user.getUsername(), roles));
            } catch (AuthenticationException e) {
                log.info("Log in failed for user {}", user.getUsername());
            }
        }
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow();
        return new ParkingUserPrincipal(user, userRolesRepo.findByUserId(user.getId()));
    }

    public Optional<UserDetails> loadUserByJwtToken(String token) {
        if (jwtProvider.isValidToken(token)) {
            log.info("Is valid token");
            return Optional.of(
                    withUsername(jwtProvider.getUserName(token))
                            .authorities(jwtProvider.getRoles(token))
                            .password("")
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build());
        }
        return Optional.empty();
    }
}
