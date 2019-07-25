package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.grpc.DnsGrpc.DnsImplBase;
import ch.ethz.vis.dnsapi.netcenter.ARecordManager;
import ch.ethz.vis.dnsapi.netcenter.types.ARecord;
import ch.ethz.vis.dnsapi.netcenter.types.CreateARecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.XmlCreateARecordRequestWrapper;
import ch.ethz.vis.dnsapi.netcenter.types.XmlSuccess;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

import java.io.IOException;

public class DnsImpl extends DnsImplBase {
    private static final Logger LOGGER = LogManager.getLogger(DnsImpl.class);

    private final ARecordManager aRecordManager;
    private final String defaultIsg;

    public DnsImpl(ARecordManager aRecordManager, String defaultIsg) {
        this.aRecordManager = aRecordManager;
        this.defaultIsg = defaultIsg;
    }

    @Override
    public void createARecord(Dnsapi.CreateARecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(createIpRecord(
                    request.getIp(),
                    request.getIpName(),
                    request.getSubdomain(),
                    request.getOptions()));
        } catch (StatusException e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }

    private Dnsapi.EmptyResponse createIpRecord(String ip, String ipName, String subdomain, Dnsapi.RecordOptions options) throws StatusException {
        if (ip == null || ipName == null || subdomain == null) {
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("hostname and ip required!"));
        }

        CreateARecordRequest request = CreateARecordRequest.Builder.newBuilder()
                .withIp(ip)
                .withIpName(ipName)
                .withSubdomain(subdomain)
                .withIsgGroup(options.getIsgGroup() != null ? options.getIsgGroup() : defaultIsg)
                .build();

        try {
            Response<XmlSuccess> response = aRecordManager.CreateARecord(new XmlCreateARecordRequestWrapper(request)).execute();
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }
}
