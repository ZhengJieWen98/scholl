package zjw.mapper;

import zjw.pojo.SchoolNews;

import java.util.List;

public interface SchoolNewsMapper {
    /**
     * @Title addSchoolNews
     * @description 批量添加学校消息
     * @author 郑洁文
     * @date 2022年8月9日 上午11:30
     * @param list
     * @return
     */
    int addSchoolNewsList(List<SchoolNews> list);

    /**
     * @Title addSchoolNews
     * @description 添加学校消息
     * @author 郑洁文
     * @date 2022年8月10日 上午9:11
     * @param schoolNews
     * @return
     */
    int addSchoolNews(SchoolNews schoolNews);

    /**
     * @Title deleteSchoolNews
     * @description delete学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午13:51
     * @param schoolNews
     * @return
     */
    int deleteSchoolNews(SchoolNews schoolNews);


    /**
     * @Title findAllSchoolNewsSchoolIdAndTypeAndId
     * @description 查询所有的SchoolId,Type,Id学校消息
     * @author 郑洁文
     * @date 2022年8月9日 下午16:33
     * @return
     */
    List<SchoolNews> findAllSchoolNewsSchoolIdAndTypeAndId();

}
