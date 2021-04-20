package org.atiuleneva.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class AdConfig {

    @JsonProperty("safeFlags")
    public List<String> safeFlags = new ArrayList<String>();
    @JsonProperty("highRiskFlags")
    public List<Object> highRiskFlags = new ArrayList<Object>();
    @JsonProperty("unsafeFlags")
    public List<String> unsafeFlags = new ArrayList<String>();
    @JsonProperty("wallUnsafeFlags")
    public List<Object> wallUnsafeFlags = new ArrayList<Object>();
    @JsonProperty("showsAds")
    public Boolean showsAds;

}
