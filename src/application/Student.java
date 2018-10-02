package application;

import java.time.LocalDate;

public class Student {
	int ID;
	String Name;
	String Gender;
	LocalDate BirthDate;
	String PhotoURL;
	float Mark;
	String Comments;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public LocalDate getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		BirthDate = birthDate;
	}
	public String getPhotoURL() {
		return PhotoURL;
	}
	public void setPhotoURL(String photoURL) {
		PhotoURL = photoURL;
	}
	public float getMark() {
		return Mark;
	}
	public void setMark(float mark) {
		Mark = mark;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	
	
	public Student(int iD, String name, String gender, LocalDate birthDate, String photoURL, float mark,
			String comments) {
		super();
		ID = iD;
		Name = name;
		Gender = gender;
		BirthDate = birthDate;
		PhotoURL = photoURL;
		Mark = mark;
		Comments = comments;
	}
	
	public Student(String name, String gender) {
		super();
		Name = name;
		Gender = gender;
	}
	
	
	
	
}
