package ch.ethz.vis.dnsapi;

//import ch.ethz.vis.dnsapi.grpc.Dnsapi;
import ch.ethz.vis.dnsapi.netcenter.ARecordManager;
import ch.ethz.vis.dnsapi.netcenter.CNameRecordManager;
import ch.ethz.vis.dnsapi.netcenter.TxtRecordManager;
import ch.ethz.vis.dnsapi.netcenter.types.*;
import okhttp3.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties p = new Properties();
            InputStream is = loader.getResourceAsStream("dnsapi.properties");
            p.load(is);
            Config c = new Config(p);

            OkHttpClient client = new OkHttpClient.Builder()
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, okhttp3.Response response) throws IOException {
                            String credentials = Credentials.basic(c.getUsername(), c.getPassword());
                            return response.request().newBuilder().header("Authorization", credentials).build();
                        }
                    })
                    .addInterceptor(new Interceptor() {
                        @Override
                        @EverythingIsNonNull
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request request = chain.request();
                            okhttp3.Response response = chain.proceed(request);
                            String responseBody = response.body().string();
                            if (response.isSuccessful() && responseBody.trim().startsWith("<error")) {
                                return response.newBuilder().code(418).body(ResponseBody.create(response.body().contentType(), responseBody)).build();
                            }
                            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBody)).build();
                        }
                    })
                    .build();

            JAXBContext context = JAXBContext.newInstance(CreateARecordRequest.class, GetARecordResponse.class,
                    GetCNameRecordResponse.class, CreateCNameRecordRequest.class, XmlError.class);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://www.netcenter.ethz.ch/netcenter/rest/")
                    .addConverterFactory(JaxbConverterFactory.create(context))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            ARecordManager arm = retrofit.create(ARecordManager.class);
            CNameRecordManager crm = retrofit.create(CNameRecordManager.class);
            TxtRecordManager trm = retrofit.create(TxtRecordManager.class);

            List<String> testviews = new LinkedList<>();
            testviews.add("intern");
            ARecord testRecord = new ARecord();
            testRecord.setIp("129.132.32.43"); // points to old-forum.vis.ethz.ch
            testRecord.setIpname("test-netcenter-api");
            testRecord.setSubdomain("vis.ethz.ch");
            testRecord.setForward(true);
            testRecord.setReverse(false);
            testRecord.setTtl(532);
            testRecord.setDhcp(false);
            testRecord.setIsgGroup("adm-vis");
            testRecord.setViews(testviews);
            testRecord.setRemark("generated automatically");

            CNameRecord testcName = new CNameRecord();
            testcName.setAliasName("test-netcenter-api-cname");
            testcName.setSubdomain("vis.ethz.ch");
            testcName.setDest("www.vis.ethz.ch");
            testcName.setIsgGroup("adm-vis");
            testcName.setTtl(234);
            testcName.setRemark("generated automatically");
            testcName.setViews(testviews);

            //Response<GetARecordResponse> r = arm.GetARecord("compute0.vis.ethz.ch").execute();
            //System.out.println(r.code());
            //System.out.println("Successful call to get ip!");
            //JAXB.marshal(r.body(), System.out);

            //System.out.println("Request:");
            //JAXB.marshal(new CreateARecordRequest(testRecord), System.out);

            Response<XmlSuccess> resp = arm.CreateARecord(new CreateARecordRequest(testRecord)).execute();
            System.out.println(resp.code());
            System.out.println(resp.errorBody().string());

            //Response<String> resp2 = arm.DeleteARecord("129.132.32.43", "test-netcenter-api.vis.ethz.ch").execute();
            //System.out.println(resp2.code());
            //System.out.println(resp2.body());

            Response<GetCNameRecordResponse> s = crm.GetCNameRecord("beer.vis.ethz.ch").execute();
            System.out.println(s.code());
            JAXB.marshal(s.body(), System.out);

            //Response<String> resp3 = crm.CreateCNameRecord(new CreateCNameRecordRequest(testcName)).execute();
            //System.out.println(resp3.code());
            //System.out.println(resp3.body());

            //Response<String> resp4 = crm.DeleteCNameRecord("test-netcenter-api-cname.vis.ethz.ch").execute();
            //System.out.println(resp4.code());
            //System.out.println(resp4.body());

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
        } catch (JAXBException e) {
            System.out.println("JAXB error: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static void testXML() {
        String test =
                "  <usedIps><usedIp>\n" +
                "    <ip>129.132.240.13</ip>\n" +
                "    <ipSubnet>129.132.240.0</ipSubnet>\n" +
                "    <fqname>avalon.ethz.ch</fqname>\n" +
                "    <forward>Y</forward>\n" +
                "    <reverse>N</reverse>\n" +
                "    <ttl>600</ttl>\n" +
                "    <dhcp>Y</dhcp>\n" +
                "    <dhcpMac>00-00-00-00-00-00-00-E0</dhcpMac>\n" +
                "    <ddns>N</ddns>\n" +
                "    <isgGroup>id-kom</isgGroup>\n" +
                "    <lastDetection>2009-06-25 15:10:17.0</lastDetection>\n" +
                "    <views>\n" +
                "      <view>intern</view>\n" +
                "      <view>extern</view>\n" +
                "    </views>\n" +
                "  </usedIp></usedIps>";
        try {
            JAXBContext context = JAXBContext.newInstance(GetARecordResponse.class);
            Unmarshaller u = context.createUnmarshaller();

            Object resp = u.unmarshal(new StringReader(test));

            if (resp instanceof GetARecordResponse) {
                GetARecordResponse a = (GetARecordResponse)resp;
                System.out.println(a.getRecords().get(0).getIp() + " " + a.getRecords().get(0).getIsgGroup());

                CreateARecordRequest createARecordRequest = new CreateARecordRequest(a.getRecords().get(0));
                JAXB.marshal(createARecordRequest, System.out);
            } else {
                System.out.println("resp was null!");
            }
        } catch (JAXBException e) {
            System.out.println("Some JAXB error: " + e);
        }

        System.out.println("Done!");
    }
}
