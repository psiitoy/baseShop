package com.rise.shop.web.controller.usage;

import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.service.art.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    UserService userService;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView login(
            @RequestParam(value = "name", required = true) String name, @RequestParam(value = "pwd", required = true) String pwd,
            HttpSession session) {
        ModelAndView mv = new ModelAndView();
        try {
            User queryParams = new User();
            queryParams.setEmail(name);
            queryParams.setPwd(pwd);
            List<User> users = userService.findBy(queryParams);
            if (users != null && users.size() == 1) {
                User user = users.get(0);
                mv.setViewName("redirect:/artist/index");
                session.setAttribute("isLogin", true);
                session.setAttribute("sessionAuthCode", user.getAuthCode());
            } else {
                mv.setViewName("/desc");
                mv.addObject("msg", "密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("/desc");
            mv.addObject("msg", "系统错误");
        }
        return mv;
    }

    @RequestMapping(value = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/artist/index");
        session.removeAttribute("isLogin");
        mv.setViewName("/desc");
        return mv;
    }
}
