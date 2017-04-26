package bsr.client;

import bsr.client.controller.Controller;
import bsr.client.controller.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

/**
 * Created by Paweł on 2017-01-24.
 */
public class ParentScreen extends StackPane
{

    HashMap<Integer, Node> screens = new HashMap<>();
    HashMap<Integer, IController> controllers = new HashMap<>();
    Controller controller = new Controller();

    public ParentScreen()
    {
        super();

    }

    public void addScreen(int id, Node node)
    {
        screens.put(id, node);
    }

    public Node getScreen(int id)
    {
        return screens.get(id);
    }

    public void addController(int id, IController controller)
    {
        controllers.put(id, controller);
    }

    public boolean loadScreens(int id, String resource)throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent loadedScreen = (Parent)loader.load();
        IController myController = (IController)loader.getController();
        myController.setScreenParent(this);
        myController.setMainController(controller);
        addScreen(id, loadedScreen);
        addController(id, myController);
        return true;
    }

    public boolean setScreen(int id)
    {
        if(screens.get(id) != null)
        {
            if(!getChildren().isEmpty())
            {
                getChildren().remove(0);
                getChildren().add(0, screens.get(id));
                controllers.get(id).initData();
            }
            else
            {
                getChildren().add(screens.get(id));
            }
            return true;
        }
        else
        {
            System.out.println("Screens cannot be loaded");
            return false;
        }
    }



}
