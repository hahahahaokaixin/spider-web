package com.guo.base.timer;

import org.springframework.stereotype.Service;

import com.guo.base.task.CustmTask;

/**
 * 清除缓存
 */
@Service
public class CleanTask extends CustmTask {

    @Override
    public void doStart() {
        TaskTimer.addJob(this, "CleanTask", "0 0/30 * * * ?");

    }

    @Override
    public void doWork() {
        Runtime runtime = Runtime.getRuntime();

        try {

            Process process = runtime.exec("cmd.exe /c start del /f /q /s %temp%\\*");
            int val = process.waitFor();
            Runtime.getRuntime().exec("cmd.exe /c start wmic process where name='cmd.exe' call terminate");
            Runtime.getRuntime().exec("cmd.exe /c start taskkill /f /im firefox.exe");
            System.out.println("执行清除完毕>>>" + val + "<<<ms");
        } catch (Exception e) {
            System.out.println("执行cmd清除命令异常");
        }

    }

}
