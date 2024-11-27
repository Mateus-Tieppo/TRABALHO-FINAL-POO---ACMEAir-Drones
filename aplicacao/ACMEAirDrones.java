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
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private PriorityQueue<Drone> dronesDisponiveis = new PriorityQueue<>(comparadorPorCodigo);
    private ArrayList<Transporte> transportes = new ArrayList<>();
    private Queue<Transporte> transportesPendentes = new LinkedList<>();
    private HashMap<Drone, Transporte> transportesFinalizados = new HashMap<>();
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

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); 

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
        tituloCadastro.setFont(new Font("Arial", Font.BOLD, 60));
        tituloCadastro.setForeground(new Color(255, 0, 0));
        tipoDroneFrame.add(tituloCadastro, BorderLayout.NORTH);
    
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
    
        JTextField codigoField = new JTextField(30);
        JTextField custoFixoField = new JTextField(30);
        JTextField autonomiaField = new JTextField(30);
        JTextField pesoMaximoField = tipo.equals("Carga Viva") ||
                                     tipo.equals("Carga Inanimada") ? new JTextField(15) : null;
        JTextField qtdPessoasField = tipo.equals("Pessoal") ? new JTextField(15) : null;
        JCheckBox climatizadoBox = tipo.equals("Carga Viva") ? new JCheckBox() : null;
        JCheckBox protecaoBox = tipo.equals("Carga Inanimada") ? new JCheckBox() : null;
    
        if (climatizadoBox != null) {
            climatizadoBox.setBounds(1, 50, 200, 30);
        }

        if (protecaoBox != null) {
            protecaoBox.setBounds(1, 50, 200, 30);
        }

        Font inputFont = new Font("Arial", Font.PLAIN, 20);
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
    
        Font labelFont = new Font("Arial", Font.BOLD, 28); 
    
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
            JLabel qtdPessoasLabel = new JLabel("Quantidade Máx. de Pessoas:");
            qtdPessoasLabel.setFont(labelFont);
            inputPanel.add(qtdPessoasLabel, gbc);
    
            gbc.gridx = 1; gbc.gridy = 3;
            inputPanel.add(qtdPessoasField, gbc);
        }

        if (climatizadoBox != null) {
            gbc.gridx = 0; gbc.gridy = 4;
            JLabel climatizadoLabel = new JLabel("O Drone é climatizado?");
            climatizadoLabel.setFont(labelFont);
            inputPanel.add(climatizadoLabel, gbc);
    
            gbc.gridx = 1; gbc.gridy = 4;
            inputPanel.add(climatizadoBox, gbc);
        }

        if (protecaoBox != null) {
            gbc.gridx = 0; gbc.gridy = 4;
            JLabel protecaoLabel = new JLabel("O Drone possui proteção?");
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
    
        salvarButton.setFont(inputFont);
        limparButton.setFont(inputFont);
        dadosButton.setFont(inputFont);
        terminarButton.setFont(inputFont);
    
        buttonPanel.add(salvarButton);
        buttonPanel.add(limparButton);
        buttonPanel.add(dadosButton);
        buttonPanel.add(terminarButton);
    
        cadastroFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        salvarButton.setFont(new Font("Arial", Font.BOLD, 25));
        limparButton.setFont(new Font("Arial", Font.BOLD, 25));
        dadosButton.setFont(new Font("Arial", Font.BOLD, 25));
        terminarButton.setFont(new Font("Arial", Font.BOLD, 25));
    
        salvarButton.setBackground(new Color(128, 128, 128));
        salvarButton.setForeground(Color.WHITE);

        limparButton.setBackground(new Color(128, 128, 128));
        limparButton.setForeground(Color.WHITE);

        dadosButton.setBackground(new Color(128, 128, 128));
        dadosButton.setForeground(Color.WHITE);
    
        terminarButton.setBackground(Color.RED);
        terminarButton.setForeground(Color.WHITE);
        terminarButton.setOpaque(true);
        terminarButton.setBorderPainted(false);
    
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
                   (qtdPessoasField != null && qtdPessoasStr.isEmpty())) {
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
                } catch (NumberFormatException n) {
                    mensagemArea.setText("Erro: O código do drone deve ser um número válido\n");
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
                    dronesDisponiveis.add(d1);
                } else if (tipo.equals("Carga Viva")){
                    Drone d1 = new DroneCargaViva(codigo, custoFixo, autonomia, pesoMaximo, climatizado);
                    dronesCadastrados.add(d1);
                    dronesDisponiveis.add(d1);
                } else if (tipo.equals("Carga Inanimada")){
                    Drone d1 = new DroneCargaInanimada(codigo, custoFixo, autonomia, pesoMaximo, protecao);
                    dronesCadastrados.add(d1);
                    dronesDisponiveis.add(d1);
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
        tipoTransporte.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tipoTransporte.setLayout(new BorderLayout(20, 10));
        tipoTransporte.setSize(900,600);
        tipoTransporte.setLocationRelativeTo(null);
        
        JLabel tituloLabel = new JLabel("ACME - Escolha o Tipo de Transporte", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 45));
        tituloLabel.setForeground(new Color(255,0,0)); 
        tipoTransporte.add(tituloLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton pessoalButton = new JButton("Transporte Pessoal");
        JButton cargaVivaButton = new JButton("Transporte de Carga Viva");
        JButton cargaInanimadaButton = new JButton("Transporte de Carga Inanimada");

        Font buttonFont = new Font("Arial", Font.BOLD, 50); 
        pessoalButton.setFont(buttonFont);
        cargaVivaButton.setFont(buttonFont);
        cargaInanimadaButton.setFont(buttonFont);

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

        tipoTransporte.add(buttonPanel, BorderLayout.CENTER);

        pessoalButton.addActionListener(e ->{
            tipoTransporte.dispose();
            cadastrarTransporte("Pessoal");
        });
        cargaVivaButton.addActionListener(e -> {
            tipoTransporte.dispose();
            cadastrarTransporte("Carga Viva");
        });
        cargaInanimadaButton.addActionListener(e -> {
            tipoTransporte.dispose();
            cadastrarTransporte("Carga Inanimada");
        });

        tipoTransporte.setVisible(true);
    }

    public void cadastrarTransporte(String tipoTransporte) {
        JFrame novoTransporte = new JFrame("Cadastrar Transporte "+ tipoTransporte);
        novoTransporte.setExtendedState(JFrame.MAXIMIZED_BOTH);
        novoTransporte.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        novoTransporte.setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("Cadastro de Transporte - Tipo: "+tipoTransporte, JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setForeground(new Color(255,0,0)); 
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        novoTransporte.add(titulo, BorderLayout.NORTH);
    
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(255,255,255));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), 
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(10, 10, 10, 10);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        grid.ipadx = 50;
        grid.ipady = 10;

        Dimension preferredSize = new Dimension(300, 40);
    
        JTextField numeroField = new JTextField();
        JTextField nomeClienteField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField pesoField = new JTextField();
        JTextField latOrigemField = new JTextField();
        JTextField latDestinoField = new JTextField();
        JTextField longOrigemField = new JTextField();
        JTextField longDestinoField = new JTextField();
        JTextField qtdPessoasField = tipoTransporte.equals("Pessoal") ? new JTextField() : null;
        JTextField tempMinimaField = tipoTransporte.equals("Carga Viva") ? new JTextField() : null;
        JTextField tempMaximaField = tipoTransporte.equals("Carga Viva") ? new JTextField() : null;
        JCheckBox cargaPerigosaBox = tipoTransporte.equals("Carga Inanimada") ? new JCheckBox() : null;

        numeroField.setPreferredSize(preferredSize);
        nomeClienteField.setPreferredSize(preferredSize);
        descricaoField.setPreferredSize(preferredSize);
        pesoField.setPreferredSize(preferredSize);
        latOrigemField.setPreferredSize(preferredSize);
        latDestinoField.setPreferredSize(preferredSize);
        longOrigemField.setPreferredSize(preferredSize);
        longDestinoField.setPreferredSize(preferredSize);

        if (qtdPessoasField != null){
            qtdPessoasField.setPreferredSize(preferredSize);
        }

        if (tempMinimaField != null){
            tempMinimaField.setPreferredSize(preferredSize);
        }

        if (tempMaximaField != null){
            tempMaximaField.setPreferredSize(preferredSize);
        }

        if (cargaPerigosaBox != null) {
            cargaPerigosaBox.setBounds(1, 50, 200, 30);
        }

        Font inputFont = new Font("Arial", Font.PLAIN, 20);
        numeroField.setFont(inputFont);
        nomeClienteField.setFont(inputFont);
        descricaoField.setFont(inputFont);
        pesoField.setFont(inputFont);
        latOrigemField.setFont(inputFont);
        latDestinoField.setFont(inputFont);
        longOrigemField.setFont(inputFont);
        longDestinoField.setFont(inputFont);

        numeroField.setBackground(new Color(255,255,255));
        nomeClienteField.setBackground(new Color(255,255,255));
        descricaoField.setBackground(new Color(255,255,255));
        pesoField.setBackground(new Color(255,255,255));
        latOrigemField.setBackground(new Color(255,255,255));
        latDestinoField.setBackground(new Color(255,255,255));
        longOrigemField.setBackground(new Color(255,255,255));
        longDestinoField.setBackground(new Color(255,255,255));

        if (qtdPessoasField != null){
            qtdPessoasField.setFont(inputFont);
            qtdPessoasField.setBackground(new Color(255,255,255));
        }
        if (tempMinimaField != null){
            tempMinimaField.setFont(inputFont);
            tempMinimaField.setBackground(new Color(255,255,255));
        }
        if (tempMaximaField != null){
            tempMaximaField.setFont(inputFont);
            tempMaximaField.setBackground(new Color(255,255,255));
        }
        if (cargaPerigosaBox != null){
            cargaPerigosaBox.setFont(inputFont);
            cargaPerigosaBox.setBackground(new Color(255,255,255));
        }

        Font labelFont = new Font("Arial", Font.BOLD, 28);

        grid.gridx = 0; grid.gridy = 0;
        JLabel numeroLabel = new JLabel("Número do Transporte:");
        numeroLabel.setFont(labelFont);
        inputPanel.add(numeroLabel, grid);

        grid.gridx = 1; grid.gridy = 0;
        inputPanel.add(numeroField, grid);

        grid.gridx = 0; grid.gridy = 1;
        JLabel nomeLabel = new JLabel("Nome do Cliente do Transporte:");
        nomeLabel.setFont(labelFont);
        inputPanel.add(nomeLabel, grid);

        grid.gridx = 1; grid.gridy = 1;
        inputPanel.add(nomeClienteField, grid);

        grid.gridx = 0; grid.gridy = 2;
        JLabel descricaoLabel = new JLabel("Descrição do Transporte:");
        descricaoLabel.setFont(labelFont);
        inputPanel.add(descricaoLabel, grid);

        grid.gridx = 1; grid.gridy = 2;
        inputPanel.add(descricaoField, grid);

        grid.gridx = 0; grid.gridy = 3;
        JLabel pesoLabel = new JLabel("Peso do Transporte:");
        pesoLabel.setFont(labelFont);
        inputPanel.add(pesoLabel, grid);

        grid.gridx = 1; grid.gridy = 3;
        inputPanel.add(pesoField, grid);

        grid.gridx = 0; grid.gridy = 4;
        JLabel latOrigemLabel = new JLabel("Latitude de Origem:");
        latOrigemLabel.setFont(labelFont);
        inputPanel.add(latOrigemLabel, grid);

        grid.gridx = 1; grid.gridy = 4;
        inputPanel.add(latOrigemField, grid);

        grid.gridx = 0; grid.gridy = 5;
        JLabel latDestinoLabel = new JLabel("Latitude de Destino:");
        latDestinoLabel.setFont(labelFont);
        inputPanel.add(latDestinoLabel, grid);

        grid.gridx = 1; grid.gridy = 5;
        inputPanel.add(latDestinoField, grid);

        grid.gridx = 0; grid.gridy = 6;
        JLabel longOrigemLabel = new JLabel("Longitude de Origem:");
        longOrigemLabel.setFont(labelFont);
        inputPanel.add(longOrigemLabel, grid);

        grid.gridx = 1; grid.gridy = 6;
        inputPanel.add(longOrigemField, grid);

        grid.gridx = 0; grid.gridy = 7;
        JLabel longDestinoLabel = new JLabel("Longitude de Destino:");
        longDestinoLabel.setFont(labelFont);
        inputPanel.add(longDestinoLabel, grid);

        grid.gridx = 1; grid.gridy = 7;
        inputPanel.add(longDestinoField, grid);

        if (qtdPessoasField != null) {
            grid.gridx = 0; grid.gridy = 8;
            JLabel qtdPessoasLabel = new JLabel("Quantidade de Pessoas:");
            qtdPessoasLabel.setFont(labelFont);
            inputPanel.add(qtdPessoasLabel, grid);

            grid.gridx = 1; grid.gridy = 8;
            inputPanel.add(qtdPessoasField, grid);
        }
        if (tempMinimaField != null) {
            grid.gridx = 0; grid.gridy = 8;
            JLabel tempMinimaLabel = new JLabel("Temperatura Mínima:");
            tempMinimaLabel.setFont(labelFont);
            inputPanel.add(tempMinimaLabel, grid);

            grid.gridx = 1; grid.gridy = 8;
            inputPanel.add(tempMinimaField, grid);
        }
        if (tempMaximaField != null) {
            grid.gridx = 0; grid.gridy = 9;
            JLabel tempMaximaLabel = new JLabel("Temperatura Máxima:");
            tempMaximaLabel.setFont(labelFont);
            inputPanel.add(tempMaximaLabel, grid);

            grid.gridx = 1; grid.gridy = 9;
            inputPanel.add(tempMaximaField, grid);
        }
        if (cargaPerigosaBox != null) {
            grid.gridx = 0; grid.gridy = 8;
            JLabel cargaPerigosaLabel = new JLabel("A Carga é Perigosa?");
            cargaPerigosaLabel.setFont(labelFont);
            inputPanel.add(cargaPerigosaLabel, grid);

            grid.gridx = 1; grid.gridy = 8;
            inputPanel.add(cargaPerigosaBox, grid);
        }

        JScrollPane scrollPane1 = new JScrollPane(inputPanel);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        novoTransporte.add(scrollPane1, BorderLayout.WEST);

        JTextArea areaMensagens = new JTextArea(30, 60);
        areaMensagens.setEditable(false);
        areaMensagens.setWrapStyleWord(true);
        areaMensagens.setLineWrap(true);
        areaMensagens.setBackground(new Color(255,250,240));
        areaMensagens.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        areaMensagens.setFont(new Font("Arial", Font.PLAIN, 21));
    
        JScrollPane scrollPane = new JScrollPane(areaMensagens);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        novoTransporte.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    
        JButton botaoConfirma = new JButton("Salvar");
        JButton botaoLimpar = new JButton("Limpar Campos");
        JButton botaoDados = new JButton("Mostrar Dados");
        JButton botaoSair = new JButton("Terminar");

        botaoConfirma.setFont(inputFont);
        botaoLimpar.setFont(inputFont);
        botaoDados.setFont(inputFont);
        botaoSair.setFont(inputFont);

        botaoConfirma.setFont(new Font("Arial", Font.BOLD, 25));
        botaoLimpar.setFont(new Font("Arial", Font.BOLD, 25));
        botaoDados.setFont(new Font("Arial", Font.BOLD, 25));
        botaoSair.setFont(new Font("Arial", Font.BOLD, 25));

        botaoConfirma.setBackground(new Color(128,128,128));
        botaoLimpar.setBackground(new Color(128,128,128));
        botaoDados.setBackground(new Color(128,128,128));
        botaoSair.setBackground(new Color(128,128,128));

        botaoConfirma.setForeground(Color.WHITE);
        botaoConfirma.setForeground(Color.WHITE);
        botaoConfirma.setForeground(Color.WHITE);
        botaoConfirma.setForeground(Color.WHITE);

        botaoSair.setBackground(Color.RED);
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setOpaque(true);
        botaoSair.setBorderPainted(false);

        buttonPanel.add(botaoConfirma);
        buttonPanel.add(botaoLimpar);
        buttonPanel.add(botaoDados);
        buttonPanel.add(botaoSair);

        novoTransporte.add(buttonPanel, BorderLayout.SOUTH);
        grid.anchor = GridBagConstraints.WEST;
    
        botaoConfirma.addActionListener(e -> {
            try {
                String numeroStr = numeroField.getText().trim();
                String nomeCliente = nomeClienteField.getText().trim();
                String descricao = descricaoField.getText().trim();
                String pesoStr = pesoField.getText().trim();
                String latOrigemStr = latOrigemField.getText().trim();
                String latDestinoStr = latDestinoField.getText().trim();
                String longOrigemStr = longOrigemField.getText().trim();
                String longDestinoStr = longDestinoField.getText().trim();
                String qtdPessoasStr = qtdPessoasField != null ? qtdPessoasField.getText().trim() : null;
                String tempMinimaStr = tempMinimaField != null ? tempMinimaField.getText().trim() : null;
                String tempMaximaStr = tempMaximaField != null ? tempMaximaField.getText().trim() : null;
                Boolean cargaPerigosa = cargaPerigosaBox != null ? cargaPerigosaBox.isSelected() : null;

                if (numeroStr.isEmpty() || nomeCliente.isEmpty() || descricao.isEmpty() ||
                    pesoStr.isEmpty() || latOrigemStr.isEmpty() || latDestinoStr.isEmpty() ||
                    longOrigemStr.isEmpty() || longDestinoStr.isEmpty() || qtdPessoasField != null &&
                    qtdPessoasStr.isEmpty() || tempMinimaField != null && tempMinimaStr.isEmpty() ||
                    tempMaximaField != null && tempMaximaStr.isEmpty()){
                        areaMensagens.setText("Erro: Todos os campos são obrigatórios.\n");
                        return;
                    }

                int numero;
                int qtdPessoas = 0;
                try {
                    numero = Integer.parseInt(numeroStr);
                    if (qtdPessoasField != null && !qtdPessoasStr.isEmpty()) {
                        qtdPessoas = Integer.parseInt(qtdPessoasStr);
                    }

                } catch (NumberFormatException ex){
                    areaMensagens.setText("Erro: O números inseridos devem ser um número válido.\n");
                    return;
                }

                for (Transporte t : transportesPendentes) {
                    if (t.getNumero() == numero) {
                        areaMensagens.setText("Erro: Já existe um transporte com esse número.\n");
                        return;
                    }
                }

                Double peso;
                Double latOrigem;
                Double latDestino;
                Double longOrigem;
                Double longDestino;
                Double tempMinima = 0.0;
                Double tempMaxima = 0.0;

                try {
                    peso = Double.parseDouble(pesoStr);
                    latOrigem = Double.parseDouble(latOrigemStr);
                    latDestino = Double.parseDouble(latDestinoStr);
                    longOrigem = Double.parseDouble(longOrigemStr);
                    longDestino = Double.parseDouble(longDestinoStr);
                    if (tempMinimaField != null && !tempMinimaStr.isEmpty()) {
                        tempMinima = Double.parseDouble(tempMinimaStr);
                    }
                    if (tempMaximaField != null && !tempMaximaStr.isEmpty()) {
                        tempMaxima = Double.parseDouble(tempMaximaStr);
                    }

                } catch (NumberFormatException ex){
                    areaMensagens.setText("Erro: O números inseridos devem ser um número válido.\n");
                    return;
                }

                if (tipoTransporte.equals("Pessoal")){
                    Transporte t = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, latDestino, longOrigem, longDestino, qtdPessoas);
                    transportesPendentes.add(t);
                    transportes.add(t);
                } else if (tipoTransporte.equals("Carga Viva")){
                    Transporte t = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, latDestino, longOrigem, longDestino, tempMinima, tempMaxima);
                    transportesPendentes.add(t);
                    transportes.add(t);
                } else if (tipoTransporte.equals("Carga Inanimada")){
                    Transporte t = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, latDestino, longOrigem, longDestino, cargaPerigosa);
                    transportesPendentes.add(t);
                    transportes.add(t);
                }

                areaMensagens.setText("Transporte "+tipoTransporte.toLowerCase()+" cadastrado com sucesso!\n");
            } catch (Exception ex){
                areaMensagens.setText("Erro inesperado ao salvar o transporte: "+ex.getMessage()+"\n");
                ex.printStackTrace();
            }
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
            qtdPessoasField.setText("");
            tempMinimaField.setText("");
            tempMaximaField.setText("");
            areaMensagens.setText("");
        });

        botaoDados.addActionListener(e -> {
            StringBuilder dados = new StringBuilder("Transportes cadastrados:\n");
            for (Transporte t : transportesPendentes) {
                dados.append(t).append("\n");
            }
            areaMensagens.setText(dados.toString());
        });

        botaoSair.addActionListener(e -> novoTransporte.dispose());

        novoTransporte.setVisible(true);
    }

    private boolean todosProcessados = false;

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

        processarButton.addActionListener(e -> {
            if (todosProcessados) {
                mensagemArea.append("Não há mais transportes pendentes para serem processados.\n");
            } else {
                while (!transportesPendentes.isEmpty()) {
                    Transporte t = transportesPendentes.poll();
                    Drone droneAssociado = getDroneAssociado(transportesFinalizados, t);
    
                    if (droneAssociado == null) {
                        if (!dronesDisponiveis.isEmpty()){
                            boolean droneAtribuido = false;
                            for (Drone d1 : dronesDisponiveis){
                                if (d1 instanceof DronePessoal && t instanceof TransportePessoal){
                                    if (((DronePessoal) d1).getQtdMaxPessoas() < ((TransportePessoal) t).getQtdPessoas()){
                                        mensagemArea.append("Erro: Não foi possível assinalar Drone de código "+d1.getCodigo()+", ao Transporte de número "+t.getNumero()+", pois a quantidade de pessoas do Transporte excede a capacidade de pessoas do Drone.\n");
                                        transportesPendentes.add(t);
                                        return;
                                    } else {
                                        transportesFinalizados.put(d1, t);
                                        t.setSituacao(Estado.ALOCADO);
                                        mensagemArea.append("\nAtribuindo o Drone - > ("+d1.toString()+") ao Transporte -> ("+t.toString()+")\n");
                                        droneAtribuido = true;
                                    }

                                } else if (d1 instanceof DroneCargaViva && t instanceof TransporteCargaViva){
                                    if (!((DroneCargaViva) d1).getClimatizado() && (((TransporteCargaViva) t).getTemperaturaMaxima() > 36 && ((TransporteCargaViva) t).getTemperaturaMinima() < 20)){
                                        mensagemArea.append("Erro: Não foi possível assinalar Drone de código "+d1.getCodigo()+", ao Transporte de número "+t.getNumero()+", pois o Drone não é climatizado, impossibilitando as temperaturas necessárias para o Transporte.\n");
                                        transportesPendentes.add(t);
                                        return;
                                    } else {
                                        transportesFinalizados.put(d1, t);
                                        t.setSituacao(Estado.ALOCADO);
                                        mensagemArea.append("\nAtribuindo o Drone - > ("+d1.toString()+") ao Transporte -> ("+t.toString()+")\n");
                                        droneAtribuido = true;
                                    }

                                } else if (d1 instanceof DroneCargaInanimada && t instanceof TransporteCargaInanimada){
                                    if (!((DroneCargaInanimada) d1).getProtecao() && ((TransporteCargaInanimada) t).getCargaPerigosa()){
                                        mensagemArea.append("Erro: Não foi possível assinalar Drone de código "+d1.getCodigo()+", ao Transporte de número "+t.getNumero()+", pois o Drone não possui proteção, impossibilitando levar a carga perigosa.\n");
                                        transportesPendentes.add(t);
                                        return;
                                    } else {
                                    transportesFinalizados.put(d1, t);
                                    t.setSituacao(Estado.ALOCADO);
                                    mensagemArea.append("\nAtribuindo o Drone - > ("+d1.toString()+") ao Transporte -> ("+t.toString()+")\n");
                                    droneAtribuido = true;
                                    }
                                }
                            } 
                            if (!droneAtribuido){
                                mensagemArea.append("Erro: Nenhum drone compatível com os transportes pendentes.\n");
                                transportesPendentes.add(t);
                            }
                        } else {
                            mensagemArea.append("Não existem drones disponíveis!\n");
                            transportesPendentes.add(t);
                        }
                    } else {
                        mensagemArea.append("Transporte "+t+" já tem um drone associado!\n");
                    }
                }
                if (transportesPendentes.isEmpty()) {
                    todosProcessados = true;
                    mensagemArea.append("Todos os transportes foram processados.\n");
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
        JFrame mostrarRelatorioGeralFrame = new JFrame("Relatório Geral");
        mostrarRelatorioGeralFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mostrarRelatorioGeralFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        mostrarRelatorioGeralFrame.setLayout(new BorderLayout(10, 10)); 
    
        JLabel tituloRelatorioGeral = new JLabel("ACME - Relatório Geral", JLabel.CENTER);
        tituloRelatorioGeral.setFont(new Font("Arial", Font.BOLD, 70));
        tituloRelatorioGeral.setForeground(new Color(255, 0, 0)); 
        tituloRelatorioGeral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mostrarRelatorioGeralFrame.add(tituloRelatorioGeral, BorderLayout.NORTH);
    
        JTextArea relatorioArea = new JTextArea(30, 60);
        relatorioArea.setEditable(false); 
        relatorioArea.setWrapStyleWord(true); 
        relatorioArea.setLineWrap(true);
        relatorioArea.setBackground(new Color(255, 250, 240)); 
        relatorioArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); 
        relatorioArea.setFont(new Font("Arial", Font.PLAIN, 21));
        mostrarRelatorioGeralFrame.add(new JScrollPane(relatorioArea), BorderLayout.CENTER); 
   
        StringBuilder relatorioGeral = new StringBuilder();
        
        relatorioGeral.append("--- DRONES: ---\n");
        for (Drone d : dronesCadastrados) {
            relatorioGeral.append(d.toString() + "\n");
        }

        relatorioGeral.append("\n-- TRANSPORTES PENDENTES: --\n");
        for (Transporte t : transportesPendentes) {
            relatorioGeral.append(t.toString() + "\n");
        }

        relatorioGeral.append("\n-- TRANSPORTES FINALIZADOS: --\n");
        transportesFinalizados.forEach((Drone d, Transporte t) ->{
            relatorioGeral.append("\nTransporte -> ("+t.toString() + "),  Drone -> (" + d.toString()+"), Custo Final = "+t.calculaCusto(d)+"\n");
        });

        relatorioArea.setText(relatorioGeral.toString());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), 
            BorderFactory.createEmptyBorder(10, 10, 10, 10) 
        ));
    
        JButton fecharButton = new JButton("Fechar");
        fecharButton.setFont(new Font("Arial", Font.BOLD, 25));
        fecharButton.setBackground(new Color(255, 0, 0)); 
        fecharButton.setForeground(Color.WHITE);
        fecharButton.setOpaque(true);
        fecharButton.setBorderPainted(false);
    
        buttonPanel.add(fecharButton);
        mostrarRelatorioGeralFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        fecharButton.addActionListener(e -> mostrarRelatorioGeralFrame.dispose());
    
        mostrarRelatorioGeralFrame.setVisible(true);
    
    }

    public void mostrarTransportes() {
        JFrame mostrarTransportesFrame = new JFrame("Transportes e Drones");
        mostrarTransportesFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        mostrarTransportesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        mostrarTransportesFrame.setLayout(new BorderLayout(10, 10)); 
    
        JLabel tituloTransportes = new JLabel(" ACME - Relatório de Transportes e Drones", JLabel.CENTER);
        tituloTransportes.setFont(new Font("Arial", Font.BOLD, 70));
        tituloTransportes.setForeground(new Color(255, 0, 0)); 
        tituloTransportes.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mostrarTransportesFrame.add(tituloTransportes, BorderLayout.NORTH);
    
        JTextArea transportesArea = new JTextArea(30, 60);
        transportesArea.setEditable(false); 
        transportesArea.setWrapStyleWord(true); 
        transportesArea.setLineWrap(true); 
        transportesArea.setBackground(new Color(255, 250, 240)); 
        transportesArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); 
        transportesArea.setFont(new Font("Arial", Font.PLAIN, 21)); 
        mostrarTransportesFrame.add(new JScrollPane(transportesArea), BorderLayout.CENTER); 
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), 
            BorderFactory.createEmptyBorder(10, 10, 10, 10) 
        ));
    
        JButton fecharButton = new JButton("Fechar");
        fecharButton.setFont(new Font("Arial", Font.BOLD, 25));
        fecharButton.setBackground(new Color(255, 0, 0)); 
        fecharButton.setForeground(Color.WHITE);
        fecharButton.setOpaque(true);
        fecharButton.setBorderPainted(false);
    
        buttonPanel.add(fecharButton);
        mostrarTransportesFrame.add(buttonPanel, BorderLayout.SOUTH);

        StringBuilder todosTransportes = new StringBuilder("-- TRANSPORTES: --\n");
        todosTransportes.append("\nTRANSPORTES PENDENTES:\n");
        for (Transporte t : transportesPendentes){
            todosTransportes.append(t.toString() + "\n");
        }
        todosTransportes.append("\nTRANSPORTES FINALIZADOS:\n");
        transportesFinalizados.forEach((Drone d, Transporte t) -> {
            todosTransportes.append("\nTransporte -> ("+t.toString() + "),  Drone -> (" + d.toString()+"), Custo Final = "+t.calculaCusto(d)+"\n");
        });
    
        transportesArea.append(todosTransportes.toString());

        fecharButton.addActionListener(e -> mostrarTransportesFrame.dispose());
    
        mostrarTransportesFrame.setVisible(true);
    }

    CarregarTransporte ct = new CarregarTransporte() {
        @Override
        public void carregarTransporte(Transporte transporte){
            transporteCarregado = transporte;
        }
    };

    public void alterarSituacaoTransporte() {
        JFrame alterarSituacaoButton = new JFrame("Alterar Situação do Transporte");
        alterarSituacaoButton.setExtendedState(JFrame.MAXIMIZED_BOTH);
        alterarSituacaoButton.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        alterarSituacaoButton.setLayout(new BorderLayout(10, 10));

        JLabel tituloLabel = new JLabel("ACME - Alterar Situação do Transporte", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 90)); 
        tituloLabel.setForeground(Color.RED);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        alterarSituacaoButton.add(tituloLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel numeroLabel = new JLabel("Número do Transporte:");
        numeroLabel.setFont(new Font("Arial", Font.PLAIN, 50)); 
        JTextField numeroTextField = new JTextField(30); 
        numeroTextField.setFont(new Font("Arial", Font.PLAIN, 24));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centralPanel.add(numeroLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        centralPanel.add(numeroTextField, gbc);

        JTextArea transporteInfoArea = new JTextArea(15, 60);
        transporteInfoArea.setEditable(false); 
        transporteInfoArea.setFont(new Font("Arial", Font.PLAIN, 20)); 
        transporteInfoArea.setBackground(new Color(255, 250, 240)); 
        transporteInfoArea.setLineWrap(true);
        transporteInfoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(transporteInfoArea);
        scrollPane.setPreferredSize(new Dimension(600,150));
        scrollPane.setMinimumSize(new Dimension(600, 150)); 
        scrollPane.setMaximumSize(new Dimension(600, 300));
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        centralPanel.add(scrollPane, gbc);

        JLabel situacaoLabel = new JLabel("Nova Situação:");
        situacaoLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        String[] situacoes = {"PENDENTE", "TERMINADO", "CANCELADO"};
        JComboBox<String> situacaoComboBox = new JComboBox<>(situacoes);
        situacaoComboBox.setFont(new Font("Arial", Font.PLAIN, 24)); 

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        centralPanel.add(situacaoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        centralPanel.add(situacaoComboBox, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0)); 
        JButton buscarButton = new JButton("Buscar");
        JButton alterarButton = new JButton("Alterar Situação");
        JButton cancelarButton = new JButton("Cancelar");

        buscarButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        buscarButton.setPreferredSize(new Dimension(200, 60)); 
        buscarButton.setBackground(new Color(128, 128, 128));
        buscarButton.setForeground(Color.WHITE);
        
        alterarButton.setFont(new Font("Arial", Font.BOLD, 24));
        alterarButton.setPreferredSize(new Dimension(200, 60)); 
        alterarButton.setBackground(new Color(128, 128, 128));
        alterarButton.setForeground(Color.WHITE);
        
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        cancelarButton.setPreferredSize(new Dimension(200, 60)); 
        cancelarButton.setBackground(new Color(255, 0, 0)); 
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
                return;
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
        JFrame lerDadosFrame = new JFrame("Ler Dados Simulação");
        lerDadosFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        lerDadosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lerDadosFrame.setLayout(new BorderLayout(10, 10));

        JLabel tituloLabel = new JLabel("ACME - Ler Dados Simulação", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 90)); 
        tituloLabel.setForeground(Color.RED);  
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        lerDadosFrame.add(tituloLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel nomeArquivoLabel = new JLabel("Nome do Arquivo:");
        nomeArquivoLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        JTextField nomeArquivoField = new JTextField(30); 
        nomeArquivoField.setFont(new Font("Arial", Font.PLAIN, 24)); 
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centralPanel.add(nomeArquivoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        centralPanel.add(nomeArquivoField, gbc);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); 
        progressBar.setPreferredSize(new Dimension(400, 30));
        progressBar.setVisible(false); 

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        centralPanel.add(progressBar, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0)); 
        JButton lerButton = new JButton("Ler Dados");
        JButton cancelarButton = new JButton("Cancelar");

        lerButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        lerButton.setPreferredSize(new Dimension(200, 60)); 
        lerButton.setBackground(new Color(128, 128, 128)); 
        lerButton.setForeground(Color.WHITE);
        
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        cancelarButton.setPreferredSize(new Dimension(200, 60)); 
        cancelarButton.setBackground(new Color(255, 0, 0));
        cancelarButton.setForeground(Color.WHITE);

        buttonPanel.add(lerButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        centralPanel.add(buttonPanel, gbc);

        lerDadosFrame.add(centralPanel, BorderLayout.CENTER);

        lerButton.addActionListener(e -> {
            String nomeArquivo = nomeArquivoField.getText().trim();
            if (nomeArquivo.isEmpty()) {
                JOptionPane.showMessageDialog(lerDadosFrame, "Por favor, insira o nome do arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            progressBar.setVisible(true);

            try {
                lerDadosAux(nomeArquivo);
                JOptionPane.showMessageDialog(lerDadosFrame, "Dados lidos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(lerDadosFrame, "Erro ao carregar os dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                progressBar.setVisible(false);
            }
        });

        cancelarButton.addActionListener(e -> {
            lerDadosFrame.dispose();
        });

        lerDadosFrame.setVisible(true);
    }

    public void lerDadosAux(String nomeArquivo) {
        Path path = Paths.get(nomeArquivo + ".csv");
        
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;
            boolean carregandoDrones = false;
            boolean carregandoTransportes = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                if (linha.equals("DRONES:")) {
                    carregandoDrones = true;
                    carregandoTransportes = false;
                    continue;
                } else if (linha.equals("TRANSPORTES:")) {
                    carregandoDrones = false;
                    carregandoTransportes = true;
                    continue;
                }

                if (carregandoDrones){
                    String[] data = linha.split(";");
                    try {
                        if (Integer.parseInt(data[0]) == 1){
                            DronePessoal d = new DronePessoal(Integer.parseInt(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Integer.parseInt(data[4]));
                            dronesCadastrados.add(d);
                        } else if (Integer.parseInt(data[0]) == 2){
                            DroneCargaInanimada d = new DroneCargaInanimada(Integer.parseInt(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Boolean.parseBoolean(data[5]));
                            dronesCadastrados.add(d);
                        } else if (Integer.parseInt(data[0]) == 3){
                            DroneCargaViva d = new DroneCargaViva(Integer.parseInt(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Boolean.parseBoolean(data[5]));
                            dronesCadastrados.add(d);
                        }
                    } catch (InputMismatchException in){
                        System.err.format("Erro: %s%n", in);
                    }
                }

                if (carregandoTransportes){
                    String data[] = linha.split(";");
                    try {
                        if (Integer.parseInt(data[0]) == 1){
                            TransportePessoal t = new TransportePessoal(Integer.parseInt(data[1]), data[2], data[3], Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), Integer.parseInt(data[9]));
                            transportes.add(t);
                        } else if (Integer.parseInt(data[0]) == 2){
                            TransporteCargaInanimada t = new TransporteCargaInanimada(Integer.parseInt(data[1]), data[2], data[3], Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), Boolean.parseBoolean(data[9]));
                            transportes.add(t);
                        } else if (Integer.parseInt(data[0]) == 3){
                            TransporteCargaViva t = new TransporteCargaViva(Integer.parseInt(data[1]), data[2], data[3], Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), Double.parseDouble(data[9]), Double.parseDouble(data[10]));
                            transportes.add(t);
                        }
                    } catch (InputMismatchException in){
                        System.err.format("Erro: %s%n", in);
                    }
                }
            }

            System.out.println("Drones carregados:");
            for (Drone d : dronesCadastrados) {
                System.out.println(d);
            }

            System.out.println("Transportes carregados:");
            for (Transporte t : transportesPendentes) {
                System.out.println(t);
            }

        } catch (IOException e) {
            System.err.format("Erro ao ler o arquivo: %s%n", e);
        }
    }

    public void salvarDados() {
        JFrame salvarDadosFrame = new JFrame("Salvar Dados");
        salvarDadosFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        salvarDadosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        salvarDadosFrame.setLayout(new BorderLayout(10, 10));

        JLabel tituloLabel = new JLabel("ACME - Salvar Dados", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 90)); 
        tituloLabel.setForeground(Color.RED);  
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        salvarDadosFrame.add(tituloLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel nomeArquivoLabel = new JLabel("Nome do Arquivo:");
        nomeArquivoLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        JTextField nomeArquivoField = new JTextField(30); 
        nomeArquivoField.setFont(new Font("Arial", Font.PLAIN, 24));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centralPanel.add(nomeArquivoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        centralPanel.add(nomeArquivoField, gbc);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); 
        progressBar.setPreferredSize(new Dimension(400, 30));
        progressBar.setVisible(false); 

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        centralPanel.add(progressBar, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0));
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        salvarButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        salvarButton.setPreferredSize(new Dimension(200, 60));
        salvarButton.setBackground(new Color(128, 128, 128)); 
        salvarButton.setForeground(Color.WHITE);
        
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        cancelarButton.setPreferredSize(new Dimension(200, 60)); 
        cancelarButton.setBackground(new Color(255, 0, 0));
        cancelarButton.setForeground(Color.WHITE);

        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        centralPanel.add(buttonPanel, gbc);

        salvarDadosFrame.add(centralPanel, BorderLayout.CENTER);

        salvarButton.addActionListener(e -> {
            String nomeArquivo = nomeArquivoField.getText().trim();
            if (nomeArquivo.isEmpty()) {
                JOptionPane.showMessageDialog(salvarDadosFrame, "Por favor, insira o nome do arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            progressBar.setVisible(true);

            try {
                salvarDadosAux(nomeArquivo);
                JOptionPane.showMessageDialog(salvarDadosFrame, "Dados salvos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(salvarDadosFrame, "Erro ao salvar os dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                progressBar.setVisible(false);
            }
        });

        cancelarButton.addActionListener(e -> {
            salvarDadosFrame.dispose(); 
        });

        salvarDadosFrame.setVisible(true);
    }

    public void salvarDadosAux(String caminho) {
        Path path = Paths.get(caminho + ".csv");
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            writer.write("DRONES:");
            writer.newLine();
        
            for (Drone d : dronesCadastrados) {
                writer.write(d.toString());
                writer.newLine();
            }
        
            writer.newLine();
            writer.write("TRANSPORTES:");
            writer.newLine();
        
            for (Transporte t : transportes) {
                writer.write(t.toString());
                writer.newLine();
            } 
        } catch (IOException e){
            System.err.format("Erro: %s%n", e);
        }
    }

    public void carregarDados() {
        JFrame carregarDadosFrame = new JFrame("Carregar Dados");
        carregarDadosFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        carregarDadosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        carregarDadosFrame.setLayout(new BorderLayout(10, 10));

        JLabel tituloLabel = new JLabel("ACME - Carregar Dados", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 90));
        tituloLabel.setForeground(Color.RED);  
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        carregarDadosFrame.add(tituloLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel nomeArquivoLabel = new JLabel("Nome do Arquivo:");
        nomeArquivoLabel.setFont(new Font("Arial", Font.PLAIN, 50)); 
        JTextField nomeArquivoField = new JTextField(30); 
        nomeArquivoField.setFont(new Font("Arial", Font.PLAIN, 24)); 
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centralPanel.add(nomeArquivoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        centralPanel.add(nomeArquivoField, gbc);

        JLabel tipoLabel = new JLabel("Tipo de Dado:");
        tipoLabel.setFont(new Font("Arial", Font.PLAIN, 50)); 
        String[] tipos = {"Drone Pessoal", "Drone Carga Viva", "Drone Carga Inanimada", "Transporte Pessoal", "Transporte Carga Viva", "Transporte Carga Inanimada"};
        JComboBox<String> tipoComboBox = new JComboBox<>(tipos);
        tipoComboBox.setFont(new Font("Arial", Font.PLAIN, 24)); 

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        centralPanel.add(tipoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        centralPanel.add(tipoComboBox, gbc);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); 
        progressBar.setPreferredSize(new Dimension(400, 30));
        progressBar.setVisible(false); 

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        centralPanel.add(progressBar, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0)); 
        JButton carregarButton = new JButton("Carregar");
        JButton cancelarButton = new JButton("Cancelar");

        carregarButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        carregarButton.setPreferredSize(new Dimension(200, 60));
        carregarButton.setBackground(new Color(128, 128, 128));
        carregarButton.setForeground(Color.WHITE);
        
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 24));
        cancelarButton.setPreferredSize(new Dimension(200, 60));
        cancelarButton.setBackground(new Color(255, 0, 0));
        cancelarButton.setForeground(Color.WHITE);

        buttonPanel.add(carregarButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        centralPanel.add(buttonPanel, gbc);

        carregarDadosFrame.add(centralPanel, BorderLayout.CENTER);

        carregarButton.addActionListener(e -> {
            String nomeArquivo = nomeArquivoField.getText().trim();
            if (nomeArquivo.isEmpty()) {
                JOptionPane.showMessageDialog(carregarDadosFrame, "Por favor, insira o nome do arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int tipo = tipoComboBox.getSelectedIndex() + 1;
            
            progressBar.setVisible(true);

            try {
                carregarDadosAux(nomeArquivo, tipo);
                JOptionPane.showMessageDialog(carregarDadosFrame, "Dados carregados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(carregarDadosFrame, "Erro ao processar os dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                progressBar.setVisible(false);
            }
        });

        cancelarButton.addActionListener(e -> {
            carregarDadosFrame.dispose();
        });

        carregarDadosFrame.setVisible(true);
    }

    public void carregarDadosAux(String caminho, int tipo) {
        Path path = Paths.get(caminho + ".csv"); 
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                try {
                    if (tipo == 1) {
                        Drone aux = new DronePessoal(Integer.parseInt(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Integer.parseInt(data[3]));
                        dronesCadastrados.add(aux);
                    } else if (tipo == 2) {
                        Drone aux = new DroneCargaViva(Integer.parseInt(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Boolean.parseBoolean(data[4]));
                        dronesCadastrados.add(aux);
                    } else if (tipo == 3) {
                        Drone aux = new DroneCargaInanimada(Integer.parseInt(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Boolean.parseBoolean(data[4]));
                        dronesCadastrados.add(aux);
                    } else if (tipo == 4) {
                        Transporte aux = new TransportePessoal(Integer.parseInt(data[0]), data[1], data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Integer.parseInt(data[8]));
                        transportesPendentes.add(aux);
                        transportes.add(aux);
                    } else if (tipo == 5) {
                        Transporte aux = new TransporteCargaViva(Integer.parseInt(data[0]), data[1], data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), Double.parseDouble(data[9]));
                        transportesPendentes.add(aux);
                        transportes.add(aux);
                    } else if (tipo == 6) {
                        Transporte aux = new TransporteCargaInanimada(Integer.parseInt(data[0]), data[1], data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Boolean.parseBoolean(data[8]));
                        transportesPendentes.add(aux);
                        transportes.add(aux);
                    }
                } catch (InputMismatchException e) {
                    System.err.format("Erro: %s%n", e);
                }
            }
        } catch (IOException e) {
            System.err.format("Erro: %s%n", e);
        }
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
