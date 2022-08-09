package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName SchoolNewsInfo
 * @description 高校最新资讯消息详情
 * @author 郑洁文
 * @date 2022年8月9日 下午15:00
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolNewsInfo implements Serializable {
    private String addTime;
    private String content;
    private String id;
    private String isFull;
    private String isSell;
    private String schoolId;
    private String schoolName;
    private String status;
    private String title;
    private String type;
    private String typeName;
}
