package ermmmm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import lombok.Getter;

import ermmmm.api.API;
import ermmmm.api.Category;
import ermmmm.api.SfwCategory;
import ermmmm.api.Type;
import ermmmm.fx.StartController;
import ermmmm.fx.WaifuPicsController;

public class Main extends Application {

	@Getter
	private static Main instance;
	@Getter
	private static Stage stage;

	Scene scene;

	StartController sc;
	WaifuPicsController wpc;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.stage = primaryStage;
		Main.instance = this;

		//API.singleAsync(Type.sfw, SfwCategory.waifu, url -> Platform.runLater(() -> stage.getIcons().add(new Image(url))));
		stage.getIcons().add(new Image(API.single(Type.sfw, SfwCategory.waifu)));
		stage.setTitle("Erm");
		//stage.setResizable(false);

		FXMLLoader loader = new FXMLLoader(StartController.class.getResource("Start.fxml"));
		Parent start = loader.load();
		sc = loader.getController();

		loader = new FXMLLoader(WaifuPicsController.class.getResource("WaifuPics.fxml"));
		loader.load();
		wpc = loader.getController();

		scene = new Scene(start);
		stage.setScene(scene);

		stage.sizeToScene();
		//stage.setMaximized(true);
		stage.centerOnScreen();
		stage.show();
	}


	public void advance(Type type, Category cat) {
		Parent waifuPics = wpc.getRoot();

		// TODO: loading screen with progress of how many done(?)
		wpc.advance(type, cat);

		scene.setRoot(waifuPics);
	}

}