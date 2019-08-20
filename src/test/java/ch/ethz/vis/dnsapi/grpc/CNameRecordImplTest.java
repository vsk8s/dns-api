package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.dto.CreateCNameRecordRequest;
import ch.ethz.vis.dnsapi.netcenter.dto.XmlCreateCNameRecordRequestWrapper;
import io.grpc.StatusRuntimeException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import javax.xml.bind.JAXB;

public class CNameRecordImplTest extends DnsImplBase {
    private static final String HOST_NAME = "the.fully.qualified.domain.name.test";
    private static final String ALIAS_NAME = "the.alias";

    @org.junit.Test
    public void successfullyCreateARecordWithDefaultIsg() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.CreateCNameRecordRequest request = defaultCreateCNameRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.createCNameRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        XmlCreateCNameRecordRequestWrapper generatedWrappedRequest = JAXB.unmarshal(rr.getBody().inputStream(), XmlCreateCNameRecordRequestWrapper.class);
        org.junit.Assert.assertNotNull(generatedWrappedRequest);

        CreateCNameRecordRequest generatedRequest = generatedWrappedRequest.getRequest();
        org.junit.Assert.assertNotNull(generatedRequest);

        assertRequiredFieldsSet(generatedRequest);
        org.junit.Assert.assertEquals(DEFAULT_ISG, generatedRequest.getIsgGroup());
    }

    @org.junit.Test
    public void successfullyCreateARecordWithCustomIsg() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.CreateCNameRecordRequest request = defaultCreateCNameRecordRequest()
                .setOptions(Dnsapi.RecordOptions.newBuilder().setIsgGroup(CUSTOM_ISG).build())
                .build();

        Dnsapi.EmptyResponse response = stub.createCNameRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        XmlCreateCNameRecordRequestWrapper generatedWrappedRequest = JAXB.unmarshal(rr.getBody().inputStream(), XmlCreateCNameRecordRequestWrapper.class);
        org.junit.Assert.assertNotNull(generatedWrappedRequest);

        CreateCNameRecordRequest generatedRequest = generatedWrappedRequest.getRequest();
        org.junit.Assert.assertNotNull(generatedRequest);

        assertRequiredFieldsSet(generatedRequest);
        org.junit.Assert.assertEquals(CUSTOM_ISG, generatedRequest.getIsgGroup());
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryCreateARecordWithErrorFromBackend() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<errors><error>Done!</error></errors>"));

        Dnsapi.CreateCNameRecordRequest request = defaultCreateCNameRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.createCNameRecord(request);
    }

    @org.junit.Test
    public void successfullyDeleteARecord() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));

        Dnsapi.DeleteCNameRecordRequest request = defaultDeleteCNameRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteCNameRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        org.junit.Assert.assertEquals(DEFAULT_PATH + "alias/" + ALIAS_NAME + "." + DEFAULT_SUBDOMAIN, rr.getPath());
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryDeleteARecordWithErrorFromBackend() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("<errors><error>Error!</error></errors>"));

        Dnsapi.DeleteCNameRecordRequest request = defaultDeleteCNameRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteCNameRecord(request);
    }

    private Dnsapi.CreateCNameRecordRequest.Builder defaultCreateCNameRecordRequest() {
        return Dnsapi.CreateCNameRecordRequest.newBuilder()
                .setHostname(HOST_NAME)
                .setAliasName(ALIAS_NAME)
                .setSubdomain(DEFAULT_SUBDOMAIN);
    }

    private Dnsapi.DeleteCNameRecordRequest.Builder defaultDeleteCNameRecordRequest() {
        return Dnsapi.DeleteCNameRecordRequest.newBuilder()
                .setAlias(ALIAS_NAME + "." + DEFAULT_SUBDOMAIN);
    }

    private void assertRequiredFieldsSet(CreateCNameRecordRequest request) {
        org.junit.Assert.assertEquals(HOST_NAME, request.getHostname());
        org.junit.Assert.assertEquals(ALIAS_NAME, request.getAliasName());
        org.junit.Assert.assertEquals(DEFAULT_SUBDOMAIN, request.getSubdomain());
    }
}
