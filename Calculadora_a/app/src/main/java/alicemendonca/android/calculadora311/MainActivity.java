package alicemendonca.android.calculadora311;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    Button btnSoma, btnSub, btnMult, btnDiv;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        resultado = findViewById(R.id.resultado);

        btnSoma = findViewById(R.id.btnSoma);
        btnSoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular("+");
            }
        });

        btnSub = findViewById(R.id.btnSub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular("-");
            }
        });

        btnMult = findViewById(R.id.btnMult);
        btnMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular("*");
            }
        });

        btnDiv = findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular("/");
            }
        });
    }

    private void calcular(String operacao) {
        try {
            String valor1Str = num1.getText().toString();
            String valor2Str = num2.getText().toString();

            if (valor1Str.isEmpty() || valor2Str.isEmpty()) {
                resultado.setText("Informe 2 valores.");
                return;
            }

            double valor1 = Double.parseDouble(valor1Str);
            double valor2 = Double.parseDouble(valor2Str);
            double res;

            switch (operacao) {
                case "+":
                    res = valor1 + valor2;
                    break;

                case "-":
                    res = valor1 - valor2;
                    break;

                case "*":
                    res = valor1 * valor2;
                    break;

                case "/":
                    if (valor2 == 0) {
                        resultado.setText("Não existe divisão por zero!");
                        return;
                    }
                    res = valor1 / valor2;
                    break;

                default:
                    resultado.setText("Operação inválida");
                    return;
            }

            resultado.setText(String.valueOf(res));

        } catch (NumberFormatException e) {
            resultado.setText("Entrada inválida!");
        }
    }
}