package zjw.mapper;

import zjw.pojo.School;
import zjw.pojo.SchoolMajor;

import java.util.List;

public interface SchoolMajorMapper {

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
