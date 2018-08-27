package netgloo.controllers.Insurance;

import netgloo.controllers.util.Byte2ImageUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static netgloo.controllers.util.Base64Encrypt.base64Encode;

/*
    模拟登陆太平保险
 */
public class TaiPingQueryController {
    //图片保存地址
    private String path = TaiPingQueryController.class.getResource("/").getPath().replaceAll("%20", " ") + "tpsafecode.png";
    public Map<String,String> getSafeCode(Map<String,String> cookies) throws IOException {
        String url = "https://sso.cntaiping.com/sso/kaptcha/login?";
        String time = System.currentTimeMillis()+"";
        url+=time;
        Connection.Response Response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).cookies(cookies).timeout(3000).execute();
        cookies.putAll(Response.cookies());
        byte[] bytes = Response.bodyAsBytes();
        Byte2ImageUtil.saveFile(path, bytes);
        System.out.println("保存验证码到：" + path);
        System.out.println("cookies：" + cookies);
        return cookies;
    }
    public Map<String,String> CheckCaptcha(Map<String,String> cookies,String captcha) throws IOException {
        String url = "https://sso.cntaiping.com/sso/register/registerCheckKaptcha";
        Map<String, String> datas = new HashMap<>(16);
        datas.put("captcha",captcha);
        Connection.Response Response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).data(datas).cookies(cookies).timeout(3000).execute();
        cookies.putAll(Response.cookies());
        System.out.println("after check body：" + Response.body());
        return cookies;
    }
    public Map<String,Map<String,String>> getFromHtml() throws IOException {
        String url = "https://sso.cntaiping.com/sso/login?time=";
        String time = System.currentTimeMillis()+"";
        url+=time;
        Connection.Response Response = Jsoup.connect(url)
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).execute();
        Document doc = Jsoup.parse(Response.body());
        Elements elements = doc.select("[name=geolocation]");
        String geolocation = elements.get(0).attr("value");
        elements = doc.select("[name=execution]");
        String execution = elements.get(0).attr("value");
        elements = doc.select("[name=_eventId]");
        String _eventId = elements.get(0).attr("value");
        elements = doc.select("[name=agrFlag]");
        String agrFlag = elements.get(0).attr("value");
        elements = doc.select("[name=loginType]");
        String loginType = elements.get(0).attr("value");
        Map<String,String> values = new HashMap<>();
        Map<String,String> cookies = new HashMap<>();
        cookies.putAll(Response.cookies());
        System.out.println("htmlcookies:"+Response.cookies());
        values.put("geolocation",geolocation);
        values.put("execution",execution);
        values.put("_eventId",_eventId);
        values.put("agrFlag",agrFlag);
        values.put("loginType",loginType);
        Map<String,Map<String,String>> result = new HashMap<>();
        result.put("cookies",cookies);
        result.put("values",values);
        return result;
    }
    public Map<String,String> login(Map<String,Map<String,String>> result,String mobile,String password,String captcha) {
        Connection connection = Jsoup.connect("https://sso.cntaiping.com/sso/login")
                .timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);
        Map<String,String> cookies = result.get("cookies");
        Map<String,String> values = result.get("values");
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Cache-Control", "max-age=0");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Host", "sso.cntaiping.com");
        connection.header("Origin", "https://sso.cntaiping.com");
        connection.header("Referer", "https://sso.cntaiping.com/sso/login");
        connection.header("Upgrade-Insecure-Requests", "1");
        String geolocation = values.get("geolocation");
        String execution = values.get("execution");
        String _eventId = values.get("_eventId");
        String agrFlag = values.get("agrFlag");
        String loginType = values.get("loginType");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("username", base64Encode(mobile.getBytes()));
        datas.put("password", base64Encode(password.getBytes()));
        datas.put("captcha", captcha);
        datas.put("agrFlag", agrFlag);
        datas.put("_eventId",_eventId);
        datas.put("geolocation", geolocation);
        datas.put("execution",execution);
        datas.put("loginType",loginType);
        System.out.println(datas);
        Connection.Response Response = null;
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).data(datas).followRedirects(false).cookies(cookies).execute();
            cookies.putAll(Response.cookies());
            System.out.println("cookies:"+Response.cookies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cookies;
    }
    public Map<String,String> travel(Map<String,String> cookies) {
        Connection connection = Jsoup.connect("https://sso.itaiping.com/sso/travel?_backurl=http%3A%2F%2Fsso.cntaiping.com%2Fsso%2Flogin%3Fservice%3Dhttp%253A%252F%252Fmy.cntaiping.com%252FloginSuccess.action&_t=&_u=7f1b3ad1" +
                "-cac1-49f2-a610-a6b02f7b412c&lgn_ur=073f82942442931ccd9642a53e2a4e3d031aa5f2582284cdeb500d4bf65eb773")
                .timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Cache-Control", "max-age=0");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Host", "sso.cntaiping.com");
        connection.header("Referer", "https://sso.cntaiping.com/sso/login?service=http%3A%2F%2Fmy.cntaiping.com%2FloginSuccess.action&_source_target=center");
        connection.header("Upgrade-Insecure-Requests", "1");
        Connection.Response Response = null;
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).followRedirects(false).cookies(cookies).execute();
            cookies.putAll(Response.cookies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cookies;
    }
    public Map<String,String> login2(Map<String,String> cookies) {
        Connection connection = Jsoup.connect("https://sso.cntaiping.com/sso/login?service=http%3A%2F%2Fmy.cntaiping.com%2FloginSuccess.action")
                .timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Cache-Control", "max-age=0");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Host", "sso.cntaiping.com");
        connection.header("Origin", "https://sso.cntaiping.com");
        connection.header("Referer", "https://sso.cntaiping.com/sso/login");
        connection.header("Upgrade-Insecure-Requests", "1");
        Connection.Response Response = null;
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).followRedirects(false).cookies(cookies).execute();
            cookies.putAll(Response.cookies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cookies;
    }
    public Map<String,String> loginsuccess(Map<String,String> cookies) {
        Connection connection = Jsoup.connect("http://my.cntaiping.com/loginSuccess.action")
                .timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Cache-Control", "max-age=0");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Host", "sso.cntaiping.com");
        connection.header("Origin", "https://sso.cntaiping.com");
        connection.header("Referer", "https://sso.cntaiping.com/sso/login");
        connection.header("Upgrade-Insecure-Requests", "1");
        Connection.Response Response = null;
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).followRedirects(false).cookies(cookies).execute();
            cookies.putAll(Response.cookies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cookies;
    }
    public Map<String,String> PointsRecord(Map<String, String> cookies) throws Exception {
        if(cookies.isEmpty()) return null;
        String URL = "http://my.cntaiping.com/index.action";
        Connection connection = Jsoup.connect(URL).timeout(3000);
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Host", "wxf.cntaiping.com");
        connection.header("Upgrade-Insecure-Requests", "1");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        Connection.Response Response = connection.method(Connection.Method.GET).followRedirects(false)
                .cookies(cookies)
                .execute();
        String body = Response.body();
        System.out.println("body:"+Response.body());
        Map<String,String> result = new HashMap<>();
            Document doc = Jsoup.parse(Response.body());
            Elements hrefs = doc.select("a[href]");
            String href = hrefs.get(0).text();
            System.out.println("href:"+href);
            URL = href;
        connection = Jsoup.connect(URL).timeout(3000);
        Response = connection.method(Connection.Method.GET)
                .cookies(cookies)
                .execute();
        System.out.println("Body"+Response.body());
        return result;
    }
    public Map<String,String> Operate(String mobile,String password) throws Exception {
        TaiPingQueryController taiPingOperate = new TaiPingQueryController();
        Map<String,Map<String,String>> result= taiPingOperate.getFromHtml();
        Map<String,String> cookies = taiPingOperate.getSafeCode(result.get("cookies"));
        result.put("cookies",cookies);
        System.out.println("输入验证码");
        Scanner scan = new Scanner(System.in);
        String captcha = scan.next();
        //cookies = taiPingOperate.CheckCaptcha(cookies,captcha);
        //result.put("cookies",cookies);
        Map<String,String> cookies2= taiPingOperate.login(result,mobile,password,captcha);
        cookies2 = taiPingOperate.login2(cookies2);
        cookies2 = taiPingOperate.loginsuccess(cookies2);
        //cookies2 = taiPingOperate.travel(cookies2);
        Map<String,String> result2= taiPingOperate.PointsRecord(cookies2);
        return null;
    }
    public static void main(String[] args) throws Exception {
        TaiPingQueryController taiPingOperate = new TaiPingQueryController();
        taiPingOperate.Operate("18257428287","xkj18257428287");
    }
}