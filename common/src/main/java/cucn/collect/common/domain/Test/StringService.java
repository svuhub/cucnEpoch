package cucn.collect.common.domain.Test;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class StringService {
    /*
     * 互斥/接口异常反馈
     * */
    public Boolean checkResult(String context) {
        String newContext = null;
        if (StringUtils.isNotBlank(context)) {
            if (context.length() > 1000) {
                int index = context.length() / 2;
                newContext = context.substring(1, index);
            } else {
                newContext = context;
            }
            if (newContext.contains("互斥") || newContext.contains("同款") || newContext.contains("超过")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
