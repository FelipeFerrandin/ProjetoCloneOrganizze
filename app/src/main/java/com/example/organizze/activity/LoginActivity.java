package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.helper.ConfiguracaoFirebase;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmailLogin, editSenhaLogin;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        carregarComponentes();
    }

    public void carregarComponentes(){
        editEmailLogin = findViewById(R.id.editEmailLogin);
        editSenhaLogin = findViewById(R.id.editSenhaLogin);
    }

    public void eventoVerificarCampos(View view){
        verificarCampos();
    }

    public void verificarCampos(){
        String getTextEmail = editEmailLogin.getText().toString();
        String getTextSenha = editSenhaLogin.getText().toString();
            if (!getTextEmail.isEmpty()) {
                if (!getTextSenha.isEmpty()) {

                    usuario = new Usuario();
                    usuario.setEmail(getTextEmail);
                    usuario.setSenha(getTextSenha);

                    validarUsuario();
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha o campo senha", Toast.LENGTH_LONG).show();
                }
            }else{
                    Toast.makeText(getApplicationContext(), "Preencha o campo e-mail", Toast.LENGTH_LONG).show();
            }

        }

    public void validarUsuario(){
        autenticacao = ConfiguracaoFirebase.getInstaceFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                    finish();
                }else{
                    tratarErros(task);
                }
            }
        });
    }

    public void tratarErros(Task<AuthResult> task){
        String excecao = "";
        try {
            throw task.getException();
        }catch(FirebaseAuthInvalidUserException e){
            excecao = "E-mail e Senha não correspondem ao usuário cadastrado";
        }catch (FirebaseAuthInvalidCredentialsException e){
            excecao = "Por favor, digite um e-mail valido";
        }catch (Exception e) {
            excecao = "Erro ao entrar na conta" + e.getMessage();
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_LONG).show();

    }

}


