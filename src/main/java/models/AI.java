package models;

import controllers.Battelfield.AI.*;
import controllers.Battelfield.Battlefield;

import java.util.ArrayList;

public class AI extends Duelist{
    public AI(User user) {
        super(user);
    }
    public void runAi(Battlefield battlefield){
        AIHandler noDangerHandler = new NoDangerHandler();
        AIHandler lowLevelHandler = new LowLevelHandler();
        AIHandler midLevelHandler = new MidLevelHandler();
        AIHandler highLevelHandler = new HighLevelHandler();
        noDangerHandler.setNextHandler(lowLevelHandler);
        lowLevelHandler.setNextHandler(midLevelHandler);
        midLevelHandler.setNextHandler(highLevelHandler);
        noDangerHandler.handle(battlefield);
    }

    public String getCommand(Battlefield battlefield){
        return "salam";
    }
}
