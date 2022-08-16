package zjw.service.imp;

import org.apache.ibatis.session.SqlSession;
import zjw.mapper.ProvinceGaoKaoInfoMapper;
import zjw.pojo.ProvinceGaoKaoInfo;
import zjw.service.ProvinceGaoKaoInfoService;
import zjw.utils.SqlSessionFactoryUtil;

import java.util.List;

public class ProvinceGaoKaoInfoServiceImp implements ProvinceGaoKaoInfoService {
    /**
     * @Title addProvinceGaoKaoInfoList
     * @description 批量添加省份高考咨询
     * @author 郑洁文
     * @date 2022年8月16日 下午11:06
     * @param list
     * @return
     */
    public int addProvinceGaoKaoInfoList(List<ProvinceGaoKaoInfo> list){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ProvinceGaoKaoInfoMapper mapper = sqlSession.getMapper(ProvinceGaoKaoInfoMapper.class);
        int i = mapper.addProvinceGaoKaoInfoList(list);
        SqlSessionFactoryUtil.commitAndClose(sqlSession);
        return i;
    }

    /**
     * @Title selectProvinceIdAll
     * @description 批量添加省份高考咨询
     * @author 郑洁文
     * @date 2022年8月16日 下午14:19
     * @return
     */
    public List<String> selectProvinceIdAll(){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ProvinceGaoKaoInfoMapper mapper = sqlSession.getMapper(ProvinceGaoKaoInfoMapper.class);
        return mapper.selectProvinceIdAll();
    }
}
