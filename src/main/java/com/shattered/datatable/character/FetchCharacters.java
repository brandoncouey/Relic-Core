package com.shattered.datatable.character;

import com.shattered.account.Account;
import com.shattered.datatable.mysql.MySQLFetch;
import com.shattered.account.character.CharacterInformation;
import lombok.Getter;
import lombok.Setter;
import org.database.query.options.impl.WhereConditionOption;

import java.sql.ResultSet;

/**
 * @author JTlr Frost 8/29/2019 : 1:45 AM
 */
public class FetchCharacters implements MySQLFetch {


    /**
     * Represents the Account Id
     */
    @Getter
    @Setter
    private long accountId;

    /**
     * Represents the Document of Characters
     */
    @Getter
    @Setter
    private CharacterInformation character;

    /**
     * 
     * @param accountId
     */
    public FetchCharacters(long accountId) {
        setAccountId(accountId);
        fetch();
    }
    
    /**
     * Database name.
     */
    @Override
    public String getDatabaseName() {
        return "shatteredrelics";
    }

    /**
     * Table name.
     */
    @Override
    public String getTableName() {
        return "information";
    }

    /**
     * Conditions.
     */
    @Override
    public WhereConditionOption[] getFetchConditions() {
        return new WhereConditionOption[] { new WhereConditionOption("accountId", getAccountId())} ;
    }

    /**
     * Fetches the Characters associated with the {@link Account}
     */
    @Override
    public boolean fetch() {
        try {
            ResultSet results = getResults();
            
            //Checks if the Account has results, to prevent sending a empty packet.
            if (!hasResults())  return false;
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String location = results.getString("location");
                boolean isMale = results.getInt("gender") == 0;
                setCharacter(new CharacterInformation(id, name, location, isMale));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
