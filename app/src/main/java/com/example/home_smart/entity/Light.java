package com.example.home_smart.entity;

public class Light {

    private Long id;

    private String lightName;

    private boolean status; // true = locked, false = unlocked

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLockName() {
        return lightName;
    }

    public void setLockName(String lockName) {
        this.lightName = lockName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
