package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Repository repo=new Repository();
        repo.readProducts("src/products.txt");
        Controller ctrl=new Controller(repo);

        FXMLLoader loader=new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setController(ctrl);
        Parent root =(Parent) loader.load();
        primaryStage.setTitle("Main");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
