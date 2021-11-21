package com.easy.im.easyim.idle.state.pinger;

import com.easy.im.easyim.idle.state.trigger.ClientIdleStateTrigger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author by yuanpeng.song
 * @ClassName Pinger 心跳发射器 ，客户端连接到服务器之后，会随机几秒去触发心跳，然后ping一下服务端，即发送一个心跳包
 * @Description 心跳发射器
 * @Date 2021/11/16 0016 23:04
 **/
@Slf4j
public class Pinger extends ChannelInboundHandlerAdapter {

    private Random random = new Random();
    private int baseRandom = 5;

    private Channel channel;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();
        ping(ctx.channel());
    }

    private void ping(Channel channel) {
        int seconds = Math.max(1, random.nextInt(baseRandom));
        log.info("next heat beat will send after {} s", seconds);

        ScheduledFuture<?> future = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                log.info("sending heat beat to server ...");
                channel.writeAndFlush(ClientIdleStateTrigger.HEAT_BEAT);
            } else {
                log.error("current connection is broken,cancel the task and send a heat beat");
                channel.closeFuture();
                throw new RuntimeException();
            }
        }, seconds, TimeUnit.SECONDS);

        future.addListener((GenericFutureListener) future1 -> {
            if (future1.isSuccess()){
                ping(channel);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        //当连接断开，仍发送心跳时，会抛出异常，就会调用当前方法
        cause.printStackTrace();
        ctx.close();

    }
}
