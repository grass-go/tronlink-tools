package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class TagCase {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.获取用户打的标签
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "获取用户打的标签")
    public void getTagTest() {
        int limit = 20;
        String user_address = "TKpkpvcGriyNoiqhfBMLBGehmyXcDkDtER";
        Map<String, String> params = new HashMap<>();
        params.put("user_address", user_address);
        params.put("start","0");
        params.put("limit",String.valueOf(limit));
        response = TronscanApiList.getTagTest(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        //contract_map
        Assert.assertTrue(targetContent.containsKey("contract_map"));
        //user_tags
        responseArrayContent = targetContent.getJSONArray("user_tags");
        Assert.assertTrue(responseArrayContent.size() > 0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("tag").toString().isEmpty());
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("userAddress")).matches());
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("targetAddress")).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("description"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("dateCreated"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("dateUpdated"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("addressTag"));
        }
    }

    /**
     * constructor.推荐标签
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "推荐标签")
    public void getTagRecommend() {
        int limit = 20;
        String user_address = "TDaVYpgwV1cBuM2p4byRpgZrBmnoLUqE1n";
        String target_address = "TXTZtfVD7ghfjSfVrnPwDFowoYXaV6cp9Y";
        Map<String, String> params = new HashMap<>();
        params.put("user_address", user_address);
        params.put("target_address",target_address);
        response = TronscanApiList.getTagRecommend(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        //recommend_tags
        responseArrayContent = targetContent.getJSONArray("recommend_tags");
        Assert.assertTrue(responseArrayContent.size() > 0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("number").toString().isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("tag"));
        }
    }
    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
