package TeamMemberServlet;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Manager.MyPersistenceManager;
import Team.TeamMember;

public class RetrieveTeamMemberServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("EUC-KR");
	    resp.setContentType("text/plain;charset=utf-8");
	    
	    HttpSession session = req.getSession(false);
		String id=null;
		
		if(session == null){
			resp.getWriter().println("Session is not available");
		}
		else{
			id = (String)session.getAttribute("login_id");
		}
	    
		String reader;
	
		PersistenceManager pm = MyPersistenceManager.getManager();
		Query qry = pm.newQuery(TeamMember.class); 
		List<TeamMember> memberList = (List<TeamMember>) qry.execute(); 
		
		resp.getWriter().println("<html><head><title>���������ȭ��</title></head><body>");
		resp.getWriter().println("<h1>���������</h1>");
		resp.getWriter().println("<tr>"+id+"���� ȯ���մϴ�.</tr>");
		resp.getWriter().println("<table border=1cellspacing=3cellpadding=3");
		resp.getWriter().println("<tr bgcolor=#24FCFF>");
		resp.getWriter().println("<th>�̸�</th><th>�й�</th><th>��ȭ��ȣ</th><th>�̸���</th>"
				+ "<th>ī��ID</th><th>���忩��</th><th>GitHubID</th><th>����</th>");
		resp.getWriter().println("</tr>");

		for(TeamMember tm : memberList){
			if(tm.getKap() != null)
				reader = "����";
			else
				reader = "����";
			resp.getWriter().println("<tr bgcolor=#ffffff>");
			resp.getWriter().println("<td> <a href=\'/updateteammember?upKey="+tm.getKey()+"\' target=_self >"
					+ tm.getName()+"</a></td>"
					+"<td>" + tm.getNum()+"</td>"
					+"<td>" + tm.getPnum()+"</td>"
					+"<td>" + tm.getEmail()+"</td>"
					+"<td>" + tm.getCid()+"</td>"
					+"<td>" + reader +"</td>"
					+"<td>" + tm.getGitid()+"</td>"
					+"<td> <input type=button value=���� onclick=\"location.href=\'/deleteteammember?delKey="+tm.getKey()+"\'\"></td>");
			resp.getWriter().println("</tr>");
		}
		resp.getWriter().println("</table>");
		resp.getWriter().println("<td> <a href=index.html target=_self >ó������</a></td>");
		resp.getWriter().println("</body></html>");
	}
}
