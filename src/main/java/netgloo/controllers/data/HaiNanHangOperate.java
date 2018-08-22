package netgloo.controllers.data;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
    爬取海南航空数据。
 */
public class HaiNanHangOperate {
    public String Beforelogin() throws IOException {
        String baseURL = "https://easternmiles.ceair.com/mpf/register/getNumberCaptcha?";
        String time = System.currentTimeMillis()+"";
        baseURL+=time;
        System.out.println("time"+time);
        Document doc = Jsoup.connect(baseURL).ignoreContentType(true).get();
        String docc = doc.body().html();
        InputStream inputStream   =   new ByteArrayInputStream(docc.getBytes());
        File file = null;
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        inputStream.close();
        Elements elements = doc.select("img");
        System.out.println(elements);
        String body = doc.toString();
        return body;
    }
    public String login(String userName,String login_pwd,String validCode_login) throws IOException {
        String baseURL = "https://easternmiles.ceair.com/mpf/sign/signIn_CN?locale=cn";
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        Connection.Response Response = null;
        Map<String, String> datas = new HashMap<>(16);
        datas.put("validCode_login", "validCode_login");
        datas.put("userName", userName);
        datas.put("login_pwd", login_pwd);
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
        HaiNanHangOperate haiNanHangOperate = new HaiNanHangOperate();
        String body = haiNanHangOperate.Beforelogin();
        String time = System.currentTimeMillis()+"";
        System.out.println("time"+time);
        String body2 = haiNanHangOperate.login("15858259121","049707","63");
        System.out.println(body);
    }
}
