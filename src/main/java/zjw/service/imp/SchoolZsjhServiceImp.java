package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMajorFeatureMapper;
import zjw.mapper.SchoolZsjhMapper;
import zjw.pojo.SchoolZsjh;
import zjw.service.SchoolZsjhService;
import zjw.utils.SqlSessionFactoryUtil;

import java.sql.SQLException;
import java.util.List;

public class SchoolZsjhServiceImp implements SchoolZsjhService {
    /**
     * @Title addSchoolZsjhList
     * @description 批量添加学校招生计划
     * @author 郑洁文
     * @date 2022年8月15日 下午16:29
     * @param list
     * @return
     */
    public int addSchoolZsjhList(List<SchoolZsjh> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolZsjhMapper mapper = sqlSession.getMapper(SchoolZsjhMapper.class);
        int i = 0;
        try {
            i = mapper.addSchoolZsjhList(list);
            SqlSessionFactoryUtil.commitAndClose(sqlSession);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            SqlSessionFactoryUtil.rollbackAndClose(sqlSession);
        }
        return i;
    }
}
