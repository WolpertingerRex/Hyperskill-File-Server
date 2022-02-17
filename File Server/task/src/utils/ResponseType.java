package utils;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    SUCCESS("200"),
    NOT_EXIST("404"),
    FORBIDDEN("403");

    private String value;

    ResponseType(final String value) {
        this.value = value;
    }

}
