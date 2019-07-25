package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.types.*;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public class NetcenterAPI {
    private final ARecordManager aRecordManager;
    private final CNameRecordManager cNameRecordManager;
    private final TxtRecordManager txtRecordManager;

    public NetcenterAPI(String username, String password) throws JAXBException {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    String credentials = Credentials.basic(username, password);
                    return response.request().newBuilder().header("Authorization", credentials).build();
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

        JAXBContext context = JAXBContext.newInstance(GetARecordResponse.class, GetCNameRecordResponse.class,
                XmlCreateARecordRequestWrapper.class, XmlCreateCNameRecordRequestWrapper.class, XmlSuccess.class);
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://www.netcenter.ethz.ch/netcenter/rest/")
                .addConverterFactory(JaxbConverterFactory.create(context))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.aRecordManager = retrofit.create(ARecordManager.class);
        this.cNameRecordManager = retrofit.create(CNameRecordManager.class);
        this.txtRecordManager = retrofit.create(TxtRecordManager.class);
    }

    public ARecordManager getaRecordManager() {
        return aRecordManager;
    }

    public CNameRecordManager getcNameRecordManager() {
        return cNameRecordManager;
    }

    public TxtRecordManager getTxtRecordManager() {
        return txtRecordManager;
    }
}
