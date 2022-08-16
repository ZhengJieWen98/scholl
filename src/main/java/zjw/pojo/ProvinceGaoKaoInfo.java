package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ProvinceGaoKaoInfo
 * @description 省份高考咨询
 * @author 郑洁文
 * @date 2022年8月16日 上午11:01
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceGaoKaoInfo {
    private String province_id;
    private String add_time;
    private String docsource;
    private String title;
    private String url;

}
