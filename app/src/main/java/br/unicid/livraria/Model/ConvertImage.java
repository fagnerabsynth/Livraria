package br.unicid.livraria.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Fagner on 28/11/2015.
 */
public class ConvertImage {


    public ConvertImage() {
    }

    //Decodifica imagem
    public Bitmap Decodifica(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    //Codifica a imagem
    public String Codifica(Bitmap img) {
        return getEncoded64ImageStringFromBitmap(img);
    }


    private String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        //para diminuir o tamanho... ele processa e transforma em 200 x 200 pixels
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        //Retorna uma string da imagem
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }


}
