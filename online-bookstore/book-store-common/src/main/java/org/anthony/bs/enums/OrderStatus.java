package org.anthony.bs.enums;

import java.util.Objects;

public enum OrderStatus {
    COMPLETED(1, "COMPLETED"),
    NEW(0, "NEW"),
    CANCELED(-1, "CANCELED");
    private int status;

    private String description;

    OrderStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static OrderStatus valueOfCode(Integer status) {
        for (OrderStatus orderStatus : values()) {
            if (Objects.equals(orderStatus.getStatus(), status)) {
                return orderStatus;
            }
        }
        return null;
    }
}
