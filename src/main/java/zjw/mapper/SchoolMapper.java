package zjw.mapper;

import zjw.pojo.School;

import java.util.List;

public interface SchoolMapper {
    /**
     * @Title addSchool
     * @description 添加学校
     * @author 郑洁文
     * @date 2022年8月3日 下午14:00
     * @param school
     * @return
     */
    int addSchool(School school);

    /**
     * @Title findAllSchoolId
     * @description 查询所有的school_id
     * @author 郑洁文
     * @date 2022年8月3日 下午14:16
     * @return
     */
    List<String> findAllSchoolId();
}
