package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.dto.*;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class NetcenterAPI {

    private final ARecordManager aRecordManager;

    private final CNameRecordManager cNameRecordManager;

    private final TxtRecordManager txtRecordManager;

    public NetcenterAPI(String url, String username, String password) throws JAXBException {

        OkHttpClient client = buildHttpClient(username, password);

        JAXBContext context = JAXBContext.newInstance(
                GetARecordResponse.class,
                GetCNameRecordResponse.class,
                XmlCreateARecordRequestWrapper.class,
                XmlCreateCNameRecordRequestWrapper.class,
                XmlSuccess.class);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(JaxbConverterFactory.create(context))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.aRecordManager = retrofit.create(ARecordManager.class);
        this.cNameRecordManager = retrofit.create(CNameRecordManager.class);
        this.txtRecordManager = retrofit.create(TxtRecordManager.class);
    }

    public ARecordManager getARecordManager() {
        return aRecordManager;
    }

    public CNameRecordManager getCNameRecordManager() {
        return cNameRecordManager;
    }

    public TxtRecordManager getTxtRecordManager() {
        return txtRecordManager;
    }

    private OkHttpClient buildHttpClient(String username, String password) {
        return new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    String credentials = Credentials.basic(username, password);
                    return response.request().newBuilder().header("Authorization", credentials).build();
                })
                .addInterceptor(new ErrorCodeReplacementInterceptor())
                .build();
    }
}
