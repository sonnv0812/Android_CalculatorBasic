package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textResult;
    int state;
    int op1, op2;
    int op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.textResult);

        findViewById(R.id.btnnumber0).setOnClickListener(this);
        findViewById(R.id.btnnumber1).setOnClickListener(this);
        findViewById(R.id.btnnumber2).setOnClickListener(this);
        findViewById(R.id.btnnumber3).setOnClickListener(this);
        findViewById(R.id.btnnumber4).setOnClickListener(this);
        findViewById(R.id.btnnumber5).setOnClickListener(this);
        findViewById(R.id.btnnumber6).setOnClickListener(this);
        findViewById(R.id.btnnumber7).setOnClickListener(this);
        findViewById(R.id.btnnumber8).setOnClickListener(this);
        findViewById(R.id.btnnumber9).setOnClickListener(this);

        findViewById(R.id.btnadd).setOnClickListener(this);
        findViewById(R.id.btnsub).setOnClickListener(this);
        findViewById(R.id.btnmul).setOnClickListener(this);
        findViewById(R.id.btndiv).setOnClickListener(this);

        findViewById(R.id.btnequal).setOnClickListener(this);

        findViewById(R.id.btnBS).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnCE).setOnClickListener(this);

        state = 1;
        op1 = op2 = 0;
        op = 0;
        textResult.setText(String.valueOf(0));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnadd:
                selectOperator(1);
                break;
            case R.id.btnsub:
                selectOperator(2);
                break;
            case R.id.btnmul:
                selectOperator(3);
                break;
            case R.id.btndiv:
                selectOperator(4);
                break;
            case R.id.btnequal:
                calculateResult();
                break;
            case R.id.btnBS:
                removeDigit();
                break;
            case R.id.btnCE:
                clearCurrentOperand();
                break;
            case R.id.btnC:
                state = 1;
                op1 = op2 = 0;
                op = 0;
                textResult.setText(String.valueOf(0));
                break;
            default:
                addDigit(Integer.valueOf(((Button)view).getText().toString()));
        }
    }

    private void selectOperator(int _op) {
        op = _op;
        state = 2;
    }

    private void addDigit(int digit) {
        if (state == 1) {
            if (op1 < 0)
                op1 = op1 * 10 - digit;
            else
                op1 = op1 * 10 + digit;
            textResult.setText(String.valueOf(op1));
        }
        else {
            if (op2 < 0)
                op2 = op2 * 10 - digit;
            else
                op2 = op2 * 10 + digit;
            textResult.setText(String.valueOf(op2));
        }
    }

    private void calculateResult() {
        int result = 0;
        if (op == 1)
            result = op1 + op2;
        else if (op == 2)
            result = op1 - op2;
        else if (op == 3)
            result = op1 * op2;
        else {
            if (op2 != 0)
                result = op1 / op2;
        }

        if (op == 4 && op2 == 0)
            textResult.setText("ERROR");
        else
            textResult.setText(String.valueOf(result));

        // Quay lai trang thai 1
        state = 1;
        op1 = 0;
        op2 = 0;
        op = 0;
    }

    private void removeDigit() {
        if (state == 1) {
            op1 = op1 / 10;
            textResult.setText(String.valueOf(op1));
        }
        else {
            op2 = op2 / 10;
            textResult.setText(String.valueOf(op2));
        }
    }

    private void clearCurrentOperand() {
        if (state == 1) {
            op1 = 0;
            textResult.setText(String.valueOf(op1));
        }
        else {
            op2 = 0;
            textResult.setText(String.valueOf(op2));
        }
    }
}
