package ermmmm.fx;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import lombok.Getter;
import lombok.SneakyThrows;

import ermmmm.api.API;
import ermmmm.api.Category;
import ermmmm.api.Type;

// TODO: back & refresh button
// TODO: image counter if doing background loading or smthn
public class WaifuPicsController {

	Type type;
	Category cat;

	@SneakyThrows
	public void advance(Type type, Category cat) {
		this.type = type;
		this.cat = cat;

		String[] urls = API.thirty(type, cat);
		List<Node> children = flowPane.getChildren();

		for (String url : urls) {
			Image image = new Image(url, 350, 350, true, true);
			//System.out.printf("%sx%s%n", image.getWidth(), image.getHeight());
			ImageView iv = new ImageView(image);
			iv.setPreserveRatio(true);
			//iv.setFitWidth(image.getHeight() / 3.5);
			//iv.setFitHeight(350);
			children.add(iv);
		}
	}

	@FXML @Getter
	private AnchorPane root;

	@FXML
	private FlowPane flowPane;

	@FXML
	private void initialize() {
	}

}
