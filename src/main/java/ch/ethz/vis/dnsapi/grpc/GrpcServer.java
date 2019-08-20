package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GrpcServer {

    private static final Logger LOG = LogManager.getLogger(GrpcServer.class);

    private NetcenterAPI netcenterAPI;

    private Server server;

    private int port;

    private String defaultIsg;

    public GrpcServer(NetcenterAPI netcenterAPI, String defaultIsg) {
        this.netcenterAPI = netcenterAPI;
        this.port = 50051;
        this.defaultIsg = defaultIsg;
    }

    public void serve() throws IOException {
        server = instantiateServer();
        server.start();
        joinServer();
    }

    private Server instantiateServer() {
        return NettyServerBuilder.forPort(port)
                .addService(new DnsImpl(netcenterAPI, defaultIsg))
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    private void joinServer() {
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            LOG.info("interrupted", e);
        }
    }
}
