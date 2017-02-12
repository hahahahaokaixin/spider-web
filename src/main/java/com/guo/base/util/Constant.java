package com.guo.base.util;

/**
 * 从系统配置文件加载的系统全局变量配置
 */
public class Constant {
    /**
     * 节点配置
     */
    public static String Node;
    public static boolean proxy = false;

    /**
     * adsl拨号配置
     */
    public static boolean vps = false;
    public static String VpsExp = "0 0/6 * * * ?";
    public static String AdslTitle;
    public static String AdslName;
    public static String AdslPwd;

    /**
     * 数据库配置
     */
    public static String DbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String DbUrl = "jdbc:sqlserver://192.168.1.15:1433;DatabaseName=DC_NEWS";
    public static String DbUsername = "root";
    public static String DbPwd = "123456";

    /**
     * fdfs配置
     */
    public static int CONNECT_TIMEOUT = 2;
    public static int NETWORK_TIMEOUT = 30;
    public static String CHARSET = "UTF-8";
    public static int FAST_HTTP_PORT = 8080;
    public static boolean ANTI_STEAL_TOKEN = false;
    //FDFS默认key
    public static String KEY = "FastDFS1234567890";
    /**
     * 截图保存服务器IP
     */
    public static String[] TRACKER_SERVER = {"192.168.1.15:22122"};

    /**
     * 消息队列配置，这里使用rabbitmq
     */
    public static String MQ_HOST = "192.168.1.15";
    public static int MQ_PORT = 5672;
    public static String MQ_USERNAME = "mqadmin";
    public static String MQ_PWD = "123456";
    public static int MQ_THREAD = 5;
    public static int MQ_NUM = 200;

    //代理IP配置
    public static String[] IpProxy = {"118.118.118:20000"};
}
