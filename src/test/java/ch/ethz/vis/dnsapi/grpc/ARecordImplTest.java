package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.types.CreateARecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.XmlCreateARecordRequestWrapper;
import io.grpc.StatusRuntimeException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import javax.xml.bind.JAXB;

public class ARecordImplTest extends DnsImplBase {
    private static final String IP = "192.0.2.10";
    private static final String IP_SUBNET = "192.0.2.0";
    private static final String IP_NAME = "some-ip-name";

    @org.junit.Test
    public void successfullyCreateARecordWithDefaultIsg() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.CreateARecordRequest request = defaultCreateARecordRequest().build();

        Dnsapi.EmptyResponse response = stub.createARecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        XmlCreateARecordRequestWrapper generatedWrappedRequest = JAXB.unmarshal(rr.getBody().inputStream(), XmlCreateARecordRequestWrapper.class);
        org.junit.Assert.assertNotNull(generatedWrappedRequest);

        CreateARecordRequest generatedRequest = generatedWrappedRequest.getRequest();
        org.junit.Assert.assertNotNull(generatedRequest);

        assertRequiredFieldsSet(generatedRequest);
        org.junit.Assert.assertEquals(DEFAULT_ISG, generatedRequest.getIsgGroup());
    }

    @org.junit.Test
    public void successfullyCreateARecordWithCustomIsg() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.CreateARecordRequest request = defaultCreateARecordRequest()
                .setOptions(Dnsapi.RecordOptions.newBuilder().setIsgGroup(CUSTOM_ISG).build())
                .build();

        Dnsapi.EmptyResponse response = stub.createARecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        XmlCreateARecordRequestWrapper generatedWrappedRequest = JAXB.unmarshal(rr.getBody().inputStream(), XmlCreateARecordRequestWrapper.class);
        org.junit.Assert.assertNotNull(generatedWrappedRequest);

        CreateARecordRequest generatedRequest = generatedWrappedRequest.getRequest();
        org.junit.Assert.assertNotNull(generatedRequest);

        assertRequiredFieldsSet(generatedRequest);
        org.junit.Assert.assertEquals(CUSTOM_ISG, generatedRequest.getIsgGroup());
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryCreateARecordWithErrorFromBackend() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<errors><error>Done!</error></errors>"));

        Dnsapi.CreateARecordRequest request = defaultCreateARecordRequest().build();

        Dnsapi.EmptyResponse response = stub.createARecord(request);
    }

    @org.junit.Test
    public void successfullyDeleteARecord() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.DeleteARecordRequest request = defaultDeleteARecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteARecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        org.junit.Assert.assertEquals(DEFAULT_PATH + "nameToIP/" + IP + "/" + IP_NAME + "." + DEFAULT_SUBDOMAIN, rr.getPath());
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryDeleteARecordWithErrorFromBackend() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<errors><error>Error!</error></errors>"));

        Dnsapi.DeleteARecordRequest request = defaultDeleteARecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteARecord(request);
    }

    private Dnsapi.CreateARecordRequest.Builder defaultCreateARecordRequest() {
        return Dnsapi.CreateARecordRequest.newBuilder()
                .setIp(IP)
                .setIpName(IP_NAME)
                .setSubdomain(DEFAULT_SUBDOMAIN);
    }

    private Dnsapi.DeleteARecordRequest.Builder defaultDeleteARecordRequest() {
        return Dnsapi.DeleteARecordRequest.newBuilder()
                .setHostname(IP_NAME + "." + DEFAULT_SUBDOMAIN)
                .setIp(IP);
    }

    private void assertRequiredFieldsSet(CreateARecordRequest request) {
        org.junit.Assert.assertEquals(IP, request.getIp());
        org.junit.Assert.assertEquals(IP_NAME, request.getIpName());
        org.junit.Assert.assertEquals(DEFAULT_SUBDOMAIN, request.getSubdomain());
    }
}
