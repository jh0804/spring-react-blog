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

    @Data
    public static class DetailDTO {
        private Integer id;
        private String comment;
        private Integer userId;
        private String username;
        private Boolean owner;

        public DetailDTO(Reply reply, int sessionUserId) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            this.userId = reply.getUser().getId(); // id이므로 lazy loading 안됨
            this.username = reply.getUser().getUsername(); // lazy loading 안됨
            this.owner = reply.getUser().getId() == sessionUserId;
        }
    }
}
