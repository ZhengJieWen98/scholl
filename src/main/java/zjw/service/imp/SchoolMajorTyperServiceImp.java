package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMajorTypeMapper;
import zjw.pojo.SchoolMajorType;
import zjw.service.SchoolMajorTyperService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolMajorTyperServiceImp implements SchoolMajorTyperService {
    /**
     * @Title addSchoolMajorType
     * @description 添加学校专业类别
     * @author 郑洁文
     * @date 2022年8月11日 下午15:39
     * @param schoolMajorType
     * @return
     */
    public int addSchoolMajorType(SchoolMajorType schoolMajorType){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorTypeMapper mapper = sqlSession.getMapper(SchoolMajorTypeMapper.class);
        int i = mapper.addSchoolMajorType(schoolMajorType);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title deleteSchoolMajorType
     * @description delete学校专业类别
     * @author 郑洁文
     * @date 2022年8月12日 上午9:39
     * @param schoolMajorType
     * @return
     */
    public int deleteSchoolMajorType(SchoolMajorType schoolMajorType){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorTypeMapper mapper = sqlSession.getMapper(SchoolMajorTypeMapper.class);
        int i = mapper.deleteSchoolMajorType(schoolMajorType);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
