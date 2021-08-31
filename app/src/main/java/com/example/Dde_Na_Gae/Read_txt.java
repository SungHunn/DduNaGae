package com.example.Dde_Na_Gae;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Read_txt extends AppCompatActivity {

    public String readTxt(int n, Context context) {

        String data = null;
        InputStream inputStream = context.getResources().openRawResource(R.raw.tour_rank);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        System.out.print(inputStream);

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(), "MS949");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] word = data.split("\t");
        String result = word[1+4*(n-1)];

        return result;
    }

}