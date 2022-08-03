package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMapper;
import zjw.pojo.School;
import zjw.service.SchoolService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolServiceImp implements SchoolService {

    public int addSchool(School school) {
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMapper mapper = sqlSession.getMapper(SchoolMapper.class);
        int i = mapper.addSchool(school);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
