package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;

public class GrpcServer {
    private static final Logger LOGGER = LogManager.getLogger(GrpcServer.class);

    private Server server;
    private NetcenterAPI netcenterAPI;
    private int port;
    private String defaultIsg;

    public GrpcServer(NetcenterAPI netcenterAPI, String defaultIsg) {
        this.netcenterAPI = netcenterAPI;
        this.port = 50051;
        this.defaultIsg = defaultIsg;
    }

    public void serve() throws IOException {
        server = NettyServerBuilder.forPort(port)
                .addService(new DnsImpl(netcenterAPI, defaultIsg))
                .addService(ProtoReflectionService.newInstance())
                .build();

        try {
            server.start();
        } catch (IOException e) {
            LOGGER.error("error encountered starting server: " + e);
            throw new RuntimeException("could not start server: " + e);
        }

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            LOGGER.info("interrupted: ", e);
        }
    }
}
