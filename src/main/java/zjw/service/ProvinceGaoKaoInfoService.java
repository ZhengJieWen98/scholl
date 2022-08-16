package zjw.service;

import zjw.pojo.ProvinceGaoKaoInfo;

import java.util.List;

public interface ProvinceGaoKaoInfoService {
    /**
     * @Title addProvinceGaoKaoInfoList
     * @description 批量添加省份高考咨询
     * @author 郑洁文
     * @date 2022年8月16日 下午11:06
     * @param list
     * @return
     */
    int addProvinceGaoKaoInfoList(List<ProvinceGaoKaoInfo> list);

    /**
     * @Title selectProvinceIdAll
     * @description 批量添加省份高考咨询
     * @author 郑洁文
     * @date 2022年8月16日 下午14:19
     * @return
     */
    List<String> selectProvinceIdAll();
}
