package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {
    private EditText editNomeCadastro, editEmailCadastro, editSenhaCadastro;
    private Button buttonCadastrar;
    private FirebaseAuth autenticacaoFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        carregarComponentes();
    }

    public void carregarComponentes() {
        editNomeCadastro = findViewById(R.id.editNomeCadastro);
        editEmailCadastro = findViewById(R.id.editEmailCadastro);
        editSenhaCadastro = findViewById(R.id.editSenhaCadastro);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

    }

    public void verificarCampos() {
        String getTextNome = editNomeCadastro.getText().toString();
        String getTextEmail = editEmailCadastro.getText().toString();
        String getTextSenha = editSenhaCadastro.getText().toString();

        if (!getTextNome.isEmpty()) {
            if (!getTextEmail.isEmpty()) {
                if (!getTextSenha.isEmpty()) {
                    cadastrarUsuario();
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha o campo Senha", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Preencha o campo E-mail", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Preencha o campo Nome", Toast.LENGTH_LONG).show();
        }
    }

    public void cadastrarUsuario(){

    }

}

