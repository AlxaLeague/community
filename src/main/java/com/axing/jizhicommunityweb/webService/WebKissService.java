package com.axing.jizhicommunityweb.webService;

import com.axing.entity.Kiss;

public interface WebKissService {
    Kiss getKissByKuid(String kuid);
    int updateKiss(Kiss kiss);
    int insertKiss(Kiss kiss);
}
