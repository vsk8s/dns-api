package ch.ethz.vis.dnsapi;

import ch.ethz.vis.dnsapi.exceptions.InitializationException;
import ch.ethz.vis.dnsapi.grpc.GrpcServer;
import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Starting application...");

        try {
            runApplication();
        } catch (Exception e) {
            LOG.error("Uncaught", e);
        }
    }

    private static void runApplication() throws IOException, InitializationException, JAXBException {
        Config config = loadConfiguration();
        GrpcServer s = instantiateServer(config);

        LOG.info("Completed startup");
        s.serve();
    }

    private static Config loadConfiguration() throws IOException, InitializationException {
        InputStream is = openPropertiesFile();
        if (is != null) {
            return loadProperties(is);
        } else {
            throw new IOException("config not found");
        }
    }

    private static GrpcServer instantiateServer(Config config) throws JAXBException {
        NetcenterAPI netcenterAPI = new NetcenterAPI("https://www.netcenter.ethz.ch/netcenter/rest/",
                config.getUsername(),
                config.getPassword());

        return new GrpcServer(netcenterAPI,
                config.getIsgGroup(),
                config.getCertFilePath(),
                config.getKeyFilePath());
    }

    private static Config loadProperties(InputStream is) throws IOException, InitializationException {
        Properties p = new Properties();
        p.load(is);
        is.close();
        return new Config(p);
    }

    private static InputStream openPropertiesFile() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream("dnsapi.properties");
    }

    void unusedButMaybeUseful() {
        //List<String> testviews = new LinkedList<>();
        //testviews.add("intern");
        //CreateARecordRequest testRecord = CreateARecordRequest.Builder.newBuilder()
        //        .withIp("129.132.32.43") // points to old-forum.vis.ethz.ch
        //        .withIpName("test-netcenter-api")
        //        .withSubdomain("vis.ethz.ch")
        //        .withForward(true)
        //        .withReverse(false)
        //        .withTtl(532)
        //        .withDhcp(false)
        //        .withDdns(false)
        //        .withIsgGroup("adm-vis")
        //        .withViews(testviews)
        //        .withRemark("generated automatically")
        //        .build();

        //CreateCNameRecordRequest testcName = CreateCNameRecordRequest.Builder.newBuilder()
        //        .withHostname("www.vis.ethz.ch")
        //        .withAliasName("test-cname-record")
        //        .withSubdomain("vis.ethz.ch")
        //        .withIsgGroup("adm-vis")
        //        .withTtl(234)
        //        .withRemark("generated automatically")
        //        .withViews(testviews)
        //        .build();

        //Response<GetARecordResponse> r = arm.GetARecord("compute0.vis.ethz.ch").execute();
        //System.out.println(r.code());
        //System.out.println("Successful call to get ip!");
        //JAXB.marshal(r.body(), System.out);

        //System.out.println("Request:");
        //JAXB.marshal(new CreateARecordRequest(testRecord), System.out);

        //Response<XmlSuccess> resp = arm.CreateARecord(new XmlCreateARecordRequestWrapper(testRecord)).execute();
        //System.out.println(resp.code());
        //if (resp.isSuccessful()) {
        //    JAXB.marshal(resp.body(), System.out);
        //} else {
        //    System.out.println(resp.errorBody().string());
        //}

        //Response<XmlSuccess> resp2 = arm.DeleteARecord("129.132.32.43", "test-netcenter-api.vis.ethz.ch").execute();
        //System.out.println(resp2.code());
        //if (resp2.isSuccessful()) {
        //    JAXB.marshal(resp2.body(), System.out);
        //} else {
        //    System.out.println(resp2.errorBody().string());
        //}

        //Response<GetCNameRecordResponse> s = crm.GetCNameRecord("beer.vis.ethz.ch").execute();
        //System.out.println(s.code());
        //JAXB.marshal(s.body(), System.out);

        //Response<XmlSuccess> resp3 = crm.CreateCNameRecord(new XmlCreateCNameRecordRequestWrapper(testcName)).execute();
        //System.out.println(resp3.code());
        //System.out.println(resp3.body().getMessage());

        //Response<XmlSuccess> resp4 = crm.DeleteCNameRecord("test-cname-record.vis.ethz.ch").execute();
        //System.out.println(resp4.code());
        //System.out.println(resp4.body().getMessage());

        //TxtRecord txtRecord = new TxtRecord();
        //txtRecord.setValue("some-text-in-the-txt-record");
        //txtRecord.setTxtName("test-netcenter-api-txt");
        //txtRecord.setSubdomain("vis.ethz.ch");
        //txtRecord.setIsgGroup("adm-vis");
        //txtRecord.setRemark("Testing of automatic txt creation");
        //txtRecord.setTtl(123);
        //txtRecord.setViews(testviews);

        //ObjectMapper om = new ObjectMapper();
        //Response<TxtResponse> resp = trm.CreateTxtRecord(txtRecord).execute();
        //System.out.println(resp.code());
        //System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resp.body()));

        //if (resp.body().getErrors() != null) {
        //    throw new Exception("aaa");
        //}
        //TxtRecord txtRecord1 = resp.body().getTxtRecord();

        //Response<TxtResponse> resp2 = trm.GetTxtRecord(txtRecord1.getId()).execute();
        //System.out.println(resp2.code());
        //System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resp2.body()));

        //Response<JsonResponse> resp3 = trm.DeleteTxtRecord(txtRecord1.getId()).execute();
        //System.out.println(resp3.code());
        //System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resp3.body()));
    }
}
