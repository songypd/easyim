package com.easy.im.easyim.idle.state.trigger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author by yuanpeng.song
 * @ClassName ServerIdleStateTrigger
 * @Description 断链触发器-》在规定时间内，没有收到客户端的任何数据包，将主动该断开该连接
 * @Date 2021/11/18 0018 22:27
 **/
public class ServerIdleStateTrigger extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent)evt).state();
            if (state.equals(IdleState.READER_IDLE)){
//                在规定时间内没有收到客户端的上行数据，主动断开连接
                ctx.disconnect();
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
