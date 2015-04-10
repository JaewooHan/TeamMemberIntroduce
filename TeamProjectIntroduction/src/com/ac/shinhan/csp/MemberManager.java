package com.ac.shinhan.csp;

import java.lang.reflect.Member;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class MemberManager {
	public static TeamMember addMember(String name, String num, String pnum, String email,
			String kid, String kap, String gitid) {
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m = new TeamMember(name, num, pnum, email, kid, kap, gitid);
		pm.makePersistent(m);

		return m;
	}
	
	public static TeamMember getMember(Long key) {
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m = pm.getObjectById(TeamMember.class, key);
		
		return m;
	}

	public static void updateMember(TeamMember newMember){
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember memberObject = MemberManager.getMember(newMember.getKey());
		memberObject.setName(newMember.getName());
		memberObject.setNum(newMember.getNum());
		memberObject.setPnum(newMember.getPnum());
		memberObject.setEmail(newMember.getEmail());
		memberObject.setKid(newMember.getKid());
		memberObject.setKap(newMember.getKap());
		memberObject.setGitid(newMember.getGitid());
		
		pm.close();
	}
	
	public static void deleteMember(Long key) {
		PersistenceManager pm = MyPersistenceManager.getManager();
		TeamMember m = MemberManager.getMember(key);
		pm.deletePersistent(m);
	}

	public static List<TeamMember> getMemberByName(String name) {
		PersistenceManager pm = MyPersistenceManager.getManager();
		Query qry = pm.newQuery(TeamMember.class);
		qry.setFilter("name == nameParam");
		qry.declareParameters("String nameParam");

		List<TeamMember> memberList = (List<TeamMember>) qry.execute(name);

		return memberList;
	}

	public static List<TeamMember> getAllMembers() {
		PersistenceManager pm = MyPersistenceManager.getManager();
		Query qry = pm.newQuery(TeamMember.class);
		List<TeamMember> memberList = (List<TeamMember>) qry.execute();

		return memberList;
	}

}
