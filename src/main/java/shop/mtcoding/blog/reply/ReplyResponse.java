package shop.mtcoding.blog.reply;


import lombok.Data;

public class ReplyResponse {

    @Data
    public static class DTO {
        private Integer replyId;
        private Integer boardId;
        private Integer userId;
        private String comment;

        public DTO(Reply reply) {
            this.replyId = reply.getId();
            this.boardId = reply.getBoard().getId();
            this.userId = reply.getUser().getId();
            this.comment = reply.getComment();
        }
    }
}
