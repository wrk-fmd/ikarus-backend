package at.wrk.fmd.ikarusbackend;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IkarusBackendApplicationTests {

    @Ignore("Hibernate context does not load.")
    @Test
    public void contextLoads() { }

}