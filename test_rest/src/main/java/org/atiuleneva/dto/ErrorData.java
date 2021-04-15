package org.atiuleneva.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class ErrorData {

    @JsonProperty("code")
    public Integer code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("type")
    public String type;
    @JsonProperty("exception")
    public List<Object> exception = new ArrayList<Object>();

}
