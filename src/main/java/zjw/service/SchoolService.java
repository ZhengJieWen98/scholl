package zjw.service;

import zjw.pojo.School;

import java.util.List;

public interface SchoolService {
    /**
     * @Title addSchool
     * @description 添加学校
     * @author 郑洁文
     * @date 2022年8月3日 下午15:00
     * @param school
     * @return
     */
    int addSchool(School school);

    /**
     * @Title deleteSchool
     * @description delete学校
     * @author 郑洁文
     * @date 2022年8月3日 上午9:22
     * @param school
     * @return
     */
    int deleteSchool(School school);

    /**
     * @Title findAllSchoolId
     * @description 查询所有的school_id
     * @author 郑洁文
     * @date 2022年8月3日 下午14:16
     * @return
     */
    List<String> findAllSchoolId();
}
