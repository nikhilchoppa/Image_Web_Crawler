package com.eulerity.hackathon.imagefinder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(name = "ImageFinder", urlPatterns = { "/main" })
public class ImageFinder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final Gson GSON = new GsonBuilder().create();

	public String[] getImagesFromWebsite(String url) {
		// Check if the URL starts with http:// or https://, if not, prepend https://
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "https://" + url;
		}
		WebCrawler webCrawler = new WebCrawler();
		webCrawler.crawlWebsite(0, url);
		return webCrawler.getImages();
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/json");
		String path = req.getServletPath();
		String url = req.getParameter("url");
		System.out.println("Got request of:" + path + " with query param:" + url);
		String[] images = getImagesFromWebsite(url);
		resp.getWriter().print(GSON.toJson(images));
	}
}
