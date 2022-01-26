package com.driveup.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.driveup.erp.adapter.LignCommandAdapter;
import com.driveup.erp.model.LignCommand;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandDetailActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private RecyclerView mRecycler;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    List<LignCommand> commandLignList;
    LignCommandAdapter commandLignAdapter;

    private String code, customer, order, delivery, status, total;
    private TextView code_cmd, customer_cmd, order_cmd, delivery_cmd, status_cmd, total_cost;

    ImageButton btn_printPDF;
    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;
    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;
    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_detail);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        String key = getIntent().getExtras().getString("command_id");
        //Toast.makeText(this, key, Toast.LENGTH_SHORT).show();

        code = getIntent().getExtras().getString("code");
        customer = getIntent().getExtras().getString("customer");
        delivery = getIntent().getExtras().getString("delivery");
        status = getIntent().getExtras().getString("status");
        order = getIntent().getExtras().getString("order");

        // Récupération des variables
        code_cmd = findViewById(R.id.code_content);
        customer_cmd = findViewById(R.id.customer_content);
        order_cmd = findViewById(R.id.order_date);
        delivery_cmd = findViewById(R.id.delivery_content);
        status_cmd = findViewById(R.id.status_content);
        total_cost = findViewById(R.id.total_bill_content);

        // Remplissage par des valeurs
        code_cmd.setText(code);
        customer_cmd.setText(customer);
        delivery_cmd.setText(delivery);
        status_cmd.setText(status);
        order_cmd.setText(order);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("commands").child(key).child("ligns_cmd");

        mRecycler = findViewById(R.id.recycler_view_lign);
        mRecycler.setLayoutManager(new LinearLayoutManager(CommandDetailActivity.this));
        mRecycler.setHasFixedSize(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CommandDetailActivity.this, total, Toast.LENGTH_SHORT).show();
                total_cost.setText(total);
            }
        }, 5000);

        btn_printPDF = findViewById(R.id.button_print);
        btn_printPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CommandDetailActivity.this)
                        .setTitle("")
                        .setMessage("Voulez-vous imprimer ?")
                        .setNegativeButton(R.string.confirm_no, null)
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (checkSelfPermission(
                                     Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                     requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE},1);
                                }

                                printPDF(customer, code, order, delivery, status, total);
                            }
                        }).create().show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandLignList = new ArrayList<>();
                int pt_total = 0;
                for (DataSnapshot commandSnap: snapshot.getChildren()){
                    LignCommand lignCommand = commandSnap.getValue(LignCommand.class);
                    pt_total = pt_total + lignCommand.getTotal_price_cmd();
                    commandLignList.add(lignCommand);
                }

                total = "" + pt_total + " FC";
                //Toast.makeText(CommandDetailActivity.this, total, Toast.LENGTH_SHORT).show();
                //List<Command> mList = new ArrayList<>();
                //Collections.reverse(commandLignList);

                commandLignAdapter = new LignCommandAdapter(CommandDetailActivity.this, commandLignList);
                mRecycler.setAdapter(commandLignAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void printPDF (String customer, String code, String order, String delivery,
                          String status, String total){
        PdfDocument mPdfDocument = new PdfDocument();
        Paint mPaint = new Paint();

        PdfDocument.PageInfo mPageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page mPage = mPdfDocument.startPage(mPageInfo);
        Canvas canvas = mPage.getCanvas();
        /*Canvas canvas = mPage.getCanvas();
        canvas.drawText("Drive Up", 40, 50, mPaint);
        mPdfDocument.finishPage(mPage);
        */

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(12.0f);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("Drive Up", mPageInfo.getPageWidth()/2, 30, mPaint);

        mPaint.setTextSize(6.0f);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("Votre entreprise au mieux", mPageInfo.getPageWidth()/2, 40, mPaint);

        mPaint.setTextSize(14.0f);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText(customer, mPageInfo.getPageWidth()/2, 60, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("Commande n°" + code, mPageInfo.getPageWidth()/2, 70, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setColor(Color.rgb(0,0,0));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(order, mPageInfo.getPageWidth()/2, 80, mPaint);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("Livraison",10, 95, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText(": " + delivery,50, 95, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("Statuts",10, 105, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText(": " + status,50, 105, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("Total à payer",100, 95, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText(total,100, 105, mPaint);

        // Title of the table
        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("N°",10, 120, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("Libellés",20, 120, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("Qté",110, 120, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("PU",145, 120, mPaint);

        mPaint.setTextSize(7.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(0,0,0));
        canvas.drawText("PT",175, 120, mPaint);

        int lignSpace = 10;
        for (LignCommand lignCommand : commandLignList){
            mPaint.setTextSize(7.0f);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.rgb(0,0,0));
            canvas.drawText("" + lignCommand.getId_lign_cmd(),10, 120 + lignSpace, mPaint);

            mPaint.setTextSize(7.0f);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.rgb(0,0,0));
            canvas.drawText(lignCommand.getProduct_cmd(),20, 120  + lignSpace, mPaint);

            mPaint.setTextSize(7.0f);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.rgb(0,0,0));
            canvas.drawText("" + lignCommand.getQuantity_cmd(),110, 120  + lignSpace, mPaint);

            mPaint.setTextSize(7.0f);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.rgb(0,0,0));
            canvas.drawText("PU",145, 120 + lignSpace, mPaint);

            mPaint.setTextSize(7.0f);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.rgb(0,0,0));
            canvas.drawText("" + lignCommand.getTotal_price_cmd(),175, 120 + lignSpace, mPaint);

            lignSpace = lignSpace + 10;
        }

        mPdfDocument.finishPage(mPage);

        File mFile = new File(Environment.getExternalStorageDirectory() , "/BonDeCommand.pdf");

        try {
            mPdfDocument.writeTo(new FileOutputStream(mFile));
        } catch(IOException e){
            e.printStackTrace();
        }

        mPdfDocument.close();
    }
}