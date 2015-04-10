package com.ac.shinhan.csp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTeamMemberServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("EUC-KR");
		resp.setContentType("text/plain;charset=utf-8");
		
		Long key = Long.parseLong(req.getParameter("delKey"));
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m =  pm.getObjectById(TeamMember.class, key);
		pm.deletePersistent(m);
		
		PrintWriter out = resp.getWriter();
		
		out.println("<html><body>");
	    out.println("<tr><td> <a href=\"/retrieveteammember\" target=\"_self\"> 삭제완료 </a> </td></tr>");
		out.println("</body></html>");
	}
}
