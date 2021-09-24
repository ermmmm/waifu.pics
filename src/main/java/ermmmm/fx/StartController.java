package ermmmm.fx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import com.jfoenix.controls.JFXButton;
import ermmmm.Main;
import ermmmm.api.Category;
import ermmmm.api.Type;

public class StartController {

	@FXML
	ComboBox<Type> typeBox;

	@FXML
	ComboBox<Category> categoryBox;

	@FXML
	JFXButton advanceBtn;

	@FXML
	private void initialize() {
		typeBox.getItems().addAll(Type.values());
		typeBox.getSelectionModel().selectedItemProperty().addListener((obs, oldType, newType) -> {
			if (newType != oldType) {
				categoryBox.getItems().setAll(newType.getCategory().getEnumConstants());
			}
		});

		var typeIsNull = typeBox.getSelectionModel().selectedItemProperty().isNull();
		categoryBox.disableProperty().bind(typeIsNull);

		advanceBtn.disableProperty().bind(typeIsNull.or(categoryBox.getSelectionModel().selectedItemProperty().isNull()));
	}

	@FXML
	void advance() {
		Type type = typeBox.getSelectionModel().getSelectedItem();
		Category cat = categoryBox.getSelectionModel().getSelectedItem();
		Main.getInstance().advance(type, cat);
	}

}
