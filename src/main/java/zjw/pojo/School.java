package zjw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName School
 * @description 高校
 * @author 郑洁文
 * @date 2022年8月3日 下午14:00
 * @Version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School implements Serializable {
    //地址
    private String address;
    //属于
    private String belong;
    //省份
    private String province_name;
    //城市
    private String city_name;
    //县
    private String county_name;
    //类别
    private String dual_class_name;
    //普通本科
    private String level_name;
    //高校名称
    private String name;
    //公办,民办
    private String nature_name;
    //查询高校详情信息对应的id
    private String school_id;
    //类型名称
    private String type_name;
}
