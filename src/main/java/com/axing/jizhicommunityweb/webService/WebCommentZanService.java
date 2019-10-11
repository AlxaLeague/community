package com.axing.jizhicommunityweb.webService;

import com.axing.entity.CommentZan;

public interface WebCommentZanService {
    int deleteByid(Integer id);

    int insertCommentZan(String commentid, String zuid);

    CommentZan selectByCidAndZuid(String commentid, String zuid);

    int selectCountByCid(String commentid);
}
