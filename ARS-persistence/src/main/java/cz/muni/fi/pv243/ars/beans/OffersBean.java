package cz.muni.fi.pv243.ars.beans;

import cz.muni.fi.pv243.ars.controller.UserController;
import cz.muni.fi.pv243.ars.persistence.model.Offer;
import cz.muni.fi.pv243.ars.persistence.model.User;
import cz.muni.fi.pv243.ars.repository.OfferRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by jsmolar on 6/7/18.
 */
@RequestScoped
@Named
public class OffersBean {

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private UserController userController;

    private User user;
    private List<Offer> offers;

    private boolean forUser;

    public List<Offer> getOffers() {
        return offers;
    }

    @PostConstruct
    public void loadOffers() {
        user = userController.matchUser();
        if (forUser) {
            offers = offerRepository.findAllForUser(user);
        } else {
            offers = offerRepository.findAllAvailableForUser(user);
        }
    }

    public boolean isForUser() {
        return forUser;
    }

    public void setForUser(boolean forUser) {
        this.forUser = forUser;
    }
}
