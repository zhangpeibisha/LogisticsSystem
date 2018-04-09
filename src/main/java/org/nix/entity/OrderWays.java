package org.nix.entity;

import org.hibernate.annotations.GenericGenerator;
import org.nix.entity.basic.BasicEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单路径记录表
 * */
@Entity
@Table(name = "order_way")
public class OrderWays {
    @Id
    @Column(name = "id", unique = true, length = 32, nullable = false)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "identity")
    private Integer id;
    /**
     * 路过城市
     * */
    @OneToOne
    @JoinColumn(name="city_id")
    private City city;
    /**
     * 是否已经到达
     * */
    private Boolean finish;

    /**
     * 预期到达时间
     * */
    private Date expectedDate;
    /**
     * 实际到达时间
     * */
    private Date arriveDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "order_id")
    private SysOrder orderId;

    public SysOrder getOrderId() {
        return orderId;
    }

    public void setOrderId(SysOrder orderId) {
        this.orderId = orderId;
    }
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }
}
