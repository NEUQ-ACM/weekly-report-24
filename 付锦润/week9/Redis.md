# SQL & NoSQL

> SQL 关系型数据库(Structured 结构化、Relational 关联的、SQL 查询、ACID)
> 磁盘存储
>
> NoSQL 非关系型数据库(非结构化、无关联的、非 SQL 查询、BASE)
> 内存存储

# Redis

> Remote Dictionary Server ，键值数据库(一种 NoSql )，值可以是 json 文档

```properties
# 配置文件
# 默认127.0.0.1，0.0.0.0 用于开发
bind 0.0.0.0

# 后台运行
daemonize yes

# 密码
requirepass 123456

# 端口
port 6379

# 工作目录，默认运行处
dir .

# 数据库数量 (编号0~15)
databases 1

# 最大内存
maxmemory 512mb

# 日志文件
logfile "redis.log"
```

## 数据结构

> key 一般为String类型，value 有更多类型
>
> key 可以写成 A:B:C:id 的形式来分层

1. String

   > 最大空间不能抽过512m

   - string：编码为字节码

   - int：编码为二进制，可自增自减

   - float：编码为二进制，可自增自减

   - 命令：

     > String 类型的 value 是无法单一修改的，如`{A:"1", B:"2"}`，要想修改A，必须进行整体的修改

     - `set key value`：添加新的或修改已存在的String类型键值对
     - `mset key1 key2`：批量添加String类型的键
     - `get key`：根据key获取String类型的value
     - `mget key1 key2`：根据多个key获取多个String类型的value
     - `incr key`：整形key自增1
     - `incrby key num`：整形key自增num，可以为负数
     - `incrbyfloat key num`：浮点型key自增num，必须指定num
     - `setnx key value` ：如果key不存在，则添加一个String类型键值对
       - 也可以set命令后面接nx，效果相同：`set ... nx`
     - `setex key seconds value` ：添加一个String类型键值对，并指定有效期
       - `set key second ex value`

2. Hash

   > value 分为 field 和 value，可以单独进行 CRUD
   >
   > 无序
   > 元素不可重复
   > 查询速度快

   - 命令：
     - `hset key field value`：添加新的或修改已存在的 hash 类型的 key 的 field
     - `hget key field`
     - `hmset key field value field2 value2`
     - `hmget key field field2 `
     - `hgetall key`：获取所有键值对
     - `hkeys key`：获取所有键
     - `kvals key`：获取所有值
     - `hincrby key field num`：让 hash 类型的 字段自增 num
     - `hsetnx key field value`：如果 field 不存在，则添加一个 hash 类型键值对

3. List

   > 支持正向和反向检索
   >
   > 有序
   > 元素可重复
   > 插入删除快
   > 查询速度一般

   - `lpush key element element2`：左插入一个或多个元素(得到element2 <-> element)，注意顺序
   - `lpop key`：移除并返回左侧第一个元素，没有则返回nil
   - `rpush key element`
   - `rpop key`
   - `lrange key start end`：返回角标范围的所有元素
   - `blpop/brpop key seconds`：同lpop和rpop，但在没有元素时进行等待指定时间而不直接返回nil

4. Set

   > 无序
   > 元素不可重复
   > 查询速度快
   > 支持交集、并集、差集等

   - `sadd key member`：向 set 中添加一个或多个元素
   - `srem key member`：移除 set 中的指定元素
   - `scard key`：返回元素个数
   - `sismember key member`：判断一个元素是否在 set 中
   - `smembers`：获取所有元素
   - `sinter key1 key2`：交集
   - `sdiff key1 key2`：差集
   - `sunion key1 key2`：并集

5. SortedSet

   > 可排序
   > 元素不可重复
   > 查询速度快
   >
   > 默认升序，要降序则在命令z后面加rev：zrevrank

   - `zadd key score member`：添加一个或多个元素，如果存在则更新score
   - `zrem`
   - `zscore key member`：获取指定元素的 score 值
   - `zrank key member`：获取指定元素排名
   - `zcard key`：获取元素个数
   - `zcount key min max`：统计 score 区间的元素个数
   - `zincrby key num member`：让指定元素自增num
   - `zrange key min max`：排序后获取指定排名范围内的元素
   - `zrangebyscore key min max`：排序后获取指定 score 范围内的元素
   - `zdiff/zinter/zunion`

6. GEO

7. BitMap

8. HyperLog

## 常用命令

1. `help @group`：查询数据结构命令
2. `keys pattern`：查看符合模板的所有key
3. `del key`：删除指定的key（可批量）
4. `exists key`：判断key是否存在（可批量）
5. `expire key seconds`：给key设置有效期
6. `ttl key`：查看key剩余有效期
7. `select index`：选择数据库

## JAVA客户端

> Spring Data Redis = Jedis + lettuce

### [Jedis](https://github.com/redis/jedis)

> 线程不安全，频繁创建和销毁连接会有性能损耗，推荐使用 Jedis 连接池代替 Jedis 直连

```java
// 直接建立连接
class Xxx {
    private Jedis jedis;

    void setUp() {
        // 建立连接
        jedis = new Jedis("192.168.124.80", 6379);
        // 设置密码
        jedis.auth("123456");
        // 选择数据库
        jedis.select(0);
        // 操作...

        // 关闭
        if (jedis != null) {
            jedis.close();
        }
    }
}

// 连接池
// 连接池依赖查看 SpringDataJedis
public class JedisConnectionFactory {
    private static final JedisPool jedisPool;
    
    // 静态代码块，首次加载到内存中时执行，且只执行一次
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接
        jedisPoolConfig.setMaxTotal(8);
        // 最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        // 最小空闲连接
        jedisPoolConfig.setMinIdel(0);
        // 最长等待时间 ms
        jedisPoolConfig.setMaxWaitMillis(1000);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.124.80", 6379, 1000, "123456");
    }
    
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    
public class Xxx {
    private Jedis jedis;
    
    void setUp() {
        // 建立连接
        jedis = jedisConnectionFactory.getJedis();
        // 设置密码
        jedis.auth("123456");
        // 选择数据库
        jedis.select(0);
        // 操作...

        // 关闭
        if (jedis != null) {
            // 使用 Jedis 连接池时不会真的关闭，只会归还给连接池
            jedis.close();
        }
    }
}
```

### SpringDataJedis

> 在创建项目时导入该依赖即可
> 默认使用 lettuce，如果需要使用 Jedis 还需而外导入 Jedis 依赖
>
> 连接池依赖：
> gourpId：org.apache.commons
> artifactId：commons-pool2

1. 配置信息：

   > 不用通过代码进行配置
   >
   > 注意：这种格式的配置文件后缀为 yml 或 yaml
   > 要求：
   > 数值前必须有空格
   > 缩进不能使用 tab 键，必须使用空格(数量不重要且 idea 会自动把 tab 转化为空格)
   > 相同层级左侧对齐

   ```yml
   # 对象/Map集合
   spring:
     redis:
       host: 192.168.124.80
       port: 6379
       password: 123456
       lettuce:
         pool:
           max-active: 8
           max-idle: 8
           min-idle: 0
           max-wait: 100ms
           
   # 数组/List/Set集合
   hobby:
     - java
     - C
     - python
   ```

2. 使用

   > Json 序列化需要导入依赖：
   > gourpId：com.fasterxml.jackson.core
   > artifactId：jackson-datebind
   >
   > 后续使用mvc时自带该依赖，不再需要手动导入

   ```java
   // 默认序列化
   class {
   	@Autowired
   	private RedisTemplate redisTemplate;
       
       void redis() {
           redisTemplate.opsForValue().set("key", "value");
           Object value = redisTemplate.opsForValue().get("key");
       }
   }
   ```

3. 序列化使用
   ```java
   // 默认 jdk 序列化不推荐使用
   // 推荐 key 使用 String 序列化，value 使用 Json 序列化
   // 创建一个包
   @Configuration  // 告诉 Spring 扫描这个包的组件，以便自动注册 Bean
   public class redis.config.RedisConfig {
       @Bean  // Bean 用于依赖注入
       public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
           // 函数的参数自动注入了
           // 创建 RedisTemplate 对象
           RedisTemplate<String, Object> template = new RedisTemplate<>();
           // 设置连接工厂
           template.setConnectionFactory(connectionFactory);
           // 创建 Json 序列化工具
           GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerialize();
           // 设置 Key 序列化
           template.setKeySerializer(RedisSerialize.String);
           template.setHashKeySerializer(RedisSerialize.String);
           // 设置 Value 序列化
           template.setValueSerializer(jsonRedisSerializer);
           template.setHashValueSerializer(jsonRedisSerializer);
           // 返回
           return template;
       }
   }
   
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class redis.pojo.User {
       private String name;
       private Integer age;
   }
   
   public class Xxx {
       @Autowired
       private RedisTemplate<String, Object> redisTemplate;
       
       void redis() {
           redisTemplate.opsForValue().set("user:1", new User("张三", 30));
           User user = (User) redisTemplate.opsForValue().get("user:1");
       }
   }
   ```

4. 手动序列化使用
   ```java
   // json 序列化会在 redis 的值的字节码中添加类名的字节码，这会带来额外的内存开销
   // 因此我们使用<String, String>的方式
   // Spring 默认提供了双 String 的序列化
   public class Xxx {
       @Autowired
       private StringRedisTemplate stringRedisTemplate;
       // Json 工具
       private static final ObjectMapper mapper = new ObjectMapper();
       
       void redis() throws JsonProcessingException {
           // 准备对象
           User user = new User("张三", 30);
           // 手动序列化
           String userJson = mapper.writeValueAsString(user);
           // 写入到 redis
           stringRedisTemplate.opsForValue().set("user:1", userJson);
           
           // 读取
           userJson = stringRedisTemplate.opsForValue().get("user:1");
           // 反序列化
           user = mapper.readValue(userJson, User.class);
       }
   }
   ```
