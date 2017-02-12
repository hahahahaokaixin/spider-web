package com.guo.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.guo.base.util.Constant;
import com.guo.base.xml.IMyReadConfig;
import com.guo.base.xml.ReadXmlApplication;

/**
 * Hello world!
 */
public class App {

    private static App app = null;

    private App() {
        System.out.println("构造函数被调用");
    }

    public static App getInstance() {
        if (app == null) {
            synchronized (App.class) {
                app = new App();
            }
        }
        return app;
    }

    public static void main(String[] args) {

        String curPath = Class.class.getClass().getResource("/").getPath();
        String spring_file = curPath + "applicationContext.xml";

        Properties pros = new Properties();
        try {
            pros.load(new FileInputStream(curPath + "config.properties"));
            Constant.Node = pros.getProperty("node");
        } catch (IOException ex) {

        }
        List<String> spring_config_list = new ArrayList<String>();
        spring_config_list.add(spring_file);

        String[] list = new String[spring_config_list.size()];
        for (int i = 0; i < spring_config_list.size(); i++) {
            list[i] = spring_config_list.get(i);

        }

        FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(list);

    }

    public static FileSystemXmlApplicationContext start(IMyReadConfig my, String... configs) {

        String curPath = getCurrentPath();
        String config_path = System.getProperty("config_path");

        //	System.out.println(classPath);
        if (config_path != null && !config_path.equals("")) {
            curPath = curPath + File.separator + config_path + File.separator + "conf" + File.separator;
        } else {
            curPath = curPath + File.separator + "conf" + File.separator;
        }
        System.out.println("###############当前路径:" + curPath + "#################");
        List<String> springConfig = new ArrayList<String>();
        List<String> propsConfig = new ArrayList<String>();
        String config_file = curPath;
        File files = new File(config_file);

        for (String config : files.list()) {
            if (config.indexOf(".xml") >= 0) {
                config = config_file + config;
                springConfig.add(config);
            } else {
                propsConfig.add(config);
            }
        }
        ReadXmlApplication app = new ReadXmlApplication(springConfig, propsConfig);
        app.setMyConfig(my);
        List<String> myConfig = app.initMyConfig();

        List<String> spring_config_list = new ArrayList<String>();

        if (myConfig != null && !myConfig.isEmpty()) {
            spring_config_list.addAll(myConfig);
        }
        String[] list = new String[spring_config_list.size()];
        for (int i = 0; i < spring_config_list.size(); i++) {
            list[i] = spring_config_list.get(i);

        }

        FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(list);
        System.out.println("￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥启动成功￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
        return fileSystemXmlApplicationContext;
    }

    public static String getCurrentPath() {

        String current_path = System.getProperty("user.dir");
        System.out.println(current_path);
        if (StringUtils.isEmpty(current_path)) {
            File directory = new File("");// 设定为当前文件夹
            try {
                current_path = directory.getCanonicalPath();// 获取标准的路径
            } catch (Exception e) {
            }
            if (current_path.endsWith("bin")) {
                current_path = current_path.substring(0, current_path.length() - 4);
            }
        } else {
            if (current_path.endsWith("bin")) {
                current_path = current_path.substring(0, current_path.length() - 4);
            }
        }

        System.out.println("current path is:" + current_path);

        return current_path;
    }
}
