package com.pcsma.videoservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FileCounter
 */

@WebServlet("/FileCounter")
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	int count;
	public static final String SEPARATOR = "!!";
	public static final String SEPARATOR_ITEM = "??";
	ArrayList<CL_Video> mVideoList;
	String[] mVideoNames = { "Anaconda", "Desi Kalaakar", "Love Dose" };
	String[] mVideoAuthors = { "Nicki Minaj", "Honey Singh", "HoneySingh" };
	String[] mVideoLength = { "4:00 mins", "3:00 mins", "3:00 mins" };
	String[] mVideoLinks = { "https://www.youtube.com/watch?v=LDZX4ooRsWs", "https://www.youtube.com/watch?v=KhnVcAC5bIM"
			, "https://www.youtube.com/watch?v=TvngY4unjn4" };
	int[] mVideoRatings = { 5, 4, 5};


	@Override
	public void init() throws ServletException {
		//Enter some videos into the initial database
		mVideoList = new ArrayList<CL_Video>();
		for(int i=0; i<mVideoNames.length; i++){
			mVideoList.add(new CL_Video(mVideoNames[i], mVideoLength[i], mVideoAuthors[i], mVideoLinks[i], mVideoRatings[i]));
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter showClient=response.getWriter();
		for (CL_Video v: mVideoList){
			showClient.write(v.getVideoName() + SEPARATOR + v.getVideoLength() + SEPARATOR 
					+ v.getVideoAuthor() + SEPARATOR + v.getVideoLink() + SEPARATOR + v.getVideoRating());
			if(mVideoList.indexOf(v) < mVideoList.size()){
				showClient.write(SEPARATOR_ITEM);
			}
		}
		showClient.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String vidName   = request.getParameter("name");
		String vidAuthor = request.getParameter("author");
		String vidLink   = request.getParameter("link");
		String vidLength = request.getParameter("length");
		String vidRating = request.getParameter("rating");

		response.setContentType("text/plain");

		if(vidName == null || vidAuthor == null || vidLink == null || vidLength == null || vidRating == null){
			response.sendError(400);
			response.getWriter().write("Missing arguments. Retry. Must be name, author, link, length and rating.");//, arg1, arg2)
		}else{
			CL_Video vidToAdd=new CL_Video(vidName, vidLength, vidAuthor, vidLink, Integer.parseInt(vidRating));
			mVideoList.add(vidToAdd);
			response.getWriter().write("Video was successfully added");
		}
	} 

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String vidName   = request.getParameter("name");
		String vidAuthor = request.getParameter("author");
		String vidLink   = request.getParameter("link");
		String vidLength = request.getParameter("length");
		String vidRating = request.getParameter("rating");
		String vidIndex  = request.getParameter("index");

		response.setContentType("text/plain");

		if(!vidIndex.equals("")){
			int index = Integer.parseInt(vidIndex);
			if(vidName == null || vidAuthor == null || vidLink == null || vidLength == null || vidRating == null){
				response.sendError(400);
				response.getWriter().write("Missing arguments. Retry. Must be name, index, author, link, length and rating.");//, arg1, arg2)
			}else{
				if(index < mVideoList.size()){
					CL_Video vidToEdit = mVideoList.get(index);
					vidToEdit.setVideoAuthor(vidAuthor);
					vidToEdit.setVideoLength(vidLength);
					vidToEdit.setVideoLink(vidLink);
					vidToEdit.setVideoName(vidName);
					vidToEdit.setVideoRating(Integer.parseInt(vidRating));
					response.getWriter().write("Video was successfully edited");
				}else {	
					response.getWriter().write("Index out of bounds");
				}
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String indexToDelete = req.getParameter("index");

		resp.setContentType("text/plain");
		if(indexToDelete == null){
			resp.sendError(400);
			resp.getWriter().write("Missing argument index.");//, arg1, arg2)
		}else {
			try{
				if(!indexToDelete.equals("")){
					int index = Integer.parseInt(indexToDelete);
					mVideoList.remove(index);
					resp.getWriter().write("Video was successfully deleted");
				}else{
					resp.getWriter().write("Empty index provided");
				}
			}catch (NumberFormatException e){
				resp.getWriter().write("Invalid index provided");
			}catch (IndexOutOfBoundsException e1){
				resp.getWriter().write("Index out of range");
			}
		}
	}
}