package cz.muni.fi.pv243.ars.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import cz.muni.fi.pv243.ars.persistence.enumeration.UserRole;
import cz.muni.fi.pv243.ars.persistence.model.User;
import cz.muni.fi.pv243.ars.repository.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.IDToken;

/**
 * Created by jsmolar on 6/4/18.
 */
@Named
@RequestScoped
public class UserController {

    @Inject
    private Logger log;

    @Inject
    private UserRepository userRepository;

    @Inject
    private HttpServletRequest request;

    public boolean isLoggedIn() {
        return request.getUserPrincipal() != null;
    }

    public void logOut() throws ServletException, IOException {
        request.logout();

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(request.getContextPath());
    }

    public void logIn() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("login.jsf?faces-redirect=true");
    }

    public void registerUser() {
        if (matchUser() != null) {
            return;
        }

        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) request.getUserPrincipal();
        IDToken token = keycloakPrincipal.getKeycloakSecurityContext().getIdToken();

        //        Map<String, Object> otherClaims = token.getOtherClaims();

        User newUser = new User();

        newUser.setKeycloakPrincipal(keycloakPrincipal.getName())
            .setName(String.valueOf(token.getGivenName()))
            .setSurname(String.valueOf(token.getFamilyName()))
            .setEmail(String.valueOf(token.getEmail()))
            .addRole(UserRole.TENANT);

        //        if(otherClaims.containsKey("user_role")) {
        //            newUser.addRole(String.valueOf(otherClaims.get("user_role")));
        //        }

        userRepository.create(newUser);
        log.info("New User was created");
    }

    public User matchUser() {
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) request.getUserPrincipal();

        if (keycloakPrincipal == null) {
            log.info("User and Keycloak Principal wants to be matched, but Principal is null");
            return null;
        }
        log.info("Principal: " + keycloakPrincipal);
        log.info("Principal name: " + keycloakPrincipal.getName());

        User matchedUser = userRepository.findByKCid(keycloakPrincipal.getName());
        if (matchedUser == null) {
            return null;
        }

        log.info("User and KC Principal are matched. User id: " + matchedUser.getId());

        return matchedUser;
    }

    public boolean isHost() {
        if (!isLoggedIn()) {
            return false;
        }
        User user = matchUser();

        return user.getRoles().contains(UserRole.HOST);
    }

    public boolean isTenant() {
        if (!isLoggedIn()) {
            return true;
        }
        User user = matchUser();

        return user.getRoles().contains(UserRole.TENANT);
    }

}
