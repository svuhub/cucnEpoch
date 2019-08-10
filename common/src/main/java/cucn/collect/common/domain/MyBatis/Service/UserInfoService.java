package cucn.collect.common.domain.MyBatis.Service;


import cucn.collect.common.CommonParts.utils.HttpClientUtilLongTime;
import cucn.collect.common.domain.MyBatis.DTO.UserExtroInfo;
import cucn.collect.common.domain.MyBatis.mapper03.UserExtroInfoMapper;
import cucn.collect.common.CommonParts.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {
    @Autowired
    private UserExtroInfoMapper userExtroInfoMapper;
    /**
     * 查询用户信息Url
     */
    @Value("${api.UserinfoUrl}")
    private String userinfoUrl;


    public JSONObject getDataObject(String url, Map params) {
        try {
            String result = HttpClientUtilLongTime.doPostJson(url, JsonUtils.objectToJson(params));
            JSONObject json = null;
            if (StringUtils.isNotBlank(result)) {
                json = new JSONObject(result);
            } else {
                throw new RuntimeException("接口返回异常");
            }
            int resCode = json.getInt("code");
            if (resCode != 0) {
                return null;
            }
            JSONObject resDataObj = json.optJSONObject("data");
            return resDataObj;
        } catch (Exception e) {
            return null;
        }
    }


    public Map<String, String> UserInfoByIdentity(String identity, String areaCode) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("accNbr", identity);
        parmMap.put("areaCode", areaCode);
        parmMap.put("accNbrType", "9");

        JSONObject resDataObj = getDataObject(userinfoUrl, parmMap);
        if (resDataObj == null) {
            return null;
        }
        String name = resDataObj.getString("name");
        String indentNbr = resDataObj.getString("indentNbr");
        String partyId = resDataObj.getString("partyId");
        map.put("name", name);
        map.put("pin", indentNbr);
        map.put("partyId", partyId);
        return map;
    }

    public List<UserExtroInfo> getAll() {
        return userExtroInfoMapper.getAll();
    }

    public List<UserExtroInfo> getAllUser() {
        return userExtroInfoMapper.getAllUser();
    }


    public int insertInfo(String name, String indentNbr, String partyId, String mobile) {
        return userExtroInfoMapper.insert(name, indentNbr, partyId, mobile);
    }

}
