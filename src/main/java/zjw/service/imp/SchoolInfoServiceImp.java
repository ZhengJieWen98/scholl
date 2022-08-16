package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolInfoMapper;
import zjw.pojo.SchoolInfo;
import zjw.service.SchoolInfoService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolInfoServiceImp implements SchoolInfoService {
    /**
     * @Title addSchoolInfo
     * @description 添加学校详情信息
     * @author 郑洁文
     * @date 2022年8月16日 下午15:22
     * @return
     */
    public int addSchoolInfo(SchoolInfo schoolInfo){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolInfoMapper mapper = sqlSession.getMapper(SchoolInfoMapper.class);
        int i = mapper.addSchoolInfo(schoolInfo);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
