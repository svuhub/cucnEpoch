package cucn.collect.common.domain.SignIn;

import cucn.collect.common.domain.Jedis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
/*
 * 1.签到
 * 2.用户当月签到状态
 * 3.第一天5 第二天10 第三天15 第四天20 第五天20
 * 4.用户昨日是否签到
 * */
@SuppressWarnings("all")
public class SignService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisUtils redisUtils;


    //实际签到操作
    public boolean doSign(String openid, LocalDate date) {
        long offset = date.getDayOfMonth() - 1;
        return stringRedisTemplate.opsForValue().setBit(buildSignKey(openid, date), offset, true);
    }

    //用户某天是否签到
    public boolean isSign(String openid, LocalDate date) {
        long offset = date.getDayOfMonth() - 1;
        return stringRedisTemplate.opsForValue().getBit(buildSignKey(openid, date), offset);
    }

    //用户签到次数
    public Long countsActive(String openid, LocalDate date) {
        String logKey = buildSignKey(openid, date);
        return bitCount(logKey);
    }

    public Long bitCount(final String key) {
        return (Long) stringRedisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(key.getBytes());
            }
        });
    }

    //获取用这个月的签到情况
    public List<Long> getMonthDaysSign(String openid, LocalDate date) {
        String type = String.format("u%d", date.lengthOfMonth());
        List<Long> bitField = redisUtils.getBitField(buildSignKey(openid, date), "GET", type, "0");
        return bitField;
    }

    //获取当前月开始至今的签到情况
    public List<Long> getDaysSign(String openid, LocalDate date) {
        String type = String.format("u%d", formatDateDay());
        List<Long> bitField = redisUtils.getBitField(buildSignKey(openid, date), "GET", type, "0");
        return bitField;
    }


    //构建用户签到的key
    private static String buildSignKey(String openid, LocalDate date) {
        return String.format("SIGNDATA:%s:%s", openid, formatDateYearMonth(date));
    }

    //格式化年月
    private static String formatDateYearMonth(LocalDate date) {
        return formatDateYearMonth(date, "yyyyMM");
    }

    //格式化日
    private static int formatDateDay() {
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String today = format.format(nowDate);
        return Integer.parseInt(today);
    }

    //格式化日期工具
    private static String formatDateYearMonth(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

}
