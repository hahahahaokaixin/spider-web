package com.guo.base.timer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.guo.base.task.CustmTask;
import com.guo.base.util.AdslUtil;
import com.guo.base.util.Constant;

/**
 * 拨号任务
 */
@Service
public class AdslTask extends CustmTask {
    private static Logger logger = Logger.getLogger(AdslTask.class);

    @Override
    public void doStart() {
        /**
         * 如果是vps的话就动态的换ip
         */
        if (Constant.vps) {
            try {
                logger.info("#########定时器断网启动:" + Constant.VpsExp + "################");
                TaskTimer.addJob(this, "AdslTask", Constant.VpsExp);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void doWork() {

        try {

            DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            AdslUtil.cutAdsl(Constant.AdslTitle);
            // RabbitMQFactroy.getChannel().abort();

            Thread.sleep(3000);
            for (int i = 0; i < 3; i++) {
                logger.info("title：" + Constant.AdslTitle + "用户名" + Constant.AdslName + "" + Constant.AdslPwd);
                Boolean ret = AdslUtil.connAdsl(Constant.AdslTitle, Constant.AdslName, Constant.AdslPwd);
                logger.info(
                        "执行切换IP完毕>>>>>>>>>>>>>>>" + Constant.AdslName + FORMAT.format(new Date()) + "<<<<<<<<<<<<<<<");
                if (ret == true) {

                    break;
                } else {
                    Thread.sleep(2000);
                    logger.info("!!!!!!!!!!继续链接Adsl，第" + i + "!!!!!!!!!!");
                }
            }

        } catch (Exception e) {
            System.out.println("执行切换IP命令异常");
        }

    }

    public static InetAddress getInetAddress() {

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("unknown host!");
        }
        return null;

    }

    public static String getHostIp(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String ip = netAddress.getHostAddress(); // get the ip address
        return ip;
    }

    public static String getHostName(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String name = netAddress.getHostName(); // get the host address
        return name;
    }

    public String getLocalHostName() {
        String hostName;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (Exception ex) {
            hostName = "";
        }
        return hostName;
    }

    public String[] getAllLocalHostIP() {
        String[] ret = null;
        try {
            String hostName = getLocalHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    ret = new String[addrs.length];
                    for (int i = 0; i < addrs.length; i++) {
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        } catch (Exception ex) {
            ret = null;
        }
        return ret;
    }

}
