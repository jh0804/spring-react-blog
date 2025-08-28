package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.SessionUser;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Integer id) {
        User sessionUser = (User) request.getAttribute("sessionUser");
        replyService.댓글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }

    @PostMapping("/api/replies")
    public ResponseEntity<?> save(HttpServletRequest request, @Valid @RequestBody ReplyRequest.SaveDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) request.getAttribute("sessionUser");
        ReplyResponse.SaveDTO respDTO = replyService.댓글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }
}