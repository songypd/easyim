package com.easy.im.easyim.idle.state.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author by yuanpeng.song
 * @ClassName ServerBizHandler
 * @Description 服务端的业务处理器
 * 收到来自客户端的数据包后，直接在控制台上打印出来
 * @Date 2021/11/18 0018 22:32
 **/
@ChannelHandler.Sharable
public class ServerBizHandler extends SimpleChannelInboundHandler<String> {

    private final String REC_HEART_BEAT = "i had received the heart beat";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        try {
            System.out.println("receive data: " + msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Established connection with the remote client.");

        //do something
        ctx.fireChannelActive();
//        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("disconnected with zhe remote client.");
        ctx.fireChannelInactive();
//        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
