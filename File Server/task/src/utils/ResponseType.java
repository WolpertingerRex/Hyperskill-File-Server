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

    public String getValue() {
        return value;
    }

    public static ResponseType ofValue(String v){
        for (ResponseType x : ResponseType.values()) {
            if (x.getValue().equals(v)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
