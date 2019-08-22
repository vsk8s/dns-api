package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class GrpcServer {

    private static final Logger LOG = LogManager.getLogger(GrpcServer.class);

    private NetcenterAPI netcenterAPI;

    private Server server;

    private int port;

    private String defaultIsg;

    private String certFilePath;

    private String keyFilePath;

    public GrpcServer(NetcenterAPI netcenterAPI,
                      String defaultIsg,
                      String certFilePath,
                      String keyFilePath) {
        this.netcenterAPI = netcenterAPI;
        this.certFilePath = certFilePath;
        this.keyFilePath = keyFilePath;
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
                .useTransportSecurity(new File(certFilePath), new File(keyFilePath))
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
