package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMajorFeatureMapper;
import zjw.mapper.SchoolMajorMapper;
import zjw.pojo.SchoolMajor;
import zjw.service.SchoolMajorService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class SchoolMajorServiceImp implements SchoolMajorService {
    /**
     * @Title addSchool
     * @description 添加学校专业
     * @author 郑洁文
     * @date 2022年8月11日 下午15:55
     * @param schoolMajorList
     * @return
     */
    public int addSchoolMajorList(List<SchoolMajor> schoolMajorList){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorMapper mapper = sqlSession.getMapper(SchoolMajorMapper.class);
        int i = mapper.addSchoolMajorList(schoolMajorList);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title deleteSchoolMajorType
     * @description delete学校专业类别
     * @author 郑洁文
     * @date 2022年8月12日 上午9:47
     * @param schoolMajor
     * @return
     */
    public int deleteSchoolMajor(SchoolMajor schoolMajor){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorMapper mapper = sqlSession.getMapper(SchoolMajorMapper.class);
        int i = mapper.deleteSchoolMajor(schoolMajor);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

}
