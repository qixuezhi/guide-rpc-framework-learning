package github.javaguide.netty.kyro.server;

import github.javaguide.netty.kyro.client.KryoClientHandler;
import github.javaguide.netty.kyro.dto.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 20:44:00
 */
public class KryoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(KryoServerHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            RpcResponse messageFromServer = RpcResponse.builder().message("message from server").build();
            System.out.println("Server write msg: " + messageFromServer);
            ChannelFuture f = ctx.writeAndFlush(messageFromServer);
            f.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}