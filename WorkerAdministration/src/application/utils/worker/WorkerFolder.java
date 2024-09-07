package application.utils.worker;

import java.io.File;

public class WorkerFolder {
	
	private Worker worker;
	
	private String mainPath;
	private String hourPath;
	private String wFilePath;
	
	public WorkerFolder(Worker w) {
		this.worker = w;
		
		this.mainPath = "C:\\Users\\ctrap\\Desktop\\test\\workadministration\\worker\\" 
				+ String.valueOf(this.worker.getId());
		
		File file = new File(this.mainPath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		this.hourPath = this.mainPath + "\\hours.yml";
		this.wFilePath = this.mainPath + "\\" + String.valueOf(this.worker.getId()) + ".yml";
	}
	
	public String getMainPath() {
		return mainPath;
	}
	
	public String getHourPath() {
		return hourPath;
	}
	
	public String getWorkerFilePath() {
		return wFilePath;
	}
}
