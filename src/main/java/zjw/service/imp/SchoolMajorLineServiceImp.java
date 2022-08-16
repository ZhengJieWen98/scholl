package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMajorLineMapper;
import zjw.mapper.SchoolZsjhMapper;
import zjw.pojo.SchoolMajorLine;
import zjw.service.SchoolMajorLineService;
import zjw.utils.SqlSessionFactoryUtil;

import java.sql.SQLException;
import java.util.List;

public class SchoolMajorLineServiceImp implements SchoolMajorLineService {
    /**
     * @Title addSchoolMajorLineList
     * @description 批量添加学校专业录取线
     * @author 郑洁文
     * @date 2022年8月16日 上午10:00
     * @param list
     * @return
     */
    public int addSchoolMajorLineList(List<SchoolMajorLine> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorLineMapper mapper = sqlSession.getMapper(SchoolMajorLineMapper.class);
        int i = mapper.addSchoolMajorLineList(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
