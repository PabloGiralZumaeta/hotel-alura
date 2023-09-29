package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alura.hotel.controller.FormaDePagoController;
import com.alura.hotel.controller.HuespedesController;
import com.alura.hotel.controller.ReservasController;
import com.alura.hotel.modelo.Huespedes;
import com.alura.hotel.modelo.Reservas;
import com.mysql.cj.protocol.Message;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservasController reservaController;
	private HuespedesController huespedController;
	private FormaDePagoController formaPagoController;
	private ReservasView reservaVista;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.reservaController = new ReservasController();
		this.formaPagoController = new FormaDePagoController();
		this.reservaVista = new ReservasView();
		this.huespedController = new HuespedesController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mostrarReservas();
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		mostrarHuesped();
		// dasasas
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if (txtBuscar.getText().isEmpty()) {
					mostrarReservas();
					mostrarHuesped();
				} else {
					mostrarReservasPorId();
					mostrarHuespedPorId();
				}

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();
				if (filaReservas >= 0 ) {
					actualizarReserva();
					limpiarTabla();
					mostrarReservas();
					mostrarHuesped();
//					System.out.println("Mostrar si llego");
				}
				else if(filaHuespedes>=0) {
					actualizarHuesped();
					limpiarTabla();
					mostrarHuesped();
					mostrarReservas();
				}
				
			
			};
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuesped = tbHuespedes.getSelectedRow();
				
				try{
				if (filaReservas >= 0) {
//					String reservas = tbReservas.getValueAt(filasReservas, 0).toString(); 
					int confirmar = JOptionPane.showConfirmDialog(null, "Desea borrar resrevas?");
					if (confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(filaReservas, 0).toString();
						
						reservaController.eliminarReserva(Long.valueOf(valor));
						//Esto esta a prueba
						huespedController.elimnarHuespedPorIdReserva(Long.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Se elimino el registro de manera exitosa");
						limpiarTabla();
						mostrarReservas();
						mostrarHuesped();
					}
				}
				else if (filaHuesped>=0){
					int confirmarHuesped =  JOptionPane.showConfirmDialog(null, "Deseas borrar el huesped?");
					
					if(confirmarHuesped == JOptionPane.YES_OPTION) {
						String valor = tbHuespedes.getValueAt(filaHuesped, 0).toString();
						huespedController.eliminarHuesped(Long.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane,"Huesped Borrado con exito!" );
						limpiarTabla();
						mostrarReservas();
						mostrarHuesped();
					}
				}
			}catch (Exception exception) {
				// TODO: handle exception
				JOptionPane.showMessageDialog( null,"Hubo un eror al eliminar");
				throw new RuntimeException(exception);
				
			}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	
	// LIMPIAR
	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}

	//RESERVAS
	
	public List<Reservas> listarReserva() {
		return this.reservaController.listarReservas();
	};

	public List<Reservas> listarReservaPorId() {
		Long idReserva = Long.valueOf(txtBuscar.getText());
		return this.reservaController.listarReservasPorId(idReserva);
	};

	public void mostrarReservas() {
		List<Reservas> reservas = listarReserva();

		// devuelve la tabla a 0
		modelo.setRowCount(0);

		try {

			for (Reservas reserva : reservas) {
				// transforma el numero de id de pago en un String
				String formaPagoNombre = this.formaPagoController.buscarNombrePorId(reserva.getIdFormaPago());
				modelo.addRow(new Object[] { reserva.getIdReserva(), reserva.getFechaE(), reserva.getFechaS(),
						reserva.getValor(), formaPagoNombre });
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void mostrarReservasPorId() {
		String idReserva = txtBuscar.getText();
		List<Reservas> reservas = listarReservaPorId();

		modelo.setRowCount(0);

		try {

			for (Reservas reserva : reservas) {
				// transforma el numero de id de pago en un String
				String formaPagoNombre = this.formaPagoController.buscarNombrePorId(reserva.getIdFormaPago());
				modelo.addRow(new Object[] { reserva.getIdReserva(), reserva.getFechaE(), reserva.getFechaS(),
						reserva.getValor(), formaPagoNombre });
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void actualizarReserva() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresent(fila -> {
					LocalDate dataE;
					LocalDate dataS;

					try {
						DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						dataE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(),
								dateFormat);
						dataS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(),
								dateFormat);
					} catch (DateTimeException e) {
						// TODO: handle exception
						System.out.println("Aqui esta el error");
						throw new RuntimeException(e);
					}
					this.reservaVista.limpiarValor();

					BigDecimal valor = calcularValorReserva(dataE, dataS);
					// Obtener el nombre de la forma de pago a partir del ID en la columna 4
					String formaPagoNombre = modelo.getValueAt(tbReservas.getSelectedRow(), 4).toString();
					// este convertira el nombre en el id de la forma de pago
					int formaPagoId = this.formaPagoController.buscarIdPorNombre(formaPagoNombre);

					Long id = Long.valueOf( modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

					// Para no cambiar el id;
					if (tbReservas.getSelectedColumn() == 0) {
						JOptionPane.showMessageDialog(this, "No se puede cambiar los id");
					} else {
						this.reservaController.actualizarReserva(dataE, dataS, valor, formaPagoId, id);
						JOptionPane.showMessageDialog(this, "Registro modificado con exito");
					}
				});
	}

	private BigDecimal calcularValorReserva(LocalDate dataE, LocalDate dataS) {
		// TODO Auto-generated method stub
		if (dataE != null && dataS != null) {
			int dias = (int) ChronoUnit.DAYS.between(dataE, dataS);
			int noche = 35;
			int valor = dias * noche;
			return BigDecimal.valueOf(valor);
		} else {
			return BigDecimal.ZERO;
		}
	}

	// HUESPEDES
	private List<Huespedes> listarHuespedes() {
		return this.huespedController.listarHuesped();
	}

	private List<Huespedes> listarHuespedesPorId() {
		Long idHuesped = Long.valueOf(txtBuscar.getText());
		return this.huespedController.listarHuespedPorId(idHuesped);
	}

	private void mostrarHuesped() {
		List<Huespedes> huespedes = listarHuespedes();

		// devuelve la tabla a 0
		modeloHuesped.setRowCount(0);

		try {

			for (Huespedes huesped : huespedes) {

				modeloHuesped.addRow(new Object[] { huesped.getIdHuesped(), huesped.getNombre(), huesped.getApellido(),
						huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
						huesped.getIdReserva() });
			}
			;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void mostrarHuespedPorId() {
		List<Huespedes> huespedes = listarHuespedesPorId();

		modeloHuesped.setRowCount(0);

		try {

			for (Huespedes huesped : huespedes) {

				modeloHuesped.addRow(new Object[] { huesped.getIdHuesped(), huesped.getNombre(), huesped.getApellido(),
						huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
						huesped.getIdReserva() });
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}


	
	private void actualizarHuesped() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresent(fila -> {

					LocalDate fechaNacimiento;
					try {
						DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						fechaNacimiento = LocalDate.parse(
								modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString(), dateFormat);

					} catch (DateTimeException e) {
						// TODO: handle exception
						System.out.println("Aqui esta el error");
						throw new RuntimeException(e);
					}

					String nombre = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1).toString();
					String apellido = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2).toString();
					String nacionalidad = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4).toString();
					String telefono = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5).toString();
					Long idReserva = Long.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
					Long idHuesped = Long.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());


					// Para no cambiar el id;
					if (tbHuespedes.getSelectedColumn() == 0) {
						JOptionPane.showMessageDialog(this, "No se puede cambiar los id del huesped");
					}else if(tbHuespedes.getSelectedColumn() == 6 ) {
						JOptionPane.showMessageDialog(this, "No se puede cambiar los id de la reserva, es automatica");

					} else {
						this.huespedController.actualizarHuesped(nombre, apellido, fechaNacimiento, nacionalidad,
								telefono, idReserva, idHuesped);
						JOptionPane.showMessageDialog(this, "Huesped modificado con exito");
					}
				});
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
