package com.easy.im.easyim.idle.state.initializer;

import com.easy.im.easyim.idle.client.TcpClient;
import com.easy.im.easyim.idle.state.pinger.Pinger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author by yuanpeng.song
 * @ClassName ClientHandlerInitalizer
 * @Description 客户端处理集合的初始化类
 * @Date 2021/11/17 0017 22:26
 **/
@Slf4j
public class ClientHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private EchoHandler echoHandler;

    public ClientHandlerInitializer(TcpClient tcpClient) {
        Assert.notNull(tcpClient, "tcpClient can not be null");
        this.echoHandler = new EchoHandler();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipe = ch.pipeline();
        pipe.addLast(new Pinger());
        pipe.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipe.addLast(new LengthFieldPrepender(4));
        pipe.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipe.addLast(new StringEncoder(CharsetUtil.UTF_8));
    }

    public class EchoHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.info("receive data from server {}",msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
