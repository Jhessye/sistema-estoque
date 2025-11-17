/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.MovimentacaoController;
import controller.ProdutoController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import model.Entrada;
import model.Movimentacao;
import model.Produto;
import model.Saida;
import principal.TelaInicial;

/** 
 *
 * @author Jhessye Lorrayne
 */
public class InserirMovimentacao extends javax.swing.JFrame {

    /**
     * Creates new form InserirMovimentacao
     */
    public InserirMovimentacao() {
        initComponents();
        carregarListaProdutos();
        criarGrupo();
        
        this.setLocationRelativeTo(null); // ← Esta linha centraliza o JFrame
        this.setResizable(false); // ← Impede redimensionamento
        this.setTitle("Cadastrar Movimentacao");  // Título personalizado
    }
    
    private void criarGrupo(){
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(botaoTipoAlterarSaida);
        grupoTipo.add(botaoTipoAlterarEntrada);
        
        
        botaoTipoAlterarEntrada.setSelected(true);
    }
    
    private void carregarListaProdutos() {
        try {
            listaProdutoM.removeAllItems();
            for (Produto produto : ProdutoController.mostrarPordutos("Lista")) {
                listaProdutoM.addItem(produto.getNome());
            }
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + ex.getMessage());
        }
    }

    public boolean inserirMovimentacao() throws SQLException {
        String data = textDataM.getText().trim();
        String quantidades = textQuantidade.getText().trim();

        if (data.isEmpty() || quantidades.isEmpty() ||
            (!botaoTipoAlterarEntrada.isSelected() && !botaoTipoAlterarSaida.isSelected())) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            return false;
        }

        try {
            Produto produtoSelecionado = ProdutoController.mostrarPordutos("Lista")
                    .get(listaProdutoM.getSelectedIndex());

            Movimentacao m;

            if (botaoTipoAlterarEntrada.isSelected()) {
                m = new Entrada();
            } else {
                m = new Saida();
            }

            m.setProduto(produtoSelecionado);

            if (data.matches("\\d{4}-\\d{2}-\\d{2}")) {
                m.setData(data);
            } else {
                JOptionPane.showMessageDialog(null, "Formato inválido! Use o formato AAAA-MM-DD");
                return false;
            }

            int quantidade = Integer.parseInt(quantidades);
            
            
            
            boolean sucesso;
            if (botaoTipoAlterarEntrada.isSelected()) {
                if (quantidade >0 && !quantidades.isEmpty() ){
                    m.getProduto().setQuantidadeSoma(quantidade);
                }else{
                    return false;
                }
                
                sucesso = MovimentacaoController.inserirEntrada((Entrada)m);
            } else {
                
                // aceitar apenas valores positivos para saída e marcar no objeto para subtração
                if (quantidade <= 0 && !quantidades.isEmpty()) {
                    quantidade = Math.abs(quantidade);
                    m.getProduto().setQuantidadeSubtrai(quantidade); // passe valor positivo; o DAO deve subtrair do estoque
                } else {
                    return false;
                }
                sucesso = MovimentacaoController.inserirSaida((Saida)m);
            }

            return sucesso;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite valores válidos!");
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        textDataM = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        listaProdutoM = new javax.swing.JComboBox<>();
        bntOkNovaMovimentacao = new javax.swing.JButton();
        bntVoltarNovaMovimentacao = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        botaoTipoAlterarSaida = new javax.swing.JRadioButton();
        botaoTipoAlterarEntrada = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        textQuantidade = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        textDataM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDataMActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("NOVA MOVIMENTAÇÃO");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Produto");

        listaProdutoM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listaProdutoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaProdutoMActionPerformed(evt);
            }
        });

        bntOkNovaMovimentacao.setBackground(new java.awt.Color(0, 0, 0));
        bntOkNovaMovimentacao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntOkNovaMovimentacao.setForeground(new java.awt.Color(255, 255, 255));
        bntOkNovaMovimentacao.setText("OK");
        bntOkNovaMovimentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOkNovaMovimentacaoActionPerformed(evt);
            }
        });

        bntVoltarNovaMovimentacao.setBackground(new java.awt.Color(0, 0, 0));
        bntVoltarNovaMovimentacao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntVoltarNovaMovimentacao.setForeground(new java.awt.Color(255, 255, 255));
        bntVoltarNovaMovimentacao.setText("VOLTAR");
        bntVoltarNovaMovimentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntVoltarNovaMovimentacaoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Data \"AAAA-MM-DD\"");

        botaoTipoAlterarSaida.setText("Saida");
        botaoTipoAlterarSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTipoAlterarSaidaActionPerformed(evt);
            }
        });

        botaoTipoAlterarEntrada.setText("Entrada");
        botaoTipoAlterarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTipoAlterarEntradaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Quantidade");

        textQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQuantidadeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Tipo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(338, 338, 338)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(listaProdutoM, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textDataM, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(81, 81, 81)
                        .addComponent(botaoTipoAlterarSaida)
                        .addGap(51, 51, 51)
                        .addComponent(botaoTipoAlterarEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(116, 116, 116))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(357, 419, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(bntOkNovaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(bntVoltarNovaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(366, 366, 366)
                    .addComponent(jLabel4)
                    .addContainerGap(565, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(listaProdutoM, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botaoTipoAlterarEntrada)
                            .addComponent(botaoTipoAlterarSaida))
                        .addGap(87, 87, 87)
                        .addComponent(bntOkNovaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(125, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textDataM, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bntVoltarNovaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(263, 263, 263)
                    .addComponent(jLabel4)
                    .addContainerGap(281, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaProdutoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaProdutoMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaProdutoMActionPerformed

    private void bntOkNovaMovimentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOkNovaMovimentacaoActionPerformed
        try {
            // TODO add your handling code here:
            boolean cadastro = inserirMovimentacao();
            
            if (cadastro){
                //pergunta se quer continuar
                int resposta = JOptionPane.showConfirmDialog(
                    null, 
                    "Movimentacao cadastrada com sucesso!\nDeseja cadastrar outra movimentacao?",
                    "Continuar?",
                    JOptionPane.YES_NO_OPTION
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    limparCampos(); //limpar os campos
                } else {
                    TelaInicial telaInicial = new TelaInicial();
                    
                    limparCampos(); //limpar os campos
                    this.setVisible(false);
                    telaInicial.setVisible(true);
                }
            } else{
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar movimentacao!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InserirMovimentacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bntOkNovaMovimentacaoActionPerformed

    private void bntVoltarNovaMovimentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntVoltarNovaMovimentacaoActionPerformed
        TelaInicial telaInicial = null;
        try {
            telaInicial = new TelaInicial();
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

        telaInicial.setVisible(true);
        limparCampos();
        this.setVisible(false);
    }//GEN-LAST:event_bntVoltarNovaMovimentacaoActionPerformed

    public void limparCampos(){
        textDataM.setText("");
        listaProdutoM.setSelectedIndex(0);
        textQuantidade.setText("");
    }
    
    private void botaoTipoAlterarSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTipoAlterarSaidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoTipoAlterarSaidaActionPerformed

    private void botaoTipoAlterarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTipoAlterarEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoTipoAlterarEntradaActionPerformed

    private void textDataMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDataMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textDataMActionPerformed

    private void textQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textQuantidadeActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntOkNovaMovimentacao;
    private javax.swing.JButton bntVoltarNovaMovimentacao;
    private javax.swing.JRadioButton botaoTipoAlterarEntrada;
    private javax.swing.JRadioButton botaoTipoAlterarSaida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> listaProdutoM;
    private javax.swing.JFormattedTextField textDataM;
    private javax.swing.JFormattedTextField textQuantidade;
    // End of variables declaration//GEN-END:variables
}
