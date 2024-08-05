package jedistest.jedisdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jedistest.User.pojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
class StringRedisTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void testString() {
        stringRedisTemplate.opsForValue().set("hello", "123");
        Object name = stringRedisTemplate.opsForValue().get("hello");
        System.out.println("name: " + name);
    }

    @Autowired
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testobject() throws JsonProcessingException {
        //创建对象
        pojo user = new pojo("tiger", 5);
        //手动序列化
        String s = mapper.writeValueAsString(user);
        //写入数据
        stringRedisTemplate.opsForValue().set("hello",s);
        //获取数据
        String hello = stringRedisTemplate.opsForValue().get("hello");
        //手动反序列化
        pojo result = mapper.readValue(hello, pojo.class);
        System.out.println("result: " + result);
    }

    @Test
    void testhash() throws JsonProcessingException {
        stringRedisTemplate.opsForHash().put("hello:hash", "hello", "redis");
        stringRedisTemplate.opsForHash().put("hello:hash", "goodnight", "idea");
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("hello:hash");
        System.out.println("entries: " + entries);
    }
}
