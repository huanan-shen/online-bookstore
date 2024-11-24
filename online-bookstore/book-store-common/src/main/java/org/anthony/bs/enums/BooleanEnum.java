package org.anthony.bs.enums;

public enum BooleanEnum {
    TRUE(1),
    FALSE(0);

    private int code;

    BooleanEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
