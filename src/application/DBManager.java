package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
	
	public List<String> loadStudents()
	{
		List<String> studentNames = new ArrayList<String>();
		Connection myConn = this.Connector();
		try {
			Statement myStmt = (Statement) myConn.createStatement();
			String sql = "select name from studenttable";
			ResultSet myRs = myStmt.executeQuery(sql);
			while(myRs.next())
			{
				String name = myRs.getString("name");
				studentNames.add(name);
			}
			this.close(myConn, myStmt, myRs);
			return studentNames;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Connection Connector()
	{
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "Alexandre","Alexandre1997");
			return connection;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs)
	{
		try {
			if(myStmt!=null) myStmt.close();
			if(myConn!=null) myConn.close();
			if(myRs!=null) myRs.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Student fetchStudentByName(String name)
	{
		Student s = null;
		Connection myConn = Connector();
		try {
			Statement myStmt = myConn.createStatement();
			String sql = "select * from studenttable where name=\"" + name + "\"";
			ResultSet myRs = myStmt.executeQuery(sql);
			while(myRs.next())
			{
				int id=myRs.getInt("id"); 
				String gender= myRs.getString("gender"); 
				LocalDate birth = null; 
				if (myRs.getDate("dateofbirth")!=null) birth = myRs.getDate("dateofbirth").toLocalDate();
				String photo = myRs.getString("photo"); 
				float mark = myRs.getFloat("mark"); 
				String comments= myRs.getString("comments");
				s = new Student(id,name,gender,birth,photo,mark,comments);
			}
			this.close(myConn, myStmt, myRs);
			return s;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public Student updateStudent(Student s)
	{
		Connection myConn = Connector();
		try {
			
			Statement myStmt = myConn.createStatement();
			String sql = "UPDATE studenttable SET name=\""+ s.Name + "\", gender=\""+ s.Gender +"\", mark=\""+ s.Mark +"\", comments=\""+ s.Comments +"\", dateofbirth=\"" + s.BirthDate + "\" where id=\""+s.ID+"\"";
			myStmt.execute(sql);
			//this.close(myConn, myStmt);
			return s;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public Student deleteStudent(Student s)
	{
		Connection myConn = Connector();
		try {
			
			Statement myStmt = myConn.createStatement();
			String sql = "DELETE from studenttable where id=\"" + s.ID + "\"";
			myStmt.execute(sql);
			//this.close(myConn, myStmt);
			return s;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean addStudent(Student s)
	{
		Connection myConn = Connector();
		try {
			String sql = "INSERT INTO studenttable (name, gender, dateofbirth, mark, comments) VALUES (?, ?, ?, ?, ?)"; 
			PreparedStatement statement = myConn.prepareStatement(sql); 
			statement.setString(1, s.getName());
			statement.setString(2, s.getGender());
			statement.setDate(3, Date.valueOf(s.getBirthDate()));
			statement.setFloat(4, s.getMark());
			statement.setString(5, s.getComments());
			statement.execute();
			//this.close(myConn, myStmt);
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
