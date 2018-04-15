package org.nix.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
        City city = new City();
        city.setCityName(srcCity.getCityName());
        city.setId(srcCity.getId());
        return srcCity;
    }

    public void setSrcCity(City srcCity) {
        this.srcCity = srcCity;
    }

    public City getDstCity() {
        City city = new City();
        city.setCityName(dstCity.getCityName());
        city.setId(dstCity.getId());
        return dstCity;
    }

    public void setDstCity(City dstCity) {
        this.dstCity = dstCity;
    }
}
