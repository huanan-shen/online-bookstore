package org.anthony.bs.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.anthony.bs.UserService;
import org.anthony.bs.domain.BsUser;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.utils.SpringContextUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Anthony
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping(value = "/find")
    public BsUser find(@RequestParam Long uid) throws BsException {
        return userService.findUser(uid);
    }

    @PostMapping(value = "/register")
    public Long register(@RequestBody BsUser user) throws BsException {
        return userService.save(user);
    }


    @PostMapping(value = "/login")
    public Long login(HttpServletResponse response, @RequestParam String name, @RequestParam String pwd) throws BsException {
        Long uid = userService.login(name, pwd);
        //
        long loginTime = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(name)
                .claim("name", name)
                .claim("pwd", pwd)
                .claim("date", loginTime)
                .claim("uid", uid)
                .setIssuedAt(new Date(loginTime))
                .signWith(SignatureAlgorithm.HS256, SpringContextUtils.getApplicationContext().getEnvironment().getProperty("cookie.jwt.sign.key"))
                .compact();
        Cookie cookie = new Cookie(SpringContextUtils.getApplicationContext().getEnvironment().getProperty("cookie.name"), token);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
        return uid;
    }

    @PostMapping(value = "/loginOut")
    public void loginOut(HttpServletResponse response) throws BsException {
        Cookie cookie = new Cookie(SpringContextUtils.getApplicationContext().getEnvironment().getProperty("cookie.name"), "");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
