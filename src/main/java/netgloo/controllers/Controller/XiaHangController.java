//package netgloo.controllers.Controller;
//
//import netgloo.controllers.Flight.HaiNanHangOperate;
//import netgloo.controllers.Flight.XiaMenHangOperate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Map;
//// import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class XiaHangController {
//  @Autowired
//  XiaMenHangOperate xiaHangController = new XiaMenHangOperate();
//  @RequestMapping("/")
//  public String index(HttpSession session) throws IOException {
//    Map<String,String> cookies = xiaHangController.getPageToken();
//    cookies= xiaHangController.getSafeCode(cookies);
//    session.setAttribute("cookies",cookies);
//    System.out.println("session-cookies:"+cookies);
//    return "XiaMenHangKong.html";
//  }
//  @RequestMapping(value="/action",method = RequestMethod.POST)
//   @ResponseBody
//  public String action(String validCode_login,String userName_login,String password_login,HttpSession session) throws IOException {
//    Map<String,String> cookies = (Map<String, String>) session.getAttribute("cookies");
//    Map<String,String> Loggedcookies =xiaHangController.login(userName_login,password_login,validCode_login,cookies);
//    //Map<String,String> meiyongdedongxi =xiaHangController.Operate(Loggedcookies);
//    return "XiaMenHangKong.html";
//  }
//
//}
