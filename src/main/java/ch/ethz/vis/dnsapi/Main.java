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
                    GetCNameRecordResponse.class, XmlCreateCNameRecordRequestWrapper.class, XmlSuccess.class,
                    CreateARecordRequest.class, XmlCreateARecordRequestWrapper.class,
                    CreateCNameRecordRequest.class, XmlCreateCNameRecordRequestWrapper.class);
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
            CreateARecordRequest testRecord = CreateARecordRequest.Builder.newBuilder()
                    .withIp("129.132.32.43") // points to old-forum.vis.ethz.ch
                    .withIpName("test-netcenter-api")
                    .withSubdomain("vis.ethz.ch")
                    .withForward(true)
                    .withReverse(false)
                    .withTtl(532)
                    .withDhcp(false)
                    .withDdns(false)
                    .withIsgGroup("adm-vis")
                    .withViews(testviews)
                    .withRemark("generated automatically")
                    .build();

            CreateCNameRecordRequest testcName = CreateCNameRecordRequest.Builder.newBuilder()
                    .withHostname("www.vis.ethz.ch")
                    .withAliasName("test-cname-record")
                    .withSubdomain("vis.ethz.ch")
                    .withIsgGroup("adm-vis")
                    .withTtl(234)
                    .withRemark("generated automatically")
                    .withViews(testviews)
                    .build();

            Response<GetARecordResponse> r = arm.GetARecord("compute0.vis.ethz.ch").execute();
            System.out.println(r.code());
            System.out.println("Successful call to get ip!");
            JAXB.marshal(r.body(), System.out);

            //System.out.println("Request:");
            //JAXB.marshal(new CreateARecordRequest(testRecord), System.out);

            Response<XmlSuccess> resp = arm.CreateARecord(new XmlCreateARecordRequestWrapper(testRecord)).execute();
            System.out.println(resp.code());
            if (resp.isSuccessful()) {
                JAXB.marshal(resp.body(), System.out);
            } else {
                System.out.println(resp.errorBody().string());
            }

            Response<XmlSuccess> resp2 = arm.DeleteARecord("129.132.32.43", "test-netcenter-api.vis.ethz.ch").execute();
            System.out.println(resp2.code());
            if (resp2.isSuccessful()) {
                JAXB.marshal(resp2.body(), System.out);
            } else {
                System.out.println(resp2.errorBody().string());
            }

            //Response<GetCNameRecordResponse> s = crm.GetCNameRecord("beer.vis.ethz.ch").execute();
            //System.out.println(s.code());
            //JAXB.marshal(s.body(), System.out);

            //Response<XmlSuccess> resp3 = crm.CreateCNameRecord(new XmlCreateCNameRecordRequestWrapper(testcName)).execute();
            //System.out.println(resp3.code());
            //System.out.println(resp3.body().getMessage());

            Response<XmlSuccess> resp4 = crm.DeleteCNameRecord("test-cname-record.vis.ethz.ch").execute();
            System.out.println(resp4.code());
            System.out.println(resp4.body().getMessage());

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
}
