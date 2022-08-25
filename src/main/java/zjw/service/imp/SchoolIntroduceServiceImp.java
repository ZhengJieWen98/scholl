package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolIntroduceMapper;
import zjw.pojo.SchoolIntroduce;
import zjw.service.SchoolIntroduceService;
import zjw.utils.SqlSessionFactoryUtil;

public class SchoolIntroduceServiceImp implements SchoolIntroduceService {

    /**
     * @Title addSchoolIntroduce
     * @description 添加学校介绍信息
     * @author 郑洁文
     * @date 2022年8月25日 下午14:15
     * @return
     */
    public int addSchoolIntroduce(SchoolIntroduce schoolIntroduce){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolIntroduceMapper mapper = sqlSession.getMapper(SchoolIntroduceMapper.class);
        int i = mapper.addSchoolIntroduce(schoolIntroduce);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
