package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SchoolMajor
 * @description 高校专业详情
 * @author 郑洁文
 * @date 2022年8月11日 下午16:30
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolMajorInfo {
    private String content;
    private String degree;
    private String id;
    private String level1_name;
    private String level2_name;
    private String level3_name;
    private String limit_year;
    private String name;
    private String nation_feature;
    private String province_feature;
    private String school_id;
    private String special_id;
    private String status;
}
