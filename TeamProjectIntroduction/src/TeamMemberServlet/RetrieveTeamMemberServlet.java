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
		
		resp.getWriter().println("<html><head><title>팀멤버정보화면</title></head><body>");
		resp.getWriter().println("<h1>팀멤버정보</h1>");
		resp.getWriter().println("<tr>"+id+"님을 환영합니다.</tr>");
		resp.getWriter().println("<table border=1cellspacing=3cellpadding=3");
		resp.getWriter().println("<tr bgcolor=#24FCFF>");
		resp.getWriter().println("<th>이름</th><th>학번</th><th>전화번호</th><th>이메일</th>"
				+ "<th>카톡ID</th><th>팀장여부</th><th>GitHubID</th><th>삭제</th>");
		resp.getWriter().println("</tr>");

		for(TeamMember tm : memberList){
			if(tm.getKap() != null)
				reader = "팀장";
			else
				reader = "팀원";
			resp.getWriter().println("<tr bgcolor=#ffffff>");
			resp.getWriter().println("<td> <a href=\'/updateteammember?upKey="+tm.getKey()+"\' target=_self >"
					+ tm.getName()+"</a></td>"
					+"<td>" + tm.getNum()+"</td>"
					+"<td>" + tm.getPnum()+"</td>"
					+"<td>" + tm.getEmail()+"</td>"
					+"<td>" + tm.getCid()+"</td>"
					+"<td>" + reader +"</td>"
					+"<td>" + tm.getGitid()+"</td>"
					+"<td> <input type=button value=삭제 onclick=\"location.href=\'/deleteteammember?delKey="+tm.getKey()+"\'\"></td>");
			resp.getWriter().println("</tr>");
		}
		resp.getWriter().println("</table>");
		resp.getWriter().println("<td> <a href=index.html target=_self >처음으로</a></td>");
		resp.getWriter().println("</body></html>");
	}
}
