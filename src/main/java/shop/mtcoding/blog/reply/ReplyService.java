package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.SessionUser;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserJPARepository;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final BoardJPARepository boardJPARepository;
    private final ReplyJPARepository replyJPARepository;
    private final UserJPARepository userJPARepository;

    @Transactional
    public ReplyResponse.DetailDTO 댓글쓰기(ReplyRequest.SaveDTO reqDTO, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId()).orElseThrow();

        Board board = boardJPARepository.findById(reqDTO.getBoardId())
                .orElseThrow(() -> new Exception404("없는 게시글에 댓글을 작성할 수 없어요"));

        Reply reply = reqDTO.toEntity(user, board);

        replyJPARepository.save(reply);

        // 어차피 insert + select 발생하므로 join fetch 할 필요 X -> laxy loading으로 처리
        // 근데 lazy loading도 sessionUser 있으니까 그걸로 처리
        // 근데 Reply reply = reqDTO.toEntity(user, board);에 의해 user가 영속화 되어있으므로 lazy loading 안됨 + 할 필요x
        return new ReplyResponse.DetailDTO(reply, sessionUser.getId());
    }

    @Transactional
    public void 댓글삭제(int replyId, int sessionUserId) {
        Reply reply = replyJPARepository.findById(replyId)
                .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 없어요"));

        if (reply.getUser().getId() != sessionUserId) {
            throw new Exception403("댓글을 삭제할 권한이 없어요");
        }

        replyJPARepository.deleteById(replyId);
    }
}
