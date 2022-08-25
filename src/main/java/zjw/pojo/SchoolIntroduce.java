package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SchoolInfo
 * @description 高校
 * @author 郑洁文
 * @date 2022年8月25日 下午14:12
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolIntroduce {
    private String content;
    private String id;
    private String school_id;
    private String status;
    private String type_name;
    private String update_time;
}
