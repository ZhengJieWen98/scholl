package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.ParaMapper;
import zjw.pojo.Para;
import zjw.service.ParaService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class ParaServiceImp implements ParaService {
    /**
     * @Title finaAllSchoolUrls
     * @description 查询所有高校的url地址
     * @author 郑洁文
     * @date 2022年8月8日 下午15:10
     * @return
     */
    public List<Para> finaAllSchoolUrls() {
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ParaMapper mapper = sqlSession.getMapper(ParaMapper.class);
        List<Para> paras = mapper.finaAllSchoolUrls();
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return paras;
    }

    /**
     * @Title finaSchoolNews
     * @description 查询高校的最新消息Url
     * @author 郑洁文
     * @date 2022年8月9日 上午11:05
     * @return
     */
    public Para finaSchoolNewsUrl(){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ParaMapper mapper = sqlSession.getMapper(ParaMapper.class);
        Para para = mapper.finaSchoolNewsUrl();
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return para;
    }

    /**
     * @Title finaSchoolNewsInfoUrl
     * @description 查询高校的最新消息详情Url
     * @author 郑洁文
     * @date 2022年8月9日 下午15:32
     * @return
     */
    public Para finaSchoolNewsInfoUrl(){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ParaMapper mapper = sqlSession.getMapper(ParaMapper.class);
        Para para = mapper.finaSchoolNewsInfoUrl();
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return para;
    }

    /**
     * @Title finaSchoolMajorUrl
     * @description 查询高校的开设专业Url
     * @author 郑洁文
     * @date 2022年8月11日 下午14:57
     * @return
     */
    public Para finaSchoolMajorUrl(){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ParaMapper mapper = sqlSession.getMapper(ParaMapper.class);
        Para para = mapper.finaSchoolMajorUrl();
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return para;
    }

    /**
     * @Title finaSchoolMajorInfoUrl
     * @description 查询高校的开设专业详情Url
     * @author 郑洁文
     * @date 2022年8月11日 下午16:43
     * @return
     */
    public Para finaSchoolMajorInfoUrl(){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ParaMapper mapper = sqlSession.getMapper(ParaMapper.class);
        Para para = mapper.finaSchoolMajorInfoUrl();
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return para;
    }
}
