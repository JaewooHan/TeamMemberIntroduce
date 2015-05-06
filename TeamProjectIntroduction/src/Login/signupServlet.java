package Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Manager.MyPersistenceManager;
import User.UserAccount;
import User.UserLoginToken;

public class signupServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("EUC-KR");
		resp.setContentType("text/plain;charset=utf-8");

		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		boolean error = false;
		
		PersistenceManager pm = MyPersistenceManager.getManager();
		Query qry = pm.newQuery(UserAccount.class);
		List<UserAccount> userAccount = (List<UserAccount>) qry.execute();
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원가입오류</title></head><body>");
		if (id.equals("") || password.equals("") || name.equals("")) {
			out.println("정보를 모두 입력해주세요 <a href='signup.html'>다시시도</a>");
		} else {
			for (UserAccount u : userAccount) {
				if (u.getUserID().equals(id)) {
					out.println("아이디중복 <a href='signup.html'>다시시도</a>");
					error = true;
				}
			}
			if (error == false) {
				pm.makePersistent(new UserAccount(id, password, name));
				resp.sendRedirect("login.html");
			}
		}
		resp.getWriter().println("</body></html>");
	}

}
