package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tvDate, tvBMI, tvOutcome;

    double weight;
    double height;
    double bmi;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonResetData);
        tvDate = findViewById(R.id.textViewDateDisplay);
        tvBMI = findViewById(R.id.textViewBMIDisplay);
        tvOutcome = findViewById(R.id.textViewOutcome);


        // Calculate Button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if( etWeight.getText().toString().isEmpty()|| etHeight.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Fields cannot be empty!", Toast.LENGTH_LONG).show();
                else {

                    // Calculate BMI using the formula and inputs from user; Display the results
                    weight = Double.parseDouble(etWeight.getText().toString());
                    height = Double.parseDouble(etHeight.getText().toString());

                    bmi = weight / (height * height);

                    tvBMI.setText(String.format("%.3f", bmi));
                    tvDate.setText(dateTime());

                    if(bmi < 18.5)
                        tvOutcome.setText("You are underweight");
                    else if(bmi <= 24.9)
                        tvOutcome.setText("Your BMI is normal");
                    else if(bmi <= 29.9)
                        tvOutcome.setText("You are overweight");
                    else
                        tvOutcome.setText("You are obese");

                    // Clear the editText after button is clicked
                    etWeight.setText("");
                    etHeight.setText("");

                    save();

                }


            }
        });

        // Reset Button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                etHeight.setText("");
                etWeight.setText("");

                tvDate.setText("");
                tvBMI.setText("");
                tvOutcome.setText("");

                save();

            }
        });

    }

    public String dateTime()
    {
        // Obtain Current date and time
        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String dateTime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        return dateTime;
    }

    public void save()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putString("Date", tvDate.getText().toString());
        prefsEdit.putString("BMI", tvBMI.getText().toString());
        prefsEdit.putString("Outcome", tvOutcome.getText().toString());

        prefsEdit.commit();
    }


}
