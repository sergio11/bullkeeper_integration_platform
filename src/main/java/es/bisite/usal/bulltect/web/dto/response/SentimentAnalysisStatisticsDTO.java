package es.bisite.usal.bulltect.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class SentimentAnalysisStatisticsDTO {
    
    @JsonProperty("social_media")
    private String socialMedia;
    @JsonProperty("value")
    private String value;

    public SentimentAnalysisStatisticsDTO(String socialMedia, String value) {
        this.socialMedia = socialMedia;
        this.value = value;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SocialMediaActivityDTO{" + "socialMedia=" + socialMedia + ", value=" + value + '}';
    }
    
}
