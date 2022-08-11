package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName SchoolMajor
 * @description 高校专业类型
 * @author 郑洁文
 * @date 2022年8月11日 下午14:23
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolMajorType {
    private String code;
    private String level3_weight;
    private String name;
    private String school_id;
    private List<SchoolMajor> special;
}
