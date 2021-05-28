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
public class MappingByMainchainAddress {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.根据主链地址获取侧链地址
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "根据主链地址获取侧链地址")
    public void getByMainchain() {
        String mainchainAddress = "TTT2UVAa28scLpN6zmJUGeJNWV3kSLScKN";
        Map<String, String> params = new HashMap<>();
        params.put("mainchainAddress", mainchainAddress);
        response = TronscanApiList.getMappingByMainchainAddress(tronScanNode,params);
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
        responseArrayContent = targetContent.getJSONArray("sidechains");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        Assert.assertTrue(responseObject.containsKey("chainid"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("mainchainGateway")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("sidechainGateway")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("mainchainAddress")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("sidechainAddress")).matches());
        Assert.assertTrue(responseObject.containsKey("chainName"));

    }

    /**
     * constructor.根据侧链地址获取主链地址
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "根据侧链地址获取主链地址")
    public void getBySidechain() {
        String sidechainAddress = "TM9d82tZmUzWFrM7C8wMjcXYPNhxB8zVtR";
        Map<String, String> params = new HashMap<>();
        params.put("sidechainAddress", sidechainAddress);
        response = TronscanApiList.getBySidechain(tronScanNode,params);
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
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(targetContent.getString("mainchainAddress")).matches());

    }
    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
