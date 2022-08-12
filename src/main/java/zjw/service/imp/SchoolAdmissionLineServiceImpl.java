package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.SchoolAdmissionLineMapper;
import zjw.pojo.SchoolAdmissionLine;
import zjw.service.SchoolAdmissionLineService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class SchoolAdmissionLineServiceImpl implements SchoolAdmissionLineService {
    /**
     * @Title addSchoolAdmissionLineList
     * @description 添加学校录取线
     * @author 郑洁文
     * @date 2022年8月12日 下午17:25
     * @param list
     * @return
     */
    public int addSchoolAdmissionLineList(List<SchoolAdmissionLine> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        SchoolAdmissionLineMapper mapper = sqlSession.getMapper(SchoolAdmissionLineMapper.class);
        int i = mapper.addSchoolAdmissionLineList(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }
}
