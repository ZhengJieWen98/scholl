package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SchoolMajorFeature
 * @description 高校特色专业
 * @author 郑洁文
 * @date 2022年8月11日 下午14:21
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolMajorFeature {
    private String code;
    private String id;
    private String is_important;
    private String is_video;
    private String level3_code;
    private String level3_id;
    private String level3_name;
    private String level3_weight;
    private String limit_year;
    private String nation_feature;
    private String province_feature;
    private String school_id;
    private String special_id;
    private String special_name;
    private String special_type;
    private String type_name;
    private String year;

}
