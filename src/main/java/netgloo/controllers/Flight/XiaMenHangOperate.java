package netgloo.controllers.Flight;

import netgloo.controllers.util.Util;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/*
    爬取厦门航空数据,获得获得图形验证并获得cookies,前端页面输入账号密码验证码，根据该cookies去进行登陆。
 */
@Component
public class XiaMenHangOperate {
    private Map<String, String> cookies;
    private String path = XiaMenHangOperate.class.getResource("/").getPath().replaceAll("%20", " ") + "xmsafecode.png";
    public Map<String,String> getSafeCode(Map<String,String> cookies) throws IOException {
        String url = "https://uia.xiamenair.com/external/api/v1/oauth2/captcha?r";
        Double random = Math.random();
        url+=random;
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).cookies(cookies).execute();
        //cookies.putAll(response.cookies());
        byte[] bytes = response.bodyAsBytes();
        Util.saveFile(path, bytes);
        System.out.println("保存验证码到：" + path);
        return cookies;
    }
    public Map<String,String> getPageToken() throws IOException {
        String url = "https://uia.xiamenair.com/external/api/v1/oauth2/authorize?scope=user&response_type=code&client_id=PCWEB&redirect_uri=https://new.xiamenair.com/api/users/ssocallback";
        Double random = Math.random();
        url+=random;
        Connection.Response Response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).execute();
        Document doc = Jsoup.parse(Response.body());
        Map<String,String>  cookies = Response.cookies();
        Elements elements = doc.select("[class=pageToken]");
        String pageToken = elements.get(0).attr("value");
        cookies.put("pageToken",pageToken);
        return cookies;
    }
    public Map<String,String> login(String account,String password,String code,Map<String,String>cookies) {
        String baseURL = "https://uia.xiamenair.com/external/api/v1/oauth2/verify";
        String pageToken = cookies.get("pageToken");
        System.out.println("cookies里拿的pagetoken:"+pageToken);
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "135");
        connection.header("Content-Type", "application/json");
        connection.header("Host", "uia.xiamenair.com");
        connection.header("Origin", "https://uia.xiamenair.com");
        connection.header("Referer", "https://uia.xiamenair.com/external/api/v1/oauth2/authorize?scope=user&response_type=code&client_id=PCWEB&redirect_uri=https://shop.xiamenair.com/loginrm.aspx?ReturnUrl=https://shop.xiamenair.com/user/UserDefault.aspx/api/users/ssocallback");
        connection.header("X-Requested-With", "XMLHttpRequest");
        Connection.Response Response = null;
        Map<String, String> datas = new HashMap<>(16);
        datas.put("account",account);
        datas.put("password",password);
        datas.put("code",code);
        datas.put("pageToken",pageToken);
        datas.put("type","1");
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).data(datas).cookies(cookies).execute();
            body = Response.body();
            cookies.putAll(Response.cookies());
            System.out.println(body);
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
        XiaMenHangOperate xiaMenHangOperate = new XiaMenHangOperate();
        xiaMenHangOperate.getPageToken();
    }
}
