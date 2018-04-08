package org.nix.entity;

import org.hibernate.validator.constraints.Length;
import org.nix.entity.basic.BasicEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/5.
 *
 * 系统资源实体类
 */
@Entity
@Table(name = "SysResources")
public class SysResources extends BasicEntity{

    //资源名字
    private String resourcesName;
    //资源的描述
    private String description;
    //访问该资源的URL路径
    private String url;

    //该资源所属的角色 一个角色拥有多个资源  一个资源属于多个角色
    private List<SysRole> sysRoles;

    @Column(name = "resourcesName" , unique = true , nullable = false,length = 50)
    @Length(max = 50)
    public String getResourcesName() {
        return resourcesName;
    }

    @Column(name = "description" , length = 200)
    @Length(max = 200)
    public String getDescription() {
        return description;
    }

    @Column(name = "url" , unique = true , nullable = false)
    public String getUrl() {
        return url;
    }

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources"
            , joinColumns = {@JoinColumn(name = "sysResources")}
            , inverseJoinColumns = {@JoinColumn(name = "sysRoles")})
    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }
}
