package com.example.pdfgen;


import androidx.appcompat.app.AppCompatActivity;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.pdf.PdfDocument;

import android.os.Bundle;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;





import com.github.barteksc.pdfviewer.PDFView;// library used for viewing pdf in app


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    private int current_page=0; //default page number to be page 1. of any pdf
    Button btn2;
    TextView eng;
    TextView eng_text;
    TextView hindi;
    TextView hin_text;
    TextView telegu;
    TextView telegu_text;
    TextView bangla;
    TextView bangla_text;
    TextView punjabi;
    TextView punjabi_text;
    PDFView pdfView;
    int pageHeight = 1300;//height & width of PDF page
    int pageWidth = 792;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn1 = findViewById(R.id.button1);

        eng = findViewById(R.id.eng);
        eng_text = findViewById(R.id.eng_text);
        hindi = findViewById(R.id.hindi);
        hin_text = findViewById(R.id.hindi_txt);
        telegu = findViewById(R.id.telegu);
        telegu_text = findViewById(R.id.telegu_text);
        bangla = findViewById(R.id.bengali);
        bangla_text = findViewById(R.id.bengali_text);
        punjabi = findViewById(R.id.punjabi);
        punjabi_text = findViewById(R.id.punjabi_text);
        pdfView=findViewById(R.id.pdfView);





       btn1.setOnClickListener(new View.OnClickListener() {

           @Override
            public void onClick(View v) {




                generatePDF();
               File path  =  new File(getApplicationContext().getFilesDir() + "/abc.pdf");//getting pdf from internal memory

               pdfView.fromFile(path).load();//displaying pdf


            }
        });


    }


    private void generatePDF() {

        PdfDocument Document = new PdfDocument();//creating pdf
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page Page = Document.startPage(pageInfo);
        Paint title=new TextPaint();
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = Page.getCanvas();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((int) (8 * 2));
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        title.setColor(Color.BLACK);
        title.setTextSize((int) (10 * 2));
        title.setFakeBoldText( true );

        canvas.drawLine(10, 10, 782, 10, paint);//page borders using line function as rectangle function fills the inner contents
        canvas.drawLine(10, 10, 10, 1290, paint);
        canvas.drawLine(10, 1290, 782, 1290, paint);
        canvas.drawLine(782, 10, 782, 1290, paint);

        canvas.drawLine(10, 250, 782, 250, paint);
        canvas.drawLine(10, 415, 782, 415, paint);
        canvas.drawLine(10, 580, 782, 580, paint);
        canvas.drawLine(10, 745, 782, 745, paint);
        canvas.drawLine(10, 920, 782, 920, paint);
        int textWidth = canvas.getWidth() - (int) (16 * 2);// so that text remains within bounds of width

        String Hin = hindi.getText().toString();
        String Telegu = telegu.getText().toString();
        String Bangla = bangla.getText().toString();
        String Punjabi = punjabi.getText().toString();
        String Eng = eng.getText().toString();

        StaticLayout EngText = new StaticLayout(eng_text.getText().toString()// using static layout to keep text in the page and no leak out
                , paint, pageWidth-80, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        StaticLayout HinText  = new StaticLayout(hin_text.getText().toString()
                , paint, pageWidth-80, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        StaticLayout PunjabiText  = new StaticLayout(punjabi_text.getText().toString()
                , paint, pageWidth-80, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        StaticLayout BanglaText  = new StaticLayout(bangla_text.getText().toString()
                , paint, pageWidth-80, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        StaticLayout TeleguText  = new StaticLayout(telegu_text.getText().toString()
                , paint, pageWidth-80, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        canvas.save();
        canvas.drawText(Eng,50,140,title);
        canvas.translate(50, 150);
        EngText.draw(canvas);

        canvas.drawText(Hin,0,140,title);
        canvas.translate(0, 150);
        HinText.draw(canvas);

        canvas.drawText(Telegu,0,150,title);
        canvas.translate(0, 160);
        TeleguText.draw(canvas);

        canvas.drawText(Bangla,0,150,title);
        canvas.translate(0, 160);
        BanglaText.draw(canvas);

        canvas.drawText(Punjabi,0,170,title);
        canvas.translate(0, 180);
        PunjabiText.draw(canvas);

        Document.finishPage(Page);
        File file = new File(getApplicationContext().getFilesDir(),"/abc.pdf");//writing in internal storage
        try {
            Document.writeTo(new FileOutputStream(file));

            Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();

        }
        Document.close();
    }







}
