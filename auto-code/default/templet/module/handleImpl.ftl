package ${packagePath}.handle.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ${packagePath}.handle.${table.className}Handle;
import ${packagePath}.service.${table.className}Service;
import ${packagePath}.pojo.${table.className};
import com.system.comm.model.Orderby;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameMapUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.container.annotation.Handle;
import com.system.container.annotation.RequestMapping;
import com.system.handle.BaseHandle;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * ${tablename}的HandleImpl
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Handle("${table.beanName}")
public class ${table.className}HandleImpl implements ${table.className}Handle,BaseHandle {

	private final static Logger LOGGER = Logger.getLogger(${table.className}HandleImpl.class);
	
	@Autowired
	private ${table.className}Service ${table.beanName}Service;
	
	@Override
	@RequestMapping(value = "/${table.beanName}/saveOrUpdate")
	public ResponseFrame saveOrUpdate(Map<String, Object> map) {
		try {
			${table.className} ${table.beanName} = FrameMapUtil.getBean(map, ${table.className}.class);
			ResponseFrame frame = ${table.beanName}Service.saveOrUpdate(${table.beanName});
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@Override
	@RequestMapping(value = "/${table.beanName}/get")
	public ResponseFrame get(Map<String, Object> map) {
		try {
			${table.firstKColumn.typeName} ${table.firstKColumn.fieldName} = FrameMapUtil.get${table.firstKColumn.typeName}(map, "${table.firstKColumn.fieldName}");
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(${table.beanName}Service.get(${table.firstKColumn.fieldName}));
			frame.setSucc();
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@Override
	@RequestMapping(value = "/${table.beanName}/pageQuery")
	public ResponseFrame pageQuery(Map<String, Object> map) {
		try {
			${table.className} ${table.beanName} = FrameMapUtil.getBean(map, ${table.className}.class);
			String orderbyString = FrameMapUtil.getString(map, "orderby");
			if(FrameStringUtil.isNotEmpty(orderbyString)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderbyString, Orderby.class);
				${table.beanName}.setOrderbys(orderbys);
			}
			ResponseFrame frame = ${table.beanName}Service.pageQuery(${table.beanName});
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
	
	@Override
	@RequestMapping(value = "/${table.beanName}/delete")
	public ResponseFrame delete(Map<String, Object> map) {
		try {
			${table.firstKColumn.typeName} ${table.firstKColumn.fieldName} = FrameMapUtil.get${table.firstKColumn.typeName}(map, "${table.firstKColumn.fieldName}");
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(${table.beanName}Service.delete(${table.firstKColumn.fieldName}));
			frame.setSucc();
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}