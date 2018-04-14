package controller.initController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nix.Application;
import org.nix.dao.repositories.SysRoleJpa;
import org.nix.entity.SysResources;
import org.nix.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InitSys {

    @Autowired
    private SysRoleJpa sysRoleJpa;

    @Test
    public void insertListRole(){

        List<SysResources> sysResources = new ArrayList<>();


        SysRole sysRole = new SysRole();

    }

}
