package zjw.test;

import zjw.pojo.School;
import zjw.service.SchoolService;
import zjw.service.imp.SchoolServiceImp;
import zjw.utils.SqlSessionFactoryUtil;

public class ServiceTest {
    public static void main(String[] args) {
        SchoolService service = new SchoolServiceImp();
        School school = new School();
        school.setAddress("望江校区：四川省成都市一环路南一段24号");
        school.setBelong("教育部");
        school.setCity_name("成都市");
        school.setCounty_name("武侯区");
        school.setProvince_name("四川");
        school.setDual_class_name("双一流");
        school.setName("四川大学");
        school.setNature_name("公办");
        school.setLevel_name("普通本科");
        school.setType_name("综合类");
        school.setSchool_id("99");
        int i = service.addSchool(school);
        System.out.println(i);

    }
}
