<template>
  <div class="chat-container">
    <div class="chat-messages">
      <!-- 聊天记录消息，包括初始消息 -->
      <div
        v-for="(message, index) in chatHistory"
        :key="index"
        class="chat-message"
      >
        <div :class="['message-wrapper', message.sender]">
          <div class="avatar">
            <img
              :src="message.sender === 'user-message' ? userAvatar : aiAvatar"
              alt="avatar"
            />
          </div>
          <div :class="['message-bubble', message.sender]">
            <p>{{ message.text }}</p>
          </div>
        </div>
      </div>

      <div v-if="isLoading" class="loading-message">
        <p>AI 正在输入...</p>
      </div>
    </div>
    <div class="input-container">
      <input
        type="text"
        v-model="userInput"
        @keyup.enter="sendMessage"
        placeholder="输入内容..."
      />
      <button @click="sendMessage">发送</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import userAvatar from "@/assets/images/user.png";
import aiAvatar from "@/assets/images/ai.png";

const initialResponse = ref("欢迎使用AI聊天演示！这是一个自动生成的初始分析。");
const userInput = ref("");
const chatHistory = ref([]);
const isLoading = ref(false);

onMounted(() => {
  // 在 mounted 阶段，将初始消息添加到 chatHistory 中
  chatHistory.value.push({ text: initialResponse.value, sender: "ai-message" });
});

const sendMessage = () => {
  if (userInput.value.trim() === "") return;

  // 添加用户消息到聊天记录中
  chatHistory.value.push({ text: userInput.value, sender: "user-message" });
  userInput.value = "";

  // 显示加载样式
  isLoading.value = true;

  // 模拟 AI 的回复，延迟一秒
  setTimeout(() => {
    const aiResponse = "好的";
    chatHistory.value.push({ text: aiResponse, sender: "ai-message" });
    isLoading.value = false;
  }, 1000);
};
</script>

<style scoped lang = "scss">
.chat-container {
  width: 80%;
  max-width: 600px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  margin-top: 50px;
}

.chat-messages {
  flex-grow: 1;
  padding: 20px;
  overflow-y: auto;
}

.message-wrapper {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;

  &.user-message {
    flex-direction: row-reverse; // 用户消息右对齐，头像在右
  }
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden; // 确保图片不超出圆形
  margin: 0 10px; // 确保头像与消息框之间有距离

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.message-bubble {
  max-width: 60%;
  padding: 10px;
  border-radius: 15px;
  word-wrap: break-word;
}

.message-bubble.user-message {
  background-color: #e0f7fa;
  color: #333;
}

.message-bubble.ai-message {
  background-color: #1a73e8;
  color: #fff;
}

.loading-message {
  text-align: left;
  color: #999;
  font-style: italic;
}

.input-container {
  display: flex;
  border-top: 1px solid #ddd;
  padding: 10px;
}

input[type="text"] {
  flex-grow: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  margin-left: 10px;
  padding: 10px 15px;
  background-color: #1a73e8;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: #155ab4;
  }
}
</style>



