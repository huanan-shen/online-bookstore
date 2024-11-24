package org.anthony.bs.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.anthony.bs.UserContext;
import org.anthony.bs.UserService;
import org.anthony.bs.domain.BsUser;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.utils.SpringContextUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.util.annotation.Nullable;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(LoginInterceptor.class);

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getTokenFromCookie(request);
        Claims claims = Jwts.parser().setSigningKey(SpringContextUtils.getApplicationContext().getEnvironment().getProperty("cookie.jwt.sign.key"))
                .parseClaimsJws(token).getBody();
        BsUser bsUser = userService.findUser(Long.parseLong(String.valueOf(claims.get("uid"))));
        UserContext.setCurrentUser(bsUser);
        // reset cookie
        Cookie cookie = new Cookie(SpringContextUtils.getApplicationContext().getEnvironment().getProperty("cookie.name"), token);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        UserContext.invalidate();
    }

    private String getTokenFromCookie(HttpServletRequest request) throws BsException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            throw BsException.of("00-001");
        }
        String cookieName = SpringContextUtils.getApplicationContext().getEnvironment().getProperty("cookie.name");
        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals(cookieName)) {
                continue;
            }
            return cookie.getValue();
        }
        throw BsException.of("00-001");
    }

}
