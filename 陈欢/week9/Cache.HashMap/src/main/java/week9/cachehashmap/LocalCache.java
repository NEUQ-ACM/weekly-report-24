package week9.cachehashmap;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class LocalCache {
    public static HashMap<String,String> cache = new HashMap<>();

    static {
        String name = 1 + "-" + UUID.randomUUID().toString();
        LocalCache.cache.put(String.valueOf(1),name);
        System.out.println("id为" + 1 + "的数据加入到了缓存");
    }

    @PostConstruct
    public  void init(){
        String name = 2 + "-" + UUID.randomUUID().toString();
        LocalCache.cache.put(String.valueOf(2),name);
        System.out.println("id为" + 2 + "的数据加入到了缓存");
    }
}
