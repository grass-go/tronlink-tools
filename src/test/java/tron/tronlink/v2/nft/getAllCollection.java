package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class getAllCollection extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();
  Map<String, Nft> NftContract = new HashMap<>();
  {
    NftContract.put("1", new Nft("SoccerCards", "card", "TPvGT3tWUNakTg23ARKMx46MGLT386nYWD"));
    NftContract.put("34", new Nft("JustNFT", "JNFT", "TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG"));
  }

  @Test
  public void getAllCollectionTest001(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","1A8%2FhWTN%2F8g7qKv7vqUdIYKVjJU%3D");
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2GetAllCollection(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONArray("data");
    Assert.assertTrue(dataContent.size() >= 1);
    for (int n = 0; n < dataContent.size(); n++ ){
      JSONObject nftData = dataContent.getJSONObject(n);

      Assert.assertTrue(nftData.containsKey("transferCount"));
      Assert.assertTrue(nftData.containsKey("nrOfTokenHolders"));
      Nft Expect = NftContract.get(nftData.getString("id"));


      Assert.assertNotNull(Expect);
      Assert.assertEquals(Expect.name, nftData.getString("name"));
      Assert.assertEquals(Expect.shortName, nftData.getString("shortName"));
      Assert.assertEquals(Expect.ContractAddress, nftData.getString("contractAddress"));

      Assert.assertEquals(200,
          TronlinkApiList.createGetConnect(nftData.getString("logoUrl")).getStatusLine().getStatusCode());

    }
    System.out.println("responseContent : " + responseContent.toString());



  }


  class Nft {
    String name;
    String shortName;
    String ContractAddress;

    public Nft(String name, String shortName, String ContractAddress){
      this.name = name;
      this.shortName = shortName;
      this.ContractAddress = ContractAddress;
    }
  }

}
