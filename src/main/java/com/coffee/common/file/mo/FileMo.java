package com.coffee.common.file.mo;

import com.coffee.common.common.mo.BaseMo;

/**
 * @author QM
 */
public class FileMo extends BaseMo {

	private static final long serialVersionUID = 4846239980581260089L;
	
	private String dir;
	
	private String type;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
