package com.yakovitalik.fixdpi;

import static com.yakovitalik.fixdpi.Constants.PORT;
import static com.yakovitalik.fixdpi.Constants.HOST;
import static com.yakovitalik.fixdpi.Constants.SITE_LIST;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProxyServer {

    private final List<byte[]> blockedList;
    private final ExecutorService executor;

    public ProxyServer() {
        blockedList = SITE_LIST.stream()
                .map(String::trim)
                .map(s -> s.getBytes(StandardCharsets.UTF_8))
                .collect(Collectors.toList());

        executor = Executors.newCachedThreadPool();
    }

    public void startProxy() {
        try {
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
            server.bind(new InetSocketAddress(HOST, PORT));

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel clientChannel, Void att) {
                    server.accept(null, this); // Accept next connection
                    handleNewConnection(clientChannel);
                }

                @Override
                public void failed(Throwable exc, Void att) {
                    System.out.println(exc.getMessage());
                }
            });

            // Keep main thread alive
            try {
                Thread.currentThread().join();
            } catch (InterruptedException ignored) {}
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleNewConnection(AsynchronousSocketChannel clientChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1500);
        clientChannel.read(buffer, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void att) {
                buffer.flip();
                byte[] requestData = new byte[buffer.limit()];
                buffer.get(requestData);

                try {
                    String[] lines = new String(requestData, StandardCharsets.UTF_8).split("\r\n");
                    String[] parts = lines[0].split(" ");
                    if (parts.length < 2 || !"CONNECT".equals(parts[0])) {
                        clientChannel.close();
                        return;
                    }

                    String[] hostPort = parts[1].split(":");
                    String host = hostPort[0];
                    int port = Integer.parseInt(hostPort[1]);

                    ByteBuffer response = ByteBuffer.wrap("HTTP/1.1 200 OK\r\n\r\n".getBytes(StandardCharsets.UTF_8));
                    clientChannel.write(response, null, new CompletionHandler<Integer, Void>() {
                        @Override
                        public void completed(Integer result, Void attachment) {
                            connectToRemote(clientChannel, host, port);
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) {
                            closeChannel(clientChannel);
                        }
                    });

                } catch (Exception e) {
                    closeChannel(clientChannel);
                }
            }

            @Override
            public void failed(Throwable exc, Void att) {
                closeChannel(clientChannel);
            }
        });
    }

    private void connectToRemote(AsynchronousSocketChannel clientChannel, String host, int port) {
        try {
            AsynchronousSocketChannel remoteChannel = AsynchronousSocketChannel.open();
            remoteChannel.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Void>() {
                @Override
                public void completed(Void result, Void attachment) {
                    if (port == 443) {
                        fragmentAndPipe(clientChannel, remoteChannel);
                    } else {
                        pipeBothWays(clientChannel, remoteChannel);
                    }
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    closeChannel(clientChannel);
                }
            });
        } catch (IOException e) {
            closeChannel(clientChannel);
        }
    }

    private void fragmentAndPipe(AsynchronousSocketChannel clientChannel, AsynchronousSocketChannel remoteChannel) {
        ByteBuffer head = ByteBuffer.allocate(5);
        ByteBuffer data = ByteBuffer.allocate(1500);

        clientChannel.read(head, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void att) {
                head.flip();
                clientChannel.read(data, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer result, Void att) {
                        data.flip();
                        byte[] dataBytes = new byte[data.limit()];
                        data.get(dataBytes);

                        var isBlocked = blockedList.stream()
                                .anyMatch(bytes -> indexOf(dataBytes, bytes) >= 0);

                        try {
                            ByteBuffer sendBuffer;
                            if (!isBlocked) {
                                ByteBuffer total = ByteBuffer.allocate(head.limit() + dataBytes.length);
                                total.put(head);
                                total.put(dataBytes);
                                total.flip();
                                sendBuffer = total;
                            } else {
                                List<byte[]> parts = new ArrayList<>();
                                byte[] remaining = dataBytes;

                                Random random = new Random();
                                while (remaining.length > 0) {
                                    int len = random.nextInt(remaining.length) + 1;
                                    byte[] fragment = Arrays.copyOfRange(remaining, 0, len);
                                    byte type = (byte) random.nextInt(256);
                                    byte[] part = new byte[5 + fragment.length];
                                    part[0] = 0x16;
                                    part[1] = 0x03;
                                    part[2] = type;
                                    part[3] = (byte) ((fragment.length >> 8) & 0xff);
                                    part[4] = (byte) (fragment.length & 0xff);
                                    System.arraycopy(fragment, 0, part, 5, fragment.length);
                                    parts.add(part);
                                    remaining = Arrays.copyOfRange(remaining, len, remaining.length);
                                }

                                int totalLength = parts.stream().mapToInt(p -> p.length).sum();
                                sendBuffer = ByteBuffer.allocate(totalLength);
                                for (byte[] part : parts) {
                                    sendBuffer.put(part);
                                }
                                sendBuffer.flip();
                            }

                            remoteChannel.write(sendBuffer, null, new CompletionHandler<Integer, Void>() {
                                @Override
                                public void completed(Integer result, Void attachment) {
                                    pipeBothWays(clientChannel, remoteChannel);
                                }

                                @Override
                                public void failed(Throwable exc, Void attachment) {
                                    closeChannel(clientChannel);
                                    closeChannel(remoteChannel);
                                }
                            });
                        } catch (Exception e) {
                            closeChannel(clientChannel);
                            closeChannel(remoteChannel);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Void att) {
                        closeChannel(clientChannel);
                        closeChannel(remoteChannel);
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void att) {
                closeChannel(clientChannel);
                closeChannel(remoteChannel);
            }
        });
    }

    private void pipeBothWays(AsynchronousSocketChannel client, AsynchronousSocketChannel remote) {
        executor.submit(() -> forwardData(client, remote));
        executor.submit(() -> forwardData(remote, client));
    }

    private void forwardData(AsynchronousSocketChannel from, AsynchronousSocketChannel to) {
        ByteBuffer buffer = ByteBuffer.allocate(1500);
        try {
            while (from.read(buffer).get() != -1) {
                buffer.flip();
                to.write(buffer).get();
                buffer.clear();
            }
        } catch (Exception e) {
            // Ignore
        } finally {
            closeChannel(from);
            closeChannel(to);
        }
    }

    private void closeChannel(AsynchronousSocketChannel ch) {
        try {
            if (ch != null && ch.isOpen()) ch.close();
        } catch (IOException ignored) {}
    }

    private int indexOf(byte[] data, byte[] pattern) {
        outer:
        for (int i = 0; i <= data.length - pattern.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }
}
