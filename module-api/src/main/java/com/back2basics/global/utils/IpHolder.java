package com.back2basics.global.utils;

public class IpHolder {

    private static final ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

    public static void set(String ip) {
        ipThreadLocal.set(ip);
    }

    public static String get() {
        return ipThreadLocal.get();
    }

    public static void clear() {
        ipThreadLocal.remove();
    }
}