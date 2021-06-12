package library;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;

/***
 * This class models the EnterResourcesTab and sets up all of its controls.
 * It extends VBox so that it can be added as a tab to the main GUI.
 * 
 * @author ryank
 *
 */
public class EnterResourcesTab extends VBox{

	//Controls for EnterResourceTab
	private Label lblResourceType;
	private Label lblTitle;
	private Label lblYear;
	private Label lblAuthorOrProdCompany;  //A label that shows either the author or the prod company.
	private Label lblISBN;

	private TextField txtTitle;
	private TextField txtYear;
	private TextField txtAuthorOrProdCompany;   //A textfield for either the author or the prod company.
	private TextField txtISBN;

	private ComboBox<String> comboResourceType;

	private Button btnSubmit;

	public EnterResourcesTab()
	{
		super(50); //set vertical/hzntal gap of VBox to 50

		lblResourceType = new Label("Type of Resource:");
		comboResourceType = new ComboBox<String>();
		comboResourceType.getItems().addAll("Book", "Movie");
		comboResourceType.setValue("Book");  //Pre-select Book to be added
		comboResourceType.setPrefWidth(300);
		
		lblTitle = new Label("Title:");
		txtTitle = new TextField();

		lblAuthorOrProdCompany = new Label("Author:");
		txtAuthorOrProdCompany = new TextField();
		lblAuthorOrProdCompany.setPrefWidth(125);

		lblYear = new Label("Year:");
		txtYear = new TextField();

		lblISBN = new Label("ISBN:");
		txtISBN = new TextField();

		btnSubmit = new Button("Submit New Resource");
		
		
		//Create a grid pane to hold all fields for a new resource.
		GridPane resInfo = new GridPane();

		resInfo.setHgap(10);
		resInfo.setVgap(10);
		resInfo.setAlignment(Pos.CENTER);

		resInfo.add(lblResourceType, 0, 0);
		resInfo.add(comboResourceType, 1, 0);
		resInfo.add(lblTitle, 0, 1);
		resInfo.add(txtTitle, 1, 1);

		resInfo.add(lblAuthorOrProdCompany, 0, 2);
		resInfo.add(txtAuthorOrProdCompany, 1, 2);

		resInfo.add(lblYear, 0, 3);
		resInfo.add(txtYear, 1, 3);

		resInfo.add(lblISBN, 0, 4);
		resInfo.add(txtISBN, 1, 4);


		//Remember this class is inheriting from VBox.
		//Add the userInfo gridPane and the submit button to the vbox.
		super.getChildren().addAll(resInfo, btnSubmit);
		super.setAlignment(Pos.CENTER);
		super.setPadding(new Insets(50, 20, 50, 20));

		
		//*******************************
		// EVENT HANDLERS BELOW HERE
		//******************************
		
		
		/**
		 * When the button Submit new Resource is pressed
		 * The information is either used to create a new resource of books 
		 * or movies. Or the program throws an error message to user if one of 
		 * the text fields is left blank
		 */
		btnSubmit.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent arg0) {
						
						//If the resource is a book get book text fields
						if(comboResourceType.getSelectionModel().getSelectedItem() =="Book")
						{
						String title = txtTitle.getText();
						String author = txtAuthorOrProdCompany.getText();
						String year = txtYear.getText();
						String isbn = txtISBN.getText();
						
						//If text fields hold info add info
						if(!title.equals("") && !author.equals("") && !year.equals("") 
								&& !isbn.equals(""))
						{
							Book books = new Book(isbn,title,author,Integer.parseInt(year));
							LibraryDatabase.addResources(books);
						}
						//Alert if field is empty
						else if(title.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid title.");
							a.showAndWait();
						}
						//Alert if field is empty
						else if(author.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid author.");
							a.showAndWait();
						}
						//Alert if field is empty
						else if(year.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid publication year.");
							a.showAndWait();
						}
						//Alert if field is empty
						else if(isbn.equals(""))
						{
							Alert a = new Alert(AlertType.INFORMATION);
							a.setContentText("Please enter a valid ISBN number.");
							a.showAndWait();
						}
						}
						//If the resource is a book get book text fields
					else if(comboResourceType.getSelectionModel().getSelectedItem() =="Movie")
					{

								String title = txtTitle.getText();
								String author = txtAuthorOrProdCompany.getText();
								String year = txtYear.getText();
								
								//If text fields hold info add info
								if(!title.equals("") && !author.equals("") && !year.equals(""))
								{
									Resource movies = new Movie(title,Integer.parseInt(year),author);
									LibraryDatabase.addResources(movies);
								}
								//Alert if field is empty
								else if(title.equals(""))
								{
									Alert a = new Alert(AlertType.INFORMATION);
									a.setContentText("Please enter a valid title.");
									a.showAndWait();
								}
								//Alert if field is empty
								else if(author.equals(""))
								{
									Alert a = new Alert(AlertType.INFORMATION);
									a.setContentText("Please enter a valid production company.");
									a.showAndWait();
								}
								//Alert if field is empty
								else if(year.equals(""))
								{
									Alert a = new Alert(AlertType.INFORMATION);
									a.setContentText("Please enter a valid publication year.");
									a.showAndWait();
								}
								
					}	
				
					}
					
					
					
					
					
				});
		
		
		
		
		
		//Event handler for the book/movie drop down.
		//If a user changes what is selected, then a label changes
		//to/from Author/Production Company and the ISBN label/text field are made visible/invisible.
		comboResourceType.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				//If a book is added, update lblAuthorOrProdCompany field to say "Author"
				//and set the ISBN label/field to visible.
				if (comboResourceType.getSelectionModel().getSelectedItem() == "Book")
				{
					lblAuthorOrProdCompany.setText("Author:");
					lblISBN.setVisible(true);
					txtISBN.setVisible(true);
				}
				else//If a movie is added, update lblAuthorOrProdCompany field to say "ProductionCompany"
					//and set the ISBN label/field to invisible.
				{
					lblAuthorOrProdCompany.setText("Production Company:");
					lblISBN.setVisible(false);
					txtISBN.setVisible(false);
				}

			}

		});




	}
}
