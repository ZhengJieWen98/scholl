package zjw.service;

import zjw.pojo.SchoolMajorInfo;

public interface SchoolMajorInfoService {

    /**
     * @Title addSchoolMajorInfo
     * @description 添加学校专业类别
     * @author 郑洁文
     * @date 2022年8月11日 下午16:32
     * @param schoolMajorInfo
     * @return
     */
    int addSchoolMajorInfo(SchoolMajorInfo schoolMajorInfo);

    /**
     * @Title deleteSchoolMajorInfo
     * @description delete学校专业详情
     * @author 郑洁文
     * @date 2022年8月12日 上午9:54
     * @param schoolMajorInfo
     * @return
     */
    int deleteSchoolMajorInfo(SchoolMajorInfo schoolMajorInfo);
}
