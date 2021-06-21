/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntradaProtocolo;

import EntradaNota.*;
import ConectaBanco.ConexaoBD;

import Principal.Menu;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import ModeloBeans.BeansItem;
import ModeloBeans.BeansProtocolo;

import ModeloBeans.ModeloTabela;
import ModeloDao.DaoItem;
import ModeloDao.DaoProtocolo;
import java.awt.Frame;

/**
 *
 * @author edson
 */
public class AddProtocolo extends javax.swing.JFrame {

    ConexaoBD conex = new ConexaoBD();
    BeansProtocolo BP = new BeansProtocolo();
    DaoProtocolo DP = new DaoProtocolo();
    BeansItem BI = new BeansItem();
    DaoItem DI = new DaoItem();
    Frame tela = null;
    JDialog_AddItem_Info info = new JDialog_AddItem_Info(tela, rootPaneCheckingEnabled);
    JDialog_AddItem_Complemento complemento = new JDialog_AddItem_Complemento(tela, rootPaneCheckingEnabled);

    String resposta = "", observacao = "obs", MinhaData, UltimoIdNota = "";
    String complemento_texto = "", selecao, item_id_nota, Minha_Id = "";
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    Date data = null;
    int ModoItem = 1;

    /**
     * Creates new form AddNota2
     */
    public AddProtocolo() {
        initComponents();
        Carrega2();
        Carrega2_item();
        BloqueiaCampos3();
        jComboBox_prod_id_1.setVisible(false);
        jComboBox_prod_id_3.setVisible(false);
        jComboBox4.setVisible(false);
        jLabel_observacao.setVisible(false);
        jLabel_complemento.setVisible(false);

    }

    public void Processar_item() {
        item_id_nota = UltimoIdNota;
        complemento_texto = jLabel_complemento.getText();
        complemento_texto = complemento_texto.toUpperCase();
        BI.setT_produto_id(Integer.parseInt((String) jComboBox_prod_id_3.getSelectedItem()));
        BI.setItem_id_protoloco(Integer.parseInt((String) item_id_nota));
        BI.setT_complemento(complemento_texto);
        String qtd = jTextField_qtd.getText();
        qtd = qtd.replace(",", ".").replace(",,", "").replace(",,,", "").replace(",,,,", "");
        BI.setT_qtd(Double.parseDouble(qtd));

        if (ModoItem == 1) {
            BI.setT_item_st(1);
            DI.Salvar(BI);
        } else if (ModoItem == 3) {
            BI.setT_item_st(3);
            BI.setId(Integer.parseInt((String) Minha_Id));
            DI.Deletar(BI);
        } else {
            BI.setT_item_st(2);
            BI.setId(Integer.parseInt((String) Minha_Id));
            DI.Alterar(BI);
        }
        ModoItem = 1;
        PreencheTabela();
    }

    public void PreencheTabela() {
        ArrayList dados = new ArrayList();

        conex.conexao();

        String[] colunas = new String[]{"Codigo ", "Produto", "UN", "Valor Un", "Quantidade", "Sub total"};
//item_id_nota="49";
        conex.executaSql2("SELECT * ,SUM ((produto_valor * t_qtd)) AS total FROM public.tbl_item_temp \n"
                + "                inner join public.tbl_produto on produto_id  =t_produto_id \n"
                + "                inner join public.tbl_unidade on id_unidade =produto_unidade   where item_id_protocolo=" + UltimoIdNota + " and t_item_st !=3 and t_item_st !=2 \n"
                + "                GROUP BY tbl_item_temp.id,tbl_produto.produto_id,tbl_unidade.id_unidade, produto_valor");

        try {
            conex.rs.first();
            do {
                String MinhaQtd = conex.rs.getString("t_qtd");
                Double num1 = (Double.parseDouble(MinhaQtd));
                BigDecimal df1 = new BigDecimal(num1);
                NumberFormat nf1 = NumberFormat.getInstance();// getCurrencyInstance
                nf1.setMinimumFractionDigits(3);
                nf1.setMaximumFractionDigits(3);
                String FormatoQtd = nf1.format(df1);

                String MenuMeuValor = conex.rs.getString("produto_valor");
                Double num2 = (Double.parseDouble(MenuMeuValor));
                BigDecimal df2 = new BigDecimal(num2);
                NumberFormat nf2 = NumberFormat.getInstance();// getCurrencyInstance
                nf2.setMinimumFractionDigits(4);
                nf2.setMaximumFractionDigits(4);
                String FormatoValor = nf2.format(df2);
                FormatoValor = "R$ " + FormatoValor;

                String MenuSubTotal = conex.rs.getString("total");
                Double num3 = (Double.parseDouble(MenuSubTotal));
                BigDecimal df3 = new BigDecimal(num3);
                NumberFormat nf3 = NumberFormat.getInstance();// getCurrencyInstance
                nf3.setMinimumFractionDigits(4);
                nf3.setMaximumFractionDigits(4);
                String FormatoSubTotal = nf3.format(df3);
                FormatoSubTotal = "R$ " + FormatoSubTotal;

                System.out.println("FormatoSubTotal = " + FormatoSubTotal);
                String produto = conex.rs.getString("produto_descricao") + " " + conex.rs.getString("t_complemento");
                dados.add(new Object[]{
                    conex.rs.getInt("id"), produto, conex.rs.getString("sigla_unidade"), FormatoValor,
                    FormatoQtd, FormatoSubTotal
                });

            } while (conex.rs.next());
        } catch (SQLException ex) {
              System.out.println("Erro "+ex);
//            JOptionPane.showMessageDialog(rootPane, "Jdialog_AddItem - " + ex);
//            Logger.getLogger(UsuarioJIF.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        conex.desconecta();

    }

    public void LimpaTudo() {
        jDateChooser_Data_Nota.setDate(null);
        jTextField_Hora.setText(null);
        jTextField2.setText(null);
//        jTextField3.setText(null);
        observacao = "";
        UltimoIdNota = "";
        PreencherFornecedor();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{};
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
//        jTable2.setModel(modelo);
        ListaProtocolo.jMenuItem4.doClick();
        this.dispose();

    }

    public void Validar() {

        BP.setId_protocolo(Integer.parseInt(UltimoIdNota));
        observacao = jLabel_observacao.getText();
        BP.setprotocolo_observacao(observacao);
        DP.AtualizaObs(BP);

        BP.setId_protocolo(Integer.parseInt(UltimoIdNota));
        DP.Validar(BP);
        DP.LimpaValidar(BP);
        VerificaValidacao();
    }

    public void VerificaValidacao() {

        System.out.println("chamou - VerificaValidacao");
        conex.conexao();
        conex.executaSql2(" SELECT item_id FROM public.tbl_item  where item_protocolo_id=" + UltimoIdNota + "");
        try {
            conex.rs.first();
            String Verificacao = conex.rs.getString("item_id");
            System.out.println("EntradaNota.AddNota.VerificaValidacao()");
            BP.setId_protocolo(Integer.parseInt(UltimoIdNota));
            BP.setprotocolo_status(8);
            //JOptionPane.showMessageDialog(rootPane, "VerificaValidacao - Verificacao = " + Verificacao);
            DP.ValidaStatusprotocolo(BP);
        } catch (SQLException ex) {
            System.out.println("VerificaValidacao " + ex);
            BP.setId_protocolo(Integer.parseInt(UltimoIdNota));
            BP.setprotocolo_status(9);
            DP.ValidaStatusprotocolo(BP);
            //JOptionPane.showMessageDialog(rootPane, "VerificaValidacao - ex = " + ex);
        }
        conex.desconecta();
    }

    public void PreencherTotal() {
        System.out.println("chamou - PreencherTotal");
        conex.conexao();
        conex.executaSql2(" SELECT SUM ((produto_valor * t_qtd)) AS total FROM public.tbl_item_temp \n"
                + "                inner join public.tbl_produto on produto_id  =t_produto_id \n"
                + "               where item_id_nota=" + UltimoIdNota + " and t_item_st !=3 and t_item_st !=2\n"
                + "                GROUP BY tbl_item_temp.item_id_nota");
        try {
            conex.rs.first();
            String MeuTotal = conex.rs.getString("total");
            Double num4 = (Double.parseDouble(MeuTotal));
            BigDecimal df4 = new BigDecimal(num4);
            NumberFormat nf4 = NumberFormat.getInstance();// getCurrencyInstance
            nf4.setMinimumFractionDigits(4);
            nf4.setMaximumFractionDigits(4);
            String FormatoTotal = nf4.format(df4);
            FormatoTotal = "R$ " + FormatoTotal;
//            jLabel_Total.setText(FormatoTotal);
            System.err.println("texto " + conex.rs.getString("total"));

            System.out.println("carregado ");
        } catch (SQLException ex) {
            System.out.println("PreencherTotal " + ex);
        }
        conex.desconecta();

    }

    public void VerificaHora() {
        String s = jTextField_Hora.getText().replace(":", "");
        if (s.length() == 1 | s.length() == 3 | s.length() == 5 | s.length() > 7) {
            jTextField_Hora.setText("");
            jTextField_Hora.requestFocus();

        }
    }

    public void AjustaMinhaHora() {
        String s = jTextField_Hora.getText().replace(":", "");
        String formatado = "";
        if (s.matches("^[0-9]*$")) {
            if (s.length() == 4) {
                formatado = s.substring(0, 2) + ":" + s.substring(2, 4);
                jTextField_Hora.setText(formatado);
            } else if (s.length() == 6) {
                formatado = s.substring(0, 2) + ":" + s.substring(2, 4) + ":" + s.substring(4, 6);
                jTextField_Hora.setText(formatado);
            } else if (s.length() < 6) {

            } else {
                jTextField_Hora.setText("");
                jTextField_Hora.requestFocus();
            }
        } else {
            jTextField_Hora.setText("");
        }
    }

    public void Processar() {

//        BP.setNota_id_cfop(Integer.parseInt((String) jComboBox2.getSelectedItem()));
        BP.setprotocolo_id_fornecedor(Integer.parseInt((String) jComboBox4.getSelectedItem()));
        if (jDateChooser_Data_Nota.getDate() == (null)) {
            MinhaData = ("");
        } else {
            data = jDateChooser_Data_Nota.getDate();
            MinhaData = (formato.format(data));
        }
        BP.setprotocolo_data(MinhaData);

        BP.setprotocolo_hora(jTextField_Hora.getText());
        BP.setprotocolo_numero(jTextField2.getText());
//        BP.setNota_chave(jTextField3.getText());
//        String total = jLabel_Total.getText();
//        total = total.replace(",", ".").replace("R$ ", "").replace("R$", "").trim();
//        BP.setNota_total(Double.parseDouble(total));
        BP.setprotocolo_status(9);
        BP.setprotocolo_id_usuario(Integer.parseInt(Menu.jLabelIdUsuario.getText()));
        observacao = jLabel_observacao.getText();
        BP.setprotocolo_observacao(observacao);
        DP.Processar(BP);
        CarregaUltimo();
        ListaNota.jMenuItem4.doClick();
    }

    public void CarregaUltimo() {
        conex.conexao();
        conex.executaSql2(" SELECT *"
                + "  FROM public.tbl_protocolo  \n"
                + "  order by id_protocolo  asc");
        try {
            conex.rs.last();
            UltimoIdNota = conex.rs.getString("id_protocolo");
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("erro " + ex);
        }
        conex.desconecta();
    }

    public void PreencherFornecedor() {
        System.out.println("chamou - PreencherFornecedor");
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_fornecedor where   fornecedor_status=1  \n"
                + "  order by fornecedor_id asc");
        try {
            conex.rs.first();
            jComboBox3.removeAllItems();
            jComboBox4.removeAllItems();

            do {
                jComboBox3.addItem(conex.rs.getString("fornecedor_nome"));
                jComboBox4.addItem(conex.rs.getString("fornecedor_id"));
                System.out.println("produto_produto " + conex.rs.getString("fornecedor_nome"));
            } while (conex.rs.next());
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("EntradaNota.JDialog_AddItem.PreencheUnidade()");
        }
        conex.desconecta();

    }

    public void BloqueiaCampos1() {
        jB_add.setVisible(false); jB_alt.setVisible(false); jB_com.setVisible(false); jB_del.setVisible(false); jB_lim.setVisible(false);
        jTextField_buscar.setVisible(false);jLabel_qtd.setVisible(false);jTextField_qtd.setVisible(false);jComboBox_prod.setVisible(false);
                
        //  jTable2.setEnabled(false);
//        jButton4.setEnabled(false); 
        jLabel1.setVisible(false);
        jTextField_buscar.setVisible(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false);
        jPanel2.setEnabled(false);
        DesbloqueiaCampos2();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{};
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        jTable_item.setModel(modelo);
    }

    public void DesbloqueiaCampos1() {
        //  jTable2.setEnabled(true);
        //jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        jPanel2.setEnabled(true);
    }

    public void BloqueiaCampos2() {
//        jComboBox1.setEnabled(false);
//        jComboBox2.setEnabled(false);
        jComboBox3.setEnabled(false);
        jComboBox4.setEnabled(false);
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jDateChooser_Data_Nota.setEnabled(false);
        jTextField_Hora.setEnabled(false);
        jTextField2.setEnabled(false);
//        jTextField3.setEnabled(false);
//        jLabel1.setEnabled(false);
        jLabel2.setEnabled(false);
        jLabel3.setEnabled(false);
        jTextField_buscar.setVisible(true);
        jTextField_buscar.requestFocus();
        jLabel1.setVisible(true);
        DesbloqueiaCampos1();
    }

    public void DesbloqueiaCampos2() {
//        jComboBox1.setEnabled(true);
//        jComboBox2.setEnabled(true);
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jDateChooser_Data_Nota.setEnabled(true);
        jTextField_Hora.setEnabled(true);
        jTextField2.setEnabled(true);
//        jTextField3.setEnabled(true);
//        jLabel1.setEnabled(true);
        jLabel2.setEnabled(true);
        jLabel3.setEnabled(true);
//        jLabel4.setEnabled(true);
    }

    public void DesbloqueiaCampos3() {
        jB_add.setEnabled(true);
        jB_com.setEnabled(true);
    }

    public void BloqueiaCampos3() {
        jB_add.setEnabled(false);
        jB_com.setEnabled(false);
//        jButton3.setEnabled(false);
        jB_alt.setEnabled(false);
        jB_del.setEnabled(false);
        //PreencheTabela();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jDateChooser_Data_Nota = new com.toedter.calendar.JDateChooser();
        jTextField_Hora = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel_observacao = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_item = new javax.swing.JTable();
        jComboBox_prod_id_3 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jTextField_buscar = new javax.swing.JTextField();
        jComboBox_prod = new javax.swing.JComboBox<>();
        jTextField_qtd = new javax.swing.JTextField();
        jLabel_qtd = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jB_add = new javax.swing.JButton();
        jB_com = new javax.swing.JButton();
        jB_lim = new javax.swing.JButton();
        jB_alt = new javax.swing.JButton();
        jB_del = new javax.swing.JButton();
        jComboBox_prod_id_1 = new javax.swing.JComboBox<>();
        jLabel_complemento = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jTextField1.setText("jTextField1");

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar Protocolo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jDateChooser_Data_Nota.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jDateChooser_Data_NotaHierarchyChanged(evt);
            }
        });
        jDateChooser_Data_Nota.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jDateChooser_Data_NotaAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jDateChooser_Data_NotaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jDateChooser_Data_Nota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateChooser_Data_NotaMouseClicked(evt);
            }
        });
        jDateChooser_Data_Nota.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser_Data_NotaPropertyChange(evt);
            }
        });
        jDateChooser_Data_Nota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateChooser_Data_NotaKeyPressed(evt);
            }
        });
        jDateChooser_Data_Nota.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                jDateChooser_Data_NotaVetoableChange(evt);
            }
        });

        jTextField_Hora.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_HoraFocusLost(evt);
            }
        });
        jTextField_Hora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_HoraActionPerformed(evt);
            }
        });
        jTextField_Hora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_HoraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_HoraKeyReleased(evt);
            }
        });

        jButton1.setText("Data");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Hora");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Fornecedor :");

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox3KeyPressed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Protocolo n° :");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("Processar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel_observacao.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser_Data_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jLabel_observacao)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1)
                            .addComponent(jDateChooser_Data_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(32, 32, 32)
                        .addComponent(jLabel_observacao))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton5.setText("Informações adicionais");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 51, 255));
        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Validar protocolo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        jScrollPane2.setViewportView(jTable_item);

        jComboBox_prod_id_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_prod_id_3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prod_id_3ItemStateChanged(evt);
            }
        });

        jTextField_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_buscarKeyReleased(evt);
            }
        });

        jComboBox_prod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prodItemStateChanged(evt);
            }
        });
        jComboBox_prod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox_prodFocusLost(evt);
            }
        });
        jComboBox_prod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox_prodKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox_prodKeyReleased(evt);
            }
        });

        jTextField_qtd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_qtdKeyReleased(evt);
            }
        });

        jLabel_qtd.setText("Qtd:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jComboBox_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_qtd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_qtd, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTextField_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_qtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_qtd))
                    .addComponent(jComboBox_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Produto :");

        jB_add.setText("Adicionar");
        jB_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_addActionPerformed(evt);
            }
        });
        jB_add.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jB_addKeyPressed(evt);
            }
        });

        jB_com.setText("Complemento");
        jB_com.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_comActionPerformed(evt);
            }
        });

        jB_lim.setText("Limpar");
        jB_lim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_limActionPerformed(evt);
            }
        });

        jB_alt.setText("Alterar");
        jB_alt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_altActionPerformed(evt);
            }
        });

        jB_del.setText("Deletar");
        jB_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_delActionPerformed(evt);
            }
        });

        jComboBox_prod_id_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_prod_id_1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_prod_id_1ItemStateChanged(evt);
            }
        });

        jLabel_complemento.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel_complemento, javax.swing.GroupLayout.DEFAULT_SIZE, 8, Short.MAX_VALUE)
                        .addGap(156, 156, 156))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jB_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_alt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_del)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_com)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_lim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox_prod_id_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_prod_id_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel1))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel_complemento)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_add)
                    .addComponent(jB_com)
                    .addComponent(jB_lim)
                    .addComponent(jComboBox_prod_id_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_alt)
                    .addComponent(jB_del)
                    .addComponent(jComboBox_prod_id_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Prencher tabela");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser_Data_NotaHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jDateChooser_Data_NotaHierarchyChanged

    }//GEN-LAST:event_jDateChooser_Data_NotaHierarchyChanged

    private void jDateChooser_Data_NotaAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jDateChooser_Data_NotaAncestorMoved

    }//GEN-LAST:event_jDateChooser_Data_NotaAncestorMoved

    private void jDateChooser_Data_NotaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jDateChooser_Data_NotaAncestorAdded

    }//GEN-LAST:event_jDateChooser_Data_NotaAncestorAdded

    private void jDateChooser_Data_NotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser_Data_NotaMouseClicked
        //  ManipulaData();        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser_Data_NotaMouseClicked

    private void jDateChooser_Data_NotaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_Data_NotaPropertyChange
        // ManipulaData();
    }//GEN-LAST:event_jDateChooser_Data_NotaPropertyChange

    private void jDateChooser_Data_NotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser_Data_NotaKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            //   jComboBox_Tipo_Fornecedor.requestFocus();
        }
    }//GEN-LAST:event_jDateChooser_Data_NotaKeyPressed

    private void jDateChooser_Data_NotaVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_jDateChooser_Data_NotaVetoableChange

    }//GEN-LAST:event_jDateChooser_Data_NotaVetoableChange

    private void jTextField_HoraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_HoraFocusLost
        if (jTextField_Hora.getText().isEmpty()) {
            jTextField_Hora.setText("  :  ");
        } else if (jTextField_Hora.getText().equals("  :  ")) {
            jTextField_Hora.setText("  :  ");
        } else {
            VerificaHora();
        }
    }//GEN-LAST:event_jTextField_HoraFocusLost

    private void jTextField_HoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_HoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_HoraActionPerformed

    private void jTextField_HoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_HoraKeyPressed

        if (jTextField_Hora.getText().length() >= 5) {

        }

    }//GEN-LAST:event_jTextField_HoraKeyPressed

    private void jTextField_HoraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_HoraKeyReleased
        AjustaMinhaHora();
    }//GEN-LAST:event_jTextField_HoraKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        BloqueiaCampos2();
//        jLabel5.setText("Nota em processamento ");
        Processar();
//        PreencheTabela();
        //UltimoIdNota="4";
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTextField_Hora.getText().isEmpty()) {
            String Relogio = (Menu.jLabelHora.getText());
//            String MenuMinhaHoraSistema = (String.format("%1$tM:%1$tS", Relogio)); /// %1$tM:%1$tS
            jTextField_Hora.setText(Relogio);
        } else {
            jTextField_Hora.setText(null);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        try {
            jComboBox4.setSelectedIndex(jComboBox3.getSelectedIndex());
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        BloqueiaCampos1();
//        jLabel5.setText(" ");
//        jComboBox1.requestFocus();
        Validar();
        LimpaTudo();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        PreencheTabela();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed
        if (evt.getKeyCode() == evt.VK_F5) {
            PreencherFornecedor();
        }
    }//GEN-LAST:event_jComboBox3KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jDateChooser_Data_Nota.getDate() == (null)) {
            try {
                data = formato.parse(Menu.jLabelData.getText());
                jDateChooser_Data_Nota.setDate(data);
            } catch (ParseException ex) {
                //                Logger.getLogger(MovimentoJIF.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jDateChooser_Data_Nota.setDate(null);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        String s = jTextField2.getText();
        String formatado = "";
        if (evt.getKeyCode() == evt.VK_DELETE | evt.getKeyCode() == evt.VK_BACK_SPACE | evt.getKeyCode() == evt.VK_SPACE | evt.getKeyCode() == evt.VK_RIGHT | evt.getKeyCode() == evt.VK_LEFT) {

        } else {
            if (s.matches("^[0-9]*$")) {
                if (s.length() < 9) {
                } else if (s.length() > 10) {
                    formatado = s.substring(0, 10);
                    jTextField2.setText(formatado);
                }
            } else {
                jTextField2.setText("");
            }
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JDialog_AddItem_Obs AddItem_Obs = new JDialog_AddItem_Obs(this, rootPaneCheckingEnabled);
        AddItem_Obs.RecebeObs(jLabel_observacao.getText());
        AddItem_Obs.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        BloqueiaCampos1();
        LimpaTudo();
    }//GEN-LAST:event_formWindowClosing

    private void jTable_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_itemMouseClicked
        if (evt.getClickCount() == 2) {
            //   selecao = "" + jTable_item.getValueAt(jTable_item.getSelectedRow(), 0);
            //  System.out.println("jTable1-- " + selecao);

            jB_alt.setEnabled(true);
            jB_del.setEnabled(true);
            jB_add.setEnabled(false);
            Carrega1_item();
            //  BuscaDados();
            //            try {
            ////            String busca = "10";
            //                jComboBox3.setSelectedItem(selecao);
            //                System.err.println("selecao " + selecao);
            ////            System.out.println("escreve " + jComboBox3.getSelectedItem());
            //
            //            } catch (Exception e) {
            //                System.out.println("Exception " + e);
            //            }
        }
    }//GEN-LAST:event_jTable_itemMouseClicked

    private void jTable_itemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_itemMouseEntered

    }//GEN-LAST:event_jTable_itemMouseEntered

    private void jComboBox_prod_id_3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prod_id_3ItemStateChanged

    }//GEN-LAST:event_jComboBox_prod_id_3ItemStateChanged

    private void jTextField_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_buscarKeyReleased

        if (evt.getKeyCode() == evt.VK_ENTER) {
            Carrega1_item();
            jB_alt.setEnabled(false);
            jB_del.setEnabled(false);
        }
    }//GEN-LAST:event_jTextField_buscarKeyReleased

    private void jComboBox_prodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prodItemStateChanged
        try {
            jComboBox_prod_id_3.setSelectedIndex(jComboBox_prod.getSelectedIndex());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox_prodItemStateChanged

    private void jComboBox_prodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox_prodFocusLost
        if (jComboBox_prod_id_3.getSelectedIndex() == -1) {
            BloqueiaCampos_item();
        } else {
            jTextField_qtd.requestFocus();
            DesbloqueiaCampos_item();
        }
    }//GEN-LAST:event_jComboBox_prodFocusLost

    private void jComboBox_prodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_prodKeyPressed

    }//GEN-LAST:event_jComboBox_prodKeyPressed

    private void jComboBox_prodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_prodKeyReleased
        if (evt.getKeyCode() == evt.VK_F5) {
        PreencherProduto();
        }
        if (evt.getKeyCode() == evt.VK_ENTER) {

            if (jComboBox_prod_id_3.getSelectedIndex() == -1) {
                BloqueiaCampos_item();
            } else {
                jTextField_qtd.requestFocus();
                DesbloqueiaCampos_item();
            }
//            PreencheTabela();
        }
        if (evt.getKeyCode() == evt.VK_DELETE | evt.getKeyCode() == evt.VK_BACK_SPACE | evt.getKeyCode() == evt.VK_F2) {
            Carrega2_item();
        }
        if (evt.getKeyCode() == evt.VK_F12) {

            String codigo = (String) jComboBox_prod_id_3.getSelectedItem();
            info.RecebeId(codigo);
            info.Chama();
            info.setVisible(true);
            //   info.setVisible(true);
            System.out.println("EntradaNota.JDialog_AddItem.jComboBox1KeyReleased()");
        }
    }//GEN-LAST:event_jComboBox_prodKeyReleased

    private void jTextField_qtdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_qtdKeyReleased
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jB_add.requestFocus();
        }
        String s = jTextField_qtd.getText();
        if (s.matches("^[0-9,]*$")) {
        } else {
            jTextField_qtd.setText("");
        }
    }//GEN-LAST:event_jTextField_qtdKeyReleased

    private void jComboBox_prod_id_1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_prod_id_1ItemStateChanged
        try {
            jComboBox_prod.setSelectedIndex(jComboBox_prod_id_1.getSelectedIndex());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox_prod_id_1ItemStateChanged

    private void jB_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_addActionPerformed
        if (jTextField_qtd.getText().trim().isEmpty()) {
            jTextField_qtd.requestFocus();
        } else {
            Processar_item();
            Carrega2_item();
            limpar_item();
            jTable_item.setEnabled(true);

        }
    }//GEN-LAST:event_jB_addActionPerformed

    private void jB_addKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jB_addKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jB_add.doClick();
        }
    }//GEN-LAST:event_jB_addKeyPressed

    private void jB_comActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_comActionPerformed
        String codigo = (String) jComboBox_prod_id_3.getSelectedItem();
        complemento.RecebeId(codigo);

        complemento.setVisible(true);
    }//GEN-LAST:event_jB_comActionPerformed

    private void jB_limActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_limActionPerformed
        Carrega2_item();
        limpar_item();
    }//GEN-LAST:event_jB_limActionPerformed

    private void jB_altActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_altActionPerformed
        if (jTextField_qtd.getText().trim().isEmpty()) {
            jTextField_qtd.requestFocus();
        } else {
            ModoItem = 2;
            Processar_item();
            Carrega2_item();
            limpar_item();
            jTable_item.setEnabled(true);

        }
    }//GEN-LAST:event_jB_altActionPerformed

    private void jB_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_delActionPerformed
        if (jTextField_qtd.getText().trim().isEmpty()) {
            jTextField_qtd.requestFocus();
        } else {
            ModoItem = 3;
            Processar_item();
            Carrega2_item();
            limpar_item();
            jTable_item.setEnabled(true);

        }
    }//GEN-LAST:event_jB_delActionPerformed
    public void Carrega1() {
        System.out.println("botao");
        resposta = jTextField1.getText();
//        PreencherProduto();
        System.out.println("botao - resposta " + resposta);
        jComboBox_prod.setVisible(true);
        jTextField1.setVisible(false);
        jTextField2.setVisible(true);
        //jLabel2.setVisible(true);
        jComboBox_prod.requestFocus();
        jB_add.setVisible(true);
        jB_com.setVisible(true);
        jB_lim.setVisible(true);
        jB_alt.setVisible(true);
        jB_del.setVisible(true);
        jLabel_qtd.setVisible(true);
        jTextField_qtd.setVisible(true);
    }

    public void Carrega2() {
        jComboBox_prod.setVisible(false);
        jTextField1.setVisible(true);
//        jTextField2.setVisible(false);
        // jLabel2.setVisible(false);
        jTextField1.requestFocus();

        jB_add.setVisible(false);
        jB_com.setVisible(false);
        jB_lim.setVisible(false);
        jB_alt.setVisible(false);
        jB_del.setVisible(false);
//        jLabel_qtd.setVisible(false);
//                jTextField_qtd.setVisible(false);
    }

    public void DesbloqueiaCampos_item() {
        jB_add.setEnabled(true);
        jB_com.setEnabled(true);

//        jButton3.setEnabled(true);
//        jButton4.setEnabled(false);
//        jButton5.setEnabled(false);
    }

    public void BloqueiaCampos_item() {
        jB_add.setEnabled(false);
        jB_com.setEnabled(false);
//        jButton3.setEnabled(false);
        jB_alt.setEnabled(false);
        jB_del.setEnabled(false);
    }

    public void limpar_item() {
        jTextField_buscar.setText("");
        jTextField_qtd.setText("");
        jLabel_complemento.setText("");
        BloqueiaCampos_item();
        jTextField_buscar.requestFocus();

    }

    public void Carrega1_item() {
        System.out.println("botao");
        resposta = jTextField_buscar.getText();
        PreencherProduto();
        System.out.println("botao - resposta " + resposta);
        jComboBox_prod.setVisible(true);
        jTextField_buscar.setVisible(false);
        jTextField_qtd.setVisible(true);
        // jLabel2.setVisible(true);
        jComboBox_prod.requestFocus();
        jB_add.setVisible(true);
        jB_com.setVisible(true);
        jB_lim.setVisible(true);
        jB_alt.setVisible(true);
        jB_del.setVisible(true);
        jLabel_qtd.setVisible(true);
        jTextField_qtd.setVisible(true);
    }

    public void Carrega2_item() {
        jComboBox_prod.setVisible(false);
        jTextField_buscar.setVisible(true);
        jTextField_qtd.setVisible(false);
//        jLabel2.setVisible(false);
        jTextField_buscar.requestFocus();

        jB_add.setVisible(false);
        jB_com.setVisible(false);
        jB_lim.setVisible(false);
        jB_alt.setVisible(false);
        jB_del.setVisible(false);
        jLabel_qtd.setVisible(false);
        jTextField_qtd.setVisible(false);
    }

    public void PreencherProduto() {
        //f String resposta=(String) jComboBox1.getSelectedItem();
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_produto \n"
                + "inner join tbl_usuario on id_usuario = produto_usuario_id\n"
                + "inner join tbl_unidade on id_unidade = produto_unidade\n"
                + " where ( coalesce((produto_descricao))"
                + "||' '||coalesce((produto_observacao))||' '||coalesce((produto_registro ))ilike '%" + resposta + "%') and produto_status=1  \n"
                + "  order by produto_id  desc");
        try {
            conex.rs.first();
            jComboBox_prod.removeAllItems();
            jComboBox_prod_id_3.removeAllItems();
            jComboBox_prod_id_1.removeAllItems();

            do {
                jComboBox_prod.addItem(conex.rs.getString("produto_descricao"));
                jComboBox_prod_id_3.addItem(conex.rs.getString("produto_id"));
                jComboBox_prod_id_1.addItem(conex.rs.getString("produto_id"));
            } while (conex.rs.next());
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("EntradaNota.JDialog_AddItem.PreencheUnidade()");
        }
        conex.desconecta();

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
            java.util.logging.Logger.getLogger(AddProtocolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddProtocolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddProtocolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddProtocolo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddProtocolo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_add;
    private javax.swing.JButton jB_alt;
    private javax.swing.JButton jB_com;
    private javax.swing.JButton jB_del;
    private javax.swing.JButton jB_lim;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox_prod;
    private javax.swing.JComboBox<String> jComboBox_prod_id_1;
    private javax.swing.JComboBox<String> jComboBox_prod_id_3;
    private com.toedter.calendar.JDateChooser jDateChooser_Data_Nota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel_complemento;
    public static javax.swing.JLabel jLabel_observacao;
    private javax.swing.JLabel jLabel_qtd;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_item;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField_Hora;
    public static javax.swing.JTextField jTextField_buscar;
    private javax.swing.JTextField jTextField_qtd;
    // End of variables declaration//GEN-END:variables

}
