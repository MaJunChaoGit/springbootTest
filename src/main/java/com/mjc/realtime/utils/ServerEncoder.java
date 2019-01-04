package com.mjc.realtime.utils;

import com.alibaba.fastjson.JSONObject;
import com.mjc.realtime.entity.MessageBean;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ServerEncoder implements Encoder.Text<MessageBean> {
    @Override
    public String encode(MessageBean messageBean) throws EncodeException {
        return JSONObject.toJSONString(messageBean);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
