/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntradaNota.Item;

import ConectaBanco.ConexaoBD;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;

/**
 *
 * @author edson
 */
public class JDialog_AddItem_Info extends javax.swing.JDialog {
// Chama conexao

    ConexaoBD conex = new ConexaoBD();
    /**
     * Creates new form JDialog_AddItem_Info
     */
    int id_referencia;
    String Rid;

    public JDialog_AddItem_Info(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void RecebeId(String id) {
        Rid = "" + id;
//        rid = id;
        System.err.println("Editar recebe id --  " + Rid);
    }

    public void Chama() {
        conex.conexao();
        conex.executaSql2("SELECT *  FROM tbl_produto"
                + " inner join tbl_usuario on id_usuario = produto_usuario_id "
                + "inner join tbl_unidade on id_unidade = produto_unidade"
                + " where produto_id ='" + Rid + "' order by produto_id asc ");
        try {
            conex.rs.last();
            id_referencia = (conex.rs.getInt("produto_id"));
             int cat = conex.rs.getInt("produto_categoria");
                String categoria = "";
                if (cat == 0) {
                    categoria = "Uso interno";

                } else if (cat == 1) {
                    categoria = "Comun / Diversos";
                } else if (cat == 2) {
                    categoria = "Kit / Agrupado";
                } else {
                    categoria = "Erro";
                }
            System.out.println("Usuario.EditUsuario.ChamaUsuario() -id_referencia-- " + id_referencia);
            System.out.println("Usuario.EditUsuario.ChamaUsuario() -Rid-- " + Rid);
            jTextField_Produto.setText(categoria);
            jTextField_Descricao.setText((conex.rs.getString("produto_descricao")));
            
             String MenuMeuValor = conex.rs.getString("produto_valor");
                Double num4 = (Double.parseDouble(MenuMeuValor));
                BigDecimal df1 = new BigDecimal(num4);
                NumberFormat nf1 = NumberFormat.getInstance();// getCurrencyInstance
                nf1.setMinimumFractionDigits(4);
                nf1.setMaximumFractionDigits(4);
                String FormatoValor = nf1.format(df1);
            jFormattedTextField_Valor.setText(FormatoValor);
            
            jFormattedTextField_Ncm.setText((conex.rs.getString("produto_ncm")));
            
            String MenuMin = conex.rs.getString("produto_min");
                Double num2 = (Double.parseDouble(MenuMin));
                BigDecimal df2 = new BigDecimal(num2);
                NumberFormat nf2 = NumberFormat.getInstance();// getCurrencyInstance
                nf2.setMinimumFractionDigits(3);
                nf2.setMaximumFractionDigits(3);
                String FormatoMin = nf2.format(df2);
                jFormattedTextField_Min.setText(FormatoMin);
            jTextArea_Observacao.setText((conex.rs.getString("produto_observacao")));
            jComboBox_Unidade.setText((conex.rs.getString("descricao_unidade")));
            jTextField_Produto.requestFocus();
            
            System.out.println("ok -- ");

        } catch (SQLException ex) {
            System.out.println("Uerro -- " + ex);
        }
        conex.desconecta();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Produto = new javax.swing.JTextField();
        jTextField_Descricao = new javax.swing.JTextField();
        jComboBox_Unidade = new javax.swing.JTextField();
        jFormattedTextField_Valor = new javax.swing.JTextField();
        jFormattedTextField_Ncm = new javax.swing.JTextField();
        jFormattedTextField_Min = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_Observacao = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informações do produto");

        jLabel1.setText("Categoria :");

        jLabel2.setText("Produto :");

        jLabel3.setText("Unidade");

        jLabel4.setText("Valor R$");

        jLabel5.setText("NCM ");

        jLabel6.setText("Est Min");

        jTextField_Produto.setEditable(false);

        jTextField_Descricao.setEditable(false);

        jComboBox_Unidade.setEditable(false);

        jFormattedTextField_Valor.setEditable(false);

        jFormattedTextField_Ncm.setEditable(false);

        jFormattedTextField_Min.setEditable(false);

        jTextArea_Observacao.setEditable(false);
        jTextArea_Observacao.setColumns(20);
        jTextArea_Observacao.setRows(5);
        jScrollPane1.setViewportView(jTextArea_Observacao);

        jLabel7.setText("Descrição");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Produto))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_Unidade)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFormattedTextField_Ncm)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jFormattedTextField_Min, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Descricao))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_Produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_Descricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_Unidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField_Ncm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField_Min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(JDialog_AddItem_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddItem_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_AddItem_Info dialog = new JDialog_AddItem_Info(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField jComboBox_Unidade;
    private javax.swing.JTextField jFormattedTextField_Min;
    private javax.swing.JTextField jFormattedTextField_Ncm;
    private javax.swing.JTextField jFormattedTextField_Valor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea_Observacao;
    private javax.swing.JTextField jTextField_Descricao;
    private javax.swing.JTextField jTextField_Produto;
    // End of variables declaration//GEN-END:variables
}
