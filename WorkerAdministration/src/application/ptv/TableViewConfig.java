package application.ptv;

import java.util.ArrayList;

import application.utils.FileManager;

public class TableViewConfig {
		
	private String path;
	private String name;
	
	private String dataSeparator;
	private ArrayList<String> loadedPaths;
	
	public TableViewConfig() {
		this("", "");
	}
	
	public TableViewConfig(String path, String name) {
		this.path = path;
		this.name = name;
		this.dataSeparator = ",";
		this.loadedPaths = new ArrayList<String>();
	}
	
	public void load() {
		if(this.path != null && this.path != "" && this.name != null && this.name != "") {
			if(!FileManager.exists(this.path, this.name)) {
				FileManager.createFile(this.path, this.name);
				String[] standardConfig = new String[2];
				
				standardConfig[0] = "Separator: ,";
				standardConfig[1] = "Paths: ";
				
				FileManager.writeToFile(this.path, this.name, standardConfig);
			} else {
				String[] configData = FileManager.readFileAsArray(this.path, this.name);
				
				if(configData != null) {
					for(int i = 0; i < configData.length; i++) {
						String[] split = configData[i].split(": ");
						
						if(split != null) {
							switch (split[0]) {
								case "Separator" -> {
									if(split[1] != null && split[1] != "") {
										this.dataSeparator = split[1];
									}
								}
								case "Paths" -> {
									if(split.length > 1) {
										if(split[1] != null && split[1] != "") {
											if(split[1].contains(this.dataSeparator)) {
												String[] split1 = split[1].split(this.dataSeparator);
												if(split1.length > 0) {
													for(int i1 = 0; i1 < split1.length; i1++) {
														if(split1[i1] != null && split1[i1] != "") {
															this.loadedPaths.add(split1[i1]);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void save() {
		if(this.path != null && this.path != "" && this.name != null && this.name != "") {
			if(FileManager.exists(this.path, this.name)) {
				String[] configData = new String[2];
				
				configData[0] = "Separator: " + this.dataSeparator;
				
				String pathData = "";
				
				for(int i = 0; i < this.loadedPaths.size(); i++) {
					if(this.loadedPaths.get(i) != null && this.loadedPaths.get(i) != "") {
						if(pathData == "") {
							pathData = this.loadedPaths.get(i) + this.getDataSeparator();
						} else {
							pathData = pathData + this.loadedPaths.get(i) + 
									this.getDataSeparator();
						}
					}
				}
				
				configData[1] = "Paths: " + pathData;
				
				FileManager.clearFile(this.path, this.name);
				FileManager.writeToFile(this.path, this.name, configData);
			}
		}
	}
	
	public ArrayList<String> getLoadedPaths() {
		return loadedPaths;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataSeparator() {
		return dataSeparator;
	}

	public void setDataSeparator(String dataSeparator) {
		this.dataSeparator = dataSeparator;
	}

}
