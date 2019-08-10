package cucn.collect.common.CommonParts.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucn.collect.common.CommonParts.Result;


import java.util.List;

/**
 * Created by Admin on 2018/6/25.
 */
public class Convert {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz    TaotaoResult中的object类型
     * @return
     */
    public static Result toResult(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, Result.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isObject()) {
                obj = MAPPER.readValue(data.traverse(), clazz);
            } else if (data.isTextual()) {
                obj = MAPPER.readValue(data.asText(), clazz);
            }
            if (jsonNode.get("code").intValue() == 0) {
                return Result.success(jsonNode.get("message").asText(), obj);
            } else {
                return Result.error(jsonNode.get("code").intValue(), jsonNode.get("message").asText(), obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static Result toResultDataList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }

            if (jsonNode.get("code").intValue() == 0) {
                return Result.success(jsonNode.get("message").asText(), obj);
            } else {
                return Result.error(jsonNode.get("code").intValue(), jsonNode.get("message").asText(), obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
