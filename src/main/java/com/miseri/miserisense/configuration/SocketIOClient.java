package com.miseri.miserisense.configuration;

import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.controllers.dtos.response.GetSensorDataResponse;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.springframework.stereotype.Component;

@Component
public class SocketIOClient {

    private Socket socket;

    public SocketIOClient() {
        try {
            socket = IO.socket("http://localhost:4000"); // Configura la dirección y el puerto del servidor Socket.io en JavaScript

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Conexión establecida");
                }
            });

            socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Conexión cerrada");
                }
            });

            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data) {
        socket.emit("updateData", data);
    }

    public void sendListData(String data) {
        socket.emit("updateListData", data);
    }
}




