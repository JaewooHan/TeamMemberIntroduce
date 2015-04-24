package TeamMemberServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Manager.MyPersistenceManager;
import Team.TeamMember;

public class UpdateTeamMemberServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html"); 

		
		Long ckey = Long.parseLong(req.getParameter("upKey"));
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m = pm.getObjectById(TeamMember.class, ckey);
		
		String ck = null;
		if(m.getKap() != null)
			ck = "checked";
		
	    PrintWriter out =  resp.getWriter();
	    
	    out.println("<html><body><h1>����� ���� ����</h1><table><tr><form method='post'action='/update'>"); 
		out.println("<tr><td> �̸� </td> <td><input type = \"text\" name = \"name\" value = "+m.getName()+"></td></tr>");
		out.println("<tr><td> �й� </td> <td><input type = \"text\" name = \"num\"value = "+m.getNum()+"></td></tr>");
		out.println("<tr><td> ��ȭ��ȣ </td> <td><input type = \"text\" name = \"pnum\"value = "+m.getPnum()+"></td></tr>");
		out.println("<tr><td> �����ּ� </td> <td><input type = \"text\" name = \"email\"value = "+m.getEmail()+"></td></tr>");
	    out.println("<tr><td> ī��ID </td> <td><input type = \"text\" name = \"cid\"value = "+m.getCid()+"></td></tr>");
	    out.println("<tr><td> ���忩�� </td> <td><input type = \"checkbox\" name = \"kap\""+ck+"></td></tr>");
	    out.println("<tr><td> GitHubID </td> <td><input type = \"text\" name = \"gitid\"value = "+m.getGitid()+"></td></tr>");
	    out.println("<tr><td> <input type=\"submit\" value=\"����\"></td></tr>");
	    out.println("<tr><td> <a href=\"/retrieveteammember\" target=\"_self\"> �������� </a> </td></tr>");
	    out.println("<tr><td><input type = \"hidden\" name = \"key\" value = "+m.getKey()+"></td></tr>");
		out.println("</tr></table></body></html>");
	}
}
