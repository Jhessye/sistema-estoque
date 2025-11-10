/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.CategoriaController;
import controller.ProdutoController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import model.Categoria;
import model.Produto;
import principal.TelaInicial;
import view.InserirCategoria;
import view.InserirProduto;

/**
 *
 * @author Jhessye Lorrayne
 */
public class AtualizarProduto extends javax.swing.JFrame {

    /**
     * Creates new form AtualizarProduto
     */
    public AtualizarProduto() {
        initComponents();
        preencherListaProduto();
        criarGrupo();
        listaAlterarCategoriaP.setVisible(false);
        
        this.setLocationRelativeTo(null); // ← Esta linha centraliza o JFrame
        this.setResizable(false); // ← Impede redimensionamento
        this.setTitle("Atualizar Produtos");  // Título personalizado
    }
    
    public ButtonGroup criarGrupo(){
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(botaoNomeAlterarProduto);
        grupoTipo.add(botaoMarcaAlterarProduto);
        grupoTipo.add(botaoDescricaoAlterarProduto);
        grupoTipo.add(botaoPrecoAlterarProduto);
        grupoTipo.add(botaoCategoriaAlterarProduto);
        
        botaoNomeAlterarProduto.setSelected(true);
        return grupoTipo;
    }

    public void carregarListaCategorias() {
        try {
            listaAlterarCategoriaP.removeAllItems();
            for (Categoria categoria : CategoriaController.mostrarCategorias("Lista")) {
                listaAlterarCategoriaP.addItem(categoria.getNome());
            }
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + ex.getMessage());
        }
    }
    
    private void preencherListaProduto(){
        try {
            listaAlterarProdutoP.removeAllItems();
            // Adiciona todas as categorias do ArrayList no JComboBox
            for (Produto produto : ProdutoController.mostrarPordutos("Lista")) {
                listaAlterarProdutoP.addItem(produto.getIdProduto() +" | "+ produto.getNome()+" | "+produto.getDescricao()+" | "+produto.getMarca()+" | "+produto.getPreco());
            }
        } catch (SQLException ex) {
            Logger.getLogger(InserirProduto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos: " + ex.getMessage());
        }
    }
    
    public boolean atualizarProduto() throws SQLException{
        String novoValor = textNovoValorP.getText().trim();
        
        ButtonGroup grupoTipo = criarGrupo();
        
        if (grupoTipo.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            return false;
        }

        try {
            
            Produto produtoSelecionado = ProdutoController.mostrarPordutos("Lista")
                    .get(listaAlterarProdutoP.getSelectedIndex());
            
            if(botaoNomeAlterarProduto.isSelected()){
                
                if (novoValor==null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return false;
                }
                
                produtoSelecionado.setNome(novoValor);
                ProdutoController.alterarProduto(produtoSelecionado, "nome");
                
            }else if(botaoMarcaAlterarProduto.isSelected()){
                
                if (novoValor==null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return false;
                }
                
                produtoSelecionado.setMarca(novoValor);
                ProdutoController.alterarProduto(produtoSelecionado, "marca");
                
            }else if(botaoDescricaoAlterarProduto.isSelected()){
                
                if (novoValor==null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return false;
                }
                
                produtoSelecionado.setDescricao(novoValor);
                ProdutoController.alterarProduto(produtoSelecionado, "descricao");
                
            }else if(botaoPrecoAlterarProduto.isSelected()){
                
                double preco = Double.parseDouble(novoValor);

                if (preco <= 0) {
                    JOptionPane.showMessageDialog(null, "Preço e quantidade devem ser positivos!");
                    return false;
                }
                
                if (novoValor==null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return false;
                }
                
                produtoSelecionado.setPreco(preco);
                ProdutoController.alterarProduto(produtoSelecionado, "preco");
                
            }else if(botaoCategoriaAlterarProduto.isSelected()){
                
                Categoria categoriaSelecionada = CategoriaController.mostrarCategorias("Lista")
                    .get(listaAlterarCategoriaP.getSelectedIndex());
                
                novoValor = "";
                
                produtoSelecionado.setCategoria(categoriaSelecionada);
                ProdutoController.alterarProduto(produtoSelecionado, "id_categoria");
            }
            
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite valores válidos!");
            return false;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        listaAlterarProdutoP = new javax.swing.JComboBox<>();
        textNovoValorP = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        botaoMarcaAlterarProduto = new javax.swing.JRadioButton();
        botaoNomeAlterarProduto = new javax.swing.JRadioButton();
        botaoDescricaoAlterarProduto = new javax.swing.JRadioButton();
        bntOkM = new javax.swing.JButton();
        bntVoltarM = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botaoPrecoAlterarProduto = new javax.swing.JRadioButton();
        botaoCategoriaAlterarProduto = new javax.swing.JRadioButton();
        listaAlterarCategoriaP = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Produto");

        listaAlterarProdutoP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listaAlterarProdutoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaAlterarProdutoPActionPerformed(evt);
            }
        });

        textNovoValorP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNovoValorPActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Novo Valor");

        botaoMarcaAlterarProduto.setText("Marca");
        botaoMarcaAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMarcaAlterarProdutoActionPerformed(evt);
            }
        });

        botaoNomeAlterarProduto.setText("Nome");
        botaoNomeAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNomeAlterarProdutoActionPerformed(evt);
            }
        });

        botaoDescricaoAlterarProduto.setText("Descrição");
        botaoDescricaoAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDescricaoAlterarProdutoActionPerformed(evt);
            }
        });

        bntOkM.setBackground(new java.awt.Color(0, 0, 0));
        bntOkM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntOkM.setForeground(new java.awt.Color(255, 255, 255));
        bntOkM.setText("OK");
        bntOkM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOkMActionPerformed(evt);
            }
        });

        bntVoltarM.setBackground(new java.awt.Color(0, 0, 0));
        bntVoltarM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntVoltarM.setForeground(new java.awt.Color(255, 255, 255));
        bntVoltarM.setText("VOLTAR");
        bntVoltarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntVoltarMActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ALTERAR PRODUTO");

        botaoPrecoAlterarProduto.setText("Preço");
        botaoPrecoAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPrecoAlterarProdutoActionPerformed(evt);
            }
        });

        botaoCategoriaAlterarProduto.setText("Categoria");
        botaoCategoriaAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCategoriaAlterarProdutoActionPerformed(evt);
            }
        });

        listaAlterarCategoriaP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listaAlterarCategoriaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaAlterarCategoriaPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(353, 353, 353))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(botaoMarcaAlterarProduto)
                                    .addComponent(botaoNomeAlterarProduto)
                                    .addComponent(botaoDescricaoAlterarProduto))
                                .addGap(555, 555, 555))
                            .addComponent(jLabel6)
                            .addComponent(botaoCategoriaAlterarProduto)
                            .addComponent(botaoPrecoAlterarProduto)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(listaAlterarCategoriaP, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textNovoValorP, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(listaAlterarProdutoP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(152, 152, 152))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(bntVoltarM, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(bntOkM, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(410, 410, 410))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listaAlterarProdutoP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoNomeAlterarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoMarcaAlterarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoDescricaoAlterarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoPrecoAlterarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoCategoriaAlterarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textNovoValorP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(listaAlterarCategoriaP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(bntOkM, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(bntVoltarM, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaAlterarProdutoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaAlterarProdutoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaAlterarProdutoPActionPerformed

    private void textNovoValorPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNovoValorPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNovoValorPActionPerformed

    private void botaoNomeAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNomeAlterarProdutoActionPerformed
        if (!textNovoValorP.isVisible()){
            textNovoValorP.setVisible(true);
            listaAlterarCategoriaP.setVisible(false);
        }
    }//GEN-LAST:event_botaoNomeAlterarProdutoActionPerformed

    private void botaoDescricaoAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDescricaoAlterarProdutoActionPerformed
        // TODO add your handling code here:
        if (!textNovoValorP.isVisible()){
            textNovoValorP.setVisible(true);
            listaAlterarCategoriaP.setVisible(false);
        }
    }//GEN-LAST:event_botaoDescricaoAlterarProdutoActionPerformed

    private void bntOkMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOkMActionPerformed
        try {
            // TODO add your handling code here:
            boolean atualizar = atualizarProduto();
            
            if (atualizar){
                //pergunta se quer continuar
                int resposta = JOptionPane.showConfirmDialog(
                    null, 
                    "Produto Atualizado com sucesso!\nDeseja atualizar outro produto?",
                    "Continuar?",
                    JOptionPane.YES_NO_OPTION
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    criarGrupo();
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
    }//GEN-LAST:event_bntOkMActionPerformed

    public void limparCampos(){
        textNovoValorP.setText("");
        listaAlterarProdutoP.setSelectedIndex(0);
        listaAlterarCategoriaP.setSelectedIndex(0);
        criarGrupo();
    }
    
    private void bntVoltarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntVoltarMActionPerformed
        TelaInicial telaInicial = null;

        try {
            telaInicial = new TelaInicial();
        } catch (SQLException ex) {
            Logger.getLogger(InserirCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }

        telaInicial.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bntVoltarMActionPerformed

    private void botaoMarcaAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMarcaAlterarProdutoActionPerformed
        // TODO add your handling code here:
        if (!textNovoValorP.isVisible()){
            textNovoValorP.setVisible(true);
            listaAlterarCategoriaP.setVisible(false);
        }
    }//GEN-LAST:event_botaoMarcaAlterarProdutoActionPerformed

    private void botaoPrecoAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPrecoAlterarProdutoActionPerformed
        // TODO add your handling code here:
        if (!textNovoValorP.isVisible()){
            textNovoValorP.setVisible(true);
            listaAlterarCategoriaP.setVisible(false);
        }
    }//GEN-LAST:event_botaoPrecoAlterarProdutoActionPerformed

    private void botaoCategoriaAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCategoriaAlterarProdutoActionPerformed
        // TODO add your handling code here:
        if(botaoCategoriaAlterarProduto.isSelected()){

            carregarListaCategorias();
            textNovoValorP.setVisible(false);
            listaAlterarCategoriaP.setVisible(true);

        }
    }//GEN-LAST:event_botaoCategoriaAlterarProdutoActionPerformed

    private void listaAlterarCategoriaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaAlterarCategoriaPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaAlterarCategoriaPActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntOkM;
    private javax.swing.JButton bntVoltarM;
    private javax.swing.JRadioButton botaoCategoriaAlterarProduto;
    private javax.swing.JRadioButton botaoDescricaoAlterarProduto;
    private javax.swing.JRadioButton botaoMarcaAlterarProduto;
    private javax.swing.JRadioButton botaoNomeAlterarProduto;
    private javax.swing.JRadioButton botaoPrecoAlterarProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> listaAlterarCategoriaP;
    private javax.swing.JComboBox<String> listaAlterarProdutoP;
    private javax.swing.JTextField textNovoValorP;
    // End of variables declaration//GEN-END:variables
}
