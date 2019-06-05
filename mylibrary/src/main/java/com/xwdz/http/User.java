package com.xwdz.http;

import com.google.gson.annotations.SerializedName;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/6
 */
public class User {

    /**
     * "login": "A",
     "id": 1410106,
     "node_id": "MDQ6VXNlcjE0MTAxMDY=",
     "avatar_url": "https://avatars2.githubusercontent.com/u/1410106?v=4",
     "gravatar_id": "",
     "url": "https://api.github.com/users/A",
     "html_url": "https://github.com/A",
     "followers_url": "https://api.github.com/users/A/followers",
     "following_url": "https://api.github.com/users/A/following{/other_user}",
     "gists_url": "https://api.github.com/users/A/gists{/gist_id}",
     "starred_url": "https://api.github.com/users/A/starred{/owner}{/repo}",
     "subscriptions_url": "https://api.github.com/users/A/subscriptions",
     "organizations_url": "https://api.github.com/users/A/orgs",
     "repos_url": "https://api.github.com/users/A/repos",
     "events_url": "https://api.github.com/users/A/events{/privacy}",
     "received_events_url": "https://api.github.com/users/A/received_events",
     "type": "User",
     "site_admin": false,
     "score": 152.38231
     */
    @SerializedName("login")
    private String login;
    @SerializedName("id")
    private String id;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("node_id")
    private String nodeId;
    @SerializedName("score")
    private String score;
    @SerializedName("html_url")
    private String htmlUrl;

    public User(String login, String id, String avatarUrl, String nodeId, String score, String htmlUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.nodeId = nodeId;
        this.score = score;
        this.htmlUrl = htmlUrl;
    }


}
