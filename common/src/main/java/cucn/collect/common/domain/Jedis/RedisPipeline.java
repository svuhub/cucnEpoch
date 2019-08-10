package cucn.collect.common.domain.Jedis;

import cucn.collect.common.CommonParts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/pipeline")
@Slf4j
public class RedisPipeline {
    @Autowired
    private JedisPool jedisPool;

    /*
     * 批量存储测试数据  381
     * */
    @RequestMapping("/testSet")
    @ResponseBody
    public Result TestSource() {
        Jedis jedis = jedisPool.getResource();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (int j = i * 100; j < (i + 1) * 100; j++) {
                jedis.hset("hashkey:" + j, "field" + j, "value" + j);
            }
        }
        long end = System.currentTimeMillis();
        return Result.successData((end - begin) / 1000);
    }


    /*
     * 批量存储测试数据 4
     * */
    @RequestMapping("/test02")
    @ResponseBody
    public Result TestPipeline() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(9);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Pipeline pipeline = jedis.pipelined();
            for (int j = i * 100; j < (i + 1) * 100; j++) {
                pipeline.hset("hashkey:" + j, "field" + j, "value" + j);
            }
            pipeline.syncAndReturnAll();
        }
        long end = System.currentTimeMillis();
        return Result.successData((end - begin) / 1000);
    }

    @RequestMapping("/testGet")
    @ResponseBody
    public Result TestGet() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(9);
        long begin = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
       /* for (int i = 0; i < 10000; i++) {
                pipeline.hset("hashkeys:" , "field" + i, "value" + i);
        }*/
        Response<Set<String>> re = pipeline.hkeys("hashkeys:");
        pipeline.syncAndReturnAll();
        re.get().size();
        Set<String> set = re.get();
        long end = System.currentTimeMillis();
        return Result.successData(set);
    }




}
