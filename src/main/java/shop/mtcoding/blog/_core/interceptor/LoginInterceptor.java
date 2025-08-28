package shop.mtcoding.blog._core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog.user.SessionUser;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 필터에서 이미 세션에 사용자 정보를 넣어주었으므로,
        // 여기서는 세션에 해당 정보가 있는지 확인만 합니다.
        SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");

        // 세션에 정보가 없으면 예외를 발생시켜 요청을 중단합니다.
        if (sessionUser == null) {
            throw new Exception401("인증이 필요한 서비스입니다.");
        }

        // 세션에 정보가 있으면 다음 단계(컨트롤러)로 진행합니다.
        return true;
    }

    // postHandle, afterCompletion 등의 메서드는 필요에 따라 구현합니다.
    // 현재 요구사항에서는 불필요하므로 제거했습니다.
}