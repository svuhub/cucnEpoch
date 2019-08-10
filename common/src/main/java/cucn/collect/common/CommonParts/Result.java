package cucn.collect.common.CommonParts;

/**
 * 返回值定义
 */
public class Result {

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    /**
     * 构造函数
     *
     * @param code
     * @param message
     */
    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param code
     * @param message
     * @param data
     */
    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    /**
     * 成功的返回码为0
     */
    private static int CODE_SUCCESS;


    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    /**
     * 成功
     *
     * @return 成功时返回码为0
     */
    public static Result success() {
        return new Result(CODE_SUCCESS, "");
    }

    /**
     * 成功
     *
     * @param message
     * @return 成功时返回码为0
     */
    public static Result success(String message) {
        return new Result(CODE_SUCCESS, message);
    }

    /**
     * 成功
     *
     * @param message
     * @param data
     * @return 成功时返回码为0
     */
    public static Result success(String message, Object data) {
        return new Result(CODE_SUCCESS, message, data);
    }

    /**
     * 成功
     *
     * @param data
     * @return 成功时返回码为0
     */
    public static Result successData(Object data) {
        return new Result(CODE_SUCCESS, "", data);
    }

    /**
     * 失败
     *
     * @param code 返回码，由于默认0为成功时的返回码，如此参数传入0将自动替换为1
     * @return
     */
    public static Result error(int code) {
        if (code == CODE_SUCCESS)
            code = 1;
        return new Result(code, "");
    }

    /**
     * 失败
     *
     * @param code    返回码，由于默认0为成功时的返回码，如此参数传入0将自动替换为1
     * @param message
     * @return
     */
    public static Result error(int code, String message) {
        if (code == CODE_SUCCESS)
            code = 1;
        return new Result(code, message);
    }

    /**
     * 失败
     *
     * @param code    返回码，由于默认0为成功时的返回码，如此参数传入0将自动替换为1
     * @param message
     * @param data
     * @return
     */
    public static Result error(int code, String message, Object data) {
        if (code == CODE_SUCCESS)
            code = 1;
        return new Result(code, message, data);
    }

    //失败时必须返回消息，此方法弃用
    /*public static Result errorData(int code, Object data)
    {
        if(code == CODE_SUCCESS)
            code = 1;
        return new Result(code, "", data);
    }*/

    public Result() {
    }
}
