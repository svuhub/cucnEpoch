package cucn.collect.common.CommonParts;

        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.ResponseBody;

        import java.util.HashMap;
        import java.util.Map;

/*
 *
 * 不加扫包范围默认扫全局
 * 加了用大括号包起来，逗号分割
 *
 * */
@ControllerAdvice(basePackages = {"cucn.collect.common.Controller","cucn.collect.common.domain"})
public class GloableException {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result HandleException() {
        //实际操作。记录日志，定时任务扫描
        Map map = new HashMap();
        map.put("errorCode", 500);
        map.put("errorMsg", "系统错误");
        return Result.successData(map);
    }

}
