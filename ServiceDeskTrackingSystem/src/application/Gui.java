package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Gui {



	public  void start(Stage primaryStage) {

		//Record userRecord = new Record(null, null, null, null, null, null);


		try {


			
			final double size =625;
			String notes=" ";


			//Generation Main pane
			GridPane mainGridPane = new GridPane();
			mainGridPane.setAlignment(Pos.TOP_CENTER); mainGridPane.setPadding(new
					Insets(11.5, 12.5, 5.5, 5.5)); mainGridPane.setHgap(5.5);
					mainGridPane.setVgap(5.5); 
					//this label will show error or confirmation messages
					Label messageLabel = new Label();
					mainGridPane.add(messageLabel,0,0);


					//first secondary mainpane to enter data
					GridPane createNewRecord = new GridPane();

					createNewRecord.setPadding(new Insets(11.5, 0, 13.5, 0));
					createNewRecord.setHgap(5.5); createNewRecord.setVgap(5.5);
					createNewRecord.setMinWidth(size-50);

					//elements for the GUI
					createNewRecord.add(new Label("Date of request: "),0,0); 
					DatePicker recordDate = new DatePicker();
					
					//calling the restricDatePicker to limit the recordDate

					restrictDatePicker(recordDate, LocalDate.of(2000, Month.JANUARY, 1),LocalDate.now());


					createNewRecord.add(recordDate,1,0);


					createNewRecord.add(new Label("Request description: "),2,0); 
					TextField descriptionOfRequestTextField = new TextField(); 
					createNewRecord.add(descriptionOfRequestTextField,3,0);

					RadioButton serviceCompleteRB = new RadioButton("YES"); 
					RadioButton	serviceIncompleteRB = new RadioButton("NO"); 

					ToggleGroup serviceOverGroup= new ToggleGroup(); 
					serviceCompleteRB.setToggleGroup(serviceOverGroup);
					serviceIncompleteRB.setToggleGroup(serviceOverGroup);

					// if the service was not completed the user will be force t write a note 
					createNewRecord.add(new Label("Was the service completed? "), 0, 1);
					createNewRecord.add(serviceCompleteRB, 1, 1);
					createNewRecord.add(serviceIncompleteRB, 2, 1);
					createNewRecord.add(new Label("No, requires a note "), 3, 1);


					createNewRecord.add(new Label("Technician name : "),2,2);
					DatePicker dateOfCompletionDatePicker = new DatePicker();
					createNewRecord.add( dateOfCompletionDatePicker,1,2);

					//calling the restricDatePicker to limit the date of completion
					restrictDatePicker(dateOfCompletionDatePicker, LocalDate.of(2000, Month.JANUARY, 1),LocalDate.now());

					createNewRecord.add(new Label("Date of completion: "),0,2); 
					TextField technicianNameTextField = new TextField();
					createNewRecord.add(technicianNameTextField ,3,2);


					GridPane notesPane = new GridPane();

					notesPane.add(new Label("Notes :"), 0, 0); 
					TextField notesTextField = new TextField(); 
					notesTextField.setMinWidth(size-13.5-11.5);
					notesPane.add(notesTextField ,0,1);

					//inabilitate fields according the user selections
					serviceIncompleteRB.setOnAction(e->{ 
						if(serviceIncompleteRB.isSelected()) 
						{



							dateOfCompletionDatePicker.setDisable(true);
							dateOfCompletionDatePicker.setValue(null);
						}
					}
							);
					serviceCompleteRB.setOnAction(e->{ 
						if(serviceCompleteRB.isSelected()) 
						{

							dateOfCompletionDatePicker.setDisable(false);

						}
					});
					Button saveRecordButton = new Button("Save Record");
					saveRecordButton.setMaxWidth(size/5.208); 

					saveRecordButton.setOnAction(e->{ 
						try {
							///validate user entry
							if(!recordDate.getValue().toString().isEmpty())
							{
								if(!descriptionOfRequestTextField.getText().trim().isEmpty())
								{
									ServiceDeskTrackingSystem test = new ServiceDeskTrackingSystem();



									//////////////////////////////////////
									if(serviceCompleteRB.isSelected())
									{
										if((!dateOfCompletionDatePicker.getValue().toString().isEmpty())&&dateOfCompletionDatePicker.getValue().isAfter(recordDate.getValue())|| dateOfCompletionDatePicker.getValue().isEqual(recordDate.getValue()) ) {

											
											if(!technicianNameTextField.getText().trim().isEmpty()) {
												messageLabel.setText("Correct");


												if(notesTextField.getText().trim().isEmpty())
												{
													// to auto generate a record ID i will count the number of records in the database then add one
													ServiceDeskTrackingSystem RecordId = new ServiceDeskTrackingSystem();
													ResultSet rs;

													rs = RecordId.getRecordID();
													Integer recordI = Integer.parseInt(rs.getString("COUNT(*)"));

													//adding a new record
													test.addService(recordI+1, recordDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), descriptionOfRequestTextField.getText(), 
															"Yes", dateOfCompletionDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), technicianNameTextField.getText(), notesTextField.getText());
													messageLabel.setText("Record saved");
													dateOfCompletionDatePicker.setDisable(false);
													dateOfCompletionDatePicker.setValue(null);
													descriptionOfRequestTextField.clear();
													technicianNameTextField.clear();
													serviceCompleteRB.setSelected(false);
													serviceIncompleteRB.setSelected(false);
													notesTextField.clear();
													recordDate.setValue(null);

												}
												else {
													// to auto generate a record ID i will count the number of records in the database then add one

													ServiceDeskTrackingSystem RecordId = new ServiceDeskTrackingSystem();
													ResultSet rs;

													rs = RecordId.getRecordID();
													Integer recordI = Integer.parseInt(rs.getString("COUNT(*)"));
													//adding a new record
													test.addService(recordI+1,recordDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), descriptionOfRequestTextField.getText(), 
															"Yes", dateOfCompletionDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), technicianNameTextField.getText(), notes);
													messageLabel.setText("Record saved");
													dateOfCompletionDatePicker.setDisable(false);
													dateOfCompletionDatePicker.setValue(null);
													descriptionOfRequestTextField.clear();
													technicianNameTextField.clear();
													serviceCompleteRB.setSelected(false);
													serviceIncompleteRB.setSelected(false);
													notesTextField.clear();
													recordDate.setValue(null);
												}
										

											}
											else 
											{ 
												messageLabel.setText("Insert techinician name"); 
											}

										}
										else 
										{ 
											messageLabel.setText("Insert a valid date of Completion"); 

										}


									}
									//////////////////////////////////

									else if(serviceIncompleteRB.isSelected())
									{
										if(!technicianNameTextField.getText().trim().isEmpty()) {

											if(!notesTextField.getText().trim().isEmpty()) {
												messageLabel.setText("Correct");
												
												// to auto generate a record ID i will count the number of records in the database then add one

												ServiceDeskTrackingSystem RecordId = new ServiceDeskTrackingSystem();
												ResultSet rs;

												rs = RecordId.getRecordID();
												Integer recordI = Integer.parseInt(rs.getString("COUNT(*)"));
												//adding a new record
												test.addService(recordI+1,recordDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), descriptionOfRequestTextField.getText(), 
														"NO", "PENDING", technicianNameTextField.getText(), notesTextField.getText());
												messageLabel.setText("Record saved");
												dateOfCompletionDatePicker.setDisable(false);
												dateOfCompletionDatePicker.setValue(null);
												descriptionOfRequestTextField.clear();
												technicianNameTextField.clear();
												serviceCompleteRB.setSelected(false);
												serviceIncompleteRB.setSelected(false);
												notesTextField.clear();
												recordDate.setValue(null);
											}
											else 
											{ 
												messageLabel.setText("For an uncomplete service a note is required"); 
											}


										}
										else 
										{ 
											messageLabel.setText("Insert techinician name"); 
										}

									}
									else
									{
										messageLabel.setText("Please select if the service was completed");

									}

								}
								else
								{
									messageLabel.setText("Insert a service description");
								}
							}

						}
						catch (NullPointerException ex) {
							messageLabel.setText("Insert a valid date value");

						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
							);



					Button clearRecordButton = new Button("Cancel"); 
					clearRecordButton.setMaxWidth(size/5.208);
					clearRecordButton.setOnAction(e->{ 

						dateOfCompletionDatePicker.setDisable(false);
						dateOfCompletionDatePicker.setValue(null);
						descriptionOfRequestTextField.clear();
						technicianNameTextField.clear();
						serviceCompleteRB.setSelected(false);
						serviceIncompleteRB.setSelected(false);
						notesTextField.clear();
						recordDate.setValue(null);
					}
							);



					GridPane getRecordPane =new GridPane(); 
					getRecordPane.setPadding(new Insets(11.5, 15, 13.5, 0));
					getRecordPane.setHgap(5.5);
					getRecordPane.setVgap(5.5);

					getRecordPane.add(saveRecordButton, 1, 0);
					getRecordPane.add(clearRecordButton, 2, 0);

					getRecordPane.add(new Label("Search specific day records :"),0,1);
					DatePicker searchByDayDatePicker = new DatePicker();
					restrictDatePicker(searchByDayDatePicker, LocalDate.of(2000, Month.JANUARY, 1),LocalDate.now());

					Button searchByDayButton = new Button("Search by Day");

					searchByDayButton.setMaxWidth(size/5.208);
					getRecordPane.add(new Label(""), 0, 1); 
					getRecordPane.add(searchByDayDatePicker,1,1);
					getRecordPane.add( searchByDayButton,2,1);

					getRecordPane.add(new Label("Search Technician :"),0,2); 
					TextField searchTechnicianRecordTextField = new TextField();
					Button searchTechnicianButton = new Button("Search Technician");
					searchTechnicianButton.setMaxWidth(size/5.208); 



					getRecordPane.add(searchTechnicianRecordTextField,1,2); 
					getRecordPane.add(searchTechnicianButton,2,2); 
					Button printRecord = new Button("Print all Records");
					printRecord.setMaxWidth(size/5.208); 
					getRecordPane.add(printRecord, 1, 3);
					Button cleanTextArea = new Button("Clean Screen");
					cleanTextArea.setMaxWidth(size/5.208); 
					getRecordPane.add(cleanTextArea, 2,3);

					//textArea to show the table content
					TextArea databaseInfo = new TextArea(); 
					databaseInfo.setMaxWidth(size-13.5-11.5);
					databaseInfo.setWrapText(true);
					databaseInfo.setEditable(false);

					//serach by day button
					searchByDayButton.setOnAction(e-> {

						try {
							databaseInfo.clear();
							databaseInfo.appendText(" Date of Request || Request Desc || Service Completion || Date Of Complet || Tech Name || Notes \n \n");

							if(!searchByDayDatePicker.getValue().toString().isEmpty())
							{
								ServiceDeskTrackingSystem test = new ServiceDeskTrackingSystem();
								ResultSet rs;


								rs = test.displayServicesByRecordDate(searchByDayDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
								int counter=0;
								while(rs.next() ) {
									databaseInfo.appendText(rs.getString("dateOfRequest")+ " || "+ rs.getString("requestDescription")+ " || "
											+ rs.getString("serviceCompleted")+" || " +rs.getString("dateOfCompletion")+" || "+rs.getString("technicianName")+" || "+rs.getString("notes")+"\n");
									counter++;
								}
								if(counter==0) {
									databaseInfo.setText("No Record Found");
								}

							}

							else

							{
								messageLabel.setText("Insert Date to search");
							}


						}catch (ClassNotFoundException ex) {
							// TODO Auto-generated catch block
							messageLabel.setText("Insert Date to search");
						} catch (SQLException ex) {
							// TODO Auto-generated catch block
							messageLabel.setText("Insert Date to search");
						}
						catch (NullPointerException ex) {
							// TODO Auto-generated catch block
							databaseInfo.setText("Insert a valid date value");
						}
					});
					
					//search by technician

					searchTechnicianButton.setOnAction(e-> {
						databaseInfo.clear();
						ServiceDeskTrackingSystem test = new ServiceDeskTrackingSystem();
						ResultSet rs;
						if(!searchTechnicianRecordTextField.getText().trim().isEmpty())
						{
							databaseInfo.appendText("ID || Date of Request || Request Desc || Service Completion || Date Of Complet || Tech Name || Notes \n \n");
							try {
								rs = test.displayServicesByUserTechnicianName(searchTechnicianRecordTextField.getText());

								while(rs.next() ) {
									databaseInfo.appendText(rs.getString("ID")+" || "+ rs.getString("dateOfRequest")+ " || "+ rs.getString("requestDescription")+ " || "
											+ rs.getString("serviceCompleted")+" || " +rs.getString("dateOfCompletion")+" || "+rs.getString("technicianName")+" || "+rs.getString("notes")+"\n");
								}

							} catch (ClassNotFoundException ex) {
								// TODO Auto-generated catch block
								ex.printStackTrace();
							} catch (SQLException ex) {
								// TODO Auto-generated catch block
								ex.printStackTrace();
							}
						}
						else {
							databaseInfo.setText("Insert a technician name");

						}

					});
					
					//print al the records
					printRecord.setOnAction(e->{
						databaseInfo.clear();
						ServiceDeskTrackingSystem test = new ServiceDeskTrackingSystem();
						ResultSet rs;

						try {
							rs = test.displayServices();

							databaseInfo.appendText("ID || Date of Request || Request Desc || Service Completion || Date Of Complet || Tech Name || Notes \n \n");

							while(rs.next() ) {
								databaseInfo.appendText(rs.getString("ID")+" || "+ rs.getString("dateOfRequest")+ " || "+ rs.getString("requestDescription")+ " || "

										+ rs.getString("serviceCompleted")+" || " +rs.getString("dateOfCompletion")+" || "+rs.getString("technicianName")+" || "+rs.getString("notes")+"\n");


							}


							rs = test.getRecordID();
							databaseInfo.appendText("Number of records found: "+rs.getString("COUNT(*)"));



						} catch (ClassNotFoundException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						} catch (SQLException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}

					});

					cleanTextArea.setOnAction(e-> {
						databaseInfo.clear();

					}
							);



					mainGridPane.add(createNewRecord,0,1); 
					mainGridPane.add(notesPane,0,2);
					mainGridPane.add(getRecordPane, 0, 4); 
					mainGridPane.add(databaseInfo,0,5);




					Scene scene = new Scene(mainGridPane,size,size/1.1);

					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene); 
					primaryStage.setResizable(false);
					primaryStage.setTitle("Service Desk Traking System");
					primaryStage.show();
					
		}
		catch(Exception e) 
		{ 
			e.printStackTrace(); 
		}



	}
	//this method will restrict the days in the day picker calendar
	
	public void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isBefore(minDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}else if (item.isAfter(maxDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
	}


}
