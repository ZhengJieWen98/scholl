package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolMajorFeatureMapper;
import zjw.mapper.SchoolNewsMapper;
import zjw.pojo.SchoolMajorFeature;
import zjw.service.SchoolMajorFeatureService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class SchoolMajorFeatureServiceImp implements SchoolMajorFeatureService {
    @Override
    public int addSchoolMajorFeatureList(List<SchoolMajorFeature> list) {
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolMajorFeatureMapper mapper = sqlSession.getMapper(SchoolMajorFeatureMapper.class);
        int i = mapper.addSchoolMajorFeatureList(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
