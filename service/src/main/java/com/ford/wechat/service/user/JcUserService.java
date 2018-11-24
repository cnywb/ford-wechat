package com.ford.wechat.service.user;

/**
 * Created by Neel on 2017/5/19.
 */
public interface JcUserService {

    void save(Long userId, String username, String email, String ip, Long groupId, Long grain);
}
