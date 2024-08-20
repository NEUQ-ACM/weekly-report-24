package week9.cachehashmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import week9.cachehashmap.LocalCache;

import java.util.UUID;

@RestController
public class CacheController {

    @RequestMapping("/test/{id}")
    public String test(@PathVariable Long id) {
        String name = LocalCache.cache.get(String.valueOf(id));
        if(name != null) {
            System.out.println("缓存中存在，查询缓存");
            System.out.println(name);
            return name;
        }
        System.out.println("缓存中不存在，查询数据库");
        name = id + "-" + UUID.randomUUID().toString();
        System.out.println(name);
        LocalCache.cache.put(String.valueOf(id), name);
        return name;
    }
}
