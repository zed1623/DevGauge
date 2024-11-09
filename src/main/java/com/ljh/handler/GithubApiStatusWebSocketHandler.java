package com.ljh.handler;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnError;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/api/websocket/{userId}")
@Component
public class GithubApiStatusWebSocketHandler {
	private static ConcurrentHashMap<String, GithubApiStatusWebSocketHandler> webSocketMap = new ConcurrentHashMap<>();
	// 实例一个 session，这个 session 是 websocket 的 session
	private Session session;

	// 新增一个方法用于主动向客户端发送消息
	public static void sendMessage(Object message, String userId) {
		GithubApiStatusWebSocketHandler webSocket = webSocketMap.get(userId);
		if (webSocket != null) {
			try {
				webSocket.session.getBasicRemote().sendText(JSONUtil.toJsonStr(message));
				System.out.println("【websocket消息】发送消息成功,用户=" + userId + ",消息内容" + message.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ConcurrentHashMap<String, GithubApiStatusWebSocketHandler> getWebSocketMap() {
		return webSocketMap;
	}

	public static void setWebSocketMap(ConcurrentHashMap<String, GithubApiStatusWebSocketHandler> webSocketMap) {
		GithubApiStatusWebSocketHandler.webSocketMap = webSocketMap;
	}

	// 前端请求时一个 websocket 时
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) {
		this.session = session;
		webSocketMap.put(userId, this);
		sendMessage("CONNECT_SUCCESS", userId);
		System.out.println("【websocket消息】有新的连接,连接id=" + userId);
	}

	// 前端关闭时一个 websocket 时
	@OnClose
	public void onClose(@PathParam("userId") String userId) {
		webSocketMap.remove(userId);
		System.out.println("【websocket消息】连接断开,总数:" + webSocketMap.size());
	}

	// 前端向后端发送消息
	@OnMessage
	public void onMessage(String message) {
		if (!message.equals("ping")) {
			System.out.println("【websocket消息】收到客户端发来的消息:" + message);
		}
	}

	// 发送 GitHub API 健康状态
	public static void sendGithubApiHealthStatus(String status, String userId) {
		GithubApiStatusWebSocketHandler webSocket = webSocketMap.get(userId);
		if (webSocket != null) {
			try {
				webSocket.session.getBasicRemote().sendText("GitHub API Health Status: " + status);
				System.out.println("【websocket消息】发送 GitHub API 状态: " + status);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 处理 WebSocket 错误
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("【websocket消息】连接出错:" + error.getMessage());
	}
}
