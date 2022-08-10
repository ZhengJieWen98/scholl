package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolNewsMapper;
import zjw.pojo.SchoolNews;
import zjw.service.SchoolNewsService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class SchoolNewsServiceImp implements SchoolNewsService {

    /**
     * @Title addSchoolNewsList
     * @description 批量添加学校消息
     * @author 郑洁文
     * @date 2022年8月9日 中午12:34
     * @param list
     * @return
     */
    public int addSchoolNewsList(List<SchoolNews> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsMapper mapper = sqlSession.getMapper(SchoolNewsMapper.class);
        int i = mapper.addSchoolNewsList(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title addSchoolNews
     * @description 添加学校消息
     * @author 郑洁文
     * @date 2022年8月10日 上午9:11
     * @param schoolNews
     * @return
     */
    public int addSchoolNews(SchoolNews schoolNews){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsMapper mapper = sqlSession.getMapper(SchoolNewsMapper.class);
        int i = mapper.addSchoolNews(schoolNews);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title deleteSchoolNews
     * @description 批量delete学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午13:51
     * @param schoolNews
     * @return
     */
    public int deleteSchoolNews(SchoolNews schoolNews){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolNewsMapper mapper = sqlSession.getMapper(SchoolNewsMapper.class);
        int i = mapper.deleteSchoolNews(schoolNews);
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
