package com.axing.jizhicommunityweb.webService;

import com.axing.entity.Comment;
import com.axing.jizhicommunityweb.entity.HomeComment;

import java.util.List;
import java.util.Map;

public interface WebCommentService {
    List<Comment> getComments(String thesisid);
    int addComment(Map<String,String> map, String uid);
    int delComment(Integer commentid);
    List<HomeComment> getCommentsByUid(String uid);

}
