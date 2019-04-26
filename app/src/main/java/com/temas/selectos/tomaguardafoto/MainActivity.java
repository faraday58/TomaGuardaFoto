package com.temas.selectos.tomaguardafoto;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    ImageButton imgbfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgbfoto= findViewById(R.id.imgbFoto);
    }

    public void onClick(View v)
    {
        Intent intcamara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intcamara,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapImagen=(Bitmap)data.getExtras().get("data");

        imgbfoto.setImageBitmap(bitmapImagen);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        //Colocar ruta de la imagen
        File path= Environment.getExternalStorageDirectory();
        //Creando Folder
        File dir = new File(path+"/imagenes/");
        dir.mkdirs();

        File archivo= new File(dir,"miimagen.jpg");

        OutputStream out = null;
        try {
            out = new FileOutputStream(archivo);
            bitmapImagen.compress(Bitmap.CompressFormat.PNG,85,out);
            out.flush();
            out.close();
            Toast.makeText(this,"Foto Guardada",Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException error)
        {
            Toast.makeText(this,"No se pudo salvar el archivo",Toast.LENGTH_LONG).show();
            Log.d("Error Archivo",error.toString());

        }
        catch (IOException error)
        {
            Toast.makeText(this,"No se pudo salvar el archivo",Toast.LENGTH_LONG).show();
            Log.d("Error Archivo",error.toString());

        }

    }
}
