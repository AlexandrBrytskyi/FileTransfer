package model;

import java.io.Serializable;

/**
 * User: huyti
 * Date: 20.10.15
 */
public class ObjectToBeTrans implements Serializable {
    private String name;
    private byte[] bytes;

    public ObjectToBeTrans(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
