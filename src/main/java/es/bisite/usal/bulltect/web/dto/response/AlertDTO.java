package es.bisite.usal.bulltect.web.dto.response;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertDTO extends ResourceSupport {

    @JsonProperty("identity")
    private String identity;
    @JsonProperty("title")
    private String title;
    @JsonProperty("level")
    private String level;
    @JsonProperty("payload")
    private String payload;
    @JsonProperty("create_at")
    private String createAt;
    @JsonProperty("son")
    private SonDTO son;

    public AlertDTO() {
    }

    public AlertDTO(String identity, String level, String title, String payload, String createAt, SonDTO son) {
        super();
        this.identity = identity;
        this.level = level;
        this.title = title;
        this.payload = payload;
        this.createAt = createAt;
        this.son = son;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public SonDTO getSon() {
        return son;
    }

    public void setSon(SonDTO son) {
        this.son = son;
    }
}
