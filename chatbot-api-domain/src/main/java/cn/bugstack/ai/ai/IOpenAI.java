package cn.bugstack.ai.ai;


import java.io.IOException;

// ChatGPT openai 接口
public interface IOpenAI {
    String doChatGPT(String question) throws IOException;
}
