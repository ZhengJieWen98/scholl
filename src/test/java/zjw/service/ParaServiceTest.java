package zjw.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zjw.pojo.Para;
import zjw.service.imp.ParaServiceImp;

import java.util.List;

public class ParaServiceTest {
    private ParaService service;
    @Before
    public void init(){
        service = new ParaServiceImp();
    }

    @Test
    public void finaAllSchoolUrls(){
        List<Para> paras = service.finaAllSchoolUrls();
        for(Para para:paras) System.out.println(para);
    }

    @After
    public void destroy(){

    }
}
