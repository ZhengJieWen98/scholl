package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Para
 * @description 参数
 * @author 郑洁文
 * @date 2022年8月8日 下午14:55
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Para implements Serializable {
    //参数类别
    private String PARACLASS;
    //参数姓名
    private String PARANAME;
    //参数值
    private String PARAVALUE;
}
