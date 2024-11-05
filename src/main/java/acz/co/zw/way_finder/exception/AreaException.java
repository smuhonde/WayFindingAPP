package acz.co.zw.way_finder.exception;

import lombok.Getter;

@Getter
public class AreaException extends  RuntimeException{

    private String responseCode = "500";

    public AreaException(String message, String responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public AreaException(String message) {
        super(message);
    }

}
