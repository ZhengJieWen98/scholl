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
}
