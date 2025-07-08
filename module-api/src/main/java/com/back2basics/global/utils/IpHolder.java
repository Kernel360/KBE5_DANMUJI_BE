package com.back2basics.global.utils;

public class IpHolder {

    private static final ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

    public static void setIp(String ip) {
        ipThreadLocal.set(ip);
    }

    public static String getIp() {
        return ipThreadLocal.get();
    }

    public static void clear() {
        ipThreadLocal.remove();
    }
}