package com.yakovitalik.fixdpi;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        var proxyServer = new ProxyServer();
        proxyServer.startProxy();
    }
}