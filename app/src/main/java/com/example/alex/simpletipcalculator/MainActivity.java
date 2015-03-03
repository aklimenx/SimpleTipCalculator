package com.example.alex.simpletipcalculator;

import java.text.NumberFormat;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

// MainActivity
public class MainActivity extends Activity
{
    // currency and percent formatters
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double customPercent = 0.18; // initial custom tip percentage
    private TextView amountDisplayTextView; // shows formatted bill amount
    private TextView percentCustomTextView; // shows custom tip percentage
    private TextView tip15TextView; // shows 15% tip
    private TextView total15TextView; // shows total with 15% tip
    private TextView tipCustomTextView; // shows custom tip amount
    private TextView totalCustomTextView; // shows total with custom tip


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        amountDisplayTextView =
                (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView =
                (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView =
                (TextView) findViewById(R.id.totalCustomTextView);


        amountDisplayTextView.setText(
                currencyFormat.format(billAmount));
        updateStandard();
        updateCustom();

        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);


        SeekBar customTipSeekBar =
                (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }


    private void updateStandard()
    {

        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
    }


    private void updateCustom()
    {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTextView.setText(percentFormat.format(customPercent));

        // calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        // display custom tip and total formatted as currency
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    }


    private OnSeekBarChangeListener customSeekBarListener =
            new OnSeekBarChangeListener()
            {
                // update customPercent, then call updateCustom
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // sets customPercent to position of the SeekBar's thumb
                    customPercent = progress / 100.0;
                    updateCustom(); // update the custom tip TextViews
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                }
            };


    private TextWatcher amountEditTextWatcher = new TextWatcher()
    {

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {

            try
            {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            }
            catch (NumberFormatException e)
            {
                billAmount = 0.0;
            }

            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after)
        {
        }
    };
}
