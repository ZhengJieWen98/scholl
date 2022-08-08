package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMapper;
import zjw.pojo.School;
import zjw.service.SchoolService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolServiceImp implements SchoolService {
    /**
     * @Title addSchool
     * @description 添加学校
     * @author 郑洁文
     * @date 2022年8月3日 下午15:00
     * @return
     */
    public int addSchool(School school) {
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMapper mapper = sqlSession.getMapper(SchoolMapper.class);
        int i = mapper.addSchool(school);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
