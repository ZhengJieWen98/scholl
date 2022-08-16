package zjw.service;

import zjw.pojo.SchoolMajorLine;

import java.util.List;

public interface SchoolMajorLineService {
    /**
     * @Title addSchoolMajorLineList
     * @description 批量添加学校专业录取线
     * @author 郑洁文
     * @date 2022年8月16日 上午10:00
     * @param list
     * @return
     */
    int addSchoolMajorLineList(List<SchoolMajorLine> list);
}
