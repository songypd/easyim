package com.easy.im.easyim.idle.state.trigger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author by yuanpeng.song
 * @ClassName ClientIdleStateTrigger
 * @Description 客户端心跳  心跳触发器
 * 用于捕获{@link IdleState#WRITER_IDLE}事件（未在指定时间内向服务器发送数据），
 * 然后向<code>Server</code>端发送一个心跳包。
 * @Date 2021/11/16 0016 22:54
 **/
public class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {

    public static final String HEAT_BEAT = "heat beat";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(HEAT_BEAT);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
