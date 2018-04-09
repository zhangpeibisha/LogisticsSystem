package org.nix.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.validator.constraints.Length;
import org.nix.entity.basic.BasicEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/4.
 *
 * 查询用户信息时，只给与暴露账户和id
 * 如果确实需要某种敏感信息，请在dto中自行获取返回给前端
 */
@Entity
@Table(name = "SysUser")
public class SysUser extends BasicEntity{

    //账户
    private String account;
    //密码
    private String password;
    //余额
    private double balance;

    //角色
    private SysRole sysRole;
    //拥有订单----个用户拥有多个订单 一个订单只有一个用户
    private List<SysOrder> sysOrder;

    @Column(name = "account" , nullable = false , unique = true , length = 18)
    @Length(max = 18 , min = 6)
    public String getAccount() {
        return account;
    }

    @Column(name = "password",nullable = false , length = 18)
    @Length(max = 18,min = 6)
    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "balance")
    @JSONField(serialize = false)
    public double getBalance() {
        return balance;
    }

    @ManyToOne(fetch = FetchType.LAZY , targetEntity = SysRole.class)
    @JoinColumn(name = "sysRole")
    @JSONField(serialize = false)
    public SysRole getSysRole() {
        return sysRole;
    }

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "sysUser")
    @JSONField(serialize = false)
    public List<SysOrder> getSysOrder() {
        return sysOrder;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public void setSysOrder(List<SysOrder> sysOrder) {
        this.sysOrder = sysOrder;
    }
}