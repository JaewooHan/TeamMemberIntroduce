package Login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Manager.MyPersistenceManager;
import Team.TeamMember;
import User.UserAccount;
import User.UserLoginToken;

public class entryServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserLoginToken ult = null;
		HttpSession session = req.getSession();
		PersistenceManager pm = MyPersistenceManager.getManager();
		String day = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		Cookie[] cookieList = req.getCookies();
		if ((cookieList != null) && (cookieList.length >= 0)) {
			for (Cookie c : cookieList) {
				if (c.getName().equals("token")) {
					Query qry = pm.newQuery(UserLoginToken.class);
					qry.setFilter("token == tokenParam");
					qry.declareParameters("String tokenParam");
					List<UserLoginToken> ultList = (List<UserLoginToken>) qry.execute(c.getValue());
					for (UserLoginToken u : ultList) {
						if (u.getToken().equals(c.getValue()))
							ult = u;
					}
					int d = ult.getExpireDate().compareTo(day);
					if (d >= 0) {
						c.setMaxAge(d * 24 * 60 * 60);
						String uuid = UUID.randomUUID().toString();
						ult.setToken(uuid);
						c.setValue(uuid);
						resp.addCookie(c);
						session.setAttribute("login_id", ult.getUserAccount());
						pm.close();
						resp.sendRedirect("index.html");
					}
				} else {
					resp.sendRedirect("login.html");
				}
			}
		} else {
			resp.sendRedirect("login.html");
		}

	}
}
