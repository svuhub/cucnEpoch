package cucn.collect.common.domain.Json;

public class User {
    private String phone;
    private String type;
    private String areaCode;
    private String partyid;
    private String channel;

    public User() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPartyid() {
        return partyid;
    }

    public void setPartyid(String partyid) {
        this.partyid = partyid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public User(String phone, String type, String areaCode, String partyid, String channel) {
        this.phone = phone;
        this.type = type;
        this.areaCode = areaCode;
        this.partyid = partyid;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", partyid='" + partyid + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
