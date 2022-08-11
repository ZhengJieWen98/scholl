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
}
