package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.SessionUser;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@RestController // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/")
    public ResponseEntity<?> mainV2(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(defaultValue = "") String keyword) {
        BoardResponse.MainV2DTO respDTO = boardService.글목록조회V2(pageable, keyword);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findOne(@PathVariable Integer id) {
        BoardResponse.DTO respDTO = boardService.글조회(id);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@Valid @RequestBody BoardRequest.SaveDTO reqDTO, Errors errors) {
        System.out.println("reqDTO");
        System.out.println(reqDTO);
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody BoardRequest.UpdateDTO reqDTO, Errors errors) {

        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글수정(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }
}