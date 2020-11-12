package at.ac.tgm.hit.sew7.dkopcinski.rechner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Taschenrechner Klasse
 * @author Dawid Kopcinski
 * @version 07-10-2020
 */
public class MainActivity extends AppCompatActivity {
    private RadioButton rb;
    private RadioGroup rg;

    /**
     * Aktiviert Radiobuttons am beginn
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        for(int i = 0; i < rg.getChildCount();i++){
            rg.getChildAt(i).setClickable(true);
        }
    }

    /**
     * Macht alle berechnungen
     * @param view
     */
    public void berechnen(View view){
        //Eingabefelder werden geholt
        EditText et1 = (EditText) findViewById(R.id.input1);
        String in1 = et1.getText().toString();
        EditText et2 = (EditText) findViewById(R.id.input2);
        String in2 = et2.getText().toString();
        float wert1 = Float.parseFloat(in1);
        float wert2 = Float.parseFloat(in2);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        String zeichen;
        if(rg.getCheckedRadioButtonId() == -1){     //Falls kein RadioButton angeklickt ist
            return;
        }else{
            rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            zeichen = rb.getText().toString();
        }
        double res = 0.0;
        //Entsprechende Berechnung wird durchgeführt
        switch(zeichen){
            case "+":
                res = wert1 + wert2;
                break;
            case "-":
                res = wert1 - wert2;
                break;
            case "*":
                res = wert1 * wert2;
                break;
            case "/":
                res = wert1 / wert2;
                break;
        }
        TextView tv = (TextView) findViewById(R.id.textView);
        //Textfarbe bei negativen Werten wird gesetzt
        if(res < 0){
            tv.setTextColor(Color.RED);
        }else{
            tv.setTextColor(Color.BLACK);
        }
        tv.setText(""+res);
        //Wenn das Ausgabefeld gedrückt wird, wird es auf 0 gesetzt
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(getString(R.string.out));
                return false;
            }
        });
    }

    /**
     * Speichert die akutell gewählte Rechenart
     * @param view
     */
    public void speichern(View view){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        rb = findViewById(rg.getCheckedRadioButtonId());
        editor.putString(getString(R.string.rb), rb.getText().toString());
        editor.commit();

        //Benachrichtigung(Toast)
        Context context = getApplicationContext();
        CharSequence text = "Gespeichert";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Setzt die gespeicherte Rechenart
     * @param view
     */
    public void lesen(View view){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String zeichen = sharedPref.getString(getString(R.string.rb), "");
        // Es wird geschaut welcher button gesetzt werden soll
        int btn = -1;
        switch(zeichen){
            case "+":
                btn = 0;
                break;
            case "-":
                btn = 1;
                break;
            case "*":
                btn = 2;
                break;
            case "/":
                btn = 3;
                break;
            case "":
                break;
        }
        //Button wird gesetzt
        if(btn != -1) {
            rb = (RadioButton) rg.getChildAt(btn);
            rb.setChecked(true);
        }

    }
}