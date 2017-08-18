package ${packagePath}.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.moudle.admin.common.controller.BaseController;
import ${packagePath}.pojo.${table.className};
import ${packagePath}.service.${table.className}Service;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * ${tablename} Controller
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Controller
public class ${table.className}Controller extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(${table.className}Controller.class);

	@Autowired
	private ${table.className}Service ${table.beanName}Service;
	
	@RequestMapping(value = "/${table.beanName}/f-view/manager")
    public String manager(HttpServletRequest request, HttpServletResponse response,
    		ModelMap modelMap) {
		return "admin/${moduleName}/${table.beanName}-manager";
    }

    @RequestMapping(value = "/${table.beanName}/f-view/edit")
    public String edit(HttpServletRequest request, HttpServletResponse response,
    		ModelMap modelMap, ${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
    	if(id != null) {
    		${table.className} ${table.beanName} = ${table.beanName}Service.get(${table.firstKColumn.fieldName});
    		modelMap.put("${table.beanName}", ${table.beanName});
    	}
		return "admin/${moduleName}/${table.beanName}-edit";
    }
    
    @RequestMapping(value = "/${table.beanName}/f-json/get")
    @ResponseBody
    public void get(HttpServletRequest request, HttpServletResponse response,
    		${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
        ResponseFrame frame = null;
		try {
			frame = new ResponseFrame();
			${table.className} ${table.beanName} = ${table.beanName}Service.get(id);
			frame.setBody(${table.beanName});
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
    }
    
    @RequestMapping(value = "/${table.beanName}/f-json/pageQuery")
    @ResponseBody
    public void pageQuery(HttpServletRequest request, HttpServletResponse response,
    		${table.className} ${table.beanName}) {
        ResponseFrame frame = null;
		try {
			frame = ${table.beanName}Service.pageQuery(${table.beanName});
			frame.setBody(frame);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
    }

    @RequestMapping(value = "/${table.beanName}/f-json/save")
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response,
    		${table.className} ${table.beanName}) {
        ResponseFrame frame = null;
		try {
			frame = ${table.beanName}Service.saveOrUpdate(${table.beanName});
			frame.setBody(frame);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
    }

    @RequestMapping(value = "/${table.beanName}/f-json/delete")
    @ResponseBody
    public void delete(HttpServletRequest request, HttpServletResponse response,
    		${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
        ResponseFrame frame = new ResponseFrame();
		try {
			${table.beanName}Service.delete(${table.firstKColumn.fieldName});
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
    }
}