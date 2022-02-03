package com.authentication;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/")
public class AuthenticationController extends HttpServlet {
	
	DAO dao;
	public void init() {
		dao = new DAO();
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String path = request.getServletPath();
		if(path.equals("/")) {
			response.sendRedirect("Login.jsp");
		}
		else if(path.equals("/login")) {
			try {
				login(request, response);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				
				e.printStackTrace();
			}
		}
		else if(path.equals("/register")) {
			try {
				register(request, response);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		else if(path.equals("/logout")) {
			logout(request, response);
		}
		else {
			response.sendRedirect("Error.jsp");
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == ""|| username == null || password =="" || password == null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginerror", "Invalid Input");
			response.sendRedirect("Login.jsp");
			return;
		}
		password = generateHash(password);
		HttpSession session = request.getSession();
		if(dao.authenticate(username, password)) {
			session.setAttribute("username",username);
			response.sendRedirect("Welcome.jsp");
		}
		else {
			session.setAttribute("loginerror", "Invalid Credentials");
			response.sendRedirect("Login.jsp");
		}
	}
	
	void register(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == ""|| username == null || password =="" || password == null) {
			HttpSession session = request.getSession();
			session.setAttribute("registererror", "Invalid Input");
			response.sendRedirect("Register.jsp");
			return;
		}
		password = generateHash(password);
		HttpSession session = request.getSession();
		session.setAttribute("registererror", "noerror");
			if(!dao.checkUsernameAvailability(username)) {
				dao.createNew(username, password);
				response.sendRedirect("Login.jsp");
				session.removeAttribute("registererror");
			}
			else {
				session.setAttribute("registererror", "username not available");
				response.sendRedirect("Register.jsp");
			} 
	}
	
	void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("username")!= null) {
			session.removeAttribute("username");
		}
		response.sendRedirect("Login.jsp");
	}
	
	String generateHash(String password) {
        String encryptedpassword = null;  
        try   
        {  
            MessageDigest m = MessageDigest.getInstance("MD5");  
              
            m.update(password.getBytes());  
                
            byte[] bytes = m.digest();  
              
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
              
            encryptedpassword = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            e.printStackTrace();  
        }  
        return encryptedpassword;  
	}

}
