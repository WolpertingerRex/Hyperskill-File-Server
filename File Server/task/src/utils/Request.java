package utils;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private final RequestType type;
    private boolean isFileName;
    private String filename;
    private int id;
    private byte[] content;

    public Request(RequestType type) {
        this.type = type;
    }

    public Request(RequestType type, boolean isFileName, String filename) {
        this.type = type;
        this.isFileName = isFileName;
        this.filename = filename;
    }

    public Request(RequestType type, boolean isFileName, int id) {
        this.type = type;
        this.isFileName = isFileName;
        this.id = id;
    }

    public Request(RequestType type, String filename) {
        this.type = type;
        this.filename = filename;
    }

    public RequestType getType() {
        return type;
    }

    public boolean isFileName() {
        return isFileName;
    }

    public String getFilename() {
        return filename;
    }

    public int getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
