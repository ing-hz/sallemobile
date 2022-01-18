package com.example.occupationdessalles;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.occupationdessalles.beans.Creneau;
import com.example.occupationdessalles.beans.Occupation;
import com.example.occupationdessalles.beans.Salle;
import com.example.occupationdessalles.network.RetrofitInstance;
import com.example.occupationdessalles.services.DataService;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private Salle salle;
    private Creneau creneau;
    private Occupation occupation;
    TextView nom_bloc;
    TextView nom_salle;
    TextView type_salle;
    TextView nom_creneau;
    Button confirmer;
    private String creneau_id;
    DataService service ;
    Date date;
    DateFormat format;
    String a;
    String time;
    double d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nom_bloc=findViewById(R.id.bloc);
        nom_salle=findViewById(R.id.salle);
        type_salle=findViewById(R.id.type);
        nom_creneau=findViewById(R.id.creneau);
        confirmer=findViewById(R.id.confirmer);
        confirmer.setEnabled(false);
        service = RetrofitInstance.getInstance().create(DataService.class);
        if(checkPermission()){
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions();
        }
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        //////////////////////
                        date = new Date();
                        format = new SimpleDateFormat("HH:mm");
                        time=format.format(date);

                        if(time.split(":")[1].split("")[0].equals("0")){
                            a=Integer.parseInt(time.split(":")[0])+".0"+Integer.parseInt(time.split(":")[1]);
                        }else{
                            a=Integer.parseInt(time.split(":")[0])+"."+Integer.parseInt(time.split(":")[1]);
                        }

                        d=Double.parseDouble(a);
                        //Toast.makeText(getApplicationContext(), ""+d, Toast.LENGTH_SHORT).show();
                        //////////////////////
                        if(d>=8.3 && d<=10.2){
                            creneau_id="61bcc5c9306411083874e610";
                            getSalle(result);
                            getCreneau(creneau_id);
                        }else if(d>=10.3 && d<=12.2){
                            creneau_id="61bcc655425d871cd4d4dc5f";
                            getSalle(result);
                            getCreneau(creneau_id);
                        }else if(d>=13.3 && d<=15.2){
                            creneau_id="61bcc66b425d871cd4d4dc61";
                            getSalle(result);
                            getCreneau(creneau_id);
                        }else if(d>=15.3 && d<=17.2){
                            creneau_id="61bdba91d3b1dd0494e58d54";
                            getSalle(result);
                            getCreneau(creneau_id);
                        }else{
                            Toast.makeText(getApplicationContext(), "Vous ne pouvez pas réserver une salle dans ce créneau", Toast.LENGTH_SHORT).show();
                            nom_bloc.setText("");
                            nom_salle.setText("");
                            type_salle.setText("");
                            nom_creneau.setText("");
                            confirmer.setEnabled(false);
                        }
                        //////////////////////

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                occupation =new Occupation(salle.getId(), creneau_id);
                createOccupation(occupation);
                nom_bloc.setText("");
                nom_salle.setText("");
                type_salle.setText("");
                nom_creneau.setText("");
                confirmer.setEnabled(false);
            }
        });
    }
    public void getSalle(@NonNull final Result r){
        Call<Salle> request=service.getSalle(r.getText()+"");
        request.enqueue(new Callback<Salle>() {
            @Override
            public void onResponse(Call<Salle> call, Response<Salle> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Code : "+response.code(), Toast.LENGTH_SHORT).show();
                    return ;
                }
                salle =response.body();
                nom_bloc.setText(salle.getBloc().getNom());
                nom_salle.setText(salle.getNom());
                type_salle.setText(salle.getType());
            }

            @Override
            public void onFailure(Call<Salle> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getCreneau(String c){
        Call<Creneau> request2=service.getCreneau(c);
        request2.enqueue(new Callback<Creneau>() {
            @Override
            public void onResponse(Call<Creneau> call, Response<Creneau> response) {
                creneau=response.body();
                nom_creneau.setText(creneau.getHeure_debut()+"-"+creneau.getHeure_fin());
                confirmer.setEnabled(true);
            }
            @Override
            public void onFailure(Call<Creneau> call, Throwable t) {
            }
        });
    }
    public void createOccupation(Occupation occupation){
        Call<Occupation> request3 =service.createOccupation(occupation);
        request3.enqueue(new Callback<Occupation>() {
            @Override
            public void onResponse(Call<Occupation> call, Response<Occupation> response) {
                Occupation o=response.body();
                if(o.getSalle()==null){
                    Toast.makeText(getApplicationContext(), "La salle est déjà occupée !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Occupation> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
    private boolean checkPermission(){
        int camera_permission= ContextCompat.checkSelfPermission(this,CAMERA);
        return camera_permission== PackageManager.PERMISSION_GRANTED;
    }
    public void requestPermissions(){
        int PERMISSION_CODE=200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
        }
    }
}