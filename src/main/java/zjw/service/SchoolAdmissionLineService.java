package zjw.service;

import zjw.pojo.SchoolAdmissionLine;

import java.util.List;

public interface SchoolAdmissionLineService {
    /**
     * @Title addSchoolAdmissionLineList
     * @description 添加学校录取线
     * @author 郑洁文
     * @date 2022年8月12日 下午17:25
     * @param list
     * @return
     */
    int addSchoolAdmissionLineList(List<SchoolAdmissionLine> list);
}
