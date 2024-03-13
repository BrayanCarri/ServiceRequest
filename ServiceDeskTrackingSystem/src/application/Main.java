package application;
	
import javafx.application.Application;
import javafx.stage.Stage;




public class Main extends Application {
	
	Gui mainWindow = new Gui();

	public static void main(String[] args) {		

		launch(args);
			
	}
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		mainWindow.start(arg0);
			
	}


	}

