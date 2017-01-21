/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.rest.controllers.api;

import com.aptitekk.aptictrl.core.domain.entities.User;
import com.aptitekk.aptictrl.core.domain.repositories.UserRepository;
import com.aptitekk.aptictrl.core.domain.rest.dtos.UserDTO;
import com.aptitekk.aptictrl.rest.controllers.api.annotations.APIController;
import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@APIController
public class AuthController extends APIControllerAbstract {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "auth/sign-in", method = RequestMethod.GET)
    public ResponseEntity<?> basicAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");

        //Make sure header exists
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {

            //Split on space (Example header: "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
            String[] splitAuthorizationHeader = authorizationHeader.split(" ");

            //Check for two pieces ( ["Basic", "QWxhZGRpbjpvcGVuIHNlc2FtZQ=="] )
            if (splitAuthorizationHeader.length == 2) {

                //Check for "Basic" auth type
                switch (splitAuthorizationHeader[0]) {
                    case "Basic":
                        //Decode
                        String decodedAuth = new String(Base64.decode(splitAuthorizationHeader[1]));

                        //Split on colon (Example of decoded: "Aladdin:open sesame")
                        String[] authSplit = decodedAuth.split(":");

                        if (authSplit.length == 2) {
                            User user = userRepository.findUserWithCredentials(authSplit[0], authSplit[1]);

                            if (user != null) {
                                authService.setCurrentUser(user, response);
                                return ok(modelMapper.map(user, UserDTO.class));
                            }

                            return unauthorized("The Username or Password supplied was incorrect.");
                        }

                        return badRequest("Decoded data could not be parsed.");

                    default:
                        return badRequest("Authorization scheme '" + splitAuthorizationHeader[0] + "' not implemented.");
                }

            }

            return badRequest("Malformed Authorization header.");
        } else {
            User currentUser = authService.getCurrentUser();
            if (currentUser != null) {
                return ok(modelMapper.map(currentUser, UserDTO.class));
            }

            return badRequest("Authorization header was empty.");
        }
    }

    @RequestMapping(value = "auth/sign-out", method = RequestMethod.GET)
    public ResponseEntity<?> signOut(HttpServletResponse response) {
        authService.signOutCurrentUser(response);
        return noContent();
    }

}
