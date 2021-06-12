package library;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;


/***
 * This class models the ViewResourcesTab and sets up all of its controls.
 * It extends Split Pane so that the list of resources can show on the left.
 * 
 * @author ryank
 *
 */
public class ViewResourcesTab extends SplitPane {

	//Set up controls
	private Label lblName;
	private Label lblEmail;
	private Label lblID;
	private Label lblPhone;
	private Label lblDeptOrMajor; //Profs have departments.  Students have majors.  We'll use one label for this.
	private Label lblYear;
	private Label lblUserType;
	private Label lblCheckedOutResources;
	private Label lblEnterEmailOrID;  //Label to tell the user to enter an email or id.

	private TextField txtName;
	private TextField txtEmail;
	private TextField txtID;
	private TextField txtPhone;
	private TextField txtYear;
	private TextField txtUserType;
	private TextField txtDeptOrMajor; //Profs have departments.  Students have majors.  We'll use one textfield for this.

	private TextField txtEnterEmailOrID; //text field in which the user enters an enter an email or id to check
	//a resources out to.

	private ComboBox<String> comboEmailOrID; //dropdown box to tell GUI whether an email or id is being used to
	//identify a user.

	private Button btnFindUser;
	private Button btnCheckOut;

	private ListView<String> lvAllLibResources;   //The list view of all resources in the lib
	private ListView<String> lvCheckedOutResources; //The list view of the checked out resources for a user.

	public ViewResourcesTab()
	{
		//Initialize controls for left pane
		lvAllLibResources = new ListView<String>();
		lvAllLibResources.setPrefHeight(600);

		for (int i = 0; i < LibraryDatabase.getResources().size(); i++)
		{
			//One by one, I am adding the name of an resource
			//to the list view.
			lvAllLibResources.getItems().add(LibraryDatabase.getResources().get(i).getTitle());
		}

		//Initialize controls for right pane
		//Disable all fields because a user cannot write into them on this tab.
		lblEnterEmailOrID = new Label("Checkout to:         ");
		comboEmailOrID = new ComboBox<String>();
		comboEmailOrID.getItems().addAll("Email", "ID");
		comboEmailOrID.setValue("Email");
		comboEmailOrID.setPrefWidth(75);
		txtEnterEmailOrID = new TextField();
		txtEnterEmailOrID.setPrefWidth(165);

		btnFindUser = new Button("Find User In System");
		/**
		 * Add a listener event.  This event listens and responds each time a user is selected from the left list.
		 * Whenever a user from the left list is  selected, this listener shows the selected uesr's
		 *  information on the right pane.
		 */
		lvAllLibResources.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

				//Find which user has been selected by the user
				//and get that user from the DB.  
				//NOTE:  The list on the left is in the same order as the user list in the 
				//       DB class so the if the 5th item on the list is selected, this is the
				//       5th item in the DB class's users list.

				/**
				 * This find user button finds a user if they exist using
				 * email or id number. If found the users info will be 
				 * displayed. 
				 */
				btnFindUser.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent arg0) {
						User user;
						//If the user selects email
						if(comboEmailOrID.getSelectionModel().getSelectedItem() == "Email")
						{
							user = LibraryDatabase.findUserWithEmail(txtEnterEmailOrID.getText());
							//If the user does not exist
							if(user == null)
							{
								Alert a = new Alert(AlertType.INFORMATION);
								a.setContentText("User not found");
								a.showAndWait();
							}
							//If user is a professor
							if (user instanceof Professor)
							{
								lblYear.setVisible(false); //Set year to invisible
								txtYear.setVisible(false); //Set year to invisible
								txtUserType.setText("Professor"); //Set user to professor
								txtName.setText(user.getName()); //Set user name
								txtEmail.setText(user.getEmail()); //Set user email
								txtID.setText(user.getId()); //Set user ID
								txtPhone.setText(user.getPhoneNumber()); //Set user phone number
								txtDeptOrMajor.setText(((Professor)user).getDepartment()); //Set professor department
							}
							//if user is a student
							else
							{
								lblYear.setVisible(true);	//Set year to visible
								txtYear.setVisible(true);	//Set year to visible
								txtUserType.setText("Student"); //Set user to Student
								txtName.setText(user.getName()); //Set user name
								txtEmail.setText(user.getEmail()); //Set user email
								txtID.setText(user.getId());	   //Set user ID
								txtPhone.setText(user.getPhoneNumber());	//Set user number
								txtDeptOrMajor.setText(((Student)user).getMajor()); //Set user Major
								txtYear.setText(String.format("%d",((Student)user).getClassYear())); //Set user class year
							}

							populateCheckedOutResources(user);
						}
						//If the user selects id
						else if(comboEmailOrID.getSelectionModel().getSelectedItem() == "ID")
						{
							user = LibraryDatabase.findUserWithID(txtEnterEmailOrID.getText());

							//If user does not exist print error message
							if(user == null)
							{
								Alert a = new Alert(AlertType.INFORMATION);
								a.setContentText("User not found");
								a.showAndWait();
							}
							//If the user is a professor
							if (user instanceof Professor)
							{
								lblYear.setVisible(false); //Year invisible
								txtYear.setVisible(false);
								txtUserType.setText("Professor"); //Fill and set text fields
								txtName.setText(user.getName());
								txtEmail.setText(user.getEmail());
								txtID.setText(user.getId());
								txtPhone.setText(user.getPhoneNumber());
								txtDeptOrMajor.setText(((Professor)user).getDepartment());
							}
							//if user is a student
							else
							{
								lblYear.setVisible(true); //year Visible
								txtYear.setVisible(true);
								txtUserType.setText("Student"); //Fill and set text fields
								txtName.setText(user.getName());
								txtEmail.setText(user.getEmail());
								txtID.setText(user.getId());
								txtPhone.setText(user.getPhoneNumber());
								txtDeptOrMajor.setText(((Student)user).getMajor());

								txtYear.setText(String.format("%d",((Student)user).getClassYear()));
							}

							populateCheckedOutResources(user);
						}

					}

				});

			}

		});
		btnFindUser.setPrefWidth(360);

		lblUserType = new Label("Type of User:");
		txtUserType = new TextField();
		txtUserType.setPrefWidth(250);
		txtUserType.setEditable(false);
		txtUserType.setDisable(true);

		lblName = new Label("Name:");
		txtName = new TextField();
		txtName.setEditable(false);
		txtName.setDisable(true);


		lblEmail = new Label("Email:");
		txtEmail = new TextField();
		txtEmail.setEditable(false);
		txtEmail.setDisable(true);

		lblID = new Label("ID:");
		txtID = new TextField();
		txtID.setEditable(false);
		txtID.setDisable(true);

		lblPhone = new Label("Phone:");
		txtPhone = new TextField();
		txtPhone.setEditable(false);
		txtPhone.setDisable(true);

		lblDeptOrMajor = new Label("Department:");
		lblDeptOrMajor.setPrefWidth(100);
		txtDeptOrMajor = new TextField();
		txtDeptOrMajor.setPrefWidth(250);
		txtDeptOrMajor.setEditable(false);
		txtDeptOrMajor.setDisable(true);


		lblYear = new Label("Year:");
		txtYear = new TextField();
		txtYear.setEditable(false);
		txtYear.setDisable(true);
		lblYear.setVisible(false);
		txtYear.setVisible(false);	

		//SEt up the list view for a user's checked out rescourses.
		lblCheckedOutResources = new Label("Resources Checked Out Already: ");
		lvCheckedOutResources = new ListView<String>();
		lvCheckedOutResources.setPrefHeight(300);
		lvCheckedOutResources.setPrefWidth(360);
		populateCheckedOutResources(null);

		btnCheckOut = new Button("Check Out New Resource to User");

		/**
		 * Allows the user to check out if the book 
		 * is available
		 */
		btnCheckOut.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent arg0) {


				User user = LibraryDatabase.findUserWithEmail(txtEmail.getText());//get user from email
				//get resource from left side list view
				Resource resource = LibraryDatabase.getResources().get(lvAllLibResources.getSelectionModel().getSelectedIndex());

				//Checks out the book for the user
				if(lvAllLibResources.getSelectionModel().getSelectedIndex() != -1) 
				{

					user.checkOut(resource);
					resource.checkOut(user);
					populateCheckedOutResources(user);


				}



			}

		});

		btnCheckOut.setPrefWidth(360);

		GridPane userInfo = new GridPane();

		userInfo.setHgap(10);
		userInfo.setVgap(10);
		userInfo.setAlignment(Pos.CENTER);

		userInfo.add(lblUserType, 0, 0);
		userInfo.add(txtUserType, 1, 0);
		userInfo.add(lblName, 0, 1);
		userInfo.add(txtName, 1, 1);

		userInfo.add(lblEmail, 0, 2);
		userInfo.add(txtEmail, 1, 2);

		userInfo.add(lblID, 0, 3);
		userInfo.add(txtID, 1, 3);

		userInfo.add(lblPhone, 0, 4);
		userInfo.add(txtPhone, 1, 4);

		userInfo.add(lblDeptOrMajor, 0, 5);
		userInfo.add(txtDeptOrMajor, 1,5);

		userInfo.add(lblYear, 0, 6);
		userInfo.add(txtYear, 1, 6);


		HBox hboxUserInput = new HBox(10, lblEnterEmailOrID, txtEnterEmailOrID, comboEmailOrID);	
		hboxUserInput.setAlignment(Pos.CENTER);

		//Add all controls to the vboxUserInfo -> So this is the root container for the right pane.
		VBox vboxUserInfo = new VBox(10, hboxUserInput, btnFindUser, userInfo, lblCheckedOutResources, lvCheckedOutResources, btnCheckOut ); 
		vboxUserInfo.setPadding(new Insets(20));
		vboxUserInfo.setAlignment(Pos.CENTER);

		//Remember - This class inherits from the SplitPane class.
		//Call the method to put the divider 30% of the way through the pane.
		super.setDividerPositions(.3);
		//Add the list view to the left pane and the vboxUserInfo to the right.
		super.getItems().addAll(lvAllLibResources, vboxUserInfo);
		super.setPadding(new Insets(20, 10, 10, 10));


		//*******************************
		// EVENT HANDLERS BELOW HERE
		//******************************

	}

	/**
	 * 
	 * @param user
	 */
	private void populateCheckedOutResources(User user)
	{
		lvCheckedOutResources.getItems().clear();
		if (user == null || user.getCheckedOutResources().size() == 0)
		{
			lvCheckedOutResources.getItems().add("None");
		}
		else
		{

			Resource r;

			for (int i = 0; i < user.getCheckedOutResources().size(); i++)
			{
				r = user.getCheckedOutResources().get(i);
				String displayString = "";
				if(r.isLate())
				{
					displayString += "LATE - WAS ";
				}
				displayString += "DUE: " + r.getDueDate() + " - " + r.getTitle();

				//One by one, I am adding the name of an employee
				//to the list view.
				lvCheckedOutResources.getItems().add(displayString);




			}

		}

	}




}
