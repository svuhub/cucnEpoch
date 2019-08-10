package cucn.collect.common.Controller;

import com.github.pagehelper.util.StringUtil;
import cucn.collect.common.CommonParts.Result;
import cucn.collect.common.CommonParts.utils.JsonUtils;
import cucn.collect.common.CommonParts.utils.RedisKeys;
import cucn.collect.common.CommonParts.utils.RedisLock;
import cucn.collect.common.domain.Jedis.RedisUtils;
import cucn.collect.common.domain.MyBatis.DTO.RoleInfo;
import cucn.collect.common.domain.MyBatis.Service.RoleInfoService;
import cucn.collect.common.domain.redis.LockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("/redis")
public class RedisController {
    int count = 1000;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private LockService lockService;

    @Autowired
    RedisLock redisLock;

    /*
     * 如目录等数据缓存在Redis
     * 以减轻数据库的访问压力
     * */
    @RequestMapping("/type01")
    @ResponseBody
    public List<RoleInfo> catalogCache() {
        String catalogConfig = stringRedisTemplate.opsForValue().get(RedisKeys.CATALOG_CACHE);
        if (StringUtils.isNotBlank(catalogConfig)) {
            List<RoleInfo> roleInfoList = JsonUtils.jsonToList(catalogConfig, RoleInfo.class);
            return roleInfoList;
        } else {
            List<RoleInfo> roleInfoListTemp = roleInfoService.getAll();
            //过滤掉ID字段
            List<RoleInfo> roleInfoList = roleInfoListTemp.stream().filter(roleInfoListStream -> roleInfoListStream.getRid() > 1).collect(Collectors.toList());
            stringRedisTemplate.opsForValue().set(RedisKeys.CATALOG_CACHE, JsonUtils.objectToJson(roleInfoList), 1, TimeUnit.MINUTES);
            return roleInfoList;
        }
    }

    /*
     * pipeline
     * */
    @RequestMapping("/type02")
    @ResponseBody
    public Long pipeline() {
        Pipeline pipeline = redisUtils.getPipeline(5);
        long a = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            for (int j = i * 100; j < (i + 1) * 100; j++) {
                pipeline.hset("hashkey" + j, "field" + j, "value" + j);
            }
            pipeline.syncAndReturnAll();
        }
        long b = System.currentTimeMillis();
        return b - a;
    }

    /*
     * redis锁
     * */
    @RequestMapping("/type03")
    @ResponseBody
    public void lock() throws Exception {
        long start = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(500);
        Executor executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 500; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        lockService.distributeLock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        long costTime = System.currentTimeMillis() - start;
        log.info("it totally took：" + costTime + " ms");
    }

    /*
     * type04和type05为校验使用和不使用pipeline情况下
     * 初始化一个1000的队列需要执行完成需要的事件
     * type04 72毫秒
     * type05 80秒
     *
     * */
    @RequestMapping("/type04")
    @ResponseBody
    public Result dazhuanpan() {
        Pipeline pipeline = redisUtils.getPipeline(5);
        long a = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++) {
            pipeline.lpush("list", String.valueOf(i));
        }
        pipeline.syncAndReturnAll();
        long b = System.currentTimeMillis();
        return Result.successData(a - b);

    }


    @RequestMapping("/type004")
    @ResponseBody
    public Result dazhuanpanSet() {
        Pipeline pipeline = redisUtils.getPipeline(5);
        long a = System.currentTimeMillis();
        for (int i = 1; i <= 10; i++) {
            pipeline.sadd("setlist2", String.valueOf(i));

        }
        pipeline.syncAndReturnAll();
        long b = System.currentTimeMillis();
        return Result.successData(a - b);

    }

    @RequestMapping("/type005")
    @ResponseBody
    public Result dazhuanpanSet2() {
        long a = System.currentTimeMillis();
        String s = redisUtils.spop("setlist2", 5);
        log.info(s);
        long b = System.currentTimeMillis();
        return Result.successData(a - b);

    }


    @RequestMapping("/type05")
    @ResponseBody
    public Result dazhuanpan1() {
        long a = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            redisUtils.lpush(5, "list1", String.valueOf(i));
        }
        long b = System.currentTimeMillis();
        return Result.successData(a - b);
    }

    @RequestMapping("/type06")
    @ResponseBody
    public boolean dazhuanpan2() {
        long l = System.currentTimeMillis() + 10000L;
        if (!redisLock.lock("random", String.valueOf(l))) {
            return false;
        }
        int a = randomNum();
        if (a < 100) {
            try {
                count--;
            } catch (Exception e) {
                redisLock.unlock("random", String.valueOf(l));
                return false;
            }

        }
        log.info("a:{},count{}", a, count);
        redisLock.unlock("random", String.valueOf(l));
        return true;
    }

    private int randomNum() {
        Random random = new Random();
        int a = random.nextInt(30);
        return a;
    }

    /*
     * redis位图签到
     * */
    @RequestMapping("/type07")
    @ResponseBody
    public Result sign() {
        for(int i=0;i<=10;i++){
            int a = randomNum();
            //访问一次，签到一次
            stringRedisTemplate.opsForValue().setBit( "openid", a, true);
        }
        return Result.success();
    }
    @RequestMapping("/type08")
    @ResponseBody
    public Result count() {
        for(int i=0;i<=10;i++){
            int a = randomNum();
            stringRedisTemplate.opsForValue().setBit( "openid", a, true);
        }
        return Result.success();
    }


}
