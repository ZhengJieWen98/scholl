package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName SchoolNews
 * @description 高校最新资讯消息
 * @author 郑洁文
 * @date 2022年8月9日 上午11:00
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolNews implements Serializable {
    private String id;
    private String isFull;
    private String isSingle;
    private String schoolId;
    private String title;
    private String type;
    private String updateTime;
    private String year;

}
