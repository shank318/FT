package code.github.networking.githubauth.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shank on 9/26/17.
 */

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("scope")
    private String scope;
    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
