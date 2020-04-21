package com.getwireless.bbs.services;


import java.io.InputStream;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuth1Token;
@Service
public class FlickrService {
	private Flickr flickr;
	private UploadMetaData uploadMetaData = new UploadMetaData();
	private String apiKey = "293ef392ad566cd4393cd594128bd321";
	private String sharedSecret = "2ebf90b1caabdd8b";

	private void connect() {
		flickr = new Flickr(apiKey, sharedSecret, new REST());
		Auth auth = new Auth();
		auth.setPermission(Permission.READ);
		auth.setToken("72157713780624712-eb57f37e2c0d6027");
		auth.setTokenSecret("de58677b8311dd9f");
		RequestContext requestContext = RequestContext.getRequestContext();
		requestContext.setAuth(auth);
		flickr.setAuth(auth);
	}

	public String uploadImage(InputStream stream, String fileName) throws FlickrException {
		connect();
		uploadMetaData.setTitle(fileName);
		String photoId = flickr.getUploader().upload(stream, uploadMetaData);
		return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
	}

	public void authentication() throws FlickrException {

		
	        

	        Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());
	        Flickr.debugStream = false;
	        AuthInterface authInterface = flickr.getAuthInterface();

	        Scanner scanner = new Scanner(System.in);

	        OAuth1RequestToken requestToken = authInterface.getRequestToken();
	        System.out.println("token: " + requestToken);

	        String url = authInterface.getAuthorizationUrl(requestToken, Permission.DELETE);
	        System.out.println("Follow this URL to authorise yourself on Flickr");
	        System.out.println(url);
	        System.out.println("Paste in the token it gives you:");
	        System.out.print(">>");

	        String tokenKey = scanner.nextLine();
	        scanner.close();

	        OAuth1Token accessToken = authInterface.getAccessToken(requestToken, tokenKey);
	        System.out.println("Authentication success");

	         Auth auth = authInterface.checkToken(accessToken);

	        // This token can be used until the user revokes it.
	        System.out.println("Token: " + accessToken.getToken());
	        System.out.println("Secret: " + accessToken.getTokenSecret());
	        System.out.println("nsid: " + auth.getUser().getId());
	        System.out.println("Realname: " + auth.getUser().getRealName());
	        System.out.println("Username: " + auth.getUser().getUsername());
	        System.out.println("Permission: " + auth.getPermission().getType());
	}
	
	
	
	public static void main(String[] args) {
		FlickrService flickrService = new FlickrService();

		try {
			flickrService.authentication();
		} catch (FlickrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
}
