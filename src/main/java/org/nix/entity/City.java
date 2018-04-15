
package org.nix.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 11723
 */
@Entity
@Table(name = "city")
public class City implements Comparable<City>,Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "increment")
    Integer id;
    /**
     * 城市名
     * */
    @Column(name = "city_name",unique = true,length = 50)
    private String cityName;

    /**
     * 直连城市
     * */
    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "src_city_id")
    private List<CityDis> dstList;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 最短路径长度
     */
    @Transient
    private double path;

    /**
     * 节点是否已经出列(是否已经处理完毕)
     */
    @Transient
    private boolean isMarked;

    @Transient
    private List<City> ways = new ArrayList<>();

    public City() {
        this.path = Double.MAX_VALUE;
        this.isMarked = false;
        ways.add(this);
    }

    public City(String cityName){
        this.cityName = cityName;
        //初始设置为无穷大
        this.path = Double.MAX_VALUE;
        this.setMarked(false);
    }
    
    public City(String cityName, double path){
        this.cityName = cityName;
        this.path = path;
        this.setMarked(false);
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getPath() {
        return path;
    }

    public void setPath(double path) {
        this.path = path;
    }

    public List<City> getWays() {
        return ways;
    }

    public String getWayString() {
        StringBuilder way = new StringBuilder();
        for (City cityModel:this.ways) {
            way.append(cityModel.getCityName() + "->");
        }
        way.append(getCityName());
        return way.toString();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setWay(City way) {
        if (this.ways.size() == 1) {
            for (City w:way.getWays()) {
                this.ways.add(ways.size() - 1 ,w);
            }
        }else {
            this.ways.add(ways.size() - 1 ,way);
        }
    }

    @JSONField(serialize = false)
    public List<CityDis> getDstList() {
        return dstList;
    }

    public void setDstList(List<CityDis> disList) {
        this.dstList = disList;
    }

    @Override
    public boolean equals(Object obj) {
        return getCityName().equals(((City)obj).getCityName());
    }

    @Override
    public int compareTo(City o) {
        return o.path > path?-1:1;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", path=" + path +
                '}';
    }
}