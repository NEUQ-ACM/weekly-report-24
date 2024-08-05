package practice_project.project;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@SpringBootTest
class ProjectApplicationTests {

    @Test
    public void testUUID()
    {
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }
    }

    @Test
    public void testGenJWT(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name","tom");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "project123156465562316546528954449856498546856485164856489648956489649869489456465") //签名算法
                .setClaims(claims) //自定义内容（载荷）
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000))//设置有效期为h
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJWT(){
        Claims claims = Jwts.parser()
                .setSigningKey("project123156465562316546528954449856498546856485164856489648956489649869489456465")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcyMjUxMjcwNn0.EZA4XR5B_Qb_vT0851DAaamAeYKPmTKtluk2BRWNDf8")
                .getBody();
        System.out.println("claims: " + claims);
    }

}
