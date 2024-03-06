package cn.bugstack.ai.zsxq.service;

import cn.bugstack.ai.zsxq.IZsxqApi;
import cn.bugstack.ai.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.bugstack.ai.zsxq.model.req.AnswerReq;
import cn.bugstack.ai.zsxq.model.req.ReqData;

import cn.bugstack.ai.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.io.IOException;

@Service
public class ZsxqApi implements IZsxqApi {
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnansweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie",cookie);

        get.addHeader("Content-Type","application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据，groupId:{}  jsonStr:{}",groupId,jsonStr);
            System.out.println(jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);


        }else {
            throw new RuntimeException("queryUnAnsweredQusetionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }

    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId,String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+ topicId +"/answer");

        post.addHeader("cookie",cookie);

        post.addHeader("Content-Type","application/json;charset=utf8");

        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.36");

//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"没有优势\\n\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"silenced\": true\n" +
//                "  }\n" +
//                "}";

        AnswerReq answerReq = new AnswerReq(new ReqData(text,silenced));
        String paramJson = JSONObject.toJSONString(answerReq);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答星球问题结果，groupId:{} topicId:{} jsonStr:{}",groupId,topicId,jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();


        }else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }

    }
}
