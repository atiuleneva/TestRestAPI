package org.atiuleneva.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "success",
        "status"
})
@Generated("jsonschema2pojo")
public class ImageDataResponse {

    @JsonProperty("data")
    public ImageData data;
    @JsonProperty("success")
    public Boolean success;
    @JsonProperty("status")
    public Integer status;
}