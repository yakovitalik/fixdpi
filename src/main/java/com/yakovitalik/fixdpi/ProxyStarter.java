package com.yakovitalik.fixdpi;

public class ProxyStarter {
    public static void startProxy() {
        Thread newThread = new Thread(() -> {
            var proxyServer = new ProxyServer();
            proxyServer.startProxy();
        });

        newThread.start();
    }
}
