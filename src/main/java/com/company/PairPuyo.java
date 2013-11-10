package com.company;


import java.util.ArrayList;
import java.util.List;

public class PairPuyo {

    private List<Puyo> pairPuyo;

    public List<Puyo> getPairPuyo() {

        return pairPuyo;
    }
    public PairPuyo() {
        pairPuyo = new ArrayList<>();
        pairPuyo.add(new Puyo());
        pairPuyo.add(new Puyo());
    }



}
