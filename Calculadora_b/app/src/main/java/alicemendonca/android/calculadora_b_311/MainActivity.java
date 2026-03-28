package alicemendonca.android.calculadora_b_311;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText display;
    boolean resultadoExibido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.tvDisplay);
        display.setText("0");

        int[] botoes = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnSoma, R.id.btnSub, R.id.btnMult, R.id.btnDiv,
                R.id.btnIgual, R.id.btnC, R.id.btnVolta, R.id.btnPonto
        };

        for (int id : botoes) {
            findViewById(id).setOnClickListener(clickListener);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button btn = (Button) v;
            String tecla = btn.getText().toString();
            String atual = display.getText().toString();

            // se tiver ERROR no visor, qualquer tecla faz aparecer 0
            if (atual.equals("ERROR")) {
                display.setText("0");
                atual = "0";
                resultadoExibido = false;
            }

            // ao digitar numero depois de resultado, limpa a memoria
            if (resultadoExibido && !isOperador(tecla) && !tecla.equals("=") && !tecla.equals("<<")) {
                display.setText("0");
                atual = "0";
                resultadoExibido = false;
            }

            switch (tecla) {
                case "C":
                    limparMemoria();
                    break;
                case "<<":
                    tratarBackspace(atual);
                    break;
                case ".":
                    tratarPonto(atual);
                    break;
                case "+": case "-": case "x": case "/":
                    tratarOperador(atual, tecla);
                    break;
                case "=":
                    calcular();
                    break;
                default: // números
                    tratarNumero(atual, tecla);
                    break;
            }
        }
    };

    private boolean isOperador(String s) {
        return s.equals("+") || s.equals("-") || s.equals("x") || s.equals("/");
    }

    private void limparMemoria() {
        display.setText("0");
        resultadoExibido = false;
    }

    private void tratarBackspace(String atual) {
        if (resultadoExibido) return; // não muda resultado final

        if (atual.length() > 1) {
            // apagar operador
            if (atual.endsWith(" ")) {
                display.setText(atual.substring(0, atual.length() - 3));
            // apagar número
            } else {
                display.setText(atual.substring(0, atual.length() - 1));
            }
        } else {
            display.setText("0");
        }
    }

    private void tratarPonto(String atual) {
        if (atual.isEmpty()) return;

        char ultimoChar = atual.charAt(atual.length() - 1);

        // último character tem que ser dígito
        if (Character.isDigit(ultimoChar)) {
            String ultimoNumero = getUltimoNumero(atual);
            // número no visor não pode já ter ponto (evitar ex.: 1.1.1)
            if (!ultimoNumero.contains(".")) {
                display.append(".");
            }
        // ponto depois de operador ou resultado vai pro padrão de começar o decimal com 0
        } else if (resultadoExibido || atual.endsWith(" ")) {
            display.append("0.");
            resultadoExibido = false;
        }
    }

    private void tratarOperador(String atual, String op) {
        if (resultadoExibido) resultadoExibido = false;

        // tem um operador no final e o usuario clica em outro operador,
        // então troca o último pelo novo
        if (atual.endsWith(" ")) {
            String operandoAnterior = atual.substring(0, atual.length() - 3);
            display.setText(operandoAnterior + " " + op + " ");
        }
        // se não tem operador na expressão ainda
        else if (!temOperador(atual) && !atual.endsWith(".")) {
            display.append(" " + op + " ");
        }
    }

    private void tratarNumero(String atual, String num) {
        // numero sem "0 à esquerda"
        if (atual.equals("0")) {
            display.setText(num);
        } else {
            display.append(num);
        }
    }

    private void calcular() {
        try {
            String expressao = display.getText().toString();
            String[] partes = expressao.split(" ");

            // verifica se o cálculo tem 2 operandos e 1 operador
            if (partes.length != 3) {
                display.setText("ERROR");
                resultadoExibido = true;
                return;
            }

            double v1 = Double.parseDouble(partes[0]);
            double v2 = Double.parseDouble(partes[2]);
            String op = partes[1];
            double res;

            switch (op) {
                case "+": res = v1 + v2; break;
                case "-": res = v1 - v2; break;
                case "x": res = v1 * v2; break;
                case "/":
                    // impedir divisão por 0
                    if (v2 == 0) {
                        display.setText("ERROR");
                        resultadoExibido = true;
                        return;
                    }
                    res = v1 / v2;
                    break;
                default:
                    display.setText("ERROR");
                    resultadoExibido = true;
                    return;
            }

            display.setText(removerZero(res));
            resultadoExibido = true;

        } catch (Exception e) {
            display.setText("ERROR");
            resultadoExibido = true;
        }
    }

    private boolean temOperador(String texto) {
        return texto.contains(" + ") || texto.contains(" - ") ||
               texto.contains(" x ") || texto.contains(" / ");
    }

    private String getUltimoNumero(String texto) {
        String t = texto.trim();
        int lastSpace = t.lastIndexOf(" ");
        if (lastSpace == -1) return t;
        return t.substring(lastSpace + 1);
    }

    // não mostra por ex. 5.0, apenas 5
    private String removerZero(double valor) {
        if (valor == (int) valor) {
            return String.valueOf((int) valor);
        }
        return String.valueOf(valor);
    }
}