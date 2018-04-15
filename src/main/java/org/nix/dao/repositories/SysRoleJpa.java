package org.nix.dao.repositories;

import org.nix.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 系统角色底层业务接口
 */
@Service
public interface SysRoleJpa extends JpaRepository<SysRole,Integer> {
    /**
     * todo: 通过角色名查找角色信息
     * @param roleName
     * @return
     */
    @Query(nativeQuery = true,
    value = "SELECT * FROM sys_role WHERE sys_role.role_name = ?1")
    SysRole findSysRoleByRoleName(String roleName);



}
