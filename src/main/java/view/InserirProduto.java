/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import principal.TelaInicial;
import controller.CategoriaController;
import controller.ProdutoController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import model.Categoria;
import model.Produto;
/**
 *
 * @author Jhessye Lorrayne
 */
public class InserirProduto extends javax.swing.JFrame {

    /**
     * Creates new form InserirProduto
     * @throws java.sql.SQLException
     */
    public InserirProduto() throws SQLException {
        initComponents();
        carregarListaCategorias();
        
        this.setLocationRelativeTo(null); // ← Esta linha centraliza o JFrame
        this.setResizable(false); // ← Impede redimensionamento
        this.setTitle("Cadastrar Produto");  // Título personalizado
    }
    
    public void carregarListaCategorias() {
        try {
            listaCategoriaP.removeAllItems();
            for (Categoria categoria : CategoriaController.mostrarCategorias("Lista")) {
                listaCategoriaP.addItem(categoria.getNome());
            }
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + ex.getMessage());
        }
    }
    
    public boolean cadastrarProduto() throws SQLException {

        String nome = textNomeP1.getText().trim();   // ← corrigido
        String descricao = textDescricaoP.getText().trim();
        String marca = textMarcaP.getText().trim();
        String precoStr = textPrecoP.getText().trim();
        String quantidadeStr = textQuantidadeP.getText().trim();

        
        if (nome.isEmpty() || descricao.isEmpty() || marca.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            return false;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);

            if (preco <= 0 || quantidade < 0) {
                JOptionPane.showMessageDialog(null, "Preço e quantidade devem ser positivos!");
                return false;
            }
            
            Categoria categoriaSelecionada = CategoriaController.mostrarCategorias("Lista")
                    .get(listaCategoriaP.getSelectedIndex());
            
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setMarca(marca);
            produto.setPreco(preco);
            produto.setQuantidade(quantidade);
            produto.setCategoria(categoriaSelecionada);

            
            boolean sucesso = ProdutoController.inserirProduto(produto);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto!");
            }

            return sucesso;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite valores numéricos válidos para preço e quantidade!");
            return false;
        }
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bntVoltarP = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        textQuantidadeP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textDescricaoP = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        textMarcaP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textPrecoP = new javax.swing.JTextField();
        bntOkP = new javax.swing.JButton();
        listaCategoriaP = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        textNomeP1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("CADASTRAR PRODUTO");

        bntVoltarP.setBackground(new java.awt.Color(0, 0, 0));
        bntVoltarP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntVoltarP.setForeground(new java.awt.Color(255, 255, 255));
        bntVoltarP.setText("VOLTAR");
        bntVoltarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntVoltarPActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nome");

        textQuantidadeP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQuantidadePActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Descrição");

        textDescricaoP.setColumns(20);
        textDescricaoP.setRows(5);
        jScrollPane1.setViewportView(textDescricaoP);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Marca");

        textMarcaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textMarcaPActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Quantidade");

        textPrecoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrecoPActionPerformed(evt);
            }
        });

        bntOkP.setBackground(new java.awt.Color(0, 0, 0));
        bntOkP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntOkP.setForeground(new java.awt.Color(255, 255, 255));
        bntOkP.setText("OK");
        bntOkP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOkPActionPerformed(evt);
            }
        });

        listaCategoriaP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listaCategoriaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaCategoriaPActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Preço");

        textNomeP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomeP1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Categoria");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textNomeP1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(bntOkP, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(229, 229, 229))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textPrecoP, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(textMarcaP, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(listaCategoriaP, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(textQuantidadeP, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)))
                        .addComponent(bntVoltarP, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(textNomeP1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(bntVoltarP, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(listaCategoriaP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textQuantidadeP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textMarcaP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textPrecoP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bntOkP, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntVoltarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntVoltarPActionPerformed
        // TODO add your handling code here:
        TelaInicial telaInicial = null;
        try {
            telaInicial = new TelaInicial();
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

        telaInicial.setVisible(true);
        limparCampos();
        this.setVisible(false);
    }//GEN-LAST:event_bntVoltarPActionPerformed

    private void textQuantidadePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textQuantidadePActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textQuantidadePActionPerformed

    private void textMarcaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textMarcaPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textMarcaPActionPerformed

    private void textPrecoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrecoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPrecoPActionPerformed

    private void bntOkPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOkPActionPerformed
        try {
            // TODO add your handling code here:
            boolean cadastro = cadastrarProduto();
            
            if (cadastro){
                //pergunta se quer continuar
                int resposta = JOptionPane.showConfirmDialog(
                    null, 
                    "Produto cadastrado com sucesso!\nDeseja cadastrar outro produto?",
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
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bntOkPActionPerformed

    private void listaCategoriaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaCategoriaPActionPerformed
      
    }//GEN-LAST:event_listaCategoriaPActionPerformed

    private void textNomeP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomeP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNomeP1ActionPerformed

    private void limparCampos() {
        textNomeP1.setText("");        // ← garanta que limpa o nome
        textQuantidadeP.setText("");
        textDescricaoP.setText("");
        textMarcaP.setText("");
        textPrecoP.setText("");
        listaCategoriaP.setSelectedIndex(0);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntOkP;
    private javax.swing.JButton bntVoltarP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> listaCategoriaP;
    private javax.swing.JTextArea textDescricaoP;
    private javax.swing.JTextField textMarcaP;
    private javax.swing.JTextField textNomeP1;
    private javax.swing.JTextField textPrecoP;
    private javax.swing.JTextField textQuantidadeP;
    // End of variables declaration//GEN-END:variables
}
