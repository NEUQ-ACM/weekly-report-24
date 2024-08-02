package jedistest.jedisdemo;

import jedistest.User.pojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@SpringBootTest
class JedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Test
    void testString() {
        redisTemplate.opsForValue().set("hello", "123");
        Object name = redisTemplate.opsForValue().get("hello");
        System.out.println("name: " + name);
    }

    @Test
    void testobject()
    {
        redisTemplate.opsForValue().set("hello",new pojo("tiger",50));
        Object result = redisTemplate.opsForValue().get("hello");
        System.out.println("result: " + result);
    }

    @Test
    void testhash()
    {
        redisTemplate.opsForHash().put("hello:hash", "hello", "redis");
        redisTemplate.opsForHash().put("hello:hash", "goodnight", "idea");
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("hello:hash");
        System.out.println("entries: " + entries);
    }
}
