package cn.bugstack.ai.zsxq;


import cn.bugstack.ai.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// API接口
public interface IZsxqApi {


    UnAnsweredQuestionsAggregates queryUnansweredQuestionsTopicId(String groupId, String cookie) throws IOException;


    boolean answer(String groupId,String cookie,String topidId,String text,boolean silenced) throws IOException;

}
