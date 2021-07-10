package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;

public abstract class AIHandler {
    protected AIHandler nextHandler;

    public void setNextHandler(AIHandler handler){
        this.nextHandler = handler;
    }

    public abstract void handle(Battlefield battlefield);
}
