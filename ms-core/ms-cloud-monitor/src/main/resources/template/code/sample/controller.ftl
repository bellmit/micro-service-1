package ${packagePath}.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${packagePath}.pojo.${table.className};
import ${packagePath}.service.${table.className}Service;
import com.monitor.api.ApiInfo;
import com.monitor.api.ApiParam;
import com.monitor.api.ApiRes;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(${table.className}Controller.class);

	@Autowired
	private ${table.className}Service ${table.beanName}Service;
	
	@RequestMapping(name = "${table.beanName}-获取详细信息", value = "/${table.beanName}/get")
	@ApiInfo(params = {
			@ApiParam(name="${table.firstKColumn.fieldName}", code="${table.firstKColumn.fieldName}", value=""),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
	})
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

	@RequestMapping(name = "${table.beanName}-新增或修改", value = "/${table.beanName}/saveOrUpdate")
	@ApiInfo(params = {
			@ApiParam(name="${table.firstKColumn.fieldName}", code="${table.firstKColumn.fieldName}", value=""),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
	})
	public ResponseFrame saveOrUpdate(${table.className} ${table.beanName}) {
		try {
			ResponseFrame frame = ${table.beanName}Service.saveOrUpdate(${table.beanName});
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(name = "${table.beanName}-分页查询信息", value = "/${table.beanName}/pageQuery")
	@ApiInfo(params = {
			@ApiParam(name="页面", code="page", value="1"),
			@ApiParam(name="每页大小", code="size", value="10"),
			@ApiParam(name="排序[{\"property\": \"createTime\", \"type\":\"desc\", \"order\":1}]", code="orderby", value="", required=false),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
			@ApiRes(name="当前页码", code="page", pCode="body", clazz=Integer.class, value="1"),
			@ApiRes(name="每页大小", code="size", pCode="body", clazz=Integer.class, value="10"),
			@ApiRes(name="总页数", code="totalPage", pCode="body", clazz=Integer.class, value="5"),
			@ApiRes(name="总记录数", code="total", pCode="body", clazz=Integer.class, value="36"),
			@ApiRes(name="数据集合", code="rows", pCode="body", clazz=List.class, value=""),
			@ApiRes(name="${table.firstKColumn.fieldName}", code="${table.firstKColumn.fieldName}", pCode="rows", value=""),
	})
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

	@RequestMapping(name = "${table.beanName}-根据主键删除", value = "/${table.beanName}/delete")
	@ApiInfo(params = {
			@ApiParam(name="${table.firstKColumn.fieldName}", code="${table.firstKColumn.fieldName}", value=""),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
	})
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