package pcsma15.spring.servlet.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;


@org.springframework.stereotype.Controller
public class Controller{

	public List<String> fileList=new ArrayList<String>();
	


	@RequestMapping(value="/home/upload", method=RequestMethod.POST)

	public ModelAndView fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result, Model model) {


		InputStream inputStream = null;
		OutputStream outputStream = null;


		MultipartFile file = uploadedFile.getFile();
		String fileName = file.getOriginalFilename();


		try {
			inputStream = file.getInputStream();

			File newFile = new File("C:/" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileList.add(fileName);
		return new ModelAndView("showFile", "message", fileName);
	}

	@RequestMapping(value="/home/fileSearch", method=RequestMethod.GET)
    public @ModelAttribute() void search() {
       // return repository.findAll();
	 for(int i=0;i<fileList.size();i++){
		 System.out.println(fileList.get(i));
	 }
	 
	 
	//add delete

	//add post
	}
	@RequestMapping(value="/home" ,method = RequestMethod.GET)
	public String CheckHome(ModelMap model) {

		return "home";
	}
}
