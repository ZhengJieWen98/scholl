package zjw.service;

import zjw.pojo.SchoolNews;

import java.util.List;

public interface SchoolNewsService {

    /**
     * @Title addSchoolNews
     * @description 批量添加学校消息
     * @author 郑洁文
     * @date 2022年8月9日 中午12:34
     * @param list
     * @return
     */
    int addSchoolNews(List<SchoolNews> list);

    /**
     * @Title deleteSchoolNews
     * @description 批量delete学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午13:51
     * @param school_id
     * @return
     */
    int deleteSchoolNews(String school_id);

    /**
     * @Title findAllSchoolNewsSchoolIdAndTypeAndId
     * @description 查询所有的SchoolId,Type,Id学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午16:33
     * @return
     */
    List<SchoolNews> findAllSchoolNewsSchoolIdAndTypeAndId();
}