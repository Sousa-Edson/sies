/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntradaNota.Item;

import EntradaNota.AddNota;
import ModeloBeans.BeansItem;
import ModeloBeans.BeansProduto;
import ModeloBeans.ModeloTabela;

import ModeloDao.DaoItem;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author edson
 */
public class JDialog_AddItem extends javax.swing.JDialog {

    // ConexaoBD conex = new ConexaoBD();
    DefaultListModel MODELO;

    BeansItem BI = new BeansItem();
    DaoItem DI = new DaoItem();

    String resposta = "", complemento_texto = "", selecao, produto, item_id_nota, Minha_Id = "";
    int ModoItem = 1;
    String item_id_campo;
    Frame tela = null;

    JDialog_AddItem_Info info = new JDialog_AddItem_Info(tela, rootPaneCheckingEnabled);
    JDialog_AddItem_Complemento complemento = new JDialog_AddItem_Complemento(tela, rootPaneCheckingEnabled);
 int ModoTabela=0;
    /**
     * Creates new form JDialog_AddItem
     */
    public JDialog_AddItem(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        MODELO = new DefaultListModel();
        jList1.setModel(MODELO);
        PreencherProduto();
//        PreencheTabela();
        OcultaInicializacao();
    }

    public void OcultaInicializacao() {
        jComboBox_prod_id_3.setVisible(false);
        jComboBox_prod_id_1.setVisible(false);
        jScrollPane_lista.setVisible(false);
        BotoesEsconde();
        jLabel_qtd.setVisible(false);
        jTextField_qtd.setVisible(false);
        BotoesBloqueia();
        jTextField_buscar.setEditable(true);
        jTextField_buscar.requestFocus();
    }

    public void BotoesEsconde() {
        jButton_add.setVisible(false);
        jButton_alt.setVisible(false);
        jButton_com.setVisible(false);
        jButton_del.setVisible(false);
        jButton_lim.setVisible(false);
    }

    public void BotoesMostra() {
        jButton_add.setVisible(true);
        jButton_alt.setVisible(true);
        jButton_com.setVisible(true);
        jButton_del.setVisible(true);
        jButton_lim.setVisible(true);
    }

    public void BotoesBloqueia() {
        jButton_add.setEnabled(false);
        jButton_alt.setEnabled(false);
        jButton_com.setEnabled(false);
        jButton_del.setEnabled(false);

    }

    public void BotoesDesbloqueia() {
        jButton_add.setEnabled(true);
        jButton_com.setEnabled(true);

    }

    public void BuscaDados() {
        DaoItem I = new DaoItem();
        for (BeansItem u : I.BuscaDados(selecao)) {
            Minha_Id = String.valueOf(u.getId());
            jTextField_qtd.setText(u.getP_qtd());
            jComboBox_prod_id_1.setSelectedItem(u.getT_produto_id());
            jTextField_buscar.setText(u.getP_descricao());
        }
    }

    public void RecebeItemIdNota(String c, String d) {
        item_id_nota = "" + c;
        item_id_campo=d;
//        rid = id;
        System.err.println("RecebeItemIdNota --  " + item_id_nota);
    }

    public void PreencheTabela() {
        ArrayList dados;
        String[] colunas = new String[]{"Codigo ", "Produto", "UN", "Valor Un", "Quantidade", "Sub total"};
        DaoItem DaoI = new DaoItem();
        dados = (ArrayList) DaoI.PreencheTabela(item_id_nota,item_id_campo);
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        jTable_item.setModel(modelo);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
        jTable_item.setRowSorter(sorter);
        jTable_item.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable_item.getColumnModel().getColumn(0).setResizable(true);
        jTable_item.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable_item.getColumnModel().getColumn(1).setResizable(true);
        jTable_item.getColumnModel().getColumn(2).setPreferredWidth(40);
        jTable_item.getColumnModel().getColumn(2).setResizable(true);
        jTable_item.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable_item.getColumnModel().getColumn(3).setResizable(true);
        jTable_item.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable_item.getColumnModel().getColumn(4).setResizable(true);
        jTable_item.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable_item.getColumnModel().getColumn(5).setResizable(true);
        jTable_item.getTableHeader().setReorderingAllowed(false);
        jTable_item.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable_item.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void Processar_item() {
        //item_id_nota = "1";
        complemento_texto = jLabel_complemento.getText();
        complemento_texto = complemento_texto.toUpperCase();
        BI.setT_produto_id(Integer.parseInt((String) jComboBox_prod_id_3.getSelectedItem()));
        BI.setItem_id_nota(Integer.parseInt((String) item_id_nota));
        BI.setT_complemento(complemento_texto);
        String qtd = jTextField_qtd.getText();
        qtd = qtd.replace(".", "").replace(",", ".").replace(",,", "").replace(",,,", "").replace(",,,,", "");
        BI.setT_qtd(Double.parseDouble(qtd));

        if (ModoItem == 1) {
            BI.setT_item_st(1);
            DI.Salvar(BI);
        } else if (ModoItem == 3) {
            BI.setT_item_st(3);
            BI.setId(Integer.parseInt((String) Minha_Id));
            DI.Deletar(BI);
        } else {
            BI.setT_item_st(1);
            BI.setId(Integer.parseInt((String) Minha_Id));
            DI.Alterar(BI);
        }
        ModoItem = 1;
        PreencheTabela();
        jTextField_buscar.setText("");
        jTextField_qtd.setText("");
        jLabel_complemento.setText("");
        OcultaInicializacao();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane_lista = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField_buscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox_prod_id_3 = new javax.swing.JComboBox<>();
        jTextField_qtd = new javax.swing.JTextField();
        jComboBox_prod_id_1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_item = new javax.swing.JTable();
        jButton_add = new javax.swing.JButton();
        jButton_com = new javax.swing.JButton();
        jButton_lim = new javax.swing.JButton();
        jButton_alt = new javax.swing.JButton();
        jButton_del = new javax.swing.JButton();
        jLabel_complemento = new javax.swing.JLabel();
        jLabel_qtd = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar itens");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "ar", "fogo", "vento", "agua" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane_lista.setViewportView(jList1);

        jLayeredPane1.add(jScrollPane_lista, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 22, 390, 80));

        jTextField_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_buscarKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jTextField_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 0, 380, -1));

        jLabel1.setText("Produto :");
        jLayeredPane1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, -1, -1));

        jComboBox_prod_id_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_prod_id_3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prod_id_3ItemStateChanged(evt);
            }
        });
        jLayeredPane1.add(jComboBox_prod_id_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 0, -1, -1));

        jTextField_qtd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_qtdKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jTextField_qtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 0, 74, -1));

        jComboBox_prod_id_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_prod_id_1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prod_id_1ItemStateChanged(evt);
            }
        });
        jLayeredPane1.add(jComboBox_prod_id_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(571, 0, 48, -1));

        jTable_item.setAutoCreateRowSorter(true);
        jTable_item.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_itemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable_itemMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_item);

        jButton_add.setText("Adicionar");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });
        jButton_add.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton_addKeyPressed(evt);
            }
        });

        jButton_com.setText("Complemento");
        jButton_com.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_comActionPerformed(evt);
            }
        });

        jButton_lim.setText("Limpar");
        jButton_lim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_limActionPerformed(evt);
            }
        });

        jButton_alt.setText("Alterar");
        jButton_alt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_altActionPerformed(evt);
            }
        });

        jButton_del.setText("Deletar");
        jButton_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delActionPerformed(evt);
            }
        });

        jLabel_complemento.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_alt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_del)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_com)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_lim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_complemento, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton_add, jButton_alt, jButton_com, jButton_del, jButton_lim});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_add)
                    .addComponent(jButton_com)
                    .addComponent(jButton_lim)
                    .addComponent(jButton_alt)
                    .addComponent(jButton_del)
                    .addComponent(jLabel_complemento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 33, 760, 280));

        jLabel_qtd.setText("Qtd:");
        jLayeredPane1.add(jLabel_qtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 3, -1, -1));
        jLabel_qtd.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_limActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_limActionPerformed
        jTextField_buscar.setText("");
        jTextField_qtd.setText("");
        jLabel_complemento.setText("");
        OcultaInicializacao();

    }//GEN-LAST:event_jButton_limActionPerformed

    private void jButton_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addActionPerformed
        if (jTextField_qtd.getText().trim().isEmpty()) {
            jTextField_qtd.requestFocus();
        } else {
            Processar_item();
        }
    }//GEN-LAST:event_jButton_addActionPerformed

    private void jButton_altActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_altActionPerformed
        if (jTextField_qtd.getText().trim().isEmpty()) {
            jTextField_qtd.requestFocus();
        } else {
            ModoItem = 2;
            Processar_item();

        }
    }//GEN-LAST:event_jButton_altActionPerformed

    private void jButton_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delActionPerformed
        if (jTextField_qtd.getText().trim().isEmpty()) {
            jTextField_qtd.requestFocus();
        } else {
            ModoItem = 3;
            Processar_item();

        }
    }//GEN-LAST:event_jButton_delActionPerformed

    private void jTable_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_itemMouseClicked
        jScrollPane_lista.setVisible(false);
        if (evt.getClickCount() == 2) {
            selecao = "" + jTable_item.getValueAt(jTable_item.getSelectedRow(), 0);
            produto = "" + jTable_item.getValueAt(jTable_item.getSelectedRow(), 1);
            System.out.println("jTable1-- " + selecao);
            jButton_alt.setEnabled(true);
            jButton_del.setEnabled(true);
            jButton_com.setEnabled(true);
            BotoesMostra();
            BuscaDados();
            jLabel_qtd.setVisible(true);
            jTextField_qtd.setVisible(true);
            jTextField_buscar.requestFocus();
        }
    }//GEN-LAST:event_jTable_itemMouseClicked

    private void jComboBox_prod_id_3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prod_id_3ItemStateChanged

    }//GEN-LAST:event_jComboBox_prod_id_3ItemStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
//        try {
//           AddNota.jM_Atualizar.doClick();
//           this.dispose();
////            AddNota.jMenuItem1.doClick(2);
//        } catch (Error e) {
////            System.out.println("EntradaNota.Item.JDialog_AddItem.formWindowClosing() -- "+e);
//        }


    }//GEN-LAST:event_formWindowClosing

    private void jTable_itemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_itemMouseEntered

    }//GEN-LAST:event_jTable_itemMouseEntered

    private void jButton_comActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_comActionPerformed
        String codigo = (String) jComboBox_prod_id_3.getSelectedItem();
        complemento.RecebeId(codigo);
        complemento.jTextField_complemento.setText(jLabel_complemento.getText());
        complemento.setVisible(true);
    }//GEN-LAST:event_jButton_comActionPerformed

    private void jButton_addKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_addKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jButton_add.doClick();
        }
    }//GEN-LAST:event_jButton_addKeyPressed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        jComboBox_prod_id_3.setSelectedIndex(jList1.getSelectedIndex());
        jTextField_buscar.setText(jList1.getSelectedValue());
        jScrollPane_lista.setVisible(false);
        jList1.setVisible(false);
        jTextField_qtd.setEnabled(true);
        jTextField_qtd.setVisible(true);
        jLabel_qtd.setVisible(true);
        jTextField_qtd.requestFocus();
        jTextField_buscar.setEditable(false);
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextField_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_buscarKeyReleased
        resposta = jTextField_buscar.getText();

        if (evt.getKeyCode() == evt.VK_F1) {
            String n = "" + jComboBox_prod_id_1.getSelectedItem();
            info.RecebeId(n);
            info.setVisible(true);
        }

        if (resposta.length() > 1) {
            PreencherProduto();
//            jScrollPane_lista.setVisible(true);
        }
        if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
            if (resposta.length() < 1) {
                jScrollPane_lista.setVisible(false);

            }

            int a = jComboBox_prod_id_3.getSelectedIndex();
            if (a == 0) {
                System.out.println("--- identificação => " + a);
                jTextField_buscar.setEditable(true);
                jTextField_qtd.setEnabled(false);
            }
//            System.out.println("--- identificação => " + a);
        }
        jTextField_buscar.requestFocus();
    }//GEN-LAST:event_jTextField_buscarKeyReleased

    private void jComboBox_prod_id_1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prod_id_1ItemStateChanged
        try {
            //            jComboBox_prod.setSelectedIndex(jComboBox_prod_id_1.getSelectedIndex());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox_prod_id_1ItemStateChanged

    private void jTextField_qtdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_qtdKeyReleased
        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (jTextField_qtd.getText().isEmpty()) {
            } else {
                if (jButton_alt.isEnabled()) {
                    jButton_alt.setEnabled(true);
                    jButton_alt.requestFocus();
                } else {
                    BotoesMostra();
                    jButton_add.setEnabled(true);
                    jButton_add.requestFocus();
                    jButton_com.setEnabled(true);
                }
            }
        }
        String s = jTextField_qtd.getText();
        if (s.matches("^[0-9,]*$")) {
        } else {
            jTextField_qtd.setText("");
        }
        if (evt.getKeyCode() == evt.VK_F1) {
            complemento.setVisible(true);
        }
    }//GEN-LAST:event_jTextField_qtdKeyReleased
    public void PreencherProduto() {
        MODELO.removeAllElements();
        jComboBox_prod_id_3.removeAllItems();
        jComboBox_prod_id_1.removeAllItems();
        DaoItem DaoI = new DaoItem();
        for (BeansProduto B : DaoI.PreencheListaA(resposta)) {
            jComboBox_prod_id_3.addItem(String.valueOf(B.getProduto_id()));
            jComboBox_prod_id_1.addItem(String.valueOf(B.getProduto_id()));
            MODELO.addElement(B.getProduto_nome());
            jList1.setModel(MODELO);
            jScrollPane_lista.setVisible(true);
            jList1.setVisible(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_AddItem dialog = new JDialog_AddItem(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_add;
    private javax.swing.JButton jButton_alt;
    private javax.swing.JButton jButton_com;
    private javax.swing.JButton jButton_del;
    private javax.swing.JButton jButton_lim;
    private javax.swing.JComboBox<String> jComboBox_prod_id_1;
    private javax.swing.JComboBox<String> jComboBox_prod_id_3;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel_complemento;
    private javax.swing.JLabel jLabel_qtd;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_lista;
    private javax.swing.JTable jTable_item;
    public static javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_qtd;
    // End of variables declaration//GEN-END:variables
}
