package models.Cards;

public enum Type {

    WARRIOR_MONSTER("Warrior"),
    NORMAL("Normal"),
    EFFECT_MONSTER("Effect"),
    AQUA_MONSTER("Aqua"),
    FIEND_MONSTER("Fiend"),
    BEAST_MONSTER("Beast"),
    SPELLCASTER_MONSTER("Spellcaster"),
    THUNDER_MONSTER("Thunder"),
    DRAGON("Dragon"),
    RITUAL_MONSTER("Ritual"),
    SEASERPENT_MOSTER("Sea Serpent"),
    CYBERSE_MONSTER("Cyberse"),
    FAIRY_MONSTER("Fairy"),
    MACHINE_MONSTER("Machine"),
    INSECT_MONSTER("Insect"),
    ROCK_MONSTER("Rock");

    private final String name;

    Type(String str){
        name = str;
    };

    public String getName() {
        return name;
    }
}
