package com.ljh.manager;

import com.ljh.exception.BaseException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Ai调用
 */
@Component
public class AiManager {

    @Autowired
    private ClientV4 clientV4;


    /**
     * 通用请求 (简化消息传递)
     * @param systemMessages
     * @param userMessages
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String systemMessages, String userMessages, Boolean stream, Float temperature){
        List<ChatMessage> chatMessageList = new ArrayList<>();

        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessages);
        chatMessageList.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessages);
        chatMessageList.add(userChatMessage);

      return doRequest(chatMessageList, stream, temperature);
    }

    /**
     * 通用请求
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature){
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(stream)
                .temperature(temperature)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();


        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getData().getChoices().get(0).toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("Ai生成回答错误！");
        }
    }

}
