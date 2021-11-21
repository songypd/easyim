package com.easy.im.easyim.idle.state.initializer;

import com.easy.im.easyim.idle.state.handler.ServerBizHandler;
import com.easy.im.easyim.idle.state.trigger.ServerIdleStateTrigger;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author by yuanpeng.song
 * @ClassName ServerHandlerInitializer
 * @Description 服务端处理集合的初始化类；用于处理初始化服务器涉及到的所有 handler
 * @Date 2021/11/18 0018 22:53
 **/
public class ServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //表示该handler如果在5秒内没有收到来自客户端的任何数据包（包括但不仅限于心跳包），将会断开与该客户端的连接
        ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(5,0,0));
        ch.pipeline().addLast("idleStateTrigger",new ServerIdleStateTrigger());
        ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(4));
        ch.pipeline().addLast("decoder",new StringDecoder());
        ch.pipeline().addLast("encoder",new StringEncoder());
        ch.pipeline().addLast("bizHandler",new ServerBizHandler());
    }
}
