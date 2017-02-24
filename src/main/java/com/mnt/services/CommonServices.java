package com.mnt.services;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonServices {
	
	@Value("${imgpath}")
	private String imgpath;
	
	public static void createDir(String urlforFolder, int id) {
		File file3 = new File(urlforFolder);
		if (!file3.exists()) {
			file3.mkdirs();
		}
	}

	public String setImage(MultipartFile file,String foldername, int id){
		String urlforFolder=imgpath + "/" + foldername + "/" + id + "/";
		File deletefiles = new File(urlforFolder);
		try {
			if(deletefiles.exists()){
				FileUtils.cleanDirectory(deletefiles);				
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		CommonServices.createDir(urlforFolder, id);
		String filenamePath = "/" + foldername + "/" + id + "/"+ file.getOriginalFilename();
		String filename = imgpath + filenamePath;
		File f = new File(filename);
		try {
			file.transferTo(f);
		} catch (Exception e) {
			
			f.delete();
			try {
				file.transferTo(f);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return filenamePath;
	}
}
