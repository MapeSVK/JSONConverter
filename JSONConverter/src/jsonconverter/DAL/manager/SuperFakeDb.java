/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.manager;

import java.util.ArrayList;
import java.util.List;
import jsonconverter.BE.Config;

/**
 *
 * @author Samuel
 */
public class SuperFakeDb {
    //SUPER FAKE DB
    private List<Config> fakeConfigDatabase = new ArrayList();

    public List<Config> getFakeConfigDatabase() {
        return fakeConfigDatabase;
    }
    
    public void addToFakeConfigDatabase(Config config) {
        fakeConfigDatabase.add(config);
    }

}
