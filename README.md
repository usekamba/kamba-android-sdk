## Guia de Integração SDK Android

[![](https://jitpack.io/v/usekamba/kamba-android-sdk.svg)](https://jitpack.io/#usekamba/kamba-android-sdk)


Facilita aos comerciantes receber pagamentos com os meios de pagamentos das Apis da Kamba Soluções Pagamentos.

## Métodos de pagamentos atuais
1. **Pagamento via QR** com um código de pagamento (útil para comerciantes com ponto físico que desejam digitalizar os pagamentos do seu negócio ou para organizadores de eventos para vendas de ingressos, etc, o código pode ser impresso ou enviado para diversos canais sóciais). 
2. **App2App** em que seu aplicativo finaliza os pagamentos abrindo à carteira instalada no dispositivo dos clientes para concluir os pagamentos. 

> **Nota:** Em ambos você acompanha os estados do pagamento, recebe notificações por e-mail, push quando pagamentos são bem sucedidos. E pode ainda controlar todos seus pagamentos com o aplicativo para Comerciantes ou o Painel Web.

## Configuração inicial
Crie uma conta Comerciante connosco entrando em contato com nossa equipe de suporte. Você receberá uma `api_key` e um identificador de comerciante `mID`  para testar a biblioteca no modo SANDBOX. Você terá ainda acesso ao App Comerciante e ao Painel Web para adiministração e controle dos seus pagamentos e clientes.

> Nota: Esta biblioteca está em fase beta e em desenvolvimento contínuo. Se você encontrar algum erro, crie uma issue para que ela seja corrigida o mais rápido possível.

## Instalação

### Android Studio (ou Gradle)

Não há necessidade de clonar o repositório ou baixar arquivos para sua máquina - basta adicionar as linhas de código à baixo ao `build.gradle` do seu aplicativo dentro da seção` dependencies`:

**Passo 1:** Inclua o repositório JitPack em seu arquivo de construção. Adicione-o em sua raiz `build.gradle` no final dos repositórios.
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

**Passo 2:** Adicione a dependência.

```
dependencies
{
	implementation 'com.github.usekamba:kamba-android-sdk:v0.9.2'
}
```

#### Configurar credenciais
É importante configurar suas credenciais para que nossos sistemas possam autenticar suas solicitações de pagamento.
Normalmente, você fará isso na `Activity` que exibirá o método de pagamento para seu aplicativo. Este código será executado após o usuário selecionar a opção de pagamento `Pagar com o Kamba`.

```java
ClientConfig.getInstance().configure("YOUR_API_KEY","YOUR_MERCHANT_ID",ClientConfig.Environment.SANDBOX);
```

## Implementação
As ferramentas atuais permitem que você use nossos componentes de UI como **Botão de pagamento, tela Checkout, e código QR de pagamento** para exibir as informações para a finalização da compra pelo cliente, seja digitalizando o código QR ou finalizando o pagamento com a sua Carteira. 

![Screenshot](screenshots/appToapp.gif)

```xml
<com.usekamba.kambapaysdk.ui.CheckoutWidget xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/checkout"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

</com.usekamba.kambapaysdk.ui.CheckoutWidget>

```

Também criamos um botão de Pagamento para que seus clientes cliquem para aceitar pagamentos via Carteira. Clicar neste botão abrirá a Carteira do cliente para finalizar o pagamento.

```xml
<com.usekamba.kambapaysdk.ui.KambaButton
        android:id="@+id/pay"
        android:layout_width="0dp"
        android:layout_height="52dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

</com.usekamba.kambapaysdk.ui.KambaButton>
```
Você pode adicionar os dois componentes no mesmo layout para mostrar o **widget Checkout** que contém os detalhes de pagamento na mesma tela em conjunto com a opção **botão de Pagamento**, dando assim aos seus clientes à escolha para melhor opção de pagamento no seu contexto.

Crie uma instância do objeto `CheckoutRequest` que representa o item que seu cliente selecionou para comprar. Adicione o preço e a descrição conforme necessário. O objeto `CheckoutTransaction` enviará uma solicitação de forma assíncrona para os nossos sistemas que retornará um objeto `CheckoutResponse` que seu `CheckoutWidget` preencherá com as demoais informações. No retorno de chamada `onSuccess`, inicie a atividade que mostrará o `CheckoutWidget` e o `KambaButton`.
```java
public class MerchantActivity extends AppCompatActivity {
    ...
    CheckoutRequest checkoutRequest = new CheckoutRequest();
    checkoutRequest.setInitialAmount(36000.00);
    checkoutRequest.setNotes("Curso Android Para Visionários");
            CheckoutTransaction checkoutTransaction = new CheckoutTransactionBuilder().addCheckoutRequest(checkoutRequest)
                    .addClientConfig(ClientConfig.getInstance()).build();
            checkoutTransaction.enqueue(new TransactionCallback() {
                @Override
                public void onSuccess(CheckoutResponse checkoutResponse) {
                    runOnUiThread(() -> {
                        startActivity(new Intent(context, CheckoutActivity.class).putExtra("checkout", checkoutResponse));
                    });
                }

                @Override
                public void onFailure(String message) {

                }
            });

    ...
}
            
```

Na Activity que mostrará o `CheckoutWidget`, faça o seguinte:

```java 
public class CheckoutActivity extends AppCompatActivity {
    CheckoutWidget checkoutWidget;
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkoutWidget = findViewById(R.id.checkout);
        payButton = findViewById(R.id.pay);

        response = (CheckoutResponse) getIntent().getSerializableExtra("checkout");
        checkoutWidget.setAmount(Double.parseDouble(checkoutResponse.getTotalAmount()));
        checkoutWidget.setExpirationDate("22/09/2018 13:58");
        checkoutWidget.setTotalCheckoutAmount(Double.parseDouble(checkoutResponse.getTotalAmount()));
        checkoutWidget.setItemDescription(checkoutResponse.getNotes());
        checkoutWidget.setItemAmount(Double.parseDouble(checkoutResponse.getTotalAmount()));
        checkoutWidget.setQrCode(checkoutResponse.getMerchant().getId());
    
        payButton.setOnClickListener(v -> payButton.payWithWallet(checkoutResponse, context));
    
    }
}
```
