package zjw.mapper;

import zjw.pojo.SchoolImg;

import java.util.List;

public interface SchoolImgMapper {
    /**
     * @Title addSchoolZsjhList
     * @description 批量添加学校招生计划
     * @author 郑洁文
     * @date 2022年8月15日 下午16:29
     * @param list
     * @return
     */
    int addSchoolImgList(List<SchoolImg> list);
}
