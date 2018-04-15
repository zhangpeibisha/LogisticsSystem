package controller.initController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nix.Application;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.dao.repositories.SysResourcesJpa;
import org.nix.dao.repositories.SysRoleJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.SysOrder;
import org.nix.entity.SysResources;
import org.nix.entity.SysRole;
import org.nix.entity.SysUser;
import org.nix.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/13.
 *
 * 备注： 权限控制通过注解设置，不再进行路径匹配设置
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InitSys {

    @Autowired
    private SysRoleJpa sysRoleJpa;

    // 导入角色进入数据库
    @Test
    public void insertListRoleTest(){

        List<SysRole> sysRoles = new ArrayList<>();

        // 系统后台管理员
        SysRole manger = new SysRole();
        manger.setDescription("Administrator");
        manger.setRoleName("manger");
        manger.setCreateTime(new Date());

        // 财务管理员
        SysRole finance = new SysRole();
        finance.setDescription("Administrator");
        finance.setRoleName("finance");
        finance.setCreateTime(new Date());

        // 普通使用用户
        SysRole user = new SysRole();
        user.setDescription("generalUser");
        user.setRoleName("generalUser");
        user.setCreateTime(new Date());

        sysRoles.add(manger);
        sysRoles.add(finance);
        sysRoles.add(user);

        sysRoleJpa.save(sysRoles);
    }


    @Autowired
    private SysResourcesJpa sysResourcesJpa;

    // 权限和角色关联 ------- 删除通过路径控制权限，使用注解设置权限，但是资源表保留
    @Test
    public void roleBondingResourcesTest(){

        List<SysResources> sysResources = sysResourcesJpa.findAll();
        List<SysRole> sysRoles = sysRoleJpa.findAll();

        List<String> manger = new ArrayList<>();
        manger.add("city");
        manger.add("order");
        manger.add("public");
        manger.add("Administrator");

        List<String> general = new ArrayList<>();
        manger.add("public");
        manger.add("generalUser");
        manger.add("order");

        // 财务暂不设置接口权限
        List<String> finance = new ArrayList<>();

        for (int i = 0; i <sysResources.size() ; i++) {
            SysResources tempResources = sysResources.get(i);
            for (int j = 0; j <sysRoles.size() ; j++) {
                if (tempResources.getDescription()
                        .equals(sysRoles.get(j).getDescription())){
                    sysRoles.get(j).getSysResources().add(tempResources);
                }
            }
        }
    }


    @Autowired
    private SysUserJpa sysUserJpa;

    // 用户注册测试
    @Test
    public void createUserTest(){

        SysRole mangerRole = sysRoleJpa.findSysRoleByRoleName("manger");
        SysRole generalRole = sysRoleJpa.findSysRoleByRoleName("generalUser");
        SysRole financeRole = sysRoleJpa.findSysRoleByRoleName("finance");

        List<SysUser> sysUsers = new ArrayList<>();

        SysUser sysUser = new SysUser();
        sysUser.setBalance(10000);
        sysUser.setCreateTime(new Date());
        sysUser.setPassword(MD5.encryption("123456"));
        sysUser.setAccount("sysUser");
        sysUser.setSysRole(mangerRole);

        SysUser general = new SysUser();
        general.setBalance(10000);
        general.setCreateTime(new Date());
        general.setPassword(MD5.encryption("123456"));
        general.setAccount("general");
        general.setSysRole(generalRole);

        SysUser finance = new SysUser();
        finance.setBalance(10000);
        finance.setCreateTime(new Date());
        finance.setPassword(MD5.encryption("123456"));
        finance.setAccount("finance");
        finance.setSysRole(financeRole);

        sysUsers.add(sysUser);
        sysUsers.add(general);
        sysUsers.add(finance);

        sysUserJpa.save(sysUsers);
    }

    @Autowired
    private SysOrderJpa sysOrderJpa;

    @Test
    public void createOrderTest(){

    }

}
