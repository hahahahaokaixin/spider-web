package com.guo.base.util;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    private static Map<Class<?>, ThreadPoolExecutor> MAP = new Hashtable<Class<?>, ThreadPoolExecutor>();

    public static ExecutorService getThreadPool(Object key, int thread) {
        Class<?> k = key.getClass();
        ThreadPoolExecutor pool = null;
        pool = MAP.get(k);
        if (pool == null) {
            pool = new ThreadPoolExecutor(thread, thread, 0L,
                    TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(1),
                    new ThreadPoolExecutor.DiscardPolicy());
            MAP.put(k, pool);
        }
        return pool;
    }

}
