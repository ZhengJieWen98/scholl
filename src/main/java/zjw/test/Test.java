package zjw.test;

import com.alibaba.fastjson.JSONObject;
import zjw.pojo.School;
import zjw.service.SchoolService;
import zjw.service.imp.SchoolServiceImp;
import zjw.utils.MyHttpClient;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        SchoolService service = new SchoolServiceImp();
//        String url1 = "https://api.eol.cn/web/api/?admissions=&central=&department=&dual_class=&f211=&f985=&is_doublehigh=&keyword=&nature=&page=1&province_id=&school_type=6000&size=30&sort=view_month&sorttype=desc&type=&uri=apidata/api/gk/school/lists&signsafe=a20ff9f5f6e5b97fac95e016f291fac3";
//        String url2 = "https://api.eol.cn/web/api/?admissions=&central=&department=&dual_class=&f211=&f985=&is_doublehigh=&keyword=&nature=&page=2&province_id=&school_type=6000&size=30&sort=view_month&sorttype=desc&type=&uri=apidata/api/gk/school/lists&signsafe=84d4e1ce4ce68aea47b4f7fcbec716df";
//        String url3 = "https://api.eol.cn/web/api/?admissions=&central=&department=&dual_class=&f211=&f985=&is_doublehigh=&keyword=&nature=&page=3&province_id=&school_type=6000&size=30&sort=view_month&sorttype=desc&type=&uri=apidata/api/gk/school/lists&signsafe=0b7d35c8d66f7392253aee00ce87be83";

        for (int i=1;i<200;i++){
            //String url1 = "https://api.eol.cn/web/api/?admissions=&central=&department=&dual_class=&f211=&f985=&is_doublehigh=&keyword=&nature=&page="+i+"&province_id=&school_type=6000&size=30&sort=view_month&sorttype=desc&type=&uri=apidata/api/gk/school/lists&signsafe=a20ff9f5f6e5b97fac95e016f291fac3";
            String url ="https://api.eol.cn/web/api/?admissions=&central=&department=&dual_class=&f211=&f985=&is_doublehigh=&is_dual_class=&keyword=&nature=&page="+i+"&province_id=50&ranktype=&request_type=1&school_type=&size=20&top_school_id=[119,179,1474,1009,1475,1476,2691,1496,1595,1483,2915]&type=&uri=apidata/api/gk/school/lists&signsafe=391f00e860c81556ba3e244506de2322";
            String s = MyHttpClient.fetchHtmlSync(url);
            JSONObject resout = JSONObject.parseObject(s);
            JSONObject data = JSONObject.parseObject(resout.get("data").toString());
            Object item = data.get("item");
            List<School> schools = JSONObject.parseArray(item.toString(), School.class);
            for(School school:schools){
                service.addSchool(school);
            }
        }


    }
}
