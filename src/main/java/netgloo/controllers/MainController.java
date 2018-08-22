package netgloo.controllers;
 
import netgloo.controllers.data.HaiNanHangOperate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
// import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

  @RequestMapping("/")
  // @ResponseBody
  public String index() {
    String url = "http://sf.shenzhenair.com/szffp-web/login/getCaptchaCode?id=1534931841049";
    return "hello.html";
  }
  @RequestMapping("/action")
  // @ResponseBody
  public String action(String yanzhengma) throws IOException {
    String id = "15858259121";
    String password = "049707";
    HaiNanHangOperate haiNanHangOperate = new HaiNanHangOperate();
    String body = haiNanHangOperate.login(id,password,yanzhengma);
    System.out.println(body);
    return "hello.html";
  }

}
