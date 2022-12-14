package zjw.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.ibatis.session.SqlSession;
import zjw.exception.VisitException;
import zjw.mapper.BatchMapper;
import zjw.pojo.*;
import zjw.pojo.group.ProvinceGaoKao;
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
    private static SchoolAdmissionLineService schoolAdmissionLineService = new SchoolAdmissionLineServiceImpl();
    private static SchoolZsjhServiceImp schoolZsjhService = new SchoolZsjhServiceImp();
    private static SchoolMajorLineService schoolMajorLineService = new SchoolMajorLineServiceImp();
    private static ProvinceGaoKaoInfoService provinceGaoKaoInfoService = new ProvinceGaoKaoInfoServiceImp();
    private static SchoolInfoService schoolInfoService = new SchoolInfoServiceImp();
    private static SchoolIntroduceService schoolIntroduceService = new SchoolIntroduceServiceImp();
    private static Map<String,String> schoolNewsUrlMap = null;
    private static Map<String,String> schoolNewsInfoUrlMap = null;
    private static Map<String,String> schoolMajorUrlMap = null;
    private static Map<String,String> schoolMajorInfoUrlMap = null;
    private static Map<String,String> schoolEnrollAllUrlMap = null;
    private static Map<String,String> schoolZSJHUrl = null;
    private static Map<String,String> schoolMajorLineUrl = null;
    private static Map<String,String> proinceGaoKaoInfoUrl = null;
    private static Map<String,String> schoolInfoUrlUrl = null;

    /**
     * @Title updateSchool
     * @description ????????????????????????
     * @author ?????????
     * @date 2022???8???8??? ??????15:32
     */
    public static void updateAll(){
        List<Para> paras = paraService.finaAllSchoolUrls();
        for(Para para:paras){
            /*
               ???????????????,???????????????
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map = analysisSchoolUrl(para.getPARAVALUE());
                    int countPage = 1;//???????????????1???
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
                                    updateSchoolMajor(school.getSchool_id());
                                }
                            }else{
                                //????????????,??????5??????
                                Thread.sleep(1000*60*5);
                                i--;
                            }
                            //??????????????????,??????5s
                            Thread.sleep(1000*5);
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        } catch (VisitException e) {
                            //?????????????????????
                            e.printStackTrace();
                            //?????????????????????,??????5??????
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
            }).start();
        }
    }

    /**
     * @Title updateSchoolNews
     * @description ?????????????????????????????????
     * @author ?????????
     * @date 2022???8???8??? ??????15:32
     */
    public static void updateSchoolNews(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
        int size = allSchoolId.size();
        int pageSize = 10;
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
                            //????????????,??????
                            e.printStackTrace();
                        } catch (VisitException e) {
                            //????????????,????????????5??????
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
     * @description ????????????????????????
     * @author ?????????
     * @date 2022???8???8??? ??????15:32
     * @param school_id
     */
    public static void updateSchoolNews(String school_id) throws IOException,VisitException {
        Map<String, String> mapUrl = analysisSchoolNewsUrl();
        String url = mapUrl.get("urlStart")+school_id+mapUrl.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        //????????????
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
            throw new VisitException("????????????,?????????????????????");
        }

    }

    /**
     * @Title updateSchoolNewsInfo
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???8??? ??????16:40
     * @param schoolNews
     */
    public static void updateSchoolNewsInfo(SchoolNews schoolNews) throws IOException,VisitException {
        Map<String, String> mapUrl = analysisSchoolNewsInfoUrl();
        String url = mapUrl.get("urlStart")+schoolNews.getSchoolId()+mapUrl.get("urlEnd")+schoolNews.getType()+"/"+schoolNews.getId()+".json";
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        //????????????
        if("0000".equals(jsonObject.get("code").toString())){
            Object data = jsonObject.get("data");
            SchoolNewsInfo schoolNewsInfo = JSON.parseObject(data.toString(), SchoolNewsInfo.class);
            schoolNewsInfoService.deleteSchoolNewsInfo(schoolNews);
            schoolNewsInfoService.addSchoolNewsInfo(schoolNewsInfo);
        }else{
            throw new VisitException("????????????,?????????????????????");
        }
    }

    /**
     * @Title updateSchoolMajor
     * @description ????????????????????????????????????
     * @author ?????????
     * @date 2022???8???11??? ??????17:10
     */
    public static void updateSchoolMajor(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
        int size = allSchoolId.size();
        int pageSize = 10;
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
                            //????????????,??????
                            e.printStackTrace();
                        } catch (VisitException e) {
                            //????????????,????????????5??????
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
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???11??? ??????15:18
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
                //schoolMajorFeatureService.deleteSchoolMajorFeature(school_id);
                schoolMajorFeatureService.addSchoolMajorFeatureList(nation_feature);
            }
            List<SchoolMajorType> special = schoolMajorGroup.getSpecial();
            for(SchoolMajorType schoolMajorType:special){
                schoolMajorType.setSchool_id(school_id);
                //
                //schoolMajorTyperService.deleteSchoolMajorType(schoolMajorType);
                schoolMajorTyperService.addSchoolMajorType(schoolMajorType);
                //
//                for(SchoolMajor schoolMajor:schoolMajorType.getSpecial()){
//                    schoolMajorService.deleteSchoolMajor(schoolMajor);
//                }
                schoolMajorService.addSchoolMajorList(schoolMajorType.getSpecial());
                updateSchoolMajorInfo(schoolMajorType.getSpecial());
            }
        }else{
            throw new VisitException("?????????????????????");
        }
    }

    /**
     * @Title updateSchoolMajorInfo
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???11??? ??????16:59
     * @param schoolMajorInfo
     */
    public static void updateSchoolMajorInfo(SchoolMajorInfo schoolMajorInfo){
        //schoolMajorInfoService.deleteSchoolMajorInfo(schoolMajorInfo);
        schoolMajorInfoService.addSchoolMajorInfo(schoolMajorInfo);
    }

    /**
     * @Title updateSchoolMajorInfo
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???11??? ??????17:00
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
                throw new VisitException("?????????????????????");
            }
        }
    }

    /**
     * @Title updateSchoolAdmissionLine
     * @description ???????????????????????????
     * @author ?????????
     * @date 2022???8???12??? ??????17:44
     */
    public static void updateSchoolAdmissionLine(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
        int size = allSchoolId.size();
        int pageSize = 10;
        int countPage =size%pageSize==0?size/pageSize:size/pageSize+1;
        for(int i=1;i<=countPage;i++){
            int start = (i-1)*pageSize;
            int end = i==countPage?size:i*pageSize;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=start;j<end;j++){
                        try {
                            updateSchoolAdmissionLine(allSchoolId.get(j));
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }
    }

    /**
     * @Title updateSchoolAdmissionLine
     * @description ?????????????????????
     * @author ?????????
     * @date 2022???8???12??? ??????17:40
     * @param school_id
     */
    public static void updateSchoolAdmissionLine(String school_id) throws IOException {
        Map<String, String> urlMap = analysisSchoolEnrollAllUrl();
        String url = urlMap.get("urlStart")+school_id+urlMap.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if("0000".equals(jsonObject.get("code").toString())){
            JSONObject data = JSONObject.parseObject(jsonObject.get("data").toString());
            JSONObject newsdata = JSONObject.parseObject(data.get("newsdata").toString());
            //??????
            List<Integer> provinces = JSON.parseObject(newsdata.get("province").toString(), List.class);
            //??????????????????
            Map<String,List> years = JSON.parseObject(newsdata.get("year").toString(), Map.class);
            //???????????????type
            Map<String,List> types = JSON.parseObject(newsdata.get("type").toString(), Map.class);

            for(Integer provinceId:provinces){
                List<Integer> province_years = years.get(""+provinceId);
                for(Integer province_year:province_years){
                    List<Integer> type = types.get(provinceId + "_" + province_year);
                    for(Integer t:type){
                        String url1="https://static-data.gaokao.cn/www/2.0/schoolprovinceindex/"+province_year+"/"+school_id+"/"+provinceId+"/"+t+"/1.json";
                        //?????????????????????????????????,??????????????????url1
                        String s1 = MyHttpClient.fetchHtmlSync(url1);
                        JSONObject jsonObject1 = JSONObject.parseObject(s1);
                        if("0000".equals(jsonObject1.get("code").toString())){
                            JSONObject data1 = JSONObject.parseObject(jsonObject1.get("data").toString());
                            List<SchoolAdmissionLine> list = JSON.parseArray(data1.get("item").toString(), SchoolAdmissionLine.class);
                            schoolAdmissionLineService.addSchoolAdmissionLineList(list);
                        }
                    }
                }
            }
        }
    }

    /**
     * @Title updateSchoolZsjh
     * @description ????????????????????????
     * @author ?????????
     * @date 2022???8???15??? ??????17:56
     */
    public static void updateSchoolZsjh(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
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
                            String school_id = allSchoolId.get(j);
                            updateSchoolZsjh(school_id);
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }
    }

    /**
     * @Title updateSchoolZsjh
     * @description ????????????????????????
     * @author ?????????
     * @date 2022???8???15??? ??????17:56
     * @param school_id
     */
    public static void updateSchoolZsjh(String school_id) throws IOException {
        Map<String, String> urlMap = analysisSchoolZSJHUrl();
        String url = urlMap.get("urlStart")+school_id+urlMap.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if ("0000".equals(jsonObject.get("code").toString())) {
            JSONObject data = JSONObject.parseObject(jsonObject.get("data").toString());
            JSONObject newsdata = JSONObject.parseObject(data.get("newsdata").toString());
            //??????
            List<Integer> provinces = JSON.parseObject(newsdata.get("province").toString(), List.class);
            //??????????????????
            Map<String, List> years = JSON.parseObject(newsdata.get("year").toString(), Map.class);
            //???????????????type
            Map<String, List> types = JSON.parseObject(newsdata.get("type").toString(), Map.class);
            //??????
            Map<String, List> batchs = JSON.parseObject(newsdata.get("batch").toString(), Map.class);
            for (Integer provinceId : provinces) {
                List<Integer> province_years = years.get("" + provinceId);
                for (Integer province_year : province_years) {
                    List<Integer> type = types.get(provinceId + "_" + province_year);
                    for (Integer t : type) {
                        List<Integer> batch = batchs.get(provinceId + "_" + province_year + "_" + t);
                        for(Integer b:batch){
                            Integer pageCount=1;//????????????,??????10?????????,?????????????????????numFound??????????????????????????????
                            for(int pageNow=1;pageNow<=pageCount;pageNow++){
                                //?????????????????????????????????,??????,??????,?????????????????????url1
                                String url1 = "https://static-data.gaokao.cn/www/2.0/schoolplanindex/"+province_year+"/"+school_id+"/"+provinceId+"/"+t+"/"+b+"/"+pageNow+".json";
                                String s1 = MyHttpClient.fetchHtmlSync(url1);
                                JSONObject jsonObject1 = JSONObject.parseObject(s1);
                                if ("0000".equals(jsonObject1.get("code").toString())) {
                                    JSONObject data1 = JSONObject.parseObject(jsonObject1.get("data").toString());
                                    Integer numFound = Integer.parseInt(data1.get("numFound").toString());
                                    pageCount=numFound%10==0?numFound/10:numFound/10+1;
                                    List<SchoolZsjh> item = JSON.parseArray(data1.get("item").toString(), SchoolZsjh.class);
                                    for(SchoolZsjh schoolZsjh:item)schoolZsjh.setYear(""+province_year);
                                    schoolZsjhService.addSchoolZsjhList(item);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * @Title updateSchoolMajorLine
     * @description ???????????????????????????
     * @author ?????????
     * @date 2022???8???16??? ??????10:30
     */
    public static void updateSchoolMajorLine(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
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
                            String school_id = allSchoolId.get(j);
                            updateSchoolMajorLine(school_id);
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }
    }
    /**
     * @Title updateSchoolMajorLine
     * @description ???????????????????????????
     * @author ?????????
     * @date 2022???8???16??? ??????10:29
     * @param school_id
     */
    public static void updateSchoolMajorLine(String school_id) throws IOException {
        Map<String, String> urlMap = analysisSchoolMajorLineUrl();
        String url = urlMap.get("urlStart")+school_id+urlMap.get("urlEnd");
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if ("0000".equals(jsonObject.get("code").toString())) {
            JSONObject data = JSONObject.parseObject(jsonObject.get("data").toString());
            JSONObject newsdata = JSONObject.parseObject(data.get("newsdata").toString());
            //??????
            List<Integer> provinces = JSON.parseObject(newsdata.get("province").toString(), List.class);
            //??????????????????
            Map<String, List> years = JSON.parseObject(newsdata.get("year").toString(), Map.class);
            //???????????????type
            Map<String, List> types = JSON.parseObject(newsdata.get("type").toString(), Map.class);
            //??????
            Map<String, List> batchs = JSON.parseObject(newsdata.get("batch").toString(), Map.class);
            for (Integer provinceId : provinces) {
                List<Integer> province_years = years.get("" + provinceId);
                for (Integer province_year : province_years) {
                    List<Integer> type = types.get(provinceId + "_" + province_year);
                    for (Integer t : type) {
                        List<Integer> batch = batchs.get(provinceId + "_" + province_year + "_" + t);
                        for(Integer b:batch){
                            Integer pageCount=1;//????????????,??????10?????????,?????????????????????numFound??????????????????????????????
                            for(int pageNow=1;pageNow<=pageCount;pageNow++){
                                //?????????????????????????????????,??????,??????,?????????????????????url1
                                String url1 = "https://static-data.gaokao.cn/www/2.0/schoolspecialindex/"+province_year+"/"+school_id+"/"+provinceId+"/"+t+"/"+b+"/"+pageNow+".json";
                                String s1 = MyHttpClient.fetchHtmlSync(url1);
                                JSONObject jsonObject1 = JSONObject.parseObject(s1);
                                if ("0000".equals(jsonObject1.get("code").toString())) {
                                    JSONObject data1 = JSONObject.parseObject(jsonObject1.get("data").toString());
                                    Integer numFound = Integer.parseInt(data1.get("numFound").toString());
                                    pageCount=numFound%10==0?numFound/10:numFound/10+1;
                                    List<SchoolMajorLine> item = JSON.parseArray(data1.get("item").toString(), SchoolMajorLine.class);
                                    for(SchoolMajorLine schoolMajorLine:item)schoolMajorLine.setYear(""+province_year);
                                    schoolMajorLineService.addSchoolMajorLineList(item);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @Title updateSchoolIntroduce
     * @description ????????????????????????
     * @author ?????????
     * @date 2022???8???25??? ??????14:30
     */
    public static void updateSchoolIntroduce(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
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
                            String school_id = allSchoolId.get(j);
                            updateSchoolIntroduce(school_id);
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }
    }

    /**
     * @Title updateSchoolIntroduce
     * @description ????????????????????????
     * @author ?????????
     * @date 2022???8???25??? ??????14:30
     */
    public static void updateSchoolIntroduce(String schoolId) throws IOException {
        //schoolIntroduceService
        String url = "https://static-data.gaokao.cn/www/2.0/school/"+schoolId+"/detail/69000.json";
        String s = MyHttpClient.fetchHtmlSync(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if ("0000".equals(jsonObject.get("code").toString())) {
            Object data = jsonObject.get("data");
            SchoolIntroduce schoolIntroduce = JSON.parseObject(data.toString(), SchoolIntroduce.class);
            schoolIntroduceService.addSchoolIntroduce(schoolIntroduce);
        }
    }

    /**
     * @Title analysisSchoolUrl
     * @description ????????????url??????
     * @author ?????????
     * @date 2022???8???8??? ??????15:52
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
     * @description ??????????????????????????????url??????
     * @author ?????????
     * @date 2022???8???9??? ??????11:22
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
     * @description ????????????????????????????????????url??????
     * @author ?????????
     * @date 2022???8???9??? ??????16:45
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
     * @description ??????????????????????????????url??????
     * @author ?????????
     * @date 2022???8???11??? ??????15:01
     */
    public static Map<String,String> analysisSchoolMajorUrl(){
        if(schoolMajorUrlMap==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolMajorUrl();
            schoolMajorUrlMap=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return schoolMajorUrlMap;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description ????????????????????????????????????url??????
     * @author ?????????
     * @date 2022???8???11??? ??????16:50
     */
    public static Map<String,String> analysisSchoolMajorInfoUrl(){
        if(schoolMajorInfoUrlMap==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolMajorInfoUrl();
            schoolMajorInfoUrlMap=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return schoolMajorInfoUrlMap;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description ??????????????????????????????url
     * @author ?????????
     * @date 2022???8???12??? ??????16:10
     */
    public static Map<String,String> analysisSchoolEnrollAllUrl(){
        if(schoolEnrollAllUrlMap==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolEnrollAllUrl();
            schoolEnrollAllUrlMap=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return schoolEnrollAllUrlMap;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description ?????????????????????????????????url
     * @author ?????????
     * @date 2022???8???15??? ??????15:28
     */
    public static Map<String,String> analysisSchoolZSJHUrl(){
        if(schoolZSJHUrl==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolZSJHUrl();
            schoolZSJHUrl=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return schoolZSJHUrl;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description ?????????????????????????????????url
     * @author ?????????
     * @date 2022???8???16??? ??????9:36
     */
    public static Map<String,String> analysisSchoolMajorLineUrl(){
        if(schoolMajorLineUrl==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolMajorLineUrl();
            schoolMajorLineUrl=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return schoolMajorLineUrl;
    }

    /**
     * @Title analysisProvinceGaoKaoInfoUrl
     * @description ?????????????????????????????????urlPROVINCE_GAOKAO_INFO
     * @author ?????????
     * @date 2022???8???16??? ??????13:15
     */
    public static Map<String,String> analysisProvinceGaoKaoInfoUrl(){
        if(proinceGaoKaoInfoUrl==null){
            Para SchoolMajorUrlPara = paraService.finaProvinceGaoKaoInfoUrl();
            String[] split = SchoolMajorUrlPara.getPARAVALUE().split("provinceId");
            proinceGaoKaoInfoUrl = new HashMap<String, String>();
            proinceGaoKaoInfoUrl.put("urlStart",split[0]);
            proinceGaoKaoInfoUrl.put("urlEnd",split[1]);
        }
        return proinceGaoKaoInfoUrl;
    }

    /**
     * @Title analysisSchoolMajorUrl
     * @description ????????????????????????url
     * @author ?????????
     * @date 2022???8???16??? ??????15:07
     */
    public static Map<String,String> analysisSchoolInfoUrl(){
        if(schoolInfoUrlUrl==null){
            Para SchoolMajorUrlPara = paraService.finaSchoolInfoUrl();
            schoolInfoUrlUrl=analysis(SchoolMajorUrlPara.getPARAVALUE());
        }
        return schoolInfoUrlUrl;
    }

    public static Map<String,String> analysis(String url){
        String[] split = url.split("school_id");
        Map<String,String> map = new HashMap<String, String>();
        map.put("urlStart",split[0]);
        map.put("urlEnd",split[1]);
        return map;
    }

    /**
     * @Title updateBatch
     * @description ??????????????????
     * @author ?????????
     * @date 2022???8???15??? ??????15:30
     */
    public static void updateBatch(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
        int size = allSchoolId.size();
        int pageSize = 10;
        int countPage =size%pageSize==0?size/pageSize:size/pageSize+1;
        for(int i=1;i<=countPage;i++){
            int start = (i-1)*pageSize;
            int end = i==countPage?size:i*pageSize;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=start;j<end;j++){
                        try {
                            String school_id = allSchoolId.get(j);
                            Map<String, String> urlMap = analysisSchoolEnrollAllUrl();
                            String url = urlMap.get("urlStart") + school_id + urlMap.get("urlEnd");
                            String s = MyHttpClient.fetchHtmlSync(url);
                            JSONObject jsonObject = JSONObject.parseObject(s);
                            if ("0000".equals(jsonObject.get("code").toString())) {
                                JSONObject data = JSONObject.parseObject(jsonObject.get("data").toString());
                                JSONObject newsdata = JSONObject.parseObject(data.get("newsdata").toString());
                                //??????
                                List<Integer> provinces = JSON.parseObject(newsdata.get("province").toString(), List.class);
                                //??????????????????
                                Map<String, List> years = JSON.parseObject(newsdata.get("year").toString(), Map.class);
                                //???????????????type
                                Map<String, List> types = JSON.parseObject(newsdata.get("type").toString(), Map.class);
                                for (Integer provinceId : provinces) {
                                    List<Integer> province_years = years.get("" + provinceId);
                                    for (Integer province_year : province_years) {
                                        List<Integer> type = types.get(provinceId + "_" + province_year);
                                        for (Integer t : type) {
                                            String url1 = "https://static-data.gaokao.cn/www/2.0/schoolprovinceindex/" + province_year + "/" + school_id + "/" + provinceId + "/" + t + "/1.json";
                                            //?????????????????????????????????,??????????????????url1
                                            String s1 = MyHttpClient.fetchHtmlSync(url1);
                                            JSONObject jsonObject1 = JSONObject.parseObject(s1);
                                            if ("0000".equals(jsonObject1.get("code").toString())) {
                                                JSONObject data1 = JSONObject.parseObject(jsonObject1.get("data").toString());
                                                List<SchoolAdmissionLine> list = JSON.parseArray(data1.get("item").toString(), SchoolAdmissionLine.class);
                                                for (SchoolAdmissionLine schoolAdmissionLine : list) {
                                                    SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
                                                    BatchMapper mapper = sqlSession.getMapper(BatchMapper.class);
                                                    String s2 = mapper.selectBatch(schoolAdmissionLine.getBatch());
                                                    if(s2==null||!s2.equals(schoolAdmissionLine.getBatch())){
                                                        int i = mapper.addBatch(schoolAdmissionLine.getBatch(), schoolAdmissionLine.getLocal_batch_name());
                                                        if(i>0)SqlSessionFactoryUtil.commitAndClose(sqlSession);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * @Title updateProvinceGaoKaoInfo
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???16??? ??????14:20
     */
    public static void updateProvinceGaoKaoInfo() throws IOException {
        List<String> provinceIDs = provinceGaoKaoInfoService.selectProvinceIdAll();
        for(String provinceID:provinceIDs){
            Map<String, String> urlMap = analysisProvinceGaoKaoInfoUrl();
            String url = urlMap.get("urlStart") + provinceID + urlMap.get("urlEnd");
            String s = MyHttpClient.fetchHtmlSync(url);
            JSONObject jsonObject = JSONObject.parseObject(s);
            Object lists = jsonObject.get("lists");
            JSONObject listsJsonObject = JSONObject.parseObject(lists.toString());
            List<ProvinceGaoKao> provinceGaoKaos = JSON.parseArray(listsJsonObject.get("1").toString(), ProvinceGaoKao.class);
            ProvinceGaoKao provinceGaoKao = provinceGaoKaos.get(0);
            List<ProvinceGaoKaoInfo> list = provinceGaoKao.getArticle();
            for(ProvinceGaoKaoInfo p:list)p.setProvince_id(provinceID);
            provinceGaoKaoInfoService.addProvinceGaoKaoInfoList(list);
        }
    }

    /**
     * @Title updateProvinceGaoKaoInfo
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???16??? ??????14:20
     */
    public static void updateSchoolInfo(){
        List<String> allSchoolId = schoolService.findAllSchoolId();
        //????????????
        int size = allSchoolId.size();
        int pageSize = 10;
        int countPage =size%pageSize==0?size/pageSize:size/pageSize+1;
        for(int i=1;i<=countPage;i++){
            int start = (i-1)*pageSize;
            int end = i==countPage?size:i*pageSize;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=start;j<end;j++){
                        try {
                            String school_id = allSchoolId.get(j);
                            Map<String, String> urlMap = analysisSchoolInfoUrl();
                            String url = urlMap.get("urlStart") + school_id + urlMap.get("urlEnd");
                            String s = MyHttpClient.fetchHtmlSync(url);
                            JSONObject jsonObject = JSONObject.parseObject(s);
                            if("0000".equals(jsonObject.get("code").toString())){
                                Object data = jsonObject.get("data");
                                SchoolInfo schoolInfo = JSON.parseObject(data.toString(), SchoolInfo.class);
                                schoolInfo.setLogo("https://static-data.gaokao.cn/upload/logo/"+school_id+".jpg");
                                schoolInfoService.addSchoolInfo(schoolInfo);
                            }
                        } catch (IOException e) {
                            //????????????,??????
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }

    /**
     * @Title updateSchoolImg
     * @description ??????????????????????????????
     * @author ?????????
     * @date 2022???8???16??? ??????17:51
     */
    public static void updateSchoolImg(){
        SchoolImgService schoolImgService = new SchoolImgServiceImp();
        List<String> allSchoolId = schoolService.findAllSchoolId();
        for(int i=0;i<allSchoolId.size();i++){
            String school_id = allSchoolId.get(i);
            String url=" https://static-data.gaokao.cn/www/2.0/school/"+school_id+"/img/list.json";
            String s = null;
            try {
                s = MyHttpClient.fetchHtmlSync(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("school_id:"+school_id+" s:"+s+" !s.equals(\"\"):"+!s.equals("\"\""));
            if(!"\"\"".equals(s)){
                JSONObject jsonObject = JSONObject.parseObject(s);
                if("0000".equals(jsonObject.get("code").toString())){
                    List<SchoolImg> data = JSON.parseArray(jsonObject.get("data").toString(), SchoolImg.class);
                    schoolImgService.addSchoolImgList(data);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        updateSchoolIntroduce();
    }

}
