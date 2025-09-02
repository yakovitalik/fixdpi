package com.yakovitalik.fixdpi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyStarter implements Runnable {
    public static void startProxy() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ProxyStarter());
        executorService.shutdown();
    }

    @Override
    public void run() {
        var proxyServer = new ProxyServer();
        proxyServer.startProxy();
    }
}
