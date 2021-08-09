package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.configurations.security.AuthorizationRequest;
import com.bdular.inventorytracker.services.SellerService;
import com.bdular.inventorytracker.services.UserDetailService;
import com.bdular.inventorytracker.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationAPI {

    @Autowired
    JWTUtil util;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailService service;
    @Autowired
    SellerService sellerService;

    @PostMapping(value = "/seller/login")
    public ResponseEntity<String> login(@RequestBody AuthorizationRequest request) {
        String token = "";
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        token = util.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }

}
