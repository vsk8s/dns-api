package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import ch.ethz.vis.dnsapi.netcenter.types.*;
import com.google.gson.internal.$Gson$Preconditions;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import okhttp3.Dns;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import javax.xml.bind.JAXBContext;

public class DnsImplTest {
    private MockWebServer mockWebServer;
    private DnsGrpc.DnsBlockingStub stub;

    @org.junit.Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @org.junit.Before
    public void resetMockServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.requireClientAuth();

        NetcenterAPI netcenterAPI = new NetcenterAPI(
                mockWebServer.url("/netcenter/rest/").toString(), "fake", "credentials");
        DnsImpl dnsImpl = new DnsImpl(netcenterAPI, "fake-isg");

        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).addService(dnsImpl).build().start());
        stub = DnsGrpc.newBlockingStub(InProcessChannelBuilder.forName(serverName).directExecutor().build());
    }

    @org.junit.Test
    public void testCreateARecord() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.CreateARecordRequest request = Dnsapi.CreateARecordRequest.newBuilder()
                .setIp("192.0.2.10")
                .setIpName("some-ip-name")
                .setSubdomain("some-subdomain")
                .build();

        Dnsapi.EmptyResponse response = stub.createARecord(request);

        try {
            RecordedRequest rr = mockWebServer.takeRequest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.Test
    public void testCreateARecordFail() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("<errors><error>Done!</error></errors>"));

        Dnsapi.CreateARecordRequest request = Dnsapi.CreateARecordRequest.newBuilder()
                .setIp("192.0.2.10")
                .setIpName("some-ip-name")
                .setSubdomain("some-subdomain")
                .build();

        Dnsapi.EmptyResponse response = stub.createARecord(request);

        try {
            RecordedRequest rr = mockWebServer.takeRequest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
