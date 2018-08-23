package netgloo.controllers;
 
import netgloo.controllers.data.HaiNanHangOperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;
// import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
  @Autowired
  HaiNanHangOperate haiNanHangOperate = new HaiNanHangOperate();
  @RequestMapping("/")
  // @ResponseBody
  public String index(HttpSession session) throws IOException {
    Map<String,String> cookies = haiNanHangOperate.getSafeCode();
    session.setAttribute("cookies",cookies);
    System.out.println("session-cookies"+session.getAttribute("cookies"));
    return "hello.html";
  }
  @RequestMapping("/getEncryptKey")
  // @ResponseBody
  public void getKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String key = haiNanHangOperate.getEncryptKey();
    PrintWriter out = response.getWriter();
    out.println(key);
  }
  @RequestMapping(value="/action",method = RequestMethod.POST)
   @ResponseBody
  public String action(String validCode_login,String userName,String login_pwd,HttpSession session) throws IOException {
    Map<String,String> cookies = (Map<String, String>) session.getAttribute("cookies");
    System.out.println("session-cookies"+cookies);
    String body = haiNanHangOperate.validate(validCode_login,cookies);
    return "hello.html";
  }

}
