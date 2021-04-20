package org.atiuleneva.dto;

import lombok.Data;

@Data
public class ErrorBody {
    public Integer status;
    public String message;
    public String timestamp;
}
