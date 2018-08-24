package netgloo.controllers.Flight;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;

import static netgloo.controllers.util.Base64Encrypt.base64Encode;

/*
    爬取深圳航空数据。
 */
public class ShenHangOperate {
    public Map<String,String> loginSms(String mobile) throws IOException {
        String url = "http://sf.shenzhenair.com/szffp-web/sms/sendCode";
        Connection connection = Jsoup.connect("http://sf.shenzhenair.com/szffp-web/sms/sendCode").timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);

        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "76");
        connection.header("Content-Type", "application/json;charset=UTF-8");
        connection.header("Host", "sf.shenzhenair.com");
        connection.header("Origin", "http://sf.shenzhenair.com");
        connection.header("Referer", "http://sf.shenzhenair.com/szffp-web/login/loginPage");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("X-Requested-With", "XMLHttpRequest");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("ASYNC", "true");
        datas.put("operType", "6");
        datas.put("verifyNo", mobile);
        datas.put("verifyType", "mobile");
        Connection.Response smsResponse = null;
        String body = null;
//        return response.cookies();
        //try {
            smsResponse = connection.method(Connection.Method.POST).data(datas).execute();
            body = smsResponse.body();
            System.out.println(body);
            Map<String, String> cookies = smsResponse.cookies();
            System.out.println(cookies);
            return cookies;
//        } catch (IOException e) {
//            //logger.error("登录验证码发送失败, 手机号: {}, 返回结果: {}, 原因: {}",
//            //        mobile, smsResponse, JSON.toJSONString(e));
//            throw new RuntimeException(e);
//        }
//        if ("0".equals(body)) {
//            // 登陆成功后的cookie信息
//            Map<String, String> cookies = smsResponse.cookies();
//            return cookies;
//        } else {
//            if ("1".equals(body)) {
//                throw new RuntimeException("对不起，短信随机码暂时不能发送，请一分钟以后再试！");
//            }
//            else if ("2".equals(body)) {
//                throw new RuntimeException("对不起，短信随机码已达上限，请24小时候再试！");
//            }
//            else {
//                throw new RuntimeException("登录验证码发送失败，请重试");
//            }
//        }
    }
    public Map<String, String> loginSms2(Map<String,String> cookies,String mobile) throws IOException {
        String url = "http://sf.shenzhenair.com/szffp-web/sms/gainSendCounts";
        Connection connection = Jsoup.connect(url).timeout(20000);
        // 使用ip代理
//        List<String> ipList = getProxyIpList();
//        connection = useProxy(connection, ipList);

        // ~设置header
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "76");
        connection.header("Content-Type", "application/json;charset=UTF-8");
        connection.header("Host", "sf.shenzhenair.com");
        connection.header("Host", "sf.shenzhenair.com");
        connection.header("Referer", "http://sf.shenzhenair.com/szffp-web/login/loginPage");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("X-Requested-With", "XMLHttpRequest");
        // ~请求参数
        Map<String, String> datas = new HashMap<>(16);
        datas.put("ASYNC", "true");
        datas.put("operType", "6");
        datas.put("verifyNo", mobile);
        datas.put("verifyType", "mobile");
        Connection.Response smsResponse = null;
        String body = null;
//        return response.cookies();
        //try {
        smsResponse = connection.method(Connection.Method.POST).cookies(cookies).data(datas).execute();
        body = smsResponse.body();
        System.out.println(body);
        Map<String, String> cookies2 = smsResponse.cookies();
        System.out.println(cookies2);
        return cookies2;
//        } catch (IOException e) {
//            //logger.error("登录验证码发送失败, 手机号: {}, 返回结果: {}, 原因: {}",
//            //        mobile, smsResponse, JSON.toJSONString(e));
//            throw new RuntimeException(e);
//        }
//        if ("0".equals(body)) {
//            // 登陆成功后的cookie信息
//            Map<String, String> cookies = smsResponse.cookies();
//            return cookies;
//        } else {
//            if ("1".equals(body)) {
//                throw new RuntimeException("对不起，短信随机码暂时不能发送，请一分钟以后再试！");
//            }
//            else if ("2".equals(body)) {
//                throw new RuntimeException("对不起，短信随机码已达上限，请24小时候再试！");
//            }
//            else {
//                throw new RuntimeException("登录验证码发送失败，请重试");
//            }
//        }
    }
    public Map<String,String> Beforelogin() throws IOException {
        String baseURL = "http://sf.shenzhenair.com/szffp-web/login/getCaptchaCode";
        Connection connection = Jsoup.connect(baseURL).timeout(3000);
        Connection.Response Res = connection.method(Connection.Method.GET).ignoreContentType(true)
                .execute();
        Map<String,String> cookies= Res.cookies();
        String body = Res.body();
        String signflight = body.substring(158);
        signflight = signflight.substring(0,signflight.indexOf("\""));
        cookies.put("sign_flight",signflight);
        System.out.println(cookies);
//        Document doc = Jsoup.connect(baseURL).ignoreContentType(true).get();
//        String docc = doc.body().html();
//        InputStream inputStream   =   new ByteArrayInputStream(docc.getBytes());
//        String body = docc.toString();
        return cookies;
    }
    public String login(Map<String,String>cookies,String loginName, String pwd, String captcha) throws IOException {
        String baseURL = "http://sf.shenzhenair.com/szffp-web/login/login";
        pwd = base64Encode(pwd.getBytes());
        Connection connection = Jsoup.connect(baseURL).timeout(20000);
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Length", "79");
        connection.header("Content-Type", "application/json;charset=UTF-8");
        connection.header("Host", "sf.shenzhenair.com");
        connection.header("Referer", "http://sf.shenzhenair.com/szffp-web/login/loginPage");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        connection.header("X-Requested-With", "XMLHttpRequest");
        Connection.Response Response = null;
        Map<String, String> datas = new HashMap<>(16);
        datas.put("captcha",captcha);
        datas.put("loginName",loginName);
        datas.put("pwd", pwd);
        datas.put("loginType", "MOBILE");
        String body = null;
        try {
            Response = connection.method(Connection.Method.POST).cookies(cookies).execute();
            body = Response.body();
            cookies.putAll(Response.cookies());
            System.out.println(cookies);
            //JSONObject jsonpObject = JSON.parseObject(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
    public static void main(String[] args) throws Exception {
        ShenHangOperate shenHangOperate = new ShenHangOperate();
        Map<String,String> cookies =shenHangOperate.loginSms("15858259121");
        Map<String,String> cookies2 =shenHangOperate.loginSms2(cookies,"15858259121");
//        String body = shenHangOperate.login(cookies,"15858259121","049707","2");
//        System.out.println(body);
    }
}
