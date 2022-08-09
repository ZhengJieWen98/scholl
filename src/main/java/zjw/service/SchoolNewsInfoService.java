package zjw.service;

import zjw.pojo.SchoolNewsInfo;

public interface SchoolNewsInfoService {
    /**
     * @Title addSchoolNewsInfo
     * @description 添加学校资讯消息详情
     * @author 郑洁文
     * @date 2022年8月9日 下午15:11
     * @param schoolNewsInfo
     * @return
     */
    int addSchoolNewsInfo(SchoolNewsInfo schoolNewsInfo);

    /**
     * @Title deleteSchoolNews
     * @description 批量delete学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午13:51
     * @param school_id
     * @return
     */
    int deleteSchoolNewsInfo(String school_id);
}
