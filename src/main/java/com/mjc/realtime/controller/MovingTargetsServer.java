package com.mjc.realtime.controller;

import com.mjc.realtime.entity.MessageBean;
import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.entity.TimePosition;
import com.mjc.realtime.service.impl.MovingTargetService;
import com.mjc.realtime.utils.ServerEncoder;
import com.mjc.realtime.utils.Timer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/movingTargets", encoders = {ServerEncoder.class})
@Component
public class MovingTargetsServer {
    private static Logger log = Logger.getLogger(UserController.class);
    private static int onlineCount = 0;
    private static final int delay = 5000;
    private static CopyOnWriteArraySet<MovingTargetsServer> webSocketSet = new CopyOnWriteArraySet<MovingTargetsServer>();
    private Session session;
    private String sid = "";
    private List<MovingTarget> movingTargetList;
    private Date startTime = null;
    private Date leftTime = null;
    private Date endTime = null;
    private String status = "pending";

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.status = "opening";
        webSocketSet.add(this);
        addOnlineCount();
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        initDate();
        movingTargetList = MovingTargetService.movingTargets;
        sendMovingTargetInfo();
    }

    /**
     * 初始化时间对象
     */
    private void initDate() {
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(MovingTargetService.startTime);
            endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(MovingTargetService.endTime);
        } catch (ParseException e) {
            log.error("起始或结束时间获取异常");
        }
    }

    /**
     * 关键方法，使用区域时间进行判断，一批一批的拿数据出去，用于进行发送数据
     */
    private void sendMovingTargetInfo() {
        List<MovingTarget> list = new ArrayList<MovingTarget>();
        // 获取当前时间轴右部分时间
        Calendar time = Calendar.getInstance();
        time.setTime(startTime);
        time.add(Calendar.MILLISECOND, delay);
        Date rightTime = time.getTime();
        if (rightTime.compareTo(endTime) >= 0 || !this.status.equals("opening")) return;
        // 获取时间轴左边时间
        leftTime = (Date) startTime.clone();

        for (MovingTarget movingTarget : movingTargetList) {
            MovingTarget target = new MovingTarget();
            target.setId(movingTarget.getId());
            target.setStartTime(movingTarget.getStartTime());
            target.setEndTime(movingTarget.getEndTime());
            target.setOptions(movingTarget.getOptions());

            for (TimePosition timePosition : movingTarget.getTimePositions()) {
                // 获取当前目标的时间
                Date targetTime = null;
                try {
                    targetTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(timePosition.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // 计算区间时间，进行比对，是否目标时间在区间时间内
                if (isEffectiveDate(targetTime, leftTime, rightTime)) {
                    target.getTimePositions().add(timePosition);
                }
            }
            list.add(movingTarget);
        }
        // 重时间轴推进
        startTime = (Date) rightTime.clone();
        try {
            MessageBean messageBean = new MessageBean();
            messageBean.setData(list);
            this.session.getBasicRemote().sendObject(messageBean);
        } catch (IOException e) {
            try {
                this.session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMovingTargetInfo();
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        this.status = "close";
        log.info(sid + "连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (MovingTargetsServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        this.status = "error";
        try {
            log.error("客户端断开链接,链接关闭");
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (MovingTargetsServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 比较时间是否在区间内
     *
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static synchronized void subOnlineCount() {
        MovingTargetsServer.onlineCount--;
    }

    private static synchronized int getOnlineCount() {
        return MovingTargetsServer.onlineCount;
    }

    private static synchronized void addOnlineCount() {
        MovingTargetsServer.onlineCount++;
    }
}
