package TeamMemberServlet;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import Manager.MyPersistenceManager;
import Team.TeamMember;

public class AddTeamMemberServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("EUC-KR");
	    resp.setContentType("text/plain;charset=utf-8");

		String name = req.getParameter("name");
		String num = req.getParameter("num");
		String pnum = req.getParameter("pnum");
		String email = req.getParameter("email");
		String cid = req.getParameter("cid");
		String kap = req.getParameter("kap");
		String gitid = req.getParameter("gitid");
		
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m = new TeamMember(name, num, pnum, email, cid, kap, gitid);
		pm.makePersistent(m);
		
		resp.getWriter().println("<html><body>");
		resp.getWriter().println("�̸�: "+name+"<br>");
		resp.getWriter().println("�й�: "+num+"<br>");
		resp.getWriter().println("��ȭ��ȣ: "+pnum+"<br>");
		resp.getWriter().println("�����ּ�: "+email+"<br>");
		resp.getWriter().println("ī��ID: "+cid+"<br>");
		if(kap != null)
			resp.getWriter().println("���忩��: ����"+"<br>");
		else
			resp.getWriter().println("���忩��: ����"+"<br>");
		resp.getWriter().println("GitHubID: "+gitid+"<br>");
		resp.getWriter().println("<td> <a href=index.html target=_self >ó������</a></td>");
		resp.getWriter().println("</body></html>");
		
	}
}
