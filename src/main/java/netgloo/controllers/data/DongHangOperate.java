package netgloo.controllers.data;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

/*
    爬取中国东方航空数据。
 */
public class DongHangOperate {
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
    public String login(String memberId, String password, String code) throws IOException {
        String baseURL = "https://easternmiles.ceair.com/mpf/app/view/sign-in.html?";
        baseURL+="memberId="+memberId+"&password="+password+"&code="+code;
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
//        Map<String, String> datas = new HashMap<>(16);
//        datas.put("memberId", memberId);
//        datas.put("password", password);
//        datas.put("code", "code");
        String body = null;
        try {
            Response = connection.method(Connection.Method.GET).execute();
            body = Response.body();
            System.out.println(body);
            //JSONObject jsonpObject = JSON.parseObject(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
    public static void main(String[] args) throws Exception {
        DongHangOperate dongHangOperate = new DongHangOperate();
        String body = dongHangOperate.Beforelogin();
        String time = System.currentTimeMillis()+"";
        System.out.println("time"+time);
        String body2 = dongHangOperate.login("15858259121","079707","63");
        System.out.println(body);
    }
}
