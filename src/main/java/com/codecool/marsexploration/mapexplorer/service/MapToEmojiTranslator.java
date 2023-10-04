package com.codecool.marsexploration.mapexplorer.service;

import com.codecool.marsexploration.mapexplorer.maploader.model.Map;

import java.util.HashMap;

import static com.codecool.marsexploration.mapexplorer.maploader.model.Symbol.*;

public class MapToEmojiTranslator {
    public Map translateMapToEmoji(Map map) {
        java.util.Map<String, String> emojiDictionary = new HashMap<>();
        emojiDictionary.put(MOUNTAIN.getSymbol(), MOUNTAIN.getEmojiCode());
        emojiDictionary.put(PIT.getSymbol(), PIT.getEmojiCode());
        emojiDictionary.put(MINERAL.getSymbol(), MINERAL.getEmojiCode());
        emojiDictionary.put(WATER.getSymbol(), WATER.getEmojiCode());
        emojiDictionary.put(SPACE.getSymbol(), SPACE.getEmojiCode());
        emojiDictionary.put(HIDDEN.getSymbol(), HIDDEN.getEmojiCode());
        emojiDictionary.put(ROVER.getSymbol(), ROVER.getEmojiCode());
        emojiDictionary.put(BASE.getSymbol(), BASE.getEmojiCode());

        String[][] representation = map.getRepresentation();

        for (int i = 0; i < representation.length; i++) {
            for (int j = 0; j < representation[i].length; j++) {
                representation[i][j] = emojiDictionary.get(representation[i][j]);
            }
        }
        return new Map(representation, true);
    }
}
