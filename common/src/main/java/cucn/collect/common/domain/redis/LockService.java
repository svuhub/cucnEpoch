package cucn.collect.common.domain.redis;

import cucn.collect.common.domain.Jedis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.UUID;

@Service
public class LockService {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JedisPool jedisPool;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    private static int ticketCount = 450;
    static String lockKey = getRequestId();

    public static String getRequestId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }


    public void distributeLock() throws InterruptedException {
        Jedis jedis = null;
        String requestId = null;
        int timeoutCount = 0;
        requestId = getRequestId();
        while (true) {
            try {
                jedis = jedisPool.getResource();
                break;
            } catch (Exception e) {
                if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException) {
                    //记录下获取多少次才获得jedis连接，并发很多的时候可能池里的连接不够而导致获取连接失败，所以这里循环获取
                    timeoutCount++;
                    //System.out.println("user:"+requestId+" getJedis timeoutCount={"+timeoutCount+"}");
                }
            }
        }

        if (tryGetDistributedLock(jedis, lockKey, requestId, 3000)) {
            if (ticketCount > 0) {
                System.out.println(requestId + " have got a ticket：" + ticketCount);
                ticketCount--;
            } else {
                System.out.println(requestId + "the ticket have been sold out.");
            }
            releaseDistributedLock(jedis, lockKey, requestId);
            jedis.close();
        } else {
            System.out.println("user" + requestId + " have been sold out!Give up getting lock");
        }

    }

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) throws InterruptedException {

        int timeoutCount = 0;
        long currentTime = System.currentTimeMillis();
        while (true) {

            try {
                String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
                if (ticketCount <= 0) {
                    System.out.println("user" + requestId + " have been sold out!Give up getting lock");
                    return false;
                }
                /*//等待了60S以上，直接不再获取锁
                if(System.currentTimeMillis() > (currentTime+ 60*1000)){
                    System.out.println("user"+ requestId +"等待了60S以上了，放弃！！！");
                    return false;
                }*/
            } catch (Exception e) {
                /*if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException) {
                    timeoutCount++;
                    //System.out.println("user:"+requestId+" jedis.set() timeoutCount={"+timeoutCount+"}");
                    if (timeoutCount > 3)
                    {
                        System.out.println("connect error！");
                        break;
                    }
                }*/
            }
        }
    }

    /**
     * 释放分布式锁
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        //判断requestId相等时为了确定解锁和获取锁的用户是同一个用户
        //lockKey是针对此业务的锁ID 执行成功返回1.
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }


}
