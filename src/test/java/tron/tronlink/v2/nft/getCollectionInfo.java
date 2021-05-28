package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
//import tron.tronlink.base.GetSign;
import tron.tronlink.base.TronlinkBase;

public class getCollectionInfo extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();

  @Test(enabled = true)
  @Parameters({"trc721OwnerAddress","trc721TokenAddress","trc721AssetId"})
  public void getCollectionInfoTest001(String trc721OwnerAddress,String trc721TokenAddress,String trc721AssetId) throws Exception{
    HashMap<String,String> signatureMap = new HashMap<>();
    signatureMap.put("address", trc721OwnerAddress);
    signatureMap.put("url", "/api/wallet/nft/getCollectionInfo");
    String signature = getSign(signatureMap);
    System.out.println(signature);



    params.put("nonce",nonce);
    params.put("secretId",secretId);
    params.put("signature",signature);
    params.put("address",trc721OwnerAddress);
    params.put("tokenAddress",trc721TokenAddress);
    params.put("assetId",trc721AssetId);

    response = TronlinkApiList.v2GetCollectionInfo(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONObject("data");

    Assert.assertTrue(dataContent.containsKey("assetId"));
    Assert.assertTrue(dataContent.containsKey("assetUri"));
    Assert.assertTrue(dataContent.containsKey("name"));

    Assert.assertEquals(params.get("tokenAddress"), dataContent.get("tokenAddress"));
    Assert.assertEquals(200, TronlinkApiList.createGetConnect(dataContent.getString("imageUrl")).getStatusLine().getStatusCode());
    Assert.assertEquals(200, TronlinkApiList.createGetConnect(dataContent.getString("logoUrl")).getStatusLine().getStatusCode());

//https://list.tronlink.org/api/wallet/nft/getCollectionInfo?tokenAddress=TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG&address=414D103F0043A4ED24B1DC97775E390CBFC2E0C45D&signature=A2epHTAV5h6STSjMQ9yC%2FReM0RA%3D&assetId=3&secretId=SFSUIOJBFMLKSJIF&nonce=12345
  }
}
