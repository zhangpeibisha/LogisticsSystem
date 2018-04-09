package org.nix.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.nix.entity.basic.BasicEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * todo: 订单评价
 */
@Entity
@Table(name = "OrderEvaluation")
public class OrderEvaluation extends BasicEntity {

    //评论内容
    private String evaluation;
    //评论对应的订单
    private SysOrder sysOrder;
    //这条评论下的回复信息
    private List<OrderEvaluation> nextEvaluations;
    //这条评论的作者,一个用户有多个评论，一个评论有一个用户
    private SysUser sysUser;

    @Column(name = "evaluation" , nullable = false , length = 200)
    @Length(max = 200)
    public String getEvaluation() {
        return evaluation;
    }

    @ManyToOne(targetEntity = SysOrder.class , fetch = FetchType.LAZY  , cascade = CascadeType.ALL)
    @JoinColumn(name = "sysOrder")
    @JSONField(serialize = false)
    public SysOrder getSysOrder() {
        return sysOrder;
    }

    @OneToMany()
    @JoinColumn(name = "nextEvaluations")
    @JSONField(serialize = false)
    public List<OrderEvaluation> getNextEvaluations() {
        return nextEvaluations;
    }

    @ManyToOne(targetEntity = SysUser.class , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "sysUser")
    @JSONField(serialize = false)
    public SysUser getSysUser() {
        return sysUser;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public void setSysOrder(SysOrder sysOrder) {
        this.sysOrder = sysOrder;
    }

    public void setNextEvaluations(List<OrderEvaluation> nextEvaluations) {
        this.nextEvaluations = nextEvaluations;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
