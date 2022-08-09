package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolNewsMapper;
import zjw.pojo.SchoolNews;
import zjw.service.SchoolNewsService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class SchoolNewsServiceImp implements SchoolNewsService {

    /**
     * @Title addSchoolNews
     * @description 批量添加学校消息
     * @author 郑洁文
     * @date 2022年8月9日 中午12:34
     * @param list
     * @return
     */
    public int addSchoolNews(List<SchoolNews> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsMapper mapper = sqlSession.getMapper(SchoolNewsMapper.class);
        int i = mapper.addSchoolNews(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title deleteSchoolNews
     * @description 批量delete学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午13:51
     * @param school_id
     * @return
     */
    public int deleteSchoolNews(String school_id){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsMapper mapper = sqlSession.getMapper(SchoolNewsMapper.class);
        int i = mapper.deleteSchoolNews(school_id);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title findAllSchoolNewsSchoolIdAndTypeAndId
     * @description 查询所有的SchoolId,Type,Id学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午16:33
     * @return
     */
    public List<SchoolNews> findAllSchoolNewsSchoolIdAndTypeAndId(){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsMapper mapper = sqlSession.getMapper(SchoolNewsMapper.class);
        return mapper.findAllSchoolNewsSchoolIdAndTypeAndId();
    }
}
