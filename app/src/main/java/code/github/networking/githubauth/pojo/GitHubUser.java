package code.github.networking.githubauth.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shank on 9/26/17.
 */

public class GitHubUser {

    @SerializedName("id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @SerializedName("login")
    String login;
}
