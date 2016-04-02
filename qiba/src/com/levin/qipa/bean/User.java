package com.levin.qipa.bean;

import java.io.Serializable;

public class User  implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String GRADE_SUPER_USER = "8";
    
    private String id;                    // 用户id
    private String userName;              // 用户名
    private String sex;                   // 用户性别
    private String profileImage;          // 小头像
    private String video_count;           //视频数量
    private String favor_count;           //喜欢数量
    private String focus_count;           //关注数量
    private String fans_count;            //粉丝数量
    private String nfans;
    private String summary;    //个人介绍
    private String sinaWeiboUid;          // 新浪微博uid
    private String sinaWeiboTokan;        // 新浪微博token
    private String tencentWeiboUid;                 // 腾讯微博uid
    public String getTencentWeiboUid() {
		return tencentWeiboUid;
	}

	public void setTencentWeiboUid(String tencentWeiboUid) {
		this.tencentWeiboUid = tencentWeiboUid;
	}

	private String tencentWeiboTokan;               // 腾讯微博tokan
   

	private String qqUid;              // qquid
    private String qqTokan;            // qqtokan
    
    
    
    // private String personalPage; // 个人页面
    private String password;              // 密码
    private String grade;                 // 身份
    
    
    // 新增
    private String profileImageLarge;     // 大头像
    private String hideName;              // 匿名名称
    private String vipTime;               // vip到期时间
    private boolean isVip;                 // 是否为vip
    private int privacy;               // 是否开放隐私：0开放，1不开放
    
    private String hobby;                 // 兴趣爱好标签，各标签用-间隔，具体标识见profile配置接口
    private String character;             // 性格标签，各标签用-间隔，具体标识profile配置接口
    private int sexlove;               // 性别取向
    
    private String birthday;              // 生日20131010
    private String sign;                  // 星座
    private String decade;                // 年代
    private String nodisturb;             // 勿扰时段开启与否，当值不为空的时候，表示开启勿扰时段,否则表示没有开启
    private String location;              // 所在地
    
    private String push;                   // 接收新消息
    private String praise;                 //赞的个数
    private String pstatus;                 //推送设置
    
    public String getTencentWeiboTokan() {
		return tencentWeiboTokan;
	}

	public void setTencentWeiboTokan(String tencentWeiboTokan) {
		this.tencentWeiboTokan = tencentWeiboTokan;
	}
    
    
    public String getVideo_count() {
        return video_count;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public String getFavor_count() {
        return favor_count;
    }

    public void setFavor_count(String favor_count) {
        this.favor_count = favor_count;
    }

    public String getFocus_count() {
        return focus_count;
    }

    public void setFocus_count(String focus_count) {
        this.focus_count = focus_count;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getNfans() {
        return nfans;
    }

    public void setNfans(String nfans) {
        this.nfans = nfans;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getPraise() {
        return praise;
    }
    
    public void setPraise(String praise) {
        this.praise = praise;
    }
    
    
    
    public String getId() {
        return id;
    }
    
    public void setSexlove(int sexlove) {
        this.sexlove = sexlove;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public String getSinaWeiboUid() {
        return sinaWeiboUid;
    }
    
    public void setSinaWeiboUid(String sinaWeiboUid) {
        this.sinaWeiboUid = sinaWeiboUid;
    }
    
    public String getSinaWeiboTokan() {
        return sinaWeiboTokan;
    }
    
    public void setSinaWeiboTokan(String sinaWeiboTokan) {
        this.sinaWeiboTokan = sinaWeiboTokan;
    }
    
    public String getQqUid() {
        return qqUid;
    }
    
    public void setQqUid(String qqUid) {
        this.qqUid = qqUid;
    }
    
    public String getQqTokan() {
        return qqTokan;
    }
    
    public void setQqTokan(String qqTokan) {
        this.qqTokan = qqTokan;
    }
    
    
    public String getHideName() {
        return hideName;
    }
    
    public void setHideName(String hideName) {
        this.hideName = hideName;
    }
    
    public String getVipTime() {
        return vipTime;
    }
    
    public void setVipTime(String vipTime) {
        this.vipTime = vipTime;
    }
    
    public boolean isVip() {
        return isVip;
    }
    
    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }
    
    public int getPrivacy() {
        return privacy;
    }
    
    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }
    
    public String getHobby() {
        return hobby;
    }
    
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    public String getCharacter() {
        return character;
    }
    
    public void setCharacter(String character) {
        this.character = character;
    }
    
    public int getSexlove() {
        return sexlove;
    }
    
    public String getBirthday() {
        return birthday;
    }
    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public String getDecade() {
        return decade;
    }
    
    public void setDecade(String decade) {
        this.decade = decade;
    }
    
    public String getNodisturb() {
        return nodisturb;
    }
    
    public void setNodisturb(String nodisturb) {
        this.nodisturb = nodisturb;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    

    public String getProfileImageLarge() {
        return profileImageLarge;
    }
    
    public void setProfileImageLarge(String profileImageLarge) {
        this.profileImageLarge = profileImageLarge;
    }
    

    
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    

    
    protected boolean checkUidIsValid(String uid) {
        if(uid == null || uid.trim().length() == 0 || "0".equals(uid) ) {
            return false;
        }
        return true;
    }
    
    public boolean checkQQValid() {
        return checkUidIsValid(qqUid);
    }
    
    public boolean checkTencentValid() {
        return checkUidIsValid(tencentWeiboUid);
    }
    
    public boolean checkSinaValid() {
        return checkUidIsValid(sinaWeiboUid);
    }
    
    
}
