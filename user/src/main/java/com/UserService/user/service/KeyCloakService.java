package com.UserService.user.service;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class KeyCloakService {
    private static final Logger log = LoggerFactory.getLogger(KeyCloakService.class);



    @Value("${keycloak.server-url}")
    private String serverUrl;          // http://localhost:8080

    @Value("${keycloak.realm}")
    private String realm;              // Land-Management

    @Value("${keycloak.client-id}")
    private String clientId;           // registration-auth-flow

    @Value("${keycloak.client-secret}")
    private String clientSecret;



    @Value("${keycloak.username}")
    private String adminUsername;

    @Value("${keycloak.password}")
    private String adminPassword;

    @Value("${keycloak.default-role:user}") // default role if not provided
    private String defaultRole;


    private Keycloak keycloak;
    @PostConstruct
    public void init(){
        // Create a Keycloak admin client instance using client credentials (client ID + secret)
        // This client will act as a "service account" that has permissions to create user
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")              // http://localhost:8080
                .realm("Land-Management")                      // authenticate against Land-Management
                .clientId("land-admin-client")
                .clientSecret("HxpzhU5WXiXQs7QioNn7AhxiFGUdXXit")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
        log.info("Keycloak admin client initialized for realm {}", realm);
    }
    public void registerUser(String username,String email, String password){

        log.info("registerUser() called for username={}", username);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);

        log.info("Sending create user request to Keycloak...");
        Response response=keycloak.realm(realm).users().create(user);

        String userId= CreatedResponseUtil.getCreatedId(response);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        log.info("CredentialRepresentation built");
        keycloak.realm("Land-Management").
                users().get(userId).resetPassword(credential);
        RoleRepresentation userRole=keycloak.realm("Land-Management").roles().get("USER")
                .toRepresentation();
        keycloak.realm("Land-Management").users().get(userId).roles().realmLevel().add(Collections.singletonList(userRole));
        log.info("Status: {} ",response.getStatus());
        /*UsersResource users;
        try {
            users = keycloak.realm(realm).users();
            log.info("UsersResource resolved successfully.");
        } catch (Exception e) {
            log.error("Failed to resolve UsersResource for realm {}", realm, e);
            throw new RuntimeException("Cannot access Keycloak users API: " + e.getMessage(), e);
        }
        // password credential




        log.info("UserRepresentation built");


        int status = response.getStatus();
        log.info("Keycloak responded with status = {}", status);

        if (status != 201) {
            String body = null;
            try {
                body = response.readEntity(String.class);
            } catch (Exception ignored) {}
            log.error("Keycloak error body: {}", body);
            throw new RuntimeException("Failed to create user in Keycloak. HTTP " + status);
        }




        //assign role
       // assignRole(userId,defaultRole);
        */
    }
    public void assignRole(String userId, String roleName) {
        var roles = keycloak.realm("realm").roles();
        var role = roles.get(roleName).toRepresentation();

        keycloak.realm(realm).users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(Collections.singletonList(role));

        log.info("Assigned role '{}' to user '{}'", roleName, userId);
    }
    public void sendEmailVerification(String userId) {
        keycloak.realm(realm)
                .users()
                .get(userId)
                .sendVerifyEmail();

        log.info("Email verification sent to user {}", userId);
    }
}
