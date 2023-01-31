package com.example.calculator;

import static java.util.Collections.replaceAll;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import java.lang.String;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity  extends AppCompatActivity {

    List<String> stringList = new ArrayList<>();
    int decimalPointCount = 0;
    int ClickedFormulaOnce = 0;
    int formulaContinuousClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculation(TextView txt) {
        // TextViewの文字を全て取得して、配列に格納する。
        String txtAll = txt.getText().toString();

        // 区切り文字自身も配列に含める正規表現
        //正規表現((?<=[+×÷-])|(?=[+×÷-]))の意味は+-×÷の直前と直後で区切るという意味。
        String[] stringArray = txtAll.split("((?<=[+×÷-])|(?=[+×÷-]))", 0);

        // 配列に格納後、TextViewの文字を全て消去する。
        txt.setText(null);
        // 配列を全てArrayListに格納する。
        for(String s:stringArray) {
            stringList.add(s);
        }
        //BigDecimalはdoubleと違い誤差が出ない。
        BigDecimal bigDecimalResultValue = new BigDecimal("0.0");
        // 掛け算割り算の処理
        for(int i = 1; i < stringList.size(); i += 2) {
            if(stringList.get(i).equals("×")) {
                bigDecimalResultValue = new BigDecimal(stringList.get(i-1)).multiply(new BigDecimal(stringList.get(i+1)));
                // 計算済みの数字と式をstringListから削除する。　例：「2*3+1」の「2*3」の部分を削除する。
                stringList.remove(i-1);
                stringList.remove(i-1);
                stringList.remove(i-1);
                // 計算結果をstringListに加える。　例：「2*3+1」を「6+1」にする。
                stringList.add(i-1, String.valueOf(bigDecimalResultValue));
                // stringListを3つ削除して1つ足したので、iから2を引く。
                i -= 2;
            } else if (stringList.get(i).equals("÷")) {
                // Bigdecimalの割り算は割り切れない数の時ArithmeticExceptionエラーが出る。そのためtrycathで囲む。
                // これをやっとくと割り切れる時は小数点以下に無駄に00000がつかない。
                // 割り切れない時だけ小数点第11位を四捨五入する。
                try {
                    bigDecimalResultValue = new BigDecimal(stringList.get(i-1)).divide(new BigDecimal(stringList.get(i+1)));
                } catch (ArithmeticException e) {
                    bigDecimalResultValue = new BigDecimal(stringList.get(i-1)).divide(new BigDecimal(stringList.get(i+1)), 10, RoundingMode.HALF_UP);//四捨五入。小数点第10位まで表示。
                }
                stringList.remove(i-1);
                stringList.remove(i-1);
                stringList.remove(i-1);
                stringList.add(i-1, String.valueOf(bigDecimalResultValue));
                i -= 2;
            }
        }
        // 足し算引き算の処理
        // 掛け算割り算はすでに処理済みなので、単純に前から順に足し算引き算をしていくだけ。
        while(stringList.size() > 1) {
            if(stringList.get(1).equals("+")) {
                bigDecimalResultValue = new BigDecimal(stringList.get(0)).add(new BigDecimal(stringList.get(2)));
                stringList.remove(0);
                stringList.remove(0);
                stringList.remove(0);
                stringList.add(0, String.valueOf(bigDecimalResultValue));
            } else if (stringList.get(1).equals("-")) {
                bigDecimalResultValue = new BigDecimal(stringList.get(0)).subtract(new BigDecimal(stringList.get(2)));
                stringList.remove(0);
                stringList.remove(0);
                stringList.remove(0);
                stringList.add(0, String.valueOf(bigDecimalResultValue));
            }
        }
        txt.setText(stringList.toString().replace("[","").replace("]",""));
        // 結果表示後にリストをクリア。
        stringList.clear();
    }
    public void btnCurrent_onClick(View view) {
        TextView textView = findViewById(R.id.textView);
        switch (view.getId()) {
            case R.id.zero:
                textView.append("0");
                formulaContinuousClick = 0;
                formulaContinuousClick = 0;
                break;
            case R.id.one:
                textView.append("1");
                formulaContinuousClick = 0;
                break;
            case R.id.two:
                textView.append("2");
                formulaContinuousClick = 0;
                break;
            case R.id.three:
                textView.append("3");
                formulaContinuousClick = 0;
                break;
            case R.id.four:
                textView.append("4");
                formulaContinuousClick = 0;
                break;
            case R.id.five:
                textView.append("5");
                formulaContinuousClick = 0;
                break;
            case R.id.six:
                textView.append("6");
                formulaContinuousClick = 0;
                break;
            case R.id.seven:
                textView.append("7");
                formulaContinuousClick = 0;
                break;
            case R.id.eight:
                textView.append("8");
                formulaContinuousClick = 0;
                break;
            case R.id.nine:
                textView.append("9");
                formulaContinuousClick = 0;
                break;
            case R.id.point:
                if (decimalPointCount == 0) {
                    textView.append(".");
                    decimalPointCount = 1;
                }
                break;
            case R.id.plus:
                formulaContinuousClick++;
                if(formulaContinuousClick == 1 && !(textView.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    textView.append("+");
                }
                decimalPointCount = 0;
                break;
            case R.id.minus:
                formulaContinuousClick++;
                if(formulaContinuousClick == 1 && !(textView.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    textView.append("-");
                }
                decimalPointCount = 0;
                break;
            case R.id.by:
                formulaContinuousClick++;
                if(formulaContinuousClick == 1 && !(textView.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    textView.append("×");
                }
                decimalPointCount = 0;
                break;
            case R.id.div:
                formulaContinuousClick++;
                if(formulaContinuousClick == 1 && !(textView.getText().toString().equals(""))) {
                    ClickedFormulaOnce = 1;
                    textView.append("÷");
                }
                decimalPointCount = 0;
                break;
            case R.id.equal:
                if (ClickedFormulaOnce == 1) {
                    formulaContinuousClick++;
                }
                if (ClickedFormulaOnce == 1 && formulaContinuousClick == 1) {
                    formulaContinuousClick = 0;
                    ClickedFormulaOnce = 0;
                    decimalPointCount = 1;
                    if (textView.getText().toString().length() > 56) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "文字数が上限の56文字を超えています", Toast.LENGTH_SHORT).show();
                    } else {
                        calculation(textView);
                    }
                }
                break;
            case R.id.ac:
                decimalPointCount = 0;
                ClickedFormulaOnce = 0;
                formulaContinuousClick = 0;
                stringList.clear();
                textView.setText(null);
                break;

        }
    }
}













