package com.example.parkmobile.Transaction;

import org.arkecosystem.crypto.networks.INetwork;

public class ParkNet implements INetwork {
    @Override
    public int version() {
        return 55;
    }

    @Override
    public int wif() {
        return 42;
    }

    @Override
    public String epoch() {
        return "2019-05-28 11:11:38.743";
    }
}
