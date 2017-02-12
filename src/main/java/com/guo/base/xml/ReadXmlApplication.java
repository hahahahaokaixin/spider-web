package com.guo.base.xml;

import java.util.List;

public class ReadXmlApplication {

	private List<String> springConfigs;
	private List<String> propsConfigs;

	public ReadXmlApplication(List<String> springConfigs, List<String> propsConfigs) {
		this.springConfigs = springConfigs;
		this.propsConfigs = propsConfigs;
	}

	public IMyReadConfig mc;

	public void setMyConfig(IMyReadConfig mc) {
		this.mc = mc;
	}

	public List<String> initMyConfig() {
		return this.mc.readConfig(springConfigs, propsConfigs);
	}

}
