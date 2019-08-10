package cucn.collect.common.Controller;

import cucn.collect.common.CommonParts.Result;
import cucn.collect.common.domain.Jedis.RedisConstans;
import cucn.collect.common.domain.Jedis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/jedis")
@Slf4j
public class JedisController {
    @Autowired
    RedisUtils redisUtils;


    final
    RedisTemplate redisTemplate;

    @Autowired
    public JedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
     * 批量存储测试数据
     * */
    @RequestMapping("/jedisTestSource")
    @ResponseBody
    public Result TestSource() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i <= 100; i++) {
            redisUtils.set(RedisConstans.TESTKEYS + i, "这是第" + i + "条数据", 0);
        }
        long end = System.currentTimeMillis();
        return Result.successData((end - begin) / 1000);
    }

    /*String类型 部分通用类型
    1.string set(String key, String value, int indexdb)    设置值，成功返回OK
    2.long expire(String key, int value, int indexdb)    设置超时时间，返回key剩余的时间
    3.Long append(String key, String str, int indexdb)    在已有字段基础上追加字段,返回最新字段的长度
    4.Boolean exists(String key, int indexdb)               判断key是否存在
    5.String flushDB(int indexdb)                          清空db里所有的值，成功返回OK
    6.Long ttl(String key, int indexdb)                  返回key剩余的生存时间 存在key无过期时间-1，不存在key -2，正常 返回剩余时间
    7.Long persist(String key, int indexdb)               移除key的过期时间，让它永不过期,当生存时间移除成功时，返回 1 .如果 key 不存在或 key 没有设置生存时间，返回 0 ， 发生异常 返回 -1
    8.Long setnx(String key, String value, int indexdb)    成功返回1，存在和异常返回0
    9.String getSet(String key, String value, int indexdb)    不存在返回null,存在的话用新值替换老值,并返回老值
    10.setrange(String key, String str, int offset, int indexdb) str为要替换成的文字，offset为起始替换索引，返回最终字符串的长度
    11.List<String> mget(int indexdb, String... keys)     同时get多个key，返回一个List集合
    12.String mset(int indexdb, String... keysvalues)     同时设置多个key，value。以逗号隔开，返回OK
    13.Long incr(String key, int indexdb)                  访问一次，自增一次.不存在从1开始
    14.Long incrBy(String key, Long integer, int indexdb)  访问一次增加指定的value值，如果不存在，值为value
    15.Long serlen(String key, int indexdb)                 返回key的指定长度
    16.String type(int indexdb, String key)                 返回指定key的类型
    *
    * */

/*Hash类型
1.Long hset(String key, String field, String value, int indexdb) 为hash类型的key赋值，成功返回1，key存在返回0，异常返回null
2.String hget(String key, String field, int indexdb)              返回指定的key field 对应的value值
3.String hmset(String key, Map<String, String> hash, int indexdb)  同时设置多组keyvalue。成功返回OK，异常返回Null
4.List<String> hmget(String key, int indexdb, String... fields)    根据Key的多个field 返回一个List的集合
5.Long hincrby(String key, String field, Long value, int indexdb)   自增
6.Boolean hexists(String key, String field, int indexdb)            判断key是否存在
7.Long hlen(String key, int indexdb)                                根据key判断field的个数
8.Long hdel(int indexdb, String key, String... fields)              根据key删除指定的field集合
9.Set<String> hkeys(String key, int indexdb)                        通过key返回所有的field
10.List<String> hvals(String key, int indexdb)                      通过key返回所有的value
11.Map<String, String> hgetall(String key, int indexdb)             通过key返回对应的map信息
*
* */

/*List
1.Long rpush(int indexdb, String key, String... strs)  从左边插入 可以是一个数组,返回插入后的长度
2.Long rpush(int indexdb, String key, String... strs)  从右边插入 可以是一个数组，返回插入后的长度
3.String lset(int indexdb, String key, Long index, String value) 通过key设置list指定下标位置的value
4.Long lrem(int indexdb, String key, long count, String value) 移除指定个数个value元素，返回移除成功的个数
5.String ltrim(int indexdb, String key, long start, long end)   保留指定的起始结束索引之间的值
6.synchronized public String lpop(int indexdb, String key)      删除从左到右的第一位，并返回该值
7.synchronized public String rpop(int indexdb, String key)      删除从右到左第一位，并返回该值
8.String rpoplpush(String srckey, String dstkey, int indexdb)   从原集合尾部删除一个值并添加到新集合的头部。并返回该值
9.String lindex(String key, long index, int indexdb)            得到指定key的指定索引的value
10.Long llen(String key, int indexdb)                           得到指定key的长度
11.List<String> lrange(String key, long start, long end, int indexdb) 得到指定key的开始索引和结束索引中间的值.如果start为0结束为-1返回所有
* */

    /*Set
    1.Long sadd(int indexdb, String key, String... members)     通过key向指定的set中添加value,返回添加成功的个数（无序存储，不能重复）
    2.Long srem(int indexdb, String key, String... members)     移除指定key的指定值，返回移除成功的个数
    3.String spop(String key, int indexdb)                      从指定的set集合中pop出一个随机值
    4.Set<String> sdiff(int indexdb, String... keys)            取两个Set集合的差集，以第一个key为准
    5.Long sdiffstore(int indexdb, String dstkey, String... keys) 取不同集合的差集存在目的Key里面
    6.Set<String> sinter(int indexdb, String... keys)            获取指定keys的交集
    7.Long sinterstore(int indexdb, String dstkey, String... keys) 讲指定keys的交集存储到目标key，存在相同的值会被覆盖，返回插入成功的个数,相同值被覆盖
    8.Long sunionstore(int indexdb, String dstkey, String... keys)  所有的key存入到一个最终的key中 返回存入的个数，相同覆盖
    9.Long smove(int indexdb, String srckey, String dstkey, String member) 将指定key中的值移除添加到目的key中
    10.Long scard(int indexdb, String key)                          通过key返回value的个数
    11.Boolean sismember(int indexdb, String key, String member)        判断value是否key中元素
    12.String srandmember(int indexdb, String key)                  随机获取key中的一个value
    13.Set<String> smembers(int indexdb, String key)                获取key中所有的value
    *
    *
    * */


    /*zset 不允许重复
     * 1.Long zadd(int indexdb, String key, double score, String member)      添加元素，指定key与score（排名），不想允许重复元素出现
     * 2.Set<String> zrange(int indexdb, String key, long min, long max)      返回指定排名区间的set集合
     * 3.Long zcount(int indexdb, String key, double min, double max)          返回指定排名区间元素的个数
     * 4.Long zrem(int indexdb, String key, String... members)                 移除指定排名区间的value值
     * 5.Double zincrby(int indexdb, String key, double score, String member)   给指定key的指定value增加score个值，返回中增加后的score值
     * 6.Long zrank(int indexdb, String key, String member)                      返回指定member在key中的排名，从0开始（从小到大）
     * 7.Long zrevrank(int indexdb, String key, String member)                    排名（从大到小）
     * 8.Set<String> zrangebyscore(int indexdb, String key, String max, String min)  返回指定score值之内的set集合
     * 9.Long zcard(int indexdb, String key)                                         通过key返回整个zset中值的个数
     * 10.Double zscore( int indexdb,String key, String member)                     返回指定的key中的value对应的排名
     * 12.Long zremrangeByRank(int indexdb, String key, long start, long end)       移除指定索引区间内的值
     * 13.Long zremrangeByScore(int indexdb, String key, double start, double end)  移除指定score区间内的值
     * */


    /*
     * jedis String的get和set方法
     * */
    @RequestMapping("/jedisGSet")
    @ResponseBody
    public Result getRedis() {
        redisUtils.set(RedisConstans.TESTKEYS, "这是一条数据", RedisConstans.REDISDBINDEX);
        Long resExpire = redisUtils.expire(RedisConstans.TESTKEYS, 60, RedisConstans.REDISDBINDEX);//设置key过期时间
        log.info("resExpire=" + resExpire);
        String res = redisUtils.get(RedisConstans.TESTKEYS, RedisConstans.REDISDBINDEX);
        return Result.successData(res);
    }

    /*
     * jedis的del方法
     * */
    @RequestMapping("/jedisDel")
    @ResponseBody
    public Result DelRedis() {
        redisUtils.set(RedisConstans.TESTKEYS + 1, "这是第一条数据", RedisConstans.REDISDBINDEX);
        redisUtils.set(RedisConstans.TESTKEYS + 2, "这是第二条数据", RedisConstans.REDISDBINDEX);
        long s = redisUtils.del(RedisConstans.REDISDBINDEX, RedisConstans.TESTKEYS + 1, RedisConstans.TESTKEYS + 2);
        return Result.successData(s);
    }

    /*
     * jedis的append方法,在原有基础上添加字段
     * */
    @RequestMapping("/jedisAppend")
    @ResponseBody
    public Result AppendRedis() {
        redisUtils.set(RedisConstans.TESTKEYS + 1, "abc", RedisConstans.REDISDBINDEX);
        Long s = redisUtils.append(RedisConstans.TESTKEYS + 1, "def", RedisConstans.REDISDBINDEX);
        return Result.successData(s);
    }

    /*
     * 某个DB中是否存在某个key
     * */
    @RequestMapping("/jedisExist")
    @ResponseBody
    public Result jedisExist() {
        boolean result = redisUtils.exists(RedisConstans.TESTKEYS + 1, RedisConstans.REDISDBINDEX);
        return Result.successData(result);
    }

    /*
     * 清空某个db中所有的key
     * */
    @RequestMapping("/jedisFlushDB")
    @ResponseBody
    public Result jedisFlushDB() {
        String result = redisUtils.flushDB(RedisConstans.REDISDBINDEX - 1);
        return Result.successData(result);
    }

    /*
     * 返回选中key的剩余生存时间
     * */
    @RequestMapping("/jedisttl")
    @ResponseBody
    public Result jedisttl() {
        redisUtils.set(RedisConstans.TESTKEYS + 2, "test", RedisConstans.REDISDBINDEX);
        redisUtils.expire(RedisConstans.TESTKEYS + 2, 6, RedisConstans.REDISDBINDEX);
        //存在的一个不过期key
        long result1 = redisUtils.ttl(RedisConstans.TESTKEYS + 1, RedisConstans.REDISDBINDEX);
        //一个5秒超时的key
        long result2 = redisUtils.ttl(RedisConstans.TESTKEYS + 2, RedisConstans.REDISDBINDEX);
        //一个不存在的key
        long result3 = redisUtils.ttl(RedisConstans.TESTKEYS + 3, RedisConstans.REDISDBINDEX);
        List<Long> list = new ArrayList<>();
        list.add(result1);//-1
        list.add(result2);//6
        list.add(result3);//-2
        return Result.successData(list);
    }

    /*
     * 移除选中key的剩余时间，让他变成一个不会过期的key
     * */
    @RequestMapping("/jedisPersist")
    @ResponseBody
    public Result persist() {
        redisUtils.set(RedisConstans.TESTKEYS + "persist", "test", RedisConstans.REDISDBINDEX);
        redisUtils.expire(RedisConstans.TESTKEYS + "persist", 50, RedisConstans.REDISDBINDEX);
        long result = redisUtils.persist(RedisConstans.TESTKEYS + "persist", RedisConstans.REDISDBINDEX);
        return Result.successData(result);
    }

    /*
     * 新增一个key同时为他设置一个超时时间
     * */
    @RequestMapping("/jedisSetex")
    @ResponseBody
    public Result jedisSetex() {
        String result = redisUtils.setex(RedisConstans.TESTKEYS + "SETEX", 60, "setex", RedisConstans.REDISDBINDEX);
        return Result.successData(result);
    }

    /*
     * 新增一个key同时为他设置一个超时时间
     * 存在返回0 不存在返回ok并设置一个新的key和value
     * */
    @RequestMapping("/jedisSetnx")
    @ResponseBody
    public Result jedisSetnx() {
        Long result = redisUtils.setnx(RedisConstans.TESTKEYS + "SETEX", "setnx", RedisConstans.REDISDBINDEX);
        return Result.successData(result);
    }

    /*
     * getset命令,如果不存在添加值。如果存在，替换value返回老值
     * */
    @RequestMapping("/jedisGetSet")
    @ResponseBody
    public Result jedisGetSet() {
        //先设置一个值
        String result = redisUtils.getSet("ll", "00", 1);
        return Result.successData(result);
    }

    /*
     *offset是起始替换的索引，str为替换成的字符串
     * */
    @RequestMapping("/jedisSetrange")
    @ResponseBody
    public Result jedisSetrange() {
        //先设置一个值
        redisUtils.set(RedisConstans.TESTKEYS + "Setrange", "abcdefg", RedisConstans.REDISDBINDEX);
        long result = redisUtils.setrange(RedisConstans.TESTKEYS + "Setrange", "111", 2, RedisConstans.REDISDBINDEX);//ab111fg
        return Result.successData(result);
    }

    /*
     * mget 获取多个数据
     * */
    @RequestMapping("/jedisMget")
    @ResponseBody
    public List jedisMget() {
        //List<String> lists = redisUtils.mget(RedisConstans.REDISDBINDEX + 2, RedisConstans.TESTKEYS + 1, RedisConstans.TESTKEYS + 2, RedisConstans.TESTKEYS + 3);
        List<String> lists = redisUtils.mget(RedisConstans.REDISDBINDEX + 2, new String[]{RedisConstans.TESTKEYS + 5, RedisConstans.TESTKEYS + 6, RedisConstans.TESTKEYS + 7});
        return lists;
    }

    /*
     * mset 设置多个数据
     * */
    @RequestMapping("/jedisMset")
    @ResponseBody
    public Result jedisMset() {
        //String result= redisUtils.mset(RedisConstans.REDISDBINDEX,"a","b","c","d");
        String result = redisUtils.mset(RedisConstans.REDISDBINDEX, new String[]{"key1", "value1", "key23", "value23"});
        return Result.successData(result);
    }

    /*
     * incr 自增,页面访问一次增加一位，如果key不存在会自动创建
     * */
    @RequestMapping("/jedisIncr")
    @ResponseBody
    public Result jedisIncr() {
        redisUtils.set(RedisConstans.TESTKEYS + "COUNT", "9", RedisConstans.REDISDBINDEX);
        Long count = redisUtils.incr(RedisConstans.TESTKEYS + "COUNT", RedisConstans.REDISDBINDEX);
        return Result.successData(count);
    }

    /*
     * incrby 增加指定的值,如果初始没有，初始值就是vallue
     * */
    @RequestMapping("/jedisIncrBy")
    @ResponseBody
    public Result jedisIncrBy() {
        Long count = redisUtils.incrBy(RedisConstans.TESTKEYS + "INCRBY", 1l, RedisConstans.REDISDBINDEX);
        return Result.successData(count);
    }

    /*
     * 根据key获取value的长度
     * */
    @RequestMapping("/jedisSerlen")
    @ResponseBody
    public Result jedisSerlen() {
        Long count = redisUtils.serlen(RedisConstans.TESTKEYS + "INCRBY", RedisConstans.REDISDBINDEX);
        return Result.successData(count);
    }


    /*
     * hget hset
     * */
    @RequestMapping("/jedisHset")
    @ResponseBody
    public Result jedisHset() {
        Long count = redisUtils.hset(RedisConstans.TESTKEYS + "HSET1", "HGETKEY", "HGETKEY1", RedisConstans.REDISDBINDEX);
        String hset = redisUtils.hget(RedisConstans.TESTKEYS + "HSET", "HGETKEY", RedisConstans.REDISDBINDEX);
        return Result.successData(count + hset);
    }

    /*
     * hmset
     * */
    @RequestMapping("/jedisHmset")
    @ResponseBody
    public Result jedisHmset() {
        Map<String, String> hash = new HashMap<>();
        hash.put("KEY1", "VALUE1");
        hash.put("KEY2", "VALUE2");
        hash.put("KEY3", "VALUE3");
        String count = redisUtils.hmset(RedisConstans.TESTKEYS + "HMSET", hash, RedisConstans.REDISDBINDEX);
        return Result.successData(count);
    }

    /*
     * hmget
     * */
    @RequestMapping("/jedisHmget")
    @ResponseBody
    public Result jedisHmget() {
        List<String> list = redisUtils.hmget(RedisConstans.TESTKEYS + "HMSET", RedisConstans.REDISDBINDEX, new String[]{"KEY1", "KEY2", "KEY3"});
        return Result.successData(list);
    }

    /*
     * hincrby
     * */
    @RequestMapping("/jedisHincrby")
    @ResponseBody
    public Result jedisHincrby() {
        long l = redisUtils.hincrby(RedisConstans.TESTKEYS + "HMGET", "KEY1", 1l, RedisConstans.REDISDBINDEX);
        return Result.successData(l);
    }

    /*
     * hexists
     * */
    @RequestMapping("/jedisHexists")
    @ResponseBody
    public Result jedisHexists() {
        boolean l = redisUtils.hexists(RedisConstans.TESTKEYS + "HMGET", "KEY1", RedisConstans.REDISDBINDEX);
        return Result.successData(l);
    }

    /*
     * hkeys 查询大key对应所有的小key
     * */
    @RequestMapping("/jedisHkeys")
    @ResponseBody
    public Result jedisHkeys() {
        Set<String> keySet = redisUtils.hkeys(RedisConstans.TESTKEYS + "HMSET", RedisConstans.REDISDBINDEX);
        return Result.successData(keySet);
    }

    /*
     * hvals 通过大key获取所有的val
     * */
    @RequestMapping("/jedisHvals")
    @ResponseBody
    public Result jedisHvals() {
        List<String> keySet = redisUtils.hvals(RedisConstans.TESTKEYS + "HMSET", RedisConstans.REDISDBINDEX);
        return Result.successData(keySet);
    }

    /*
     * hgetall 通过大key获取对应的field和value
     * */
    @RequestMapping("/jedisGetAll")
    @ResponseBody
    public Result jedisGetAll() {
        Map<String, String> hashMap = redisUtils.hgetall(RedisConstans.TESTKEYS + "HMSET", RedisConstans.REDISDBINDEX);
        return Result.successData(hashMap);
    }


    /*
     * lpush rpush
     * */
    @RequestMapping("/jedisLRpush")
    @ResponseBody
    public Result jedisLRpush() {
        Long result = redisUtils.rpush(RedisConstans.REDISDBINDEX + 2, "lrpush", new String[]{"aa", "bb", "cc"});
        Long result2 = redisUtils.lpush(RedisConstans.REDISDBINDEX + 2, "lrpush", new String[]{"aa", "bb", "cc"});
        return Result.successData(result2);
    }

    /*
     * lrem
     * */
    @RequestMapping("/jedisLrem")
    @ResponseBody
    public Result jedisLset() {
        long result = redisUtils.lrem(RedisConstans.REDISDBINDEX + 2, "lrpush", 2L, "aa");
        return Result.successData(result);
    }

    /*
     * ltrim 只保留key从开始索引到结束索引的值
     * */
    @RequestMapping("/jedisLtrim")
    @ResponseBody
    public Result jedisLtrim() {
        String result = redisUtils.ltrim(RedisConstans.REDISDBINDEX + 2, "lrpush", 0L, 2l);
        return Result.successData(result);
    }

    /*
     * lpop 通过key从list的左到右第一位删除
     * */

    @RequestMapping("/jedisLpop")
    @ResponseBody
    public Result jedisLpop() {
        String result = redisUtils.lpop(RedisConstans.REDISDBINDEX + 2, "lrpush");
        return Result.successData(result);
    }
    /*
     * rpop 通过key从list的右到左第一位删除
     * */

    @RequestMapping("/jedisRpop")
    @ResponseBody
    public Result jedisRpop() {
        String result = redisUtils.rpop("lrpush", RedisConstans.REDISDBINDEX + 2);
        return Result.successData(result);
    }

    /*
     * rpoplpush 第一个key的最右边一位删除，添加到第二个key的最左边
     * */
    @RequestMapping("/jedisRpoplpush")
    @ResponseBody
    public Result jedisRpoplpush() {
        String result = redisUtils.rpoplpush("lrpush", "lrpush2", RedisConstans.REDISDBINDEX + 2);
        return Result.successData(result);
    }

    /*
     * lindex 获取指定key的index的值
     * */
    @RequestMapping("/jedisLindex")
    @ResponseBody
    public Result jedisLindex() {
        String result = redisUtils.lindex("lrpush2", 2L, RedisConstans.REDISDBINDEX + 2);
        return Result.successData(result);
    }

    /*
     * llen通过key获取list的长度
     * */
    @RequestMapping("/jedisLlen")
    @ResponseBody
    public Result jedisLlen() {
        Long result = redisUtils.llen("lrpush2", RedisConstans.REDISDBINDEX + 2);
        return Result.successData(result);
    }

    /*
     * lrange 通过key获取开始结束索引
     * */
    @RequestMapping("/jedisLrange")
    @ResponseBody
    public Result jedisLrange() {
        List<String> result = redisUtils.lrange("lrpush2", 0L, 3L, RedisConstans.REDISDBINDEX + 2);
        return Result.successData(result);
    }


    /*
     * sadd 给指定的set添加 value set 无顺 不能重复
     * */
    @RequestMapping("/jedisSadd")
    @ResponseBody
    public Result jedisSadd() {
        Long result = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey1", new String[]{"no1", "no2", "no3", "no4"});
        Long resul2 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey2", new String[]{"no1", "no2", "no3", "no6"});
        Long resul3 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey2", new String[]{"no1"});
        return Result.successData(result);
    }

    /*
     * srem 删除给定value中的值
     * */
    @RequestMapping("/jedisSrem")
    @ResponseBody
    public Result jedisSrem() {
        long result = redisUtils.srem(RedisConstans.REDISDBINDEX + 2, "setkey1", new String[]{"no1", "no2", "no3", "no4"});
        return Result.successData(result);
    }

    /*
     * spop set中随机删除一个key
     * */
    @RequestMapping("/jedisSpop")
    @ResponseBody
    public Result jedisSpop() {
        String result = redisUtils.spop("setkeys", RedisConstans.REDISDBINDEX + 2);
        return Result.successData(result);
    }

    /*
     * sdiff 取两个set集合的差集，差集以第一个集合为准
     * */
    @RequestMapping("/jedisSdiff")
    @ResponseBody
    public Result jedisSdiff() {
        Long result = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey1", new String[]{"no1", "no2", "no3", "no4"});
        Long resul2 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey2", new String[]{"no1", "no2", "no3", "no6"});
        Long resul3 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey3", new String[]{"no1"});
        Set<String> re = redisUtils.sdiff(RedisConstans.REDISDBINDEX + 2, "setkey2", "setkey1", "setkey3");
        return Result.successData(re);
    }

    /*
     * sdiffstore 取差集存入一个新的set集合,原来集合会被覆盖
     * */
    @RequestMapping("/jedisSdiffstore")
    @ResponseBody
    public Result jedisSdiffstore() {
        long result = redisUtils.sdiffstore(RedisConstans.REDISDBINDEX + 8, "set03", new String[]{"set01", "set02"});
        return Result.successData(result);
    }

    /*
     *sinter
     * */
    @RequestMapping("/jedisSinter")
    @ResponseBody
    public Result jedisSinter() {
        Set<String> s = redisUtils.sinter(RedisConstans.REDISDBINDEX + 8, new String[]{"set01", "set02"});
        return Result.successData(s);
    }

    /*
     *sinterstore destkey中有多个值的时候会被覆盖
     * */
    @RequestMapping("/jedisSinterStore")
    @ResponseBody
    public Result jedisSinterStore() {
        Long result = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey1", new String[]{"no1", "no2", "no3", "no4"});
        Long resul2 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey2", new String[]{"no1", "no2", "no3", "no6"});
        Long resul3 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey3", new String[]{"no1"});
        long s = redisUtils.sinterstore(RedisConstans.REDISDBINDEX + 2, "setkey3", new String[]{"setkey2", "setkey1"});
        return Result.successData(s);
    }

    /*
     *过key返回所有set的并集,相同值只出现一次
     * */
    @RequestMapping("/jedisUnion")
    @ResponseBody
    public Result jedisUnion() {
        Long result = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey1", new String[]{"no1", "no2", "no3", "no4"});
        Long resul2 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey2", new String[]{"no1", "no2", "no3", "no6"});
        Long resul3 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey3", new String[]{"no1"});
        Set<String> s = redisUtils.sunion(RedisConstans.REDISDBINDEX + 2, new String[]{"setkey1", "setkey2", "setkey3"});
        return Result.successData(s);
    }

    /*
     *通过key返回所有set的并集,并存入到新的set中
     * */
    @RequestMapping("/jediSunionstore")
    @ResponseBody
    public Result jediSunionstore() {
        long s = redisUtils.sunionstore(RedisConstans.REDISDBINDEX + 2, "setkey3", new String[]{"setkey1", "setkey2"});
        return Result.successData(s);
    }

    /*
     *通过key将set中的一个value移除并添加到第二个set中
     * */
    @RequestMapping("/jediSmove")
    @ResponseBody
    public Result jediSmove() {
        Long result = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey1", new String[]{"no1", "no2", "no3", "no4"});
        Long resul2 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey2", new String[]{"no1", "no2", "no3", "no6"});
        Long resul3 = redisUtils.sadd(RedisConstans.REDISDBINDEX + 2, "setkey3", new String[]{"no1"});
        long s = redisUtils.smove(RedisConstans.REDISDBINDEX + 2, "setkey2", "setkey3", "no1");
        return Result.successData(s);
    }

    /*
     * 通过key获取value的个数
     * */
    @RequestMapping("/jedisScard")
    @ResponseBody
    public Result jedisScard() {
        long s = redisUtils.scard(RedisConstans.REDISDBINDEX + 8, "set03");
        return Result.successData(s);
    }

    //sismember
    /*
     * 通过key判断value是否key中的元素
     * */
    @RequestMapping("/jedisSismember")
    @ResponseBody
    public Result jedisSismember() {
        Boolean s = redisUtils.sismember(RedisConstans.REDISDBINDEX + 8, "set03", "33");
        return Result.successData(s);
    }

    //srandmember
    /*
     * 通过key判断value中随机的value
     * */
    /*
     * 通过key判断value是否key中的元素
     * */
    @RequestMapping("/jedisSrandmember")
    @ResponseBody
    public Result jedisSrandmember() {
        String s = redisUtils.srandmember(9, "set02");
        return Result.successData(s);
    }

    //smembers
    /*
     * 根据key获取所有的value
     * */
    @RequestMapping("/jedisSmember")
    @ResponseBody
    public Result jedisSmember() {
        Set<String> s = redisUtils.smembers(9, "set02");
        return Result.successData(s);
    }


    //zadd
    /*
     * 根据key获取所有的value
     * */
    @RequestMapping("/jedisZadd")
    @ResponseBody
    public Result jedisZadd() {
        Long s = redisUtils.zadd(9, "zset01", 7, "1");
        redisUtils.zadd(9, "zset01", 8, "1");
        redisUtils.zadd(9, "zset01", 6, "2");
        redisUtils.zadd(9, "zset01", 5, "3");
        redisUtils.zadd(9, "zset01", 4, "4");
        redisUtils.zadd(9, "zset01", 3, "5");
        redisUtils.zadd(9, "zset01", 2, "6");
        return Result.successData(s);
    }

    //zrange zcount
    /*
     * 返回有序集 key 中，指定区间内的成员,
     * 返回一共有多少个
     * */
    @RequestMapping("/jedisZrange")
    @ResponseBody
    public Result jedisZrange() {
        Set<String> s = redisUtils.zrange(9, "zset01", 1L, 8L);
        Long l = redisUtils.zcount(9, "zset02", 1L, 8L);
        return Result.successData(s);
    }

    //hincrBy
    /*
     * 计数器（积分签到） incrby做页面总共又多少人访问
     * */
    @RequestMapping("/jedisHincrBy")
    @ResponseBody
    public Result jedisHincrBy() {
        long s = redisUtils.hincrby("incr", "incrvalue", 2L, 9);
        return Result.successData(s);
    }


    //zrem 通过key删除指定的value
    @RequestMapping("/jedisZrem")
    @ResponseBody
    public Result jedisZrem() {
        long s = redisUtils.zrem(9, "incr", new String[]{"9"});
        return Result.successData(s);
    }

    //zincrby 通过key增加zset中value的score值 member为要增加的字段
    @RequestMapping("/jedisZincrby")
    @ResponseBody
    public Result jedisZincrby() {
        redisUtils.zadd(9, "zset01", 8, "1");
        redisUtils.zadd(9, "zset01", 6, "2");
        redisUtils.zadd(9, "zset01", 5, "3");
        redisUtils.zadd(9, "zset01", 4, "4");
        redisUtils.zadd(9, "zset01", 3, "5");
        redisUtils.zadd(9, "zset01", 2, "6");
        double s = redisUtils.zincrby(9, "zset01", 3.0, "6");
        return Result.successData(s);
    }

    //zrank 返回value在key中的排名（从小到大,从0开始）
    @RequestMapping("/jedisZrank")
    @ResponseBody
    public Result jedisZrank() {
        long s = redisUtils.zrank(9, "zset", "rr");
        return Result.successData(s);
    }

    //zrevrank 返回value在key中的排名（从大到小,从0开始）
    @RequestMapping("/jedisZrevrank")
    @ResponseBody
    public Result jedisZrevrank() {
        long s = redisUtils.zrevrank(9, "zset", "rr");
        return Result.successData(s);
    }

    //zrangebyscore 指定范围内zset的value,max和min都为zset的score值
    @RequestMapping("/jedisZrangebyscore")
    @ResponseBody
    public Result jedisZrangebyscore() {
        Set<String> s = redisUtils.zrangebyscore(9, "zset", "5", "2");
        return Result.successData(s);
    }

    //zcount指定范围内key中的value个数
    @RequestMapping("/jedisZcount")
    @ResponseBody
    public Result jedisZcount() {
        long s = redisUtils.zcount(9, "zset", "2", "5");
        return Result.successData(s);
    }

    //zcard 通过key返回zset中value的个数
    @RequestMapping("/jedisZcard")
    @ResponseBody
    public Result jedisZcard() {
        long s = redisUtils.zcard(9, "zset");
        return Result.successData(s);
    }

    //zscore 通过key获取value在zset中的value值
    @RequestMapping("/jedisZscore")
    @ResponseBody
    public Result jedisZscore() {
        double s = redisUtils.zscore(9, "zset", "rr");
        return Result.successData(s);
    }

    //zremrangeByRank 移除指定区间的值 start end为索引值
    @RequestMapping("/jedisZremrangeByRank")
    @ResponseBody
    public Result jedisZremrangeByRank() {
        redisUtils.zadd(9, "zset01", 8, "1");
        redisUtils.zadd(9, "zset01", 6, "2");
        redisUtils.zadd(9, "zset01", 5, "3");
        redisUtils.zadd(9, "zset01", 4, "4");
        redisUtils.zadd(9, "zset01", 3, "5");
        redisUtils.zadd(9, "zset01", 2, "6");
        long s = redisUtils.zremrangeByRank(9, "zset01", 1, 4);
        return Result.successData(s);
    }

    //zremrangeByScore移除指定区间的值 start end为score值
    @RequestMapping("/jedisZremrangeByScore")
    @ResponseBody
    public Result jedisZremrangeByScore() {
        long s = redisUtils.zremrangeByScore(9, "zset", 1, 7);
        return Result.successData(s);
    }

    //type返回指定key的type
    @RequestMapping("/jedisType")
    @ResponseBody
    public Result jedisType() {
        String s = redisUtils.type(RedisConstans.REDISDBINDEX, RedisConstans.TESTKEYS + "COUNT");
        return Result.successData(s);
    }

    //type返回指定key的type
    @RequestMapping("/jedisPfadd")
    @ResponseBody
    public Result jedisPfadd() {
        for (int i = 0; i <= 1000; i++) {
            redisUtils.pfadd(2, "pfadd", "1" + i);
        }

        return Result.successData("插入完成");
    }


}