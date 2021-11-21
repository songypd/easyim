package com.easy.im.easyim.idle.server;

import com.easy.im.easyim.idle.state.initializer.ServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author by yuanpeng.song
 * @ClassName TcpServer
 * @Description 服务器端
 * @Date 2021/11/18 0018 23:01
 **/
public class TcpServer {
    private int port;
    private ServerHandlerInitializer serverHandlerInitializer;

    public TcpServer(int port) {
        this.port = port;
        this.serverHandlerInitializer = new ServerHandlerInitializer();
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).childHandler(serverHandlerInitializer)
                    .channel(NioServerSocketChannel.class);
//            绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("server start listen at " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 2222;
        new TcpServer(port).start();
    }
}
