package org.nix.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11723
 */
@Entity
@Table(name = "city_dis")
public class CityDis implements Serializable {
    @EmbeddedId
    private CityDisPrimaryKey primaryKey;

    private Double distance;

    public CityDis(City srcCity, City dstCity, Double distance) {
       this.primaryKey = new CityDisPrimaryKey(srcCity,dstCity);
        this.distance = distance;
    }

    public CityDis() {
    }

    public CityDisPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(CityDisPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
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
