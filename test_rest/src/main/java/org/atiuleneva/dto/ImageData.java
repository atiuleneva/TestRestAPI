package org.atiuleneva.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("jsonschema2pojo")
public class ImageData {

    @JsonProperty("id")
    public String id;
    @JsonProperty("title")
    public Object title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("datetime")
    public Integer datetime;
    @JsonProperty("type")
    public String type;
    @JsonProperty("animated")
    public Boolean animated;
    @JsonProperty("width")
    public Integer width;
    @JsonProperty("height")
    public Integer height;
    @JsonProperty("size")
    public Integer size;
    @JsonProperty("views")
    public Integer views;
    @JsonProperty("bandwidth")
    public Integer bandwidth;
    @JsonProperty("vote")
    public Object vote;
    @JsonProperty("favorite")
    public Boolean favorite;
    @JsonProperty("nsfw")
    public Boolean nsfw;
    @JsonProperty("section")
    public Object section;
    @JsonProperty("account_url")
    public Object accountUrl;
    @JsonProperty("account_id")
    public Integer accountId;
    @JsonProperty("is_ad")
    public Boolean isAd;
    @JsonProperty("in_most_viral")
    public Boolean inMostViral;
    @JsonProperty("has_sound")
    public Boolean hasSound;
    @JsonProperty("tags")
    public List<Object> tags = new ArrayList<Object>();
    @JsonProperty("ad_type")
    public Integer adType;
    @JsonProperty("ad_url")
    public String adUrl;
    @JsonProperty("edited")
    public String edited;
    @JsonProperty("in_gallery")
    public Boolean inGallery;
    @JsonProperty("deletehash")
    public String deletehash;
    @JsonProperty("name")
    public String name;
    @JsonProperty("link")
    public String link;
    @JsonProperty("mp4")
    public String mp4;
    @JsonProperty("gifv")
    public String gifv;
    @JsonProperty("hls")
    public String hls;
    @JsonProperty("mp4_size")
    public Integer mp4Size;
    @JsonProperty("looping")
    public Boolean looping;
    @JsonProperty("ad_config")
    public AdConfig adConfig;
    @JsonProperty("error")
    public ErrorData error;
    @JsonProperty("request")
    public String request;
    @JsonProperty("method")
    public String method;
}
