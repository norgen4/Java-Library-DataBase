package library;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 */
public class LibraryGUI extends Application {

	//Controls for main GUI - Controls are placed in a tabbed pane
	private TabPane tabPane;

	private EnterUsersTab enterUsersTab;          //add new users 
	private EnterResourcesTab enterResourcesTab;  //add new books
	private ViewUsersTab viewUsersTab;             //view users/checkin/renew
	private ViewResourcesTab viewResourcesTab;     //view resources
	

	/**
	 * Launches the GUI and populates the DB collections.
	 * 
	 * @param args No arguments are passed
	 * 
	 */
	public static void main(String[] args)  {

		
		//Load the DB
		LibraryDatabase.populateCollection();
		
		//Launch the GUI
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {


		//Set up the tabs
		tabPane = new TabPane();

		enterUsersTab = new EnterUsersTab();
		enterResourcesTab = new EnterResourcesTab();
		viewUsersTab = new ViewUsersTab();
		viewResourcesTab = new ViewResourcesTab();

		tabPane.getTabs().add(new Tab("Enter New User", enterUsersTab));
		tabPane.getTabs().add( new Tab("Enter New Resource", enterResourcesTab));		
		tabPane.getTabs().add( new Tab("View Users", viewUsersTab));	
		tabPane.getTabs().add( new Tab("View Resources", viewResourcesTab));		


		//tabPane.getTabs().get(2) refers to the View Users Tab.  This is because it was the
		//3rd tab added above. Add an event handler to it so that whenever someone switches to the
		//view users tab, the resource collection is reloaded to the list view.  This way,
		//if someone adds a user on a different pane, we'll see it when we switch into the new pane.
		tabPane.getTabs().get(2).setOnSelectionChanged(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				viewUsersTab.reloadUsers();  //The reload method refreshes the list of users shown on the left
				                             //side of the viewUsersTab.
			}

		});


		//Add the tabbed pane to the scene and set the stage.
		VBox vBox = new VBox(tabPane);
		Scene scene = new Scene(vBox, 700, 600);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Library GUI");

		primaryStage.show();
	}	
}


