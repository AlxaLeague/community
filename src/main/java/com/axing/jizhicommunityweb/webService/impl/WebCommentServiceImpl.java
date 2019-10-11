package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.Comment;
import com.axing.entity.Thesis;
import com.axing.jizhicommunityweb.entity.HomeComment;
import com.axing.jizhicommunityweb.webService.WebCommentService;
import com.axing.jizhicommunityweb.webService.WebThesisService;
import com.axing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WebCommentServiceImpl implements WebCommentService {

    @Autowired
    WebThesisService webThesisService;

    @Reference
    CommentService commentService;
    @Override
    public List<Comment> getComments(String thesisid) {
        return commentService.getComments(thesisid);
    }

    @Override
    public int addComment(Map<String,String> map,String uid) {
        String content = map.get("content");
        String contenthtml = map.get("contenthtml");
        String thesisid = map.get("thesisid");
        String timestamp = ""+System.currentTimeMillis()/1000;

        Comment comment = new Comment();
        comment.setCommentcontent(content);
        comment.setCommentcontenth(contenthtml);
        comment.setCommentuid(uid);
        comment.setThesisid(thesisid);
        comment.setTimestamp(timestamp);
        return commentService.addComment(comment);
    }

    @Override
    public int delComment(Integer commentid) {
        return commentService.delComment(commentid);
    }

    @Override
    public List<HomeComment> getCommentsByUid(String uid) {
        List<HomeComment> homeComments = new ArrayList<HomeComment>();
        List<Comment> comments = commentService.getCommentsByUid(uid);
        for (Comment comment : comments){

            Date date = new Date(Long.parseLong(comment.getTimestamp())*1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String thesistimestamp = simpleDateFormat.format(date);
            Thesis thesis = webThesisService.getThesisByTid(comment.getThesisid());
            homeComments.add(new HomeComment(thesis.getTitle(),thesis.getThesisid(),comment.getCommentcontenth(),thesistimestamp));
        }
        return homeComments;
    }


}
