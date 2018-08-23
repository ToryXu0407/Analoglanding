package netgloo.controllers.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    爬取中国南方航空，登陆后获得数据，返回两个JSONObject，通过解析获得想要的属性值，放到Map。
 */
@Component
public class NanHangOperate {
    Map<String,String> cookies = null;
    public JSONObject login(String mobile, String password) {
        Connection connection = Jsoup.connect("https://b2c.csair.com/portal/user/login").timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);

        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("userId", mobile);
        datas.put("passWord", password);
        datas.put("loginType", "1");
        datas.put("memberType","1");
        Connection.Response smsResponse = null;
        String body = null;
        Boolean success;
        try {
            smsResponse = connection.method(Connection.Method.POST).data(datas).execute();
            System.out.println(body);
            JSONObject jsonpObject = JSON.parseObject(body);
            success = jsonpObject.getBoolean("success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (success) {
            // 登陆成功后的cookie信息
            cookies = smsResponse.cookies();
            JSONObject jsonObject = JSON.parseObject(body).getJSONObject("data");
            return jsonObject;
        } else {
                throw new RuntimeException("抱歉，您输入的用户名或者密码有误!");
            }
        }
    public JSONObject serviceRecord(Map<String, String> cookies) throws Exception {
       if(cookies.isEmpty()) return null;
        String URL = "https://skypearl.csair.com/skypearl/cn/queryMileageAndTierAction.action";
                Connection connection = Jsoup.connect(URL).timeout(3000);
                connection.header("Accept", "application/json, text/javascript, */*");
                connection.header("Accept-Encoding", "gzip, deflate, br");
                connection.header("Accept-Language", "zh-CN,zh;q=0.9");
                connection.header("Connection", "keep-alive");
                connection.header("Content-Length", "0");
                connection.header("Host", "skypearl.csair.com");
                connection.header("Origin", "https://skypearl.csair.com");
                connection.header("Referer", "https://skypearl.csair.com/skypearl/cn/tomemberArea.action?urlredirect=commRecord3");
                connection.header("X-Requested-With", "XMLHttpRequest");
                connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
                Connection.Response isTierRes = connection.method(Connection.Method.POST).ignoreContentType(true)
                        .cookies(cookies)
                        .execute();
                String body = isTierRes.body();
                JSONObject jsonObject = JSON.parseObject(body).getJSONObject("attachment");
                return jsonObject;
    }
    public Map<String,String> Operate() throws Exception {
        Map<String,String> map = new HashMap<>();
        NanHangOperate NanHangOperate = new NanHangOperate();
        JSONObject jsonObjectLogin = NanHangOperate.login("15858259121","049707");
        JSONObject jsonObjectMileage = NanHangOperate.serviceRecord(NanHangOperate.cookies);
        map.put("userName", (String) jsonObjectLogin.get("userName"));
        map.put("memberNo", (String) jsonObjectMileage.get("memberNo"));
        map.put("membersLevel", (String) jsonObjectLogin.get("membersLevel"));
        map.put("canOverdraftLimit", (String) jsonObjectMileage.get("canOverdraftLimit"));
        map.put("needMileage", jsonObjectMileage.getBigDecimal("needMileage")+"");
        map.put("needSegment",  jsonObjectMileage.getBigDecimal("needSegment")+"");
        map.put("upgradeMileage",  jsonObjectMileage.getBigDecimal("upgradeMileage")+"");
        map.put("upgradeSegment",  jsonObjectMileage.getBigDecimal("upgradeSegment")+"");
        return map;
    }
    public static void main(String[] args) throws Exception {
        NanHangOperate nanHangOperate = new NanHangOperate();
        System.out.println(nanHangOperate.Operate());
    }
}
