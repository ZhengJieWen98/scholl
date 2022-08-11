package zjw.mapper;

import zjw.pojo.SchoolMajorFeature;
import zjw.pojo.SchoolNews;

import java.util.List;

public interface SchoolMajorFeatureMapper {
    /**
     * @Title addSchoolMajorFeatureList
     * @description 批量添加学校特色专业
     * @author 郑洁文
     * @date 2022年8月11日 下午15:27
     * @param list
     * @return
     */
    int addSchoolMajorFeatureList(List<SchoolMajorFeature> list);

    /**
     * @Title deleteSchool
     * @description delet学校特色专业
     * @author 郑洁文
     * @date 2022年8月11日 下午17:33
     * @param school_id
     * @return
     */
    int deleteSchoolMajorFeature(String school_id);

}
