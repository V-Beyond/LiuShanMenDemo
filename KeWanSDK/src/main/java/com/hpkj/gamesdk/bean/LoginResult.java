package com.hpkj.gamesdk.bean;

import org.xutils.http.annotation.HttpResponse;

/**
 * @ClassNname：LoginResult.java
 * @Describe 登录实体类
 * @author huanglei
 * @time 2018/3/12 11:01
 */
@HttpResponse(parser = JsonResponseParser.class)
public class LoginResult extends  BaseResult {

    /**
     * data : {"user":{"id":47,"nickname":"15221316476","mobile":15221316476,"sex":"","birthday":"","avatar":"http://kwimg.700000.cc/uploads/images/20180312/5d4834d1117971a7073d64d0d1c726e2.jpg","type":0}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user : {"id":47,"nickname":"15221316476","mobile":15221316476,"sex":"","birthday":"","avatar":"http://kwimg.700000.cc/uploads/images/20180312/5d4834d1117971a7073d64d0d1c726e2.jpg","type":0}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 47
             * nickname : 15221316476
             * mobile : 15221316476
             * sex :
             * birthday :
             * avatar : http://kwimg.700000.cc/uploads/images/20180312/5d4834d1117971a7073d64d0d1c726e2.jpg
             * type : 0
             */

            private int id;
            private String nickname;
            private long mobile;
            private String sex;
            private String birthday;
            private String avatar;
            private int type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public long getMobile() {
                return mobile;
            }

            public void setMobile(long mobile) {
                this.mobile = mobile;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
