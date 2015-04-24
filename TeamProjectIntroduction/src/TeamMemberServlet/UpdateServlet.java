package TeamMemberServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Manager.MyPersistenceManager;
import Team.TeamMember;

public class UpdateServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html");
	    
		Long key = Long.parseLong(req.getParameter("key"));
		
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m = pm.getObjectById(TeamMember.class, key);
		
		m.setName(req.getParameter("name"));
		m.setNum(req.getParameter("num"));
		m.setPnum(req.getParameter("pnum"));
		m.setEmail(req.getParameter("email"));
		m.setCid(req.getParameter("cid"));
		m.setKap(req.getParameter("kap"));
		m.setGitid(req.getParameter("gitid"));
		pm.close();
		
		PrintWriter out = resp.getWriter();
		
		out.println("<html><body>");
	    out.println("<tr><td><a href=\"/retrieveteammember\" target=\"_self\"> 수정완료 </a> </td></tr>");
		out.println("</body></html>");	
	}
}
