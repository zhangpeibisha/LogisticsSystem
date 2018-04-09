import org.junit.Test;
import org.junit.runner.RunWith;
import org.nix.Application;
import org.nix.dao.repositories.CityJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServerApplicationTests {
    @Autowired
    private CityJpa cityJpa;
    @Test
    public void contextLoads() {
        System.out.println(cityJpa.search("%成%"));
    }

}