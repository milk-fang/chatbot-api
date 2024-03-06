package cn.bugstack.ai.ai.service;

import cn.bugstack.ai.ai.IOpenAI;

import java.io.IOException;
import java.util.List;

import cn.bugstack.ai.ai.model.aggregates.AIAnswer;
import cn.bugstack.ai.ai.model.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class OpenAI implements IOpenAI {

    @Value("${chatbot-api.openAIKey}")
    private String openAIKey;

    private Logger logger = LoggerFactory.getLogger(OpenAI.class);
    @Override
    public String doChatGPT(String question) throws IOException {
        String pro = "127.0.0.1";//本机地址
        int pro1 = 7890; //代理端口号
        //创建一个 HttpHost 实例，这样就设置了代理服务器的主机和端口。
        HttpHost httpHost = new HttpHost(pro, pro1);
        //创建一个 RequestConfig 对象，然后使用 setProxy() 方法将代理 httpHost 设置进去。
        RequestConfig build = RequestConfig.custom().setProxy(httpHost).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");

        post.addHeader("Content-Type","application/json");

        post.addHeader("Authorization","Bearer " + openAIKey);
        post.setConfig(build);
        String paramJson = "{\"model\": \"gpt-3.5-turbo\",\n" +
                "\"messages\": [{\"role\": \"user\", \"content\": \""+ question+"\"}],\n" +
                "\"temperature\": 0.7}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("jsonStr:{}",jsonStr);
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for(Choices choice : choices){
                answers.append(choice.getMessage().getContent());
            }

            return answers.toString();

        }else {
            //System.out.println(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
            throw new RuntimeException("api.openai.com ERR Code is " + response.getStatusLine().getStatusCode());
        }

    }
}
