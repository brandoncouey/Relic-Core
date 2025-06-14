package com.shattered.account;

import com.shattered.networking.NetworkBootstrap;
import com.shattered.networking.proto.PacketOuterClass;
import com.shattered.networking.proto.Proxy;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author JTlr Frost 7/20/18 : 5:44 PM
 */
@Data
public class AccountInformation {

    /**
     * Represents the Permission Level of the Account
     */
    public enum AccountLevel {

        /**
         * Represents the Normal Account Level
         * @permission
         */
        NORMAL,

        /**
         * Represents the Tech Account Level
         * @permission
         */
        TECH,

        /**
         * Represents the Master Account Level
         * @permission
         */
        MASTER

        ;

        /**
         * Ensures and checks for the correct zoneId, to prevent Array Index Out of Bounds Exception
         *
         * @param level
         * @return
         */
        public static AccountLevel forId(int level) {
            switch (level) {
                case 3:
                    return AccountLevel.NORMAL;
                case 2:
                    return AccountLevel.TECH;
                case 1:
                    return AccountLevel.MASTER;
            }
            return AccountLevel.NORMAL;
        }

    }

    /**
     * Represents the Account's Current Online Status
     */
    public enum OnlineStatus {

        /**
         * Represents the Offline Status
         */
        OFFLINE,

        /**
         * Represents the Realm Status
         */
        REALM,

        /**
         * Represents the World Status
         */
        WORLD,

    }

    /**
     * Represents the Account Id
     */
    private int accountId;

    /**
     * Represents the Connection Uuid
     */
    private String connectionUuid;

    /**
     * Represents the Account Name
     */
    private String accountName;

    /**
     * Represents the Email
     */
    private String email;

    /**
     * Represents the Account Password
     */
    private String password;

    /**
     * Represents the Account Level
     */
    private AccountLevel accountLevel = AccountLevel.NORMAL;

    /**
     * Represents the Account Online Status.
     */
    private OnlineStatus onlineStatus = OnlineStatus.OFFLINE;

    /**
     * @param accountId
     * @param accountName
     * @param connectionUuid
     * @param password
     */
    public AccountInformation(int accountId, String accountName, String connectionUuid, String password) {
        setAccountId(accountId);
        setAccountName(accountName);
        setConnectionUuid(connectionUuid);
        setPassword(password);
    }

    /**
     *
     * @param accountName
     * @param connectionUuid
     */
    public AccountInformation(String accountName, String connectionUuid, String email, String password, AccountLevel accountLevel) {
        setAccountName(accountName);
        setConnectionUuid(connectionUuid);
        setEmail(email);
        setPassword(password);
        setAccountLevel(accountLevel);
    }

    /**
     * Transmits the Channel to the Proxy
     * @param channel
     */
    public void transmit(Channel channel) {
        NetworkBootstrap.sendPacket(channel, PacketOuterClass.Opcode.P_TransmitAccount, Proxy.TransmitAccount.newBuilder().setAccountId(getAccountId()).setAccountName(getAccountName()).setEmail(getEmail())
        .setPassword(getPassword()).setAccountLevel(getAccountLevel() == null ? AccountLevel.NORMAL.ordinal() : getAccountLevel().ordinal()).build());
    }

}
