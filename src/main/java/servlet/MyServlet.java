package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.EntityManagerHelper;

@SuppressWarnings("serial")
@WebServlet (name= "mytest", urlPatterns={ "/myurl" })
public class MyServlet extends HttpServlet {
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		EntityManagerHelper.getEntityManager();
	}
	
	@Override
	protected void doGet(HttpServletRequest req , HttpServletResponse resp ) throws ServletException, IOException {
			PrintWriter p = new PrintWriter(resp.getOutputStream());
			p.print("Hello world SIR");
			p.flush();
	}
	
	@Override
	protected void doPost(HttpServletRequest req , HttpServletResponse resp ) throws ServletException, IOException {
		super.doPost( req , resp );
		EntityManagerHelper.beginTransaction();
		
		
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

