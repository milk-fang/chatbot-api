package cn.bugstack.ai;

import io.github.bonigarcia.wdm.online.HttpClient;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public class ApiTest {


    // 回答：https://api.zsxq.com/v2/topics/8855828848115882/answer
    @Test
    public void query_unanswered_question() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/48888882215428/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie","zsxq_access_token=A1CAB677-9F49-DB56-FC5C-F89089356F12_2D402ADD9507D077; zsxqsessionid=e93778f75f62579ae9d755514341bb5b; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22548488254512224%22%2C%22first_id%22%3A%2218e082bde788cf-0563853b7ccdec8-78505773-2073600-18e082bde7917ac%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fbugstack.cn%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlMDgyYmRlNzg4Y2YtMDU2Mzg1M2I3Y2NkZWM4LTc4NTA1NzczLTIwNzM2MDAtMThlMDgyYmRlNzkxN2FjIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTQ4NDg4MjU0NTEyMjI0In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22548488254512224%22%7D%2C%22%24device_id%22%3A%2218e082bde788cf-0563853b7ccdec8-78505773-2073600-18e082bde7917ac%22%7D");

        get.addHeader("Content-Type","application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);

        }else {
            System.out.println(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        }


    }


    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/5122151418418124/answer");

        post.addHeader("cookie","zsxq_access_token=A1CAB677-9F49-DB56-FC5C-F89089356F12_2D402ADD9507D077; zsxqsessionid=e93778f75f62579ae9d755514341bb5b; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22548488254512224%22%2C%22first_id%22%3A%2218e082bde788cf-0563853b7ccdec8-78505773-2073600-18e082bde7917ac%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fbugstack.cn%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlMDgyYmRlNzg4Y2YtMDU2Mzg1M2I3Y2NkZWM4LTc4NTA1NzczLTIwNzM2MDAtMThlMDgyYmRlNzkxN2FjIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTQ4NDg4MjU0NTEyMjI0In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22548488254512224%22%7D%2C%22%24device_id%22%3A%2218e082bde788cf-0563853b7ccdec8-78505773-2073600-18e082bde7917ac%22%7D");

        post.addHeader("Content-Type","application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"没有优势\\n\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);

        }else {
            System.out.println(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        }

    }
    @Test
    public void test_chatgpt() throws IOException {
        String pro = "127.0.0.1";//本机地址
        int pro1 = 7890; //代理端口号
        //创建一个 HttpHost 实例，这样就设置了代理服务器的主机和端口。
        HttpHost httpHost = new HttpHost(pro, pro1);
        //创建一个 RequestConfig 对象，然后使用 setProxy() 方法将代理 httpHost 设置进去。
        RequestConfig build = RequestConfig.custom().setProxy(httpHost).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");

        post.addHeader("Content-Type","application/json");

        post.addHeader("Authorization","Bearer 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");
        post.setConfig(build);
        String paramJson = "{\"model\": \"gpt-3.5-turbo\",\n" +
                "\"messages\": [{\"role\": \"user\", \"content\": \"帮我写一个Java冒泡排序\"}],\n" +
                "\"temperature\": 0.7}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);

        }else {
            System.out.println(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        }


    }
}
