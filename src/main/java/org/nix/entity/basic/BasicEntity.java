package org.nix.entity.basic;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Create by zhangpe0312@qq.com on 2018/4/5.
 *
 * 所有实体类的父类，存放公共属性
 *
 * 返回给前端时，父类只返回id值，创建时间不予返回
 * ，如果确实需要时间请在dto中另外设置字段进行获取
 */
@MappedSuperclass
public class BasicEntity {

    private int id;

    private Date createTime;

    @Id
    @Column(name = "id", unique = true, length = 32, nullable = false)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "identity")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "createTime", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(serialize = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
