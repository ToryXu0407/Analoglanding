package netgloo.controllers.data;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static netgloo.controllers.util.Base64Encrypt.base64Encode;

/*
    爬取中国东方航空数据。
 */
public class ShenHangOperate {
    public String Beforelogin() throws IOException {
        String baseURL = "https://easternmiles.ceair.com/mpf/register/getNumberCaptcha?";
        String time = System.currentTimeMillis()+"";
        baseURL+=time;
        System.out.println("time"+time);
        Document doc = Jsoup.connect(baseURL).ignoreContentType(true).get();
        String docc = doc.body().html();
        InputStream inputStream   =   new ByteArrayInputStream(docc.getBytes());
        String body = doc.toString();
        return body;
    }
    public String login(String loginName, String pwd, String captcha) throws IOException {
        String baseURL = "https://easternmiles.ceair.com/mpf/app/view/sign-in.html?";
        pwd = base64Encode(pwd.getBytes());
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Host", "skypearl.csair.com");
        connection.header("Referer", "https://easternmiles.ceair.com/mpf/app/view/sign-in.html");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        Connection.Response Response = null;
        Map<String, String> datas = new HashMap<>(16);
        datas.put("captcha",captcha);
        datas.put("loginName",loginName);
        datas.put("pwd", pwd);
        datas.put("loginType", "MOBILE");
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).execute();
            body = Response.body();
            System.out.println(body);
            //JSONObject jsonpObject = JSON.parseObject(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
    public static void main(String[] args) throws Exception {
        ShenHangOperate shenHangOperate = new ShenHangOperate();
        //String body = shenHangOperate.Beforelogin();
        String time = System.currentTimeMillis()+"";
        System.out.println("time"+time);
        String body2 = shenHangOperate.login("15858259121","049707","6");
        System.out.println(body2);
    }
}
