package bsr.client.controller;

import bsr.model.History;
import bsr.client.ParentScreen;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Paweł on 2017-01-26.
 */
public class HistoryController implements Initializable, IController
{
    private ParentScreen parentScreen;
    Controller controller;
    List<History> currentHistory;
    public TableColumn<History, Integer> col0;
    public TableColumn<History, Integer> col1;
    public TableColumn<History, String> col2;
    public TableColumn<History, String> col3;
    public TableColumn<History, String> col4;
    public TableColumn<History, String> col5;
    public TableView<History> table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col0.setCellValueFactory(new PropertyValueFactory<>("account"));
        col1.setCellValueFactory(new PropertyValueFactory<>("title"));
        col2.setCellValueFactory(new PropertyValueFactory<>("income"));
        col3.setCellValueFactory(new PropertyValueFactory<>("outcome"));
        col4.setCellValueFactory(new PropertyValueFactory<>("source"));
        col5.setCellValueFactory(new PropertyValueFactory<>("saldo"));


    }

    public void initData(){
        currentHistory = controller.getHistoryList();
        table.setItems(FXCollections.observableArrayList(currentHistory));
        //table.setItems(FXCollections.observableArrayList(currentHistory));
        //table.getColumns().addAll(col1, col2, col3, col4, col5);
    }

    @Override
    public void setScreenParent(ParentScreen parent) {
        this.parentScreen = parent;

    }

    public void setMainController(Controller controller)
    {
        this.controller = controller;
    }

    public void backToHome(ActionEvent actionEvent)
    {
        parentScreen.setScreen(3);
    }
}
