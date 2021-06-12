package library;
import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;

/***
 * This class models the EnterResourcesTab and sets up all of its controls.
 * It extends Split Pane so that the list of users can show on the left.
 * 
 * @author Angel Negron,Dom, Dan Saviet
 *
 */
public class ViewUsersTab extends SplitPane {

	//Set up the controls
	private Label lblName;
	private Label lblEmail;
	private Label lblID;
	private Label lblPhone;
	private Label lblDeptOrMajor;
	private Label lblYear;
	private Label lblUserType;
	private Label lblCheckedOutResources;

	private TextField txtName;
	private TextField txtEmail;
	private TextField txtID;
	private TextField txtPhone;
	private TextField txtYear;
	private TextField txtUserType;
	private TextField txtDeptOrMajor;

	private Button btnCheckIn;
	private Button btnRenew;

	private ListView<String> lvUsers;  //The list view of users
	private ListView<String> lvCheckedOutResources;   //The list view of the checked out resources for a user.

	public ViewUsersTab()
	{

		//Initialize list view on left pane
		lvUsers = new ListView<String>();

		//method to add the users to the list view  - This method can be called whenever
		//users in this tab should be reloaded.  
		//  HINT:  It is called in LibraryGUI to so that every time someone 
		//  clicks on this tab, the uses list is refreshed, just in case a user is ever added.
		reloadUsers();  



		//Initialize controls for right pane
		//Disable all fields because a user cannot write into them on this tab.
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

		btnCheckIn = new Button("Check in");
		btnRenew = new Button("Renew");
		btnCheckIn.setDisable(true);
		btnRenew.setDisable(true);

		//SEt up the list view for a user's checked out rescourses.
		lblCheckedOutResources = new Label("Checked Out Resources: ");
		lvCheckedOutResources = new ListView<String>();
		lvCheckedOutResources.setPrefHeight(200);

		//This method is for populating hte checked out resources.
		//When null is passed, it disables the check-in/renew button.
		populateCheckedOutResources(null);


		//Set up grid pane for the right
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


		HBox hboxButtons = new HBox(10, btnCheckIn, btnRenew);
		hboxButtons.setAlignment(Pos.CENTER);

		//Add all controls to the vboxUserInfo -> So this is the root container for the right pane.
		VBox vboxUserInfo = new VBox(10, userInfo, lblCheckedOutResources, lvCheckedOutResources, hboxButtons); 
		vboxUserInfo.setPadding(new Insets(20));

		//Remember - This class inherits from the SplitPane class.
		//Call the method to put the divider 30% of the way through the pane.
		super.setDividerPositions(.3);
		//Add the list view to the left pane and the vboxUserInfo to the right.
		super.getItems().addAll(lvUsers, vboxUserInfo);
		super.setPadding(new Insets(50, 20, 50, 20)); //set some padding



		//*******************************
		// EVENT HANDLERS BELOW HERE
		//******************************

		/**
		 * The Check in button allows the user check to check in resources that the user previously had
		 */
		btnCheckIn.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent arg0) {

				int desiredReturn = lvCheckedOutResources.getSelectionModel().getSelectedIndex();//find the index of the resource to return
				User currentUser = LibraryDatabase.findUserWithEmail(txtEmail.getText());//find the user who has the resource

				Resource r = currentUser.getCheckedOutResources().get(desiredReturn);//find the resource to return

				currentUser.checkIn(r);//return the resource
				r.checkIn();

				populateCheckedOutResources(currentUser);//refresh the list
			}



		});

		/**
		 * The renew button allows the user to renew a book if 
		 * the book is not already late. 
		 */
		btnRenew.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent arg0) {

				int desiredRenew = lvCheckedOutResources.getSelectionModel().getSelectedIndex();//find the index of the resource to renew
				User currentUser = LibraryDatabase.findUserWithEmail(txtEmail.getText()); //find the user who has the resource

				Resource r = currentUser.getCheckedOutResources().get(desiredRenew); //get the resource to renew


				if(currentUser.renew(r))//renew the resource
				{
					Alert a = new Alert(AlertType.INFORMATION);
					a.setContentText("Resource renewed successfully.");
					a.showAndWait();
				}

				r.renew();
				populateCheckedOutResources(currentUser);//refresh the list
			}



		});



		/**
		 * Add a listener event.  This event listens and responds each time a user is selected from the left list.
		 * Whenever a user from the left list is  selected, this listener shows the selected uesr's
		 *  information on the right pane.
		 */
		lvUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

				//Find which user has been selected by the user
				//and get that user from the DB.  
				//NOTE:  The list on the left is in the same order as the user list in the 
				//       DB class so the if the 5th item on the list is selected, this is the
				//       5th item in the DB class's users list.
				int selectedUserIndex = lvUsers.getSelectionModel().getSelectedIndex();

				if(selectedUserIndex != -1)
				{
					User user = LibraryDatabase.getUsers().get(selectedUserIndex);

					//Set the controls to the selected uer's information.
					//If user is of type professor, show correct fields.
					//If user is of type student, show some fields.	
					if (user instanceof Student)
					{
						lblYear.setVisible(true);			//Set year visible
						txtYear.setVisible(true);			//Set year visible
						txtUserType.setText("Student");		//Set user type to student
						txtName.setText(user.getName());	//Set the user name
						txtEmail.setText(user.getEmail());	//Set the user email
						txtID.setText(user.getId());		//Set the user ID
						txtPhone.setText(user.getPhoneNumber());	//Set the user phone number
						txtDeptOrMajor.setText(((Student)user).getMajor());	//Get the students major
						txtYear.setText(String.format("%d",((Student)user).getClassYear())); //Get the classes class year
					}
					else
					{
						lblYear.setVisible(false);						//Set year to invisible
						txtYear.setVisible(false);						//Set year to invisible
						txtUserType.setText("Professor");				//Set user to professor
						txtName.setText(user.getName());				//Set the user name
						txtEmail.setText(user.getEmail());				//Set the user email
						txtID.setText(user.getId());					//Set the user ID
						txtPhone.setText(user.getPhoneNumber());		//Set the user phone number
						txtDeptOrMajor.setText(((Professor)user).getDepartment());	//Set the professor's department
					}


					//Finally, call populate checkedOutResources to show which resources are checked out to the user.
					populateCheckedOutResources(user);
				}
			}

		});

	}

	/**
	 * Helper method to reload the list of users shown on the left pane.  
	 * This method should be called whenever the left list needs refreshing.
	 *  HINT:  It is called in LibraryGUI to so that every time someone 
	 *  clicks on this tab, the uses list is refreshed, just in case a user is ever added.
	 */
	public void reloadUsers()
	{
		lvUsers.getItems().clear();

		for (int i = 0; i < LibraryDatabase.getUsers().size(); i++)
		{
			lvUsers.getItems().add(LibraryDatabase.getUsers().get(i).getName());
		}
	}


	/**
	 * Helper method to nicely populate the checked out resources for a user.  This will be called
	 * every time a new user is selected from the left pane.
	 * @param user
	 */
	private void populateCheckedOutResources(User user)
	{
		//Clear the checked out resources that was there for previous user.
		lvCheckedOutResources.getItems().clear();


		//If the user parameter is null or there are no resources checked out to the user
		//just show "none" in the list and disable the checkin/renew buttons.
		if (user == null || user.getCheckedOutResources().size() == 0)
		{
			lvCheckedOutResources.getItems().add("None");
			btnCheckIn.setDisable(true);
			btnRenew.setDisable(true);
		}
		else  //Otherwise, the user has checked out resources and we need to show them.
		{

			//Loop over the user's checked out resources.  One by one, add a
			//string to show to the user's list of checked out resources.
			Resource r;
			for (int i = 0; i < user.getCheckedOutResources().size(); i++)
			{
				//Add the title to the list.
				r = user.getCheckedOutResources().get(i);
				String displayString =  "";

				if(r.isLate())
				{
					displayString += "LATE - WAS ";
				}
				displayString += "DUE: " + r.getDueDate() + " - " + r.getTitle();


				//Add the display string to the list.
				lvCheckedOutResources.getItems().add(displayString);
			}

			//Because the user had checked out resources, we'll enable to the check-in/renew buttons.

			btnCheckIn.setDisable(false);
			btnRenew.setDisable(false);
		}

	}


}
