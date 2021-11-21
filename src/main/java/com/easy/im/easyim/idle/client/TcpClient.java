package com.easy.im.easyim.idle.client;

import com.easy.im.easyim.idle.facade.RetryPolicy;
import com.easy.im.easyim.idle.facade.impl.ExponentialBackOffRetry;
import com.easy.im.easyim.idle.state.initializer.ClientHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

/**
 * @author by yuanpeng.song
 * @ClassName TcpClient
 * @Description TODO
 * @Date 2021/11/17 0017 22:39
 **/
public class TcpClient {
    private String host;
    private int port;
    private Bootstrap bootstrap;
    /**
     *将channel保存起来，可用于再其他非handler的地方发送数据
     */
    private Channel channel;

    public TcpClient(String host, int port) {
        this(host,port,new ExponentialBackOffRetry(1000,Integer.MAX_VALUE,60*1000));
    }

    public TcpClient(String host, int port, RetryPolicy retryPolicy){
        this.host = host;
        this.port = port;
        init();

    }

    public void connect(){
        synchronized (bootstrap){
            SocketAddress remoteAddress;
            ChannelFuture future = bootstrap.connect(host,port);
        }
    }

    private void init() {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ClientHandlerInitializer(TcpClient.this));
    }

    public static void main(String[] args) {
        TcpClient tcpClient = new TcpClient("localhost",2222);
        tcpClient.connect();
    }
}
