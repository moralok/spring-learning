package com.moralok.netty.server.handler;

import com.moralok.netty.message.RpcRequestMessage;
import com.moralok.netty.message.RpcResponseMessage;
import com.moralok.netty.server.service.ServiceFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) throws Exception {
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setSequenceId(msg.getSequenceId());
        try {
            Object service = ServiceFactory.getService(Class.forName(msg.getInterfaceName()));
            Method method = service.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
            Object invoke = method.invoke(service, msg.getParameterValues());
            log.debug("invoke: {}", invoke);

            rpcResponseMessage.setReturnValue(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            rpcResponseMessage.setExceptionValue(new RuntimeException(e.getCause().getMessage()));
        }
        ctx.writeAndFlush(rpcResponseMessage);
    }
}
