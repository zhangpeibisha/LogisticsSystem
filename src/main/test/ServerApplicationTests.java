import org.junit.Test;
import org.junit.runner.RunWith;
import org.nix.Application;
import org.nix.dao.impl.SysOrderDaoImpl;
import org.nix.dao.repositories.CityJpa;
import org.nix.dao.repositories.SysOrderJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}