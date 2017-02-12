package com.guo.base.util;


import java.util.Random;

public abstract class ProxyPool {
    private static int ips = Constant.IpProxy.length;
    private static Random random = new Random();

    public static ProxyIp getRandomProxy() {
        String ip = Constant.IpProxy[random.nextInt(ips)];
        ProxyIp ipp = new ProxyIp(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
        return ipp;
    }

}
