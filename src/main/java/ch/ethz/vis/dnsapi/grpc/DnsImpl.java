package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.grpc.DnsGrpc.DnsImplBase;
import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import ch.ethz.vis.dnsapi.netcenter.types.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class DnsImpl extends DnsImplBase {
    private static final Logger LOGGER = LogManager.getLogger(DnsImpl.class);

    private final NetcenterAPI netcenterAPI;
    private final String defaultIsg;

    public DnsImpl(NetcenterAPI netcenterAPI, String defaultIsg) {
        this.netcenterAPI = netcenterAPI;
        this.defaultIsg = defaultIsg;
    }

    @Override
    public void createARecord(Dnsapi.CreateARecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        LOGGER.debug("Got createARecord request");
        try {
            responseObserver.onNext(createARecord(
                    request.getIp(),
                    request.getIpName(),
                    request.getSubdomain(),
                    request.getOptions()));
            LOGGER.debug("Successfully created A record");
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse createARecord(String ip, String ipName, String subdomain, Dnsapi.RecordOptions options) throws StatusException {
        LOGGER.debug("Create A: " + ipName + "." + subdomain + " -> " + ip);

        if (ip == null || ipName == null || subdomain == null) {
            LOGGER.debug("ip, ipName and subdomain not given to createARecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("ip, ipName and subdomain required!"));
        }

        CreateARecordRequest request = CreateARecordRequest.Builder.newBuilder()
                .withIp(ip)
                .withIpName(ipName)
                .withSubdomain(subdomain)
                .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                .withReverse(false) // FIXME: Find sensible defaults.
                .build();

        try {
            Response<XmlSuccess> response = netcenterAPI.getaRecordManager().CreateARecord(
                    new XmlCreateARecordRequestWrapper(request)).execute();
            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void deleteARecord(Dnsapi.DeleteARecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(deleteARecord(request.getIp(), request.getHostname()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse deleteARecord(String ip, String fqName) throws StatusException {
        LOGGER.debug("Delete A: " + fqName + " -> " + ip);
        if (ip == null || fqName == null) {
            LOGGER.debug("ip and fqName not given to deleteARecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("ip and fqName required"));
        }

        try {
            Response<XmlSuccess> response = netcenterAPI.getaRecordManager().DeleteARecord(ip, fqName).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void createCNameRecord(Dnsapi.CreateCNameRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(createCNameRecord(request.getHostname(), request.getAliasName(), request.getSubdomain(), request.getOptions()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse createCNameRecord(String hostname, String aliasName, String subdomain, Dnsapi.RecordOptions options) throws StatusException {
        LOGGER.debug("Create CNAME: " + aliasName + "." + subdomain + " -> " + hostname);
        if (hostname == null || aliasName == null || subdomain == null) {
            LOGGER.debug("hostname, aliasName and subdomain not given to createCNameRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("hostname, aliasName and subdomain required"));
        }

        try {
            CreateCNameRecordRequest request = CreateCNameRecordRequest.Builder.newBuilder()
                    .withHostname(hostname)
                    .withAliasName(aliasName)
                    .withSubdomain(subdomain)
                    .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                    .build();

            Response<XmlSuccess> response = netcenterAPI.getcNameRecordManager().CreateCNameRecord(
                    new XmlCreateCNameRecordRequestWrapper(request)).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void deleteCNameRecord(Dnsapi.DeleteCNameRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(deleteCNameRecord(request.getAlias()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse deleteCNameRecord(String alias) throws StatusException {
        LOGGER.debug("Delete CNAME: " + alias);
        if (alias == null) {
            LOGGER.debug("alias not given to deleteCNameRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("alias required"));
        }

        try {
            Response<XmlSuccess> response = netcenterAPI.getcNameRecordManager().DeleteCNameRecord(alias).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void createTxtRecord(Dnsapi.CreateTxtRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(createTxtRecord(request.getTxtName(), request.getSubdomain(), request.getValue(), request.getOptions()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse createTxtRecord(String txtName, String subdomain, String value, Dnsapi.RecordOptions options) throws StatusException {
        LOGGER.debug("Create TXT: " + txtName + "." + subdomain + " -> " + value);
        if (txtName == null || subdomain == null || value == null) {
            LOGGER.debug("txtName, subdomain and value not given to createTxtRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("txtName, subdomain and value are required"));
        }

        try {
            CreateTxtRecordRequest request = CreateTxtRecordRequest.Builder.newBuilder()
                    .withTxtName(txtName)
                    .withSubdomain(subdomain)
                    .withValue(value)
                    .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                    .withViews(Collections.singletonList("intern")) // FIXME: Find sensible default
                    .withTtl(options.getTtl() > 0 ? options.getTtl() : 600) // FIXME: Find sensible default
                    .build();

            Response<TxtResponse> response = netcenterAPI.getTxtRecordManager().CreateTxtRecord(request).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            } else if (response.body().getTxtRecord() == null) {
                LOGGER.error("Could not parse body!");
                throw new StatusException(Status.INTERNAL.withDescription("Could not parse netcenter response"));
            } else if (response.body().getErrors() != null) {
                String errorMsg = response.body().getErrors().stream().map(JsonError::getErrorMsg).collect(Collectors.joining(", "));
                LOGGER.debug("Something went wrong: " + errorMsg);
                throw new StatusException(Status.INTERNAL.withDescription("Got errors from the API: " + errorMsg));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void deleteTxtRecord(Dnsapi.DeleteTxtRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(deleteTxtRecord(request.getFqName(), request.getValue()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse deleteTxtRecord(String fqName, String value) throws StatusException {
        LOGGER.debug("Delete TXT: " + fqName + " -> " + value);
        if (fqName == null || value == null) {
            LOGGER.debug("fqName and value not given to deleteTxtRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("fqName and value are required"));
        }

        TxtRecord wantedRecord = TxtRecord.Builder.newBuilder()
                .withFqName(fqName)
                .withValue(value)
                .build();

        try {
            Response<TxtResponse> searchTxtRecordResponse = netcenterAPI.getTxtRecordManager().SearchTxtRecord(wantedRecord).execute();
            if (!searchTxtRecordResponse.isSuccessful()) {
                LOGGER.error("Searching for txt record id went wrong: " + searchTxtRecordResponse.errorBody().string());
                throw new StatusException(Status.INTERNAL.withDescription("Error getting txt id"));
            } else if (searchTxtRecordResponse.body() == null) {
                LOGGER.error("Empty body in successful call");
                throw new StatusException(Status.INTERNAL.withDescription("Empty body returned in successful call"));
            } else if (searchTxtRecordResponse.body().getErrors() != null) {
                String errorMsg = searchTxtRecordResponse.body().getErrors().stream().map(JsonError::getErrorMsg).collect(Collectors.joining(","));
                LOGGER.error("Error returned from the netcenter API: " + errorMsg);
                throw new StatusException(Status.INTERNAL.withDescription("Errors returned by the API: " + errorMsg));
            } else if (searchTxtRecordResponse.body().getTxtRecord() == null) {
                LOGGER.error("TXT record not found");
                throw new StatusException(Status.NOT_FOUND.withDescription("TXT record not found"));
            }

            TxtRecord record = searchTxtRecordResponse.body().getTxtRecord();
            LOGGER.debug("Found TXT record: " + record.getFqName() + " -> " + record.getValue());
            if (record.getId() == null || record.getFqName() == null || record.getValue() == null) {
                LOGGER.warn("TXT record not found");
                throw new StatusException(Status.NOT_FOUND.withDescription("TXT record not found"));
            } else if (!record.getFqName().equals(fqName) || !record.getValue().equals(value)) {
                LOGGER.warn("TXT record does not match given parameters");
                throw new StatusException(Status.NOT_FOUND.withDescription("TXT record not found"));
            }

            LOGGER.debug("Got TXT id: " + record.getId());

            Response<JsonResponse> deleteTxtResponse = netcenterAPI.getTxtRecordManager().DeleteTxtRecord(record.getId()).execute();

            if (!deleteTxtResponse.isSuccessful()) {
                String error = deleteTxtResponse.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            } else if (deleteTxtResponse.body().getErrors() != null) {
                String errorMsg = deleteTxtResponse.body().getErrors().stream().map(JsonError::getErrorMsg).collect(Collectors.joining(", "));
                LOGGER.debug("Something went wrong: " + errorMsg);
                throw new StatusException(Status.INTERNAL.withDescription("API reported error: " + errorMsg));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("Error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }
}
