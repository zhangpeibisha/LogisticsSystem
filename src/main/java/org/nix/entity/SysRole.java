package org.nix.entity;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import org.nix.entity.basic.BasicEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/5.
 * <p>
 * 系统角色实体
 */
@Entity
@Table(name = "SysRole")
public class SysRole extends BasicEntity {

    //角色名字
    private String roleName;
    //角色描述
    private String description;

    //该角色拥有的访问资源，一个角色拥有多个资源，一个资源属于多个角色
    private List<SysResources> sysResources;
    //该角色属于多个用户 一个用户拥有一个角色
    private List<SysUser> sysUsers;

    @Column(name = "roleName", unique = true, nullable = false, length = 20)
    @Length(max = 20)
    public String getRoleName() {
        return roleName;
    }

    @Column(name = "description", length = 100)
    @Length(max = 100)
    public String getDescription() {
        return description;
    }

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources"
            , joinColumns = {@JoinColumn(name = "sysRoles")}
            , inverseJoinColumns = {@JoinColumn(name = "sysResources")})
    public List<SysResources> getSysResources() {
        return sysResources;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
    @JSONField(serialize = false)
    public List<SysUser> getSysUsers() {
        return sysUsers;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSysResources(List<SysResources> sysResources) {
        this.sysResources = sysResources;
    }

    public void setSysUsers(List<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }
}
