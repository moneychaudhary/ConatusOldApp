package com.example.money.conatusapp.NavigationDrawer;

/**
 * Created by #money on 9/28/2016.
 */

public class DrawerMenu {
    public DrawerMenu(String title, int imageid) {
        this.title = title;
        Imageid = imageid;
    }

    private String title;

    public int getImageid() {
        return Imageid;
    }

    public String getTitle() {
        return title;
    }

    private int Imageid;
}
