package zjw.mapper;
import zjw.pojo.SchoolZsjh;

import java.sql.SQLException;
import java.util.List;

public interface SchoolZsjhMapper {
    /**
     * @Title addSchoolZsjhList
     * @description 批量添加学校招生计划
     * @author 郑洁文
     * @date 2022年8月15日 下午16:29
     * @param list
     * @return
     */
    int addSchoolZsjhList(List<SchoolZsjh> list) throws SQLException;
}
