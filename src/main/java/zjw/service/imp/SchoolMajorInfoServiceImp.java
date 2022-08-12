package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMajorInfoMapper;
import zjw.pojo.SchoolMajorInfo;
import zjw.service.SchoolMajorInfoService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolMajorInfoServiceImp implements SchoolMajorInfoService {

    /**
     * @Title addSchoolMajorInfo
     * @description 添加学校专业类别
     * @author 郑洁文
     * @date 2022年8月11日 下午16:32
     * @param schoolMajorInfo
     * @return
     */
    public int addSchoolMajorInfo(SchoolMajorInfo schoolMajorInfo){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorInfoMapper mapper = sqlSession.getMapper(SchoolMajorInfoMapper.class);
        int i = mapper.addSchoolMajorInfo(schoolMajorInfo);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title deleteSchoolMajorInfo
     * @description delete学校专业详情
     * @author 郑洁文
     * @date 2022年8月12日 上午9:54
     * @param schoolMajorInfo
     * @return
     */
    public int deleteSchoolMajorInfo(SchoolMajorInfo schoolMajorInfo){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorInfoMapper mapper = sqlSession.getMapper(SchoolMajorInfoMapper.class);
        int i = mapper.deleteSchoolMajorInfo(schoolMajorInfo);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
