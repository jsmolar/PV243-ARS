package cz.muni.fi.pv243.ars.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jsmolar on 5/19/18.
 */
//@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

}
