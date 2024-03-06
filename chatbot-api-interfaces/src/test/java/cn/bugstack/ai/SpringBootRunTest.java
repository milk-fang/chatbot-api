package cn.bugstack.ai;


import cn.bugstack.ai.ai.IOpenAI;
import cn.bugstack.ai.zsxq.IZsxqApi;
import cn.bugstack.ai.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.bugstack.ai.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;


    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openai;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnansweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果:{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();

        for(Topics topic : topics){
            String topic_id = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            logger.info("topicId:{} text: {}",topic_id,text);
            zsxqApi.answer(groupId,cookie,topic_id,text,false);
        }
    }

    @Test
    public void test_openai() throws IOException {
        String response = openai.doChatGPT("帮我写个Java快速排序");
        logger.info("测试结果：{}",response);
    }
}
