package aplicacao;

import dados.Drone;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;
import dados.DronePessoal;
import dados.Estado;
import dados.Transporte;
import dados.TransporteCargaInanimada;
import dados.TransporteCargaViva;
import dados.TransportePessoal;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class ACMEAirDrones {

    public interface CarregarTransporte {
        void carregarTransporte(Transporte transporte);        
    }

    private JFrame frame;
    private JPanel panel;
    private Comparator<Drone> comparadorPorCodigo = new Comparator<Drone>(){
        @Override
        public int compare(Drone d1, Drone d2){
            return Integer.compare(d1.getCodigo(), d2.getCodigo());
        }
    };
    private PriorityQueue<Drone> dronesCadastrados = new PriorityQueue<>(comparadorPorCodigo);
    private PriorityQueue<Drone> dronesDisponíveis = new PriorityQueue<>(comparadorPorCodigo);
    private ArrayList<Transporte> transportes = new ArrayList<Transporte>();
    private HashMap<Drone, Transporte> transportesFinalizados = new HashMap<>();
    private Queue<Transporte> transportesPendentes = new LinkedList<>();
    private Transporte transporteCarregado;

    public ACMEAirDrones() {}

    public void executar() {
        frame = new JFrame("ACME Air Drones - Sistema de Gerenciamento");
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); 
    
        
        JLabel tituloLabel = new JLabel("ACME", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 100)); 
        tituloLabel.setForeground(Color.RED); 
    
        
        JLabel subTituloLabel = new JLabel("Air Drones", JLabel.CENTER);
        subTituloLabel.setFont(new Font("Arial", Font.PLAIN, 50)); 
        subTituloLabel.setForeground(Color.BLACK); 
    
        JPanel tituloPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        tituloPanel.setLayout(layout);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;  
    
        
        tituloPanel.add(tituloLabel, gbc);
    
        gbc.gridy = 1;  
        tituloPanel.add(subTituloLabel, gbc);
    
        JPanel tituloContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tituloContainer.add(tituloPanel);
    
        // Criação dos botões
        JButton cadastrarDroneButton = new JButton("Cadastrar Novo Drone");
        JButton cadastrarTransporteButton = new JButton("Cadastrar Novo Transporte");
        JButton processarTransportesButton = new JButton("Processar Transportes Pendentes");
        JButton relatorioGeralButton = new JButton("Mostrar Relatório Geral");
        JButton mostrarTransportesButton = new JButton("Mostrar Todos os Transportes");
        JButton alterarSituacaoButton = new JButton("Alterar Situação de Transporte");
        JButton lerDadosButton = new JButton("Ler Dados de Simulação");
        JButton salvarDadosButton = new JButton("Salvar Dados");
        JButton carregarDadosButton = new JButton("Carregar Dados");
        JButton finalizarButton = new JButton("Finalizar Sistema");

        // Personalizando os botões com o método 'customizeButton'
        customizeButton(cadastrarDroneButton);
        customizeButton(cadastrarTransporteButton);
        customizeButton(processarTransportesButton);
        customizeButton(relatorioGeralButton);
        customizeButton(mostrarTransportesButton);
        customizeButton(alterarSituacaoButton);
        customizeButton(lerDadosButton);
        customizeButton(salvarDadosButton);
        customizeButton(carregarDadosButton);
        customizeButton(finalizarButton);

        // Adicionar ações aos botões
        cadastrarDroneButton.addActionListener(e -> abrirTelaCadastroDrone());
        cadastrarTransporteButton.addActionListener(e -> telaCadastroTransporte());
        processarTransportesButton.addActionListener(e -> processarTransportesPendentes());
        relatorioGeralButton.addActionListener(e -> mostrarRelatorioGeral());
        mostrarTransportesButton.addActionListener(e -> mostrarTransportes());
        alterarSituacaoButton.addActionListener(e -> alterarSituacaoTransporte());
        lerDadosButton.addActionListener(e -> lerDadosSimulacao());
        salvarDadosButton.addActionListener(e -> salvarDados());
        carregarDadosButton.addActionListener(e -> carregarDados());
        finalizarButton.addActionListener(e -> finalizarSistema());

        // Criar o painel para adicionar os botões
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // Layout com 5 linhas e 2 colunas, espaçamento de 10px entre os botões

        // Adicionar os botões ao painel
        panel.add(cadastrarDroneButton);
        panel.add(cadastrarTransporteButton);
        panel.add(processarTransportesButton);
        panel.add(relatorioGeralButton);
        panel.add(mostrarTransportesButton);
        panel.add(alterarSituacaoButton);
        panel.add(lerDadosButton);
        panel.add(salvarDadosButton);
        panel.add(carregarDadosButton);
        panel.add(finalizarButton);
    
        
        frame.add(tituloContainer, BorderLayout.NORTH);
    
        frame.add(panel, BorderLayout.CENTER);
    
        frame.setSize(600, 600); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setResizable(true); 
        frame.setVisible(true);
    
        finalizarButton.setBackground(Color.RED);
        finalizarButton.setForeground(Color.WHITE);
        finalizarButton.setOpaque(true);
    }
    
    // Método para personalizar os botões
    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 35)); 
        button.setFont(new Font("Arial", Font.PLAIN, 20)); 
        button.setBorder(new LineBorder(Color.BLACK, 3, true)); 
        button.setBackground(new Color(169, 169, 169)); 

        button.setForeground(Color.BLACK); 

        button.setOpaque(true);
    }
    
    private void abrirTelaCadastroDrone() {
        
        JFrame tipoDroneFrame = new JFrame("Escolha o Tipo de Drone");
        tipoDroneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tipoDroneFrame.setLayout(new BorderLayout(20, 10));
    
        
        tipoDroneFrame.setSize(900, 600);
        tipoDroneFrame.setLocationRelativeTo(null);
    
        
        JLabel tituloCadastro = new JLabel("ACME - Cadastro de Drones", JLabel.CENTER);
        tituloCadastro.setFont(new Font("Arial", Font.BOLD, 70));
        tituloCadastro.setForeground(new Color(255, 0, 0));
        tipoDroneFrame.add(tituloCadastro, BorderLayout.NORTH);
    
        // Painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton pessoalButton = new JButton("Drone Pessoal");
        JButton cargaVivaButton = new JButton("Drone de Carga Viva");
        JButton cargaInanimadaButton = new JButton("Drone de Carga Inanimada");
    
        
        Font botaoFont = new Font("Arial", Font.BOLD, 50);
        pessoalButton.setFont(botaoFont);
        cargaVivaButton.setFont(botaoFont);
        cargaInanimadaButton.setFont(botaoFont);
    
        
        pessoalButton.setBackground(Color.LIGHT_GRAY);
        cargaVivaButton.setBackground(Color.LIGHT_GRAY);
        cargaInanimadaButton.setBackground(Color.LIGHT_GRAY);
    
        pessoalButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); 
        cargaVivaButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        cargaInanimadaButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
        
        pessoalButton.setPreferredSize(new Dimension(300, 200));
        cargaVivaButton.setPreferredSize(new Dimension(300, 200));
        cargaInanimadaButton.setPreferredSize(new Dimension(300, 200));
    
        buttonPanel.add(pessoalButton);
        buttonPanel.add(cargaVivaButton);
        buttonPanel.add(cargaInanimadaButton);
        tipoDroneFrame.add(buttonPanel, BorderLayout.CENTER);
    
        pessoalButton.addActionListener(e -> {
            tipoDroneFrame.dispose();
            abrirTelaCadastroDroneEspecifico("Pessoal");
        });
    
        cargaVivaButton.addActionListener(e -> {
            tipoDroneFrame.dispose();
            abrirTelaCadastroDroneEspecifico("Carga Viva");
        });

        cargaInanimadaButton.addActionListener(e -> {
            tipoDroneFrame.dispose();
            abrirTelaCadastroDroneEspecifico("Carga Inanimada");
        });
    
        tipoDroneFrame.setVisible(true);
    }    

    public void abrirTelaCadastroDroneEspecifico(String tipo) {
        
        JFrame cadastroFrame = new JFrame("Cadastrar Drone " + tipo);
        cadastroFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        cadastroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cadastroFrame.setLayout(new BorderLayout(10, 10));
    
        // Estilo do título
        JLabel tituloCadastroDrone = new JLabel("Cadastro de Drone - Tipo: " + tipo, JLabel.CENTER);
        tituloCadastroDrone.setFont(new Font("Arial", Font.BOLD, 50)); 
        tituloCadastroDrone.setForeground(new Color(255, 0, 0)); 
        tituloCadastroDrone.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); 
        cadastroFrame.add(tituloCadastroDrone, BorderLayout.NORTH); 
    
        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(255, 255, 255)); 
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), 
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Campos de entrada
        JTextField codigoField = new JTextField(30);
        JTextField custoFixoField = new JTextField(30);
        JTextField autonomiaField = new JTextField(30);
        JTextField pesoMaximoField = tipo.equals("Carga Viva") ||
                                     tipo.equals("Carga Inanimada") ? new JTextField(15) : null;
        JTextField qtdPessoasField = tipo.equals("Pessoal") ? new JTextField(15) : null;
        JCheckBox climatizadoBox = tipo.equals("Carga Viva") ? new JCheckBox("O drone é climatizado?") : null;
        JCheckBox protecaoBox = tipo.equals("Carga Inanimada") ? new JCheckBox("O drone possui proteção?") : null;

        if (climatizadoBox != null){
            climatizadoBox.setBounds(1, 50, 200, 30);
        }
        if (protecaoBox != null){
            protecaoBox.setBounds(1, 50, 200, 30);
        }
    
        // Estilização dos campos de texto
        Font inputFont = new Font("Arial", Font.PLAIN, 20); // Define uma fonte maior para os campos
        codigoField.setFont(inputFont);
        custoFixoField.setFont(inputFont);
        autonomiaField.setFont(inputFont);
        if (pesoMaximoField != null) {
            pesoMaximoField.setFont(inputFont);
        }
        if (qtdPessoasField != null) {
            qtdPessoasField.setFont(inputFont);
        }
        if (climatizadoBox != null) { 
            climatizadoBox.setFont(inputFont);
        }
        if (protecaoBox != null) {
            protecaoBox.setFont(inputFont);
        }
    
        codigoField.setBackground(new Color(255, 255, 255)); 
        custoFixoField.setBackground(new Color(255, 255, 255)); 
        autonomiaField.setBackground(new Color(255, 255, 255)); 
        if (pesoMaximoField != null) {
            pesoMaximoField.setBackground(new Color(255, 255, 255)); 
        }
        if (qtdPessoasField != null) { 
            qtdPessoasField.setBackground(new Color(255, 255, 255));
        }
        if (climatizadoBox != null) { 
            climatizadoBox.setBackground(new Color(255, 255, 255));
        }
        if (protecaoBox != null) {
            protecaoBox.setBackground(new Color(255, 255, 255));
        }
    
        // Estilização dos rótulos
        Font labelFont = new Font("Arial", Font.BOLD, 28); 
    
        // Adicionar campos ao painel
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel codigoLabel = new JLabel("Código do Drone:");
        codigoLabel.setFont(labelFont); 
        inputPanel.add(codigoLabel, gbc);
    
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(codigoField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel custoFixoLabel = new JLabel("Custo Fixo do Drone:");
        custoFixoLabel.setFont(labelFont);
        inputPanel.add(custoFixoLabel, gbc);
    
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(custoFixoField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel autonomiaLabel = new JLabel("Autonomia do Drone:");
        autonomiaLabel.setFont(labelFont);
        inputPanel.add(autonomiaLabel, gbc);
    
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(autonomiaField, gbc);
    
        if (pesoMaximoField != null) {
            gbc.gridx = 0; gbc.gridy = 3;
            JLabel pesoMaximoLabel = new JLabel("Peso Máximo do Drone:");
            pesoMaximoLabel.setFont(labelFont);
            inputPanel.add(pesoMaximoLabel, gbc);
    
            gbc.gridx = 1; gbc.gridy = 3;
            inputPanel.add(pesoMaximoField, gbc);
        }
        if (qtdPessoasField != null) { 
            gbc.gridx = 0; gbc.gridy = 3;
            JLabel qtdPessoasLabel = new JLabel("Quantidade Máx. de Pessoas: ");
            qtdPessoasLabel.setFont(labelFont);
            inputPanel.add(qtdPessoasLabel, gbc);

            gbc.gridx = 1; gbc.gridy = 3;
            inputPanel.add(qtdPessoasField, gbc);
        }
        if (climatizadoBox != null) {
            gbc.gridx = 0; gbc.gridy = 4;
            JLabel climatizadoLabel = new JLabel("O Drone é Climatizado?");
            climatizadoLabel.setFont(labelFont);
            inputPanel.add(climatizadoLabel, gbc);

            gbc.gridx = 1; gbc.gridy = 4;
            inputPanel.add(climatizadoBox, gbc);
        }
        if (protecaoBox != null) { 
            gbc.gridx = 0; gbc.gridy = 4;
            JLabel protecaoLabel = new JLabel("O Drone Possui Proteçaõ?");
            protecaoLabel.setFont(labelFont);
            inputPanel.add(protecaoLabel, gbc);

            gbc.gridx = 1; gbc.gridy = 4;
            inputPanel.add(protecaoBox, gbc);
        }
    
        cadastroFrame.add(inputPanel, BorderLayout.WEST);
    
        JTextArea mensagemArea = new JTextArea(30, 60);
        mensagemArea.setEditable(false);
        mensagemArea.setWrapStyleWord(true);
        mensagemArea.setLineWrap(true);
        mensagemArea.setBackground(new Color(255, 250, 240)); 
        mensagemArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mensagemArea.setFont(new Font("Arial", Font.PLAIN, 21));
    
        cadastroFrame.add(new JScrollPane(mensagemArea), BorderLayout.CENTER);
    
        // Painel para os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)  
        ));
    
        // Botões
        JButton salvarButton = new JButton("Salvar");
        JButton limparButton = new JButton("Limpar Campos");
        JButton dadosButton = new JButton("Mostrar Dados");
        JButton terminarButton = new JButton("Terminar");
    
        // Personalização dos botões
        salvarButton.setFont(inputFont);
        limparButton.setFont(inputFont);
        dadosButton.setFont(inputFont);
        terminarButton.setFont(inputFont);
    
        buttonPanel.add(salvarButton);
        buttonPanel.add(limparButton);
        buttonPanel.add(dadosButton);
        buttonPanel.add(terminarButton);
    
        cadastroFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        // Estilizar botões
        salvarButton.setFont(new Font("Arial", Font.BOLD, 25));
        limparButton.setFont(new Font("Arial", Font.BOLD, 25));
        dadosButton.setFont(new Font("Arial", Font.BOLD, 25));
        terminarButton.setFont(new Font("Arial", Font.BOLD, 25));
    
        // Botão Salvar - Cinza Claro
        salvarButton.setBackground(new Color(128, 128, 128)); // Cinza Claro
        salvarButton.setForeground(Color.WHITE); // Texto Branco

        // Botão Limpar - Cinza Médio
        limparButton.setBackground(new Color(128, 128, 128)); // Cinza Médio
        limparButton.setForeground(Color.WHITE); // Texto Branco

        // Botão Dados - Cinza Escuro
        dadosButton.setBackground(new Color(128, 128, 128)); // Cinza Escuro
        dadosButton.setForeground(Color.WHITE); // Texto Branco
    
        // Estilizar botão "Terminar"
        terminarButton.setBackground(Color.RED);
        terminarButton.setForeground(Color.WHITE);
        terminarButton.setOpaque(true);
        terminarButton.setBorderPainted(false);
    
        // Adicionar botões ao painel
        buttonPanel.add(salvarButton);
        buttonPanel.add(limparButton);
        buttonPanel.add(dadosButton);
        buttonPanel.add(terminarButton);
        cadastroFrame.add(buttonPanel, BorderLayout.SOUTH);    
    
        salvarButton.addActionListener(e -> {
            try {
                
                String codigoStr = codigoField.getText().trim();
                String custoFixoStr = custoFixoField.getText().trim();
                String autonomiaStr = autonomiaField.getText().trim();
                String pesoMaximoStr = pesoMaximoField != null ? pesoMaximoField.getText().trim() : null;
                String qtdPessoasStr = qtdPessoasField != null ? qtdPessoasField.getText().trim() : null;
                Boolean climatizado = climatizadoBox != null ? climatizadoBox.isSelected() : null;
                Boolean protecao = protecaoBox != null ? protecaoBox.isSelected() : null;
                
                
                if (codigoStr.isEmpty() || custoFixoStr.isEmpty() || autonomiaStr.isEmpty() ||
                   (pesoMaximoField != null && pesoMaximoStr.isEmpty()) ||
                    qtdPessoasField != null && qtdPessoasStr.isEmpty()) {
                    mensagemArea.setText("Erro: Todos os campos são obrigatórios.\n");
                    return;
                }

                int codigo;
                int qtdPessoas = 0;
                try {
                    codigo = Integer.parseInt(codigoStr);
                    if (qtdPessoasField != null && !qtdPessoasStr.isEmpty()) {
                        qtdPessoas = Integer.parseInt(qtdPessoasStr);
                    }
                } catch (NumberFormatException n){
                    mensagemArea.setText("Erro: O número do transporte deve ser um número válido.\n");
                    return;
                }
                
                dronesCadastrados.forEach(d1 -> {
                    if (d1.getCodigo() == codigo){
                        mensagemArea.setText("Erro: Já existe um drone com o código " + codigo + ".\n");
                    }
                });
                
                Double custoFixo;
                Double autonomia;
                Double pesoMaximo = 0.0;
        
                try {
                    custoFixo = Double.parseDouble(custoFixoStr);
                    autonomia = Double.parseDouble(autonomiaStr);
                    if (pesoMaximoField != null && !pesoMaximoStr.isEmpty()) {
                        pesoMaximo = Double.parseDouble(pesoMaximoStr);
                    }
                } catch (NumberFormatException ex) {
                    mensagemArea.setText("Erro: Custo Fixo, Autonomia e Peso Máximo devem ser números válidos.\n");
                    return;
                }
                
                if (tipo.equals("Pessoal")){
                    Drone d1 = new DronePessoal(codigo, custoFixo, autonomia, qtdPessoas);
                    dronesCadastrados.add(d1);
                    dronesDisponíveis.add(d1);
                } else if (tipo.equals("Carga Viva")){
                    Drone d1 = new DroneCargaViva(codigo, custoFixo, autonomia, pesoMaximo, climatizado);
                    dronesCadastrados.add(d1);
                    dronesDisponíveis.add(d1);
                } else if (tipo.equals("Carga Inanimada")){
                    Drone d1 = new DroneCargaInanimada(codigo, custoFixo, autonomia, pesoMaximo, protecao);
                    dronesCadastrados.add(d1);
                    dronesDisponíveis.add(d1);
                }
        
                mensagemArea.setText("Drone " + tipo.toLowerCase() + " cadastrado com sucesso!\n");
            } catch (Exception ex) {
                mensagemArea.setText("Erro inesperado ao salvar o drone: " + ex.getMessage() + "\n");
                ex.printStackTrace(); 
            }
        });               
    
        limparButton.addActionListener(e -> {
            codigoField.setText("");
            custoFixoField.setText("");
            autonomiaField.setText("");
            if (pesoMaximoField != null) pesoMaximoField.setText("");
            if (qtdPessoasField != null) qtdPessoasField.setText("");
            mensagemArea.setText("");
        });
    
        dadosButton.addActionListener(e -> {
            try {
                if (dronesCadastrados.isEmpty()) {
                    mensagemArea.setText("Não há drones cadastrados.\n");
                } else {
                    StringBuilder listaDrones = new StringBuilder("Drones Cadastrados:\n");
                    dronesCadastrados.forEach(d1 -> 
                    listaDrones.append(d1.toString()).append("\n"));
                    mensagemArea.setText(listaDrones.toString());
                }
            } catch (Exception ex) {
            mensagemArea.setText("Erro ao exibir os dados dos drones: " + ex.getMessage() + "\n");
            }
        }); 
        
        terminarButton.addActionListener(e -> {
            cadastroFrame.dispose(); 
        });
    
        cadastroFrame.setVisible(true);
    }  
    
    private void telaCadastroTransporte() {
        JFrame tipoTransporte = new JFrame("Escolha o Tipo de Transporte");
        
        // Título com estilo personalizado
        JLabel tituloLabel = new JLabel("ACME - Escolha o Tipo de Transporte", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 70)); // Aumenta o tamanho da fonte para dar destaque
        tituloLabel.setForeground(new Color(255,0,0)); 
        tituloLabel.setBackground(new Color(240, 240, 240)); // Cor de fundo suave
        tituloLabel.setOpaque(true); // Torna o fundo visível
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espaçamento no topo e na parte inferior

        // Configura a janela para abrir em tela cheia
        tipoTransporte.setSize(300,300);
        tipoTransporte.setUndecorated(false); // Barra de título visível
        tipoTransporte.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha ao clicar no "X"
        tipoTransporte.setLayout(new BorderLayout(10, 10)); // Layout com margem

        // Adicionando o título à parte superior da janela
        tipoTransporte.add(tituloLabel, BorderLayout.NORTH);

        // Painel central com grid para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Grid de 3 linhas com espaçamento de 10px entre os botões
        buttonPanel.setBackground(Color.WHITE); // Cor de fundo para o painel de botões

        // Criando os botões com estilo moderno
        JButton pessoalButton = new JButton("Transporte Pessoal");
        JButton cargaVivaButton = new JButton("Transporte de Carga Viva");
        JButton cargaInanimadaButton = new JButton("Transporte de Carga Inanimada");

        // Estilo para os botões
        Font buttonFont = new Font("Arial", Font.BOLD, 30); // Fonte para os botões
        pessoalButton.setFont(buttonFont);
        cargaVivaButton.setFont(buttonFont);
        cargaInanimadaButton.setFont(buttonFont);

        // Cores de fundo dos botões
        pessoalButton.setBackground(new Color(76, 175, 80)); // Verde
        cargaVivaButton.setBackground(new Color(33, 150, 243)); // Azul
        cargaInanimadaButton.setBackground(new Color(244, 67, 54)); // Vermelho

        // Cores de texto dos botões
        pessoalButton.setForeground(Color.WHITE);
        cargaVivaButton.setForeground(Color.WHITE);
        cargaInanimadaButton.setForeground(Color.WHITE);

        // Efeito de foco nos botões
        pessoalButton.setFocusPainted(false);
        cargaVivaButton.setFocusPainted(false);
        cargaInanimadaButton.setFocusPainted(false);

        // Adicionando os botões ao painel
        buttonPanel.add(pessoalButton);
        buttonPanel.add(cargaVivaButton);
        buttonPanel.add(cargaInanimadaButton);

        // Adicionando o painel com os botões à janela
        tipoTransporte.add(buttonPanel, BorderLayout.CENTER);

        // Personalizar os botões usando o método customizeButton
        customizeButton(pessoalButton);
        customizeButton(cargaVivaButton);
        customizeButton(cargaInanimadaButton);

        // Ações dos botões
        pessoalButton.addActionListener(e -> cadastrarTransporte("Pessoal"));
        cargaVivaButton.addActionListener(e -> cadastrarTransporte("Carga Viva"));
        cargaInanimadaButton.addActionListener(e -> cadastrarTransporte("Carga Inanimada"));

        // Tornar a janela visível
        tipoTransporte.setVisible(true);
    }

    private boolean parseBooleanSafe(String input){
        if ("true".equalsIgnoreCase(input)) {
            return true;
        } else if ("false".equalsIgnoreCase(input)) {
            return false;
        } else {
            throw new IllegalArgumentException("Valor inválido para Boolean. Use 'true' ou 'false'.");
        }
    }

    public void cadastrarTransporte(String tipoTransporte) {
        JFrame novoTransporte = new JFrame("Cadastrar Novo Transporte");
        novoTransporte.setExtendedState(JFrame.MAXIMIZED_BOTH);
        novoTransporte.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela quando clicado

        // Criar o painel para o título
        JPanel tituloPanel = new JPanel();
        tituloPanel.setLayout(new BorderLayout()); // Usando BorderLayout para centralizar o título

        // Criando o JLabel com o texto e alinhamento
        JLabel titulo = new JLabel("ACME - Cadastrar Novo Transporte", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 70)); // Define o tamanho e estilo da fonte
        titulo.setForeground(Color.RED); // Cor do texto (Vermelho)

        // Adiciona o painel de título ao JFrame
        novoTransporte.add(tituloPanel, BorderLayout.NORTH);
    
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
    
        JTextField numeroField = new JTextField(15);
        JTextField nomeClienteField = new JTextField(15);
        JTextField descricaoField = new JTextField(15);
        JTextField pesoField = new JTextField(15);
        JTextField latOrigemField = new JTextField(15);
        JTextField latDestinoField = new JTextField(15);
        JTextField longOrigemField = new JTextField(15);
        JTextField longDestinoField = new JTextField(15);
        JTextArea areaMensagens = new JTextArea(10, 30);
        areaMensagens.setEditable(false);
        areaMensagens.setWrapStyleWord(true);
        areaMensagens.setLineWrap(true);
    
        JScrollPane scrollPane = new JScrollPane(areaMensagens);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        JButton botaoConfirma = new JButton("Cadastrar transporte");
        JButton botaoLimpar = new JButton("Limpar Campos");
        JButton botaoDados = new JButton("Mostrar Dados");
        JButton botaoSair = new JButton("Sair");
        JButton botaoCampos = new JButton("Exibir Campos Específicos");

        // Estilizar botões
        botaoConfirma.setFont(new Font("Arial", Font.BOLD, 25));
        botaoConfirma.setBackground(new Color(128, 128, 128));
        botaoConfirma.setForeground(Color.WHITE);

        botaoLimpar.setFont(new Font("Arial", Font.BOLD, 25));
        botaoLimpar.setBackground(new Color(128, 128, 128));
        botaoLimpar.setForeground(Color.WHITE);

        botaoDados.setFont(new Font("Arial", Font.BOLD, 25));
        botaoDados.setBackground(new Color(128, 128, 128)); 
        botaoDados.setForeground(Color.WHITE);

        botaoSair.setFont(new Font("Arial", Font.BOLD, 25));
        botaoSair.setBackground(Color.RED); // Vermelho
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setOpaque(true);
        botaoSair.setBorderPainted(false);

        // Botão Campos Específicos
        botaoCampos.setFont(new Font("Arial", Font.BOLD, 25));
        botaoCampos.setBackground(new Color(128, 128, 128));
        botaoCampos.setForeground(Color.WHITE);

        // Alinhamento dos Campos à Esquerda e Mensagens à Direita
        grid.anchor = GridBagConstraints.WEST;  // Alinhar à esquerda
    
        // Adicionando campos de entrada à esquerda
        grid.gridx = 0; grid.gridy = 0;
        inputPanel.add(new JLabel("Nº Transporte:"), grid);
        grid.gridx = 1; grid.gridy = 0;
        inputPanel.add(numeroField, grid);

        grid.gridx = 0; grid.gridy = 1;
        inputPanel.add(new JLabel("Nome do Cliente:"), grid);
        grid.gridx = 1; grid.gridy = 1;
        inputPanel.add(nomeClienteField, grid);

        grid.gridx = 0; grid.gridy = 2;
        inputPanel.add(new JLabel("Descrição do Transporte:"), grid);
        grid.gridx = 1; grid.gridy = 2;
        inputPanel.add(descricaoField, grid);

        grid.gridx = 0; grid.gridy = 3;
        inputPanel.add(new JLabel("Peso:"), grid);
        grid.gridx = 1; grid.gridy = 3;
        inputPanel.add(pesoField, grid);

        grid.gridx = 0; grid.gridy = 4;
        inputPanel.add(new JLabel("Latitude de Origem:"), grid);
        grid.gridx = 1; grid.gridy = 4;
        inputPanel.add(latOrigemField, grid);

        grid.gridx = 0; grid.gridy = 5;
        inputPanel.add(new JLabel("Latitude de Destino:"), grid);
        grid.gridx = 1; grid.gridy = 5;
        inputPanel.add(latDestinoField, grid);

        grid.gridx = 0; grid.gridy = 6;
        inputPanel.add(new JLabel("Longitude de Origem:"), grid);
        grid.gridx = 1; grid.gridy = 6;
        inputPanel.add(longOrigemField, grid);

        grid.gridx = 0; grid.gridy = 7;
        inputPanel.add(new JLabel("Longitude de Destino:"), grid);
        grid.gridx = 1; grid.gridy = 7;
        inputPanel.add(longDestinoField, grid);

        // Colocando o painel de entrada no canto esquerdo
        novoTransporte.add(inputPanel, BorderLayout.WEST);
        // Colocando o painel de mensagens no centro
        novoTransporte.add(scrollPane, BorderLayout.CENTER);
    
        // Organizando os botões na parte inferior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(botaoConfirma);
        buttonPanel.add(botaoLimpar);
        buttonPanel.add(botaoDados);
        buttonPanel.add(botaoSair);
        buttonPanel.add(botaoCampos);
        novoTransporte.add(buttonPanel, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(frame, "Atenção! Lembre-se pressionar o botão 'Exibir Campos Específicos', caso contrário, não será possível concluir o cadastro do transporte.");
    
        botaoCampos.addActionListener(e -> {
            inputPanel.removeAll();

            grid.gridx = 0; grid.gridy = 0;
            inputPanel.add(new JLabel("Nº Transporte:"), grid);
            grid.gridx = 1; grid.gridy = 0;
            inputPanel.add(numeroField, grid);

            grid.gridx = 0; grid.gridy = 1;
            inputPanel.add(new JLabel("Nome do Cliente:"), grid);
            grid.gridx = 1; grid.gridy = 1;
            inputPanel.add(nomeClienteField, grid);

            grid.gridx = 0; grid.gridy = 2;
            inputPanel.add(new JLabel("Descrição do Transporte:"), grid);
            grid.gridx = 1; grid.gridy = 2;
            inputPanel.add(descricaoField, grid);

            grid.gridx = 0; grid.gridy = 3;
            inputPanel.add(new JLabel("Peso:"), grid);
            grid.gridx = 1; grid.gridy = 3;
            inputPanel.add(pesoField, grid);

            grid.gridx = 0; grid.gridy = 4;
            inputPanel.add(new JLabel("Latitude de Origem:"), grid);
            grid.gridx = 1; grid.gridy = 4;
            inputPanel.add(latOrigemField, grid);

            grid.gridx = 0; grid.gridy = 5;
            inputPanel.add(new JLabel("Latitude de Destino:"), grid);
            grid.gridx = 1; grid.gridy = 5;
            inputPanel.add(latDestinoField, grid);

            grid.gridx = 0; grid.gridy = 6;
            inputPanel.add(new JLabel("Longitude de Origem:"), grid);
            grid.gridx = 1; grid.gridy = 6;
            inputPanel.add(longOrigemField, grid);

            grid.gridx = 0; grid.gridy = 7;
            inputPanel.add(new JLabel("Longitude de Destino:"), grid);
            grid.gridx = 1; grid.gridy = 7;
            inputPanel.add(longDestinoField, grid);
    
            if (tipoTransporte.equals("Pessoal")) {
                JTextField qtdPessoasField = new JTextField(15);
                grid.gridx = 0; grid.gridy = 8;
                inputPanel.add(new JLabel("Quantidade de Pessoas:"), grid);
                grid.gridx = 1; grid.gridy = 8;
                inputPanel.add(qtdPessoasField, grid);

                botaoConfirma.addActionListener(ae -> {
                    String numeroString = numeroField.getText().trim();
                    int numero;
                    try {
                        numero = Integer.parseInt(numeroString);
                    } catch (NumberFormatException ex){
                        areaMensagens.setText("Erro: O número do transporte deve ser um número válido.\\n");
                        return;
                    }

                    for (Transporte t : transportesPendentes) {
                        if (t.getNumero() == numero) {
                            areaMensagens.setText("Erro: Já existe um transporte com esse número.\n");
                            return;
                        }
                    }

                    double peso;
                    try {
                        peso = Double.parseDouble(pesoField.getText().trim());
                        if (peso <= 0) {
                            areaMensagens.setText("Erro: O peso deve ser maior que 0.\n");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: O peso deve ser um número válido.\n");
                        return;
                    }

                    double latOrigem, latDestino, longOrigem, longDestino;
                    try {
                        latOrigem = Double.parseDouble(latOrigemField.getText().trim());
                        latDestino = Double.parseDouble(latDestinoField.getText().trim());
                        longOrigem = Double.parseDouble(longOrigemField.getText().trim());
                        longDestino = Double.parseDouble(longDestinoField.getText().trim());

                        if (latOrigem < -90 || latOrigem > 90) {
                            areaMensagens.setText("Erro: A latitude de origem deve estar entre -90 e 90.\n");
                            return;
                        }
                        if (latDestino < -90 || latDestino > 90) {
                            areaMensagens.setText("Erro: A latitude de destino deve estar entre -90 e 90.\n");
                            return;
                        }
                
                        if (longOrigem < -180 || longOrigem > 180) {
                            areaMensagens.setText("Erro: A longitude de origem deve estar entre -180 e 180.\n");
                            return;
                        }
                        if (longDestino < -180 || longDestino > 180) {
                            areaMensagens.setText("Erro: A longitude de destino deve estar entre -180 e 180.\n");
                            return;
                        }

                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: As coordenadas de origem ou destino devem ser números válidos.\n");
                        return;
                    }

                    int qtdPessoas;
                    try {
                        qtdPessoas = Integer.parseInt(qtdPessoasField.getText().trim());
                        if (qtdPessoas <= 0) {
                            areaMensagens.setText("Erro: A quantidade de pessoas deve ser maior que 0.\n");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: A quantidade de pessoas deve ser um número válido.\n");
                        return;
                    }

                    TransportePessoal tp = new TransportePessoal(numero, nomeClienteField.getText().trim(), descricaoField.getText().trim(),
                            peso, latOrigem, latDestino, longOrigem, longDestino, qtdPessoas);
                    transportesPendentes.add(tp);
                    transportes.add(tp);
                    areaMensagens.setText("Transporte pessoal cadastrado com sucesso.\n");
                });

            } else if (tipoTransporte.equals("Carga Viva")) {
                JTextField tempMinimaField = new JTextField(15);
                grid.gridx = 0; grid.gridy = 8;
                inputPanel.add(new JLabel("Temperatura Mínima:"), grid);
                grid.gridx = 1; grid.gridy = 8;
                inputPanel.add(tempMinimaField, grid);

                JTextField tempMaximaField = new JTextField(15);
                grid.gridx = 0; grid.gridy = 9;
                inputPanel.add(new JLabel("Temperatura Máxima:"), grid);
                grid.gridx = 1; grid.gridy = 9;
                inputPanel.add(tempMaximaField, grid);

                botaoConfirma.addActionListener(ae -> {
                    String numeroString = numeroField.getText().trim();
                    int numero;
                    try {
                        numero = Integer.parseInt(numeroString);
                    } catch (NumberFormatException ex){
                        areaMensagens.setText("Erro: O número do transporte deve ser um número válido.\\n");
                        return;
                    }

                    for (Transporte t : transportesPendentes) {
                        if (t.getNumero() == numero) {
                            areaMensagens.setText("Erro: Já existe um transporte com esse número.\n");
                            return;
                        }
                    }

                    double peso;
                    try {
                        peso = Double.parseDouble(pesoField.getText().trim());
                        if (peso <= 0) {
                            areaMensagens.setText("Erro: O peso deve ser maior que 0.\n");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: O peso deve ser um número válido.\n");
                        return;
                    }

                    double latOrigem, latDestino, longOrigem, longDestino;
                    try {
                        latOrigem = Double.parseDouble(latOrigemField.getText().trim());
                        latDestino = Double.parseDouble(latDestinoField.getText().trim());
                        longOrigem = Double.parseDouble(longOrigemField.getText().trim());
                        longDestino = Double.parseDouble(longDestinoField.getText().trim());

                        if (latOrigem < -90 || latOrigem > 90) {
                            areaMensagens.setText("Erro: A latitude de origem deve estar entre -90 e 90.\n");
                            return;
                        }
                        if (latDestino < -90 || latDestino > 90) {
                            areaMensagens.setText("Erro: A latitude de destino deve estar entre -90 e 90.\n");
                            return;
                        }
                
                        if (longOrigem < -180 || longOrigem > 180) {
                            areaMensagens.setText("Erro: A longitude de origem deve estar entre -180 e 180.\n");
                            return;
                        }
                        if (longDestino < -180 || longDestino > 180) {
                            areaMensagens.setText("Erro: A longitude de destino deve estar entre -180 e 180.\n");
                            return;
                        }

                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: As coordenadas de origem ou destino devem ser números válidos.\n");
                        return;
                    }

                    double tempMinima, tempMaxima;
                    try {
                        tempMinima = Double.parseDouble(tempMinimaField.getText().trim());
                        tempMaxima = Double.parseDouble(tempMaximaField.getText().trim());
                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: As temperaturas devem ser números válidos.\n");
                        return;
                    }

                    TransporteCargaViva tc = new TransporteCargaViva(numero, nomeClienteField.getText().trim(), descricaoField.getText().trim(),
                            peso, latOrigem, latDestino, longOrigem, longDestino, tempMinima, tempMaxima);
                    transportesPendentes.add(tc);
                    transportes.add(tc);
                    areaMensagens.setText("Transporte de carga viva cadastrado com sucesso.\n");
                });

            } else if (tipoTransporte.equals("Carga Inanimada")) {
                JTextField cargaPerigosaField = new JTextField(15);
                grid.gridx = 0; grid.gridy = 8;
                inputPanel.add(new JLabel("Carga Perigosa?(true ou false):"), grid);
                grid.gridx = 1; grid.gridy = 8;
                inputPanel.add(cargaPerigosaField, grid);

                botaoConfirma.addActionListener(ae -> {
                    String numeroString = numeroField.getText().trim();
                    int numero;
                    try {
                        numero = Integer.parseInt(numeroString);
                    } catch (NumberFormatException ex){
                        areaMensagens.setText("Erro: O número do transporte deve ser um número válido.\\n");
                        return;
                    }

                    for (Transporte t : transportesPendentes) {
                        if (t.getNumero() == numero) {
                            areaMensagens.setText("Erro: Já existe um transporte com esse número.\n");
                            return;
                        }
                    }

                    double peso;
                    try {
                        peso = Double.parseDouble(pesoField.getText().trim());
                        if (peso <= 0) {
                            areaMensagens.setText("Erro: O peso deve ser maior que 0.\n");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: O peso deve ser um número válido.\n");
                        return;
                    }

                    double latOrigem, latDestino, longOrigem, longDestino;
                    try {
                        latOrigem = Double.parseDouble(latOrigemField.getText().trim());
                        latDestino = Double.parseDouble(latDestinoField.getText().trim());
                        longOrigem = Double.parseDouble(longOrigemField.getText().trim());
                        longDestino = Double.parseDouble(longDestinoField.getText().trim());

                        if (latOrigem < -90 || latOrigem > 90) {
                            areaMensagens.setText("Erro: A latitude de origem deve estar entre -90 e 90.\n");
                            return;
                        }
                        if (latDestino < -90 || latDestino > 90) {
                            areaMensagens.setText("Erro: A latitude de destino deve estar entre -90 e 90.\n");
                            return;
                        }
                
                        if (longOrigem < -180 || longOrigem > 180) {
                            areaMensagens.setText("Erro: A longitude de origem deve estar entre -180 e 180.\n");
                            return;
                        }
                        if (longDestino < -180 || longDestino > 180) {
                            areaMensagens.setText("Erro: A longitude de destino deve estar entre -180 e 180.\n");
                            return;
                        }

                    } catch (NumberFormatException ex) {
                        areaMensagens.setText("Erro: As coordenadas de origem ou destino devem ser números válidos.\n");
                        return;
                    }

                    boolean cargaPerigosa;
                    try {
                        cargaPerigosa = parseBooleanSafe(cargaPerigosaField.getText().trim());
                    } catch (IllegalArgumentException ex) {
                        areaMensagens.setText("Erro: A condição de carga perigosa deve ser 'true' ou 'false'.\n");
                        return;
                    }

                    TransporteCargaInanimada tci = new TransporteCargaInanimada(numero, nomeClienteField.getText().trim(), descricaoField.getText().trim(),
                            peso, latOrigem, latDestino, longOrigem, longDestino, cargaPerigosa);
                    transportesPendentes.add(tci);
                    transportes.add(tci);
                    areaMensagens.setText("Transporte de carga inanimada cadastrado com sucesso.\n");
                });
            }

            inputPanel.revalidate();
            inputPanel.repaint();
            novoTransporte.setVisible(true);
        });

        botaoLimpar.addActionListener(e -> {
            numeroField.setText("");
            nomeClienteField.setText("");
            descricaoField.setText("");
            pesoField.setText("");
            latOrigemField.setText("");
            latDestinoField.setText("");
            longOrigemField.setText("");
            longDestinoField.setText("");
            areaMensagens.setText("");
        });

        botaoDados.addActionListener(e -> {
            StringBuilder dados = new StringBuilder();
            for (Transporte t : transportesPendentes) {
                dados.append(t).append("\n");
            }
            areaMensagens.setText(dados.toString());
        });

        botaoSair.addActionListener(e -> novoTransporte.dispose());

        novoTransporte.setVisible(true);
    }

    private boolean todosProcessados = false;

    // Método para processar transportes pendentes
    public void processarTransportesPendentes() {
        JFrame processarTransportesPendentes = new JFrame("Transportes Pendentes");
        processarTransportesPendentes.setExtendedState(JFrame.MAXIMIZED_BOTH);
        processarTransportesPendentes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        processarTransportesPendentes.setLayout(new BorderLayout(10, 10));

        JLabel tituloProcessarTransportesPendentes = new JLabel("Processar Transportes Pendentes", JLabel.CENTER);
        tituloProcessarTransportesPendentes.setFont(new Font("Arial", Font.BOLD, 70));
        tituloProcessarTransportesPendentes.setForeground(new Color(255, 0, 0));
        tituloProcessarTransportesPendentes.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        processarTransportesPendentes.add(tituloProcessarTransportesPendentes, BorderLayout.NORTH);

        JTextArea mensagemArea = new JTextArea(30, 60);
        mensagemArea.setEditable(false);
        mensagemArea.setWrapStyleWord(true);
        mensagemArea.setLineWrap(true);
        mensagemArea.setBackground(new Color(255, 250, 240));
        mensagemArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mensagemArea.setFont(new Font("Arial", Font.PLAIN, 21));

        StringBuilder dados = new StringBuilder("Transportes Pendentes:\n");
        for (Transporte t : transportesPendentes) {
            dados.append(t).append("\n");
        }
        mensagemArea.setText(dados.toString());

        JScrollPane scrollPane = new JScrollPane(mensagemArea);
        processarTransportesPendentes.add(scrollPane, BorderLayout.CENTER);

        mensagemArea.setCaretPosition(0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JButton processarButton = new JButton("Processar Transportes");
        JButton terminarButton = new JButton("Terminar");

        processarButton.setFont(new Font("Arial", Font.BOLD, 25));
        processarButton.setBackground(new Color(128, 128, 128)); 
        processarButton.setForeground(Color.WHITE);

        terminarButton.setFont(new Font("Arial", Font.BOLD, 25));
        terminarButton.setBackground(Color.RED);
        terminarButton.setForeground(Color.WHITE);
        terminarButton.setOpaque(true);
        terminarButton.setBorderPainted(false);

        buttonPanel.add(processarButton);
        buttonPanel.add(terminarButton);
        processarTransportesPendentes.add(buttonPanel, BorderLayout.SOUTH);

        terminarButton.addActionListener(e -> processarTransportesPendentes.dispose());

        // Ação para o botão "Processar Transportes"
        processarButton.addActionListener(e -> {
            if (todosProcessados) {
                mensagemArea.setText("Não há mais transportes pendentes para serem processados");
            } else {
                while (!transportesPendentes.isEmpty()) {
                    Transporte t = transportesPendentes.poll();
                    Drone droneAssociado = getDroneAssociado(transportesFinalizados, t);
    
                    if (droneAssociado == null) {
                        if (!dronesDisponíveis.isEmpty()){
                            dronesDisponíveis.forEach(d1 ->{
                            if ((d1 instanceof DronePessoal && t instanceof TransportePessoal) || 
                                (d1 instanceof DroneCargaViva && t instanceof TransporteCargaViva) || 
                                (d1 instanceof DroneCargaInanimada && t instanceof TransporteCargaInanimada)){
                                transportesFinalizados.put(d1, t);
                                t.setSituacao(Estado.ALOCADO);
                                mensagemArea.append("\nAtribuindo o Drone -> "+d1.toString()+" ao Transporte -> "+t.toString());
                            } else {
                                mensagemArea.setText("Erro: Nenhum drone compatível com os transportes pendentes.");
                                transportesPendentes.add(t);
                            }
                            });
                        } else {
                            mensagemArea.setText("Não existem drones disponíveis!");
                            transportesPendentes.add(t);
                        }
                    } else {
                        mensagemArea.setText("Transporte "+t+" já tem um drone associado!");
                    }
                }
                if (transportesPendentes.isEmpty()) {
                    todosProcessados = true;
                }
            }
        });

        processarTransportesPendentes.setVisible(true); 
    }

    private Drone getDroneAssociado(HashMap<Drone, Transporte> mapDronesTransporte, Transporte transporte) {
        for (Map.Entry<Drone, Transporte> entry : mapDronesTransporte.entrySet()) {
            if (entry.getValue().getNumero() == (transporte.getNumero())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void mostrarRelatorioGeral() {
        /* 
        // Criando a janela principal para mostrar o relatório
        JFrame mostrarRelatorioGeralFrame = new JFrame("Relatório Geral");
        mostrarRelatorioGeralFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Janela maximizada
        mostrarRelatorioGeralFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela ao clicar no X
        mostrarRelatorioGeralFrame.setLayout(new BorderLayout(10, 10)); // Layout com espaçamento
    
        // Título do relatório
        JLabel tituloRelatorioGeral = new JLabel("ACME - Relatório Geral", JLabel.CENTER);
        tituloRelatorioGeral.setFont(new Font("Arial", Font.BOLD, 70));
        tituloRelatorioGeral.setForeground(new Color(255, 0, 0)); 
        tituloRelatorioGeral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mostrarRelatorioGeralFrame.add(tituloRelatorioGeral, BorderLayout.NORTH);
    
        // Área de texto para exibição do relatório
        JTextArea relatorioArea = new JTextArea(30, 60);
        relatorioArea.setEditable(false); // Não pode editar o conteúdo
        relatorioArea.setWrapStyleWord(true); // Palavras não são quebradas no meio
        relatorioArea.setLineWrap(true); // Quebra de linha automática
        relatorioArea.setBackground(new Color(255, 250, 240)); // Cor de fundo clara
        relatorioArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borda leve
        relatorioArea.setFont(new Font("Arial", Font.PLAIN, 21)); // Fonte do texto
        mostrarRelatorioGeralFrame.add(new JScrollPane(relatorioArea), BorderLayout.CENTER); // Envolvendo o JTextArea em um JScrollPane para permitir rolagem
   
        // Relatorio Geral
        StringBuilder relatorioGeral = new StringBuilder();
        dronesCadastrados.forEach((chave, valor) -> {
            relatorioGeral.append(chave).append(" - Tipo: ").append(valor).append("\n");
        });
        /* Esperando ArrayList de transportes do Fred */
        /*

        relatorioArea.setText(relatorioGeral.toString());

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), // Borda para o painel
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Espaçamento interno
        ));
    
        // Botão de "Fechar"
        JButton fecharButton = new JButton("Fechar");
        fecharButton.setFont(new Font("Arial", Font.BOLD, 25));
        fecharButton.setBackground(new Color(255, 0, 0)); // Cor vermelha para o botão
        fecharButton.setForeground(Color.WHITE);
        fecharButton.setOpaque(true);
        fecharButton.setBorderPainted(false);
    
        // Adiciona o botão ao painel de botões
        buttonPanel.add(fecharButton);
        mostrarRelatorioGeralFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        // Ação para o botão "Fechar" - Fecha a janela ao ser clicado
        fecharButton.addActionListener(e -> mostrarRelatorioGeralFrame.dispose());
    
        // Tornando a janela visível
        mostrarRelatorioGeralFrame.setVisible(true);
        */
    }

    public void mostrarTransportes() {
        // Criando a janela principal para mostrar os transportes
        JFrame mostrarTransportesFrame = new JFrame("Transportes e Drones");
        mostrarTransportesFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Janela maximizada
        mostrarTransportesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela ao clicar no X
        mostrarTransportesFrame.setLayout(new BorderLayout(10, 10)); // Layout com espaçamento
    
        // Título da janela
        JLabel tituloTransportes = new JLabel(" ACME - Relatório de Transportes e Drones", JLabel.CENTER);
        tituloTransportes.setFont(new Font("Arial", Font.BOLD, 70));
        tituloTransportes.setForeground(new Color(255, 0, 0)); 
        tituloTransportes.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mostrarTransportesFrame.add(tituloTransportes, BorderLayout.NORTH);
    
        // Área de texto para exibição do relatório
        JTextArea transportesArea = new JTextArea(30, 60);
        transportesArea.setEditable(false); // Não pode editar o conteúdo
        transportesArea.setWrapStyleWord(true); // Palavras não são quebradas no meio
        transportesArea.setLineWrap(true); // Quebra de linha automática
        transportesArea.setBackground(new Color(255, 250, 240)); // Cor de fundo clara
        transportesArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borda leve
        transportesArea.setFont(new Font("Arial", Font.PLAIN, 21)); // Fonte do texto
        mostrarTransportesFrame.add(new JScrollPane(transportesArea), BorderLayout.CENTER); // Envolvendo o JTextArea em um JScrollPane para permitir rolagem
    
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), // Borda para o painel
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Espaçamento interno
        ));
    
        // Botão de "Fechar"
        JButton fecharButton = new JButton("Fechar");
        fecharButton.setFont(new Font("Arial", Font.BOLD, 25));
        fecharButton.setBackground(new Color(255, 0, 0)); // Cor vermelha para o botão
        fecharButton.setForeground(Color.WHITE);
        fecharButton.setOpaque(true);
        fecharButton.setBorderPainted(false);
    
        // Adiciona o botão ao painel de botões
        buttonPanel.add(fecharButton);
        mostrarTransportesFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        // Ação para o botão "Fechar" - Fecha a janela ao ser clicado
        fecharButton.addActionListener(e -> mostrarTransportesFrame.dispose());
    
        // Tornando a janela visível
        mostrarTransportesFrame.setVisible(true);
    }

    CarregarTransporte ct = new CarregarTransporte() {
        @Override
        public void carregarTransporte(Transporte transporte){
            transporteCarregado = transporte;
        }
    };

    public void alterarSituacaoTransporte() {
        // Criando a janela para alterar a situação do transporte
        JFrame alterarSituacaoButton = new JFrame("Alterar Situação do Transporte");
        alterarSituacaoButton.setSize(600,400);
        alterarSituacaoButton.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        alterarSituacaoButton.setLayout(new BorderLayout(10, 10));
    
        // Título da janela
        JLabel tituloLabel = new JLabel("Alterar Situação do Transporte", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setForeground(Color.BLACK);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        alterarSituacaoButton.add(tituloLabel, BorderLayout.NORTH);
    
        // Painel central para exibir os dados do transporte
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
    
        // Campo de texto para inserir o número do transporte
        JTextField numeroTextField = new JTextField(10);
        numeroTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        centralPanel.add(new JLabel("Número do Transporte:"));
        centralPanel.add(numeroTextField);
    
        // Área de texto para exibir os dados do transporte
        JTextArea transporteInfoArea = new JTextArea(10, 40);
        transporteInfoArea.setEditable(false); // Não pode editar
        transporteInfoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        transporteInfoArea.setBackground(new Color(255, 250, 240)); // Fundo claro
        centralPanel.add(new JScrollPane(transporteInfoArea));
    
        // ComboBox para selecionar a nova situação
        String[] situacoes = {"PENDENTE", "ALOCADO", "TERMINADO", "CANCELADO"};
        JComboBox<String> situacaoComboBox = new JComboBox<>(situacoes);
        centralPanel.add(new JLabel("Nova Situação:"));
        centralPanel.add(situacaoComboBox);
    
        // Botões de ação
        JPanel buttonPanel = new JPanel();
        JButton buscarButton = new JButton("Buscar");
        JButton alterarButton = new JButton("Alterar Situação");
        JButton cancelarButton = new JButton("Cancelar");
    
        // Definir fontes e cores dos botões
        buscarButton.setFont(new Font("Arial", Font.BOLD, 16));
        buscarButton.setBackground(new Color(60, 179, 113)); // Verde
        buscarButton.setForeground(Color.WHITE);
    
        alterarButton.setFont(new Font("Arial", Font.BOLD, 16));
        alterarButton.setBackground(new Color(0, 123, 255)); // Azul
        alterarButton.setForeground(Color.WHITE);
    
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelarButton.setBackground(new Color(255, 69, 0)); // Vermelho
        cancelarButton.setForeground(Color.WHITE);
    
        buttonPanel.add(buscarButton);
        buttonPanel.add(alterarButton);
        buttonPanel.add(cancelarButton);
        centralPanel.add(buttonPanel);
    
        alterarSituacaoButton.add(centralPanel, BorderLayout.CENTER);
    
        buscarButton.addActionListener(e -> {
            Transporte tr = null;

            try {
                tr = transportes.get(0);
            } catch (IndexOutOfBoundsException ex) {
                transporteInfoArea.setText("Erro: Nenhum transporte disponível.");
                return;
            }

            int numero;
            String numeroStr = numeroTextField.getText().trim();

            try {
                numero = Integer.parseInt(numeroStr);
            } catch (NumberFormatException ex){
                transporteInfoArea.setText("Erro: O número do transporte deve ser um número válido.\n");
                return;
            }

            if (numeroStr.isEmpty()) {
                JOptionPane.showMessageDialog(alterarSituacaoButton, "Por favor, insira o número do transporte.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Transporte t : transportes){
                if (t.getNumero() == numero){
                    tr = t;
                    transporteInfoArea.setText(t.toString());
                }
            }

            if (tr == null){
                transporteInfoArea.setText("Erro: Não existe um transporte com esse número.");
            }

            ct.carregarTransporte(tr);
        });

        alterarButton.addActionListener(a -> {
            String situacaoSelecionada = (String) situacaoComboBox.getSelectedItem();
            Estado situacao = Estado.valueOf(situacaoSelecionada);

            if (transporteCarregado.getSituacao() != Estado.PENDENTE && situacao == Estado.PENDENTE){
                transporteInfoArea.setText("Erro: Se um transporte não for mais pendente, ele não pode voltar a ser pendente.");
            }
            if (transporteCarregado.getSituacao() == Estado.TERMINADO ||
                transporteCarregado.getSituacao() == Estado.CANCELADO){
                transporteInfoArea.setText("Erro: Se um transporte estiver TERMINADO OU CANCELADO, sua situação não pode mais ser alterada.");
                return;
            }

            if (situacao == transporteCarregado.getSituacao()){
                transporteInfoArea.setText("Erro: A nova situação deve ser DIFERENTE da situação atual.");
            } else {
                transporteCarregado.setSituacao(situacao);
                transporteInfoArea.setText("Situação alterada com sucesso!");
            }

        });

        cancelarButton.addActionListener(e -> {alterarSituacaoButton.dispose();});

        alterarSituacaoButton.setVisible(true);
    }  

    public void lerDadosSimulacao() {
    }

    public void salvarDados() {
    }

    public void carregarDados() {
    }

    public void finalizarSistema() {
        int resposta = JOptionPane.showConfirmDialog(
            null, 
            "Você tem certeza que deseja finalizar o sistema?", 
            "Confirmar Finalização", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0); 
        }
    }
}
