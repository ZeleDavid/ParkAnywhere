package com.example.parkmobile.Transaction;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.arkecosystem.client.Connection;
import org.arkecosystem.client.api.two.Two;
import org.arkecosystem.crypto.configuration.Network;
import org.arkecosystem.crypto.transactions.Transaction;
import org.arkecosystem.crypto.transactions.builder.Transfer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ParkTransaction {
    public static void CreateParkTransaction(int amount, String recipientAddress, String passphrase1, String ParkHouseId, String registerska, Double cas, String naziv, String uid) throws IOException {
        Network.set(new ParkNet());
        HashMap<String, Object> map = new HashMap<>();
        // map.put("host", "http://IP:4003/api/"); // network settings are autoc-configured from the node
        map.put("host", "http://45.77.58.205:4003/api/");////"http://167.114.29.51:4003/api/"
        map.put("API-Version", 2);
        map.put("content-type","application/json");
        Connection<Two> connection2 = new Connection(map);

        JSONObject jsObj = new JSONObject();
        try {
            jsObj.put("reg", registerska);
            jsObj.put("pId", ParkHouseId);
            jsObj.put("cas", cas);
            jsObj.put("naziv", naziv);
            jsObj.put("uid", uid);
            Log.i("JSON", jsObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("TRANSACTION", passphrase1);
        Log.i("TRANSACTION", recipientAddress);

        Transaction actual = new Transfer()
                .recipient(recipientAddress)
                .amount(amount)
                .vendorField(jsObj.toString()) //imava parkirnaHisaId, registerska, casParkiranja, nazivParkirisca
                .sign(passphrase1)
                .transaction;

        // creating a transaction

        System.out.println(actual.toJson());

        // adding transaction to payload, payload is an array of transactions
        ArrayList<HashMap> payload = new ArrayList<>();
        payload.add(actual.toHashMap());

        // posting transactions to the connected node as specified in the connection above
        LinkedTreeMap<String, Object> postResponse = connection2.api().transactions.create(payload);

        System.out.println(postResponse);
    }
}
