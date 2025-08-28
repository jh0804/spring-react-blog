package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> userinfo(@PathVariable Integer id) {
        UserResponse.DTO respDTO = userService.회원조회(id);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(HttpServletRequest request, @PathVariable Integer id, @Valid @RequestBody UserRequest.UpdateDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");
        SessionUser newSessionUser = userService.회원수정(sessionUser.getId(), reqDTO);
        request.setAttribute("sessionUser", newSessionUser);

        return ResponseEntity.ok(new ApiUtil(newSessionUser));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO reqDTO, Errors errors) {
        String jwt = userService.로그인(reqDTO);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(new ApiUtil(null));
    }
}