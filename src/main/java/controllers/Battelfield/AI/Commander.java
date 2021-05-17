package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import controllers.Battelfield.Phase;

public class Commander {

    public String getCommand(Battlefield battlefield){
        if(battlefield.getPhase() == Phase.DRAW_PHASE) return "next phase";
        if(battlefield.getPhase() == Phase.STANDBY_PHASE) return "next phase";
        //if(battlefield.getPhase() == Phase.MAIN1_PHASE)
        return "next phase";
    }
}
