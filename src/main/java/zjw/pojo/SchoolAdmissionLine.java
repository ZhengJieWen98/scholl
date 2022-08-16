package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName School
 * @description 高校录取线
 * @author 郑洁文
 * @date 2022年8月12日 下午17:13
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolAdmissionLine {
    private String province_id;
    private String year;
    private String school_id;
    private String type;
    private String min;
    private String min_section;
    private String proscore;
    private String zslx_name;
    private String local_batch_name;
    private String batch;

}
