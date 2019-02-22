## Guia de Integração da Biblioteca Checkout App2App Android

[![](https://jitpack.io/v/usekamba/kamba-android-sdk.svg)](https://jitpack.io/#usekamba/kamba-android-sdk)

Ofereça pagamentos de produtos ou serviços em seu aplicativo de forma nativa para Android.

Com uma única integração, seus clientes poderão realizar pagamentos com a sua carteira via código QR de pagamento ou Botão de pagamento, além de utilizarem seus dados cadastrados para futuras compras em 2 cliques. Notificação para lembretes de finalização de compra, levantamento da quantia para sua conta bancária em até 72 horas e muito mais benefícios técnicos e de negócios à longo termo.

![Screenshot](/screenshots/kamba_widget.png)

> A autenticação deve ser feita com as suas credenciais de conta Comerciante. Veja mais sobre os tipos de credenciais em [Autenticação](https://docs.usekamba.com/#autenticacao).

**Atenção:**
> O Checkout App2App está em fase beta. Para fazer parte desta fase você precisa seguir alguns passos antes:

- Enviar um e-mail para suporte@usekamba.com informando um telefone de contato e o e-mail para a sua conta Comerciante.
- Assim que possível, nossa equipa entrará em contato com você para obter mais informações e liberar a funcionalidade para a sua conta Comerciante.

Assim que você implementar o Checkout App2App em sua aplicação, envie o link para seu App Android na Google Play Store e até mesmo feedback para a nossa equipa. Nesta fase de implementação a sua opinião é extremamente importante.

## Formas atuais de pagamento
1. **Pagamento via QR** com um código de pagamento (útil para comerciantes com ponto físico que desejam digitalizar os pagamentos do seu negócio ou para organizadores de eventos para vendas de ingressos, etc, o código pode ser impresso ou enviado para diversos canais sóciais). 
2. **App2App** em que seu aplicativo finaliza os pagamentos abrindo à carteira instalada no dispositivo dos clientes para concluir os pagamentos. 

> **Nota:** Em ambos você acompanha os estados do pagamento, recebe notificações por e-mail, push quando pagamentos são bem sucedidos. E pode ainda controlar todos seus pagamentos com o aplicativo para Comerciantes ou o Painel Web.

## Configuração inicial
Crie uma conta Comerciante connosco entrando em contato com nossa equipe de suporte. Você receberá uma `secret_key` e um identificador de comerciante `merchant_id`  para testar a biblioteca no modo SANDBOX. Você terá ainda acesso ao App Comerciante e ao Painel Web para adiministração e controle dos seus pagamentos e clientes.

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
	implementation 'com.github.usekamba:kamba-android-sdk:v1.0.3'
}
```

**Passo 3:** Adicione a permissão para Internet.
```
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
```
#### Configurar credenciais
É importante configurar suas credenciais para que nossos sistemas possam autenticar suas solicitações de pagamento.
Normalmente, você fará isso na `Activity` que exibirá o método de pagamento para seu aplicativo. Este código será executado após o usuário selecionar a opção de pagamento `Pagar com o Kamba`.

**NOTA:** Durante a fase de desenvolvimento deve-se usar o sdk em ambiente SANDBOX.
```java
ClientConfig.getInstance().configure("SEU_MERCHAND_ID", "SUA_CHAVE_SECRETA", ClientConfig.Environment.SANDBOX);
```
**Configurações do `CLientConfig`:**

| Atributo        | Descrição         |
| ------------- |:-------------:|
| `enironment`      | O campo `environment` define qual ambiente poderá ser usado. Durante a fase de desenvolvimento deve-se usar o ambiente ```ClientConfig.Environment.SANDBOX``` e quando estiver pronto para produção deve-se usar ```ClientConfig.Environment.PRODUCTION```.  |
| `merchantId`       | Use o `merchantId` (ID de comerciante) que copiou do seu painel de comerciante para substituir o valor do campo `merchantId`. Recomenda-se usar variáveis de ambiente sempre, e não deve ser compartilhada ou exposta em sua página html. **O ID de comerciante da API para sandbox e production são diferentes.     |
| `secretKey`        | Campo `secretkey` é a `chave secreta` usado para geração de assinaturas para permitir que seus clientes pagam em ***segurança***|

**Configurações do `CheckoutRequest`:**

| Atributo        | Descrição         |
| ------------- |:-------------:|
|`initialAmount`| Este campo recebe o preço do produto ou serviço a ser comercializado. <br/>eg. ```checkoutRequest.setInitialAmount(28999);```|
|`notes`|Substitua o valor do campo `notes` por uma anotação ou descrição geral a cerca do pagamento.<br/>```checkoutRequest.setNotes("Serviço de hospedagem - Plano Mais");```|

 ## Geração de assinaturas

Para garantir a segurança do checkout e da transação, uma assinatura é gerada usando a chave secreta atribuída ao comerciante.

A chave secreta designada deve ser mantida em segurança, pois é usada para autenticar o checkout através da API da Kamba. 

A geração de assinatura é tratada pela própia biblíoteca garantindo assim um checkout com maior segurança para si e os seus clientes. 

## Implementação
As ferramentas atuais permitem que você use nossos componentes de UI como **Botão de pagamento, tela Checkout, e código QR de pagamento** para exibir as informações para a finalização da compra pelo cliente, seja digitalizando o código QR ou finalizando o pagamento com a sua Carteira. 

```xml
<com.usekamba.kambapaysdk.ui.CheckoutWidget xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/checkout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
</com.usekamba.kambapaysdk.ui.CheckoutWidget>
```

Também criamos um botão de Pagamento para que seus clientes cliquem para aceitar pagamentos via Carteira. Clicar neste botão abrirá a Carteira do cliente para finalizar o pagamento.

```xml
<com.usekamba.kambapaysdk.ui.KambaButton
        android:id="@+id/pay"
        android:layout_width="match_parent"
        android:layout_height="52dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp">
</com.usekamba.kambapaysdk.ui.KambaButton>
```
Você pode adicionar os dois componentes no mesmo layout para mostrar o **widget Checkout** que contém os detalhes de pagamento na mesma tela em conjunto com a opção **botão de Pagamento**, dando assim aos seus clientes à escolha para melhor opção de pagamento no seu contexto.

Crie uma instância do objeto `CheckoutRequest` que representa o item que seu cliente selecionou para comprar. Adicione o preço e a descrição conforme necessário. O objeto `CheckoutTransaction` enviará uma solicitação de forma assíncrona para os nossos sistemas que retornará um objeto `CheckoutResponse` que seu `CheckoutWidget` preencherá com as demais informações. No retorno de chamada `onSuccess`, inicie a atividade que mostrará o `CheckoutWidget` e o `KambaButton`.
```java
public class MerchantActivity extends AppCompatActivity {
    ...
    checkoutRequest = new CheckoutRequest();
    checkoutRequest.setInitialAmount(28999);
    checkoutRequest.setNotes("Serviço de hospedagem - Plano Mais");
    CheckoutTransaction checkoutTransaction = new CheckoutTransactionBuilder()
                .addClientConfig(ClientConfig.getInstance())
                .addCheckoutRequest(checkoutRequest)
                .build();
    checkoutTransaction.enqueue(new TransactionCallback() {
            @Override
            public void onSuccess(final CheckoutResponse checkout) {
                runOnUiThread(() -> startActivity(new Intent(context, CheckoutActivity.class).putExtra("checkout", checkout)));
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(context, "Error initiating Payment request: " + message, Toast.LENGTH_LONG).show() );
            }
        });

    ...
}
            
```

Na Activity que mostrará o `CheckoutWidget`, faça o seguinte:

```java 
public class CheckoutActivity extends AppCompatActivity {
    private CheckoutWidget checkoutWidget;
    private CheckoutResponse checkoutResponse;
    private KambaButton payButton;
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkoutWidget = findViewById(R.id.checkout);
        payButton = findViewById(R.id.pay);
        // Para acessar o checkout response da tela anterior usa sempre response "(CheckoutResponse) getIntent().getSerializableExtra("checkout");
        checkoutResponse = (CheckoutResponse) getIntent().getSerializableExtra("checkout");
        checkoutWidget.setAmount(checkoutResponse.getTotalAmount());
        checkoutWidget.setExpirationDate(checkoutResponse.getExpiresAt());
        checkoutWidget.setTotalCheckoutAmount(checkoutResponse.getTotalAmount());
        checkoutWidget.setItemDescription(checkoutResponse.getNotes());
        checkoutWidget.setItemAmount(checkoutResponse.getInitialAmount());
        checkoutWidget.setQrCode(checkoutResponse.getQrCode());
        payButton.setOnClickListener(v -> payButton.payWithWallet(checkoutResponse, context));
    
    }
}
```

**IMPORTANTE:** Se tudo está funcionando bem em modo SANDBOX e o seu aplicativo está pronto para RELEASE (Lançamento) altera o 
ambiente para production e certifica que estejas a usar a CHAVE_SECRETA e MERCHANT_ID para PRODUCTION.
```java
ClientConfig.getInstance().configure("SEU_MERCHAND_ID", "SUA_CHAVE_SECRETA", ClientConfig.Environment.PRODUCTION);
```

## Customizar
Actualmente é possível alterar o estilo do botão conforme a UI do seu app. 

### Dark Theme
![Screenshot](/screenshots/kamba-button-dark-theme.png)
Para usar este tema basta definir a propriedade:  ```xml app:lightTheme="false" ```

Exemplo:

```xml 
<com.usekamba.kambapaysdk.ui.KambaButton
        android:id="@+id/pay"
        android:layout_width="match_parent"
        android:layout_height="52dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        app:lightTheme="false"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">
</com.usekamba.kambapaysdk.ui.KambaButton>

```

### Light Theme
![Screenshot](/screenshots/kamba-button-light-theme.png)
Para usar este tema basta definir a propriedade:  ```xml app:lightTheme="true" ```

Exemplo:

```xml 
<com.usekamba.kambapaysdk.ui.KambaButton
        android:id="@+id/pay"
        android:layout_width="match_parent"
        android:layout_height="52dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        app:lightTheme="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">
</com.usekamba.kambapaysdk.ui.KambaButton>

```

## Histórico de versões
``` 0.9.3: Melhorias e correção de bugs - 07/07/2018 ``` <br/>
``` 0.9.4: Melhorias e correção de bugs - 20/08/2018 ``` <br/>
``` 1.0.0: Melhoras para reflectir mudanças feitas na API - 03/10/2018 ``` <br/>
``` 1.0.1: Corrigimos o bug que causava uma execção ao gerar o código QR do CheckoutWidget - 04/10/2018 ``` <br/>
``` 1.0.2: Adicionar suporte para temas ao botão Kamba - 09/10/2018 ``` <br/>
``` 1.0.3: Implementamos hmac para asegurar transações e o checkout usando a função de dispersão criptográfica SHA1``` <br/>

© 2018 Soluções de Pagamento. Todos os direitos reservados. USEKAMBA, LDA. - Rua Avenida Manuel Vandunem, Ingombotas - Luanda - Angola
