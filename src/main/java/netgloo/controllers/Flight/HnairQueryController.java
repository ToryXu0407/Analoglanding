package netgloo.controllers.Flight;

import netgloo.controllers.util.Byte2ImageUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/*
    爬取海南航空数据,分两步骤,调用getSafeCode()获得图形验证并获得cookies,将图形验证显示在前端页面,
    输入账号密码验证码，前端发起请求获得AES加密KEY，再将值传回后台，并根据该cookies进行登陆。
 */
@Component
public class HnairQueryController {
    private static Map<String, String> cookies;
    private String path = HnairQueryController.class.getResource("/").getPath().replaceAll("%20", " ") + "hnsafecode.png";
    public Map<String,String> getSafeCode() throws IOException {
        String url = "https://ffp.hnair.com/FFPClub/imgcode.do?r";
        Double random = Math.random();
        url+=random;
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).execute();
        cookies = response.cookies();
        byte[] bytes = response.bodyAsBytes();
        Byte2ImageUtil.saveFile(path, bytes);
        System.out.println("保存验证码到：" + path);
        return cookies;
    }
    public String getEncryptKey(Map<String,String>cookies) throws IOException {
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
            cookies.putAll(Response.cookies());
        return body;
    }

//    public Map<String,String> validate(String validCode_login,Map<String,String>cookies) {
//        String baseURL = "https://ffp.hnair.com/FFPClub/imgcode.do?r";
//        Double random = Math.random();
//        baseURL+=random;
//        Connection connection = Jsoup.connect(baseURL).timeout(20000);
//        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
//        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
//        connection.header("Accept-Encoding", "gzip, deflate, br");
//        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
//        connection.header("Connection", "keep-alive");
//        connection.header("Content-Length", "7");
//        connection.header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        connection.header("Host", "ffp.hnair.com");
//        connection.header("Origin", "https://ffp.hnair.com");
//        connection.header("Referer", "https://ffp.hnair.com/FFPClub/member/user/main?cp_d=1");
//        connection.header("X-Requested-With", "XMLHttpRequest");
//        Connection.Response Response = null;
//        Map<String, String> datas = new HashMap<>(16);
//        datas.put("validCode_login", validCode_login);
//        String body = null;
//        try {
//            Response = connection.method(Connection.Method.POST).data(datas).cookies(cookies).execute();
//            body = Response.body();
//            System.out.println(body);
//           // cookies.putAll(Response.cookies());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return cookies;
//    }
    public Map<String,String> login(String userName,String login_pwd,String validCode_login,Map<String,String>cookies) {
        String baseURL = "https://ffp.hnair.com/FFPClub/member/loginIdx";
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "7");
        connection.header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        connection.header("Host", "ffp.hnair.com");
        connection.header("Origin", "https://ffp.hnair.com");
        connection.header("Referer", "https://ffp.hnair.com/FFPClub/member/user/main?cp_d=1");
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
            cookies.putAll(Response.cookies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cookies;
    }
    public Map<String,String> Operate(Map<String,String>cookies) {
        String baseURL = "https://ffp.hnair.com/FFPClub/member/user/accountInfo";
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "6");
        connection.header("Host", "ffp.hnair.com");
        connection.header("Upgrade-Insecure-Requests", "1");
        connection.header("Referer", "https://ffp.hnair.com/FFPClub/member/user/main?wdzh");
        connection.header("X-Requested-With", "XMLHttpRequest");
        Connection.Response Response = null;
        Map<String,String> result = new HashMap<>();
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).cookies(cookies).execute();
            body = Response.body();
            Document doc = Jsoup.parse(Response.body());
            Elements elements = doc.select("[class=f333]");
            String username = elements.get(0).text();
            username = username.substring(0,username.indexOf("先"));
            elements = doc.select("[class=red]");
            String cid = elements.get(0).text();
            String cardlevel = elements.get(1).text();
            String point = elements.get(3).text();
            String upgrade = elements.get(6).text();
            String[] upgrades = upgrade.split("/");
            String upgradeMileage = upgrades[0];
            String upgradeSegment = upgrades[1];
            String expire = elements.get(5).text();
            String uplevelpoint = elements.get(7).text();
            String uplevelsegment = elements.get(8).text();
            result.put("username",username);
            result.put("cid",cid);
            result.put("cardlevel",cardlevel);
            result.put("point",point);
            result.put("expire",expire);
            result.put("upgradeMileage",upgradeMileage);
            result.put("upgradeSegment",upgradeSegment);
            result.put("uplevelpoint",uplevelpoint);
            result.put("uplevelsegment",uplevelsegment);
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        HnairQueryController haiNanHangOperate = new HnairQueryController();
        String username = "15858259121";
        haiNanHangOperate.getSafeCode();
        username = haiNanHangOperate.getEncryptKey(cookies);
        System.out.println("输入验证码：");
        Scanner scan = new Scanner(System.in);
        String validCode_login = scan.next();
        haiNanHangOperate.login(username,"049707",validCode_login,cookies);
//        String key = haiNanHangOperate.getEncryptKey();
//        System.out.println(body);
        //String body2 = haiNanHangOperate.login("15858259121","049707","b6y4");
//        String body = haiNanHangOperate.Beforelogin();
//        String time = System.currentTimeMillis()+"";
//        System.out.println("time"+time);
//        String body2 = haiNanHangOperate.login("15858259121","049707","63");
//        System.out.println(body);
    }
}
