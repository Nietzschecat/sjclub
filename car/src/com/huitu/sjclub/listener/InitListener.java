package com.huitu.sjclub.listener;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;


import com.huitu.sjclub.Setting;
import com.huitu.sjclub.controller.admin.UserController;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.UserService;
import com.huitu.sjclub.util.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	//private static final String INSTALL_INIT_CONFIG_FILE_PATH = "/install_init.conf";

	private static final Logger logger = Logger.getLogger(InitListener.class.getName());

	private ServletContext servletContext;

	@Value("${system.version}")
	private String systemVersion;

	@Resource(name = "userServiceImpl")
	private UserService userService;


	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
			String info = "I|n|i|t|i|a|l|i|z|i|n|g| |社|团|管|理|+|+| |" + systemVersion;
			logger.info(info.replace("|", ""));
			Setting setting= SystemUtils.getSetting();
			String defaultName=setting.getDefaultName();
			String password=setting.getDefaultPassword();
			User user=userService.findByUsername(defaultName);
			if(user!=null){
				logger.info("管理员账号admin");
			}else{
				userService.setDefaultUser(defaultName,password);
			}
		}
	}

}
