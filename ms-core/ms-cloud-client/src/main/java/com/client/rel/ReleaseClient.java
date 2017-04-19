package com.client.rel;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.client.ConfigCons;
import com.client.rel.data.DispatchFailData;
import com.client.rel.data.ExecShellFailData;
import com.client.rel.enums.PrjInfoContainer;
import com.client.rel.model.ExecShell;
import com.client.rel.utils.ClientUtil;
import com.client.rel.utils.ServerUtil;
import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameFileUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameTimeUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

public class ReleaseClient {

	private static final Logger LOGGER = Logger.getLogger(ReleaseClient.class);

	public void release(final Integer prjId, final String version, final Integer container, final String shellScript, final String pathUrl) {
		LOGGER.info("发布项目[" + prjId + "]中...");
		ThreadPoolTaskExecutor threadPoolTaskExecutor = FrameSpringBeanUtil.getBean(ThreadPoolTaskExecutor.class);

		String suffix = pathUrl.substring(pathUrl.lastIndexOf("."));
		final String prjName = version + suffix;
		final String path = ClientUtil.getPrjPath(prjId, version);
		//判断操作系统
		final PrjInfoContainer piContainer = PrjInfoContainer.get(container);
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					//下载发布的项目包
					File file = download(pathUrl, path, prjName);
					if(file == null) {
						fail("下载项目异常");
					}
					if("linux".equals(piContainer.getSysType())) {
						releaseLinux(path, prjName);
					} else if("win".equals(piContainer.getSysType())) {
						releaseWin(path, prjName);
					}
				} catch (Exception e) {
					LOGGER.error("发布项目失败: " + e.getMessage(), e);
					//发布失败调度回写服务端
					fail("发布项目失败: " + e.getMessage());
				}
			}

			private void releaseWin(String path, String prjName) throws Exception {
				//生成可执行的bat文件
				String batName = version + ".bat";
				File shFile = FrameFileUtil.readFile(path + batName);
				ExecShellFailData.add(new ExecShell(batName, version, path, piContainer));

				String command = convertCommand(shellScript, prjId, prjName, version);
				FrameFileUtil.writeFile(command, shFile);

				/*String[] cmdChmod = {"/bin/sh", "-c", "chmod a+x " + path + shellName};
			Runtime.getRuntime().exec(cmdChmod).waitFor();*/
				String[] cmdChmod = {"cmd", "/c", "start " + path + batName};
				Runtime.getRuntime().exec(cmdChmod).waitFor();
				LOGGER.info("执行命令返回结果: 成功");
				succ("发布成功");
			}

			private void releaseLinux(String path, String prjName) throws Exception {
				//生成可执行的shell文件
				String shellName = version + ".sh";
				File shFile = FrameFileUtil.readFile(path + shellName);
				ExecShellFailData.add(new ExecShell(shellName, version, path, piContainer));

				String command = convertCommand(shellScript, prjId, prjName, version);
				FrameFileUtil.writeFile(command, shFile);

				/*String[] cmdChmod = {"/bin/sh", "-c", "chmod a+x " + path + shellName};
			Runtime.getRuntime().exec(cmdChmod).waitFor();*/

				String[] cmdChmod = {"/bin/sh", "-c", "chmod a+x " + path + shellName};
				Runtime.getRuntime().exec(cmdChmod).waitFor();

				//ProcessBuilder pb = new ProcessBuilder("." + path + shellName, param1, param2, param3);
				ProcessBuilder pb = new ProcessBuilder("./" + shellName);
				//设置要运行脚本的目录
				pb.directory(new File(path));
				int runningStatus = 0;
				Process p = pb.start();
				try {
					runningStatus = p.waitFor();
				} catch (InterruptedException e) {
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				int i=0;
				while ((line = br.readLine()) != null) {
					sb.append("["+(i++)+"]"+line).append("\n");
				}
				LOGGER.info("执行命令返回结果: " + sb.toString());
				//runningStatus：运行状态，0标识正常。
				if (runningStatus != 0) {
					//发布失败调度回写服务端
					fail("执行Shell命令出错");
				} else {
					//发布成功调度回写服务端
					succ("发布成功");
				}
			}

			private File download(String url, String savePath, String saveName) {
				String time = String.valueOf(System.currentTimeMillis());
				String sercret = ConfigCons.clientToken;
				String clientId = ConfigCons.clientId;
				String sign = AuthUtil.auth(clientId, time, sercret);
				String downUrl = ConfigCons.clientServerHost + "/api/client/download.shtml?clientId=" + clientId
						+ "&time=" + time + "&sign=" + sign + "&url=" + url;
				LOGGER.info("下载项目地址: " + downUrl);
				File file = FrameFileUtil.readFile(downUrl, savePath, saveName);
				return file;
			}

			private void succ(String statusMsg) {
				//发布成功调用接口，修改发布状态
				ExecShellFailData.del(version);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("prjId", prjId.toString());
				paramsMap.put("version", version);
				//状态[10待发布、20发布中、30发布失败、40发布成功]
				paramsMap.put("status", "40");
				paramsMap.put("statusMsg", statusMsg);
				try {
					ResponseFrame resultFrame = ServerUtil.post("/api/client/updateRelease.shtml", paramsMap);
					if(ResponseCode.SUCC.getCode() == resultFrame.getCode().intValue()) {
						LOGGER.info("项目[" + prjId + "]发布成功!");
					} else {
						LOGGER.error("项目[" + prjId + "]发布成功, 调用服务端接口失败!");
						DispatchFailData.addUpdateReleaseSuccPrjIds(prjId);
					}
				} catch (Exception e) {
					LOGGER.error("项目[" + prjId + "]发布成功, 调用服务端接口失败!" + e.getMessage());
					DispatchFailData.addUpdateReleaseSuccPrjIds(prjId);
				}
			}

			private void fail(String statusMsg) {
				//发布成功调用接口，修改发布状态
				ExecShellFailData.del(version);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("prjId", prjId.toString());
				paramsMap.put("version", version);
				//状态[10待发布、20发布中、30发布失败、40发布成功]
				paramsMap.put("status", "30");
				paramsMap.put("statusMsg", statusMsg);
				try {
					ResponseFrame resultFrame = ServerUtil.post("/api/client/updateRelease.shtml", paramsMap);
					if(ResponseCode.SUCC.getCode() == resultFrame.getCode().intValue()) {
						LOGGER.info("项目[" + prjId + "]发布失败!");
					} else {
						LOGGER.error("项目[" + prjId + "]发布失败, 调用服务端接口失败!");
						DispatchFailData.addUpdateReleaseFailPrjIds(prjId);
					}
				} catch (Exception e) {
					LOGGER.error("项目[" + prjId + "]发布失败, 调用服务端接口失败!" + e.getMessage());
					DispatchFailData.addUpdateReleaseFailPrjIds(prjId);
				}
			}
		});
	}
	
	/**
	 * 转换特殊参数<br>
	 * 参数：
	 * 		{prj.path}:项目在客户端的路径
	 * 		{prj.name}:项目在客户端的名称包含后缀
	 * @param command
	 * @param prjId
	 * @return
	 */
	private String convertCommand(String command, Integer prjId, String prjName, String version) {
		if(command.contains("[prj.path]")) {
			//处理项目路径
			command = command.replace("[prj.path]", ClientUtil.getPrjPath(prjId, version));
		}
		if(command.contains("[prj.name]")) {
			//处理项目名称
			command = command.replace("[prj.name]", prjName);
		}
		if(command.contains("[current.date]")) {
			//处理当前日期
			command = command.replace("[current.date]", FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDD));
		}
		if(command.contains("[current.time]")) {
			//处理当前时间
			command = command.replace("[current.time]", FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDDHHMMSS));
		}
		return command;
	}
}