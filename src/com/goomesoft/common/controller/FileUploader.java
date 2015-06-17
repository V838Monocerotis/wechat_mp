package com.goomesoft.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.goomesoft.common.utils.JsonUtil;

/**
 * 文件上传
 */
@WebServlet("/uploader.action")
@MultipartConfig
public class FileUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(FileUploader.class);
	
	private static final String FORMATTER = "yyyyMMddHHmmss";
	
	private static String storePath = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploader() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		storePath = config.getServletContext().getRealPath("/") + "File";
		File file = new File(storePath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		List<String> files = new ArrayList<String>();

        log.debug("upload file size :" + request.getParts().size());
		String filename;
		for(Part part : request.getParts()) {
			filename = getFileName(part);
			part.write(storePath + File.separator + filename);
			log.info("file : " + filename);
			files.add(filename);
		}

        String json = JsonUtil.toJsonString(files);
        log.info("json : " + json);
        PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

	private String getFileName(Part part) {
		String header = part.getHeader("content-disposition");
		String originName = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);
		String ext = originName.substring(originName.lastIndexOf("."), originName.length());
		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER);
		String time = sdf.format(new Date());

        Random random = new Random();
        String rand = String.valueOf(random.nextInt(100));
		
		return time + rand + ext;
	}
	
}
