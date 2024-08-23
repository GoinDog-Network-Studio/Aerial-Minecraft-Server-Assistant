package cn.goindog.amsm;

import cn.goindog.amsm.manager.ManagerWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketExporter {
    @Bean
    public ServerEndpointExporter serverEndpointExporter (){

        ServerEndpointExporter exporter = new ServerEndpointExporter();

        // 手动注册 WebSocket 端点
        exporter.setAnnotatedEndpointClasses(ManagerWebSocket.class);

        return exporter;
    }
}
