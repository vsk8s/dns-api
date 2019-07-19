package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.types.CreateCNameRecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.GetCNameRecordResponse;
import ch.ethz.vis.dnsapi.netcenter.types.TxtRecord;
import retrofit2.Call;
import retrofit2.http.*;

// FIXME: This can actually map both A and AAAA records. Rename appropriately
public interface TxtRecordManager {
    @POST("txt")
    Call<String> CreateTxtRecord(@Body TxtRecord txtRecord);

    @GET("txt/{id}")
    Call<TxtRecord> GetTxtRecord(@Path("id") String id);

    @POST("txt/search")
    Call<TxtRecord> SearchTxtRecord (@Body TxtRecord txtRecord);

    @DELETE("alias/{id}")
    Call<String> DeleteTxtRecord(@Path("id") String id);
}
