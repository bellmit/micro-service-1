package ${packagePath}.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${packagePath}.pojo.${table.className};
import ${packagePath}.service.${table.className}Service;
import com.system.comm.model.Orderby;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * ${tablename}的Controller
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@RestController
public class ${table.className}Controller {

	private static final Logger LOGGER = Logger.getLogger(${table.className}Controller.class);

	@Autowired
	private ${table.className}Service ${table.beanName}Service;
	
	@RequestMapping(value = "/${table.beanName}/get")
	public ResponseFrame get(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(${table.beanName}Service.get(${table.firstKColumn.fieldName}));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/${table.beanName}/saveOrUpdate")
	public ResponseFrame saveOrUpdate(${table.className} ${table.beanName}) {
		try {
			ResponseFrame frame = ${table.beanName}Service.saveOrUpdate(${table.beanName});
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/${table.beanName}/pageQuery")
	public ResponseFrame pageQuery(${table.className} ${table.beanName}, String orderby) {
		try {
			if(FrameStringUtil.isNotEmpty(orderby)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderby, Orderby.class);
				${table.beanName}.setOrderbys(orderbys);
			}
			ResponseFrame frame = ${table.beanName}Service.pageQuery(${table.beanName});
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/${table.beanName}/delete")
	public ResponseFrame delete(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
		try {
			ResponseFrame frame = ${table.beanName}Service.delete(${table.firstKColumn.fieldName});
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}