package org.nix.dao.repositories;

import org.nix.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 系统用户底层业务接口
 */

public interface SysUserJpa extends JpaRepository<SysUser,Integer> {

    /**
     * todo: 通过账号查找到系统用户，如果未找到用户则返回null
     * @param account 系统用户账号
     * @return 查询到的用户
     */
    @Query(nativeQuery = true,
    value = "SELECT * FROM sys_user WHERE account = ?1")
    SysUser findSysUserByAccount(String account);

    /**
     * 更新财务管理员的账号里的余额
     * @param financeAccount 财务管理员的账号
     * @param money 更新的金额
     */
    @Query(nativeQuery = true,
    value = "")
    void updateFinanceBlance(String financeAccount , double money);

}
