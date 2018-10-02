package application;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StudentController implements Initializable {
	
@FXML
Label commentslabel;
@FXML
Label marklabel;
@FXML
Label LstSt;
@FXML
Label photolabel;
@FXML
Label genderlabel;
@FXML
Label datelabel;
@FXML
Label labelname;
@FXML
ListView<String> Lst;
@FXML
TextField txtname;
@FXML
TextField marktext;
@FXML
ComboBox<String> gendercombo;
@FXML
DatePicker dateDate;
@FXML
TextArea commentsArea;
@FXML
ImageView photoPhoto;
@FXML
Label labelstudents;
@FXML
Button modif;
@FXML
Label debug;
@FXML
Button plusbutton;
@FXML
Button minusbutton;
@FXML
Button reloadbutton;
@FXML
Button ajoutButton;

DBManager manager;

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	List<String> gvalues = new ArrayList<String>();
	gvalues.add("Male");
	gvalues.add("Female");
	ObservableList<String> gender = FXCollections.observableArrayList(gvalues);
	gendercombo.setItems(gender);
	
	manager = new DBManager();
	fetchStudents();
	Lst.getSelectionModel().selectedItemProperty().addListener(e-> displayStudentDetails(Lst.getSelectionModel().getSelectedItem()));
	modif.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	updateCurrentStudent(Lst.getSelectionModel().getSelectedItem());
	    }
	});
	plusbutton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	clearInfos();
	    }
	});
	minusbutton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	deleteCurrentStudent(Lst.getSelectionModel().getSelectedItem());
	    }
	});
	reloadbutton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	fetchStudents();
	    }
	});
	ajoutButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override public void handle(ActionEvent e) {
	    	ajouterEtudiant();
	    }
	});
}

private void displayStudentDetails(String name)
{
	try {
		Student s = manager.fetchStudentByName(name);
		txtname.setText(s.getName());
		gendercombo.setValue(s.getGender());
		dateDate.setValue(s.getBirthDate());
		
		Image image;
		if(s.getPhotoURL()!=null)
		{
			File file = new File(s.getPhotoURL());
			image = new Image(file.toURI().toString());
			photoPhoto.setImage(image);
		}
		else {
			if(gendercombo.getValue()=="Male")
			{
				image = new Image("https://static.thenounproject.com/png/1243526-200.png");
				photoPhoto.setImage(image);
			}
			else {
				image = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqf8E2lMsr6gEgmsCyqb_iya9ytaePHSA7LlxmSsR91W4P31ww");
				photoPhoto.setImage(image);
			}
		}
		marktext.setText(String.valueOf(s.getMark()));
		commentsArea.setText(s.getComments());
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
}



public void fetchStudents() {
	ObservableList<String> students;
	if(manager.loadStudents()!=null)
	{
		students = FXCollections.observableArrayList(manager.loadStudents());
		Lst.setItems(students);
	}
}

public void updateCurrentStudent(String name)
{
	Student s = manager.fetchStudentByName(name);
	s.Comments = commentsArea.getText();
	s.Gender = gendercombo.getValue();
	s.Mark = Float.valueOf(marktext.getText().trim()).floatValue();
	s.Name = txtname.getText();
	if(dateDate.getValue()!=null)
	{
		s.BirthDate = dateDate.getValue();
	} 
	else 
	{
		LocalDate L = LocalDate.of(0,1,1);
		s.BirthDate = L;
	}
	manager.updateStudent(s);
	debug.setText("Informations modifiées.");
	fetchStudents();
}

public void clearInfos()
{
	txtname.setText(null);
	gendercombo.setValue(null);
	dateDate.setValue(null);
	marktext.setText(null);
	commentsArea.setText(null);
	photoPhoto.setImage(null);
}

public void deleteCurrentStudent(String name)
{
	Student s = manager.fetchStudentByName(name);
	manager.deleteStudent(s);
	debug.setText("Etudiant supprimé.");
	fetchStudents();
	
}

public void ajouterEtudiant()
{
	if(txtname!=null && gendercombo!=null)
	{
		Student s = new Student(txtname.getText(), gendercombo.getValue());
		if(commentsArea.getText()!=null)
		{
			s.setComments(commentsArea.getText());
		} else s.setComments("");
		if(marktext.getText() != null && marktext.getText()!="")
		{
			s.setMark(Float.valueOf(marktext.getText().trim()).floatValue());
			
		} else s.setMark(0);
		if(dateDate.getValue()!=null)
		{
			s.setBirthDate(dateDate.getValue());
		} else s.setBirthDate(LocalDate.of(0,1,1));
		Boolean success = manager.addStudent(s);
		if(success) debug.setText("Etudiant ajouté.");
		else debug.setText("Echec.");
		fetchStudents();
	}
	
	else {
		debug.setText("Nom ou genre manquant.");
	}
}
}
