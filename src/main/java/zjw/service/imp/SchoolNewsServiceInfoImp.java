package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolNewsInfoMapper;
import zjw.mapper.SchoolNewsMapper;
import zjw.pojo.SchoolNewsInfo;
import zjw.service.SchoolNewsInfoService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolNewsServiceInfoImp implements SchoolNewsInfoService {
    /**
     * @Title addSchoolNewsInfo
     * @description 添加学校资讯消息详情
     * @author 郑洁文
     * @date 2022年8月9日 下午15:11
     * @param schoolNewsInfo
     * @return
     */
    public int addSchoolNewsInfo(SchoolNewsInfo schoolNewsInfo){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsInfoMapper mapper = sqlSession.getMapper(SchoolNewsInfoMapper.class);
        int i = mapper.addSchoolNewsInfo(schoolNewsInfo);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title deleteSchoolNewsInfo
     * @description 批量delete学校消息详情
     * @author 郑洁文
     * @date 2022年8月9日 下午13:51
     * @param school_id
     * @return
     */
    public int deleteSchoolNewsInfo(String school_id){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsInfoMapper mapper = sqlSession.getMapper(SchoolNewsInfoMapper.class);
        int i = mapper.deleteSchoolNewsInfo(school_id);
        return i;
    }
}
