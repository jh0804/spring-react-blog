package shop.mtcoding.blog._core.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.utils.JwtUtil;
import shop.mtcoding.blog.user.SessionUser;

import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("Authorization");

        // 토큰이 존재할 경우에만 검증 로직을 수행합니다.
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.replace("Bearer ", "");
            try {
                // JwtUtil을 사용해 토큰을 검증하고 사용자 정보를 가져옵니다.
                SessionUser sessionUser = JwtUtil.verify(jwt);

                // 세션에 사용자 정보를 설정합니다.
                // stateless 서버이므로 session 보다는 request에 담는게 원래는 맞다 (이전에는 기존 코드 그대로 사용하고자 사용한 것 뿐)
                req.setAttribute("sessionUser", sessionUser);
            } catch (TokenExpiredException e) {
                // 토큰 만료 시 예외 처리 (필터에서는 예외를 던지기보다 로그를 남기는 것이 일반적)
                // 현재는 간단한 예외 처리만 합니다.
                throw new Exception401("토큰 만료시간이 지났습니다. 다시 로그인해주세요.");
            } catch (JWTDecodeException e) {
                // 토큰이 유효하지 않을 경우 예외 처리
                throw new Exception401("유효하지 않은 토큰입니다.");
            } catch (Exception e) {
                // 그 외 모든 예외 처리
                throw new ServletException(e.getMessage());
            }
        }

        // 다음 필터나 서블릿으로 요청을 전달합니다.
        chain.doFilter(request, response);
    }
}