package dev.pronovich.tennis.scoreboard.domain;

public enum Points {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD");

    private final String value;

    Points(String value){
        this.value = value;
    }

    public String getCode() {
        return value;
    }


    public Points nextPoints() {
      return switch (this) {
            case ZERO -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY, ADVANTAGE -> ADVANTAGE;
        };
    }

}