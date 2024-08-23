package cn.goindog.amsm.manager;

import cn.goindog.amsm.system.SystemStatus;
import com.google.gson.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ServerEndpoint("/socket")
public class ManagerWebSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerWebSocket.class);

    private Session session;
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        LOGGER.info("[ WebSocket ] WebSocket has connected");
        Thread thread = new Thread(() -> {
            while (true) {
                JsonObject object = new JsonObject();
                object.addProperty("type", "device_status");
                object.add("device_info", SystemStatus.getDeviceInfo());
                object.add("cpu_status", SystemStatus.getCPUStatus());
                object.add("memory_status", SystemStatus.getMemoryStatus());
                this.session.getAsyncRemote().sendText(object.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @OnClose
    public void onClose(CloseReason closeReason){
        LOGGER.info("[ WebSocket ] WebSocket has disconnected");
    }

    public void send(String msg) {
        this.session.getAsyncRemote().sendText(msg);
    }
}
