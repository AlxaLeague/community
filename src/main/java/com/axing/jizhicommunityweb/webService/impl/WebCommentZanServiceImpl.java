package com.axing.jizhicommunityweb.webService.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.axing.entity.CommentZan;
import com.axing.jizhicommunityweb.webService.WebCommentZanService;
import com.axing.service.CommentZanService;
import org.springframework.stereotype.Service;

@Service
public class WebCommentZanServiceImpl implements WebCommentZanService {

    @Reference
    CommentZanService commentZanService;
    @Override
    public int deleteByid(Integer id) {
        return commentZanService.deleteByid(id);
    }

    @Override
    public int insertCommentZan(String commentid, String zuid) {
        CommentZan commentZan = new CommentZan();
        commentZan.setZtimestamp(""+System.currentTimeMillis()/1000);
        commentZan.setZuid(zuid);
        commentZan.setCommentid(commentid);
        return commentZanService.insertCommentZan(commentZan);
    }

    @Override
    public CommentZan selectByCidAndZuid(String commentid, String zuid) {

        return commentZanService.selectByCidAndZuid(commentid,zuid);
    }

    @Override
    public int selectCountByCid(String commentid) {
        return commentZanService.selectCountByCid(commentid);
    }
}
