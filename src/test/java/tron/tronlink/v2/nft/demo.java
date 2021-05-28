package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.GetSign;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;

public class demo {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();

  @Test(enabled = true)
  public void printSignature() throws Exception {
    HashMap<String, String> signatureMap = new HashMap<>();

    signatureMap.put("lang", "2");
    signatureMap.put("system", "AndroidTest");
    signatureMap.put("version", "v1.0.0");
    signatureMap.put("deviceId", "1:1:1:1");
    signatureMap.put("chain", "MainChain");
    signatureMap.put("channel", "official");
    signatureMap.put("nonce", "12345");
    signatureMap.put("ts", "1609302220000");
    signatureMap.put("method", "POST");

    signatureMap.put("address", "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
    signatureMap.put("url", "/api/wallet/v2/addAsset");

    String signature = getSign(signatureMap);
    System.out.println("============= Below is signature =============");
    System.out.println(signature);
  }

  public String getSign(HashMap<String,String> paramMap) throws Exception{
    GetSign getSign = new GetSign();
    return URLEncoder.encode(getSign.getSignature(
            paramMap.containsKey("channel") ? paramMap.get("channel") : "official",
            paramMap.containsKey("chain") ? paramMap.get("chain") : "MainChain",
            paramMap.containsKey("lang") ? paramMap.get("lang") : "1",
            paramMap.get("address"),
            paramMap.containsKey("nonce") ? paramMap.get("nonce") : "12345",
            paramMap.containsKey("secretId") ? paramMap.get("secretId") : "SFSUIOJBFMLKSJIF",
            paramMap.containsKey("system") ? paramMap.get("system") : "AndroidTest",
            paramMap.containsKey("deviceId") ? paramMap.get("deviceId") : "1:1:1:1",
            paramMap.containsKey("ts") ? paramMap.get("ts") : "1609302220000",
            paramMap.containsKey("version") ? paramMap.get("version") : "v1.0.0",
            paramMap.get("url"),
            paramMap.containsKey("method") ? paramMap.get("method") : "GET"));
  }

}
