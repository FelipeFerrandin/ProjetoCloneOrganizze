package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {
    private EditText editNomeCadastro, editEmailCadastro, editSenhaCadastro;
    private Button buttonCadastrar;
    private FirebaseAuth autenticacaoFirebase;
    private Usuario usuario;

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

    public void eventoVerificarCampos(View view){
        verificarCampos();
    }

    public void verificarCampos() {
        String getTextNome = editNomeCadastro.getText().toString();
        String getTextEmail = editEmailCadastro.getText().toString();
        String getTextSenha = editSenhaCadastro.getText().toString();
        usuario = new Usuario();

        if (!getTextNome.isEmpty()) {
            if (!getTextEmail.isEmpty()) {
                if (!getTextSenha.isEmpty()) {

                     usuario.setNome(getTextNome);
                     usuario.setEmail(getTextEmail);
                     usuario.setSenha(getTextSenha);

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
        autenticacaoFirebase = ConfiguracaoFirebase.getInstaceFirebaseAuth();
        autenticacaoFirebase.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Sucesso ao Realizar Cadastro", Toast.LENGTH_LONG).show();
                }else{
                    tratarErrosCadastro(task);
                }
            }
        });
    }

    public void tratarErrosCadastro(@NonNull Task<AuthResult> task){
        String excecao = "";
        try {
            throw task.getException();
        }catch (FirebaseAuthWeakPasswordException e) {
            excecao = "Digite uma senha mais forte";
        }catch (FirebaseAuthInvalidCredentialsException e){
            excecao = "Por favor, digite um email valido";
        }catch (FirebaseAuthUserCollisionException e){
            excecao ="Esta conta já existe";
        } catch (Exception e) {
            excecao ="Erro ao cadatra usuário: " + e.getMessage();
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_LONG).show();


    }

}

