package aw.bowl.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest({"eureka.client.enabled:false"})
public class BowlServiceApplicationTests {

    @MockBean
    private CommandLineRunner runner;

	@Test
	public void contextLoads() {
	}

}
