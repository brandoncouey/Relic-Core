package com.shattered.account.character;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author JTlr Frost 9/5/2019 : 9:18 PM
 */
@Data
@RequiredArgsConstructor
public class CharacterInformation {

    /**
     * Represents the DB Id.
     */
    private final int id;

    /**
     * Represents the Name of the Character
     */
    private final String name;

    /**
     * Represents the location of the Character
     */
    private final String location;

    /**
     * Represents if the Character is a Male
     */
    private final boolean isMale;

}
