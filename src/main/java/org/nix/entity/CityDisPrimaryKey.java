package org.nix.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11723
 */
@Embeddable
public class CityDisPrimaryKey implements Serializable {
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "src_city_id",updatable=false,insertable=false)
    private City srcCity;
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "dst_city_id",updatable=false,insertable=false)
    private City dstCity;

    public CityDisPrimaryKey() {
    }

    public CityDisPrimaryKey(City srcCity, City dstCity) {
        this.srcCity = srcCity;
        this.dstCity = dstCity;
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
}
