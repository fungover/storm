package org.fungover.storm.integration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class IntegrationTest {

    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);

    @Test
    void testRedisIntegration() {
    // Get the Redis container host and port
        String redisHost = redisContainer.getHost();
        Integer redisPort = redisContainer.getMappedPort(6379);

    // Create a Jedi pool that connects to the Redis container.
        JedisPool jedisPool = new JedisPool(redisHost, redisPort);

    // Performing a Redis operation, such as a set and goat
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("testKey", "testValue");
            String result = jedis.get("testKey");

    // Check that the operation was successful
            assertEquals("testValue", result);
        }
    }
}
