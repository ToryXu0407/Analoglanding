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

/*
    模拟登陆新华保险 getSafeCode->getFromHtml->Login
 */
public class XinHuaQueryController {
    //图片保存地址
    private String path = XinHuaQueryController.class.getResource("/").getPath().replaceAll("%20", " ") + "xhsafecode.png";
    public Map<String,String> getSafeCode(Map<String,String> cookies) throws IOException {
        String url = "http://oauth.e-nci.com/captcha.jpg?";
        Double random = Math.random();
        url+=random;
        Connection.Response Response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).cookies(cookies).execute();
        cookies.putAll(Response.cookies());
        byte[] bytes = Response.bodyAsBytes();
        Byte2ImageUtil.saveFile(path, bytes);
        System.out.println("保存验证码到：" + path);
        return cookies;
    }
    public Map<String,Map<String,String>> getFromHtml() throws IOException {
        String url = "http://oauth.e-nci.com/login?service=http://jf.e-nci.com/shiro-cas";
        Connection.Response Response = Jsoup.connect(url).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).execute();
        Document doc = Jsoup.parse(Response.body());
        Elements elements = doc.select("[name=lt]");
        String lt = elements.get(0).attr("value");
        elements = doc.select("[name=execution]");
        String execution = elements.get(0).attr("value");
        elements = doc.select("[name=_eventId]");
        String _eventId = elements.get(0).attr("value");
        Map<String,String> values = new HashMap<>();
        Map<String,String> cookies = Response.cookies();
        values.put("lt",lt);
        values.put("execution",execution);
        values.put("_eventId",_eventId);
        Map<String,Map<String,String>> result = new HashMap<>();
        result.put("cookies",cookies);
        result.put("values",values);
        return result;
    }
    public Map<String,String> login(Map<String,Map<String,String> > result,String mobile, String password,String authcode) {
        Connection connection = Jsoup.connect("http://oauth.e-nci.com/login?service=http://jf.e-nci.com/shiro-cas")
                .timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);
        Map<String,String> cookies = result.get("cookies");
        Map<String,String> values = result.get("values");
        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Cache-Control", "max-age=0");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Host", "oauth.e-nci.com");
        connection.header("Origin", "http://oauth.e-nci.com");
        connection.header("Referer", "http://oauth.e-nci.com/login?service=http://jf.e-nci.com/shiro-cas");
        connection.header("Upgrade-Insecure-Requests", "1");
        String lt = values.get("lt");
        String execution = values.get("execution");
        String _eventId = values.get("_eventId");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("username", mobile);
        datas.put("password", password);
        datas.put("authcode", authcode);
        datas.put("submit", "登录");
        datas.put("_eventId",_eventId);
        datas.put("lt", lt);
        datas.put("execution",execution);
        Connection.Response Response = null;
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).data(datas).cookies(cookies).ignoreContentType(true).execute();
            cookies.putAll(Response.cookies());
            System.out.println("logged cookies:"+cookies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cookies;
    }
        public Map<String,String> PointsRecord(Map<String, String> cookies) throws Exception {
            if(cookies.isEmpty()) return null;
            String URL = "http://jf.e-nci.com/ucenter";
            Connection connection = Jsoup.connect(URL).timeout(3000);
            connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.header("Accept-Encoding", "gzip, deflate");
            connection.header("Accept-Language", "zh-CN,zh;q=0.9");
            connection.header("Connection", "keep-alive");
            connection.header("Host", "jf.e-nci.com");
            connection.header("Referer", "http://jf.e-nci.com/ucenter");
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            Connection.Response Response = connection.method(Connection.Method.GET).ignoreContentType(true)
                    .cookies(cookies)
                    .execute();
            Document doc = Jsoup.parse(Response.body());
            Elements elements = doc.select("[class=user_info fl]");
            String mobile = elements.get(0).text();
            elements = doc.select("[class=c_f96800 f21 yahei]");
            String balance = elements.get(0).text();
            String expire = elements.get(1).text();
            Map<String,String> result = new HashMap<>();
            result.put("mobile",mobile);
            result.put("expire",expire);
            result.put("balance",balance);
            return result;
        }
    public Map<String,String> Operate(String mobile,String password) throws Exception {
        XinHuaQueryController xinHuaOperate = new XinHuaQueryController();
        Map<String,Map<String,String>> result = xinHuaOperate.getFromHtml();
        Map<String,String> cookies = result.get("cookies");
        cookies = xinHuaOperate.getSafeCode(cookies);
        result.put("cookies",cookies);
        System.out.println("输入验证码");
        Scanner scan = new Scanner(System.in);
        String authcode = scan.next();
        Map<String,String> cookies2= xinHuaOperate.login(result,mobile,password,authcode);
        Map<String,String> results= xinHuaOperate.PointsRecord(cookies2);
        return results;
    }
        public static void main(String[] args) throws Exception {
            XinHuaQueryController xinHuaOperate = new XinHuaQueryController();
            Map<String,String> result = xinHuaOperate.Operate("15858259121","xjj19970407");
            System.out.println(result);
        }
    }

