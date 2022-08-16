package zjw.service;

import zjw.pojo.Para;

import java.util.List;

public interface ParaService {
    /**
     * @Title finaAllSchoolUrls
     * @description 查询所有高校的url地址
     * @author 郑洁文
     * @date 2022年8月8日 下午15:10
     * @return
     */
    List<Para> finaAllSchoolUrls();

    /**
     * @Title finaSchoolNews
     * @description 查询高校的最新消息Url
     * @author 郑洁文
     * @date 2022年8月9日 上午11:05
     * @return
     */
    Para finaSchoolNewsUrl();

    /**
     * @Title finaSchoolNewsInfoUrl
     * @description 查询高校的最新消息详情Url
     * @author 郑洁文
     * @date 2022年8月9日 下午15:32
     * @return
     */
    Para finaSchoolNewsInfoUrl();

    /**
     * @Title finaSchoolMajorUrl
     * @description 查询高校的开设专业Url
     * @author 郑洁文
     * @date 2022年8月11日 下午14:57
     * @return
     */
    Para finaSchoolMajorUrl();

    /**
     * @Title finaSchoolMajorInfoUrl
     * @description 查询高校的开设专业详情Url
     * @author 郑洁文
     * @date 2022年8月11日 下午16:43
     * @return
     */
    Para finaSchoolMajorInfoUrl();

    /**
     * @Title finaSchoolEnrollAllUrl
     * @description 查询获取高校所有省录取线(school_id学校id)Url
     * @author 郑洁文
     * @date 2022年8月1２日 下午16:04
     * @return
     */
    Para finaSchoolEnrollAllUrl();

    /**
     * @Title finaSchoolZSJHUrl
     * @description 查询获取高校所有省招生计划(school_id学校id)Url
     * @author 郑洁文
     * @date 2022年8月15日 下午15:25
     * @return
     */
    Para finaSchoolZSJHUrl();

    /**
     * @Title finaSchoolMajorLineUrl
     * @description 查询获取高校所有省专业录取线(school_id学校id)Url
     * @author 郑洁文
     * @date 2022年8月16日 上午9:31
     * @return
     */
    Para finaSchoolMajorLineUrl();

    /**
     * @Title finaProvinceGaoKaoInfoUrl
     * @description 获取省份高考咨询Url
     * @author 郑洁文
     * @date 2022年8月16日 上午10:59
     * @return
     */
    Para finaProvinceGaoKaoInfoUrl();
}
