package netgloo.controllers.data;

import netgloo.controllers.util.Util;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static netgloo.controllers.util.string2FileUtil.string2File;


/*
    爬取海南航空数据。
 */
@Component
public class HaiNanHangOperate {
    private static Map<String, String> cookies;
    private String path = HaiNanHangOperate.class.getResource("/").getPath().replaceAll("%20", " ") + "safecode.png";
    public Map<String,String> getSafeCode() throws IOException {
        String url = "https://ffp.hnair.com/FFPClub/imgcode.do";
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).execute();
        cookies = response.cookies();
        byte[] bytes = response.bodyAsBytes();
        Util.saveFile(path, bytes);
        System.out.println("保存验证码到：" + path);
        return cookies;
    }
    public String getEncryptKey() throws IOException {
        String baseURL = "https://ffp.hnair.com/FFPClub/member/memberFindAesKey";
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        Connection.Response Response = null;
        String body = null;
            Response = connection.method(Connection.Method.POST).cookies(cookies).data(datas).execute();
            body =Response.body();
            //cookies.putAll(Response.cookies());
        return body;
    }
    public String validate(String validCode_login,Map<String,String>cookie) throws IOException {
        String baseURL = "https://ffp.hnair.com/FFPClub/imgcode.do?r0.7303532457942747";
        //Double random = Math.random();
        //baseURL+=random;
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "7");
        connection.header("Content-Type", "application/json;charset=UTF-8");
        connection.header("Host", "ffp.hnair.com");
        connection.header("Origin", "https://ffp.hnair.com");
        connection.header("Referer", "https://ffp.hnair.com/FFPClub/member/user/main?cp_d=1");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("X-Requested-With", "XMLHttpRequest");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("vc",validCode_login);
        Connection.Response Response = null;
        String body = null;
        Response = connection.method(Connection.Method.POST).cookies(cookie).data(datas).execute();
        body =Response.body();
        //cookies.putAll(Response.cookies());
        System.out.println(body);
        System.out.println(Response.cookies());
        return body;
    }
    public String login(String userName,String login_pwd,String validCode_login) {
        String baseURL = "https://ffp.hnair.com/FFPClub/member/loginIdx";
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "80");
        connection.header("Content-Type", "application/json;charset=UTF-8");
        connection.header("Host", "ffp.hnair.com");
        connection.header("Origin", "https://ffp.hnair.com");
        connection.header("Referer", "https://ffp.hnair.com/FFPClub/member/user/main?cp_d=1");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("X-Requested-With", "XMLHttpRequest");
        Connection.Response Response = null;
        Map<String, String> datas = new HashMap<>(16);
        datas.put("validCode_login", validCode_login);
        datas.put("userName", userName);
        datas.put("login_pwd", login_pwd);
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).data(datas).cookies(cookies).execute();
            body = Response.body();
            System.out.println(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body;
    }

    public static void main(String[] args) throws Exception {
        HaiNanHangOperate haiNanHangOperate = new HaiNanHangOperate();
        String username = "15858259121";
        haiNanHangOperate.getSafeCode();
        username = haiNanHangOperate.getEncryptKey();
        System.out.println("输入验证码：");
        Scanner scan = new Scanner(System.in);
        String validCode_login = scan.next();
        haiNanHangOperate.validate(validCode_login,cookies);
        String body = haiNanHangOperate.login(username,"049707",validCode_login);
//        String key = haiNanHangOperate.getEncryptKey();
//        String body = haiNanHangOperate.validate("gb68");
//        System.out.println(body);
        //String body2 = haiNanHangOperate.login("15858259121","049707","b6y4");
//        String body = haiNanHangOperate.Beforelogin();
//        String time = System.currentTimeMillis()+"";
//        System.out.println("time"+time);
//        String body2 = haiNanHangOperate.login("15858259121","049707","63");
//        System.out.println(body);
    }
}
