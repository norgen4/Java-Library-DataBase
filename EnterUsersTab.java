package library;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;

/***
 * This class models the EnterUsersTab and sets up all of its controls.
 * It extends VBox so that it can be added as a tab to the main GUI.
 * 
 * @author ryank
 *
 */
public class EnterUsersTab extends VBox {

	//Controls
	private Label lblName;
	private Label lblEmail;
	private Label lblID;
	private Label lblPhone;
	private Label lblDeptOrMajor;  //This label will be used for departments when a professor is being added.
	                               //Otherwise, it is used for the major of a student.
	private Label lblYear;
	private Label lblUserType;

	private TextField txtName;
	private TextField txtPhone;
	private TextField txtEmail;
	private TextField txtID;

	private ComboBox<String> comboUserType;
	private ComboBox<String> comboYear;
	private ComboBox<String> comboDeptOrMajor; //This combo box lists possible departments when a professor is being added.
											    //Otherwise, it lists possible majors for a student.
	private Button btnSubmit;
	
	//possible Majors allowed in system
	public static final String[] MAJORS = {"Accounting",
			"American Studies",
			"Behavioral Neuroscience",
			"Biochemistry-Molecular Biology",
			"Biology",
			"Biomechanics",
			"Business",
			"Business Administration",
			"Chemistry",
			"Communication",
			"Computer Science",
			"Criminal Justice",
			"Dance",
			"Design/Tech Theatre",
			"Digital Art",
			"Economics",
			"Education - Early Childhood  ",
			"Education - Secondary ",
			"Education - Special Education ",
			"English",
			"Exploratory Studies",
			"Finance",
			"Health Communication",
			"Healthcare Administration",
			"History",
			"Homeland Security",
			"Human Resource Management",
			"International Business",
			"Law and Society",
			"Liberal Studies",
			"Management",
			"Marketing",
			"Marriage and Family Studies",
			"Mathematics",
			"Media Studies",
			"Medical Studies ",
			"Neuroscience",
			"Nursing",
			"Pharmaceutical Marketing",
			"Philosophy",
			"Political Science",
			"Psychology",
			"Spanish",
			"Sport and Exercise Physiology",
			"Sport Management",
			"Supply Chain Management",
			"Theatre",
			"Theology"};

	//Possible Departments allowed in system
	public static final String[] DEPARTMENTS = {"Accounting",
			"Biology",
			"Business Administration",
			"Chemistry and Physics",
			"Criminal Justice",
			"Dance",
			"Economics",
			"Education",
			"Finance",
			"Graduate Nursing",
			"Healthcare Administration",
			"Humanities",
			"International Business",
			"Mathematics and Computer Science",
			"Pyshcology",
			"Social Sciences",
			"Sports Management",
			"Supply Chain Mangement",
			"Theater",
			"TV/Film",
			"Undergraduate Nursing"};
	
	
	/**
	 * The Constructor:  Builds The VBox shown on the Enter User Tabs.
	 * Adds an event handler so that the user can add a new user when 
	 * the submit button is selected.
	 */
	public EnterUsersTab()
	{
		super(50); //set vertical/hzntal gap of VBox to 50
		
		//Set up the controls
		lblUserType = new Label("Type of User:");
		comboUserType = new ComboBox<String>();
		comboUserType.getItems().addAll("Student", "Professor");
		comboUserType.setValue("Professor");  //Pre select professor
		comboUserType.setPrefWidth(250);
		
		lblName = new Label("Name:");
		txtName = new TextField();
		
		lblEmail = new Label("Email:");
		txtEmail = new TextField();
		
		lblID = new Label("ID:");
		txtID = new TextField();

		lblPhone = new Label("Phone:");
		txtPhone = new TextField();
		
		lblDeptOrMajor = new Label("Department:");
		lblDeptOrMajor.setPrefWidth(100);
		comboDeptOrMajor = new ComboBox<String>();
		
		//Add Departments to the dropdown combo box first.
		//If users opts to add a student, an event handler
		//changes this to list all the majors.
		comboDeptOrMajor.getItems().addAll(DEPARTMENTS);
		comboDeptOrMajor.setValue(DEPARTMENTS[0]);  //Pre-select whatever is first in the list
		comboDeptOrMajor.setPrefWidth(250);
		
		
		lblYear = new Label("Year:");
		lblYear.setVisible(false);   //Year is invisible to start because professor option is pre-selected.
        							  //Professors don't have class years.
		comboYear = new ComboBox<String>();
		comboYear.getItems().addAll("2021", "2022", "2023", "2024", "2025");
		comboYear.setValue("2021");    //Pre-select 2021
		comboYear.setVisible(false);  //Year is invisible to start because professor option is pre-selected.
		                              //Professors don't have class years.
		comboYear.setPrefWidth(250);
	
		
		btnSubmit = new Button("Submit New User");
		
		/**
		 * Submitting a new user makes it possible to add users
		 * but not add a user that already exist in the system
		 * if they already exist alerts are thrown
		 */
		btnSubmit.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent arg0) {
						//pull info from text fields
						String name = txtName.getText();
						String email = txtEmail.getText();
						String id = txtID.getText();
						String phone = txtPhone.getText();
						String deptOrMajor = comboDeptOrMajor.getValue();
						String year = comboYear.getValue();
						
						//If the txt fields are not empty and do not already exist a new user is added
						if(!name.equals("") && !email.equals("") && !id.equals("") && !name.equals("") && !phone.equals(""))
						{
							if(LibraryDatabase.findUserWithEmail(email) != null)
							{
								Alert a = new Alert(AlertType.INFORMATION);
								a.setContentText("A user with this email already exists.");
								a.showAndWait();
							}
							//If a user already exist 
							else if(LibraryDatabase.findUserWithID(id) != null)
							{
								Alert a = new Alert(AlertType.INFORMATION);
								a.setContentText("A user with this ID already exists.");
								a.showAndWait();
							}
							else
							{
								if(comboUserType.getSelectionModel().getSelectedItem() == "Student")
								{
									User fill = new Student(name,id, email,phone,Integer.parseInt(year), deptOrMajor);
									LibraryDatabase.addUser(fill);//add the user if fields are not null
								}
								else
								{
									User fill = new Professor(name,id, email,phone, deptOrMajor);
									LibraryDatabase.addUser(fill);//add the user if fields are not null
								}
									
									
								
							}
							
						}
						//input validation error messages
						else if(name.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid name.");
							a.showAndWait();
						}
						else if(email.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid email.");
							a.showAndWait();
						}
						else if(id.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid ID.");
							a.showAndWait();
						}
						else if(phone.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid phone number.");
							a.showAndWait();
						}
					}
					
				});
		
		//Create a grid pane to hold all fields for a new user.
		GridPane userInfo = new GridPane();
		
		userInfo.setHgap(10);
		userInfo.setVgap(10);
		userInfo.setAlignment(Pos.CENTER);
				
		userInfo.add(lblUserType, 0, 0);
		userInfo.add(comboUserType, 1, 0);
		userInfo.add(lblName, 0, 1);
		userInfo.add(txtName, 1, 1);
		
		userInfo.add(lblEmail, 0, 2);
		userInfo.add(txtEmail, 1, 2);
		
		userInfo.add(lblID, 0, 3);
		userInfo.add(txtID, 1, 3);
		
		userInfo.add(lblPhone, 0, 4);
		userInfo.add(txtPhone, 1, 4);
		
		userInfo.add(lblDeptOrMajor, 0, 5);
		userInfo.add(comboDeptOrMajor, 1,5);

		userInfo.add(lblYear, 0, 6);
		userInfo.add(comboYear, 1, 6);

		
		
		//Remember this class is inheriting from VBox.
		//Add the userInfo gridPane and the submit button to the vbox.
		super.getChildren().addAll(userInfo, btnSubmit);
		super.setAlignment(Pos.CENTER);
		super.setPadding(new Insets(50, 20, 50, 20));
		
		//*******************************
		// EVENT HANDLERS BELOW HERE
		//******************************
		
		
		
		//Event handler for the professor/student drop down.
		//If a user changes what is selected, then a label changes
		//to/from Department/Major and the year label/text field are made visible/invisible.
		
		
		
		comboUserType.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				//If a student, update lblDeptOrMajor field to say "Major" and show the
				//the  year label/dropdown.  Also, populate the comoDeptOrMajor dropdown
				//with all possible majors.
				if (comboUserType.getSelectionModel().getSelectedItem() == "Student")
				{
					lblDeptOrMajor.setText("Major:     ");
					
					comboDeptOrMajor.getItems().clear();  //clear what was in the dropdown 
					comboDeptOrMajor.getItems().addAll(MAJORS);  //add majors
					comboDeptOrMajor.setValue(MAJORS[0]); //pre-select the first in the list
					comboYear.setValue("2021");  //Pre-select 2021
					
					
					
					 //make the year dropdown/label visible
					comboYear.setVisible(true); 
					lblYear.setVisible(true);
					
				

				}
				else //If the professor option is selected, update lblDeptOrMajor field to say "Department" 
					 //and make the  year label/dropdown invisible.  Also, populate the comoDeptOrMajor dropdown
					//with all possible departments.
				{
					lblDeptOrMajor.setText("Department:");			
					comboDeptOrMajor.getItems().clear();  //clear what was in the dropdown 
					comboDeptOrMajor.getItems().addAll(DEPARTMENTS);  //add departments
					comboDeptOrMajor.setValue(DEPARTMENTS[0]);  //Pre-select whatever is first in the list
					
					 //make the year dropdown/label invisible
					lblYear.setVisible(false);  
					comboYear.setVisible(false);
				}
				
			}
			
		});
		
		
	}
}
