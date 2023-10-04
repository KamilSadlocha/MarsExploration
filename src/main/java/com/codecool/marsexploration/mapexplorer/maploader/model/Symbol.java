package com.codecool.marsexploration.mapexplorer.maploader.model;

public enum Symbol {
    MOUNTAIN("#", "🗻"),
    PIT("&", "\uD83C\uDF11"),
    MINERAL("%", "\uD83D\uDC8E"),
    WATER("*", "\uD83C\uDF0A"),
    SPACE(" ", "\uD83D\uDFE8"),
    HIDDEN("X","⬛"),
    ROVER("O", "🚙"),
    NOSYMBOL("", ""),

    BASE("B" , "\uD83C\uDFD9");

    private final String symbol;

    private final String emojiCode;

    Symbol(String symbol, String emojiCode) {
        this.symbol = symbol;
        this.emojiCode = emojiCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getEmojiCode() {
        return emojiCode;
    }
}
