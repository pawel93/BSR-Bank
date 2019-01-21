package bsr.client.controller;

import bsr.client.RootScreen;
import bsr.model.History;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;



public class HistoryController implements Initializable, IController
{
    private RootScreen rootScreen;

    @FXML private TableColumn<History, Integer> col0;
    @FXML private TableColumn<History, Integer> col1;
    @FXML private TableColumn<History, String> col2;
    @FXML private TableColumn<History, String> col3;
    @FXML private TableColumn<History, String> col4;
    @FXML private TableColumn<History, String> col5;
    @FXML private TableColumn<History, Date> col6;

    @FXML private TableView<History> table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col0.setCellValueFactory(new PropertyValueFactory<>("account"));
        col1.setCellValueFactory(new PropertyValueFactory<>("title"));
        col2.setCellValueFactory(new PropertyValueFactory<>("income"));
        col3.setCellValueFactory(new PropertyValueFactory<>("outcome"));
        col4.setCellValueFactory(new PropertyValueFactory<>("source"));
        col5.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        col6.setCellValueFactory(new PropertyValueFactory<>("date"));


    }

    public void initData(List<History> currentHistory){

        table.setItems(FXCollections.observableArrayList(currentHistory));

        //table.setItems(FXCollections.observableArrayList(currentHistory));
        //table.getColumns().addAll(col1, col2, col3, col4, col5);
    }

    @Override
    public void setScreenParent(RootScreen parent) {
        this.rootScreen = parent;

    }

    public void backToHome(ActionEvent actionEvent)
    {
        rootScreen.setScreen("home");
    }
}
