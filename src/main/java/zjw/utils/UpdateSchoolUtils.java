package zjw.utils;

import com.alibaba.fastjson.JSONObject;

import zjw.pojo.Para;
import zjw.pojo.School;
import zjw.service.ParaService;
import zjw.service.SchoolService;
import zjw.service.imp.ParaServiceImp;
import zjw.service.imp.SchoolServiceImp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateSchoolUtils {
    private static ParaService paraService = new ParaServiceImp();
    private static SchoolService schoolService = new SchoolServiceImp();
    /**
     * @Title update
     * @description 更新高校信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     */
    public static void update() throws IOException {
        List<Para> paras = paraService.finaAllSchoolUrls();
        for(Para para:paras){
            /*
               可开启线程,提高效率。
             */
            Map<String, String> map = analysisSchoolUrl(para.getPARAVALUE());
            int countPage = 1;//默认总页数1页
            for(int i=1;i<=countPage;i++){
                String url=map.get("urlStart")+i+map.get("urlEnd");
                String jsonString = MyHttpClient.fetchHtmlSync(url);
                JSONObject resout = JSONObject.parseObject(jsonString);
                JSONObject data = JSONObject.parseObject(resout.get("data").toString());
                int numFound = Integer.parseInt(data.get("numFound").toString());
                countPage = numFound%20==0?numFound/20:numFound/20+1;
                Object item = data.get("item");
                List<School> schools = JSONObject.parseArray(item.toString(), School.class);
                for(School school:schools){
                    schoolService.addSchool(school);
                }
            }
        }
    }



    /**
     * @Title analysisSchoolUrl
     * @description 解析高校url地址
     * @author 郑洁文
     * @date 2022年8月8日 下午15:52
     */
    public static Map<String,String> analysisSchoolUrl(String schoolUrl){
        Map<String,String> map = new HashMap<String, String>();
        String[] split = schoolUrl.split("page=");
        int index = split[1].indexOf("&");
        map.put("urlStart",split[0]+"page=");
        map.put("urlEnd",split[1].substring(index));
        return map;
    }


}
