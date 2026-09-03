import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class frmCajaRegistradora extends JFrame {

    // variables globales
    private int[] denominaciones = {100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private String[] tipo = {"Billete","Billete","Billete","Billete","Billete","Billete","Billete","Moneda","Moneda","Moneda","Moneda"};
    private int[] existencias = new int[denominaciones.length];
    private int[] cantidadUsada = new int[denominaciones.length];

    private JComboBox cmbDenominacion;
    private JTextField txtExistencia;
    private JTextField txtValorDevolver;
    private JTable tblDevuelta;

    private String[] encabezados = {"Cantidad","Presentación","Denominación"};

    // método constructor
    public frmCajaRegistradora() {
        // definir tamaño de la ventana
        setSize(600, 400);
        // asignar título
        setTitle("Caja Registradora");
        // operación de cierre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // quitar distribución
        setLayout(null);

        JLabel lblDen = new JLabel("Denominación:");
        lblDen.setBounds(10, 10, 100, 25);
        add(lblDen);

        cmbDenominacion = new JComboBox();
        for (int den : denominaciones) {
            cmbDenominacion.addItem(den);
        }
        cmbDenominacion.setBounds(120, 10, 100, 25);
        add(cmbDenominacion);

        JLabel lblExist = new JLabel("Cantidad disponible:");
        lblExist.setBounds(10, 45, 120, 25);
        add(lblExist);

        txtExistencia = new JTextField();
        txtExistencia.setBounds(140, 45, 80, 25);
        add(txtExistencia);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(230, 45, 100, 25);
        add(btnActualizar);

        JLabel lblValor = new JLabel("Valor a Devolver:");
        lblValor.setBounds(10, 80, 120, 25);
        add(lblValor);

        txtValorDevolver = new JTextField();
        txtValorDevolver.setBounds(140, 80, 100, 25);
        add(txtValorDevolver);

        JButton btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(250, 80, 100, 25);
        add(btnDevolver);

        tblDevuelta = new JTable();
        JScrollPane spTabla = new JScrollPane(tblDevuelta);
        spTabla.setBounds(10, 120, 560, 200);
        add(spTabla);

        // inicializar tabla
        DefaultTableModel dtm = new DefaultTableModel(new String[denominaciones.length][encabezados.length], encabezados);
        tblDevuelta.setModel(dtm);

        // eventos
        btnActualizar.addActionListener(e -> actualizarExistencia());
        btnDevolver.addActionListener(e -> calcularDevuelta());
    }

    // método para actualizar existencias
    private void actualizarExistencia() {
        int den = Integer.parseInt(cmbDenominacion.getSelectedItem().toString());
        int cantidad = Integer.parseInt(txtExistencia.getText());
        for (int i = 0; i < denominaciones.length; i++) {
            if (denominaciones[i] == den) {
                existencias[i] = cantidad;
            }
        }
        JOptionPane.showMessageDialog(this, "Existencia actualizada: " + cantidad + " unidades de $" + den);
    }

    // método para calcular la devuelta
    private void calcularDevuelta() {
        int devuelta = Integer.parseInt(txtValorDevolver.getText());
        int[] copiaExistencias = existencias.clone();

        for (int i = 0; i < denominaciones.length; i++) {
            cantidadUsada[i] = 0;
            while (devuelta >= denominaciones[i] && copiaExistencias[i] > 0) {
                devuelta -= denominaciones[i];
                copiaExistencias[i]--;
                cantidadUsada[i]++;
            }
        }

        String[][] datos = new String[denominaciones.length][encabezados.length];
        for (int i = 0; i < denominaciones.length; i++) {
            datos[i][0] = String.valueOf(cantidadUsada[i]);
            datos[i][1] = tipo[i];
            datos[i][2] = "$" + denominaciones[i];
        }

        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tblDevuelta.setModel(dtm);

        if (devuelta > 0) {
            JOptionPane.showMessageDialog(this, "No fue posible entregar la devuelta completa. Faltan $" + devuelta);
        }
    }

    public static void main(String[] args) {
        frmCajaRegistradora ventana = new frmCajaRegistradora();
        ventana.setVisible(true);
    }

    }