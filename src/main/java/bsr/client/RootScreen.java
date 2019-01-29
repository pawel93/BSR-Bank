package bsr.client;

import bsr.client.controller.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;



public class RootScreen extends StackPane
{

    private HashMap<String, Pane> screens = new HashMap<>();
    private HashMap<String, IController> controllers = new HashMap<>();

    public RootScreen()
    {
        super();

    }

    public void addScreen(String name, Pane screen)
    {
        screens.put(name, screen);
    }

    public void addController(String name, IController controller)
    {
        controllers.put(name, controller);
    }

    public IController getController(String name){

        return controllers.get(name);
    }

    public void loadScreens(String name, String resource)throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Pane loadedScreen = loader.load();
        IController screenController = loader.getController();
        screenController.setScreenParent(this);

        addScreen(name, loadedScreen);
        addController(name, screenController);
    }

    public void setScreen(String name)
    {
        if(screens.get(name) != null)
        {
            if(!getChildren().isEmpty())
            {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
            }
            else
            {
                getChildren().add(screens.get(name));
            }
        }

    }



}
