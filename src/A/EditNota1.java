/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A;

import ConectaBanco.ConexaoBD;
import EntradaNota.Item.JDialog_AddItem;
import EntradaNota.Item.JDialog_AddItem_Obs;
import EntradaNota.ListaNota;
import ModeloBeans.BeansNota;
import ModeloBeans.ModeloTabela;

import ModeloDao.DaoNota;
import Principal.Menu;
import Validacao.ValidarNota;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author edson
 */
public class EditNota1 extends javax.swing.JFrame {

    ConexaoBD conex = new ConexaoBD();
    BeansNota BEANS = new BeansNota();
    DaoNota DAO = new DaoNota();
    ValidarNota validar = new ValidarNota();
    String resposta = "", observacao = "", MinhaData, UltimoIdNota = "";
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    Date data = null;

    /**
     * Creates new form AddNota2
     */
    public EditNota1() {
        initComponents();
//        BloqueiaCampos1();
//        PreencherNatureza();
//        PreencherFornecedor();
        //PreencheTabela();
        jComboBox2.setVisible(false);
        jComboBox4.setVisible(false);
        jComboBox5.setVisible(false);
        jComboBox6.setVisible(false);
        jLabel_observacao.setVisible(false);

    }

    public void LimpaTudo() {
        jDateChooser_Data_Nota.setDate(null);
        jTextField_Hora.setText(null);
        jTextField2.setText(null);
        jTextField3.setText(null);
        observacao = "";
        UltimoIdNota = "";
        PreencherNatureza();
        PreencherFornecedor();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{};
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        jTable1.setModel(modelo);
        ListaNota.jMenuItem4.doClick();
        this.dispose();

    }

    public void Validar() {

        BEANS.setId_nota(Integer.parseInt(UltimoIdNota));
        observacao = jLabel_observacao.getText();
        BEANS.setNota_observacao(observacao);
        DAO.AtualizaObs(BEANS);

        BEANS.setId_nota(Integer.parseInt(UltimoIdNota));
        validar.Validar(BEANS);
        VerificaValidacao();
        validar.LimpaValidar(BEANS);

    }

    public void VerificaValidacao() {

        System.out.println("chamou - VerificaValidacao");
        conex.conexao();
        conex.executaSql2(" SELECT item_id FROM public.tbl_item  where item_nota_id=" + UltimoIdNota + "");
        try {
            conex.rs.first();
            String Verificacao = conex.rs.getString("item_id");
            System.out.println("EntradaNota.AddNota.VerificaValidacao()");
            BEANS.setId_nota(Integer.parseInt(UltimoIdNota));
            BEANS.setNota_status(8);
            //JOptionPane.showMessageDialog(rootPane, "VerificaValidacao - Verificacao = " + Verificacao);
            validar.ValidaStatusNota(BEANS);
        } catch (SQLException ex) {
            System.out.println("VerificaValidacao " + ex);
            BEANS.setId_nota(Integer.parseInt(UltimoIdNota));
            BEANS.setNota_status(9);
            validar.ValidaStatusNota(BEANS);
            //JOptionPane.showMessageDialog(rootPane, "VerificaValidacao - ex = " + ex);
        }
        conex.desconecta();
    }

    public void VerificaLimpaValidar() {
        System.out.println("chamou - VerificaLimpaValidar");
        conex.conexao();
        conex.executaSql2(" SELECT * FROM public.tbl_item_temp   ");
        try {
            conex.rs.first();
            String Verificacao = conex.rs.getString("item_id");
            System.out.println("VerificaLimpaValidar - encontrou registro " + Verificacao);
        } catch (SQLException ex) {
            System.out.println("VerificaLimpaValidar " + "- zerou");
            //DAO.ZeraTabelaItemTemp(BEANS);
        }
        conex.desconecta();
    }

    public void PreencheTabela() {
        ArrayList dados = new ArrayList();

        conex.conexao();
//UltimoIdNota="53";
        String[] colunas = new String[]{"Codigo ", "Produto", "UN", "Valor Un", "Quantidade", "Sub total"};
//item_id_nota="49";
        conex.executaSql2("SELECT * ,SUM ((produto_valor * t_qtd)) AS total FROM public.tbl_item_temp \n"
                + "                inner join public.tbl_produto on produto_id  =t_produto_id \n"
                + "                inner join public.tbl_unidade on id_unidade =produto_unidade   where item_id_nota=" + UltimoIdNota + " and t_item_st !=3 and t_item_st !=2\n"
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
            ////JOptionPane.showMessageDialog(rootPane, "Jdialog_AddItem - " + ex);
//            Logger.getLogger(UsuarioJIF.class.getName()).log(Level.SEVERE, null, ex);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        jTable1.setModel(modelo);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
        jTable1.setRowSorter(sorter);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);

        jTable1.getColumnModel().getColumn(0).setResizable(true);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTable1.getColumnModel().getColumn(1).setResizable(true);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(2).setResizable(true);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setResizable(true);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setResizable(true);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(5).setResizable(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        conex.desconecta();
        PreencherTotal();
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

        BEANS.setNota_id_cfop(Integer.parseInt((String) jComboBox2.getSelectedItem()));
        BEANS.setNota_id_fornecedor(Integer.parseInt((String) jComboBox4.getSelectedItem()));
        if (jDateChooser_Data_Nota.getDate() == (null)) {
            MinhaData = ("");
        } else {
            data = jDateChooser_Data_Nota.getDate();
            MinhaData = (formato.format(data));
        }
        BEANS.setNota_data(MinhaData);

        BEANS.setNota_hora(jTextField_Hora.getText());
        BEANS.setNota_numero(jTextField2.getText());
        BEANS.setNota_chave(jTextField3.getText());
        String total = jLabel_Total.getText();
        total = total.replace(".", "").replace(",", ".").replace("R$ ", "").replace("R$", "").trim();
        BEANS.setNota_total(Double.parseDouble(total));
        BEANS.setNota_status(9);
        BEANS.setNota_id_usuario(Integer.parseInt(Menu.jLabelIdUsuario.getText()));
        BEANS.setNota_observacao(observacao);
        BEANS.setId_nota(Integer.parseInt(UltimoIdNota));
        DAO.ProcessarAlteracao(BEANS);
        // CarregaUltimo();
        ListaNota.jMenuItem4.doClick();
        PreencheTabela();
    }

    public void RecebeId(int id) {
        UltimoIdNota = "" + id;
//        rid = id;s
        System.err.println("Editar recebe id --  " + UltimoIdNota);
    }

    public void Chama() {
        System.err.println("EntradaNota.EditNota.Chama()");
        conex.conexao();
        conex.executaSql2(" SELECT *"
                + "  FROM public.tbl_nota where id_nota=" + UltimoIdNota + "  \n"
                + "  order by id_nota  asc");
        try {
            conex.rs.last();
            UltimoIdNota = conex.rs.getString("id_nota");
            jComboBox5.setSelectedItem(conex.rs.getString("nota_id_cfop"));
            jComboBox6.setSelectedItem(conex.rs.getString("nota_id_fornecedor"));
            jTextField_Hora.setText(conex.rs.getString("nota_hora"));
            jTextField2.setText(conex.rs.getString("nota_numero"));
            jTextField3.setText(conex.rs.getString("nota_chave"));
            jLabel_observacao.setText(conex.rs.getString("nota_observacao"));
            this.setTitle("Editar Nf-e " + " ( Código " + UltimoIdNota + " )");
            System.out.println("EntradaNota.EditNota.Chama() " + conex.rs.getString("nota_data"));

            try {
                data = formato.parse(conex.rs.getString("nota_data"));
                jDateChooser_Data_Nota.setDate(data);

            } catch (Exception e) {
            }

            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("erro " + ex);
        }

        conex.desconecta();

        BEANS.setId_nota(Integer.parseInt(UltimoIdNota));
        DAO.PuxaDados(BEANS);
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
            jLabel_Total.setText(FormatoTotal);
            System.err.println("texto " + conex.rs.getString("total"));

            System.out.println("carregado ");
        } catch (SQLException ex) {
            System.out.println("PreencherTotal " + ex);
        }
        conex.desconecta();

    }

    public void PreencherNatureza() {
        System.out.println("chamou - PreencherNatureza");
        conex.conexao();
        conex.executaSql2(" SELECT *  FROM tbl_cfop where   status_cfop=1  \n"
                + "  order by id_cfop  asc");
        try {
            conex.rs.first();
            jComboBox1.removeAllItems();
            jComboBox2.removeAllItems();
            jComboBox5.removeAllItems();
            do {
                String a = conex.rs.getString("sigla_cfop");
                String b = conex.rs.getString("descricao_cfop");
                jComboBox1.addItem(a + " | " + b);
                jComboBox2.addItem(conex.rs.getString("id_cfop"));
                jComboBox5.addItem(conex.rs.getString("id_cfop"));
                System.err.println("texto " + conex.rs.getString("descricao_cfop"));
            } while (conex.rs.next());
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("EntradaNota.JDialog_AddItem.PreencheUnidade()");
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
            jComboBox6.removeAllItems();

            do {
                jComboBox3.addItem(conex.rs.getString("fornecedor_nome"));
                jComboBox4.addItem(conex.rs.getString("fornecedor_id"));
                jComboBox6.addItem(conex.rs.getString("fornecedor_id"));
                System.out.println("produto_produto " + conex.rs.getString("fornecedor_nome"));
            } while (conex.rs.next());
            System.out.println("carregado");
        } catch (SQLException ex) {
            System.out.println("EntradaNota.JDialog_AddItem.PreencheUnidade()");
        }
        conex.desconecta();

    }

    public void BloqueiaCampos1() {
        jTable1.setEnabled(false);
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false);
        jPanel2.setEnabled(false);
        DesbloqueiaCampos2();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{};
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        jTable1.setModel(modelo);
    }

    public void DesbloqueiaCampos1() {
        jTable1.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        jPanel2.setEnabled(true);
    }

    public void BloqueiaCampos2() {
        jComboBox1.setEnabled(false);
        jComboBox2.setEnabled(false);
        jComboBox3.setEnabled(false);
        jComboBox4.setEnabled(false);
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jDateChooser_Data_Nota.setEnabled(false);
        jTextField_Hora.setEnabled(false);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jLabel1.setEnabled(false);
        jLabel2.setEnabled(false);
        jLabel3.setEnabled(false);
        jLabel4.setEnabled(false);
        DesbloqueiaCampos1();
    }

    public void DesbloqueiaCampos2() {
        jComboBox1.setEnabled(true);
        jComboBox2.setEnabled(true);
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jDateChooser_Data_Nota.setEnabled(true);
        jTextField_Hora.setEnabled(true);
        jTextField2.setEnabled(true);
        jTextField3.setEnabled(true);
        jLabel1.setEnabled(true);
        jLabel2.setEnabled(true);
        jLabel3.setEnabled(true);
        jLabel4.setEnabled(true);
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
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jDateChooser_Data_Nota = new com.toedter.calendar.JDateChooser();
        jTextField_Hora = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel_Total = new javax.swing.JLabel();
        jLabel_observacao = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jTextField1.setText("jTextField1");

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Nf-e");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Natureza :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jDateChooser_Data_Nota.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jDateChooser_Data_NotaHierarchyChanged(evt);
            }
        });
        jDateChooser_Data_Nota.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jDateChooser_Data_NotaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jDateChooser_Data_NotaAncestorMoved(evt);
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

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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

        jLabel3.setText("Nota :");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel4.setText("Chave :");

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("Processar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total");

        jLabel_Total.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_Total.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_Total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Total.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel_observacao.setText(" ");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, 295, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser_Data_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(205, 205, 205)
                                .addComponent(jLabel_observacao)
                                .addGap(23, 23, 23)))
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jDateChooser_Data_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel_observacao))
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setText("Adicionar item");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
        jButton6.setText("Validar nota");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jButton5)
                        .addComponent(jButton6))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jMenuItem2.setText("Reprocessar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("jMenuItem3");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("jMenuItem4");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

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
        jLabel5.setText("Nota em processamento ");
        Processar();
        PreencherTotal();
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

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
//        jComboBox2.setSelectedIndex(jComboBox1.getSelectedIndex());
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        try {
            jComboBox2.setSelectedIndex(jComboBox1.getSelectedIndex());
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        try {
            jComboBox4.setSelectedIndex(jComboBox3.getSelectedIndex());
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        BloqueiaCampos1();
        jLabel5.setText(" ");
        jComboBox1.requestFocus();
        Validar();
        LimpaTudo();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jLabel5.setText("Nota em edição ");
        String t="q";
        JDialog_AddItem AddItem = new JDialog_AddItem(this, rootPaneCheckingEnabled);
        AddItem.RecebeItemIdNota(UltimoIdNota,t);
        AddItem.PreencheTabela();
        AddItem.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        PreencheTabela();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        if (evt.getKeyCode() == evt.VK_F5) {
            PreencherNatureza();
        }
        if (evt.getKeyCode() == evt.VK_F12) {
            jMenuItem1.doClick();
        }
    }//GEN-LAST:event_jComboBox1KeyPressed

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

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        String s = jTextField3.getText();
        String formatado = "";
        if (evt.getKeyCode() == evt.VK_DELETE | evt.getKeyCode() == evt.VK_BACK_SPACE
                | evt.getKeyCode() == evt.VK_SPACE | evt.getKeyCode() == evt.VK_RIGHT | evt.getKeyCode() == evt.VK_LEFT
                | evt.getKeyCode() == evt.VK_CONTROL | evt.getKeyCode() == evt.VK_CONTROL + evt.VK_V) {

        } else {
//            if (s.matches("^[0-9]*$")) {
            if (s.length() < 54) {
            } else if (s.length() > 54) {
                formatado = s.substring(0, 54);
                jTextField3.setText(formatado);
            }
//            } else {
//                jTextField3.setText("");
//            }
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JDialog_AddItem_Obs AddItem_Obs = new JDialog_AddItem_Obs(this, rootPaneCheckingEnabled);
        AddItem_Obs.RecebeObs(jLabel_observacao.getText());
        AddItem_Obs.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        try {
            jComboBox1.setSelectedIndex(jComboBox5.getSelectedIndex());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        try {
            jComboBox3.setSelectedIndex(jComboBox6.getSelectedIndex());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        BloqueiaCampos1();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        jPanel3.setVisible(false);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        jPanel3.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
            java.util.logging.Logger.getLogger(EditNota1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditNota1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditNota1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditNota1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditNota1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser_Data_Nota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Total;
    public static javax.swing.JLabel jLabel_observacao;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField_Hora;
    // End of variables declaration//GEN-END:variables

}
