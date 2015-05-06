package com.fb.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

/**
 * Created by Jeffee Chen on 2015/4/29.
 */
public class MyPlayer {


    /**
     * @author Brandon B. Lin
     * 2013-1-25
     */
    private String filename;
    private Player player;

    public MyPlayer() {
        this.filename = "D:\\相信自己.mp3";
    }

    public void play() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(filename));
            player = new Player(buffer);
            player.play();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        MyPlayer mp3 = new MyPlayer();
        mp3.play();

    }


}
