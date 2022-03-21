package Controller;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTooltip;

import Database.EmployeeDao;
import Model.AlertMaker;
import Model.Employee;
import Model.Main;
import Model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeController implements Initializable {

	@FXML
	private JFXTextField empName, email, phoneNbr, employeeID, password;
	@FXML
	private Button saveBtn, cancelBtn;


	@FXML
	public void saveCancelAction(ActionEvent event) {

		if (event.getSource().equals(saveBtn)) {
			if (empName.getText().isEmpty() || employeeID.getText().isEmpty() || phoneNbr.getText().isEmpty()
					|| email.getText().isEmpty()) {
				AlertMaker.showWarningAlert("Invalid Inputs", "All field are REQUIRED for adding this employee");
			} else if (empName.getText().split(" ").length != 2) {
				AlertMaker.showWarningAlert("Invalid Name", "Name should only contain First and Last name");
			}

			else if (phoneNbr.getText().chars().allMatch(Character::isDigit)) {
				if (phoneNbr.getText().length() != 8) {
					AlertMaker.showWarningAlert("Invalid Phone Number", "The Phone Number should be 8 digits");
				} else {
					Optional<ButtonType> response = AlertMaker.showConfigurationAlert(null, "Proceed ?");
					if (response.get().equals(ButtonType.OK)) {
						AlertMaker.showInformationAlert("Save Member", "Employee added successfully to your list");

						Person p = new Employee(employeeID.getText(), empName.getText(), phoneNbr.getText(),
								email.getText(), password.getText());
						Main.empDAO.add(p);
						Stage stage = (Stage) saveBtn.getScene().getWindow();
						stage.close();
					}
				}
			} else {
				AlertMaker.showWarningAlert("Invalid Phone Number", "The Phone Number should be contains only digits");
			}
		}
		if (event.getSource().equals(cancelBtn)) {
			Stage stage = (Stage) cancelBtn.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void generateId(ActionEvent event) {
		Random rand = new Random();
		int nb = rand.nextInt(100);
		String []name=empName.getText().split(" ");
		
		employeeID.setText(""+name[0].charAt(0)+name[1].charAt(0)+ nb);
	}

	@FXML
	void generatePassword(ActionEvent event) {
				password.setText(employeeID.getText() + "@" + phoneNbr.getText().hashCode());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub


	}

}