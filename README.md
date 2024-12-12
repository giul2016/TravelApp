
# 🚗 **Aplicativo de Solicitação de Viagens - Carro Particular** 🚗

## 📝 **Descrição do Aplicativo**

Este aplicativo permite que o usuário solicite viagens de carro particular de um ponto A até um ponto B. Através de uma interface amigável, o usuário pode escolher entre diversas opções de motoristas e valores, confirmar a viagem e visualizar o histórico de viagens realizadas.

O fluxo do aplicativo é simples e intuitivo:

1. Solicitação de viagem
2. Escolha do motorista
3. Confirmação da viagem
4. Visualização do histórico de viagens

## 💡 **Fluxo do Aplicativo**

### **1. Solicitação de Viagem**

- O usuário insere o **ID**, **origem** e **destino** da viagem.
- Um botão de **"Estimar Preço"** realiza uma consulta à API para calcular os valores da viagem.
- A tela de estimativa retorna com opções de motoristas disponíveis.

### **2. Opções de Viagem**

- Exibe um **mapa estático** com a rota plotada, marcando o ponto de origem e destino.
- Uma lista de **motoristas disponíveis** é exibida com:
  - Nome
  - Descrição
  - Veículo
  - Avaliação
  - Valor da viagem
- Cada motorista tem um botão **"Escolher"** que confirma a viagem e redireciona para o histórico.

### **3. Histórico de Viagens**

- Permite o **filtro de viagens** por motorista ou por todas as viagens.
- Exibe o histórico com informações como:
  - Data e hora da viagem
  - Nome do motorista
  - Origem e destino
  - Distância
  - Tempo de viagem
  - Valor da viagem

### **4. Tratamento de Erros**

- Mensagens de erro são exibidas sempre que necessário, permitindo que o usuário saiba o que está errado.
- O app exibe um **feedback visual** quando uma requisição está sendo processada.

---

## 🗺️ **Configuração da API do Google Maps**

Para utilizar o **Google Maps** em seu aplicativo, siga as instruções abaixo:

1. **Obtenha a chave da API**:
   - Acesse o [Google Cloud Console](https://console.cloud.google.com/) e crie um projeto.
   - Habilite a API **Maps SDK for Android**.
   - Gere a **chave de API**.

2. **Inserir a chave da API no aplicativo**:
   - Abra o arquivo `res/values/strings.xml` em seu projeto.
   - Adicione a seguinte linha, substituindo `"YOUR_API_KEY"` pela sua chave real:

   ```xml
   <string name="maps_api_key">YOUR_API_KEY</string>
   ```

---

## ⚙️ **Testes Realizados**

Durante o desenvolvimento, foram realizados os seguintes testes para garantir a estabilidade e funcionalidade do aplicativo:

1. **Testes de Requisição de Estimativa**:
   - Cenários de sucesso e erro com diferentes parâmetros de origem e destino.
   
2. **Testes de Escolha de Motorista**:
   - Validação de opções de motoristas com base nas distâncias e avaliações.

3. **Testes de Histórico de Viagens**:
   - Confirmação da exibição de viagens passadas com dados corretos.

---

## 📱 **Tela de Solicitação de Viagem**

A tela de solicitação de viagem permite que o usuário insira o ID, a origem e o destino da viagem. Ao clicar no botão de estimar preço, ele verá as opções de motoristas.

---

## 🎨 **Estilo Visual**

O aplicativo utiliza **cores vibrantes** e **animações suaves** para garantir uma experiência agradável e moderna.

- **Textos e botões** são destacados com fontes legíveis.
- A interface é **clean** e **responsiva**, se ajustando a diferentes tamanhos de tela.

---

## 💬 **Feedback de Usuário**

Sempre que houver um erro ou uma requisição, o usuário recebe um **feedback visual**, como uma animação de carregamento ou uma mensagem informando o erro.

---

## 🔧 **Tecnologias Utilizadas**

- **Kotlin** para o desenvolvimento Android.
- **Google Maps API** para o mapa e rotas.
- **Retrofit** para comunicação com a API.
- **MVVM (Model-View-ViewModel)** para organizar o código de forma eficiente e escalável.
**Hilt** para a injeção de dependências, facilitando o gerenciamento de componentes e promovendo testes mais fáceis e independentes.

---

## 📅 **Futuras Melhorias**

- **Integração com outras APIs de mapas**.
- **Melhorias no design da interface**.
- **Otimização de requisições**.

---

## 📥 **Como Rodar o Projeto**

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-repositorio.git
   ```

2. **Instale as dependências**:
   ```bash
   ./gradlew build
   ```

3. **Execute o aplicativo** em um emulador ou dispositivo Android.

---

## 🔗 **Links Úteis**

- [Documentação do Google Maps API](https://developers.google.com/maps/documentation/android-sdk/start)
- [Documentação do Retrofit](https://square.github.io/retrofit/)

---

## 👀 **Screenshots**

> Imagem ilustrativa das telas do aplicativo (adicione imagens reais do app)

1. **Tela de Solicitação de Viagem**
   ![Tela de Solicitação](TravelApp/docs/images/viagem.png)
   
2. **Tela de Opções de Viagem**
   ![Tela de Opções](TravelApp/docs/images/motorista.png)

3. **Tela de Histórico de Viagens**
   ![Tela de Histórico](TravelApp/docs/images/histórico.png)

---

## 📍 **Contribuições**

Sinta-se à vontade para contribuir para o projeto. Caso tenha ideias de melhorias ou encontre um bug, crie um **issue** ou envie um **pull request**.

---

## 🏅 **Licença**

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais informações.

---

**Obrigado por conferir o nosso aplicativo! 🚗✨**
