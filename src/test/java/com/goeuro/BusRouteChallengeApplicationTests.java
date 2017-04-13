package com.goeuro;

import com.goeuro.routes.DirectBusResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"nonOptionArgs=src/test/resources/test.data"})
public class BusRouteChallengeApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void existingRouteTest() {
        DirectBusResponse response = restTemplate.getForObject("/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", DirectBusResponse.class, 3, 6);
        assertThat(response.isDirectRouteExists()).isEqualTo(true);
    }

    @Test
    public void nonExistingRouteTest() {
        DirectBusResponse response = restTemplate.getForObject("/direct?dep_sid={dep_sid}&arr_sid={arr_sid}", DirectBusResponse.class, 0, 5);
        assertThat(response.isDirectRouteExists()).isEqualTo(false);

    }

}
