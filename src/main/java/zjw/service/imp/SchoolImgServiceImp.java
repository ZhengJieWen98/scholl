package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolImgMapper;
import zjw.pojo.SchoolImg;
import zjw.service.SchoolImgService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class SchoolImgServiceImp implements SchoolImgService {
    /**
     * @Title addSchoolZsjhList
     * @description 批量添加学校招生计划
     * @author 郑洁文
     * @date 2022年8月15日 下午16:29
     * @param list
     * @return
     */
    public int addSchoolImgList(List<SchoolImg> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolImgMapper mapper = sqlSession.getMapper(SchoolImgMapper.class);
        int i = mapper.addSchoolImgList(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
