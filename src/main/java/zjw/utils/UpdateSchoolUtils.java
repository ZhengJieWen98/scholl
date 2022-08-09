package zjw.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import zjw.pojo.Para;
import zjw.pojo.School;
import zjw.pojo.SchoolNews;
import zjw.pojo.SchoolNewsInfo;
import zjw.service.*;
import zjw.service.imp.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateSchoolUtils {
    private static ParaService paraService = new ParaServiceImp();
    private static SchoolService schoolService = new SchoolServiceImp();
    private static SchoolNewsService schoolNewsService = new SchoolNewsServiceImp();
    private static SchoolNewsInfoService schoolNewsInfoService = new SchoolNewsServiceInfoImp();
    private static Para SchoolNewsUrlPara = null;
    private static Para SchoolNewsInfoUrlPara = null;

    /**
     * @Title updateSchool
     * @description 更新高校信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     */
    public static void updateSchool() throws IOException {
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
     * @Title updateSchool
     * @description 更新所有的高校信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     */
    public static void updateSchoolNews() throws IOException {
        List<String> allSchoolId = schoolService.findAllSchoolId();
        for(int i=0;i<allSchoolId.size();i++){
            updateSchoolNews(allSchoolId.get(i));
        }
    }

    /**
     * @Title updateSchool
     * @description 更新高校信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     * @param school_id
     */
    public static void updateSchoolNews(String school_id) throws IOException {
        schoolNewsService.deleteSchoolNews(school_id);
        Map<String, String> mapUrl = analysisSchoolNewsUrl();
        String url = mapUrl.get("urlStart")+school_id+mapUrl.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        Object data = jsonObject.get("data");
        List<SchoolNews> schoolNews = JSON.parseArray(data.toString(), SchoolNews.class);
        List<SchoolNews> news = schoolNews.stream().limit(10).collect(Collectors.toList());
        schoolNewsService.addSchoolNews(news);
    }

    public static void updateSchoolNewsInfo() throws IOException {
        List<SchoolNews> list = schoolNewsService.findAllSchoolNewsSchoolIdAndTypeAndId();
        for(int i=0;i<list.size();i++){
            SchoolNews schoolNews = list.get(i);
            updateSchoolNewsInfo(schoolNews);
        }
    }

    /**
     * @Title updateSchoolNewsInfo
     * @description 更新高校信息详情
     * @author 郑洁文
     * @date 2022年8月8日 下午16:40
     * @param schoolNews
     */
    public static void updateSchoolNewsInfo(SchoolNews schoolNews) throws IOException {
        schoolNewsInfoService.deleteSchoolNewsInfo(schoolNews);
        Map<String, String> mapUrl = analysisSchoolNewsInfoUrl();
        String url = mapUrl.get("urlStart")+schoolNews.getSchoolId()+mapUrl.get("urlEnd")+schoolNews.getType()+"/"+schoolNews.getId()+".json";
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        Object data = jsonObject.get("data");
        SchoolNewsInfo schoolNewsInfo = JSON.parseObject(data.toString(), SchoolNewsInfo.class);
        schoolNewsInfoService.addSchoolNewsInfo(schoolNewsInfo);
    }



    /**
     * @Title analysisSchoolUrl
     * @description 解析高校url地址
     * @author 郑洁文
     * @date 2022年8月8日 下午15:52
     * @param schoolUrl
     */
    public static Map<String,String> analysisSchoolUrl(String schoolUrl){
        Map<String,String> map = new HashMap<String, String>();
        String[] split = schoolUrl.split("page=");
        int index = split[1].indexOf("&");
        map.put("urlStart",split[0]+"page=");
        map.put("urlEnd",split[1].substring(index));
        return map;
    }

    /**
     * @Title analysisSchoolNewsUrl
     * @description 解析高校高校招生快讯url地址
     * @author 郑洁文
     * @date 2022年8月9日 上午11:22
     */
    public static Map<String,String> analysisSchoolNewsUrl(){
        if(SchoolNewsUrlPara==null){
            SchoolNewsUrlPara = paraService.finaSchoolNewsUrl();
        }
        return analysis(SchoolNewsUrlPara.getPARAVALUE());
    }

    /**
     * @Title analysisSchoolNewsInfoUrl
     * @description 解析高校高校招生快讯详情url地址
     * @author 郑洁文
     * @date 2022年8月9日 下午16:45
     */
    public static Map<String,String> analysisSchoolNewsInfoUrl(){
        if(SchoolNewsInfoUrlPara==null){
            SchoolNewsInfoUrlPara = paraService.finaSchoolNewsInfoUrl();
        }
        return analysis(SchoolNewsInfoUrlPara.getPARAVALUE());
    }


    public static Map<String,String> analysis(String url){
        String[] split = url.split("school_id");
        Map<String,String> map = new HashMap<String, String>();
        map.put("urlStart",split[0]);
        map.put("urlEnd",split[1]);
        return map;
    }


}
