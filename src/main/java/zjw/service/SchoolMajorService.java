package zjw.service;

import zjw.pojo.SchoolMajor;

import java.util.List;

public interface SchoolMajorService {
    /**
     * @Title addSchool
     * @description 添加学校专业
     * @author 郑洁文
     * @date 2022年8月11日 下午15:55
     * @param schoolMajorList
     * @return
     */
    int addSchoolMajorList(List<SchoolMajor> schoolMajorList);
}
