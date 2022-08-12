package zjw.mapper;

import zjw.pojo.SchoolMajorType;

public interface SchoolMajorTypeMapper {
    /**
     * @Title addSchoolMajorType
     * @description 添加学校专业类别
     * @author 郑洁文
     * @date 2022年8月11日 下午15:39
     * @param schoolMajorType
     * @return
     */
    int addSchoolMajorType(SchoolMajorType schoolMajorType);

    /**
     * @Title deleteSchoolMajorType
     * @description delete学校专业类别
     * @author 郑洁文
     * @date 2022年8月12日 上午9:39
     * @param schoolMajorType
     * @return
     */
    int deleteSchoolMajorType(SchoolMajorType schoolMajorType);
}
