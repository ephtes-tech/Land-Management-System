package com.UserService.user.service;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
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
    private static String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String adminUsername;

    @Value("${keycloak.password}")
    private String adminPassword;

    @Value("${keycloak.default-role:user}") // default role if not provided
    private String defaultRole;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private Keycloak keycloak;
    @PostConstruct
    public void init(){
        this.keycloak= KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
        log.info("Connected to Keycloak realm: {}", realm);
    }
    public String registerUser(String username,String email, String password){
        UsersResource resource=keycloak.realm(realm).users();

        // Create password credential
        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);

        //Build User
        UserRepresentation user=new UserRepresentation();
        user.setEmail(email);
        user.setUsername(username);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credentialRepresentation));

        //Create user
        Response response=resource.create(user);
        if (response.getStatus()!=201){
            throw new RuntimeException("failed to create user: "+response.getStatusInfo());
        }
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        log.info("User created: {}", userId);

        //assign role
        assignRole(userId,defaultRole);
        return userId;
    }
    public void assignRole(String userId, String roleName) {
        var roles = keycloak.realm(realm).roles();
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
