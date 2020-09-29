package com.nowcoder.controller;

import com.nowcoder.model.User;

import com.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path={"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession) {
        return wendaService.getMessage(2) + "hello Caihuiling " + httpSession.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "zz", required = false) String key) {
        return String.format("Profile page of %s / %d, t:%d k:%s", groupId, userId, type, key);
    }

    @RequestMapping(path={"/vm"}, method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1", "vvvvv1");
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i*i));

        }
        model.addAttribute("map", map);

        model.addAttribute("user", new User("LEE"));
        return "home";
    }

    @RequestMapping(path={"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(sessionId + "<br>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ": " + request.getHeader(name) + "<br>");
        }
        if (request.getCookies() != null) {
            for (Cookie cookie: request.getCookies()) {
                sb.append("cookie: " + cookie.getName() + " value: " + cookie.getValue());
            }
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");
        sb.append(request.getRequestURL() + "<br>");

        response.addHeader("Caihuiling", "hello");
        response.addCookie(new Cookie("username", "caihuiling"));

        return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession) {
        httpSession.setAttribute("msg", "jump from redirect");
        return "redirect:/";
    }
}
