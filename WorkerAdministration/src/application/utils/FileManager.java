package application.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
	
	public static void createFile(String filePath) {
		try {
			File file = new File(filePath);
			
			if(!file.exists()) {
				
				file.createNewFile();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createFile(String path, String name) {
		try {
			File file = new File(path + "\\" + name);
			
			if(!file.exists()) {
				
				file.createNewFile();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String path, String name, String[] text) {
		File file = new File(path + "\\" + name);
		ArrayList<String> finishedList;
		BufferedWriter bw;
		Scanner s;
		
		try {
			if(file.exists()) {
				finishedList = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					finishedList.add(s.nextLine());
				}
				s.close();
				
				for(int i = 0; i < text.length; i++) {
					if(text[i] != null) {
						finishedList.add(text[i]);
					}
				}
				
				bw = new BufferedWriter(new FileWriter(file));
				
				finishedList.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				bw.close();
			}
		} catch(Exception e) {
		}
	}
	
	public static void writeToFile(String path, String[] text) {
		File file = new File(path);
		ArrayList<String> finishedList;
		BufferedWriter bw;
		Scanner s;
		
		try {
			if(file.exists()) {
				finishedList = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					finishedList.add(s.nextLine());
				}
				s.close();
				
				for(int i = 0; i < text.length; i++) {
					if(text[i] != null) {
						finishedList.add(text[i]);
					}
				}
				
				bw = new BufferedWriter(new FileWriter(file));
				
				finishedList.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				bw.close();
			}
		} catch(Exception e) {
		}
	}
	
	public static void writeToFile(String filePath, ArrayList<String> list) {
		File file = new File(filePath);
		ArrayList<String> finishedList;
		BufferedWriter bw;
		Scanner s;
		
		try {
			if(file.exists()) {
				finishedList = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					finishedList.add(s.nextLine());
				}
				s.close();
				
				list.forEach(str -> finishedList.add(str));
				
				bw = new BufferedWriter(new FileWriter(file));
				
				finishedList.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				bw.close();
			}
			
		} catch(Exception e) {
		}
	}
	
	public static void writeToFile(String path, String name, ArrayList<String> list) {
		File file = new File(path + "\\" + name);
		ArrayList<String> finishedList;
		BufferedWriter bw;
		Scanner s;
		
		try {
			if(file.exists()) {
				finishedList = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					finishedList.add(s.nextLine());
				}
				s.close();
				
				list.forEach(str -> finishedList.add(str));
				
				bw = new BufferedWriter(new FileWriter(file));
				
				finishedList.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				bw.close();
			}
			
		} catch(Exception e) {
		}
	}
	
	public static void writeToFile(String path, String name, String text) {
		File file = new File(path + "\\" + name);
		ArrayList<String> list;
		BufferedWriter bw;
		Scanner s;
		
		try {
			if(file.exists()) {
				list = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					list.add(s.nextLine());
				}
				s.close();
				
				list.add(text);
				
				bw = new BufferedWriter(new FileWriter(file));
				
				list.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				
				bw.close();
			}
		} catch(Exception e) {
		}
	}
	
	public static void removeOutofFile(String path, String name, String text) {
		File file = new File(path + "\\" + name);
		ArrayList<String> finishedList;
		BufferedWriter bw;
		Scanner s;
				
		try {
			if(file.exists()) {
				finishedList = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					finishedList.add(s.nextLine());
				}
				
				s.close();
								
				for(int i = 0; i < finishedList.size(); i++) {
					if(finishedList.get(i).equals(text)) {
						finishedList.remove(i);
					}
				}
				
				bw = new BufferedWriter(new FileWriter(file));
				
				finishedList.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				bw.close();
			}
		} catch(Exception e) {
			
		}
	}
	
	public static void removeOutofFile(String path, String name, String[] text) {
		File file = new File(path + "\\" + name);
		ArrayList<String> finishedList;
		Scanner s;
		BufferedWriter bw;
		
		try {
			if(file.exists()) {
				finishedList = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					finishedList.add(s.nextLine());
				}
				
				s.close();
				
				for(int i = 0; i < text.length; i++) {
					if(finishedList.contains(text[i])) {
						for(int i1 = 0; i1 < finishedList.size(); i1++) {
							if(finishedList.get(i1).equals(text[i])) {
								finishedList.remove(i1);
							}
						}
					}
				}
												
				bw = new BufferedWriter(new FileWriter(file));
				
				finishedList.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				bw.close();
			}
		} catch(Exception e) {
			
		}
	}
	
	public static void removeOutofFile(String path, String name, ArrayList<String> list) {
		removeOutofFile(path, name, (String[]) list.toArray());
	}
	
	public static void changeInFile(String path, String name, String old, String newStr) {
		File file = new File(path + "\\" + name);
		ArrayList<String> list = readFileAsList(path, name);
		BufferedWriter bw;
				
		try {
			if(file.exists()) {
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i).contains(":")) {
						String[] split = list.get(i).split(": ");
						
						if(split[0].equals(old)) {
							list.set(i, newStr);
							break;
						}
					}
				}
				
				bw = new BufferedWriter(new FileWriter(file));
				
				list.forEach(str -> {
					try {
						bw.write(str);
						bw.write(System.getProperty("line.separator"));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				bw.close();
			}
		} catch(Exception e) {
			
		}
	}
	
	public static void clearFile(String filePath) {
		File file = new File(filePath);
		BufferedWriter bw;
		
		try {
			if(file.exists()) {
				bw = new BufferedWriter(new FileWriter(file));
				
				bw.write("");
				bw.flush();
				
				bw.close();
			}
		} catch(Exception e) {
			
		}
		
	}
	
	public static void clearFile(String path, String name) {
		File file = new File(path + "\\" + name);
		BufferedWriter bw;
		
		try {
			if(file.exists()) {
				bw = new BufferedWriter(new FileWriter(file));
				
				bw.write("");
				bw.flush();
				
				bw.close();
			}
		} catch(Exception e) {
			
		}
		
	}
	
	public static ArrayList<String> readFileAsList(String filePath) {
		File file = new File(filePath);
		ArrayList<String> list;
		Scanner s;
		
		try {
			if(file.exists()) {
				list = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					list.add(s.nextLine());
				}
				
				s.close();
				
				return list;
			}
		} catch(Exception e) {
		}	
		return null;
	}
	
	public static ArrayList<String> readFileAsList(String path, String name) {
		File file = new File(path + "\\" + name);
		ArrayList<String> list;
		Scanner s;
		
		try {
			if(file.exists()) {
				list = new ArrayList<String>();
				s = new Scanner(file);
				
				while(s.hasNext()) {
					list.add(s.nextLine());
				}
				
				s.close();
				
				return list;
			}
		} catch(Exception e) {
		}	
		return null;
	}
	
	public static String[] readFileAsArray(String filePath) {
		ArrayList<String> list = readFileAsList(filePath);
		
		String[] str =  new String[list.size()];
		
		for(int i = 0; i < list.size(); i ++) {
			str[i] = list.get(i);
		}
		
		return str;
	}
	
	public static String[] readFileAsArray(String path, String name) {
		ArrayList<String> list = readFileAsList(path, name);
		
		String[] str =  new String[list.size()];
		
		for(int i = 0; i < list.size(); i ++) {
			str[i] = list.get(i);
		}
		
		return str;
	}
	
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			return true;
		}
		return false;
	}
	
	public static boolean exists(String path, String name) {
		File file = new File(path + "\\" + name);
		if(file.exists()) {
			return true;
		}
		return false;
	}

}
