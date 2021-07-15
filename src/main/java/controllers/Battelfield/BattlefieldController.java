package controllers.Battelfield;

import controllers.Battelfield.Battlefield;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class BattlefieldController {
    public static HashMap<User,Battlefield> battlefields = new HashMap<>();
    public static HashMap<Battlefield,Thread> threads = new HashMap<>();

}
