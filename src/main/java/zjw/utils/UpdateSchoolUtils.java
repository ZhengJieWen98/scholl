package zjw.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import zjw.exception.VisitException;
import zjw.pojo.*;
import zjw.pojo.group.SchoolMajorGroup;
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
    private static SchoolMajorFeatureService schoolMajorFeatureService = new SchoolMajorFeatureServiceImp();
    private static SchoolMajorTyperService schoolMajorTyperService = new SchoolMajorTyperServiceImp();
    private static SchoolMajorService schoolMajorService = new SchoolMajorServiceImp();
    private static SchoolMajorInfoService schoolMajorInfoService = new SchoolMajorInfoServiceImp();
    private static Map<String,String> schoolNewsUrlMap = null;
    private static Map<String,String> schoolNewsInfoUrlMap = null;
    private static Map<String,String> SchoolMajorUrlMap = null;
    private static Map<String,String> SchoolMajorInfoUrlMap = null;

    /**
     * @Title updateSchool
     * @description 更新所有高校信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     */
    public static void updateSchool(){
        List<Para> paras = paraService.finaAllSchoolUrls();
        for(Para para:paras){
            /*
               可开启线程,提高效率。
             */
            Map<String, String> map = analysisSchoolUrl(para.getPARAVALUE());
            int countPage = 1;//默认总页数1页
            for(int i=1;i<=countPage;i++){
                String url=map.get("urlStart")+i+map.get("urlEnd");
                String jsonString = null;
                try {
                    jsonString = MyHttpClient.fetchHtmlSync(url);
                    JSONObject resout = JSONObject.parseObject(jsonString);
                    if("0000".equals(resout.get("code").toString())){
                        JSONObject data = JSONObject.parseObject(resout.get("data").toString());
                        int numFound = Integer.parseInt(data.get("numFound").toString());
                        countPage = numFound%20==0?numFound/20:numFound/20+1;
                        Object item = data.get("item");
                        List<School> schools = JSONObject.parseArray(item.toString(), School.class);
                        for(School school:schools){
                            schoolService.deleteSchool(school);
                            schoolService.addSchool(school);
                            updateSchoolNews(school.getSchool_id());
                        }
                    }else{
                        //访问频繁,暂停5分钟
                        Thread.sleep(1000*60*5);
                        i--;
                    }
                    //避免访问频繁,暂停5s
                    Thread.sleep(1000*5);
                } catch (IOException e) {
                    //无法访问,跳过
                    e.printStackTrace();
                } catch (VisitException e) {
                    //子操作访问频繁
                    e.printStackTrace();
                    //子操作访问频繁,暂停5分钟
                    try {
                        Thread.sleep(1000*60*5);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    i--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Title updateSchoolNews
     * @description 更新所有的高校资讯信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     */
    public static void updateSchoolNews(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
//        for(int i=0;i<allSchoolId.size();i++){
//            try {
//                updateSchoolNews(allSchoolId.get(i));
//            } catch (IOException e) {
//                //无法访问,跳过
//                e.printStackTrace();
//            } catch (VisitException e) {
//                //访问频繁,暂停访问5分钟
//                e.printStackTrace();
//                try {
//                    Thread.sleep(1000*60*5);
//                } catch (InterruptedException interruptedException) {
//                    interruptedException.printStackTrace();
//                }
//                i--;
//            }
//        }

        //开启线程
        int size = allSchoolId.size();
        int pageSize = 30;
        int countPage =size%pageSize==0?size/pageSize:size/pageSize+1;
        for(int i=1;i<=countPage;i++){
            int start = (i-1)*pageSize;
            int end = i==countPage?size:i*pageSize;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=start;j<end;j++){
                        try {
                            updateSchoolNews(allSchoolId.get(j));
                        } catch (IOException e) {
                            //无法访问,跳过
                            e.printStackTrace();
                        } catch (VisitException e) {
                            //访问频繁,暂停访问5分钟
                            e.printStackTrace();
                            try {
                                Thread.sleep(1000*60*5);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            j--;
                        }
                    }
                }
            }).start();


        }

    }

    /**
     * @Title updateSchool
     * @description 更新高校资讯信息
     * @author 郑洁文
     * @date 2022年8月8日 下午15:32
     * @param school_id
     */
    public static void updateSchoolNews(String school_id) throws IOException,VisitException {
        Map<String, String> mapUrl = analysisSchoolNewsUrl();
        String url = mapUrl.get("urlStart")+school_id+mapUrl.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        //成功获取
        if("0000".equals(jsonObject.get("code").toString())){
            Object data = jsonObject.get("data");
            List<SchoolNews> schoolNews = JSON.parseArray(data.toString(), SchoolNews.class);
            List<SchoolNews> news = schoolNews.stream().limit(10).collect(Collectors.toList());
            //schoolNewsService.addSchoolNews(news);
            for(SchoolNews item:news){
                schoolNewsService.deleteSchoolNews(item);
                schoolNewsService.addSchoolNews(item);
                updateSchoolNewsInfo(item);
            }
        }else{
            throw new VisitException("访问频繁,没有获取到数据");
        }

    }

    /**
     * @Title updateSchoolNewsInfo
     * @description 更新所有的高校资讯信息详情
     * @author 郑洁文
     * @date 2022年8月8日 下午16:40
     */
    public static void updateSchoolNewsInfo() {
        List<SchoolNews> list = schoolNewsService.findAllSchoolNewsSchoolIdAndTypeAndId();

        for(int i=0;i<list.size();i++){
            try {
                updateSchoolNewsInfo(list.get(i));
            } catch (IOException e) {
                //无法访问,跳过
                e.printStackTrace();
            } catch (VisitException e) {
                //访问频繁,暂停访问5分钟
                e.printStackTrace();
                try {
                    Thread.sleep(1000*60*5);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                i--;
            }
        }

//        //开启线程
//        int size = list.size();
//        int pageSize = 100;
//        int countPage =size%pageSize==0?size/pageSize:size/pageSize+1;
//        for(int i=1;i<=countPage;i++){
//            int start = (i-1)*100;
//            int end = i==countPage?size:i*pageSize;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for(int j=start;j<end;j++){
//                        try {
//                            updateSchoolNewsInfo(list.get(j));
//                        } catch (IOException e) {
//                            //无法访问,跳过
//                            e.printStackTrace();
//                        } catch (VisitException e) {
//                            //访问频繁,暂停访问5分钟
//                            e.printStackTrace();
//                            try {
//                                Thread.sleep(1000*60*5);
//                            } catch (InterruptedException interruptedException) {
//                                interruptedException.printStackTrace();
//                            }
//                            j--;
//                        }
//                    }
//                }
//            }).start();
//        }

    }

    /**
     * @Title updateSchoolNewsInfo
     * @description 更新高校资讯信息详情
     * @author 郑洁文
     * @date 2022年8月8日 下午16:40
     * @param schoolNews
     */
    public static void updateSchoolNewsInfo(SchoolNews schoolNews) throws IOException,VisitException {
        Map<String, String> mapUrl = analysisSchoolNewsInfoUrl();
        String url = mapUrl.get("urlStart")+schoolNews.getSchoolId()+mapUrl.get("urlEnd")+schoolNews.getType()+"/"+schoolNews.getId()+".json";
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        //成功获取
        if("0000".equals(jsonObject.get("code").toString())){
            Object data = jsonObject.get("data");
            SchoolNewsInfo schoolNewsInfo = JSON.parseObject(data.toString(), SchoolNewsInfo.class);
            schoolNewsInfoService.deleteSchoolNewsInfo(schoolNews);
            schoolNewsInfoService.addSchoolNewsInfo(schoolNewsInfo);
        }else{
            throw new VisitException("访问频繁,没有获取到数据");
        }
    }

    /**
     * @Title updateSchoolMajor
     * @description 更新高校所有开设专业信息
     * @author 郑洁文
     * @date 2022年8月11日 下午17:10
     */
    public static void updateSchoolMajor(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //开启线程
        int size = allSchoolId.size();
        int pageSize = 5;
        int countPage =size%pageSize==0?size/pageSize:size/pageSize+1;
        for(int i=1;i<=countPage;i++){
            int start = (i-1)*pageSize;
            int end = i==countPage?size:i*pageSize;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=start;j<end;j++){
                        try {
                            updateSchoolMajor(allSchoolId.get(j));
                        } catch (IOException e) {
                            //无法访问,跳过
                            e.printStackTrace();
                        } catch (VisitException e) {
                            //访问频繁,暂停访问5分钟
                            e.printStackTrace();
                            try {
                                Thread.sleep(1000*60*5);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            j--;
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * @Title updateSchoolMajor
     * @description 更新高校开设专业信息
     * @author 郑洁文
     * @date 2022年8月11日 下午15:18
     * @param school_id
     */
    public static void updateSchoolMajor(String school_id) throws IOException,VisitException {
        Map<String, String> mapUrl = analysisSchoolMajorUrl();
        String url = mapUrl.get("urlStart")+school_id+mapUrl.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if("0000".equals(jsonObject.get("code").toString())){
            Object data = jsonObject.get("data");
            SchoolMajorGroup schoolMajorGroup = JSON.parseObject(data.toString(), SchoolMajorGroup.class);
            List<SchoolMajorFeature> nation_feature = schoolMajorGroup.getNation_feature();

            if(nation_feature.size()>0){
                schoolMajorFeatureService.deleteSchoolMajorFeature(school_id);
                schoolMajorFeatureService.addSchoolMajorFeatureList(nation_feature);
            }

            List<SchoolMajorType> special = schoolMajorGroup.getSpecial();
            for(SchoolMajorType schoolMajorType:special){
                schoolMajorType.setSchool_id(school_id);
                //
                schoolMajorTyperService.deleteSchoolMajorType(schoolMajorType);
                schoolMajorTyperService.addSchoolMajorType(schoolMajorType);
                //
                for(SchoolMajor schoolMajor:schoolMajorType.getSpecial()){
                    schoolMajorService.deleteSchoolMajor(schoolMajor);
                }
                schoolMajorService.addSchoolMajorList(schoolMajorType.getSpecial());

                updateSchoolMajorInfo(schoolMajorType.getSpecial());

            }

        }else{
            throw new VisitException("未获取到数据！");
        }
    }

    /**
     * @Title updateSchoolMajorInfo
     * @description 更新高校开设专业信息
     * @author 郑洁文
     * @date 2022年8月11日 下午16:59
     * @param schoolMajorInfo
     */
    public static void updateSchoolMajorInfo(SchoolMajorInfo schoolMajorInfo){
        schoolMajorInfoService.deleteSchoolMajorInfo(schoolMajorInfo);
        schoolMajorInfoService.addSchoolMajorInfo(schoolMajorInfo);
    }

    /**
     * @Title updateSchoolMajorInfo
     * @description 更新高校开设专业信息
     * @author 郑洁文
     * @date 2022年8月11日 下午17:00
     * @param schoolMajorList
     */
    public static void updateSchoolMajorInfo(List<SchoolMajor> schoolMajorList) throws IOException, VisitException {
        for(SchoolMajor schoolMajor:schoolMajorList){
            String school_id = schoolMajor.getSchool_id();
            String id = schoolMajor.getId();
            Map<String, String> urlMap = analysisSchoolMajorInfoUrl();
            String url = urlMap.get("urlStart")+school_id+urlMap.get("urlEnd")+id+".json";
            String s = MyHttpClient.fetchHtmlSync(url);
            JSONObject jsonObject = JSONObject.parseObject(s);
            if("0000".equals(jsonObject.get("code").toString())){
                Object data = jsonObject.get("data");
                SchoolMajorInfo schoolMajorInfo = JSON.parseObject(data.toString(), SchoolMajorInfo.class);
                updateSchoolMajorInfo(schoolMajorInfo);
            }else{
                throw new VisitException("未获取到数据！");
            }
        }
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
        if(schoolNewsUrlMap==null){
            Para SchoolNewsUrlPara = paraService.finaSchoolNewsUrl();
            schoolNewsUrlMap = analysis(SchoolNewsUrlPara.getPARAVALUE());
        }
        return schoolNewsUrlMap;
    }

    /**
     * @Title analysisSchoolNewsInfoUrl
     * @description 解析高校高校招生快讯详情url地址
     * @author 郑洁文
     * @date 2022年8月9日 下午16:45
     */
    public static Map<String,String> analysisSchoolNewsInfoUrl(){
        if(schoolNewsInfoUrlMap==null){
            Para SchoolNewsInfoUrlPara = paraService.finaSchoolNewsInfoUrl();
            schoolNewsInfoUrlMap=analysis(SchoolNewsInfoUrlPara.getPARAVALUE());
        }
        return schoolNewsInfoUrlMap;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description 解析高校高校开设专业url地址
     * @author 郑洁文
     * @date 2022年8月11日 下午15:01
     */
    public static Map<String,String> analysisSchoolMajorUrl(){
        if(SchoolMajorUrlMap==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolMajorUrl();
            SchoolMajorUrlMap=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return SchoolMajorUrlMap;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description 解析高校高校开设专业详情url地址
     * @author 郑洁文
     * @date 2022年8月11日 下午16:50
     */
    public static Map<String,String> analysisSchoolMajorInfoUrl(){
        if(SchoolMajorInfoUrlMap==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolMajorInfoUrl();
            SchoolMajorInfoUrlMap=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return SchoolMajorInfoUrlMap;
    }

    public static Map<String,String> analysis(String url){
        String[] split = url.split("school_id");
        Map<String,String> map = new HashMap<String, String>();
        map.put("urlStart",split[0]);
        map.put("urlEnd",split[1]);
        return map;
    }


    public static void main(String[] args) throws IOException,VisitException {
        updateSchoolMajor();
    }
}
