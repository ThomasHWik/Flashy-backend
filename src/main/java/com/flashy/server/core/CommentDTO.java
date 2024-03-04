package com.flashy.server.core;

import lombok.Data;
@Data
public class CommentDTO {

        private String comment;
        private String username;
        private String carddeckuuid;
        private String uuid;
        private String createdat;

        public CommentDTO() {

        }

        public CommentDTO(String comment, String username, String carddeckuuid, String uuid, String createdat) {
            this.comment = comment;
            this.username = username;
            this.carddeckuuid = carddeckuuid;
            this.uuid = uuid;
            this.createdat = createdat;
        }

}
