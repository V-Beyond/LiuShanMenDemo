package com.hpkj.gamesdk.bean;

/**
 * @author huanglei
 * @ClassNname：RoleBaseData.java
 * @Describe 角色信息类
 * @time 2018/4/2 17:55
 */

public class KeWanRoleBaseData {
    private String type;
    private int zoneid;
    private String zonename;
    private String roleid;
    private String rolename;
    private int professionid;
    private String profession;
    private String gender;
    private int professionroleid;
    private String professionrolename;
    private int rolelevel;
    private int power;
    private int vip;
    private KeWanBalance balance;
    private int partyid;
    private String partyname;
    private int partyroleid;
    private String partyrolename;
    private KeWanFriendlist friendlist;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getZoneid() {
        return zoneid;
    }

    public void setZoneid(int zoneid) {
        this.zoneid = zoneid;
    }

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public int getProfessionid() {
        return professionid;
    }

    public void setProfessionid(int professionid) {
        this.professionid = professionid;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getProfessionroleid() {
        return professionroleid;
    }

    public void setProfessionroleid(int professionroleid) {
        this.professionroleid = professionroleid;
    }

    public String getProfessionrolename() {
        return professionrolename;
    }

    public void setProfessionrolename(String professionrolename) {
        this.professionrolename = professionrolename;
    }

    public int getRolelevel() {
        return rolelevel;
    }

    public void setRolelevel(int rolelevel) {
        this.rolelevel = rolelevel;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public KeWanBalance getBalance() {
        return balance;
    }

    public void setBalance(KeWanBalance balance) {
        this.balance = balance;
    }

    public int getPartyid() {
        return partyid;
    }

    public void setPartyid(int partyid) {
        this.partyid = partyid;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public int getPartyroleid() {
        return partyroleid;
    }

    public void setPartyroleid(int partyroleid) {
        this.partyroleid = partyroleid;
    }

    public String getPartyrolename() {
        return partyrolename;
    }

    public void setPartyrolename(String partyrolename) {
        this.partyrolename = partyrolename;
    }

    public KeWanFriendlist getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(KeWanFriendlist friendlist) {
        this.friendlist = friendlist;
    }

    public static class KeWanBalance {
        private int balanceid;
        private String balancename;
        private int balancenum;

        public int getBalanceid() {
            return balanceid;
        }

        public void setBalanceid(int balanceid) {
            this.balanceid = balanceid;
        }

        public String getBalancename() {
            return balancename;
        }

        public void setBalancename(String balancename) {
            this.balancename = balancename;
        }

        public int getBalancenum() {
            return balancenum;
        }

        public void setBalancenum(int balancenum) {
            this.balancenum = balancenum;
        }
    }

    public static  class KeWanFriendlist {
        private int roleid;
        private int intimacy;
        private int nexusid;
        private String nexusname;

        public int getRoleid() {
            return roleid;
        }

        public void setRoleid(int roleid) {
            this.roleid = roleid;
        }

        public int getIntimacy() {
            return intimacy;
        }

        public void setIntimacy(int intimacy) {
            this.intimacy = intimacy;
        }

        public int getNexusid() {
            return nexusid;
        }

        public void setNexusid(int nexusid) {
            this.nexusid = nexusid;
        }

        public String getNexusname() {
            return nexusname;
        }

        public void setNexusname(String nexusname) {
            this.nexusname = nexusname;
        }
    }
}
