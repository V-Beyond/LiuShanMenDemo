package com.hpkj.gamesdk.bean;

import org.xutils.http.annotation.HttpResponse;

/**
 * @author huanglei
 * @des 注册实体类
 * @date 2018/3/13 14:03
 */
@HttpResponse(parser = JsonResponseParser.class)
public class RegisterBean extends BaseResult {
    User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public class User {
        private String id;
        private String mobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

}
