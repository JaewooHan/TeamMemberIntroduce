package Login;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Manager.MyPersistenceManager;
import User.UserAccount;
import User.UserLoginToken;

public class logoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserLoginToken ult = null;
		HttpSession session = req.getSession();
		session.invalidate();
		PersistenceManager pm = MyPersistenceManager.getManager();
		Cookie[] cookieList = req.getCookies();
		if((cookieList!=null)&&(cookieList.length>=0)){
			for (Cookie c : cookieList) {
				if (c.getName().equals("token")) {
					Query qry = pm.newQuery(UserLoginToken.class);
					qry.setFilter("token == tokenParam");
					qry.declareParameters("String tokenParam");
					List<UserLoginToken> ultList = (List<UserLoginToken>) qry.execute(c.getValue());
					for(UserLoginToken u : ultList){
						if(u.getToken().equals(c.getValue()))
							ult = u;
					}
					for(UserLoginToken u : ultList){
						if(u.getToken().equals(c.getValue()))
							ult = u;
					}
					c.setValue(null);
					c.setMaxAge(0);
					resp.addCookie(c);
					pm.deletePersistent(ult);
				}
			}
		}
		resp.sendRedirect("login.html");
	}

}
