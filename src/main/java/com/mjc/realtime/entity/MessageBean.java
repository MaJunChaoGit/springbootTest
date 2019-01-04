package com.mjc.realtime.entity;

import java.io.Serializable;
import java.util.List;

public class MessageBean implements Serializable{
    private static final long serialVersionUID = -3697433670758405042L;
    private List<MovingTarget> data;

    public List<MovingTarget> getData() {
        return data;
    }

    public void setData(List<MovingTarget> data) {
        this.data = data;
    }
}
