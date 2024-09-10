package application.utils.worker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.utils.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkerManager {
	
	private String dirPath;
	private String cfgPath;
	
	private ExecutorService exe;
	
	private volatile ObservableList<Worker> workers;
	
	private WorkerSearchAlgo wsa;
	
	private int lastID;
	private int activeWorkers;
	private String datasepartor;
	
	
	public WorkerManager() {
		this.dirPath = "C:\\Users\\ctrap\\Desktop\\test\\workadministration\\worker";
		this.cfgPath = "C:\\Users\\ctrap\\Desktop\\test\\workadministration\\config.yml";
		
		this.exe = Executors.newCachedThreadPool();
				
		File file = new File(this.dirPath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		if(!FileManager.exists(this.cfgPath)) {
			FileManager.createFile(this.cfgPath);
			
			String[] standardFile = new String[3];
			
			standardFile[0] = "LetzteID: 0";
			standardFile[1] = "Data-Separator: ,";
			
			FileManager.writeToFile(this.cfgPath, standardFile);
		}
		
		this.loadCfg();
		
		if(this.hasWorkers()) {
			this.workers = FXCollections.synchronizedObservableList(this.loadAllWorkers());
		} else {
			this.workers = 
					FXCollections.observableArrayList(Collections.synchronizedCollection(new ArrayList<Worker>()));
		}
		
		this.activeWorkers = this.getActiveWorker();
		
		this.wsa = new WorkerSearchAlgo(this);		
	}
	
	public void saveCfg() {
		if(FileManager.exists(this.cfgPath)) {
			FileManager.clearFile(this.cfgPath);
			String[] file = new String[3];
			
			file[0] = "LetzteID: " + String.valueOf(this.lastID);
			file[1] = "Data-Separator: " + this.datasepartor;
			
			FileManager.writeToFile(this.cfgPath, file);
		}
	}
	
	public void createWorker(NewWorker nw) {
		if(nw != null && this.workers != null) {
			synchronized (this.workers) {
				Worker w = new Worker(this.lastID + 1, nw.getFirstName(),
						nw.getLastName(), nw.getBirthday());
				
				if(!this.workers.contains(w)) {
					this.workers.add(w);
					w.create();
					this.lastID++;
					this.activeWorkers++;
					this.wsa.updateWorkerData();
					
					this.saveCfg();
				}
			}
		}
	}
	
	public void removeWorker(Worker w, boolean delete) {
		if(w != null && this.workers != null) {
			if(this.workers.contains(w)) {
				for(int i = 0; i < this.workers.size(); i++) {
					if(this.workers.get(i) != null) {
						if(this.workers.get(i).equals(w)) {
							this.workers.remove(i);
						}
					}
				}
				
				this.wsa.updateWorkerData();
				this.activeWorkers--;
				
				if(delete) {
					this.deleteDirc(w);
				}
				
				this.saveCfg();
			}
		}
	}
	
	public boolean convertFile(String path) {
		if(path != null && path != "" && FileManager.exists(path)) {
			try {
				Future<Boolean> future = 
						this.exe.submit(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						ArrayList<String> list = FileManager.readFileAsList(path);
						boolean b = false;
						
						if(list != null && list.size() > 0) {
							list.forEach(w -> {
								if(w != null) {
									String[] split = w.split(datasepartor);
									if(split[0] != "" && split[1] != "" && split[2] != "") {
										NewWorker nw = new NewWorker(split[0], split[1], split[2]);
										createWorker(nw);
									}
								}
							});
							
							b = true;
						}
						return b;
					}
				});		
				return future.get();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public Worker getWorkerBYID(int id) {
		Worker w = null;
		if(id != 0 && this.workers != null && this.workers.size() > 0) {
			for(int i = 0; i < this.workers.size(); i++) {
				if(this.workers.get(i) != null) {
					if(this.workers.get(i).getId() == id) {
						w = this.workers.get(i);
						break;
					}
				}
			}
		}
		return w;
	}
	
	public Worker getWorkerBYNAME(String firstName, String lastName) {
		Worker w = null;
		if(firstName != "" && lastName != "" && this.workers != null && this.workers.size() > 0) {
			for(int i = 0; i < this.workers.size(); i++) {
				if(this.workers.get(i) != null) {
					if(this.workers.get(i).getFirstName().equalsIgnoreCase(firstName) && 
							this.workers.get(i).getFirstName().equalsIgnoreCase(lastName)) {
						w = this.workers.get(i);
						break;
					}
				}
			}
		}
		return w;
	}
	
	public Worker getWorkerBYNAMEANDBD(String firstName, String birthday) {
		Worker w = null;
		if(firstName != "" && birthday != "" && this.workers != null && this.workers.size() > 0) {
			for(int i = 0; i < this.workers.size(); i++) {
				if(this.workers.get(i) != null) {
					if(this.workers.get(i).getFirstName().equalsIgnoreCase(firstName) &&
							this.workers.get(i).getBirthDay().getDate().equalsIgnoreCase(birthday)) {
						w = this.workers.get(i);
						break;
					}
				}
			}
		}
		return w;
	}
	
	public WorkerSearchAlgo getWSA() {
		return wsa;
	}
	
	public ObservableList<Worker> getWorkers(){
		return workers;
	}
	
	private ObservableList<Worker> loadAllWorkers() {
		try {
			
			Future<ObservableList<Worker>> future = this.exe.submit(new Callable<ObservableList<Worker>>() {

				@Override
				public ObservableList<Worker> call() throws Exception {
					
					ObservableList<Worker> tempList = FXCollections.observableArrayList();
					
					if(dirPath != null) {
						File file = new File(dirPath);
						
						if(file != null) {
							for(File files : file.listFiles()) {
								if(files.isDirectory()) {
									WorkerFile wFile = 
											new WorkerFile(Integer.valueOf(files.getName()));
									
									if(wFile != null) {
										Worker w = new Worker(wFile);
										
										if(w != null) {
											tempList.add(w);
										}
									}
								}
							}							
						}
					}
					return tempList;
				}
			});
			
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void loadCfg() {
		if(FileManager.exists(this.cfgPath)) {
			String[] file = FileManager.readFileAsArray(this.cfgPath);
			
			for(int i = 0; i < file.length; i++) {
				if(file[i] != null && file[i] != "") {
					String[] split = file[i].split(": ");
					switch (split[0].toLowerCase()) {
						case "letzteid" -> this.lastID = Integer.valueOf(split[1]);
						case "mitarbeiterzahl" -> this.activeWorkers = Integer.valueOf(split[1]);
						case "data-separator" -> this.datasepartor = String.valueOf(split[1]);
					}
				}
			}
		}
	}
	
	private void deleteDirc(Worker w) {
		if(w != null) {
			if(FileManager.exists(w.getFolder().getMainPath())) {
				File file = new File(w.getFolder().getMainPath());
				
				for(File files : file.listFiles()) {
					if(!files.isDirectory()) {
						files.delete();
					}
				}
				
				if(file.listFiles().length == 0) {
					file.delete();
				}
			}
		}
	}
	
	private boolean hasWorkers() {
		if(this.dirPath != null) {
			File file = new File(this.dirPath);
			
			if(file != null && file.listFiles().length > 0) {
				return true;
			}
		}
		return false;
	}
	
	private int getActiveWorker() {
		if(this.dirPath != null) {
			File file = new File(this.dirPath);
			
			if(file != null) {
				return file.listFiles().length;
			}
		}
		
		return 0;
	}
}
