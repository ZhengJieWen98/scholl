package zjw.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 1. 类加载的时候就创建出来了一个SqlSessionFactory对象
 * 2. 我们得声明一个方法，用于创建SqlSession对象,因为每次执行SQL语句的sqlSession对象不能是同一个
 */
public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //1 将主配置文件SqlMapConfig.xml转换成字节输入流
            InputStream is = Resources.getResourceAsStream("MyBatis.xml");
            //2 创建一个SqlSessionFactoryBuilder
            SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
            //3 使用factoryBuilder对象加载字节输入流，创建SqlSessionFactory对象
            sqlSessionFactory = factoryBuilder.build(is); //使用了构建者模式

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建SqlSession对象
     * @return
     */
    public static SqlSession openSqlSession(){
        return sqlSessionFactory.openSession();
    }

    /**
     * 提交事务并且关闭sqlSession
     * @param sqlSession
     */
    public static void commitAndClose(SqlSession sqlSession){
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 回滚事务并且关闭sqlSession
     * @param sqlSession
     */
    public static void rollbackAndClose(SqlSession sqlSession){
        sqlSession.rollback();
        sqlSession.close();
    }
}

