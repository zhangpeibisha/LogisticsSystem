package org.nix.dao.repositories;

import org.nix.entity.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 系统资源URL底层业务接口
 */
public interface SysResourcesJpa extends JpaRepository<SysResources,Integer> {
}
