package mx.gob.comude.api;

import java.sql.Timestamp;
import java.util.ArrayList;

import mx.gob.comude.model.Tweet;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by codigus on 26/05/2017.
 */

public interface TwitterAPI {

    //@Headers("Authorization:OAuth oauth_consumer_key=\"zHb326FK2xT666c0olifieF9h\",oauth_token=\"865314952470560768-BZ5AXIzHyRndtxWqcYe8u2rKMIb25cF\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1496426570\",oauth_nonce=\"bPoTnC\",oauth_version=\"1.0\",oauth_signature=\"6Qr2ymBJMvIVAJ7uCenbTaotQDg%3D\"")
    @GET("/1.1/statuses/user_timeline.json")
    Call<ArrayList<Tweet>> get (
            @Query("screen_name") String screenName,
            @Header("Authorization") String authorization
    );

}
