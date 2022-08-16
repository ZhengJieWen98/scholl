package zjw.mapper;

import org.apache.ibatis.annotations.Param;

public interface BatchMapper {
    int addBatch(@Param("batch") String batch,@Param("batch_name") String batch_name);
    String selectBatch(@Param("batch") String batch);
}
