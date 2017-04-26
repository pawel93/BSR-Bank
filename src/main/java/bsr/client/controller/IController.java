package bsr.client.controller;

import bsr.client.ParentScreen;

/**
 * Created by Paweł on 2017-01-24.
 */
public interface IController
{
    public void setScreenParent(ParentScreen parent);
    public void setMainController(Controller controller);
    public void initData();
}
