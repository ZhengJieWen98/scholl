package zjw.pojo.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zjw.pojo.SchoolMajorFeature;
import zjw.pojo.SchoolMajorType;

import java.util.List;

/**
 * @ClassName SchoolMajorGroup
 * @description 高校专业组合类
 * @author 郑洁文
 * @date 2022年8月11日 下午14:41
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolMajorGroup {
    private List<SchoolMajorFeature> nation_feature;
    private List<SchoolMajorType> special;
}
