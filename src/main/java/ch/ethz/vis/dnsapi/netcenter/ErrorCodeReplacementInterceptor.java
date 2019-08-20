package ch.ethz.vis.dnsapi.netcenter;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class ErrorCodeReplacementInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);

            return processResponse(response);
        }

    private Response processResponse(Response response) throws IOException {
        ResponseBody responseBody = response.body();

        if (responseBody != null && response.isSuccessful()) {
            String body = responseBody.string();
            if (body.trim().startsWith("<error")) {
                return response.newBuilder().code(418).body(ResponseBody.create(responseBody.contentType(), body)).build();
            } else {
                return response.newBuilder().body(ResponseBody.create(responseBody.contentType(), body)).build();
            }
        } else {
            return response;
        }
    }
}
