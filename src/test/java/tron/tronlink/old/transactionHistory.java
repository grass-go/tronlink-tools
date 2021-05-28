package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class transactionHistory extends TronlinkBase {
  private HttpResponse response;
  private String defaultHash = "bf1da4cac95c7edc0b219fed147460fc32eacc47736728a70710a76d8ce0bec2";

  @Test(enabled = true, description = "Api GET /api/simple-transaction test")
  public void test001TransactionTo() throws Exception {
    HashMap<String,String> parameter = new HashMap<>();
    parameter.put("to", "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");
    response = api.transactionHistory(parameter);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);

    JSONArray data = jsonObject.getJSONArray("data");
    for (Object object:data){
      JSONObject history = (JSONObject) JSON.toJSON(object);
      Assert.assertTrue(!history.getString("hash").isEmpty());
      Assert.assertTrue(!history.getString("timestamp").isEmpty());
      Assert.assertTrue(!history.getString("ownerAddress").isEmpty());
      Assert.assertTrue(!history.getString("toAddress").isEmpty());
      Assert.assertTrue(!history.getString("contractType").isEmpty());
//      String toAddress = history.getString("toAddress");
//      Assert.assertEquals("TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of",toAddress);
      String hash = history.getString("hash");

      if(defaultHash.equals(hash)){
        JSONObject contractData = history.getJSONObject("contractData");
        Assert.assertEquals("10000000",contractData.getString("amount"));
        Assert.assertEquals("1002636",contractData.getString("asset_name"));
        Assert.assertEquals("THvoHCwueb9WHMdiVk5M2kYd9HUKVdh6wv",contractData.getString("owner_address"));
        Assert.assertEquals("TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of",contractData.getString("to_address"));
      }
    }


  }

  @Test(enabled = true)
  public void test002TransactionFrom() throws Exception {
    HashMap<String, String> parameter = new HashMap<>();
    parameter.put("from", "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");
    response = api.transactionHistory(parameter);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);

    JSONArray data = jsonObject.getJSONArray("data");
    for (Object object : data) {
      JSONObject history = (JSONObject) JSON.toJSON(object);
      Assert.assertTrue(!history.getString("hash").isEmpty());
      Assert.assertTrue(!history.getString("timestamp").isEmpty());
      Assert.assertTrue(!history.getString("ownerAddress").isEmpty());
//      Assert.assertTrue(!history.getString("toAddress").isEmpty());
      Assert.assertTrue(!history.getString("contractType").isEmpty());
      Assert.assertEquals(history.getString("ownerAddress"), "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");
    }

  }

  @Test(enabled = true)
  public void test003TransactionAll() throws Exception {
    HashMap<String, String> parameter = new HashMap<>();
    parameter.put("address", "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");
    response = api.transactionHistory(parameter);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);

    JSONArray data = jsonObject.getJSONArray("data");
    for (Object object : data) {
      JSONObject history = (JSONObject) JSON.toJSON(object);
      Assert.assertTrue(!history.getString("hash").isEmpty());
      Assert.assertTrue(!history.getString("timestamp").isEmpty());
      Assert.assertTrue(!history.getString("ownerAddress").isEmpty());
//      Assert.assertTrue(!history.getString("toAddress").isEmpty());
      Assert.assertTrue(!history.getString("contractType").isEmpty());
    }

  }

}

