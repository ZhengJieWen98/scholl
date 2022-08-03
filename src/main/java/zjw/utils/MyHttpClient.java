package zjw.utils;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http客户端工具
 */
public class MyHttpClient {

    public static void main(String[] args) throws IOException {
        String html = fetchHtmlSync("http://47.108.249.189:8080/diary/java/43java/day43.html");
        System.out.println(html);
    }

    /**
     * okhttp客户端
     */
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient
            .Builder()
            //设置连接超时时间为30秒
            .connectTimeout(30, TimeUnit.SECONDS)
            //自定义线程池任务调度策略
            //.dispatcher(new Dispatcher(new ThreadPoolExecutor(1, 1, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<>(6000), r -> new Thread("spider task"))))
            //设置代理，所以在代码中没有使用代理ip进行抓取，而正常实践工作中抓取数据一般都会使用代理ip，大家可以自己加入代理ip进行爬取
            //.proxy();
            .connectionPool(new ConnectionPool(2, 1, TimeUnit.MINUTES))
            .build();

    /**
     * 同步获取网页数据
     *
     * @param url 网站地址
     * @return 网页内容
     * @throws IOException
     */
    public static String fetchHtmlSync(String url) throws IOException {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder().url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36")
                .addHeader("Referer", "http://www.weather.com.cn/")
                .addHeader("Cookie", "f_city=%E9%95%BF%E6%B2%99%7C101250101%7C; Hm_lvt_080dabacb001ad3dc8b9b9049b36d43b=1646805682,1647329569,1647396910; Hm_lpvt_080dabacb001ad3dc8b9b9049b36d43b=1647410932")
                .build();
        return OK_HTTP_CLIENT.newCall(request).execute().body().string();
    }

}

