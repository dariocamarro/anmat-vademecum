package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.thinkupstudios.anmat.vademecum.aplicacion.MiAplicacion;
import com.thinkupstudios.anmat.vademecum.providers.GenericProvider;
import com.thinkupstudios.anmat.vademecum.providers.SQLiteDBService;
import com.thinkupstudios.anmat.vademecum.providers.helper.DatabaseHelper;
import com.thinkupstudios.anmat.vademecum.providers.services.contract.IRemoteDBService;
import com.thinkupstudios.anmat.vademecum.providers.tables.MedicamentosTable;
import com.thinkupstudios.anmat.vademecum.providers.tables.PrincipiosActivosTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by FaQ on 11/04/2015.
 * Activity creada solo para splashScreen
 */
public class Splash extends Activity {
    private IRemoteDBService dbService;
    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbService = new SQLiteDBService();
        dbHelper = new DatabaseHelper(this);

        if (!dbService.isUpToDate(dbHelper.getReadableDatabase().getVersion())) {
            //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbService.getNewDataBase(), null);
            String outFileName = "/data/data/com.thinkupstudios.annmat.vademecum/databases/prueba.sqlite";
            try {
                InputStream inputStream = this.getAssets().open("prueba.sqlite");
                // Path to the just created empty db


                //Open the empty db as the output stream
                OutputStream myOutput = new FileOutputStream(outFileName);

                //transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                //Close the streams
                myOutput.flush();
                myOutput.close();
                inputStream.close();
            }catch (IOException e){

            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File(outFileName), null);
            dbHelper.onUpgrade(db, dbHelper.getReadableDatabase().getVersion(), db.getVersion());
        }

        GenericProvider genericProvider = new GenericProvider(new DatabaseHelper(this));
        ((MiAplicacion)this.getApplication()).setNombresComerciales(genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_COMERCIAL));
        ((MiAplicacion)this.getApplication()).setLaboratorios(genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_LABORATORIO));
        ((MiAplicacion)this.getApplication()).setNombresGenericos(genericProvider.getDistinctColumns(MedicamentosTable.TABLE_NAME, MedicamentosTable.COLUMN_GENERICO));
        ((MiAplicacion)this.getApplication()).setPrincipiosActivos(genericProvider.getDistinctColumns(PrincipiosActivosTable.TABLE_NAME, PrincipiosActivosTable.COLUMN_NAME));

        startActivity(new Intent(this, MainMenuActivity.class));

    }


}
