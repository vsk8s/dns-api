package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import okhttp3.mockwebserver.MockWebServer;

public class DnsImplBase {
    protected final static String DEFAULT_PATH = "/netcenter/rest/";
    protected final static String DEFAULT_ISG = "default-isg";

    protected MockWebServer mockWebServer;
    protected DnsGrpc.DnsBlockingStub stub;

    @org.junit.Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @org.junit.Before
    public void resetMockServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.requireClientAuth();

        NetcenterAPI netcenterAPI = new NetcenterAPI(
                mockWebServer.url(DEFAULT_PATH).toString(), "fake", "credentials");
        DnsImpl dnsImpl = new DnsImpl(netcenterAPI, DEFAULT_ISG);

        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).addService(dnsImpl).build().start());
        stub = DnsGrpc.newBlockingStub(InProcessChannelBuilder.forName(serverName).directExecutor().build());
    }
}
