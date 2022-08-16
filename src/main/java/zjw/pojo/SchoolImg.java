package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片真实地址:https://static-data.gaokao.cn/+url
 * @ClassName SchoolIMG
 * @description 高校图片
 * @author 郑洁文
 * @date 2022年8月16日 下午17:40
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolImg {
    private String b_url;
    private String m_url;
    private String rank;
    private String school_id;
    private String title;
    private String url;

}
