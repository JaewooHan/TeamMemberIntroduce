package Login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.search.DateUtil;

import Manager.MyPersistenceManager;
import User.UserAccount;
import User.UserLoginToken;

public class loginServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("EUC-KR");
		resp.setContentType("text/plain;charset=utf-8");

		String id = req.getParameter("id");
		String password = req.getParameter("password");
		String days = req.getParameter("days");
		int time = 30 * 24 * 60 * 60;
		boolean success = false;

		PersistenceManager pm = MyPersistenceManager.getManager();

		Query qry = pm.newQuery(UserAccount.class);
		qry.setFilter("userID == idParam");
		qry.declareParameters("String idParam");
		List<UserAccount> userAccount = (List<UserAccount>) qry.execute(id);

		if (userAccount.size() == 0) {
			success = false;
		}
		else if (userAccount.get(0).getPassword().equals(password)) {
			success = true;
		}
		else {
			success = false;
		}

		if (!success) {
			resp.getWriter().println("<html><head><title>로그인오류</title></head><body>");
			resp.getWriter().println("로그인실패<a href='login.html'>다시시도</a>");
			resp.getWriter().println("</body></html>");
		}
		if (success) {
			HttpSession session = req.getSession();
			session.setAttribute("login_id", id);
			if (days != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.DATE, +30);
				String day = new SimpleDateFormat("yyyyMMdd").format(calendar
						.getTime());
				String uuid = UUID.randomUUID().toString();
				Cookie token = new Cookie("token", uuid);
				token.setMaxAge(time);
				resp.addCookie(token);
				UserLoginToken ult = new UserLoginToken(uuid, id, day);
				pm.makePersistent(ult);
				session.setMaxInactiveInterval(time);
			}
			resp.sendRedirect("index.html");
		}
	}
}
