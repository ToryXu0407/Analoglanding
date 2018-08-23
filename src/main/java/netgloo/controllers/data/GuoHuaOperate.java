package netgloo.controllers.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
    模拟登陆国华人寿
 */
public class GuoHuaOperate {

    public Map<String,String> login(String mobile, String password) {
        Connection connection = Jsoup.connect("https://eservice.95549.cn/eservice/login.action?action=login").timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);

        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("sid","SJzVb22LfyxvLPYYT2LFgJxzQ92LnNmLlpvShp0sSKhpd6kcNXtt!376757450!-1042195375!1535016555018");
        datas.put("username", mobile);
        datas.put("password", password);
        datas.put("loginType", "1");
        Connection.Response Response = null;
        String body = null;
        String success;
        Map<String, String> cookies = null;
        try {
            Response = connection.method(Connection.Method.POST).data(datas).ignoreContentType(true).execute();
            body = Response.body();
            System.out.println(body);
            JSONObject jsonObject = JSON.parseObject(body);
            success = jsonObject.getString("code");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            if (success.equals("1")) {
                // 登陆成功后的cookie信息
                cookies = Response.cookies();
                return cookies;
            } else {
                throw new RuntimeException("抱歉，您输入的用户名或者密码有误!");
            }
    }


    public Map<String,String> Index(Map<String, String> cookies,Map<String,String> result) throws Exception {
        if(cookies.isEmpty()) return null;
        String URL = "https://eservice.95549.cn/eservice/home.do?action=index";
        Connection connection = Jsoup.connect(URL).timeout(3000);
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "0");
        connection.header("Host", "eservice.95549.cn");
        connection.header("Referer", "https://eservice.95549.cn/eservice/home.do?action=index");
        connection.header("Upgrade-Insecure-Requests", "1");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        Connection.Response Res = connection.method(Connection.Method.POST).ignoreContentType(true)
                .cookies(cookies)
                .execute();
        String body = Res.body();
        System.out.println("Res"+body);
        Document doc = Jsoup.parse(Res.body());
        Elements elements = doc.select("[class=account]");
        result.put("username",elements.get(0).text().substring(5));
        System.out.println(result);
        return result;
    }
        public Map<String,String> PointsRecord(Map<String, String> cookies) throws Exception {
            if(cookies.isEmpty()) return null;
            String URL = "https://eservice.95549.cn/eservice/points.do?action=initPointsList";
            Connection connection = Jsoup.connect(URL).timeout(3000);
            connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.header("Accept-Encoding", "gzip, deflate, br");
            connection.header("Accept-Language", "zh-CN,zh;q=0.9");
            connection.header("Connection", "keep-alive");
            connection.header("Content-Length", "0");
            connection.header("Host", "eservice.95549.cn");
            connection.header("Referer", "https://eservice.95549.cn/eservice/home.do?action=index");
            connection.header("Upgrade-Insecure-Requests", "1");
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            Connection.Response Res = connection.method(Connection.Method.POST).ignoreContentType(true)
                    .cookies(cookies)
                    .execute();
            String body = Res.body();
            System.out.println("Res"+body);
            Map<String,String> result = new HashMap<>();
            Document doc = Jsoup.parse(Res.body());
            Elements elements = doc.select("[class=b8101d fw MSYH]");
            result.put("aviliablepoints",elements.get(0).text());
            result.put("frozenpoints",elements.get(1).text());
            System.out.println(result);
            return result;
        }
    public Map<String,String> Operate() throws Exception {
        GuoHuaOperate GuoHuaOperate = new GuoHuaOperate();
        Map<String,String> cookies= GuoHuaOperate.login("15858259121","049707");
        Map<String,String> result= GuoHuaOperate.PointsRecord(cookies);
        result = GuoHuaOperate.Index(cookies,result);
        return result;
    }
        public static void main(String[] args) throws Exception {
            GuoHuaOperate GuoHuaOperate = new GuoHuaOperate();
            System.out.println(GuoHuaOperate.Operate());
        }
    }

