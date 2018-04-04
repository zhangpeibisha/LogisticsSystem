package org.nix.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11723
 */
@Entity
public class CityDis implements Serializable {
    @Id
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "srcCityName",updatable=false,insertable=false)
    private City srcCity;
    @Id
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "disCityName",updatable=false,insertable=false)
    private City dstCity;

    private Double distance;

    public CityDis(City srcCity, City dstCity, Double distance) {
        this.srcCity = srcCity;
        this.dstCity = dstCity;
        this.distance = distance;
    }

    public CityDis() {
    }

    public City getSrcCity() {
        return srcCity;
    }

    public void setSrcCity(City srcCity) {
        this.srcCity = srcCity;
    }

    public City getDstCity() {
        return dstCity;
    }

    public void setDstCity(City dstCity) {
        this.dstCity = dstCity;
    }

    public Double getDistance() {
        if (distance == 0) {
            return Double.MAX_VALUE;
        }
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
