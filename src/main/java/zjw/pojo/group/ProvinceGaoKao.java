package zjw.pojo.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zjw.pojo.ProvinceGaoKaoInfo;

import java.util.List;

/**
 * @ClassName ProvinceGaoKao
 * @description 省高考咨询组合类
 * @author 郑洁文
 * @date 2022年8月16日 下午14:05
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceGaoKao {
    private String classid;
    private String classname;
    private String url;
    private List<ProvinceGaoKaoInfo> article;
}
