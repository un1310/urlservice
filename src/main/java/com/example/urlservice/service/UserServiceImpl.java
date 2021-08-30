package com.example.urlservice.service;

import com.example.urlservice.entity.JWTResponse;
import com.example.urlservice.entity.User;
import com.example.urlservice.repository.JWTRepository;
import com.example.urlservice.repository.UserRepository;
import com.example.urlservice.utils.JWTUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTRepository jwtRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public JWTResponse login(User user) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(user.getUserName());

        final String token =
                jwtUtility.generateToken(userDetails);

        jwtRepository.deleteAll();
        jwtRepository.save(new JWTResponse(token));
        return new JWTResponse(token);
    }

    @Override
    public User registerUser(User user) {
        log.info("Saving user to database:{}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser() {
        User user = getAuthenticatedUser();
        if (user.getUrlResponse() == null && user.getUrl() != null) {
            log.info("fetching response for url for userId:{} ", user.getId());
            user.setUrlResponse(getUrlWebContent(user.getUrl()));
            return userRepository.save(user);
        }
        return null;

    }

    @Override
    public User updateUser(User user) {
        User userdb = getAuthenticatedUser();
        log.info("updating url of userId:{} ", userdb.getId());
        userdb.setUrl(user.getUrl());
        return userRepository.save(userdb);

    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = userRepository.findByUserName(username);
        return user.get();
    }

    private String getUrlWebContent(String url) {
        try {
            return Jsoup.connect(url).get().html();
        } catch (Exception e) {
            log.info("Not able to fetch Content for url:{} .Msg:{}", url, e.getMessage());
            return null;
        }

    }

}
