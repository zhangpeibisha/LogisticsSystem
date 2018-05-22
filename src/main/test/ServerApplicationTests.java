import org.junit.Test;
import org.junit.runner.RunWith;
import org.nix.Application;
import org.nix.dao.impl.SysOrderDaoImpl;
import org.nix.dao.repositories.CityJpa;
import org.nix.dao.repositories.OrderEvaluationJpa;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.OrderEvaluation;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServerApplicationTests {
    @Autowired
    private CityJpa cityJpa;
    @Autowired
    private SysOrderJpa sysOrderJpa;
    @Autowired
    private SysOrderDaoImpl sysOrderDao;
    @Test
    public void contextLoads() {
        System.out.println(sysOrderDao.list(0,0,"","",""));
    }

    @Autowired
    private OrderEvaluationJpa orderEvaluationJpa;

    @Autowired
    private SysUserJpa sysUserJpa;


    @Test
    public void OrderETest(){

        String message = "这个快递公司很好";
        SysUser sysUser = sysUserJpa.findSysUserByAccount("111111");
        SysOrder sysOrder = sysOrderJpa.findByOrderId(1);


        OrderEvaluation orderEvaluation = new OrderEvaluation();
        orderEvaluation.setCreateTime(new Date());
        orderEvaluation.setEvaluation(message);
        orderEvaluation.setSysUser(sysUser);
        orderEvaluation.setSysOrder(sysOrder);

//        sysOrder.getOrderEvaluation().add(orderEvaluation);
//        sysOrderJpa.saveAndFlush(sysOrder);
        orderEvaluationJpa.save(orderEvaluation);
    }

}