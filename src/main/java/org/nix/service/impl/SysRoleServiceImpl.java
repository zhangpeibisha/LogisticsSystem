package org.nix.service.impl;

import org.nix.Exception.SelectObjectException;
import org.nix.dao.repositories.SysResourcesJpa;
import org.nix.entity.SysResources;
import org.nix.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by zhangpe0312@qq.com on 2018/4/14.
 * todo: 角色服务类
 */
@Service
public class SysRoleServiceImpl {

    @Autowired
    private SysResourcesJpa sysResourcesJpa;

    /**
     * todo: 判断这个角色是否能够使用这个资源
     * @param sysRole
     * @param uri
     * @return 如果能够访问，则返回true
     * @see SelectObjectException 查询失败
     */
    public boolean roleUserResources(SysRole sysRole , String uri){

        SysResources sysResources = sysResourcesJpa.findSysResourcesByURL(uri);
        if (sysResources == null)
            throw new SelectObjectException();

        if (sysRole.getSysResources().contains(sysResources))
            return true;

        return false;
    }
}
