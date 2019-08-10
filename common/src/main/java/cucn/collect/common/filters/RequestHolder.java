package cucn.collect.common.filters;

public class RequestHolder {
    private static final ThreadLocal<String> deviceNumHolder = new ThreadLocal<String>();

    public static void addDeviceNum(String deviceNum) {
        deviceNumHolder.set(deviceNum);
    }

    public static String getCurrentDeviceNum() {
        return deviceNumHolder.get();
    }

    private static final ThreadLocal<String> deviceNameHolder = new ThreadLocal<String>();

    public static void addDeviceName(String deviceName) {
        deviceNameHolder.set(deviceName);
    }

    public static String getCurrentDeviceName() {
        return deviceNameHolder.get();
    }


    public static void remove() {
        deviceNumHolder.remove();
        deviceNameHolder.remove();

    }


}
