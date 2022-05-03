package com.compulynx.accountmanegement.Utils;

import java.util.Random;

public class GenerateRandomString {

    // Generate random id, for example 283952-V8M32
    private char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public GenerateRandomString(){

    }

    public String generateTransactionId(){
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "T");
   for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

    return sb.toString();
    }

    public String userPin(){
        Random rnd = new Random();
        Integer n = 100000 + rnd.nextInt(900000);
        return n.toString();
    }


    public String generateAccountId(String userData){
        char [] userDataArray= userData.toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "AC");
        for (int i = 0; i < 5; i++)
            sb.append(userDataArray[rnd.nextInt(userDataArray.length)]);

        return sb.toString();
    }

}
