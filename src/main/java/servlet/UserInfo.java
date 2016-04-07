package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import jpa.EntityManagerHelper;

@SuppressWarnings("serial")
@WebServlet (name= "userinfo" ,
urlPatterns={ "/UserInfo" })
public class UserInfo extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		EntityManagerHelper.getEntityManager();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
//		super.doPost( request , response );
		response.setContentType("text/html" );
		PrintWriter out = response.getWriter();
		out.println("<HTML>\n<BODY>\n" +
				"<H1>Recapitulatif des informations</H1>\n" +
				"<UL>\n" +
				" <LI>Nom: "
				+ request . getParameter ( "name" ) + "\n" +
				" <LI>Prenom: "
				+ request . getParameter ( "firstname" ) + "\n" +
				" <LI>Mail: "
				+ request . getParameter ( "mail" ) + "\n" +
				"</UL>\n" +
				"</BODY></HTML>" );
		
		EntityManagerHelper.beginTransaction();
		
		String name = request.getParameter("name");
		String firstName = request.getParameter("firstname");
		String mail = request.getParameter("mail");
		Person p = new Person(name, firstName, mail);
		EntityManagerHelper.getEntityManager().persist(p);
		
		
		EntityManagerHelper.commit();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		EntityManagerHelper.closeEntityManager();
		EntityManagerHelper.closeEntityManagerFactory();
		
	}
}