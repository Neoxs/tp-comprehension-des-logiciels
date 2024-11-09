package parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser<T> {

	protected String projectPath;
	protected String jrePath;
	protected T parser;
	
	public Parser(String projectPath, String jrePath) {
		setProjectPath(projectPath);
		setJREPath(jrePath);
	}
	
	public String getProjectPath() {
		return projectPath;
	}
	
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
	public String getJREPath() {
		return jrePath;
	}
	
	public void setJREPath(String jrePath) {
		this.jrePath = jrePath;
	}
	
	public T getParser() {
		return parser;
	}

	public List<File> listJavaFiles(String filePath) {
		File folder = new File(filePath);
		List<File> javaFiles = new ArrayList<>();

		// Check if the folder exists and is a directory
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles(); // Call listFiles once

			// Check if files is null
			if (files != null) {
				for (File file : files) {
					String fileName = file.getName();

					if (file.isDirectory()) {
						javaFiles.addAll(listJavaFiles(file.getAbsolutePath()));
					} else if (fileName.endsWith(".java")) {
						javaFiles.add(file);
					}
				}
			} else {
				System.err.println("The folder is empty or there was an error reading the files in: " + filePath);
			}
		} else {
			System.err.println("The provided path is either invalid or not a directory: " + filePath);
		}

		return javaFiles;
	}
	
	public List<File> listJavaProjectFiles(){
		return listJavaFiles(getProjectPath());
	}
	
}