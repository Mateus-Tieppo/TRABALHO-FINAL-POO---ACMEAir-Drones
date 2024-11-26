package aplicacao;

import dados.Drone;
import dados.DroneCarga;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class ACMEAirDrones {

    public interface CarregarTransporte {
        void carregarTransporte(Transporte transporte);        
    }

    private JFrame frame;
    private JPanel panel;
    private Queue<Transporte> transportesPendentes = new LinkedList<>();
    private HashMap<String, String> dronesCadastrados = new HashMap<>();
    private HashMap<Drone, Transporte> transportes = new HashMap<>();
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
    
        
        tipoDroneFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tipoDroneFrame.setLocationRelativeTo(null);
    
        
        JLabel tituloCadastro = new JLabel("ACME - Cadastro de Drones", JLabel.CENTER);
        tituloCadastro.setFont(new Font("Arial", Font.BOLD, 70));
        tituloCadastro.setForeground(new Color(255, 0, 0));
        tipoDroneFrame.add(tituloCadastro, BorderLayout.NORTH);
    
        // Painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton pessoalButton = new JButton("Cadastrar Drone Pessoal");
        JButton cargaButton = new JButton("Cadastrar Drone de Carga");
    
        
        Font botaoFont = new Font("Arial", Font.BOLD, 50);
        pessoalButton.setFont(botaoFont);
        cargaButton.setFont(botaoFont);
    
        
        pessoalButton.setBackground(Color.LIGHT_GRAY);
        cargaButton.setBackground(Color.LIGHT_GRAY);
    
        pessoalButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); 
        cargaButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
        
        pessoalButton.setPreferredSize(new Dimension(400, 150));
        cargaButton.setPreferredSize(new Dimension(400, 150));
    
        buttonPanel.add(pessoalButton);
        buttonPanel.add(cargaButton);
        tipoDroneFrame.add(buttonPanel, BorderLayout.CENTER);
    
        pessoalButton.addActionListener(e -> {
            tipoDroneFrame.dispose();
            abrirTelaCadastroDroneEspecifico("Pessoal");
        });
    
        cargaButton.addActionListener(e -> {
            tipoDroneFrame.dispose();
            abrirTelaCadastroDroneEspecifico("Carga");
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
        JTextField nomeField = new JTextField(30);
        JTextField autonomiaField = new JTextField(30);
        JTextField capacidadeField = tipo.equals("Carga") ? new JTextField(15) : null;
    
        // Estilização dos campos de texto
        Font inputFont = new Font("Arial", Font.PLAIN, 20); // Define uma fonte maior para os campos
        codigoField.setFont(inputFont);
        nomeField.setFont(inputFont);
        autonomiaField.setFont(inputFont);
        if (capacidadeField != null) {
            capacidadeField.setFont(inputFont);
        }
    
        codigoField.setBackground(new Color(255, 255, 255)); 
        nomeField.setBackground(new Color(255, 255, 255)); 
        autonomiaField.setBackground(new Color(255, 255, 255)); 
        if (capacidadeField != null) {
            capacidadeField.setBackground(new Color(255, 255, 255)); 
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
        JLabel nomeLabel = new JLabel("Nome do Drone:");
        nomeLabel.setFont(labelFont);
        inputPanel.add(nomeLabel, gbc);
    
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(nomeField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel autonomiaLabel = new JLabel("Autonomia do Drone:");
        autonomiaLabel.setFont(labelFont);
        inputPanel.add(autonomiaLabel, gbc);
    
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(autonomiaField, gbc);
    
        if (capacidadeField != null) {
            gbc.gridx = 0; gbc.gridy = 3;
            JLabel capacidadeLabel = new JLabel("Capacidade de Carga:");
            capacidadeLabel.setFont(labelFont);
            inputPanel.add(capacidadeLabel, gbc);
    
            gbc.gridx = 1; gbc.gridy = 3;
            inputPanel.add(capacidadeField, gbc);
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
                
                String codigo = codigoField.getText().trim();
                String nome = nomeField.getText().trim();
                String autonomia = autonomiaField.getText().trim();
                String capacidade = capacidadeField != null ? capacidadeField.getText().trim() : null;
                
                
                if (codigo.isEmpty() || nome.isEmpty() || autonomia.isEmpty() || (capacidadeField != null && capacidade.isEmpty())) {
                    mensagemArea.setText("Erro: Todos os campos são obrigatórios.\n");
                    return;
                }
        
            
                if (dronesCadastrados.containsKey(codigo)) {
                    mensagemArea.setText("Erro: Já existe um drone com o código " + codigo + ".\n");
                    return;
                }
        
                
                Double autonomiaNumerica = null;
                Double capacidadeNumerica = null;
        
                try {
                    autonomiaNumerica = Double.parseDouble(autonomia);
                    if (capacidadeField != null && !capacidade.isEmpty()) {
                        capacidadeNumerica = Double.parseDouble(capacidade);
                    }
                } catch (NumberFormatException ex) {
                    mensagemArea.setText("Erro: Autonomia e Capacidade devem ser valores numéricos válidos.\n");
                    return;
                }
        
                
                String detalhes = "Nome: " + nome + ", Autonomia: " + autonomia;
                if (capacidadeField != null && capacidadeNumerica != null) {
                    detalhes += ", Capacidade: " + capacidadeNumerica;
                }
        
                
                dronesCadastrados.put(codigo, tipo + " - " + detalhes);
        
                
                mensagemArea.setText("Drone " + tipo.toLowerCase() + " cadastrado com sucesso!\n");
            } catch (Exception ex) {
                
                mensagemArea.setText("Erro inesperado ao salvar o drone: " + ex.getMessage() + "\n");
                ex.printStackTrace(); 
            }
        });        
        
        // Ação do botão Mostrar Dados
        dadosButton.addActionListener(e -> {
            try {
                if (dronesCadastrados.isEmpty()) {
                    mensagemArea.setText("Não há drones cadastrados.\n");
                } else {
                    StringBuilder listaDrones = new StringBuilder("Drones Cadastrados:\n");
                    dronesCadastrados.forEach((codigo, detalhes) -> listaDrones.append("Código: ").append(codigo).append(", ").append(detalhes).append("\n"));
                    mensagemArea.setText(listaDrones.toString());
                }
            } catch (Exception ex) {
                mensagemArea.setText("Erro ao exibir os dados dos drones: " + ex.getMessage() + "\n");
            }
        });        
    
        limparButton.addActionListener(e -> {
            codigoField.setText("");
            nomeField.setText("");
            autonomiaField.setText("");
            if (capacidadeField != null) capacidadeField.setText("");
            mensagemArea.setText("");
        });
    
        dadosButton.addActionListener(e -> {
            try {
            if (dronesCadastrados.isEmpty()) {
                mensagemArea.setText("Não há drones cadastrados.\n");
            } else {
                StringBuilder listaDrones = new StringBuilder("Drones Cadastrados:\n");
                dronesCadastrados.forEach((codigo, detalhes) -> listaDrones.append("Código: ").append(codigo).append(", ").append(detalhes).append("\n"));
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
        tipoTransporte.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
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
        novoTransporte.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
        novoTransporte.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela quando clicado

        // Criar o painel para o título
        JPanel tituloPanel = new JPanel();
        tituloPanel.setLayout(new BorderLayout()); // Usando BorderLayout para centralizar o título

        // Criar o título
        JLabel titulo = new JLabel("ACME - Cadastrar Novo Transporte", JLabel.CENTER); // Centraliza o título
        titulo.setFont(new Font("Arial", Font.BOLD, 70)); // Define o tamanho e estilo da fonte
        titulo.setForeground(Color.RED); // Cor do texto

        // Adicionar o título ao painel
        tituloPanel.add(titulo, BorderLayout.CENTER);

        // Adicionar o painel ao JFrame
        novoTransporte.add(tituloPanel, BorderLayout.NORTH); // Adiciona o painel ao topo da janela
    
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
                    transportes.put(null, tp);
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
                    transportes.put(null, tc);
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
                    transportes.put(null, tci);
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
        tituloProcessarTransportesPendentes.setForeground(new Color(255, 0, 0)); // Vermelho
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
                    Drone droneAssociado = getDroneAssociado(transportes, t);
    
                    if (droneAssociado == null) {
                        for (Map.Entry<String, String> entry : dronesCadastrados.entrySet()) {
                            String droneId = entry.getKey();
                            String droneDet = entry.getValue();
                            Drone drone = null;
                            // ENTRE AQUI
                            if (droneDet.contains("Pessoal")){
                                drone = new DronePessoal(Integer.parseInt(droneId), 100, 0, 0);
                            } else {
                                if (t instanceof TransporteCargaInanimada){
                                    drone = new DroneCargaInanimada(Integer.parseInt(droneId), 50, 0, 0, false);
                                } else if (t instanceof TransporteCargaViva) {
                                    drone = new DroneCargaViva(Integer.parseInt(droneId), 200, 0, 0, false);
                                }
                            }
                            // E AQUI NÃO ESTÁ PRONTO
                            transportes.put(drone, t);
                            t.setSituacao(Estado.ALOCADO);
                            mensagemArea.setText("Atribuindo o Drone -> " + drone.toString() + " ao Transporte -> " + t);
                            break;
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

    private static Drone getDroneAssociado(HashMap<Drone, Transporte> mapDronesTransporte, Transporte transporte) {
        for (Map.Entry<Drone, Transporte> entry : mapDronesTransporte.entrySet()) {
            if (entry.getValue().getNumero() == (transporte.getNumero())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void mostrarRelatorioGeral() {
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
    alterarSituacaoButton.setExtendedState(JFrame.MAXIMIZED_BOTH);
    alterarSituacaoButton.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    alterarSituacaoButton.setLayout(new BorderLayout(10, 10));

    // Título da janela
    JLabel tituloLabel = new JLabel("ACME - Alterar Situação do Transporte", JLabel.CENTER);
    tituloLabel.setFont(new Font("Arial", Font.BOLD, 90)); // Aumentando o tamanho da fonte
    tituloLabel.setForeground(Color.RED);
    tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Maior espaçamento
    alterarSituacaoButton.add(tituloLabel, BorderLayout.NORTH);

    // Painel central para exibir os dados do transporte
    JPanel centralPanel = new JPanel();
    centralPanel.setLayout(new GridBagLayout()); // Usando GridBagLayout para um layout mais flexível
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(20, 20, 20, 20); // Espaçamento entre os componentes

    // Campo de texto para inserir o número do transporte
    JLabel numeroLabel = new JLabel("Número do Transporte:");
    numeroLabel.setFont(new Font("Arial", Font.PLAIN, 50)); // Fonte maior
    JTextField numeroTextField = new JTextField(30); // Aumentando a largura do campo
    numeroTextField.setFont(new Font("Arial", Font.PLAIN, 24)); // Fonte maior
    
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    centralPanel.add(numeroLabel, gbc);
    gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
    centralPanel.add(numeroTextField, gbc);

    // Área de texto para exibir os dados do transporte
    JTextArea transporteInfoArea = new JTextArea(15, 60); // Aumentando a altura e a largura
    transporteInfoArea.setEditable(false); // Não pode editar
    transporteInfoArea.setFont(new Font("Arial", Font.PLAIN, 20)); // Fonte maior
    transporteInfoArea.setBackground(new Color(255, 250, 240)); // Fundo claro
    JScrollPane scrollPane = new JScrollPane(transporteInfoArea);
    
    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
    centralPanel.add(scrollPane, gbc);

    // ComboBox para selecionar a nova situação
    JLabel situacaoLabel = new JLabel("Nova Situação:");
    situacaoLabel.setFont(new Font("Arial", Font.PLAIN, )); // Fonte maior
    String[] situacoes = {"PENDENTE", "ALOCADO", "TERMINADO", "CANCELADO"};
    JComboBox<String> situacaoComboBox = new JComboBox<>(situacoes);
    situacaoComboBox.setFont(new Font("Arial", Font.PLAIN, 24)); // Fonte maior

    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
    centralPanel.add(situacaoLabel, gbc);
    gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
    centralPanel.add(situacaoComboBox, gbc);

    // Botões de ação
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1, 3, 30, 0)); // Distribuir botões de forma mais equilibrada
    JButton buscarButton = new JButton("Buscar");
    JButton alterarButton = new JButton("Alterar Situação");
    JButton cancelarButton = new JButton("Cancelar");

    // Definir fontes e cores dos botões
    buscarButton.setFont(new Font("Arial", Font.BOLD, 24)); // Fonte maior
    buscarButton.setPreferredSize(new Dimension(200, 60)); // Aumentando o tamanho do botão
    buscarButton.setBackground(new Color(128, 128, 128));
    buscarButton.setForeground(Color.WHITE);
    
    alterarButton.setFont(new Font("Arial", Font.BOLD, 24)); // Fonte maior
    alterarButton.setPreferredSize(new Dimension(200, 60)); // Aumentando o tamanho do botão
    alterarButton.setBackground(new Color(128, 128, 128));
    alterarButton.setForeground(Color.WHITE);
    
    cancelarButton.setFont(new Font("Arial", Font.BOLD, 24)); // Fonte maior
    cancelarButton.setPreferredSize(new Dimension(200, 60)); // Aumentando o tamanho do botão
    cancelarButton.setBackground(new Color(255, 0, 0)); // Vermelho
    cancelarButton.setForeground(Color.WHITE);

    buttonPanel.add(buscarButton);
    buttonPanel.add(alterarButton);
    buttonPanel.add(cancelarButton);

    gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
    centralPanel.add(buttonPanel, gbc);

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

        for (Transporte t : transportes.values()){
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
        if (transporteCarregado.getSituacao() == Estado.TERMINADO ||
            transporteCarregado.getSituacao() == Estado.ALOCADO){
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
