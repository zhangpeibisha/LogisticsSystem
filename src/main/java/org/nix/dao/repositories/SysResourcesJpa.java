package org.nix.dao.repositories;

import org.nix.entity.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 系统资源URL底层业务接口
 */
@Service
public interface SysResourcesJpa extends JpaRepository<SysResources,Integer> {

    /**
     * todo: 通过接口uri找到这个资源的详细信息
     * @param uri 系统接口uri
     * @return 如果找到这个资源则返回这个资源的全部信息，如果没有找到则返回null
     */
    @Query(nativeQuery = true,
    value = "SELECT * FROM sys_resources WHERE sys_resources.url = ?1")
    SysResources findSysResourcesByURL(String uri);

}
