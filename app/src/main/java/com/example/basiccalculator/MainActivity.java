package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Follow theo coding conventions của google: https://source.android.com/setup/contribute/code-style
     */

    /**
     * Để access-modifier là private nếu không có nhu cầu truy cập từ class khác
     * Đặt tên biến đầy đủ, có thể đọc được: operand1, operand2
     */

    private static final int STATE_OPERAND_FIRST = 1;
    private static final int STATE_OPERAND_SECOND = 2;
    private int state;
    private int operand1, operand2;
    private int operator;
    private TextView textResult;

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
        findViewById(R.id.buttonC).setOnClickListener(this);
        findViewById(R.id.btnCE).setOnClickListener(this);

        state = STATE_OPERAND_FIRST;
        operand1 = operand2 = 0;
        operator = Operator.INIT;
        textResult.setText(String.valueOf(0));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            /**
             * Không để hard code như này nhé, cần để vào hằng số
             * Có thể tạo 1 class khác lưu các loại operators, và 1 class lưu các loại state
             */
            case R.id.btnadd:
                selectOperator(Operator.ADD);
                break;
            case R.id.btnsub:
                selectOperator(Operator.SUB);
                break;
            case R.id.btnmul:
                selectOperator(Operator.MUL);
                break;
            case R.id.btndiv:
                selectOperator(Operator.DIV);
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
            case R.id.buttonC:
                state = STATE_OPERAND_FIRST;
                operand1 = operand2 = 0;
                operator = Operator.INIT;
                textResult.setText(String.valueOf(0));
                break;
            default:
                //Sử dụng parse Int thay vì valueOf để tránh tạo thêm các biến rác trong quá trình sinh byte code
                addDigit(Integer.parseInt(((Button)view).getText().toString()));
        }
    }

    private void selectOperator(int _op) {
        operator = _op;
        state = STATE_OPERAND_SECOND;
    }

    private void addDigit(int digit) {
        if (state == STATE_OPERAND_FIRST) {
            if (operand1 < 0)
                operand1 = operand1 * 10 - digit;
            else
                operand1 = operand1 * 10 + digit;
            textResult.setText(String.valueOf(operand1));
        }
        else {
            if (operand2 < 0)
                operand2 = operand2 * 10 - digit;
            else
                operand2 = operand2 * 10 + digit;
            textResult.setText(String.valueOf(operand2));
        }
    }

    private void calculateResult() {
        /**
         * Nếu các khối lệnh if else có 3 nhánh trở lên, dùng switch-case để khiến code dễ đọc hơn
         *          switch (operator){
         *             case Operator.ADD: result = operand1 + operand2; break;
         *         }
         */
        int result = 0;
        switch (operator) {
            case Operator.ADD:
                result = operand1 + operand2;
                break;
            case Operator.SUB:
                result = operand1 - operand2;
                break;
            case Operator.MUL:
                result = operand1 * operand2;
                break;
            case Operator.DIV:
                if (operand2 != 0)
                    result = operand1 / operand2;
                break;
        }

        /**
         * Những đoạn text hiển thị cho người dùng nhìn thấy thì nên đặt trong strings.xml để tiện cho việc chuyển đổi ngôn ngữ
         * String finalResult = (operator == 4 && operand2 == 0) ? getString(R.string.msg_error) : result.toString();
         */
        if (operator == Operator.DIV && operand2 == 0)
            textResult.setText(R.string.msg_error);
        else
            textResult.setText(String.valueOf(result));

        // Quay lai trang thai 1
        /**
         * Đặt tên rõ ràng state để khi code không cần phải comment mà vẫn hiểu được code
         * state = States.SOME_THING;
         */
        state = STATE_OPERAND_FIRST;
        operand1 = 0;
        operand2 = 0;
        operator = Operator.INIT;
    }

    private void removeDigit() {
        if (state == STATE_OPERAND_FIRST) {
            operand1 = operand1 / 10;
            textResult.setText(String.valueOf(operand1));
        }
        else {
            operand2 = operand2 / 10;
            textResult.setText(String.valueOf(operand2));
        }
    }

    private void clearCurrentOperand() {
        if (state == STATE_OPERAND_FIRST) {
            operand1 = 0;
            textResult.setText(String.valueOf(operand1));
        }
        else {
            operand2 = 0;
            textResult.setText(String.valueOf(operand2));
        }
    }
}
